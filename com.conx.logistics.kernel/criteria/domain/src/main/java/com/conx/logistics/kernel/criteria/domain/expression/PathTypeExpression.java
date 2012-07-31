package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.path.AbstractPathImpl;

/**
 * Used to construct the result of {@link javax.persistence.criteria.Path#type()}
 *
 */
public class PathTypeExpression<T> extends ExpressionImpl<T> implements Serializable {
	private final AbstractPathImpl<T> pathImpl;

	public PathTypeExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> javaType, AbstractPathImpl<T> pathImpl) {
		super( criteriaBuilder, javaType );
		this.pathImpl = pathImpl;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(RenderingContext renderingContext) {
		return "type(" + pathImpl.getPathIdentifier() + ")";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
