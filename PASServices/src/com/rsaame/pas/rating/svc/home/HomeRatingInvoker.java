/**
 * 
 */
package com.rsaame.pas.rating.svc.home;

import java.math.BigDecimal;
import com.cts.writeRate.Coverage;
import com.cts.writeRate.Factor;
import com.cts.writeRate.Item;
import com.cts.writeRate.Policy;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;

/**
 * @author M1014644
 *
 */
public class HomeRatingInvoker extends RatingServiceInvoker{

	
	private static final Logger LOGGER = Logger.getLogger( HomeRatingInvoker.class );
	/* (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.RatingServiceInvoker#invokeRatingForPremium(com.cts.writeRate.Policy[], com.rsaame.pas.vo.bus.PolicyDataVO)
	 * 
	 * Details for the second request has been set in the following method
	 */ 
	@Override
	protected Policy[] invokeRatingForPremium( Policy[] coverDetails, PolicyDataVO policyDataVO ){
		
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) policyDataVO;

		if( !Utils.isEmpty( coverDetails ) ){
			for( Policy policy : coverDetails ){
				//service name for the next request has been set here
				policy.setService( SvcConstants.RATING_SERVICE_GET_RATE );

				if( !Utils.isEmpty( policy.getItems() ) ){
					for( Item item : policy.getItems() ){

						if( !Utils.isEmpty( item.getCoverages() ) ){
							for( Coverage coverage : item.getCoverages() ){

								if( !Utils.isEmpty( coverage.getCoverageFactors() ) ){
									for( Factor factor : coverage.getCoverageFactors() ){

										String derivedFactorName = factor.getFactorName().trim().replaceAll( "\\s+", "_" );

										switch( HomeFactorNames.valueOf( derivedFactorName ) ){
											case Home_Building_Rate:
												break;
											case Home_Building_SI:
												getHomeBuildingSI( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_Content_Rate:
												break;
											case Home_Content_SI:
											case Home_Content_Range:
												getHomeContentDetails( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_EL_EMP:
												getHomeELDetail( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_EL_Flag:
												getHomeELFlag( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_EL_Rate:
												break;
											case Home_LOD_Flag:
												getHomeLODFlag( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_LOD_Rate:
												break;
											case Home_PB_Range:
											case Home_PB_SI:
												getHomePBDetails( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_TLL_Limit:
												getHomeTLLLimit( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_TLL_Rate:
												break;
											case Home_SC_SI:
												getHomeScRate( homeInsuranceVO, factor, derivedFactorName );
												break;
											case Home_SPB_SI:
												getHomeSppRate( homeInsuranceVO, factor, derivedFactorName );
												break;
											default:
										}
									}
								}
							}
						}
					}
				}
			}
		}
		try{
			coverDetails = ratingInvoker.getPremiumForPolicy( coverDetails );
		}
		catch( ErateException e ){
			LOGGER.error( "Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null" );

			throw new BusinessException( "rating.invocation.no.loc",e, "riskGroupDetailsMap.entrySet() is null" );

		}

		return coverDetails;
	}

	private void getHomeScRate( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		long totalScSi = 0L;
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){

			if( !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode() != 0 ) ){
				if( ( ( SvcConstants.CONTENT_RISK_TYPE_CODE ) ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
						&& ( SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode() )
						&& ( SvcConstants.CONTENT_SUB_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						//factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
						totalScSi += coverDetailsVO.getSumInsured().getSumInsured().longValue();
					}
				}
			}
		}
		
		if( totalScSi > 0L ){
			factor.setFactorValue( String.valueOf( totalScSi ) );
			ThreadLevelContext.set( "TLC_KEY_SCSI", totalScSi );
		}
		
		if(Utils.isEmpty( factor.getFactorValue() )){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_1"+factor.getFactorName()+"1. --  " +factor.getFactorValue());
	
		
	}

	private void getHomeSppRate( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType is 32, then get sumInsured
		long totalSppSi = 0L;
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			if( !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) ){

				if( ( ( SvcConstants.PP_RSK_TYPE_CODE ) ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
						&& ( SvcConstants.PP_SUB_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						// factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
						totalSppSi += coverDetailsVO.getSumInsured().getSumInsured().longValue();
					}
				}
			}
		}
		
		if( totalSppSi > 0L ){
			factor.setFactorValue( String.valueOf( totalSppSi ) );
			ThreadLevelContext.set( "TLC_KEY_SPPSI", totalSppSi );
		}
		
		if( Utils.isEmpty( factor.getFactorValue() ) ){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info( "Factor Detail : --_2" + factor.getFactorName() + "2.  --  " + factor.getFactorValue() );
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeTLLLimit( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType == 31 and coverCode == 4, getSumInsured 
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode()!=0 )){
				if( ( ( SvcConstants.CONTENT_RISK_TYPE_CODE )).equals( coverDetailsVO.getRiskCodes().getRiskType() )
						&& (   SvcConstants.TLL_COVER_CODE==(coverDetailsVO.getCoverCodes().getCovCode() )  ) ){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
					}
					if( coverDetailsVO.getSumInsured().isPromoCover()){
						factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
					}
				}
			}
		}
		if(Utils.isEmpty( factor.getFactorValue() )){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_3"+factor.getFactorName()+"3.  --  " +factor.getFactorValue());
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomePBDetails( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType is 32, then get sumInsured
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) ){

				if( (( SvcConstants.PP_RSK_TYPE_CODE )).equals( coverDetailsVO.getRiskCodes().getRiskType()  ) 
						&& (SvcConstants.PP_MAIN_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ))){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
					}
				}
			}
		}
		if(Utils.isEmpty( factor.getFactorValue() )){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_4"+factor.getFactorName()+"4.  --  " +factor.getFactorValue());
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeLODFlag( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType == 31 and coverCode == 2, if it is Y else N
		boolean found = false;
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode()!=0 )){
				if((SvcConstants.CONTENT_RISK_TYPE_CODE) .equals( coverDetailsVO.getRiskCodes().getRiskType() )
						&& (   SvcConstants.LOD_COVER_CODE == ( coverDetailsVO.getCoverCodes().getCovCode() ) )  ) {
					if( coverDetailsVO.getSumInsured().isPromoCover() || !SvcConstants.STATUS_ON.equals( coverDetailsVO.getIsCovered() ) ){
						factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
					}
					else{
						factor.setFactorValue( "Y" );	
					}
					found = true;
					break;
				}
			}
		}
		if( !found ){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_5"+factor.getFactorName()+"5.  --  " +factor.getFactorValue());	
		
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeELFlag( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType == 31 and coverCode == 3, if eDesc not null Y else N 
		boolean found = false;
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode()!=0 )){

			if(  (( SvcConstants.CONTENT_RISK_TYPE_CODE )) .equals( coverDetailsVO.getRiskCodes().getRiskType() )
					&& (   SvcConstants.EL_COVER_CODE == ( coverDetailsVO.getCoverCodes().getCovCode() )   ) ){
				if( !Utils.isEmpty( coverDetailsVO.getSumInsured().geteDesc() ) ){
					if( coverDetailsVO.getSumInsured().isPromoCover()){
						factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
					}
					else{
						factor.setFactorValue( "Y" );
					}
					found = true;
					break;
				}
			}
			}
		}
		if( !found ){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_6"+factor.getFactorName()+"6.  --  " +factor.getFactorValue());
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeELDetail( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		// if riskType is 31 and coverCode is 3
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
		if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode()!=0 )){

			if( (( SvcConstants.CONTENT_RISK_TYPE_CODE ) ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
					&& (   (  SvcConstants.EL_COVER_CODE )==(coverDetailsVO.getCoverCodes().getCovCode() ) ) ){
				if( !Utils.isEmpty( coverDetailsVO.getSumInsured().geteDesc() ) ){
					factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().geteDesc() ) );
				}
				if( coverDetailsVO.getSumInsured().isPromoCover()){
					factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
				}
			}
		}
		}
		if(Utils.isEmpty( factor.getFactorValue() )){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_7"+factor.getFactorName()+"7.  --  " +factor.getFactorValue());
		
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeContentDetails( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode()!= 0 )){
				if(( ( SvcConstants.CONTENT_RISK_TYPE_CODE )).equals(  coverDetailsVO.getRiskCodes().getRiskType() ) 
						&& ( SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode()  ) 
						 && ( SvcConstants.CONTENT_MAIN_RISK_CATEGORY.equals(coverDetailsVO.getRiskCodes().getRiskCat()))){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
					}
				}
			}
		}
		if(Utils.isEmpty( factor.getFactorValue() )){
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_8"+factor.getFactorName()+"8.  --  " +factor.getFactorValue());
	}

	/**
	 * @param homeInsuranceVO
	 * @param factor
	 * @param derivedFactorName
	 */
	private void getHomeBuildingSI( HomeInsuranceVO homeInsuranceVO, Factor factor, String derivedFactorName ){
		if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) && 
				!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured() ) &&
					!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) ){
			factor.setFactorValue( String.valueOf( (homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured().longValue()) ) );
		}
		else{
			factor.setFactorValue( HomeFactorNames.valueOf( derivedFactorName ).getDefaultValue() );
		}
		LOGGER.info("Factor Detail : --_9"+factor.getFactorName()+"9.  --  " +factor.getFactorValue());
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.RatingServiceInvoker#mapPremiumToVo(com.cts.writeRate.Policy[], com.rsaame.pas.vo.bus.PolicyDataVO)
	 * 
	 *  This method maps the premium details to the policy data VO
	 */
	@Override
	protected void mapPremiumToVo( Policy[] premiumDetails, PolicyDataVO policyDataVO ){
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) policyDataVO;

		short coverCode;
		Integer riskType = null;
		
		String coverageCode = null;

		for( Policy policy : premiumDetails ){

			for( Item item : policy.getItems() ){

				for( Coverage coverage : item.getCoverages() ){
					//Extracting coverageCode , cover 
					coverageCode = coverage.getCoverageCode();
					coverCode = Short.valueOf( coverageCode.substring( 4, 7 ) );
					Integer riskCat= null;
					if ( coverageCode.length() > 18 ){
						riskCat = Integer.valueOf( coverageCode.substring( 13 ) .substring(5) );
						riskType = Integer.valueOf( coverageCode.substring( 13 ).substring(3,5) );
					}else{
						riskCat =  SvcConstants.CONTENT_MAIN_RISK_CATEGORY ;
						riskType = Integer.valueOf( coverageCode.substring( 13 ) );
					}
					
					Double prmPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( new BigDecimal( coverage.getPremium().getBasePremium() ), homeInsuranceVO
							.getCommonVO().getLob().name() ) );
					LOGGER.info( "Coverage Code ::_1" + coverageCode + " -- cover code ::_1" + coverCode + " -- risk type :_1" + riskType + "Premium :" +  prmPremium );

					// building sum insured detail
					if( coverCode == SvcConstants.DEFAULT_HOME_COVER_CODE && riskType.equals( SvcConstants.BUILDING_RISK_TYPE_CODE ) ){

						if(!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured()) 
								&& !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() )
								&& (Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) 
										|| (homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() > 0) ) ){
							homeInsuranceVO.getBuildingDetails().setPremiumAmt( prmPremium );
							homeInsuranceVO.getBuildingDetails().setPremiumAmtActual( prmPremium );
							LOGGER.info( "Coverage Code ::_2" + coverageCode + " -- cover code ::_2" + coverCode + " -- risk type :_2" + riskType +"SI: "+ 
							homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() + "Premium :" +  prmPremium );
						}
					}
					else{
						for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){

							if( ( coverDetailsVO.getCoverCodes().getCovCode() ) == coverCode && ( coverDetailsVO.getRiskCodes().getRiskType().equals( riskType ) )
									&& ( riskCat.equals( SvcConstants.CONTENT_MAIN_RISK_CATEGORY ) )
									&& coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.CONTENT_MAIN_RISK_CATEGORY ) ){
								if( !Utils.isEmpty( coverDetailsVO.getSumInsured() ) ){
									if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) && ( coverDetailsVO.getSumInsured().getSumInsured() > 0 )
											&& coverCode == SvcConstants.DEFAULT_HOME_COVER_CODE ){
										coverDetailsVO.setPremiumAmt( prmPremium );
										coverDetailsVO.setPremiumAmtActual( prmPremium );
										LOGGER.info( "Coverage Code ::_3" + coverageCode + " -- cover code ::_3" + coverCode + " -- risk type :_3" + riskType + "SI: "
												+ coverDetailsVO.getSumInsured() + com.Constant.CONST_PREMIUM_END + prmPremium );
									}

									else if( ( Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) || ( coverDetailsVO.getSumInsured().getSumInsured() > 0 ) )
											&& ( coverCode == SvcConstants.EL_COVER_CODE || coverCode == SvcConstants.LOD_COVER_CODE || coverCode == SvcConstants.TLL_COVER_CODE ) ){
										coverDetailsVO.setPremiumAmt( prmPremium );
										coverDetailsVO.setPremiumAmtActual( prmPremium );
										LOGGER.info( "Coverage Code ::_4" + coverageCode + " -- cover code ::_4" + coverCode + " -- risk type :_4" + riskType + "SI: "
												+ coverDetailsVO.getSumInsured() + com.Constant.CONST_PREMIUM_END + prmPremium );
									}
									else{
										coverDetailsVO.setPremiumAmt( 0 );
										coverDetailsVO.setPremiumAmtActual( 0 );
									}
								}
								else{
									coverDetailsVO.setPremiumAmt( 0 );
									coverDetailsVO.setPremiumAmtActual( 0 );
								}

							}

							//Map the premium for the specific contents
							else if( ( coverDetailsVO.getCoverCodes().getCovCode() ) == coverCode && ( coverDetailsVO.getRiskCodes().getRiskType().equals(riskType) ) && ( coverDetailsVO.getRiskCodes().getRiskType().equals( ( SvcConstants.CONTENT_RISK_TYPE_CODE ))
									&& ( coverDetailsVO.getRiskCodes().getRiskCat().equals(riskCat) ) && SvcConstants.CONTENT_SUB_RISK_CATEGORY.equals(  coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
								if( !Utils.isEmpty( coverDetailsVO.getSumInsured() ) && !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() )
										|| ( coverDetailsVO.getSumInsured().getSumInsured() > 0 ) ){
									Long totalScSi = (Long) ThreadLevelContext.get( "TLC_KEY_SCSI" );
									if( Utils.isEmpty( totalScSi ) ){
										throw new BusinessException( "", null, "Total SI of specific contents is null, Unable to proportionate premium"  );
									}
									double scSpecificPrm = ( prmPremium * coverDetailsVO.getSumInsured().getSumInsured().floatValue() )
											/ totalScSi.floatValue();
									coverDetailsVO.setPremiumAmt( scSpecificPrm );
									coverDetailsVO.setPremiumAmtActual( scSpecificPrm );
									LOGGER.info( "Coverage Code ::_5" + coverageCode + " -- cover code ::_5" + coverCode + " -- risk type :_5" + riskType +"SI: "+ coverDetailsVO.getSumInsured() +" Premium _1" +  scSpecificPrm );
								}
								else{
									coverDetailsVO.setPremiumAmt( 0 );
									coverDetailsVO.setPremiumAmtActual( 0 );
								}

							}

							//Map the premium for the specific personal possession 
							else if( ( coverDetailsVO.getCoverCodes().getCovCode() ) == coverCode && ( coverDetailsVO.getRiskCodes().getRiskType().equals(riskType) ) && ( coverDetailsVO.getRiskCodes().getRiskType().equals( ( SvcConstants.PP_RSK_TYPE_CODE ))
									&& ( coverDetailsVO.getRiskCodes().getRiskCat().equals(riskCat) ) && SvcConstants.PP_SUB_RISK_CATEGORY.equals(  coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){

								if( !Utils.isEmpty( coverDetailsVO.getSumInsured() ) && !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() )
										|| ( coverDetailsVO.getSumInsured().getSumInsured() > 0 ) ){
									Long totalSppSi = (Long) ThreadLevelContext.get( "TLC_KEY_SPPSI" );
									if( Utils.isEmpty( totalSppSi ) ){
										throw new BusinessException( "", null, "Total SI of specific personal Possession   is null, Unable to proportionate premium"  );
									}
									double sppSpecificPrm = ( prmPremium * coverDetailsVO.getSumInsured().getSumInsured().floatValue() )
											/ totalSppSi.floatValue();
									coverDetailsVO.setPremiumAmt( sppSpecificPrm );
									coverDetailsVO.setPremiumAmtActual( sppSpecificPrm );
									LOGGER.info( "Coverage Code ::_6" + coverageCode + " -- cover code ::_6" + coverCode + " -- risk type :_6" + riskType +"SI: "+ coverDetailsVO.getSumInsured() +" Premium _2" +  sppSpecificPrm );
								}
								else{
									coverDetailsVO.setPremiumAmt( 0 );
									coverDetailsVO.setPremiumAmtActual( 0 );
								}

							}

						}
					}
				}
			}
		}
		
		// Setting package premium amount
		if(Utils.isEmpty( homeInsuranceVO.getPremiumVO() )){
			PremiumVO premiumVO = new PremiumVO();
			homeInsuranceVO.setPremiumVO( premiumVO );
		}
		
		populatePackagePremium( homeInsuranceVO );
		LOGGER.info( "Policy Package Premium -- "+homeInsuranceVO.getPremiumVO().getPremiumAmt() );
		
	}
	
	/**
	 * @param homeInsuranceVO
	 * calculates the package premium that need to be displayed in home risk page
	 */
	private void populatePackagePremium( HomeInsuranceVO homeInsuranceVO ){
		
		double packagePremium =  0;
		double discountedPremium = 0;
		
		if(!Utils.isEmpty( homeInsuranceVO )){
			if(!Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) ){
				packagePremium += homeInsuranceVO.getBuildingDetails().getPremiumAmt();
			}
			
			for(CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()){
				packagePremium += coverDetailsVO.getPremiumAmt();
			}
			
			if(Utils.isEmpty( homeInsuranceVO.getPremiumVO() )){
				homeInsuranceVO.setPremiumVO( new PremiumVO() );
			}
			
		//	discountedPremium = SvcUtils.getPromoDiscount( packagePremium, homeInsuranceVO.getPremiumVO().getPromoDiscPerc() );
			
			homeInsuranceVO.getPremiumVO().setPremiumAmt( packagePremium );
			homeInsuranceVO.getPremiumVO().setPremiumAmtActual( packagePremium );
		}
		
		//Setting back the premiumVO to the CommonVO for Future use
		if(!Utils.isEmpty( homeInsuranceVO.getCommonVO())){

			homeInsuranceVO.getCommonVO().setPremiumVO( homeInsuranceVO.getPremiumVO() );
		}
		
	}

}
