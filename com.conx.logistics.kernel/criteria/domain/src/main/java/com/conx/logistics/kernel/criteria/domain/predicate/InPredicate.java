package com.conx.logistics.kernel.criteria.domain.predicate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Subquery;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.ValueHandlerFactory;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.LiteralExpression;

/**
 * Models an <tt>[NOT] IN</tt> restriction
 *
 */
public class InPredicate<T>
		extends AbstractSimplePredicate
		implements CriteriaBuilderImpl.In<T>, Serializable {
	private final Expression<? extends T> expression;
	private final List<Expression<? extends T>> values;

	/**
	 * Constructs an <tt>IN</tt> predicate against a given expression with an empty list of values.
	 *
	 * @param criteriaBuilder The query builder from which this originates.
	 * @param expression The expression.
	 */
	public InPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends T> expression) {
		this( criteriaBuilder, expression, new ArrayList<Expression<? extends T>>() );
	}

	/**
	 * Constructs an <tt>IN</tt> predicate against a given expression with the given list of expression values.
	 *
	 * @param criteriaBuilder The query builder from which this originates.
	 * @param expression The expression.
	 * @param values The value list.
	 */
	public InPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends T> expression,
			Expression<? extends T>... values) {
		this( criteriaBuilder, expression, Arrays.asList( values ) );
	}

	/**
	 * Constructs an <tt>IN</tt> predicate against a given expression with the given list of expression values.
	 *
	 * @param criteriaBuilder The query builder from which this originates.
	 * @param expression The expression.
	 * @param values The value list.
	 */
	public InPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends T> expression,
			List<Expression<? extends T>> values) {
		super( criteriaBuilder );
		this.expression = expression;
		this.values = values;
	}

	/**
	 * Constructs an <tt>IN</tt> predicate against a given expression with the given given literal value list.
	 *
	 * @param criteriaBuilder The query builder from which this originates.
	 * @param expression The expression.
	 * @param values The value list.
	 */
	public InPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends T> expression,
			T... values) {
		this( criteriaBuilder, expression, Arrays.asList( values ) );
	}

	/**
	 * Constructs an <tt>IN</tt> predicate against a given expression with the given literal value list.
	 *
	 * @param criteriaBuilder The query builder from which this originates.
	 * @param expression The expression.
	 * @param values The value list.
	 */
	public InPredicate(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<? extends T> expression,
			Collection<T> values) {
		super( criteriaBuilder );
		this.expression = expression;
		this.values = new ArrayList<Expression<? extends T>>( values.size() );
		ValueHandlerFactory.ValueHandler<? extends T> valueHandler = ValueHandlerFactory.isNumeric( expression.getJavaType() )
				? ValueHandlerFactory.determineAppropriateHandler( (Class<? extends T>) expression.getJavaType() )
				: new ValueHandlerFactory.NoOpValueHandler<T>();
		for ( T value : values ) {
			this.values.add(
					new LiteralExpression<T>( criteriaBuilder, valueHandler.convert( value ) )
			);
		}
	}

	@SuppressWarnings("unchecked")
	public Expression<T> getExpression() {
		return ( Expression<T> ) expression;
	}

	public Expression<? extends T> getExpressionInternal() {
		return expression;
	}

	public List<Expression<? extends T>> getValues() {
		return values;
	}

	public InPredicate<T> value(T value) {
		return value( new LiteralExpression<T>( criteriaBuilder(), value ) );
	}

	public InPredicate<T> value(Expression<? extends T> value) {
		values.add( value );
		return this;
	}

	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getExpressionInternal(), registry );
		for ( Expression value : getValues() ) {
			Helper.possibleParameter(value, registry);
		}
	}

	public String render(RenderingContext renderingContext) {
		StringBuilder buffer = new StringBuilder();

		buffer.append( ( (Renderable) getExpression() ).render( renderingContext ) );

		if ( isNegated() ) {
			buffer.append( " not" );
		}
		buffer.append( " in " );

		// subquery expressions are already wrapped in parenthesis, so we only need to
		// render the parenthesis here if the values represent an explicit value list
		boolean isInSubqueryPredicate = getValues().size() == 1
				&& Subquery.class.isInstance( getValues().get( 0 ) );
		if ( isInSubqueryPredicate ) {
			buffer.append( ( (Renderable) getValues().get(0) ).render( renderingContext ) );
		}
		else {
			buffer.append( '(' );
			String sep = "";
			for ( Expression value : getValues() ) {
				buffer.append( sep )
						.append( ( (Renderable) value ).render( renderingContext ) );
				sep = ", ";
			}
			buffer.append( ')' );
		}
		return buffer.toString();
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
