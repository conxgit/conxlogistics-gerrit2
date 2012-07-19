package com.conx.logistics.app.whse.rcv.rcv.dao.services;

import java.util.List;
import java.util.Set;

import com.conx.logistics.app.whse.rcv.rcv.domain.Arrival;


public interface IArrivalDAOService {
	public List<Arrival> getAll();

	public Arrival add(Arrival record);
	
	public void delete(Arrival record);

	public Arrival update(Arrival record);

	public Arrival get(long id);
}
