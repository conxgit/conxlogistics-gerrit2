package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;



/**
 * Models an ANSI SQL <tt>COALESCE</tt> expression.  <tt>COALESCE</tt> is a specialized <tt>CASE</tt> statement.
 *
 */
public class CoalesceExpression<T> extends ExpressionImpl<T> implements Coalesce<T>, Serializable {
	private final List<Expression<? extends T>> expressions;
	private Class<T> javaType;

	public CoalesceExpression(CriteriaBuilderImpl criteriaBuilder) {
		this( criteriaBuilder, null );
	}

	public CoalesceExpression(
			CriteriaBuilderImpl criteriaBuilder,
			Class<T> javaType) {
		super( criteriaBuilder, javaType );
		this.javaType = javaType;
		this.expressions = new ArrayList<Expression<? extends T>>();
	}

	@Override
	public Class<T> getJavaType() {
		return javaType;
	}

	public Coalesce<T> value(T value) {
		return value( new LiteralExpression<T>( criteriaBuilder(), value ) );
	}

	@SuppressWarnings({ "unchecked" })
	public Coalesce<T> value(Expression<? extends T> value) {
		expressions.add( value );
		if ( javaType == null ) {
			javaType = (Class<T>) value.getJavaType();
		}
		return this;
	}

	public List<Expression<? extends T>> getExpressions() {
		return expressions;
	}

	public void registerParameters(ParameterRegistry registry) {
		for ( Expression expression : getExpressions() ) {
			Helper.possibleParameter(expression, registry);
		}
	}

	public String render(RenderingContext renderingContext) {
		StringBuilder buffer = new StringBuilder( "coalesce(" );
		String sep = "";
		for ( Expression expression : getExpressions() ) {
			buffer.append( sep )
					.append( ( (Renderable) expression ).render( renderingContext ) );
			sep = ", ";
		}
		return buffer.append( ")" ).toString();
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
