package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.currency.CurrencyUnit;
import com.conx.logistics.mdm.domain.product.DimUnit;
import com.conx.logistics.mdm.domain.product.PackUnit;
import com.conx.logistics.mdm.domain.product.Product;
import com.conx.logistics.mdm.domain.product.ProductType;
import com.conx.logistics.mdm.domain.product.WeightUnit;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnLinesPage extends PageFlowPage {
	private static final String VIEW_HEIGHT = "450px";
	
	private int pageMode = LIST_PAGE_MODE;
	
	private static final int LIST_PAGE_MODE = 0;
	private static final int EDIT_PAGE_MODE = 1;
	private static final int NEW_PAGE_MODE = 2;
	
	private Table asnLineTable;
	private VerticalLayout mainView;
	private VerticalLayout listView;
	private ComboBox referenceId;
	private Button bindProductButton;
	private HorizontalLayout addStrip;
	private Button newButton;
	private Button deleteButton;
	private HorizontalLayout toolStrip;
	private VerticalLayout newView;
	private ComboBox productTypeFilter;
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
	private BeanContainer<String, ReferenceNumber> refNumBeanContainer;

	private HorizontalLayout toolstripLeftButtonPanel;
	private Button resetButton;
	private Button saveButton;
	private HorizontalLayout toolstripRightButtonPanel;
	private Button editButton;
	private Table productMaster;
	private VerticalLayout productLayout;
	private Button productFilterButton;
	private HorizontalLayout productFilterLayout;
	private Button newProductButton;
	private HorizontalLayout productButtonStrip;
	private HorizontalLayout productToolStrip;
	private Label selectedProductLabel;
	private HorizontalLayout productSelectionLayout;
	private JPAContainer<CurrencyUnit> currencyUnitContainer;
	private BeanContainer<String, ASNLine> asnLineContainer;
	private Product currentProduct;
	private int asnLineIndex = 0;

	private EntityManagerFactory emf;
	private RichTextArea specialInstructions;
	private HorizontalLayout specialInstructionsLayout;
	private VerticalLayout specialInstructionsForm;
	private Button cancelButton;
	private HorizontalSplitPanel productView;
	private TextField totalProductWeight;
	private ComboBox totalProductWeightUnit;
	private TextField totalProductVolume;
	private ComboBox totalProductVolumeUnit;
	private TextField productLength;
	private ComboBox productLengthUnit;
	private TextField productWidth;
	private ComboBox productWidthUnit;
	private TextField productHeight;
	private ComboBox productHeightUnit;
	private TextField productUnitsOuter;
	private ComboBox productUnitsOuterPackageType;
	private TextField productUnitsInner;
	private ComboBox productUnitsInnerPackageType;
	private TextField productName;
	private ComboBox productType;
	private GridLayout productDetailsForm;
	private TabSheet productDetail;
	
	private void setPageMode(int mode) {
		this.pageMode = mode;
		switch (mode) {
		case LIST_PAGE_MODE:
			newButton.setEnabled(true);
			saveButton.setEnabled(false);
			if (asnLineTable.getValue() != null) {
				deleteButton.setEnabled(true);
			}
			resetButton.setEnabled(false);
			
			newView.setVisible(false);
			listView.setVisible(true);
			break;
		case EDIT_PAGE_MODE:
			newButton.setEnabled(false);
			saveButton.setEnabled(true);
			deleteButton.setEnabled(false);
			resetButton.setEnabled(true);
			
			reset();
			listView.setVisible(false);
			newView.setVisible(true);
			break;
		case NEW_PAGE_MODE:
			newButton.setEnabled(false);
			saveButton.setEnabled(true);
			deleteButton.setEnabled(false);
			resetButton.setEnabled(true);
			
			reset();
			listView.setVisible(false);
			newView.setVisible(true);
			break;
		}
	}
	
	private boolean validate() {
		StringBuffer message = new StringBuffer();
		switch (pageMode) {
		case NEW_PAGE_MODE:
			if (currentProduct == null) {
				message.append("</br>Product was not attatched");
			}
			if (totalWeight.getValue() == null || ((String) totalWeight.getValue()).isEmpty()) {
				message.append("</br>Total Weight was not provided");
			}
			if (totalWeightUnit.getValue() == null) {
				message.append("</br>Total Weight Unit was not provided");
			}
			if (totalVolume.getValue() == null || ((String) totalVolume.getValue()).isEmpty()) {
				message.append("</br>Total Volume was not provided");
			}
			if (totalVolumeUnit.getValue() == null) {
				message.append("</br>Total Volume Unit was not provided");
			}
			if (length.getValue() == null || ((String) length.getValue()).isEmpty()) {
				message.append("</br>Length was not provided");
			}
			if (lengthUnit.getValue() == null) {
				message.append("</br>Length Unit was not provided");
			}
			if ((width.getValue() == null || ((String) width.getValue()).isEmpty())) {
				message.append("</br>Width was not provided");
			}
			if (widthUnit.getValue() == null) {
				message.append("</br>Width Unit was not provided");
			}
			if (height.getValue() == null || ((String) height.getValue()).isEmpty()) {
				message.append("</br>Height was not provided");
			}
			if (heightUnit.getValue() == null) {
				message.append("</br>Height Unit was not provided");
			}
			if (unitsOuter.getValue() == null || ((String) unitsOuter.getValue()).isEmpty()) {
				message.append("</br>Units Outer was not provided");
			}
			if (unitsOuterPackageType.getValue() == null) {
				message.append("</br>Units Outer Package Type was not provided");
			}
			if (unitsInner.getValue() == null || ((String) unitsInner.getValue()).isEmpty()) {
				message.append("</br>Units Inner was not provided");
			}
			if (unitsInnerPackageType.getValue() == null) {
				message.append("</br>Units Inner Package Type was not provided");
			}
			if (number.getValue() == null || ((String) number.getValue()).isEmpty()) {
				message.append("</br>Value was not provided");
			}
			if (numberUnit.getValue() == null) {
				message.append("</br>Value Currency was not provided");
			}
			if (referenceId.getValue() == null) {
				message.append("</br>Reference Number was not provided");
			}
			
			if (message.length() != 0) {
				showNotification("Could Not Add Reference Number", message.toString());
				return false;
			} else {
				return true;
			}
		case EDIT_PAGE_MODE:
			if (currentProduct == null) {
				message.append("</br>Product was not attatched");
			}
			if (totalWeight.getValue() == null || ((String) totalWeight.getValue()).isEmpty()) {
				message.append("</br>Total Weight was not provided");
			}
			if (totalWeightUnit.getValue() == null) {
				message.append("</br>Total Weight Unit was not provided");
			}
			if (totalVolume.getValue() == null || ((String) totalVolume.getValue()).isEmpty()) {
				message.append("</br>Total Volume was not provided");
			}
			if (totalVolumeUnit.getValue() == null) {
				message.append("</br>Total Volume Unit was not provided");
			}
			if (length.getValue() == null || ((String) length.getValue()).isEmpty()) {
				message.append("</br>Length was not provided");
			}
			if (lengthUnit.getValue() == null) {
				message.append("</br>Length Unit was not provided");
			}
			if ((width.getValue() == null || ((String) width.getValue()).isEmpty())) {
				message.append("</br>Width was not provided");
			}
			if (widthUnit.getValue() == null) {
				message.append("</br>Width Unit was not provided");
			}
			if (height.getValue() == null || ((String) height.getValue()).isEmpty()) {
				message.append("</br>Height was not provided");
			}
			if (heightUnit.getValue() == null) {
				message.append("</br>Height Unit was not provided");
			}
			if (unitsOuter.getValue() == null || ((String) unitsOuter.getValue()).isEmpty()) {
				message.append("</br>Units Outer was not provided");
			}
			if (unitsOuterPackageType.getValue() == null) {
				message.append("</br>Units Outer Package Type was not provided");
			}
			if (unitsInner.getValue() == null || ((String) unitsInner.getValue()).isEmpty()) {
				message.append("</br>Units Inner was not provided");
			}
			if (unitsInnerPackageType.getValue() == null) {
				message.append("</br>Units Inner Package Type was not provided");
			}
			if (number.getValue() == null || ((String) number.getValue()).isEmpty()) {
				message.append("</br>Value was not provided");
			}
			if (numberUnit.getValue() == null) {
				message.append("</br>Value Currency was not provided");
			}
			if (referenceId.getValue() == null) {
				message.append("</br>Reference Number was not provided");
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
			ASNLine line = asnLineContainer.getItem(asnLineTable.getValue()).getBean();
			referenceId.setValue(line.getRefNumber().getValue());
			productMaster.setValue(line.getProduct().getId());
			currentProduct = line.getProduct();
			currentProduct = productContainer.getItem(productMaster.getValue()).getEntity();
			selectedProductLabel.setValue(currentProduct.getName());
			bindProductButton.setEnabled(false);
			totalWeight.setValue(currentProduct.getWeight());
			totalWeightUnit.setValue(currentProduct.getWeightUnit().getId());
			totalVolume.setValue(currentProduct.getVolume());
			totalVolumeUnit.setValue(currentProduct.getVolUnit().getId());
			length.setValue(currentProduct.getLen());
			lengthUnit.setValue(currentProduct.getDimUnit().getId());
			width.setValue(currentProduct.getWidth());
			widthUnit.setValue(currentProduct.getDimUnit().getId());
			height.setValue(currentProduct.getHeight());
			heightUnit.setValue(currentProduct.getDimUnit().getId());
			if (currentProduct.getInnerPackCount() == null) {
				unitsInner.setValue(0);
			} else {
				unitsInner.setValue(currentProduct.getInnerPackCount());
			}
			if (currentProduct.getInnerPackUnit() != null) {
				unitsInnerPackageType.setValue(currentProduct.getInnerPackUnit().getId());
			}
			if (currentProduct.getOuterPackCount() == null) {
				unitsOuter.setValue(0);
			} else {
				unitsOuter.setValue(currentProduct.getOuterPackCount());
			}
			if (currentProduct.getOuterPackUnit() != null) {
				unitsOuterPackageType.setValue(currentProduct.getOuterPackUnit().getId());
			}
			if (currentProduct.getCommercialRecord() != null && 
					currentProduct.getCommercialRecord().getCommercialValue() != null) {
				if (currentProduct.getCommercialRecord().getCommercialValue().getValue() != null) {
					number.setValue(currentProduct.getCommercialRecord().getCommercialValue().getValue());
				}
				if (currentProduct.getCommercialRecord().getCommercialValue().getCurrency() != null && 
						currentProduct.getCommercialRecord().getCommercialValue().getCurrency().getId() != null) {
					numberUnit.setValue(currentProduct.getCommercialRecord().getCommercialValue().getCurrency().getId());
				}
			}
			if (line.getDescription() != null) {
				specialInstructions.setValue(line.getDescription());
			}
			entityTabSheet.setSelectedTab(productLayout);
			break;
		case NEW_PAGE_MODE:
			referenceId.setValue(null);
//			productTable.setValue(null);
			bindProductButton.setEnabled(false);
			selectedProductLabel.setValue("<i>No Product Currently Selected</i>");
			totalWeight.setValue(0);
			totalWeightUnit.setValue(null);
			totalVolume.setValue(0.0);
			totalVolumeUnit.setValue(null);
			length.setValue(0.0);
			lengthUnit.setValue(null);
			width.setValue(0.0);
			widthUnit.setValue(null);
			height.setValue(0.0);
			heightUnit.setValue(null);
			unitsOuter.setValue(0);
			unitsOuterPackageType.setValue(null);
			unitsInner.setValue(0);
			unitsInnerPackageType.setValue(null);
			number.setValue(0.0);
			numberUnit.setValue(null);
			specialInstructions.setValue("");
			entityTabSheet.setSelectedTab(productLayout);
			break;
		}
	}
	
	private void edit() {
		if (validate()) {
			BeanItem<ASNLine> item = this.asnLineContainer.getItem(asnLineTable.getValue()); 
			ASNLine line = item.getBean();
			ReferenceNumber rn = refNumBeanContainer.getItem(referenceId.getValue()).getBean();
			Integer eipc = Integer.parseInt((String) unitsInner.getValue());
			Integer eopc = Integer.parseInt((String) unitsInner.getValue());
			Double etv = Double.parseDouble((String) totalVolume.getValue());
			Double etw = Double.parseDouble((String) totalWeight.getValue());
			Double l = Double.parseDouble((String) length.getValue());
			Double w = Double.parseDouble((String) width.getValue());
			Double h = Double.parseDouble((String) height.getValue());
			String si = (String) specialInstructions.getValue();
			
			line.setProduct(currentProduct);
			line.setRefNumber(rn);
			item.getItemProperty("refNumber.value").setValue(rn.getValue());
			line.setExpectedInnerPackCount(eipc);
			item.getItemProperty("expectedInnerPackCount").setValue(eipc);
			line.setExpectedOuterPackCount(eopc);
			item.getItemProperty("expectedOuterPackCount").setValue(eopc);
			line.setExpectedTotalWeight(etw);
			item.getItemProperty("expectedTotalWeight").setValue(etw);
			line.setExpectedTotalVolume(etv);
			item.getItemProperty("expectedTotalVolume").setValue(etv);
			line.setExpectedTotalLen(l);
			item.getItemProperty("expectedTotalLen").setValue(l);
			line.setExpectedTotalWidth(w);
			item.getItemProperty("expectedTotalWidth").setValue(w);
			line.setExpectedTotalHeight(h);
			item.getItemProperty("expectedTotalHeight").setValue(h);
			line.setDescription(si);
			
			setPageMode(LIST_PAGE_MODE);
		}
	}
	
	private void create() {
		if (validate()) {
			ASNLine line = new ASNLine();
			line.setProduct(currentProduct);
			line.setRefNumber(refNumBeanContainer.getItem(referenceId.getValue()).getBean());
			line.setLineNumber(asnLineIndex);
			line.setExpectedInnerPackCount(Integer.parseInt((String) unitsInner.getValue()));
			line.setExpectedOuterPackCount(Integer.parseInt((String) unitsOuter.getValue()));
			line.setExpectedTotalWeight(Double.parseDouble((String) totalWeight.getValue()));
			line.setExpectedTotalVolume(Double.parseDouble((String) totalVolume.getValue()));
			line.setExpectedTotalLen(Double.parseDouble((String) length.getValue()));
			line.setExpectedTotalWidth(Double.parseDouble((String) width.getValue()));
			line.setExpectedTotalHeight(Double.parseDouble((String) height.getValue()));
			line.setDescription((String) specialInstructions.getValue());
			asnLineContainer.addBean(line);
			asnLineIndex++;
			setPageMode(LIST_PAGE_MODE);
		}
	}
	
	private void initContainers() {
		dimUnitContainer = JPAContainerFactory.make(DimUnit.class, this.emf.createEntityManager());
		currencyUnitContainer = JPAContainerFactory.make(CurrencyUnit.class, this.emf.createEntityManager());
		weightUnitContainer = JPAContainerFactory.make(WeightUnit.class, this.emf.createEntityManager());
		packUnitContainer = JPAContainerFactory.make(PackUnit.class, this.emf.createEntityManager());
		productContainer = JPAContainerFactory.make(Product.class, this.emf.createEntityManager());
		productTypeContainer = JPAContainerFactory.make(ProductType.class, this.emf.createEntityManager());
		asnLineContainer = new BeanContainer<String, ASNLine>(ASNLine.class);
		refNumBeanContainer = new BeanContainer<String, ReferenceNumber>(ReferenceNumber.class);
		
		refNumBeanContainer.setBeanIdProperty("value");
		productContainer.addNestedContainerProperty("productType.name");
		asnLineContainer.setBeanIdProperty("lineNumber");
		asnLineContainer.addNestedContainerProperty("product.name");
		asnLineContainer.addNestedContainerProperty("refNumber.value");
		asnLineContainer.addNestedContainerProperty("product.weightUnit.name");
		asnLineContainer.addNestedContainerProperty("product.volUnit.name");
	}
	
	public void initListView() {
		asnLineTable = new Table(); 
		asnLineTable.setSizeFull();
		asnLineTable.setSelectable(true);
		asnLineTable.setImmediate(true);
		asnLineTable.setNullSelectionAllowed(false);
		asnLineTable.setContainerDataSource(asnLineContainer);
		asnLineTable.setVisibleColumns(new Object[] { "product.name", "refNumber.value", "expectedTotalWeight", "product.weightUnit.name", "expectedTotalVolume", "product.volUnit.name", "expectedOuterPackCount" });
		asnLineTable.setColumnHeader("product.name", "Product Name");
		asnLineTable.setColumnHeader("refNumber.value", "Reference Number");
		asnLineTable.setColumnHeader("expectedTotalWeight", "Total Weight");
		asnLineTable.setColumnHeader("product.weightUnit.name", "Weight Unit");
		asnLineTable.setColumnHeader("expectedTotalVolume", "Total Volume");
		asnLineTable.setColumnHeader("product.volUnit.name", "Volume Unit");
		asnLineTable.setColumnHeader("expectedOuterPackCount", "Expected Quantity");
		asnLineTable.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 4696828026010510338L;

			public void valueChange(ValueChangeEvent event) {
                deleteButton.setEnabled(true);
                editButton.setEnabled(true);
            }
        });
		
		listView = new VerticalLayout();
		listView.addComponent(asnLineTable);
		listView.setSpacing(true);
		listView.setWidth("100%");
		listView.setHeight(VIEW_HEIGHT);
		listView.setExpandRatio(asnLineTable, 1.0f);
	}
	
	public void initNewView() {
		referenceId = new ComboBox();
		referenceId.setInputPrompt("No Reference Number Assigned");
		referenceId.setNullSelectionAllowed(false);
		referenceId.setContainerDataSource(refNumBeanContainer);
		referenceId.setWidth("100%");

		addStrip = new HorizontalLayout();
		addStrip.setHeight("50px");
		addStrip.setWidth("100%");
		addStrip.setSpacing(true);
		addStrip.addComponent(referenceId);
		
		newView = new VerticalLayout();
        newView.setWidth("100%");
        newView.setHeight(VIEW_HEIGHT);
        newView.addComponent(addStrip);
        newView.addComponent(entityTabSheet);
        newView.setExpandRatio(entityTabSheet, 1.0f);
        newView.setVisible(false);
	}
	
	public void initProductView() {
		Label totalProductWeightLabel = new Label();
		totalProductWeightLabel.setValue("Total Weight");

		totalProductWeight = new TextField(new ObjectProperty<Double>(0.0));
		totalProductWeight.setInputPrompt("Total Weight");
		totalProductWeight.setWidth("100%");

		totalProductWeightUnit = new ComboBox();
		totalProductWeightUnit.setInputPrompt("Units");
		totalProductWeightUnit.setContainerDataSource(weightUnitContainer);
		totalProductWeightUnit.setItemCaptionPropertyId("name");
		totalProductWeightUnit.setWidth("100%");
		totalProductWeightUnit.setNullSelectionAllowed(false);

		Label totalProductWeightFormLabel = new Label("<h3>Total Weight</h3>");
		totalProductWeightFormLabel.setContentMode(Label.CONTENT_XHTML);

		GridLayout totalProductWeightForm = new GridLayout(3, 1);
		totalProductWeightForm.setSpacing(true);
		totalProductWeightForm.setMargin(false, true, true, true);
		totalProductWeightForm.setColumnExpandRatio(0, 0.3f);
		totalProductWeightForm.setColumnExpandRatio(1, 0.4f);
		totalProductWeightForm.setColumnExpandRatio(2, 0.3f);
		totalProductWeightForm.setWidth("100%");
//		totalProductWeightForm.addComponent(totalProductWeightFormLabel, 0, 0, 1, 0);
		totalProductWeightForm.addComponent(totalProductWeightLabel);
		totalProductWeightForm.addComponent(totalProductWeight);
		totalProductWeightForm.addComponent(totalProductWeightUnit);
//		totalProductWeightForm.setComponentAlignment(totalProductWeightLabel, Alignment.MIDDLE_LEFT);

		Label totalProductVolumeLabel = new Label();
		totalProductVolumeLabel.setValue("Total Volume");

		Label productWidthLabel = new Label();
		productWidthLabel.setValue("Width");

		Label productLengthLabel = new Label();
		productLengthLabel.setValue("Length");

		Label productHeightLabel = new Label();
		productHeightLabel.setValue("Height");

		totalProductVolume = new TextField(new ObjectProperty<Double>(0.0));
		totalProductVolume.setInputPrompt("Total Volume");
		totalProductVolume.setWidth("100%");

		totalProductVolumeUnit = new ComboBox();
		totalProductVolumeUnit.setInputPrompt("Units");
		totalProductVolumeUnit.setContainerDataSource(dimUnitContainer);
		totalProductVolumeUnit.setItemCaptionPropertyId("name");
		totalProductVolumeUnit.setWidth("100%");
		totalProductVolumeUnit.setNullSelectionAllowed(false);

		productLength = new TextField(new ObjectProperty<Double>(0.0));
		productLength.setInputPrompt("Length");
		productLength.setWidth("100%");

		productLengthUnit = new ComboBox();
		productLengthUnit.setInputPrompt("Units");
		productLengthUnit.setContainerDataSource(dimUnitContainer);
		productLengthUnit.setItemCaptionPropertyId("name");
		productLengthUnit.setWidth("100%");
		productLengthUnit.setNullSelectionAllowed(false);

		productWidth = new TextField(new ObjectProperty<Double>(0.0));
		productWidth.setInputPrompt("Width");
		productWidth.setWidth("100%");

		productWidthUnit = new ComboBox();
		productWidthUnit.setInputPrompt("Units");
		productWidthUnit.setContainerDataSource(dimUnitContainer);
		productWidthUnit.setItemCaptionPropertyId("name");
		productWidthUnit.setWidth("100%");
		productWidthUnit.setNullSelectionAllowed(false);

		productHeight = new TextField(new ObjectProperty<Double>(0.0));
		productHeight.setInputPrompt("Height");
		productHeight.setWidth("100%");

		productHeightUnit = new ComboBox();
		productHeightUnit.setInputPrompt("Units");
		productHeightUnit.setContainerDataSource(dimUnitContainer);
		productHeightUnit.setItemCaptionPropertyId("name");
		productHeightUnit.setWidth("100%");
		productHeightUnit.setNullSelectionAllowed(false);

		Label productDimensionFormLabel = new Label("<h3>Dimensions</h3>");
		productDimensionFormLabel.setContentMode(Label.CONTENT_XHTML);

		GridLayout productDimensionForm = new GridLayout(3, 4);
		productDimensionForm.setSpacing(true);
		productDimensionForm.setMargin(false, true, true, true);
		productDimensionForm.setColumnExpandRatio(0, 0.3f);
		productDimensionForm.setColumnExpandRatio(1, 0.4f);
		productDimensionForm.setColumnExpandRatio(2, 0.3f);
		productDimensionForm.setWidth("100%");
//		productDimensionForm.addComponent(productDimensionFormLabel, 0, 0, 1, 0);
		productDimensionForm.addComponent(totalProductVolumeLabel);
		productDimensionForm.addComponent(totalProductVolume);
		productDimensionForm.addComponent(totalProductVolumeUnit);
		productDimensionForm.addComponent(productLengthLabel);
		productDimensionForm.addComponent(productLength);
		productDimensionForm.addComponent(productLengthUnit);
		productDimensionForm.addComponent(productWidthLabel);
		productDimensionForm.addComponent(productWidth);
		productDimensionForm.addComponent(productWidthUnit);
		productDimensionForm.addComponent(productHeightLabel);
		productDimensionForm.addComponent(productHeight);
		productDimensionForm.addComponent(productHeightUnit);
//		productDimensionForm.setComponentAlignment(totalProductVolumeLabel, Alignment.MIDDLE_LEFT);
//		productDimensionForm.setComponentAlignment(productLengthLabel, Alignment.MIDDLE_LEFT);
//		productDimensionForm.setComponentAlignment(productWidthLabel, Alignment.MIDDLE_LEFT);
//		productDimensionForm.setComponentAlignment(productHeightLabel, Alignment.MIDDLE_LEFT);

		Label productUnitsOuterLabel = new Label();
		productUnitsOuterLabel.setValue("Units Outer");

		Label productUnitsInnerLabel = new Label();
		productUnitsInnerLabel.setValue("Units Inner");

		productUnitsOuter = new TextField(new ObjectProperty<Integer>(0));
		productUnitsOuter.setInputPrompt("Units Outer");
		productUnitsOuter.setWidth("100%");

		productUnitsOuterPackageType = new ComboBox();
		productUnitsOuterPackageType.setInputPrompt("Package Type");
		productUnitsOuterPackageType.setContainerDataSource(packUnitContainer);
		productUnitsOuterPackageType.setItemCaptionPropertyId("name");
		productUnitsOuterPackageType.setWidth("100%");
		productUnitsOuterPackageType.setNullSelectionAllowed(false);

		productUnitsInner = new TextField(new ObjectProperty<Integer>(0));
		productUnitsInner.setInputPrompt("Units Inner");
		productUnitsInner.setWidth("100%");

		productUnitsInnerPackageType = new ComboBox();
		productUnitsInnerPackageType.setInputPrompt("Package Type");
		productUnitsInnerPackageType.setContainerDataSource(packUnitContainer);
		productUnitsInnerPackageType.setItemCaptionPropertyId("name");
		productUnitsInnerPackageType.setWidth("100%");
		productUnitsInnerPackageType.setNullSelectionAllowed(false);

		Label productQuantityFormLabel = new Label("<h3>Quantity</h3>");
		productQuantityFormLabel.setContentMode(Label.CONTENT_XHTML);

		GridLayout productQuantityForm = new GridLayout(3, 2);
		productQuantityForm.setSpacing(true);
		productQuantityForm.setMargin(false, true, false, true);
		productQuantityForm.setColumnExpandRatio(0, 0.3f);
		productQuantityForm.setColumnExpandRatio(1, 0.4f);
		productQuantityForm.setColumnExpandRatio(2, 0.3f);
		productQuantityForm.setWidth("100%");
		productQuantityForm.addComponent(productUnitsOuterLabel);
		productQuantityForm.addComponent(productUnitsOuter);
		productQuantityForm.addComponent(productUnitsOuterPackageType);
		productQuantityForm.addComponent(productUnitsInnerLabel);
		productQuantityForm.addComponent(productUnitsInner);
		productQuantityForm.addComponent(productUnitsInnerPackageType);
//		productQuantityForm.setComponentAlignment(productUnitsOuterLabel, Alignment.MIDDLE_LEFT);
//		productQuantityForm.setComponentAlignment(productUnitsInnerLabel, Alignment.MIDDLE_LEFT);

		HorizontalLayout productWeightDimensionQuantityLayout = new HorizontalLayout();
		productWeightDimensionQuantityLayout.setWidth("100%");
		productWeightDimensionQuantityLayout.setSpacing(true);
		productWeightDimensionQuantityLayout.setMargin(true);
		productWeightDimensionQuantityLayout.addComponent(totalProductWeightForm);
		productWeightDimensionQuantityLayout.addComponent(productDimensionForm);
		productWeightDimensionQuantityLayout.addComponent(productQuantityForm);
		productWeightDimensionQuantityLayout.setExpandRatio(totalProductWeightForm, 0.33f);
		productWeightDimensionQuantityLayout.setExpandRatio(productDimensionForm, 0.33f);
		productWeightDimensionQuantityLayout.setExpandRatio(productQuantityForm, 0.33f);
		
		Label productNameLabel = new Label("Product Name");
		
		productName = new TextField();
		productName.setWidth("100%");
		productName.setInputPrompt("Product Name");
		
		Label productTypeLabel = new Label("Product Type");
		
		productType = new ComboBox();
		productType.setInputPrompt("Product Type");
		productType.setContainerDataSource(productTypeContainer);
		productType.setItemCaptionPropertyId("name");
		productType.setWidth("100%");
		
		Label productDetailsFormLabel = new Label("<h3>Product Name</h3>");
		productDetailsFormLabel.setContentMode(Label.CONTENT_XHTML);
		
		productDetailsForm = new GridLayout(2, 2);
		productDetailsForm.setSpacing(true);
		productDetailsForm.setMargin(true, true, true, true);
		productDetailsForm.setColumnExpandRatio(0, 0.3f);
		productDetailsForm.setColumnExpandRatio(1, 0.7f);
		productDetailsForm.setWidth("100%");
		productDetailsForm.addComponent(productNameLabel);
		productDetailsForm.addComponent(productName);
		productDetailsForm.addComponent(productTypeLabel);
		productDetailsForm.addComponent(productType);
		
		productDetail = new TabSheet();
		productDetail.setSizeFull();
		productDetail.addTab(productDetailsForm, "Product Details");
		productDetail.addTab(productWeightDimensionQuantityLayout, "Weight, Dimensions & Quantity");
		
		productMaster = new Table();
		productMaster.setSizeFull();
		productMaster.setSelectable(true);
		productMaster.setImmediate(true);
		productMaster.setNullSelectionAllowed(false);
		productMaster.setContainerDataSource(productContainer);
		productMaster.setVisibleColumns(new String[] { "name",  "productType.name", "description" });
		productMaster.setColumnHeader("name", "Product Name");
		productMaster.setColumnHeader("productType.name", "Product Type");
		productMaster.setColumnHeader("description", "Description");
		productMaster.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 463328026010510338L;

			public void valueChange(ValueChangeEvent event) {
				currentProduct = productContainer.getItem(productMaster.getValue()).getEntity();
				bindProductButton.setEnabled(true);
				productName.setValue(currentProduct.getName());
				productType.setValue(currentProduct.getProductType().getId());
				totalProductWeight.setValue(currentProduct.getWeight());
				totalProductWeightUnit.setValue(currentProduct.getWeightUnit().getId());
				totalProductVolume.setValue(currentProduct.getVolume());
				totalProductVolumeUnit.setValue(currentProduct.getVolUnit().getId());
				productLength.setValue(currentProduct.getLen());
				productLengthUnit.setValue(currentProduct.getDimUnit().getId());
				productWidth.setValue(currentProduct.getWidth());
				productWidthUnit.setValue(currentProduct.getDimUnit().getId());
				productHeight.setValue(currentProduct.getHeight());
				productHeightUnit.setValue(currentProduct.getDimUnit().getId());
				if (currentProduct.getInnerPackCount() == null) {
					productUnitsInner.setValue(0);
				} else {
					productUnitsInner.setValue(currentProduct.getInnerPackCount());
				}
				if (currentProduct.getInnerPackUnit() != null) {
					productUnitsInnerPackageType.setValue(currentProduct.getInnerPackUnit().getId());
				}
				if (currentProduct.getOuterPackCount() == null) {
					productUnitsOuter.setValue(0);
				} else {
					productUnitsOuter.setValue(currentProduct.getOuterPackCount());
				}
				if (currentProduct.getOuterPackUnit() != null) {
					productUnitsOuterPackageType.setValue(currentProduct.getOuterPackUnit().getId());
				}

				productDetail.setSelectedTab(productDetailsForm);
            }
        });
		
		productView = new HorizontalSplitPanel();
		productView.setSizeFull();
		productView.addComponent(productMaster);
		productView.addComponent(productDetail);
	}
	
	public void initEntityTabSheet() {
		initProductView();
		/*productTable = new Table();
		productTable.setSizeFull();
		productTable.setSelectable(true);
		productTable.setImmediate(true);
		productTable.setNullSelectionAllowed(false);
		productTable.setContainerDataSource(productContainer);
		productTable.setVisibleColumns(new String[] { "name",  "productType.name" });
		productTable.setColumnHeader("name", "Product Name");
		productTable.setColumnHeader("productType.name", "Product Type");
		productTable.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 463328026010510338L;

			public void valueChange(ValueChangeEvent event) {
				bindProductButton.setEnabled(true);
            }
        }); */
		
		productTypeFilter = new ComboBox();
		productTypeFilter.setInputPrompt("Product Type");
		productTypeFilter.setContainerDataSource(productTypeContainer);
		productTypeFilter.setItemCaptionPropertyId("name");
		productTypeFilter.setWidth("100%");
		
		productFilterButton = new Button("Filter");
		productFilterButton.setWidth("100px");
		productFilterButton.addListener(new ClickListener() {
			private static final long serialVersionUID = -3985957993420116880L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});
		
		productFilterLayout = new HorizontalLayout();
		productFilterLayout.setWidth("450px");
		productFilterLayout.setSpacing(true);
		productFilterLayout.addComponent(productTypeFilter);
		productFilterLayout.addComponent(productFilterButton);
		productFilterLayout.setExpandRatio(productTypeFilter, 1.0f);
		
		newProductButton = new Button("New Product");
		newProductButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 574083792656762569L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (newProductButton.getCaption().equals("New Product")) {
					newProductButton.setCaption("Save Product");
					productView.setSplitPosition(0);
					productView.setLocked(true);
					productName.setValue("");
					productType.setValue(null);
					totalProductWeight.setValue(0.0f);
					totalProductWeightUnit.setValue(null);
					totalProductVolume.setValue(0.0f);
					totalProductVolumeUnit.setValue(null);
					productLength.setValue(0.0f);
					productLengthUnit.setValue(null);
					productWidth.setValue(0.0f);
					productWidthUnit.setValue(null);
					productHeight.setValue(0.0f);
					productHeightUnit.setValue(null);
					productUnitsInner.setValue(0);
					productUnitsInnerPackageType.setValue(null);
					productUnitsOuter.setValue(0);
					productUnitsOuterPackageType.setValue(null);
	
					productDetail.setSelectedTab(productDetailsForm);
				} else {
					Product p = new Product();
					p.setName((String) productName.getValue());
					p.setWeight(Double.parseDouble((String) totalProductWeight.getValue()));
					p.setVolume(Double.parseDouble((String) totalProductWeight.getValue()));
					p.setLen(Double.parseDouble((String) totalProductWeight.getValue()));
					p.setWidth(Double.parseDouble((String) totalProductWeight.getValue()));
					p.setHeight(Double.parseDouble((String) totalProductWeight.getValue()));
					p.setInnerPackCount(Integer.parseInt((String) productUnitsInner.getValue()));
					p.setOuterPackCount(Integer.parseInt((String) productUnitsOuter.getValue()));
					
					p.setWeightUnit(weightUnitContainer.getItem(totalProductWeightUnit.getValue()).getEntity());
					p.setVolUnit(dimUnitContainer.getItem(totalProductVolumeUnit.getValue()).getEntity());
					p.setDimUnit(dimUnitContainer.getItem(productLengthUnit.getValue()).getEntity());
					p.setInnerPackUnit(packUnitContainer.getItem(productUnitsInnerPackageType.getValue()).getEntity());
					p.setOuterPackUnit(packUnitContainer.getItem(productUnitsInnerPackageType.getValue()).getEntity());
				
					productContainer.addEntity(p);
					
					productView.setSplitPosition(50);
					productView.setLocked(false);
					productName.setValue("");
					productType.setValue(null);
					totalProductWeight.setValue(0.0f);
					totalProductWeightUnit.setValue(null);
					totalProductVolume.setValue(0.0f);
					totalProductVolumeUnit.setValue(null);
					productLength.setValue(0.0f);
					productLengthUnit.setValue(null);
					productWidth.setValue(0.0f);
					productWidthUnit.setValue(null);
					productHeight.setValue(0.0f);
					productHeightUnit.setValue(null);
					productUnitsInner.setValue(0);
					productUnitsInnerPackageType.setValue(null);
					productUnitsOuter.setValue(0);
					productUnitsOuterPackageType.setValue(null);
	
					productDetail.setSelectedTab(productDetailsForm);
				}
			}
		});
		
		bindProductButton = new Button("Attach Selected Product to ASN Line");
		bindProductButton.setEnabled(false);
		bindProductButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 318740392837363674L;

			public void buttonClick(ClickEvent event) {
				currentProduct = productContainer.getItem(productMaster.getValue()).getEntity();
				selectedProductLabel.setValue(currentProduct.getName());
				bindProductButton.setEnabled(false);
				totalWeight.setValue(currentProduct.getWeight());
				totalWeightUnit.setValue(currentProduct.getWeightUnit().getId());
				totalVolume.setValue(currentProduct.getVolume());
				totalVolumeUnit.setValue(currentProduct.getVolUnit().getId());
				length.setValue(currentProduct.getLen());
				lengthUnit.setValue(currentProduct.getDimUnit().getId());
				width.setValue(currentProduct.getWidth());
				widthUnit.setValue(currentProduct.getDimUnit().getId());
				height.setValue(currentProduct.getHeight());
				heightUnit.setValue(currentProduct.getDimUnit().getId());
				if (currentProduct.getInnerPackCount() == null) {
					unitsInner.setValue(0);
				} else {
					unitsInner.setValue(currentProduct.getInnerPackCount());
				}
				if (currentProduct.getInnerPackUnit() != null) {
					unitsInnerPackageType.setValue(currentProduct.getInnerPackUnit().getId());
				}
				if (currentProduct.getOuterPackCount() == null) {
					unitsOuter.setValue(0);
				} else {
					unitsOuter.setValue(currentProduct.getOuterPackCount());
				}
				if (currentProduct.getOuterPackUnit() != null) {
					unitsOuterPackageType.setValue(currentProduct.getOuterPackUnit().getId());
				}
				if (currentProduct.getCommercialRecord() != null && 
						currentProduct.getCommercialRecord().getCommercialValue() != null) {
					if (currentProduct.getCommercialRecord().getCommercialValue().getValue() != null) {
						number.setValue(currentProduct.getCommercialRecord().getCommercialValue().getValue());
					}
					if (currentProduct.getCommercialRecord().getCommercialValue().getCurrency() != null && 
							currentProduct.getCommercialRecord().getCommercialValue().getCurrency().getId() != null) {
						numberUnit.setValue(currentProduct.getCommercialRecord().getCommercialValue().getCurrency().getId());
					}
				}
			}
		});
		
		productButtonStrip = new HorizontalLayout();
		productButtonStrip.setSpacing(true);
		productButtonStrip.addComponent(newProductButton);
		productButtonStrip.addComponent(bindProductButton);
		
		productToolStrip = new HorizontalLayout();
		productToolStrip.setWidth("100%");
		productToolStrip.addComponent(productButtonStrip);
