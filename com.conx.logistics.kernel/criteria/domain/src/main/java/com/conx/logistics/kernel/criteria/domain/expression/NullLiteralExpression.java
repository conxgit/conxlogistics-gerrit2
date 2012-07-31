package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Represents a <tt>NULL</tt>literal expression.
 *
 */
public class NullLiteralExpression<T> extends ExpressionImpl<T> implements Serializable {
	public NullLiteralExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> type) {
		super( criteriaBuilder, type );
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(RenderingContext renderingContext) {
		return "null";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}