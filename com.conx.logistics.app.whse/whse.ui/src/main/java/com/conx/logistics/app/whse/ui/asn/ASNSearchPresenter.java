package com.conx.logistics.app.whse.ui.asn;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.ViewFactoryException;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.ui.WarehouseEventBus;
import com.conx.logistics.app.whse.ui.WarehousePresenter;
import com.conx.logistics.app.whse.ui.view.asn.ASNSearchView;
import com.conx.logistics.app.whse.ui.view.asn.IASNSearchView;
import com.conx.logistics.kernel.system.dao.services.application.IApplicationDAOService;
import com.conx.logistics.kernel.ui.common.mvp.MainMVPApplication;
import com.conx.logistics.kernel.ui.common.mvp.view.feature.FeatureView;
import com.conx.logistics.mdm.domain.application.Feature;
import com.conx.logistics.mdm.domain.geolocation.Address;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.terminal.Sizeable;
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
	private BeanContainer<String, ReferenceNumber> asnRefNumContainer;
	private EntityManagerFactory kernelSystemEntityManagerFactory;
	
	public void initAsnLines() {		
		asnLineTable = new Table(); 
		asnLineTable.setSizeFull();
		asnLineTable.setSelectable(true);
		asnLineTable.setContainerDataSource(asnLineContainer);
		asnLineTable.setVisibleColumns(new Object[] { "product.name", "refNumber.value", "expectedTotalWeight", "product.weightUnit.name", "expectedTotalVolume", "product.volUnit.name", "expectedOuterPackCount" });
		asnLineTable.setColumnHeader("product.name", "Product Name");
		asnLineTable.setColumnHeader("refNumber.value", "Reference Number");
		asnLineTable.setColumnHeader("expectedTotalWeight", "Total Weight");
		asnLineTable.setColumnHeader("product.weightUnit.name", "Weight Unit");
		asnLineTable.setColumnHeader("expectedTotalVolume", "Total Volume");
		asnLineTable.setColumnHeader("product.volUnit.name", "Volume Unit");
		asnLineTable.setColumnHeader("expectedOuterPackCount", "Expected Quantity");
		

		asnLineLayout = new VerticalLayout();
		asnLineLayout.setSizeFull();
		asnLineLayout.setMargin(true);
		asnLineLayout.setSpacing(true);
		asnLineLayout.addComponent(asnLineTable);
		asnLineLayout.setExpandRatio(asnLineTable, 1.0f);
	}

	public void initRefNums() {
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
		refNumLayout.addComponent(refNumTable);
		refNumLayout.setExpandRatio(refNumTable, 1.0f);
	}

	public void initLocalTrans() {
		Label pickupLocationLabel = new Label();
		pickupLocationLabel.setContentMode(Label.CONTENT_XHTML);
		pickupLocationLabel.setValue("<h3>Pick Up Location</h3>");
		
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

		GridLayout pickupLocationLayout = new GridLayout(2, 6);
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
		
		GridLayout dropOffLocationLayout = new GridLayout(2, 7);
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
		localTransLayout.setWidth("100%");
		localTransLayout.setHeight("300px");
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
		entityTabSheet.setSizeFull();
		entityTabSheet.addTab(refNumLayout, "Reference Numbers");
		entityTabSheet.addTab(asnLineLayout, "Asn Lines");
		entityTabSheet.addTab(localTransLayout, "Local Transportation");
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
		refNumContainer = new BeanContainer<String, ReferenceNumber>(ReferenceNumber.class);
		asnLineContainer = new BeanContainer<String, ASNLine>(ASNLine.class);
		refNumContainer.setBeanIdProperty("value");
		refNumContainer.addNestedContainerProperty("type.name");
		asnLineContainer.setBeanIdProperty("lineNumber");
		asnLineContainer.addNestedContainerProperty("product.name");
		asnLineContainer.addNestedContainerProperty("refNumber.value");
		asnLineContainer.addNestedContainerProperty("product.weightUnit.name");
		asnLineContainer.addNestedContainerProperty("product.volUnit.name");
		
		initEntityTabSheet();
		this.view.getSplitLayout().addComponent(entityTabSheet);

		this.view.getButtonLayout().setMargin(false, false, true, false);
		this.view.getSearchGrid().setContainerDataSource(this.asns);
		this.view.getSearchGrid().setSizeFull();
		this.view.getSearchGrid().setImmediate(true);
		this.view.getSearchGrid().setSelectable(true);
		this.view.getSearchGrid().setNullSelectionAllowed(false);
		this.view.getSearchGrid().setColumnCollapsingAllowed(true);
		this.view.getSearchGrid().setColumnReorderingAllowed(true);
		this.view.getSearchGrid().addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = view.getSearchGrid().getValue();
				if (value != null) {
					EntityItem<ASN> item = asns.getItem(value);
					if (item != null) {
						ASN asn = item.getEntity();
						if (asn != null) {
							if (refNumContainer != null) {
								try {
									refNumContainer.removeAllItems();
									refNumContainer.addAll(asn.getRefNumbers());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (asnLineContainer != null) {
								try {
									asnLineContainer.removeAllItems();
									for (ASNLine line : asn.getAsnLines())
									{
										if (line.getRefNumber() == null)
										{
											line.setRefNumber(new ReferenceNumber());
										}
									}
									asnLineContainer.addAll(asn.getAsnLines());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							pickupLocationOrg.setValue(new Label(asn.getPickup().getPickUpFrom().getName()));
							pickupLocationAddress.setValue((addressToXhtml(asn.getPickup().getPickUpFromAddress())));
							pickupLocationContactName.setValue((asn.getPickup().getPickUpFrom().getMainContact().getFirstName() + " " + asn.getPickup().getPickUpFrom().getMainContact().getLastName()));
							pickupLocationContactPhone.setValue((asn.getPickup().getPickUpFrom().getMainContact().getCellPhoneNumber()));

							inboundCarrierOrg.setValue((asn.getPickup().getLocalTrans().getName()));
							inboundCarrierAddress.setValue((addressToXhtml(asn.getPickup().getLocalTransAddress())));
							inboundCarrierContactName.setValue((asn.getPickup().getLocalTrans().getMainContact().getFirstName() + " " + asn.getPickup().getLocalTrans().getMainContact().getLastName()));
							inboundCarrierContactPhone.setValue((asn.getPickup().getLocalTrans().getMainContact().getCellPhoneNumber()));
							inboundCarrierDriverId.setValue((asn.getPickup().getDriverId()));
							inboundCarrierVehicleId.setValue((asn.getPickup().getVehicleId()));
							inboundCarrierBolNum.setValue((asn.getPickup().getBolNumber()));
							inboundCarrierSealNum.setValue((asn.getPickup().getSealNumber()));

							try {
								pickupDateTime.setValue((asn.getPickup().getEstimatedPickup()
										.toString()));
								whArrivalDateTime.setValue((asn.getDropOff()
										.getEstimatedDropOff().toString()));
							} catch (Exception e) {
								pickupDateTime.setValue("N/A");
								whArrivalDateTime.setValue("N/A");
							}

							dropOffLocationOrg.setValue((asn.getDropOff().getDropOffAt().getName()));
							dropOffLocationAddress.setValue((addressToXhtml(asn.getDropOff().getDropOffAtAddress())));
							dropOffLocationContactName.setValue((asn.getDropOff().getDropOffAt().getMainContact().getFirstName() + " " + asn.getDropOff().getDropOffAt().getMainContact().getLastName()));
							dropOffLocationContactPhone.setValue((asn.getDropOff().getDropOffAt().getMainContact().getCellPhoneNumber()));
							return;
						}
					}
				}
				view.getSearchGrid().getWindow().showNotification("OnValueChanged Failed", "Could not get value.");
			}
		});

		this.view.getSearchGrid().setColumnHeader("dateCreated", "Date Created");
		this.view.getSearchGrid().setColumnHeader("dateLastUpdated", "Date Last Updated");
		this.view.getSearchGrid().setVisibleColumns(
				new Object[] { "dateCreated", "dateLastUpdated"});

		// -- Init detail form
//		Collection<?> visibleProps = java.util.Arrays.asList(new Object[] {
//				"dateCreated", "dateLastUpdated"});
//		Collection<?> editableProps = java.util.Arrays
//				.asList(new Object[] { });

//		this.view.getASNForm().setFormFieldFactory(
//				new BasicFieldFactory(this.application
//						.getEntityTypeContainerFactory(), visibleProps,
//						editableProps));
//
//		this.view.getASNForm().setItemDataSource(
//				this.asns.createEntityItem(new ASN()));
//		this.view.getASNForm().setEnabled(false);
//		this.view.getASNForm().setWriteThrough(false);
//		this.view.getASNForm().setCaption("ASN Editor");
//		this.view.getASNForm().addStyleName("bordered"); // Custom style
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
		this.kernelSystemEntityManagerFactory = application.getKernelSystemEntityManagerFactory();
		this.kernelSystemEntityManager = this.kernelSystemEntityManagerFactory.createEntityManager();
		
		//Find ASN Search feature
		Feature searchFeature = null;
		TypedQuery<Feature> q = this.kernelSystemEntityManager.createQuery("select o from Feature o WHERE o.code = :code",Feature.class);
		q.setParameter("code", "whse.rcv.asn.CreateNewASNByOrgV1.0");
		try 
		{
			searchFeature = q.getSingleResult();
		} 
		catch (NoResultException e) 
		{
			e.printStackTrace();
		}	
		
	    //IPresenterFactory pf = application.getPresenterFactory();
	    //WarehousePresenter whsePresenter = (WarehousePresenter) pf.createPresenter(WarehousePresenter.class);		
		
	    WarehouseEventBus whseeb = (WarehouseEventBus)application.getEeventBusManager().getEventBus(WarehouseEventBus.class);
	    whseeb.openFeatureView(searchFeature);
		/*
		ASN td = new ASN();
		td.setName("New ASN");
		// BeanItem<ASN> beanItem = new BeanItem<ASN>(td);
		this.view.getASNForm().setItemDataSource(
				this.asns.createEntityItem(td));
		this.view.getASNForm().setEnabled(true);
		resetButtons(true, false, true);
		*/
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
