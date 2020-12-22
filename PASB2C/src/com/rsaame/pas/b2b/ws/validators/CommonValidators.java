package com.rsaame.pas.b2b.ws.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.SBSWsAppConstants;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.request.FirePreventiveMeasure;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail_;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail__;
import com.rsaame.pas.b2b.ws.vo.request.UnnamedEmployeesDetail;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.controllers.SBSQuotationController;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class CommonValidators {
	public List<SBSWSValidators> validate(Object object, List<SBSWSValidators> sbsWSValidatorsList, PolicyVO policyVO) throws ParseException {
		CreateSBSQuoteRequest createSBSQuoteRequest = (CreateSBSQuoteRequest) object;
		boolean checkAnual=false;
		if (createSBSQuoteRequest != null && !Utils.isEmpty(createSBSQuoteRequest)) {

			// checking PAR,PL and WC is present
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())&& !Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder())) {
			if (((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())
					||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())||createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0)
					&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStock())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()<=0))
							&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit())|| Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())|| new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue()<=0)
					&& (((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount()<=0)&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount()<=0)))) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("PAR_PL_WC",
						"SBSWS_ERR_019", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			}
			// Checking for PAR and BI is present
		
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
			// checking Machinery Breakdown is mandatory for PAR or property content value
			/*if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())&&(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0 )) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Machinery Breakdown", "SBSWS_ERR_021", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators); 
			}*/
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
			if((createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()==null|| createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0)&&(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0 )) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Machinery Breakdown", "SBSWS_ERR_016", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators); 
			}
			}
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&& Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0))&&(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0 )) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Machinery Breakdown", "SBSWS_ERR_021", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators); 
			}
			// checking Machinery Breakdown is mandatory for Deterioration of Stock
						
			if ((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit()) &&( !Utils
					.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())&& createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount()>0))
					&& (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())||(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())||createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()<=0))) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
						.businessErrorMapping("Deterioration of  Stock", "SBSWS_ERR_024", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} 
			
			//FG with PAR and PL
			if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee()!=null) {
				if(((createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail()!=null && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()>0)||(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail()!=null))&&(((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()) || createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<=0)&&(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()==null || createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<=0)&&(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()==null||createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()==null || createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0))&&((createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit()==null ||createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()==null || new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue() <=0)&&(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()!=null&&createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()!=null&&createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()>0)))) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
							.businessErrorMapping("Fidelity Guarantee", "SBSWS_ERR_075", SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
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
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("natureOfBusiness", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
							/*if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NUMBER_OF_EMPLOYEES, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//sbsWSValidatorsList.add(SBSbusinessValidators); not handled 
							}*/
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
								//sbsWSValidatorsList.add(SBSbusinessValidators); not handled 
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
					/*if(!(ValidationUtil.isAlphaNumericWithSpace(createSBSQuoteRequest.getPolicyHolder()
									.getContactMethods().getPostMailContact().get(0).getAddressLine2().toString()))){
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine2",
								"SBSWS_ERR_051", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
							}*/ // code commented as address can contain special characters 
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
						/*if(!(ValidationUtil.isAlphaNumericWithSpace(createSBSQuoteRequest.getPolicyHolder()
								.getContactMethods().getPostMailContact().get(0).getAddressLine1().getValue()))) {
							SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("addressLine1",
									"SBSWS_ERR_051", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}*/ //code commented as address can contain special characters 
				}	
					}	
			
			// For Customer City 
			if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getCode()))
			{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_001, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getCode())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}else if(WSBusinessValidatorUtils.getcityValidators(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getCode())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("city", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			
			// For Customer country 
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry()))
						{
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Country", com.Constant.CONST_SBSWS_ERR_001, SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else {
							if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry())) {
							String checkVal="4"; //changed to UAE
								if(!(checkVal.equals(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry()))) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Country", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
								}
						}
						}
			
		//	 For Customer postalCode 
			/*if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode()))
			{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("postalCode", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}*/
			
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode())) {
					if(!ValidationUtil.isValidAlphaNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode())){
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("postalCode",
						"SBSWS_ERR_022", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode().toString().length()>SBSWsAppConstants.maxPostBoxLength) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("postalCode", "SBSWS_ERR_052",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
					}
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
				if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber()!=null || createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber()!=null) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber())) {
				if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber())) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Mobile",com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(),com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber())) {
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber().length()>SBSWsAppConstants.maxPhoneLength) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Mobile", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber())) {
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Landline", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}	else
					if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber().length()>SBSWsAppConstants.maxPhoneLength) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Landline", "SBSWS_ERR_053",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
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
							boolean isDate=false;
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									isDate=true;
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, com.Constant.CONST_SBSWS_ERR_002,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
								if(!isDate) {
									isDate=false;
								if(!WSBusinessValidatorUtils.checkEffectiveDate(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, "SBSWS_ERR_045",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
								if(!WSBusinessValidatorUtils.checkEffectiveDateValid(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, "SBSWS_ERR_090",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
							}
						}
						// For Expiration date
						if(SBSQuotationController.ischeckUpdate) {
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getExpirationDate()))
						{
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EXPIRATIONDATE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						//changed from else if to if
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getExpirationDate())) {
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getExpirationDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EXPIRATIONDATE, com.Constant.CONST_SBSWS_ERR_002,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
									if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate())) {
										//validation changed for no of days M1043209
										Date effDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD).parse(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate());
										Date expiryDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD).parse(createSBSQuoteRequest.getPolicySchedule().getExpirationDate());
										int year = effDate.getYear();
										int year2 = expiryDate.getYear();
										boolean leapYear = false;
										boolean leapYear2 = false;
										boolean containExtraDay = false;
										if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
											leapYear = true;
										}
										if((year2 % 400 == 0) || ((year2 % 4 == 0) && (year2 % 100 != 0))) {
											leapYear2 = true;
										}
										if(leapYear==true && effDate.getMonth()<2 ) {
											containExtraDay = true;
										}
										else if(leapYear2 == true && effDate.getMonth()>=2) {
											containExtraDay = true;
										}
										if(!((AppUtils.getDateDifference(expiryDate, effDate).intValue()==365 && containExtraDay == false) || 
												(AppUtils.getDateDifference(expiryDate, effDate).intValue()==366 && containExtraDay == true))) {
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EXPIRATIONDATE, "SBSWS_ERR_026",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											sbsWSValidatorsList.add(SBSbusinessValidators);
										}
									}
						
						}
			}
						// For CreationDate date
						if(SBSQuotationController.ischeckUpdate) {
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getCreationDate()))
						{
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_CREATIONDATE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						//changed from else if to if
						if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getCreationDate())) {
								if(!WSBusinessValidatorUtils.checkDateFormat(createSBSQuoteRequest.getPolicySchedule().getCreationDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_CREATIONDATE, com.Constant.CONST_SBSWS_ERR_002,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
						}
								if(!WSBusinessValidatorUtils.checkCreationDate(createSBSQuoteRequest.getPolicySchedule().getCreationDate().toString(),com.Constant.CONST_YYYY_MM_DD)) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_CREATIONDATE, "SBSWS_ERR_044",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						}
						}
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
							//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Loss experience quantum", "SBSWS_ERR_054",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSTYPE, com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_BUSINESSACTIVITY, com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FreeZoneAuthority", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
								}
						}
						else {
							if(WSBusinessValidatorUtils.getLocation(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Location", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Location", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
				
					// Annual Rent Payable
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())){
						if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualRentPayable", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualRentPayable", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators); validation not needed
					}
			}
		//	Property Content value 
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())) {
					if(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyContentValue", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyContentValue", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					//	sbsWSValidatorsList.add(SBSbusinessValidators); validation not needed
			}
			}
			}
			
			//Stock validation
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStock())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount())) {
					if(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()<0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Stock", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Stock", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					//	sbsWSValidatorsList.add(SBSbusinessValidators); validation not needed
			}
			}
			}
			
