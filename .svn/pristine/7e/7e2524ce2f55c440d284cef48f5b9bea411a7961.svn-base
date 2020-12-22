package com.rsaame.pas.wc.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnWctplPersonQuo;
import com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.WCCoversVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;

public class WCSectionLoadDAO extends BaseSectionLoadDAO  {


	@Override
	protected RiskGroupDetails getRiskDetails(RiskGroup riskGroup,
			Long policyId, Long endId,LoadExistingInputVO lei) {
		
		WCVO wcvo = new WCVO();	
		com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO> empTypeDetails = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(EmpTypeDetailsVO.class);
		Boolean paCovCode = null;
		Double totalLocPrm = 0.0;
		double totalLocSi = 0;
		
		List<TTrnWctplUnnamedPersonQuo> unnamedPersonQuoList=null;
		unnamedPersonQuoList = getTTrnWctplUnnamedPersonQuoData(riskGroup, policyId, endId, lei);
		
		
		List<TTrnWctplPersonQuo> personQuoList=null;
		personQuoList = getTTrnWctplPersonQuoData(riskGroup, policyId, endId, lei);
		
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(),endId,lei.getPolLinkingId(),lei.isQuote() );
		
		for( TTrnWctplUnnamedPersonQuo tTrnWctplUnnamedPersonQuo : unnamedPersonQuoList ){
			
			EmpTypeDetailsVO empTypeVO = new EmpTypeDetailsVO();
			/* Populate EmpTypeDetailsVO data from TTrnWctplUnnamedPersonQuo table data*/
			empTypeVO= BeanMapper.map( tTrnWctplUnnamedPersonQuo, empTypeVO );
			TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
			
			/** 
			 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
			 * 
			 */
			
			premiumQuo = getEndtStatePremium(validityStartDate,policyId,endId,riskGroup,lei,tTrnWctplUnnamedPersonQuo);
			
			if(Utils.isEmpty( premiumQuo )){
				throw new BusinessException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Data retrieved for Premium/Quo table is null for [ "+ riskGroup.getRiskGroupId() + " ] risk group id " +
						" and [" + policyId + " ] policy id " );
			}			
			empTypeVO = BeanMapper.map( premiumQuo, empTypeVO );			
			
			empTypeDetails.add( empTypeVO );
			if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
			/* Calculate section level premium to populate in WCVO */
			totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
			
