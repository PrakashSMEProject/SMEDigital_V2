package com.rsaame.pas.par.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;


public class ParLoadDAO extends BaseSectionLoadDAO{
	private final static Integer CNT_RSK_CODE = Integer.valueOf(2);

	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId,LoadExistingInputVO lei ){
		
		/* fetch   PAR/UWDetails details from TTrnBuildingQuo */
		Double totalParSi = new Double( 0 );
		Double totalLocPrm = 0.0;
		Double prmAmountAct = 0.0;
		List<TTrnBuildingQuo> trnBuildingQuoList = null;
		ParVO parVO =null;
		PARUWDetailsVO pARUWDetailsVO = new PARUWDetailsVO();
		List<TTrnPremiumQuo> premiumQuoList = null;
		com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails> propertyCoversDetails = new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class );
		Double deductibles = null;
		
		trnBuildingQuoList = (List<TTrnBuildingQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_BUILDING_POLICY", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()));
		/* Throw a business exception in case building/quo list obtained is null */
		if( Utils.isEmpty( trnBuildingQuoList ) ){
			throw new BusinessException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Data retrieved for Building/Quo table is null for [ " + riskGroup.getRiskGroupId()
					+ " ] risk group id" + " and [ " + policyId + " ] policy id" );
		}
		
		parVO=new 	ParVO();
		TTrnBuildingQuo trnBuildingQuo = trnBuildingQuoList.get(0);
		
		parVO = BeanMapper.map( trnBuildingQuo, parVO );
		parVO.setBasicRiskId(trnBuildingQuo.getId().getBldId());
		pARUWDetailsVO= BeanMapper.map( trnBuildingQuo, pARUWDetailsVO );
		
			
		for( TTrnBuildingQuo tTrnBuildingQuo : trnBuildingQuoList ){
	
			/* Basic risk details i.e. Building cover details are to be handled outside of 
			 * PropertyRiskDetails which will now be only used for Contents List which 
			 * are dynamic in nature */
			if( !Utils.isEmpty( tTrnBuildingQuo.getBldSumInsured() ) ){
				parVO.setBldCover( tTrnBuildingQuo.getBldSumInsured().doubleValue() );
				totalParSi = totalParSi + tTrnBuildingQuo.getBldSumInsured().doubleValue();
			}

			if( !Utils.isEmpty( trnBuildingQuo.getBldDesc() ) ){
				parVO.setBldDesc( trnBuildingQuo.getBldDesc() );
			}
			
			
			premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( com.Constant.CONST_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					new BigDecimal( trnBuildingQuo.getId().getBldId() ), new BigDecimal( trnBuildingQuo.getId().getBldId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE )
							.shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_SUB_TYPE" ) ) );
			
			if( !Utils.isEmpty( premiumQuoList ) ){
				for( TTrnPremiumQuo premiumQuo : premiumQuoList ){
					if( !Utils.isEmpty( premiumQuo.getPrmCompulsoryExcess() ) ){
						deductibles = premiumQuo.getPrmCompulsoryExcess().doubleValue();
						/** riskDetails.setDeductibles(deductibles);*/
						parVO.setBldDeductibles( deductibles );
					}
					/* assigning building premium and adding up the same to obtain location level premium */
					if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
						PremiumVO prmVO = new PremiumVO();
						// Renewals will call the rating again to set the premium
						if(!lei.getAppFlow().equals( Flow.RENEWAL))
						{
							prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
							if(!Utils.isEmpty( premiumQuo.getPrmPremiumActual()))
							{
								prmAmountAct =  premiumQuo.getPrmPremiumActual().doubleValue();
								prmVO.setPremiumAmtActual(prmAmountAct);
							}
						}
						totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
						parVO.setBldPremium( prmVO );
				
					}
					break;
				}
			}
		}
		
		/*
		 * Contents will be handled using PropertyRiskDetails List
		 */
		List<TTrnContentQuo> tTrnContentQuoList = null;
		
		
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		
		/** 
		 * Changed the query for fetching contents as previous query was not fetching records for canceled policy as well as now we are fetching the 
		 * record based on validity start date.
		 */
			
		tTrnContentQuoList = getEndtStateContentList(validityStartDate,policyId,endId,riskGroup,lei);
		
		
		if(!Utils.isEmpty(tTrnContentQuoList)){
			
			/* Get the list of contents configured for the section using section id, class code, tariff code 
			 * and risk code as the key. This is in order to retain original configured content list to load 
			 * the values entered under different contents by the user */

			
			/* Form a master list of contents to merge with the contents saved by the user so that while displaying using
			 * json jsp we always have comprehensive list of contents and each saved content value is displayed correctly */
			
			List<TTrnPolicyQuo> polLst = (List<TTrnPolicyQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_POLICY, lei.getAppFlow(), getHibernateTemplate(),
					SvcConstants.IS_TABLE_QUERY_HBM, endId, lei.getPolLinkingId(), Integer.valueOf( SvcConstants.CLASS_ID_PAR ).shortValue() );
			/*
			List<TTrnPolicyQuo> polLst = getHibernateTemplate().find("from TTrnPolicyQuo pol where (pol.id.polEndtId,pol.id.policyId) in (select  max(innerpol.id.polEndtId), innerpol.id.polPolicyId from TTrnPolicyQuo innerpol " +
					"where ",policyId,endId);
			*/
			if(Utils.isEmpty( polLst )){
				throw new BusinessException( "cmn.unknownError", null, "Couldn't retrieve record from TTrnPolicy/Quo table for ["+ policyId +" ] policy id and ["+ endId +"] endorsement id" +
						" for flow ["+ lei.getAppFlow()+"]" );
			}
			
			/* polLst obtained can be of size greater than 1 but tariff code across all the records will be same hence using get(0)  */
			Integer polTarCode = polLst.get( 0 ).getPolTarCode().intValue();
			
			List<VMasPasFetchBasicDtls> basicPlusAddtlCoverCntLst = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), null, null, DAOUtils.constructPPPCriteriaVOForPPPDataFetch(2,1,polTarCode), true, CNT_RSK_CODE );
			
			
			
			for(VMasPasFetchBasicDtls vMasPasFetchBasicDtl: basicPlusAddtlCoverCntLst ){
				PropertyRiskDetails riskDetails = new PropertyRiskDetails();
				riskDetails.setRiskType( vMasPasFetchBasicDtl.getId().getPrRtCode() );
				propertyCoversDetails.add(riskDetails);
			}
			
			for(TTrnContentQuo tTrnContentQuo : tTrnContentQuoList){
				
				PropertyRiskDetails riskDetails = getRiskDetailsForContentKey(propertyCoversDetails,tTrnContentQuo );
				/*
				 * Check if the riskDetails obtained is null in order to throw BusinessException as we cannot
				 * proceed with the flow but however its not a ideal scenario
				 */
				if( Utils.isEmpty( riskDetails ) ){
					throw new BusinessException( "par.contentCategoryIsNull", null, "Property Risk Details object with same content category " + "not found for [" + policyId
							+ " ] policy id and [" + endId + "] endt id" );
				}
				
				
				if(!Utils.isEmpty(tTrnContentQuo.getCntSumInsured())){
					riskDetails.setCover(tTrnContentQuo.getCntSumInsured().doubleValue());
					totalParSi = totalParSi + tTrnContentQuo.getCntSumInsured().doubleValue();
				}
				if(!Utils.isEmpty(tTrnContentQuo.getCntDescription())){
					riskDetails.setDesc(tTrnContentQuo.getCntDescription());
				}
				if(!Utils.isEmpty(tTrnContentQuo.getId())){
					riskDetails.setSetValidityStartDate(tTrnContentQuo.getId().getCntValidityStartDate());
					riskDetails.setCoverId(tTrnContentQuo.getId().getCntContentId());
				}
				riskDetails.setBuildingId(tTrnContentQuo.getCntBasicRiskId());
				riskDetails.setRiskType(tTrnContentQuo.getCntCategory());
				
				premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( com.Constant.CONST_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						new BigDecimal( tTrnContentQuo.getId().getCntContentId() ), new BigDecimal( tTrnContentQuo.getCntBasicRiskId() ),
						Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_SUB_TYPE" ) ) );
				
				if(!Utils.isEmpty(premiumQuoList)){
					for(TTrnPremiumQuo premiumQuo :premiumQuoList){
						if(!Utils.isEmpty(premiumQuo.getPrmCompulsoryExcess())){
							deductibles=premiumQuo.getPrmCompulsoryExcess().doubleValue();
							riskDetails.setDeductibles(deductibles);
						}
						 /** SK : Changes */
						/** assigning individual content premium and adding up the same to obtain location level premium */
						if(!Utils.isEmpty(premiumQuo.getPrmPremium())){
							PremiumVO prmVO = new PremiumVO();
							// Renewals will call the rating again to set the premium
							if(!lei.getAppFlow().equals( Flow.RENEWAL)){
								prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() ) ;
								if(!Utils.isEmpty( premiumQuo.getPrmPremiumActual()))
								{
									prmAmountAct +=  premiumQuo.getPrmPremiumActual().doubleValue();
									prmVO.setPremiumAmtActual(prmAmountAct);
								}
							}
							totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
							riskDetails.setPremium( prmVO );
							if(lei.getAppFlow().equals( Flow.RENEWAL)){
								riskDetails.setCoverCode( Integer.valueOf( Short.toString(premiumQuo.getId().getPrmCovCode())));
							}
						}
						/*Below check to be removed after 3.7 and merged with above check*/
						if(lei.getAppFlow().equals( Flow.RENEWAL) && SvcUtils.isAlsamScheme(lei.getTariffCode().toString())){
							riskDetails.setCoverCode( Integer.valueOf( Short.toString(premiumQuo.getId().getPrmCovCode())));
						}
					}
				}		
			}
			
			handleAdditionalCoversDataLoad( propertyCoversDetails, basicPlusAddtlCoverCntLst, polTarCode, parVO.getBasicRiskId(), policyId, endId, lei, totalLocPrm );
			
		}
		if(Utils.isEmpty(parVO.getCovers())){
			parVO.setCovers(new PropertyRisks());
		}	
		parVO.getCovers().setPropertyCoversDetails(propertyCoversDetails);
		
		/* setting the premium */
		parVO.setSumInsured(totalParSi);
		
		parVO.setUwDetails(pARUWDetailsVO);
		
		
		/* Fetching the questions */
		//UWQuestionVO question = new UWQuestionVO();
		UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();
		List<TTrnUwQuestionsQuo> questionsQuo = null;
		List<UWQuestionVO> uwQuestionList= new ArrayList<UWQuestionVO> ();
		/*if((lei.getAppFlow()==Flow.AMEND_POL) || (lei.getAppFlow()==Flow.RESOLVE_REFERAL))
		{
			questionsQuo = getHibernateTemplate().find("from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? " +
					"and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityExpiryDate = ?",Long.valueOf(policyId),Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()),SvcConstants.EXP_DATE);
		}
		else
		{
			questionsQuo = (List<TTrnUwQuestionsQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_UW_QUESTIONS", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()));
		}*/
		
		/*
		 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
		 * 
		 */
		
		questionsQuo = DAOUtils.getEndtStateUWQ(getHibernateTemplate(),validityStartDate,policyId,endId,riskGroup,lei);
		
		
		for(TTrnUwQuestionsQuo questionsVOs : questionsQuo){
			UWQuestionVO uwQuestion=new UWQuestionVO();
			uwQuestion.setQId(questionsVOs.getId().getUqtUwqCode());
			uwQuestion.setResponse(questionsVOs.getUqtUwqAnswer());
			uwQuestionList.add(uwQuestion);
		}
		uWQuestionsVO.setQuestions(uwQuestionList);
		parVO.setUwQuestions(uWQuestionsVO);
		parVO.setPolicyId(policyId);		

		/** SK : Changes */
		/** Passing back the summed up location premium through riskgroupdetails premium field */
		PremiumVO prmVO = new PremiumVO();
		 //Added for Bahrain 3 decimal - Starts
		 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
				prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
		 }else {
					 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
		 }		 
		//Added for Bahrain 3 decimal - Ends
		prmVO.setPremiumAmtActual( prmAmountAct ); // required during BI rating call
		parVO.setPremium( prmVO );
		return parVO;
	}


	/**
	 * This method gets the riskDetails object from the List<PropertyRiskDetails> ( Master list of
	 * contents configured for the tariff) which has same riskType as that of Content object 
	 * passed
	 * @param prdLst
	 * @param tTrnContentQuo
	 * @return
	 */
	public PropertyRiskDetails getRiskDetailsForContentKey(com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails> prdLst,TTrnContentQuo tTrnContentQuo ){
		PropertyRiskDetails prdToBeReturned = null;
		for(PropertyRiskDetails prd : prdLst){	
			if( prd.getRiskType().equals( tTrnContentQuo.getCntCategory() ) ){
				return prd;
			}
		}
		return prdToBeReturned;
	}
	
	public void handleAdditionalCoversDataLoad( com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails> propertyCoversDetails, List<VMasPasFetchBasicDtls> basicPlusAddtlCoverCntLst,
			Integer policyTariffCode, Long basicRiskIdForLocation, Long policyId, Long endId, LoadExistingInputVO lei, Double totalLocPrm ){

		basicPlusAddtlCoverCntLst = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), null, basicPlusAddtlCoverCntLst,
				DAOUtils.constructPPPCriteriaVOForPPPDataFetch( 2, 1, policyTariffCode ), false, Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_BASIC_RISK" ) ) );

		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtl : basicPlusAddtlCoverCntLst ){
			if( vMasPasFetchBasicDtl.getId().getPrCovCode().intValue() == SvcConstants.APP_BASE_COVER_CODE ) continue;

			PropertyRiskDetails riskDetails = new PropertyRiskDetails();

			/*
			 * In case sum insured and limit both are defined then consider limit value
			 * as sum insured. This generally is the configuration in cases where a range is defined
			 * using sum insured and limit columns. Hence using this for Additional cover case handling
			 */
			riskDetails = BeanMapper.map( vMasPasFetchBasicDtl, riskDetails );
			//updateKeyValuesToPRD( riskDetails, vMasPasFetchBasicDtl );

			TTrnPremiumQuo prmRecordForCoverCode = getPrmTableRecordForCoverCode( vMasPasFetchBasicDtl, basicRiskIdForLocation, policyId, endId, lei );
			if( Utils.isEmpty( prmRecordForCoverCode ) ){
				riskDetails.setCoverOpted( SvcConstants.APP_ADDITIONAL_COVER_NOT_OPTED );			
				
				if( !Utils.isEmpty( vMasPasFetchBasicDtl.getId().getPrSumInsured() ) && !Utils.isEmpty( vMasPasFetchBasicDtl.getId().getPrLimit() )
						|| !Utils.isEmpty( vMasPasFetchBasicDtl.getId().getPrLimit() ) ){
					riskDetails.setCover(  vMasPasFetchBasicDtl.getId().getPrLimit().doubleValue() );
				}
				
				if( !Utils.isEmpty( vMasPasFetchBasicDtl.getId().getPrCompulsoryExcess() ) ){
					riskDetails.setDeductibles(  vMasPasFetchBasicDtl.getId().getPrCompulsoryExcess().doubleValue() );
				}
			}
			if( !Utils.isEmpty( prmRecordForCoverCode ) ){
				if( !Utils.isEmpty( prmRecordForCoverCode.getPrmCompulsoryExcess() ) ){
					prmRecordForCoverCode.getPrmCompulsoryExcess().doubleValue();
					riskDetails.setDeductibles( prmRecordForCoverCode.getPrmCompulsoryExcess().doubleValue() );
				}

				/* 
				 * assigning individual content premium and adding up the same to obtain location level premium 
				 * */
				if( !Utils.isEmpty( prmRecordForCoverCode.getPrmPremium() ) ){
					PremiumVO prmVO = new PremiumVO();
					// Renewals will call the rating again to set the premium
					if(!lei.getAppFlow().equals( Flow.RENEWAL)){
						prmVO.setPremiumAmt( prmRecordForCoverCode.getPrmPremium().doubleValue() );
					}
					totalLocPrm += prmRecordForCoverCode.getPrmPremium().doubleValue();
					riskDetails.setPremium( prmVO );
				}

				riskDetails.setCoverOpted( SvcConstants.APP_ADDITIONAL_COVER_OPTED );
				riskDetails.setCover( prmRecordForCoverCode.getPrmSumInsured().doubleValue() );
				if(lei.getAppFlow().equals( Flow.RENEWAL)){
					//riskDetails.setCoverId( prmRecordForCoverCode.getId().get )
					riskDetails.setCoverCode(  Integer.valueOf( Short.toString(prmRecordForCoverCode.getId().getPrmCovCode())) );
				}
			}
			propertyCoversDetails.add( riskDetails );
		}
	}
	
	public void updateKeyValuesToPRD(PropertyRiskDetails prd, VMasPasFetchBasicDtls addtlCoverCnt){
		prd.setRiskType( addtlCoverCnt.getId().getPrRtCode() );
		prd.setRiskCat( addtlCoverCnt.getId().getPrRcCode() );
		prd.setRiskSubCat( addtlCoverCnt.getId().getPrRscCode() );
		prd.setCoverCode( addtlCoverCnt.getId().getPrCovCode().intValue() );
		prd.setCoverType( addtlCoverCnt.getId().getPrCtCode().intValue() );
		prd.setCoverSubType( addtlCoverCnt.getId().getPrCstCode().intValue() );
	}
	
	
	public TTrnPremiumQuo getPrmTableRecordForCoverCode(VMasPasFetchBasicDtls addtlCoverCnt, Long basicRiskIdForLocation, Long policyId, Long endId,LoadExistingInputVO lei){
		List<TTrnPremiumQuo> premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( com.Constant.CONST_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId,
				policyId, new BigDecimal( basicRiskIdForLocation ), new BigDecimal( basicRiskIdForLocation ), addtlCoverCnt.getId().getPrCovCode(), addtlCoverCnt.getId()
						.getPrCtCode(), addtlCoverCnt.getId().getPrCstCode() );
		
		TTrnPremiumQuo NIL_PRM_RECORD_FOR_COVER = null;
		if(Utils.isEmpty( premiumQuoList ) ) return NIL_PRM_RECORD_FOR_COVER;
		
		if( premiumQuoList.size() > 1 ) {
			throw new BusinessException( "cmn.multiplePrmEntriesCoverCode", null, " Mutiple records fetch from T_TRN_PREMIUM(_QUO) for PAR section with basicRiskId  [ "+ basicRiskIdForLocation + " ] location " );
		}
		 
		return premiumQuoList.get( 0 );
	}
	
	/**
	 * 
	 * @param validityStartDate
	 * @param policyId
	 * @param endId
	 * @param riskGroup
	 * @param lei
	 * @return
	 */
	private List<TTrnContentQuo> getEndtStateContentList( Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei ){
		List<TTrnContentQuo> tTrnContentQuoList = null;
		if( !Utils.isEmpty( validityStartDate ) ){

			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and cntEndtId = ? and cntBasicRiskId = ? and cntRiskDtl=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( "PAR_RISK_DTL" ) ) );
			}
			else{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and " +
								" cntEndtId <= ? and cntStatus <> 4 and cntBasicRiskId = ? and cntRiskDtl=?", policyId,
						validityStartDate, validityStartDate, endId ,Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( "PAR_RISK_DTL" ) ));

			}
		}
		
		return tTrnContentQuoList;
	}
	 
	
}
