/**
 * 
 */
package com.rsaame.pas.referral.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspWriter;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1012799
 *
 */
public class ConsolidatedReferralMessageRenderer implements IHtmlRenderer{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.lookup.ui.IHtmlRenderer#buildHTMLContent(java.util.HashMap)
	 */
	/**
	 * This method renders the referral text, the consolidated referral text is fetched from both db and the data set in the session
	 * eg: in case of home lob the general info refferal msg will be saved in DB but in rsk page the referral of the rsk page is not yet saved
	 */
	@Override
	public void buildHTMLContent( HashMap<String, Object> attributeList ) throws IOException{
		Long policyId = (Long) attributeList.get( AppConstants.POLICY_ID );
		Long endtId = (Long) attributeList.get( AppConstants.ENDT_ID );
		CommonVO commonVO = (CommonVO) attributeList.get( AppConstants.COMMON_VO );
		JspWriter out = (JspWriter) attributeList.get( AppConstants.OUT );
		StringBuffer responseString = new StringBuffer();

		/*
		 * Get the referral text for the current page. This needs to be set in the rule response mapper
		 */
		Map<String, Map<String, String>> referralData = (Map<String, Map<String, String>>) attributeList.get( AppConstants.REFERRAL_VO );
		String nextAction = (String) attributeList.get( "nextAction" );
		String[] generalFact = Utils.getMultiValueAppConfig( "GENRAL_FACT" );
		String[] homeFact = Utils.getMultiValueAppConfig( "HOME_FACT" );
		String[] travelFact = Utils.getMultiValueAppConfig( "TRAVEL_FACT" );
		boolean isFetchReq = false;

		if( !Utils.isEmpty( referralData ) ){
			isFetchReq = true;
		}

		if( Utils.isEmpty( referralData ) && ( !Utils.isEmpty( nextAction ) && nextAction.equalsIgnoreCase( "Save" ) ) ){
			isFetchReq = true;
		}
		Map<String, String> referralMessages = null;
		if( !Utils.isEmpty( policyId ) ){
			/*
			 * Get the existing referral text, from table if any
			 */
			
			if( isFetchReq ){
				referralMessages = DAOUtils.getReferralMessages( policyId, endtId, (((UserProfile)commonVO.getLoggedInUser() ).getRsaUser().getUserId() ) );
			}
		}
			
			if( !Utils.isEmpty( referralData ) || !Utils.isEmpty( referralMessages ) ){
				
			if( !Utils.isEmpty( referralData ) ){
				for( String fieldName : referralData.keySet() ){
					//Merge the referral text from the risk page and database
					Map<String, String> referralValues = referralData.get( fieldName );
					if( !Utils.isEmpty( referralMessages ) ){
						referralMessages.put( fieldName, getRefTxt( referralValues ) );
					}
					else{
						referralMessages = new HashMap<String, String>();
						referralMessages.put( fieldName, getRefTxt( referralValues ) );
					}

				}
			}
				

				if( !Utils.isEmpty( referralMessages ) ){

					prepareRefText( responseString, generalFact, referralMessages, "General Info" );
					if( commonVO.getLob().equals( LOB.HOME ) ){
						prepareRefText( responseString, homeFact, referralMessages, "Home" );
					}
					else if( commonVO.getLob().equals( LOB.TRAVEL ) ){
						prepareRefText( responseString, travelFact, referralMessages, "Travel" );
					}else
					{
						removeGeneralMessages(generalFact, referralMessages);
						prepareRefTextMonoline( responseString, referralMessages, commonVO.getLob().toString() );
					}
				}
			}

		out.print( responseString );

	}

	private void prepareRefText( StringBuffer responseString, String[] generalFact, Map<String, String> referralMessages, String sectionHeader ) throws IOException{
		//Heading
		boolean executeOnce = true;
		if( !Utils.isEmpty( generalFact ) ){
			for( String sectionFact : generalFact ){
				String refferalMsg = referralMessages.get( sectionFact );
				//msg
				if( !Utils.isEmpty( refferalMsg ) ){
					if( executeOnce ){
						responseString.append( "<b>" ).append( sectionHeader ).append( "</b>" );
						executeOnce = false;
					}
					responseString.append( "<div>" ).append( refferalMsg ).append( "</div>" );
				}
			}
		}

	}
	
	private void removeGeneralMessages(String[] generalFact, Map<String, String> referralMessages ) throws IOException{
		//Heading
		if( !Utils.isEmpty( generalFact ) ){
			for( String sectionFact : generalFact ){
				referralMessages.remove(sectionFact);
			}
		}

	}
	
	private void prepareRefTextMonoline( StringBuffer responseString, Map<String, String> referralMessages, String sectionHeader ) throws IOException{
		//Heading
		boolean executeOnce = true;
		if( !Utils.isEmpty( referralMessages ) ){
			for( Map.Entry<String, String> entry : referralMessages.entrySet()){
				if( !Utils.isEmpty( entry.getValue() ) ){
					if( executeOnce ){
						responseString.append( "<b>" ).append( sectionHeader ).append( "</b>" );
						executeOnce = false;
					}
					responseString.append( "<div>" ).append( entry.getValue() ).append( "</div>" );
				}
			}
		}

	}

	private String getRefTxt( Map<String, String> referralValues ){

		for( Entry<String, String> entry : referralValues.entrySet() ){

			return entry.getValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.lookup.ui.IHtmlRenderer#buildEmptyControl(javax.servlet.jsp.JspWriter)
	 */
	@Override
	public void buildEmptyControl( JspWriter out ) throws IOException{
		// TODO Auto-generated method stub

	}

}
