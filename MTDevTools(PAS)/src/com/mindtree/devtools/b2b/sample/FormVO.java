package com.mindtree.devtools.b2b.sample;

import java.util.List;

import com.mindtree.ruc.cmn.reflect.IFieldValue;


public class FormVO implements IFieldValue{
	private java.util.Date effDate;
	private LevelTwo levelTwo;
	private List<String> uwq;
	
	public java.util.Date getEffDate(){
		return effDate;
	}
	public void setEffDate( java.util.Date effDate ){
		this.effDate = effDate;
	}
	public LevelTwo getLevelTwo(){
		return levelTwo;
	}
	public void setLevelTwo( LevelTwo levelTwo ){
		this.levelTwo = levelTwo;
	}
	public List<String> getUwq(){
		return uwq;
	}
	public void setUwq( List<String> uwq ){
		this.uwq = uwq;
	}
	@Override
	public Object getFieldValue( String fieldName ){
		if( "effDate".equals( fieldName ) ) return getEffDate();
		if( "levelTwo".equals( fieldName ) ) return getLevelTwo();
		if( "uwq".equals( fieldName ) ) return getUwq();
		return null;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append( "effDate [" ).append( this.effDate ).append( "]\r\n" );
		sb.append( "levelTwo.yes [" ).append( this.levelTwo.getYes() ).append( "]\r\n" );
		sb.append( "uwq " ).append( this.uwq ).append( "\r\n" );
		
		return sb.toString();
	}
}
