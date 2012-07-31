package com.conx.logistics.kernel.criteria.domain.expression;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

/**
 * Contract for operators with a single operand.
 *
 * @author Steve Ebersole
 */
public interface UnaryOperatorExpression<T> extends Expression<T>, Serializable {
	/**
	 * Get the operand.
	 *
	 * @return The operand.
	 */
	public Expression<?> getOperand();
}
