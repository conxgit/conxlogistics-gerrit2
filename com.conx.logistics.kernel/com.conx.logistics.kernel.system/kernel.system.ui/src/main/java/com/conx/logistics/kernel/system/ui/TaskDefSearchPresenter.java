package com.conx.logistics.kernel.system.ui;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.ViewFactoryException;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.system.ui.view.TaskDefSearchView;
import com.conx.logistics.kernel.system.ui.view.ITaskDefSearchView;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.view.feature.FeatureView;
import com.conx.logistics.kernel.ui.common.ui.form.field.BasicFieldFactory;
import com.conx.logistics.mdm.domain.task.TaskDefinition;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

@Presenter(view = TaskDefSearchView.class)
public class TaskDefSearchPresenter extends
		BasePresenter<ITaskDefSearchView, TaskDefSearchEventBus> {

	private MainMVPApplication application;
	private FeatureView fv;

	private IPresenter<?, ? extends EventBus> contentPresenter;
	private VerticalLayout defaultDetailView;
	private EntityManager kernelSystemEntityManager;
	private JPAContainer<TaskDefinition> taskDefs;

	public void onLaunch(MainMVPApplication app) {
		// keep a reference to the application instance
		this.application = app;

		// -- Init task def table
		EntityManagerFactory kernelSystemEntityManagerFactory = this.application
				.getKernelSystemEntityManagerFactory();
		this.kernelSystemEntityManager = kernelSystemEntityManagerFactory
				.createEntityManager();
		this.taskDefs = JPAContainerFactory.make(TaskDefinition.class,
				this.kernelSystemEntityManager);

		this.view.getSearchGrid().setContainerDataSource(this.taskDefs);
		this.view.getSearchGrid().setSizeFull();
		this.view.getSearchGrid().setSelectable(true);
		this.view.getSearchGrid().setNullSelectionAllowed(false);
		this.view.getSearchGrid().setColumnCollapsingAllowed(true);
		this.view.getSearchGrid().setColumnReorderingAllowed(true);
		this.view.getSearchGrid().addListener(new ItemClickListener() {
			private static final long serialVersionUID = 7230326485331772539L;

			public void itemClick(ItemClickEvent event) {
				showDetailView((JPAContainerItem<TaskDefinition>) event
						.getItem());
			}
		});

		this.view.getSearchGrid().setColumnHeader("name", "Name");
		this.view.getSearchGrid().setColumnHeader("description", "Description");
		this.view.getSearchGrid().setColumnHeader("bpmn2ProcDefURL",
				"BPMN2.0 Proc");
		this.view.getSearchGrid().setVisibleColumns(
				new Object[] { "name", "description", "bpmn2ProcDefURL",
						"version", "dateCreated", "dateLastUpdated" });

		// -- Init detail form
		Collection<?> visibleProps = java.util.Arrays.asList(new Object[] {
				"name", "description", "bpmn2ProcDefURL", "version",
				"dateCreated", "dateLastUpdated", "parentApplication" });
		Collection<?> editableProps = java.util.Arrays
				.asList(new Object[] { "name", "description",
						"bpmn2ProcDefURL", "parentApplication" });

		this.view.getTaskDefForm().setFormFieldFactory(
				new BasicFieldFactory(this.application
						.getEntityTypeContainerFactory(), visibleProps,
						editableProps));

		this.view.getTaskDefForm().setItemDataSource(
				this.taskDefs.createEntityItem(new TaskDefinition()));
		this.view.getTaskDefForm().setEnabled(false);
		this.view.getTaskDefForm().setWriteThrough(false);
		this.view.getTaskDefForm().setCaption("Task Def Editor");
		this.view.getTaskDefForm().addStyleName("bordered"); // Custom style
		this.view.getTaskDefForm().getField("name").setReadOnly(false);
		this.view.getTaskDefForm().getField("description").setReadOnly(false);
		this.view.getTaskDefForm().getField("bpmn2ProcDefURL")
				.setReadOnly(false);
	}

	private void showDetailView(JPAContainerItem<TaskDefinition> entityItem) {
		this.view.getTaskDefForm().setItemDataSource(entityItem);
	}

	public void onShowDialog(Window dialog) {
		this.application.getMainWindow().addWindow(dialog);
	}

	@Override
	public void bind() {
		// -- Setup layout
		VerticalLayout mainLayout = this.view.getMainLayout();
		VerticalSplitPanel layoutPanel = this.view.getSplitLayout();
		layoutPanel.setStyleName("main-split");
		layoutPanel.setSizeFull();
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(layoutPanel, 1.0f);
		layoutPanel.setSplitPosition(300, HorizontalSplitPanel.UNITS_PIXELS);

		// -- Setup gridlayout
		VerticalLayout gridLayout = this.view.getGridLayout();
		Table grid = this.view.getSearchGrid();
		HorizontalLayout buttonLayout = this.view.getButtonLayout();
		gridLayout.setExpandRatio(grid, 1.0f);
		// gridLayout.setExpandRatio(buttonLayout, 1);
		// buttonLayout.setHeight(20,Sizeable.UNITS_PIXELS);

		// -- Setup detal view
		Label defaultDetailViewMessage = new Label("Select an task definition");
		defaultDetailViewMessage.addStyleName("defaultDetailViewMessage");
		defaultDetailView = new VerticalLayout();
		defaultDetailView.setSizeFull();
		defaultDetailView.addComponent(defaultDetailViewMessage);
		defaultDetailView.setComponentAlignment(defaultDetailViewMessage,
				Alignment.MIDDLE_CENTER);
		// this.view.setDetailComponent(this.view.getDetailLayout());
	}

	/**
	 * 
	 * CRUD Event Listeners
	 * 
	 */
	public void onCreateTaskDef() throws ViewFactoryException {
		TaskDefinition td = new TaskDefinition();
		td.setName("new task def");
		td.setBpmn2ProcDefURL("Enter New BPMN2.0 Process URL");
		// BeanItem<TaskDefinition> beanItem = new BeanItem<TaskDefinition>(td);
		this.view.getTaskDefForm().setItemDataSource(
				this.taskDefs.createEntityItem(td));
		this.view.getTaskDefForm().setEnabled(true);
		resetButtons(true, false, true);
	}

	public void onRemoveTaskDef() {
		// check if a user is selected in the table
		Table grid = this.view.getSearchGrid();
		Object selected = grid.getValue();
		if (selected != null) {
			this.taskDefs.removeItem(selected);
		}
		this.view.getTaskDefForm().discard();
		this.view.getTaskDefForm().setEnabled(false);
		resetButtons(false, false, false);
	}

	public void onSaveTaskDef() {
		this.view.getTaskDefForm().commit();
		this.view.getTaskDefForm().setEnabled(false);
		resetButtons(false, true, false);
	}

	public void onEditTaskDef() {
		if (this.view.getSearchGrid().getValue() != null)
		{
			this.view.getTaskDefForm().setEnabled(true);
			resetButtons(true, false, false);
		}
	}
	
	public void onCancelTaskDef() {
		this.view.getTaskDefForm().discard();
		this.view.getTaskDefForm().setEnabled(false);
		resetButtons(false, true, false);
	}	
	
	private void resetButtons(boolean saveEnable, boolean editEnable, boolean cancelEnable)
	{
		//this.view.getSaveTaskDef().setEnabled(saveEnable);
		//this.view.getEditTaskDef().setEnabled(editEnable);
		//this.view.getCancelEditTaskDef().setEnabled(cancelEnable);
	}
}
