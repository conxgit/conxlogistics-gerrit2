package com.conx.logistics.kernel.criteria.domain;

import javax.persistence.criteria.Path;

import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;

/**
 * Implementation contract for things which can be the source (parent, left-hand-side, etc) of a path
 *
 */
public interface PathSource<X> extends Path<X> {
	public void prepareAlias(RenderingContext renderingContext);

	/**
	 * Get the string representation of this path as a navigation from one of the
	 * queries <tt>identification variables</tt>
	 *
	 * @return The path's identifier.
	 */
	public String getPathIdentifier();
}
