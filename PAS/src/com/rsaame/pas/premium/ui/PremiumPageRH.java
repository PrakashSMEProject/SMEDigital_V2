package com.rsaame.pas.premium.ui;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PremiumSummary;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * This request handler handles the preparation of data and redirection for:
 * <ol>
 * <li>Consolidated referrals message display</li>
 * <li>Premium Page</li><br/><br/>
 * 
 * If there are referral messages saved in the database against the quote, then the user will be taken
 * to the consolidated referrals page. If there are no referral messages, the Premium Page will be 
 * shown.
 */
public class PremiumPageRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response resp = new Response();
		ReferralVO referalVO = null;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
		/** SK : Changes */
		/**
		 * Commented out the logic of getting consolidated referral message when invoked from
		 * Last section. This block of code has been moved to ReferralRH
		 */

		if(!Utils.isEmpty( policyContext ) && !policyVO.getIsQuote()  && policyContext.isPolicyCancelled()){
			policyContext.setAppFlow( Flow.AMEND_POL );
			policyVO.setAppFlow( Flow.AMEND_POL );
			request.setAttribute(  com.Constant.CONST_AMENDACTION , "GET_REFUND_PREMIUM" );
		}
		if( !Utils.isEmpty( policyContext ) && !Utils.isEmpty( policyContext.getPolicyDetails() ) ){
		
			/*Start of ticket 137704 */
			int userId = 0;
			String role = null;

			UserProfile userProfileVO = (UserProfile) policyContext.getPolicyDetails().getLoggedInUser();
			userId = userProfileVO.getRsaUser().getUserId();
			role=DAOUtils.getUserRole(policyVO);
			
			/*End of ticket 137704 */
			
			
			
			referalVO = new ReferralVO();
			referalVO.setPolLinkingId( policyContext.getPolicyDetails().getPolLinkingId() );
			referalVO.setTprUserId(userId);
			referalVO.setTprUserRole(role);
		}

		/* Check if there are referral messages saved for this quote:
		 * (a) If yes, then the response is JSON with referral messages as data.
		 * (b) Else, the response is the Premium Page HTML. */

		ReferralListVO referralListVO = null;

		if( !isReadOnlyMode( policyContext.getPolicyDetails() ) ){
			if ( !Utils.isEmpty( policyContext.getNextScreen() ) && !Utils.isEmpty( policyContext.getNextScreen().getSectionId() ) && !policyContext.getNextScreen().getSectionId().equals( SvcConstants.SECTION_ID_PREMIUM )) {
				
				HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
				Iterator<SectionVO> sectionListItr = null;
		 		List<SectionVO> sectionVOList = null;
		 		SectionVO sectionVO = null;
	 			sectionVOList = policyVO.getRiskDetails();
	 			DataHolderVO<Object[]> holderVO = new DataHolderVO<Object[]>();
	 			String locationId = null;
	 			
	 			if( !Utils.isEmpty( sectionVOList ) ){
	 				sectionListItr = sectionVOList.iterator();

	 				while( sectionListItr.hasNext() ){
	 					sectionVO = (SectionVO) sectionListItr.next();
	 					int sectionId = sectionVO.getSectionId().intValue();
 						riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
 						if( !Utils.isEmpty( riskGroupDetails ) ){
	 						LocationVO locationDetails = null;
	 						for( Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
	 							locationDetails = (LocationVO) riskGroupDetailsEntry.getKey();
	 							if( !Utils.isEmpty( locationDetails ) && !Utils.isEmpty( PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO ) )){
	 								
	 								locationId = locationDetails.getRiskGroupId();
	 								Object[] input = { policyVO , sectionId , locationId };
	 								
	 								/* Setting data in holderVO so that sectionId and locationId are available in the taskExecutor call. */
	 								holderVO.setData( input );
	 								
	 								locationDetails.setToSave( true );
	 								SectionRHUtils.executeReferralTask( resp,"SECTION_"+sectionId+"_SAVE", policyVO, "SECTION_"+sectionId+"_SAVE" );
	 								policyVO = (PolicyVO) TaskExecutor.executeTasks("HANDLE_RULES_PREMIUM_PAGE", holderVO );
	 								locationDetails.setToSave( false );
	 							}
	 						}
 						}
	 				}
	 			}
				/*SectionRHUtils.executeReferralTask( resp,"SECTION_1_SAVE", policyVO, "SECTION_1_SAVE" );
				policyVO = (PolicyVO) TaskExecutor.executeTasks("HANDLE_RULES_PREMIUM_PAGE", policyVO);*/
			}
			referralListVO = (ReferralListVO) TaskExecutor.executeTasks( "PREMIUM_PAGE", referalVO );
			

			/*Start of ticket 137704 */
			String secId=null;
			if(!Utils.isEmpty(referralListVO)){
			    if(request.getAttribute("termpCurrentSecId")!=null){
			         secId=request.getAttribute("termpCurrentSecId").toString();
			         policyContext.setAsCurrentSection(Integer.parseInt(secId));
			    }
			}
			/*End of ticket 137704 */

		}
		
		
		
		for(Integer section: policyContext.getAllSelectedSections()){
			System.out.println("------------------------section--------------"+section);
			boolean sectionAbsent = true;
			boolean atleastOneLocPresent = false;
			if(!Utils.isEmpty(policyVO.getSavedSections()) && policyVO.getSavedSections().contains(section)){
				continue;
			}
			else if(!Utils.isEmpty(policyContext.getSectionDetails( section )) && !Utils.isEmpty( policyContext.getSectionDetails( section ).getRiskGroupDetails())){
				sectionAbsent = false;
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : policyContext.getSectionDetails( section ).getRiskGroupDetails().entrySet() ){
					/* Check if any of the selected section is empty(data not added) */
					if(!Utils.isEmpty(locationEntry.getValue())){
						atleastOneLocPresent = true;
						break;
					}
				}
			}
			if(sectionAbsent || !atleastOneLocPresent){
				SectionList output = (SectionList) TaskExecutor.executeTasks( "FETCH_SAVED_SECTIONS", policyVO );
				if( !Utils.isEmpty( output ) && !Utils.isEmpty( output.getSelectedSec() ) ){
					policyVO.setSavedSections(output.getSelectedSec());
				}
				if(!output.getSelectedSec().contains(section)){
					request.setAttribute( "allSectionsLoaded", false ); 
					break;
				}
				
			}
		}
		
		if( AppUtils.getBasicFlowFromResolveReferral( policyContext.getPolicyDetails() ) == Flow.AMEND_POL ){

			java.util.List<EndorsmentVO> endorsements = null;
			String amendAction = (String) ( !Utils.isEmpty( request.getParameter( com.Constant.CONST_AMENDACTION ) ) ? request.getParameter( com.Constant.CONST_AMENDACTION ) : request.getAttribute( com.Constant.CONST_AMENDACTION ) );
			if( !Utils.isEmpty( amendAction ) ){
				endorsements = ( (PolicyVO) TaskExecutor.executeTasks( amendAction, policyContext.getPolicyDetails() ) ).getEndorsements();
				request.setAttribute( "amendFlowType", "CANCEL_POLICY" );

			}
			else if( Utils.isEmpty( amendAction ) ){

				if( !Utils.isEmpty( policyContext.getPolicyDetails().getEndorsements() ) && !Utils.isEmpty( policyContext.getPolicyDetails().getEndorsements().get( 0 ) )
						&& policyContext.getPolicyDetails().getEndorsements().get( 0 ).isPolicyToBeCancelled() == true ){

					//						endorsements = ( (PolicyVO) TaskExecutor.executeTasks( "GET_REFUND_PREMIUM", policyContext.getPolicyDetails() ) ).getEndorsements();
					endorsements = policyContext.getPolicyDetails().getEndorsements();
					request.setAttribute( "amendFlowType", "CANCEL_POLICY" );

				}
				else{
					endorsements = ( (PolicyVO) TaskExecutor.executeTasks( "PREMIUM_PAGE_ENDORSEMENT", policyContext.getPolicyDetails() ) ).getEndorsements();
				}

			}
			policyContext.getPolicyDetails().setEndorsements( endorsements );
		}

		long endtNo = 0;
		if( !Utils.isEmpty( policyVO.getEndtNo() ) ) endtNo = policyVO.getEndtNo();

		/**
		 * If the flow is VIEW POL and endNo is greator than 0 than the policy is endorsement so
		 * wil call dao to get the Endorsement text to be displayed in the Endorsement Summary block.
		 */
		if(Flow.VIEW_POL.equals(AppUtils.getBasicFlowFromResolveReferral( policyVO )) && endtNo > 0) {

			java.util.List<EndorsmentVO> endorsements = null;
			endorsements = ( (PolicyVO) TaskExecutor.executeTasks( "PREMIUM_PAGE_ENDORSEMENT", policyContext.getPolicyDetails() ) ).getEndorsements();
			policyContext.getPolicyDetails().setEndorsements( endorsements );
		}

		if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
			
			//Added StringBuffer to avoid "+" to fix sonar violation on 14-09-2017
			StringBuffer stringBuffer=new StringBuffer();
			String consolidatedReferralMessage = "";

			//Iterating all the referrals to get consolidated message 
			List<ReferralVO> referralVOs = referralListVO.getReferrals();
			for( ReferralVO voTemp : referralVOs ){
				if( !Utils.isEmpty( voTemp ) ){
				//consolidatedReferralMessage += voTemp.getSectionName() + " : " + voTemp.getReferralText() + "\n";
					consolidatedReferralMessage=stringBuffer.append(voTemp.getSectionName()).append(" : ").append("\n").toString();
				}
			}

			policyContext.getPolicyDetails().setConCatRefMsgs( consolidatedReferralMessage );

			resp.setResponseType( com.mindtree.ruc.mvc.Response.Type.JSON );
			resp.setData( referralListVO );
		}
		else{
			/*Start : Changes made to fetch premium details so that premium details are not null or incomplete when 
			 * visited through left navigation link
			 */
			if( !Utils.isEmpty( policyVO ) ){
					PremiumSummary prmSummary =  (PremiumSummary) TaskExecutor.executeTasks( AppConstants.GET_PREMIUM_DETAILS, policyVO );
					request.setAttribute( AppConstants.PREMIUM_PAGE_DETAILS, prmSummary );
					//populatePolContextwithPrmSummary( dataVO, policyContext, policyVO );
					/*
					 * set attribute to know where the convert to policy button to be enabled.
					 */
					if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_CONV_POL_ENABLE )) && request.getParameter( com.Constant.CONST_CONV_POL_ENABLE ).toString().equalsIgnoreCase("true") 
							&&  Flow.VIEW_QUO.equals(policyVO.getAppFlow()) 
							&& !Utils.isEmpty( policyVO.getAuthInfoVO().getTxnType()) 
							&& policyVO.getStatus().equals(AppConstants.QUOTE_ACTIVE )
							&& policyVO.getAuthInfoVO().getTxnType().equals(6)){
					request.setAttribute( com.Constant.CONST_CONV_POL_ENABLE, "true");
					}
			}
			/*
			 * Added quotation number/Policy number on premium summary page.
			 */
			
			/*Commented the if block  for null value check to avoid sonar violation on 3-10-2017  */
			if( policyVO != null && policyVO.getPolicyNo()!= null && !policyVO.getIsQuote()){
			//if( !Utils.isEmpty(policyVO)  && !Utils.isEmpty(policyVO.getPolicyNo()) && !Utils.isEmpty(policyVO.getIsQuote())){
				request.setAttribute( com.Constant.CONST_TRANSACTIONNO, "Policy No : "+policyVO.getConcatPolicyNo() );
			}else{
				if(policyVO != null && policyVO.getQuoteNo()!= null){
					if( policyVO.getPolRefPolicyNo() != null && !policyVO.getPolRefPolicyNo().toString().equals("0")) {
						request.setAttribute( com.Constant.CONST_TRANSACTIONNO, "Renewal Policy No.: "+ policyVO.getPolRefPolicyNo()+" : "+"Quotation : "+policyVO.getQuoteNo() );
					}else{
						request.setAttribute( com.Constant.CONST_TRANSACTIONNO, "Quotation : "+policyVO.getQuoteNo() );
					}
				}
			}
			
			Boolean tradelicenceExists = true;
			tradelicenceExists = checkIfTradelicenceExists(policyVO);
			
			request.setAttribute(AppConstants.TRADE_LICENCE_EXISTS,tradelicenceExists);
			
			/*End : Changes made to fetch premium details so that premium details are not null or incomplete when 
			 * visited through left navigation link
			 */
			resp.setResponseType( com.mindtree.ruc.mvc.Response.Type.HTML );
			Redirection redirection = new Redirection( "/jsp/quote/premium-page.jsp", Type.TO_JSP );
			resp.setRedirection( redirection );
		}

		return resp;
	}
