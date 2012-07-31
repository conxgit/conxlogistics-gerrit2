package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.expression.ExpressionImpl;

/**
 * Basic template support for {@link Predicate} implementors providing
 * expression handling, negation and conjunction/disjunction handling.
 *
 */
public abstract class AbstractPredicateImpl
		extends ExpressionImpl<Boolean>
		implements Predicate, Serializable {
	private boolean negated;

	protected AbstractPredicateImpl(CriteriaBuilderImpl criteriaBuilder) {
		super( criteriaBuilder, Boolean.class );
	}

	public boolean isNegated() {
		return negated;
	}

	public Predicate not() {
		negated = !negated;
		return this;
	}


	// Selection ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Override
	public final boolean isCompoundSelection() {
		// Should always be false for predicates
		return super.isCompoundSelection();
	}

	@Override
	public final List<Selection<?>> getCompoundSelectionItems() {
		// Should never have sub selection items for predicates
		return super.getCompoundSelectionItems();
	}

}
