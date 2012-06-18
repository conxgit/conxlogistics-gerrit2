package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.mdm.domain.product.DimUnit;
import com.conx.logistics.mdm.domain.product.PackUnit;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.product.ProductType;
import com.conx.logistics.mdm.domain.product.WeightUnit;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
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
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnLinesPage extends IPageFlowPage {
private static final String VIEW_HEIGHT = "400px";
	
	private Table referenceIdTable;
	private VerticalLayout mainView;
	private VerticalLayout listView;
	private ComboBox referenceId;
	private TextField referenceIdField;
	private Button bindProductButton;
	private HorizontalLayout addStrip;
	private IndexedContainer referenceIdContainer;
	private Button newButton;
	private Button deleteButton;
	private HorizontalLayout toolstripButtonPanel;
	private HorizontalLayout toolStrip;
	private VerticalLayout canvas;
	private VerticalLayout newView;
	private Form productForm;
	private TextArea productDescription;
	private ComboBox selectProductName;
	private ComboBox selectProductType;
	private TabSheet entityTabSheet;
	private TextField totalWeight;
	private ComboBox totalWeightUnit;
	private GridLayout totalWeightForm;
	private ComboBox totalVolumeUnit;
	private TextField totalVolume;
	private TextField length;
	private ComboBox lengthUnit;
	private ComboBox heightUnit;
	private TextField height;
	private ComboBox widthUnit;
	private TextField width;
	private GridLayout dimensionForm;
	private ComboBox unitsOuterPackageType;
	private TextField unitsOuter;
	private TextField unitsInner;
	private ComboBox unitsInnerPackageType;
	private GridLayout quantityForm;
	private HorizontalLayout weightDimensionQuantityLayout;
	private TextField number;
	private ComboBox numberUnit;
	private GridLayout valueForm;
	private HorizontalLayout additionalAttributesLayout;

	private JPAContainer<DimUnit> dimUnitContainer;
	private JPAContainer<WeightUnit> weightUnitContainer;
	private JPAContainer<PackUnit> packUnitContainer;
	private JPAContainer<Product> productContainer;
	private JPAContainer<ProductType> productTypeContainer;
	
	private void initContainers() {
		dimUnitContainer = JPAContainerFactory.make(DimUnit.class, this.emf.createEntityManager());
		weightUnitContainer = JPAContainerFactory.make(WeightUnit.class, this.emf.createEntityManager());
		packUnitContainer = JPAContainerFactory.make(PackUnit.class, this.emf.createEntityManager());
		productContainer = JPAContainerFactory.make(Product.class, this.emf.createEntityManager());
		productTypeContainer = JPAContainerFactory.make(ProductType.class, this.emf.createEntityManager());
	}
	
	public void initListView() {
		referenceIdContainer = new IndexedContainer();
		referenceIdContainer.addContainerProperty("Asn Line Id", String.class, "");
		referenceIdContainer.addContainerProperty("Product Name", String.class, "");
		referenceIdContainer.addContainerProperty("Ref Id", String.class, "");
		referenceIdContainer.addContainerProperty("Total Weight/Units", String.class, "");
		referenceIdContainer.addContainerProperty("Total Volume/Units", String.class, "");
		referenceIdContainer.addContainerProperty("Expected Quantity", String.class, "");
		
		referenceIdTable = new Table(); 
		referenceIdTable.setSizeFull();
		referenceIdTable.setSelectable(true);
		referenceIdTable.setImmediate(true);
		referenceIdTable.setNullSelectionAllowed(false);
		referenceIdTable.setContainerDataSource(referenceIdContainer);
		referenceIdTable.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 4696828026010510338L;

			public void valueChange(ValueChangeEvent event) {
                newButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });
		
		listView = new VerticalLayout();
		listView.addComponent(referenceIdTable);
		listView.setSpacing(true);
		listView.setWidth("100%");
		listView.setHeight(VIEW_HEIGHT);
		listView.setExpandRatio(referenceIdTable, 1.0f);
	}
	
	public void initNewView() {
		referenceId = new ComboBox();
		referenceId.setInputPrompt("Reference Id");
		referenceId.addItem("Red");
		referenceId.addItem("Orange");
		referenceId.addItem("Blue");
		referenceId.addItem("Yellow");
		referenceId.addItem("Grey");
		referenceId.setWidth("100%");
		
		bindProductButton = new Button("Add Product to Reference Id");
		bindProductButton.setWidth("220px");
		bindProductButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 328740392837363674L;
			private Item item;
			private int id = 0;
			public void buttonClick(ClickEvent event) {
				item = referenceIdTable.addItem(++id);
				item.getItemProperty("id").setValue(referenceIdField.getValue());
				item.getItemProperty("type").setValue(referenceId.getValue());
				referenceIdField.setValue("");
				referenceId.setValue(null);
				referenceIdField.focus();
			}
		});
		

		addStrip = new HorizontalLayout();
		addStrip.setHeight("50px");
		addStrip.setWidth("100%");
		addStrip.setSpacing(true);
		addStrip.addComponent(referenceId);
		addStrip.addComponent(bindProductButton);
		addStrip.setExpandRatio(referenceId, 1.0f);
		
		newView = new VerticalLayout();
        newView.setWidth("100%");
        newView.setHeight(VIEW_HEIGHT);
        newView.addComponent(addStrip);
        newView.addComponent(entityTabSheet);
        newView.setExpandRatio(entityTabSheet, 1.0f);
        newView.setVisible(false);
	}
	
	public void initEntityTabSheet() {
		selectProductType = new ComboBox();
		selectProductType.setCaption("Product Type");
		selectProductType.setInputPrompt("Product Type");
		selectProductType.setContainerDataSource(productTypeContainer);
		selectProductType.setItemCaptionPropertyId("name");
		selectProductType.setWidth("100%");
		selectProductType.setRequired(true);
		
		selectProductName = new ComboBox();
		selectProductName.setCaption("Product Name");
		selectProductName.setInputPrompt("Product Name");
		selectProductName.setContainerDataSource(productContainer);
		selectProductName.setItemCaptionPropertyId("name");
		selectProductName.setWidth("100%");
		selectProductName.setRequired(true);
		
		productDescription = new TextArea(null, "");
		productDescription.setCaption("Product Description");
		productDescription.setRows(10);
		productDescription.setColumns(20);
		productDescription.setImmediate(true);
		productDescription.setWidth("100%");
		productDescription.setInputPrompt("Product Description");
		
		productForm = new Form();
        productForm.setWriteThrough(false); // we want explicit 'apply'
        productForm.setInvalidCommitted(false); // no invalid values in datamodel
        productForm.getLayout().addComponent(selectProductName);
        productForm.getLayout().addComponent(selectProductType);
        productForm.getLayout().addComponent(productDescription);
        productForm.getLayout().setMargin(true);
        productForm.getLayout().setWidth("70%");
        
        Label totalWeightLabel = new Label();
        totalWeightLabel.setValue("Total Weight");
        
        totalWeight = new TextField();
        totalWeight.setInputPrompt("Total Weight");
        totalWeight.setWidth("100%");
        
        totalWeightUnit = new ComboBox();
        totalWeightUnit.setInputPrompt("Units");
        totalWeightUnit.setContainerDataSource(weightUnitContainer);
        totalWeightUnit.setItemCaptionPropertyId("name");
        totalWeightUnit.setWidth("100%");
        totalWeightUnit.setNullSelectionAllowed(false);
        
        Label totalWeightFormLabel = new Label("<h3>Total Weight</h3>");
        totalWeightFormLabel.setContentMode(Label.CONTENT_XHTML);
        
        totalWeightForm = new GridLayout(3, 2);
        totalWeightForm.setSpacing(true);
        totalWeightForm.setMargin(false, true, true, true);
        totalWeightForm.setColumnExpandRatio(0, 0.2f);
        totalWeightForm.setColumnExpandRatio(1, 0.4f);
        totalWeightForm.setColumnExpandRatio(2, 0.4f);
        totalWeightForm.setWidth("100%");
        totalWeightForm.addComponent(totalWeightFormLabel, 0, 0, 2, 0);
        totalWeightForm.addComponent(totalWeightLabel);
        totalWeightForm.addComponent(totalWeight);
        totalWeightForm.addComponent(totalWeightUnit);
        totalWeightForm.setComponentAlignment(totalWeightLabel, Alignment.MIDDLE_LEFT);
        
        Label totalVolumeLabel = new Label();
        totalVolumeLabel.setValue("Total Volume");
        
        Label widthLabel = new Label();
        widthLabel.setValue("Width");
        
        Label lengthLabel = new Label();
        lengthLabel.setValue("Length");
        
        Label heightLabel = new Label();
        heightLabel.setValue("Height");
        
        totalVolume = new TextField();
        totalVolume.setInputPrompt("Total Volume");
        totalVolume.setWidth("100%");
        
        totalVolumeUnit = new ComboBox();
        totalVolumeUnit.setInputPrompt("Units");
        totalVolumeUnit.setContainerDataSource(dimUnitContainer);
        totalVolumeUnit.setItemCaptionPropertyId("name");
        totalVolumeUnit.setWidth("100%");
        totalVolumeUnit.setNullSelectionAllowed(false);
        
        length = new TextField();
        length.setInputPrompt("Length");
        length.setWidth("100%");
        
        lengthUnit = new ComboBox();
        lengthUnit.setInputPrompt("Units");
        lengthUnit.setContainerDataSource(dimUnitContainer);
        lengthUnit.setItemCaptionPropertyId("name");
        lengthUnit.setWidth("100%");
        lengthUnit.setNullSelectionAllowed(false);
        
        width = new TextField();
        width.setInputPrompt("Width");
        width.setWidth("100%");
        
        widthUnit = new ComboBox();
        widthUnit.setInputPrompt("Units");
        widthUnit.setContainerDataSource(dimUnitContainer);
        widthUnit.setItemCaptionPropertyId("name");
        widthUnit.setWidth("100%");
        widthUnit.setNullSelectionAllowed(false);
        
        height = new TextField();
        height.setInputPrompt("Height");
        height.setWidth("100%");
        
        heightUnit = new ComboBox();
        heightUnit.setInputPrompt("Units");
        heightUnit.setContainerDataSource(dimUnitContainer);
        heightUnit.setItemCaptionPropertyId("name");
        heightUnit.setWidth("100%");
        heightUnit.setNullSelectionAllowed(false);
        
        Label dimensionFormLabel = new Label("<h3>Dimensions</h3>");
        dimensionFormLabel.setContentMode(Label.CONTENT_XHTML);
        
        dimensionForm = new GridLayout(3, 5);
        dimensionForm.setSpacing(true);
        dimensionForm.setMargin(false, true, true, true);
        dimensionForm.setColumnExpandRatio(0, 0.2f);
        dimensionForm.setColumnExpandRatio(1, 0.4f);
        dimensionForm.setColumnExpandRatio(2, 0.4f);
        dimensionForm.setWidth("100%");
        dimensionForm.addComponent(dimensionFormLabel, 0, 0, 2, 0);
        dimensionForm.addComponent(totalVolumeLabel);
        dimensionForm.addComponent(totalVolume);
        dimensionForm.addComponent(totalVolumeUnit);
        dimensionForm.addComponent(lengthLabel);
        dimensionForm.addComponent(length);
        dimensionForm.addComponent(lengthUnit);
        dimensionForm.addComponent(widthLabel);
        dimensionForm.addComponent(width);
        dimensionForm.addComponent(widthUnit);
        dimensionForm.addComponent(heightLabel);
        dimensionForm.addComponent(height);
        dimensionForm.addComponent(heightUnit);
        dimensionForm.setComponentAlignment(totalVolumeLabel, Alignment.MIDDLE_LEFT);
        dimensionForm.setComponentAlignment(lengthLabel, Alignment.MIDDLE_LEFT);
        dimensionForm.setComponentAlignment(widthLabel, Alignment.MIDDLE_LEFT);
        dimensionForm.setComponentAlignment(heightLabel, Alignment.MIDDLE_LEFT);
        
        Label unitsOuterLabel = new Label();
        unitsOuterLabel.setValue("Units Outer");
        
        Label unitsInnerLabel = new Label();
        unitsInnerLabel.setValue("Units Inner");
        
        unitsOuter = new TextField();
        unitsOuter.setInputPrompt("Units Outer");
        unitsOuter.setWidth("100%");
        
        unitsOuterPackageType = new ComboBox();
        unitsOuterPackageType.setInputPrompt("Package Type");
        unitsOuterPackageType.setContainerDataSource(packUnitContainer);
        unitsOuterPackageType.setItemCaptionPropertyId("name");
        unitsOuterPackageType.setWidth("100%");
        unitsOuterPackageType.setNullSelectionAllowed(false);
        
        unitsInner = new TextField();
        unitsInner.setInputPrompt("Units Inner");
        unitsInner.setWidth("100%");
        
        unitsInnerPackageType = new ComboBox();
        unitsInnerPackageType.setInputPrompt("Package Type");
        unitsInnerPackageType.setContainerDataSource(packUnitContainer);
        unitsInnerPackageType.setItemCaptionPropertyId("name");
        unitsInnerPackageType.setWidth("100%");
        unitsInnerPackageType.setNullSelectionAllowed(false);
        
        Label quantityFormLabel = new Label("<h3>Quantity</h3>");
        quantityFormLabel.setContentMode(Label.CONTENT_XHTML);
        
        quantityForm = new GridLayout(3, 3);
        quantityForm.setSpacing(true);
        quantityForm.setMargin(false, true, true, true);
        quantityForm.setColumnExpandRatio(0, 0.2f);
        quantityForm.setColumnExpandRatio(1, 0.4f);
        quantityForm.setColumnExpandRatio(2, 0.4f);
        quantityForm.setWidth("100%");
        quantityForm.addComponent(quantityFormLabel, 0, 0, 2, 0);
        quantityForm.addComponent(unitsOuterLabel);
        quantityForm.addComponent(unitsOuter);
        quantityForm.addComponent(unitsOuterPackageType);
        quantityForm.addComponent(unitsInnerLabel);
        quantityForm.addComponent(unitsInner);
        quantityForm.addComponent(unitsInnerPackageType);
        quantityForm.setComponentAlignment(unitsOuterLabel, Alignment.MIDDLE_LEFT);
        quantityForm.setComponentAlignment(unitsInnerLabel, Alignment.MIDDLE_LEFT);
        
        weightDimensionQuantityLayout = new HorizontalLayout();
        weightDimensionQuantityLayout.setWidth("100%");
        weightDimensionQuantityLayout.setSpacing(true);
        weightDimensionQuantityLayout.setMargin(true);
        weightDimensionQuantityLayout.addComponent(totalWeightForm);
        weightDimensionQuantityLayout.addComponent(dimensionForm);
        weightDimensionQuantityLayout.addComponent(quantityForm);
        weightDimensionQuantityLayout.setExpandRatio(totalWeightForm, 0.33f);
        weightDimensionQuantityLayout.setExpandRatio(dimensionForm, 0.33f);
        weightDimensionQuantityLayout.setExpandRatio(quantityForm, 0.33f);
        
        Label numberLabel = new Label();
        numberLabel.setValue("Number");
        
        number = new TextField();
        number.setInputPrompt("Number");
        number.setWidth("100%");
        
        numberUnit = new ComboBox();
        numberUnit.setInputPrompt("Units");
        numberUnit.setContainerDataSource(dimUnitContainer);
        numberUnit.setItemCaptionPropertyId("name");
        numberUnit.setWidth("100%");
        numberUnit.setNullSelectionAllowed(false);
        
        Label valueFormLabel = new Label("<h3>Value</h3>");
        valueFormLabel.setContentMode(Label.CONTENT_XHTML);
        
        valueForm = new GridLayout(3, 2);
        valueForm.setSpacing(true);
        valueForm.setMargin(false, true, true, true);
        valueForm.setColumnExpandRatio(0, 0.2f);
        valueForm.setColumnExpandRatio(1, 0.4f);
        valueForm.setColumnExpandRatio(2, 0.4f);
        valueForm.setWidth("33%");
        valueForm.addComponent(valueFormLabel, 0, 0, 2, 0);
        valueForm.addComponent(numberLabel);
        valueForm.addComponent(number);
        valueForm.addComponent(numberUnit);
        
        additionalAttributesLayout = new HorizontalLayout();
        additionalAttributesLayout.setWidth("100%");
        additionalAttributesLayout.setSpacing(true);
        additionalAttributesLayout.setMargin(true);
        additionalAttributesLayout.addComponent(valueForm);
		
		entityTabSheet = new TabSheet();
		entityTabSheet.setSizeFull();
		entityTabSheet.addTab(productForm, "Product");
		entityTabSheet.addTab(weightDimensionQuantityLayout, "Weight, Dimensions & Quantity");
		entityTabSheet.addTab(additionalAttributesLayout, "Additional Attributes");
	}
	
	public void initToolStrip() {
		newButton = new Button("New");
		newButton.setWidth("100%");
		newButton.addListener(new ClickListener() {
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
		
		toolstripButtonPanel = new HorizontalLayout();
		toolstripButtonPanel.setWidth("200px");
		toolstripButtonPanel.setSpacing(true);
		toolstripButtonPanel.addComponent(newButton);
		toolstripButtonPanel.addComponent(deleteButton);
		toolstripButtonPanel.setExpandRatio(newButton, 0.5f);
		toolstripButtonPanel.setExpandRatio(deleteButton, 0.5f);
		
		toolStrip = new HorizontalLayout();
		toolStrip.setWidth("100%");
		toolStrip.setMargin(true, false, true, false);
		toolStrip.addComponent(toolstripButtonPanel);
		toolStrip.setComponentAlignment(toolstripButtonPanel, Alignment.MIDDLE_RIGHT);
	}
	
	public void init() {
		initContainers();
		initListView();
		initEntityTabSheet();
		initNewView();
		initToolStrip();
		
		mainView = new VerticalLayout();
		mainView.setWidth("100%");
		mainView.setHeight(VIEW_HEIGHT);
		mainView.addComponent(listView);
		mainView.addComponent(newView);
		
		canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(mainView);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(mainView, 1.0f);
	}
	
	private void cycleMainView() {
		if (newButton.getCaption().equals("New")) {
			newButton.setCaption("Add");
			deleteButton.setEnabled(false);
		} else {
			newButton.setCaption("New");
			if (referenceIdTable.getValue() != null) {
				deleteButton.setEnabled(true);
			}
		}
		listView.setVisible(!listView.isVisible());
		newView.setVisible(!newView.isVisible());
	}
	
	@Override
	public String getTaskName() {
		return "AddAsnLines";
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
		return "Add Asn Lines";
	}

}
