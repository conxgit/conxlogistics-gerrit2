package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.util.Collection;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.path.PluralAttributePath;

/**
 * Represents a "size of" expression in regards to a persistent collection; the implication is
 * that of a subquery.
 *
 */
public class SizeOfCollectionExpression<C extends Collection>
		extends ExpressionImpl<Integer>
		implements Serializable {
	private final PluralAttributePath<C> collectionPath;

	public SizeOfCollectionExpression(
			CriteriaBuilderImpl criteriaBuilder,
			PluralAttributePath<C> collectionPath) {
		super( criteriaBuilder, Integer.class);
		this.collectionPath = collectionPath;
	}

	public PluralAttributePath<C> getCollectionPath() {
		return collectionPath;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(RenderingContext renderingContext) {
		return "size(" + getCollectionPath().render( renderingContext ) + ")";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
