package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.Selection;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterContainer;
import com.conx.logistics.kernel.criteria.domain.SelectionImplementor;
import com.conx.logistics.kernel.criteria.domain.ValueHandlerFactory;


/**
 * The Hibernate implementation of the JPA {@link Selection}
 * contract.
 */
public abstract class SelectionImpl<X>
		extends AbstractTupleElement<X>
		implements SelectionImplementor<X>, ParameterContainer, Serializable {
	public SelectionImpl(CriteriaBuilderImpl criteriaBuilder, Class<X> javaType) {
		super( criteriaBuilder, javaType );
	}

	public Selection<X> alias(String alias) {
		setAlias( alias );
		return this;
	}

	public boolean isCompoundSelection() {
		return false;
	}

	public List<ValueHandlerFactory.ValueHandler> getValueHandlers() {
		return getValueHandler() == null
				? null
				: Collections.singletonList( (ValueHandlerFactory.ValueHandler) getValueHandler() );
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		throw new IllegalStateException( "Not a compound selection" );
	}
}
