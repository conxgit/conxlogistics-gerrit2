package com.conx.logistics.app.whse.rcv.asn.pageflow.pages;

import java.util.Map;

import com.conx.logistics.kernel.pageflow.services.IPageFlowPage;
import com.conx.logistics.mdm.domain.organization.Organization;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
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
import com.vaadin.ui.VerticalLayout;

public class ConfirmAsnOrgPage extends IPageFlowPage {
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
	
	private JPAContainer<Organization> organizationContainer;
	
	private void initContainers() {
		organizationContainer = JPAContainerFactory.make(Organization.class, this.emf.createEntityManager());
	}
	
	public void initConfirmOrg() {
		organization = new ComboBox();
		organization.setInputPrompt("Default Organization");
		organization.setContainerDataSource(organizationContainer);
		organization.setItemCaptionPropertyId("code");
		organization.isReadOnly();
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
		initContainers();
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
		return "ConfirmAsnOrg";
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
		return "Confirm Asn Organization";
	}

}
