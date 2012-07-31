package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import java.sql.Time;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

/**
 * Models the ANSI SQL <tt>CURRENT_TIME</tt> function.
 *
 */
public class CurrentTimeFunction
		extends BasicFunctionExpression<Time> 
		implements Serializable {
	public static final String NAME = "current_time";

	public CurrentTimeFunction(CriteriaBuilderImpl criteriaBuilder) {
		super( criteriaBuilder, Time.class, NAME );
	}
}
