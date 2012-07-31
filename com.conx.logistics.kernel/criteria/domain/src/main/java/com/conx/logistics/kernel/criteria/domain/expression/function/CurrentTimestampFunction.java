package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import java.sql.Timestamp;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

/**
 * Models the ANSI SQL <tt>CURRENT_TIMESTAMP</tt> function.
 *
 */
public class CurrentTimestampFunction
		extends BasicFunctionExpression<Timestamp>
		implements Serializable {
	public static final String NAME = "current_timestamp";

	public CurrentTimestampFunction(CriteriaBuilderImpl criteriaBuilder) {
		super( criteriaBuilder, Timestamp.class, NAME );
	}
}
