package com.conx.logistics.client.web.app.mock.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conx.logistics.client.web.app.mock.MockApp;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@Service
@Transactional
public class MockAppServlet extends AbstractApplicationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505600789309312501L;

	protected final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	static private MockApp mainApp;

	public MockApp getMainApp() {
		return mainApp;
	}

	@Autowired
	public void setMainApp(MockApp mainApp) {
		MockAppServlet.mainApp = mainApp;
	}

    @Override
    protected Class<? extends Application> getApplicationClass() {
        return MockApp.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request)	throws ServletException {
        return mainApp;
    }
}
