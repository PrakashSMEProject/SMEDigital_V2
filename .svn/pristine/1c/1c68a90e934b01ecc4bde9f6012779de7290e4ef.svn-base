package com.mindtree.devtools.b2b.sample;

import java.util.List;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

public class LevelTwo implements IFieldValue{
	private boolean yes;
	private UWQuestion[] uwq;
	private List<UWQuestion> uwqList;
	private String yesString;

	public String getYesString(){
		return yesString;
	}

	public void setYesString( String yesString ){
		this.yesString = yesString;
	}

	public boolean getYes(){
		return yes;
	}

	public boolean isYes(){
		return yes;
	}

	public void setYes( boolean yes ){
		this.yes = yes;
	}

	public UWQuestion[] getUwq(){
		return uwq;
	}

	public void setUwq( UWQuestion[] uwq ){
		this.uwq = uwq;
	}

	public List<UWQuestion> getUwqList(){
		return uwqList;
	}

	public void setUwqList( List<UWQuestion> uwqList ){
		this.uwqList = uwqList;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		if( "yes".equals( fieldName ) ) return getYes();
		if( "uwq".equals( fieldName ) ) return getUwq();
		if( "uwqList".equals( fieldName ) ) return getUwqList();
		return null;
	}
}
