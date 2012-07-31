package com.conx.logistics.kernel.criteria.domain.expression;

import java.io.Serializable;

import javax.persistence.criteria.Subquery;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Represents a {@link Modifier#ALL}, {@link Modifier#ANY}, {@link Modifier#SOME} modifier appplied to a subquery as
 * part of a comparison.
 *
 */
public class SubqueryComparisonModifierExpression<Y>
		extends ExpressionImpl<Y>
		implements Serializable {
	public static enum Modifier {
		ALL {
			String rendered() {
				return "all ";
			}
		},
		SOME {
			String rendered() {
				return "some ";
			}
		},
		ANY {
			String rendered() {
				return "any ";
			}
		};
		abstract String rendered();
	}

	private final Subquery<Y> subquery;
	private final Modifier modifier;

	public SubqueryComparisonModifierExpression(
			CriteriaBuilderImpl criteriaBuilder,
			Class<Y> javaType,
			Subquery<Y> subquery,
			Modifier modifier) {
		super( criteriaBuilder, javaType);
		this.subquery = subquery;
		this.modifier = modifier;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public Subquery<Y> getSubquery() {
		return subquery;
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do (the subquery should be handled directly, and the modified itself is not parameterized)
	}

	public String render(RenderingContext renderingContext) {
		return getModifier().rendered() + ( (Renderable) getSubquery() ).render( renderingContext );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