/*
 * Added as part of 3.3 Trade licence download
 */
	private Boolean checkIfTradelicenceExists(PolicyVO polDataVO) {
		//Radar fix
		DataHolderVO<Object[]> holder = null; //new DataHolderVO<Object[]>();
		Boolean tradelicenceExists = true;
		try{
		
		holder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks(  "GET_LINKING_ID_QUO" ,  polDataVO );
		
		Object holderData[] = holder.getData();
		
		Long quoLinkingId = (Long) holderData[0];
		List<BigDecimal> quoEndtList = (List<BigDecimal>) holderData[1];
		Long polLinkingId = (Long) holderData[2];
		List<BigDecimal> polEndtList = (List<BigDecimal>) holderData[3];
		
		
		
		
		if(polEndtList.size() != AppConstants.ZERO){
			tradelicenceExists = fileExists(polEndtList, polLinkingId);
		}
		
		
		
		// if Policy folder path does not exists then check for Quote path folder
		tradelicenceExists = fileExists(quoEndtList, quoLinkingId);
		
		
	} catch(Exception e){
		throw new SystemException( "cmn.unknownError", null, "No linking Id" );
	}
     return tradelicenceExists;
		
	}

	/**
	 * This method is used to populate policy context with premium summary values in case when riskGroup details are not populated 
	 * for a section.
	 * @param dataVO
	 * @param policyContext
	 * @param policyVO
	 *//*
	private void populatePolContextwithPrmSummary( DataHolderVO<List<PremiumSummaryVO>> dataVO, PolicyContext policyContext, PolicyVO policyVO ){

		List<PremiumSummaryVO> parSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> mbSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> biSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> plSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> wcSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> moneySummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> eeSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> tbSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> gpaSummaryVOs = new ArrayList<PremiumSummaryVO>();
		List<PremiumSummaryVO> fidelitySummaryVOs = new ArrayList<PremiumSummaryVO>();
		if( !Utils.isEmpty( dataVO ) ){
			List<PremiumSummaryVO> premiumSummaryVOs = dataVO.getData();
			if( !Utils.isEmpty( premiumSummaryVOs ) ){
				for( PremiumSummaryVO premiumSummaryVO : premiumSummaryVOs ){
					if( !Utils.isEmpty( premiumSummaryVO ) ){
						switch( premiumSummaryVO.getSecId() ){
							case 1:
								parSummaryVOs.add( premiumSummaryVO );
								break;

							case 3:
								mbSummaryVOs.add( premiumSummaryVO );
								break;
							case 2:
								biSummaryVOs.add( premiumSummaryVO );
								break;

							case 6:
								plSummaryVOs.add( premiumSummaryVO );
								break;

							case 7:
								wcSummaryVOs.add( premiumSummaryVO );
								break;

							case 8:
								moneySummaryVOs.add( premiumSummaryVO );
								break;
							case 9:
								fidelitySummaryVOs.add( premiumSummaryVO );
								break;
							case 5:
								eeSummaryVOs.add( premiumSummaryVO );
								break;
							case 10:
								tbSummaryVOs.add( premiumSummaryVO );
								break;
							case 12:
								gpaSummaryVOs.add( premiumSummaryVO );
								break;
							default:
								break;
						}
					}
				}
				populatePremium( policyVO, parSummaryVOs );
				populatePremium( policyVO, mbSummaryVOs );
				populatePremium( policyVO, plSummaryVOs );
				populatePremium( policyVO, biSummaryVOs );
				populatePremium( policyVO, wcSummaryVOs );
				populatePremium( policyVO, moneySummaryVOs );
				populatePremium( policyVO, eeSummaryVOs );
				populatePremium( policyVO, tbSummaryVOs );
				populatePremium( policyVO, gpaSummaryVOs );
				populatePremium( policyVO, fidelitySummaryVOs );
			}
		}

	}*/

	/**
	 * This method populates the premium details for a particular section
	 * @param policyVO
	 * @param parSummaryVOs
	 *//*
	private void populatePremium( PolicyVO policyVO, List<PremiumSummaryVO> parSummaryVOs ){

		SectionVO section = null;
		//RiskGroupDetails groupDetails = null;
		if( !Utils.isEmpty( parSummaryVOs ) ){

			switch( parSummaryVOs.get( 0 ).getSecId() ){
				case 1:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new ParVO();
					break;
				case 2:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new MBVO();
					break;
				case 3:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new MBVO();
					break;
				case 6:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new PublicLiabilityVO();
					break;

				case 7:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new WCVO();
					break;

				case 8:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//groupDetails = new MoneyVO();
					break;
				case 9:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );					
					break;
				case 5:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//	groupDetails = new EEVO();
					break;

				case 10:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//	groupDetails = new TravelBaggageVO();
					break;
				case 12:
					section = getSectionVO( policyVO, parSummaryVOs.get( 0 ).getSecId() );
					//	groupDetails = new GroupPersonalAccidentVO();
					break;
				default:
					break;
			}
			
			 * Below sectionVO NULL check is ideally not required but since even after risk deselection, premium value
			 * for that risk is fetched from V_TRN_PAS_PREMIUM_SUMMARY view, this leads to null pointer
			 
			if( !Utils.isEmpty( section ) ){
				if( !Utils.isEmpty( parSummaryVOs.get( 0 ).getCommission() ) ) section.setCommission( parSummaryVOs.get( 0 ).getCommission().doubleValue() );
				populateRiskGroupDetails( section, parSummaryVOs );
			}

		}
	}*/

	/**
	 * This method is used to populate premium details in risk group details for a given section
	 * @param section
	 * @param parSummaryVOs
	 * @param groupDetails
	 *//*
	private void populateRiskGroupDetails( SectionVO section, List<PremiumSummaryVO> parSummaryVOs ){
		RiskGroupDetails groupDetails = null;
		Map<LocationVO, RiskGroupDetails> locDetails = new LinkedHashMap<LocationVO, RiskGroupDetails>();
		if( !Utils.isEmpty( section ) ){
			Map<? extends RiskGroup, ? extends RiskGroupDetails> locationDetails = section.getRiskGroupDetails();
			if( !Utils.isEmpty( locationDetails ) )
				return;
			else{

				for( PremiumSummaryVO summaryVO : parSummaryVOs ){
					boolean entryPresent = false;
					if( !Utils.isEmpty( summaryVO ) ){
						for( Map.Entry<LocationVO, RiskGroupDetails> entry : locDetails.entrySet() ){
							if( !Utils.isEmpty( entry ) ){
								LocationVO locVO = entry.getKey();
								if( locVO.getRiskGroupId().equals( summaryVO.getLocationId().toString() ) ){
									entryPresent = true;
									groupDetails = entry.getValue();
									double sumInsured = groupDetails.getSumInsured();
									if( !Utils.isEmpty( summaryVO.getCoverSiAmt() ) ){
										sumInsured += summaryVO.getCoverSiAmt().doubleValue();
									}
									groupDetails.setSumInsured( sumInsured );
									double premiumAmt = groupDetails.getPremium().getPremiumAmt();
									if( !Utils.isEmpty( summaryVO.getCoverPrmAmt() ) ) premiumAmt += summaryVO.getCoverPrmAmt().doubleValue();
									groupDetails.getPremium().setPremiumAmt( premiumAmt );
								}
							}
						}
						if( !entryPresent ){
							Short secId = parSummaryVOs.get( 0 ).getSecId();
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_PAR ) ){
								groupDetails = new ParVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_PL ) ){
								groupDetails = new PublicLiabilityVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_BI ) )
							{
								groupDetails = new BIVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_WC ) ){
								groupDetails = new WCVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_MONEY ) ){
								groupDetails = new MoneyVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_FIDELITY ) ){
								groupDetails = new FidelityVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_MB ) ){
								groupDetails = new MBVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_EE ) ){
								groupDetails = new EEVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_TB ) ){
								groupDetails = new TravelBaggageVO();
							}
							if( secId == Short.parseShort( AppConstants.DEFAULT_SECTION_ID_GPA ) ){
								groupDetails = new GroupPersonalAccidentVO();
							}
							LocationVO locationVO = new LocationVO();
							locationVO.setRiskGroupId( summaryVO.getLocationId().toString() );
							locationVO.setRiskGroupName( summaryVO.getLocationName() );

							//							groupDetails = new ParVO();
							if( !Utils.isEmpty( summaryVO.getCoverSiAmt() ) ){
								groupDetails.setSumInsured( summaryVO.getCoverSiAmt().doubleValue() );
							}
							PremiumVO premiumVO = new PremiumVO();
							if( !Utils.isEmpty( summaryVO.getCoverPrmAmt() ) ){
								premiumVO.setPremiumAmt( summaryVO.getCoverPrmAmt().doubleValue() );
							}
							groupDetails.setPremium( premiumVO );
							if( !Utils.isEmpty( summaryVO.getCommission() ) ) groupDetails.setCommission( summaryVO.getCommission().doubleValue() );
							locDetails.put( locationVO, groupDetails );

							if( !Utils.isEmpty( summaryVO.getPolicyId() ) ){
								groupDetails.setPolicyId( summaryVO.getPolicyId() );
							}

						}
					}
				}
				section.setRiskGroupDetails( locDetails );
			}
		}

	}*/
	/*
	 * Added as part of 3.3 Trade licence download
	 */
	private Boolean fileExists(List<BigDecimal> endtList, Long linkingId) {
		String filepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_TRADE_LICENCE_FOLDER" )+"/"+linkingId+"/";
		
		String filename = Utils.getSingleValueAppConfig( "TRADE_LIC_DOWNLOAD_FILE_NAME" )+"_";
		
		List<String> extensionsList = AppUtils.getExtensionSupported();
		
		for ( BigDecimal endIt :  endtList){
			String filenameWithEndtId = filename + endIt;
			
			for (String extension : extensionsList) {

				
				File file = new File(filepath + filenameWithEndtId + "." + extension);

				if (file.isFile()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method gets the section details for a given section ID
	 * @param policyVO
	 * @param secId
	 * @return
	 *//*
	private SectionVO getSectionVO( PolicyVO policyVO, Short secId ){
		List<SectionVO> sectionVOs = policyVO.getRiskDetails();
		SectionVO sectionVO = null;
		if( !Utils.isEmpty( sectionVOs ) ){
			for( SectionVO section : sectionVOs ){
				if( secId.shortValue() == section.getSectionId().shortValue() ){
					sectionVO = section;
					break;
				}
			}
		}
		return sectionVO;
	}*/

	public static boolean isReadOnlyMode( PolicyVO policyVO ){
		Flow appFlow = AppUtils.getBasicFlowFromResolveReferral( policyVO );
		if( !Utils.isEmpty( appFlow ) && ( appFlow == Flow.VIEW_QUO || appFlow == Flow.VIEW_POL ) ){
			return true;
		}else{
			return false;
		}
	}
}
