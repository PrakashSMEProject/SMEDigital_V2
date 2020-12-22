package com.rsaame.pas.b2c.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * Validator class for web-services lookup data validation
 * 
 * @author M1020859
 *
 */
public class TravelInsuranceLookupValidator implements Validator {
	
	private static final Logger logger = Logger.getLogger(TravelInsuranceLookupValidator.class);
	private LookUpService lookUpService;
	private List<String> relationList = new ArrayList<String>();
	private List<String> nationalityList = new ArrayList<String>();
	private List<String> locationList = new ArrayList<String>();
	private List<String> policyTermList = new ArrayList<String>();
	
	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object travelInsuranceObject, Errors errors) {
		
		List<LookUpListVO> lookUpListVO = loadLookupData();
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO)travelInsuranceObject;
		
		if (!Utils.isEmpty(lookUpListVO) && !Utils.isEmpty(travelInsuranceVO)) {
			// Populate relation list and nationality list
			populateLists(lookUpListVO);
			// Validate the travel period
			validateTravelPrd(travelInsuranceVO,errors);
			// Validate travel location
			validateTravelLoc(travelInsuranceVO.getTravelDetailsVO(),errors);
			// Validate traveler details
			validateTravelerDetails(travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList(),errors);
		}
	}

	/**
	 * This method will prepare the lookupVO object to load
	 * the lookup data from the cache
	 * 
	 * @return
	 */
	private List<LookUpListVO> loadLookupData() {

		LookUpListVO lookUpListVO = (LookUpListVO) Utils.getBean("lookUpListVO");
		List<LookUpVO> lookUpVOList = new ArrayList<LookUpVO>();
		List<LookUpListVO> lookUpListVOs = new ArrayList<LookUpListVO>();

		LookUpVO policyPrdLookupVO = (LookUpVO) Utils.getBean(com.Constant.CONST_LOOKUPVO);
		policyPrdLookupVO.setCategory("POLICY_TERM");
		policyPrdLookupVO.setLevel1("ALL");
		policyPrdLookupVO.setLevel2("No");
		lookUpVOList.add(policyPrdLookupVO);

		LookUpVO relationLookUpVO = (LookUpVO) Utils.getBean(com.Constant.CONST_LOOKUPVO);
		relationLookUpVO.setCategory("PAS_RELATN");
		relationLookUpVO.setLevel1("ALL");
		relationLookUpVO.setLevel2("ALL");
		lookUpVOList.add(relationLookUpVO);
		
		LookUpVO nationalityLookUpVO = (LookUpVO) Utils.getBean(com.Constant.CONST_LOOKUPVO);
		nationalityLookUpVO.setCategory("NATIONALITY");
		nationalityLookUpVO.setLevel1("ALL");
		nationalityLookUpVO.setLevel2("ALL");
		lookUpVOList.add(nationalityLookUpVO);
		
		LookUpVO travelLocationLookup = (LookUpVO) Utils.getBean(com.Constant.CONST_LOOKUPVO);
		travelLocationLookup.setCategory("PAS_TR_LOC");
		travelLocationLookup.setLevel1("ALL");
		travelLocationLookup.setLevel2("No");
		lookUpVOList.add(travelLocationLookup);

		lookUpListVO.setLookUpList(lookUpVOList);

		lookUpListVOs = fetchLookupData(lookUpListVO);
		
		return lookUpListVOs;
	}

	/**
	 * This method will query to service layer to get the lookup data
	 * 
	 * @param lookUpListVO
	 * @return
	 */
	private List<LookUpListVO> fetchLookupData(LookUpListVO lookupList) {
		
		LookUpListVO lookUpListVO = new LookUpListVO();
		List<LookUpListVO> lookUpListVOs = new ArrayList<LookUpListVO>();
		if (!Utils.isEmpty(lookupList)) {
			for(LookUpVO lookUpVO:lookupList.getLookUpList()) {
				lookUpListVO = (LookUpListVO) lookUpService.getListOfDescription( lookUpVO );
				logger.debug("First look up object - "+lookUpListVO);
				lookUpListVOs.add(lookUpListVO);
			}
		}
		return lookUpListVOs;
	}
	
	/**
	 * This method will validate the travel period against the lookup
	 * 
	 * @param travelInsuranceVO
	 * @param errors
	 */
	private void validateTravelPrd(TravelInsuranceVO travelInsuranceVO, Errors errors) {
		
		if (!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(policyTermList)) {
			if (!policyTermList.contains(String.valueOf(travelInsuranceVO.getPolicyTerm()))) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.travelPeriod", "Please select appropriate appropriate travel period");
			}
		}
	}
	
	/**
	 * This method will validate the travel location against the lookup
	 * 
	 * @param travelDetailsVO
	 * @param errors
	 */
	private void validateTravelLoc(TravelDetailsVO travelDetailsVO, Errors errors) {
		
		if (!Utils.isEmpty(travelDetailsVO) && !Utils.isEmpty(locationList)) {
			if (!locationList.contains(travelDetailsVO.getTravelLocation())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.travelPeriod", "Please select appropriate travel location");
			}
		}
	}
	
	/**
	 * This method will validate the details against traveler entered
	 * as relation and nationality 
	 * 
	 * @param travelerDetails
	 * @param lookUpListVO
	 * @param errors
	 */
	private void validateTravelerDetails(List<TravelerDetailsVO> travelerDetails, Errors errors) {
		if (!Utils.isEmpty(relationList) && !Utils.isEmpty(nationalityList)) {
			
			for (TravelerDetailsVO travelerDetailsVO:travelerDetails) {
				
				// Going to check for Nationality
				if (!Utils.isEmpty(travelerDetailsVO.getNationality())) {
					if (!nationalityList.contains(String.valueOf(travelerDetailsVO.getNationality()))) {
						errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.mobileNo.invalid", "Please select appropriate relation");
					}
				}
				
				// Going to check for Relation
				if (!Utils.isEmpty(travelerDetailsVO.getRelation())) {
					if (!relationList.contains(String.valueOf(travelerDetailsVO.getRelation()))) {
						errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.mobileNo.invalid", "Please select appropriate relation");
					}
				}
			}
		}
	}
	
	/**
	 * This method will populate all the generalized list for relation, nationality,
	 * location, and policy term
	 * 
	 * @param lookUpListVO
	 */
	private void populateLists(List<LookUpListVO> lookUpListVO) {
		if (!Utils.isEmpty(lookUpListVO)) {
			for (LookUpListVO lookListVO : lookUpListVO) {
				if (!Utils.isEmpty(lookListVO.getLookUpList())) {
					for (LookUpVO lookUpVO:lookListVO.getLookUpList()) {
						if (lookUpVO.getCategory().equalsIgnoreCase("PAS_RELATN")) {
							relationList.add(String.valueOf(lookUpVO.getCode()));
						}
						if (lookUpVO.getCategory().equalsIgnoreCase("NATIONALITY")) {
							nationalityList.add(String.valueOf(lookUpVO.getCode()));
						}
						if(lookUpVO.getCategory().equalsIgnoreCase("PAS_TR_LOC")) {
							locationList.add(lookUpVO.getDescription());
						}
						if(lookUpVO.getCategory().equalsIgnoreCase("POLICY_TERM")) {
							policyTermList.add(String.valueOf(lookUpVO.getCode()));
						}
					}
				}
			}
			relationList.removeAll(Collections.singleton(null));
			nationalityList.removeAll(Collections.singleton(null));
			locationList.removeAll(Collections.singleton(null));
			policyTermList.removeAll(Collections.singleton(null));
		}
	}
	
	/**
	 * @return the lookUpService
	 */
	public LookUpService getLookUpService() {
		return lookUpService;
	}

	/**
	 * @param lookUpService
	 *            the lookUpService to set
	 */
	public void setLookUpService(LookUpService lookUpService) {
		this.lookUpService = lookUpService;
	}

	/**
	 * @return the relationList
	 */
	public List<String> getRelationList() {
		return relationList;
	}

	/**
	 * @param relationList the relationList to set
	 */
	public void setRelationList(List<String> relationList) {
		this.relationList = relationList;
	}

	/**
	 * @return the nationalityList
	 */
	public List<String> getNationalityList() {
		return nationalityList;
	}

	/**
	 * @param nationalityList the nationalityList to set
	 */
	public void setNationalityList(List<String> nationalityList) {
		this.nationalityList = nationalityList;
	}

	/**
	 * @return the locationList
	 */
	public List<String> getLocationList() {
		return locationList;
	}

	/**
	 * @param locationList the locationList to set
	 */
	public void setLocationList(List<String> locationList) {
		this.locationList = locationList;
	}

	/**
	 * @return the policyTermList
	 */
	public List<String> getPolicyTermList() {
		return policyTermList;
	}

	/**
	 * @param policyTermList the policyTermList to set
	 */
	public void setPolicyTermList(List<String> policyTermList) {
		this.policyTermList = policyTermList;
	}

	
}
