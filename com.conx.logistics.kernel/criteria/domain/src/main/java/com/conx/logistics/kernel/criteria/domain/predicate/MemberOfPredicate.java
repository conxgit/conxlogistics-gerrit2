package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.LiteralExpression;
import com.conx.logistics.kernel.criteria.domain.path.PluralAttributePath;

/**
 * Models an <tt>[NOT] MEMBER OF</tt> restriction
 *
 */
public class MemberOfPredicate<E, C extends Collection<E>>
		extends AbstractSimplePredicate
		implements Serializable {

	private final Expression<E> elementExpression;
	private final PluralAttributePath<C> collectionPath;

	public MemberOfPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<E> elementExpression,
			PluralAttributePath<C> collectionPath) {
		super( criteriaBuilder );
		this.elementExpression = elementExpression;
		this.collectionPath = collectionPath;
	}

	public MemberOfPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			E element,
			PluralAttributePath<C> collectionPath) {
		this(
				criteriaBuilder,
				new LiteralExpression<E>( criteriaBuilder, element ),
				collectionPath
		);
	}

	public PluralAttributePath<C> getCollectionPath() {
		return collectionPath;
	}

	public Expression<E> getElementExpression() {
		return elementExpression;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getCollectionPath(), registry );
		Helper.possibleParameter( getElementExpression(), registry );
	}

	public String render(RenderingContext renderingContext) {
		return ( (Renderable) elementExpression ).render( renderingContext )
				+ ( isNegated() ? " not" : "" ) + " member of "
				+ getCollectionPath().render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
