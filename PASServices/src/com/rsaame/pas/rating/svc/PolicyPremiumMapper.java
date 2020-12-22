package com.rsaame.pas.rating.svc;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.cts.writeRate.Coverage;
import com.cts.writeRate.ErrorType;
import com.cts.writeRate.Item;
import com.cts.writeRate.Policy;
import com.cts.writeRate.Premium;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.admin.model.Employee;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCVO;

public class PolicyPremiumMapper{
	private final static Logger logger = Logger.getLogger( PolicyPremiumMapper.class );

	public PolicyVO createPASPolicyfromeRatorPolicy( Policy[] eRatorPolicyArrayWithPremium, PolicyVO policy, HashMap<String, String> ratingTracker, boolean isPrepacked ){
		long startTime = System.currentTimeMillis();
		for( Policy eRatorPolicyWithPremium : eRatorPolicyArrayWithPremium ){
			if( Utils.isEmpty( eRatorPolicyWithPremium.getError() ) ){

				//Map item level premium

				if( !Utils.isEmpty( eRatorPolicyWithPremium.getItems() ) ){

					policy = mapItemLevelPremiums( eRatorPolicyWithPremium.getItems(), policy, ratingTracker, isPrepacked );
					
					handleSectionLevelMinPremium (policy);
				}
			}
			else{
				//Radar fix
				ErrorType errorFromRatingEngine = null; //new ErrorType();
				errorFromRatingEngine = eRatorPolicyWithPremium.getError();
				logger.error( "Rating Engine Get Details Error Response:RatingErrorMessage:" + errorFromRatingEngine.getErrorUserMessage() );
				logger.error( "Rating Engine Get Details Error Response:ErrorOccuranceDate:" + errorFromRatingEngine.getErrorOccuranceDate() );
				logger.error( "Rating Engine Get Details Error Response:ErrorClass:" + errorFromRatingEngine.getClass() );
				logger.error( "Rating Engine Get Details Error Response:ErrorID:" + errorFromRatingEngine.getErrorId() );
				logger.error( "Rating Engine Get Details Error Response:ErrorElementData:" + errorFromRatingEngine.getErrorElementData() );
				BusinessException businessExcp = new BusinessException( "rating.getRates.error", null, errorFromRatingEngine.getErrorUserMessage() );
				throw businessExcp;

			}

		}
		long endTime = System.currentTimeMillis();
		policy = calculateTotalPremium( policy );
		logger.info( " Rating Engine integration Timer: Premium mapping time (In MilliSec):: " + ( endTime - startTime ) );

		return policy;
	}

	

