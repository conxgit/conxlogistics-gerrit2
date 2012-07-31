package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * TODO : javadoc
 *
 */
public class EntityTypeExpression<T> extends ExpressionImpl<T> implements Serializable {
	public EntityTypeExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> javaType) {
		super( criteriaBuilder, javaType );
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothign to do
	}

	public String render(RenderingContext renderingContext) {
		// todo : is it valid for this to get rendered into the query itself?
		throw new IllegalArgumentException( "Unexpected call on EntityTypeExpression#render" );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
