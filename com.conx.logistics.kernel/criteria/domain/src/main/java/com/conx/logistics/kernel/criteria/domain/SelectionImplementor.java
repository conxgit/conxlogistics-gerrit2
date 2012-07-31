package com.conx.logistics.kernel.criteria.domain;

import java.util.List;
import javax.persistence.criteria.Selection;

public interface SelectionImplementor<X> extends TupleElementImplementor<X>, Selection<X>  {
	public List<ValueHandlerFactory.ValueHandler> getValueHandlers();
}
