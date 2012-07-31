package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;

public abstract class AbstractSimplePredicate
		extends AbstractPredicateImpl 
		implements Serializable {
	private static final List<Expression<Boolean>> NO_EXPRESSIONS = Collections.emptyList();

	public AbstractSimplePredicate(CriteriaBuilderImpl criteriaBuilder) {
		super( criteriaBuilder );
	}

	public BooleanOperator getOperator() {
		return BooleanOperator.AND;
	}

	public final List<Expression<Boolean>> getExpressions() {
		return NO_EXPRESSIONS;
	}

}
