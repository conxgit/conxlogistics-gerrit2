package com.conx.logistics.kernel.bpm.impl.jbpm.core.mock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.process.Node;
import org.drools.definition.process.WorkflowProcess;
import org.drools.io.ResourceFactory;
import org.jbpm.workflow.core.node.HumanTaskNode;

import com.conx.logistics.kernel.bpm.services.IBPMProcessInstance;
import com.conx.logistics.kernel.bpm.services.IBPMService;
import com.conx.logistics.kernel.bpm.services.IBPMTask;

public class MockBPMServiceImpl implements IBPMService {
	public static final String GUVNOR_PROTOCOL_KEY = "guvnor.protocol";
    public static final String GUVNOR_HOST_KEY = "guvnor.host";
    public static final String GUVNOR_USR_KEY = "guvnor.usr";
    public static final String GUVNOR_PWD_KEY = "guvnor.pwd";
    public static final String GUVNOR_PACKAGES_KEY = "guvnor.packages";
    public static final String GUVNOR_SUBDOMAIN_KEY = "guvnor.subdomain";
    public static final String GUVNOR_CONNECTTIMEOUT_KEY = "guvnor.connect.timeout";
    public static final String GUVNOR_READTIMEOUT_KEY = "guvnor.read.timeout";
    public static final String GUVNOR_SNAPSHOT_NAME = "guvnor.snapshot.name";
    public static final String EXT_BPMN = "bpmn";
    public static final String EXT_BPMN2 = "bpmn2";
    
	private KnowledgeBase kbase;
	private Properties properties;
	
	public void start() {
		properties = getProperties();
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		addRemoteProcessDefinition("whse.rcv.asn", "test", kbuilder);
		kbase = kbuilder.newKnowledgeBase();
		getProcessTasks("whse.rcv.asn");
	}
	
	public void stop() {
		
	}
	
	private void addRemoteProcessDefinition(String packageName, String assetName, KnowledgeBuilder kbuilder) {
        String assetSourceURL = getProperty(GUVNOR_PROTOCOL_KEY)
                + "://"
                + getProperty(GUVNOR_HOST_KEY)
                + "/"
                + getProperty(GUVNOR_SUBDOMAIN_KEY)
                + "/rest/packages/" + packageName + "/assets/" + assetName
                + "/source/";

        try {
            InputStream in = getInputStreamForURL(assetSourceURL, "GET");
            kbuilder.add(ResourceFactory.newInputStreamResource(in), ResourceType.BPMN2); 
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	private InputStream getInputStreamForURL(String urlLocation,
            String requestMethod) throws Exception {
        URL url = new URL(urlLocation);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(requestMethod);
        connection
        .setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16");
        connection.setRequestProperty("Accept", "text/plain,text/html,application/xhtml+xml,application/xml");
        connection.setRequestProperty("charset", "UTF-8");
        connection.setConnectTimeout(Integer.parseInt(getProperty(GUVNOR_CONNECTTIMEOUT_KEY)));
        connection.setReadTimeout(Integer.parseInt(getProperty(GUVNOR_READTIMEOUT_KEY)));
        applyAuth(connection);
        connection.connect();

        BufferedReader sreader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String line = null;
        while ((line = sreader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        
        return new ByteArrayInputStream(stringBuilder.toString().getBytes(
                "UTF-8"));
    }
	
	protected void applyAuth(HttpURLConnection connection) {
		String auth = getProperty(GUVNOR_USR_KEY) + ":" + getProperty(GUVNOR_PWD_KEY);
		String encodedString = "Basic " + Base64.encodeBase64String(auth.getBytes());
		encodedString = encodedString.replace("\r\n", "");
		connection.setRequestProperty("Authorization", encodedString);
	}
	
	private String getProperty(String prop) {
        if(!isEmpty(properties.getProperty(prop))) {
            String retStr = properties.getProperty(prop).trim();
            if(retStr.startsWith("/")){
                retStr = retStr.substring(1);
            }
            if(retStr.endsWith("/")) {
                retStr = retStr.substring(0,retStr.length() - 1);
            }
            return retStr;
        } else {
            return "";
        }
    }
	
	private boolean isEmpty(final CharSequence str) {
        if ( str == null || str.length() == 0 ) {
            return true;
        }
        for ( int i = 0, length = str.length(); i < length; i++ ){
            if ( str.charAt( i ) != ' ' ) {
                return false;
            }
        }
        return true;
    }
	
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
		}
		try {
			properties.load(MockBPMServiceImpl.class
					.getResourceAsStream("/jbpm.conxrepo.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load jbpm.conxrepo.properties", e);
		}

		return properties;
	}
	
	public MockBPMServiceImpl() {
	}

	@Override
	public IBPMProcessInstance getProcessInstance(String processId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, IBPMTask> getProcessTasks(String processId) {
		Map<String, IBPMTask> map = new HashMap<String, IBPMTask>();
		org.drools.definition.process.Process proc = kbase.getProcess(processId);
		if (proc != null) {
			Node[] nodes = ((WorkflowProcess) proc).getNodes();
			for (Node n : nodes) {
				if (n instanceof HumanTaskNode) {
					map.put(((HumanTaskNode)n).getUniqueId(), new BPMTaskImpl((HumanTaskNode)n, processId));
				}
			}
			return map;
		}
		return null;
	}

	@Override
	public IBPMProcessInstance startNewProcess(String userId, String processId) {
		// TODO Auto-generated method stub
		return null;
	}

}
