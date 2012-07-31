package com.conx.logistics.kernel.criteria.domain;

import com.conx.logistics.kernel.criteria.domain.compile.RenderingContext;


public interface Renderable {
	public String render(RenderingContext renderingContext);
	public String renderProjection(RenderingContext renderingContext);
}
