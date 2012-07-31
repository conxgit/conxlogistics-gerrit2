package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.LiteralExpression;

/**
 * Models the ANSI SQL <tt>LOCATE</tt> function.
 *
 */
public class LocateFunction
		extends BasicFunctionExpression<Integer>
		implements Serializable {
	public static final String NAME = "locate";

	private final Expression<String> pattern;
	private final Expression<String> string;
	private final Expression<Integer> start;

	public LocateFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<String> pattern,
			Expression<String> string,
			Expression<Integer> start) {
		super( criteriaBuilder, Integer.class, NAME );
		this.pattern = pattern;
		this.string = string;
		this.start = start;
	}

	public LocateFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<String> pattern,
			Expression<String> string) {
		this( criteriaBuilder, pattern, string, null );
	}

	public LocateFunction(CriteriaBuilderImpl criteriaBuilder, String pattern, Expression<String> string) {
		this(
				criteriaBuilder,
				new LiteralExpression<String>( criteriaBuilder, pattern ),
				string,
				null
		);
	}

	public LocateFunction(CriteriaBuilderImpl criteriaBuilder, String pattern, Expression<String> string, int start) {
		this(
				criteriaBuilder,
				new LiteralExpression<String>( criteriaBuilder, pattern ),
				string,
				new LiteralExpression<Integer>( criteriaBuilder, start )
		);
	}

	public Expression<String> getPattern() {
		return pattern;
	}

	public Expression<Integer> getStart() {
		return start;
	}

	public Expression<String> getString() {
		return string;
	}

	@Override
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getPattern(), registry );
		Helper.possibleParameter( getStart(), registry );
		Helper.possibleParameter( getString(), registry );
	}

	@Override
	public String render(RenderingContext renderingContext) {
		StringBuilder buffer = new StringBuilder();
		buffer.append( "locate(" )
				.append( ( (Renderable) getPattern() ).render( renderingContext ) )
				.append( ',' )
				.append( ( (Renderable) getString() ).render( renderingContext ) );
		if ( getStart() != null ) {
			buffer.append( ',' )
					.append( ( (Renderable) getStart() ).render( renderingContext ) );
		}
		buffer.append( ')' );
		return buffer.toString();
	}
}
