package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.ExpressionImpl;

/**
 * Models a <tt>CAST</tt> function.
 *
 * @param <T> The cast result type.
 * @param <Y> The type of the expression to be cast.
 *
 */
public class CastFunction<T,Y>
		extends BasicFunctionExpression<T>
		implements FunctionExpression<T>, Serializable {
	public static final String CAST_NAME = "cast";

	private final ExpressionImpl<Y> castSource;

	public CastFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Class<T> javaType,
			ExpressionImpl<Y> castSource) {
		super( criteriaBuilder, javaType, CAST_NAME );
		this.castSource = castSource;
	}

	public ExpressionImpl<Y> getCastSource() {
		return castSource;
	}

	@Override
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getCastSource(), registry );
	}

	@Override
	public String render(RenderingContext renderingContext) {
		return CAST_NAME + '(' +
				castSource.render( renderingContext ) +
				" as " +
				renderingContext.getCastType( getJavaType() ) +
				')';
	}
}
