package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

/**
 * Models the ANSI SQL <tt>ABS</tt> function.
 *
 */
public class AbsFunction<N extends Number>
		extends ParameterizedFunctionExpression<N>
		implements Serializable {
	public static final String NAME = "abs";

	public AbsFunction(CriteriaBuilderImpl criteriaBuilder, Expression expression) {
		super( criteriaBuilder, expression.getJavaType(), NAME, expression );
	}

	@Override
	protected boolean isStandardJpaFunction() {
		return true;
	}
}
