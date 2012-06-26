package org.jbpm.task.service.persistence;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.drools.persistence.TransactionManager;
import org.drools.persistence.jta.JtaTransactionManager;

public class TaskJTACustomTransactionManager implements TaskTransactionManager {
	   private org.drools.persistence.TransactionManager tm;

	   TaskJTACustomTransactionManager(javax.transaction.TransactionManager tm,
			                           UserTransaction ut) {
	        this.tm = new JtaTransactionManager(ut, null, tm);
	    }

	    public void attachPersistenceContext(EntityManager em) { 
	        em.joinTransaction();
	    }
	    
	    public boolean begin(EntityManager em) {
	        int status = getStatus(em);
	        boolean begun = false;
	        if( status == TransactionManager.STATUS_NO_TRANSACTION 
	            || status == TransactionManager.STATUS_COMMITTED
	            || status == TransactionManager.STATUS_ROLLEDBACK ) { 
	            begun =  tm.begin();
	        }
	        return begun;
	    }

	    public void commit(EntityManager em, boolean txOwner) {
	        tm.commit(txOwner);
	    }

	    public void rollback(EntityManager em, boolean txOwner) {
	        switch(tm.getStatus()) { 
	        case TransactionManager.STATUS_COMMITTED:
	        case TransactionManager.STATUS_NO_TRANSACTION:
	        case TransactionManager.STATUS_ROLLEDBACK:
	            // do nothing
	            break;
	        default:
	            tm.rollback(txOwner);
	        }
	    }

	    public int getStatus(EntityManager em) {
	        return tm.getStatus();
	    }

	    public void dispose() {
	        tm = null;
	    }
}
