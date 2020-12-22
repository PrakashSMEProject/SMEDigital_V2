package com.rsaame.pas.home.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.RiskVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;
import org.apache.log4j.Logger;

/**
 * Service which is common to both B2B and B2C, fetches the content and premium data and populates the CoverDetailsVO
 * service is invoked with the LoadDataInputVO's object as input.
 * 
 * @author M1016996 Mindtree Limited
 */
public class HomeCoverDetailsLoadSvc extends BaseService{
	
	private final static Logger LOGGER = Logger.getLogger(HomeCoverDetailsLoadSvc.class);

	private BaseLoadSvc baseLoadSvc;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( "homeCoverDetailsLoadService".equals( methodName ) ){
			returnValue = loadHomeCoverDetails( (BaseVO) args[ 0 ], (HomeInsuranceVO) args[ 1 ] );
		}
		return returnValue;
	}

	/**
	 * Returns the populated coverdetailsVO list.
	 * 
	 * @param baseVO
	 * @param homeInsuranceVO 
	 * @return
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private HomeInsuranceVO loadHomeCoverDetails( BaseVO baseVO, HomeInsuranceVO homeInsuranceVO ){
		LOGGER.info("Entered HomeCoverDetailsLoadSvc.loadHomeCoverDetails method.");
		LoadDataInputVO loadInputVO = (LoadDataInputVO) baseVO;

		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		dataToLoad.put( SvcConstants.T_TRN_CONTENT, CoverDetailsVO.class );
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad",
				loadInputVO, dataToLoad );
		
		PolicyDataVO policyDataVO = (PolicyDataVO) dataHolderVO.getData().get( SvcConstants.T_TRN_POLICY ).get( 0 ).getTableData();
		 
		List<TableData> coverDetailsVOs = dataHolderVO.getData().get( SvcConstants.T_TRN_CONTENT );
		
		List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
		List<CoverDetailsVO> detailsVOs = new ArrayList<CoverDetailsVO>();
		if( !Utils.isEmpty( coverDetailsVOs ) ){
			for( TableData coverDetailData : coverDetailsVOs ){

				CoverDetailsVO coverDetailsVO = (CoverDetailsVO) coverDetailData.getTableData();
				if( !Utils.isEmpty( premiumList ) ){
					Iterator<TableData> iterator = premiumList.iterator();

					while( iterator.hasNext() ){

						TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();

						if( isThisContentRelatedRecord( premiumVOHolder ) ){

							if( premiumVOHolder.getPrmCovCode() == 1 ){

								if( areTheyRelated( coverDetailsVO, premiumVOHolder ) ){
									mapCoverDetailsFromPremium( coverDetailsVO, premiumVOHolder );
									detailsVOs.add( coverDetailsVO );
									iterator.remove();
									//break;
								}

							}
							else{
								CoverDetailsVO additionalCoverDetailsVO2 = new CoverDetailsVO();
								mapCoverDetailsFromPremium( additionalCoverDetailsVO2, premiumVOHolder );
								detailsVOs.add( additionalCoverDetailsVO2 );
								iterator.remove();
								//break;
							}
						}
						// check for special covers
						else if(Integer.valueOf( Utils.getSingleValueAppConfig( "SPECIAL_CODE" ) ).equals( premiumVOHolder.getPrmRskCode() )){
							
							populateSpecialCovPrm( homeInsuranceVO, premiumVOHolder );
							
						}
						else{
							iterator.remove();
						}

					}
				}
			}
			
		}
		else{
			
			if( !Utils.isEmpty( premiumList ) ){
				Iterator<TableData> iterator = premiumList.iterator();
				while( iterator.hasNext() ){
					TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();
					if(Integer.valueOf( Utils.getSingleValueAppConfig( "SPECIAL_CODE" ) ).equals( premiumVOHolder.getPrmRskCode() )){
						populateSpecialCovPrm( homeInsuranceVO, premiumVOHolder );
					}
				}
			}
			

		}
		
		List<Object> promoDiscountWithDesc = null;
		if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getSchemeCode() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo() )
				&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPromoCode() )
				&& !Utils.isEmpty( policyDataVO.getPolicyClassCode() ) && !Utils.isEmpty( policyDataVO.getScheme().getEffDate() ) ){
			LOGGER.info("HomeCoverDetailsLoadSvc.loadHomeCoverDetails method, calling HomeCoverDetailsLoadSvc.loadPromotionalCovers method - starts");
			promoDiscountWithDesc = loadPromotionalCovers( policyDataVO.getScheme().getSchemeCode(), policyDataVO.getGeneralInfo().getSourceOfBus().getPromoCode(),
					policyDataVO.getPolicyClassCode(), policyDataVO.getScheme().getEffDate(),homeInsuranceVO.getCommonVO().getIsQuote() );
			LOGGER.info("HomeCoverDetailsLoadSvc.loadHomeCoverDetails method, calling HomeCoverDetailsLoadSvc.loadPromotionalCovers method - completes.");

		}

		if( Utils.isEmpty( homeInsuranceVO.getPremiumVO() ) ) homeInsuranceVO.setPremiumVO( new PremiumVO() );
		
		if( !Utils.isEmpty( promoDiscountWithDesc ) && !Utils.isEmpty( promoDiscountWithDesc.get( 0 ) ) ){
			if( Utils.isEmpty( homeInsuranceVO.getPremiumVO().getPromoDiscPerc() ) || !homeInsuranceVO.getPremiumVO().getPromoDiscPerc().equals( promoDiscountWithDesc.get( 0 ) ) ){

				homeInsuranceVO.getPremiumVO().setPromoDiscPerc( ( (Double)promoDiscountWithDesc.get( 0 ) ).doubleValue() );
			}
			if( !Utils.isEmpty( promoDiscountWithDesc.get( 1 ) ) ){
				//GeneralInfoVO will be null at this point.
				GeneralInfoVO generalInfoVO = null;
				SourceOfBusinessVO sourceOfBusinessVO = null;
				if( !Utils.isEmpty( homeInsuranceVO.getGeneralInfo() ) ){
					generalInfoVO = homeInsuranceVO.getGeneralInfo();
					if( !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus() ) ){
						sourceOfBusinessVO =  homeInsuranceVO.getGeneralInfo().getSourceOfBus();
					}else{
						sourceOfBusinessVO = new SourceOfBusinessVO();
					}
				}else{
					generalInfoVO = new GeneralInfoVO();
					sourceOfBusinessVO = new SourceOfBusinessVO();	
				}
				sourceOfBusinessVO.setPromoCodeDesc(  promoDiscountWithDesc.get( 1 ).toString() );
				generalInfoVO.setSourceOfBus( sourceOfBusinessVO );
				homeInsuranceVO.setGeneralInfo( generalInfoVO );
			}
		}
		
		homeInsuranceVO.setCovers( detailsVOs );
		
		if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getSchemeCode() ) ){
			homeInsuranceVO.setCommission( SvcUtils.getCommission( policyDataVO, policyDataVO.getScheme().getSchemeCode().toString() ) );
		}
		
		LOGGER.info("Exiting HomeCoverDetailsLoadSvc.loadHomeCoverDetails method.");
		return homeInsuranceVO;
	}

	/**
	 * @param homeInsuranceVO
	 * @param premiumVOHolder
	 */
	private void populateSpecialCovPrm( HomeInsuranceVO homeInsuranceVO, TTrnPremiumVOHolder premiumVOHolder ){
		if(Utils.isEmpty( homeInsuranceVO.getPremiumVO() )){
			homeInsuranceVO.setPremiumVO( new PremiumVO() );
		}
		
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_DISCOUNT )
				|| Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_LOADING ) ){
			if( !Utils.isEmpty( premiumVOHolder.getPrmLoadDisc() ) ){

				homeInsuranceVO.getPremiumVO().setDiscOrLoadPerc( premiumVOHolder.getPrmLoadDisc().doubleValue() );
			}
		}
		/*
		 * Set the promo code discount if any
		 */
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_PROMO_DISC ) ){

				homeInsuranceVO.getPremiumVO().setPromoDiscPerc(  !Utils.isEmpty( premiumVOHolder.getPrmLoadDisc() )? premiumVOHolder.getPrmLoadDisc().doubleValue(): 0.0 );
			}
		/*
		 * Set Policy Fees if any
		 */
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_POLICY_FEE ) ){

			homeInsuranceVO.getPremiumVO().setPolicyFees( !Utils.isEmpty( premiumVOHolder.getPrmPremium() )? premiumVOHolder.getPrmPremium().doubleValue(): 0.0 );
		}
		/*
		 * Set Govt Tax if any
		 */
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_GOVT_TAX ) ){

			homeInsuranceVO.getPremiumVO().setGovtTax( !Utils.isEmpty( premiumVOHolder.getPrmPremium() )? premiumVOHolder.getPrmPremium().doubleValue(): 0.0 );
		}
		
		/*
		 * Set Vat Tax if any 142244
		 */
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX ) ){

			homeInsuranceVO.getPremiumVO().setVatTax( !Utils.isEmpty( premiumVOHolder.getPrmPremium() )? premiumVOHolder.getPrmPremium().doubleValue(): 0.0 );
		}
		/*
		 * Set Vat Tax Perc if any 142244
		 */
		if( Short.valueOf( premiumVOHolder.getPrmCovCode() ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX ) ){

			homeInsuranceVO.getPremiumVO().setVatTaxPerc( !Utils.isEmpty( premiumVOHolder.getPrmPremium() )? (!Utils.isEmpty( premiumVOHolder.getPrmRate())?premiumVOHolder.getPrmRate().doubleValue():0.0): 0.0 );
		}
		
	}
	
	/**
	 * To load the promotional covers for the selected scheme
	 * @param homeInsuranceVO
	 */
	private List<Object> loadPromotionalCovers(Integer schemeCode, String promoCode, Integer classCode, Date effectiveDate,Boolean isQuote){
		
		List<Object> promoDiscount = DAOUtils.getPromotionalCodeDiscount( schemeCode,promoCode, classCode, effectiveDate,isQuote );

		return promoDiscount;
	}
	

	/**
	 * Returns boolean base on whether the record is of content type(Just filter in the premium content records).
	 * 
	 * @param premiumVOHolder
	 * @return boolean
	 */
	private boolean isThisContentRelatedRecord( TTrnPremiumVOHolder premiumVOHolder ){

		if( premiumVOHolder.getPrmRskCode() == 2 && premiumVOHolder.getPrmRtCode() != 1 ){
			return true;
		}

		return false;
	}

	/**
	 * Returns an boolean based on whether an object of CoverdetailsVO and TTrnPremiumVOHolder are related to
	 * each other(Every entry in coverdetailsVO will be having a entry in the Premium table). So to find this
	 * difference we are using the below method
	 * 
	 * @param coverDetailsVO
	 * @param premiumVOHolder
	 * @return boolean
	 */
	public boolean areTheyRelated( CoverDetailsVO coverDetailsVO, TTrnPremiumVOHolder premiumVOHolder ){

		RiskVO coverDetRiskVO = coverDetailsVO.getRiskCodes();

		if( !coverDetRiskVO.getRskId().equals( premiumVOHolder.getPrmRskId() ) ){
			return false;
		}

		return true;

	}

	/**
	 * Will map the data from Premium table to coverDetailsVO 
	 * 
	 * @param coverDetailsVO
	 * @param premiumVOHolder
	 */
	private void mapCoverDetailsFromPremium( CoverDetailsVO coverDetailsVO, TTrnPremiumVOHolder premiumVOHolder ){

		BeanMapper.map( premiumVOHolder, coverDetailsVO );

	}

	/**
	 * 
	 * @param loadOperation
	 * @return
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}

}
