/**
 * 
 */
package com.rsaame.pas.taglib.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.policy.model.Cover;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.dao.cmn.IBaseLoadOperation;
import com.rsaame.pas.dao.model.VMasProductConfigPas;
import com.rsaame.pas.dao.model.VMasProductConfigPasWrapper;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author M1021201
 *
 */
public class LoadCoverDAO extends BaseDBDAO implements ILoadCoverDAO{

	private static final Logger LOGGER = Logger.getLogger( LoadCoverDAO.class );
	private IBaseLoadOperation baseLoadOperation ;
	
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO getRiskCoverDetails( BaseVO baseVO ){

		LOGGER.info( "Inside getRiskCoverDetails.." );
		VMasProductConfigPasWrapper coverDetailList = null;
		CoverDetails coverDetails = null;
		SchemeVO schemeVO = null;

		try{
			coverDetails = new CoverDetails();
			schemeVO = (SchemeVO)baseVO;
 
			String key = Utils.concat( schemeVO.getSchemeCode().toString(), "-", schemeVO.getTariffCode().toString() );
			Object cachedObject = CacheManagerFactory.getCacheManager().get( PASCache.COVER, key );
			if( !Utils.isEmpty( cachedObject ) ){
				coverDetails = (CoverDetails) cachedObject;
				CacheManagerFactory.getCacheManager().remove( PASCache.COVER, key );
			}
			else{
				coverDetailList = new VMasProductConfigPasWrapper();
				String[] paramNames = { "scheme", "tariff", "policyType" };
				Object[] values = { Integer.valueOf( schemeVO.getSchemeCode().toString() ), Integer.valueOf( schemeVO.getTariffCode().toString() ),
						Integer.valueOf( schemeVO.getPolicyType() ) };
				coverDetailList.setVProductConfigPasList( getHibernateTemplate().findByNamedQueryAndNamedParam( "getRiskCoverDetails", paramNames, values ));
				coverDetails = BeanMapper.map( coverDetailList, coverDetails );
				CacheManagerFactory.getCacheManager().put( PASCache.COVER, key, coverDetails );
			}
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return coverDetails;
	}

	public BaseVO getSchemeDetails( BaseVO baseVO ){
		
		//HashMap<String, Object> resultMap = null;
		//TTrnPolicyQuo policyRecord = null;
		SchemeVO schemeVO = null;
		try{
			CommonVO commonVO = (CommonVO) baseVO;
			//policyRecord = new TTrnPolicyQuo();
			schemeVO = new SchemeVO();
			/* Prepare the data to fetch the records from database.*/
			LoadDataInputVO inputVO = new LoadDataInputVO();
			inputVO.setIsQuote( commonVO.getIsQuote() );
			inputVO.setLocCode( commonVO.getLocCode() );
			inputVO.setEndtId( commonVO.getEndtId() );
			inputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
			inputVO.setDocCode( commonVO.getDocCode() );
			if(commonVO.getIsQuote()){
				inputVO.setQuoteNo( commonVO.getQuoteNo() );
			}else{
				inputVO.setPolicyNo( commonVO.getPolicyNo() );
			}
			
			Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
			dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
			dataMap.put( SvcConstants.T_TRN_PREMIUM, TTrnPremiumVOHolder.class );
			
			if( inputVO.getIsQuote() ){
				baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION );
			}
			else{
				baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION_POL );
			}

			@SuppressWarnings( "unchecked" )
			DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>>) baseLoadOperation.loadData(
					inputVO, dataMap );

			List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
			PolicyDataVO policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();
			
