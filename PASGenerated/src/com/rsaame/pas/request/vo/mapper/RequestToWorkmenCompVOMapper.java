/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.request.vo.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.WCVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToWCVOMapper.class )</code>.
 */
public class RequestToWorkmenCompVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.WorkmenCompVO>{
	private final Logger log = Logger.getLogger( this.getClass() );
	static Integer size = 0;

	public RequestToWorkmenCompVOMapper(){
		super();
	}

	public RequestToWorkmenCompVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.WorkmenCompVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WorkmenCompVO mapBean(){

		log.info( "Request to WC monoline VO mapper started..." );

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WorkmenCompVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WorkmenCompVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		/* Mapping: com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE -> "empTypeDetails[].empType" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "WC_dropdown_employeeType[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setEmpType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_employeeType[" + i + "]" ) ) ) );
			}
		}

		/* Mapping: "quote_name_noOfEmployee" -> "empTypeDetails[].noOfEmp" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "quote_name_noOfEmployee" ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "quote_name_noOfEmployee[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setNoOfEmp( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_noOfEmployee[" + i + "]" ) ) ) );
			}
		}

		/* Mapping: com.Constant.CONST_QUOTE_NAME_ANNUALWAGEROLL -> "empTypeDetails[].wageroll" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_QUOTE_NAME_ANNUALWAGEROLL ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "quote_name_annualWageroll[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setWageroll( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_annualWageroll[" + i + "]" ) ) ) );
			}
		}

		/* Mapping: "WC_dropdown_deductible" -> "empTypeDetails[].deductibles" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_deductible" ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "WC_dropdown_deductible[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "",
						"" );
				beanB.getEmpTypeDetails().get( i ).setDeductibles( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_deductible[" + i + "]" ) ) ) );
			}
		}

		/* Mapping: "WC_dropdown_limit" -> "empTypeDetails[].limit" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_deductible" ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "WC_dropdown_limit[0]" ) ) ){
				com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "",
						"" );
				beanB.getEmpTypeDetails().get( i ).setLimit( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_limit[0]" ) ) ) );
			}
		}

		/* Mapping: "wcriskid" -> "empTypeDetails[].riskId" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE ).size();
		String[] tradeGroupStr = null;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "quote_txt_tradeGroup[" + i + "]" ) ) ){
				tradeGroupStr = beanA.getParameter( "quote_txt_tradeGroup[" + i + "]" ).toString().split(" ");
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( tradeGroupStr[2] ) ) );
			}
		}
		
		/* Mapping: "wcriskid" -> "empTypeDetails[].riskId" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "quote_txt_hazardlevel[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setHazardLevel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_txt_hazardlevel[" + i + "]" ) ) ) );
			}
		}
		
		/* Mapping: "wcriskid" -> "empTypeDetails[].riskId" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "unnamedPersonRiskId[" + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
				beanB.getEmpTypeDetails().get( i ).setRiskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "unnamedPersonRiskId[" + i + "]" ) ) ) );
			}
		}
		/* Mapping: "coverVSD" -> "empTypeDetails[].vsd" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_WC_DROPDOWN_EMPLOYEETYPE ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "unnamedPersonVSD[" + i + "]" ) ) ){
				/*com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
						"format=yyyy-MM-dd HH:mm:ss.SSS" );
				beanB.getEmpTypeDetails().get( i ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "unnamedPersonVSD[" + i + "]" ) ) ) );*/
			
				Timestamp vsd = Timestamp.valueOf( beanA.getParameter( "unnamedPersonVSD[" + i + "]" ) );
				beanB.getEmpTypeDetails().get( i ).setVsd( vsd );
				
			}
		}
		
		/* Mapping: "Utils.getSingleValueAppConfig( "WC_BASIC_COVER" )" -> "empTypeDetails[].coverCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_QUOTE_NAME_ANNUALWAGEROLL ).size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) ) ){
				beanB.getEmpTypeDetails().get( i ).setCoverCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) ) );
			}
		}

		/* Mapping: "24HourPACoverHiddenValue" -> "wcCovers.PACover" */
		if( !Utils.isEmpty( beanA.getParameter( "24HourPACover" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.getWcCovers().setPACover( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "24HourPACover" ) ) ) );
		}

		WorkmenCompVO wcVO = new WorkmenCompVO();
		wcVO=  (WorkmenCompVO) src.getSession().getAttribute("WCMONOLINE_EMPDETAIL");
		
		String eName = "";
		boolean empExists= false;
		List<String> noOfEmp = null;
		Integer index = null;
		//int numberOfWcNamedEmp = 0;

		// Changes done for Radar Id 82
		// WC MOnoline employee update issue
		
	/*	We will get the wcVo from session. If employee in request is present before in the session vo, 
		Then we will simply add the details from session vo which includes the wprId and vsd.
		If it is not available in it before, then we just add the ename and keep rest untouched.
		Hence no need of assigning wprId and vsd individually.*/
		
		
		/* Mapping: "wcEmpName" -> "wcEmployeeDetails[].empName" */
		noOfEmp = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "wcEmpName" );
		noOfEmp = this.sort(noOfEmp);
		index = 0;
		for (String i : noOfEmp) {
			if (!Utils.isEmpty(beanA.getParameter(i))) {
				empExists= false;
				//numberOfWcNamedEmp++;
				eName = beanA.getParameter(i);
				if (!Utils.isEmpty(wcVO)) {
					for (WCNammedEmployeeVO wcVoFromSession : wcVO
							.getWcEmployeeDetails()) {
						if (wcVoFromSession.getEmpName()
								.equalsIgnoreCase(eName)) {
							empExists = true;
							beanB.getWcEmployeeDetails().get(index)
									.setEmpName(eName);
							beanB.getWcEmployeeDetails().get(index)
									.setWprWCId(wcVoFromSession.getWprWCId());
							beanB.getWcEmployeeDetails().get(index)
									.setVsd(wcVoFromSession.getVsd());
							index++;
							break;
						}
					}
				}
				if (!empExists) {
					beanB.getWcEmployeeDetails().get(index++).setEmpName(eName);
				}

				// beanB.getWcEmployeeDetails().get( index++ ).setEmpName( beanA.getParameter( i ) );
			}
		}

		
		 //Commented for Radar Id 82
		// WC MOnoline employee update issue
		
		/*noOfEmp = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "wcPrId" );
		index = 0;
		noOfEmp = this.sort(noOfEmp);
		for( String i : noOfEmp ){
			if(numberOfWcNamedEmp>index)
			{
				if (!Utils.isEmpty(beanA.getParameter(i))
						&& !beanA.getParameter(i).equalsIgnoreCase("null")) {
					beanB.getWcEmployeeDetails().get(index)
							.setWprWCId(Long.valueOf(beanA.getParameter(i)));
				}
				index++;
			}
		}
		//Commented for Radar Id 82
		// WC MOnoline employee update issue
		noOfEmp = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "wcPrVsd" );
		index = 0;
		noOfEmp = this.sort(noOfEmp);
		for( String i : noOfEmp ){
			if(numberOfWcNamedEmp>index)
			{
				if (!Utils.isEmpty(beanA.getParameter(i))
						&& !beanA.getParameter(i).equalsIgnoreCase("null")) {
					Timestamp vsd = Timestamp.valueOf(beanA.getParameter(i));
					beanB.getWcEmployeeDetails().get(index).setVsd(vsd);
				}
				index++;
			}
		}*/

		/* Mapping: "total_premium" -> "premiumVO.premiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "totalPremium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "totalPremium" ) ) ) );
		}

		/* Mapping: "premium_payable" -> "premiumVO.premiumAmtActual" */
		if( !Utils.isEmpty( src.getParameter( "payable_premium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmtActual( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "payable_premium" ) ) ) );
		}

		/* Mapping: "currency" -> "premiumVO.currency" */
		if( !Utils.isEmpty( src.getParameter( "currency" ) ) ){
			beanB.getPremiumVO().setCurrency( beanA.getParameter( "currency" ) );
		}

		/* Mapping: "govt_tax" -> "premiumVO.govtTax" */
		if( !Utils.isEmpty( src.getParameter( "govtTax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setGovtTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "govtTax" ) ) ) );
		}
		
		/* Mapping: "vat_tax" -> "premiumVO.vattTax" */ 
		//142244
		if( !Utils.isEmpty( src.getParameter( "vatTax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTax" ) ) ) );
		}
		/* Mapping: "vat_tax_perc" -> "premiumVO.vattTaxPerc" */ 
		if( !Utils.isEmpty( src.getParameter( "vatTaxPerc" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTaxPerc" ) ) ) );
		}
		
		/* Mapping: "vat_tax_code" -> "premiumVO.vatCode" */ 
		if( !Utils.isEmpty( src.getParameter( "vatCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatCode" ) ) ) );
		}
		/* Added for AdventID - 142244*/
		if( !Utils.isEmpty( src.getParameter( "quote_vat_reg_no" ) ) ){
			beanB.getGeneralInfo().getInsured().setVatRegNo( beanA.getParameter( "quote_vat_reg_no" ) );
		}
		
		/* Mapping: "special_discount" -> "premiumVO.specialDiscount" */
		if( !Utils.isEmpty( src.getParameter( "special_discount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setSpecialDiscount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "special_discount" ) ) ) );
		}

		/* Mapping: "policy_fees" -> "premiumVO.policyFees" */
		if( !Utils.isEmpty( src.getParameter( "policyFees" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPolicyFees( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyFees" ) ) ) );
		}

		/* Mapping: "disc_or_load_perc" -> "premiumVO.discOrLoadPerc" */
		if( !Utils.isEmpty( src.getParameter( "discountPercentage" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "discountPercentage" ) ) ) );
		}

		/* Mapping: "disc_or_load_amt" -> "premiumVO.discOrLoadAmt" */
		if( !Utils.isEmpty( src.getParameter( "discountAmount" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "discountAmount" ).replaceAll( ",", "" ) ) ) );
		}

		/* Mapping "commissionPremium" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commissionPremium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commissionPremium" ) ) ) );
		}

		/* Mapping "promoDisc" -> "premiumVO.promoDiscPerc" */
		if( !Utils.isEmpty( src.getParameter( "promoDiscount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPromoDiscPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "promoDiscount" ) ) ) );
		}
		List<EmpTypeDetailsVO> empTypeDetails = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class );
		for(EmpTypeDetailsVO emp: beanB.getEmpTypeDetails()){
			if ( ( !Utils.isEmpty( emp.getEmpType() ) && !Utils.isEmpty( emp.getWageroll() ) && 
					/*!Utils.isEmpty( emp.getNoOfEmp() ) && emp.getNoOfEmp() != 0*/ 
					!Utils.isEmpty( emp.getDeductibles() ) && !Utils.isEmpty( emp.getLimit() ) ) || !Utils.isEmpty( emp.getRiskId() ) ){
					
				empTypeDetails.add( emp );
			}
		}
		beanB.setEmpTypeDetails( (com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>) empTypeDetails );
		
		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WorkmenCompVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.WorkmenCompVO beanB ){

		int unnamedEmpCount = 0;
		int namedEmpCount = 0;
		
		int noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_QUOTE_NAME_ANNUALWAGEROLL ).size();
		/*for( int i = 0; i < noOfItems; i++ ){
				unnamedEmpCount++;
		}*/
		unnamedEmpCount = noOfItems;
		
		List<String> noOfEmp = null;
		noOfEmp = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "wcEmpName" );
		for( String i : noOfEmp ){
			if( !Utils.isEmpty( beanA.getParameter( i ) ) ){
				namedEmpCount++;
			}
		}
		BeanUtils.initializeBeanField( "empTypeDetails[]", beanB, unnamedEmpCount );
		BeanUtils.initializeBeanField( "wcCovers", beanB );
		BeanUtils.initializeBeanField( "premiumVO", beanB );
		BeanUtils.initializeBeanField( "wcEmployeeDetails[]", beanB, namedEmpCount );
		return beanB;
	}
	
	/**
	 * Method to sort the tags
	 * @param inputList
	 * @return
	 */
	private List<String> sort(List<String> inputList) {

		SortedSet<Integer> indexSet = new TreeSet<Integer>();
		List<String> resultList = new ArrayList<String>();
		String tagName = null;
		for (String element : inputList) {
			indexSet.add(Integer.valueOf((element.substring(
					element.indexOf('[') + 1, element.indexOf(']')))));
			tagName = element.substring(0, element.indexOf('[') + 1);
		}
		for (Integer i : indexSet) {
			resultList.add(tagName + i + "]");
		}
		
		/*Collections.sort(inputList, new Comparator<String>(){
				        @Override
				        public int compare(String e1, String e2)
				        {
					String s1 = e1.replaceAll("room[", "");
					String s2 = e2.replaceAll("room", "");
				            return new Integer(s1.replaceAll("]", ""))
				                .compareTo(new Integer(s2.replaceAll("room", "")));

				@Override
				public int compare(String arg0, String arg1) {
			
					return 0;
				}
		//		        }

				    });*/
		
		
		return resultList;
	}
	
}
