package com.conx.logistics.kernel.system.dao.services.application;

import java.util.List;

import com.conx.logistics.mdm.domain.application.Application;

public interface IApplicationDAOService {
	/**
	 * System Control Panel App
	 */
	//== App
	public static final String SYSTEM_CONTROL_PANEL_APP_NAME = "Control Panel";
	public static final String SYSTEM_CONTROL_PANEL_APP_CODE = "KERNEL.CONTROLPANEL";
	
	//==== Modules Featureset
	public static final String SYSTEM_CONTROL_PANEL_APP_MODMNGMT_NAME = "Application Management";
	public static final String SYSTEM_CONTROL_PANEL_APP_MODMNGMT_CODE = "MODMNGMT";	
	
	//========= Modules :: Search Feature
	public static final String SYSTEM_CONTROL_PANEL_APP_MODMNGMT_MODS_SEARCH_NAME = "Applications";
	public static final String SYSTEM_CONTROL_PANEL_APP_MODMNGMT_MODS_SEARCH_CODE = "MODS.SEARCH";	
	
	//==== Workflow Management Featureset
	public static final String SYSTEM_CONTROL_PANEL_APP_WFMNGMT_NAME = "Workflow Management";
	public static final String SYSTEM_CONTROL_PANEL_APP_WFMNGMT_CODE = "WFMNGMT";
	
	//========= WorkTaskDef :: Search Feature
	public static final String SYSTEM_CONTROL_PANEL_APP_WFMNGMT_WTDFS_SEARCH_NAME = "Task Definitions";
	public static final String SYSTEM_CONTROL_PANEL_APP_WFMNGMT_WTDFS_SEARCH_CODE = "WTDFS.SEARCH";		
	
	/**
	 * Warehouse App
	 */
	//== App
	public static final String WAREHOUSE_APP_NAME = "Warehouse";
	public static final String WAREHOUSE_APP_CODE = "WHSE";
	
	//==== Receiving Featureset
	public static final String WAREHOUSE_APP_RECEIVING_NAME = "Receiving";
	public static final String WAREHOUSE_APP_RECEIVING_CODE = "RCVNG";	
	
	//==== Receiving ASN Featureset
	public static final String WAREHOUSE_APP_RECEIVING_ASN_NAME = "ASN's";
	public static final String WAREHOUSE_APP_RECEIVING_ASN_CODE = "ASN";		
	
	//========= Receiving ASN :: Search Feature
	public static final String WAREHOUSE_APP_RECEIVING_ASN_SEARCH_NAME = "Search";
	public static final String WAREHOUSE_APP_RECEIVING_ASN_SEARCH_CODE = "SEARCH";	
	
	//========= Receiving ASN :: New ASN Feature
	public static final String WAREHOUSE_APP_RECEIVING_ASN_NEW_NAME = "New";
	public static final String WAREHOUSE_APP_RECEIVING_ASN_NEW_CODE = "NEW";	
	
	public List<Application> getApplications();

	public Application addApplication(Application app);

	public void deleteApplication(Application app);

	public Application updateApplication(Application app);

	public Application getApplication(long id);

	public Application provideControlPanelApplication();
}
