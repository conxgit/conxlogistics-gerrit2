package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.app.whse.domain.docktype.DockType;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedEventHandler;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.constants.AddressCustomCONSTANTS;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.organization.Contact;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddAsnLocalTransPage extends PageFlowPage {
	private static final String VIEW_HEIGHT = "100%";

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
	private Label pickupCarrierAddressLabel;
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
	private Label pickupLocationContactAddressLabel;

	private JPAContainer<Organization> pickupLocationOrganizationContainer;
	private JPAContainer<Organization> pickupCarrierOrganizationContainer;
	private JPAContainer<Organization> dropOffLocationOrganizationContainer;

	private Organization currentPickupLocationOrg = null;
	private Label pickupCarrierContactAddressLabel;
	private Organization currentPickupCarrierOrg;
	private Label dropOffLocationContactAddressLabel;
	private Label dropOffLocationAddressLabel;
	private Organization currentDropOffLocationOrg;
	private EntityManagerFactory emf;
	private Label pickupLocationAddressLabel;
	private Button cancelButton;
	private Map<String, Object> state;
	private JPAContainer<DockType> dockTypeContainer;

	private void initContainers() {
		pickupLocationOrganizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
		pickupCarrierOrganizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
		dropOffLocationOrganizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
		dockTypeContainer = JPAContainerFactory.make(DockType.class, this.emf.createEntityManager());
	}
	
	private boolean validate() {
		StringBuffer message = new StringBuffer();
		if (expectedPickupDate.getValue() == null) {
			message.append("</br>Expected Pick-Up Date & Time were not provided.");
		}
		if (expectedWhArrivalDate.getValue() == null) {
			message.append("</br>Expected Warehouse Arrival Date & Time were not provided.");
		}

		if (message.length() != 0) {
			showWarningNotification("Could Not Save Local Transportation", message.toString());
			return false;
		} else {
			return true;
		}
	}

	public void initPickupLocation() {
		pickupLocationOrganization = new ComboBox();
		pickupLocationOrganization.setInputPrompt("Organization");
		pickupLocationOrganization.setContainerDataSource(pickupLocationOrganizationContainer);
		pickupLocationOrganization.setItemCaptionPropertyId("code");
		pickupLocationOrganization.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		pickupLocationOrganization.setImmediate(true);
		pickupLocationOrganization.setWidth("100%");
		pickupLocationOrganization.setValue(pickupLocationOrganizationContainer.getIdByIndex(0));
		pickupLocationOrganization.setNullSelectionAllowed(false);
		currentPickupLocationOrg = pickupLocationOrganizationContainer.getItem(pickupLocationOrganizationContainer.getIdByIndex(0)).getEntity();
		pickupLocationOrganization.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = -7077062557675605070L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updatePickupLocationFields(pickupLocationOrganization.getValue());
			}
		});

		pickupLocationAddress = new ComboBox();
		pickupLocationAddress.setInputPrompt("Address");
		pickupLocationAddress.setEnabled(false);
		pickupLocationAddress.setNullSelectionAllowed(false);
		pickupLocationAddress.setWidth("100%");
		pickupLocationAddress.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getAdHocAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getShippingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getReceivingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getBillingAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getPickupAddress()));
				} else if (pickupLocationAddress.getValue().equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getDeliveryAddress()));
				} else {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getMainAddress()));
				}
			}
		});

		pickupLocationAddressLabel = new Label();
		pickupLocationAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationAddressLabel.setValue("<i>No Address Selected</i>");

		if (currentPickupLocationOrg.getAdHocAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			pickupLocationAddress.setEnabled(true);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getAdHocAddress()));
		} else if (currentPickupLocationOrg.getShippingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getShippingAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		} else if (currentPickupLocationOrg.getReceivingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getReceivingAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		} else if (currentPickupLocationOrg.getBillingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getBillingAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.BILLING_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		} else if (currentPickupLocationOrg.getPickupAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getPickupAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		} else if (currentPickupLocationOrg.getDeliveryAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getDeliveryAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		} else if (currentPickupLocationOrg.getMainAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getMainAddress()));
			pickupLocationAddress.setValue(AddressCustomCONSTANTS.MAIN_ADDRESS);
			pickupLocationAddress.setEnabled(true);
		}

		Label pickupLocationOrganizationLayoutLabel = new Label();
		pickupLocationOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");

		Label pickupLocationOrganizationLabel = new Label();
		pickupLocationOrganizationLabel.setValue("Organization");

		Label pickupLocationAddressFieldLabel = new Label();
		pickupLocationAddressFieldLabel.setValue("Address");

		pickupLocationOrganizationLayout = new GridLayout(2, 4);
		pickupLocationOrganizationLayout.setWidth("100%");
		pickupLocationOrganizationLayout.setSpacing(true);
		pickupLocationOrganizationLayout.setMargin(false, true, true, true);
		pickupLocationOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganizationLayoutLabel, 0, 0, 1, 0);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganizationLabel);
		pickupLocationOrganizationLayout.addComponent(pickupLocationOrganization);
		pickupLocationOrganizationLayout.addComponent(pickupLocationAddressFieldLabel);
		pickupLocationOrganizationLayout.addComponent(pickupLocationAddress);
		pickupLocationOrganizationLayout.addComponent(pickupLocationAddressLabel, 1, 3, 1, 3);
		pickupLocationOrganizationLayout.setComponentAlignment(pickupLocationOrganizationLabel, Alignment.MIDDLE_LEFT);
		pickupLocationOrganizationLayout.setComponentAlignment(pickupLocationAddressFieldLabel, Alignment.MIDDLE_LEFT);

		pickupLocationContact = new ComboBox();
		pickupLocationContact.setEnabled(false);
		pickupLocationContact.setInputPrompt("Contact");
		pickupLocationContact.setWidth("100%");
		String name = currentPickupLocationOrg.getMainContact().getFirstName() + " " + currentPickupLocationOrg.getMainContact().getLastName();
		pickupLocationContact.addItem(name);
		pickupLocationContact.setNullSelectionAllowed(false);
		pickupLocationContact.setEnabled(currentPickupLocationOrg != null);
		pickupLocationContact.setValue(name);
		pickupLocationContact.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 779001014178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				pickupLocationContactAddressLabel.setValue(contactToXhtml(currentPickupLocationOrg.getMainContact()));
			}
		});

		pickupLocationContactAddressLabel = new Label();
		pickupLocationContactAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactAddressLabel.setValue(contactToXhtml(currentPickupLocationOrg.getMainContact()));

		Label pickupLocationContactLabel = new Label();
		pickupLocationContactLabel.setValue("Contact");

		Label pickupLocationContactLayoutLabel = new Label();
		pickupLocationContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationContactLayoutLabel.setValue("<h3>Contact</h3>");

		pickupLocationContactLayout = new GridLayout(2, 3);
		pickupLocationContactLayout.setMargin(false,  true, true, true);
		pickupLocationContactLayout.setSpacing(true);
		pickupLocationContactLayout.setWidth("100%");
		pickupLocationContactLayout.setColumnExpandRatio(0, 0.3f);
		pickupLocationContactLayout.setColumnExpandRatio(1, 0.7f);
		pickupLocationContactLayout.addComponent(pickupLocationContactLayoutLabel, 0, 0, 1, 0);
		pickupLocationContactLayout.addComponent(pickupLocationContactLabel);
		pickupLocationContactLayout.addComponent(pickupLocationContact);
		pickupLocationContactLayout.addComponent(pickupLocationContactAddressLabel, 1, 2, 1, 2);
		pickupLocationContactLayout.setComponentAlignment(pickupLocationContactLabel, Alignment.MIDDLE_LEFT);

		pickupLocationDockType = new ComboBox();
		pickupLocationDockType.setInputPrompt("Dock Type");
		pickupLocationDockType.setWidth("100%");
		pickupLocationDockType.setNullSelectionAllowed(false);
		pickupLocationDockType.setItemCaptionPropertyId("name");
		pickupLocationDockType.setContainerDataSource(dockTypeContainer);

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
		pickupCarrierOrganization.setContainerDataSource(pickupCarrierOrganizationContainer);
		pickupCarrierOrganization.setItemCaptionPropertyId("code");
		pickupCarrierOrganization.setWidth("100%");
		pickupCarrierOrganization.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		pickupCarrierOrganization.setImmediate(true);
		pickupCarrierOrganization.setValue(pickupCarrierOrganizationContainer.getIdByIndex(0));
		pickupCarrierOrganization.setNullSelectionAllowed(false);
		currentPickupCarrierOrg = pickupCarrierOrganizationContainer.getItem(pickupCarrierOrganizationContainer.getIdByIndex(0)).getEntity();
		pickupCarrierOrganization.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updatePickupCarrierFields(pickupCarrierOrganization.getValue());
			}
		});

		pickupCarrierAddress = new ComboBox();
		pickupCarrierAddress.setInputPrompt("Address");
		pickupCarrierAddress.setEnabled(false);
		pickupCarrierAddress.setNullSelectionAllowed(false);
		pickupCarrierAddress.setWidth("100%");
		pickupCarrierAddress.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getAdHocAddress()));
				} else if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getShippingAddress()));
				} else if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getReceivingAddress()));
				} else if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getBillingAddress()));
				} else if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getPickupAddress()));
				} else if (pickupCarrierAddress.getValue().equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getDeliveryAddress()));
				} else {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getMainAddress()));
				}
			}
		});

		pickupCarrierAddressLabel = new Label();
		pickupCarrierAddressLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierAddressLabel.setValue("<i>No Address Selected</i>");

		if (currentPickupCarrierOrg.getAdHocAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getAdHocAddress()));
		} else if (currentPickupCarrierOrg.getShippingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getShippingAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		} else if (currentPickupCarrierOrg.getReceivingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getReceivingAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		} else if (currentPickupCarrierOrg.getBillingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getBillingAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.BILLING_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		} else if (currentPickupCarrierOrg.getPickupAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getPickupAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		} else if (currentPickupCarrierOrg.getDeliveryAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getDeliveryAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		} else if (currentPickupCarrierOrg.getMainAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getMainAddress()));
			pickupCarrierAddress.setValue(AddressCustomCONSTANTS.MAIN_ADDRESS);
			pickupCarrierAddress.setEnabled(true);
		}

		Label pickupCarrierOrganizationLayoutLabel = new Label();
		pickupCarrierOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");

		Label pickupCarrierOrganizationLabel = new Label();
		pickupCarrierOrganizationLabel.setValue("Organization");

		Label pickupCarrierAddressFieldLabel = new Label();
		pickupCarrierAddressFieldLabel.setValue("Address");

		pickupCarrierOrganizationLayout = new GridLayout(2, 4);
		pickupCarrierOrganizationLayout.setWidth("100%");
		pickupCarrierOrganizationLayout.setSpacing(true);
		pickupCarrierOrganizationLayout.setMargin(false, true, true, true);
		pickupCarrierOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		pickupCarrierOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganizationLayoutLabel, 0, 0, 1, 0);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganizationLabel);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierOrganization);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierAddressFieldLabel);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierAddress);
		pickupCarrierOrganizationLayout.addComponent(pickupCarrierAddressLabel, 1, 3, 1, 3);
		pickupCarrierOrganizationLayout.setComponentAlignment(pickupCarrierOrganizationLabel, Alignment.MIDDLE_LEFT);
		pickupCarrierOrganizationLayout.setComponentAlignment(pickupCarrierAddressFieldLabel, Alignment.MIDDLE_LEFT);

		pickupCarrierContact = new ComboBox();
		pickupCarrierContact.setEnabled(false);
		pickupCarrierContact.setInputPrompt("Contact");
		pickupCarrierContact.setWidth("100%");
		String name = currentPickupCarrierOrg.getMainContact().getFirstName() + " " + currentPickupCarrierOrg.getMainContact().getLastName();
		pickupCarrierContact.addItem(name);
		pickupCarrierContact.setNullSelectionAllowed(false);
		pickupCarrierContact.setEnabled(currentPickupCarrierOrg != null);
		pickupCarrierContact.setValue(name);
		pickupCarrierContact.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 779001014178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				pickupCarrierContactAddressLabel.setValue(contactToXhtml(currentPickupCarrierOrg.getMainContact()));
			}
		});

		pickupCarrierContactAddressLabel = new Label();
		pickupCarrierContactAddressLabel.setContentMode(Label.CONTENT_XHTML);
		//		pickupCarrierContactAddressLabel.setValue("<i>No Contact Selected</i>");
		pickupCarrierContactAddressLabel.setValue(contactToXhtml(currentPickupCarrierOrg.getMainContact()));

		Label pickupCarrierContactLabel = new Label();
		pickupCarrierContactLabel.setValue("Contact");

		Label pickupCarrierContactLayoutLabel = new Label();
		pickupCarrierContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		pickupCarrierContactLayoutLabel.setValue("<h3>Contact</h3>");

		pickupCarrierContactLayout = new GridLayout(2, 3);
		pickupCarrierContactLayout.setMargin(false,  true, true, true);
		pickupCarrierContactLayout.setSpacing(true);
		pickupCarrierContactLayout.setWidth("100%");
		pickupCarrierContactLayout.setColumnExpandRatio(0, 0.3f);
		pickupCarrierContactLayout.setColumnExpandRatio(1, 0.7f);
		pickupCarrierContactLayout.addComponent(pickupCarrierContactLayoutLabel, 0, 0, 1, 0);
		pickupCarrierContactLayout.addComponent(pickupCarrierContactLabel);
		pickupCarrierContactLayout.addComponent(pickupCarrierContact);
		pickupCarrierContactLayout.addComponent(pickupCarrierContactAddressLabel, 1, 2, 1, 2);
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
		dropOffLocationOrganization.setContainerDataSource(dropOffLocationOrganizationContainer);
		dropOffLocationOrganization.setItemCaptionPropertyId("code");
		dropOffLocationOrganization.setWidth("100%");
		dropOffLocationOrganization.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		dropOffLocationOrganization.setImmediate(true);
		dropOffLocationOrganization.setValue(dropOffLocationOrganizationContainer.getIdByIndex(0));
		dropOffLocationOrganization.setNullSelectionAllowed(false);
		currentDropOffLocationOrg = dropOffLocationOrganizationContainer.getItem(dropOffLocationOrganizationContainer.getIdByIndex(0)).getEntity();
		dropOffLocationOrganization.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateDropOffLocationFields(dropOffLocationOrganization.getValue());

			}
		});

		dropOffLocationAddress = new ComboBox();
		dropOffLocationAddress.setInputPrompt("Address");
		dropOffLocationAddress.setEnabled(false);
		dropOffLocationAddress.setNullSelectionAllowed(false);
		dropOffLocationAddress.setWidth("100%");
		dropOffLocationAddress.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 7796995334178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getAdHocAddress()));
				} else if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getShippingAddress()));
				} else if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getReceivingAddress()));
				} else if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getBillingAddress()));
				} else if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getPickupAddress()));
				} else if (dropOffLocationAddress.getValue().equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getDeliveryAddress()));
				} else {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getMainAddress()));
				}
			}
		});

		dropOffLocationAddressLabel = new Label();
		dropOffLocationAddressLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationAddressLabel.setValue("<i>No Address Selected</i>");

		if (currentDropOffLocationOrg.getAdHocAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getAdHocAddress()));
		} else if (currentDropOffLocationOrg.getShippingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getShippingAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		} else if (currentDropOffLocationOrg.getReceivingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getReceivingAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		} else if (currentDropOffLocationOrg.getBillingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getBillingAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.BILLING_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		} else if (currentDropOffLocationOrg.getPickupAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getPickupAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		} else if (currentDropOffLocationOrg.getDeliveryAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getDeliveryAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		} else if (currentDropOffLocationOrg.getMainAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getMainAddress()));
			dropOffLocationAddress.setValue(AddressCustomCONSTANTS.MAIN_ADDRESS);
			dropOffLocationAddress.setEnabled(true);
		}

		Label dropOffLocationOrganizationLayoutLabel = new Label();
		dropOffLocationOrganizationLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationOrganizationLayoutLabel.setValue("<h3>Address Details</h3>");

		Label dropOffLocationOrganizationLabel = new Label();
		dropOffLocationOrganizationLabel.setValue("Organization");

		Label dropOffLocationAddressFieldLabel = new Label();
		dropOffLocationAddressFieldLabel.setValue("Address");

		dropOffLocationOrganizationLayout = new GridLayout(2, 4);
		dropOffLocationOrganizationLayout.setWidth("100%");
		dropOffLocationOrganizationLayout.setSpacing(true);
		dropOffLocationOrganizationLayout.setMargin(false, true, true, true);
		dropOffLocationOrganizationLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationOrganizationLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganizationLayoutLabel, 0, 0, 1, 0);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganizationLabel);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationOrganization);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationAddressFieldLabel);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationAddress);
		dropOffLocationOrganizationLayout.addComponent(dropOffLocationAddressLabel, 1, 3, 1, 3);
		dropOffLocationOrganizationLayout.setComponentAlignment(dropOffLocationOrganizationLabel, Alignment.MIDDLE_LEFT);
		dropOffLocationOrganizationLayout.setComponentAlignment(dropOffLocationAddressLabel, Alignment.MIDDLE_LEFT);

		dropOffLocationContact = new ComboBox();
		dropOffLocationContact.setEnabled(false);
		dropOffLocationContact.setInputPrompt("Contact");
		dropOffLocationContact.setWidth("100%");
		String name = currentDropOffLocationOrg.getMainContact().getFirstName() + " " + currentDropOffLocationOrg.getMainContact().getLastName();
		dropOffLocationContact.addItem(name);
		dropOffLocationContact.setNullSelectionAllowed(false);
		dropOffLocationContact.setEnabled(currentDropOffLocationOrg != null);
		dropOffLocationContact.setValue(name);
		dropOffLocationContact.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 779001014178965L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				dropOffLocationContactAddressLabel.setValue(contactToXhtml(currentDropOffLocationOrg.getMainContact()));
			}
		});

		dropOffLocationContactAddressLabel = new Label();
		dropOffLocationContactAddressLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactAddressLabel.setValue(contactToXhtml(currentDropOffLocationOrg.getMainContact()));

		Label dropOffLocationContactLabel = new Label();
		dropOffLocationContactLabel.setValue("Contact");

		Label dropOffLocationContactLayoutLabel = new Label();
		dropOffLocationContactLayoutLabel.setContentMode(Label.CONTENT_XHTML);
		dropOffLocationContactLayoutLabel.setValue("<h3>Contact</h3>");

		dropOffLocationContactLayout = new GridLayout(2, 3);
		dropOffLocationContactLayout.setMargin(false,  true, true, true);
		dropOffLocationContactLayout.setSpacing(true);
		dropOffLocationContactLayout.setWidth("100%");
		dropOffLocationContactLayout.setColumnExpandRatio(0, 0.3f);
		dropOffLocationContactLayout.setColumnExpandRatio(1, 0.7f);
		dropOffLocationContactLayout.addComponent(dropOffLocationContactLayoutLabel, 0, 0, 1, 0);
		dropOffLocationContactLayout.addComponent(dropOffLocationContactLabel);
		dropOffLocationContactLayout.addComponent(dropOffLocationContact);
		dropOffLocationContactLayout.addComponent(dropOffLocationContactAddressLabel, 1, 2, 1, 2);
		dropOffLocationContactLayout.setComponentAlignment(dropOffLocationContactLabel, Alignment.MIDDLE_LEFT);

		dropOffLocationDockType = new ComboBox();
		dropOffLocationDockType.setInputPrompt("Dock Type");
		dropOffLocationDockType.setWidth("100%");
		dropOffLocationDockType.setNullSelectionAllowed(false);
		dropOffLocationDockType.setItemCaptionPropertyId("name");
		dropOffLocationDockType.setContainerDataSource(dockTypeContainer);

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
		saveButton.setEnabled(true);
		saveButton.setWidth("100%");
		saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 500312301678L;

			public void buttonClick(ClickEvent event) {
				if (validate()) {
					if (wizard != null) {
						wizard.setNextEnabled(true);
						saveButton.setEnabled(false);
					}
				}
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

	private String contactToXhtml(Contact contact) {
		StringBuffer xhtml = new StringBuffer();
		xhtml.append("<b>");
		xhtml.append(contact.getFirstName());
		xhtml.append(" ");
		xhtml.append(contact.getLastName());
		xhtml.append("</b></br>Office Phone: ");
		xhtml.append(contact.getOfficePhoneNumber());
		xhtml.append("</br>Cell Phone: ");
		xhtml.append(contact.getCellPhoneNumber());
		xhtml.append("</br>Email: ");
		xhtml.append(contact.getEmail());
		xhtml.append("</br>");
		return xhtml.toString();
	}

	private void updatePickupLocationFields(Object id) {
		currentPickupLocationOrg = pickupLocationOrganizationContainer.getItem(id).getEntity();
		String name = currentPickupLocationOrg.getMainContact().getFirstName() + " " + currentPickupLocationOrg.getMainContact().getLastName();
		pickupLocationContact.removeAllItems();
		pickupLocationContact.addItem(name);
		pickupLocationContact.setValue(name);
		pickupLocationContact.setEnabled(true);
		pickupLocationContactAddressLabel.setValue(contactToXhtml(currentPickupLocationOrg.getMainContact()));

		try {
			pickupLocationAddress.removeAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectedAddress = null;
		int addressCount = 0;
		if (currentPickupLocationOrg.getAdHocAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.ADHOC_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getShippingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.SHIPPING_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getReceivingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.RECEIVING_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getBillingAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.BILLING_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getPickupAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.PICKUP_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getDeliveryAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.DELIVERY_ADDRESS;
			addressCount++;
		} else if (currentPickupLocationOrg.getMainAddress() != null) {
			pickupLocationAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.MAIN_ADDRESS;
			addressCount++;
		}
		if (selectedAddress != null) {
			if (addressCount > 1) {
				pickupLocationAddress.setEnabled(true);
			}
			pickupLocationAddress.setValue(selectedAddress);
			if (addressCount == 1) {
				if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getAdHocAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getShippingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getReceivingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getBillingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getPickupAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getDeliveryAddress()));
				} else {
					pickupLocationAddressLabel.setValue(addressToXhtml(currentPickupLocationOrg.getMainAddress()));
				}
			}
		}
	}

	private void updatePickupCarrierFields(Object id) {
		currentPickupCarrierOrg = pickupCarrierOrganizationContainer.getItem(id).getEntity();
		String name = currentPickupCarrierOrg.getMainContact().getFirstName() + " " + currentPickupCarrierOrg.getMainContact().getLastName();
		pickupCarrierContact.removeAllItems();
		pickupCarrierContact.addItem(name);
		pickupCarrierContact.setValue(name);
		pickupCarrierContact.setEnabled(true);
		pickupCarrierContactAddressLabel.setValue(contactToXhtml(currentPickupCarrierOrg.getMainContact()));

		try {
			pickupCarrierAddress.removeAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectedAddress = null;
		int addressCount = 0;
		if (currentPickupCarrierOrg.getAdHocAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.ADHOC_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getShippingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.SHIPPING_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getReceivingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.RECEIVING_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getBillingAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.BILLING_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getPickupAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.PICKUP_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getDeliveryAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.DELIVERY_ADDRESS;
			addressCount++;
		} else if (currentPickupCarrierOrg.getMainAddress() != null) {
			pickupCarrierAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.MAIN_ADDRESS;
			addressCount++;
		}
		if (selectedAddress != null) {
			if (addressCount > 1) {
				pickupCarrierAddress.setEnabled(true);
			}
			pickupCarrierAddress.setValue(selectedAddress);
			if (addressCount == 1) {
				if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getAdHocAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getShippingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getReceivingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getBillingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getPickupAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getDeliveryAddress()));
				} else {
					pickupCarrierAddressLabel.setValue(addressToXhtml(currentPickupCarrierOrg.getMainAddress()));
				}
			}
		}
	}

	private void updateDropOffLocationFields(Object id) {
		currentDropOffLocationOrg = dropOffLocationOrganizationContainer.getItem(id).getEntity();
		String name = currentDropOffLocationOrg.getMainContact().getFirstName() + " " + currentDropOffLocationOrg.getMainContact().getLastName();
		dropOffLocationContact.removeAllItems();
		dropOffLocationContact.addItem(name);
		dropOffLocationContact.setValue(name);
		dropOffLocationContact.setEnabled(true);
		dropOffLocationContactAddressLabel.setValue(contactToXhtml(currentDropOffLocationOrg.getMainContact()));

		try {
			dropOffLocationAddress.removeAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectedAddress = null;
		int addressCount = 0;
		if (currentDropOffLocationOrg.getAdHocAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.ADHOC_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.ADHOC_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getShippingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.SHIPPING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.SHIPPING_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getReceivingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.RECEIVING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.RECEIVING_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getBillingAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.BILLING_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.BILLING_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getPickupAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.PICKUP_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.PICKUP_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getDeliveryAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.DELIVERY_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.DELIVERY_ADDRESS;
			addressCount++;
		} else if (currentDropOffLocationOrg.getMainAddress() != null) {
			dropOffLocationAddress.addItem(AddressCustomCONSTANTS.MAIN_ADDRESS);
			selectedAddress = AddressCustomCONSTANTS.MAIN_ADDRESS;
			addressCount++;
		}
		if (selectedAddress != null) {
			if (addressCount > 1) {
				dropOffLocationAddress.setEnabled(true);
			}
			dropOffLocationAddress.setValue(selectedAddress);
			if (addressCount == 1) {
				if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getAdHocAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getShippingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getReceivingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getBillingAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getPickupAddress()));
				} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getDeliveryAddress()));
				} else {
					dropOffLocationAddressLabel.setValue(addressToXhtml(currentDropOffLocationOrg.getMainAddress()));
				}
			}
		}
	}

	@Override
	public String getTaskName() {
		return "AddAsnLocalTrans";
	}

	@Override
	public String getCaption() {
		return "Add Local Transportation";
	}

	@Override
	public void initialize(EntityManagerFactory emf, PlatformTransactionManager ptm,
			IPageFlowPageChangedEventHandler pfpEventHandler, ITaskWizard wizard) {		setExecuted(false);
		this.emf = emf;
		this.wizard = wizard;
		
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
		ASNPickup pickup = new ASNPickup();
		ASNDropOff dropOff = new ASNDropOff();

		Organization pickupLocOrg = this.pickupLocationOrganizationContainer.getItem(pickupLocationOrganization.getValue()).getEntity();
		Organization pickupCarrierOrg = this.pickupCarrierOrganizationContainer.getItem(pickupCarrierOrganization.getValue()).getEntity();
		Organization dropOffLocOrg = this.dropOffLocationOrganizationContainer.getItem(dropOffLocationOrganization.getValue()).getEntity();

		pickup.setPickUpFrom(pickupLocOrg);
		String selectedAddress = (String) pickupLocationAddress.getValue();
		if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getAdHocAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getShippingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getReceivingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getBillingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getPickupAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
			pickup.setPickUpFromAddress(pickupLocOrg.getDeliveryAddress());
		} else {
			pickup.setPickUpFromAddress(pickupLocOrg.getMainAddress());
		}

		pickup.setLocalTrans(pickupCarrierOrg);
		selectedAddress = (String) pickupCarrierAddress.getValue();
		if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getAdHocAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getShippingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getReceivingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getBillingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getPickupAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
			pickup.setLocalTransAddress(pickupCarrierOrg.getDeliveryAddress());
		} else {
			pickup.setLocalTransAddress(pickupCarrierOrg.getMainAddress());
		}
		pickup.setDriverId((String) pickupCarrierDriverId.getValue());
		pickup.setVehicleId((String) pickupCarrierVehicleId.getValue());
		pickup.setBolNumber((String) pickupCarrierBolNum.getValue());
		pickup.setVehicleId((String) pickupCarrierSealNum.getValue());
		pickup.setEstimatedPickup((Date) expectedPickupDate.getValue());
		if (pickupLocationDockType.getValue() != null) {
			pickup.setDockType(dockTypeContainer.getItem(pickupLocationDockType.getValue()).getEntity());
		}
		
		dropOff.setDropOffAt(dropOffLocOrg);
		selectedAddress = (String) dropOffLocationAddress.getValue();
		if (selectedAddress.equals(AddressCustomCONSTANTS.ADHOC_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getAdHocAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.SHIPPING_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getShippingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.RECEIVING_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getReceivingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.BILLING_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getBillingAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.PICKUP_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getPickupAddress());
		} else if (selectedAddress.equals(AddressCustomCONSTANTS.DELIVERY_ADDRESS)) {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getDeliveryAddress());
		} else {
			dropOff.setDropOffAtAddress(dropOffLocOrg.getMainAddress());
		}
		dropOff.setEstimatedDropOff((Date) expectedWhArrivalDate.getValue());
		if (dropOffLocationDockType.getValue() != null) {
			dropOff.setDockType(dockTypeContainer.getItem(dropOffLocationDockType.getValue()).getEntity());
		}

		Map<String,Object> asnLocalTransMap = new HashMap<String, Object>();
		asnLocalTransMap.put("asnPickup", pickup);	
		asnLocalTransMap.put("asnDropoff", dropOff);

		if (isExecuted()) {
			this.state.put("asnLocalTransMap", asnLocalTransMap);
		} else {
			this.state.put("asnLocalTransMapOut", asnLocalTransMap);
		}
		setExecuted(true);
		return this.state;
	}

	@Override
	public void setOnStartState(Map<String, Object> state) {
		this.state = new HashMap<String, Object>();
		this.wizard.setNextEnabled(false);
		this.wizard.setBackEnabled(true);
	}

}
