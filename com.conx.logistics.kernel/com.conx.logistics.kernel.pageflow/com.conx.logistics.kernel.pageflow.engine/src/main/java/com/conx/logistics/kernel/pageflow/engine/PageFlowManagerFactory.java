package com.conx.logistics.kernel.pageflow.engine;

import com.conx.logistics.kernel.pageflow.engine.PageFlowEngineImpl;
import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;

public class PageFlowManagerFactory {
	public static IPageFlowManager createPageFlowManager() {
		return new PageFlowEngineImpl();
	}
}