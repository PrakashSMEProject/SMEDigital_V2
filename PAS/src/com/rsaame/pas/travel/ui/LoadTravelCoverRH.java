/**
 * 
 */
package com.rsaame.pas.travel.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.mindtree.ruc.cmn.exception.BusinessException;

/**
 * @author M1016284, M1033804
 * @since FGB
 */
public class LoadTravelCoverRH implements IRequestHandler{
	private final static Logger LOGGER = Logger.getLogger( LoadTravelCoverRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		
		/* Fetch commonVO from PolicyContext.*/
		Response responseObj = new Response();
		try{
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		
			//CR#13983 -changes for renewal quote validation
		 Boolean SaveQuoteFlow = false;
		 if(!Utils.isEmpty(request.getParameter("buttonClick")) && request.getParameter("buttonClick").equalsIgnoreCase("saveQuote")) {
		 SaveQuoteFlow=true;
		 }
		//CR#13983 -changes for renewal quote validation
		
		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		travelInsuranceVO.setCommonVO( commonVO );
		
		SchemeVO schemeVO = new SchemeVO();
		travelInsuranceVO.setScheme( schemeVO );
		Boolean loadOnSave = (!Utils.isEmpty( request.getParameter( "loadOnSave" ) ) ) ? 
				Boolean.valueOf( request.getParameter( "loadOnSave" ).toString() ) : false;				
		
		/*LoadCoverSvc coverSvc = (LoadCoverSvc)Utils.getBean( "loadCoverSvc" );
		TravelInsuranceVO coverTravelInsuranceVO = (TravelInsuranceVO) coverSvc.invokeMethod( "getPackages", commonVO );
		
		travelInsuranceVO = (TravelInsuranceVO)TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", travelInsuranceVO );
		
		travelInsuranceVO.setTravelPackageList( coverTravelInsuranceVO.getTravelPackageList() );
		
		
		RatingServiceInvoker ratingServiceInvoker = (RatingServiceInvoker)Utils.getBean( "travelRatingInvoker" );
		
		try{
			travelInsuranceVO = (TravelInsuranceVO)ratingServiceInvoker.invokeRating( travelInsuranceVO );
		}catch (Exception e) {
			BusinessException businessExcp = new BusinessException( "rating.invocation.no.loc", null, "riskGroupDetailsMap.entrySet() is null" );
			return null;
		}*/
		
		travelInsuranceVO = (TravelInsuranceVO)TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
		
		// Vat Code Impl - 142244
		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> vatRateHolder =(DataHolderVO<Object[]>) TaskExecutor.executeTasks("LOAD_TRAVEL_VAT_RATE_AND_CODE", travelInsuranceVO);		
		
		if(!Utils.isEmpty(vatRateHolder)) {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> vatRateMap = (Map<String, Object>) vatRateHolder.getData()[0];
			if(!Utils.isEmpty(vatRateMap)) {
				Double vatTaxPerc = (Double)vatRateMap.get("vatRate");				
				LOGGER.debug("**********vatRate**********: "+vatTaxPerc);				
				travelInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPerc);
			}
		}
		
		if( !commonVO.getIsQuote()
				&& ( Flow.VIEW_POL.equals( AppUtils.getBasicFlowCommonResolveReferral( commonVO ) ) || Flow.AMEND_POL
						.equals( AppUtils.getBasicFlowCommonResolveReferral( commonVO ) ) ) && commonVO.getEndtId().compareTo( 0L ) != 0
				&& Utils.isEmpty( request.getAttribute( com.Constant.CONST_CANCELDETAILS ) ) && commonVO.getStatus() != SvcConstants.DEL_SEC_LOC_STATUS ){
			PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
			travelInsuranceVO.setPolicyClassCode( policyDataVO.getPolicyClassCode() );
			travelInsuranceVO.setPolicyType( policyDataVO.getPolicyType() );
			travelInsuranceVO.getScheme().setSchemeCode( policyDataVO.getScheme().getSchemeCode() );
			travelInsuranceVO.getScheme().setTariffCode( policyDataVO.getScheme().getTariffCode() );
			DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
			Object[] inpObjects = { travelInsuranceVO, true };
			dataHolder.setData( inpObjects );
			if( !policyDataVO.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ) ){
				travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
			}

		}
		/*IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
		if( !Utils.isEmpty( Currency.getPolicyTypeScaleMap() ) && !Utils.isEmpty( Currency.getPolicyTypeScaleMap().get( converter.getBFromA(travelInsuranceVO.getPolicyType()) ) ) ){
			Currency.setScale( Currency.getPolicyTypeScaleMap().get( converter.getBFromA(travelInsuranceVO.getPolicyType()) ) );
		}*/
		setDefaultAndCommonValues(  travelInsuranceVO,  request, commonVO );
		if( !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO() ) && !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO().get( 0 ) ) ){
			EndorsmentVO endorsmentVO = new EndorsmentVO();
			endorsmentVO = travelInsuranceVO.getEndorsmentVO().get( 0 );
			
			StringBuilder endtTxt = new StringBuilder();
			com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endtVO = ( (PolicyDataVO) TaskExecutor.executeTasks( "CAPTURE_AMEND_POLICY_ENDT_TEXT", travelInsuranceVO ) )
					.getEndorsmentVO();
			if( !Utils.isEmpty( endtVO ) ){
				for( EndorsmentVO endt : endtVO ){
					if( !Utils.isEmpty( endt.getEndText() ) ) {
						endtTxt.append(endt.getEndText()).append("\n"); // changes done for Sonar Critical issue
					}
				}
			}
			//endtTxt.trim();
			endorsmentVO.setEndText( endtTxt.toString() );
			com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endtList = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( null );
			endtList.add( endorsmentVO );
			travelInsuranceVO.setEndorsmentVO( endtList );
		}
		/*Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change*/
		DAOUtils.checkVatEnabled(request);
		request.setAttribute( "travelInsuranceVO", travelInsuranceVO );
		request.setAttribute( AppConstants.PAGE_VALUE, travelInsuranceVO );
		
		request.setAttribute(AppConstants.FUNTION_NAME,  policyContext.getAppFlow().toString() );
		request.setAttribute( "LOB", commonVO.getLob().toString());
		
		// 142244 Added VatCode for report Generation purpose
		commonVO.getPremiumVO().setVatCode(travelInsuranceVO.getVatCode());
		
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date pollExpiryDate = null;
        try {
        	pollExpiryDate = dateFormat.parse(travelInsuranceVO.getScheme().getExpiryDate().toString());
            LOGGER.debug("Date formatted - Policy Expiry Date: "+ pollExpiryDate);
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        
		commonVO.setPolExpiryDate(pollExpiryDate);
		
		if(loadOnSave){
			request.setAttribute( "dataSaved", "Data is saved successfully" );
		}
		//CR#13983 -changes for renewal quote validation
		boolean isRenewalQuote=false;

			if(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")) {
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO())&& Utils.getSingleValueAppConfig("NEW_RENEWAL_QUOTATION").equalsIgnoreCase(travelInsuranceVO.getCommonVO().getDocCode().toString())
				&& travelInsuranceVO.getCommonVO().getIsQuote() && !SaveQuoteFlow && Flow.VIEW_QUO.equals( travelInsuranceVO.getCommonVO().getAppFlow())){
					
					
			UserProfile userProfile=(UserProfile)travelInsuranceVO.getCommonVO().getLoggedInUser();
			int BrokerId=userProfile.getRsaUser().getBrokerId();
			String profile=userProfile.getRsaUser().getProfile();
			if(BrokerId!=0 || profile.equalsIgnoreCase("Broker")){
			isRenewalQuote=true;
			}
			
		}
		}
		request.setAttribute("isRenewalQuote", isRenewalQuote);
		//CR#13983 -changes for renewal quote validation
		//CTS 06.08.2020 CR#613 -Document attachment change
		boolean documentexists=PLdocumentExists(travelInsuranceVO);
		request.setAttribute("documentexists", documentexists);
		//CTS 06.08.2020 CR#613 -Document attachment change
		
		} catch( Exception exp ){
			exp.printStackTrace();
			UserProfile userProfile =  AppUtils.getUserDetailsFromSession( request );
			AppUtils.setUserProfileDetsToRequest(request,userProfile);
			AppUtils.addErrorMessage( request, "pas.something.went.wrong" );
			
			Redirection redirection = new Redirection( "/jsp/my-transactionsContent.jsp", Type.TO_JSP );
			responseObj.setRedirection(redirection );
			responseObj.addErrorKey("pas.something.went.wrong");
			responseObj.setResponseType(Response.Type.DOJO_IFRAME);
			response.setHeader("travelLoadError","travelLoadError");
			return responseObj;
			
		}
		return responseObj;
	}
	
	
	private void setDefaultAndCommonValues( TravelInsuranceVO travelInsuranceVO, HttpServletRequest request,CommonVO commonVO ){

		request.setAttribute( "commissionPercentage", travelInsuranceVO.getCommission() );
		double totalPremium = 0,premiumAfterDiscount=0,payablePremium=0,commissionAmount=0,vatAmt = 0;
		double totalDiscountAmount = 0;
		Double promoDiscountPerc = null;
		double promoDiscountAmt = 0;
		/*added by Dileep for Travel VAT*/
		double transaction_premium=0.0 , vatTaxPerc=0.0, viewVatAmount=0.0 ,viewTotalAmount=0.0 ;
	//	double transactionPremium=0.0;
		double transactionEndtPrm=0.0;
		double vatTaxDisplay = 0.0;
		/*Ends*/
		//BigDecimal bgPayablePremium = new BigDecimal(0);
		String shortTerm = request.getParameter( "shortTerm" );
		boolean isPolicyCancelled = ( Utils.getSingleValueAppConfig( "POLICY_CANCELLED" ) ).equals( commonVO.getStatus().toString() ) ;
		int loggedInLoc = commonVO.getLocCode();
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
				
		List<TravelPackageVO> travelPackageList = travelInsuranceVO.getTravelPackageList();
		PolicyDataVO policyDataVO = (PolicyDataVO) request.getAttribute( com.Constant.CONST_CANCELDETAILS );
		
		if(!Utils.isEmpty( policyDataVO ) &&  !Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
			
			/*Added for Short Term Cancellation for OMAN*/
			if(!Utils.isEmpty( shortTerm ) && "true".equals( shortTerm ) ){
				//double refundAmtPerc =PremiumHelper.getRefundPerc(policyDataVO);
				totalPremium = PremiumHelper.totalCancelPrmShortTerm(policyDataVO);
			}
			else{
				totalPremium = getTotalPremiumForCancellation(policyDataVO , travelInsuranceVO.getPremiumVO());
			}
		}else{
			for(TravelPackageVO travelPackage:travelPackageList){
				if(travelPackage.getIsSelected()){
					totalPremium = travelPackage.getPremiumAmt() ;			
					promoDiscountPerc = travelPackage.getPromoDiscPerc();
				}
			}
		}
		/*
		 * In case of cancelled policy, the new premium will be present in 
		 * endorsmentVO.getPremiumVO().getPremiumAmt() and travelPackage.getPremiumAmt() will contain the package premium
		 */
		if( commonVO.getAppFlow().equals( Flow.VIEW_POL ) && commonVO.getStatus() == SvcConstants.DEL_SEC_LOC_STATUS ){
			EndorsmentVO endorsmentVO = travelInsuranceVO.getEndorsmentVO().get( 0 );
			double totalPremiumActual = endorsmentVO.getPremiumVO().getPremiumAmt();
			//totalPremium is inclusive of all discounts/loading. So reverse calculating the totalPremium by removing policy Fees and govt tax 
			totalPremiumActual-=travelInsuranceVO.getPremiumVO().getPolicyFees();
			totalPremiumActual-=travelInsuranceVO.getPremiumVO().getGovtTax();
		 	if(Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
		       // totalPremiumActual-=travelInsuranceVO.getPremiumVO().getVatTax(); //142244
		        }
			//totalPremium = travelInsuranceVO.getPremiumVO().getPremiumAmt();

			//totalPremium is inclusive of all discounts/loading. So reverse calculating the discounts, totalPremium and premiumAfterDiscount
			double loadDiscAmt = 0;

			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_CURR_PROMO_DISC_SUM, travelInsuranceVO.getCommonVO().getPolicyId(),
					travelInsuranceVO.getCommonVO().getEndtId() );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				loadDiscAmt = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
			}
			totalDiscountAmount += loadDiscAmt;
			promoDiscountAmt = loadDiscAmt;

			loadDiscAmt = 0;

			java.util.List<Object> valueHolder1 = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_CURR_DISC_LOAD_SUM, travelInsuranceVO.getCommonVO().getPolicyId(),
					travelInsuranceVO.getCommonVO().getEndtId() );
			if( !Utils.isEmpty( valueHolder1 ) && valueHolder1.size() > 0 && !Utils.isEmpty( valueHolder1.get( 0 ) ) ){
				loadDiscAmt = ( (BigDecimal) valueHolder1.get( 0 ) ).doubleValue();
			}
			totalDiscountAmount += loadDiscAmt;

			totalPremium = totalPremiumActual - totalDiscountAmount;
			totalPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( new BigDecimal( totalPremium ), commonVO.getLob().name() ) );
			totalDiscountAmount = Double.parseDouble( Currency.getUnformattedScaledCurrency( new BigDecimal( totalDiscountAmount ), commonVO.getLob().name() ) );

			premiumAfterDiscount = totalPremiumActual;
			
			/* 
			 * Short Term Travel cancellation -> full refund always.
			 * So policy fees and govt tax should not be included in new premium
			 */
			govtTaxCalculation(travelInsuranceVO, request, commonVO,
					premiumAfterDiscount);
			
			/* For canceled policies, discount percent is 0.0 - Anveshan*/
			if (isPolicyCancelled && !Utils.isEmpty( travelInsuranceVO.getPremiumVO().getDiscOrLoadAmt() )) {
				totalDiscountAmount = travelInsuranceVO.getPremiumVO().getDiscOrLoadAmt().doubleValue();
				premiumAfterDiscount = totalPremium + totalDiscountAmount;
			}
			if( travelInsuranceVO.getPolicyType().toString().equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ){
				payablePremium = premiumAfterDiscount;
			}
			else{
					//Travel VAT
					payablePremium = premiumAfterDiscount + travelInsuranceVO.getPremiumVO().getGovtTax() + travelInsuranceVO.getPremiumVO().getPolicyFees()+travelInsuranceVO.getPremiumVO().getVatTax();
			}
			if( !Utils.isEmpty( travelInsuranceVO.getCommission() ) ){
				
				if(loggenInLoc.equals("50")) {
					//Added for Bahrain Commission changes
					double formattedPrem=SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(premiumAfterDiscount, commonVO.getLob().toString()).replace(",", "")));
					commissionAmount = ( travelInsuranceVO.getCommission() * formattedPrem ) / 100;
					commissionAmount=SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(commissionAmount, commonVO.getLob().toString()).replace(",", "")));			

				}
				else {
					commissionAmount = ( travelInsuranceVO.getCommission() * premiumAfterDiscount ) / 100;
				}
			}

			travelInsuranceVO.getPremiumVO().setPromoDiscPerc( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() );
			/*Dileep for Travel VAT*/
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						travelInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
			}
			if(loggedInLoc == 50) {
				double vatTax = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
						travelInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						vatTax, commonVO.getLob().toString()));
			}
			
		}

		else if(!Utils.isEmpty( travelInsuranceVO.getPremiumVO() )){
			
			if( !Utils.isEmpty( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() ) ){
				totalDiscountAmount -= ( ( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() * totalPremium ) / 100 );
				promoDiscountAmt =  ( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() * totalPremium ) / 100 ;
			}
			
			if(!Utils.isEmpty( travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc() )){
				totalDiscountAmount  += (travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100;
			}
			if(loggenInLoc.equals("30"))
				totalDiscountAmount = getRoundOffCalculationOmanTravel(totalDiscountAmount);
			totalPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency(new BigDecimal( totalPremium ),commonVO.getLob().name() ) );
			totalDiscountAmount = Double.parseDouble( Currency.getUnformattedScaledCurrency(new BigDecimal( totalDiscountAmount ),commonVO.getLob().name() ) );
			
			premiumAfterDiscount = totalPremium + totalDiscountAmount;
			
			govtTaxCalculation(travelInsuranceVO, request, commonVO,
					premiumAfterDiscount);
			Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
			//Commented by Dileep and added*/
			//travelInsuranceVO.getPremiumVO().setVatTax(vatAmtAndDaysMap.get(4));	
			//Travel VAT 22-10-2018 
			
			if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)&& Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ) )){
			    viewVatAmount=travelInsuranceVO.getPremiumVO().getVatTax();
			    viewTotalAmount=travelInsuranceVO.getPremiumVO().getPremiumAmt();
            }else if (travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_QUO)&&travelInsuranceVO.getCommonVO().getIsQuote()){
                viewVatAmount=travelInsuranceVO.getPremiumVO().getVatTax();
                viewTotalAmount=travelInsuranceVO.getPremiumVO().getPremiumAmt();
            }
			vatAmt = vatAmtAndDaysMap.get(4);
			transactionEndtPrm = vatAmtAndDaysMap.get(7);
			vatTaxDisplay =  vatAmtAndDaysMap.get(5);
			travelInsuranceVO.getPremiumVO().setVatTax(vatAmt);	
			//We can test below line if above line will not work- Dileep 
			//travelInsuranceVO.getPremiumVO().setVatTax(Double.parseDouble(Currency
					//.getUnformattedScaledCurrency(new BigDecimal(vatAmt),
					//		commonVO.getLob().name())));
			//Ends
			
			//Setting Policy Fees for VASCO Scheme
			String schemeCode = Utils.getSingleValueAppConfig( "VASCO_SCH_CODE" );
			double VascoPolFeesPerc = 0.0;
			double VascoPolFees = 0.0;
			if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getSchemeCode() ) && schemeCode.equals( travelInsuranceVO.getScheme().getSchemeCode().toString() ) ){
				java.util.List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_T_MAS_GROUP_UP, schemeCode );
				if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
					for( Object[] result : resultSet ){
				
					Boolean percentCheck = true;
					String pOrF = (String) result[0];
					if(pOrF.equals("P") ){
						percentCheck = true;
					}
					else{
						percentCheck = false;
					}
					VascoPolFeesPerc = ( (BigDecimal) result[1] ).doubleValue();
					
					if(!Utils.isEmpty( commonVO ) && commonVO.getIsQuote()){
						if(percentCheck){
							VascoPolFees = totalPremium * VascoPolFeesPerc / 100;
						}
						else{
							VascoPolFees = VascoPolFeesPerc;
						}
						
						travelInsuranceVO.getPremiumVO().setPolicyFees(VascoPolFees);
						}
					}
				}
			}
			
			/* 
			 * Short Term Travel cancellation -> full refund always.
			 * So policy fees and govt tax should not be included in new premium
			 */
			if( !Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getEndorsmentVO() )
					&& policyDataVO.getPolicyType().toString().equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) && !loggenInLoc.equals("30")){
				payablePremium = premiumAfterDiscount + travelInsuranceVO.getPremiumVO().getPolicyFees();
			}
			else if(!loggenInLoc.equals("30")){
				payablePremium = premiumAfterDiscount + travelInsuranceVO.getPremiumVO().getGovtTax() + travelInsuranceVO.getPremiumVO().getPolicyFees();
				if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getVatTax())) {
						/*Dileep - Commented and added*/
					//payablePremium += Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(travelInsuranceVO.getPremiumVO().getVatTax()), "SBS"));
					transaction_premium = payablePremium + vatAmt;
					/*Ends*/
				}
			}
			else
			{
				payablePremium = premiumAfterDiscount + travelInsuranceVO.getPremiumVO().getGovtTax() + getRoundOffCalculationOmanTravel(travelInsuranceVO.getPremiumVO().getPolicyFees());
				if(loggedInLoc == 20 || loggedInLoc == 21) {
					payablePremium = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), "SBS"));
				}
				if(loggedInLoc == 50) {
					payablePremium = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), commonVO.getLob().toString()));
				}
				
			}
			if(!Utils.isEmpty( travelInsuranceVO.getCommission() )){
				commissionAmount = (travelInsuranceVO.getCommission()*premiumAfterDiscount)/100;
				if(loggenInLoc.equals("30"))
					commissionAmount  = getRoundOffCalculationOmanTravel(commissionAmount);
				else if(loggenInLoc.equals("50")) {
					//Added for Bahrain Commission changes
					double formattedPrem=SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(premiumAfterDiscount, commonVO.getLob().toString()).replace(",", "")));
					commissionAmount = ( travelInsuranceVO.getCommission() * formattedPrem ) / 100;
					commissionAmount=SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(commissionAmount, commonVO.getLob().toString()).replace(",", "")));			

				}
				
			}
			
			travelInsuranceVO.getPremiumVO().setPromoDiscPerc( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() );
			/* Travel VAT*/
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						travelInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
			}
			if(loggedInLoc == 50) {
				double vatTax = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
						travelInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						vatTax, commonVO.getLob().toString()));
			}
			
		}
		

		if(commonVO.getAppFlow().equals(  Flow.AMEND_POL )){
			//for initial page load in AMEND there should not be any change in payable/refund premium
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(0.0), "SBS"));
			}
			if(loggedInLoc == 50) {
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(0.0), commonVO.getLob().toString()));
			}
			
			
			request.setAttribute( "initialAmendPageLoad", "initialAmendPageLoad");
			if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy() && travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
				vatAmt=0.0; //153167
			}
			
			if(!Utils.isEmpty(SvcConstants.POL_STATUS_ACCEPT) && 
					(travelInsuranceVO.getCommonVO().getStatus() == SvcConstants.POL_STATUS_ACCEPT)) { 
				vatAmt = travelInsuranceVO.getPremiumVO().getOldVatAmt();							
			}					
			else{
			/*Endoresment Cumulative Vat amount*/
			vatAmt=travelInsuranceVO.getPremiumVO().getOldVatAmt()+vatAmt;
			}
		
			travelInsuranceVO.getPremiumVO().setVatTax(vatAmt);	
			//Travel VAT
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
						vatTaxDisplay, "SBS"));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency.getFormattedCurrency(
	            		(transactionEndtPrm + vatTaxDisplay), "SBS"));
			}
			if(loggedInLoc == 50) {
				vatTaxDisplay = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
						vatTaxDisplay, commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
						vatTaxDisplay, commonVO.getLob().toString()));
				transactionEndtPrm = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
						transactionEndtPrm, commonVO.getLob().toString()).replace(",", "")));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency.getFormattedCurrency(
	            		(transactionEndtPrm + vatTaxDisplay), commonVO.getLob().toString()));
			}
			
		} else if(!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO())){
			EndorsmentVO endorsmentVO = travelInsuranceVO.getEndorsmentVO().get( 0 );
				/*Added on 17 Oct*/
			      if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()){
			                    /*Refund Scenario*/
			                    request.setAttribute("legacyRefund",travelInsuranceVO.getCommonVO().getIslegacyPolicy());
			                    
			       } 
				//Travel VAT
			      if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)&& Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ) )){
		                viewVatAmount=travelInsuranceVO.getPremiumVO().getVatTax();
		                viewTotalAmount=travelInsuranceVO.getPremiumVO().getPremiumAmt();
		            }else if (travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_QUO)&&travelInsuranceVO.getCommonVO().getIsQuote()){
		                viewVatAmount=travelInsuranceVO.getPremiumVO().getVatTax();
		                viewTotalAmount=travelInsuranceVO.getPremiumVO().getPremiumAmt();
		            }
			if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
				//request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt(),commonVO.getLob().toString()));
				if(loggedInLoc == 20 || loggedInLoc ==21) {
					request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt()),"SBS"));

					//Travel VAT
					Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
					transactionEndtPrm = vatAmtAndDaysMap.get(7);
		            vatTaxDisplay =  vatAmtAndDaysMap.get(5);
		            
		            transaction_premium=Math.abs(transactionEndtPrm)+Math.abs(vatTaxDisplay);
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
		                    .getFormattedCurrency(new BigDecimal(transaction_premium),
		                            "SBS"));
		            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
		                    Math.abs(vatTaxDisplay), "SBS"));
				}
				if(loggedInLoc == 50) {
					double payable_premium_rounded = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt()),commonVO.getLob().toString()).replace(",", ""));
					payable_premium_rounded = SvcUtils.getRoundingOffBah(payable_premium_rounded);
					request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(payable_premium_rounded),commonVO.getLob().toString()));

					//Travel VAT
					Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
					transactionEndtPrm = vatAmtAndDaysMap.get(7);
		            vatTaxDisplay =  vatAmtAndDaysMap.get(5);
		            vatTaxDisplay = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
		                    .getFormattedCurrency(new BigDecimal(vatTaxDisplay),
		                    		commonVO.getLob().toString()).replace(",", "")));
		            transactionEndtPrm = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
		                    .getFormattedCurrency(new BigDecimal(transactionEndtPrm),
		                    		commonVO.getLob().toString()).replace(",", "")));
		            transaction_premium=Math.abs(transactionEndtPrm)+Math.abs(vatTaxDisplay);
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
		                    .getFormattedCurrency(new BigDecimal(transaction_premium),
		                    		commonVO.getLob().toString()));
		            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
		                    Math.abs(vatTaxDisplay), commonVO.getLob().toString()));
				}
				
				
			}else{	
				
				//request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt(),commonVO.getLob().toString()));
				if(loggedInLoc == 20 || loggedInLoc ==21) {
					endorsmentVO.getPremiumVO().setPremiumAmt(payablePremium);
					request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt()),"SBS"));
				}
				if(loggedInLoc == 50) {
					double payablePremiumRound = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium),commonVO.getLob().toString()).replace(",", "")));
					endorsmentVO.getPremiumVO().setPremiumAmt(payablePremiumRound);
					double payable_premium_rounded = payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt();
					payable_premium_rounded = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payable_premium_rounded),commonVO.getLob().toString()).replace(",", ""));
					payable_premium_rounded = SvcUtils.getRoundingOffBah(payable_premium_rounded);
					request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getUnformattedScaledCurrency(new BigDecimal(payable_premium_rounded),commonVO.getLob().toString()));
				}
				
			}
		}else {
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), "SBS"));
				/* added by Dileep for Travel VAT*/
			  
		
			request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
					.getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
							"SBS"));
			request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
					travelInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
			request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
					travelInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
			request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
					travelInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
			//Travel VAT 22-10-2018
			if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)&& Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ) )){
               
                request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
                        viewVatAmount, "SBS"));
                request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                        viewVatAmount, "SBS"));
                transaction_premium = payablePremium + viewVatAmount;
                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
                                "SBS"));
            }
			else if (travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_QUO)&&travelInsuranceVO.getCommonVO().getIsQuote()){
			    request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
                        viewVatAmount, "SBS"));
                request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                        viewVatAmount, "SBS"));
                transaction_premium = payablePremium + viewVatAmount;
                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
                                "SBS"));
            }
			}
			if(loggedInLoc == 50) {
				double payablePremiumRounded  = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremiumRounded), commonVO.getLob().toString()));
				/* added by Dileep for Travel VAT*/
			  
		
			request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
					.getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
							commonVO.getLob().toString()));
			double vatTaxRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
					travelInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()).replace(",", "")));
			request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
					vatTaxRounded, commonVO.getLob().toString()));
			request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
					vatTaxRounded, commonVO.getLob().toString()));
			request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
					vatTaxRounded, commonVO.getLob().toString()));
			//Travel VAT 22-10-2018
			if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)&& Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ) )){
               viewVatAmount = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()).replace(",", "")));
                request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()));
                request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()));
                payablePremiumRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
                		new BigDecimal(payablePremium), commonVO.getLob().toString()).replace(",", "")));
                transaction_premium = payablePremiumRounded + viewVatAmount;
                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
                        		commonVO.getLob().toString()));
            }
			else if (travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_QUO)&&travelInsuranceVO.getCommonVO().getIsQuote()){
				viewVatAmount = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()).replace(",", "")));
			    request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()));
                request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                        viewVatAmount, commonVO.getLob().toString()));
                payablePremiumRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
                		new BigDecimal(payablePremium), commonVO.getLob().toString()).replace(",", "")));
                transaction_premium = payablePremiumRounded + viewVatAmount;
                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
                        		commonVO.getLob().toString()));
            }
			}
			/*Ends*/
		}
		

		//for the 'direct walk in',direct web,direct call center and brokers, commission field should be disabled
		if(AppUtils.isBrokerOrDirectWalkin( travelInsuranceVO, request )){
			request.setAttribute( "isCommissionEditable",  AppConstants.TRUE);
		}else{
			request.setAttribute( "isCommissionEditable",  AppConstants.FALSE);
		}
		
		/*
		 * CR:123969  
		 * Added by Vishwa to disable discountLoading for FGB broker users 
		 */	

		/*
		 * CR:141906  
		 * Added by Sandhya to enable discountLoading for FGB/FAB broker users 
		 
		if(AppUtils.isFGBBroker( travelInsuranceVO, request )){
			LOGGER.info("isFGBBroker.. true");
			request.setAttribute( "isDiscLoadEditable",  AppConstants.TRUE);
		}else{
			LOGGER.info("isFGBBroker.. false");	
			request.setAttribute( "isDiscLoadEditable",  AppConstants.FALSE);
		}*/
		request.setAttribute( "isDiscLoadEditable",  AppConstants.FALSE);
		if( Flow.VIEW_QUO.equals( commonVO.getAppFlow() ) && !Utils.isEmpty( request.getParameter( "buttonClick" ) )
				&& request.getParameter( "buttonClick" ).equalsIgnoreCase( "saveQuote" ) && commonVO.getStatus().equals( Integer.valueOf( AppConstants.QUOTE_ACTIVE ) ) ){
			request.setAttribute( "renewalC2P", "true" );
		}
		
		//for ANA Scheme, policy fees is editable
		String[] schemeCodes = Utils.getMultiValueAppConfig( "POLICY_FEES_ENABLED_SCHEMES" );
		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getSchemeCode() ) && Arrays.asList( schemeCodes ).contains( travelInsuranceVO.getScheme().getSchemeCode().toString() ) ){
			request.setAttribute( com.Constant.CONST_ISPOLICYFEESEDITABLE, AppConstants.TRUE );
		}
		else{
			request.setAttribute( com.Constant.CONST_ISPOLICYFEESEDITABLE, AppConstants.FALSE );
		}
		
		if(loggenInLoc.equals("30"))
		{
			request.setAttribute( com.Constant.CONST_ISPOLICYFEESEDITABLE, AppConstants.TRUE );
		}
		
		
		//for VASCO Scheme, policy fees is displayed with populated value
		String schemeCode = Utils.getSingleValueAppConfig( "VASCO_SCH_CODE" );
		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getSchemeCode() ) && schemeCode.equals( travelInsuranceVO.getScheme().getSchemeCode().toString() ) ){
			request.setAttribute( com.Constant.CONST_ISPOLICYFEESEDITABLE, "VASCO" );
		}
		else{
			request.setAttribute( com.Constant.CONST_ISPOLICYFEESEDITABLE, AppConstants.FALSE );
		}
		/* 
		 * Short Term Travel cancellation -> full refund always.
		 * So policy fees and govt tax should not be included in new premium
		 */
		if( ( !Utils.isEmpty( policyDataVO ) && policyDataVO.getPolicyType().toString().equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) && !loggenInLoc.equals("30"))
				|| travelInsuranceVO.getPolicyType().toString().equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) && !loggenInLoc.equals("30") && commonVO.getAppFlow().equals( Flow.VIEW_POL )
				&& commonVO.getStatus() == SvcConstants.DEL_SEC_LOC_STATUS ){
			if(!Utils.isEmpty( travelInsuranceVO.getPremiumVO().getPolicyFees() )){
				request.setAttribute( com.Constant.CONST_POLICYFEES, Currency.getFormattedCurrency(travelInsuranceVO.getPremiumVO().getPolicyFees(),commonVO.getLob().toString() ) );
			}else{
				request.setAttribute( com.Constant.CONST_POLICYFEES, 0.0 );
			}
			request.setAttribute( com.Constant.CONST_GOVTTAX, 0.0 );
		}
		else{
			request.setAttribute( com.Constant.CONST_POLICYFEES, Currency.getFormattedCurrency(travelInsuranceVO.getPremiumVO().getPolicyFees(),commonVO.getLob().toString() ) );
			request.setAttribute( com.Constant.CONST_GOVTTAX, Currency.getFormattedCurrency(travelInsuranceVO.getPremiumVO().getGovtTax(),commonVO.getLob().toString() ) );
			//vatTaxCalculation(travelInsuranceVO, request, commonVO, premiumAfterDiscount);
			Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
			travelInsuranceVO.getPremiumVO().setVatTax(vatAmtAndDaysMap.get(4));
			//Travel VAT
            if (!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO())) {
                if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_CANCELDETAILS))) {
                    transactionEndtPrm = vatAmtAndDaysMap.get(7);
                    vatTaxDisplay = vatAmtAndDaysMap.get(5);
                    
                
 					if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy() && travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
                        vatTaxDisplay = vatAmtAndDaysMap.get(5);
                    }
                    if(loggedInLoc == 20 || loggedInLoc == 21) {
                    	transaction_premium = Math.abs(transactionEndtPrm) + Math.abs(vatTaxDisplay);
                    	request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                                .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),  /*During cancel to hold vat amount for the earned period*/
                                        "SBS"));
                        request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
                                Currency.getFormattedCurrency(Math.abs(vatTaxDisplay), "SBS"));
                    }
                    if(loggedInLoc == 50) {
                    	double transactionEndtPrmRounded = SvcUtils.getRoundingOffBah(Math.abs(transactionEndtPrm));
                    	Double vatTaxDisplayRounded = SvcUtils.getRoundingOffBah(Math.abs(vatTaxDisplay));
                    	transaction_premium = Math.abs(transactionEndtPrmRounded) + Math.abs(vatTaxDisplayRounded);
                    	request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                                .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),  /*During cancel to hold vat amount for the earned period*/
                                        commonVO.getLob().toString()));
                        request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
                                Currency.getFormattedCurrency(Math.abs(vatTaxDisplayRounded), commonVO.getLob().toString()));
                    }
                    
                }
            }
		}
		
		// 131378
		int isOldOrNewQuote=0;
		try {
			 isOldOrNewQuote = checkPolPreparedDt (travelInsuranceVO);
				LOGGER.info(" ***isOldOrNewQuote.. ****"+isOldOrNewQuote);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("isOldOrNewQuote", isOldOrNewQuote);
		
		/*
		 * D2C : Custom Discount Picked from appconfig.properties
		 */
		if(!Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getPremiumVO()) && travelInsuranceVO.getAuthenticationInfoVO().getCreatedBy().equals("991") 
				&& travelInsuranceVO.getCommonVO().getStatus()==20 && travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()==10
				){
			travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc(Double.parseDouble(Utils.getSingleValueAppConfig("B2B_DISCOUNT_TRAVEL")));
		}
		
		if(isPolicyCancelled){
			request.setAttribute( com.Constant.CONST_PREMIUMDISCOUNTAMOUNT,Currency.getFormattedCurrency(totalDiscountAmount, commonVO.getLob().toString()));
			travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc( 0.0 );
			//TARVEL VAT
			Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
			travelInsuranceVO.getPremiumVO().setVatTax(vatAmtAndDaysMap.get(4));
			if(loggedInLoc == 20 || loggedInLoc == 21) {
				if(viewVatAmount==0){
				    request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
		                    Math.abs(viewVatAmount), "SBS"));
				    transaction_premium=vatAmtAndDaysMap.get(7);
				    request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
	                                "SBS"));
				    
				}else{
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
				        Math.abs(vatAmtAndDaysMap.get(4)), "SBS"));
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
				        Math.abs(vatAmtAndDaysMap.get(5)), "SBS"));
				}
			}
			if(loggedInLoc == 50) {
				if(viewVatAmount==0){
				    request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
		                    Math.abs(viewVatAmount), commonVO.getLob().toString()));
				    transaction_premium=vatAmtAndDaysMap.get(7);
				    double transaction_premium_rounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
	                        .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium)),
	                        		commonVO.getLob().toString()).replace(",", "")));
				    request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(transaction_premium_rounded,
	                        		commonVO.getLob().toString()));
				    
				}else{
					double vatTaxRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
					        Math.abs(vatAmtAndDaysMap.get(4)), commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						vatTaxRounded, commonVO.getLob().toString()));
				double vatTaxDisplayRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
				        Math.abs(vatAmtAndDaysMap.get(5)), commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
						vatTaxDisplayRounded, commonVO.getLob().toString()));
				}
			}
			
		}
		else {
			request.setAttribute( com.Constant.CONST_PREMIUMDISCOUNTAMOUNT, Currency.getFormattedCurrency((travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100, commonVO.getLob().toString()) );
		}
		request.setAttribute( "distributionChannel", travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel() );
		request.setAttribute( "premiumAfterCoverDiscount", Currency.getFormattedCurrency( totalPremium,commonVO.getLob().toString() ) );
		request.setAttribute( "totalPremium", Currency.getFormattedCurrency( totalPremium,commonVO.getLob().toString() ));
		if(loggenInLoc.equals("30"))
			request.setAttribute( com.Constant.CONST_PREMIUMDISCOUNTAMOUNT,  new Double(Currency.getFormattedCurrency(getRoundOffCalculationOmanTravel((travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100),commonVO.getLob().toString() )).doubleValue());
		else
			request.setAttribute( com.Constant.CONST_PREMIUMDISCOUNTAMOUNT,  Currency.getFormattedCurrency((travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100,commonVO.getLob().toString() ));
		request.setAttribute( "premiumAfterDiscount",  Currency.getFormattedCurrency(premiumAfterDiscount,commonVO.getLob().toString() ) );
		if(loggedInLoc == 20 || loggedInLoc == 21) {
			request.setAttribute( "commissionAmount",   Currency.getFormattedCurrency(commissionAmount,"SBS" ));
			request.setAttribute( "promoDiscountAmt", Currency.getFormattedCurrency(promoDiscountAmt,commonVO.getLob().toString() ));
			request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), "SBS"));
		}
		if(loggedInLoc == 50) {
			request.setAttribute( "commissionAmount",   Currency.getFormattedCurrency(commissionAmount, commonVO.getLob().toString() ));
			request.setAttribute( "promoDiscountAmt", Currency.getFormattedCurrency(promoDiscountAmt,commonVO.getLob().toString() ));
			double payablePremiumRounded = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremium), commonVO.getLob().toString()).replace(",", "")));
			request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM, Currency.getUnformattedScaledCurrency(new BigDecimal(payablePremiumRounded), commonVO.getLob().toString()));
		}
		
		/*Added by Dileep*/
		/*request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
				.getFormattedCurrency(new BigDecimal(transaction_premium),
						"SBS")); */
		/*Ends*/
		
		if (Utils.isEmpty(travelInsuranceVO.getEndorsmentVO())) {
            if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_CANCELDETAILS))) {
            	if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()){
                    /*Refund Scenario*/
                    request.setAttribute("legacyRefund",travelInsuranceVO.getCommonVO().getIslegacyPolicy());
                    
                }
                Map<Integer, Double> vatAmtAndDaysMap = AppUtils.calculateVatTaxAndVatableAmount(travelInsuranceVO, premiumAfterDiscount, request);
                transaction_premium=vatAmtAndDaysMap.get(6);
                vatTaxDisplay=vatAmtAndDaysMap.get(4);
                if(loggedInLoc == 20 || loggedInLoc == 21) {
                	request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                            .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium + Math.abs(vatTaxDisplay)   )),
                                    "SBS"));
                    request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                            Math.abs(vatTaxDisplay), "SBS"));
                }
                if(loggedInLoc == 50) {
                	vatTaxDisplay = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getFormattedCurrency(
                            Math.abs(vatTaxDisplay), commonVO.getLob().toString()).replace(",", "")));
                	request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
                            .getFormattedCurrency(new BigDecimal(Math.abs(transaction_premium + Math.abs(vatTaxDisplay)   )),
                                    commonVO.getLob().toString()));
                    request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
                            Math.abs(vatTaxDisplay), commonVO.getLob().toString()));
                }
                
            }
		}
	if (commonVO.getAppFlow().equals(Flow.AMEND_POL)) {
           

                if (!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO()) && 
                        travelInsuranceVO.getEndorsmentVO().size() > 0  &&
                        !Utils.isEmpty(travelInsuranceVO.getEndorsmentVO().get(0))) {
                    
                    EndorsmentVO endorsmentVO=travelInsuranceVO.getEndorsmentVO().get(0);
                    
         /*           if (  endorsmentVO.getEndType()!=null  &&
                            endorsmentVO.getEndType().equalsIgnoreCase( AppConstants.REFUND_TYPE)) {*/
                   if( endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt() < 0 ){

                    if (!Utils.isEmpty(travelInsuranceVO.getCommonVO()
                             .getIslegacyPolicy())
                            && travelInsuranceVO.getCommonVO()
                                    .getIslegacyPolicy()
                            && travelInsuranceVO.getPremiumVO().getOldVatAmt() == 0) {

                        vatAmt = 0.0; // 153167
                        if(loggedInLoc == 20 || loggedInLoc == 21) {
                        	request.setAttribute(com.Constant.CONST_VATTAX, Currency
                                    .getFormattedCurrency(Math.abs(vatAmt), "SBS"));
                        }
                        if(loggedInLoc == 50) {
                        	request.setAttribute(com.Constant.CONST_VATTAX, Currency
                                    .getFormattedCurrency(Math.abs(vatAmt), commonVO.getLob().toString()));
                        }
                        
                    }
                }
            }
        }
		request.setAttribute( "vatCode", travelInsuranceVO.getPremiumVO().getVatCode());		
	}
	
	
	//131378 : To view old or new cover details
	/**
	 * @param travelInsuranceVO
	 * */
	private int checkPolPreparedDt(TravelInsuranceVO travelInsuranceVO) throws ParseException {
		
			int isTerrCruiseInductionDate = 0;
			String d2 = Utils.getSingleValueAppConfig("TerrCruiseInductionDate");
			SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
			Long QuoteNum = travelInsuranceVO.getQuoteNo();
			if(Utils.isEmpty(QuoteNum)){
				QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
			}
			Date polPrepDt = DAOUtils.getPreparedDateForCovers(QuoteNum);
			if(!Utils.isEmpty(polPrepDt)){
			LOGGER.debug("polPrepDt "+polPrepDt);
			}
			Date prodDt = s2.parse(d2);
			if(polPrepDt.after(prodDt)){
				isTerrCruiseInductionDate = 1;
			}
			return isTerrCruiseInductionDate;
	}

	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param commonVO
	 * @param premiumAfterDiscount
	 * @param policyDataVO
	 */
	private void govtTaxCalculation(TravelInsuranceVO travelInsuranceVO,
			HttpServletRequest request, CommonVO commonVO,
			double premiumAfterDiscount) {
			String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			request.setAttribute( com.Constant.CONST_POLICYFEES, Currency.getFormattedCurrency(travelInsuranceVO.getPremiumVO().getPolicyFees(),commonVO.getLob().toString() ) );
			request.setAttribute( "govtTaxPerc", String.valueOf(travelInsuranceVO.getGovtTaxPerc()) );
			
			double govtTax = (premiumAfterDiscount *  travelInsuranceVO.getGovtTaxPerc())/ 100;
			if(loggenInLoc.equals("30"))
				govtTax = getRoundOffCalculationOmanTravel(govtTax);
			travelInsuranceVO.getPremiumVO().setGovtTax(govtTax);
			
			request.setAttribute( com.Constant.CONST_GOVTTAX, Currency.getFormattedCurrency( govtTax ,commonVO.getLob().toString() ) );
	}
	
	
	/**
	 * 142244 - Vat Tax Percent, Amount calculation
	 * @param travelInsuranceVO
	 * @param request
	 * @param commonVO
	 * @param premiumAfterDiscount
	 * @param policyDataVO
	 */
	/*private void vatTaxCalculation(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request, CommonVO commonVO,
		double premiumAfterDiscount) {
			
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			
		double vatTaxPerc = travelInsuranceVO.getPremiumVO().getVatTaxPerc();
			
		//BigDecimal vatTaxPerc = BigDecimal.valueOf( vatTaxPerc ).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
		request.setAttribute( "vatTaxPerc", String.valueOf(vatTaxPerc) );
			
		BigDecimal vatTaxAmount = BigDecimal.valueOf(premiumAfterDiscount)
													.multiply(BigDecimal.valueOf(vatTaxPerc))
													.divide(BigDecimal.valueOf(100))
													.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
		if(!loggenInLoc.equals("30"))
			vatTax = getRoundOffCalculationOmanTravel(vatTaxAmount.doubleValue());
				
		travelInsuranceVO.getPremiumVO().setVatTax(vatTaxAmount.doubleValue());			
		request.setAttribute( com.Constant.CONST_VATTAX, String.valueOf( vatTaxAmount) );
	}*/
	
	/*private void vatTaxCalculation(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request, CommonVO commonVO,
		double premiumAfterDiscount) {
			
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);			
		request.setAttribute( "vatTaxPerc", String.valueOf(travelInsuranceVO.getPremiumVO().getVatTaxPerc()) );			
		double vatTax = (premiumAfterDiscount *  travelInsuranceVO.getPremiumVO().getVatTaxPerc())/ 100;			
		if(!loggenInLoc.equals("30"))
				vatTax = getRoundOffCalculationOmanTravel(vatTax);
				
		travelInsuranceVO.getPremiumVO().setVatTax(vatTax);			
		request.setAttribute( com.Constant.CONST_VATTAX, Currency.getFormattedCurrency( vatTax ,commonVO.getLob().toString() ) );
	}*/
	
	/**
	 * @param policyDataVO
	 * @param premiumVO 
	 * @return
	 * calculation of totalPremium for 'Total Premium' field in premium detail 
	 */
	private double getTotalPremiumForCancellation( PolicyDataVO policyDataVO, PremiumVO premiumVO ){
		
		double newAnnualizedPremium = 0;
		double totalPremium = 0;
		double totalDiscountPercent = 0;
		
		/*if(policyDataVO.getEndorsmentVO().size()>0){
			newAnnualizedPremium = policyDataVO.getEndorsmentVO().get( 0 ).getPremiumVO().getPremiumAmt();
			
			if(!Utils.isEmpty( premiumVO.getPromoDiscPerc() )){
				totalDiscountPercent +=  premiumVO.getPromoDiscPerc();
			}
			
			if(!Utils.isEmpty( premiumVO.getDiscOrLoadPerc() )){
				totalDiscountPercent -=  premiumVO.getDiscOrLoadPerc();
			}
			
			totalPremium = (newAnnualizedPremium * 100) / (100 - totalDiscountPercent);
		}*/
		if( !policyDataVO.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ) ){
			totalPremium = PremiumHelper.totalCancelPrm( policyDataVO, premiumVO );
		}
		return totalPremium;
	}
	
	/**
	 * 
	 * @param govtTax 
	 * @param travelInsuranceVO
	 * @param premiunFromRatingEngine
	 * @param travelerDetailsVO
	 * @return
	 */
	private double getRoundOffCalculationOmanTravel(
			double actualValue) {
	
			double formatedValue = 0;
			DecimalFormat df2 = new DecimalFormat("0.0");
			if(actualValue>0)
				df2.setRoundingMode(RoundingMode.CEILING);
			else
				df2.setRoundingMode(RoundingMode.FLOOR);
			DecimalFormat df3 = new DecimalFormat("0.000");
			
			formatedValue = new Double(df2.format(actualValue)).doubleValue();
			formatedValue = new Double(df3.format(formatedValue)).doubleValue();
			return formatedValue;
			
	}
	
	private boolean PLdocumentExists(TravelInsuranceVO travelInsuranceVO) {
		// TODO Auto-generated method stub
		boolean documentExists=false;
		Long QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		String InsuredCode=DAOUtils.FetchInsuredCode(QuoteNum.toString());
		if(!Utils.isEmpty(QuoteNum)){
			String filepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_"+Utils.getSingleValueAppConfig("TRAVEL_DEFAULT_SCREENID")+"_FOLDER" )+"/"+QuoteNum+"/";
			File folder = new File(filepath);
			File listOfFiles[] = folder.listFiles();
				if(!Utils.isEmpty(InsuredCode)){
					
					String Insuredfilepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_"+Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID")+"_FOLDER" )+"/"+InsuredCode+"/";
					File Insuredfolder = new File(Insuredfilepath);
					File EmirateslistOfFiles[] = Insuredfolder.listFiles();
			
			if(!Utils.isEmpty(listOfFiles) || !Utils.isEmpty(EmirateslistOfFiles)){
			documentExists=true;
			}
				}
		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
		return documentExists;
	}

}
