package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.UnaryOperatorExpression;

/**
 * Defines a {@link javax.persistence.criteria.Predicate} for checking the
 * nullness state of an expression, aka an <tt>IS [NOT] NULL</tt> predicate.
 * <p/>
 * The <tt>NOT NULL</tt> form can be built by calling the constructor and then
 * calling {@link #not}.
 *
 */
public class NullnessPredicate
		extends AbstractSimplePredicate
		implements UnaryOperatorExpression<Boolean>, Serializable {
	private final Expression<?> operand;

	/**
	 * Constructs the affirmitive form of nullness checking (<i>IS NULL</i>).  To
	 * construct the negative form (<i>IS NOT NULL</i>) call {@link #not} on the
	 * constructed instance.
	 *
	 * @param criteriaBuilder The query builder from whcih this originates.
	 * @param operand The expression to check.
	 */
	public NullnessPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<?> operand) {
		super( criteriaBuilder );
		this.operand = operand;
	}

	public Expression<?> getOperand() {
		return operand;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getOperand(), registry );
	}

	public String render(RenderingContext renderingContext) {
		return ( (Renderable) operand ).render( renderingContext ) + check();
	}

	private String check() {
		return isNegated()
				? " is not null"
				: " is null";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
