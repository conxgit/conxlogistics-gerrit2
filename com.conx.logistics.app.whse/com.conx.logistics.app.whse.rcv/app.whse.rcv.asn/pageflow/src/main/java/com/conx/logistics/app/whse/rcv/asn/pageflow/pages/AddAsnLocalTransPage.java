package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.mdm.domain.constants.AddressCustomCONSTANTS;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnLocalTransPage extends IPageFlowPage {
private static final String VIEW_HEIGHT = "400px";
	
	private VerticalLayout canvas;
	private ComboBox pickupCarrierOrganization;
	private TabSheet entityTabSheet;
	private ComboBox pickupCarrierAddress;
	private GridLayout pickupCarrierOrganizationLayout;
	private ComboBox pickupCarrierContact;
	private ComboBox pickupLocationOrganization;
	private ComboBox pickupLocationAddress;
	private GridLayout pickupLocationOrganizationLayout;
	private ComboBox pickupLocationContact;
	private ComboBox pickupLocationDockType;
	private HorizontalLayout pickupLocationLayout;
	private TextField pickupCarrierDriverId;
	private TextField pickupCarrierVehicleId;
	private TextField pickupCarrierBolNum;
	private TextField pickupCarrierSealNum;
	private HorizontalLayout pickupCarrierLayout;
	private Label pickupAddressLabel;
	private GridLayout pickupLocationContactLayout;
	private GridLayout pickupLocationDockTypeLayout;
	private GridLayout pickupCarrierContactLayout;
	private GridLayout pickupCarrierDriverDetailLayout;
	private InlineDateField expectedPickupDate;
	private GridLayout expectedPickupDateLayout;
	private InlineDateField expectedWhArrivalDate;
	private GridLayout expectedWhArrivalDateLayout;
	private HorizontalLayout dateTimeLayout;
	private ComboBox dropOffLocationOrganization;
	private ComboBox dropOffLocationAddress;
	private Label dropOffAddressLabel;
	private GridLayout dropOffLocationOrganizationLayout;
	private ComboBox dropOffLocationContact;
	private GridLayout dropOffLocationContactLayout;
	private ComboBox dropOffLocationDockType;
	private GridLayout dropOffLocationDockTypeLayout;
	private HorizontalLayout dropOffLocationLayout;
	private Button saveButton;
	private Button resetButton;
	private HorizontalLayout toolstripLeftButtonPanel;
	private HorizontalLayout toolStrip;

	private JPAContainer<Organization> pickupLocationOrganizationContainer;
	
	private void initContainers() {
		pickupLocationOrganizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
//		pickupLocationContactContainer = JPAContainerFactory.make(Contact.class, this.emf.createEntityManager());
	}
	
	public void initPickupLocation() {
		pickupLocationOrganization = new ComboBox();
		pickupLocationOrganization.setInputPrompt("Organization");
		pickupLocationOrganization.setContainerDataSource(pickupLocationOrganizationContainer);
		pickupLocationOrganization.setItemCaptionPropertyId("code");
		pickupLocationOrganization.setWidth("100%");
		pickupLocationOrganization.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (!pickupLocationAddress.isEnabled()) {
					pickupLocationAddress.setEnabled(true);
				}
			}
		});
		
		pickupLocationAddress = new ComboBox();
		pickupLocationAddress.setInputPrompt("Address");
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
		pickupLocationAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
		pickupLocationAddress.setWidth("100%");
		pickupLocationAddress.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;
			
			private Organization organization;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				organization = pickupLocationOrganizationContainer.getItem(pickupLocationOrganization.getValue()).getEntity();
				if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getAdHocAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getShippingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getReceivingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getBillingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getPickupAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					pickupAddressLabel.setValue(addressToXhtml(organization.getDeliveryAddress()));
				} else {
					pickupAddressLabel.setValue(addressToXhtml(organization.getMainAddress()));
				}
			}
		});
		
		pickupAddressLabel = new Label();
		pickupAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupAddressLabel.setValue("<i>No Address Selected</i>");
		
		Label pickupLocationOrganizationLayoutLabel = new Label();
		pickupLocationOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");
		
		Label pickupLocationOrganizationLabel = new Label();
		pickupLocationOrganizationLabel.setValue("Organization");
		
		Label pickupLocationAddressLabel = new Label();
		pickupLocationAddressLabel.setValue("Address");
		
		pickupLocationOrganizationLayout = new GridLayout(2, 4);
		pickupLocationOrganizationLayout.setWidth("100%");
		pickupLocationOrganizationLayout.setSpacing(true);
		pickupLocationOrganizationLayout.setMargin(false, true, true, true);
		pickupLocationOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganizationLayoutLabel, 0, 0, 1, 0);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganizationLabel);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganization);
		pickupLocationOrganizationLayout.addComponent(pickupLocationAddressLabel);
		pickupLocationOrganizationLayout.addComponent(pickupLocationAddress);
		pickupLocationOrganizationLayout.addComponent(pickupAddressLabel, 1, 3, 1, 3);
		pickupLocationOrganizationLayout.setComponentAlignment(pickupLocationOrganizationLabel, Alignment.MIDDLE_LEFT);
		pickupLocationOrganizationLayout.setComponentAlignment(pickupLocationAddressLabel, Alignment.MIDDLE_LEFT);
		
		pickupLocationContact = new ComboBox();
		pickupLocationContact.setInputPrompt("Contact");
		pickupLocationContact.addItem("Red");
		pickupLocationContact.addItem("Orange");
		pickupLocationContact.addItem("Blue");
		pickupLocationContact.addItem("Yellow");
		pickupLocationContact.addItem("Grey");
		pickupLocationContact.setWidth("100%");
		//TODO
		
		Label pickupLocationContactLabel = new Label();
		pickupLocationContactLabel.setValue("Contact");

		Label pickupLocationContactLayoutLabel = new Label();
		pickupLocationContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactLayoutLabel.setValue("<h3>Contact</h3>");
		
		pickupLocationContactLayout = new GridLayout(2, 2);
		pickupLocationContactLayout.setMargin(false,  true, true, true);
		pickupLocationContactLayout.setSpacing(true);
		pickupLocationContactLayout.setWidth("100%");
		pickupLocationContactLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationContactLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationContactLayout.addComponent(pickupLocationContactLayoutLabel, 0, 0, 1, 0);
		pickupLocationContactLayout.addComponent(pickupLocationContactLabel);
		pickupLocationContactLayout.addComponent(pickupLocationContact);
		pickupLocationContactLayout.setComponentAlignment(pickupLocationContactLabel, Alignment.MIDDLE_LEFT);
		
		pickupLocationDockType = new ComboBox();
		pickupLocationDockType.setInputPrompt("Dock Type");
		pickupLocationDockType.addItem("Red");
		pickupLocationDockType.addItem("Orange");
		pickupLocationDockType.addItem("Blue");
		pickupLocationDockType.addItem("Yellow");
		pickupLocationDockType.addItem("Grey");
		pickupLocationDockType.setWidth("100%");
		//TODO
		
		Label pickupLocationDockTypeLabel = new Label();
		pickupLocationDockTypeLabel.setValue("Dock Type");

		Label pickupLocationDockTypeLayoutLabel = new Label();
		pickupLocationDockTypeLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationDockTypeLayoutLabel.setValue("<h3>Dock Info</h3>");
		
		pickupLocationDockTypeLayout = new GridLayout(2, 2);
		pickupLocationDockTypeLayout.setMargin(false,  true, true, true);
		pickupLocationDockTypeLayout.setSpacing(true);
		pickupLocationDockTypeLayout.setWidth("100%");
		pickupLocationDockTypeLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationDockTypeLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationDockTypeLayout.addComponent(pickupLocationDockTypeLayoutLabel, 0, 0, 1, 0);
		pickupLocationDockTypeLayout.addComponent(pickupLocationDockTypeLabel);
		pickupLocationDockTypeLayout.addComponent(pickupLocationDockType);
		pickupLocationDockTypeLayout.setComponentAlignment(pickupLocationDockTypeLabel, Alignment.MIDDLE_LEFT);
		
		pickupLocationLayout = new HorizontalLayout();
		pickupLocationLayout.setMargin(true);
		pickupLocationLayout.setSizeFull();
		pickupLocationLayout.setSpacing(true);
		pickupLocationLayout.addComponent(pickupLocationOrganizationLayout);
		pickupLocationLayout.addComponent(pickupLocationContactLayout);
		pickupLocationLayout.addComponent(pickupLocationDockTypeLayout);
		pickupLocationLayout.setExpandRatio(pickupLocationOrganizationLayout, 0.33f);
		pickupLocationLayout.setExpandRatio(pickupLocationContactLayout, 0.33f);
		pickupLocationLayout.setExpandRatio(pickupLocationDockTypeLayout, 0.33f);
	}
	
	public void initPickupCarrier() {
		pickupCarrierOrganization = new ComboBox();
		pickupCarrierOrganization.setInputPrompt("Organization");
		pickupCarrierOrganization.addItem("Red");
		pickupCarrierOrganization.addItem("Orange");
		pickupCarrierOrganization.addItem("Blue");
		pickupCarrierOrganization.addItem("Yellow");
		pickupCarrierOrganization.addItem("Grey");
		pickupCarrierOrganization.setWidth("100%");
		//TODO
		
		pickupCarrierAddress = new ComboBox();
		pickupCarrierAddress.setInputPrompt("Address");
		pickupCarrierAddress.addItem("Red");
		pickupCarrierAddress.addItem("Orange");
		pickupCarrierAddress.addItem("Blue");
		pickupCarrierAddress.addItem("Yellow");
		pickupCarrierAddress.addItem("Grey");
		pickupCarrierAddress.setWidth("100%");
		//TODO
		
		pickupAddressLabel = new Label();
		pickupAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupAddressLabel.setValue("</br><b>220 N Elm Ave</b></br>Newtown, PA</br>18940");
		
		Label pickupCarrierOrganizationLayoutLabel = new Label();
		pickupCarrierOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");
		
		Label pickupCarrierOrganizationLabel = new Label();
		pickupCarrierOrganizationLabel.setValue("Organization");
		
		Label pickupCarrierAddressLabel = new Label();
		pickupCarrierAddressLabel.setValue("Address");
		
		pickupCarrierOrganizationLayout = new GridLayout(2, 4);
		pickupCarrierOrganizationLayout.setWidth("100%");
		pickupCarrierOrganizationLayout.setSpacing(true);
		pickupCarrierOrganizationLayout.setMargin(false, true, true, true);
		pickupCarrierOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		pickupCarrierOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganizationLayoutLabel, 0, 0, 1, 0);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganizationLabel);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganization);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierAddressLabel);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierAddress);
		pickupCarrierOrganizationLayout.addComponent(pickupAddressLabel, 1, 3, 1, 3);
		pickupCarrierOrganizationLayout.setComponentAlignment(pickupCarrierOrganizationLabel, Alignment.MIDDLE_LEFT);
		pickupCarrierOrganizationLayout.setComponentAlignment(pickupCarrierAddressLabel, Alignment.MIDDLE_LEFT);
		
		pickupCarrierContact = new ComboBox();
		pickupCarrierContact.setInputPrompt("Contact");
		pickupCarrierContact.addItem("Red");
		pickupCarrierContact.addItem("Orange");
		pickupCarrierContact.addItem("Blue");
		pickupCarrierContact.addItem("Yellow");
		pickupCarrierContact.addItem("Grey");
		pickupCarrierContact.setWidth("100%");
		//TODO
		
		Label pickupCarrierContactLabel = new Label();
		pickupCarrierContactLabel.setValue("Contact");

		Label pickupCarrierContactLayoutLabel = new Label();
		pickupCarrierContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierContactLayoutLabel.setValue("<h3>Contact</h3>");
		
		pickupCarrierContactLayout = new GridLayout(2, 2);
		pickupCarrierContactLayout.setMargin(false,  true, true, true);
		pickupCarrierContactLayout.setSpacing(true);
		pickupCarrierContactLayout.setWidth("100%");
		pickupCarrierContactLayout.setColumnExpandRatio(0, 0.3f);
		pickupCarrierContactLayout.setColumnExpandRatio(1, 0.7f);
		pickupCarrierContactLayout.addComponent(pickupCarrierContactLayoutLabel, 0, 0, 1, 0);
		pickupCarrierContactLayout.addComponent(pickupCarrierContactLabel);
		pickupCarrierContactLayout.addComponent(pickupCarrierContact);
		pickupCarrierContactLayout.setComponentAlignment(pickupCarrierContactLabel, Alignment.MIDDLE_LEFT);
		
		pickupCarrierDriverId = new TextField();
		pickupCarrierDriverId.setWidth("100%");
		pickupCarrierDriverId.setInputPrompt("Driver Id");
		
		pickupCarrierVehicleId = new TextField();
		pickupCarrierVehicleId.setWidth("100%");
		pickupCarrierVehicleId.setInputPrompt("Vehicle Id");
		
		pickupCarrierBolNum = new TextField();
		pickupCarrierBolNum.setWidth("100%");
		pickupCarrierBolNum.setInputPrompt("BOL #");
		
		pickupCarrierSealNum = new TextField();
		pickupCarrierSealNum.setWidth("100%");
		pickupCarrierSealNum.setInputPrompt("Seal #");
		
		Label pickupCarrierDriverIdLabel = new Label();
		pickupCarrierDriverIdLabel.setValue("Driver Id");
		
		Label pickupCarrierVehicleIdLabel = new Label();
		pickupCarrierVehicleIdLabel.setValue("Vehicle Id");
		
		Label pickupCarrierBolNumLabel = new Label();
		pickupCarrierBolNumLabel.setValue("BOL #");
		
		Label pickupCarrierSealNumLabel = new Label();
		pickupCarrierSealNumLabel.setValue("Seal #");

		Label pickupCarrierDriverDetailLayoutLabel = new Label();
		pickupCarrierDriverDetailLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierDriverDetailLayoutLabel.setValue("<h3>Driver Details</h3>");
		
		pickupCarrierDriverDetailLayout = new GridLayout(2, 5);
		pickupCarrierDriverDetailLayout.setMargin(false,  true, true, true);
		pickupCarrierDriverDetailLayout.setSpacing(true);
		pickupCarrierDriverDetailLayout.setWidth("100%");
		pickupCarrierDriverDetailLayout.setColumnExpandRatio(0, 0.3f);
		pickupCarrierDriverDetailLayout.setColumnExpandRatio(1, 0.7f);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierDriverDetailLayoutLabel, 0, 0, 1, 0);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierDriverIdLabel);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierDriverId);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierVehicleIdLabel);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierVehicleId);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierBolNumLabel);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierBolNum);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierSealNumLabel);
		pickupCarrierDriverDetailLayout.addComponent(pickupCarrierSealNum);
		pickupCarrierDriverDetailLayout.setComponentAlignment(pickupCarrierDriverIdLabel, Alignment.MIDDLE_LEFT);
		pickupCarrierDriverDetailLayout.setComponentAlignment(pickupCarrierVehicleIdLabel, Alignment.MIDDLE_LEFT);
		pickupCarrierDriverDetailLayout.setComponentAlignment(pickupCarrierBolNumLabel, Alignment.MIDDLE_LEFT);
		pickupCarrierDriverDetailLayout.setComponentAlignment(pickupCarrierSealNumLabel, Alignment.MIDDLE_LEFT);
		
		pickupCarrierLayout = new HorizontalLayout();
		pickupCarrierLayout.setMargin(true);
		pickupCarrierLayout.setSizeFull();
		pickupCarrierLayout.setSpacing(true);
		pickupCarrierLayout.addComponent(pickupCarrierOrganizationLayout);
		pickupCarrierLayout.addComponent(pickupCarrierContactLayout);
		pickupCarrierLayout.addComponent(pickupCarrierDriverDetailLayout);
		pickupCarrierLayout.setExpandRatio(pickupCarrierOrganizationLayout, 0.33f);
		pickupCarrierLayout.setExpandRatio(pickupCarrierContactLayout, 0.33f);
		pickupCarrierLayout.setExpandRatio(pickupCarrierDriverDetailLayout, 0.33f);
	}
	
	public void initDateTime() {
		expectedPickupDate = new InlineDateField();
//		expectedPickupDate.setInputPrompt("Expected Pickup Date & Time");
		expectedPickupDate.setWidth("100%");
		expectedPickupDate.setResolution(InlineDateField.RESOLUTION_MIN);
		
		Label expectedPickupDateLabel = new Label();
		expectedPickupDateLabel.setValue("Expected Pickup Date");
		
		Label expectedPickupDateLayoutLabel = new Label();
		expectedPickupDateLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		expectedPickupDateLayoutLabel.setValue("<h3>Pickup Date</h3>");
		
		expectedPickupDateLayout = new GridLayout(2, 2);
		expectedPickupDateLayout.setMargin(false,  true, true, true);
		expectedPickupDateLayout.setSpacing(true);
		expectedPickupDateLayout.setWidth("100%");
		expectedPickupDateLayout.setColumnExpandRatio(0, 0.4f);
		expectedPickupDateLayout.setColumnExpandRatio(1, 0.6f);
		expectedPickupDateLayout.addComponent(expectedPickupDateLayoutLabel, 0, 0, 1, 0);
		expectedPickupDateLayout.addComponent(expectedPickupDateLabel);
		expectedPickupDateLayout.addComponent(expectedPickupDate);
		expectedPickupDateLayout.setComponentAlignment(expectedPickupDateLabel, Alignment.TOP_LEFT);
		
		Label expectedWhArrivalDateLabel = new Label();
		expectedWhArrivalDateLabel.setValue("Expected W/H Arrival Date & Time");
		
		Label expectedWhArrivalDateLayoutLabel = new Label();
		expectedWhArrivalDateLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		expectedWhArrivalDateLayoutLabel.setValue("<h3>W/H Arrival Date</h3>");
		
		expectedWhArrivalDate = new InlineDateField();
//		expectedWhArrivalDate.setInputPrompt("Expected W/H Arrival Date & Time");
		expectedWhArrivalDate.setWidth("100%");
		expectedWhArrivalDate.setResolution(InlineDateField.RESOLUTION_MIN);
		
		expectedWhArrivalDateLayout = new GridLayout(2, 2);
		expectedWhArrivalDateLayout.setMargin(false,  true, true, true);
		expectedWhArrivalDateLayout.setSpacing(true);
		expectedWhArrivalDateLayout.setWidth("100%");
		expectedWhArrivalDateLayout.setColumnExpandRatio(0, 0.4f);
		expectedWhArrivalDateLayout.setColumnExpandRatio(1, 0.6f);
		expectedWhArrivalDateLayout.addComponent(expectedWhArrivalDateLayoutLabel, 0, 0, 1, 0);
		expectedWhArrivalDateLayout.addComponent(expectedWhArrivalDateLabel);
		expectedWhArrivalDateLayout.addComponent(expectedWhArrivalDate);
		expectedWhArrivalDateLayout.setComponentAlignment(expectedWhArrivalDateLabel, Alignment.TOP_LEFT);
		
		dateTimeLayout = new HorizontalLayout();
		dateTimeLayout.setMargin(true);
		dateTimeLayout.setWidth("75%");
		dateTimeLayout.setHeight("100%");
		dateTimeLayout.setSpacing(true);
		dateTimeLayout.addComponent(expectedPickupDateLayout);
		dateTimeLayout.addComponent(expectedWhArrivalDateLayout);
		dateTimeLayout.setExpandRatio(expectedPickupDateLayout, 0.5f);
		dateTimeLayout.setExpandRatio(expectedWhArrivalDateLayout, 0.5f);
	}
	
	public void initDropOffLocation() {
		dropOffLocationOrganization = new ComboBox();
		dropOffLocationOrganization.setInputPrompt("Organization");
		dropOffLocationOrganization.addItem("Red");
		dropOffLocationOrganization.addItem("Orange");
		dropOffLocationOrganization.addItem("Blue");
		dropOffLocationOrganization.addItem("Yellow");
		dropOffLocationOrganization.addItem("Grey");
		dropOffLocationOrganization.setWidth("100%");
		//TODO
		
		dropOffLocationAddress = new ComboBox();
		dropOffLocationAddress.setInputPrompt("Address");
		dropOffLocationAddress.addItem("Red");
		dropOffLocationAddress.addItem("Orange");
		dropOffLocationAddress.addItem("Blue");
		dropOffLocationAddress.addItem("Yellow");
		dropOffLocationAddress.addItem("Grey");
		dropOffLocationAddress.setWidth("100%");
		//TODO
		
		dropOffAddressLabel = new Label();
		dropOffAddressLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffAddressLabel.setValue("</br><b>220 N Elm Ave</b></br>Newtown, PA</br>18940");
		
		Label dropOffLocationOrganizationLayoutLabel = new Label();
		dropOffLocationOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");
		
		Label dropOffLocationOrganizationLabel = new Label();
		dropOffLocationOrganizationLabel.setValue("Organization");
		
		Label dropOffLocationAddressLabel = new Label();
		dropOffLocationAddressLabel.setValue("Address");
		
		dropOffLocationOrganizationLayout = new GridLayout(2, 4);
		dropOffLocationOrganizationLayout.setWidth("100%");
		dropOffLocationOrganizationLayout.setSpacing(true);
		dropOffLocationOrganizationLayout.setMargin(false, true, true, true);
		dropOffLocationOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganizationLayoutLabel, 0, 0, 1, 0);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganizationLabel);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganization);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationAddressLabel);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationAddress);
		dropOffLocationOrganizationLayout.addComponent(dropOffAddressLabel, 1, 3, 1, 3);
		dropOffLocationOrganizationLayout.setComponentAlignment(dropOffLocationOrganizationLabel, Alignment.MIDDLE_LEFT);
		dropOffLocationOrganizationLayout.setComponentAlignment(dropOffLocationAddressLabel, Alignment.MIDDLE_LEFT);
		
		dropOffLocationContact = new ComboBox();
		dropOffLocationContact.setInputPrompt("Contact");
		dropOffLocationContact.addItem("Red");
		dropOffLocationContact.addItem("Orange");
		dropOffLocationContact.addItem("Blue");
		dropOffLocationContact.addItem("Yellow");
		dropOffLocationContact.addItem("Grey");
		dropOffLocationContact.setWidth("100%");
		//TODO
		
		Label dropOffLocationContactLabel = new Label();
		dropOffLocationContactLabel.setValue("Contact");

		Label dropOffLocationContactLayoutLabel = new Label();
		dropOffLocationContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactLayoutLabel.setValue("<h3>Contact</h3>");
		
		dropOffLocationContactLayout = new GridLayout(2, 2);
		dropOffLocationContactLayout.setMargin(false,  true, true, true);
		dropOffLocationContactLayout.setSpacing(true);
		dropOffLocationContactLayout.setWidth("100%");
		dropOffLocationContactLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationContactLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationContactLayout.addComponent(dropOffLocationContactLayoutLabel, 0, 0, 1, 0);
		dropOffLocationContactLayout.addComponent(dropOffLocationContactLabel);
		dropOffLocationContactLayout.addComponent(dropOffLocationContact);
		dropOffLocationContactLayout.setComponentAlignment(dropOffLocationContactLabel, Alignment.MIDDLE_LEFT);
		
		dropOffLocationDockType = new ComboBox();
		dropOffLocationDockType.setInputPrompt("Dock Type");
		dropOffLocationDockType.addItem("Red");
		dropOffLocationDockType.addItem("Orange");
		dropOffLocationDockType.addItem("Blue");
		dropOffLocationDockType.addItem("Yellow");
		dropOffLocationDockType.addItem("Grey");
		dropOffLocationDockType.setWidth("100%");
		//TODO
		
		Label dropOffLocationDockTypeLabel = new Label();
		dropOffLocationDockTypeLabel.setValue("Dock Type");

		Label dropOffLocationDockTypeLayoutLabel = new Label();
		dropOffLocationDockTypeLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationDockTypeLayoutLabel.setValue("<h3>Dock Info</h3>");
		
		dropOffLocationDockTypeLayout = new GridLayout(2, 2);
		dropOffLocationDockTypeLayout.setMargin(false,  true, true, true);
		dropOffLocationDockTypeLayout.setSpacing(true);
		dropOffLocationDockTypeLayout.setWidth("100%");
		dropOffLocationDockTypeLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationDockTypeLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationDockTypeLayout.addComponent(dropOffLocationDockTypeLayoutLabel, 0, 0, 1, 0);
		dropOffLocationDockTypeLayout.addComponent(dropOffLocationDockTypeLabel);
		dropOffLocationDockTypeLayout.addComponent(dropOffLocationDockType);
		dropOffLocationDockTypeLayout.setComponentAlignment(dropOffLocationDockTypeLabel, Alignment.MIDDLE_LEFT);
		
		dropOffLocationLayout = new HorizontalLayout();
		dropOffLocationLayout.setMargin(true);
		dropOffLocationLayout.setSizeFull();
		dropOffLocationLayout.setSpacing(true);
		dropOffLocationLayout.addComponent(dropOffLocationOrganizationLayout);
		dropOffLocationLayout.addComponent(dropOffLocationContactLayout);
		dropOffLocationLayout.addComponent(dropOffLocationDockTypeLayout);
		dropOffLocationLayout.setExpandRatio(dropOffLocationOrganizationLayout, 0.33f);
		dropOffLocationLayout.setExpandRatio(dropOffLocationContactLayout, 0.33f);
		dropOffLocationLayout.setExpandRatio(dropOffLocationDockTypeLayout, 0.33f);
	}
	
	public void initEntityTabSheet() {
		initPickupLocation();
		initPickupCarrier();
		initDateTime();
		initDropOffLocation();
		
		entityTabSheet = new TabSheet();
		entityTabSheet.setWidth("100%");
		entityTabSheet.setHeight(VIEW_HEIGHT);
		entityTabSheet.addTab(pickupLocationLayout, "Pick-Up Location");
		entityTabSheet.addTab(pickupCarrierLayout, "Pick-Up Carrier");
		entityTabSheet.addTab(dateTimeLayout, "Dates/Times");
		entityTabSheet.addTab(dropOffLocationLayout, "Drop-Off Location");
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
	
	private String addressToXhtml(Address address) {
		StringBuffer xhtml = new StringBuffer();
		xhtml.append("<b>");
		xhtml.append(address.getStreet1());
		xhtml.append("</b></br>");
		xhtml.append(address.getUnloco().getPortCity());
		xhtml.append(", ");
		xhtml.append(address.getCountryState().getName());
		xhtml.append("</br>");
		xhtml.append(address.getZipCode());
		xhtml.append(" ");
		xhtml.append(address.getCountry());
		xhtml.append("</br>");
		return xhtml.toString();
	}
	
	@Override
	public String getTaskName() {
		// TODO Auto-generated method stub
		return "AddAsnLocalTrans";
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
		return "Add Local Transportation";
	}

}
