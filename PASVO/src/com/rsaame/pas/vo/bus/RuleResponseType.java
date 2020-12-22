/**
 * This enum contains the expected responses from Rule Engine for each business rules
 */
package com.rsaame.pas.vo.bus;


/**
 * @author Sarath
 * @since Post Phase 3
 *
 */
public enum RuleResponseType {
	
	Pass(1), Info(2), Fail(3), HardStop(4);
	
	private int precedence;
	
	private RuleResponseType(int value){
		this.setPrecedence(value);
	}
	
	public static boolean contains(String value) {

		try{
			RuleResponseType.valueOf(value);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public int getPrecedence() {
		return precedence;
	}

	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

}
