/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceUtils;
import com.rsaame.pas.b2c.ws.vo.BuildingDetails;
import com.rsaame.pas.b2c.ws.vo.CustomerDetails;
import com.rsaame.pas.b2c.ws.vo.Extras;
import com.rsaame.pas.b2c.ws.vo.FeesAndTaxes;
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.PartnerDetails;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.b2c.ws.vo.StaffDetails;
import com.rsaame.pas.b2c.ws.vo.TLLimit;
import com.rsaame.pas.b2c.ws.vo.TransactionDetails;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteResponse;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;

/**
 * @author M1037404
 *
 */
public class HomeUpdateQuoteResponseMapper implements BaseResponseVOMapper{

	private final static Logger LOGGER = Logger.getLogger(HomeUpdateQuoteResponseMapper.class);
	
	@Override
	public void mapVOToResponse(Object valueObj, Object responseObj) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("Enters to HomeUpdateQuoteResponseMapper.mapVOToResponse, maps the homeInsuranceVO details to response...");
		if (responseObj instanceof UpdateHomeQuoteResponse
				&& valueObj instanceof HomeInsuranceVO) {
			// requestObj instanceof RetrieveQuoteByPolicyRequestMapper
			UpdateHomeQuoteResponse updateHomeQuoteResponse = (UpdateHomeQuoteResponse) responseObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			mapQuoteDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapFeesAndTaxes(updateHomeQuoteResponse,homeInsuranceVO);
			
			//mapPartnerDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapCustomerDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapTransactionDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapMandatoryOptionalCovers(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapOptionalCoversDetails(updateHomeQuoteResponse, homeInsuranceVO);
			
			mapBuildingDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			//mapStaffDetails(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapUnderWritingQuestions(updateHomeQuoteResponse,homeInsuranceVO);
			
			mapExtraDetails(updateHomeQuoteResponse, homeInsuranceVO);
			
			LOGGER.info("Successfully mapped to HomeInsuranceVO details to response object..");
		}
		else {
			throw new Exception("Unexpected response or value object");
		}
		
	}
	

	private void mapUnderWritingQuestions(UpdateHomeQuoteResponse updateHomeQuoteResponse,
			HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		
	}

	private void mapStaffDetails(HomeInsuranceVO homeInsuranceVO, OptionalCovers opList) throws ParseException {
		List<TTrnGaccPersonQuo> resultList = DAOUtils.getGaccPersonDetails(homeInsuranceVO);
		if( !Utils.isEmpty( resultList ) ){
				if(Utils.isEmpty(opList.getStaffDetails())) {
					opList.setStaffDetails(new StaffDetails());
					opList.getStaffDetails().setStaff(new ArrayList<Staff>());
				}
				if(!Utils.isEmpty(homeInsuranceVO.getStaffDetails())) {
					for (StaffDetailsVO staffDetailsVO : homeInsuranceVO.getStaffDetails()) {
						Staff staff = new Staff();
						staff.setStaffDob(staffDetailsVO.getEmpDob());
						staff.setStaffName(staffDetailsVO.getEmpName());
						for(TTrnGaccPersonQuo trnGaccPersonVOHolder : resultList ){
							if(trnGaccPersonVOHolder.getGprEName().equals(staffDetailsVO.getEmpName())) {
								staff.setStaffId(trnGaccPersonVOHolder.getId().getGprId().intValue());
								System.out.println("GPR_ID : "+trnGaccPersonVOHolder.getId().getGprId());
							}
						}
						opList.getStaffDetails().getStaff().add(staff);
					}
				}
			}
		}
		
	private void mapBuildingDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getBuildingDetails())) {
			updateHomeQuoteResponse.setBuildingDetails(new BuildingDetails());
		}
		
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())){
			updateHomeQuoteResponse.getBuildingDetails().setOwnershipStatus(homeInsuranceVO.getBuildingDetails().getOwnershipStatus().intValue());
			if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getEmirates())){
				updateHomeQuoteResponse.getBuildingDetails().setEmirate(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setEmirate(SvcUtils.getLookUpDescription( "CITY", "ALL", "ALL", Integer.valueOf(homeInsuranceVO.getBuildingDetails().getEmirates() ) ));
			}
			/*if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getArea())){
				updateHomeQuoteResponse.getBuildingDetails().setArea(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setArea(Integer.parseInt(homeInsuranceVO.getBuildingDetails().getArea()));
			}*/
			if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getArea())){
				updateHomeQuoteResponse.getBuildingDetails().setArea(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setArea(homeInsuranceVO.getBuildingDetails().getOtherDetails());
			}
			if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTypeOfProperty())){
				updateHomeQuoteResponse.getBuildingDetails().setPropertyType(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setPropertyType(homeInsuranceVO.getBuildingDetails().getTypeOfProperty().intValue());
			}
			if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getBuildingname())){
				updateHomeQuoteResponse.getBuildingDetails().setBuildingName(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setBuildingName(homeInsuranceVO.getBuildingDetails().getBuildingname());
			}
			if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getFlatVillaNo())){
				updateHomeQuoteResponse.getBuildingDetails().setFlatVillaNo(null);
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setFlatVillaNo(homeInsuranceVO.getBuildingDetails().getFlatVillaNo());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
				updateHomeQuoteResponse.getBuildingDetails().setMortgageeCode(Integer.parseInt(homeInsuranceVO.getBuildingDetails().getMortgageeName()));
			}else{
				updateHomeQuoteResponse.getBuildingDetails().setMortgageeCode(null);
			}
			
			updateHomeQuoteResponse.getBuildingDetails().setPoBox(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox()) ? null : homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox());
			updateHomeQuoteResponse.getBuildingDetails().setTotalFloors(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTotalNoFloors()) ? null : homeInsuranceVO.getBuildingDetails().getTotalNoFloors());
			updateHomeQuoteResponse.getBuildingDetails().setTotalRooms(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTotalNoRooms()) ? null : homeInsuranceVO.getBuildingDetails().getTotalNoRooms());
			updateHomeQuoteResponse.getBuildingDetails().setLatitude(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getLatitude()) ? null : homeInsuranceVO.getBuildingDetails().getLatitude());
			updateHomeQuoteResponse.getBuildingDetails().setLongitude(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getLongitude()) ? null : homeInsuranceVO.getBuildingDetails().getLongitude());
			updateHomeQuoteResponse.getBuildingDetails().setInfoMapStatus(homeInsuranceVO.getBuildingDetails().getInforMapStatus());
			//CTS - 26.08.2020 - #SAT42424 - Retrieve quote response when only create quote is completed -- Starts
			String[] address = null;
			int length = 0;
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getAddress())){
			 address = homeInsuranceVO.getBuildingDetails().getAddress().split(",");
			 if(!Utils.isEmpty(address)){
				 length = address.length;
			 }
			}
			//CTS - 22.09.2020 - Home Digital UAT defect - unable to retrieve quote no 20184301 - Starts 
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getFlatVillaNo()) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getAddress())) {
				updateHomeQuoteResponse.getBuildingDetails().setStreet((address[2] == null ) ? null : address[2]);
				updateHomeQuoteResponse.getBuildingDetails().setArea((address[3] == null   ) ? null : address[3]);
				updateHomeQuoteResponse.getBuildingDetails().setZone((address[4] == null )? null : address[4]);
				if(address[length-1] != null)
				address[length-1] = address[length-1].replace("GRL-","");
				updateHomeQuoteResponse.getBuildingDetails().setGrl((address[length-1] == null ) ? null : address[length-1]);
			}else if(address != null){
				updateHomeQuoteResponse.getBuildingDetails().setStreet((address[1] == null ) ? null : address[1]);
				updateHomeQuoteResponse.getBuildingDetails().setArea((address[2] == null ) ? null : address[2]);
				updateHomeQuoteResponse.getBuildingDetails().setZone((address[3] == null ) ? null : address[3]);
				if(address[length-1] != null)
				address[length-1] = address[length-1].replace("GRL-","");
				updateHomeQuoteResponse.getBuildingDetails().setGrl((address[length-1] == null ) ? null : address[length-1]);
				//CTS - 22.09.2020 - Home Digital UAT defect - unable to retrieve quote no 20184301 - Ends
			}else if(address == null){
				updateHomeQuoteResponse.getBuildingDetails().setArea( null);
				updateHomeQuoteResponse.getBuildingDetails().setZone(null);
				updateHomeQuoteResponse.getBuildingDetails().setStreet(null);
				updateHomeQuoteResponse.getBuildingDetails().setGrl(null);

			}
			//CTS - 26.08.2020 - #SAT42424 - Retrieve quote response when only create quote is completed -- Ends
		}
		
	
		
	}

	private void mapMandatoryOptionalCovers(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getMandatoryCovers())) {
			updateHomeQuoteResponse.setMandatoryCovers(new ArrayList<MandatoryCovers>());
		}
		
		MandatoryCovers mandatoryBuildingCovers = new MandatoryCovers();
		
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured())) {
			mandatoryBuildingCovers.setCoverDesc("Building");
			mandatoryBuildingCovers.setPremium(new BigDecimal(homeInsuranceVO.getBuildingDetails().getPremiumAmt()));
			mandatoryBuildingCovers.setCoverIncluded(true);
			mandatoryBuildingCovers.setCoverageLimit(new BigDecimal(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured()));
			//mandatoryBuildingCovers.setCoverMappingCode(""+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovCode()+"-"+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovTypeCode()+"-"+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovSubTypeCode()+"");
			
			if(Utils.isEmpty(mandatoryBuildingCovers.getRiskDetails())){
				mandatoryBuildingCovers.setRiskDetails(new RiskDetails());
			}
			mandatoryBuildingCovers.getRiskDetails().setRskId(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRskId().intValue());
			mandatoryBuildingCovers.getRiskDetails().setBasicRskId(homeInsuranceVO.getBuildingDetails().getRiskCodes().getBasicRskId().intValue());
			if(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat() == null) {	homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCat(0); }
			//mandatoryBuildingCovers.getRiskDetails().setBasicRskCode(homeInsuranceVO.getBuildingDetails().getRiskCodes().getBasicRskCode());
			//mandatoryBuildingCovers.getRiskDetails().setRiskCode(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCode());
			//mandatoryBuildingCovers.getRiskDetails().setRiskType(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskType());
			//mandatoryBuildingCovers.getRiskDetails().setRiskCat(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat());
			mandatoryBuildingCovers.setRiskMappingCode(""+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCode()+"-"+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskType()+"-"+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat()+"");
			updateHomeQuoteResponse.getMandatoryCovers().add(mandatoryBuildingCovers);	
		}
		
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getListedItems())) {
			updateHomeQuoteResponse.setListedItems(new ArrayList<ListedItems>());
		}
		
		if(!Utils.isEmpty(homeInsuranceVO.getCovers())) {
			
			
			for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
				
				if(coverDetailsVO.getCoverCodes().getCovCode()==SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCat()==SvcConstants.oneVal) {
					
					MandatoryCovers covers = new MandatoryCovers();
					covers.setRiskDetails(new RiskDetails());
					
					covers.setCoverDesc(coverDetailsVO.getCoverName());
					covers.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
					covers.setCoverIncluded(true);
					covers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
//					covers.setCoverMappingCode(""+coverDetailsVO.getCoverCodes().getCovCode()+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode()+"");
					covers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					covers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					if(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat() == null) {	homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCat(0); }
//					covers.getRiskDetails().setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
//					covers.getRiskDetails().setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
//					covers.getRiskDetails().setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
//					covers.getRiskDetails().setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
					covers.setRiskMappingCode(""+coverDetailsVO.getRiskCodes().getRiskCode()+"-"+coverDetailsVO.getRiskCodes().getRiskType()+"-"+coverDetailsVO.getRiskCodes().getRiskCat()+"");
					updateHomeQuoteResponse.getMandatoryCovers().add(covers);
				}
				else if(coverDetailsVO.getCoverCodes().getCovCode()==SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCat()==2) {
					ListedItems listedCovers = new ListedItems();
					listedCovers.setRiskDetails(new RiskDetails());					
					listedCovers.setCoverDesc(coverDetailsVO.getCoverName());
//					listedCovers.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
					listedCovers.setCoverIncluded(true);
					if(!Utils.isEmpty(coverDetailsVO.getSumInsured().getSumInsured())) {
						listedCovers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
					}
//					listedCovers.setCoverMappingCode(""+coverDetailsVO.getCoverCodes().getCovCode()+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode()+"");
					listedCovers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					listedCovers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					listedCovers.setRiskMappingCode(""+coverDetailsVO.getRiskCodes().getRiskCode()+"-"+coverDetailsVO.getRiskCodes().getRiskType()+"-"+coverDetailsVO.getRiskCodes().getRiskCat()+"");
					updateHomeQuoteResponse.getListedItems().add(listedCovers);
				}
			}
		}
	}

	private void mapOptionalCoversDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse,
			HomeInsuranceVO homeInsuranceVO) throws ParseException {
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getOptionalCovers())) {
			updateHomeQuoteResponse.setOptionalCovers(new ArrayList<OptionalCovers>());
		}
		int ownershipStatusCheck = homeInsuranceVO.getBuildingDetails().getOwnershipStatus().intValue();

		List<TMasPolicyRating> coverList = null;
		List<OptionalCovers> optionalCoversList = null;
		WebServiceUtils webServiceUtils = new WebServiceUtils();
		coverList = DAOUtils.getHomeOptionalCovers(homeInsuranceVO.getCommonVO(),homeInsuranceVO.getClassCode(),homeInsuranceVO.getPolicyType(),null,homeInsuranceVO.getScheme().getTariffCode());
		optionalCoversList = webServiceUtils.retrieveOptionalCoversFromRating(coverList,optionalCoversList,homeInsuranceVO);
		//CTS - 14.09.2020 - TFS#43968 - Optional Covers are displayed when only building cover is selected - Starts
		for(CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
		if(coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCode()==2 && coverDetailsVO.getRiskCodes().getRiskType()==SvcConstants.CONTENT_RSK_TYPE_CODE) {
		updateHomeQuoteResponse.setOptionalCovers(optionalCoversList);
		}
		}
		//CTS - 14.09.2020 - TFS#43968 - Optional Covers are displayed when only building cover is selected - Ends
		if(!Utils.isEmpty(homeInsuranceVO.getCovers())) {
			for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
				if(coverDetailsVO.getCoverCodes().getCovCode()!=SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCat()==SvcConstants.oneVal) {
					System.out.println("CoverCode is 2,3,4 and RiskCat is 1");
					if(ownershipStatusCheck == 1 || ownershipStatusCheck == 2) {
						if(coverDetailsVO.getCoverCodes().getCovCode()==2) {
							OptionalCovers opList = updateHomeQuoteResponse.getOptionalCovers().get(0);
							opList.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
							//if(coverDetailsVO.getIsCovered().equals(AppConstants.STATUS_ON)) {
								opList.setCoverIncluded(true);
							//}
							/*if(!Utils.isEmpty(coverDetailsVO.getSumInsured().getSumInsured())) {
								opList.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
							}*/
							opList.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
							opList.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
						}else if (coverDetailsVO.getCoverCodes().getCovCode()==3) {
							OptionalCovers opList = updateHomeQuoteResponse.getOptionalCovers().get(1);
							opList.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
							//if(coverDetailsVO.getIsCovered().equals(AppConstants.STATUS_ON)) {
								opList.setCoverIncluded(true);
							//}
							if(!Utils.isEmpty(coverDetailsVO.getSumInsured().getSumInsured())) {
								opList.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
							}
							opList.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
							opList.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
							mapStaffDetails(homeInsuranceVO, opList);
						}
					}
					if(ownershipStatusCheck == 2) {
						if (coverDetailsVO.getCoverCodes().getCovCode()==4) {
							OptionalCovers opList = updateHomeQuoteResponse.getOptionalCovers().get(2);
							List<TLLimit> limits= opList.getTllLimit();
							for (TLLimit tlLimit : limits) {
								if(tlLimit.getCoverageLimit().equals(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()))) {
									//if(coverDetailsVO.getIsCovered().equals(AppConstants.STATUS_ON)) {
										tlLimit.setSelected(true);
										opList.setCoverIncluded(true);
									//}
						//CTS - 04.09.2020 - TFS#52590 - Additional Tenants Liability Limit set selected flag - Starts
								} else {
									tlLimit.setSelected(false);
							}
						//CTS - 04.09.2020 - TFS#52590 - Additional Tenants Liability Limit set selected flag - Ends
							}
							opList.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
							opList.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
						}
					}
				}
			}
		}
	}

	private void mapTransactionDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse,
			HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getTransactionDetails())) {
			updateHomeQuoteResponse.setTransactionDetails(new TransactionDetails());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getScheme())) {
			updateHomeQuoteResponse.getTransactionDetails().setEffectiveDate(homeInsuranceVO.getScheme().getEffDate());
			updateHomeQuoteResponse.getTransactionDetails().setExpiryDate(homeInsuranceVO.getScheme().getExpiryDate());
			//updateHomeQuoteResponse.getTransactionDetails().setPolicyTypeCode(homeInsuranceVO.getScheme().getPolicyCode());
			updateHomeQuoteResponse.getTransactionDetails().setSchemeCode(homeInsuranceVO.getScheme().getSchemeCode());
			updateHomeQuoteResponse.getTransactionDetails().setTariffCode(homeInsuranceVO.getScheme().getTariffCode());
			//updateHomeQuoteResponse.getTransactionDetails().setPolicyTerm(AppUtils.getDateDifference(homeInsuranceVO.getScheme().getExpiryDate(), homeInsuranceVO.getScheme().getEffDate()).intValue());
		}
		/*if(!Utils.isEmpty(homeInsuranceVO.getClassCode())) {
			updateHomeQuoteResponse.getTransactionDetails().setClassCode(homeInsuranceVO.getClassCode());
		}*/
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel())) {
			updateHomeQuoteResponse.getTransactionDetails().setDistChannel(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel());
		}
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent())) {
			updateHomeQuoteResponse.getTransactionDetails().setDirectSubAgent(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().intValue());
		}*/
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness())) {
			updateHomeQuoteResponse.getTransactionDetails().setBusinessSource(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
		}*/
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode())) {
			updateHomeQuoteResponse.getTransactionDetails().setPromocode(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode());
		}
		
		updateHomeQuoteResponse.getTransactionDetails().setPartnerTrnReferenceNumber(updateHomeQuoteResponse.getTransactionDetails().getPartnerTrnReferenceNumber());
		updateHomeQuoteResponse.getTransactionDetails().setFinalUpdate(updateHomeQuoteResponse.getTransactionDetails().getFinalUpdate());
		
	}

	private void mapCustomerDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(Utils.isEmpty(updateHomeQuoteResponse.getCustomerDetails())) {
			updateHomeQuoteResponse.setCustomerDetails(new CustomerDetails());
		}
		
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())) {
			
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId().toString())) {
				updateHomeQuoteResponse.getCustomerDetails().setEmailId(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId().toString());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo().toString())) {
				updateHomeQuoteResponse.getCustomerDetails().setMobileNo(homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo().toString());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getInsuredCode())) {
				updateHomeQuoteResponse.getCustomerDetails().setInsuredId(homeInsuranceVO.getGeneralInfo().getInsured().getInsuredCode());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getFirstName())) {
				updateHomeQuoteResponse.getCustomerDetails().setFirstName(homeInsuranceVO.getGeneralInfo().getInsured().getFirstName());
			}else{
				updateHomeQuoteResponse.getCustomerDetails().setFirstName(null);
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getLastName())) {
				updateHomeQuoteResponse.getCustomerDetails().setLastName(homeInsuranceVO.getGeneralInfo().getInsured().getLastName());
			}else{
				updateHomeQuoteResponse.getCustomerDetails().setLastName(null);
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox())) {
				updateHomeQuoteResponse.getCustomerDetails().setPoBox(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox());
			}else{
				updateHomeQuoteResponse.getCustomerDetails().setPoBox(null);
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates())) {
				updateHomeQuoteResponse.getCustomerDetails().setCity(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates());
				updateHomeQuoteResponse.getCustomerDetails().setNationality(homeInsuranceVO.getGeneralInfo().getInsured().getNationality());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getCountry())) {
				updateHomeQuoteResponse.getCustomerDetails().setNationalID(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getCountry().toString()); 
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getVatRegNo())) {
				updateHomeQuoteResponse.getCustomerDetails().setVatRegNo(homeInsuranceVO.getGeneralInfo().getInsured().getVatRegNo());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType())) {
				updateHomeQuoteResponse.getCustomerDetails().setRewardProgrammeType(homeInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType());
			}
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo())) {
				updateHomeQuoteResponse.getCustomerDetails().setRewardCardNumber(homeInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo());
			}
			
			
		}
		
	}

	private void mapExtraDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		if (Utils.isEmpty(updateHomeQuoteResponse.getExtras())) {
			updateHomeQuoteResponse.setExtras(new Extras());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getEndDate())) {
			updateHomeQuoteResponse.getExtras().setExpiry("This quote is valid until "+homeInsuranceVO.getEndDate()+ " Subject to no known or reported claims.");
		}else {
			
			if(!Utils.isEmpty(homeInsuranceVO.getScheme())){
		       	Date effDate;
		       	effDate = homeInsuranceVO.getScheme().getEffDate();
		       	if(!Utils.isEmpty( effDate )){
					Calendar effCalendar = Calendar.getInstance();
					effCalendar.setTime( effDate );
					
					//Quote should to be valid exactly for 30 days from the date of Issue.
					effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 1 );
					effCalendar.add( Calendar.MONTH,0);
					updateHomeQuoteResponse.getExtras().setExpiry("This quote is valid until "+effCalendar.getTime()+ " Subject to no known or reported claims.");
			        ///createHomeQuoteResponse.getExtras().setExpiry("This quote is valid until "+validDate+". Subject to no known or reported claims.");
		       	}
		    }
		}
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCodeDesc())) {
			updateHomeQuoteResponse.getExtras()
			.setPromotional_Message(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCodeDesc());
		}
		
	}

	private void mapPartnerDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		if(Utils.isEmpty(updateHomeQuoteResponse.getPartnerDetails())) {
			updateHomeQuoteResponse.setPartnerDetails(new PartnerDetails());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getCommission())) {
			updateHomeQuoteResponse.getPartnerDetails().setCommissionPercentage(new BigDecimal(homeInsuranceVO.getCommission()));
			updateHomeQuoteResponse.getPartnerDetails().setCommissionAmount(new BigDecimal(homeInsuranceVO.getCommission() * homeInsuranceVO.getPremiumVO().getPremiumAmt() /100));
		}
		
	}

	private void mapFeesAndTaxes(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		FeesAndTaxes feesAndTaxes = new FeesAndTaxes();
		
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc())) {
			feesAndTaxes.setLoadingOrDiscountPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc()));
		}
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt())) {
			feesAndTaxes.setLoadingOrDiscountAmount(homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt());
		}
		
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPromoDiscPerc())) {
			feesAndTaxes.setPromoCodeDiscountPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getPromoDiscPerc()));
			feesAndTaxes.setPromoCodeDiscountAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()).add(homeInsuranceVO.getPremiumVO().getMinPremiumApplied())
					.multiply(new BigDecimal(homeInsuranceVO.getPremiumVO().getPromoDiscPerc())).divide(new BigDecimal(100)));
		}
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getGovtTax())) {
			feesAndTaxes.setGovtTaxPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getGovtTax()));
			
		}*/
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getVatTaxPerc())){
			feesAndTaxes.setVatRatePercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getVatTaxPerc()));
			//set scale for SAT issues
			feesAndTaxes.setVatAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getVatTax()).setScale(2,RoundingMode.HALF_UP));
			//set scale for SAT issues
		
		}
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPolicyFees())) {
			feesAndTaxes.setPolicyFees(new BigDecimal(homeInsuranceVO.getPremiumVO().getPolicyFees()));
		}*/
		updateHomeQuoteResponse.setFeesAndTaxes(feesAndTaxes);
	}

	private void mapQuoteDetails(UpdateHomeQuoteResponse updateHomeQuoteResponse, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getQuoteNo())) {
			updateHomeQuoteResponse.setQuotationNo(homeInsuranceVO.getCommonVO().getQuoteNo());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getScheme().getEffDate())) {
			updateHomeQuoteResponse.setPolicyEffectiveDate(homeInsuranceVO.getScheme().getEffDate());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getScheme().getExpiryDate())) {
			updateHomeQuoteResponse.setPolicyExpiryDate(homeInsuranceVO.getScheme().getExpiryDate());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getInsuredCode())) {
			updateHomeQuoteResponse.setInsuredId(homeInsuranceVO.getGeneralInfo().getInsured().getInsuredCode());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyId())) {
			updateHomeQuoteResponse.setPolicyId(homeInsuranceVO.getCommonVO().getPolicyId());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getEndtId())) {
			updateHomeQuoteResponse.setEndtId(homeInsuranceVO.getCommonVO().getEndtId());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getEndtNo())) {
			updateHomeQuoteResponse.setEndtNo(homeInsuranceVO.getCommonVO().getEndtNo().intValue());
		}
		
		if(!Utils.isEmpty(homeInsuranceVO.getScheme())){
	       	Date effDate;
	       	effDate = homeInsuranceVO.getScheme().getEffDate();
	       	if(!Utils.isEmpty( effDate )){
				Calendar effCalendar = Calendar.getInstance();
				effCalendar.setTime( effDate );
				
				//Quote should to be valid exactly for 30 days from the date of Issue.
				effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 1 );
				effCalendar.add( Calendar.MONTH,0);
				updateHomeQuoteResponse.setQuoteValidTill(effCalendar.getTime());
		        ///createHomeQuoteResponse.getExtras().setExpiry("This quote is valid until "+validDate+". Subject to no known or reported claims.");
	       	}
	    }
			//updateHomeQuoteResponse.setQuoteValidTill(homeInsuranceVO.getScheme().getExpiryDate());
		
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getStatus())) {
			updateHomeQuoteResponse.setQuoteStatus(homeInsuranceVO.getCommonVO().getStatus());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPremiumAmtActual())) {
			
			if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getMinPremiumApplied())) {
				updateHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()).add(homeInsuranceVO.getPremiumVO().getMinPremiumApplied()));
			}
			else
				updateHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()));
			//CTS - 08.09.2020 - TFS#42732 - Premium mismatch in B2C while loading during B2B referral approval-- Starts
			if(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() > 0 && !Utils.isEmpty(homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt())){
				updateHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()).add((homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt())));
			}
			//CTS - 08.09.2020 - TFS#42732 - Premium mismatch in B2C while loading during B2B referral approval-- Ends
		}
		//set scale for SAT issues
		updateHomeQuoteResponse.setPremiumPayable(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmt()).setScale(2,RoundingMode.HALF_UP));

		//set scale for SAT issues
		if(!Utils.isEmpty(Currency.getUnitName())) {
			updateHomeQuoteResponse.setCurrencyType(Currency.getUnitName());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getScheme().getTariffName())) {
			updateHomeQuoteResponse.setProductDesc(homeInsuranceVO.getScheme().getTariffName());
		}
		
	}

}
