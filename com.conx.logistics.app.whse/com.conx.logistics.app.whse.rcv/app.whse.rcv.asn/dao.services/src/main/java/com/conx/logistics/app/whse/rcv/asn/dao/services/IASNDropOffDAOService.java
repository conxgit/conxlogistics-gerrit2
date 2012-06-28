package com.conx.logistics.app.whse.rcv.asn.dao.services;

import java.util.List;

import com.conx.logistics.app.whse.rcv.asn.domain.ASNDropOff;

public interface IASNDropOffDAOService {
	public List<ASNDropOff> getAll();

	public ASNDropOff add(ASNDropOff record);

	public void delete(ASNDropOff record);

	public ASNDropOff update(ASNDropOff record);

	public ASNDropOff get(long id);
}
