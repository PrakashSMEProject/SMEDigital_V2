/**
 * 
 */
package com.rsaame.pas.com.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author M1012799
 *
 * Service class used for Travel to load the Travel package, load General Info and call Rating engine to get the premium
 */
public class TravelPackagePremiumSvc extends BaseService{

	private final static Logger logger = Logger.getLogger( TravelPackagePremiumSvc.class );
	
	private BaseLoadSvc baseLoadSvc;
	private LoadCoverSvc loadCoverSvc;
	private GeneralInfoCommonSvc generalInfoCmnLoadSvc;
	
	/**
	 * @return the loadCoverSvc
	 */
	public LoadCoverSvc getLoadCoverSvc(){
		return loadCoverSvc;
	}

	/**
	 * @param loadCoverSvc the loadCoverSvc to set
	 */
	public void setLoadCoverSvc( LoadCoverSvc loadCoverSvc ){
		this.loadCoverSvc = loadCoverSvc;
	}

	/**
	 * @return the baseLoadSvc
	 */
	public BaseLoadSvc getBaseLoadSvc() {
		return baseLoadSvc;
	}

	/**
	 * @param baseLoadSvc the baseLoadSvc to set
	 */
	public void setBaseLoadSvc(BaseLoadSvc baseLoadSvc) {
		this.baseLoadSvc = baseLoadSvc;
	}

	/**
	 * @return the generalInfoCmnLoadSvc
	 */
	public GeneralInfoCommonSvc getGeneralInfoCmnLoadSvc() {
		return generalInfoCmnLoadSvc;
	}