			/* Calculate section level sum insured to populate in WCVO */
			totalLocSi += premiumQuo.getPrmSumInsured().doubleValue();
			}
			/* Take Cover code to set it into WCVO, from any one content as it will be same for all contents/employee list  */
			if(premiumQuo.getId().getPrmCovCode() == 1){
				paCovCode = true ;
			}else{
				paCovCode = false ;
			}
			
		}
		
		wcvo.setEmpTypeDetails( empTypeDetails );
		
		WCNammedEmployeeVO namedEmployee = null;
		List<WCNammedEmployeeVO> nammedEmployeesList = null;
		
		if (!Utils.isEmpty(personQuoList)) 
		{
			nammedEmployeesList = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(WCNammedEmployeeVO.class);
			for (TTrnWctplPersonQuo personQuo : personQuoList) 
			{
				
				namedEmployee = new WCNammedEmployeeVO();
				namedEmployee.setEmpName(personQuo.getWprEName());
				namedEmployee.setWprWCId(personQuo.getId().getWprId());
		
				nammedEmployeesList.add(namedEmployee);
			}
			wcvo.setWcEmployeeDetails(nammedEmployeesList);

		}
		
		
		
		WCCoversVO wcCovers = new WCCoversVO();
		wcCovers.setPACover( paCovCode );
		wcvo.setWcCovers( wcCovers );

		wcvo.setPolicyId( policyId );	
		
		wcvo.setSumInsured( totalLocSi );
		
		/* Set section level premium calculated in above content iteration */
		PremiumVO prmVO = new PremiumVO();
		//Added for Bahrain 3 decimal - Starts
		 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
				prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
		 }else {
					 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
		 }		 
		//Added for Bahrain 3 decimal - Ends
		wcvo.setPremium(prmVO);
		
		return wcvo;
	}

	private List<TTrnWctplPersonQuo> getTTrnWctplPersonQuoData(
			RiskGroup riskGroup, Long policyId, Long endId,
			LoadExistingInputVO lei) {
		
		List<TTrnWctplPersonQuo> personQuoList=null;
		
		/** SK : Changes */
		/** Query to TTrnWctplPerson table has been changed as Basic Risk Id will not be
		 *  holding building/premise id rather wup_bld_id will hold the same */
		
		/*
		 *
		 *
		 * */
		/** 
		 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
		 * 
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(),  endId,lei.getPolLinkingId(),lei.isQuote() );
		personQuoList = getEndtStatePerson(validityStartDate,policyId,endId,riskGroup,lei);
		


		/** Do not throw Business exception in case named person list obtained is null */
/*		if(Utils.isEmpty( personQuoList )){
			throw new BusinessException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Data retrieved for TTrnWctplUnnamedPerson/Quo table is null for [ "+ riskGroup.getRiskGroupId() + " ] risk group id" );
		}*/
		
		return personQuoList;
	}

	private List<TTrnWctplPersonQuo> getEndtStatePerson(Date validityStartDate,
			Long policyId, Long endId, RiskGroup riskGroup,
			LoadExistingInputVO lei) {
	
		List<TTrnWctplPersonQuo> personQuoList=null;
		
		if( !Utils.isEmpty( validityStartDate ) ){
			
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
				personQuoList = getHibernateTemplate().find(
					"from TTrnWctplPersonQuo where wprPolicyId = ? and id.wprValidityStartDate <= ?" +
					com.Constant.CONST_AND_END + " wprValidityExpiryDate > ?" +
					" and wprEndtId = ? and " +
					" (wprBldId,id.wprId) in " +
					"( select max(w.wprBldId),w.id.wprId from TTrnWctplPersonQuo w where w.wprPolicyId=? and w.wprBldId = ? group by w.id.wprId)", policyId,validityStartDate,validityStartDate
					,endId,policyId,Long.valueOf( riskGroup.getRiskGroupId()));
			
			}else{
				personQuoList = getHibernateTemplate().find(
						"from TTrnWctplPersonQuo where wprPolicyId = ? and id.wprValidityStartDate <= ?" +
						com.Constant.CONST_AND_END + " wprValidityExpiryDate > ?" +
						" and wprEndtId <= ? and wprStatus <> 4 and " +
						" wprBldId = ? order by id.wprId", policyId,validityStartDate,validityStartDate
						,endId,Long.valueOf( riskGroup.getRiskGroupId()));
				
			}
		
		}
		return personQuoList;
	}

	/**
	 * 
	 * @param validityStartDate
	 * @param policyId
	 * @param endId
	 * @param riskGroup
	 * @param lei
	 * @param tTrnWctplUnnamedPersonQuo
	 * @return
	 */
	private TTrnPremiumQuo getEndtStatePremium( Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei,
			TTrnWctplUnnamedPersonQuo tTrnWctplUnnamedPersonQuo ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		if( !Utils.isEmpty( validityStartDate ) ){
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
				premiumQuo = (TTrnPremiumQuo) getHibernateTemplate().find(
						"from TTrnPremiumQuo prmQuo where prmQuo.id.prmPolicyId=? and prmQuo.id.prmRskId = ? and prmQuo.id.prmRskCode = ? "
								+ " and  prmQuo.id.prmValidityStartDate <= ? and  prmQuo.prmValidityExpiryDate > ?  and prmQuo.prmEndtId =?", policyId,
						new BigDecimal( tTrnWctplUnnamedPersonQuo.getId().getWupId() ), tTrnWctplUnnamedPersonQuo.getWupRskCode().intValue(), validityStartDate, validityStartDate,
						endId ).get( 0 );
			}
			else{

				premiumQuo = (TTrnPremiumQuo) getHibernateTemplate().find(
						"from TTrnPremiumQuo prmQuo where prmQuo.id.prmPolicyId=? and prmQuo.id.prmRskId = ? and prmQuo.id.prmRskCode = ? "
								+ " and  prmQuo.id.prmValidityStartDate <= ? and  prmQuo.prmValidityExpiryDate > ?  " + com.Constant.CONST_AND_END + " prmQuo.prmEndtId <=? and prmQuo.prmStatus<>4",
						policyId, new BigDecimal( tTrnWctplUnnamedPersonQuo.getId().getWupId() ), tTrnWctplUnnamedPersonQuo.getWupRskCode().intValue(), validityStartDate,
						validityStartDate, endId ).get( 0 );
			}
		}
		return premiumQuo;
	}

	/**
	 * Fetches records from TTrnWctplUnnamedPersonQuo
	 * @param riskGroup
	 * @param policyId
	 * @param endId
	 * @param lei
	 * @return
	 */
	private List<TTrnWctplUnnamedPersonQuo> getTTrnWctplUnnamedPersonQuoData( RiskGroup riskGroup, Long policyId, Long endId,LoadExistingInputVO lei ){
		
		List<TTrnWctplUnnamedPersonQuo> unnamedPersonQuoList=null;
		
		/** SK : Changes */
		/** Query to TTrnWctplUnnamedPerson table has been changed as Basic Risk Id will not be
		 *  holding building/premise id rather wup_bld_id will hold the same */
		
		/*
		 * select * from t_trn_wctpl_unnamed_person where (wup_endt_id , wup_policy_id , wup_bld_id ) in 
		(select max (wup_endt_id),wup_policy_id , wup_bld_id from  t_trn_wctpl_unnamed_person where wup_endt_id <= 0
		and wup_policy_id = 4513837 and wup_bld_id =  409317 GROUP BY wup_endt_id , wup_policy_id , wup_bld_id , wup_basic_risk_id ) 
		and ( ( wup_endt_id =0 and wup_status <> 4  ) or ( wup_endt_id < 0 and wup_validity_expiry_date ='31-DEC-2049
		 */
		/** 
		 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
		 * 
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(),  endId,lei.getPolLinkingId(),lei.isQuote() );
		unnamedPersonQuoList = getEndtStateUnnamedPerson(validityStartDate,policyId,endId,riskGroup,lei);
		

		/** Throw Business exception in case unnamed person list obtained is null */
		if(Utils.isEmpty( unnamedPersonQuoList )){
			throw new BusinessException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Data retrieved for TTrnWctplUnnamedPerson/Quo table is null for [ "+ riskGroup.getRiskGroupId() + " ] risk group id" );
		}
		
		return unnamedPersonQuoList;
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
	private List<TTrnWctplUnnamedPersonQuo> getEndtStateUnnamedPerson( Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei ){
		
		
		List<TTrnWctplUnnamedPersonQuo> unnamedPersonQuoList=null;
		
		if( !Utils.isEmpty( validityStartDate ) ){
			
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
			unnamedPersonQuoList = getHibernateTemplate().find(
					"from TTrnWctplUnnamedPersonQuo where wupPolicyId = ? and id.wupValidityStartDate <= ?" +
					com.Constant.CONST_AND_END + " wupValidityExpiryDate > ?" +
					" and wupEndtId = ? and " +
					" (wupBldId,id.wupId) in " +
					"( select max(w.wupBldId),w.id.wupId from TTrnWctplUnnamedPersonQuo w where w.wupPolicyId=? and w.wupBldId = ? group by w.id.wupId)", policyId,validityStartDate,validityStartDate
					,endId,policyId,Long.valueOf( riskGroup.getRiskGroupId()));
			
			}else{
				unnamedPersonQuoList = getHibernateTemplate().find(
						"from TTrnWctplUnnamedPersonQuo where wupPolicyId = ? and id.wupValidityStartDate <= ?" +
						com.Constant.CONST_AND_END + " wupValidityExpiryDate > ?" +
						" and wupEndtId <= ? and wupStatus <> 4 and " +
						" wupBldId = ? order by id.wupId", policyId,validityStartDate,validityStartDate
						,endId,Long.valueOf( riskGroup.getRiskGroupId()));
				
			}
		
		}
		return unnamedPersonQuoList;
	}
	
	

}
