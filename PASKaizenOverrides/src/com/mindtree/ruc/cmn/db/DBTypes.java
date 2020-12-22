package com.mindtree.ruc.cmn.db;

/**
 * A constants enum to define DB types. Currently follows the values specified by <code>oracle.jdbc.OracleTypes</code>. 
 */
public enum DBTypes{
	ROWID( -8 ),
	BIT( -7 ),
	TINYINT( -6 ),
	BIGINT( -5 ),
	LONGVARBINARY( -4 ),
	VARBINARY( -3 ),
	BINARY( -2 ),
	RAW( -2 ),
	LONGVARCHAR( -1 ),
	NULL( 0 ),
	CHAR( 1 ),
	NUMBER( 2 ),
	DECIMAL( 3 ),
	INTEGER( 4 ),
	SMALLINT( 5 ),
	FLOAT( 6 ),
	REAL( 7 ),
	DOUBLE( 8 ),
	VARCHAR( 12 ),
	BOOLEAN( 16 ),
	DATE( 91 ),
	TIME( 92 ),
	TIMESTAMP( 93 ),
	BINARY_FLOAT( 100 ),
	BINARY_DOUBLE( 101 ),
	JAVA_OBJECT( 2000 ),
	ARRAY( 2003 ),
	BLOB( 2004 ),
	CLOB( 2005 );
	
	private int typeId;

	/**
	 * Returns the type number associated with the named type.
	 */
	public int getTypeId(){
		return typeId;
	}

	private DBTypes( int typeId ){
		this.typeId = typeId;
	}
}
