package com.rsaame.pas.rating.svc;

import java.util.ArrayList;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.quote.model.FactorBO;
import com.rsaame.pas.svc.constants.SvcConstants;

public class SectionFactorsDefaulter{
	private static final String DEFAULT_DEDUCTIBLE = "2500";
	private static final String DEFAULT_DEDUCTIBLE_BAHRAIN = "250";
	private static final String DEFAULT_WC_DEDUCTIBLE = "500";
	private static final String DEFAULT_WC_DEDUCTIBLE_BAHRAIN = "50";
	private static final String BAHRAIN_LOCATION_CODE = "50";
	/*public Item setDefaultPARItemFactors( Item item ){

		if (item.getItemFactors() != null) {
			Factor[] itemFactor = item.getItemFactors();
			for (Factor factor:itemFactor) {
				if(factor.getFactorName().equalsIgnoreCase("TEST")){

				}
			}
		}
		return item;
	}*/

	public ArrayList<FactorBO> createDefaultPARFactorList(int ratingItemNumber, FactorBO commonFactor){


		// TODO Extract factors from the PAR VO and fill it to ArrayList <FactorBO>

		
		
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
		factor.setFactorValue(String.valueOf( ratingItemNumber) );
		factorList.add( factor );	

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );			
		}
		else{
			factor.setFactorValue( DEFAULT_DEDUCTIBLE );
		}
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_SI_FACTOR );
		factor.setFactorValue("0" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR );
		factor.setFactorValue("1" );
		factorList.add( factor );

		//Changes done as required for CR, ADVN ID : 93276
		if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
				Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
			factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}else{
			factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}


		factorList.add(commonFactor);

		return factorList;	
	}

	public ArrayList<FactorBO> createDefaultPARContentFactorList(int ratingItemNumber, FactorBO commonFactor){

		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
		factor.setFactorValue(String.valueOf( ratingItemNumber) );
		factorList.add( factor );	

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );			
		}
		else{
			factor.setFactorValue( DEFAULT_DEDUCTIBLE );
		}
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_CONTENT_SI_FACTOR );
		factor.setFactorValue("0" );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_CONTENT_CAT_FACTOR);
		factor.setFactorValue("Furnitures and Fixtures" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_EE10_BUS_TYPE_FACTOR);
		factor.setFactorValue("0" );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_EE5_BUS_TYPE_FACTOR);
		factor.setFactorValue("0" );
		factorList.add( factor );
		
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR );
		factor.setFactorValue("1" );
		factorList.add( factor );

		//Changes done as required for CR, ADVN ID : 93276
		if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
				Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
			factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}else{
			factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}

		factorList.add(commonFactor);

		return factorList;
		
	}

	public ArrayList<FactorBO> createDefaultPLFactorList(int ratingItemNumber, FactorBO commonFactor){
	
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
	

		FactorBO factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
		factor.setFactorValue(String.valueOf( ratingItemNumber) );
		factorList.add( factor );	

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
		factor.setFactorValue("None" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_TENANT_FLG_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_FOOD_DRINK_FLG_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_STUDENT_LIABILITY_FACTOR );
		factor.setFactorValue("0" );
		factorList.add( factor );
		//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_WORK_AWY_FLG_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_LIB_LIMIT_FACTOR);
		factor.setFactorValue("1" );
		factorList.add( factor );
		
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_PL_DEDUCTIBLE_FACTOR );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );			
		}
		else{
			factor.setFactorValue( DEFAULT_DEDUCTIBLE );
		}
		factorList.add( factor );
		factorList.add(commonFactor);

		return factorList;
		
		
	}

	public ArrayList<FactorBO> createDefaultWCFactorList(int ratingItemNumber, FactorBO commonFactor){
		
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		

		FactorBO factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
		factor.setFactorValue(String.valueOf( ratingItemNumber) );
		factorList.add( factor );
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_ANNUAL_WAGE_ROLL_FACTOR );
		factor.setFactorValue(String.valueOf( 0) );
		factorList.add( factor );
		 
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_EMP_TYP_FACTOR );
		factor.setFactorValue("1" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_LIBLIMIT_FACTOR );
		factor.setFactorValue("1" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_PA_FLG_FACTOR );
		factor.setFactorValue("N" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_DEDUCTIBLE_FACTOR );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor.setFactorValue( DEFAULT_WC_DEDUCTIBLE_BAHRAIN );			
		}
		else{
			factor.setFactorValue( DEFAULT_WC_DEDUCTIBLE );
		}
		factorList.add( factor );
		factorList.add(commonFactor);
		
		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_WC_NO_OF_EMP_FACTOR );
		factor.setFactorValue("0" );
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}

	public ArrayList<FactorBO> createDefaultMoneyFactorList(int ratingItemNumber, FactorBO commonFactor){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		

		FactorBO factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
		factor.setFactorValue(String.valueOf( ratingItemNumber) );
		factorList.add( factor );	

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_MONEY_CAT_FACTOR );
		factor.setFactorValue("Estimated Annual Carryings" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_MONEY_SI_FACTOR );
		factor.setFactorValue("0" );
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_MONEY_DEDUCTIBLE_FACTOR );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );			
		}
		else{
			factor.setFactorValue( DEFAULT_DEDUCTIBLE );
		}
		factorList.add( factor );

		factor = new FactorBO();
		factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
		factor.setFactorValue("None" );
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}

	public ArrayList<FactorBO> createDefaultBIFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_BI_AVG_PRM_PAR , String.valueOf( SvcConstants.RATING_BI_AVG_PRM_PAR_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_BI_INDEMNIY_PERIOD , String.valueOf( SvcConstants.RATING_BI_INDEMPRD_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_BI_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_BI_DEDUCTIBLE_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_BI_SI , String.valueOf( SvcConstants.RATING_BI_SI_DEFAULT ));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	


	public ArrayList<FactorBO> createDefaultMBFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_MB_CATEGORY , String.valueOf( SvcConstants.RATING_MB_CATEGORY_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_MB_SI , String.valueOf( SvcConstants.RATING_MB_SI_DEFAULT));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultEEFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_EE_CATEGORY , String.valueOf( SvcConstants.RATING_EE_CATEGORY_DEFAULT ));
		factorList.add( factor );
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_EE_DEDUCTIBLE_BAHRAIN_DEFAULT ));			
		}
		else{
			factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_EE_DEDUCTIBLE_DEFAULT));
		}
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_EE_SI , String.valueOf( SvcConstants.RATING_EE_SI_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_EE_ITEMS , String.valueOf( SvcConstants.RATING_EE_ITEMS_DEFAULT));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultTBFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_TB_CAT , String.valueOf( SvcConstants.RATING_TB_CAT_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_TB_LIMIT_SI , String.valueOf( SvcConstants.RATING_TB_LIMIT_SI_DEFAULT));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_TB_NO_OF_PEOPLE , String.valueOf( SvcConstants.RATING_TB_NO_OF_PEOPLE_DEFAULT ));
		factorList.add( factor );
		//factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_DEFAULT));
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_BAHRAIN_DEFAULT ));			
		}
		else{
			factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_DEFAULT));
		}
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultGPAFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GPA_CAPITAL_SI , String.valueOf( SvcConstants.RATING_GPA_CAPITAL_SI_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GPA_NO_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_NO_OF_EMP_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GPA_TYPE_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_TYPE_OF_EMP_DEFAULT ));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultGITFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GIT_SINGLE_TRUCK_LIMIT , String.valueOf( SvcConstants.RATING_GIT_SINGLE_TRUCK_LIMIT_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GIT_COMMODITY_TYPE , String.valueOf( SvcConstants.RATING_GIT_COMMODITY_TYPE_DEFAULT ));
		factorList.add( factor );
		//factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_DEFAULT ));
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_BAHRAIN_DEFAULT ));		
		}
		else{
			factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_DEFAULT ));
		}
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_GIT_EST_ANNUAL_TURNOVER , String.valueOf( SvcConstants.RATING_GIT_EST_ANNUAL_TURNOVER_DEFAULT ));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultDOSFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_DOS_CAPITAL_SI , String.valueOf( SvcConstants.RATING_DOS_CAPITAL_SI_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_DOS_CAT_TYPE , String.valueOf( SvcConstants.RATING_DOS_CAT_TYPE_DEFAULT ));
		factorList.add( factor );
		factorList.add(commonFactor);
		return factorList;
	}
	
	public ArrayList<FactorBO> createDefaultFIDFactorList( int ratingItemNumber, FactorBO commonFactor ){
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		
		FactorBO factor = new FactorBO();
		factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_FID_NO_OF_EMP , String.valueOf( SvcConstants.RATING_FID_NO_OF_EMP_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_FID_BASIS_OF_COVER , String.valueOf( SvcConstants.RATING_FID_BASIS_OF_COVER_DEFAULT ));
		factorList.add( factor );
		//factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
		if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
			factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_BAHRAIN_DEFAULT ));		
		}
		else{
			factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
		}
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_FID_TRADE_GROUP , String.valueOf( SvcConstants.RATING_FID_TRADE_GROUP_DEFAULT ));
		factorList.add( factor );
		factor = setFactor(SvcConstants.RATING_FID_AGGREGATE , String.valueOf( SvcConstants.RATING_FID_AGGREGATE_DEFAULT ));
		factorList.add( factor );
		
		factorList.add(commonFactor);
		return factorList;
	}
	
private FactorBO setFactor( String name, String value ){
		
		FactorBO factor = new FactorBO();
		factor.setFactorName( name );
		factor.setFactorValue( value );
		
		return factor;
	}

}
