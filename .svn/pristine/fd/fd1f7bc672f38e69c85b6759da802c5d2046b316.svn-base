package com.rsaame.pas.gpa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

public class GroupPersonalAccidentLoadDAO extends BaseSectionLoadDAO implements IGroupPersonalAccidentSectionDAO{

	
	private final static Logger LOGGER = Logger.getLogger( GroupPersonalAccidentLoadDAO.class );
	private final static Short GROUP_PERSONAL_ACCIDENT_COVER_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_COVER_CODE" ) );
	private final static Short GROUP_PERSONAL_ACCIDENT_COVER_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_COVER_TYPE" ) );
	private final static Short GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE" ) );
	private final static Integer GPA_RISK_CODE = 28;
	@SuppressWarnings("unchecked")
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){
		
		LOGGER.info("GroupPersonalAccident : Inside Load Section");
		LocationVO locationVO = (LocationVO) riskGroup;
		// fetch the Travel Baggage details for each riskGroup 
		List<GPAUnnammedEmpVO> gpaUnnammedEmpVOs=new  ArrayList<GPAUnnammedEmpVO>();
		List<GPANammedEmpVO> gpaNammedEmpVOs=new  ArrayList<GPANammedEmpVO>();
		GroupPersonalAccidentVO groupPersonalAccidentVO = new GroupPersonalAccidentVO();
		GPANammedEmpVO gpaNammedEmpVO =null;
		GPAUnnammedEmpVO gpaUnnammedEmpVO =null;
		
		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		
		// Fetch the GaccUnnamedPerson details from the TtrnGaccPerson table where riskId is mapped as GRP_BLD_id 
		List<TTrnGaccUnnamedPersonQuo> tTrnGaccUnnamedPersonQuoList = null;
		if (lei.getAppFlow().equals(Flow.VIEW_POL)|| lei.getAppFlow().equals(Flow.VIEW_QUO)) 
		{
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*tTrnGaccUnnamedPersonQuoList = getHibernateTemplate().find("from TTrnGaccUnnamedPersonQuo unamedPerson where (gupEndtId, id.gupId, gupPolicyId, gupBldId,gupRskCode) in "
						+ "( select max ( gupEndtId ),id.gupId , gupPolicyId, gupBldId,gupRskCode from TTrnGaccUnnamedPersonQuo where gupEndtId <= ? and gupPolicyId= ? "
						+ "and gupBldId= ? and gupRskCode=? group by gupPolicyId,  gupBldId,gupRskCode ,id.gupId ) ",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()), GPA_RISK_CODE);*/
				
				tTrnGaccUnnamedPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccUnnamedPersonQuo where gupPolicyId = ? and id.gupValidityStartDate <= ? and " + " gupValidityExpiryDate > ? and gupEndtId = ? and gupBldId = ? and gupRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),GPA_RISK_CODE );
			}
			else
			{
				/*tTrnGaccUnnamedPersonQuoList = getHibernateTemplate().find("from TTrnGaccUnnamedPersonQuo unamedPerson where (gupEndtId, id.gupId, gupPolicyId, gupBldId,gupRskCode) in "
						+ "( select max ( gupEndtId ),id.gupId , gupPolicyId, gupBldId,gupRskCode from TTrnGaccUnnamedPersonQuo where gupEndtId <= ? and gupPolicyId= ? "
						+ "and gupBldId= ? and gupRskCode=? group by gupPolicyId,  gupBldId,gupRskCode ,id.gupId )  and unamedPerson.gupStatus <> 4",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()), GPA_RISK_CODE);*/
				
				tTrnGaccUnnamedPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccUnnamedPersonQuo where gupPolicyId = ? and id.gupValidityStartDate <= ? and " + " gupValidityExpiryDate > ? and gupEndtId <= ? and gupStatus <> 4 and gupBldId = ? and gupRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),GPA_RISK_CODE );
			}

		} 
		else 
		{
			tTrnGaccUnnamedPersonQuoList = (List<TTrnGaccUnnamedPersonQuo>)DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON_LOAD, lei.getAppFlow(), getHibernateTemplate(),false,endId, policyId, Long.valueOf( locationVO.getRiskGroupId()), GPA_RISK_CODE);
		}
		if(!Utils.isEmpty( tTrnGaccUnnamedPersonQuoList )){
			LOGGER.info("GroupPersonalAccident : Size of Unnnamed Employee details: tTrnGaccUnnamedPersonQuoList :" + tTrnGaccUnnamedPersonQuoList.size());
			
			for(int i=0; i < tTrnGaccUnnamedPersonQuoList.size(); ++i){
							
				gpaUnnammedEmpVO = new GPAUnnammedEmpVO();
				SumInsuredVO sumInsuredvo = new SumInsuredVO();
				
				gpaUnnammedEmpVO.setUnnammedEmployeeType( tTrnGaccUnnamedPersonQuoList.get( i ).getGupOcCode() );
				gpaUnnammedEmpVO.setUnnammedNumberOfEmloyee( tTrnGaccUnnamedPersonQuoList.get( i ).getGupNoOfPerson());
				gpaUnnammedEmpVO.setGupId( String.valueOf(tTrnGaccUnnamedPersonQuoList.get( i ).getId().getGupId()) );
				gpaUnnammedEmpVO.setUnnammedAnnualSalary( (double)tTrnGaccUnnamedPersonQuoList.get( i ).getGupSalary().longValue());
				groupPersonalAccidentVO.setAggregateLimit( (double)tTrnGaccUnnamedPersonQuoList.get( i ).getGupAgrLmt().longValue());
				try{
					TTrnPremiumQuo tTrnPremiumQuo = (TTrnPremiumQuo)DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
												new BigDecimal(tTrnGaccUnnamedPersonQuoList.get( i ).getId().getGupId()),new BigDecimal(tTrnGaccUnnamedPersonQuoList.get( i ).getGupBldId()),GROUP_PERSONAL_ACCIDENT_COVER_CODE,GROUP_PERSONAL_ACCIDENT_COVER_TYPE,GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE);
				
						if(!Utils.isEmpty( tTrnPremiumQuo.getPrmSumInsured() )){
							sumInsuredvo.setSumInsured(tTrnPremiumQuo.getPrmSumInsured().doubleValue());
							
						}	
						if(!Utils.isEmpty( tTrnPremiumQuo.getPrmCompulsoryExcess() )){
							groupPersonalAccidentVO.setGpaDeductible( tTrnPremiumQuo.getPrmCompulsoryExcess().doubleValue() );
						}
				
				}catch(BusinessException E){
					LOGGER.debug("BusinessException : "+E.getMessage()); /*Added logger statement - sonar violation fix */
				}
				
				gpaUnnammedEmpVO.setSumInsuredDetails( sumInsuredvo );
												
				gpaUnnammedEmpVOs.add( gpaUnnammedEmpVO );
				}
				
			groupPersonalAccidentVO.setGpaUnnammedEmpVO( gpaUnnammedEmpVOs );		
		} 
				
	/***************************************************************************************************************/
		
		
		// Fetch the GaccPerson details from the TtrnGaccPerson table where riskId is mapped as GRP_BLD_id
		List<TTrnGaccPersonQuo> tTrnGaccPersonQuoList = null;
		if (lei.getAppFlow().equals(Flow.VIEW_POL)|| lei.getAppFlow().equals(Flow.VIEW_QUO)) 
		{
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
						+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
						+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId ) order by id.gprId",
						endId,policyId,Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),GPA_RISK_CODE.longValue());*/
				
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId <= ? and gprBldId = ? and gprRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( ( GPA_RISK_CODE ) ) );
			}
			else
			{
				/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
						+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
						+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId )  and namedPerson.gprStatus <> 4 order by id.gprId",
						endId,policyId,Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),GPA_RISK_CODE.longValue());*/
				
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId <= ? and gprStatus <> 4 and gprBldId = ? and gprRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( ( GPA_RISK_CODE ) ) );
			}
		} 
		else
		{
			tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>)DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON_GPA_LOAD, lei.getAppFlow(), getHibernateTemplate(),false,endId, policyId, Long.valueOf( locationVO.getRiskGroupId() ), Long.valueOf(GPA_RISK_CODE));
		}
		if(!Utils.isEmpty( tTrnGaccPersonQuoList )){
			LOGGER.info("GroupPersonalAccident : Size of Named Employee details: tTrnGaccPersonQuoList :" + tTrnGaccPersonQuoList.size());
			
			for(int i=0; i < tTrnGaccPersonQuoList.size(); ++i){	
					 
					gpaNammedEmpVO = new GPANammedEmpVO();
					SumInsuredVO sumInsuredvo = new SumInsuredVO();
					gpaNammedEmpVO.setNameOfEmployee( tTrnGaccPersonQuoList.get(i).getGprEName() );
					gpaNammedEmpVO.setNammedEmpDob( tTrnGaccPersonQuoList.get(i).getGprDateOfBirth().toString() );
					gpaNammedEmpVO.setGprId( String.valueOf(tTrnGaccPersonQuoList.get(i).getId().getGprId()) );
					gpaNammedEmpVO.setEmployeeType( Integer.parseInt( tTrnGaccPersonQuoList.get(i).getGprRtCode().toString()));
					gpaNammedEmpVO.setNamedEmpGender( tTrnGaccPersonQuoList.get(i).getGprGender());
					gpaNammedEmpVO.setNammedEmpAnnualSalary( (double)tTrnGaccPersonQuoList.get(i).getGprSalary().longValue() );
					gpaNammedEmpVO.setNammedEmpDesignation( tTrnGaccPersonQuoList.get(i).getGprAName());
					
					groupPersonalAccidentVO.setAggregateLimit( (double)tTrnGaccPersonQuoList.get( i ).getGprAgrLmt().longValue());
					
					// fetch the premium record based on tTrnGaccPersonQuo.gprid  and map to premium.basicriskId 
					
						TTrnPremiumQuo tTrnPremiumQuo = (TTrnPremiumQuo)DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
							new BigDecimal(tTrnGaccPersonQuoList.get(i).getId().getGprId()),new BigDecimal(tTrnGaccPersonQuoList.get(i).getGprBldId()),GROUP_PERSONAL_ACCIDENT_COVER_CODE,GROUP_PERSONAL_ACCIDENT_COVER_TYPE,GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE);
					
							if(!Utils.isEmpty( tTrnPremiumQuo.getPrmSumInsured() )){
								sumInsuredvo.setSumInsured(tTrnPremiumQuo.getPrmSumInsured().doubleValue());
							
							}	
							if(!Utils.isEmpty( tTrnPremiumQuo.getPrmCompulsoryExcess() )){
								groupPersonalAccidentVO.setGpaDeductible( tTrnPremiumQuo.getPrmCompulsoryExcess().doubleValue() );
							}
						
						gpaNammedEmpVO.setSumInsuredDetails( sumInsuredvo );
											
					gpaNammedEmpVOs.add( gpaNammedEmpVO );
				}
			groupPersonalAccidentVO.setGpaNammedEmpVO( gpaNammedEmpVOs );			
		}
		groupPersonalAccidentVO.setPolicyId(policyId);		
		return groupPersonalAccidentVO;
}
	
	@Override
	public BaseVO loadGroupPersonalAccidentSection( BaseVO baseVO ){
		
		return null;
	}

	@Override
	public BaseVO saveGroupPersonalAccidentSection( BaseVO baseVO ){
		
		return null;
	}


	
}
