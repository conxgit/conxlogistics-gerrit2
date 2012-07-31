package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;


/**
 * Models the ANSI SQL <tt>LOWER</tt> function.
 *
 */
public class LowerFunction
		extends ParameterizedFunctionExpression<String>
		implements Serializable {
	public static final String NAME = "lower";

	public LowerFunction(CriteriaBuilderImpl criteriaBuilder, Expression<String> string) {
		super( criteriaBuilder, String.class, NAME, string );
	}

	@Override
	protected boolean isStandardJpaFunction() {
		return true;
	}
}
