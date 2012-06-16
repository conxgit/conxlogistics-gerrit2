package com.conx.logistics.app.whse.rcv.asn.shared.type;


public enum ASNLINESTATUS {
	WIP("WIP","Work In Progress",1),
	NEW("NEW","New",2),
	ATTACHED("ATD","Attached",3);
	
    private String abbr = "";
    private String description = "";
    private int ordinal;
    
    private ASNLINESTATUS(String abbr,String description,int ordinal)
    {
    	this.abbr = abbr;
    	this.description = description;
    	this.ordinal = ordinal;
    }

	public String getAbbr() {
		return abbr;
	}   
	
	public String getDescription() {
		return description;
	}

	public int getOrdinal() {
		return ordinal;
	}	
}
