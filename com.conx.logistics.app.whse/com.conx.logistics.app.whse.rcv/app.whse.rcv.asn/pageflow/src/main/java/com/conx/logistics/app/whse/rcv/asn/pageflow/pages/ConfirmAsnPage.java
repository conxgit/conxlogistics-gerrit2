package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedEventHandler;
import com.conx.logistics.kernel.pageflow.event.PageFlowPageChangedEvent;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ConfirmAsnPage extends PageFlowPage {
	private static final String VIEW_HEIGHT = "100%";

	private TabSheet entityTabSheet;
	private Button saveButton;
	private Button resetButton;
	private HorizontalLayout toolstripLeftButtonPanel;
	private HorizontalLayout toolStrip;
	private Table asnLineTable;
	private BeanContainer<String, ASNLine> asnLineContainer;
	@SuppressWarnings("unused")
	private EntityManagerFactory emf;
	private Button cancelButton;
	private BeanContainer<String, ReferenceNumber> refNumContainer;
	private Table refNumTable;
	private HorizontalLayout localTransLayout;
	private Label pickupLocationOrgLabel;
	private Label pickupLocationOrg;
	private Label pickupLocationAddressLabel;
	private Label pickupLocationAddress;
	private Label pickupLocationContactNameLabel;
	private Label pickupLocationContactName;
	private Label pickupLocationContactPhoneLabel;
	private Label pickupLocationDockTypeLabel;
	private Label pickupLocationContactPhone;
	private Label pickupLocationDockType;
	private Label inboundCarrierOrgLabel;
	private Label inboundCarrierOrg;
	private Label inboundCarrierAddressLabel;
	private Label inboundCarrierAddress;
	private Label inboundCarrierContactNameLabel;
	private Label inboundCarrierContactName;
	private Label inboundCarrierContactPhoneLabel;
	private Label inboundCarrierContactPhone;
	private Label inboundCarrierDriverIdLabel;
	private Label inboundCarrierDriverId;
	private Label inboundCarrierVehicleIdLabel;
	private Label inboundCarrierVehicleId;
	private Label inboundCarrierBolNumLabel;
	private Label inboundCarrierBolNum;
	private Label inboundCarrierSealNumLabel;
	private Label inboundCarrierSealNum;
	private Label dropOffLocationOrgLabel;
	private Label dropOffLocationOrg;
	private Label dropOffLocationAddressLabel;
	private Label dropOffLocationAddress;
	private Label dropOffLocationContactNameLabel;
	private Label dropOffLocationContactName;
	private Label dropOffLocationContactPhoneLabel;
	private Label dropOffLocationContactPhone;
	private Label dropOffLocationDockTypeLabel;
	private Label dropOffLocationDockType;
	private Label pickupDateTime;
	private Label whArrivalDateTime;
	private Label whArrivalDateTimeLabel;
	private Label pickupDateTimeLabel;
	private VerticalLayout refNumLayout;
	private VerticalLayout asnLineLayout;

	private Map<String, Object> state;

	private void initContainers() {
		asnLineContainer = new BeanContainer<String, ASNLine>(ASNLine.class);
		refNumContainer = new BeanContainer<String, ReferenceNumber>(ReferenceNumber.class);

		refNumContainer.setBeanIdProperty("value");
		refNumContainer.addNestedContainerProperty("type.name");

		asnLineContainer.setBeanIdProperty("lineNumber");
		asnLineContainer.addNestedContainerProperty("product.name");
		asnLineContainer.addNestedContainerProperty("refNumber.value");
		asnLineContainer.addNestedContainerProperty("product.weightUnit.name");
		asnLineContainer.addNestedContainerProperty("product.volUnit.name");
	}

	public void initAsnLines() {
		Label asnLineLabel = new Label();
		asnLineLabel.setContentMode(Label.CONTENT_XHTML);
		asnLineLabel.setValue("<h3>Confirm Asn Lines</h3>");

		asnLineTable = new Table(); 
		asnLineTable.setSizeFull();
		asnLineTable.setSelectable(true);
		asnLineTable.setContainerDataSource(asnLineContainer);
		asnLineTable.setVisibleColumns(new Object[] { "product.name", "expectedTotalWeight", "product.weightUnit.name", "expectedTotalVolume", "product.volUnit.name", "expectedOuterPackCount" });
		asnLineTable.setColumnHeader("product.name", "Product Name");
		asnLineTable.setColumnHeader("expectedTotalWeight", "Total Weight");
		asnLineTable.setColumnHeader("product.weightUnit.name", "Weight Unit");
		asnLineTable.setColumnHeader("expectedTotalVolume", "Total Volume");
		asnLineTable.setColumnHeader("product.volUnit.name", "Volume Unit");
		asnLineTable.setColumnHeader("expectedOuterPackCount", "Outer Pack Count");

		asnLineLayout = new VerticalLayout();
		asnLineLayout.setSizeFull();
		asnLineLayout.setMargin(true);
		asnLineLayout.setSpacing(true);
		asnLineLayout.addComponent(asnLineLabel);
		asnLineLayout.addComponent(asnLineTable);
		asnLineLayout.setExpandRatio(asnLineTable, 1.0f);
	}

	public void initRefNums() {
		Label refNumLabel = new Label();
		refNumLabel.setContentMode(Label.CONTENT_XHTML);
		refNumLabel.setValue("<h3>Confirm Reference Numbers</h3>");

		refNumTable = new Table(); 
		refNumTable.setSizeFull();
		refNumTable.setSelectable(true);
		refNumTable.setImmediate(true);
		refNumTable.setNullSelectionAllowed(false);
		refNumTable.setContainerDataSource(refNumContainer);
		refNumTable.setVisibleColumns(new String[] { "value",  "type.name" });
		refNumTable.setColumnHeader("value", "Reference Number");
		refNumTable.setColumnHeader("type.name", "Type");

		refNumLayout = new VerticalLayout();
		refNumLayout.setSizeFull();
		refNumLayout.setMargin(true);
		refNumLayout.setSpacing(true);
		refNumLayout.addComponent(refNumLabel);
		refNumLayout.addComponent(refNumTable);
		refNumLayout.setExpandRatio(refNumTable, 1.0f);
	}

	public void initLocalTrans() {
		Label pickupLocationLabel = new Label();
		pickupLocationLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationLabel.setValue("<h3>Pick-Up Location</h3>");

		pickupLocationOrgLabel = new Label("<b>Organization</b>");
		pickupLocationOrgLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationOrg = new Label("");
		pickupLocationAddressLabel = new Label("<b>Address</b>");
		pickupLocationAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationAddress = new Label("");
		pickupLocationAddress.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactNameLabel = new Label("<b>Contact Name</b>");
		pickupLocationContactNameLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactName = new Label("");
		pickupLocationContactPhoneLabel = new Label("<b>Contact Phone</b>");
		pickupLocationContactPhoneLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactPhone = new Label("");
		pickupLocationDockTypeLabel = new Label("<b>Dock Type</b>");
		pickupLocationDockTypeLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationDockType = new Label("Dock Type");

		Label inboundCarrierLabel = new Label();
		inboundCarrierLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierLabel.setValue("<h3>Inbound Carrier Location</h3>");

		inboundCarrierOrgLabel = new Label("<b>Organization</b>");
		inboundCarrierOrgLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierOrg = new Label("");
		inboundCarrierAddressLabel = new Label("<b>Address</b>");
		inboundCarrierAddressLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierAddress = new Label("");
		inboundCarrierAddress.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierContactNameLabel = new Label("<b>Contact Name</b>");
		inboundCarrierContactNameLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierContactName = new Label("");
		inboundCarrierContactPhoneLabel = new Label("<b>Contact Phone</b>");
		inboundCarrierContactPhoneLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierContactPhone = new Label("");
		inboundCarrierDriverIdLabel = new Label("<b>Driver Id</b>");
		inboundCarrierDriverIdLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierDriverId = new Label("");
		inboundCarrierVehicleIdLabel = new Label("<b>Vehicle Id</b>");
		inboundCarrierVehicleIdLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierVehicleId = new Label("");
		inboundCarrierBolNumLabel = new Label("<b>Bol #</b>");
		inboundCarrierBolNumLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierBolNum = new Label("");
		inboundCarrierSealNumLabel = new Label("<b>Seal #</b>");
		inboundCarrierSealNumLabel.setContentMode(Label.CONTENT_XHTML);
		inboundCarrierSealNum = new Label("");

		Label datesTimesLabel = new Label();
		datesTimesLabel.setContentMode(Label.CONTENT_XHTML);
		datesTimesLabel.setValue("<h3>Dates & Times</h3>");

		pickupDateTimeLabel = new Label("<b>Pick-Up Date/Time</b>");
		pickupDateTimeLabel.setContentMode(Label.CONTENT_XHTML);
		pickupDateTime = new Label("");
		whArrivalDateTimeLabel = new Label("<b>WH Arrival Date/Time</b>");
		whArrivalDateTimeLabel.setContentMode(Label.CONTENT_XHTML);
		whArrivalDateTime = new Label("");

		Label dropOffLocationLabel = new Label();
		dropOffLocationLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationLabel.setValue("<h3>Drop Off Location</h3>");

		dropOffLocationOrgLabel = new Label("<b>Organization</b>");
		dropOffLocationOrgLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationOrg = new Label("");
		dropOffLocationAddressLabel = new Label("<b>Address</b>");
		dropOffLocationAddressLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationAddress = new Label("");
		dropOffLocationAddress.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactNameLabel = new Label("<b>Contact Name</b>");
		dropOffLocationContactNameLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactName = new Label("");
		dropOffLocationContactPhoneLabel = new Label("<b>Contact Phone</b>");
		dropOffLocationContactPhoneLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactPhone = new Label("");
		dropOffLocationDockTypeLabel = new Label("<b>Dock Type</b>");
		dropOffLocationDockTypeLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationDockType = new Label("Dock Type");

		GridLayout pickupLocationLayout = new GridLayout(2, 7);
		pickupLocationLayout.setWidth("100%");
		pickupLocationLayout.setSpacing(true);
		pickupLocationLayout.setMargin(false, true, true, true);
		pickupLocationLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationLayout.addComponent(pickupLocationLabel, 0, 0, 1, 0);
		pickupLocationLayout.addComponent(pickupLocationOrgLabel);
		pickupLocationLayout.addComponent(pickupLocationOrg);
		pickupLocationLayout.addComponent(pickupLocationAddressLabel);
		pickupLocationLayout.addComponent(pickupLocationAddress);
		pickupLocationLayout.addComponent(pickupLocationContactNameLabel);
		pickupLocationLayout.addComponent(pickupLocationContactName);
		pickupLocationLayout.addComponent(pickupLocationContactPhoneLabel);
		pickupLocationLayout.addComponent(pickupLocationContactPhone);
		pickupLocationLayout.addComponent(pickupLocationDockTypeLabel);
		pickupLocationLayout.addComponent(pickupLocationDockType);
		pickupLocationLayout.addComponent(pickupDateTimeLabel);
		pickupLocationLayout.addComponent(pickupDateTime);

		GridLayout inboundCarrierLayout = new GridLayout(2, 9);
		inboundCarrierLayout.setWidth("100%");
		inboundCarrierLayout.setSpacing(true);
		inboundCarrierLayout.setMargin(false, true, true, true);
		inboundCarrierLayout.setColumnExpandRatio(0, 0.3f);
		inboundCarrierLayout.setColumnExpandRatio(1, 0.7f);
		inboundCarrierLayout.addComponent(inboundCarrierLabel, 0, 0, 1, 0);
		inboundCarrierLayout.addComponent(inboundCarrierOrgLabel);
		inboundCarrierLayout.addComponent(inboundCarrierOrg);
		inboundCarrierLayout.addComponent(inboundCarrierAddressLabel);
		inboundCarrierLayout.addComponent(inboundCarrierAddress);
		inboundCarrierLayout.addComponent(inboundCarrierContactNameLabel);
		inboundCarrierLayout.addComponent(inboundCarrierContactName);
		inboundCarrierLayout.addComponent(inboundCarrierContactPhoneLabel);
		inboundCarrierLayout.addComponent(inboundCarrierContactPhone);
		inboundCarrierLayout.addComponent(inboundCarrierDriverIdLabel);
		inboundCarrierLayout.addComponent(inboundCarrierDriverId);
		inboundCarrierLayout.addComponent(inboundCarrierVehicleIdLabel);
		inboundCarrierLayout.addComponent(inboundCarrierVehicleId);
		inboundCarrierLayout.addComponent(inboundCarrierBolNumLabel);
		inboundCarrierLayout.addComponent(inboundCarrierBolNum);
		inboundCarrierLayout.addComponent(inboundCarrierSealNumLabel);
		inboundCarrierLayout.addComponent(inboundCarrierSealNum);

		GridLayout dropOffLocationLayout = new GridLayout(2, 6);
		dropOffLocationLayout.setWidth("100%");
		dropOffLocationLayout.setSpacing(true);
		dropOffLocationLayout.setMargin(false, true, true, true);
		dropOffLocationLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationLayout.addComponent(dropOffLocationLabel, 0, 0, 1, 0);
		dropOffLocationLayout.addComponent(dropOffLocationOrgLabel);
		dropOffLocationLayout.addComponent(dropOffLocationOrg);
		dropOffLocationLayout.addComponent(dropOffLocationAddressLabel);
		dropOffLocationLayout.addComponent(dropOffLocationAddress);
		dropOffLocationLayout.addComponent(dropOffLocationContactNameLabel);
		dropOffLocationLayout.addComponent(dropOffLocationContactName);
		dropOffLocationLayout.addComponent(dropOffLocationContactPhoneLabel);
		dropOffLocationLayout.addComponent(dropOffLocationContactPhone);
		dropOffLocationLayout.addComponent(dropOffLocationDockTypeLabel);
		dropOffLocationLayout.addComponent(dropOffLocationDockType);
		dropOffLocationLayout.addComponent(whArrivalDateTimeLabel);
		dropOffLocationLayout.addComponent(whArrivalDateTime);

		localTransLayout = new HorizontalLayout();
		localTransLayout.setSizeFull();
		localTransLayout.setMargin(true);
		localTransLayout.setSpacing(true);
		localTransLayout.addComponent(pickupLocationLayout);
		localTransLayout.addComponent(inboundCarrierLayout);
		localTransLayout.addComponent(dropOffLocationLayout);
		localTransLayout.setExpandRatio(pickupLocationLayout, 0.25f);
		localTransLayout.setExpandRatio(inboundCarrierLayout, 0.25f);
		localTransLayout.setExpandRatio(dropOffLocationLayout, 0.25f);
	}

	public void initEntityTabSheet() {
		initRefNums();
		initAsnLines();
		initLocalTrans();

		entityTabSheet = new TabSheet();
		entityTabSheet.setWidth("100%");
		entityTabSheet.setHeight(VIEW_HEIGHT);
		entityTabSheet.addTab(refNumLayout, "Reference Numbers");
		entityTabSheet.addTab(asnLineLayout, "Asn Lines");
		entityTabSheet.addTab(localTransLayout, "Local Transportation");
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
		toolStrip.setComponentAlignment(toolstripLeftButtonPanel, Alignment.MIDDLE_LEFT);
	}

	private String addressToXhtml(Address address) {
		StringBuffer xhtml = new StringBuffer();
		xhtml.append("<b>");
		xhtml.append(address.getStreet1());
		xhtml.append("</b></br>");
		if (address.getUnloco() != null && address.getCountryState() != null) {
			xhtml.append(address.getUnloco().getPortCity());
			xhtml.append(", ");
			xhtml.append(address.getCountryState().getName());
			xhtml.append("</br>");
		} else if (address.getUnloco() == null && address.getCountryState() != null) {
			xhtml.append(address.getCountryState().getName());
			xhtml.append("</br>");
		} else if (address.getCountryState() == null && address.getUnloco() != null) {
			xhtml.append(address.getUnloco().getPortCity());
			xhtml.append("</br>");
		}

		if (address.getCountry() != null && address.getZipCode() != null) {
			xhtml.append(address.getZipCode());
			xhtml.append(" ");
			xhtml.append(address.getCountry().getName());
			xhtml.append("</br>");
		} else if (address.getCountry() != null && address.getZipCode() == null) {
			xhtml.append(address.getCountry().getName());
			xhtml.append("</br>");
		} else if (address.getCountry() == null && address.getZipCode() != null) {
			xhtml.append(address.getZipCode());
			xhtml.append("</br>");
		}

		return xhtml.toString();
	}

	@Override
	public String getTaskName() {
		return "ConfirmAsn";
	}

	@Override
	public String getCaption() {
		return "Confirm Asn";
	}

	@Override
	public void initialize(EntityManagerFactory emf, PlatformTransactionManager ptm,
			IPageFlowPageChangedEventHandler pfpEventHandler, ITaskWizard wizard) {
		setExecuted(false);
		
		super.wizard = wizard;
		super.pfpEventHandler = pfpEventHandler;
		super.pfpEventHandler.registerForPageFlowPageChanged(this);		
		
		initContainers();
		initEntityTabSheet();
		initTableToolStrip();

		VerticalLayout canvas = new VerticalLayout();
		canvas.setSizeFull();
		canvas.addComponent(entityTabSheet);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(entityTabSheet, 1.0f);

		this.setCanvas(canvas);
	}
	
	@Override
	public Map<String, Object> getOnCompleteState() {
		setExecuted(true);
		return this.state;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setOnStartState(Map<String, Object> state) {
		this.state = (Map<String, Object>)state.get("Content");
		if (state != null) {
			Map<String, Object> asnRefNumMapIn = (Map<String, Object>)this.state.get("asnRefNumMapIn");
			Set<ReferenceNumber> refNums = (Set<ReferenceNumber>)asnRefNumMapIn.get("asnRefNumCollection");
			
			Map<String, Object> asnASNLineProductMapIn = (Map<String, Object>)this.state.get("asnASNLineProductMapIn");			
			Set<ASNLine> asnLines = (Set<ASNLine>)asnASNLineProductMapIn.get("asnLinesCollection");
			
			Map<String, Object> asnLocalTransMapIn = (Map<String, Object>)this.state.get("asnLocalTransMapIn");			
			ASNPickup pickup = (ASNPickup)asnLocalTransMapIn.get("asnPickup");
			ASNDropOff dropOff = (ASNDropOff)asnLocalTransMapIn.get("asnDropoff");

			if (refNumContainer != null && refNums != null) {
				try {
					refNumContainer.addAll(refNums);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (asnLineContainer != null && asnLines != null) {
				try {
					asnLineContainer.addAll(asnLines);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (pickup != null) {
				pickupLocationOrg.setValue(new Label(pickup.getPickUpFrom().getName()));
				pickupLocationAddress.setValue((addressToXhtml(pickup.getPickUpFromAddress())));
				pickupLocationContactName.setValue((pickup.getPickUpFrom().getMainContact().getFirstName() + " " + pickup.getPickUpFrom().getMainContact().getLastName()));
				pickupLocationContactPhone.setValue((pickup.getPickUpFrom().getMainContact().getCellPhoneNumber()));
				if (pickup.getDockType() != null) {
					pickupLocationDockType.setValue(dropOff.getDockType().getName());
				} else {
					pickupLocationDockType.setValue("N/A");
				}
				
				inboundCarrierOrg.setValue((pickup.getLocalTrans().getName()));
				inboundCarrierAddress.setValue((addressToXhtml(pickup.getLocalTransAddress())));
				inboundCarrierContactName.setValue((pickup.getLocalTrans().getMainContact().getFirstName() + " " + pickup.getLocalTrans().getMainContact().getLastName()));
				inboundCarrierContactPhone.setValue((pickup.getLocalTrans().getMainContact().getCellPhoneNumber()));
				inboundCarrierDriverId.setValue((pickup.getDriverId()));
				inboundCarrierVehicleId.setValue((pickup.getVehicleId()));
				inboundCarrierBolNum.setValue((pickup.getBolNumber()));
				inboundCarrierSealNum.setValue((pickup.getSealNumber()));
			
				try {
					pickupDateTime.setValue((pickup.getEstimatedPickup()
							.toString()));
				} catch (Exception e) {
					pickupDateTime.setValue("N/A");
				}
			}
			if (dropOff != null) {
				try {
					whArrivalDateTime.setValue((dropOff
							.getEstimatedDropOff().toString()));
				} catch (Exception e) {
					whArrivalDateTime.setValue("N/A");
				}
				
				dropOffLocationOrg.setValue((dropOff.getDropOffAt().getName()));
				dropOffLocationAddress.setValue((addressToXhtml(dropOff.getDropOffAtAddress())));
				dropOffLocationContactName.setValue((dropOff.getDropOffAt().getMainContact().getFirstName() + " " + dropOff.getDropOffAt().getMainContact().getLastName()));
				dropOffLocationContactPhone.setValue((dropOff.getDropOffAt().getMainContact().getCellPhoneNumber()));
				if (dropOff.getDockType() != null) {
					dropOffLocationDockType.setValue(dropOff.getDockType().getName());
				} else {
					dropOffLocationDockType.setValue("N/A");
				}
			}
		}
	}

	@Override
	public void onPageChanged(PageFlowPageChangedEvent event) {
	}	
}
