package com.rsaame.pas.svc.utils;

import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * A utility class that provides convenience methods to access PolicyVO and its structural elements. However, this
 * class is not meant to provide any feature to modify its contents. Quite a few of the methods are a replica of
 * PolicyContext class in PAS project. However, they have been intentionally kept separate.
 */
public class PolicyUtils{
	
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	
	/**
	 * 
	 * @param policy
	 * @param sectionId
	 * @return
	 * Returns the SectionVO for the passed section Id
	 */
	public static SectionVO getSectionVO( PolicyVO policy, int sectionId ){

		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );
		if(policy.getRiskDetails().contains( finderSection )){
			return policy.getRiskDetails().get( policy.getRiskDetails().indexOf( finderSection ) );
		}
		return null;
		
	}
	
	/*public static RiskGroup getRiskGroup( PolicyVO policy, int sectionId, String riskGroupId ){
		
		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );
		SectionVO riskDetails = policy.getRiskDetails().get( policy.getRiskDetails().indexOf( finderSection ) );
		
	}*/
	/**
	 * 
	 * @param section
	 * @return
	 * Return the location for to be processed, that is the location where to save is set to true
	 */
	public static RiskGroup getRiskGroupForProcessing( SectionVO section ){
		Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
		
		LocationVO locationDetails = null;
		for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
			locationDetails = (LocationVO) locationEntry.getKey();
			if( locationDetails.getToSave().booleanValue() ){
				break;
			}
		}
		return locationDetails;
	}

	/**
	 * 
	 * @param rg
	 * @param section
	 * @return
	 * Returns the risk details for the location being processed
	 */
	public static RiskGroupDetails getRiskGroupDetails( RiskGroup rg, SectionVO section ){
		Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
		return riskGroupDetails.get( rg );
	}
	
	/**
	 * 
	 * @param policyVO
	 * @return
	 * Returns the basic risk id, if par is present par id is returned else pls id is returned 
	 */
	public static Integer getBasicSectionId( PolicyVO policyVO ){
		Integer basicSectionId = null;
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( PAR_SECTION_ID );

		if( policyVO.getRiskDetails().contains( section ) ){
			basicSectionId = PAR_SECTION_ID;
		}
		else{
			section.setSectionId( PL_SECTION_ID );
			if( policyVO.getRiskDetails().contains( section ) ){
				basicSectionId = PL_SECTION_ID;
			}
		}
		return basicSectionId;
	}
	
	public static SectionVO getBasicSectionVO( PolicyVO policyVO ){
		
		Integer basicSectionId  = getBasicSectionId(  policyVO );
		return getSectionVO(  policyVO,  basicSectionId );
	}
	
	/**
	 * Returns the risk details of the of basic risk, i.e ParVO or PublicLiabilityVO
	 */
	public static RiskGroupDetails getBasicSectionRiskDetails(PolicyVO policyVO, LocationVO locationVO )
	{
		Integer basicSectionId = getBasicSectionId(  policyVO );
		SectionVO sectionVO = getSectionVO(  policyVO,  basicSectionId );
		return sectionVO.getRiskGroupDetails().get( locationVO );
	}
	
	public static RiskGroupDetails getRiskGroupDetailsForProcessing( PolicyVO policy, int sectionId ){
		SectionVO section = getSectionVO( policy, sectionId );
		return getRiskGroupDetails( getRiskGroupForProcessing( section ), section );
	}
}
