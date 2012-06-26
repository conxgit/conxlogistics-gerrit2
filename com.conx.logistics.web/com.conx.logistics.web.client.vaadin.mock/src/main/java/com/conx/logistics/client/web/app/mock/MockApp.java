package com.conx.logistics.client.web.app.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.conx.logistics.kernel.pageflow.services.IPageFlowManager;
import com.conx.logistics.kernel.pageflow.services.IPageFlowSession;
import com.conx.logistics.mdm.domain.task.TaskDefinition;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Service
@Transactional
public class MockApp extends Application {
	private static final long serialVersionUID = -5470222303880854277L;
	private IPageFlowSession pfs;
	private IPageFlowManager defaultPageFlowEngine;
	
	@Autowired
	private PlatformTransactionManager globalJtaTransactionManager;

	public void start() {
	}
	
	public void stop() {
	}

	@Override
	public void init() {
		Window w = new Window();
		w.setSizeFull();
		
		TaskDefinition td = new TaskDefinition();
		td.setBpmn2ProcDefURL("");
		td.setProcessId("whse.rcv.asn");
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("web.app.mock");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.globalJtaTransactionManager.getTransaction(def);	
		
		try
		{
			pfs = defaultPageFlowEngine.startPageFlowSession("skeswa", td);
			w.addComponent(pfs.getWizardComponent());
			this.globalJtaTransactionManager.commit(status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.globalJtaTransactionManager.rollback(status);
		}
		
		this.setMainWindow(w);
	}

	public IPageFlowManager getDefaultPageFlowEngine() {
		return defaultPageFlowEngine;
	}

	public void setDefaultPageFlowEngine(IPageFlowManager defaultPageFlowEngine) {
		this.defaultPageFlowEngine = defaultPageFlowEngine;
	}
}