	/**
	 * @param generalInfoCmnLoadSvc the generalInfoCmnLoadSvc to set
	 */
	public void setGeneralInfoCmnLoadSvc(GeneralInfoCommonSvc generalInfoCmnLoadSvc) {
		this.generalInfoCmnLoadSvc = generalInfoCmnLoadSvc;
	}


	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( SvcConstants.GET_PACKAGE_PREMIUM.equals( methodName ) ){
			returnValue = getPackagePremium( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	/**
	 * This method is used to load the package details, load general info and call rating to get the premium
	 * 
	 * @param baseVO
	 * @return
	 */
	private BaseVO getPackagePremium( BaseVO baseVO ){
		logger.info( "Entering getPackagePremium" );
		logger.debug( "TravelInsuranceVO:" + baseVO );
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) baseVO;

		CommonVO commonVO = travelInsuranceVO.getCommonVO();

		List<TravelPackageVO> packageList = travelInsuranceVO.getTravelPackageList();

		TravelPackageVO travelPackageVO = null;
		String desc = null;

		/*
		 * Load all the packages from
		 */
		TravelInsuranceVO coverTravelInsuranceVO = (TravelInsuranceVO) loadCoverSvc.invokeMethod( "getPackages", commonVO );

		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();

		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		loadDataInputVO.setDocCode( commonVO.getDocCode() );
		loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );

		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		dataMap.put( SvcConstants.T_TRN_PREMIUM, TTrnPremiumVOHolder.class );

		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>>) baseLoadSvc.invokeMethod(
				"baseLoad", loadDataInputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		List<TableData<BaseVO>> prmTableData = dataHolder.getData().get( SvcConstants.T_TRN_PREMIUM );
		
		PolicyDataVO policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();
		
		CoverDetailsVO coverDetailsVO = null;
		CoverVO coverVO = null;
		TTrnPremiumVOHolder prmHolderVO = null;
		List<CoverDetailsVO> splCovers = new ArrayList<CoverDetailsVO>();
		String[] splCovCodes = Utils.getMultiValueAppConfig( "SPECIAL_COVER_CODES" );
		logger.info( "TravelPackagePremiumSvc.getPackagePremium method, splCovCodes: " + Arrays.toString(splCovCodes) );
		
		if( !Utils.isEmpty( prmTableData ) ){
			
			for( TableData<BaseVO> prmData : prmTableData ){
				
				prmHolderVO = (TTrnPremiumVOHolder)prmData.getTableData();
				
				if( CopyUtils.asList( splCovCodes ).contains( Short.valueOf( prmHolderVO.getPrmCovCode() ).toString() ) ){

					coverDetailsVO = new CoverDetailsVO();
					coverVO = new CoverVO();
					coverVO.setCovCode( prmHolderVO.getPrmCovCode() );
					coverDetailsVO.setCoverCodes( coverVO );
					coverDetailsVO.setVsd( prmHolderVO.getPrmValidityStartDate() );
					SumInsuredVO insuredVO = new SumInsuredVO();
					insuredVO.setSumInsured(prmHolderVO.getPrmSumInsured().doubleValue());	
					coverDetailsVO.setSumInsured(insuredVO);
					//coverDetailsVO.getSumInsured().setSumInsured(prmHolderVO.getPrmSumInsured().doubleValue());
					// Set the Policy Fees Premium, Govt Tax Premium
					if( ( Short.valueOf( prmHolderVO.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_POLICY_FEE ) || ( Short.valueOf( prmHolderVO.getPrmCovCode() )
							.equals( SvcConstants.SC_PRM_COVER_GOVT_TAX ) ) ||  ( Short.valueOf( prmHolderVO.getPrmCovCode() )
									.equals( SvcConstants.SC_PRM_COVER_VAT_TAX ) ) ) && !Utils.isEmpty( prmHolderVO.getPrmPremium() ) ){
						coverDetailsVO.setPremiumAmt( prmHolderVO.getPrmPremium().doubleValue() );
						
						if(Short.valueOf( prmHolderVO.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX )) {
							coverDetailsVO.setVatTax(prmHolderVO.getPrmPremium().doubleValue() );
							coverDetailsVO.setVatTaxPerc(prmHolderVO.getPrmRate().doubleValue() );
						}
					}
					coverDetailsVO.setDiscOrLoadAmt( prmHolderVO.getPrmPremium() );
					if( !Utils.isEmpty( prmHolderVO.getPrmLoadDisc() )){
						coverDetailsVO.setDiscOrLoadPerc( prmHolderVO.getPrmLoadDisc().doubleValue() );
					}
					splCovers.add( coverDetailsVO );
					
				}
				
			}

		}

		/* Add covers in selected package.*/
		for(TravelPackageVO packageVO : coverTravelInsuranceVO.getTravelPackageList() ){
			
			if( packageVO.getIsSelected() ){
				desc = packageVO.getDescription();
				packageVO.getCovers().addAll( splCovers );
			}
			
		}
		
		logger.debug( "TravelInsuranceVO after getting all packages:" + coverTravelInsuranceVO );
		
		/*
		 * This is Save case where the package list will contain only one element
		 */
		if( !Utils.isEmpty( packageList ) && packageList.size() == 1 ){
			travelPackageVO = packageList.get( 0 );
			if(Utils.isEmpty(travelPackageVO.getDescription()))
			{
				travelPackageVO.setDescription(desc);
			}
		}

		/*
		 * Fetch general info data from quotation table if it is quote else fetch the data from policy table
		 */
		TravelInsuranceVO travellerTravelInsuranceVO = (TravelInsuranceVO) generalInfoCmnLoadSvc.invokeMethod( SvcConstants.LOAD_GEN_INFO, travelInsuranceVO );

		logger.debug( "TravelInsuranceVO after getting general info details:" + travellerTravelInsuranceVO );
		
		/*
		 * Set the traveller details to travelInsuranceVO which is sent to rating engine
		 */
		mergeTravelInsuranceVO(travelInsuranceVO,travellerTravelInsuranceVO);
		travelInsuranceVO.getScheme().setSchemeCode( coverTravelInsuranceVO.getScheme().getSchemeCode() );

		List<Object> promotionalCoverCodesWithDesc = DAOUtils.getPromotionalCodeCover( travelInsuranceVO.getScheme().getSchemeCode(), travellerTravelInsuranceVO.getGeneralInfo()
				.getSourceOfBus().getPromoCode(), travelInsuranceVO.getPolicyClassCode(), travelInsuranceVO.getScheme().getEffDate(),travelInsuranceVO.getCommonVO().getIsQuote()  );
		
		List<Short> promotionalCoverCodes = null;
		if( !Utils.isEmpty( promotionalCoverCodesWithDesc ) ){
			if( !Utils.isEmpty( promotionalCoverCodesWithDesc.get( 0 ) ) ){
				promotionalCoverCodes = (List<Short>)promotionalCoverCodesWithDesc.get( 0 );	
			}
			
			logger.info( "TravelPackagePremiumSvc.getPackagePremium method, promotionalCoverCodes: " +promotionalCoverCodes);
			
			if( !Utils.isEmpty( promotionalCoverCodesWithDesc.get( 1 ) ) ){
				travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCodeDesc( promotionalCoverCodesWithDesc.get( 1 ).toString() );	
			}
		}
		
		List<Object> promoDiscountWithDesc = DAOUtils.getPromotionalCodeDiscount( travelInsuranceVO.getScheme().getSchemeCode(), travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus()
				.getPromoCode(), travelInsuranceVO.getPolicyClassCode(), travelInsuranceVO.getScheme().getEffDate() ,travelInsuranceVO.getCommonVO().getIsQuote());
		
		Double promoDiscount = null;
		if( !Utils.isEmpty( promoDiscountWithDesc ) ){
			if( !Utils.isEmpty( promoDiscountWithDesc.get( 0 ) ) ){
				promoDiscount = (Double)promoDiscountWithDesc.get( 0 );	
			}
			if( !Utils.isEmpty( promoDiscountWithDesc.get( 1 ) ) ){
				travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCodeDesc( promoDiscountWithDesc.get( 1 ).toString() );	
			}
		}
		

		/*
		 * If there is a promotional code available update the packagelist available in coverTravelInsuranceVO
		 */
		if( ( !Utils.isEmpty( promoDiscount ) ) || !Utils.isEmpty( promotionalCoverCodes ) && promotionalCoverCodes.size() > 0 ){
			List<TravelPackageVO> masterPackageList = new ArrayList<TravelPackageVO>( 0 );

			for( TravelPackageVO packageVO : coverTravelInsuranceVO.getTravelPackageList() ){
				TravelPackageVO masterPackageVO = CopyUtils.copySerializableObject( packageVO );
				List<CoverDetailsVO> masterCoverDetails = new ArrayList<CoverDetailsVO>( 0 );
				setPromotionalCode( promotionalCoverCodes, promoDiscount, packageVO.getCovers(), masterCoverDetails, masterPackageVO );
				masterPackageList.add( masterPackageVO );
			}
			if( !Utils.isEmpty( masterPackageList ) && masterPackageList.size() > 0 ){
				coverTravelInsuranceVO.setTravelPackageList( masterPackageList );
			}
		}

		/*
		 * Set the package list to the travelInsuranceVO which is sent to rating engine based on if it is save case or load case
		 */
		if( !Utils.isEmpty( travelPackageVO ) ){
			String selectedTar = travelPackageVO.getTariffCode();
			List<TravelPackageVO> travelPackageList = coverTravelInsuranceVO.getTravelPackageList();
			for( TravelPackageVO packageVO : travelPackageList ){

				if( !selectedTar.equals( packageVO.getTariffCode() ) ){
					packageVO.setIsSelected( false );
					travelInsuranceVO.getTravelPackageList().add( packageVO );
				}
				else{
					
					/*
					 * Populate the promotional code to the VO obtained from request
					 */
					if( ( !Utils.isEmpty( promoDiscount ) ) || !Utils.isEmpty( promotionalCoverCodes ) && promotionalCoverCodes.size() > 0 ){
						TravelPackageVO finderPackage = new TravelPackageVO();
						finderPackage.setTariffCode( selectedTar );
						int index = travelInsuranceVO.getTravelPackageList().indexOf( finderPackage );
						List<CoverDetailsVO> covers = travelInsuranceVO.getTravelPackageList().get( index ).getCovers();
						List<CoverDetailsVO> masterCoverDetails = new ArrayList<CoverDetailsVO>( 0 );
						TravelPackageVO selectPackageVO = travelInsuranceVO.getTravelPackageList().get( index );

						setPromotionalCode( promotionalCoverCodes, promoDiscount, covers, masterCoverDetails, selectPackageVO );
					}
				}
			}

		}
		else{
			travelInsuranceVO.setTravelPackageList( coverTravelInsuranceVO.getTravelPackageList() );
		}
		logger.debug( "TravelInsuranceVO after merging with package details,general info and promotional code:" + travelInsuranceVO );
		logger.debug( "TravelInsuranceVO before rating engine call:" + travelInsuranceVO );
		
		/*
		 * Invoke rating engine to get the actual premium
		 */
		RatingServiceInvoker ratingServiceInvoker = (RatingServiceInvoker) Utils.getBean( "travelRatingInvoker" );
		travelInsuranceVO = (TravelInsuranceVO) ratingServiceInvoker.invokeRating( travelInsuranceVO );
		
		populatePackagePremium( travelInsuranceVO );
		Integer distChannel = policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel();
		logger.info( "TravelPackagePremiumSvc.getPackagePremium method, distChannel: " +distChannel);
		Integer brokerId = null;
		if(SvcConstants.DIST_CHANNEL_BROKER.equals(distChannel))
		{
			brokerId = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
		}else if(SvcConstants.DIST_CHANNEL_AGENT.equals(distChannel) || SvcConstants.DIST_CHANNEL_AFFINITY_AGENT.equals(distChannel))
		{
			brokerId = Integer.valueOf(policyDataVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().intValue());
		}
		String user = Utils.isEmpty( brokerId ) ? Utils.getSingleValueAppConfig( SvcConstants.USER + "_" + distChannel.toString() ) : brokerId.toString();
		LookUpListVO listVO = (LookUpListVO) SvcUtils.getLookUpCodesList( SvcConstants.SCHEME, user, Utils.getSingleValueAppConfig( "SHORT_TRAVEL_POL_TYPE" ) );
		String schemeCode = null;
		if(SvcConstants.DIST_CHANNEL_AGENT.equals(distChannel) 
				|| SvcConstants.DIST_CHANNEL_AFFINITY_AGENT.equals(distChannel)
					||SvcConstants.DIST_CHANNEL_BROKER.equals(distChannel)
					|| SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT.equals(distChannel))
		{
			schemeCode = policyDataVO.getScheme().getSchemeCode().toString();
		}
		else
		{
			schemeCode = listVO.getLookUpList().get( 0 ).getCode().toString();
		}
		
		logger.info( "TravelPackagePremiumSvc.getPackagePremium method, distChannel: " +distChannel+" , brokerId: "+brokerId+" , user: "+user+" , schemeCode : "+schemeCode);
		
		travelInsuranceVO.setCommission( SvcUtils.getCommission( policyDataVO, schemeCode));
		
		
		//populate EndorsmentVo in case of endorsement no greater than 0
				if(commonVO.getEndtNo() > 0 && (Flow.AMEND_POL.equals( SvcUtils.getBasicFlowCommonResolveReferral( commonVO) ) || Flow.VIEW_POL.equals(SvcUtils.getBasicFlowCommonResolveReferral( commonVO )))){
					populateEndorsmentVO( travelInsuranceVO );
				}
		
		logger.info( "Exiting getPackagePremium" );
		return travelInsuranceVO;
	}
	