//			Property value 
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())) {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount())) {
							if(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()<0) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyValue", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
						}
							if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PropertyValue", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//	sbsWSValidatorsList.add(SBSbusinessValidators); validation not needed
							}
						}
					}
			// Sprinklers value 
					if((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()>0)
							|| (createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0) 
							|| (createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()>0)) {
						if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Sprinklers", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//	sbsWSValidatorsList.add(SBSbusinessValidators); handled in rule engine 
						}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()) && createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures().size()>0 ){
				for(FirePreventiveMeasure firePreventiveMeasure:createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()) {
					if(!((firePreventiveMeasure.getCode().equals("1"))||firePreventiveMeasure.getCode().equals("3")||firePreventiveMeasure.getCode().equals("7"))){
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("FirePreventiveMeasures", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators); validation is handled in Request Mapper
						//break;
					}
				}
				}
				}
			}
		}
			
			//End of PAR 
			
			//PL Validations
			//Annual Turnover
			if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder())) {
				boolean isAnnualCheck=false;
				if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany())) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue())) {
						if(Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())) {
							isAnnualCheck=true;
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
						//	sbsWSValidatorsList.add(SBSbusinessValidators); not required
				}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())>SBSWsAppConstants.maxAnnualLength) {
							//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, "SBSWS_ERR_080",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}
					if((Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()) || Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()))&&((createSBSQuoteRequest.getLiabilityInformation()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit()!=null&&createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()!=null&& new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue()>0))){
						if(!isAnnualCheck) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUAL_TURNOVER, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					}
				}
	}
			// Limit Of Indemnity Amount
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit())) {	
					if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PublicLiabilityLimit", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					else {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())&& new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) >0) {
							if(WSBusinessValidatorUtils.getLimitofIdemnity(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount().toString(),policyVO)) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PublicLiabilityLimit", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
			
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit())||createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount()==null)
				{
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Liability", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators); //revisit
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit())){
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount())) {
						if(WSBusinessValidatorUtils.getEmployeeLiability(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount().toString(),policyVO)) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Liability", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
			}
			
		//	Total annual Wageroll For WorkmenAdminCompensation
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("WorkmenAdminCompensation", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())) {
						if(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("WorkmenAdminCompensation", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						/*if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount())>SBSWsAppConstants.maxTotalWagerollLimit) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Total annual Wageroll", "SBSWS_ERR_060",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//sbsWSValidatorsList.add(SBSbusinessValidators); Not required
							}*/
					}
				
			}
		
			//Total annual Wageroll For workmenNonAdminCompensation
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("workmenNonAdminCompensation", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount())) {
						if(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("workmenNonAdminCompensation", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}/*else {
							if(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount().toString().length()>SBSWsAppConstants.maxTotalWagerollLimit) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Total annual Wageroll", "SBSWS_ERR_060",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}*/
					}
			}
			
			
			
		//	Number of Employee
			
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount())){
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount()<0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
			}
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {
				if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount())){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NONADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount())){
					if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount().toString())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NONADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount()<0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_NONADMINHEADCOUNT, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						//sbsWSValidatorsList.add(SBSbusinessValidators);
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
			if((createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()!=null&& createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()>0)||(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()!=null && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()>0)) {	
			if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()) && Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()) && Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("BI", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				//sbsWSValidatorsList.add(SBSbusinessValidators);
			}else {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
				
					if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())) && (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()))&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()))){
					
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("BI Cover", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					//	sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					else {
						
						//Validation for GrossProfitLimit
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())) {
							if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())) {
								if(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()<0){
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GrossProfitLimit", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
									} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount().toString())) {
										SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GrossProfitLimit", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
										//sbsWSValidatorsList.add(SBSbusinessValidators);
										}if(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount().toString().length()>SBSWsAppConstants.maxCoverLength) {
											//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GrossProfitLimit", "SBSWS_ERR_069",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											//sbsWSValidatorsList.add(SBSbusinessValidators);
										}	
								}
							}
						//Validation for RentAndIcowLimit
						else if(! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())){ 
								if(! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())){ 
									if(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()<0){
										SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("RentAndIcowLimit", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
										sbsWSValidatorsList.add(SBSbusinessValidators);
										} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString())) {
											SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("RentAndIcowLimit", com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
										//	sbsWSValidatorsList.add(SBSbusinessValidators);
										} if(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount().toString().length()>SBSWsAppConstants.maxCoverLength){
											//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("RentAndIcowLimit", "SBSWS_ERR_069",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
											//sbsWSValidatorsList.add(SBSbusinessValidators);
										}	
								}
						}
						//Validation for AnnualRentReceivable
						else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) { 
								if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()<0){
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators);
								} if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString())) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//	sbsWSValidatorsList.add(SBSbusinessValidators);
								}if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable().toString().length()>SBSWsAppConstants.maxCoverLength) {
								//	SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, "SBSWS_ERR_069",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//	sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
						
						//PAR and BI Sum Insured
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
							if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
								if((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount() + createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())+(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())>SBSWsAppConstants.maxSIPABBIValue) {
									SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_BI_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
									sbsWSValidatorsList.add(SBSbusinessValidators); //they need referral in this case
								}
							}
						}
					}
					
					//for property value
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())) {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount())&&createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()>0&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
							if(createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()+(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())>SBSWsAppConstants.maxSIPABBIValue) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_BI_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators); //they need referral in this case
							}
						}
					}
					//for property content
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())) {
						if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())&&createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
							if(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()+(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())>SBSWsAppConstants.maxSIPABBIValue) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_BI_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators); //they need referral in this case
							}
						}
					}
					
					//for annual rent payble
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&! Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
						if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()>0&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())) {
							if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()+(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()+createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable())>SBSWsAppConstants.maxSIPABBIValue) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALRENTRECEIVABLE, com.Constant.CONST_SBSWS_BI_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
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
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())){
				if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()) && (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())&&createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0))) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_016",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				}
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue())) {
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0 && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0) {	
					if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit())) ) {
						if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					/*	else if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())|| createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()<=0) && (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())&&createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>0)) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_016",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
		}*/
			else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())) {
			
			if(!(ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount().toString()))) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			//	sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount())>SBSWsAppConstants.maxCoverLength){
				//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_ERR_083",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				//sbsWSValidatorsList.add(SBSbusinessValidators); need clarification
			}
			
			if(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()>createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MACHINERYBREAKDOWNLIMIT, "SBSWS_MB_002",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
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
			}else if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())&& createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount()>0){
				if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount().toString())) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
				if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
					//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_STOCKGUARANTEELIMIT, "SBSWS_ERR_085",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					//sbsWSValidatorsList.add(SBSbusinessValidators); need clarification
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
							//sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
							//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, "SBSWS_ERR_090",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators); //Incorrect comparision .doesn't make sense to check the length. Only value should suffice
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()>SBSWsAppConstants.maxEECoverValue) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PortableEquipmentLimit", "SBSWS_EE_002",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
			
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("PortableEquipmentLimit", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
		}
					if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()!=null) {
						if(!ValidationUtil.isNumeric(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators);
						}
						if(ValidationUtil.countDigits(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount())>SBSWsAppConstants.maxCoverLength) {
							//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EQUIPMENTLIMIT, "SBSWS_ERR_090",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators); //Incorrect comparision . doesn't make sense to check the length. Only value should suffice
						}
			
						if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()>SBSWsAppConstants.maxEECoverValue) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ComputerBreakdownLimit", "SBSWS_EE_003",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
				
						}
						if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("ComputerBreakdownLimit", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
				}
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit())) {
					if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()) && createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()>0 &&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount())&& createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()>0 ) {
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
			Double aggregateLimit = new Double(0.0);
			boolean isEmpsize=true;
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail()))&&  Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_FIDELITYGUARANTEE_EMPLOYEESDETAIL, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} 
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())) {
					if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()<=SBSWsAppConstants.maxEmployee) {
						for(NamedEmployeesDetail__ namedEmployeesDetail:createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())	{
					
					if(Utils.isEmpty(namedEmployeesDetail.getName())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Name", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						break;
					}
					if(Utils.isEmpty(namedEmployeesDetail.getDesignation())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Employee Designation", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					
					}
					if(Utils.isEmpty(namedEmployeesDetail.getCategory())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Category", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						
					}
					if(!Utils.isEmpty(namedEmployeesDetail.getCategory())) {
						if(!(namedEmployeesDetail.getCategory().equals("16")||namedEmployeesDetail.getCategory().equals("17"))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Category", com.Constant.CONST_SBSWS_ERR_014,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
						if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().get(0).getGender()!=null) {
							if(namedEmployeesDetail.getGender().equals("")|| namedEmployeesDetail.getGender().equals(" ")) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Gender", "SBSWS_ERR_005",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
					}
					if(Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EMPLOYEE_SUMINSURED, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						break;
					}
					if(!Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						if(namedEmployeesDetail.getSumInsured()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EMPLOYEE_SUMINSURED, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
							break;
						}
						if(namedEmployeesDetail.getSumInsured()>SBSWsAppConstants.maxFGLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EMPLOYEE_SUMINSURED, com.Constant.CONST_SBSWS_FG_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
							break;
						}
					}
				
					if(namedEmployeesDetail.getName()!=null && namedEmployeesDetail.getCategory()!=null && namedEmployeesDetail.getDesignation()!=null && namedEmployeesDetail.getDateOfBirth()!=null && namedEmployeesDetail.getGender()!=null && namedEmployeesDetail.getSumInsured()!=null && namedEmployeesDetail.getSumInsured()>0) {
						aggregateLimit += namedEmployeesDetail.getSumInsured().doubleValue();
					}
					}
					
				}else {
					SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("FidelityGuarantee NamedEmployeesDetail", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
				}
			}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())) {
					UnnamedEmployeesDetail unnamedEmployeesDetail = createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail();
					if((Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingEmployeesCount()))&&(Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("UnnamedEmployeesDetail EmployeesCount", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if((Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingInsured()))&&(Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingInsured()))) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("UnnamedEmployeesDetail Insured	", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					
					if(!Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getCashHandelingEmployeesCount()>0) {
						aggregateLimit+=(unnamedEmployeesDetail.getCashHandelingInsured().doubleValue() * unnamedEmployeesDetail.getCashHandelingEmployeesCount());
					}
					if(!Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()>0) {
						aggregateLimit+=( unnamedEmployeesDetail.getNonCashHandelingInsured().doubleValue() * unnamedEmployeesDetail.getNonCashHandelingEmployeesCount());
					}
					if(!Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getCashHandelingEmployeesCount()>0) {
						if(unnamedEmployeesDetail.getCashHandelingEmployeesCount()>SBSWsAppConstants.maxEmployee) {
								isEmpsize=false;
						SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("CashHandelingEmployeesCount", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
							}
					}
					if(!Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()>0) {
						if(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()>SBSWsAppConstants.maxEmployee) {
								isEmpsize=false;
							SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("NonCashHandelingEmployeesCount", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
							}
					}
					
					if(!Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingInsured())) {
						if(unnamedEmployeesDetail.getNonCashHandelingInsured()>SBSWsAppConstants.maxFGLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("NonCashHandelingInsured", com.Constant.CONST_SBSWS_FG_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
					if(!Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingInsured())) {
						if(unnamedEmployeesDetail.getCashHandelingInsured()>SBSWsAppConstants.maxFGLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("CashHandelingInsured", com.Constant.CONST_SBSWS_FG_001,SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
					if(!Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()>0&&!Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getCashHandelingEmployeesCount()>0) {
						if((unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()+unnamedEmployeesDetail.getCashHandelingEmployeesCount())>SBSWsAppConstants.maxEmployee) {
						if(isEmpsize) {
							isEmpsize=false;
							SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("FidelityGuarantee UnnamedEmployeesDetail ", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
						}
						}
					}
				}
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())&&createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()>0&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()) &&  createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()>0) {
					if((createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()+createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount())>SBSWsAppConstants.maxEmployee){
						if(isEmpsize) {
							isEmpsize=false;
						SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_FIDELITYGUARANTEE_EMPLOYEESDETAIL, com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
							}
						}
				} 
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())&&createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()>0&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()) &&  createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()>0) {
					if((createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()+createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount())>SBSWsAppConstants.maxEmployee){
						if(isEmpsize) {
							isEmpsize=false;
						SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_FIDELITYGUARANTEE_EMPLOYEESDETAIL, com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
						}
						}
				}
				 if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())&&createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()>0&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())&&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()) &&  createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()>0 &&!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()) &&  createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()>0) {
					if((createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail().size()+createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()+createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount())>SBSWsAppConstants.maxEmployee){
						if(isEmpsize) {
							isEmpsize=false;
						SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_FIDELITYGUARANTEE_EMPLOYEESDETAIL, com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidatorsVal);
					}
					}
				}
				if((aggregateLimit/2)>SBSWsAppConstants.maxFGAggregateLimit) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Fidelity Guarantee", "SBSWS_FG_002",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
		}
	}	
	//End of Fidelity Guarantee Validation
	
	//Travel Baggage Validation
	if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation())){
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage())) {
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail())) {
				if(createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail().size()<=SBSWsAppConstants.maxEmployee) {
					double sumIns=0.0;
				for(NamedEmployeesDetail_ namedEmployeesDetail:createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail()) {
					if(Utils.isEmpty(namedEmployeesDetail.getName())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee Name", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
						break;
					}
					if(Utils.isEmpty(namedEmployeesDetail.getDateOfBirth())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee DOB", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					} if(!Utils.isEmpty(namedEmployeesDetail.getDateOfBirth())) {
						if(!WSBusinessValidatorUtils.checkDateFormat(namedEmployeesDetail.getDateOfBirth(), "dd/MM/yyyy")) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee DOB", "SBSWS_ERR_008",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
							break;
						}
					}
					if(Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					} if (!Utils.isEmpty(namedEmployeesDetail.getSumInsured())) {
						if(!ValidationUtil.isNumeric(namedEmployeesDetail.getSumInsured().toString())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							//sbsWSValidatorsList.add(SBSbusinessValidators);	
						}
						if(namedEmployeesDetail.getSumInsured()<0) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);	
							break;
						}
						if(namedEmployeesDetail.getSumInsured()>SBSWsAppConstants.maxTravelLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, "SBSWS_TRL_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);	
							break;
						}
						sumIns+=namedEmployeesDetail.getSumInsured();
						if(sumIns>SBSWsAppConstants.maxTravelLimit) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_TRAVEL_SUMINSURED, "SBSWS_TRL_001",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
							break;
						}
					}
				}
			}else {
				SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("Travel Employee", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidatorsVal);	
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
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_CATEGORY, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_DOB, "SBSWS_GPA_003",SBSErrorCodes.WARNING.name(), com.Constant.CONST_BUSINESS);
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
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("GPA Salary", com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
								break;
							}
						}
						if(Utils.isEmpty(employeesDetail.getSumInsured())) {
							SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}else if(!Utils.isEmpty(employeesDetail.getSumInsured())) {
							if(!ValidationUtil.isNumeric(employeesDetail.getSumInsured().toString())) {
								SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_003,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								//sbsWSValidatorsList.add(SBSbusinessValidators); not handled
							}
							if(employeesDetail.getSumInsured()<0) {
								SBSWSValidators	SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_GPA_SUMINSURED, com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators); 
								break;
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
					SBSWSValidators SBSbusinessValidatorsVal  = WSBusinessValidatorUtils.businessErrorMapping("GPA Employee", com.Constant.CONST_SBSWS_ERR_015,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
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
		if(((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()>0)&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())||Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())))) {
			SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MAXVALUEPERTRANSIT, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())) {
			if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())&& (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())&& createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()>0)) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE, com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())||createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() <= 0)&&(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())||createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() <= 0)) {
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("AnnualCarryingEstimate/MaxValuePerTransit", com.Constant.CONST_SBSWS_ERR_001,SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				//sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			else 
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())) {
					if(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()<0) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
					if(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()>SBSWsAppConstants.maxAnnualCarryLimit) {
						SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_ANNUALCARRYINGESTIMATE,"SBSWS_MNY_10",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
		}
		//Cash In Transit MaxValuePerTransit and Estimated Annual Carryings Validation
		
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) 
				&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0) &&
					(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) 
					&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) && createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() > 0)
					) {
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()<0){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MAXVALUEPERTRANSIT,com.Constant.CONST_SBSWS_ERR_006,SBSErrorCodes.ERROR.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()>SBSWsAppConstants.maxSingleTrLimit){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MAXVALUEPERTRANSIT,"SBSWS_MNY_05",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) {
				//SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MAXVALUEPERTRANSIT,"SBSWS_MNY_08",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				//sbsWSValidatorsList.add(SBSbusinessValidators); this is handled in the warning 
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*12.50/100){
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_MAXVALUEPERTRANSIT,"SBSWS_MNY_06",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
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
				SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedDrawer","SBSWS_MNY_02",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer()>createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()*0.50/100){
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("MoneyInLockedDrawer","SBSWS_MNY_01",SBSErrorCodes.WARNING.toString(), com.Constant.CONST_BUSINESS);
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
		} /*else {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("Request",
					"SBSWS_ERR_018", "Error", com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}*/
		return sbsWSValidatorsList;
	}
	/*
	 * Added for JLT API Renewal Scope #11424
	 */
	@SuppressWarnings("deprecation")
	public List<SBSWSValidators> validateRenewalQuoteEffectiveDate(CreateSBSQuoteRequest createSBSQuoteRequest, List<SBSWSValidators> sbsWSValidatorsList, PolicyVO policyVO) throws ParseException{
		
		if(!Utils.isEmpty(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate())) {
			
			List<TTrnPolicyQuo> trnPolicies = WSDAOUtils.getPolicyDetails(policyVO);
			Date policyExpiryDate = null;
			Date renewalQuoteEffectiveDate = new SimpleDateFormat("yyyy-MM-dd").parse(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate()); // createSBSQuoteRequest.getPolicySchedule().getEffectiveDate()
			
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if(!Utils.isEmpty(trnPolicies)) {
				policyExpiryDate = trnPolicies.get(0).getPolExpiryDate();
				if(renewalQuoteEffectiveDate.compareTo(policyExpiryDate)<=0) {
					SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_EFFECTIVEDATE, "SBSWS_ERR_103",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
		}
		/*
		 * Added new validation to verify claim check
		 */
		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation())
				&& !Utils.isEmpty(policyVO.getStatus())) {
			if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims())
					&& !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()
							.getNumberOfClaims())) {

				List<TMasCashCustomerQuo> tMasCashCustomerQuos = WSDAOUtils.getClaimInformation(policyVO);
				
				if (!Utils.isEmpty(tMasCashCustomerQuos)) {
					if (tMasCashCustomerQuos.get(0).getCshLossAmt() != null
							|| tMasCashCustomerQuos.get(0).getCshETelexNo() != null) {

						if (!Utils.isEmpty(tMasCashCustomerQuos.get(0).getCshLossAmt())) {
							if (createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()
									.getValueOfClaims() < tMasCashCustomerQuos.get(0).getCshLossAmt().intValue()) {

								SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
										"Loss experience quantum", "SBSWS_ERR_106", SBSErrorCodes.ERROR.name(),
										com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}else if(!Utils.isEmpty(tMasCashCustomerQuos.get(0).getCshETelexNo())) {
							if (createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()
									.getNumberOfClaims() < Integer
											.parseInt(tMasCashCustomerQuos.get(0).getCshETelexNo())) {
								SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
										"Loss experience quantum", "SBSWS_ERR_106", SBSErrorCodes.ERROR.name(),
										com.Constant.CONST_BUSINESS);
								sbsWSValidatorsList.add(SBSbusinessValidators);
							}
						}
					}
				}
			}
		}
		return sbsWSValidatorsList;
	}
	public List<SBSWSValidators> validateGetDocumentList(PolicyVO policyVO, List<SBSWSValidators> sbsWSValidatorsList){
		
		List<TTrnPolicyQuo> policy = null;
		
		policy = WSDAOUtils.getPolicyDetailsByPolicyNoAndYear(policyVO);
		
		if(Utils.isEmpty(policy)) {
			SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_104",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
			
		}
		return sbsWSValidatorsList;
	}
	public List<SBSWSValidators> validateGetDocument(List<SBSWSValidators> sbsWSValidatorsList, PolicyVO policyVO){
		
		List<TTrnPolicyQuo> policy = null;
		policyVO.setAuthInfoVO(new AuthenticationInfoVO());
		policyVO.getAuthInfoVO().setRefPolicyNo(policyVO.getPolicyNo());
		policy = WSDAOUtils.getPolicyDetails(policyVO);
		
		if(Utils.isEmpty(policy)) {
			SBSWSValidators SBSbusinessValidators  = WSBusinessValidatorUtils.businessErrorMapping("Document_Id", "SBSWS_ERR_105",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
			
		}
		return sbsWSValidatorsList;
	}
	public List<SBSWSValidators> validateQuoteNumber(PolicyVO policyVO,List<SBSWSValidators> sbsWSValidatorsList) {
		List<TTrnPolicyQuo> policy = null;
		policy = WSDAOUtils.isValidQuote(policyVO);
		if(Utils.isEmpty(policy)) {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_081",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		return sbsWSValidatorsList;
	}
}
