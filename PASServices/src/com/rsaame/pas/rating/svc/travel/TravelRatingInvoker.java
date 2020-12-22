package com.rsaame.pas.rating.svc.travel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cts.writeRate.Coverage;
import com.cts.writeRate.Factor;
import com.cts.writeRate.Item;
import com.cts.writeRate.Policy;
import com.cts.writeRate.Premium;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.rating.svc.TravelFactorName;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

public class TravelRatingInvoker extends RatingServiceInvoker{
	private static final String TRAVELLER_INDEX = "travellerIndex";
	private final static Logger logger = Logger.getLogger( RatingServiceInvoker.class );
	private static final String TRAVEL_INSURANCE = "travelInsurance";
	private static final String TRAVEL_DETAILS = "travelerDetails";
	private static final int MAX_TRAVELLER_LOAD = Integer.valueOf( Utils.getSingleValueAppConfig( "MAX_TRAVELLER_LOAD" ).trim() );
	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.RatingServiceInvoker#invokeRatingForPremium(com.cts.writeRate.Policy[], com.rsaame.pas.vo.bus.PolicyDataVO)
	 */
	@Override
	protected Policy[] invokeRatingForPremium( Policy[] coverDetails, PolicyDataVO policyDataVO ){
		logger.info( "Entering invokeRatingForPremium" );
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) policyDataVO;
		
		//if policyTerm for annual trip should be set it to 181
		if(travelInsuranceVO.getPolicyType().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" ) ) )){
			travelInsuranceVO.setPolicyTerm( Integer.valueOf(Utils.getSingleValueAppConfig( "ANNUAL_TRIP_DAYS_FOR_RATING" )) );
		}
		
		TravelDetailsVO travelDetailsVO = travelInsuranceVO.getTravelDetailsVO();
		List<TravelerDetailsVO> travelerDetailsList = travelDetailsVO.getTravelerDetailsList();

		List<Policy> policyList = new ArrayList<Policy>( 0 );

		Map<BigDecimal, List<Integer>> travellerIndex = new HashMap<BigDecimal, List<Integer>>( 0 );

		ThreadLevelContext.set( TRAVEL_DETAILS, travelDetailsVO );
		ThreadLevelContext.set( TRAVEL_INSURANCE, travelInsuranceVO );
		/*Iterate over the policy object and set the factor values */
		int j = 0;
		for( Policy policy : coverDetails ){

			/*Iterate over the items and set the factor values for each item */

			for( TravelerDetailsVO travellersDetails : travelerDetailsList ){
				Date dateOfBirth = travellersDetails.getDateOfBirth();
				Short age = null;
				if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getEffDate() ) ){
					age = SvcUtils.getAge( dateOfBirth, travelInsuranceVO.getScheme().getEffDate() );
				}
				Short CHILD_MIN_AGE = Short.valueOf( Utils.getSingleValueAppConfig( "CHILD_MIN_AGE" ).trim() );