	private PolicyVO mapItemLevelPremiums( Item[] items, PolicyVO policy, HashMap<String, String> ratingTracker, boolean isPrepacked ){
		//START: For each item
		//int parContCoverageNumFromRating=0;
		for( Item item : items ){

			if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_BUILD_ITEM_CODE ) ){

				String ratingTrackerKey = "PAR_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );

				logger.debug( "Rating : ratingTracker read: PAR_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						String contentType = "blding";
						policy = setPremiumForContent( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 1 ), contentType );
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 1 ) );
					}
				}
			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_BUILD_CONTENT_ITEM_CODE ) ){
				String ratingTrackerKey = "PAR_CONT_" + item.getItemNumber();
				String riskIdwithContentType = ratingTracker.get( ratingTrackerKey );
				String riskUniqId = null;
				String contentType = "NoContent";
				if( ( !Utils.isEmpty( riskIdwithContentType ) ) ){
					if( riskIdwithContentType.indexOf( com.Constant.CONST_PAR_CONT_TYPE ) == -1 ){
						riskUniqId = riskIdwithContentType;
					}
					else{

						riskUniqId = riskIdwithContentType.substring( 0, riskIdwithContentType.indexOf( com.Constant.CONST_PAR_CONT_TYPE ) );
						contentType = riskIdwithContentType.substring( riskIdwithContentType.indexOf( com.Constant.CONST_PAR_CONT_TYPE ) + 14 );
					}

					logger.debug( "Rating : ratingTracker read: PAR_CONT_" + item.getItemNumber(), riskUniqId );
					if( !isPrepacked ){
						if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
							if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){

								policy = setPremiumForContent( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 1 ), contentType );
								policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 1 ) );

							}
						}
					}
					else{
						// If prepacked, rating par content have multiple coverages apart from normal single coverage. 
						for( Coverage cover : item.getCoverages() ){
							if( !Utils.isEmpty( cover.getPremium().getBasePremium() ) ){
								if( new Float( 0 ) != cover.getPremium().getBasePremium() ){
									policy = setPremium( policy, riskUniqId, cover.getPremium(), String.valueOf( 1 ) );
								}
							}
							//parContCoverageNumFromRating++;
						}
					}

				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_PL_ITEM_CODE ) ){
				String ratingTrackerKey = "PL_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: PL_" + item.getItemNumber(), riskUniqId );
				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(6) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 6 ) );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_WC_ITEM_CODE ) ){
				String ratingTrackerKey = "WC_" + item.getItemNumber();
				String riskUniqId = null;
				String empTypeNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				
				
				logger.debug( "Rating : ratingTracker read: WC_" + item.getItemNumber(), riskUniqId );

				if(!Utils.isEmpty( riskUniqIdItemNumber ))
				{
					riskUniqId=riskUniqIdItemNumber.substring(0,riskUniqIdItemNumber.indexOf(com.Constant.CONST_EMP_TYPE_END));
					int beginIndex = riskUniqIdItemNumber.indexOf(com.Constant.CONST_EMP_TYPE_END) + (com.Constant.CONST_EMP_TYPE_END).length();
					empTypeNumber=riskUniqIdItemNumber.substring(beginIndex, riskUniqIdItemNumber.length());
				}
				else{
					riskUniqId =ratingTracker.get( ratingTrackerKey );
					empTypeNumber = "0";
				}
				if(!Utils.isEmpty(item.getCoverages()[0].getPremium().getBasePremium())){
					if(new Float(0)!=item.getCoverages()[0].getPremium().getBasePremium()){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
						policy = setPremiumForWC(policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7),empTypeNumber);
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_MONEY_ITEM_CODE ) ){
				String ratingTrackerKey = "MONEY_" + item.getItemNumber();
				String riskUniqId = null;
				String riskIdwithContentType = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: WC_" + item.getItemNumber(), riskUniqId );
				String moneyCategory = "NoContent";
				if( ( !Utils.isEmpty( riskIdwithContentType ) ) ){
					if( riskIdwithContentType.indexOf( com.Constant.CONST_MONEY_CONT_TYPE ) == -1 ){
						riskUniqId = riskIdwithContentType;
					}
					else{

						riskUniqId = riskIdwithContentType.substring( 0, riskIdwithContentType.indexOf( com.Constant.CONST_MONEY_CONT_TYPE ) );
						moneyCategory = riskIdwithContentType.substring( riskIdwithContentType.indexOf( com.Constant.CONST_MONEY_CONT_TYPE ) + 16 );
					}

					//policy = setPremium( policy, riskUniqId, item.getPremium() );
					//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium() ,String.valueOf(8));
					if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
						if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
							policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 8 ) );
							policy = setPremiumForMoneyContent( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( 8 ), moneyCategory );
						}
					}

				}
			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_BI_ITEM_CODE ) ){
				String ratingTrackerKey = "BI_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: BI_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_BI ) );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_MB_ITEM_CODE ) ){
				String ratingTrackerKey = "MB_" + item.getItemNumber();
				String riskUniqId = null;
				String mbItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: MB_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_MB_NUM_END ) );
					int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_MB_NUM_END ) + ( com.Constant.CONST_MB_NUM_END ).length();
					mbItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					mbItemNumber = "0";
				}
				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_MB ) );
						policy = setPremiumForMB( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_MB ), mbItemNumber );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_DOS_ITEM_CODE ) ){
				String ratingTrackerKey = "DOS_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				String dosItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: DOS_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_DOS_NUM_END ) );
					int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_DOS_NUM_END ) + ( com.Constant.CONST_DOS_NUM_END ).length();
					dosItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					dosItemNumber = "0";
				}
				logger.debug( "Rating : ratingTracker read: DOS_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_DOS ) );
						policy = setPremiumForDOS( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_DOS ), dosItemNumber );
					}
				}
			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_EEQ_ITEM_CODE ) ){
				String ratingTrackerKey = "EEQ_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				String eeItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: EEQ_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_EE_NUM_END ) );
					int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_EE_NUM_END ) + ( com.Constant.CONST_EE_NUM_END ).length();
					eeItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					eeItemNumber = "0";
				}
				logger.debug( "Rating : ratingTracker read: EEQ_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_EE ) );
						policy = setPremiumForEE( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_EE ), eeItemNumber );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_TB_ITEM_CODE ) ){
				String ratingTrackerKey = "Travel_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: Travel_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_TB ) );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_GIT_ITEM_CODE ) ){
				String ratingTrackerKey = "GIT_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				String gitItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: GIT_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_GIT_NUM_END ) );
					int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_GIT_NUM_END ) + ( com.Constant.CONST_GIT_NUM_END ).length();
					gitItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					gitItemNumber = "0";
				}
				logger.debug( "Rating : ratingTracker read: GIT_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_GIT ) );
						policy = setPremiumForGIT( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_GIT ), gitItemNumber );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_GPA_ITEM_CODE ) ){
				String ratingTrackerKey = "GPA_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				String gpaUnnamedItemNumber = "";
				String gpaNamedItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: GPA_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					if(riskUniqIdItemNumber.contains( com.Constant.CONST_GPA_UNNAMED_END )){
						riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_GPA_UNNAMED_END ) );
						int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_GPA_UNNAMED_END ) + ( com.Constant.CONST_GPA_UNNAMED_END ).length();
						gpaUnnamedItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
					}
					if(riskUniqIdItemNumber.contains( com.Constant.CONST_GPA_NAMED_END )){
						riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_GPA_NAMED_END ) );
						int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_GPA_NAMED_END ) + ( com.Constant.CONST_GPA_NAMED_END ).length();
						gpaNamedItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
					}
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					gpaUnnamedItemNumber = "0";
					gpaNamedItemNumber = "0";
				}
				logger.debug( "Rating : ratingTracker read: GPA_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_GPA ) );
						policy = setPremiumForGPA( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_GPA ), gpaUnnamedItemNumber , gpaNamedItemNumber );
					}
				}

			}
			else if( item.getItemCode().equalsIgnoreCase( SvcConstants.RATING_FID_ITEM_CODE ) ){
				String ratingTrackerKey = "FID_" + item.getItemNumber();
				String riskUniqId = ratingTracker.get( ratingTrackerKey );
				String fidUnnamedItemNumber = "";
				String fidNamedItemNumber = "";
				String riskUniqIdItemNumber = ratingTracker.get( ratingTrackerKey );
				logger.debug( "Rating : ratingTracker read: FID_" + item.getItemNumber(), riskUniqId );
				if( !Utils.isEmpty( riskUniqIdItemNumber ) ){
					if(riskUniqIdItemNumber.contains( com.Constant.CONST_FID_UNNAMED_END )){
						riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_FID_UNNAMED_END ) );
						int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_FID_UNNAMED_END ) + ( com.Constant.CONST_FID_UNNAMED_END ).length();
						fidUnnamedItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
					}
					if(riskUniqIdItemNumber.contains( com.Constant.CONST_FID_NAMED_END )){
						riskUniqId = riskUniqIdItemNumber.substring( 0, riskUniqIdItemNumber.indexOf( com.Constant.CONST_FID_NAMED_END ) );
						int beginIndex = riskUniqIdItemNumber.indexOf( com.Constant.CONST_FID_NAMED_END ) + ( com.Constant.CONST_FID_NAMED_END ).length();
						fidNamedItemNumber = riskUniqIdItemNumber.substring( beginIndex, riskUniqIdItemNumber.length() );
					}
				}
				else{
					riskUniqId = ratingTracker.get( ratingTrackerKey );
					fidUnnamedItemNumber = "0";
					fidNamedItemNumber = "0";
				}
				logger.debug( "Rating : ratingTracker read: FID_" + item.getItemNumber(), riskUniqId );

				//policy = setPremium( policy, riskUniqId, item.getPremium() );
				//policy = setPremium( policy, riskUniqId, item.getCoverages()[0].getPremium(),String.valueOf(7) );
				if( !isPrepacked ){
				if( !Utils.isEmpty( item.getCoverages()) && (item.getCoverages().length > 1) && !Utils.isEmpty( item.getCoverages()[ 1 ]) && (!Utils.isEmpty( item.getCoverages()[ 1 ].getPremium())) &&  !Utils.isEmpty( item.getCoverages()[ 1 ].getPremium().getBasePremium() ) ){
					setPremiumForFIDAggregate( policy, riskUniqId, item.getCoverages()[ 1 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_FID ));
				}
				if( !Utils.isEmpty( item.getCoverages()) && (item.getCoverages().length > 0) && !Utils.isEmpty( item.getCoverages()[ 0 ]) && (!Utils.isEmpty( item.getCoverages()[ 0 ].getPremium())) &&  !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
					if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
						policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_FID ) );
						policy = setPremiumForFID( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_FID ), fidUnnamedItemNumber , fidNamedItemNumber );
					}
				}
				}
				else{
					if(!Utils.isEmpty( item.getCoverages()) && (item.getCoverages().length > 0) && !Utils.isEmpty( item.getCoverages()[ 0 ]) &&  (!Utils.isEmpty( item.getCoverages()[ 0 ].getPremium())) &&  !Utils.isEmpty( item.getCoverages()[ 0 ].getPremium().getBasePremium() ) ){
						if( new Float( 0 ) != item.getCoverages()[ 0 ].getPremium().getBasePremium() ){
							policy = setPremium( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_FID ) );
							policy = setPremiumForFID( policy, riskUniqId, item.getCoverages()[ 0 ].getPremium(), String.valueOf( SvcConstants.SECTION_ID_FID ), fidUnnamedItemNumber , fidNamedItemNumber );
						}
					}
				}
			}

		}
	//End: For each item

		return policy;
	}

	private PolicyVO setPremiumForDOS( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate, String dosItemNumber ){
		
			List<SectionVO> sectionList = policy.getRiskDetails();
			if( !Utils.isEmpty( sectionList ) ){
				for( SectionVO sectionVo : sectionList ){
					String sectionId = sectionVo.getSectionId().toString();
					if( sectionIdToUpdate.equals( sectionId ) ){
						
						if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
							Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
							for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
								RiskGroup locationInfo = entry.getKey();

								if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
										&& !Utils.isEmpty( entry.getValue() ) ){
									RiskGroupDetails riskDetails = entry.getValue();
									
									// Start: riskDetails Empty check 
									if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_DOS)))){
								DeteriorationOfStockVO deteriorationOfStockVO = (DeteriorationOfStockVO) riskDetails;
								if(!Utils.isEmpty( deteriorationOfStockVO )){
									 List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsVOs = deteriorationOfStockVO.getDeteriorationOfStockDetails();
									 int dosRowNumber = 0;
									 for(DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : deteriorationOfStockDetailsVOs){
										 if(dosRowNumber == Integer.parseInt(dosItemNumber)){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											//Added for Bahrain 3 decimal - Start
					                            if (isSBSBahrainPolicy(policy)) {
					                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                          //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 deteriorationOfStockDetailsVO.setPremium( premiumVo );

										 }
										 dosRowNumber++;
									 }
								}
								
							 }// End: riskDetails Empty check 
								}
							}

						}
						else{
							logger.debug( "RiskGroupDetails for Section is nul_1" + sectionVo.getSectionId() );
						}
					}//Section check
				}

			}
			return policy;

		}

	

	private PolicyVO setPremiumForEE( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate, String eeItemNumber ){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_EE)))){
							EEVO eevo = (EEVO) riskDetails;
							if(!Utils.isEmpty( eevo )){
								 List<EquipmentVO> equipmentVOs = eevo.getEquipmentDtls();
								 int eeRowNumber = 0;
								 for(EquipmentVO equipmentVO : equipmentVOs){
									 if(eeRowNumber == Integer.parseInt(eeItemNumber)){
										 PremiumVO premiumVo = new PremiumVO();
										 double premiumToAdd= Double.valueOf( 0 );
										//Added for Bahrain 3 decimal - Start
				                            if (isSBSBahrainPolicy(policy)) {
				                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
												 premiumToAdd = roundToThreeDecimal(premiumToAdd);
				                            }
				                            else {
				                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
												 premiumToAdd = roundToTwoDecimal(premiumToAdd);
				                            }
				                          //Added for Bahrain 3 decimal - Ends
										 premiumVo.setPremiumAmt( premiumToAdd);
										 equipmentVO.setPremium( premiumVo );
										// eeRowNumber++;
									 }
									 eeRowNumber++;
								 }
							}
							
						 }// End: riskDetails Empty check 
							}
						}

					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_2" + sectionVo.getSectionId() );
					}
				}//Section check
			}

		}
		return policy;

	}

	private PolicyVO setPremiumForMB( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate, String mbItemNumber ){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if( ( !Utils.isEmpty( riskDetails ) ) && ( sectionIdToUpdate.equals( Integer.toString( SvcConstants.SECTION_ID_MB ) ) ) ){
									MBVO mbvo = (MBVO) riskDetails;
									if( !Utils.isEmpty( mbvo ) ){
										List<MachineDetailsVO> machineDetailsVOs = mbvo.getMachineryDetails();
										int mbRowNumber = 0;
										for( MachineDetailsVO machineDetailsVO : machineDetailsVOs ){
											if( mbRowNumber == Integer.parseInt( mbItemNumber ) ){
												PremiumVO premiumVo = new PremiumVO();
												double premiumToAdd = new Double( 0 );
												//Added for Bahrain 3 decimal - Start
					                            if (isSBSBahrainPolicy(policy)) {
					                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                          //Added for Bahrain 3 decimal - Ends
												premiumVo.setPremiumAmt( premiumToAdd );
												machineDetailsVO.setPremium( premiumVo );
												//mbRowNumber++;
											}
											mbRowNumber++;
										}
									}

								}// End: riskDetails Empty check 
							}
						}

					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_3" + sectionVo.getSectionId() );
					}
				}//Section check
			}

		}
		return policy;

	}

	private PolicyVO setPremiumForMoneyContent( PolicyVO policy, String riskUniqId, Premium ratingEnginePremium, String sectionIdToUpdate, String moneyCategory ){
		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if( ( !Utils.isEmpty( riskDetails ) ) && ( sectionIdToUpdate.equals( "8" ) ) ){
									MoneyVO moneyVO = (MoneyVO) riskDetails;

									// Start: Check CASH_RESIDNCE
									if( moneyCategory.equals( "CASH_RESIDNCE" ) ){
										PremiumVO premiumVo = new PremiumVO();
										double premiumToAdd = new Double( 0 );
										//Added for Bahrain 3 decimal -  Starts
			                            if (isSBSBahrainPolicy(policy)) {
			                            	premiumToAdd = roundToNDecimal( ratingEnginePremium.getBasePremium(), 3 );
											premiumToAdd = roundToThreeDecimal( premiumToAdd );
			                            }
			                            else {
			                            	premiumToAdd = roundToTwoDecimal( ratingEnginePremium.getBasePremium(), 2 );
											premiumToAdd = roundToTwoDecimal( premiumToAdd );
			                            }
			                          //Added for Bahrain 3 decimal -  Ends
										premiumVo.setPremiumAmt( premiumToAdd );
										moneyVO.setCashResidencePremium( premiumVo );

									}
									else{
										// Start: Cover Empty check 
										if( !Utils.isEmpty( moneyVO.getContentsList() ) ){
											//PropertyRisks propRisks=moneyVo.getCovers();
											List<Contents> contentsList = moneyVO.getContentsList();
											boolean isBuilding = true;

											//Start:Content list check
											if( !Utils.isEmpty( moneyVO.getContentsList() ) ){
												// Start: Looping through the covers in Money 
												for( Contents contents : contentsList ){
													//Check for the first iteration. 1st coverlist will be always of building 
													/* if(isBuilding){
														 isBuilding=false;
														 continue;
													 }*/
													//Start : Check for right content
								 /* Check if the content is not added then skip the execution process for that content */
									if( PremiumHelper.isContentSumInsuredZero( contents )){
										//contents.setRiskId( null );
										continue;
									}
									/*if( Utils.isEmpty( contents.getRiskId() ) ){
										continue;
									}*/
									 StringTokenizer st = new StringTokenizer(moneyCategory, "-");
									 int riskType= Integer.parseInt(st.nextToken());
									 int riskCat=Integer.parseInt(st.nextToken());
									 int riskSubCat= Integer.parseInt(st.nextToken());
									 
									 //Start : Check for content
									 if(!Utils.isEmpty( contents))  {
									  //End : Start for right content
										 if( !Utils.isEmpty(contents.getRiskType())  && contents.getRiskType()==riskType && 
											 !Utils.isEmpty(contents.getRiskCat())  &&	 contents.getRiskCat()==riskCat &&
											 !Utils.isEmpty(contents.getRiskSubCat())  && contents.getRiskSubCat()== riskSubCat){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											 if((!Utils.isEmpty(riskDetails.getPremium())) &&(!Utils.isEmpty(riskDetails.getPremium().getPremiumAmt()))){
												//Added for Bahrain 3 decimal - Starts
						                            if (isSBSBahrainPolicy(policy)) {
						                            	 premiumToAdd=roundToNDecimal(ratingEnginePremium.getBasePremium(),3);
						                            }
						                            else {
						                            	 premiumToAdd=roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
						                            }
						                          //Added for Bahrain 3 decimal - Ends  
												
												 logger.debug("Rating:PremiumMapper:Existing Premium Am_1"+riskDetails.getPremium().getPremiumAmt());
												 logger.debug("Rating:PremiumMapper:Premium Amt from Rating Engin_1"+ratingEnginePremium.getBasePremium());
												 logger.debug("Rating:PremiumMapper:Total Premium Amt For SectionID:_1" +sectionIdToUpdate + "is :"+ premiumToAdd);

											 }else{
												 //premiumToAdd=ratingEnginePremium.getBasePremium();
												//Added for Bahrain 3 decimal - Starts
						                            if (isSBSBahrainPolicy(policy)) {
						                            	 premiumToAdd=roundToNDecimal(ratingEnginePremium.getBasePremium(),3);
						                            }
						                            else {
						                            	 premiumToAdd=roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
						                            }
						                          //Added for Bahrain 3 decimal - Ends

											 }
											//Added for Bahrain 3 decimal - Starts
					                            if (isSBSBahrainPolicy(policy)) {
					                            	premiumToAdd= roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	premiumToAdd= roundToTwoDecimal(premiumToAdd);
					                            }
					                          //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 contents.setPremium( premiumVo );	
										 } //End : Check for right content


									 } //End : Check for  content
								 }// Start: Looping through the covers in Money
								 }//End:Content list check
									 }// Start: Cover Empty check 
						}// End: Check CASH_RESIDNCE 
						
								 }// End: riskDetails Empty check 
							}

				}

			}else
			{
				logger.debug("RiskGroupDetails for Section is nul_4"+sectionVo.getSectionId());
			}
			}//Section check
			}
			
		}
		return policy;
	}

	private  float roundToTwoDecimal(float Rval, int Rpl) {
		if(Rval==0){
			return Rval;
		}
		   float p = (float)Math.pow(10,Rpl);
		   Rval = Rval * p;
		   float tmp = Math.round(Rval);
		   return (float)tmp/p;
		   }
		 

	private PolicyVO setPremiumForContent(PolicyVO policy, String riskUniqId,
			Premium ratingEnginePremium, String sectionIdToUpdate, String contentType) {
		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if(sectionIdToUpdate.equals(sectionId)){
				//For PAR
				if(!Utils.isEmpty(sectionVo) && !Utils.isEmpty(sectionVo.getRiskGroupDetails())){
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
					RiskGroup locationInfo = entry.getKey();

					if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
							&& !Utils.isEmpty( entry.getValue() )){
						RiskGroupDetails riskDetails = entry.getValue();
						/*LocationVO loc=(LocationVO)locationInfo;*/
						
						// Start: riskDetails Empty check 
						if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals("1"))){
							ParVO parVO = (ParVO) riskDetails;
							if(contentType.equals("blding")){
							PremiumVO bldPremium = new PremiumVO();
							double premiumToAdd= Double.valueOf( 0 );
							premiumToAdd=ratingEnginePremium.getBasePremium();
							//Added for Bahrain 3 decimal
							if(isSBSBahrainPolicy(policy)) {
								premiumToAdd= roundToThreeDecimal(premiumToAdd);
							}
							else {
								premiumToAdd= roundToTwoDecimal(premiumToAdd);
							}
							
							bldPremium.setPremiumAmt( premiumToAdd);
							parVO.setBldPremium(bldPremium);
							}
							
							else{// START: Else of blding
							// Start: Cover Empty check 
							if(!Utils.isEmpty( parVO.getCovers())){
								 PropertyRisks propRisks=parVO.getCovers();
								 List <PropertyRiskDetails> propCoversList=propRisks.getPropertyCoversDetails();
								 boolean isBuilding=true;
								// Start: Looping through the covers in PAR 
								 for(PropertyRiskDetails contentDetails: propCoversList){
									 //Check for the first iteration. 1st coverlist will be always of building 
									/* if(isBuilding){
										 isBuilding=false;
										 continue;
									 }*/
									 //Start : Check for right content
									 if(!Utils.isEmpty( contentDetails) && (!Utils.isEmpty( contentDetails.getRiskType()))&& (contentDetails.getRiskType().toString().equals(contentType)))  {
										 PremiumVO premiumVo = new PremiumVO();
											double premiumToAdd= Double.valueOf( 0 );
											if((!Utils.isEmpty(riskDetails.getPremium())) &&(!Utils.isEmpty(riskDetails.getPremium().getPremiumAmt()))){
												//Added for Bahrain 3 decimal
												if(isSBSBahrainPolicy(policy)) {
													premiumToAdd=roundToNDecimal(ratingEnginePremium.getBasePremium(),3);
												}
												else {
													premiumToAdd=roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
												}
												
												logger.debug("Rating:PremiumMapper:Existing Premium Am_2"+riskDetails.getPremium().getPremiumAmt());
												logger.debug("Rating:PremiumMapper:Premium Amt from Rating Engin_2"+ratingEnginePremium.getBasePremium());
												logger.debug("Rating:PremiumMapper:Total Premium Amt For SectionID:_2" +sectionIdToUpdate + "is :"+ premiumToAdd);
												
											}else{
												//premiumToAdd=ratingEnginePremium.getBasePremium();
												if(isSBSBahrainPolicy(policy)) {
													premiumToAdd=roundToNDecimal(ratingEnginePremium.getBasePremium(),3);
												}
												else {
													premiumToAdd=roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
												}
												
											
												
											}
											
											//Added for Bahrain 3 decimal
											if(isSBSBahrainPolicy(policy)) {
												premiumToAdd= roundToThreeDecimal(premiumToAdd);
											}
											else {
												premiumToAdd= roundToTwoDecimal(premiumToAdd);
											}
											premiumVo.setPremiumAmt( premiumToAdd);
											contentDetails.setPremium( premiumVo );

									 } //End : Check for right content
								 }// Start: Looping through the covers in PAR
									 }// Start: Cover Empty check 
						}// END: Else of blding
								 }// End: riskDetails Empty check 
							}

				}

			}else
			{
				logger.debug("RiskGroupDetails for Section is nul_5"+sectionVo.getSectionId());
			}
			}//Section check
			}
			
		}
		return policy;
	}

	private double roundToTwoDecimal(double premiumToAdd) {
		if(premiumToAdd==0){
            return premiumToAdd;
		}
		return (double)(Math.round(premiumToAdd*100))/100;
	}

	private PolicyVO calculateTotalPremium(PolicyVO policy) {
		PremiumVO premiumVo = new PremiumVO();
		double totalPremium=new Double(0);
		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				
				int sectionId=sectionVo.getSectionId();
				//For PAR
				if(!Utils.isEmpty(sectionVo) && !Utils.isEmpty(sectionVo.getRiskGroupDetails())){
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
					RiskGroup locationInfo = entry.getKey();

					if( !Utils.isEmpty( locationInfo.getRiskGroupId() )&& !Utils.isEmpty( entry.getValue() )){
						RiskGroupDetails riskDetails = entry.getValue();
						/*LocationVO loc=(LocationVO)locationInfo;*/
						//ParVO parVO = (ParVO) riskDetails;
						
						
						
						if((!Utils.isEmpty(riskDetails.getPremium())) &&(!Utils.isEmpty(riskDetails.getPremium().getPremiumAmt()))){
							totalPremium=totalPremium+riskDetails.getPremium().getPremiumAmt();
							switch(sectionId){
							case 1:break;
							//sonar fix
							default:
								break;
							}
														
						}
					}

				}

			}else
			{
				logger.debug("RiskGroupDetails for Section is nul_6"+sectionVo.getSectionId());
			}
			}
			
		}
		 // Added for Bahrain decimal
		if(isSBSBahrainPolicy(policy)) {
			totalPremium= roundToThreeDecimal(totalPremium);
		}
		else {
			totalPremium= roundToTwoDecimal(totalPremium);
		}
		
		premiumVo.setPremiumAmt( totalPremium);
		policy.setPremiumVO(premiumVo);
		return policy;
	}

	private PolicyVO setPremium( PolicyVO policy, String riskUniqId, Premium ratingEnginePremium,String sectionIdToUpdate ){
		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if(sectionIdToUpdate.equals(sectionId)){
				//For PAR
				if(!Utils.isEmpty(sectionVo) && !Utils.isEmpty(sectionVo.getRiskGroupDetails())){
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
					RiskGroup locationInfo = entry.getKey();
					
					
					if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
							&& !Utils.isEmpty( entry.getValue() )){
						RiskGroupDetails riskDetails = entry.getValue();
						/*LocationVO loc=(LocationVO)locationInfo;*/
						//ParVO parVO = (ParVO) riskDetails;
						
						PremiumVO premiumVo = new PremiumVO();
						double premiumToAdd= Double.valueOf( 0 );
						if((!Utils.isEmpty(riskDetails.getPremium())) &&(!Utils.isEmpty(riskDetails.getPremium().getPremiumAmt()))){

					         //Added for Bahrain 3 decimal - Starts
                            if (isSBSBahrainPolicy(policy)) {
                            premiumToAdd = riskDetails.getPremium().getPremiumAmt()+ roundToNDecimal(ratingEnginePremium.getBasePremium(), 3);    
                            }else {
                              premiumToAdd=riskDetails.getPremium().getPremiumAmt()+ roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
                                            //premiumToAdd=ratingEnginePremium.getBasePremium();

                            }
                            //Added for Bahrain 3 decimal - Ends
                            logger.debug("Rating:PremiumMapper:Existing Premium Am_3"+riskDetails.getPremium().getPremiumAmt());
							logger.debug("Rating:PremiumMapper:Premium Amt from Rating Engin_3"+ratingEnginePremium.getBasePremium());
							logger.debug("Rating:PremiumMapper:Total Premium Amt For SectionID:_3" +sectionIdToUpdate + "is :"+ premiumToAdd);
							
						}else{
							 //Added for Bahrain 3 decimal - Starts
							if (isSBSBahrainPolicy(policy)) {
								premiumToAdd = roundToNDecimal(ratingEnginePremium.getBasePremium(), 3);
							}
							else {
								premiumToAdd=roundToTwoDecimal(ratingEnginePremium.getBasePremium(),2);
							}
							 //Added for Bahrain 3 decimal - Ends
							
						}
						
						 //Added for Bahrain 3 decimal - Starts
								
						
						if (isSBSBahrainPolicy(policy)) {
							 premiumToAdd = roundToThreeDecimal( premiumToAdd );
						}
						else {
							premiumToAdd = roundToTwoDecimal( premiumToAdd );
						}
						//Added for Bahrain 3 decimal - Ends
						
						premiumVo.setPremiumAmt( premiumToAdd );
						premiumVo.setPremiumAmtActual( premiumToAdd );
								
						riskDetails.setPremium( premiumVo );

					}

				}
				
				

			}else
			{
				logger.debug("RiskGroupDetails for Section is nul_7"+sectionVo.getSectionId());
			}
			}//Section check
			}

		}
		return policy;
	}
	

	private PolicyVO setPremiumForGIT( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate, String gitItemNumber){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_GIT)))){
							GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) riskDetails;
							if(!Utils.isEmpty( goodsInTransitVO )){
								 List<CommodityDetailsVO> commodityDetailsVOs = goodsInTransitVO.getCommodityDtls();
								 int gitRowNumber = 0;
								 for(CommodityDetailsVO commodityDetailsVO : commodityDetailsVOs){
									 if(gitRowNumber == Integer.parseInt(gitItemNumber)){
										 PremiumVO premiumVo = new PremiumVO();
										 double premiumToAdd= Double.valueOf( 0 );
										 //Added for Bahrain 3 decimal - Starts
				                            if (isSBSBahrainPolicy(policy)) {
				                            	 premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
												 premiumToAdd = roundToThreeDecimal(premiumToAdd);
				                            }
				                            else {
				                            	 premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
												 premiumToAdd = roundToTwoDecimal(premiumToAdd);
				                            }
				                            //Added for Bahrain 3 decimal - Ends
										 premiumVo.setPremiumAmt( premiumToAdd);
										 commodityDetailsVO.setPremium( premiumVo );
										//gitRowNumber++;
									 }
									 gitRowNumber++;
								 }
							}
							
						 }// End: riskDetails Empty check 
							}
						}

					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_8" + sectionVo.getSectionId() );
					}
				}//Section check
			}

		}
		return policy;

	}
	private PolicyVO setPremiumForGPA( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate,String gpaUnnamedItemNumber, String  gpaNamedItemNumber ){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_GPA)))){
							GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) riskDetails;
							if(!Utils.isEmpty( groupPersonalAccidentVO )){
								if(!Utils.isEmpty( gpaUnnamedItemNumber)) {
									 List<GPAUnnammedEmpVO> unnammedEmpVOs = groupPersonalAccidentVO.getGpaUnnammedEmpVO();
									 int gpaUnnamedRowNumber = 0;
									 for(GPAUnnammedEmpVO unnammedEmpVO : unnammedEmpVOs){
										 if(gpaUnnamedRowNumber == Integer.parseInt(gpaUnnamedItemNumber)){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											 //Added for Bahrain 3 decimal - Starts
					                            if (isSBSBahrainPolicy(policy)) {
					                            	 premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	 premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                            //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 unnammedEmpVO.setPremium( premiumVo );
											 //gpaUnnamedRowNumber++;
										 }
										 gpaUnnamedRowNumber++;
									 }
								}
								if(!Utils.isEmpty( gpaNamedItemNumber)) {
									 List<GPANammedEmpVO> nammedEmpVOs = groupPersonalAccidentVO.getGpaNammedEmpVO();
									 int gpaNamedRowNumber = 0;
									 for(GPANammedEmpVO nammedEmpVO : nammedEmpVOs){
										 if(gpaNamedRowNumber == Integer.parseInt(gpaNamedItemNumber)){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											 //Added for Bahrain 3 decimal - Starts
					                            if (isSBSBahrainPolicy(policy)) {
					                            	 premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	 premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                            //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 nammedEmpVO.setPremium( premiumVo );
											 //gpaNamedRowNumber++;
										 }
										 gpaNamedRowNumber++;
									 }
								}

							}
							
						 }// End: riskDetails Empty check 
							}
						}

					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_9" + sectionVo.getSectionId() );
					}
				}//Section check
			}

		}
		return policy;

	}

