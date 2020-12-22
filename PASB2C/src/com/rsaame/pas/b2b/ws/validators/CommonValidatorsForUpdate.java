package com.rsaame.pas.b2b.ws.validators;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.SBSWsAppConstants;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.request.FirePreventiveMeasure;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail_;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

public class CommonValidatorsForUpdate {
	public List<SBSWSValidators> validate(Object object, List<SBSWSValidators> sbsWSValidatorsList, PolicyVO policyVO) {
		CreateSBSQuoteRequest createSBSQuoteRequest = (CreateSBSQuoteRequest) object;
		boolean checkAnual=false;
		if (createSBSQuoteRequest != null && !Utils.isEmpty(createSBSQuoteRequest)) {

			// checking PAR,PL and WC is present
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())&& !Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder())) {
			if (((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())
					||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())||createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0))
							&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())|| new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue()<=0)
					&& (((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount()<=0)))) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("PAR_PL_WC",
						"SBSWS_ERR_019", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			}
			// Checking for PAR and BI is present
		
			/*if (!(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()))
					&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()))) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Business Interruption", "SBSWS_ERR_017", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);

			} else if (!(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())
					|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())
					|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())
					|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())
					|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()))
					&& !(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())
							&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())
							&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()))) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())
						&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())
						&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())
						&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())
						&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())
						&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())
								&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())
								&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
				if (WSBusinessValidatorUtils.isBISelected(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()
								.toString(),
						createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().toString())) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
							.businessErrorMapping("Business Interruption", "SBSWS_ERR_017", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
			}*/
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
				if(((createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()>0)||(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()!=null&&createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()!=null&&createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()>0))&&!((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()>0)||(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0))) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
							.businessErrorMapping("Business Interruption", "SBSWS_ERR_017", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				
			
		
			

			// checking for Electronic Equipment and PAR values
			
				if(((createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()>0))&&!((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()>0)||(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0))) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
							.businessErrorMapping("Electronic Equipment", "SBSWS_ERR_020", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			// checking Machinery Breakdown is mandatory for Deterioration of
			// Stock
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()))&&(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0 )) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Machinery Breakdown", "SBSWS_ERR_021", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			
			if (!(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit()) || Utils
					.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount()))
					&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()<=0)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Deterioration of  Stock", "SBSWS_ERR_024", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else if (!(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit())
					|| Utils.isEmpty(
							createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())
							&& (!Utils.isEmpty(
									createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())&& createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0))) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())) {
				/*if (WSBusinessValidatorUtils.isDOSSelected(
						createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount().toString(),
						createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().toString())) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
							.businessErrorMapping("Deterioration of  Stock", "SBSWS_ERR_024", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}*/
				}
			}
		}

			// GI Page Schema Validation
			/* For Customer Insured Name */
			if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany())) {
					if (Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getName()))
					  {
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("name",
						com.Constant.CONST_SBSWS_ERR_001, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
			}	else	 {
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getName())) {
						if((createSBSQuoteRequest.getPolicyHolder().getCompany().getName().toString()
						.length() > SBSWsAppConstants.maxNameLength)) {
							SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("name",
									"SBSWS_ERR_049", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}
			
//			 For Customer NatureOfBusiness 1
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany())) {
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNatureOfBusiness())) {
					if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNatureOfBusiness().getCode())||(createSBSQuoteRequest.getPolicyHolder().getCompany().getNatureOfBusiness().getCode().equalsIgnoreCase("4")||(createSBSQuoteRequest.getPolicyHolder().getCompany().getNatureOfBusiness().getCode().equalsIgnoreCase("3"))))
					{
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("natureOfBusiness", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}else if (WSBusinessValidatorUtils.getBusinessTypeValidation(createSBSQuoteRequest.getPolicyHolder().getCompany().getNatureOfBusiness().getCode().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("natureOfBusiness", com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
						}
					//	 For Number of Employees and Customer Annual Turnover 
				
					if((Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()))&&(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue())||Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())))
					{
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Number of Employees/Annual Turnover", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					} 
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee())) 
						{
							if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NUMBER_OF_EMPLOYEES, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
								if(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()<=0 && (createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()!=null && createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()!=null && createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()<=0)) {
									SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NUMBER_OF_EMPLOYEES, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
								}
								if(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()<=0 && (Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()) || Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()))) {
									SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NUMBER_OF_EMPLOYEES, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
								}
									if(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee().toString().length()>SBSWsAppConstants.maxEmpLength) {
								SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NUMBER_OF_EMPLOYEES, "SBSWS_ERR_073",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
									}
							}
						 if(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()!=null && createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()!=null) {
							if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
								if((createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()<=0) && (!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()) && createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()<=0)) {
								SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
							}
								if(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()<=0 && createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()==null) {
									checkAnual=true;
									SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
								}
								if(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount().toString().length()>SBSWsAppConstants.maxAnnualLength) {
								SBSWSValidators SBSbusinessValidatorsLength  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, "SBSWS_ERR_074",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidatorsLength);
							}
						}
				
				//	 For  VAT Registration Number  
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getCompanyVATRegistrationNumber()))
					{
						if(createSBSQuoteRequest.getPolicyHolder().getCompany().getCompanyVATRegistrationNumber().toString().length()>SBSWsAppConstants.maxVatLength) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("companyVATRegistrationNumber", "SBSWS_ERR_028",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
					
				}
				}
			// For Customer AddressLine2
			if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact())) {
					if ((Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
							.get(0).getAddressLine2()))
					&& (Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
							.get(0).getAddressLine1())||Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
									.get(0).getAddressLine1().getValue())))
				 {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine",
						com.Constant.CONST_SBSWS_ERR_001, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
							.get(0).getAddressLine2())){
						if((createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0)
						.getAddressLine2().toString().length() > SBSWsAppConstants.maxAddressLength)) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine2",
							"SBSWS_ERR_050", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!(ValidationUtil.isAlphaNumericWithSpace(createSBSQuoteRequest.getPolicyHolder()
									.getContactMethods().getPostMailContact().get(0).getAddressLine2().toString()))){
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine2",
								"SBSWS_ERR_051", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
							}
			}
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
							.get(0).getAddressLine1())){
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact()
						.get(0).getAddressLine1().getValue())){
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0)
							.getAddressLine1().getValue().length() > SBSWsAppConstants.maxAddressLength) {
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine1",
								"SBSWS_ERR_050", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
						if(!(ValidationUtil.isAlphaNumericWithSpace(createSBSQuoteRequest.getPolicyHolder()
								.getContactMethods().getPostMailContact().get(0).getAddressLine1().getValue()))) {
							SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine1",
									"SBSWS_ERR_051", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
				}	
					}	
			
			// For Customer City 
		/*	if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getValue()))
			{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_001, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getValue())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(WSBusinessValidatorUtils.getcityValidators(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getValue())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}*/
			
			// For Customer country 
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry()))
						{
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Country", "SBSWS_ERR_071", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else {
							if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry())) {
							String checkVal="4"; //changed to UAE
								if(!(checkVal.equals(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry()))) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Country", com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
								}
						}
						}
			
			
		//	 For Customer postalCode 
			if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode()))
			{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POSTALCODE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode()))
					if(!(ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode()))){
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POSTALCODE,
						com.Constant.CONST_SBSWS_ERR_003, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode().toString().length()>SBSWsAppConstants.maxPostBoxLength) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POSTALCODE, "SBSWS_ERR_052",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
		
			// For Customer Email 
			if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getEmailContact())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getEmailContact().get(0).getUrl())) {
				if(!ValidationUtil.isValidEmail(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getEmailContact().get(0).getUrl())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("email", "SBSWS_ERR_023",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
				}
					
			}
		//	 For Customer phone 
		if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts())&& createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().size()>0){
			if ((Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts()
							.get(0).getInternationalFullNumber()))
					&& (Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods()
									.getPhoneContacts().get(1).getInternationalFullNumber()) ))
			{
				SBSWSValidators SBSbusinessValidators=WSBusinessValidatorUtils.businessErrorMapping("internationalFullNumber",com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(),com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else {
				if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber())) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Phone",com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(),com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				
				}else
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber())) {
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber().length()>SBSWsAppConstants.maxPhoneLength) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Phone", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber())) {
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("mobile", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}	else
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber().length()>SBSWsAppConstants.maxPhoneLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Mobile", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
				}
			}
		}
			}
			// For Effective date
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate()))
						{
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						//changed from else-if to if
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate())) {
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),"yyyy-MM-dd")) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, "SBSWS_ERR_002",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
								if(!WSBusinessValidatorUtils.checkEffectiveDate(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),"yyyy-MM-dd")) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, "SBSWS_ERR_045",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
						}
						// For Expiration date
					/*	if(Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getExpirationDate()))
						{
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ExpirationDate", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						//changed from else if to if
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getExpirationDate())) {
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getExpirationDate().toString(),"yyyy-MM-dd")) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ExpirationDate", "SBSWS_ERR_002",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
									if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate())) {
										if(!WSBusinessValidatorUtils.checkExpirationDate(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),createSBSQuoteRequest.getPolicySchedule().getExpirationDate().toString())) {
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ExpirationDate", "SBSWS_ERR_026",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											sbsWSValidatorsList.add(SBSbusinessValidators);
										}
									}
						
						}*/
					
						
						// For CreationDate date
					/*	if(Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getCreationDate()))
						{
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("CreationDate", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						//changed from else if to if
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getCreationDate())) {
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getCreationDate().toString(),"yyyy-MM-dd")) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("CreationDate", "SBSWS_ERR_002",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
						}
								if(!WSBusinessValidatorUtils.checkCreationDate(createSBSQuoteRequest.getPolicySchedule().getCreationDate().toString(),"yyyy-MM-dd")) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("CreationDate", "SBSWS_ERR_044",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						}*/
		}	
		//	 For Loss experience
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation())) {
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()))
					{
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Loss experience quantum", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims())) {
						if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims().toString().length()>SBSWsAppConstants.maxLossLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Loss experience quantum", "SBSWS_ERR_054",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators);//length we are not handling
						}
				}
			
			}
			
			//For businessType
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getbusinessType())){
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode())) 
				{
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSTYPE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}	else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode())) {
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSTYPE, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(WSBusinessValidatorUtils.getOccupancyTrade(createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode(),policyVO)) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSTYPE, com.Constant.CONST_SBSWS_ERR_009,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
			}
			
		//For business Activity
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity())){
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity().getCode())) 
				{
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSACTIVITY, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}	else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity().getCode())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity().getCode())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSACTIVITY, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(WSBusinessValidatorUtils.getBusinessDesc(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity().getCode())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSACTIVITY, com.Constant.CONST_SBSWS_ERR_009,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}
			
			//For FreeZone and Location
			
			if((createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority()!=null && createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()!=null)
					&& createSBSQuoteRequest.getLiabilityInformation().getFreeZone()!=null ) {
				
				//FreeZone validation
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority())) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FreeZoneAuthority", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getFreeZone()) {
							if(WSBusinessValidatorUtils.getPLFreezoonValidation(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().toString(),policyVO)) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FreeZoneAuthority", "SBSWS_ERR_014",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
								}
						}
						else {
							if(WSBusinessValidatorUtils.getLocation(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Location", "SBSWS_ERR_014",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						
					}
				}	
		/*	//For Location
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getlocation())) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getlocation().getCode())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getlocation().getCode().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Location", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(WSBusinessValidatorUtils.getLocation(createSBSQuoteRequest.getLiabilityInformation().getlocation().getCode().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Location", "SBSWS_ERR_014",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}*/
			}
			else {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FreeZoneAuthority/Location", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		
			}	
			
			//End of GI Validation
			
			//Agent Validation
			//For Email
	/*		if((!Utils.isEmpty(createSBSQuoteRequest.getAgent()))) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods())){
						if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getEmailContact())){
							if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getEmailContact().get(0).getUrl())) {
								if(!ValidationUtil.isValidEmail(createSBSQuoteRequest.getAgent().getContactMethods().getEmailContact().get(0).getUrl())) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("email", "SBSWS_ERR_023",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}	
							}
				}
				//For phone and mobile
				if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts())){
					if ((Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(0)
								.getInternationalFullNumber()))
							&& (Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(1)
										.getInternationalFullNumber() ) ))
					{
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("internationalFullNumber", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}	else	 {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(0)
							.getInternationalFullNumber())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Phone", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
					
						}	else
							if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(0)
							.getInternationalFullNumber())) {
								if(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(0)
								.getInternationalFullNumber().toString().length()>SBSWsAppConstants.maxPhoneLength) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Phone", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
						}
								
							}
						if(!Utils.isEmpty(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(1)
							.getInternationalFullNumber())) {
							
							if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(1)
								.getInternationalFullNumber())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("mobile", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}	else
							if(createSBSQuoteRequest.getAgent().getContactMethods().getPhoneContacts().get(1)
								.getInternationalFullNumber().toString().length()>SBSWsAppConstants.maxPhoneLength) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Mobile", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
				}
			}
		}*/
			//End of Agent Validation is completed
		
			//PAR Validations
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
			
				if ((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()!=null)
						|| (createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null) 
						|| (createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null)) {
				
		
					/*if((createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<=0)&&(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0)&&(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<=0)) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualRentPayable", com.Constant.CONST_SBSWS_ERR_009,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}*/
					// Annual Rent Payable
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())){
						if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<=0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualRentPayable", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators);Handled in Section mapper
						}
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualRentPayable", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
			}
		//	Property Content value 
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())) {
					if(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyContentValue", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators);Handled in Section mapper
				}
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyContentValue", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			}
			}
			
