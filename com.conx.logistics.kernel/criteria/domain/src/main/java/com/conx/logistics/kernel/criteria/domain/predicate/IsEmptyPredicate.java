package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;
import java.util.Collection;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.UnaryOperatorExpression;
import com.conx.logistics.kernel.criteria.domain.path.PluralAttributePath;

/**
 * Models an <tt>IS [NOT] EMPTY</tt> restriction
 *
 */
public class IsEmptyPredicate<C extends Collection>
		extends AbstractSimplePredicate
		implements UnaryOperatorExpression<Boolean>, Serializable {

	private final PluralAttributePath<C> collectionPath;

	public IsEmptyPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			PluralAttributePath<C> collectionPath) {
		super( criteriaBuilder );
		this.collectionPath = collectionPath;
	}

	public PluralAttributePath<C> getOperand() {
		return collectionPath;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(RenderingContext renderingContext) {
		final String operator = isNegated() ? " is not empty" : " is empty";
		return getOperand().render( renderingContext ) + operator;
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
