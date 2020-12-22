package com.rsaame.pas.cmn.access;

/**
 * This class represents a basic resource to which an access restriction can be applied.
 */
public class Resource{
	/**
	 * The code for the resource. This can be defined the way the resource is used. Eg, in the case
	 * of functionalities, these may be 2-3 letter codes.
	 */
	private String code;
	
	/**
	 * The name of the resource.
	 */
	private String name;

	public enum AccessType{
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

	public String getCode(){
		return code;
	}

	public void setCode( String code ){
		this.code = code;
	}

	public String getName(){
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}
}
