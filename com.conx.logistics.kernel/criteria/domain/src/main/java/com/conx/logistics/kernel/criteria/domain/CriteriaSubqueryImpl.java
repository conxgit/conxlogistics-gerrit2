package com.conx.logistics.kernel.criteria.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.DelegatedExpressionImpl;
import com.conx.logistics.kernel.criteria.domain.expression.ExpressionImpl;

/**
 * The Hibernate implementation of the JPA {@link Subquery} contract.  Mostlty a set of delegation to its internal
 * {@link QueryStructure}.
 *
 */
public class CriteriaSubqueryImpl<T> extends ExpressionImpl<T> implements Subquery<T>, Serializable {
	private final CommonAbstractCriteria parent;
	private final QueryStructure<T> queryStructure;

	public CriteriaSubqueryImpl(
			CriteriaBuilderImpl criteriaBuilder,
			Class<T> javaType,
			CommonAbstractCriteria parent) {
		super( criteriaBuilder, javaType);
		this.parent = parent;
		this.queryStructure = new QueryStructure<T>( this, criteriaBuilder );
	}

	@Override
	public AbstractQuery<?> getParent() {
		if ( ! AbstractQuery.class.isInstance( parent ) ) {
			throw new IllegalStateException( "Cannot call getParent on update/delete criterias" );
		}
		return (AbstractQuery<?>) parent;
	}

	public CommonAbstractCriteria getContainingQuery() {
		return parent;
	}


	@Override
	public Class<T> getResultType() {
		return getJavaType();
	}

	@Override
	public <X> Root<X> from(Class<X> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> Root<X> from(EntityType<X> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Subquery<U> subquery(Class<U> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Root<?>> getRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate getRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Expression<?>> getGroupList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate getGroupRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String render(RenderingContext renderingContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderProjection(RenderingContext renderingContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerParameters(ParameterRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Subquery<T> select(Expression<T> expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> groupBy(Expression<?>... grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> groupBy(List<Expression<?>> grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> having(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> having(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subquery<T> distinct(boolean distinct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <Y> Root<Y> correlate(Root<Y> parentRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X, Y> Join<X, Y> correlate(Join<X, Y> parentJoin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X, Y> CollectionJoin<X, Y> correlate(
			CollectionJoin<X, Y> parentCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X, Y> SetJoin<X, Y> correlate(SetJoin<X, Y> parentSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X, Y> ListJoin<X, Y> correlate(ListJoin<X, Y> parentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X, K, V> MapJoin<X, K, V> correlate(MapJoin<X, K, V> parentMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Join<?, ?>> getCorrelatedJoins() {
		// TODO Auto-generated method stub
		return null;
	}

}
