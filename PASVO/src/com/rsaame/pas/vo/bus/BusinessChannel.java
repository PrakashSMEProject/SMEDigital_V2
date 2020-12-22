/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author Sarath
 * Created during mirror site to differentiate between b2b & b2c
 *
 */
public enum BusinessChannel {
	
	B2B(1), B2C(2);
	
	private int id;
	
	BusinessChannel(int num){
		this.id = num;
	}
	
	public int getId(){
		return id;
	}

}
