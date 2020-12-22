/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.PremiumVO;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.WorkmenCompVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnPremiumVOHolderMapperReverse.class )</code>.
 */
public class WorkmenCompVOToTTrnPremiumVOHolderMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnPremiumVOHolder, com.rsaame.pas.vo.bus.WorkmenCompVO>{

	public final static Short SC_PRM_COVER_GOVT_TAX = 101;
	public final static Short SC_PRM_COVER_POLICY_FEE = 100;
	public final static Short SC_PRM_COVER_DISCOUNT = 51;
	public final static Short SC_PRM_COVER_LOADING = 20;
	//Added for AdventId:142244 - WC
	public final static Short SC_PRM_COVER_VAT= 151;

	private final Logger log = Logger.getLogger( this.getClass() );

	public WorkmenCompVOToTTrnPremiumVOHolderMapperReverse(){
		super();
	}

	public WorkmenCompVOToTTrnPremiumVOHolderMapperReverse( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder src, com.rsaame.pas.vo.bus.WorkmenCompVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WorkmenCompVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WorkmenCompVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WorkmenCompVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		for( int i = 0; i < beanB.getEmpTypeDetails().size(); i++ ){
			if( Utils.isEmpty( beanB.getPremiumVO() ) ){
				beanB.setPremiumVO( new PremiumVO() );
			}
			if( beanB.getEmpTypeDetails().get( i ).getRiskId().toString().equals( beanA.getPrmRskId().toString() ) ){

				beanB.getEmpTypeDetails().get( i ).setPremium( new PremiumVO() );

				/* Mapping: "prmBasicRskCode" -> "empTypeDetails[].basicRiskCode" */
				if( !Utils.isEmpty( beanA.getPrmBasicRskCode() ) ){
					beanB.getEmpTypeDetails().get( i ).setBasicRiskCode( beanA.getPrmBasicRskCode() );
				}

				/* Mapping: "prmRskCode" -> "empTypeDetails[].riskCode" */
				if( !Utils.isEmpty( beanA.getPrmRskCode() ) ){
					beanB.getEmpTypeDetails().get( i ).setRiskCode( beanA.getPrmRskCode() );
				}

				/* Mapping: "prmCovCode" -> "empTypeDetails[].coverCode" */
				if( !Utils.isEmpty( beanA.getPrmCovCode() ) ){
					com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
					beanB.getEmpTypeDetails().get( i ).setCoverCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmCovCode() ) ) );
				}

				/* Mapping: "prmCtCode" -> "empTypeDetails[].coverType" */
				if( !Utils.isEmpty( beanA.getPrmCtCode() ) ){
					com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
					beanB.getEmpTypeDetails().get( i ).setCoverType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmCtCode() ) ) );
				}

				/* Mapping: "prmCstCode" -> "empTypeDetails[].coverSubType" */
				if( !Utils.isEmpty( beanA.getPrmCstCode() ) ){
					com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
					beanB.getEmpTypeDetails().get( i ).setCoverSubType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmCstCode() ) ) );
				}

				/* Mapping: "prmValidityStartDate" -> "empTypeDetails[].vsd" */
				if( !Utils.isEmpty( beanA.getPrmValidityStartDate() ) ){
					beanB.getEmpTypeDetails().get( i ).setVsd( beanA.getPrmValidityStartDate() );
				}

				/* Mapping: "prmClCode" -> "empTypeDetails[].classCode" */
				if( !Utils.isEmpty( beanA.getPrmClCode() ) ){
					beanB.getEmpTypeDetails().get( i ).setClassCode( beanA.getPrmClCode() );
				}

				/* Mapping: "prmPremium" -> "empTypeDetails[].premium.premiumAmt" */
				if( !Utils.isEmpty( beanA.getPrmPremium() ) ){
					com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class,
							"", "" );
					beanB.getEmpTypeDetails().get( i ).getPremium().setPremiumAmt( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmPremium() ) ) );
				}

				/* Mapping: "prmCompulsoryExcess" -> "empTypeDetails[].deductibles" */
				if( !Utils.isEmpty( beanA.getPrmCompulsoryExcess() ) ){
					beanB.getEmpTypeDetails().get( i ).setDeductibles( beanA.getPrmCompulsoryExcess() );
				}

				/* Mapping: "prmPremiumActual" -> "empTypeDetails[].premium.premiumAmtActual" */
				if( !Utils.isEmpty( beanA.getPrmPremiumActual() ) ){
					com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class,
							"", "" );
					beanB.getEmpTypeDetails().get( i ).getPremium().setPremiumAmtActual( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmPremiumActual() ) ) );

				}
				break;
			}
			else if( Short.valueOf( beanA.getPrmCovCode() ).equals( SC_PRM_COVER_DISCOUNT ) || Short.valueOf( beanA.getPrmCovCode() ).equals( SC_PRM_COVER_LOADING ) ){
				if( !Utils.isEmpty( beanA.getPrmLoadDisc() ) ){
					beanB.getPremiumVO().setDiscOrLoadPerc( beanA.getPrmLoadDisc().doubleValue() );
				}
				break;
			}
			else if( Short.valueOf( beanA.getPrmCovCode() ).equals( Utils.getSingleValueAppConfig( "SPECIAL_COVER_CODES_PROMO_DISC" ) ) ){
				beanB.getPremiumVO().setPromoDiscPerc( !Utils.isEmpty( beanA.getPrmLoadDisc() ) ? beanA.getPrmLoadDisc().doubleValue() : 0.0 );
				break;
			}
			else if( Short.valueOf( beanA.getPrmCovCode() ).equals( SC_PRM_COVER_POLICY_FEE ) ){
				beanB.getPremiumVO().setPolicyFees( !Utils.isEmpty( beanA.getPrmPremium() ) ? beanA.getPrmPremium().doubleValue() : 0.0 );
				break;
			}
			else if( Short.valueOf( beanA.getPrmCovCode() ).equals( SC_PRM_COVER_GOVT_TAX ) ){
				beanB.getPremiumVO().setGovtTax( !Utils.isEmpty( beanA.getPrmPremium() ) ? beanA.getPrmPremium().doubleValue() : 0.0 );
				break;
			}
			/* Mapping: "polVatTax" -> "premiumVO.vatTax" 142244 */
			else if( Short.valueOf( beanA.getPrmCovCode() ).equals( SC_PRM_COVER_VAT ) ){
				beanB.getPremiumVO().setVatTax( !Utils.isEmpty( beanA.getPrmPremium() ) ? beanA.getPrmPremium().doubleValue() : 0.0 );
				//beanB.getPremiumVO().setVatTaxPerc( !Utils.isEmpty( beanA.getPrmPremium() ) ? beanA.getPrmRate().doubleValue() : 0.0 );
				break;
			}
			
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WorkmenCompVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA, com.rsaame.pas.vo.bus.WorkmenCompVO beanB ){
		return beanB;
	}
}
