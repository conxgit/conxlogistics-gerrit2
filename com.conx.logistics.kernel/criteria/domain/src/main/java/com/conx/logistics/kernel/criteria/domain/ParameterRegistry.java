package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.criteria.ParameterExpression;

public interface ParameterRegistry {
	/**
	 * Registers the given parameter with this regitry.
	 *
	 * @param parameter The parameter to register.
	 */
	public void registerParameter(ParameterExpression<?> parameter);
}
