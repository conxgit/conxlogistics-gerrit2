package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.ValueHandlerFactory;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;


/**
 * Represents a literal expression.
 *
 */
public class LiteralExpression<T> extends ExpressionImpl<T> implements Serializable {
	private Object literal;

	@SuppressWarnings({ "unchecked" })
	public LiteralExpression(CriteriaBuilderImpl criteriaBuilder, T literal) {
		this( criteriaBuilder, (Class<T>) determineClass( literal ), literal );
	}

	private static Class determineClass(Object literal) {
		return literal == null ? null : literal.getClass();
	}

	public LiteralExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> type, T literal) {
		super( criteriaBuilder, type );
		this.literal = literal;
	}

	@SuppressWarnings({ "unchecked" })
	public T getLiteral() {
		return (T) literal;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	@SuppressWarnings({ "unchecked" })
	public String render(RenderingContext renderingContext) {
		if ( ValueHandlerFactory.isNumeric( literal ) ) {
			return ValueHandlerFactory.determineAppropriateHandler( (Class) literal.getClass() ).render( literal );
		}

		// else...
		final String parameterName = renderingContext.registerLiteralParameterBinding( getLiteral(), getJavaType() );
		return ':' + parameterName;
	}

	@SuppressWarnings({ "unchecked" })
	public String renderProjection(RenderingContext renderingContext) {
		// some drivers/servers do not like parameters in the select clause
		final ValueHandlerFactory.ValueHandler handler =
				ValueHandlerFactory.determineAppropriateHandler( literal.getClass() );
		if ( ValueHandlerFactory.isCharacter( literal ) ) {
			return '\'' + handler.render( literal ) + '\'';
		}
		else {
			return handler.render( literal );
		}
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	protected void resetJavaType(Class targetType) {
		super.resetJavaType( targetType );
		ValueHandlerFactory.ValueHandler valueHandler = getValueHandler();
		if ( valueHandler == null ) {
			valueHandler = ValueHandlerFactory.determineAppropriateHandler( targetType );
			forceConversion( valueHandler );
		}

		if ( valueHandler != null ) {
			literal = valueHandler.convert( literal );
		}
	}
}
