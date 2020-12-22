package com.rsaame.pas.rating.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.cts.writeRate.ErrorType;
import com.cts.writeRate.Policy;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.admin.model.Location;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.kaizen.framework.model.CalculatedPremium;
import com.rsaame.kaizen.quote.model.FactorBO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;


public class PremiumCalculator {
	GetFactorList factsIdentifier=new GetFactorList();
	Double parAvgRatingFactor=0.0;
	private final static Logger logger = Logger.getLogger(PremiumCalculator.class);
	private Integer TRAVEL_BAGGAGE_FREE_COVER = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_BAGGAGE_FREE_COVER" ) );

	public PolicyVO calculateRiskPremium(PolicyVO policy,BigDecimal renewalLoading){
		long startTime = System.currentTimeMillis();
		//ArrayList <FactorBO> factorsList=factsIdentifier.getCLSPEKey(policy);
		//SectionVO secVO=null;//=policy.SectionList.each item in the list
		IRatingInvoker ratingInvoker = new RatingInvoker();
		ArrayList <Policy> eRatorPolicyListForDetails=new ArrayList<Policy>();
		Policy[] eRatorPolicyArrayForDetails=null,eRatorPolicyArrayWithDetails=null,eRatorPolicyArrayForPremium=null,eRatorPolicyArrayWithPremium=null;

		ArrayList <CalculatedPremium> premiumList=new ArrayList<CalculatedPremium>();
		ArrayList<ArrayList<FactorBO>> sectionwiseFactors= new ArrayList<ArrayList<FactorBO>>();
		ArrayList<RatingInvocationTracker> ratingInvocationTrackerList= new ArrayList<RatingInvocationTracker>();
		boolean isPrepacked = policy.getIsPrepackaged();
		//START : Latest
		ArrayList <ArrayList<FactorBO>> PARList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> PLList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> WCList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> MoneyList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> contentList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> MBList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> BIList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> EEQList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> TravelList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> GPAList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> GITList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> DOSList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <ArrayList<FactorBO>> FIDList=new ArrayList<ArrayList<FactorBO>>();
		ArrayList <FactorBO> CLSPEfactorsList=new ArrayList <FactorBO>();
		
		FactorBO commonFactor=new FactorBO();
		Double suminsured = 0.0;
		
		/*Added to fetch the prepared Date to pass to Rating service - Ticket Id: 140443 */
		Date quotePreparedDt = GetQuotePreparedDate(policy); 
		
		if((!Utils.isEmpty(policy.getGeneralInfo())) &&(!Utils.isEmpty(policy.getGeneralInfo().getInsured()))
				&& (!Utils.isEmpty(policy.getGeneralInfo().getInsured().getBusType()))){
			int businessType = policy.getGeneralInfo().getInsured().getBusType();
			commonFactor.setFactorName("Business Type");
			commonFactor.setFactorValue(String.valueOf(businessType));
		}
		else{
			logger.error( "General Exception while invoking Rating Engine : businessType not found"  );
			
			BusinessException businessExcp=new BusinessException( "rating.invocation.generalexception", null,"policy.getGeneralInfo().getInsured().getBusType() not found");
			throw businessExcp;
			
		}
		
		
		//policy.getIsPrepackaged();
		// END : Latest
		SchemeVO scheme=policy.getScheme();
		Integer backupTariff = policy.getScheme().getTariffCode();
		policy.getScheme().setTariffCode(mapTariff(backupTariff));
		List <SectionVO> sectionList=policy.getRiskDetails();
		// Take out all sections available in the policy
		if(!Utils.isEmpty(sectionList))
		{
			HashMap <String,String> ratingTracker=new HashMap <String,String>();
			int ratingItemNumber=0;
			int riskNumber=0;
			int PARRtgItmSeqNo=0;
			int contentRtgItmSeqNo=0;
			int PLRrtgItmSeqNo=0;
			int WCRtgItmSeqNo=0;
			int BIRtgItmSeqNo=0;
			int MBRtgItmSeqNo=0;
			int EEQRtgItmSeqNo=0;
			int TravelRtgItmSeqNo=0;
			int GPARtgItmSeqNo=0;
			int GITRtgItmSeqNo=0;
			int DOSRtgItmSeqNo=0;
			int FIDRtgItmSeqNo=0;
			
			
			boolean isPARAvailable=false;
			boolean isContentAvailable=false;
			boolean isPLAvailable=false;
			boolean isWCAvailable=false;
			boolean isMoneyAvailable=false;
			boolean isBIAvailable=false;
			boolean isMBAvailable=false;
			boolean isEEQAvailable=false;
			boolean isTravelAvailable=false;
			boolean isGPAAvailable=false;
			boolean isGITAvailable=false;
			boolean isDOSAvailable=false;
			boolean isFIDAvailable=false;
			String sectionId= null;
			Map<? extends RiskGroup, ? extends RiskGroupDetails> parRiskDetailsMap = null;
			
			//Start Loop to get all Sections: For each section in the Policy 
			for(SectionVO sectionVo:sectionList){
				//Map <RiskGroup,RiskGroupDetails> riskGroupDetailsMap=sectionVo.getRiskGroupDetails();
				//START: Check sectionVo.getRiskGroupDetails() is empty or not
				if(!Utils.isEmpty(sectionVo.getRiskGroupDetails())){

				Map <? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap=sectionVo.getRiskGroupDetails();
				
				
			
				//Start Loop to get the risks associated with this section
				// If section is not location specific then this will run only once; Else loop will run as many times=location available

				
				
				for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet()) {
					if(Utils.isEmpty(entry)){
						logger.error( "Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null" );
						
						BusinessException businessExcp=new BusinessException( "rating.invocation.no.loc", null,"riskGroupDetailsMap.entrySet() is null");
						throw businessExcp;

					}
					//sectionwiseFactors=new ArrayList[numberofRisksinSection];
					//eRatorPolicyLisForDetails= new Policy[riskGroupDetailsMap.size()];
					// Take out the Section information 
					// riskGroupDetails is nothing but the specific SectionVO like PARVO or WCVO etc
					
					RiskGroupDetails riskDetails = entry.getValue();
					
					// END: Check for riskDetails
					if(!Utils.isEmpty(riskDetails)){
						// Commented due to premium check happens in Check is the premium calculated status
						/*//Start: Reset Premium Amount
						PremiumVO restetedPremium= new PremiumVO();
						
						if(!Utils.isEmpty(riskDetails.getPremium())){
							restetedPremium=riskDetails.getPremium();
							restetedPremium.setPremiumAmt(0);
							riskDetails.setPremium(restetedPremium);
						}
						//End: Reset Premium Amount
						 */					
						
					
						//Check is the premium calculated status
					sectionId = sectionVo.getSectionId().toString();
					boolean needToCalculatePremium= checkPremiumStatus(riskDetails);
					//Start : Check if premium need to be calculated or not
					if(needToCalculatePremium){
						RiskGroup locationInfo=entry.getKey();
						if (Utils.isEmpty(CLSPEfactorsList)){
						CLSPEfactorsList=factsIdentifier.getCLSPEKey(riskDetails,locationInfo,sectionVo.getSectionId().toString(),scheme,quotePreparedDt);
						}
						//Check: Yes premium need to be calculated or not
					// Get the factors for CLSPEKey
					

					
					
					//START : Latest
					
					
					String riskUniqId= "";
					 
					 if(!Utils.isEmpty(locationInfo.getRiskGroupId())){
						 riskUniqId=  locationInfo.getRiskGroupId();
					  }else{
						 BusinessException businessExcp=new BusinessException( "", null,"riskGroupId is not present or null" );
						throw businessExcp;
					 }
						 
					// Create Array of Factors of FactorBO for each risk type 
					
					 //sectionId = sectionVo.getSectionId().toString();
					if(sectionId.equals("1")){
						
						
							ParVO parVO=(ParVO)riskDetails;
							if(!isPrepacked){
								//PAR building is not covered in Prepack 
								PARList.add(factsIdentifier.getSectionSpeceficFactorsForPAR(parVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								//ratingTracker.put( "PAR_"+PARRtgItmSeqNo, riskUniqId );
								ratingTracker.put( "PAR_"+ratingItemNumber, riskUniqId );

								logger.debug("Rating : ratingTracker entry: PAR_"+PARRtgItmSeqNo,riskUniqId);

								isPARAvailable=true;	 
								ratingItemNumber++;
								PARRtgItmSeqNo++; 
							}
						 					 
						 
						 if(!Utils.isEmpty( parVO.getCovers())){
							 PropertyRisks propRisks=parVO.getCovers();
							 List <PropertyRiskDetails> propCoversList=propRisks.getPropertyCoversDetails();
							 boolean isBuilding=true;
							 boolean prepackWithAdditionalCover=false;
							 for(PropertyRiskDetails contentDetails: propCoversList){
								//Check for the first iteration. 1st coverlist will be always of building 
								 /*if(isBuilding){
									isBuilding=false;
									continue;
								}*/	 
								 if(!Utils.isEmpty( contentDetails) && (!Utils.isEmpty( contentDetails.getRiskType())))  {
									 if( (!Utils.isEmpty( contentDetails.getCover())))	 {
										 if(isPrepacked){
											 if(!Utils.isEmpty( contentDetails.getCoverCode())&& contentDetails.getRiskType()==999 ){
												 if("EDITABLE".equals(Utils.getSingleValueAppConfig("COMBINED_TARIFF_VISIBILITY_".concat(backupTariff.toString()))) ){
														 suminsured = contentDetails.getCover();
													 }
												  } 
											 if(!Utils.isEmpty( contentDetails.getCoverCode())&& contentDetails.getCoverCode()==20 ){
												 if((!Utils.isEmpty( contentDetails)) && (contentDetails.getCoverOpted().equals( 1 ))){
												 contentList.add(factsIdentifier.getSectionSpeceficFactorsForPARContent(parVO,contentDetails,renewalLoading,locationInfo,ratingItemNumber,commonFactor,backupTariff,suminsured));
												 //ratingTracker.put( com.Constant.CONST_PAR_CONT_END+contentRtgItmSeqNo, riskUniqId );
												 String riskIdwithContentType=riskUniqId+"PAR_CONT_TYPE:"+contentDetails.getRiskType().toString();
												 ratingTracker.put( com.Constant.CONST_PAR_CONT_END+ratingItemNumber, riskIdwithContentType );
												 logger.debug("Rating : ratingTracker entry: PAR_CONT_1"+contentRtgItmSeqNo,riskUniqId);
												 isContentAvailable=true;
												 contentRtgItmSeqNo++;	 
												 ratingItemNumber++;
												 prepackWithAdditionalCover=true;
												 }
												 } 
											 
										 }
										 else{
											 if(contentDetails.getCover()!=0 ){
												 // In case of prepack if the contentDetails.getCoverCode()==20 then add the factor to PARList
												 contentList.add(factsIdentifier.getSectionSpeceficFactorsForPARContent(parVO,contentDetails,renewalLoading,locationInfo,ratingItemNumber,commonFactor,backupTariff,suminsured));
												 //ratingTracker.put( com.Constant.CONST_PAR_CONT_END+contentRtgItmSeqNo, riskUniqId );
												 String riskIdwithContentType=riskUniqId+"PAR_CONT_TYPE:"+contentDetails.getRiskType().toString();
												 ratingTracker.put( com.Constant.CONST_PAR_CONT_END+ratingItemNumber, riskIdwithContentType );
												 logger.debug("Rating : ratingTracker entry: PAR_CONT_2"+contentRtgItmSeqNo,riskUniqId);
												 isContentAvailable=true;
												 contentRtgItmSeqNo++;	 
												 ratingItemNumber++;
												 }
											 
										 }
										 // if cover code ==20, then cover can be zero
										
									 }
								 }
								 
							 }
							 
							 
							 
							 //To create contentList if no additional covers are selected
							 if(isPrepacked && !prepackWithAdditionalCover){
								 ArrayList<FactorBO> contentListWithOnlyCommonFactor= new ArrayList<FactorBO>();
								 contentListWithOnlyCommonFactor.add(commonFactor);
								 	
								 FactorBO	factor = new FactorBO();
									factor.setFactorName( SvcConstants.RATING_EE5_BUS_TYPE_FACTOR);
									factor.setFactorValue("0" );
									contentListWithOnlyCommonFactor.add( factor );
									
									
									factor = new FactorBO();
									factor.setFactorName( SvcConstants.RATING_EE10_BUS_TYPE_FACTOR );
									factor.setFactorValue("0" );
									contentListWithOnlyCommonFactor.add( factor );
									
									 if(suminsured > 0.0){
										    factor = new FactorBO();
											factor.setFactorName( SvcConstants.RATING_CONTENT_SI_FACTOR );
											factor.setFactorValue( suminsured.toString() );
											contentListWithOnlyCommonFactor.add( factor );
									 }
								
									contentList.add(contentListWithOnlyCommonFactor);
								 ratingTracker.put( com.Constant.CONST_PAR_CONT_END+ratingItemNumber, riskUniqId );
								 logger.debug("Rating : ratingTracker entry: PAR_CONT_3"+contentRtgItmSeqNo,riskUniqId);
								 isContentAvailable=true;
								 contentRtgItmSeqNo++;	 
								 ratingItemNumber++;
								 isContentAvailable=true;
							 }
							 							 
							
						 }
						 //setAvgRatingFactor(parVO);
					 }else if(sectionId.equals("6")){
						 PublicLiabilityVO plVO=(PublicLiabilityVO)riskDetails;
						 PLList.add(factsIdentifier.getSectionSpeceficFactorsForPL(plVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
						 //ratingTracker.put( "PL_"+PLRrtgItmSeqNo, riskUniqId );
						 ratingTracker.put( "PL_"+ratingItemNumber, riskUniqId );
						 logger.debug("Rating : ratingTracker entry: PL_"+PLRrtgItmSeqNo,riskUniqId);
						 isPLAvailable=true;
						 PLRrtgItmSeqNo++;
						 ratingItemNumber++;
						 
					 }else if(sectionId.equals("7")){
						 WCVO wcVO=(WCVO)riskDetails;
						 if(!Utils.isEmpty(wcVO)){
							 List<EmpTypeDetailsVO> empTypeDetailsVOs = wcVO.getEmpTypeDetails();
							 WCRtgItmSeqNo = 0;
							 /* Get premium for each WC content */
							 for(EmpTypeDetailsVO empTypeDetailsVO : empTypeDetailsVOs){
								 /*
								  * For Dubai Null check present in getSectionSpeceficFactorsForWC()
								  *  method in GetFactorList file.
								  */
								 if(isContentDataPresent(empTypeDetailsVO)){
									 WCList.add(factsIdentifier.getSectionSpeceficFactorsForWC(wcVO,empTypeDetailsVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
									 String riskUniqIdItemNumber = riskUniqId + "EMP_TYPE:" +Integer.toString( WCRtgItmSeqNo );
									 ratingTracker.put( "WC_"+ratingItemNumber, riskUniqIdItemNumber );
									 logger.debug("Rating : ratingTracker entry: WC_"+WCRtgItmSeqNo,riskUniqId);
									 isWCAvailable=true;
									 WCRtgItmSeqNo++;
									 ratingItemNumber++;
								 }
							 }
						 }
						 
					 }else if(sectionId.equals("8")){
						// START : Is pre-pack check 
						 // Money is not considered for prepack
						 if(!isPrepacked){
							 int MoneyRtgItmSeqNo=0;
							 String moneyCategory="";
							 MoneyVO moneyVO=(MoneyVO)riskDetails;

							 if(!Utils.isEmpty(moneyVO.getContentsList())){
								 //List <SumInsuredVO> siList= moneyVO.getSumInsuredDets();

								 for( Contents content:moneyVO.getContentsList() ){
									 //for()	


									 /* if(MoneyRtgItmSeqNo==1){


									 }else{*/
									 /* Check if the content is not added then skip the execution process for that content */
										if(  PremiumHelper.isContentSumInsuredZero( content ) ){
											//content.setRiskId( null );
											continue;
										}
										//TODO: check if this logic is required, the continue can be put in the previous if block
										/*if( Utils.isEmpty( content.getRiskId() ) ){
											continue;
										}*/
									 // Start : Money 0 SI check 
									 if(content.getCover()!=new BigDecimal(0)){
										 // As no risk type present in money.
										 //content.setRiskType(MoneyRtgItmSeqNo);

										 moneyCategory=content.getRiskType()+"-"+ content.getRiskCat()+"-"+content.getRiskSubCat();
										 if(moneyCategory.equals("1-2-0")){
											 // Since Rating Engine doesn't need Cash in transit which comes as second
											 logger.debug("Rating : MONEY_1"+moneyCategory,riskUniqId);
										 }else{
											 MoneyList.add(factsIdentifier.getSectionSpeceficFactorsForMoney(content,renewalLoading,locationInfo,ratingItemNumber,MoneyRtgItmSeqNo,commonFactor));
											 //ratingTracker.put( "MONEY_"+MoneyRtgItmSeqNo, riskUniqId );
											 //String riskIdwithContentType=riskUniqId+"MONEY_CONT_TYPE:"+MoneyRtgItmSeqNo;
											 String riskIdwithContentType=riskUniqId+"MONEY_CONT_TYPE:"+moneyCategory;
											 //ratingTracker.put( com.Constant.CONST_PAR_CONT_END+ratingItemNumber, riskIdwithContentType );
											 ratingTracker.put( "MONEY_"+ratingItemNumber, riskIdwithContentType );
											 logger.debug("Rating : MONEY_2"+moneyCategory,riskUniqId);

										 }

									 }// End : Money 0 SI check
									 // }

									 MoneyRtgItmSeqNo++;
									 ratingItemNumber++;


								 }
							 }

							 //START: For cash at residence  
							 if(!Utils.isEmpty( (moneyVO.getCashResDetails()))){
								 Double deductible=0.0;
								 Double si=0.0;
								 //STRAT: For each cashResidence
								 for(CashResidenceVO cashResidence:moneyVO.getCashResDetails()){
									 if(!Utils.isEmpty( (cashResidence.getSumInsuredDets()))){
										 if(!Utils.isEmpty( (cashResidence.getSumInsuredDets().getDeductible()))){
											 deductible=deductible+cashResidence.getSumInsuredDets().getDeductible();	
										 }
										 if(!Utils.isEmpty( (cashResidence.getSumInsuredDets().getSumInsured())) && !cashResidence.isToBeDeleted() ){
											 si=si+cashResidence.getSumInsuredDets().getSumInsured();	
										 }
									 }
								 }
								 //END: For each cashResidence
								 //Create dummy sum insured VO
								 if(si!=0){
									 Contents contents=new Contents(); 
									 contents.setDeductibles(new BigDecimal(deductible) );
									 contents.setCover(new BigDecimal(si) );
									 contents.setRiskType(5);
									 contents.setRiskCat(0);
									 contents.setRiskSubCat(0);
									 MoneyList.add(factsIdentifier.getSectionSpeceficFactorsForMoney(contents,renewalLoading,locationInfo,ratingItemNumber,MoneyRtgItmSeqNo,commonFactor));
									 String riskIdwithContentType=riskUniqId+"MONEY_CONT_TYPE:"+"CASH_RESIDNCE";
									 //ratingTracker.put( com.Constant.CONST_PAR_CONT_END+ratingItemNumber, riskIdwithContentType );
									 ratingTracker.put( "MONEY_"+ratingItemNumber, riskIdwithContentType );
									 //ratingTracker.put( "MONEY_"+MoneyRtgItmSeqNo, riskUniqId );
									 // ratingTracker.put( "MONEY_"+ratingItemNumber, riskUniqId );

									 logger.debug("Rating : MONEY_3"+MoneyRtgItmSeqNo,riskUniqId);

									 MoneyRtgItmSeqNo++;
									 ratingItemNumber++;
								 }
							 }
							 isMoneyAvailable=true;

							 //END: For cash at residence
						 }// END : Is pre-pack check 
					 }
					
					//END : Latest
					
					//Adding changes for Phase 2a
					 else if(sectionId.equals(Utils.getSingleValueAppConfig( "BI_SECTION" )))
					 {
						// if(!isPrepacked)
						 {
							 BIVO biVO=(BIVO)riskDetails;
							 updateOccTradeForBI(locationInfo,policy);
							 BIList.add(factsIdentifier.getSectionSpeceficFactorsForBI(biVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor,getAvgRatingFactor(locationInfo,policy,biVO)));
							 ratingTracker.put( "BI_"+ratingItemNumber, riskUniqId );
							 logger.debug("Rating : ratingTracker entry: BI_"+BIRtgItmSeqNo,riskUniqId);
							 isBIAvailable=true;
							 BIRtgItmSeqNo++;
							 ratingItemNumber++;
						 }
						 
						 
					 }
					 else if(sectionId.equals(Utils.getSingleValueAppConfig( "MB_SECTION" )))
					 {
						 MBVO mbVO=(MBVO)riskDetails;
						 if(!Utils.isEmpty( mbVO ))
						 {
							 List<MachineDetailsVO> machineDetailsVOs = mbVO.getMachineryDetails();
							 MBRtgItmSeqNo = 0;
							 for(MachineDetailsVO machineDetailsVO : machineDetailsVOs)
							 {
								 if(!Utils.isEmpty( machineDetailsVO.getIsToBeDeleted()))
								 {
									 PremiumVO premiumVO = new PremiumVO();
									 premiumVO.setPremiumAmt( 0.0 );
									 premiumVO.setCurrency( "AED" );
									 machineDetailsVO.setPremium( premiumVO );
									 continue;
								 }
								 MBList.add(factsIdentifier.getSectionSpeceficFactorsForMB(machineDetailsVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								 String riskUniqIdItemNumber = riskUniqId + "MB_NUM:" +Integer.toString( MBRtgItmSeqNo );
								 ratingTracker.put( "MB_"+ratingItemNumber, riskUniqIdItemNumber );
								 logger.debug("Rating : ratingTracker entry: MB_"+MBRtgItmSeqNo,riskUniqId);
								 MBRtgItmSeqNo++;
								 ratingItemNumber++;
							 }
						 }
						 isMBAvailable=true;
					}else if(sectionId.equals(Utils.getSingleValueAppConfig( "EE_SECTION" ))){
						 EEVO eeVO=(EEVO)riskDetails;
						 if( !Utils.isEmpty( eeVO ))
						 {
							 EEQRtgItmSeqNo = 0;
							 List<EquipmentVO> equipmentVOs = eeVO.getEquipmentDtls();
							 for(EquipmentVO equipmentVO : equipmentVOs)
							 {
								 if(!Utils.isEmpty( equipmentVO ))
								 {
									 if(!Utils.isEmpty( equipmentVO.getIsToBeDeleted()))
									 {
										 PremiumVO premiumVO = new PremiumVO();
										 premiumVO.setPremiumAmt( 0.0 );
										 premiumVO.setCurrency( "AED" );
										 equipmentVO.setPremium( premiumVO );
										 continue;
									 }
									 setAvgRatingFactorForEE(locationInfo,policy,eeVO);
									 updateOccTradeForEE(locationInfo,policy);
									 EEQList.add(factsIdentifier.getSectionSpeceficFactorsForEEQ(equipmentVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor,eeVO));
									 String riskUniqIdItemNumber = riskUniqId + "EE_NUM:" +Integer.toString( EEQRtgItmSeqNo );
									 ratingTracker.put( "EEQ_"+ratingItemNumber, riskUniqIdItemNumber );
									 logger.debug("Rating : ratingTracker entry: EEQ_"+EEQRtgItmSeqNo,riskUniqId);
									 EEQRtgItmSeqNo++;
									 ratingItemNumber++;
									 if(isPrepacked)
									 {
										 break;
									 }
								 }
							 }
						 }
						isEEQAvailable=true;
						 
					 }else if(sectionId.equals(Utils.getSingleValueAppConfig( "TB_SECTION")))
					 {
						 Double limitSI = 0.0;
							Double deductible = 0.0;
						 if(!isPrepacked)
						 {
							 TravelBaggageVO tbVO=(TravelBaggageVO)riskDetails;
							 // remove the deleted rows if any and then calculate the premium 
							 TravelBaggageVO travelBaggageVO = new TravelBaggageVO();
							 /*
							  * making changes here for travel baggage premium calculation and also changing the method 
							  * signature  ticket id 69851
							  */
							 
							 for(TravellingEmployeeVO employeeVO : tbVO.getTravellingEmpDets() )
							 {
								 if(Utils.isEmpty( employeeVO.getIsToBeDeleted()) && !Utils.isEmpty(employeeVO.getSumInsuredDtl())  )
								 {
									 travelBaggageVO.getTravellingEmpDets().add( employeeVO );
									 if(!Utils.isEmpty( employeeVO )){
											SumInsuredVO sumInsuredVO = employeeVO.getSumInsuredDtl();
											if(!Utils.isEmpty( sumInsuredVO )){
												limitSI += sumInsuredVO.getSumInsured() ;
												deductible = sumInsuredVO.getDeductible();
											}
									 
								 } 
									 
								 }	} 
								if(limitSI > TRAVEL_BAGGAGE_FREE_COVER.doubleValue()) {
									 TravelList.add(factsIdentifier.getSectionSpeceficFactorsForTravel(tbVO,limitSI,deductible,renewalLoading,locationInfo,ratingItemNumber,commonFactor,TRAVEL_BAGGAGE_FREE_COVER.doubleValue()));
									 ratingTracker.put( "Travel_"+ratingItemNumber, riskUniqId );
									 logger.debug("Rating : ratingTracker entry: BI_"+TravelRtgItmSeqNo,riskUniqId);
									 isTravelAvailable=true;
									 TravelRtgItmSeqNo++;
									 ratingItemNumber++;
								 }
								 else
								 {
									 PremiumVO premiumVO = new PremiumVO();
									 premiumVO.setPremiumAmt( 0.0 );
									 premiumVO.setCurrency( "AED" );
									 travelBaggageVO.setPremium( premiumVO );
								 }
							 }
							  
						 
					 }
					 else if(sectionId.equals(Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION"))){
						 GroupPersonalAccidentVO groupPersonalAccidentVO=(GroupPersonalAccidentVO)riskDetails;
						 if(!Utils.isEmpty( groupPersonalAccidentVO )){
							 List<GPAUnnammedEmpVO> gpaUnnammedEmpVOs = groupPersonalAccidentVO.getGpaUnnammedEmpVO();
							 List<GPANammedEmpVO> gpaNammedEmpVOs = groupPersonalAccidentVO.getGpaNammedEmpVO();
							 
							 GPARtgItmSeqNo = 0;
							 for(GPAUnnammedEmpVO gpaUnnammedEmpVO : gpaUnnammedEmpVOs){
								 GPAList.add(factsIdentifier.getSectionSpeceficFactorsForGPAUnnamed(gpaUnnammedEmpVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								 String riskUniqIdItemNumber = riskUniqId + "GPA_UNNAMED:" +Integer.toString( GPARtgItmSeqNo );
								 ratingTracker.put( "GPA_"+ratingItemNumber, riskUniqIdItemNumber );
								 logger.debug("Rating : ratingTracker entry: GPA_"+GPARtgItmSeqNo,riskUniqId);
								 GPARtgItmSeqNo++;
								 ratingItemNumber++;
							 }
							 GPARtgItmSeqNo = 0;
							 for(GPANammedEmpVO gpaNammedEmpVO : gpaNammedEmpVOs){
								 GPAList.add(factsIdentifier.getSectionSpeceficFactorsForGPANamed(gpaNammedEmpVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								 String riskUniqIdItemNumber = riskUniqId + "GPA_NAMED:" +Integer.toString( GPARtgItmSeqNo );
								 ratingTracker.put( "GPA_"+ratingItemNumber, riskUniqIdItemNumber );
								 logger.debug("Rating : ratingTracker entry: GPA_"+GPARtgItmSeqNo,riskUniqId);
								 GPARtgItmSeqNo++;
								 ratingItemNumber++;
							 }
						 
						 isGPAAvailable=true;
						 }
					 }else if(sectionId.equals(Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION"))){
						 GoodsInTransitVO goodsInTransitVO=(GoodsInTransitVO)riskDetails;
						 if( !Utils.isEmpty( goodsInTransitVO )){
							 GITRtgItmSeqNo = 0;
						 List<CommodityDetailsVO> commodityDetailsVOs = goodsInTransitVO.getCommodityDtls();
							 for(CommodityDetailsVO commodityDetailsVO : commodityDetailsVOs){
								 if(!Utils.isEmpty( commodityDetailsVO )){
									 GITList.add(factsIdentifier.getSectionSpeceficFactorsForGIT(goodsInTransitVO,commodityDetailsVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
									 String riskUniqIdItemNumber = riskUniqId + "GIT_NUM:" +Integer.toString( GITRtgItmSeqNo );
									 ratingTracker.put( "GIT_"+ratingItemNumber, riskUniqIdItemNumber );
									 logger.debug("Rating : ratingTracker entry: GIT_"+GITRtgItmSeqNo,riskUniqId);
									 GITRtgItmSeqNo++;
									 ratingItemNumber++;
								 }
							 }
						 }
						 isGITAvailable=true;
					 }
					 else if(sectionId.equals(Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION"))){
						 DeteriorationOfStockVO deteriorationOfStockVO=(DeteriorationOfStockVO)riskDetails;
						 if( !Utils.isEmpty( deteriorationOfStockVO )){
							 DOSRtgItmSeqNo = 0;
						 List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsVOs = deteriorationOfStockVO.getDeteriorationOfStockDetails();
							 for(DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : deteriorationOfStockDetailsVOs){
								 if(!Utils.isEmpty( deteriorationOfStockDetailsVO )){
									 DOSList.add(factsIdentifier.getSectionSpeceficFactorsForDOS(deteriorationOfStockDetailsVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
									 String riskUniqIdItemNumber = riskUniqId + "DOS_NUM:" +Integer.toString( DOSRtgItmSeqNo );
									 ratingTracker.put( "DOS_"+ratingItemNumber, riskUniqIdItemNumber );
									 logger.debug("Rating : ratingTracker entry: DOS_"+DOSRtgItmSeqNo,riskUniqId);
									 DOSRtgItmSeqNo++;
									 ratingItemNumber++;
								 }
							 }
						 }
						 isDOSAvailable=true;
					 }
					 else if(sectionId.equals(Utils.getSingleValueAppConfig( "FIDELITY_SECTION"))){
						 FidelityVO fidelityVO=(FidelityVO)riskDetails;
						 if(!Utils.isEmpty( fidelityVO )){
							 List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeeVOs = fidelityVO.getUnnammedEmployeeDetails();
							 List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeeDetailsVOs = fidelityVO.getFidelityEmployeeDetails();
							 
							 FIDRtgItmSeqNo = 0;
							 for(FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO : fidelityUnnammedEmployeeVOs){
								 FIDList.add(factsIdentifier.getSectionSpeceficFactorsForFIDUnnamed(fidelityUnnammedEmployeeVO,fidelityVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								 String riskUniqIdItemNumber = riskUniqId + "FID_UNNAMED:" +Integer.toString( FIDRtgItmSeqNo );
								 ratingTracker.put( "FID_"+ratingItemNumber, riskUniqIdItemNumber );
								 logger.debug("Rating : ratingTracker entry: FID_"+FIDRtgItmSeqNo,riskUniqId);
								 FIDRtgItmSeqNo++;
								 ratingItemNumber++;
							 }
							 FIDRtgItmSeqNo = 0;
							 for(FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO : fidelityNammedEmployeeDetailsVOs){
								 FIDList.add(factsIdentifier.getSectionSpeceficFactorsForFIDNamed(fidelityNammedEmployeeDetailsVO,fidelityVO,renewalLoading,locationInfo,ratingItemNumber,commonFactor));
								 String riskUniqIdItemNumber = riskUniqId + "FID_NAMED:" +Integer.toString( FIDRtgItmSeqNo );
								 ratingTracker.put( "FID_"+ratingItemNumber, riskUniqIdItemNumber );
								 logger.debug("Rating : ratingTracker entry: FID_"+FIDRtgItmSeqNo,riskUniqId);
								 FIDRtgItmSeqNo++;
								 ratingItemNumber++;
							 }
						 }
						 isFIDAvailable=true;
					 }
				
					// Add Section factors from the policy details.  
					//factorsList.addAll(factsIdentifier.sectionSpeceficFactors(riskDetails, factorsList,renewalLoading,locationInfo,sectionVo.getSectionId().toString()));
					

					// Keep Factors list extracted for create eRatorPolicyObject for getRates service invocation
					//sectionwiseFactors.add(factorsList);
					//Keep a track of Section risk pair send for Rating Invocation
					/*RatingInvocationTracker ratingInvocationTracker=new RatingInvocationTracker();
					ratingInvocationTracker.setSectionNumberinPolicy(sectionNumber);
					ratingInvocationTracker.setRiskLocId(locationInfo);
					ratingInvocationTrackerList.add(ratingInvocationTracker);*/
					
					//End:Check: Yes premium need to be calculated or not
					}else{
						//Check: No premium need to be calculated or not
						logger.debug("Premium need not to be calculated");
					}
					//End : Check if premium need to be calculated or not
					// Increase riskNumber for tracking
					riskNumber++;	
					}// END: Check for riskDetails
				}
								
				//End Loop to get the risks associated with this section
				// Increase sectionNumber for tracking
				//sectionNumber++;
				}//END: Check sectionVo.getRiskGroupDetails() is empty or not
				else{
					logger.debug( "Rating : sectionVo.getRiskGroupDetails()is null" );
				}
			}
			//End  Loop to get all Sections: For each section in the Policy
			

			if(!isPARAvailable){
				 //Fill default values for PAR
				 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				 //item=sectionFactorsDefaulter.setDefaultPARItemFactors(item);
				 ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPARFactorList(ratingItemNumber,commonFactor);
				 PARList.add(defaultList);
				 
				 //ratingTracker.put( ratingItemNumber, ratingItemNumber.to );
				}
			
			if(!isContentAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPARContentFactorList(ratingItemNumber,commonFactor);
				 contentList.add(defaultList);
				
			}
			if(!isPLAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPLFactorList(ratingItemNumber,commonFactor);;
				 PLList.add(defaultList);
			}
			if(!isWCAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultWCFactorList(ratingItemNumber,commonFactor);
				 WCList.add(defaultList);
			}
			if(!isMoneyAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultMoneyFactorList(ratingItemNumber,commonFactor);
				 MoneyList.add(defaultList);
				
			}
			if(!isBIAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultBIFactorList(ratingItemNumber,commonFactor);
				 BIList.add(defaultList);
				
			}
			if(!isMBAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultMBFactorList(ratingItemNumber,commonFactor);
				 MBList.add(defaultList);
				
			}
			if(!isEEQAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultEEFactorList(ratingItemNumber,commonFactor);
				 EEQList.add(defaultList);
				
			}
			if(!isTravelAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultTBFactorList(ratingItemNumber,commonFactor);
				 TravelList.add(defaultList);
			}
			if(!isGITAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultGITFactorList( ratingItemNumber, commonFactor );
				 GITList.add(defaultList);
			}
			if(!isGPAAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultGPAFactorList(ratingItemNumber,commonFactor);
				 GPAList.add(defaultList);
			}
			if(!isDOSAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultDOSFactorList(ratingItemNumber,commonFactor);
				 DOSList.add(defaultList);
			}
			if(!isFIDAvailable){
				SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
				ratingItemNumber++;
				 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultFIDFactorList(ratingItemNumber,commonFactor);
				 FIDList.add(defaultList);
			}
			//START: LATEST
			Map <String,ArrayList<ArrayList<FactorBO>>> allFactors=new HashMap<String,ArrayList<ArrayList<FactorBO>>>();
			if(!Utils.isEmpty(PARList)){
				allFactors.put( "PAR", PARList );	
			}
			if(!Utils.isEmpty(contentList)){
				allFactors.put( "PARCONTENT", contentList );	
			}
			if(!Utils.isEmpty(PLList)){
				allFactors.put( "PL", PLList );	
			}
			if(!Utils.isEmpty(WCList)){
				allFactors.put( "WC", WCList );	
			}
			if(!Utils.isEmpty(MoneyList)){
				allFactors.put( "MONEY", MoneyList );	
			}
			if(!Utils.isEmpty(BIList)){
				allFactors.put( "BI", BIList );	
			}
			if(!Utils.isEmpty(EEQList)){
				allFactors.put( "EEQ", EEQList );	
			}
			if(!Utils.isEmpty(TravelList)){
				allFactors.put( "TB", TravelList );	
			}
			if(!Utils.isEmpty(MBList)){
				allFactors.put( "MB", MBList );	
			}
			if(!Utils.isEmpty(GITList)){
				allFactors.put( "GIT", GITList );	
			}
			if(!Utils.isEmpty(GPAList)){
				allFactors.put( "GPA", GPAList );	
			}
			if(!Utils.isEmpty(DOSList)){
				allFactors.put( "DOS", DOSList );	
			}
			if(!Utils.isEmpty(FIDList)){
				allFactors.put( "FID", FIDList );	
			}
				
			// Create  eRatorpolicy from the  FactorsList
			PremiumCaluclatorHelper premiumCaluclatorHelper=new PremiumCaluclatorHelper();
			Policy eRatorPolicy=premiumCaluclatorHelper.setPolicyForDetails(CLSPEfactorsList);
			// Create  eRatorpolicyArray from the  sectionFactorsList
			eRatorPolicyListForDetails.add(eRatorPolicy);
			//END: LATEST
			
			
			
			
			
			long endTime = System.currentTimeMillis();
			logger.info(" Rating Engine integration Timer: Policy creation time for getDeatils service (In MilliSec):: " + (endTime - startTime));
			
			
			try {
				//Create Array of policyObjects				
				eRatorPolicyArrayForDetails=new Policy[eRatorPolicyListForDetails.size()];
				eRatorPolicyListForDetails.toArray(eRatorPolicyArrayForDetails);
				//eRatorPolicyArrayForDetails=(Policy[])eRatorPolicyListForDetails.toArray();
				//Invoke rating  for Get Details
				eRatorPolicyArrayWithDetails=ratingInvoker.getDetailsForPolicy(eRatorPolicyArrayForDetails);


				 startTime = System.currentTimeMillis();
				
				
				//Invoke rating  for Get Premium after filling factors
				int policyNumberInList=0;
				boolean errorResponse=false;
				String ratingErrorMessage="";
				ErrorType errorFromRatingEngine= new ErrorType();
				// Initiate policy array
				eRatorPolicyArrayForPremium= new Policy[eRatorPolicyArrayWithDetails.length];
				//for(Policy eRatorPolicyForPremium:eRatorPolicyArrayWithDetails){
				// Assumption is that Rating Engine response contains only one policy with all sections.
				Policy eRatorPolicyForPremium=eRatorPolicyArrayWithDetails[0];
					if(Utils.isEmpty(eRatorPolicyForPremium.getError())){ 
					// TODO: Need to remove : Testing Rating Engine Int
					//eRatorPolicy=changePolicyLevelFactorstoItemLevel(eRatorPolicy);

					//ArrayList <FactorBO> factorsListForThisSection=sectionwiseFactors.get(policyNumberInList);
					// Create  eRatorpolicy from the  FactorsList
					//eRatorPolicyForPremium=setPolicyForPremium(factorsListForThisSection,eRatorPolicyForPremium);



					//START: LATEST
					SectionFactorsHelper sectFactHelper= new SectionFactorsHelper();
					eRatorPolicyForPremium=sectFactHelper.setPolicyForPremium(allFactors,eRatorPolicyForPremium,isPrepacked);


					//END: LATEST


					// Set the Service name for this policy
					eRatorPolicyForPremium.setService( "getRates" );
					//Create Array of policyObjects	
					eRatorPolicyArrayForPremium[policyNumberInList]=eRatorPolicyForPremium;
					policyNumberInList++;
					}else{
						ratingErrorMessage=eRatorPolicyForPremium.getError().getErrorUserMessage();
						errorFromRatingEngine=eRatorPolicyForPremium.getError();
						errorResponse=true;
					}
				//}
					
				endTime = System.currentTimeMillis();
				logger.info(" Rating Engine integration Timer: Policy creation time for getRates service (In MilliSec):: " + (endTime - startTime));
					

				if(!Utils.isEmpty(errorResponse)){ 
				eRatorPolicyArrayWithPremium=ratingInvoker.getPremiumForPolicy(eRatorPolicyArrayForPremium);
				}else{
					
					logger.error( "Rating Engine Get Details Error Response:RatingErrorMessage:"+ratingErrorMessage );
					logger.error( "Rating Engine Get Details Error Response:ErrorOccuranceDate:"+errorFromRatingEngine.getErrorOccuranceDate() );
					logger.error( "Rating Engine Get Details Error Response:ErrorClass:"+errorFromRatingEngine.getClass() );
					logger.error( "Rating Engine Get Details Error Response:ErrorID:"+errorFromRatingEngine.getErrorId());
					logger.error( "Rating Engine Get Details Error Response:ErrorElementData:"+errorFromRatingEngine.getErrorElementData());
					BusinessException businessExcp=new BusinessException( "rating.getdetails.error", null,ratingErrorMessage );
					throw businessExcp;
				}

			} catch (ErateException e) {
				
				logger.error( "Rating Engine ErateException:"+e.getMessage() );
				logger.error( "Rating Engine ErateException stack trace:"+e.getStackTraceString() );
				BusinessException businessExcp=new BusinessException( "rating.invocation.error", null,e.getMessage());
				throw businessExcp;

			}
			catch (Exception e) {
				
				logger.error( "General Exception while invoking Rating Engine :"+e.getCause() );
				logger.error( "General Exception while invoking Rating Engine stack trace :");
				BusinessException businessExcp=new BusinessException( "rating.invocation.generalexception", e.getCause(),e.getCause().toString());
				throw businessExcp;

			}

			
			//policy= createPASPolicyfromeRatorPolicy(eRatorPolicyArrayWithPremium,policy,ratingInvocationTrackerList);
			PolicyPremiumMapper policyPremiumMapper=new PolicyPremiumMapper();
			policy= policyPremiumMapper.createPASPolicyfromeRatorPolicy(eRatorPolicyArrayWithPremium,policy,ratingTracker,isPrepacked);
			//}//END:
		}
		scheme.setTariffCode(backupTariff);
	return policy;
	}

	


	private Boolean isContentDataPresent( EmpTypeDetailsVO empTypeDetailsVO ){
		
		Boolean flag = true;
		
		String fieldsToValidate = Utils.getSingleValueAppConfig("WC_NO_OF_EMP");
		List<String> fieldsToValidateList = CopyUtils.asList(fieldsToValidate.split(","));
		// If Oman deductible check is not needed
		if(!"NO".equalsIgnoreCase((Utils.getSingleValueAppConfig("DEDUCTIBLEREQUIRED")))){
			if(Utils.isEmpty(empTypeDetailsVO.getDeductibles())){
				flag = false;
			}
		}
		if(fieldsToValidateList.contains("wageroll") && ( Utils.isEmpty(empTypeDetailsVO.getWageroll()) || (!Utils.isEmpty(empTypeDetailsVO.getWageroll()) && empTypeDetailsVO.getWageroll().equals( 0.0 ))) ){
			flag = false;
		}
		
		if(fieldsToValidateList.contains("limit") && Utils.isEmpty(empTypeDetailsVO.getLimit())){
			flag = false;
		}
		
		if(fieldsToValidateList.contains("empType") &&  Utils.isEmpty(empTypeDetailsVO.getEmpType()) || ( !Utils.isEmpty(empTypeDetailsVO.getEmpType()) && empTypeDetailsVO.getEmpType().equals( CommonConstants.DEFAULT_LOW_INTEGER ))){	
			flag = false;
		}
		
		if(fieldsToValidateList.contains("noOfEmp") &&  Utils.isEmpty(empTypeDetailsVO.getNoOfEmp()) && Integer.valueOf(empTypeDetailsVO.getNoOfEmp()).equals( CommonConstants.DEFAULT_LOW_INTEGER )){	
			flag = false;
		}
		
		return flag;
		
	}




	/*private Policy changePolicyLevelFactorstoItemLevel( Policy eRatorPolicy ){
		
		Item item=new Item();
		item.setItemNumber( 0 );
		item.setItemCode("0");
		Coverage[] coverages=eRatorPolicy.getPolicyCoverages();
		item.setCoverages( coverages );
		item.setItemCode( coverages[0].getCoverageCode().substring( 13 ));
		//item.setItemCode( "20001" );
		coverages[0].getCoverageCode();
		coverages=null;
		eRatorPolicy.setPolicyCoverages( null );
		//item.setItemCode( itemCode )
		Item[] items= new Item[1];
		items[0]=item;
		
		eRatorPolicy.setItems( items );
		
		return eRatorPolicy;
	}
*/


	/**
	 * @param eRatorPolicyArrayWithPremium
	 * @param policy
	 * @param ratingInvocationTrackerList 
	 * @return
	 *//*
	private PolicyVO createPASPolicyfromeRatorPolicy(
			Policy[] eRatorPolicyArrayWithPremium, PolicyVO policy, ArrayList<RatingInvocationTracker> ratingInvocationTrackerList) {

		//For each eRatorPolicy from Rating Service 
		int policyVOTraceker=0;
		double totalPremium=0;
		for(Policy eRatorPolicyWithPremium:eRatorPolicyArrayWithPremium){
			//Premium from this policy should be of risk in ratingInvocationTrackerList() 
			// Identified the risk by using  ratingInvocationTrackerList
			RatingInvocationTracker ratingInvocationTracker=ratingInvocationTrackerList.get(policyVOTraceker);
			int sectionNumber=ratingInvocationTracker.getSectionNumberinPolicy();
			RiskGroup riskLocId=ratingInvocationTracker.getRiskLocId();
			List <SectionVO> sectionVOList= policy.getRiskDetails();
			SectionVO sectionVO=sectionVOList.get(sectionNumber);
			HashMap <RiskGroup,RiskGroupDetails> riskGroupDetailsMap=(HashMap <RiskGroup,RiskGroupDetails>)sectionVO.getRiskGroupDetails();
			RiskGroupDetails risk=riskGroupDetailsMap.get(riskLocId);
			
			//Update the identified risk with the premium associated with the policy from Rating Engine for the risk
			risk=updatePremiumforRisk(risk,eRatorPolicyWithPremium);
			
			//Update the riskGroupDetailsMap with the risk updated with premium
			riskGroupDetailsMap.put(riskLocId, risk);
			
			//Set riskGroupDetailsMap back to the Section.
			sectionVO.setRiskGroupDetails(riskGroupDetailsMap);
			
			//Update Section back to the SectionVOList
			sectionVOList.add(sectionNumber, sectionVO);
			
			//Update PolicyVO with the sectionVOList
			policy.setRiskDetails(sectionVOList);
			totalPremium=totalPremium+risk.getPremium().getPremiumAmt();
		}
		PremiumVO policyLevelPremium=new PremiumVO();
		policyLevelPremium.setPremiumAmt( totalPremium );
		policy.setPremiumVO( policyLevelPremium );
		return policy;
	}*/



	/**
	 * @param risk
	 * @param eRatorPolicyWithPremium
	 * @return
	 *//*
	private RiskGroupDetails updatePremiumforRisk(RiskGroupDetails risk,
			com.cts.writeRate.Policy eRatorPolicyWithPremium) {
		// TODO Auto-generated method stub
		// Update the risk with the premium from Policy
		// Take out the Premium list as in
		PremiumCaluclatorHelper premiumCaluclatorHelper=new PremiumCaluclatorHelper();
		PremiumUpdater premiumUpdator=new PremiumUpdater();
		List premiumListFromRating=premiumCaluclatorHelper.getPremiumList(eRatorPolicyWithPremium);
		risk=premiumUpdator.updatePremium(risk,premiumListFromRating);
		
		PremiumVO premium=new PremiumVO();
		if(!(Utils.isEmpty(eRatorPolicyWithPremium.getItems()[0])) &&  !(Utils.isEmpty(eRatorPolicyWithPremium.getItems()[0].getPremium()))){
			premium.setPremiumAmt( eRatorPolicyWithPremium.getItems()[0].getPremium().getBasePremium() );
		}
		
		//double dPremiumFromErator=premiumFromErator;
		risk.setPremium( premium );
		return risk;
	}
*/


	/**
	 * @param riskDetails
	 * @return
	 */
	private boolean checkPremiumStatus(RiskGroupDetails riskDetails) {
		boolean needPremiumCalculation=false;
		if(!Utils.isEmpty(riskDetails)){
			//Start: Reset Premium Amount
			
			
			if(!Utils.isEmpty(riskDetails.getPremium())){
				if( new Double(0.0) ==riskDetails.getPremium().getPremiumAmt()){
					needPremiumCalculation=true;	
				}
				
			}else{
				needPremiumCalculation=true;
			}
		}
		// TODO Auto-generated method stub
		// Implement the logic for taking the premium calculated status from premiumVo
		// If user edits the section values and saved the values instead of pressing next, then the status is set as 6.
		// If status is 6 then method should return true as it requires a premium calculation.

		return needPremiumCalculation;
	}



	

	/**
	 * @param factorsListForThisSection
	 * @param eRatorPolicy
	 * @return
	 *//*
	private Policy setPolicyForPremium(ArrayList<FactorBO> factorsListForThisSection,
			Policy eRatorPolicy) {
		// 
		// TODO Auto-generated method stub
		// Implement as in
		// com.rsaame.kaizen.quote.businessfunction.GetPremiumForMotor.setPolicyForPremium()

		// Logic to set policy level coverage factor values
		
		
		
		
		if (eRatorPolicy.getPolicyFactors() != null) {
			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"Setting Policy Level Run time factor");
			Factor[] policyFactors;
			policyFactors = eRatorPolicy.getPolicyFactors();
			for (int count = 0; count < policyFactors.length; count++) {
				ListIterator listItr = factorsListForThisSection.listIterator();
				while (listItr.hasNext()) {
					FactorBO keyValue = (FactorBO) listItr.next();
					if (policyFactors[count].getFactorName().equalsIgnoreCase(
							keyValue.getFactorName())) {
						policyFactors[count].setFactorValue(keyValue
								.getFactorValue());
						logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
								"Policy Level Run time factor:"
										+ keyValue.getFactorValue());
					}
				}
			}
			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"After Setting Policy Level Run time factor");
			eRatorPolicy.setPolicyFactors(policyFactors);
		}

		//Logic to set policy level coverage factor values
		if (eRatorPolicy.getPolicyCoverages() != null) {
			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"Setting Policy Level Coverage Run time factor:");
			Factor[] coverFactors;
			Coverage[] coverage;
			coverage = eRatorPolicy.getPolicyCoverages();
			for (int countCoverage = 0; countCoverage < coverage.length; countCoverage++) {
				if (coverage[countCoverage] != null) {
					coverFactors = coverage[countCoverage].getCoverageFactors();
					for (int factCount = 0; factCount < coverFactors.length; factCount++) {
						ListIterator listItr = factorsListForThisSection.listIterator();
						while (listItr.hasNext()) {
							FactorBO keyValue = (FactorBO) listItr.next();
							if (coverFactors[factCount].getFactorName().equalsIgnoreCase(
									keyValue.getFactorName())) {
								coverFactors[factCount].setFactorValue(keyValue
										.getFactorValue());
								logger.debug(
										AMEConstants.SET_POLICY_FOR_PREMIUM,
										"Policy Coverage Level Run time factor:"
												+ keyValue.getFactorValue());
								break;
							}
						}
						coverage[countCoverage].setCoverageFactors(factCount,
								coverFactors[factCount]);
					}
				}
			}
			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"After Setting Policy Level Coverage Run time factor");
			eRatorPolicy.setPolicyCoverages(coverage);
		}

		//		Logic to set Item level factor values
		if (eRatorPolicy.getItems() != null) {

			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"Setting Item Level Run time factor");

			Item[] item;

			Coverage[] coverage;

			Factor[] factors;

			item = eRatorPolicy.getItems();

			for (int itemCount = 0; itemCount < item.length; itemCount++) {
				//STRAT TODO : NICY : Content Level New logic to repeat for each Content
				// Logic: if item code = 025000100000020002
					
					//then fill check the PAR contents 
					//for each content fill the item with that particular content type 
					// and SI with normal other factors
					// For this at the time of building Factors will create 
					// Factors for each content like FURN_SI, RENT_SI...
					// Check the they are available in Factor List
					//item[itemCount].getItemCode()

				//END TODO : NICY : Content Level New logic to repeat for each Content
 
				
				//Reading Factor for Each item and setting the value for the
				// same
				if (item[itemCount].getItemFactors() != null) {
					Factor[] itemFactor = item[itemCount].getItemFactors();
					for (int countItemFactor = 0; countItemFactor < itemFactor.length; countItemFactor++) {
						ListIterator listItr = factorsListForThisSection.listIterator();
						while (listItr.hasNext()) {
							FactorBO keyValue = (FactorBO) listItr.next();
							if (itemFactor[countItemFactor].getFactorName()
									.equalsIgnoreCase(keyValue.getFactorName())) {
								itemFactor[countItemFactor]
										.setFactorValue(keyValue
												.getFactorValue());
								
								
								
							}
						}
						item[itemCount].setItemFactors(countItemFactor,
								itemFactor[countItemFactor]);
					}

					logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
							"After Setting Item Level Run time factor");
					eRatorPolicy.setItems(item);
				}

				if (item[itemCount].getCoverages() != null) {
					logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
							"Setting Item Level Coverage Run time factor");
					coverage = item[itemCount].getCoverages();

					for (int covergeCount = 0; covergeCount < coverage.length; covergeCount++) {
						if (coverage[covergeCount] != null) {
							factors = coverage[covergeCount]
									.getCoverageFactors();
							for (int countCoverageFactor = 0; countCoverageFactor < factors.length; countCoverageFactor++) {
								ListIterator listItr = factorsListForThisSection
										.listIterator();
								while (listItr.hasNext()) {
									FactorBO keyValue = (FactorBO) listItr
											.next();
									if (factors[countCoverageFactor]
											.getFactorName().equalsIgnoreCase(
													keyValue.getFactorName())) {
										factors[countCoverageFactor]
												.setFactorValue(keyValue
														.getFactorValue());
										logger
												.debug(
														AMEConstants.SET_POLICY_FOR_PREMIUM,
														"Setting Item Level Coverage Run time factor Name:Value:"
																+ keyValue
																		.getFactorName()
																+ ":"
																+ keyValue
																		.getFactorValue());
										break;
									}
								}
								coverage[covergeCount].setCoverageFactors(
										countCoverageFactor,
										factors[countCoverageFactor]);
							}
						}
					}

					item[itemCount].setCoverages(coverage);

					logger
							.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
									"After Setting Item Level Coverage Run time factor");

					eRatorPolicy.setItems(item);
				}
			}
		}
		
		
		
		
		
		return eRatorPolicy;
	}*/

	private void updateOccTradeForBI(RiskGroup locationInfo, PolicyVO policy){
		SectionVO parSection = PolicyUtils.getSectionVO( policy, 1 );
		LocationVO locationVO = (LocationVO)locationInfo;
		
		Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = parSection.getRiskGroupDetails();
		Set<? extends RiskGroup> rgs = riskGroupDetails.keySet();
		for( RiskGroup riskGroup : rgs ){
			LocationVO location = (LocationVO)riskGroup;
			if(location.getRiskGroupId().equals(locationVO.getRiskGroupId()))
			{
				locationVO.setOccTradeGroup(location.getOccTradeGroup());
			}
		}
	}

	private void updateOccTradeForEE(RiskGroup locationInfo, PolicyVO policy){
		SectionVO parSection = PolicyUtils.getSectionVO( policy, 1 );
		LocationVO locationVO = (LocationVO)locationInfo;
		
		Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = parSection.getRiskGroupDetails();
		Set<? extends RiskGroup> rgs = riskGroupDetails.keySet();
		for( RiskGroup riskGroup : rgs ){
			LocationVO location = (LocationVO)riskGroup;
			if(location.getRiskGroupId().equals(locationVO.getRiskGroupId()))
			{
				locationVO.setOccTradeGroup(location.getOccTradeGroup());
			}
		}
	}
	
	public static RiskGroupDetails getRiskGroupDetails( RiskGroup rg, SectionVO section ){
		if(!Utils.isEmpty(section)){
		Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
		return riskGroupDetails.get( rg );
		}else{
			return null;
		}
	}
	
	private void setAvgRatingFactorForEE(RiskGroup locationInfo, PolicyVO policy,EEVO eeVO)
	{
		SectionVO parSection = PolicyUtils.getSectionVO( policy, 1 );
		//LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) getRiskGroupDetails( locationInfo, parSection );
		if(!(Utils.isEmpty( parDetails.getUwDetails() )))
		{
			PARUWDetailsVO  uwDetails =(PARUWDetailsVO) parDetails.getUwDetails();
			if(!(Utils.isEmpty( uwDetails.getHazardLevel())))
			{
				eeVO.setHazardLevel( uwDetails.getHazardLevel());	
			}
			else
			{		
				eeVO.setHazardLevel(2);	
			}
		}
		
	}
	
	
	private Double getAvgRatingFactor(RiskGroup location,PolicyVO policy,BIVO biVO)
	{
		SectionVO parSection = PolicyUtils.getSectionVO( policy, 1 );
		//LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) getRiskGroupDetails( location, parSection );
		//ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );
		if( "N".equals( Utils.getSingleValueAppConfig( "BI_PAR_PRM_ENABLED" ) ) )
		{
			parAvgRatingFactor = 1.0;
		}
		else if( !Utils.isEmpty( parDetails ) && !Utils.isEmpty( parDetails.getPremium() ) && !Utils.isEmpty( parDetails.getPremium().getPremiumAmtActual() ) ){
			parAvgRatingFactor = parDetails.getPremium().getPremiumAmtActual() / getParSI( parDetails );
		}
		else{
			parAvgRatingFactor = 0.075;
		}
		if(!(Utils.isEmpty(parDetails)) && !(Utils.isEmpty( parDetails.getUwDetails() )))
		{
			PARUWDetailsVO  uwDetails =(PARUWDetailsVO) parDetails.getUwDetails();
			if(!(Utils.isEmpty( uwDetails.getHazardLevel())))
			{
				biVO.setHazardLevel( uwDetails.getHazardLevel());	
			}
			else
				
				biVO.setHazardLevel(2);
		}
		return parAvgRatingFactor;
}
	private double getParSI( ParVO locationDetail ){

		double sumInsured = 0.0;
		ArrayList<PropertyRiskDetails> propertyRiskDetails;
		if( !Utils.isEmpty( locationDetail.getBldCover() ) ) sumInsured = locationDetail.getBldCover();

		PropertyRisks propertyRisks = (PropertyRisks) locationDetail.getCovers();

		if( !Utils.isEmpty( propertyRisks ) ){
			propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
			if( !Utils.isEmpty( propertyRiskDetails ) )
			{
				Iterator<PropertyRiskDetails> propertyRiskDetailsItr = propertyRiskDetails.iterator();
				while( propertyRiskDetailsItr.hasNext() )
				{
					/** Resetting the values to null */
					double cover = 0;

					PropertyRiskDetails propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
					if( !Utils.isEmpty( propertyRiskDetail.getCover() ) ) cover = propertyRiskDetail.getCover();
					sumInsured = sumInsured + cover;
				}
			}
		}
		return sumInsured;
	}
	
	private int mapTariff(int tariff){
		String RULE_TARIFF_MAP =  Utils.getSingleValueAppConfig("RATING_TARIFF_MAP") ;
		if(RULE_TARIFF_MAP.contains("$"+tariff)){
			StringTokenizer tok = new StringTokenizer(RULE_TARIFF_MAP,";");
			while(tok.hasMoreTokens()){
				String str = tok.nextToken();
				if(str.contains("$"+tariff)){
					tariff = Integer.valueOf(str.substring(1, str.indexOf("-")));
					break;
				}
			}
		}
		return tariff;
	}
	
	/*Added to fetch the prepare Date to pass to Rating service - Ticket Id: 140443 */
	private Date GetQuotePreparedDate(PolicyVO policy) {		
		
		Long quoteNum = policy.getQuoteNo();
		Integer policyType = policy.getPolicyTypeCode();
		Date polPrepDt = DAOUtils.getPreparedDateForRating(quoteNum,policyType);
		return polPrepDt;
}
}

