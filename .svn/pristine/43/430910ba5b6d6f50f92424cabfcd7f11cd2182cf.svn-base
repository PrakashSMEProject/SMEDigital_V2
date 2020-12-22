package com.rsaame.pas.com.helper;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1006438
 * This class is used to derive the data needed to insert to the t_trn_policy table
 */
public class DerivePolicyDetails extends BaseDervieDetails{
	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){
		TTrnPolicyQuo policy = (TTrnPolicyQuo) mappedPojo;
		// Policy data is not yet present in thread level context. Hence polData sent to this methd is null. 
		// So get the polData from the tableData
		polData = (PolicyDataVO) tableData.getTableData();
		
		//Change due to Renewal Batch Processing - If Scheduler is running, getRsaUser() returns the  null value
		Integer userId = null;
		
		if( !Utils.isEmpty( polData ) && !Utils.isEmpty( polData.getAuthenticationInfoVO() ) && !Utils.isEmpty( polData.getAuthenticationInfoVO().getLicensedBy() ) ){
			userId = polData.getAuthenticationInfoVO().getLicensedBy();
		}
		else if( Utils.isEmpty( commonVO.getLoggedInUser().getUserId() ) ){
			userId = ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId();
		}
		else{
			userId = Integer.parseInt( commonVO.getLoggedInUser().getUserId() );
		}		
		policy.setPolCoinsuranceIndicator( Boolean.valueOf( Utils.getSingleValueAppConfig( "COINSURANCE_INDICATOR" ) ) );
		policy.setPolCoinsuranceType( Boolean.valueOf( Utils.getSingleValueAppConfig( "COINSURANCE_TYPE" ) ) );
		// To get the customer id(i.e customer billing account from t_mas_scheme)
		// Set the customer id only when scheme code already exits
		if(!Utils.isEmpty( polData.getScheme().getSchemeCode() )){
			policy.setPolCustomerId( DAOUtils.getCustoemrId( ht, polData.getScheme().getSchemeCode() ) );
		}
		
		policy.setPolExchangeRate( BigDecimal.valueOf( Long.valueOf( Utils.getSingleValueAppConfig( "EXCHANGE_RATE" ) ) ) );
		policy.setPolCurrencyCode( Short.valueOf( Utils.getSingleValueAppConfig( "CURRENCY_CODE" ) ) );
		policy.setPolFinancialIntInd( Byte.valueOf( Utils.getSingleValueAppConfig( "FINANCIAL_IND" ) ) );
		// Get the city/region details from t_mas_control
		DataHolderVO<Object[]> controlData = (DataHolderVO<Object[]>) DAOUtils.getControlDetails( ht );
		Object[] contrlolResult = controlData.getData();
		policy.setPolCtyCode( (Integer) contrlolResult[ 0 ] );
		policy.setPolRegCode( (Integer) contrlolResult[ 1 ] );

		policy.setPolCutCode( Short.valueOf( Utils.getSingleValueAppConfig( "POL_CUT_CODE" ) ) );
		policy.setPolCoverNoteMin( Byte.valueOf( Utils.getSingleValueAppConfig( "POL_COVER_NOTE_MIN" ) ) );
		policy.setPolRatingType( Byte.valueOf( Utils.getSingleValueAppConfig( "POL_RATING_TYPE_DEF" ) ) );
		policy.setPolDctCode( Byte.valueOf( Utils.getSingleValueAppConfig( "POL_DCT_CODE" ) ) );
		policy.setPolLinkingId( Long.valueOf( Utils.getSingleValueAppConfig( "POL_LINKING_ID" ) ) );
		policy.setPolSbsInd( Boolean.valueOf( Utils.getSingleValueAppConfig( "POL_SBS_IND" ) ) );
		policy.setPolPepCode( Short.valueOf( Utils.getSingleValueAppConfig( "POL_PEP_CODE_DEF" ) ) ); 
		
		policy.setPolProcessedDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		
		policy.setPolProposalDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		
		policy.setPolProposalNo(Long.valueOf( SvcConstants.zeroVal ));
		
		 /* OMAN multi-branch fix */
		if((SvcConstants.OMAN.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))))
		{
			policy.setPolProcLocCode(  polData.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
		}
		else
		{	
			//Phase 3: Backend defect fix :  
			policy.setPolProcLocCode(  polData.getAuthenticationInfoVO().getLocationCode() );
		}
		
				 
		if( Utils.isEmpty( policy.getPolPremium() ) ){
			policy.setPolPremium( BigDecimal.valueOf(Long.valueOf( SvcConstants.zeroVal )) );
		}
		if( Utils.isEmpty( policy.getPolGovernmentTax() ) ){
			policy.setPolGovernmentTax( BigDecimal.valueOf( Long.valueOf( SvcConstants.zeroVal ) ) );
		}
		if( Utils.isEmpty( policy.getPolPolicyFees() ) ){
			policy.setPolPolicyFees( ( BigDecimal.valueOf( Long.valueOf( SvcConstants.zeroVal ) ) ) );
		}
		
		if( Utils.isEmpty( polData.getGeneralInfo().getInsured().getInsuredId() ) ){
			Long seqNo = NextSequenceValue.getNextSequence( SvcConstants.INSUREDID_SEQ_SBS, Integer.valueOf(polData.getPolicyType()),polData.getPolicyClassCode(), null, null, ht );
			polData.getGeneralInfo().getInsured().setInsuredId( seqNo );
			policy.setPolInsuredId( seqNo );
			
		}
		policy.setPolIssueHour( SvcConstants.POL_ISSUE_HOUR );
		//Setting cash customer ID before invoking update
		if(!Utils.isEmpty(polData.getCommonVO()) && !Utils.isEmpty(polData.getCommonVO().getLob()) ){
			policy.setPolCcgCode(Integer.valueOf( Utils.getSingleValueAppConfig( polData.getCommonVO().getLob().toString() + SvcConstants.LOB_CCG_CODE )));
		}else {
			policy.setPolCcgCode(SvcConstants.CCG_CODE.intValue());
		}
		//policy.setPolUserId( ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId() );
		policy.setPolUserId(userId);
		
		/*Set default approved by, approved date and approval indicator. */
		//TODO : The below fields need to be reset during referral flow.
		//policy.setPolApprovedBy( ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId() );
		if(Utils.isEmpty( policy.getPolApprovedBy() )){
			policy.setPolApprovedBy(userId);
		}
		//policy.setPolApprovalDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		
		policy.setPolApprovalDate(new Date());
		
		//Phase 3: Backend defect fix :  
		policy.setPolApprovedInd( SvcConstants.APPROVED_IND_YES );
			
		if( Utils.isEmpty( polData.getAuthenticationInfoVO().getAccountingDate() ) ){
			policy.setPolIssueDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE )  );
			policy.setPolQuotationDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		}
		
		if( !Utils.isEmpty( policy.getPolDistributionChnl() ) ){
			/*Set Source of business as per distribution channel value.*/
			if( policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_DIRECT ) ){
				policy.setPolSourceOfBusiness( Byte.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SRC_OF_BUS_DIRECT ) ) );
				policy.setPolBrCode(null);
			}
			else if( policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_DIRECT_CALL_CENTER ) ){
					policy.setPolSourceOfBusiness( Byte.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SRC_OF_BUS_DIRECT ) ) );
					policy.setPolBrCode(null);
				}
			else if( policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_DIRECT_WEB ) ){
					policy.setPolSourceOfBusiness( Byte.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SRC_OF_BUS_DIRECT ) ) );
					policy.setPolBrCode(null);
				}
			else{
				if(Utils.isEmpty((polData.getGeneralInfo().getSourceOfBus().getSourceOfBusiness()))
						&& !policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT ))
				{
					policy.setPolSourceOfBusiness( Byte.valueOf( Utils.getSingleValueAppConfig( "SRC_OF_BUS_NON_DIRECT" ) ) );
				}
				else
				{
					if(!Utils.isEmpty((polData.getGeneralInfo().getSourceOfBus().getSourceOfBusiness())))
					{
						policy.setPolSourceOfBusiness( Byte.valueOf(polData.getGeneralInfo().getSourceOfBus().getSourceOfBusiness().toString()) );
						if(!policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_BROKER ))
						{
							policy.setPolBrCode(null);
						}
					}
					else
					{
						policy.setPolSourceOfBusiness( Byte.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SRC_OF_BUS_DIRECT ) ) );
						if(!policy.getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_BROKER ))
						{
							policy.setPolBrCode(null);
						}
					}
				}
			}

		}
		if(!Utils.isEmpty(commonVO.getConcatPolicyNo())){
			policy.setPolConcPolicyNo(commonVO.getConcatPolicyNo());
		}
		if( !Utils.isEmpty( polData.getEndEffectiveDate() ) && ( (UserProfile) ( commonVO ).getLoggedInUser() ).getRsaUser().getProfile().equalsIgnoreCase( "Broker" )){
			policy.setPolEndtEffectiveDate( polData.getEndEffectiveDate() );
		}
		else if( !Utils.isEmpty( commonVO.getEndtEffectiveDate() ) ){
			policy.setPolEndtEffectiveDate( commonVO.getEndtEffectiveDate() );
		}
	}
	
	
	@Override

	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase ){

		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolCommisionId() ) ){
			if( !Utils.isEmpty( existingRecord ) && !((TTrnPolicyQuo) mappedPojo).getPolDistributionChnl().equals( SvcConstants.DIST_CHANNEL_DIRECT )){
				if(( (TTrnPolicyQuo) mappedPojo ).getPolCoverNoteHour().equals( ( (TTrnPolicyQuo) existingRecord ).getPolCoverNoteHour()  ))
					( (TTrnPolicyQuo) mappedPojo ).setPolCommisionId( ( (TTrnPolicyQuo) existingRecord ).getPolCommisionId() );
				else
					( (TTrnPolicyQuo) mappedPojo ).setPolCommisionId(null);
			}
		}
		// Change for quote version - Setting values if we edit the renewal quote 
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolRefPolicyId() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolRefPolicyId( ( (TTrnPolicyQuo) existingRecord ).getPolRefPolicyId() );
			}
		}
		// Change for quote version - Setting values if we edit the renewal quote 
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolRefPolicyNo() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolRefPolicyNo( ( (TTrnPolicyQuo) existingRecord ).getPolRefPolicyNo() );
			}
		}
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolPolicyNo() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolPolicyNo( ( (TTrnPolicyQuo) existingRecord ).getPolPolicyNo() );
			}
		}
		
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolCoverNoteHour() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolCoverNoteHour( ( (TTrnPolicyQuo) existingRecord ).getPolCoverNoteHour() );
			}
		}
		
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolPymntDueDate() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolPymntDueDate( ( (TTrnPolicyQuo) existingRecord ).getPolPymntDueDate() );
			}
		}
		
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolEffectiveHour() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolEffectiveHour( ( (TTrnPolicyQuo) existingRecord ).getPolEffectiveHour() );
			}
		}
		
		if( Utils.isEmpty( ( (TTrnPolicyQuo) mappedPojo ).getPolCoverNoteNo() ) ){
			if( !Utils.isEmpty( existingRecord ) ){
				( (TTrnPolicyQuo) mappedPojo ).setPolCoverNoteNo( ( (TTrnPolicyQuo) existingRecord ).getPolCoverNoteNo() );
			}
		}

		if( saveCase.equals( SaveCase.CHANGE_WITH_NEW_REC ) || saveCase.equals( SaveCase.CREATE ) ){
			( (TTrnPolicyQuo) mappedPojo ).setPolIssueDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		}
		
	}
	
}
