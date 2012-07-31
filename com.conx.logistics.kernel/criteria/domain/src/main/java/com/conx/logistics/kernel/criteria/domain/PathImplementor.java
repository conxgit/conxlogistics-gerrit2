package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;

/**
 * Implementation contract for the JPA {@link Path} interface.
 *
 */
public interface PathImplementor<X> extends ExpressionImplementor<X>, Path<X>, PathSource<X>, Renderable {
	/**
	 * Retrieve reference to the attribute this path represents.
	 *
	 * @return The metamodel attribute.
	 */
	public Attribute<?, ?> getAttribute();

	/**
	 * Defines handling for the JPA 2.1 TREAT down-casting feature.
	 *
	 * @param treatAsType The type to treat the path as.
	 * @param <T> The parameterized type representation of treatAsType.
	 *
	 * @return The properly typed view of this path.
	 */
	public <T extends X> PathImplementor<T> treatAs(Class<T> treatAsType);
}
