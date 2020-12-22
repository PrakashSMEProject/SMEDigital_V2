package com.rsaame.pas.money.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnGaccBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashDetailsQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SafeVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.app.LoadExistingInputVO;

/**
 * @author m1016303
 *
 */

public class MoneyLoadDAO extends BaseSectionLoadDAO{
	
	
	@Override
	public RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId,LoadExistingInputVO lei ){
		MoneyVO moneyVO = null;
		LocationVO locationVO = (LocationVO) riskGroup;
		java.util.List<SumInsuredVO> sumInsuredDets = new ArrayList<SumInsuredVO>();
		Double totalLocPrm = 0.0;
		/*
		 * Along with List<SumInsuredVO> populate List<Contents> so that the existing 
		 * data display can be from Contents List rather than SumInsuredVO list.
		 * Setting the data to List<SumInsuredVO> can be removed after bringing in 
		 * common approach for dynamic content display in MoneyContent 
		 */
		java.util.List<Contents> contentsList = new ArrayList<Contents>();
		
		java.util.List< SafeVO> safeDetails =  new com.mindtree.ruc.cmn.utils.List<SafeVO>(SafeVO.class);
		java.util.List<CashResidenceVO> cashResDetails = new com.mindtree.ruc.cmn.utils.List<CashResidenceVO>(CashResidenceVO.class);
		
		/* SK : Changes */
		/* Below logic is added to obtain the basic risk id from T_TRN_GACC_BUILDING/QUO 
		 *  table basis risk group id obtained from LocationVO
		 */
		java.util.List<TTrnGaccBuildingQuo> gaccBldList =null;
		/*if ((lei.getAppFlow()==Flow.AMEND_POL))
		{
		gaccBldList = getHibernateTemplate().find( "from TTrnGaccBuildingQuo ttrn where ttrn.gbdPolicyId=? and ttrn.gbdValidityExpiryDate=? and ttrn.gbdBldId=?", policyId,
				SvcConstants.EXP_DATE, Long.valueOf( locationVO.getRiskGroupId() ) );
		}
		else{
			gaccBldList = getHibernateTemplate().find( "from TTrnGaccBuildingQuo where gbdPolicyId=? and gbdEndtId=? and gbdBldId=?", policyId,
					endId, Long.valueOf( locationVO.getRiskGroupId() ) );
		}
		*/
		
		gaccBldList = (List<TTrnGaccBuildingQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_BUILDING_LOAD", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,Long.valueOf( locationVO.getRiskGroupId() ));
		
		if(Utils.isEmpty( gaccBldList )){
			throw new BusinessException( CommonErrorKeys.UNKNOWN_ERROR, null, " Data retrived from ttrngaccbld/quo is null for [ " + locationVO.getRiskGroupId() + " ]" +
					" risk group id and [ " + policyId + " ] policy id "  );
		}
		
		
		TTrnGaccBuildingQuo gaccBld = gaccBldList.get( 0 );
		/* Even if there are contents values entered there will be a record in building */
		moneyVO = new MoneyVO();
		moneyVO.setBasicRiskId( gaccBld.getId().getGbdId() );
		
		java.util.List<TTrnGaccCashQuo> cashQuoList=null;
		
		/*if ((lei.getAppFlow()==Flow.AMEND_POL))
		{
			 cashQuoList =getHibernateTemplate().find( "from TTrnGaccCashQuo ttrn where ttrn.gchPolicyId=? and ttrn.gchValidityExpiryDate=? and ttrn.gchBasicRiskId=? " +
					" and ttrn.gchStatus <> 4", policyId,
				SvcConstants.EXP_DATE, gaccBld.getId().getGbdId() );
			
			
			cashQuoList = (List<TTrnGaccCashQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_CASH_LOAD", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,gaccBld.getId().getGbdId());
			
		}else{
			cashQuoList = getHibernateTemplate().find( "from TTrnGaccCashQuo where gchPolicyId=? and gchEndtId=? and gchBasicRiskId=?", policyId,
					endId, gaccBld.getId().getGbdId() );
		}*/
	
		cashQuoList = (List<TTrnGaccCashQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_CASH_LOAD", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,gaccBld.getId().getGbdId(),null);
		
		if( !Utils.isEmpty( cashQuoList ) ){

			//System.out.println( cashQuoList.get( 0 ).getGchBasicRiskId() );
			Boolean cashInResidence = false;
			Boolean excessCashInSafe = false;
			Boolean setValidityDate = true;
			for( TTrnGaccCashQuo cashQ : cashQuoList ){

				/*
				 * Check has been introduced to skip addition of
				 * cash in residence to SumInsured
				 */
				SumInsuredVO siVO = getSumInsuredVO( cashQ );
				if( !Utils.isEmpty( siVO ) ) sumInsuredDets.add( getSumInsuredVO( cashQ ) );

				/*
				 * Populate contentsList by constructing Contents VO for each cashQ record
				 */

				Contents content = getContentsVO( cashQ,lei.getAppFlow() );
				if( !Utils.isEmpty( content ) && !Utils.isEmpty( content.getPremium() ) ) totalLocPrm += content.getPremium().getPremiumAmt();
				if( !Utils.isEmpty( content ) ) contentsList.add( content );

				/*java.util.List<TTrnGaccCashDetailsQuo> cashDetailsQuoList = getHibernateTemplate().find( "from TTrnGaccCashDetailsQuo where gcdGchId=? and gcdPolPolicyId=?",
						cashQ.getId().getGchId(), cashQ.getGchPolicyId() );*/
				
				//java.util.List<TTrnGaccCashDetailsQuo> cashDetailsQuoList = (List<TTrnGaccCashDetailsQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_GACC_CASH_DETAILS_LOAD", lei.getAppFlow(), getHibernateTemplate(), false, endId, cashQ.getId().getGchId(), cashQ.getGchPolicyId() );
				java.util.List<TTrnGaccCashDetailsQuo> cashDetailsQuoList = null;
				
				/*
				if ((lei.getAppFlow()==Flow.AMEND_POL || lei.getAppFlow()==Flow.EDIT_QUO)){
					cashDetailsQuoList = (List<TTrnGaccCashDetailsQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_GACC_CASH_DETAILS_LOAD", lei.getAppFlow(), getHibernateTemplate(), false, endId, cashQ.getId().getGchId(), cashQ.getGchPolicyId() );
				}else{
					
					cashDetailsQuoList = (List<TTrnGaccCashDetailsQuo>) getHibernateTemplate().find( "from TTrnGaccCashDetailsQuo gcd where " +
							"gcd.gcdPolEndtId <= ? and gcd.gcdGchId= ? and " +
							"gcd.gcdPolPolicyId= ? and gcd.gcdValidityExpiryDate= ? and gcd.gcdStatus in (1,6) ", endId,
							cashQ.getId().getGchId(),policyId,SvcConstants.EXP_DATE );
				}*/
				
				/** 
				 * TTrnGaccCashDetailsQuo record is fetched based on validity start date and bld id. 
				 */
				Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId,lei.getPolLinkingId(),lei.isQuote() );
				
				/** 
				 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
				 * 
				 */
				cashDetailsQuoList = getEndtStateCashDetails(validityStartDate,policyId,endId,riskGroup,lei,cashQ);
				
				for( TTrnGaccCashDetailsQuo cashDetailsQuo : cashDetailsQuoList ){
					if( isSafeListPresent( cashDetailsQuo ) ){
						excessCashInSafe = true;
						SafeVO safeVO = getSafeVO( cashDetailsQuo );
						safeVO.setIndex( cashDetailsQuoList.indexOf( cashDetailsQuo ) );
						safeDetails.add( safeVO );
					}

					if( isCashInResPresent( cashDetailsQuo ) ){
						cashInResidence = true;
						CashResidenceVO cashResVO = getCashResidence( cashDetailsQuo );
						cashResVO.setIndex( cashDetailsQuoList.indexOf( cashDetailsQuo ) );
						cashResDetails.add( cashResVO );
					}
				}

				if( setValidityDate ){
					moneyVO.setValidityStartDate( cashQ.getId().getGchValidityStartDate() );
					setValidityDate = false;
				}

			}

			moneyVO.setSumInsuredDets( sumInsuredDets );
			moneyVO.setBasicRiskId( gaccBld.getId().getGbdId() );
			
			if(!lei.getIsPrepackaged()){
				contentsList = orderContentList(contentsList,lei.getAppFlow());
			}
			
			/*
			 * Set contentsList value to MoneyVO which will be used to display
			 * in moneyjson.jsp
			 */
			moneyVO.setContentsList( contentsList );
			moneyVO.setCashInResidence( cashInResidence );
			moneyVO.setCashResDetails( cashResDetails );
			moneyVO.setExcessCashInSafe( excessCashInSafe );
			moneyVO.setSafeDetails( safeDetails );
			PremiumVO prmVO = new PremiumVO();
			//Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
					prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
			 }else {
						 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
			 }		 
			//Added for Bahrain 3 decimal - Ends
			//To avoid SI is getting displayed as 0 in Premium page
			if(!Utils.isEmpty(sumInsuredDets)){
				for( SumInsuredVO sumInured : sumInsuredDets ){
					if(Utils.isEmpty(moneyVO.getSumInsured())){
						moneyVO.setSumInsured( sumInured.getSumInsured() );
					} else {
						moneyVO.setSumInsured( moneyVO.getSumInsured()+ sumInured.getSumInsured());
					}
				}
			}
			// To add cash at residence to sum insured
			if(!Utils.isEmpty(cashResDetails)){
				for( CashResidenceVO cashRes : cashResDetails ){
					if(!Utils.isEmpty(cashRes.getSumInsuredDets().getSumInsured())){
						if(Utils.isEmpty(moneyVO.getSumInsured())){
							moneyVO.setSumInsured( cashRes.getSumInsuredDets().getSumInsured() );
						} else {
							moneyVO.setSumInsured( moneyVO.getSumInsured()+ cashRes.getSumInsuredDets().getSumInsured());
						}
				     }
				}
			}
			
			moneyVO.setPremium( prmVO );
		}
		return moneyVO;
	}

	private List<TTrnGaccCashDetailsQuo> getEndtStateCashDetails( Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei,TTrnGaccCashQuo cashQ  ){
		
		java.util.List<TTrnGaccCashDetailsQuo> cashDetailsQuoList = null;
		
		if( !Utils.isEmpty( validityStartDate ) ){

			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){

				cashDetailsQuoList = (List<TTrnGaccCashDetailsQuo>) getHibernateTemplate().find(
						"from TTrnGaccCashDetailsQuo gcd where gcd.gcdPolEndtId = ? "
								+ "and gcd.gcdGchId= ? and gcd.gcdPolPolicyId= ? and gcd.id.gcdValidityStartDate <= ? and gcd.gcdValidityExpiryDate > ? ", endId,
						 cashQ.getId().getGchId(), policyId, validityStartDate, validityStartDate );
			}
			else{
				cashDetailsQuoList = (List<TTrnGaccCashDetailsQuo>) getHibernateTemplate().find(
						"from TTrnGaccCashDetailsQuo gcd where gcd.gcdPolEndtId <= ? and gcd.gcdStatus <>4 "
								+ "and gcd.gcdGchId= ? and gcd.gcdPolPolicyId= ? and gcd.id.gcdValidityStartDate <= ? and " + "  gcd.gcdValidityExpiryDate > ? ", endId,
						 cashQ.getId().getGchId(), policyId, validityStartDate, validityStartDate );
			}

		}
		return cashDetailsQuoList;
	}

	private List<Contents> orderContentList( List<Contents> contentsList, Flow flow ){

		HashMap<String, Contents > contentToSort =  new HashMap<String, Contents>();
		for( Contents content : contentsList ){
			String key = Utils.concat( content.getRiskType().toString().trim(),"-",
					content.getRiskCat().toString().trim(),"-",
					content.getRiskSubCat().toString().trim() );
			contentToSort.put( key, content );
		}
		
		List<Contents> sortedContentsList = new ArrayList<Contents>();
		/* The N number of contents configured for money and which are not mandatory, In that case to render
		 * saved contents in correct order, manipulating list with saved content and dummy contentVo for not saved contents
		 * in the order required for rendering on the jsp page.*/
		int moneyTotalContents = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_RISK_TYPES_COUNT" ) ).intValue(); 
		// For renewals add Cash in Residence also in money content list as it is just used for the purpose of updating the premium, not used in UI
		if(flow.equals( Flow.RENEWAL)){
			moneyTotalContents++;
		}
		for( int i = 1; i <= moneyTotalContents; i++ ){
			String riskType = "MONEY_RISK_TYPES_" + i;
			String riskCodes = Utils.getSingleValueAppConfig( riskType );
			if(!Utils.isEmpty( contentToSort.get( riskCodes ) )){
				sortedContentsList.add( contentToSort.get( riskCodes ) );
			}else{
				sortedContentsList.add( new Contents() );
			}
		}
		
		return sortedContentsList;
	}

	private CashResidenceVO getCashResidence( TTrnGaccCashDetailsQuo cashDetailsQuo ){
		CashResidenceVO cashResVO = new CashResidenceVO();
		SumInsuredVO  sumInsuredDets = new SumInsuredVO();
		
		if(!Utils.isEmpty( cashDetailsQuo.getGcdCashResEmpEName() ) ) cashResVO.setEmpName( cashDetailsQuo.getGcdCashResEmpEName() );
		if(!Utils.isEmpty( cashDetailsQuo.getGcdCashResOccupation() ) ) cashResVO.setOccupation( cashDetailsQuo.getGcdCashResOccupation() );
		if(!Utils.isEmpty( cashDetailsQuo.getGcdCashResAmt() ) ) sumInsuredDets.setSumInsured( cashDetailsQuo.getGcdCashResAmt().doubleValue() );
		cashResVO.setSumInsuredDets( sumInsuredDets );
		
		/*
		 * Pass back the id and vsd to UI so that during edit flow duplicate entries
		 * are not created in the tables
		 */
		cashResVO.setId( cashDetailsQuo.getId().getGcdId() );
		cashResVO.setVsd( cashDetailsQuo.getId().getGcdValidityStartDate() );
		
		return cashResVO;
	}

	private boolean isCashInResPresent( TTrnGaccCashDetailsQuo cashDetailsQuo ){
		if(!Utils.isEmpty(cashDetailsQuo.getGcdCashResEmpEName()) ){
			return true;
		}
		
		return false;
	}

	private SafeVO getSafeVO( TTrnGaccCashDetailsQuo cashDetailsQuo ){
		SafeVO safeVO = new SafeVO();
		
		
		if(!Utils.isEmpty( cashDetailsQuo.getGcdSafeDwrMake() ) )  safeVO.setMake( cashDetailsQuo.getGcdSafeDwrMake() );
		if(!Utils.isEmpty( cashDetailsQuo.getGcdSafeDwrHeightCms() ) )  safeVO.setHeight( cashDetailsQuo.getGcdSafeDwrHeightCms().doubleValue());
		if(!Utils.isEmpty( cashDetailsQuo.getGcdSafeDwrWeightKg() ) ) safeVO.setWeight( cashDetailsQuo.getGcdSafeDwrWeightKg().doubleValue() );
		if(!Utils.isEmpty( cashDetailsQuo.getGcdSafeDwrWidthCms() ) ) safeVO.setWidth( cashDetailsQuo.getGcdSafeDwrWidthCms().doubleValue() );
		if(!Utils.isEmpty( cashDetailsQuo.getGcdSafeDwrAnchoredFlag() ) ) safeVO.setAnchored( cashDetailsQuo.getGcdSafeDwrAnchoredFlag() );
		safeVO.setCashDetailsid( cashDetailsQuo.getId().getGcdId() );
		/*
		 * Pass back the id and validity start date back to UI so that save operation
		 * during edit quote flow doesn't result in duplicate entries
		 */
		safeVO.setId( cashDetailsQuo.getId().getGcdId() );
		safeVO.setVsd( cashDetailsQuo.getId().getGcdValidityStartDate() );
		return safeVO;
	}

		
	private boolean isSafeListPresent(TTrnGaccCashDetailsQuo cashDetailsQuo){
		if(!Utils.isEmpty(cashDetailsQuo.getGcdSafeDwrMake()) ){
			return true;
		}
		
		return false;
	}

	private SumInsuredVO getSumInsuredVO( TTrnGaccCashQuo cashQ ){
		SumInsuredVO sumInsuredVO = null;
		/* RiskType-RiskCategory-RiskSubCategory (5-0-0) is an entry for cash in residence hence 
		 *  should not be part of SumInsuredDets List or Contents List to be displayed */
		if( !Utils.getSingleValueAppConfig( "MONEY_RISK_TYPES_7" ).equalsIgnoreCase(
				cashQ.getGchRtCode() + "-" + cashQ.getGchRcCode() + "-" + cashQ.getGchRscCode() ) ){
			sumInsuredVO = new SumInsuredVO();
			/* SK : Changes */
			/*
			 * Query to premium table has been changed as original query was always getting same result since it was not forming
			 * a the key for the records being fetched
			 */
			TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) getHibernateTemplate().find( "from TTrnPremiumQuo where id.prmPolicyId=? and id.prmBasicRskCode=? and id.prmBasicRskId=? and prmRiRskCode=? " +
					"and prmRtCode=? and prmRcCode=? and prmRscCode=? and id.prmRskId = ? ", cashQ.getGchPolicyId(), cashQ.getGchBasicRskCode(), BigDecimal.valueOf(cashQ.getGchBasicRiskId()), cashQ.getGchRiRskCode(), 
					cashQ.getGchRtCode().intValue(), cashQ.getGchRcCode().intValue(), cashQ.getGchRscCode().intValue(),  BigDecimal.valueOf( cashQ.getId().getGchId() ) ).get( 0 );
			sumInsuredVO.setDeductible( premiumQuo.getPrmCompulsoryExcess().doubleValue());
			sumInsuredVO.setSumInsured( cashQ.getGchSumInsured().doubleValue() );
			sumInsuredVO.setCash_Id( cashQ.getId().getGchId() );
		}
		return sumInsuredVO;
	}
	
	/**
	 * This method will populate Contents with the values obtained from
	 * T_TRN_GACC_CASH_CUSTOMER_QUO table
	 * @param cashQ(TTrnGaccCashQuo)
	 * @return
	 */
	private Contents getContentsVO( TTrnGaccCashQuo cashQ, Flow flow ){
		
		if(Utils.isEmpty( cashQ )) return null;
		
		Contents content = null;
		boolean addCurrentContent =  true;
		/* RiskType-RiskCategory-RiskSubCategory (5-0-0) is an entry for cash in residence hence 
		 *  should not be part of SumInsuredDets List or Contents List to be displayed */
		if(Utils.getSingleValueAppConfig( "MONEY_RISK_TYPES_7" ).equalsIgnoreCase( cashQ.getGchRtCode() + "-" + cashQ.getGchRcCode() + "-" + cashQ.getGchRscCode() ) ){
			addCurrentContent = false;
		}
		// For renewals flow add cash in resident contents also to the money content list as it is only used for updating premium , NO UI involved
		if(flow.equals( Flow.RENEWAL)){
			addCurrentContent = true;
		}
		//if( !Utils.getSingleValueAppConfig( "MONEY_RISK_TYPES_7" ).equalsIgnoreCase( cashQ.getGchRtCode() + "-" + cashQ.getGchRcCode() + "-" + cashQ.getGchRscCode() ) ){
		if( addCurrentContent){
			/* SK : Changes */
			/*
			 * Query to premium table has been changed as original query was always getting same result since it was not forming
			 * a the key for the records being fetched
			 */
			TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) getHibernateTemplate().find(
					"from TTrnPremiumQuo where id.prmPolicyId=? and id.prmBasicRskCode=? and id.prmBasicRskId=? and prmRiRskCode=? "
							+ "and prmRtCode=? and prmRcCode=? and prmRscCode=? and id.prmRskId = ? ", cashQ.getGchPolicyId(), cashQ.getGchBasicRskCode(),
					BigDecimal.valueOf( cashQ.getGchBasicRiskId() ), cashQ.getGchRiRskCode(), cashQ.getGchRtCode().intValue(), cashQ.getGchRcCode().intValue(),
					cashQ.getGchRscCode().intValue(), BigDecimal.valueOf( cashQ.getId().getGchId() ) ).get( 0 );
			if( !Utils.isEmpty( premiumQuo ) ){
				content = new Contents();
				content.setCover( cashQ.getGchSumInsured() );
				content.setDeductibles( premiumQuo.getPrmCompulsoryExcess() );
				content.setRiskType( premiumQuo.getPrmRtCode() );
				content.setRiskCat( premiumQuo.getPrmRcCode() );
				content.setRiskSubCat( premiumQuo.getPrmRscCode() );
				content.setRiskId( premiumQuo.getId().getPrmRskId().longValue() );
				/* SK : Changes */
				/* Update premium for each of the contents and also add up the same to obtain total location premium */
				PremiumVO prmVO = new PremiumVO();
				if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
					// Renewals will call the rating again to set the premium
					if(!flow.equals( Flow.RENEWAL)){
						prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
					}
					content.setPremium( prmVO );
					if(flow.equals( Flow.RENEWAL)){
						content.setCoverCode( Integer.valueOf( Short.toString(premiumQuo.getId().getPrmCovCode())) );
					}
				}

			}
		}
		return content;
	}
}