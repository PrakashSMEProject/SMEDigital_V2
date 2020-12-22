package com.mindtree.devtools.b2b.vo;

public class CollectionMappingVO{
	/* The following fields are for handling deep-collection mappings. That is, mappings where values of deep elements in one 
	 * collection have to be mapped to deep fields within elements of another collection. 
	 * 
	 * Example for multi-element mapping:
	 * <field>
	 *     <a>a.b.c[].d</a>
	 *     <b>x.y[].z</b>
	 * </field>
	 * 
	 * Example for single-element mapping:
	 * <field>
	 *     <a>a.b.c[ 0 ].d</a>
	 *     <b>x.y[ 'KEY' ].z</b>
	 * </field>
	 * 
	 */
	private String srcAttrCollType;
	private String srcAttrAccessorMethodTillColl; //The part of the 
	private String srcAttrAccessorMethodAfterColl;
	private String srcAttrKeyInColl; //The key inside the collection (if it is a single mapping)
	private String destAttrCollType;
	private String destAttrMutatorMethodTillColl;
	private String destAttrMutatorMethodAfterColl;
	private String destAttrKeyInColl; //The key inside the collection (if it is a single mapping)

}
