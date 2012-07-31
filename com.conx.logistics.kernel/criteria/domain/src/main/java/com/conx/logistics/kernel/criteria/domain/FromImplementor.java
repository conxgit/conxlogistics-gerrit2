package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.criteria.From;

import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Implementation contract for the JPA {@link From} interface.
 */
public interface FromImplementor<Z,X> extends PathImplementor<X>, From<Z,X> {
	public void prepareAlias(RenderingContext renderingContext);
	public String renderTableExpression(RenderingContext renderingContext);


	public FromImplementor<Z,X> correlateTo(CriteriaSubqueryImpl subquery);
	public void prepareCorrelationDelegate(FromImplementor<Z,X> parent);
	public FromImplementor<Z, X> getCorrelationParent();
}
