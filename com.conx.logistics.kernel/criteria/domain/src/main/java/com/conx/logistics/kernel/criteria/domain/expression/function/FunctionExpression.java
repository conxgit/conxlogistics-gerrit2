package com.conx.logistics.kernel.criteria.domain.expression.function;

import javax.persistence.criteria.Expression;

/**
 * Contract for expressions which model a SQL function call.
 *
 * @param <T> The type of the function result.
 *
 */
public interface FunctionExpression<T> extends Expression<T> {
	/**
	 * Retrieve the name of the function.
	 *
	 * @return The function name.
	 */
	public String getFunctionName();

	/**
	 * Is this function a value aggregator (like a <tt>COUNT</tt> or <tt>MAX</tt> function e.g.)?
	 *
	 * @return True if this functions does aggregation.
	 */
	public boolean isAggregation();
}