/**
	 * Rerutn's the premium filled PolicyVO, for each contents into EmpTypeDetailsVO list of WCVO for WC section
	 */
	private PolicyVO setPremiumForWC( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate, String empTypeNumber ){
		

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if(sectionIdToUpdate.equals(sectionId)){
				//For PAR
				if(!Utils.isEmpty(sectionVo) && !Utils.isEmpty(sectionVo.getRiskGroupDetails())){
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
					RiskGroup locationInfo = entry.getKey();

					if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
							&& !Utils.isEmpty( entry.getValue() )){
						RiskGroupDetails riskDetails = entry.getValue();
						/*LocationVO loc=(LocationVO)locationInfo;*/
						
						// Start: riskDetails Empty check 
						if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_WC)))){
							WCVO wcvo = (WCVO) riskDetails;
							if(!Utils.isEmpty( wcvo )){
								 List<EmpTypeDetailsVO> empTypeDetailsVOs = wcvo.getEmpTypeDetails();
								 int empTypeRowNumber = 0;
								 for(EmpTypeDetailsVO empTypeDetailsVO : empTypeDetailsVOs){
									 
									 /* Check if the content is not added then skip the execution process for that content */
										if(  !Utils.isEmpty( empTypeDetailsVO.getEmpType() ) && empTypeDetailsVO.getEmpType().equals( CommonConstants.DEFAULT_LOW_LONG ) && isContentSumInsuredEmpty(empTypeDetailsVO) ){
											empTypeDetailsVO.setEmpType( null );
										}
										if( Utils.isEmpty( empTypeDetailsVO.getEmpType() ) || isContentSumInsuredEmpty(empTypeDetailsVO) ){
											continue;
										}
										
									 if(empTypeRowNumber == Integer.parseInt(empTypeNumber)){
										 PremiumVO premiumVo = new PremiumVO();
										 double premiumToAdd= Double.valueOf( 0 );
										//Added for Bahrain 3 decimal - Start
				                            if (isSBSBahrainPolicy(policy)) {
				                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
												 premiumToAdd = roundToThreeDecimal(premiumToAdd);
				                            }
				                            else {
				                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
												 premiumToAdd = roundToTwoDecimal(premiumToAdd);
				                            }
				                          //Added for Bahrain 3 decimal - Ends
										 premiumVo.setPremiumAmt( premiumToAdd);
										 empTypeDetailsVO.setPremium( premiumVo );										 
									 }
									 empTypeRowNumber++;
								 }
							}
							
						 }// End: riskDetails Empty check 
					}
				}

			}else
			{
				logger.debug("RiskGroupDetails for Section is nul_10"+sectionVo.getSectionId());
			}
			}//Section check
			}
			
		}
		return policy;
	
	}
	private PolicyVO setPremiumForFID( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate,String fidUnnamedItemNumber, String  fidNamedItemNumber ){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_FID)))){
									FidelityVO fidelityDetails = (FidelityVO) riskDetails;
							if(!Utils.isEmpty( fidelityDetails )){
								if(!Utils.isEmpty( fidUnnamedItemNumber)) {
									 List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeeVOs = fidelityDetails.getUnnammedEmployeeDetails();
									 int fidUnnamedRowNumber = 0;
									 for(FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO : fidelityUnnammedEmployeeVOs){
										 if(fidUnnamedRowNumber == Integer.parseInt(fidUnnamedItemNumber)){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											//Added for Bahrain 3 decimal - Start
					                            if (isSBSBahrainPolicy(policy)) {
					                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                          //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 fidelityUnnammedEmployeeVO.setPremium( premiumVo );
											 //gpaUnnamedRowNumber++;
										 }
										 fidUnnamedRowNumber++;
									 }
								}
								if(!Utils.isEmpty( fidNamedItemNumber)) {
									 List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeeDetailsVOs = fidelityDetails.getFidelityEmployeeDetails();
									 int fidNamedRowNumber = 0;
									 for(FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO : fidelityNammedEmployeeDetailsVOs){
										 if(fidNamedRowNumber == Integer.parseInt(fidNamedItemNumber)){
											 PremiumVO premiumVo = new PremiumVO();
											 double premiumToAdd= Double.valueOf( 0 );
											//Added for Bahrain 3 decimal - Start
					                            if (isSBSBahrainPolicy(policy)) {
					                            	premiumToAdd = roundToNDecimal(premium.getBasePremium(),3);
													 premiumToAdd = roundToThreeDecimal(premiumToAdd);
					                            }
					                            else {
					                            	premiumToAdd = roundToTwoDecimal(premium.getBasePremium(),2);
													 premiumToAdd = roundToTwoDecimal(premiumToAdd);
					                            }
					                          //Added for Bahrain 3 decimal - Ends
											 premiumVo.setPremiumAmt( premiumToAdd);
											 fidelityNammedEmployeeDetailsVO.setPremium( premiumVo );
											 //gpaNamedRowNumber++;
										 }
										 fidNamedRowNumber++;
									 }
								}
 
							}
							
						 }// End: riskDetails Empty check 
							}
						}

					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_11" + sectionVo.getSectionId() );
					}
				}//Section check
			}

		}
		return policy;

	}

	private void setPremiumForFIDAggregate( PolicyVO policy, String riskUniqId, Premium premium, String sectionIdToUpdate){

		List<SectionVO> sectionList = policy.getRiskDetails();
		if( !Utils.isEmpty( sectionList ) ){
			for( SectionVO sectionVo : sectionList ){
				String sectionId = sectionVo.getSectionId().toString();
				if( sectionIdToUpdate.equals( sectionId ) ){
					//For PAR
					if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getRiskGroupDetails() ) ){
						Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVo.getRiskGroupDetails();
						for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
							RiskGroup locationInfo = entry.getKey();

							if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && locationInfo.getRiskGroupId().equalsIgnoreCase( riskUniqId )
									&& !Utils.isEmpty( entry.getValue() ) ){
								RiskGroupDetails riskDetails = entry.getValue();
								/*LocationVO loc=(LocationVO)locationInfo;*/

								// Start: riskDetails Empty check 
								if((!Utils.isEmpty(riskDetails))&&(sectionIdToUpdate.equals(Integer.toString(SvcConstants.SECTION_ID_FID)))){
									FidelityVO fidelityDetails = (FidelityVO) riskDetails;
							if(!Utils.isEmpty( fidelityDetails )){
								//Added for Bahrain 3 decimal - Starts
	                            if (isSBSBahrainPolicy(policy)) {
	                            	fidelityDetails.setFidAggregateBasePremium(roundToNDecimal(premium.getBasePremium(),3));
									fidelityDetails.setFidAggregateMonthlyPremium(roundToNDecimal(premium.getMonthlyPremium(),3));
									fidelityDetails.setFidAggregateQuaterlyPremium(roundToNDecimal(premium.getQuaterlyPremium(),3));
									fidelityDetails.setFidAggregateTermPremium(roundToNDecimal(premium.getTermPremium(),3));
	                            }
	                            else {
	                            	fidelityDetails.setFidAggregateBasePremium(roundToTwoDecimal(premium.getBasePremium(),2));
									fidelityDetails.setFidAggregateMonthlyPremium(roundToTwoDecimal(premium.getMonthlyPremium(),2));
									fidelityDetails.setFidAggregateQuaterlyPremium(roundToTwoDecimal(premium.getQuaterlyPremium(),2));
									fidelityDetails.setFidAggregateTermPremium(roundToTwoDecimal(premium.getTermPremium(),2));
	                            }
	                            
	                          //Added for Bahrain 3 decimal - Ends
								}
								}
								
								
							}
							
						 }// End: riskDetails Empty check 
							}
					
					}
					else{
						logger.debug( "RiskGroupDetails for Section is nul_12" + sectionVo.getSectionId() );
					}
				}//Section check
			}
		

	}
	/**
	 * 
	 * @param empTypeDetailsVO
	 * @return
	 */
	private boolean isContentSumInsuredEmpty( EmpTypeDetailsVO empTypeDetailsVO ){
		
		if( Utils.isEmpty( empTypeDetailsVO.getWageroll() ) || empTypeDetailsVO.getWageroll() ==  0.0){
			return true;
		}		
		return false;
	}
	
	/**
	 * Method to calculate the minimum premium 
	 * 
	 * @param policy
	 */
	private void handleSectionLevelMinPremium( PolicyVO policy ){
		
		
		if(!Utils.isEmpty( policy ) && !Utils.isEmpty( policy.getScheme() ) && !Utils.isEmpty( policy.getScheme().getTariffCode() )
				&& policy.getScheme().getTariffCode() == Integer.valueOf( Utils.getSingleValueAppConfig( "JAZFA_TARIFF" ) ).intValue() ){
			SectionVO wcSectionVo = PolicyUtils.getSectionVO( policy, SvcConstants.SECTION_ID_WC ) ;
			
			if(!Utils.isEmpty( wcSectionVo )){
				
				if(!Utils.isEmpty(wcSectionVo.getRiskGroupDetails())){
					
					Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = wcSectionVo.getRiskGroupDetails();
					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
						RiskGroup locationInfo = entry.getKey();
						
						
						if( !Utils.isEmpty( locationInfo.getRiskGroupId() ) && !Utils.isEmpty( entry.getValue() )){
							RiskGroupDetails riskDetails = entry.getValue();
							
							WCVO wcVo = (WCVO)riskDetails;
							
							if(!Utils.isEmpty( wcVo )){
								PremiumVO wcPremium = wcVo.getPremium();
								
								if(!Utils.isEmpty( wcPremium )){
									if(!Utils.isEmpty( wcPremium ) && wcPremium.getPremiumAmt() < Double.valueOf(Utils.getSingleValueAppConfig( com.Constant.CONST_JAZFA_WC_MIN_PREMIUM ))){
										Double ratingTotalPremium = wcPremium.getPremiumAmt(); 
										
										wcPremium.setPremiumAmt( Double.valueOf(Utils.getSingleValueAppConfig( com.Constant.CONST_JAZFA_WC_MIN_PREMIUM )) );
										wcPremium.setPremiumAmtActual( Double.valueOf(Utils.getSingleValueAppConfig( com.Constant.CONST_JAZFA_WC_MIN_PREMIUM )) );
										
										List<EmpTypeDetailsVO> empTypeDetailsVOs = wcVo.getEmpTypeDetails();
										
										for(EmpTypeDetailsVO emplDetailsVO:empTypeDetailsVOs){
											PremiumVO empPremium = emplDetailsVO.getPremium();
											
											if(!Utils.isEmpty( empPremium )){
												empPremium.setPremiumAmt( Double.valueOf(Utils.getSingleValueAppConfig( com.Constant.CONST_JAZFA_WC_MIN_PREMIUM )) * (empPremium.getPremiumAmt())/(ratingTotalPremium) );
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		}
		
	}
	
    // Added for Bahrain decimal
    private float roundToNDecimal(float Rval, int Rpl) {
                    if (Rval == 0) {
                                    return Rval;
                    }
                    float p = (float) Math.pow(10, Rpl);
                    Rval = Rval * p;
                    float tmp = Math.round(Rval);
                    return (float) tmp / p;
    }

    private double roundToThreeDecimal(double premiumToAdd) {
                    if (premiumToAdd == 0) {
                                    return premiumToAdd;
                    }
                    return (double) (Math.round(premiumToAdd * 1000)) / 1000;
    }
    private boolean isSBSBahrainPolicy(PolicyVO policy) {
    	
    	Integer policyType=0;
        policyType = Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
        if (policyType == Integer.valueOf(policy.getScheme().getPolicyType())
                && Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)
                .equalsIgnoreCase("50")) {
        	return true;
        }
        return false;
    }


}
