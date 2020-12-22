/**
 * 
 */
package com.rsaame.pas.rsadirect.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.model.TTrnMakeClaim;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;

/**
 * @author Sarath Varier
 * @since Phase 3 - Make a Claim migration
 *
 */

public class MakeClaimDAO extends BaseDBDAO implements IRSADirectDao {
	
	private final static Logger LOGGER = Logger.getLogger(MakeClaimDAO.class);
	
	HibernateTemplate hibernateTemplate;

	@Override
	public TTrnMakeClaim submitClaim(TTrnMakeClaim ttrnMakeClaim) {
		
		/*List<Object[]> policyDetails = DAOUtils.getSqlResult(QueryConstants.GET_LATEST_POLICY_ID_ENDT_ID, hibernateTemplate, ttrnMakeClaim.getMacPolicyNo());
		if( Utils.isEmpty(policyDetails) || policyDetails.size() < 1){
			throw new BusinessException("pasb2c.claims.invalidPolicyNo", null, "Policy number entered for Motor claim is not found in DB");
		}
		BigDecimal endtId = (BigDecimal)(policyDetails.get(0))[0];
		BigDecimal policyId = (BigDecimal)(policyDetails.get(0))[1];*/
		Long claimId = NextSequenceValue.getNextSequence( SvcConstants.SEQ_SUBMIT_CLAIM_ID, null, null, null, null, getHibernateTemplate() );
		if(null != ttrnMakeClaim.getMacDriverLicense()) {
			ttrnMakeClaim.setMacDriverLicense( ttrnMakeClaim.getMacDriverLicense().replaceFirst("##", String.valueOf(claimId)));
		}
		if(null != ttrnMakeClaim.getMacRegcardCopy()) {
			ttrnMakeClaim.setMacRegcardCopy(ttrnMakeClaim.getMacRegcardCopy().replaceFirst("##", String.valueOf(claimId)));
		}
		
		//Added for Oman D2C
		LoginLocation location = (LoginLocation) Utils.getBean("location");
		String deployedLocation = location.getLocation();
		if (null != deployedLocation && deployedLocation.equals(QueryConstants.LOCATION_CODE)) {
			if(null != ttrnMakeClaim.getMacClaimForm())
				ttrnMakeClaim.setMacClaimForm(ttrnMakeClaim.getMacClaimForm().replaceFirst("##", String.valueOf(claimId)));
			if(null != ttrnMakeClaim.getMacMRTAForm())
				ttrnMakeClaim.setMacMRTAForm(ttrnMakeClaim.getMacMRTAForm().replaceFirst("##", String.valueOf(claimId)));
			if(null != ttrnMakeClaim.getMacAccidentType() && ttrnMakeClaim.getMacAccidentType().equalsIgnoreCase(QueryConstants.ACCIDENT_TYPE_POLICE_REPORT) && null != ttrnMakeClaim.getMacPoliceReport()) {
				ttrnMakeClaim.setMacPoliceReport(ttrnMakeClaim.getMacPoliceReport().replaceFirst("##", String.valueOf(claimId)));
			}
		}else {
			if(null != ttrnMakeClaim.getMacPoliceReport())
				ttrnMakeClaim.setMacPoliceReport(ttrnMakeClaim.getMacPoliceReport().replaceFirst("##", String.valueOf(claimId)));
		}
		//End D2C
		
		ttrnMakeClaim.setMacClaimId(claimId);
		ttrnMakeClaim.setMacPreparedDate(getSysDate());
		/*ttrnMakeClaim.setMacPolicyId(policyId);
		ttrnMakeClaim.setMacEndtId(endtId);*/
		
		hibernateTemplate.save(ttrnMakeClaim);
		
		return ttrnMakeClaim;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public BaseVO getLocation(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		DataHolderVO<Integer> output = new DataHolderVO<Integer>();		
		Object[] data = holderVO.getData();
		//String email =  (String) data[1];
		//Date dob = (Date) data[2];		
		Integer location = null;
		
		/*query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(QRY_GET_LOCATION_CODE);			
		query.setParameter("policyNo", (Long)data[0]);
		query.setParameter("dob", (Date)data[2]);
		Integer location = null;
		List<Object> locations = query.list();	*/
		List<Object> locations = DAOUtils.getSqlResultSingleColumn(QueryConstants.QRY_GET_LOCATION_CODE,hibernateTemplate,(Long)data[0],(Date)data[2]);
		if(!Utils.isEmpty(locations) && !Utils.isEmpty( locations.get( SvcConstants.zeroVal ))){
			location =Integer.parseInt(locations.get( SvcConstants.zeroVal ).toString());
			output.setData(location);
			LOGGER.debug("******Location code******: "+location);
		}else {
			throw new BusinessException("", null, Utils.getAppErrorMessage("pasb2c.renewal.empty.record"));
		}	
		return output;					
	}
	
}
