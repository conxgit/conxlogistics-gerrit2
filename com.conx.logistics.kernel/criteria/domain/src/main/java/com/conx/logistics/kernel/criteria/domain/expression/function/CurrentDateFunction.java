package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import java.sql.Date;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;


/**
 * Models the ANSI SQL <tt>CURRENT_DATE</tt> function.
 *
 */
public class CurrentDateFunction
		extends BasicFunctionExpression<Date>
		implements Serializable {
	public static final String NAME = "current_date";

	public CurrentDateFunction(CriteriaBuilderImpl criteriaBuilder) {
		super( criteriaBuilder, Date.class, NAME );
	}
}
