package com.conx.logistics.app.whse.ui.asn;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.ViewFactoryException;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.ui.view.asn.ASNSearchView;
import com.conx.logistics.app.whse.ui.view.asn.IASNSearchView;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.view.feature.FeatureView;
import com.conx.logistics.kernel.ui.common.ui.form.field.BasicFieldFactory;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

@Presenter(view = ASNSearchView.class)
public class ASNSearchPresenter extends
		BasePresenter<IASNSearchView, ASNSearchEventBus> {

	private MainMVPApplication application;
	private FeatureView fv;

	private IPresenter<?, ? extends EventBus> contentPresenter;
	private VerticalLayout defaultDetailView;
	private EntityManager kernelSystemEntityManager;
	private JPAContainer<ASN> asns;

	public void onLaunch(MainMVPApplication app) {
		// keep a reference to the application instance
		this.application = app;

		// -- Init task def table
		EntityManagerFactory kernelSystemEntityManagerFactory = this.application
				.getKernelSystemEntityManagerFactory();
		this.kernelSystemEntityManager = kernelSystemEntityManagerFactory
				.createEntityManager();
		this.asns = JPAContainerFactory.make(ASN.class,
				this.kernelSystemEntityManager);

		this.view.getSearchGrid().setContainerDataSource(this.asns);
		this.view.getSearchGrid().setSizeFull();
		this.view.getSearchGrid().setSelectable(true);
		this.view.getSearchGrid().setNullSelectionAllowed(false);
		this.view.getSearchGrid().setColumnCollapsingAllowed(true);
		this.view.getSearchGrid().setColumnReorderingAllowed(true);
		this.view.getSearchGrid().addListener(new ItemClickListener() {
			private static final long serialVersionUID = 7230326485331772539L;

			@Override
			public void itemClick(ItemClickEvent event) {
				showDetailView((JPAContainerItem<ASN>) event
						.getItem());
			}
		});

		this.view.getSearchGrid().setColumnHeader("dateCreated", "Date Created");
		this.view.getSearchGrid().setColumnHeader("dateLastUpdated", "Date Last Updated");
		this.view.getSearchGrid().setVisibleColumns(
				new Object[] { "dateCreated", "dateLastUpdated"});

		// -- Init detail form
		Collection<?> visibleProps = java.util.Arrays.asList(new Object[] {
				"dateCreated", "dateLastUpdated"});
		Collection<?> editableProps = java.util.Arrays
				.asList(new Object[] { });

		this.view.getASNForm().setFormFieldFactory(
				new BasicFieldFactory(this.application
						.getEntityTypeContainerFactory(), visibleProps,
						editableProps));

		this.view.getASNForm().setItemDataSource(
				this.asns.createEntityItem(new ASN()));
		this.view.getASNForm().setEnabled(false);
		this.view.getASNForm().setWriteThrough(false);
		this.view.getASNForm().setCaption("ASN Editor");
		this.view.getASNForm().addStyleName("bordered"); // Custom style
		//this.view.getASNForm().getField("name").setReadOnly(false);
		//this.view.getASNForm().getField("description").setReadOnly(false);
		//this.view.getASNForm().getField("bpmn2ProcDefURL").setReadOnly(false);
	}

	private void showDetailView(JPAContainerItem<ASN> entityItem) {
		this.view.getASNForm().setItemDataSource(entityItem);
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
		layoutPanel.setSplitPosition(300, Sizeable.UNITS_PIXELS);

		// -- Setup gridlayout
		VerticalLayout gridLayout = this.view.getGridLayout();
		Table grid = this.view.getSearchGrid();
		HorizontalLayout buttonLayout = this.view.getButtonLayout();
		gridLayout.setExpandRatio(grid, 1.0f);
		// gridLayout.setExpandRatio(buttonLayout, 1);
		// buttonLayout.setHeight(20,Sizeable.UNITS_PIXELS);

		// -- Setup detal view
		Label defaultDetailViewMessage = new Label("Select An ASN");
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
	public void onCreateASN() throws ViewFactoryException {
		ASN td = new ASN();
		td.setName("New ASN");
		// BeanItem<ASN> beanItem = new BeanItem<ASN>(td);
		this.view.getASNForm().setItemDataSource(
				this.asns.createEntityItem(td));
		this.view.getASNForm().setEnabled(true);
		resetButtons(true, false, true);
	}

	public void onRemoveASN() {
		// check if a user is selected in the table
		Table grid = this.view.getSearchGrid();
		Object selected = grid.getValue();
		if (selected != null) {
			this.asns.removeItem(selected);
		}
		this.view.getASNForm().discard();
		this.view.getASNForm().setEnabled(false);
		resetButtons(false, false, false);
	}

	public void onSaveASN() {
		this.view.getASNForm().commit();
		this.view.getASNForm().setEnabled(false);
		resetButtons(false, true, false);
	}

	public void onEditASN() {
		if (this.view.getSearchGrid().getValue() != null)
		{
			this.view.getASNForm().setEnabled(true);
			resetButtons(true, false, false);
		}
	}
	
	public void onCancelASN() {
		this.view.getASNForm().discard();
		this.view.getASNForm().setEnabled(false);
		resetButtons(false, true, false);
	}	
	
	private void resetButtons(boolean saveEnable, boolean editEnable, boolean cancelEnable)
	{
		//this.view.getSaveASN().setEnabled(saveEnable);
		//this.view.getEditASN().setEnabled(editEnable);
		//this.view.getCancelEditASN().setEnabled(cancelEnable);
	}
}
