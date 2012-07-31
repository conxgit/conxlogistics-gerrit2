package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;

import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Models unary arithmetic operation (unary plus and unary minus).
 *
 */
public class UnaryArithmeticOperation<T> 
		extends ExpressionImpl<T>
		implements UnaryOperatorExpression<T>, Serializable {

	public static enum Operation {
		UNARY_PLUS, UNARY_MINUS
	}

	private final Operation operation;
	private final Expression<T> operand;

	@SuppressWarnings({ "unchecked" })
	public UnaryArithmeticOperation(
			CriteriaBuilderImpl criteriaBuilder,
			Operation operation,
			Expression<T> operand) {
		super( criteriaBuilder, (Class)operand.getJavaType() );
		this.operation = operation;
		this.operand = operand;
	}

	public Operation getOperation() {
		return operation;
	}

	/**
	 * {@inheritDoc}
	 */
	public Expression<T> getOperand() {
		return operand;
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getOperand(), registry );
	}

	public String render(RenderingContext renderingContext) {
		return ( getOperation() == Operation.UNARY_MINUS ? '-' : '+' )
				+ ( (Renderable) getOperand() ).render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
