package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.TupleElement;

public interface TupleElementImplementor<X> extends TupleElement<X> {
	public ValueHandlerFactory.ValueHandler<X> getValueHandler();
}