	/**
	 * @param homeInsuranceVO
	 */
	private void populateEndorsmentVO(TravelInsuranceVO travelInsuranceVO){


		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/* Update endt no and id before loading premium page*/

		EndorsmentVO endorsmentVO = new EndorsmentVO() ;
		Long endtId = travelInsuranceVO.getCommonVO().getEndtId();
		Long polId = travelInsuranceVO.getCommonVO().getPolicyId();
	
		if( !Utils.isEmpty( endtId ) ){
			
				BaseVO baseVO1 = TaskExecutor.executeTasks( "CAPTURE_AMEND_POLICY_ENDT_TEXT", travelInsuranceVO );
				travelInsuranceVO = (TravelInsuranceVO) baseVO1;
			if( !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO() ) && travelInsuranceVO.getEndorsmentVO().size() > 0 ){
					endorsmentVO = travelInsuranceVO.getEndorsmentVO().get( 0 );
				}
				

			
			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the need is to display the 
			 * refund amount before actually cancellation of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;
			/*
			 * Caching Issue    
			 * Ticket - 117462/117605/117549/120595 : BEGIN
			 */
			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getPremiumForProrate( polId, endtId,travelInsuranceVO.getCommonVO().getLob() );
			/*
			 * Caching Issue    
			 * Ticket - 117462/117605/117549/120595 : END
			 */
			
			newPremiumAmt = premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );

