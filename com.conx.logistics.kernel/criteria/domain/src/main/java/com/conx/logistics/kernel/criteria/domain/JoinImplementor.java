package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

/**
 * Consolidates the {@link Join} and {@link Fetch} hierarchies since that is how we implement them.
 * This allows us to treat them polymorphically.
*
*/
public interface JoinImplementor<Z,X> extends Join<Z,X>, Fetch<Z,X>, FromImplementor<Z,X> {
	/**
	 * Refined return type
	 */
	@Override
	public JoinImplementor<Z,X> correlateTo(CriteriaSubqueryImpl subquery);

	/**
	 * Coordinate return type between {@link Join#on(Expression)} and {@link Fetch#on(Expression)}
	 */
	@Override
	public JoinImplementor<Z, X> on(Expression<Boolean> restriction);

	/**
	 * Coordinate return type between {@link Join#on(Predicate...)} and {@link Fetch#on(Predicate...)}
	 */
	@Override
	public JoinImplementor<Z, X> on(Predicate... restrictions);

	@Override
	public <T extends X> JoinImplementor<Z, T> treatAs(Class<T> treatAsType);
}
