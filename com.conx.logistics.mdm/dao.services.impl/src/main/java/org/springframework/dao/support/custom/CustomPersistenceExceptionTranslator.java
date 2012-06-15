package org.springframework.dao.support.custom;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaDialect;

import ch.qos.logback.core.db.dialect.MySQLDialect;

public class CustomPersistenceExceptionTranslator implements
		PersistenceExceptionTranslator {
	
	private JpaDialect jpaDialect;

	
	public void setJpaDialect(JpaDialect jpaDialect) {
		this.jpaDialect = jpaDialect;
	}

	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return (this.jpaDialect != null ? this.jpaDialect.translateExceptionIfPossible(ex) :
				EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(ex));
	}

}
