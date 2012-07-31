package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;

import javax.persistence.criteria.Subquery;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Models an <tt>EXISTS(<subquery>)</tt> predicate
 *
 */
public class ExistsPredicate
		extends AbstractSimplePredicate
		implements Serializable {
	private final Subquery<?> subquery;

	public ExistsPredicate(CriteriaBuilderImpl criteriaBuilder, Subquery<?> subquery) {
		super( criteriaBuilder );
		this.subquery = subquery;
	}

	public Subquery<?> getSubquery() {
		return subquery;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do here
	}

	public String render(RenderingContext renderingContext) {
		return ( isNegated() ? "not " : "" ) + "exists "
				+ ( (Renderable) getSubquery() ).render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
