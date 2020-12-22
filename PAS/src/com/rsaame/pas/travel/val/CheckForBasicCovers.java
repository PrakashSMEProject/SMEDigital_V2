/**
 * 
 */
package com.rsaame.pas.travel.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CoverVO;

/**
 * @author M1016284
 *
 */
public class CheckForBasicCovers implements IBeanValidator{

	@Override
	public boolean validate( Object fieldData, Map<String, String> parameters, List<String> errorKeys ){

		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) fieldData;

		boolean atLeastOneBasicCoverFound = false;
		List<CoverDetailsVO> covers = null;
		CoverDetailsVO coverDetailsVO = null;
		CoverDetailsVO coverRetrieved = null;
		CoverVO coverVO = null;
		
		String[] mandatoryCovers = Utils.getMultiValueAppConfig( "MANDATORY_COVERS_TRAVEL" );
		String[] doNotCheckForTariffs = Utils.getMultiValueAppConfig( "COVERS_VALIDATION_NOT_FOR" );

		for( TravelPackageVO packageVO : travelInsuranceVO.getTravelPackageList() ){
			/* Validate only if the tariff is not among the ones not to be checked.
			 * Not to be checked tariffs are those for which personal possession and emergency medical expenses 
			 * are not covered ( they appear as cross mark in the UI ).
			 * */
			if( packageVO.getIsSelected() && !CopyUtils.asList( doNotCheckForTariffs ).contains( packageVO.getTariffCode().toString() ) ){
				
				covers = packageVO.getCovers();
				
				for( String covCode : mandatoryCovers ){
					
					coverVO = new CoverVO();
					coverDetailsVO = new CoverDetailsVO();
					coverVO.setCovCode( Short.valueOf( covCode ) );
					coverDetailsVO.setCoverCodes( coverVO );
					coverRetrieved = null;
					
					if( covers.contains( coverDetailsVO ) ){
						coverRetrieved = covers.get( covers.indexOf( coverDetailsVO ) );
						if( ( coverRetrieved.getSumInsured().getSumInsured().compareTo( Double.valueOf( AppConstants.zeroVal ) ) > 0 )
								|| ( !Utils.isEmpty( coverRetrieved.getIsCovered() ) && AppConstants.STATUS_ON.equalsIgnoreCase( coverRetrieved.getIsCovered() ) ) ){
							atLeastOneBasicCoverFound = true;
							break;
						}
						
					}
					
				}
				
			}else if( CopyUtils.asList( doNotCheckForTariffs ).contains( packageVO.getTariffCode().toString() ) ){
				/* Set the boolean variable as true so that exception is not thrown for tariffs on which validation is not required. */
				atLeastOneBasicCoverFound = true;
			}
			
		}
		if( !atLeastOneBasicCoverFound ){
			errorKeys.add( "travel.basicCovers.notFound" );
		}
		return atLeastOneBasicCoverFound;
	}

}