			Double currentPremiumAmt = premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );

		
			newPremiumVO.setPremiumAmt( newPremiumAmt ); // New premium amount.
			oldPremiumVO.setPremiumAmt( currentPremiumAmt ); // Old premium amount.
			
			Double discAmt = PremiumHelper.getSplPrm( travelInsuranceVO.getCommonVO(), travelInsuranceVO.getCommonVO().getEndtId(), new Short[]{ SvcConstants.SC_PRM_COVER_LOADING,
				SvcConstants.SC_PRM_COVER_DISCOUNT }, (HibernateTemplate) Utils.getBean( "hibernateTemplate" ) );
			if(!Utils.isEmpty( discAmt )){
				travelInsuranceVO.getPremiumVO().setDiscOrLoadAmt( BigDecimal.valueOf( discAmt ));
			}
			else {
				travelInsuranceVO.getPremiumVO().setDiscOrLoadAmt(new BigDecimal( 0.0 ));
			}
			
			endorsmentVO.setOldPremiumVO( oldPremiumVO );
			endorsmentVO.setPremiumVO( newPremiumVO );
			endorsmentVO.setEndEffDate( travelInsuranceVO.getEndEffectiveDate() );
			endorsmentVO.setEndDate( travelInsuranceVO.getScheme().getExpiryDate() );
			
		
			endorsements.add( endorsmentVO );
			
			travelInsuranceVO.setEndorsmentVO( endorsements );

		}
		
	}

	/**
	 * @param travelInsuranceVO
	 * calculates the package premium that need to be displayed in travel risk page
	 */
	private void populatePackagePremium( TravelInsuranceVO travelInsuranceVO ){
		
		double packagePremium =  0;
		double packageActualPremium = 0;
		//Double zero = new Double( 0.0 );
		Double discLoadPerc = null;
		double policyFees = 0.0;
		double govtTax = 0.0;
		double vatTax = 0.0;
		/*Added VAT */
		double vatTaxPerc = 0.0;
		boolean VatTaxAvl = false;
		
		String[] discLoadCovCodes = Utils.getMultiValueAppConfig( "DISC_LOAD_COVER_CODES" );
		
		if(!Utils.isEmpty( travelInsuranceVO )){
			
			for(TravelPackageVO packageVO : travelInsuranceVO.getTravelPackageList() ){
				if( packageVO.getIsSelected() ){
						boolean isDiscOrLoad=true;
					for( CoverDetailsVO coverDetailsVO : packageVO.getCovers() ){
						packagePremium += coverDetailsVO.getPremiumAmt();
						packageActualPremium += coverDetailsVO.getPremiumAmtActual();
						
						if( CopyUtils.asList( discLoadCovCodes ).contains( Short.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ).toString() ) 
								&& !Utils.isEmpty( coverDetailsVO.getDiscOrLoadPerc() ) &&  isDiscOrLoad ){
							
							discLoadPerc = coverDetailsVO.getDiscOrLoadPerc();
							
							if(discLoadPerc.doubleValue()!=0.0){
								isDiscOrLoad=false;
							}
						}
						/*
						 * Set Policy Fees if any
						 */
						if( Short.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ).equals( SvcConstants.SC_PRM_COVER_POLICY_FEE ) ){
							if( !Utils.isEmpty( coverDetailsVO.getPremiumAmt() ) ){
								policyFees = coverDetailsVO.getPremiumAmt();
							}
						}
						/*
						 * Set Govt Tax if any
						 */
						if( Short.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ).equals( SvcConstants.SC_PRM_COVER_GOVT_TAX ) ){
							if( !Utils.isEmpty( coverDetailsVO.getPremiumAmt() ) ){
								govtTax = coverDetailsVO.getPremiumAmt();
							}
						}
						
						// Set VAT if coverCode matches
						if( Short.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX ) ){
							if( !Utils.isEmpty( coverDetailsVO.getPremiumAmt() ) ){
								vatTax = coverDetailsVO.getPremiumAmt();
								vatTaxPerc = coverDetailsVO.getVatTaxPerc();
								VatTaxAvl = true;
							}
						}
					}	
					
				}
			}
			
		}
		
		if(Utils.isEmpty( travelInsuranceVO.getPremiumVO() )){
			travelInsuranceVO.setPremiumVO( new PremiumVO() );
		}
		packagePremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( new BigDecimal( packagePremium ), travelInsuranceVO.getCommonVO().getLob().name() ) );
		travelInsuranceVO.getPremiumVO().setPremiumAmt( packagePremium );
		travelInsuranceVO.getPremiumVO().setPremiumAmtActual( packageActualPremium );
		travelInsuranceVO.getPremiumVO().setPolicyFees( policyFees );
		travelInsuranceVO.getPremiumVO().setGovtTax( govtTax );
		travelInsuranceVO.setGovtTaxPerc(DAOUtils.getGovtTax(travelInsuranceVO).doubleValue());
		//travelInsuranceVO.setGovtTaxPerc(DAOUtils.getTax(travelInsuranceVO, SvcConstants.SC_PRM_COVER_GOVT_TAX).doubleValue());
		
		travelInsuranceVO.getPremiumVO().setVatTax( vatTax );
		//travelInsuranceVO.setVatTaxPerc(DAOUtils.getVatTax(travelInsuranceVO).doubleValue());
		//travelInsuranceVO.setVatTaxPerc(DAOUtils.getTax(travelInsuranceVO, SvcConstants.SC_PRM_COVER_VAT_TAX).doubleValue());
		
		if( !Utils.isEmpty( discLoadPerc ) ) travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc( discLoadPerc );
	}
	
	/**
	 * Method to merge the travelInsuranceVO with the TravellerInsuranceVO
	 * @param travelInsuranceVO
	 * @param travellerTravelInsuranceVO
	 */
	private void mergeTravelInsuranceVO( TravelInsuranceVO travelInsuranceVO, TravelInsuranceVO travellerTravelInsuranceVO ){
		
		if(!Utils.isEmpty( travelInsuranceVO.getGeneralInfo()) && !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getSourceOfBus())
				&& !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))
		{
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setCallCentreNo( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setCcEmailId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setReplyToEmailId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setFromEmailID( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID() );
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setFaqUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
			travellerTravelInsuranceVO.getGeneralInfo().getSourceOfBus().setPolicyTermUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());
			
		}
		travelInsuranceVO.setTravelDetailsVO( travellerTravelInsuranceVO.getTravelDetailsVO() );
		travelInsuranceVO.getTravelDetailsVO().setTravelerDetailsList( travellerTravelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() );
		travelInsuranceVO.setPolicyClassCode( travellerTravelInsuranceVO.getPolicyClassCode() );
		travelInsuranceVO.setPolicyType( travellerTravelInsuranceVO.getPolicyType() );
		travelInsuranceVO.setPolicyTerm( travellerTravelInsuranceVO.getPolicyTerm() );
		travelInsuranceVO.getScheme().setEffDate( travellerTravelInsuranceVO.getScheme().getEffDate() );
		travelInsuranceVO.getScheme().setExpiryDate( travellerTravelInsuranceVO.getScheme().getExpiryDate() );
		travelInsuranceVO.setGeneralInfo( travellerTravelInsuranceVO.getGeneralInfo() );
		travelInsuranceVO.setEndEffectiveDate( travellerTravelInsuranceVO.getEndEffectiveDate() );
		travelInsuranceVO.setVatCode( travellerTravelInsuranceVO.getVatCode() );
		if(Utils.isEmpty(travelInsuranceVO.getScheme().getTariffCode() ) ){
			travelInsuranceVO.getScheme().setTariffCode(travellerTravelInsuranceVO.getScheme().getTariffCode());
		}
		travelInsuranceVO.setAuthenticationInfoVO( travellerTravelInsuranceVO.getAuthenticationInfoVO() );
		
	}

	/**
	 * Method to set the promo free cover
	 * 
	 * @param covers
	 * @param masterCoverDetails
	 * @param promotionalCoverCodes
	 */
	private void setPromoCover( List<CoverDetailsVO> covers, List<CoverDetailsVO> masterCoverDetails, List<Short> promotionalCoverCodes ){
		for( CoverDetailsVO coverDetails : covers ){
			CoverDetailsVO masterCoverDetail = CopyUtils.copySerializableObject( coverDetails );

			Short coverCode = coverDetails.getCoverCodes().getCovCode();

			if( promotionalCoverCodes.contains( coverCode ) ){
				masterCoverDetail.getSumInsured().setPromoCover( true );
			}
			masterCoverDetails.add( masterCoverDetail );
		}
	}

	/**
	 * Method to set the promo free cover/discount
	 * 
	 * @param covers
	 * @param masterCoverDetails
	 * @param promotionalCoverCodes
	 */
	private void setPromotionalCode( List<Short> promotionalCoverCodes, Double promoDiscount, List<CoverDetailsVO> covers, List<CoverDetailsVO> masterCoverDetails,
			TravelPackageVO packageVO ){

		/*
		 * Check if free cover is available from promotional code
		 */
		if( !Utils.isEmpty( promotionalCoverCodes ) && promotionalCoverCodes.size() > 0 ){
			logger.debug( "Promotional Code Cover:" + promotionalCoverCodes );
			setPromoCover( covers, masterCoverDetails, promotionalCoverCodes );
			packageVO.setCovers( masterCoverDetails );
		}

		/*
		 * Check if discount is available from promotional code
		 */
		if( !Utils.isEmpty( promoDiscount ) ){
			logger.debug( "Promotional Code Discount:" + promoDiscount );
			packageVO.setPromoDiscPerc( promoDiscount );
		}

	}

}
