package com.conx.logistics.app.whse.rcv.rcv.pageflow.pages;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.kernel.pageflow.event.IPageFlowPageChangedEventHandler;
import com.conx.logistics.kernel.pageflow.services.ITaskWizard;
import com.conx.logistics.kernel.pageflow.services.PageFlowPage;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ProcessArrivalReceiptsPage extends PageFlowPage {
	private static final String VIEW_HEIGHT = "100%";
	
	@SuppressWarnings("unused")
	private String userId;
	@SuppressWarnings("unused")
	private String orgId;
	
	private TabSheet entityTabSheet;
	private ComboBox organization;
	private Label organizationLabel;
	private GridLayout organizationLayout;
	private HorizontalLayout confirmOrganizationLayout;
	private Button saveButton;
	private Button resetButton;
	private HorizontalLayout toolstripLeftButtonPanel;
	private HorizontalLayout toolStrip;
	private JPAContainer<Organization> organizationContainer;
	private EntityManagerFactory emf;
	private Button cancelButton;

	private Map<String, Object> state;

	private ASN asn;
	
	private void initContainers() {
		organizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
	}
	
	public void initConfirmOrg() {
		organization = new ComboBox();
		organization.setInputPrompt("Default Organization");
		organization.setContainerDataSource(organizationContainer);
		organization.setItemCaptionPropertyId("name");
		organization.setNullSelectionAllowed(false);
		organization.isReadOnly();
		organization.setWidth("100%");
		organization.setValue(organizationContainer.firstItemId());
//		organization.setEnabled(false);
		
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
		initConfirmOrg();
		
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

	@Override
	public String getTaskName() {
		return "ConfirmAsnOrg";
	}

	@Override
	public String getCaption() {
		return "Confirm Asn Organization";
	}

	@Override
	public void initialize(EntityManagerFactory emf, PlatformTransactionManager ptm,
			IPageFlowPageChangedEventHandler pfpEventHandler, ITaskWizard wizard) {
		setExecuted(false);
		this.emf = emf;
		
/*		initContainers();
		initEntityTabSheet();
		initTableToolStrip();*/
		
		VerticalLayout canvas = new VerticalLayout();
		canvas.setSizeFull();
/*		canvas.addComponent(entityTabSheet);
		canvas.addComponent(toolStrip);
		canvas.setExpandRatio(entityTabSheet, 1.0f);*/
		
		this.setCanvas(canvas);
	}

	@Override
	public Map<String, Object> getOnCompleteState() {
		//Map<String,Object> outParams = new HashMap<String, Object>();
		//outParams.put("Result", this.state);
		setExecuted(true);
		return new HashMap<String, Object>();
	}

	@Override
	public void setOnStartState(Map<String, Object> state) {
		this.asn = (ASN)state.get("Content");
		if (state != null) {
			this.orgId = (String) state.get("orgId");
			this.userId = (String) state.get("userId");
		}
	}

}
