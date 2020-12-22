/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1016303
 *
 */
public class NonStandardClause extends ClauseVO {
	private static final long serialVersionUID = 1L;
	
	
	private long policyId;
	
	public NonStandardClause(){
		// TODO Auto-generated constructor stub
	}

	public long getPolicyId(){
		return policyId;
	}

	public void setPolicyId( long policyId ){
		this.policyId = policyId;
	}

	
	
	
}
