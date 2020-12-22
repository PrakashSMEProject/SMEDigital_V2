/**
 * 
 */
package com.rsaame.pas.com.helper;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.VMasProductConfigPas;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1020204
 * 
 */
public class DeriveGaccPersonDetails extends BaseDervieDetails{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#updateValues(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 * 
	 * Method to execute updates before gacc_person_id sequence getting generated
	 */
	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){
		
		//Getting the TTrnPolicyQuo from ThreadLevelContext
		TTrnPolicyQuo trnPolicyQuo = (TTrnPolicyQuo) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_REC );
		Integer policyType = null;
		Integer tarCode = null;

		if(!Utils.isEmpty( trnPolicyQuo ) && !Utils.isEmpty( trnPolicyQuo.getPolTarCode() ) && !Utils.isEmpty( trnPolicyQuo.getPolPolicyType() )){
			policyType = Integer.valueOf( trnPolicyQuo.getPolPolicyType() );
			tarCode = Integer.valueOf(trnPolicyQuo.getPolTarCode());
			
			
			if(!Utils.isEmpty( policyType ) && !Utils.isEmpty( tarCode ) ){
				
				String[] paramNames = {  "tariff", "policyType" };
				Object[] values = null ;
				///if(!Utils.isEmpty(ht.findByNamedQueryAndNamedParam( "getRiskDetails", paramNames, values ))){
		//		if(!Utils.isEmpty(Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" ))||!Utils.isEmpty( Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ))){
				if(policyType.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" )) ||
						policyType.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ))){
					values =  new Object[]{ tarCode, 6 };
				}else{
					values = new Object[]{ tarCode, policyType };
				}
				
		//		}
				
				
				List<VMasProductConfigPas> vMasProductConfigPasList= (List<VMasProductConfigPas>) ht.findByNamedQueryAndNamedParam( "getRiskDetails", paramNames, values );
				
				
				if(!Utils.isEmpty( vMasProductConfigPasList )){
					VMasProductConfigPas vMasProductConfigPas = vMasProductConfigPasList.get( 0 );
						
						TTrnGaccPersonQuo trnGaccPersonQuo = (TTrnGaccPersonQuo) mappedPojo;
						trnGaccPersonQuo.setGprRtCode( vMasProductConfigPas.getPcRtCode().longValue() );
				}
				
				//}
			}
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#preprocessRecord(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 * 
	 * Method to execute updates after gacc_person_id sequence getting generated
	 */
	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO , SaveCase saveCase){
		
		//updating basic risk id with gpr id before saving
		TTrnGaccPersonQuo personQuo = (TTrnGaccPersonQuo) mappedPojo;
		if(!Utils.isEmpty( existingRecord )){
			TTrnGaccPersonQuo existingPersonQuo = (TTrnGaccPersonQuo) existingRecord;
			if(!Utils.isEmpty(existingPersonQuo.getGprRtCode())){
				personQuo.setGprRtCode( existingPersonQuo.getGprRtCode() );
			}
			if(!Utils.isEmpty(existingPersonQuo.getGprSumInsured())){
				personQuo.setGprSumInsured( existingPersonQuo.getGprSumInsured() );
			}
		}
		personQuo.setGprBasicRiskId( personQuo.getId().getGprId() );
		
	}

}
