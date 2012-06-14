package com.conx.logistics.app.whse.rcv.asn.dao.services;

import java.util.List;

import com.conx.logistics.app.whse.rcv.asn.domain.ASN;

public interface IASNDAOService {
	public List<ASN> getAll();

	public ASN add(ASN record);

	public void delete(ASN record);

	public ASN update(ASN record);

	public ASN get(long id);
}
