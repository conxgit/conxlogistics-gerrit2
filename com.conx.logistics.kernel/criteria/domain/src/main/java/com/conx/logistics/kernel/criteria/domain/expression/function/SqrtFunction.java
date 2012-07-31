package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

/**
 * Models the ANSI SQL <tt>SQRT</tt> function.
 */
public class SqrtFunction
		extends ParameterizedFunctionExpression<Double>
		implements Serializable {
	public static final String NAME = "sqrt";

	public SqrtFunction(CriteriaBuilderImpl criteriaBuilder, Expression<? extends Number> expression) {
		super( criteriaBuilder, Double.class, NAME, expression );
	}

	@Override
	protected boolean isStandardJpaFunction() {
		return true;
	}
}
