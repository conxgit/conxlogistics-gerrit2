package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;


/**
 * Defines a {@link Predicate} used to wrap an {@link Expression Expression&lt;Boolean&gt;}.
 */
public class BooleanExpressionPredicate
		extends AbstractSimplePredicate
		implements Serializable {
	private final Expression<Boolean> expression;

	public BooleanExpressionPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Boolean> expression) {
		super( criteriaBuilder );
		this.expression = expression;
	}

	/**
	 * Get the boolean expression defining the predicate.
	 * 
	 * @return The underlying boolean expression.
	 */
	public Expression<Boolean> getExpression() {
		return expression;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter(expression, registry);
	}

	public String render(RenderingContext renderingContext) {
		return ( (Renderable) getExpression() ).render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
