package com.conx.logistics.kernel.criteria.domain;

import java.io.Serializable;

public class AbstractNode implements Serializable {
	private final CriteriaBuilderImpl criteriaBuilder;

	public AbstractNode(CriteriaBuilderImpl criteriaBuilder) {
		this.criteriaBuilder = criteriaBuilder;
	}

	/**
	 * Provides access to the underlying {@link CriteriaBuilderImpl}.
	 *
	 * @return The underlying {@link CriteriaBuilderImpl} instance.
	 */
	public CriteriaBuilderImpl criteriaBuilder() {
		return criteriaBuilder;
	}
}
