package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumberType;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnRefNumsPage extends PageFlowPage {
	private static final String VIEW_HEIGHT = "450px";

	private int pageMode = LIST_PAGE_MODE;

	private static final int LIST_PAGE_MODE = 0;
	private static final int EDIT_PAGE_MODE = 1;

	private Table referenceIdTable;
	private VerticalLayout mainView;
	private VerticalLayout editView;
	private VerticalLayout listView;
	private ComboBox referenceIdType;
	private TextField referenceIdField;
	private Button addReferenceIdButton;
	private HorizontalLayout addStrip;
	private TextField referenceIdEditorField;
	private ComboBox referenceIdEditorType;
	private Button editButton;
	private Button deleteButton;
	private HorizontalLayout toolstripRightButtonPanel;
	private HorizontalLayout toolStrip;
	private HorizontalLayout referenceIdEditorStrip;
	private TextArea referenceIdEditorNotes;
	private Button addReferenceIdType;
	private HorizontalLayout toolstripLeftButtonPanel;
	private Button saveButton;
	private Button resetButton;

	private JPAContainer<ReferenceNumberType> refNumTypeContainer;

	private BeanContainer<String, ReferenceNumber> refNumBeanContainer;
	private EntityManagerFactory emf;
	private Button cancelButton;

	private void setPageMode(int mode) {
		switch (mode) {
		case LIST_PAGE_MODE:
			referenceIdField.setValue("");
			referenceIdType.setValue(null);

			saveButton.setEnabled(false);
			resetButton.setEnabled(false);
			if (referenceIdTable.getValue() == null) {
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
			} else {
				editButton.setEnabled(true);
				deleteButton.setEnabled(true);
			}

			editView.setVisible(false);
			listView.setVisible(true);
			break;
		case EDIT_PAGE_MODE:
			ReferenceNumber refNum = refNumBeanContainer.getItem(referenceIdTable.getValue()).getBean();
			referenceIdEditorType.setValue(refNum.getType().getId());
			referenceIdEditorField.setValue(refNum.getValue());
			if (refNum.getDescription() != null && !refNum.getDescription().isEmpty()) {
				referenceIdEditorNotes.setValue(refNum.getDescription());
			}

			saveButton.setEnabled(true);
			resetButton.setEnabled(true);
			editButton.setEnabled(false);
			deleteButton.setEnabled(true);

			listView.setVisible(false);
			editView.setVisible(true);
			break;
		}
		this.pageMode = mode;
	}

	private boolean validate() {
		StringBuffer message = new StringBuffer();
		switch (pageMode) {
		case LIST_PAGE_MODE:
			if ((referenceIdField.getValue() == null) || ((String) referenceIdField.getValue()).isEmpty()) {
				message.append("</br>Reference Number was not provided");
			} 
			if (referenceIdType.getValue() == null) {
				message.append("</br>Reference Number Type was not provided");
			}

			if (message.length() != 0) {
				showNotification("Could Not Add Reference Number", message.toString());
				return false;
			} else {
				return true;
			}
		case EDIT_PAGE_MODE:
			if ((referenceIdEditorField.getValue() == null) || ((String) referenceIdEditorField.getValue()).isEmpty()) {
				message.append("</br>Reference Number was not provided");
			} 
			if (referenceIdEditorType.getValue() == null) {
				message.append("</br>Reference Number Type was not provided");
			}

			if (message.length() != 0) {
				showNotification("Could Not Edit Reference Number", message.toString());
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private void reset() {
		switch (pageMode) {
		case EDIT_PAGE_MODE:
			ReferenceNumber refNum = refNumBeanContainer.getItem(referenceIdTable.getValue()).getBean();
			referenceIdEditorType.setValue(refNum.getType().getId());
			referenceIdEditorField.setValue(refNum.getValue());
			if (refNum.getDescription() != null && !refNum.getDescription().isEmpty()) {
				referenceIdEditorNotes.setValue(refNum.getDescription());
			} else {
				referenceIdEditorNotes.setValue("");
			}
			break;
		}
	}

	private void create() {
		if (validate()) {
			ReferenceNumber refNum = new ReferenceNumber();
			refNum.setValue((String) referenceIdField.getValue());
			refNum.setType(refNumTypeContainer.getItem(referenceIdType.getValue()).getEntity());
			refNumBeanContainer.addBean(refNum);
			referenceIdField.setValue("");
			referenceIdType.setValue(null);
			referenceIdField.focus();
		}
	}

	private void edit() {
		if (validate()) {
			BeanItem<ReferenceNumber> item = refNumBeanContainer.getItem(referenceIdTable.getValue());
			ReferenceNumber refNum = item.getBean();
			String refNumValue = (String) referenceIdEditorField.getValue();
			ReferenceNumberType refNumType = refNumTypeContainer.getItem(referenceIdEditorType.getValue()).getEntity();

			refNum.setValue(refNumValue);
			item.getItemProperty("value").setValue(refNumValue);
			refNum.setType(refNumType);
			item.getItemProperty("type.name").setValue(refNumType.getName());
			refNum.setDescription((String) referenceIdEditorNotes.getValue());

			setPageMode(LIST_PAGE_MODE);
		}
	}

	private void initContainers() {
		refNumTypeContainer = JPAContainerFactory.make(ReferenceNumberType.class, this.emf.createEntityManager());
		refNumBeanContainer = new BeanContainer<String, ReferenceNumber>(ReferenceNumber.class);

		refNumBeanContainer.setBeanIdProperty("value");
		refNumBeanContainer.addNestedContainerProperty("type.name");
	}

	public void initRefIdToolStrip() {
		referenceIdField = new TextField();
		referenceIdField.setInputPrompt("Reference Id");
		referenceIdField.setWidth("100%");

		referenceIdType = new ComboBox();
		referenceIdType.setInputPrompt("Reference Id Type");
		referenceIdType.setContainerDataSource(refNumTypeContainer);
		referenceIdType.setItemCaptionPropertyId("name");
		referenceIdType.setNullSelectionAllowed(false);
		referenceIdType.setWidth("100%");

		addReferenceIdButton = new Button("Add Reference Id");
		addReferenceIdButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 328740392837363674L;

			public void buttonClick(ClickEvent event) {
				create();
			}
		});

		addReferenceIdType = new Button("New Reference Id Type");
		addReferenceIdType.addListener(new ClickListener() {
			private static final long serialVersionUID = 328750392837363674L;
			public void buttonClick(ClickEvent event) {
			}
		});

		addStrip = new HorizontalLayout();
		addStrip.setHeight("50px");
		addStrip.setWidth("100%");
		addStrip.setSpacing(true);
		addStrip.addComponent(referenceIdField);
		addStrip.addComponent(referenceIdType);
		addStrip.addComponent(addReferenceIdButton);
		addStrip.addComponent(addReferenceIdType);
		addStrip.setExpandRatio(referenceIdField, 0.5f);
		addStrip.setExpandRatio(referenceIdType, 0.5f);
	}

	public void initRefIdTable() {
		referenceIdTable = new Table(); 
		referenceIdTable.setSizeFull();
		referenceIdTable.setSelectable(true);
		referenceIdTable.setImmediate(true);
		referenceIdTable.setNullSelectionAllowed(false);
		referenceIdTable.setContainerDataSource(refNumBeanContainer);
		referenceIdTable.setVisibleColumns(new String[] { "value",  "type.name" });
		referenceIdTable.setColumnHeader("value", "Reference Number");
		referenceIdTable.setColumnHeader("type.name", "Type");
		referenceIdTable.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 4696828026010510338L;

			public void valueChange(ValueChangeEvent event) {
				editButton.setEnabled(true);
				deleteButton.setEnabled(true);
			}
		});
	}

	public void initListView() {
		initRefIdToolStrip();
		initRefIdTable();

		listView = new VerticalLayout();
		listView.setWidth("100%");
		listView.setHeight(VIEW_HEIGHT);
		listView.addComponent(addStrip);
		listView.addComponent(referenceIdTable);
		listView.setExpandRatio(referenceIdTable, 1.0f);
	}

	public void initEditView() {
		referenceIdEditorField = new TextField();
		referenceIdEditorField.setInputPrompt("Reference Id");
		referenceIdEditorField.setWidth("100%");

		referenceIdEditorType = new ComboBox();
		referenceIdEditorType.setInputPrompt("Reference Id Type");
		referenceIdEditorType.setContainerDataSource(refNumTypeContainer);
		referenceIdEditorType.setItemCaptionPropertyId("name");
		referenceIdEditorType.setNullSelectionAllowed(false);
		referenceIdEditorType.setWidth("100%");

		referenceIdEditorStrip = new HorizontalLayout();
		referenceIdEditorStrip.addComponent(referenceIdEditorField);
		referenceIdEditorStrip.addComponent(referenceIdEditorType);
		referenceIdEditorStrip.setExpandRatio(referenceIdEditorField, 0.5f);
		referenceIdEditorStrip.setExpandRatio(referenceIdEditorType, 0.5f);
		referenceIdEditorStrip.setWidth("100%");
		referenceIdEditorStrip.setSpacing(true);

		referenceIdEditorNotes = new com.vaadin.ui.TextArea(null, "");
		referenceIdEditorNotes.setRows(8);
		referenceIdEditorNotes.setColumns(0);
		referenceIdEditorNotes.setImmediate(true);
		referenceIdEditorNotes.setSizeFull();
		referenceIdEditorNotes.setInputPrompt("Notes");

		editView = new VerticalLayout();
		editView.setWidth("100%");
		editView.setHeight(VIEW_HEIGHT);
		editView.addComponent(referenceIdEditorStrip);
		editView.addComponent(referenceIdEditorNotes);
		editView.setExpandRatio(referenceIdEditorNotes, 1.0f);
		editView.setSpacing(true);
		editView.setVisible(false);
	}

	public void initTableToolStrip() {
		editButton = new Button("Edit");
		editButton.setEnabled(false);
		editButton.setWidth("100%");
		editButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500322301678L;

			public void buttonClick(ClickEvent event) {
				ReferenceNumber refNum = refNumBeanContainer.getItem(referenceIdTable.getValue()).getBean();
				referenceIdField.setValue(refNum.getValue());
				referenceIdType.setValue(refNum.getType().getId());
				setPageMode(EDIT_PAGE_MODE);
			}
		});

		deleteButton = new Button("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setWidth("100%");
		deleteButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5003289976157301678L;

			public void buttonClick(ClickEvent event) {
				referenceIdTable.removeItem(referenceIdTable.getValue());
				if (pageMode == EDIT_PAGE_MODE) {
					referenceIdTable.removeItem(referenceIdTable.getValue());
					setPageMode(LIST_PAGE_MODE);
				}
			}
		});

		toolstripRightButtonPanel = new HorizontalLayout();
		toolstripRightButtonPanel.setWidth("200px");
		toolstripRightButtonPanel.setSpacing(true);
		toolstripRightButtonPanel.addComponent(editButton);
		toolstripRightButtonPanel.addComponent(deleteButton);
		toolstripRightButtonPanel.setExpandRatio(editButton, 0.5f);
		toolstripRightButtonPanel.setExpandRatio(deleteButton, 0.5f);

		saveButton = new Button("Save");
		saveButton.setEnabled(false);
		saveButton.setWidth("100%");
		saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500312301678L;

			public void buttonClick(ClickEvent event) {
				edit();
			}
		});

		resetButton = new Button("Reset");
		resetButton.setEnabled(false);
		resetButton.setWidth("100%");
		resetButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 50030978L;

			public void buttonClick(ClickEvent event) {
				reset();
			}
		});

		cancelButton = new Button("Cancel");
		cancelButton.setEnabled(false);
		cancelButton.setWidth("100%");
		cancelButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500785840900978L;

			public void buttonClick(ClickEvent event) {
			}
		});

		toolstripLeftButtonPanel = new HorizontalLayout();
		toolstripLeftButtonPanel.setWidth("300px");
		toolstripLeftButtonPanel.setSpacing(true);
		toolstripLeftButtonPanel.addComponent(saveButton);
		toolstripLeftButtonPanel.addComponent(resetButton);
		toolstripLeftButtonPanel.addComponent(cancelButton);
		toolstripLeftButtonPanel.setExpandRatio(saveButton, 0.33f);
		toolstripLeftButtonPanel.setExpandRatio(resetButton, 0.33f);
		toolstripLeftButtonPanel.setExpandRatio(cancelButton, 0.33f);

		toolStrip = new HorizontalLayout();
		toolStrip.setWidth("100%");
		toolStrip.setMargin(true, false, true, false);
		toolStrip.addComponent(toolstripLeftButtonPanel);
		toolStrip.addComponent(toolstripRightButtonPanel);
		toolStrip.setComponentAlignment(toolstripLeftButtonPanel, Alignment.MIDDLE_LEFT);
		toolStrip.setComponentAlignment(toolstripRightButtonPanel, Alignment.MIDDLE_RIGHT);
	}

	@Override
	public void initialize(EntityManagerFactory emf) {
		this.emf = emf;

		initContainers();
		initListView();
		initEditView();
		initTableToolStrip();

		mainView = new VerticalLayout();
		mainView.setWidth("100%");
		mainView.setHeight(VIEW_HEIGHT);
		mainView.addComponent(listView);
		mainView.addComponent(editView);

		VerticalLayout canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(mainView);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(mainView, 1.0f);

		this.setCanvas(canvas);
	}

	@Override
	public String getTaskName() {
		return "AddAsnRefNums";
	}

	@Override
	public String getCaption() {
		return "Add Reference Numbers";
	}


	@Override
	public Map<String, Object> getOnCompleteState() {
		Map<String,Object> outParams = new HashMap<String, Object>();
		Set<ReferenceNumber> refNums = new HashSet<ReferenceNumber>();
		for (Object id : referenceIdTable.getItemIds()) {
			refNums.add(refNumBeanContainer.getItem(id).getBean());
		}

		outParams.put("refNumsCollectionOut", refNums);
		return outParams;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setOnStartState(Map<String, Object> state) {
		if (state != null) {
			Set<ReferenceNumber> refNums = (Set<ReferenceNumber>) state.get("refNumsCollectionIn");

			if (refNums != null && refNumBeanContainer != null) {
				refNumBeanContainer.addAll(refNums);
			}
		}
	}

}
