package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * ANSI-SQL defines <tt>TRUE</tt>, <tt>FALSE</tt> and <tt>UNKNOWN</tt> as <i>truth values</i>.  These
 * <i>truth values</i> are used to explicitly check the result of a boolean expression (the syntax is like
 * <tt>a > b IS TRUE</tt>.  <tt>IS TRUE</tt> is the assumed default.
 * <p/>
 * JPA defines support for only <tt>IS TRUE</tt> and <tt>IS FALSE</tt>, not <tt>IS UNKNOWN</tt> (<tt>a > NULL</tt>
 * is an example where the result would be UNKNOWN.
 *
 */
public class ExplicitTruthValueCheck
		extends AbstractSimplePredicate
		implements Serializable {
	// TODO : given that JPA supports only TRUE and FALSE, can this be handled just with negation?
	private final Expression<Boolean> booleanExpression;
	private final TruthValue truthValue;

	public ExplicitTruthValueCheck(CriteriaBuilderImpl criteriaBuilder, Expression<Boolean> booleanExpression, TruthValue truthValue) {
		super( criteriaBuilder );
		this.booleanExpression = booleanExpression;
		this.truthValue = truthValue;
	}

	public Expression<Boolean> getBooleanExpression() {
		return booleanExpression;
	}

	public TruthValue getTruthValue() {
		return truthValue;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getBooleanExpression(), registry );
	}

	public String render(RenderingContext renderingContext) {
		return ( (Renderable) getBooleanExpression() ).render( renderingContext )
				+ " = "
				+ ( getTruthValue() == TruthValue.TRUE ? "true" : "false" );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}

