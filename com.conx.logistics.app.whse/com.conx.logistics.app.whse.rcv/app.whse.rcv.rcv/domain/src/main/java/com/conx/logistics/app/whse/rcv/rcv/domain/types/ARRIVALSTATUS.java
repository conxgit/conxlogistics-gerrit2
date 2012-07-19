package com.conx.logistics.app.whse.rcv.rcv.domain.types;

public enum ARRIVALSTATUS {
	ARRIVING("ARV","Arriving",1),
	ARRIVED("AVD","Arrived",2),
	CANCELLED("CLD","Cancelled",2);
	
    private String abbr = "";
    private String description = "";
    private int ordinal;
    
    private ARRIVALSTATUS(String abbr,String description,int ordinal)
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

