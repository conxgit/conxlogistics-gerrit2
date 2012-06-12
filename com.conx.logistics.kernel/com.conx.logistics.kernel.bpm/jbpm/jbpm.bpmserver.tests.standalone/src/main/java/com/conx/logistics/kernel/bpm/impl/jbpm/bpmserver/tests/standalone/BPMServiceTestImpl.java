package com.conx.logistics.kernel.bpm.impl.jbpm.bpmserver.tests.standalone;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;

public class BPMServiceTestImpl {
	private IBPMService bpmService;

	public void setBpmService(IBPMService bpmService) {
		try {
			this.bpmService = bpmService;
			//bpmService.startNewProcess("","");
			System.out.println("Test");
		} 
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
		}	
		catch (Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
		}		
	}
	
	private void start()
	{
	}
	
	private void stop()
	{
	}
}
