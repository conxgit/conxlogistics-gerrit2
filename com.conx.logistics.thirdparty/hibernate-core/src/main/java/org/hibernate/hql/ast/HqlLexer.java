/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.hibernate.hql.ast;

import java.io.InputStream;
import java.io.Reader;

import antlr.Token;
import org.hibernate.QueryException;
import org.hibernate.hql.antlr.HqlBaseLexer;

/**
 * Custom lexer for the HQL grammar.  Extends the base lexer generated by ANTLR
 * in order to keep the grammar source file clean.
 */
class HqlLexer extends HqlBaseLexer {
	/**
	 * A logger for this class. *
	 */
	private boolean possibleID = false;

	public HqlLexer(InputStream in) {
		super( in );
	}

    public HqlLexer(Reader in) {
        super(in);
    }

	public void setTokenObjectClass(String cl) {
		// Ignore the token class name parameter, and use a specific token class.
		//super.setTokenObjectClass( HqlToken.class.getName() );
		tokenObjectClass = HqlToken.class;
	}

	protected void setPossibleID(boolean possibleID) {
		this.possibleID = possibleID;
	}

	protected Token makeToken(int i) {
		HqlToken token = ( HqlToken ) super.makeToken( i );
		token.setPossibleID( possibleID );
		possibleID = false;
		return token;
	}

	public int testLiteralsTable(int i) {
		int ttype = super.testLiteralsTable( i );
		return ttype;
	}

	public void panic() {
		//overriden to avoid System.exit
		panic("CharScanner: panic");
	}

	public void panic(String s) {
		//overriden to avoid System.exit
		throw new QueryException(s);
	}
}
