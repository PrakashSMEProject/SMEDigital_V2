/**
 * This is used to instantiate the currency for a country,
 */
package com.rsaame.pas.cmn.currency;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1014644
 *
 */
public class Currency {

	/*private static String pfxSymbol = ""; // Leading currency symbol
	private static String sfxSymbol = ""; // Trailing currency symbol
	private static char decimalPoint = '.'; // Character for fractions 
	private static char groupSeparator = ','; // Character for 1000nds 
	private static String unitName = ""; // Name of monetary unit 
	private static String fractionName = ""; // Name of fraction unit 
	
	private static int scale = 1000; // defaulted to 3

	private static String currencyEName;
	private static String currencyNativeName;
	
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	private static Map<Short, Integer> policyTypeScaleMap = new HashMap<Short, Integer>();*/
	
	private static  NumberFormat formatter = NumberFormat.getCurrencyInstance();
	private static HashMap<String, CurrencyDetails> currLocDetails = new HashMap<String, CurrencyDetails>();
	private static final String DEPLOYED_LOCATION = "DEPLOYED_LOCATION";
	public Currency(){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/**
	 * Depending on the location of the application the currency is instantiated.
	 * 
	 * @param scale
	 * @param pfxSymbol
	 * @param sfxSymbol
	 * @param decimalPoint
	 * @param groupSeparator
	 * @param unitName
	 * @param fractionName
	 * @param currencyEName
	 * @param currencyNativeName
	 * @param location
	 */
	public static void setCurrency( Integer scale, String pfxSymbol, String sfxSymbol, char decimalPoint, char groupSeparator, String unitName, String fractionName,
			String currencyEName, String currencyNativeName, String location ){
		/*Currency.scale = scale;
		Currency.pfxSymbol = pfxSymbol;
		Currency.sfxSymbol = sfxSymbol;
		Currency.decimalPoint = decimalPoint;
		Currency.groupSeparator = groupSeparator;
		Currency.unitName = unitName;
		Currency.fractionName = fractionName;
		Currency.currencyEName = currencyEName;
		Currency.currencyNativeName = currencyNativeName;*/
		
		CurrencyDetails currencyDetails = new CurrencyDetails();
		currencyDetails.setScale(scale.intValue());
		currencyDetails.setPfxSymbol(pfxSymbol);
		currencyDetails.setSfxSymbol(sfxSymbol);
		currencyDetails.setDecimalPoint(decimalPoint);
		currencyDetails.setGroupSeparator(groupSeparator);
		currencyDetails.setUnitName(unitName);
		currencyDetails.setFractionName(fractionName);
		currencyDetails.setCurrencyEName(currencyEName);
		currencyDetails.setCurrencyNativeName(currencyNativeName);
		
		currLocDetails.put(location, currencyDetails);
	
	}

	/**
	 * Returns the comma separated currency for displaying purpose
	 * @param amount
	 * @return
	 */
	public static String getFormattedCurrency( BigDecimal amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( amount, true );
	}
	
	/**
	 * Returns the comma separated currency for displaying purpose
	 * @param amount
	 * @return
	 */
	public static String getFormattedCurrency( BigDecimal amount, String lob ){
		int reqdScale = 1000;
		if(!Utils.isEmpty( lob )){
			String loc = Utils.getSingleValueAppConfig(DEPLOYED_LOCATION);
			Short policyType = Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPE_"+lob ));
			reqdScale =  Currency.getPolicyTypeScaleMap().get(policyType);
			if(loc.equalsIgnoreCase("50")){
			Currency.setScale(reqdScale);
			}
		}
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( amount, true, reqdScale );
	}

	/**
	 * Returns currency with out comma separation but formatted for a location
	 * @param amount
	 * @return
	 */
	public static String getUnformattedScaledCurrency( BigDecimal amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( amount, false );
	}
	
	public static String getUnformattedScaledCurrency( BigDecimal amount, String lob ){
		int scale = 1000;
		if(!Utils.isEmpty( lob )){
			Short policyType = Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPE_"+lob ));
			scale =  Currency.getPolicyTypeScaleMap().get(policyType); 
		}
		if(Utils.isEmpty( amount ))return "";
		//System.out.println("Currency.java FFF scale is " + scale);
		//System.out.println("Currency.java FFF amount is " + amount);

		return getFormattedCurrency( amount, false, scale );
	}
	
	public static String getUnformttedScaledCurrency( Double amount ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ) );
	}
	
	public static String getUnformttedScaledCurrency( Double amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ), lob );
	}

	public static String getUnformttedScaledCurrency( Integer amount ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ) );
	}
	
	public static String getUnformttedScaledCurrency( Integer amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ), lob );
	}

	public static String getUnformttedScaledCurrency( Long amount ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ) );
	}
	
	public static String getUnformttedScaledCurrency( Long amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getUnformattedScaledCurrency( BigDecimal.valueOf( amount ), lob );
	}

	public static String getUnformttedScaledCurrency( String amount ){
		if(Utils.isEmpty( amount ))return "";
		if(amount.contains(",")){
			amount = amount.replace(",", "");
		}
		return getUnformattedScaledCurrency( BigDecimal.valueOf( Double.valueOf( amount ) ) );
	}
	
	public static String getUnformttedScaledCurrency( String amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		if(amount.contains(",")){
			amount = amount.replace(",", "");
		}
		return getUnformattedScaledCurrency( BigDecimal.valueOf( Double.valueOf( amount ) ), lob );
	}

	public static String getFormattedCurrency( Double amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ) );
	}

	public static String getFormattedCurrency( Double amount, String lob ){		
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ),lob );
	}
	
	public static String getFormattedCurrency( Integer amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ) );
	}
	
	public static String getFormattedCurrency( Integer amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ), lob );
	}
	
	public static String getFormattedCurrency( Long amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ) );
	}
	
	public static String getFormattedCurrency( Long amount,String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( amount ),lob );
	}


	public static String getFormattedCurrency( String amount ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( Double.valueOf( amount ) ) );
	}
	
	public static String getFormattedCurrency( String amount, String lob ){
		if(Utils.isEmpty( amount ))return "";
		return getFormattedCurrency( BigDecimal.valueOf( Double.valueOf( amount ) ),lob );
	}
	
	
	public static BigDecimal add( BigDecimal a , BigDecimal b ){
		if(Utils.isEmpty( a ))return b;
		if(Utils.isEmpty( b ))return a;
		return BigDecimal.valueOf( Double.valueOf( getUnformattedScaledCurrency( a.add( b ) ) ) );
	}
	
	public static BigDecimal subtract( BigDecimal a , BigDecimal b ){
		if(Utils.isEmpty( a ))return b.multiply( BigDecimal.valueOf( -1 ) );
		if(Utils.isEmpty( b ))return a;
		return BigDecimal.valueOf( Double.valueOf( getUnformattedScaledCurrency( a.subtract( b ) ) ) );
	}
	
	public static BigDecimal getPercentage( BigDecimal a , BigDecimal b ){
		if(Utils.isEmpty( a ))return BigDecimal.ZERO;
		if(Utils.isEmpty( b ) || b.compareTo( BigDecimal.ZERO ) == 0)return null;
		return BigDecimal.valueOf( Double.valueOf( getUnformattedScaledCurrency( ( a.divide( b ) ).multiply( BigDecimal.valueOf( 100 ) ) ) ) );
	}
	
	public static BigDecimal applyPercentage( BigDecimal a , Double percentage ){
		if(Utils.isEmpty( a ))return BigDecimal.ZERO;
		if(Utils.isEmpty( percentage ))return null;
		return BigDecimal.valueOf( Double.valueOf( getUnformattedScaledCurrency( a.multiply( BigDecimal.valueOf( percentage ) ).divide( BigDecimal.valueOf( 100 ) ) ) ) );
	}
	
	/**
	 * @return
	 */
	private static CurrencyDetails getCurrencyDetails() {
		
		String loc = Utils.getSingleValueAppConfig(DEPLOYED_LOCATION);
		CurrencyDetails currencyDetails = currLocDetails.get(loc);

		return currencyDetails;
	}
	
	/**
	 * @return the pfxSymbol
	 */
	public static final String getPfxSymbol(){
		return getCurrencyDetails().getPfxSymbol();
	}

	/**
	 * @return the sfxSymbol
	 */
	public static final String getSfxSymbol(){
		return getCurrencyDetails().getPfxSymbol();
	}

	/**
	 * @return the decimalPoint
	 */
	public static final char getDecimalPoint(){
		return getCurrencyDetails().getDecimalPoint();
	}

	/**
	 * @return the groupSeparator
	 */
	public static final char getGroupSeparator(){
		return getCurrencyDetails().getGroupSeparator();
	}

	/**
	 * @return the unitName
	 */
	public static final String getUnitName(){
		return getCurrencyDetails().getUnitName();
	}

	/**
	 * @return the fractionName
	 */
	public static final String getFractionName(){
		return getCurrencyDetails().getFractionName();
	}

	/**
	 * @return the currencyEName
	 */
	public static final String getCurrencyEName(){
		return getCurrencyDetails().getCurrencyEName();
	}

	/**
	 * @return the currencyNativeName
	 */
	public static final String getCurrencyNativeName(){
		return getCurrencyDetails().getCurrencyNativeName();
	}

	/**
	 * @param scale the scale to set
	 */
	public static final void setScale( int scale ){
		Currency.getCurrencyDetails().setScale(scale);
	}
	
	/**
	 * @return the policyTypeScaleMap
	 */
	public static Map<Short, Integer> getPolicyTypeScaleMap(){
		return getCurrencyDetails().getPolicyTypeScaleMap();
	}

	/**
	 * @param policyTypeScaleMap the policyTypeScaleMap to set
	 */
	public static void setPolicyTypeScaleMap( Map<Short, Integer> policyTypeScaleMap, String location){
		
		currLocDetails.get(location).setPolicyTypeScaleMap(policyTypeScaleMap);
	}

	private static BigDecimal wholeUnits( BigDecimal amount ){
		return amount.subtract( amount.remainder( BigDecimal.ONE ) );
	}

	private static String cents( BigDecimal amount ){
		System.out.println("Currency.java FFF recieved amount to cents(); func is : " + amount);
		
		String formattedCent  = amount.remainder( BigDecimal.ONE ).multiply( BigDecimal.valueOf( getCurrencyDetails().getScale() ) ).setScale( 0, BigDecimal.ROUND_HALF_UP ).toPlainString();
	//	System.out.println("Currency.java FFF formattedCent is :" + formattedCent);
	//	System.out.println("Currency.java FFF getResovedScale() is :" + getResovedScale());
		while(formattedCent.length()<getResovedScale()){
			formattedCent = "0" + formattedCent;
		}
		return formattedCent;
	}
	
	
	private static String formattedWhole( BigDecimal whole ){
		String moneyString = formatter.format( whole );
		if( moneyString.endsWith( ".00" ) ){
			int centsIndex = moneyString.lastIndexOf( ".00" );
			if( centsIndex != -1 ){
				moneyString = moneyString.substring( 1, centsIndex );
			}
		}
		return moneyString;
	}
	
	public static int getResovedScale(){
		short result; //    of decimal digits in an integer
		int scale = getCurrencyDetails().getScale();
		for( result = 0; scale >= 10; result++, scale /= 10 )
			; // Decimal "shift" and count
		return result;
	}
	
	public static int getResovedScale(int reqdScale){
		short result; //    of decimal digits in an integer
		int scale = reqdScale;
		for( result = 0; scale >= 10; result++, scale /= 10 )
			; // Decimal "shift" and count
		return result;
	}
	
	public static int getScale(){
		return getCurrencyDetails().getScale();
	}
	
	private static String getFormattedCurrency(BigDecimal amount, boolean formatted){
		
		//System.out.println( "ZZZ Currency.java Inside overloaded getFormattedCurrency(BigDecimal amount, boolean formatted)");
		
		StringBuilder result = new StringBuilder();
		boolean negative = ( amount.compareTo( BigDecimal.ZERO ) < 0 ); //  Remember original sign
		amount.setScale( 0, BigDecimal.ROUND_CEILING );
		amount = negative ? amount.multiply( BigDecimal.valueOf( -1 ) ) : amount; //  Discard sign temporarily
		//System.out.println("Currency.java ZZZA original getResovedScale() is : "+ getResovedScale());
		
		if((getResovedScale() == 0) && (Utils.getSingleValueAppConfig(DEPLOYED_LOCATION)!=null && Utils.getSingleValueAppConfig(DEPLOYED_LOCATION).equalsIgnoreCase("20"))) { //new logic for API
			
			//System.out.println("Currency.java ZZZA since the original getResovedScale() is 0, setting the scale manually to 2 for this amount" );
		//	System.out.println("The currency value is :"+Utils.getSingleValueAppConfig("USER_10"));
		//	System.out.println("The currency value is :"+Utils.getSingleValueAppConfig("USER_6"));
		//	System.out.println("The user from B2C is :"+Utils.getSingleValueAppConfig("RSA_USER_0"));
			amount = amount.setScale( 2, BigDecimal.ROUND_HALF_UP );

			//System.out.println("Currency.java ZZZA the new amount with scale 2 is  :" + amount );
		}
		
		else { //old logic
		amount = amount.setScale( getResovedScale(), BigDecimal.ROUND_HALF_UP );
		}
		BigDecimal whole = wholeUnits( amount ).setScale( 0, BigDecimal.ROUND_HALF_UP ); //  Separate arg. into whole
		if(getCurrencyDetails().getScale() == 0){
			//System.out.println("Currency.java ZZZ Inside Scale ZERO condition");
			//result = result.append( negative ? "-" : "" ).append(whole);
			//System.out.println("Currency.java ZZZAA Inside Scale ZERO condition");

			//System.out.println("Currency.java ZZZAA cents( amount ) " + cents( amount ) );
			if(!cents( amount ).equals("0") && !cents( amount ).equals("00")) {
			result = result.append( negative ? "-" : "" ).append(formatted? formattedWhole( whole ) : whole).append( getCurrencyDetails().getDecimalPoint() ).append( cents( amount ) );
			}
			
			else {
				//System.out.println("Currency.java ZZZAA Inside new Error Handling Condition");

				//String amountStringArr[] = amount.toString().split(".");
				result = result.append( negative ? "-" : "" ).append(amount);
			}
		}else{
			//System.out.println("Currency.java ZZZ cents( amount ) " + cents( amount ) );
			if(!cents( amount ).equals("0") && !cents( amount ).equals("00")) {
			result = result.append( negative ? "-" : "" ).append(formatted? formattedWhole( whole ) : whole).append( getCurrencyDetails().getDecimalPoint() ).append( cents( amount ) );
			}
			
			else {
				//System.out.println("Currency.java ZZZ Inside new Error Handling Condition");

				//String amountStringArr[] = amount.toString().split(".");
				result = result.append( negative ? "-" : "" ).append(amount);
			}
		}
		return result.toString();
	}
	
	private static String getFormattedCurrency(BigDecimal amount, boolean formatted,int reqdScale ){
		//System.out.println("Currency.java FFF recieved amount is : " + amount );
		StringBuilder result = new StringBuilder();
		boolean negative = ( amount.compareTo( BigDecimal.ZERO ) < 0 ); //  Remember original sign
		amount.setScale( 0, BigDecimal.ROUND_CEILING );
		//System.out.println("Currency.java FFF amount.setScale( 0, BigDecimal.ROUND_CEILING ) is : " + amount );

		amount = negative ? amount.multiply( BigDecimal.valueOf( -1 ) ) : amount; //  Discard sign temporarily
		amount = amount.setScale( getResovedScale(reqdScale), BigDecimal.ROUND_HALF_UP );
		//System.out.println("Currency.java FFF amount.setScale( getResovedScale(reqdScale), BigDecimal.ROUND_HALF_UP ) is : " + amount );

		BigDecimal whole = wholeUnits( amount ).setScale( 0, BigDecimal.ROUND_HALF_UP ); //  Separate arg. into whole
		//System.out.println("Currency.java FFF wholeUnits( amount ).setScale( 0, BigDecimal.ROUND_HALF_UP ); is : " + whole );

		
		if(reqdScale == 0){
			//System.out.println("Currency.java FFF inside IF block ");
			result = result.append( negative ? "-" : "" ).append(formatted? formattedWhole( whole ) : whole);
		}else{
			//System.out.println("Currency.java FFF inside ELSE block ");
			
			//System.out.println("Currency.java FFF getCurrencyDetails().getDecimalPoint() is " + getCurrencyDetails().getDecimalPoint());
			//System.out.println("Currency.java FFF  cents( amount ) is : " +  cents( amount ));
			
			//System.out.println("Currency.java FFF amount before decimal comparision split is  : " + amount);
			if(!cents( amount ).equals("0") && !cents( amount ).equals("00")) {
				//System.out.println("Currency.java FFF Inside NORMAL condition");
			result = result.append( negative ? "-" : "" ).append(formatted? formattedWhole( whole ) : whole).append(  getCurrencyDetails().getDecimalPoint()  ).append( cents( amount ) );
			}
			else {
				//System.out.println("Currency.java FFF Inside new Error Handling Condition");

				//String amountStringArr[] = amount.toString().split(".");
				result = result.append( negative ? "-" : "" ).append(amount);
			}
			//System.out.println("Currency.java FFF result is : " + result);
		}
		//System.out.println("Currency.java FFF result.toString() is : " + result.toString());
		return result.toString();
	}
	

	public static void main( String[] args ){

		System.out.println( Currency.getFormattedCurrency( BigDecimal.valueOf( 12345.678900343 ), "HOME" )+"\n" );
		System.out.println( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( 12345.678900343 ) ) );
	}

}
