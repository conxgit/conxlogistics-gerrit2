package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ConfirmAsnPage extends IPageFlowPage {
	private static final String VIEW_HEIGHT = "400px";
	
	private VerticalLayout canvas;
	private TabSheet entityTabSheet;
	private ComboBox organization;
	private Label organizationLabel;
	private GridLayout organizationLayout;
	private HorizontalLayout confirmOrganizationLayout;
	private Button saveButton;
	private Button resetButton;
	private HorizontalLayout toolstripLeftButtonPanel;
	private HorizontalLayout toolStrip;

	private IndexedContainer referenceIdContainer;
	private Table referenceIdTable;
	
	public void initConfirmProducts() {
		referenceIdContainer = new IndexedContainer();
		referenceIdContainer.addContainerProperty("asn line id", String.class, "");
		referenceIdContainer.addContainerProperty("ref #", String.class, "");
		referenceIdContainer.addContainerProperty("product name", String.class, "");
		referenceIdContainer.addContainerProperty("total weight", String.class, "");
		referenceIdContainer.addContainerProperty("total volume", String.class, "");
		referenceIdContainer.addContainerProperty("total volume", String.class, "");
		
		referenceIdTable = new Table(); 
		referenceIdTable.setSizeFull();
		referenceIdTable.setSelectable(true);
		referenceIdTable.setImmediate(true);
		referenceIdTable.setNullSelectionAllowed(false);
		referenceIdTable.setContainerDataSource(referenceIdContainer);
		referenceIdTable.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 469681126010510338L;

			public void valueChange(ValueChangeEvent event) {
            }
        });
		
		organization = new ComboBox();
		organization.setInputPrompt("Default Organization");
		organization.addItem("Red");
		organization.addItem("Orange");
		organization.addItem("Blue");
		organization.addItem("Yellow");
		organization.addItem("Grey");
		organization.setWidth("100%");
		
		organizationLabel = new Label();
		organizationLabel.setValue("Organization");
		
		Label organizationLayoutLabel = new Label();
		organizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		organizationLayoutLabel.setValue("<h3>Confirm Organization</h3>");
		
		organizationLayout = new GridLayout(2, 2);
		organizationLayout.setWidth("100%");
		organizationLayout.setSpacing(true);
		organizationLayout.setMargin(false, true, true, true);
		organizationLayout.setColumnExpandRatio(0, 0.3f);
		organizationLayout.setColumnExpandRatio(1, 0.7f);
		organizationLayout.addComponent(organizationLayoutLabel, 0, 0, 1, 0);
		organizationLayout.addComponent(organizationLabel);
		organizationLayout.addComponent(organization);
		organizationLayout.setComponentAlignment(organizationLabel, Alignment.MIDDLE_LEFT);
		
		confirmOrganizationLayout = new HorizontalLayout();
		confirmOrganizationLayout.setMargin(true);
		confirmOrganizationLayout.setWidth("40%");
		confirmOrganizationLayout.setHeight("100%");
		confirmOrganizationLayout.setSpacing(true);
		confirmOrganizationLayout.addComponent(organizationLayout);
	}
	
	public void initEntityTabSheet() {
		initConfirmProducts();
		
		entityTabSheet = new TabSheet();
		entityTabSheet.setWidth("100%");
		entityTabSheet.setHeight(VIEW_HEIGHT);
		entityTabSheet.addTab(confirmOrganizationLayout, "Confirm Organization");
	}
	
	public void initTableToolStrip() {
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
		toolStrip.setComponentAlignment(toolstripLeftButtonPanel, Alignment.MIDDLE_LEFT);
	}
	
	public void init() {
		initEntityTabSheet();
		initTableToolStrip();
		
		canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(entityTabSheet);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(entityTabSheet, 1.0f);
	}

	@Override
	public String getTaskName() {
		return "ConfirmAsn";
	}

	@Override
	public Component getContent() {
		/*if (canvas == null) {
			init();
		}
		return canvas;*/
		return new VerticalLayout();
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
		return "Confirm Asn";
	}

}
