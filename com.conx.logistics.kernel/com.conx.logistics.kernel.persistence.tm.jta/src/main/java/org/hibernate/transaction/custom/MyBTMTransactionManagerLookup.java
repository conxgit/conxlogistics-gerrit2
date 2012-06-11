package org.hibernate.transaction.custom;

import java.util.Properties;

import javax.transaction.TransactionManager;
import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.transaction.TransactionManagerLookup;

/**
 * TransactionManager lookup strategy for BTM
 *
 * @author Ludovic Orban
 */
public class MyBTMTransactionManagerLookup implements TransactionManagerLookup {

	public MyBTMTransactionManagerLookup(){}
	/**
	 * {@inheritDoc}
	 */
	public TransactionManager getTransactionManager(Properties props) throws HibernateException {
		try {
			Class clazz = bitronix.tm.TransactionManagerServices.class;
			return (TransactionManager) clazz.getMethod("getTransactionManager", null).invoke(null, null);
		}
		catch (Exception e) {
			throw new HibernateException( "Could not obtain BTM transaction manager instance", e );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserTransactionName() {
		return "java:comp/UserTransaction";
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getTransactionIdentifier(Transaction transaction) {
		return transaction;
	}
}
