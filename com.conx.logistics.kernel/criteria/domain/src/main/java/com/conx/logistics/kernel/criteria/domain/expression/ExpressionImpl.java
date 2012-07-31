package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ExpressionImplementor;
import com.conx.logistics.kernel.criteria.domain.expression.function.CastFunction;

/**
 * Models an expression in the criteria query language.
 *
 */
public abstract class ExpressionImpl<T>
		extends SelectionImpl<T>
		implements ExpressionImplementor<T>, Serializable {
	public ExpressionImpl(CriteriaBuilderImpl criteriaBuilder, Class<T> javaType) {
		super( criteriaBuilder, javaType );
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public <X> Expression<X> as(Class<X> type) {
		return type.equals( getJavaType() )
				? (Expression<X>) this
				: new CastFunction<X, T>( criteriaBuilder(), type, this );
	}

	/**
	 * {@inheritDoc}
	 */
	public Predicate isNull() {
		return criteriaBuilder().isNull( this );
	}

	/**
	 * {@inheritDoc}
	 */
	public Predicate isNotNull() {
		return criteriaBuilder().isNotNull( this );
	}

	/**
	 * {@inheritDoc}
	 */
    public Predicate in(Object... values) {
		return criteriaBuilder().in( this, values );
	}

	/**
	 * {@inheritDoc}
	 */
	public Predicate in(Expression<?>... values) {
		return criteriaBuilder().in( this, values );
	}

	/**
	 * {@inheritDoc}
	 */
	public Predicate in(Collection<?> values) {
		return criteriaBuilder().in( this, values.toArray() );
	}

	/**
	 * {@inheritDoc}
	 */
	public Predicate in(Expression<Collection<?>> values) {
		return criteriaBuilder().in( this, values );
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<Long> asLong() {
		resetJavaType( Long.class );
		return (ExpressionImplementor<Long>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<Integer> asInteger() {
		resetJavaType( Integer.class );
		return (ExpressionImplementor<Integer>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<Float> asFloat() {
		resetJavaType( Float.class );
		return (ExpressionImplementor<Float>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<Double> asDouble() {
		resetJavaType( Double.class );
		return (ExpressionImplementor<Double>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<BigDecimal> asBigDecimal() {
		resetJavaType( BigDecimal.class );
		return (ExpressionImplementor<BigDecimal>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<BigInteger> asBigInteger() {
		resetJavaType( BigInteger.class );
		return (ExpressionImplementor<BigInteger>) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public ExpressionImplementor<String> asString() {
		resetJavaType( String.class );
		return (ExpressionImplementor<String>) this;
	}
}
