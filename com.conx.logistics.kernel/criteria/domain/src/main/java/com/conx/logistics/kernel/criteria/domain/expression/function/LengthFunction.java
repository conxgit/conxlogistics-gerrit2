package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

/**
 * Models the ANSI SQL <tt>LENGTH</tt> function.
 *
 */
public class LengthFunction
		extends ParameterizedFunctionExpression<Integer>
		implements Serializable {
	public static final String NAME = "length";

	@Override
	protected boolean isStandardJpaFunction() {
		return true;
	}

	public LengthFunction(CriteriaBuilderImpl criteriaBuilder, Expression<String> value) {
		super( criteriaBuilder, Integer.class, NAME, value );
	}
}
