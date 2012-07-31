package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Predicate to assert the explicit value of a boolean expression:<ul>
 * <li>x = true</li>
 * <li>x = false</li>
 * <li>x <> true</li>
 * <li>x <> false</li>
 * </ul>
 **/
public class BooleanAssertionPredicate
		extends AbstractSimplePredicate
		implements Serializable {
	private final Expression<Boolean> expression;
	private final Boolean assertedValue;

	public BooleanAssertionPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<Boolean> expression,
			Boolean assertedValue) {
		super( criteriaBuilder );
		this.expression = expression;
		this.assertedValue = assertedValue;
	}

	public Expression<Boolean> getExpression() {
		return expression;
	}

	public Boolean getAssertedValue() {
		return assertedValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( expression, registry );
	}

	/**
	 * {@inheritDoc}
	 */
	public String render(RenderingContext renderingContext) {
		final String operator = isNegated() ? " <> " : " = ";
		final String assertionLiteral = assertedValue ? "true" : "false";

		return ( (Renderable) expression ).render( renderingContext )
				+ operator
				+ assertionLiteral;
	}

	/**
	 * {@inheritDoc}
	 */
	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}

}
