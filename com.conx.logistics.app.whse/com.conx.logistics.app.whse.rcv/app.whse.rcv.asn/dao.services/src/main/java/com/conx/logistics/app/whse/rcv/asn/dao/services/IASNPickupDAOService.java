package com.conx.logistics.app.whse.rcv.asn.dao.services;

import java.util.List;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;
import com.conx.logistics.app.whse.rcv.asn.domain.ASNPickup;

public interface IASNPickupDAOService {
	public List<ASNPickup> getAll();

	public ASNPickup add(ASNPickup record);

	public void delete(ASNPickup record);

	public ASNPickup update(ASNPickup record);

	public ASNPickup get(long id);
}
