package com.conx.logistics.app.whse.rcv.rcv.domain.types;

public enum RECEIVESTATUS {
	NEW("NEW","New(No Lines)",1),
	AWAITING_ARRIVAL("AWA","Awaiting Arrival",1),
	ARRIVING("ARV","Arriving",2),
	ARRIVED("AVD","Arrived",3),
	ONHOLD("OHD","On Hold",3),
	DEMAGED("DMG","Demaged",3),
	CANCELLED("CCL","Cancelled",2),
	COMMITTED("CMT","Committed",4),
	FINALIZED("FND","Finallized",5);
	
    private String abbr = "";
    private String description = "";
    private int ordinal;
    
    private RECEIVESTATUS(String abbr,String description,int ordinal)
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
