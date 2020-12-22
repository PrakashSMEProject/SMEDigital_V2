/**
 * 
 */
package com.rsaame.pas.pa.svc;

import java.util.LinkedHashMap;
import java.util.List;
import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author  Raman Shankar
 * @since Phase 4 generalization
 */
public class PersonalAccidentSvc extends BaseService{

	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( PersonalAccidentSvc.class );
	private PersonalDetailsSvc personalDetailsBean;
	private PremiumSaveCommonSvc premiumSvc;

	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
	
		BaseVO returnValue = null;
		
		/*CommonVO commonVO = null;
		if( args[ 0 ] instanceof PolicyDataVO ){
			commonVO = ( (PolicyDataVO) args[ 0 ] ).getCommonVO();
		}*/

		if( SvcConstants.SAVE_PERSONAL_ACCIDENT.equals( methodName ) ){
			PolicyDataVO policyData = (PolicyDataVO) (BaseVO) args[ 0 ];
			
				returnValue = (BaseVO) savePersonAccidentDetails( policyData);
			}
		
		
		if (SvcConstants.LOAD_PERSONAL_ACCIDENT.equals(methodName)) {
			PolicyDataVO polData = (PolicyDataVO) (BaseVO) args[ 0 ];
			returnValue = (BaseVO) loadPersonAccidentDetails(polData);
		}
		
		
		return returnValue;
	}
	

		
	/**
	 * @param input
	 * @param baseVO 
	 * @return
	 * This method is used to save the PersonAccident Details at the backend
	 */
	@SuppressWarnings( "rawtypes" )
	private BaseVO savePersonAccidentDetails( BaseVO input ){
		BaseVO baseVO= null;
		
		//-- invoke save personal details for PA
		baseVO =  (BaseVO) personalDetailsBean.invokeMethod(SvcConstants.SAVE_PERSONAL_ACCIDENT_INFO, input);
		
		
		//-- invoke save premium details for PA
		baseVO =  (BaseVO) premiumSvc.invokeMethod(SvcConstants.SAVE_PA_PREMIUM, baseVO);
		
		//-- calling issue quote
		DAOUtils.callUpdateStatusProcedureForIssueQuote((PolicyDataVO)baseVO);
		
		return baseVO;
	}
		
	
	
	
	/**
	 * @param input
	 * @param baseVO 
	 * @return
	 * This method is used to load the PersonAccident Details at the backend
	 */
	@SuppressWarnings( "rawtypes" )
	private BaseVO loadPersonAccidentDetails( BaseVO input ){
	
		BaseVO baseVO= null;
		
		return baseVO;
	}



	/**
	 * @return the personalDetailsBean
	 */
	public PersonalDetailsSvc getPersonalDetailsBean() {
		return personalDetailsBean;
	}



	/**
	 * @param personalDetailsBean the personalDetailsBean to set
	 */
	public void setPersonalDetailsBean(PersonalDetailsSvc personalDetailsBean) {
		this.personalDetailsBean = personalDetailsBean;
	}


	/**
	 * @param premiumSvc the premiumSvc to set
	 */
	public void setPremiumSvc(PremiumSaveCommonSvc premiumSvc) {
		this.premiumSvc = premiumSvc;
	}



	/**
	 * @return the premiumSvc
	 */
	public PremiumSaveCommonSvc getPremiumSvc() {
		return premiumSvc;
	}





	
	
}
