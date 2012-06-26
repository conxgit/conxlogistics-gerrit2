package com.conx.logistics.kernel.pageflow.services;

import java.util.Map;

public interface IPageFlowListener {
	public void onNext(IPageFlowPage currentPage, Map<String, Object> state);
	public void onPrevious(IPageFlowPage currentPage, Map<String, Object> state);
}