//		productToolStrip.addComponent(productFilterLayout);
		productToolStrip.setComponentAlignment(productButtonStrip, Alignment.MIDDLE_LEFT);
//		productToolStrip.setComponentAlignment(productFilterLayout, Alignment.MIDDLE_RIGHT);
		
		Label productLayoutLabel = new Label("<h3>Product Selection</h3>");
		productLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		
		Label productSelectionLabel = new Label("<b>Selected Product:</b>");
		productSelectionLabel.setContentMode(Label.CONTENT_XHTML);
		
		selectedProductLabel = new Label("<i>No Product Currently Selected</i>");
		selectedProductLabel.setContentMode(Label.CONTENT_XHTML);
		
		productSelectionLayout = new HorizontalLayout();
		productSelectionLayout.setSpacing(true);
		productSelectionLayout.addComponent(productSelectionLabel);
		productSelectionLayout.addComponent(selectedProductLabel);
        
		productLayout = new VerticalLayout();
		productLayout.setSizeFull();
		productLayout.setMargin(true);
		productLayout.setSpacing(true);
		productLayout.addComponent(productLayoutLabel);
		productLayout.addComponent(productToolStrip);
		productLayout.addComponent(productView);
		productLayout.addComponent(productSelectionLayout);
		productLayout.setExpandRatio(productView, 1.0f); 
        
        Label totalWeightLabel = new Label();
        totalWeightLabel.setValue("Total Weight");
        
        totalWeight = new TextField(new ObjectProperty<Double>(0.0));
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
        
        totalVolume = new TextField(new ObjectProperty<Double>(0.0));
        totalVolume.setInputPrompt("Total Volume");
        totalVolume.setWidth("100%");
        
        totalVolumeUnit = new ComboBox();
        totalVolumeUnit.setInputPrompt("Units");
        totalVolumeUnit.setContainerDataSource(dimUnitContainer);
        totalVolumeUnit.setItemCaptionPropertyId("name");
        totalVolumeUnit.setWidth("100%");
        totalVolumeUnit.setNullSelectionAllowed(false);
        
        length = new TextField(new ObjectProperty<Double>(0.0));
        length.setInputPrompt("Length");
        length.setWidth("100%");
        
        lengthUnit = new ComboBox();
        lengthUnit.setInputPrompt("Units");
        lengthUnit.setContainerDataSource(dimUnitContainer);
        lengthUnit.setItemCaptionPropertyId("name");
        lengthUnit.setWidth("100%");
        lengthUnit.setNullSelectionAllowed(false);
        
        width = new TextField(new ObjectProperty<Double>(0.0));
        width.setInputPrompt("Width");
        width.setWidth("100%");
        
        widthUnit = new ComboBox();
        widthUnit.setInputPrompt("Units");
        widthUnit.setContainerDataSource(dimUnitContainer);
        widthUnit.setItemCaptionPropertyId("name");
        widthUnit.setWidth("100%");
        widthUnit.setNullSelectionAllowed(false);
        
        height = new TextField(new ObjectProperty<Double>(0.0));
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
        
        unitsOuter = new TextField(new ObjectProperty<Integer>(0));
        unitsOuter.setInputPrompt("Units Outer");
        unitsOuter.setWidth("100%");
        
        unitsOuterPackageType = new ComboBox();
        unitsOuterPackageType.setInputPrompt("Package Type");
        unitsOuterPackageType.setContainerDataSource(packUnitContainer);
        unitsOuterPackageType.setItemCaptionPropertyId("name");
        unitsOuterPackageType.setWidth("100%");
        unitsOuterPackageType.setNullSelectionAllowed(false);
        
        unitsInner = new TextField(new ObjectProperty<Integer>(0));
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
        numberLabel.setValue("Value");
        
        number = new TextField(new ObjectProperty<Double>(0.0));
        number.setInputPrompt("Value");
        number.setWidth("100%");
        
        numberUnit = new ComboBox();
        numberUnit.setInputPrompt("Currency");
        numberUnit.setContainerDataSource(currencyUnitContainer);
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
        
        specialInstructions = new RichTextArea(null, "");
        specialInstructions.setSizeFull();
        
        Label specialInstructionsFormLabel = new Label("<h3>Special Instructions</h3>");
        specialInstructionsFormLabel.setContentMode(Label.CONTENT_XHTML);
        
        specialInstructionsForm = new VerticalLayout();
        specialInstructionsForm.setSpacing(true);
        specialInstructionsForm.setMargin(false, true, true, true);
        specialInstructionsForm.setSizeFull();
        specialInstructionsForm.addComponent(specialInstructionsFormLabel);
        specialInstructionsForm.addComponent(specialInstructions);
        
        specialInstructionsLayout = new HorizontalLayout();
        specialInstructionsLayout.setWidth("100%");
        specialInstructionsLayout.setSpacing(true);
        specialInstructionsLayout.setMargin(true);
        specialInstructionsLayout.addComponent(specialInstructionsForm);
		
		entityTabSheet = new TabSheet();
		entityTabSheet.setSizeFull();
		entityTabSheet.addTab(productLayout, "Product");
		entityTabSheet.addTab(weightDimensionQuantityLayout, "Weight, Dimensions & Quantity");
		entityTabSheet.addTab(additionalAttributesLayout, "Additional Attributes");
		entityTabSheet.addTab(specialInstructionsLayout, "Special Instructions");
	}
	
	public void initToolStrip() {
		newButton = new Button("New");
		newButton.setWidth("100%");
		newButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500322301678L;

			public void buttonClick(ClickEvent event) {
				setPageMode(NEW_PAGE_MODE);
			}
		});
		
		editButton = new Button("Edit");
		editButton.setEnabled(false);
		editButton.setWidth("100%");
		editButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500322301678L;

			public void buttonClick(ClickEvent event) {
				setPageMode(EDIT_PAGE_MODE);
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setWidth("100%");
		deleteButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5003289976157301678L;

			public void buttonClick(ClickEvent event) {
				asnLineTable.removeItem(asnLineTable.getValue());
				if (pageMode == EDIT_PAGE_MODE) {
					setPageMode(LIST_PAGE_MODE);
				}
			}
		});
		
		toolstripRightButtonPanel = new HorizontalLayout();
		toolstripRightButtonPanel.setWidth("300px");
		toolstripRightButtonPanel.setSpacing(true);
		toolstripRightButtonPanel.addComponent(newButton);
		toolstripRightButtonPanel.addComponent(editButton);
		toolstripRightButtonPanel.addComponent(deleteButton);
		toolstripRightButtonPanel.setExpandRatio(newButton, 0.33f);
		toolstripRightButtonPanel.setExpandRatio(editButton, 0.33f);
		toolstripRightButtonPanel.setExpandRatio(deleteButton, 0.33f);
		
		saveButton = new Button("Save");
		saveButton.setEnabled(false);
		saveButton.setWidth("100%");
		saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500312301678L;

			public void buttonClick(ClickEvent event) {
				switch (pageMode) {
				case NEW_PAGE_MODE:
					create();
					break;
				case EDIT_PAGE_MODE:
					edit();
					break;
				}
			}
		});
		
		resetButton = new Button("Reset");
		resetButton.setEnabled(false);
		resetButton.setWidth("100%");
		resetButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5003289976900978L;

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
	public String getTaskName() {
		return "AddAsnLines";
	}

	@Override
	public String getCaption() {
		return "Add Asn Lines";
	}

	@Override
	public void initialize(EntityManagerFactory emf) {
		this.emf = emf;
		
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
		
		VerticalLayout canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(mainView);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(mainView, 1.0f);
		
		this.setCanvas(canvas);
	}

	@Override
	public Map<String, Object> getOnCompleteState() {
		Map<String,Object> outParams = new HashMap<String, Object>();
		Set<ASNLine> asnLines = new HashSet<ASNLine>();
		for (Object id : asnLineTable.getItemIds()) {
			asnLines.add(asnLineContainer.getItem(id).getBean());
		}
		outParams.put("asnLinesCollectionOut", asnLines);
		
		return outParams;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setOnStartState(Map<String, Object> state) {
		Set<ReferenceNumber> refNums = (Set<ReferenceNumber>) state.get("refNumsCollectionOut");
		Set<ASNLine> asnLines = (Set<ASNLine>) state.get("asnLinesCollectionOut");
		if (refNums != null && refNumBeanContainer != null) {
			try {
				refNumBeanContainer.addAll(refNums);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (asnLines != null && asnLineContainer != null) {
			try {
				asnLineContainer.addAll(asnLines);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
