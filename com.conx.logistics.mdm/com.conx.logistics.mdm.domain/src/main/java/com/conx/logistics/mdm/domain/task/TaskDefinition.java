package com.conx.logistics.mdm.domain.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;
import com.conx.logistics.mdm.domain.application.Application;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="mdmtaskdefinition")
public class TaskDefinition extends MultitenantBaseEntity {
    private String bpmn2ProcDefURL;
    private String processId;
    
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn
    @NotNull
    protected Application parentApplication;     

	public String getBpmn2ProcDefURL() {
		return bpmn2ProcDefURL;
	}

	public void setBpmn2ProcDefURL(String bpmn2ProcDefURL) {
		this.bpmn2ProcDefURL = bpmn2ProcDefURL;
	}

	public Application getParentApplication() {
		return parentApplication;
	}

	public void setParentApplication(Application parentApplication) {
		this.parentApplication = parentApplication;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
