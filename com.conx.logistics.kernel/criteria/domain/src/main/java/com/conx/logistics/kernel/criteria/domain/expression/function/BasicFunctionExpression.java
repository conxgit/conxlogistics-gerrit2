package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.ExpressionImpl;


/**
 * Models the basic concept of a SQL function.
 *
 */
public class BasicFunctionExpression<X>
		extends ExpressionImpl<X>
		implements FunctionExpression<X>, Serializable {

	private final String functionName;

	public BasicFunctionExpression(
			CriteriaBuilderImpl criteriaBuilder,
			Class<X> javaType,
			String functionName) {
		super( criteriaBuilder, javaType );
		this.functionName = functionName;
	}

	protected  static int properSize(int number) {
		return number + (int)( number*.75 ) + 1;
	}

	public String getFunctionName() {
		return functionName;
	}

	public boolean isAggregation() {
		return false;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do here...
	}

	public String render(RenderingContext renderingContext) {
		return getFunctionName() + "()";
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
