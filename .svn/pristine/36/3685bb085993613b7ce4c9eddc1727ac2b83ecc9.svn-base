package com.rsaame.pas.tb.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
/**
 * 
 * @author m1017935
 * This Dao load for the travel section
 *
 */
@SuppressWarnings( "unused" )
public class TravelBaggageLoadDAO extends BaseSectionLoadDAO{

	
	private final static Short TB_COV_CODE = 1;
	private final static Short TB_CT_CODE = 0;
	private final static Short TB_CST_CODE =0 ;
	private final static Integer TB_RISK_CODE = 27;
	private final static Integer ZERO_CONSTANT = 0;
	@SuppressWarnings( "unchecked" )
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){
		
		LocationVO locationVO = (LocationVO) riskGroup;
		
		/* fetch the Travel Baggage details for each riskGroup */
		TravelBaggageVO travelBaggageVO = new TravelBaggageVO();
		TravellingEmployeeVO travellingEmployeeVO =null;
		Integer index=0;
		TTrnPremiumQuo tTrnPremiumQuo  = null;
		List<TTrnGaccPersonQuo> tTrnGaccPersonQuoList = null;
		
		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		
		/* Fetch the GaccPerson details from the TtrnGaccPerson table where riskId is mapped as GRP_BLD_id */
		if (lei.getAppFlow().equals(Flow.VIEW_POL) || lei.getAppFlow().equals(Flow.VIEW_QUO)) 
		{
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
						+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
						+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId )",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),TB_RISK_CODE.longValue());*/
				
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId = ? and gprBldId = ? and gprRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),TB_RISK_CODE.longValue());
			}
			else
			{
				/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
						+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
						+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId )  and namedPerson.gprStatus <> 4",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),TB_RISK_CODE.longValue());*/
				
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId <= ? and gprStatus <> 4 and gprBldId = ? and gprRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf(TB_RISK_CODE) );
			}

		} 
		else 
		{
			tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_PERSON_LOAD_TRAVEL",lei.getAppFlow(), getHibernateTemplate(), false,
                			    endId, policyId,Long.valueOf(locationVO.getRiskGroupId()), Long.valueOf(TB_RISK_CODE));
		}
		//List<TTrnGaccPersonQuo> tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>)DAOUtils.getTableSnapshotQuery( "T_TRN_GACC_PERSON_LOAD", lei.getAppFlow(), getHibernateTemplate(),false,endId,policyId, Long.valueOf( locationVO.getRiskGroupId() ) );
		
		//List<TTrnGaccPersonQuo> tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>)getHibernateTemplate().find( " from TTrnGaccPersonQuo gpr where gpr.gprPolicyId = ? and gpr.gprBldId = ? and gpr.gprEndtId=?",policyId ,Long.valueOf( locationVO.getRiskGroupId() ),endId  );
		if(tTrnGaccPersonQuoList.size() == ZERO_CONSTANT && lei.getAppFlow().equals( Flow.VIEW_POL )){
			tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>)getHibernateTemplate().find( " from TTrnGaccPersonQuo gpr where gpr.gprPolicyId = ? and gpr.gprBldId = ? and gpr.gprEndtId <= ? and gpr.gprStatus <> 4 and gpr.gprValidityExpiryDate = ?",policyId ,Long.valueOf( locationVO.getRiskGroupId() ),endId,SvcConstants.EXP_DATE );
		}
		if(!Utils.isEmpty( tTrnGaccPersonQuoList )){
			index = tTrnGaccPersonQuoList.size();
		}
		travelBaggageVO.setIndex( index );
		//DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_HBM_T_TRN_GACC_PERSON_LOAD, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,Long.valueOf( locationVO.getRiskGroupId() )  );
		
		// Get the premium based on basic risk Id for the added one
		List<TTrnPremiumQuo> tTrnPremiumQuoList = null;
		
		tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId= ? and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ? and prm.prmEndtId <= ? and prm.prmStatus <> 4 and prm.prmValidityExpiryDate = ?", policyId,
				new BigDecimal(locationVO.getRiskGroupId()),TB_RISK_CODE,endId,SvcConstants.EXP_DATE);
		
		if(Utils.isEmpty(tTrnPremiumQuoList) && (lei.getAppFlow().equals( Flow.AMEND_POL ) || lei.getAppFlow().equals(Flow.EDIT_QUO))){
			tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId= ? and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ?  and prm.prmStatus <> 4 and prm.prmValidityExpiryDate = ?", policyId,
					new BigDecimal(locationVO.getRiskGroupId()),TB_RISK_CODE,SvcConstants.EXP_DATE);
		}
		if(!Utils.isEmpty(tTrnPremiumQuoList)){
			tTrnPremiumQuo = tTrnPremiumQuoList.get( 0 );
		}
		for(TTrnGaccPersonQuo tTrnGaccPersonQuo : tTrnGaccPersonQuoList){
			
			/* map tTrnGaccPersonQuo to travelling employeeVO */
			 travellingEmployeeVO = new TravellingEmployeeVO();
			travellingEmployeeVO.setName( tTrnGaccPersonQuo.getGprEName() );
			travellingEmployeeVO.setDateOfBirth( tTrnGaccPersonQuo.getGprDateOfBirth().toString() );
			travellingEmployeeVO.setGprId( String.valueOf(tTrnGaccPersonQuo.getId().getGprId()) );
			
			
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			

			/* fetch the premium record based on tTrnGaccPersonQuo.gprid  and map to premium.basicriskId */
			if(!Utils.isEmpty( tTrnPremiumQuo )){
				if(!Utils.isEmpty( tTrnPremiumQuo.getPrmCompulsoryExcess() )){
					sumInsuredVO.setDeductible( tTrnPremiumQuo.getPrmCompulsoryExcess().doubleValue() );
				}
			}	
			  
			  if(!Utils.isEmpty( tTrnGaccPersonQuo.getGprSumInsured() )){
				  sumInsuredVO.setSumInsured(tTrnGaccPersonQuo.getGprSumInsured().doubleValue());
			  }	
			travellingEmployeeVO.setSumInsuredDtl( sumInsuredVO );
			
			travellingEmployeeVO.setIndex( index );
			index=index-1;
			travelBaggageVO.getTravellingEmpDets().add( travellingEmployeeVO );
			
		}
		Double sumInsured =0.0;
		for(TravellingEmployeeVO travellingEmployee :travelBaggageVO.getTravellingEmpDets()){
			sumInsured = sumInsured +travellingEmployee.getSumInsuredDtl().getSumInsured();
		}
		travelBaggageVO.setSumInsured( sumInsured );
		
		PremiumVO prmVO = new PremiumVO();
		if(!Utils.isEmpty( tTrnPremiumQuo )){
			if(!Utils.isEmpty( tTrnPremiumQuo.getPrmPremium() )){
				//Added for Bahrain 3 decimal - Starts
				 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
					 prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( tTrnPremiumQuo.getPrmPremium().doubleValue() ) ) );
				 }
				 else {
				prmVO.setPremiumAmt( Double.valueOf( decForm.format( tTrnPremiumQuo.getPrmPremium().doubleValue() ) ) );
				 }
				//Added for Bahrain 3 decimal - Ends
			}
		}	
		travelBaggageVO.setPremium( prmVO );
		return travelBaggageVO;
	}
	
	
}
