package com.conx.logistics.mdm.domain.constants;

import java.util.HashMap;
import java.util.Map;

public class BaseEntityCONSTANTS {
	public static final Map<String, String> name2label = new HashMap<String, String>() {
		{
							put(dateCreated, "Date Created");
							put(createdBy, "Created By");
							put(dateLastUpdated, "Date Last Created");
							put(lastUpdatedBy, "Last Updated By");
							put(code, "Code");
							put(id, "ID");
							put(name, "Name");
							put(portalId, "portalId");
							put(version, "Version");
							put(activ, "Active");
					}
	};
	public static final Map<String, Integer> name2width = new HashMap<String, Integer>() {
		{
							put(dateCreated,Integer.valueOf(100));
							put(createdBy,Integer.valueOf(100));
							put(dateLastUpdated,Integer.valueOf(100));
							put(lastUpdatedBy,Integer.valueOf(100));
							put(code,Integer.valueOf(100));							
							put(id,Integer.valueOf(100));
							put(name,Integer.valueOf(100));
							put(portalId,Integer.valueOf(100));
							put(version,Integer.valueOf(100));
							put(activ,Integer.valueOf(100));
					}
	};	

	public static final String dateCreated = "dateCreated";
	public static final String createdBy = "createdBy";
	
	public static final String dateLastUpdated = "dateLastUpdated";
	public static final String lastUpdatedBy = "lastUpdatedBy";
	
	public static final String code = "code";
	public static final String id = "id";
	public static final String name = "name";
	public static final String portalId = "portalId";
	public static final String version = "version";
	public static final String activ = "activ";
	
	public static final String externalName = "externalName";
	public static final String externalCode = "externalCode";
	public static final String externalRefId = "externalRefId";
	public static final String externalParentCode = "externalParentCode";
	public static final String externalParentRefId = "externalParentRefId";	
		
	public static String getTitle(String name)
	{
		return name2label.get(name);
	}	
	
	public static Integer getWidth(String name)
	{
		return name2width.get(name);
	}	
}
