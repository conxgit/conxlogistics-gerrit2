package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

public interface IPageFlowListener {
	public void onNext(PageFlowPage currentPage, Map<String, Object> state);
	public void onPrevious(PageFlowPage currentPage, Map<String, Object> state);
}
