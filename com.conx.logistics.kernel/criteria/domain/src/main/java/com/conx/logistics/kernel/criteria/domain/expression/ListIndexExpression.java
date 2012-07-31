package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import javax.persistence.metamodel.ListAttribute;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.PathImplementor;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * An expression for referring to the index of a list.
 *
 */
public class ListIndexExpression extends ExpressionImpl<Integer> implements Serializable {
	private final PathImplementor origin;
	private final ListAttribute listAttribute;

	public ListIndexExpression(
			CriteriaBuilderImpl criteriaBuilder,
			PathImplementor origin,
			ListAttribute listAttribute) {
		super( criteriaBuilder, Integer.class );
		this.origin = origin;
		this.listAttribute = listAttribute;
	}

	public ListAttribute getListAttribute() {
		return listAttribute;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(RenderingContext renderingContext) {
		return "index("
				+ origin.getPathIdentifier() + '.' + getListAttribute().getName()
				+ ")";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
