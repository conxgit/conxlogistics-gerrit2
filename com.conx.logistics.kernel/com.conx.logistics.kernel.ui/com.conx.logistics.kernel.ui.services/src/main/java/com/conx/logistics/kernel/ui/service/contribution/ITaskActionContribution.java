package com.conx.logistics.kernel.ui.service.contribution;

import java.util.Map;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public interface ITaskActionContribution extends IActionContribution {
	public Component execute(Application application, Map<String,Object> properties) throws Exception;
}