				if(age!= null &&  age>CHILD_MIN_AGE){
					Policy modifiedPolicy = CopyUtils.copySerializableObject( policy );
					List<Item> itemForTravellers = new ArrayList<Item>( 0 );
					int i = 0;
					Item[] items = modifiedPolicy.getItems();
	
					List<Integer> index = new ArrayList<Integer>( 0 );
					for( Item item : items ){
						TravelPackageVO travelPackageVO = getTravelPackage( travelInsuranceVO, item.getCoverages( i ).getCoverageCode() );
	
						Item itemForTraveller = getTravellerItem( item, travellersDetails, travelPackageVO );
						itemForTraveller.setItemNumber( j );
						itemForTravellers.add( itemForTraveller );
	
						index.add( j );
						j++;
					}
					i++;
	
					/*Set the obtained items */
					travellerIndex.put( travellersDetails.getGprId(), index );
					Item[] itemForTravellersArray = new Item[ itemForTravellers.size() ];
					itemForTravellers.toArray( itemForTravellersArray );
	
					modifiedPolicy.setService( "getRates" );
					modifiedPolicy.setItems( itemForTravellersArray );
					/* Add the modifiedPolicy to policy list to be converted to array later */
					policyList.add( modifiedPolicy );
				}
			}

		}

		ThreadLevelContext.set( TRAVELLER_INDEX, travellerIndex );
		
		List<Policy> finalPolicyArray = new ArrayList<Policy>();
		
		if( policyList.size() > MAX_TRAVELLER_LOAD ){
			int firstIndex = 0, lastIndex = 0;
			while( lastIndex < policyList.size()  ){
				lastIndex += MAX_TRAVELLER_LOAD;
				if( lastIndex >= policyList.size() ){
					lastIndex = policyList.size();
				}
				
				List<Policy> maxLoadForTravelRating = policyList.subList( firstIndex, lastIndex );
				Policy[] policyArray = new Policy[ maxLoadForTravelRating.size() ];
				maxLoadForTravelRating.toArray( policyArray );
				
				if(!Utils.isEmpty( policyArray )){		/* Added 'Arrays.toString' in below logger and if condition for null check of policyArray - sonar violation fix */
				logger.debug( "Policy For Premium:" + Arrays.toString(policyArray) );
				}
				try{
					policyArray = ratingInvoker.getPremiumForPolicy( policyArray );
					finalPolicyArray.addAll( Arrays.asList( policyArray ) ) ;
				}
				catch( ErateException e ){
					e.printStackTrace();
					logger.error( "Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null" );

					BusinessException businessExcp = new BusinessException( "rating.invocation.no.loc", null, "riskGroupDetailsMap.entrySet() is null" );
					throw businessExcp;

				}
				firstIndex = lastIndex;
				
			}
		}else{
			Policy[] policyArray = new Policy[ policyList.size() ];
			policyList.toArray( policyArray );
			if(!Utils.isEmpty( policyArray )){		/* Added 'Arrays.toString' in below logger and if condition for null check of policyArray - sonar violation fix */
			logger.debug( "Policy For Premium:" + Arrays.toString(policyArray) );
			}
			try{
				policyArray = ratingInvoker.getPremiumForPolicy( policyArray );
				finalPolicyArray.addAll( Arrays.asList( policyArray ) ) ;
			}
			catch( ErateException e ){
				e.printStackTrace();
				logger.error( "Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null" );

				BusinessException businessExcp = new BusinessException( "rating.invocation.no.loc", null, "riskGroupDetailsMap.entrySet() is null" );
				throw businessExcp;

			}
		}
		

		ThreadLevelContext.clear( TRAVEL_DETAILS );
		ThreadLevelContext.clear( TRAVEL_INSURANCE );
		logger.info( "Exiting invokeRatingForPremium" );
		Policy[] policyArray = new Policy[ finalPolicyArray.size() ];
		finalPolicyArray.toArray( policyArray );
		return policyArray;
	}

	private TravelPackageVO getTravelPackage( TravelInsuranceVO travelInsuranceVO, String coverageCode ){

		coverageCode = coverageCode.replaceAll( " ", "" );
		String tarCodeForCover = Utils.getSingleValueAppConfig( "COVERAGE_" + coverageCode );
		TravelPackageVO finderTravelPackageVO = new TravelPackageVO();
		finderTravelPackageVO.setTariffCode( tarCodeForCover );
		if( !Utils.isEmpty( travelInsuranceVO.getTravelPackageList() ) ){
			if( travelInsuranceVO.getTravelPackageList().indexOf( finderTravelPackageVO ) > -1 ){
				return travelInsuranceVO.getTravelPackageList().get( travelInsuranceVO.getTravelPackageList().indexOf( finderTravelPackageVO ) );
			}
			else{
				return finderTravelPackageVO;
			}

		}
		else{
			return finderTravelPackageVO;
		}
	}

	/**
	 * Get the traveller item for each indiviual traveller
	 * 
	 * @param item
	 * @param travellersDetails
	 * @param travelPackageVO
	 * @return
	 */
	private Item getTravellerItem( Item item, TravelerDetailsVO travellersDetails, TravelPackageVO travelPackageVO ){
		Coverage[] coverageArray = item.getCoverages();
		Item itemForTraveller = CopyUtils.copySerializableObject( item );
		Factor[] modifiedFactorList = null;
		List<Coverage> coverageList = new ArrayList<Coverage>( 0 );
		for( Coverage coverage : coverageArray ){
			Factor[] factorList = coverage.getCoverageFactors();
			Coverage modifiedCoverage = CopyUtils.copySerializableObject( coverage );
			modifiedFactorList = getFactorList( factorList, travellersDetails, travelPackageVO );
			modifiedCoverage.setCoverageFactors( modifiedFactorList );
			coverageList.add( modifiedCoverage );
		}

		/*Set the factors to item */
		if( coverageList.size() > 0 ){
			coverageList.toArray( coverageArray );

			itemForTraveller.setCoverages( coverageArray );
		}

		return itemForTraveller;
	}

	/**
	 * get the factor list from traveller and package details
	 * 
	 * @param factorList
	 * @param travellersDetails
	 * @param travelPackageVO
	 * @return
	 */
	private Factor[] getFactorList( Factor[] factorList, TravelerDetailsVO travellersDetails, TravelPackageVO travelPackageVO ){
		List<Factor> modifiedFactorList = new ArrayList<Factor>( 0 );

		for( Factor factor : factorList ){
			Factor modifiedFactor = setFactor( factor, travellersDetails, travelPackageVO );
			modifiedFactorList.add( modifiedFactor );
		}
		Factor[] modifiedFactorArray = new Factor[ modifiedFactorList.size() ];
		modifiedFactorList.toArray( modifiedFactorArray );
		return modifiedFactorArray;
	}

	/**
	 * Method to set the factor value for the factor name
	 * 
	 * @param factor
	 */
	private Factor setFactor( Factor factor, TravelerDetailsVO travellersDetails, TravelPackageVO travelPackageVO ){
		Factor modifiedFactor = CopyUtils.copySerializableObject( factor );
		TravelDetailsVO travelDetailsVO = (TravelDetailsVO) ThreadLevelContext.get( TRAVEL_DETAILS );

		if( !Utils.isEmpty( factor ) && !Utils.isEmpty( factor.getFactorName() ) ){
			switch( TravelFactorName.valueOf( factor.getFactorName() ) ){
				case tripdays:
					modifiedFactor.setFactorValue( getTravelPeriod( factor.getFactorName(), travelDetailsVO ) );
					break;
				case traveltype:
					modifiedFactor.setFactorValue( getTravelType( factor.getFactorName(), travelDetailsVO ) );
					break;
				case agecategory:
					modifiedFactor.setFactorValue( getAgeCategory( factor.getFactorName(), travellersDetails ) );
					break;
				case Final_flag:
					modifiedFactor.setFactorValue( getFinalFlag( factor.getFactorName(), travelDetailsVO, travellersDetails ) );
					break;
				case wintersportfactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.WINTER_SPORT_COVER_CODE ) );
					break;
				case golffactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.GOLF_COVER_CODE ) );
					break;
				case medexfactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.MEDICAL_COVER_CODE ) );
					break;
				case baggfactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.BAGG_COVER_CODE ) );
					break;
				case sioption:
					modifiedFactor.setFactorValue( getPASiOption( factor.getFactorName(), travelPackageVO ) );
					break;
				case age:
					modifiedFactor.setFactorValue( getAgeOfTraveller( factor.getFactorName(), travellersDetails ) );
					break;
				case CruiseFactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.CRUISE_COVER_CODE ) );
					logger.debug("Cruise Checked Deatils"+getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.CRUISE_COVER_CODE ));
					break;
				case Terrorismfactor:
					modifiedFactor.setFactorValue( getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.TERRORISM_COVER_CODE ) );
					logger.debug("Terrorismfactor Checked Deatils"+getCoverFactor( factor.getFactorName(), travelPackageVO, SvcConstants.TERRORISM_COVER_CODE ));
					break;
					//sonar fix
				default:
					break;
			}
		}
		modifiedFactor.setFactorCode( factor.getFactorCode() );
		modifiedFactor.setFactorName( factor.getFactorName() );
		logger.debug( "Modified Factor:" + modifiedFactor );
		return modifiedFactor;
	}

	private String getPASiOption(String factorName,TravelPackageVO travelPackageVO) {
		String coverFactor = null;
		List<CoverDetailsVO> coverDetailList = travelPackageVO.getCovers();

		if( !Utils.isEmpty( coverDetailList ) ){
			for( CoverDetailsVO coverDetailsVO : coverDetailList ){
				if( !Utils.isEmpty( coverDetailsVO.getCoverCodes() ) && !Utils.isEmpty( coverDetailsVO.getCoverCodes().getCovCode() ) ){
					if( coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.PERSONAL_ACCIDENT_COVER_CODE ){
						
						logger.info( "Cover Code:" + coverDetailsVO.getCoverCodes().getCovCode() );
						
						if( !Utils.isEmpty( coverDetailsVO.getSumInsured() ) && !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
							
							logger.info( "Sum Insured:" + coverDetailsVO.getSumInsured().getSumInsured() );
							
							if(!SvcConstants.STATUS_ON.equalsIgnoreCase( coverDetailsVO.getIsCovered() )){
								coverFactor = SvcConstants.PA_OPTION1;
							}else{
								coverFactor = SvcConstants.PA_OPTION2;
							}
						}

					}
				}
			}
		}
		
		return coverFactor;
	}

	/**
	 * Method to return the travel period
	 * 
	 * @param travelDetailsVO
	 * @return
	 */

	private String getTravelPeriod( String factorName, TravelDetailsVO travelDetailsVO ){
		String travelPeriod = null;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ThreadLevelContext.get( TRAVEL_INSURANCE );
		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getPolicyTerm() ) ){
			travelPeriod = String.valueOf( travelInsuranceVO.getPolicyTerm() );
		}
		else{
			travelPeriod = TravelFactorName.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "Travel Period Factor:" + factorName + ":" + travelPeriod );
		return travelPeriod;
	}

	/**
	 * Method to get the cover factor for rating
	 * 
	 * @param travelPackageVO
	 * @return
	 */
	private String getCoverFactor( String factorName, TravelPackageVO travelPackageVO, Short coverCode ){
		String coverFactor = null;
		List<CoverDetailsVO> coverDetailList = travelPackageVO.getCovers();

		if( !Utils.isEmpty( coverDetailList ) ){
			for( CoverDetailsVO coverDetailsVO : coverDetailList ){
				if( !Utils.isEmpty( coverDetailsVO.getCoverCodes() ) && !Utils.isEmpty( coverDetailsVO.getCoverCodes().getCovCode() ) ){
					if( coverDetailsVO.getCoverCodes().getCovCode() == coverCode ){
						logger.info( "Cover Code:" + coverDetailsVO.getCoverCodes().getCovCode() );
						if( !Utils.isEmpty( coverDetailsVO.getSumInsured() ) && !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
							logger.info( "Sum Insured:" + coverDetailsVO.getSumInsured().getSumInsured() );
							//Added to handle on load scenario of risk page where we set coverFactor based on mandatoryIndicatior flag. 
							if(!Utils.isEmpty(coverDetailsVO.getMandatoryIndicator()))
							{
								if((coverDetailsVO.getMandatoryIndicator() && coverDetailsVO.getSumInsured().getSumInsured().doubleValue()>0.0) 
										|| SvcConstants.STATUS_ON.equalsIgnoreCase( coverDetailsVO.getIsCovered() ))
								{
									coverFactor = SvcConstants.COVER_VALUE;
								}
								else
								{
									coverFactor = SvcConstants.COVER_VALUE_NEGATIVE;
								}
							}
							else
							{
								if( (coverDetailsVO.getSumInsured().getSumInsured().doubleValue() > 0.0 )|| 
										SvcConstants.STATUS_ON.equalsIgnoreCase( coverDetailsVO.getIsCovered() ) ){  // added isCovered for addl cover checkBoxes
									coverFactor = SvcConstants.COVER_VALUE;
								}
								else if( coverDetailsVO.getSumInsured().getSumInsured() == 0.0 ){
									coverFactor = SvcConstants.COVER_VALUE_NEGATIVE;
								}
							}
							if( coverDetailsVO.getSumInsured().isPromoCover() ){
								coverFactor = SvcConstants.COVER_VALUE_NEGATIVE;

							}
						}

					}
				}
			}
		}

		if( Utils.isEmpty( coverFactor ) ){
			coverFactor = TravelFactorName.valueOf( factorName ).getDefaultValue();
		}
		logger.debug( "Cover Factor:" + factorName + ":" + coverFactor );
		return coverFactor;
	}

	/**
	 * Method to get the final flag
	 * @param travellersDetails 
	 * 
	 * @param travellersDetails
	 * @return
	 */
	private String getFinalFlag( String factorName, TravelDetailsVO travelDetailsVO, TravelerDetailsVO travellersDetails ){
		String finalFlag = SvcConstants.NONE_FLAG;

		List<TravelerDetailsVO> travelerDetailsList = travelDetailsVO.getTravelerDetailsList();
		List<Byte> relation = new ArrayList<Byte>( 0 );
		List<Byte> familyRelationList = new ArrayList<Byte>( 0 );
		List<Byte> spouseRelationList = new ArrayList<Byte>( 0 );

		String[] familyRelation = Utils.getMultiValueAppConfig( "FAMILY_RELATION" );;
		String[] spouseRelation = SvcConstants.SPOUSE_RELATION;

		for( String discountRelation : familyRelation ){
			familyRelationList.add( Byte.valueOf( discountRelation ) );
		}

		for( String discountRelation : spouseRelation ){
			spouseRelationList.add( Byte.valueOf( discountRelation ) );
		}
		for( TravelerDetailsVO travelerDetailsVO : travelerDetailsList ){
			relation.add( travelerDetailsVO.getRelation() );
		}

		Byte travellerRelation = travellersDetails.getRelation();
		if( !Utils.isEmpty( relation ) ){
			if( relation.contains( SvcConstants.RELATION_OTHER ) ){
				finalFlag = SvcConstants.NONE_FLAG;
			}

			Date dateOfBirth = travellersDetails.getDateOfBirth();
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ThreadLevelContext.get( TRAVEL_INSURANCE );
			Short age = null;
			if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getEffDate() ) ){
				age = SvcUtils.getAge( dateOfBirth, travelInsuranceVO.getScheme().getEffDate() );
			}
			
			/*if( relation.contains( SvcConstants.RELATION_SELF )
					&& relation.contains( SvcConstants.RELATION_SPOUSE )
					&&  relation.contains( SvcConstants.RELATION_CHILD ) && ( relation.contains( SvcConstants.RELATION_FATHER ) || relation.contains( SvcConstants.RELATION_MOTHER ) )
					&& familyRelationList.contains( travellerRelation ) ){*/
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) 
					&& !Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30") 
					&& relation.contains( SvcConstants.RELATION_SELF )
					&& relation.contains( SvcConstants.RELATION_SPOUSE )
					&& relation.contains( SvcConstants.RELATION_CHILD ) 
					&& familyRelationList.contains( travellerRelation ) ){
				finalFlag = SvcConstants.FAMILY_FLAG;
			}else if (!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))
						&& Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")
						&& ( relation.contains(SvcConstants.RELATION_OTHER) || (relation.contains( SvcConstants.RELATION_CHILD ) && age >= SvcConstants.CHILD_AGE))
						//&& relation.contains(SvcConstants.RELATION_SPOUSE)
						//&& relation.contains( SvcConstants.RELATION_CHILD ) 
						&& familyRelationList.contains(travellerRelation) && !travellerRelation.equals(SvcConstants.RELATION_SELF)) {
					finalFlag = SvcConstants.FAMILY_FLAG;
			}/*else if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))
						&& Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")
						&& relation.contains( SvcConstants.RELATION_CHILD ) 
						&& familyRelationList.contains(travellerRelation) && age >= SvcConstants.CHILD_AGE && !travellerRelation.equals(SvcConstants.RELATION_SELF)){
					finalFlag = SvcConstants.FAMILY_FLAG;
			}*/
			
			if(	!Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")
					&& relation.contains( SvcConstants.RELATION_SELF )
					&& relation.contains( SvcConstants.RELATION_SPOUSE )
					&& !( relation.contains( SvcConstants.RELATION_CHILD ) )
					&& spouseRelationList.contains( travellerRelation ) ){
				finalFlag = SvcConstants.SPOUSE_FLAG;
			}else if ( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30") 
					&& relation.contains( SvcConstants.RELATION_SPOUSE )
					&& !( relation.contains( SvcConstants.RELATION_CHILD ))
					&& !( relation.contains( SvcConstants.RELATION_OTHER ))
					&& spouseRelationList.contains( travellerRelation ) && !travellerRelation.equals(SvcConstants.RELATION_SELF)){
				finalFlag = SvcConstants.SPOUSE_FLAG;
			}
			/*else if( !Utils.isEmpty( travelerDetailsList ) ){
				if( travelerDetailsList.size() > SvcConstants.GROUP_TRAVELLER ){
					finalFlag = SvcConstants.GROUP_FLAG;
				}
			}*/
		}
		logger.debug( "Final Flag Factor:" + factorName + ":" + finalFlag );
		return finalFlag;
	}

	/**
	 * Method to get the age category
	 * 
	 * @param travellersDetails
	 * @return
	 */
	private String getAgeCategory( String factorName, TravelerDetailsVO travellersDetails ){
		String ageCategory = null;
		Short age = null;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ThreadLevelContext.get( TRAVEL_INSURANCE );

		if( !Utils.isEmpty( travellersDetails ) && !Utils.isEmpty( travellersDetails.getDateOfBirth() ) ){
			Date dateOfBirth = travellersDetails.getDateOfBirth();

			if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getEffDate() ) ){
				age = SvcUtils.getAge( dateOfBirth, travelInsuranceVO.getScheme().getEffDate() );
			}
		}

		if( !Utils.isEmpty( age ) ){
			if( age < SvcConstants.CHILD_AGE ){
				ageCategory = SvcConstants.CHILD_FLAG;
			}
			else if( age > SvcConstants.SENIOR_ADULT_AGE ){
				ageCategory = SvcConstants.SENIOR_ADULT_FLAG;
			}
			else{
				ageCategory = SvcConstants.ADULT_FLAG;
			}
		}
		else{
			ageCategory = TravelFactorName.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "Age Category Factor:" + factorName + ":" + ageCategory );
		return ageCategory;
	}
	
	private String getAgeOfTraveller( String factorName, TravelerDetailsVO travellersDetails ){
		
		Short age = null;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ThreadLevelContext.get( TRAVEL_INSURANCE );

		if( !Utils.isEmpty( travellersDetails ) && !Utils.isEmpty( travellersDetails.getDateOfBirth() ) ){
			Date dateOfBirth = travellersDetails.getDateOfBirth();

			if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getEffDate() ) ){
				age = SvcUtils.getAge( dateOfBirth, travelInsuranceVO.getScheme().getEffDate() );
			}
		}
		
		if( !Utils.isEmpty( age ) ){
			if( age < SvcConstants.CHILD_AGE ){
				return "1";
			}
			else{
				return "2";
			}
		}
		else{
			return TravelFactorName.valueOf( factorName ).getDefaultValue();
		}
		
	}

	/**
	 * Method to get the travel type
	 * 
	 * @param travelDetailsVO
	 * @return
	 */
	private String getTravelType( String factorName, TravelDetailsVO travelDetailsVO ){
		String travelType = null;

		if( !Utils.isEmpty( travelDetailsVO ) && !Utils.isEmpty( travelDetailsVO.getTravelLocation() ) ){
			String travelLocation = travelDetailsVO.getTravelLocation();

			if( !Utils.isEmpty( travelLocation ) ){
				if( travelLocation.equalsIgnoreCase( SvcConstants.WORLDWIDE_EX_US_CAN ) ){
					travelType = SvcConstants.WORLDWIDE_EX_US_CAN_TRAVEL_TYPE;
				}
				else if( travelLocation.equalsIgnoreCase( SvcConstants.WORLDWIDE ) ){
					travelType = SvcConstants.WORLDWIDE_TRAVEL_TYPE;
				}
			}
		}
		else{
			travelType = TravelFactorName.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "Travel Type Factor:" + factorName + ":" + travelType );
		return travelType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.RatingServiceInvoker#mapPremiumToVo(com.cts.writeRate.Policy[], com.rsaame.pas.vo.bus.PolicyDataVO)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	protected void mapPremiumToVo( Policy[] premiumDetails, PolicyDataVO policyDataVO ){
		logger.info( "Entering mapPremiumToVo" );
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) policyDataVO;

		//TravelDetailsVO travelDetailsVO = travelInsuranceVO.getTravelDetailsVO();

		//List<TravelerDetailsVO> travelerDetailsList = travelDetailsVO.getTravelerDetailsList();
		String loggenInLoc = Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION);
		double premiumAmt = 0;

		double travellerPremium = 0; // Traveller premium is the premium obtained for traveller from rating engine
		double discountedProratedTravellerPremium = 0; // Discounted prorated premium is the premium after discount and prorate

		double packagePremium = 0; // Package premium is the premium obtained for package from rating engine
		double discountedProratedPackagePremium = 0;// Discounted prorated premium is the premium after discount and prorate
		double govtTax = DAOUtils.getGovtTax(travelInsuranceVO).doubleValue();
		//double govtTax = DAOUtils.getTax(travelInsuranceVO, SvcConstants.SC_PRM_COVER_GOVT_TAX).doubleValue();

		//List<TravelPackageVO> travelPackageList = travelInsuranceVO.getTravelPackageList();
		if( !Utils.isEmpty( premiumDetails ) ){

			for( Policy policy : premiumDetails ){
				Item[] itemArray = policy.getItems();
				travellerPremium = 0;
				for( Item item : itemArray ){
					Coverage[] coverageArray = item.getCoverages();

					for( Coverage coverage : coverageArray ){
						Premium premium = coverage.getPremium();

						TravelerDetailsVO travelerDetailsVO = getTravellerDetail( item.getItemNumber(), travelInsuranceVO );

						packagePremium = 0;
						/*
						 * Set the premium of respective package and traveller
						 */
						if( !Utils.isEmpty( travelerDetailsVO ) ){
							TravelPackageVO travelPackageVO = getTravelPackage( travelInsuranceVO, coverage.getCoverageCode() );

							if( travelPackageVO.getIsSelected() != null && travelPackageVO.getIsSelected() ){

								travellerPremium = travellerPremium + Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(premium.getBasePremium()),travelInsuranceVO.getCommonVO().getLob().name()));
								if(loggenInLoc.equals("30"))
								{
									govtTaxAndPremiunRoundOffCalculationOmanTravel(govtTax,travelInsuranceVO,travellerPremium,travelerDetailsVO);
								}
								else
								{
									travelerDetailsVO.setPremiumAmt( travellerPremium );
									travelerDetailsVO.setPremiumAmtActual( travellerPremium );
								}
								/*Double discount = travelPackageVO.getPromoDiscPerc();
								discountedProratedTravellerPremium = SvcUtils.getPromoDiscount( travellerPremium, discount );*/

								

								logger.debug( "Traveller  Premium:" + travelerDetailsVO.getName() + ":" + travelerDetailsVO.getPremiumAmt() );
								logger.debug( "Traveller Actual Premium:" + travelerDetailsVO.getName() + ":" + travelerDetailsVO.getPremiumAmtActual() );
							}

							packagePremium = travelPackageVO.getPremiumAmtActual() + premium.getBasePremium();
							
							packagePremium = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(packagePremium),travelInsuranceVO.getCommonVO().getLob().name()));

							/* Get the discount for package premium */
							Double discount = travelPackageVO.getPromoDiscPerc();
							discountedProratedPackagePremium = SvcUtils.getPromoDiscount( packagePremium, discount );
							if(loggenInLoc.equals("30"))
							{
								govtTaxAndPremiunRoundOffCalculationOmanTravel(govtTax,travelInsuranceVO,packagePremium,travelPackageVO);
							}
							else{
								/* Discounted premium calculation is done in LoadTravelCoverRH.*/
								travelPackageVO.setPremiumAmt( packagePremium );
								travelPackageVO.setPremiumAmtActual( packagePremium );
							}

							logger.debug( "Travel Package Premium:" + travelPackageVO.getTariffCode() + ":" + travelPackageVO.getPremiumAmt() );
							logger.debug( "Travel Package Actual Premium:" + travelPackageVO.getTariffCode() + ":" + travelPackageVO.getPremiumAmtActual() );
						}

					}
				}
			}

			//Setting back the premiumVO to the CommonVO for Future use
			if( !Utils.isEmpty( policyDataVO.getCommonVO() ) ){

				if( Utils.isEmpty( policyDataVO.getCommonVO().getPremiumVO() ) ){
					policyDataVO.getCommonVO().setPremiumVO( new PremiumVO() );

				}

				policyDataVO.getCommonVO().getPremiumVO().setPremiumAmt( premiumAmt );
				policyDataVO.getCommonVO().getPremiumVO().setPremiumAmtActual( premiumAmt );

			}

		}
		logger.info( "Exiting mapPremiumToVo" );

	}

	/**
	 * Method to get the TravellerDetailVO
	 * 
	 * @param itemNumber
	 * @param travellerIndex
	 * @param travellerDetailsList
	 * @return
	 */
	private TravelerDetailsVO getTravellerDetail( int itemNumber, TravelInsuranceVO travelInsuranceVO ){

		TravelDetailsVO travelDetailsVO = travelInsuranceVO.getTravelDetailsVO();
		List<TravelerDetailsVO> travellerDetailsList = travelDetailsVO.getTravelerDetailsList();

		TravelerDetailsVO travelerDetailsVO = null;

		Map<BigDecimal, List<Integer>> travellerIndex = (Map<BigDecimal, List<Integer>>) ThreadLevelContext.get( TRAVELLER_INDEX );

		for( TravelerDetailsVO travelerDetails : travellerDetailsList ){
			List<Integer> index = travellerIndex.get( travelerDetails.getGprId() );

			if(!Utils.isEmpty( index )){
				if( index.contains( itemNumber ) ){
					travelerDetailsVO = travelerDetails;
				}
			}

		}

		return travelerDetailsVO;
	}
	
	/**
	 * 
	 * @param govtTax 
	 * @param travelInsuranceVO
	 * @param premiunFromRatingEngine
	 * @param travelerDetailsVO
	 * @return
	 */
	private void govtTaxAndPremiunRoundOffCalculationOmanTravel(double govtTaxPerc, TravelInsuranceVO travelInsuranceVO,
			double premiunFromRatingEngine, BaseVO baseVO) {
	
			double totalPrmForOmnTrvl = 0;
			double formatedPremiumFromRatingEngine = 0;
			double govtTax = (premiunFromRatingEngine *govtTaxPerc)/ 100;
			DecimalFormat df2 = new DecimalFormat("0.0");
			df2.setRoundingMode(RoundingMode.CEILING);
			DecimalFormat df3 = new DecimalFormat("0.000");
			
			//formatedPremiumFromRatingEngine = premiunFromRatingEngine+govtTax;
			//formatedPremiumFromRatingEngine = premiunFromRatingEngine;
			formatedPremiumFromRatingEngine = new Double(df2.format(premiunFromRatingEngine)).doubleValue();
			formatedPremiumFromRatingEngine = new Double(df3.format(formatedPremiumFromRatingEngine)).doubleValue();
			//totalPrmForOmnTrvl = (formatedPremiumFromRatingEngine/(100+govtTaxPerc))*100;
			totalPrmForOmnTrvl = formatedPremiumFromRatingEngine;
			totalPrmForOmnTrvl = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(totalPrmForOmnTrvl),travelInsuranceVO.getCommonVO().getLob().name()));
			govtTax = formatedPremiumFromRatingEngine - totalPrmForOmnTrvl;
			govtTax = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(govtTax),travelInsuranceVO.getCommonVO().getLob().name()));
			if(baseVO instanceof TravelerDetailsVO)
			{
				((TravelerDetailsVO)baseVO).setPremiumAmt(totalPrmForOmnTrvl);
				((TravelerDetailsVO)baseVO).setPremiumAmtActual(totalPrmForOmnTrvl);
			}
			else if(baseVO instanceof TravelPackageVO)
			{
				((TravelPackageVO)baseVO).setPremiumAmt(totalPrmForOmnTrvl);
				((TravelPackageVO)baseVO).setPremiumAmtActual(totalPrmForOmnTrvl);
			}
			if(Utils.isEmpty( travelInsuranceVO.getPremiumVO() )){
				travelInsuranceVO.setPremiumVO( new PremiumVO() );
			}
			if(baseVO instanceof TravelPackageVO && ((TravelPackageVO)baseVO).getIsSelected())
			travelInsuranceVO.getPremiumVO().setGovtTax(govtTax);
	}
}
