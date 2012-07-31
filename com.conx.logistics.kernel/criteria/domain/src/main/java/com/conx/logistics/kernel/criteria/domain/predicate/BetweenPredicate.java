package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Models a <tt>BETWEEN</tt> {@link javax.persistence.criteria.Predicate}.
 */
public class BetweenPredicate<Y>
		extends AbstractSimplePredicate
		implements Serializable {
	private final Expression<? extends Y> expression;
	private final Expression<? extends Y> lowerBound;
	private final Expression<? extends Y> upperBound;

	public BetweenPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends Y> expression,
			Y lowerBound,
			Y upperBound) {
		this(
				criteriaBuilder,
				expression,
				criteriaBuilder.literal( lowerBound ),
				criteriaBuilder.literal( upperBound )
		);
	}

	public BetweenPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends Y> expression,
			Expression<? extends Y> lowerBound,
			Expression<? extends Y> upperBound) {
		super( criteriaBuilder );
		this.expression = expression;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public Expression<? extends Y> getExpression() {
		return expression;
	}

	public Expression<? extends Y> getLowerBound() {
		return lowerBound;
	}

	public Expression<? extends Y> getUpperBound() {
		return upperBound;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getExpression(), registry );
		Helper.possibleParameter( getLowerBound(), registry );
		Helper.possibleParameter( getUpperBound(), registry );
	}

	public String render(RenderingContext renderingContext) {
		final String operator = isNegated() ? " not between " : " between ";
		return ( (Renderable) getExpression() ).render( renderingContext )
				+ operator
				+ ( (Renderable) getLowerBound() ).render( renderingContext )
				+ " and "
				+ ( (Renderable) getUpperBound() ).render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
