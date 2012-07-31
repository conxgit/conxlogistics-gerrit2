package com.conx.logistics.kernel.criteria.domain.expression.function;

import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;
import com.conx.logistics.kernel.criteria.domain.expression.LiteralExpression;


/**
 * Models the ANSI SQL <tt>TRIM</tt> function.
 *
 */
public class TrimFunction
		extends BasicFunctionExpression<String>
		implements Serializable {
	public static final String NAME = "trim";
	public static final Trimspec DEFAULT_TRIMSPEC = Trimspec.BOTH;
	public static final char DEFAULT_TRIM_CHAR = ' ';

	private final Trimspec trimspec;
	private final Expression<Character> trimCharacter;
	private final Expression<String> trimSource;

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Trimspec trimspec,
			Expression<Character> trimCharacter,
			Expression<String> trimSource) {
		super( criteriaBuilder, String.class, NAME );
		this.trimspec = trimspec;
		this.trimCharacter = trimCharacter;
		this.trimSource = trimSource;
	}

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Trimspec trimspec,
			char trimCharacter,
			Expression<String> trimSource) {
		super( criteriaBuilder, String.class, NAME );
		this.trimspec = trimspec;
		this.trimCharacter = new LiteralExpression<Character>( criteriaBuilder, trimCharacter );
		this.trimSource = trimSource;
	}

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<String> trimSource) {
		this( criteriaBuilder, DEFAULT_TRIMSPEC, DEFAULT_TRIM_CHAR, trimSource );
	}

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Expression<Character> trimCharacter,
			Expression<String> trimSource) {
		this( criteriaBuilder, DEFAULT_TRIMSPEC, trimCharacter, trimSource );
	}

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			char trimCharacter,
			Expression<String> trimSource) {
		this( criteriaBuilder, DEFAULT_TRIMSPEC, trimCharacter, trimSource );
	}

	public TrimFunction(
			CriteriaBuilderImpl criteriaBuilder,
			Trimspec trimspec,
			Expression<String> trimSource) {
		this( criteriaBuilder, trimspec, DEFAULT_TRIM_CHAR, trimSource );
	}

	public Expression<Character> getTrimCharacter() {
		return trimCharacter;
	}

	public Expression<String> getTrimSource() {
		return trimSource;
	}

	public Trimspec getTrimspec() {
		return trimspec;
	}

	@Override
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getTrimCharacter(), registry );
		Helper.possibleParameter( getTrimSource(), registry );
	}

	@Override
	public String render(RenderingContext renderingContext) {
		return new StringBuilder()
				.append( "trim(" )
				.append( trimspec.name() )
				.append( ' ' )
				.append( ( (Renderable) trimCharacter ).render( renderingContext ) )
				.append( " from " )
				.append( ( (Renderable) trimSource ).render( renderingContext ) )
				.append( ')' )
				.toString();
	}
}
