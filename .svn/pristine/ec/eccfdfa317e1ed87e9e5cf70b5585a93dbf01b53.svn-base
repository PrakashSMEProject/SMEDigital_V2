package com.rsaame.pas.cmn.access;

/**
 * This class represents one functionality in the application.
 */
public class Functionality extends Resource{
	private FunctionalityAccessType accessType;
	
	public enum FunctionalityAccessType{
		/* Please don't change the order. The enum elements' order is expected
		 * to be used.
		 */
		NO_ACCESS, 	//The user has no access to the functionality
		SECTION, 	//The section for the functionality will be present in the UI 
		VIEW,		//The user should be able to view the functionality (read-only mode)
		EDIT,		//The user should be able to edit an existing data element, but cannot create
		CREATE,		//The user should be able to create a new data element
		ALL;		//The user has all kinds of accesses on the functionality
	}

	public FunctionalityAccessType getAccessType(){
		return accessType;
	}

	public void setAccessType( FunctionalityAccessType accessType ){
		this.accessType = accessType;
	}
}