			//Long endtId = policyDataVO.getEndtId();
			//Long policyId = policyDataVO.getPolicyId();
			schemeVO.setSchemeCode( policyDataVO.getScheme().getSchemeCode());
			schemeVO.setTariffCode( policyDataVO.getScheme().getTariffCode());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return schemeVO;
	}

	/* 
	 * This method is used to fetch all the packages from the master configuration and also replaces the required package with transaction table records.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public TravelInsuranceVO getPackages( CommonVO commonVO ){

		LOGGER.info( "Inside getPakages." );

		/*
		 * Fetch the policyId and endtId from input and get the brokerId and distributionChannel from TTrnPolicyQuo. These will be the parameters
		 * to fetch the scheme from lookUp for that particular user. Use the scheme to fetch the packages for Travel from the view .
		 * 
		 * */

		List<Integer> tariffCodes = new ArrayList<Integer>();

		/* Prepare the data to fetch the records from database.*/
		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setVsd( commonVO.getVsd() );
		inputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		inputVO.setDocCode( commonVO.getDocCode() );
		if( commonVO.getIsQuote() ){
			inputVO.setQuoteNo( commonVO.getQuoteNo() );
		}else{
			inputVO.setPolicyNo( commonVO.getPolicyNo() );
		}

		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		dataMap.put( SvcConstants.T_TRN_PREMIUM, TTrnPremiumVOHolder.class );
		//Oman Travel Changes
		dataMap.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );
		
		if( inputVO.getIsQuote() ){
			baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION );
		}
		else{
			baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION_POL );
		}

		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>>) baseLoadOperation.loadData(
				inputVO, dataMap );

		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		PolicyDataVO policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();

		List<TableData<BaseVO>> prmTableData = dataHolder.getData().get( SvcConstants.T_TRN_PREMIUM );
		
		Integer distChannel = policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel();
		Integer brokerId = null;
		if(SvcConstants.DIST_CHANNEL_BROKER.equals(distChannel))
		{
			brokerId = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
		}else if(SvcConstants.DIST_CHANNEL_AGENT.equals(distChannel) 
				|| SvcConstants.DIST_CHANNEL_AFFINITY_AGENT.equals(distChannel))
		{
			brokerId = Integer.valueOf(policyDataVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().intValue());
		}
		Integer classCode = policyDataVO.getPolicyClassCode();
		Integer tarCode = policyDataVO.getScheme().getTariffCode();

		policyDataVO.setCommonVO( commonVO );

		String user = Utils.isEmpty( brokerId ) ? Utils.getSingleValueAppConfig( SvcConstants.USER + "_" + distChannel.toString() ) : brokerId.toString();
		String polType = SvcConstants.TRAVEL_POLICY_TYPE_FOR_LOOKUP.toString();

		/* Get the schemeCode using userId and policyType. */
		LookUpListVO listVO = (LookUpListVO) SvcUtils.getLookUpCodesList( SvcConstants.SCHEME, user, polType );
		String schemeCode = null;
		/*Commented and added new if condition as control was going into else block and scheme code was coming from lookup in stead of PolicyDataVO  - Ticket Id - 145686
		/*if(SvcConstants.DIST_CHANNEL_AGENT.equals(distChannel) 
				|| SvcConstants.DIST_CHANNEL_AFFINITY_AGENT.equals(distChannel)
					||SvcConstants.DIST_CHANNEL_BROKER.equals(distChannel)
					|| SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT.equals(distChannel))
					
		 	*/		
		if(!Utils.isEmpty(policyDataVO.getScheme()) && !Utils.isEmpty(policyDataVO.getScheme().getSchemeCode()))
		{
			schemeCode = policyDataVO.getScheme().getSchemeCode().toString();
		}
		else
		{
			schemeCode = listVO.getLookUpList().get( 0 ).getCode().toString();
		}

		
		//Oman Travel Changes
		String travelType = null;
		List<TableData<BaseVO>> gaccPerTableData = dataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );
		
		if( !Utils.isEmpty( gaccPerTableData.get( 0 ) ) && !Utils.isEmpty( gaccPerTableData.get( 0 ).getTableData() ) ){
			
		TTrnGaccPersonVOHolder tTrnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) gaccPerTableData.get( 0 ).getTableData();
			
			if( !Utils.isEmpty( tTrnGaccPersonVOHolder ) && !Utils.isEmpty( tTrnGaccPersonVOHolder.getGprDescription() ) ){
				String travelLocation = tTrnGaccPersonVOHolder.getGprDescription();
	
				if( !Utils.isEmpty( travelLocation ) ){
					if( travelLocation.equalsIgnoreCase( SvcConstants.WORLDWIDE_EX_US_CAN ) ){
						travelType = SvcConstants.WORLDWIDE_EX_US_CAN_TRAVEL_TYPE;
					}
					else if( travelLocation.equalsIgnoreCase( SvcConstants.WORLDWIDE ) ){
						travelType = SvcConstants.WORLDWIDE_TRAVEL_TYPE;
					}
				}
			}
		}
		
		/* Query the data base to get the covers from the view with product configurations by passing required parameters.*/ 
			//Request ## 131378  154808 New Covers for Dubai and Abu Dhabi Bahrain
			List<VMasProductConfigPas> covers=null;
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
					( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")))
			{
				
			LOGGER.debug(" ****Load Cover SI for Specific Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION) );
		Date preparedDate=null;
		preparedDate = DAOUtils.getPreparedDateForCovers(getHibernateTemplate(),commonVO.getQuoteNo());
		
		if(!Utils.isEmpty(travelType))
		 covers = (List<VMasProductConfigPas>) getHibernateTemplate().find(
				com.Constant.CONST_FROM_VMASPRODUCTCONFIGPAS_VIEW_WHERE_VIEW_PCSCHEME_END + "and view.pcClCode = ? and view.pcRcCode in (0, ?) "+
						"AND trunc(view.prcStartDate)<=? AND trunc(view.prcEndDate)>=? AND trunc(view.prRateEffectiveDate)<=? AND TRUNC(view.prRateExpiryDate)>=? order by view.pcId", Integer.parseInt( schemeCode ), classCode,Integer.parseInt(travelType) ,preparedDate,preparedDate,preparedDate,preparedDate);
		} else{
			LOGGER.debug(" ****Load Cover SI for other Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION) );
		if(!Utils.isEmpty(travelType))
		covers = (List<VMasProductConfigPas>) getHibernateTemplate().find(
					com.Constant.CONST_FROM_VMASPRODUCTCONFIGPAS_VIEW_WHERE_VIEW_PCSCHEME_END	 + "and view.pcClCode = ? and view.pcRcCode in (0, ?) order by view.pcId", Integer.parseInt( schemeCode ), classCode,Integer.parseInt(travelType) );
	
		}
		SortedSet <Integer> packageOrder = new TreeSet<Integer>();
		Map <Integer, Integer> packageMap = new HashMap<Integer, Integer>();
		try{
			for( VMasProductConfigPas cover : covers ){
				
				packageOrder.add(cover.getPcOrder());
				packageMap.put(cover.getPcOrder(), cover.getPcTariff());
			}
			
		}catch (Exception e) {
			LOGGER.debug("Exception while getting cover");
		}
		if(!Utils.isEmpty(packageOrder)){
			for (Integer orderNo: packageOrder){
				tariffCodes.add( packageMap.get(orderNo) );
			}
		}
		
		CoverDetails coverDetails = new CoverDetails();
		VMasProductConfigPasWrapper viewWrapper = new VMasProductConfigPasWrapper();
		viewWrapper.setVProductConfigPasList( covers );

		/* Map the covers into CoverDetailsVO. */
		coverDetails = BeanMapper.map( viewWrapper, coverDetails );

		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		List<TravelPackageVO> packages = new ArrayList<TravelPackageVO>();
		travelInsuranceVO.setScheme( new SchemeVO() );

		/*Set the default tariff into travelInsuranceVO.*/
		/*Added try and catch block to avoid null pointer , sonar violation fix on 9-10-2017*/
		try{
		    if( !Utils.isEmpty( covers.get( SvcConstants.zeroVal ) ) && !Utils.isEmpty( covers.get( SvcConstants.zeroVal ).getDefaultTariffCode() )){
			    travelInsuranceVO.setDefaultTariff( Integer.valueOf( covers.get( SvcConstants.zeroVal ).getDefaultTariffCode() ) );
		    }
		 }catch (NullPointerException e) {
			LOGGER.debug("Null pointer exception");
		}
		
		/* Populate the travelInsuranceVO to be passed back to the renderer.*/
		for( Integer tariff : tariffCodes ){

			TravelPackageVO packageVO = new TravelPackageVO();
			packageVO.setPackageName( SvcUtils.getLookUpDescription( SvcConstants.TARIFF, schemeCode, polType, tariff ) );
			packageVO.setTariffCode( String.valueOf( tariff ) );
			for( CoverDetailsVO cover : coverDetails.getCoverDetails() ){

				if( cover.getTariffCode().equals( tariff ) ){
					packageVO.getCovers().add( cover );
					/*Start : Set help messages for each package. Once for each package.(For B2C)*/
					if( !Utils.isEmpty( cover.getCoverDesc() ) && Utils.isEmpty( packageVO.getDescription() ) ){
						packageVO.setDescription( cover.getCoverDesc() );
					}
					/*End : Set help messages for each package.*/
				}
			}
			
			if( !Utils.isEmpty( travelInsuranceVO.getDefaultTariff() ) && tariff.equals( travelInsuranceVO.getDefaultTariff() ) ){
				packageVO.setIsSelected( Boolean.TRUE );
				packageVO.setIsRecommended( true );
			}else{
				packageVO.setIsSelected( Boolean.FALSE );
			}
			packages.add( packageVO );
		}

		updatePackageDetails( tarCode, prmTableData , packages );
		travelInsuranceVO.setTravelPackageList( packages );
		travelInsuranceVO.getScheme().setSchemeCode( Integer.parseInt( schemeCode ) );
		travelInsuranceVO.setCommission(  SvcUtils.getCommission( policyDataVO, schemeCode ) );
		
		return travelInsuranceVO;
	}

	/**
	 * @param baseVO
	 */
	@SuppressWarnings("unchecked")
	public List<TravelPackageVO> getTravelPackageList(BaseVO baseVO) {
		
		SchemeVO schemeVO = (SchemeVO) baseVO;
		Integer classCode = schemeVO.getId();
		Date preparedDate = null;
		List<VMasProductConfigPas> covers = null;
		List<Integer> tariffCodes = new ArrayList<Integer>();
		//Request ## 131378 New Covers for Dubai and Abu Dhabi
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
				( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")))
		{
			LOGGER.debug(" ****Load Cover for Specific Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION) );
			preparedDate = DAOUtils.getPreparedDateForCovers(getHibernateTemplate(), schemeVO.getQuoteNo());
		LOGGER.debug(" getTravelCovers : To Load Travel cover ----->  QuoteNo "+ schemeVO.getQuoteNo());
		LOGGER.debug(" getTravelCovers : To Load Travel cover -----> preparedDate "+ preparedDate);
		covers = (List<VMasProductConfigPas>) getHibernateTemplate().find(
					"from VMasProductConfigPas prdConfig where prdConfig.pcScheme = ?  " +
					"AND trunc(prdConfig.prcStartDate)<=? AND trunc(prdConfig.prcEndDate)>=? AND trunc(prdConfig.prRateEffectiveDate)<=? AND TRUNC(prdConfig.prRateExpiryDate)>=?  order by prdConfig.prMandatoryInd desc, prdConfig.pcId",
					schemeVO.getSchemeCode(), preparedDate,preparedDate,preparedDate,preparedDate); 
		}
		else{
			
			LOGGER.debug(" ****Load Cover for Other Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION));
			 covers = (List<VMasProductConfigPas>) getHibernateTemplate().find(
					com.Constant.CONST_FROM_VMASPRODUCTCONFIGPAS_VIEW_WHERE_VIEW_PCSCHEME_END + "and view.pcClCode = ? order by view.pcId", schemeVO.getSchemeCode() , classCode );
			
		}

		SortedSet <Integer> packageOrder = new TreeSet<Integer>();
		Map <Integer, Integer> packageMap = new HashMap<Integer, Integer>();
		for( VMasProductConfigPas cover : covers ){
			
			packageOrder.add(cover.getPcOrder());
			packageMap.put(cover.getPcOrder(), cover.getPcTariff());
		}
		
		if(!Utils.isEmpty(packageOrder)){
			for (Integer orderNo: packageOrder){
				tariffCodes.add( packageMap.get(orderNo) );
			}
		}
		
		CoverDetails coverDetails = new CoverDetails();
		VMasProductConfigPasWrapper viewWrapper = new VMasProductConfigPasWrapper();
		viewWrapper.setVProductConfigPasList( covers );

		/* Map the covers into CoverDetailsVO. */
		coverDetails = BeanMapper.map( viewWrapper, coverDetails );

		List<TravelPackageVO> packages = new ArrayList<TravelPackageVO>();
		/*TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		
		travelInsuranceVO.setScheme( new SchemeVO() );

		Set the default tariff into travelInsuranceVO.
		if( !Utils.isEmpty( covers.get( SvcConstants.zeroVal ) ) && !Utils.isEmpty( covers.get( SvcConstants.zeroVal ).getDefaultTariffCode() )){
			travelInsuranceVO.setDefaultTariff( Integer.valueOf( covers.get( SvcConstants.zeroVal ).getDefaultTariffCode() ) );
		}*/
		
		/* Populate the travelInsuranceVO to be passed back to the renderer.*/
		for( Integer tariff : tariffCodes ){

			TravelPackageVO packageVO = new TravelPackageVO();
			//packageVO.setPackageName( SvcUtils.getLookUpDescription( SvcConstants.TARIFF, schemeVO.getSchemeCode(), polType, tariff ) );
			packageVO.setTariffCode( String.valueOf( tariff ) );
			for( CoverDetailsVO cover : coverDetails.getCoverDetails() ){

				if( cover.getTariffCode().equals( tariff ) ){
					packageVO.getCovers().add( cover );
					/*Start : Set help messages for each package. Once for each package.(For B2C)*/
					if( !Utils.isEmpty( cover.getCoverDesc() ) && Utils.isEmpty( packageVO.getDescription() ) ){
						packageVO.setDescription( cover.getCoverDesc() );
					}
					/*End : Set help messages for each package.*/
				}
			}
			
			/*if( !Utils.isEmpty( travelInsuranceVO.getDefaultTariff() ) && tariff.equals( travelInsuranceVO.getDefaultTariff() ) ){
				packageVO.setIsSelected( Boolean.TRUE );
				packageVO.setIsRecommended( true );
			}else{
				packageVO.setIsSelected( Boolean.FALSE );
			}*/
			packages.add( packageVO );
		}
		
		return packages;
	}
	
	/**
	 * @param policyDataVO 
	 * @param packages
	 * Updates the saved values from transaction table.
	 */
	private void updatePackageDetails( Integer tarCode, List<TableData<BaseVO>> premTableData, List<TravelPackageVO> packages ){

		String SUM_INSURED_CHECKED = "1";
		String SUM_INSURED_UNCHECKED = "0";
		Double ZERO_VAL = new Double( 0 );
		Map<Short, TTrnPremiumVOHolder> coverVOMap = null;
		TTrnPremiumVOHolder premiumHolderVO = null;
		CoverDetailsVO coverDetailsVO = null;
		
		//update only if matching tariff is found
		if( tarCode != null ){

			coverVOMap = new HashMap<Short, TTrnPremiumVOHolder>();

			if(!Utils.isEmpty( premTableData )){
				for( TableData<BaseVO> premiumData : premTableData ){
					TTrnPremiumVOHolder prmHolderVO = (TTrnPremiumVOHolder)premiumData.getTableData();
					coverVOMap.put( prmHolderVO.getPrmCovCode(), prmHolderVO );
				}
			}
			for( TravelPackageVO travelPackageVO : packages ){//iterate over packages to identify tariff ID to be updated

				coverDetailsVO = travelPackageVO.getCovers().get( 0 );
				if( tarCode.equals( coverDetailsVO.getTariffCode() ) ){

					//iterate over fetched values
					for( CoverDetailsVO coverVO : travelPackageVO.getCovers() ){

						if( coverVOMap.containsKey( coverVO.getCoverCodes().getCovCode() ) ){
							premiumHolderVO = coverVOMap.get( coverVO.getCoverCodes().getCovCode() );

							if( !Utils.isEmpty( coverVO.getFieldType() ) && FieldType.CHECK_BOX.equals( coverVO.getFieldType() ) ){
								// update sum insured to mark checked
								coverVO.getSumInsured().seteDesc( premiumHolderVO.getPrmSumInsured().toString() );
								coverVO.getSumInsured().setSumInsured( premiumHolderVO.getPrmSumInsured().doubleValue() );
								coverVO.setIsCovered( SvcConstants.STATUS_ON );
							}
							
							if( !Utils.isEmpty( coverVO.getFieldType() ) && FieldType.RADIO.equals( coverVO.getFieldType() ) ){
								
									if(SvcConstants.PERSONAL_ACCIDENT_COVER_CODE.equals(coverVO.getCoverCodes().getCovCode()) && !Utils.isEmpty(premiumHolderVO.getPrmSumInsured()) && SvcConstants.PA_OPTION2_SI == premiumHolderVO.getPrmSumInsured().doubleValue()){
										coverVO.setIsCovered( SvcConstants.STATUS_ON );
									}
							}
							coverVO.setVsd( premiumHolderVO.getPrmValidityStartDate() );
							
							/* Setting PremiumAmt and PremiumActual as they are required in TravelPackagePremiumSvc. */
							if( !Utils.isEmpty( premiumHolderVO.getPrmPremium() ) ) coverVO.setPremiumAmt( premiumHolderVO.getPrmPremium().doubleValue() );
							if( !Utils.isEmpty( premiumHolderVO.getPrmPremiumActual() ) ) coverVO.setPremiumAmtActual( premiumHolderVO.getPrmPremiumActual().doubleValue() );

						}
						else /*if( coverVO.getSumInsured().getSumInsured().equals( ZERO_VAL ) )*/{
							//Mark as unchecked as the cover is not saved in DB. 
							//update sum insured to avoid marking checked
							
							coverVO.getSumInsured().seteDesc( SUM_INSURED_UNCHECKED );
							coverVO.getSumInsured().setSumInsured( ZERO_VAL );
						}

					}
					travelPackageVO.setIsSelected( Boolean.TRUE );
					//break; // commenting this line so that for all other packages isSelected is set as false.  
				}else{
					travelPackageVO.setIsSelected( Boolean.FALSE );
				}

			}

		}	

	}

	/* 
	 * Fetches the master list of covers for the a scheme and map into CoverDetails list.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoverDetailsVO> getTravelCovers( SchemeVO input ){

		Date preparedDate = null;
		List<Object[]> covers=null;
		//Request ## 131378 New Covers for Dubai and Abu Dhabi
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
				( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")  || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")))
		{
			LOGGER.debug(" ****Load Cover for Specific Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION) );
			preparedDate = DAOUtils.getPreparedDateForCovers(getHibernateTemplate(), input.getQuoteNo());
		LOGGER.debug(" getTravelCovers : To Load Travel cover ----->  QuoteNo "+ input.getQuoteNo());
		LOGGER.debug(" getTravelCovers : To Load Travel cover -----> preparedDate "+ preparedDate);
		 covers = (List<Object[]>) getHibernateTemplate().find(
				"select distinct prdConfig.pcId, prdConfig.pcCovCode, prdConfig.pcEDesc, prdConfig.prMandatoryInd,prdConfig.prcDisplayInd, prdConfig.prcBToCDisplayInd from VMasProductConfigPas prdConfig where prdConfig.pcScheme = ?  " +
				"AND trunc(prdConfig.prcStartDate)<=? AND trunc(prdConfig.prcEndDate)>=? AND trunc(prdConfig.prRateEffectiveDate)<=? AND TRUNC(prdConfig.prRateExpiryDate)>=?  order by prdConfig.prMandatoryInd desc, prdConfig.prcDisplayInd desc, prdConfig.pcId",
				input.getSchemeCode(), preparedDate,preparedDate,preparedDate,preparedDate); 
		}
		else{
			
			LOGGER.debug(" ****Load Cover for Other Locations *******"+Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION));
			covers = (List<Object[]>) getHibernateTemplate().find(
					"select distinct prdConfig.pcId, prdConfig.pcCovCode, prdConfig.pcEDesc, prdConfig.prMandatoryInd,prdConfig.prcDisplayInd , prdConfig.prcBToCDisplayInd  from VMasProductConfigPas prdConfig where prdConfig.pcScheme = ? order by prdConfig.pcId",
					input.getSchemeCode() );
		}
		java.util.List<CoverDetailsVO> coverDetails = new com.mindtree.ruc.cmn.utils.List<CoverDetailsVO>( CoverDetailsVO.class );
		CoverDetailsVO coverDetailsVO = null;
		CoverVO coverVO = null;
		/*RiskVO riskVO = null;*/
		for( Object[] array : covers ){

			coverDetailsVO = new CoverDetailsVO();
			coverVO = new CoverVO();

			coverVO.setCovCode( ( (Integer) array[ 1 ] ).shortValue() );
			coverDetailsVO.setCoverCodes( coverVO );
			coverDetailsVO.setCoverName( (String) array[ 2 ] );
			coverDetailsVO.setCoverDesc( (String) array[ 2 ] );
			coverDetailsVO.setMandatoryIndicator( (Boolean) array[ 3 ] );
			coverDetailsVO.setPrcDisplayInd( (Integer) array[ 4 ] );
			coverDetailsVO.setPrcBToCDisplayInd( (Integer) array[ 5 ] );

			coverDetails.add( coverDetailsVO );

		}
		return coverDetails;
	}
	
	@Override
	public List<TTrnPremiumVOHolder> getPremiumRecords( CommonVO commonVO ){

		/*
		 * Fetch the policyId and endtId from input and get the brokerId and distributionChannel from TTrnPolicyQuo. These will be the parameters
		 * to fetch the scheme from lookUp for that particular user. Use the scheme to fetch the packages for Travel from the view .
		 * 
		 * */

		/* Prepare the data to fetch the records from database.*/
		LoadDataInputVO inputVO = new LoadDataInputVO();
	
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		
		if(commonVO.getIsQuote()){
			inputVO.setQuoteNo( commonVO.getQuoteNo() );
		}else{
			inputVO.setPolicyNo( commonVO.getPolicyNo() );
		}
		inputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		inputVO.setDocCode( commonVO.getDocCode() );
		List<TTrnPremiumVOHolder> premiumRecList = new ArrayList<TTrnPremiumVOHolder>( 0 );

		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		dataMap.put( SvcConstants.T_TRN_PREMIUM, TTrnPremiumVOHolder.class );

		if( inputVO.getIsQuote() ){
			baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION );
		}
		else{
			baseLoadOperation = (IBaseLoadOperation) Utils.getBean( com.Constant.CONST_BASELOADOPERATION_POL );
		}

		@SuppressWarnings("unchecked")
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>>) baseLoadOperation.loadData(
				inputVO, dataMap );

		//	List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		//PolicyDataVO policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();

		List<TableData<BaseVO>> prmTableData = dataHolder.getData().get( SvcConstants.T_TRN_PREMIUM );
		if (!Utils.isEmpty(prmTableData)) {
			for (TableData<BaseVO> premiumData : prmTableData) {
				TTrnPremiumVOHolder prmHolderVO = (TTrnPremiumVOHolder) premiumData.getTableData();
				premiumRecList.add(prmHolderVO);
			}
		}
		return premiumRecList;
	}


}
