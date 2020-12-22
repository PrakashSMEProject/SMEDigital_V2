package com.rsaame.pas.endorse.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPasReferralDetailsId;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * This DAO class is to save the consolidated referral messages
 * @author M1020859
 *
 */
public class PasReferralDetailsSaveDAO extends BaseDBDAO implements IPasReferralSave{

	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( PasReferralDetailsSaveDAO.class );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public TTrnPasReferralDetails savePasReferralData( BaseVO baseVO ){
		logger.debug( "TempPasReferralSaveDAO ----- > Inside saveTempPasRefData method" );
		TTrnPasReferralDetails pasReferralDetails = null;
		if( !Utils.isEmpty( baseVO ) && baseVO instanceof PolicyDataVO ){
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
			logger.debug( "TempPasReferralSaveDAO ----- > Going to call populateTempPasRefData" );
			pasReferralDetails = populateAndSavePasRefData( policyDataVO );
			logger.debug( "TempPasReferralSaveDAO ----- > Going to call saveReferralData" );
			//logger.debug("TempPasReferralSaveDAO ----- > Referral policy id is - "+pasReferralDetails.getId().getRefPolicyId());
		}
		return pasReferralDetails;

	}

	/**
	 * This method will populate the TTrnTempPasReferral object from PolicyDataVO
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private TTrnPasReferralDetails populateAndSavePasRefData( PolicyDataVO policyDataVO ){
		logger.debug( "TempPasReferralSaveDAO ----- > Populating TTrnPasReferralDetails from PolicyDataVO" );
		TTrnPasReferralDetails pasReferralDetails = null;
		TTrnPasReferralDetailsId pasReferralDetailsId = null;
		
		//CommonVO commonVO = policyDataVO.getCommonVO();
		
		logger.debug( "TempPasReferralSaveDAO ----- > Start setting the referral field wise data" );
		if (!Utils.isEmpty(policyDataVO.getReferralVOList().getReferrals().get(0).getRefDataTextField())) {
			for (String fieldName : policyDataVO.getReferralVOList().getReferrals().get(0).getRefDataTextField().keySet()) {
				Map<String, String> referralValues = policyDataVO.getReferralVOList().getReferrals().get(0).getRefDataTextField().get(fieldName);
				if( !Utils.isEmpty( referralValues ) ){
					for (Map.Entry<String, String> refParams : referralValues.entrySet()) {
						pasReferralDetails = (TTrnPasReferralDetails)Utils.getBean("pasReferralDetails");
						pasReferralDetailsId = (TTrnPasReferralDetailsId)Utils.getBean("pasReferralDetailsID");
						
						if( !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ) )
								&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getLocationCode() ) ){
							pasReferralDetailsId.setLocationId( new Long( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getLocationCode() ) );
						}
					/*	else{
							//Set the default location code
							pasReferralDetailsId.setLocationId( Long.valueOf( PASServiceContext.getLocation() ) );
						}*/
						
						if (Utils.isEmpty(policyDataVO.getCommonVO().getEndtId())) {
							// Radar fix
							pasReferralDetailsId.setRefEndId(Long.valueOf( 0 )); //Default value for saving
						} else {
							pasReferralDetailsId.setRefEndId(policyDataVO.getCommonVO().getEndtId());
						}

						pasReferralDetails.setRefStatus("20");
						pasReferralDetailsId.setSecId(Short.valueOf( Utils.getSingleValueAppConfig( policyDataVO.getCommonVO().getLob().toString()+"_SEC_ID" ) ) ); //TODO need to change
						if(!Utils.isEmpty(policyDataVO.getPolicyId()))
						{
							pasReferralDetailsId.setRefPolicyId(policyDataVO.getPolicyId());
							pasReferralDetailsId.setPolLinkingId(policyDataVO.getPolicyId());
						}
						else
						{
							pasReferralDetailsId.setRefPolicyId(policyDataVO.getCommonVO().getPolicyId());
							pasReferralDetailsId.setPolLinkingId(policyDataVO.getCommonVO().getPolicyId());
						}							
						pasReferralDetailsId.setRefField(fieldName);
						pasReferralDetailsId.setRefValue(refParams.getKey());
						pasReferralDetails.setRefText(refParams.getValue());
						
						pasReferralDetails.setRefCreatedBy(((UserProfile)policyDataVO.getLoggedInUser() ).getRsaUser().getUserId() );
						
						pasReferralDetails.setId(pasReferralDetailsId);
						
						pasReferralDetails.setRefModifiedDate( new Date() );
						pasReferralDetails.setRefCreatedDate( new Date() );
						
						getHibernateTemplate().saveOrUpdate(pasReferralDetails);
					}
					getHibernateTemplate().flush();
				}
				
			}
		}
		logger.debug( "TempPasReferralSaveDAO ----- > Populating TTrnPasReferralDetails completed" );
		return pasReferralDetails;
	}
	
	@Override
	public Boolean removeReferralData( BaseVO baseVO, List<String> factList ){
		List<TTrnPasReferralDetails> ttrnPasReferralDetails = null;
		
		CommonVO commonVO = (CommonVO) baseVO;
			
		ttrnPasReferralDetails = getHibernateTemplate().find(QueryConstants.FETCH_REF_DETAILS, SvcConstants.POL_STATUS_REFERRED.toString(), 
				commonVO.getPolicyId(), commonVO.getEndtId() );
		
		/*String query = QueryConstants.FETCH_REF_DETAILS;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery( query.toString() );
		
		sqlQuery.setParameter( "status", SvcConstants.POL_STATUS_REFERRED.toString() );
		sqlQuery.setParameter( "policyId", commonVO.getPolicyId() );
		sqlQuery.setParameterList("facts", factList );
		sqlQuery.setParameter( "endtId", commonVO.getEndtId() );

		List data = sqlQuery.list();*/
		
		if(!Utils.isEmpty(ttrnPasReferralDetails)){
			Iterator<TTrnPasReferralDetails> it = ttrnPasReferralDetails.iterator();
			while(it.hasNext()){
				TTrnPasReferralDetails record = it.next();
				if(factList.contains(record.getId().getRefField())){
					delete( record );
				}
			}
		}
		return Boolean.TRUE;
	}
	
}
