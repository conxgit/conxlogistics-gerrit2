package com.conx.logistics.kernel.pageflow.event;

public interface IPageFlowPageChangedEventHandler {
	public void registerForPageFlowPageChanged(IPageFlowPageChangedListener listener);
	public void unregisterForPageFlowPageChanged(IPageFlowPageChangedListener listener);
	public void enablePageFlowPageChangedEventHandling(boolean enable);
	public void fireOnPageFlowChanged(PageFlowPageChangedEvent event);
}
