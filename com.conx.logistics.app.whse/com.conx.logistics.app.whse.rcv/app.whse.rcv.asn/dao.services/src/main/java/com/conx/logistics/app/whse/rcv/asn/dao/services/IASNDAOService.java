package com.conx.logistics.app.whse.rcv.asn.dao.services;

import java.util.List;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNLine;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;

public interface IASNDAOService {
	public List<ASN> getAll();

	public ASN add(ASN record);
	
	public ASN addLines(Long asnId, List<ASNLine> line);	
	
	public ASN addRefNums(Long asnId, List<ReferenceNumber> numbers);	
	
	public ASN addLocalTrans(Long asnId, ASNPickup pickUp, ASNDropOff dropOff);

	public void delete(ASN record);

	public ASN update(ASN record);

	public ASN get(long id);
}
