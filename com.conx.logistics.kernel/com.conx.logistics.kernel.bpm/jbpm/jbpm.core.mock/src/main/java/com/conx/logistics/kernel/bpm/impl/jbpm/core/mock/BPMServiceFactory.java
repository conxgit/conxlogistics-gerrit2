package com.conx.logistics.kernel.bpm.impl.jbpm.core.mock;

import com.conx.logistics.kernel.bpm.impl.jbpm.core.mock.MockBPMServiceImpl;
import com.conx.logistics.kernel.bpm.services.IBPMService;

public class BPMServiceFactory {
	public static IBPMService getBPMService() {
		return new MockBPMServiceImpl();
	}
}
