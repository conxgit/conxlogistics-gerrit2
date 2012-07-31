package com.conx.logistics.kernel.criteria.domain.expression;


import java.io.Serializable;
import java.util.Map;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.MapAttribute;

import com.conx.logistics.kernel.criteria.domain.CriteriaBuilderImpl;
import com.conx.logistics.kernel.criteria.domain.ParameterRegistry;
import com.conx.logistics.kernel.criteria.domain.PathImplementor;
import com.conx.logistics.kernel.criteria.domain.Renderable;
import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;


public class MapEntryExpression<K,V>
		extends ExpressionImpl<Map.Entry<K,V>>
		implements Expression<Map.Entry<K,V>>, Serializable {

	private final PathImplementor origin;
	private final MapAttribute<?, K, V> attribute;

	public MapEntryExpression(
			CriteriaBuilderImpl criteriaBuilder,
			Class<Map.Entry<K, V>> javaType,
			PathImplementor origin,
			MapAttribute<?, K, V> attribute) {
		super( criteriaBuilder, javaType);
		this.origin = origin;
		this.attribute = attribute;
	}

	public MapAttribute<?, K, V> getAttribute() {
		return attribute;
	}

	public void registerParameters(ParameterRegistry registry) {
		// none to register
	}

	public String render(RenderingContext renderingContext) {
		// don't think this is valid outside of select clause...
		throw new IllegalStateException( "illegal reference to map entry outside of select clause." );
	}

	public String renderProjection(RenderingContext renderingContext) {
		return "entry(" + path( renderingContext ) + ")";
	}

	private String path(RenderingContext renderingContext) {
		return origin.getPathIdentifier()
				+ '.'
				+ ( (Renderable) getAttribute() ).renderProjection( renderingContext );
	}
}
