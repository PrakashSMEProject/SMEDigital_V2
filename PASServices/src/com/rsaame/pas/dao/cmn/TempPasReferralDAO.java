package com.rsaame.pas.dao.cmn;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnTempPasReferral;
import com.rsaame.pas.dao.model.TTrnTempPasReferralId;
import com.rsaame.pas.vo.bus.ReferralVO;

public class TempPasReferralDAO extends BaseDBDAO{
	

	public void insertReferal( ReferralVO referralVO ){

		if( !Utils.isEmpty( referralVO ) ){
			TTrnTempPasReferral tempPasReferral = new TTrnTempPasReferral();
			String refTxt = "";
			String referralText = "";
			StringBuilder strBuilder = new StringBuilder();			/* Declared variable to remove '+' concat of string - sonar violation fix */
			for( int i = 0; i < referralVO.getReferralText().size(); i++ ){
				//refTxt += " "+ referralVO.getReferralText().get( i );
				refTxt = strBuilder.append(" ").append(referralVO.getReferralText().get( i )).toString();	/* Replaced '+' concat of string with strBuilder - sonar violation fix */
			}
			
			// Bug Fix Start -  referralText in database is 4000 varchar
			if(refTxt.length()>4000)
			{
				referralText = refTxt.trim().substring(0,4000);
			}
			else
			{
				referralText = refTxt;
			}
			// Bug Fix End -  referralText in database is 4000 varchar
			
			TTrnTempPasReferralId id = new TTrnTempPasReferralId();
			id.setTprPolLinkingId(referralVO.getPolLinkingId());
			id.setTprLocationId( Long.valueOf( referralVO.getRiskGroupId() ) );
			//id.setTprRefText( referralText );
			id.setTprSecId( referralVO.getSectionId().shortValue() );
			tempPasReferral.setTprRefText( referralText );
			
			/*Start of ticket 137704 */
			tempPasReferral.setTprUserId(referralVO.getTprUserId());
			tempPasReferral.setTprUserRole(referralVO.getTprUserRole());
			/*End of ticket 137704 */

			tempPasReferral.setId( id );

			try{
				/* Merge works here not saveOrUpdate as we are creating new Id object every time and in case save operation is performed
				 * more than once on a section then there is possibility of getting different object with same identifier already exists
				 * in the session exception from hibernate */
				this.getHibernateTemplate().merge( tempPasReferral );
				this.getHibernateTemplate().flush();
				this.getHibernateTemplate().clear();
				
			}
			catch( DataAccessException e ){
				throw new SystemException( "wc.tempInsert.fail", e, "Error while updating data to TempPasReferral table" );
			}
		}

	}

}
