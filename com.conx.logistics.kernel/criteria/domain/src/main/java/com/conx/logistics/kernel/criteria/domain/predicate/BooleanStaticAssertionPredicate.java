package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Predicate used to assert a static boolean condition.
 *
 */
public class BooleanStaticAssertionPredicate
		extends AbstractSimplePredicate
		implements Serializable {
	private final Boolean assertedValue;

	public BooleanStaticAssertionPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Boolean assertedValue) {
		super( criteriaBuilder );
		this.assertedValue = assertedValue;
	}

	public Boolean getAssertedValue() {
		return assertedValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerParameters(ParameterRegistry registry) {
		// nada
	}

	/**
	 * {@inheritDoc}
	 */
	public String render(RenderingContext renderingContext) {
		boolean isTrue = getAssertedValue();
		if ( isNegated() ) {
			isTrue = !isTrue;
		}

		return isTrue
				? "1=1"
				: "0=1";
	}

	/**
	 * {@inheritDoc}
	 */
	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}

}