//			Property value 
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())) {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount())) {
							if(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<=0) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyValue", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//	sbsWSValidatorsList.add(SBSbusinessValidators); Handled in Section mapper
						}
							if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyValue", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
			// Sprinklers value 
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures())) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Sprinklers", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()) && createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures().size()>0 ){
				for(FirePreventiveMeasure firePreventiveMeasure:createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()) {
					if(!((firePreventiveMeasure.getCode().equals("1"))||firePreventiveMeasure.getCode().equals("3")||firePreventiveMeasure.getCode().equals("7"))){
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FirePreventiveMeasures", com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
				}
			
			}
		}
			
			
			//End of PAR 
			
			//PL Validations
			//Annual Turnover
			if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder())) {
				
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany())) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue())) {
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
			}
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())&&(createSBSQuoteRequest.getLiabilityInformation()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()!=null&& new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue() >0)){
						if(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()<=0) {
							if(!checkAnual) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
							}
				}
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
				}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())>SBSWsAppConstants.maxAnnualLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, "SBSWS_ERR_080",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}
					if((Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()) || Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())&&((createSBSQuoteRequest.getLiabilityInformation()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()!=null&& new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) >0)))){
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
	}
			// Limit Of Indemnity Amount
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit())) {	
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_PUBLICLIABILITYLIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					else {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
							if(new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) <=0) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_PUBLICLIABILITYLIMIT, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
							if(WSBusinessValidatorUtils.getLimitofIdemnity(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount().toString(),policyVO)) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_PUBLICLIABILITYLIMIT, com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
			}
		}
			//End of PL
		
			//WC Validation
			
			// For Employee type
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())||!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation()))
			{
				//	Employee  Liability  
			
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit()))
				{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Liability", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators); //revisit
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit())){
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount())) {
						if(WSBusinessValidatorUtils.getEmployeeLiability(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount().toString(),policyVO)) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Liability", com.Constant.CONST_SBSWS_ERR_O14,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
			}
			
		//	Total annual Wageroll For WorkmenAdminCompensation
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
							/*if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())>SBSWsAppConstants.maxTotalWagerollLimit) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, "SBSWS_ERR_060",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//sbsWSValidatorsList.add(SBSbusinessValidators); Not required
							}*/
					}
				
			}
		
			//Total annual Wageroll For workmenNonAdminCompensation
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else {
							if(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount().toString().length()>SBSWsAppConstants.maxTotalWagerollLimit) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TOTAL_ANNUAL_WAGEROLL, "SBSWS_ERR_060",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
			}
			
		//	Number of Employee
			
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AdminHeadCount", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())){
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AdminHeadCount", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
			}
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("NonAdminHeadCount", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount())){
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("NonAdminHeadCount", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
			}
			}
			}
			/*if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount())) || (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())) || (!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee()))){
			if((createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount()+createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())!=(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("No Employee", "SBSWS_ERR_064",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			}*/
			
		//End of WC 	
			
			//BI Validation
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation()))	{	
			//validation for Cover
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())||(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()==null ||createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()<=0)) && (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())||createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()==null ||createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()<=0 ) && (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())||createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()<=0)) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("BI", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
				
					if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())) && (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()))&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()))){
					
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("BI Cover", "SBSWS_ERR_066",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					else {
						
						//Validation for GrossProfitLimit
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())) {
							if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())) {
								if(Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount().toString())<=0){
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GROSSPROFITLIMIT, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
									} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount().toString())) {
										SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GROSSPROFITLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
										sbsWSValidatorsList.add(SBSbusinessValidators);
										}if(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount().toString().length()>SBSWsAppConstants.maxCoverLength) {
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GROSSPROFITLIMIT, com.Constant.CONST_SBSWS_ERR_069,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											sbsWSValidatorsList.add(SBSbusinessValidators);
										}	
								}
							}
						//Validation for RentAndIcowLimit
						else if(! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())){ 
								if(! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())){ 
									if((Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString())<=0)){
										SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_RENTANDICOWLIMIT, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
										sbsWSValidatorsList.add(SBSbusinessValidators);
										} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString())) {
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_RENTANDICOWLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											sbsWSValidatorsList.add(SBSbusinessValidators);
										} if(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString().length()>SBSWsAppConstants.maxCoverLength){
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_RENTANDICOWLIMIT, com.Constant.CONST_SBSWS_ERR_069,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											sbsWSValidatorsList.add(SBSbusinessValidators);
										}	
								}
						}
						//Validation for AnnualRentReceivable
						else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) { 
								if(Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString())<=0){
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString())) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString().length()>SBSWsAppConstants.maxCoverLength) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_ERR_069,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						
						//PAR and BI Sum Insured
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
							if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
								if((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount() + createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())+(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())>SBSWsAppConstants.maxSIPABBIValue) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, "SBSWS_BI_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators); //they need referral in this case
								}
							}
						}
					}
				}
				
			}
		}
	//END of BI		
			
			//MB Validation
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
			//MachineryBreakdownLimit
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0 ) {	
					if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())) ) {
						if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						else if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())) && !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_016",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
		}
			else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())) {
			
			if(!(ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount().toString()))) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())>SBSWsAppConstants.maxCoverLength){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_083",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			
			if(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_089",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			
			if(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>SBSWsAppConstants.maxMBCoverValue) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_MB_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
	}
	}	/*else {
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())){
		SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_093",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
		sbsWSValidatorsList.add(SBSbusinessValidators);
	}
	}*/
	}
			}
	//End of MB Validation
	
	//DOS Validation
	
	//StockGuaranteeLimit Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit())){
			if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())){
				if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount().toString())) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, "SBSWS_ERR_085",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				if(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount()>SBSWsAppConstants.maxMBCoverValue) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, "SBSWS_DOS_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
		}
	}
	//DOS validation end
	
	//EE Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit())) {
			if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()) && Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()))  {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}	 
		//changed from else if TO if (otherwise below block will never execute for EE
		if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()!=null) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount())) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, "SBSWS_ERR_090",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators); //Incorrect comparision .doesn't make sense to check the length. Only value should suffice
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()>SBSWsAppConstants.maxEECoverValue) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PortableEquipmentLimit", "SBSWS_ERR_092",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
			
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()<=0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PortableEquipmentLimit", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
			
						}
					}
					if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()!=null) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, "SBSWS_ERR_090",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators); //Incorrect comparision . doesn't make sense to check the length. Only value should suffice
						}
			
						if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()>SBSWsAppConstants.maxEECoverValue) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ComputerBreakdownLimit", "SBSWS_ERR_091",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
				
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()<=0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ComputerBreakdownLimit", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit()) && !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount())&&(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()))) {
						if((createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount())>SBSWsAppConstants.maxEECoverValue) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, "SBSWS_EE_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}	
				
			}
		}
	//End of EE Validation
			
	//Fidelity Guarantee Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee())){
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail()))&&  Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FidelityGuarantee EmployeesDetail", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())||(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail()))){
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())) {
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getName())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Name", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getDesignation())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Designation", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getCategory())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Category", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getCategory())) {
						if(!(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getCategory().equals("16")||createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getCategory().equals("17"))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Category", "SBSWS_ERR_022",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
						if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getGender()!=null) {
							if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getGender().equals("")|| createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getGender().equals(" ")) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Gender", "SBSWS_ERR_005",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
					}
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getSumInsured())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee SumInsured", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getSumInsured())) {
						if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getSumInsured()>SBSWsAppConstants.maxFGLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee SumInsured", "SBSWS_FG_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
			}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())) {
					if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()))&&(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()==null||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("UnnamedEmployeesDetail EmployeesCount", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingInsured()))&&(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingInsured()==null||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingInsured()))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("UnnamedEmployeesDetail Insured	", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
			}
		}
	}	
	//End of Fidelity Guarantee Validation
	
	//Travel Baggage Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage())) {
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail())) {
				for(NamedEmployeesDetail_ namedEmployeesDetail:createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail()) {
					if(Utils.isEmpty(namedEmployeesDetail.getName())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee Name", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(Utils.isEmpty(namedEmployeesDetail.getDateOfBirth())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee DOB", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}else if(!Utils.isEmpty(namedEmployeesDetail.getDateOfBirth())) {
						if(!WSBusinessValidatorUtils.checkDateFormat(namedEmployeesDetail.getDateOfBirth(), "dd/MM/yyyy")) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee DOB", "SBSWS_ERR_008",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
					if(Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}else if (!Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						if(!ValidationUtil.isNumeric(namedEmployeesDetail.getSumInsured().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);	
						}
						if(String.valueOf(namedEmployeesDetail.getSumInsured()).toString().length()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);	
						}
						if(String.valueOf(namedEmployeesDetail.getSumInsured()).toString().length()>SBSWsAppConstants.maxTravelLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, "SBSWS_TRL_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);	
						}
					}
				}
			}	else if(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail().size()==0) {
				SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel NamedEmployeesDetail", "SBSWS_ERR_046",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
	}
	//END of Travel Baggage
	
	//Group Personal Accident Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident())) {
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident().getNamedEmployeesDetail())) {
			//	int sumIns=0; 
				
				if(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident().getNamedEmployeesDetail().size()<=SBSWsAppConstants.maxEmployee) {
					for(NamedEmployeesDetail employeesDetail : createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident().getNamedEmployeesDetail()) {
						if(Utils.isEmpty(employeesDetail.getName())) {
							SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Name", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(Utils.isEmpty(employeesDetail.getCategory())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_CATEGORY, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getCategory())) {
							if(!ValidationUtil.isNumeric(employeesDetail.getCategory().toString()))
							{
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_CATEGORY, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
							if(WSBusinessValidatorUtils.getGPAEmployeeType(employeesDetail.getCategory(),policyVO)) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_CATEGORY, com.Constant.CONST_SBSWS_ERR_009,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						if(Utils.isEmpty(employeesDetail.getDateOfBirth())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_DOB, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getDateOfBirth())) {
							if(!WSBusinessValidatorUtils.checkDateFormat(employeesDetail.getDateOfBirth(),"dd/MM/yyyy")) {
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_DOB, "SBSWS_ERR_008",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}else if(!WSBusinessValidatorUtils.checkAge(employeesDetail.getDateOfBirth()))
							{
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_DOB, "SBSWS_ERR_006",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						if(Utils.isEmpty(employeesDetail.getGender())) {
							SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Gender", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getGender())) {
							if(!(employeesDetail.getGender().equalsIgnoreCase("Male")|| employeesDetail.getGender().equalsIgnoreCase("Female"))) {
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Gender", "SBSWS_ERR_005",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						if(Utils.isEmpty(employeesDetail.getSalary())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Salary", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getSalary())) {
							if(employeesDetail.getSalary()<0) {
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Salary", com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						if(Utils.isEmpty(employeesDetail.getSumInsured())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getSumInsured())) {
							if(!ValidationUtil.isNumeric(employeesDetail.getSumInsured().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
							if(String.valueOf(employeesDetail.getSumInsured()).toString().length()>SBSWsAppConstants.maxCoverLength) {
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_069,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//	sbsWSValidatorsList.add(SBSbusinessValidators); this is not part of webservice
							}
							if(employeesDetail.getSumInsured()>SBSWsAppConstants.maxGPASumInsured) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, "SBSWS_GPA_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
							}
							/*sumIns+=employeesDetail.getSumInsured();
							if(sumIns>SBSWsAppConstants.maxGPASumInsured) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, "SBSWS_ERR_007",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}*/
						}
						if(!Utils.isEmpty(employeesDetail.getSumInsured())&&!Utils.isEmpty(employeesDetail.getSalary())) {
							if(employeesDetail.getSumInsured()>employeesDetail.getSalary()*4) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, "SBSWS_GPA_002",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
				}	else {
					SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("GPA Employee", "SBSWS_ERR_015",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidatorsVal);	
				}
			}else if(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident().getNamedEmployeesDetail().size()==0) {
				SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("NamedEmployeesDetail", "SBSWS_ERR_046",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
	}
		
	//End of Group Personal Accident Validation
	
	//Money Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
		//Estimated Annual Carryings Validation
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit().getAmount())))) {
			SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MONEYINTRANSITLIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit())) {
			if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit().getAmount())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())||createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() <= 0)&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit().getAmount() <= 0)) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualCarryingEstimate/MoneyInTransitLimit", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			else 
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())) {
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				/*	if(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()<=0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}*/
					if(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()>SBSWsAppConstants.maxAnnualCarryLimit) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,"SBSWS_MNY_10",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
		}
		//Cash In Transit moneyInTransitLimit and Estimated Annual Carryings Validation
		
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) 
				&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0) &&
					(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) 
					&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) && createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() > 0)
					) {
			if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit().toString())){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("moneyInTransitLimit",com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			/*if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInTransitLimit().getAmount()<=0){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("moneyInTransitLimit",com.Constant.CONST_SBSWS_ERR_004,SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}*/
			
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() >SBSWsAppConstants.maxSingleTrLimit){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("moneyInTransitLimit","SBSWS_MNY_07",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() >createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MONEYINTRANSITLIMIT,"SBSWS_MNY_08",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() >createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*12.50/100){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MONEYINTRANSITLIMIT,"SBSWS_MNY_09",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
	}
		//MoneyInLockedSafe Validation and MoneyInLockedDrawer Validation for Carraying Estimate
		if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&&((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer())&& createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer() > 0)||(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe())&&createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe()>0))){
			SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,"SBSWS_ERR_068",SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		//MoneyInLockedSafe Validation
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0)&&(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe())&&createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe()>0)){
			if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe()>SBSWsAppConstants.maxSingleTrLimit) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedSafe","SBSWS_MNY_05",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
				if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*12.50/100){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedSafe","SBSWS_MNY_06",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
		//MoneyInLockedDrawer Validation
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0)&&(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer())&&createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer()>0)){
			if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer()>SBSWsAppConstants.maxDrawerLimit) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedDrawer","SBSWS_MNY_04",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*0.50/100){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedDrawer","SBSWS_MNY_03",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
		}
		//MoneyInEmployeePremises Validation
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0)&&(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises())&&createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises()>0)){
			if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises()>SBSWsAppConstants.maxDrawerLimit) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInEmployeePremises","SBSWS_MNY_02",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
				if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*0.50/100){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInEmployeePremises","SBSWS_MNY_01",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}	
		}
	//END of money validation
		} else {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("Request",
					"SBSWS_ERR_018", "Error", com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		return sbsWSValidatorsList;

	}
}
