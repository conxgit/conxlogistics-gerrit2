package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnRefNumsPage extends IPageFlowPage {
private static final String VIEW_HEIGHT = "400px";
	
	private Table referenceIdTable;
	private VerticalLayout mainView;
	private VerticalLayout editView;
	private VerticalLayout listView;
	private ComboBox referenceIdType;
	private TextField referenceIdField;
	private Button addReferenceIdButton;
	private HorizontalLayout addStrip;
	private IndexedContainer referenceIdContainer;
	private TextField referenceIdEditorField;
	private ComboBox referenceIdEditorType;
	private Button editButton;
	private Button deleteButton;
	private HorizontalLayout toolstripRightButtonPanel;
	private HorizontalLayout toolStrip;
	private HorizontalLayout referenceIdEditorStrip;
	private TextArea referenceIdEditorNotes;
	private VerticalLayout canvas;
	private Button addReferenceIdType;
	private HorizontalLayout toolstripLeftButtonPanel;
	private Button saveButton;
	private Button resetButton;
	
	public void initRefIdToolStrip() {
		referenceIdField = new TextField();
		referenceIdField.setInputPrompt("Reference Id");
		referenceIdField.setWidth("100%");
		
		referenceIdType = new ComboBox();
		referenceIdType.setInputPrompt("Reference Id Type");
		referenceIdType.addItem("Red");
		referenceIdType.addItem("Orange");
		referenceIdType.addItem("Blue");
		referenceIdType.addItem("Yellow");
		referenceIdType.addItem("Grey");
		referenceIdType.setWidth("100%");
		
		addReferenceIdButton = new Button("Add Reference Id");
//		addReferenceIdButton.setWidth("100px");
		addReferenceIdButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 328740392837363674L;
			private Item item;
			private int id = 0;
			public void buttonClick(ClickEvent event) {
				item = referenceIdTable.addItem(++id);
				item.getItemProperty("id").setValue(referenceIdField.getValue());
				item.getItemProperty("type").setValue(referenceIdType.getValue());
				referenceIdField.setValue("");
				referenceIdType.setValue(null);
				referenceIdField.focus();
			}
		});
		
		addReferenceIdType = new Button("New Reference Id Type");
//		addReferenceIdType.setWidth("100px");
		addReferenceIdType.addListener(new ClickListener() {
			private static final long serialVersionUID = 328740392837363674L;
//			private Item item;
//			private int id = 0;
			public void buttonClick(ClickEvent event) {
				//
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
		referenceIdContainer = new IndexedContainer();
		referenceIdContainer.addContainerProperty("id", String.class, "");
		referenceIdContainer.addContainerProperty("type", String.class, "");
		
		referenceIdTable = new Table(); 
		referenceIdTable.setSizeFull();
		referenceIdTable.setSelectable(true);
		referenceIdTable.setImmediate(true);
		referenceIdTable.setNullSelectionAllowed(false);
		referenceIdTable.setContainerDataSource(referenceIdContainer);
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
		referenceIdEditorType.addItem("Red");
		referenceIdEditorType.addItem("Orange");
		referenceIdEditorType.addItem("Blue");
		referenceIdEditorType.addItem("Yellow");
		referenceIdEditorType.addItem("Grey");
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
		referenceIdEditorNotes.setColumns(20);
		referenceIdEditorNotes.setImmediate(true);
		referenceIdEditorNotes.setWidth("100%");
		referenceIdEditorNotes.setInputPrompt("Notes");
		
		editView = new VerticalLayout();
		editView.setWidth("100%");
		editView.setHeight(VIEW_HEIGHT);
		editView.addComponent(referenceIdEditorStrip);
		editView.addComponent(referenceIdEditorNotes);
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
				cycleMainView();
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setWidth("100%");
		deleteButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5003289976157301678L;

			public void buttonClick(ClickEvent event) {
				referenceIdTable.removeItem(referenceIdTable.getValue());
				cycleMainView();
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
				
			}
		});
		
		resetButton = new Button("Reset");
		resetButton.setEnabled(false);
		resetButton.setWidth("100%");
		resetButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5003289976900978L;

			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		toolstripLeftButtonPanel = new HorizontalLayout();
		toolstripLeftButtonPanel.setWidth("200px");
		toolstripLeftButtonPanel.setSpacing(true);
		toolstripLeftButtonPanel.addComponent(saveButton);
		toolstripLeftButtonPanel.addComponent(resetButton);
		toolstripLeftButtonPanel.setExpandRatio(saveButton, 0.5f);
		toolstripLeftButtonPanel.setExpandRatio(resetButton, 0.5f);
		
		toolStrip = new HorizontalLayout();
		toolStrip.setWidth("100%");
		toolStrip.setMargin(true, false, true, false);
		toolStrip.addComponent(toolstripLeftButtonPanel);
		toolStrip.addComponent(toolstripRightButtonPanel);
		toolStrip.setComponentAlignment(toolstripLeftButtonPanel, Alignment.MIDDLE_LEFT);
		toolStrip.setComponentAlignment(toolstripRightButtonPanel, Alignment.MIDDLE_RIGHT);
	}
	
	public void init() {
		initListView();
		initEditView();
		initTableToolStrip();

		mainView = new VerticalLayout();
		mainView.setWidth("100%");
		mainView.setHeight(VIEW_HEIGHT);
		mainView.addComponent(listView);
		mainView.addComponent(editView);
		
		canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(mainView);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(mainView, 1.0f);
	}
	
	private void cycleMainView() {
		if (editButton.getCaption().equals("Edit")) {
			editButton.setCaption("Apply");
		} else {
			editButton.setCaption("Edit");
		}
		listView.setVisible(!listView.isVisible());
		editView.setVisible(!editView.isVisible());
	}
	
	@Override
	public String getTaskName() {
		// TODO Auto-generated method stub
		return "AddAsnRefNums";
	}

	@Override
	public Component getContent() {
		if (canvas == null) {
			init();
		}
		return canvas;
	}

	@Override
	public void setDataContainerMap(Map<String, Container> containerMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Container> getDataContainerMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return "Add Reference Numbers";
	}

}
