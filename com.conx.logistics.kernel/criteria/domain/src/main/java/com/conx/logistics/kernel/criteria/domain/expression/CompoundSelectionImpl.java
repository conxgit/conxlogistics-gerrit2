package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Tuple;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Selection;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.TupleElementImplementor;
import com.conx.logistics.kernel.criteria.domain.ValueHandlerFactory;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;


/**
 * The Hibernate implementation of the JPA {@link CompoundSelection}
 * contract.
 *
 */
public class CompoundSelectionImpl<X>
		extends SelectionImpl<X>
		implements CompoundSelection<X>, Renderable, Serializable {
	private final boolean isConstructor;
	private List<Selection<?>> selectionItems;

	public CompoundSelectionImpl(
			CriteriaBuilderImpl criteriaBuilder,
			Class<X> javaType,
			List<Selection<?>> selectionItems) {
		super( criteriaBuilder, javaType );
		this.isConstructor = !javaType.isArray() && !Tuple.class.isAssignableFrom( javaType );
		this.selectionItems = selectionItems;
	}

	@Override
	public boolean isCompoundSelection() {
		return true;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
		return selectionItems;
	}

	@Override
	public List<ValueHandlerFactory.ValueHandler> getValueHandlers() {
		if ( isConstructor ) {
			return null;
		}
		boolean foundHandlers = false;
		ArrayList<ValueHandlerFactory.ValueHandler> valueHandlers = new ArrayList<ValueHandlerFactory.ValueHandler>();
		for ( Selection selection : getCompoundSelectionItems() ) {
			ValueHandlerFactory.ValueHandler valueHandler = ( (TupleElementImplementor) selection ).getValueHandler();
			valueHandlers.add( valueHandler );
			foundHandlers = foundHandlers || valueHandler != null;
		}
		return foundHandlers ? null : valueHandlers;
	}

	public void registerParameters(ParameterRegistry registry) {
		for ( Selection selectionItem : getCompoundSelectionItems() ) {
			Helper.possibleParameter(selectionItem, registry);
		}
	}

	public String render(RenderingContext renderingContext) {
		StringBuilder buff = new StringBuilder();
		if ( isConstructor ) {
			buff.append( "new " ).append( getJavaType().getName() ).append( '(' );
		}
		String sep = "";
		for ( Selection selection : selectionItems ) {
			buff.append( sep )
					.append( ( (Renderable) selection ).renderProjection( renderingContext ) );
			sep = ", ";
		}
		if ( isConstructor ) {
			buff.append( ')' );
		}
		return buff.toString();
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
