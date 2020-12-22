package com.rsaame.pas.b2c.wsValidators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class HomeCreateQuoteValidator {
	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		int ownerShipStatus=0;
		boolean containPersonalPossession=false;
		boolean containContentItems=false;
		boolean containPpItems=false;
		boolean mandatoryCoverPresent=false;
		boolean containsListedItems=false;
		boolean containsStaffDetails=false;
		BigDecimal contentCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal ppCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal sumContentItemsCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal sumPpItemsCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal minContentItemsCoveraggeLimit= new BigDecimal(40000.0);
		BigDecimal minPpItemsCoveraggeLimit= new BigDecimal(10000.0);
		CreateHomeQuoteRequest createHomeQuoteRequest = (CreateHomeQuoteRequest) arg0;
		if (!Utils.isEmpty(createHomeQuoteRequest) && createHomeQuoteRequest!=null)
		{
			
			/* For Customer Mobile Number */
			if(createHomeQuoteRequest.getCustomerDetails().getMobileNo()==null || Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getMobileNo()))
			{
				ValidationError error = ErrorMapping.errorMapping("MobileNo", "WS_003");
				errors.add(error);
			}
			else{
				ValidationError error = CommonValidator.isValidPhoneNo(createHomeQuoteRequest.getCustomerDetails().getMobileNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			
			/* For Customer Email Id */
			if(createHomeQuoteRequest.getCustomerDetails().getEmailId()==null || Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getEmailId()))
			{
				ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_001");
				errors.add(error);
			}
			else
			{
				ValidationError error = CommonValidator.isValidEmail(createHomeQuoteRequest.getCustomerDetails().getEmailId());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Customer Nationality */
			if(createHomeQuoteRequest.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getNationality()))
			{
				ValidationError error = CommonValidator.isValidNationality(createHomeQuoteRequest.getCustomerDetails().getNationality());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer First Name */
			if(createHomeQuoteRequest.getCustomerDetails().getFirstName()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getFirstName()))
			{
					ValidationError error = CommonValidator.isValidFirstName(createHomeQuoteRequest.getCustomerDetails().getFirstName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			
			/* For Customer Last Name */
			if(createHomeQuoteRequest.getCustomerDetails().getLastName()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getLastName()))
			{
				ValidationError error = CommonValidator.isValidLastName(createHomeQuoteRequest.getCustomerDetails().getLastName());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Customer Post Office */
			if(createHomeQuoteRequest.getCustomerDetails().getPoBox()!=null || !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getPoBox()))
			{
					ValidationError error = CommonValidator.isValidPostBox(createHomeQuoteRequest.getCustomerDetails().getPoBox());
					if (error != null)
					{
						errors.add(error);
					}
			}
			/* For Customer City */
			if(createHomeQuoteRequest.getCustomerDetails().getCity()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getCity()))
			{
					ValidationError error = CommonValidator.isValidCity(createHomeQuoteRequest.getCustomerDetails().getCity());
					if (error != null)
					{
						errors.add(error);
					}
			}
			/* For Customer InsuredID */
			if(createHomeQuoteRequest.getCustomerDetails().getInsuredId()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getInsuredId()))
			{
				if(createHomeQuoteRequest.getCustomerDetails().getInsuredId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("InsuredId", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(createHomeQuoteRequest.getCustomerDetails().getInsuredId().toString().length()>WsAppConstants.maxInsuredIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping("InsuredId", "WS_061");
					errors.add(error);
				}
			}
			
			/* For Customer NationalId */
			if(createHomeQuoteRequest.getCustomerDetails().getNationalID()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getNationalID()))
			{
				ValidationError error = CommonValidator.isValidNationalID(createHomeQuoteRequest.getCustomerDetails().getNationalID());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer VatRegNo */
			if(createHomeQuoteRequest.getCustomerDetails().getVatRegNo()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getVatRegNo()))
			{
				ValidationError error = CommonValidator.isValidVatRegnNo(createHomeQuoteRequest.getCustomerDetails().getVatRegNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardProgrammeType */
			if(createHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType()))
			{
				ValidationError error = CommonValidator.isValidRewardProgrammeType(createHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardCardNumber */
			if(createHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()!=null && !Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()))
			{
				ValidationError error = CommonValidator.isValidRewardCardNumber(createHomeQuoteRequest.getCustomerDetails().getRewardCardNumber());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Transaction Details*/
			/*List<ValidationError> errors1 = new ArrayList<ValidationError>();
			if(createHomeQuoteRequest.getTransactionDetails()==null || Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails()))
			{
				ValidationError error = ErrorMapping.errorMapping("TransactionalDetails", "WS_020");
				if (error != null)
				{
					errors.add(error);
				}
			}
			else
			{
				errors1 = CommonValidator.validateTransactionDetails(createHomeQuoteRequest.getTransactionDetails());
				if(errors1!=null)
				{
					errors.addAll(errors1);
				}
				if(!(createHomeQuoteRequest.getTransactionDetails().getTariffCode() == WsAppConstants.PACKAGED_HOME_TARIFF_CODE ))
				{
					ValidationError error = ErrorMapping.errorMapping("TariffCode", "WS_010");
					errors.add(error);
				}
				List<Integer> distChannels = new ArrayList<Integer>();
				distChannels.add(4);
				distChannels.add(9);
				distChannels.add(10);
				 For Transaction Distribution Channel 
				if(!distChannels.contains(createHomeQuoteRequest.getTransactionDetails().getDistChannel()))
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", "WS_257");
					errors.add(error);
				}
				 For Transaction EffectiveDate 
				if(createHomeQuoteRequest.getTransactionDetails().getEffectiveDate()!=null && !Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getEffectiveDate()))
				{
					ValidationError error = ErrorMapping.errorMapping("EffectiveDate", "WS_141");
					errors.add(error);
				}
				 For Transaction ExpiryDate 
				if(createHomeQuoteRequest.getTransactionDetails().getExpiryDate()!=null && !Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getExpiryDate()))
				{
					ValidationError error = ErrorMapping.errorMapping("ExpiryDate", "WS_142");
					errors.add(error);
				}
				 For Transaction PolicyTerm 
				if(!Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getPolicyTerm()) && !(createHomeQuoteRequest.getTransactionDetails().getPolicyTerm()==null) )
				{
					ValidationError error = ErrorMapping.errorMapping("PolicyTerm", "WS_143");
					errors.add(error);
				}
				ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
				 For Transaction PolicyTypeCode 
				if(!Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()) && !(createHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()==null))
				{
					
					if(createHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					else if(Integer.parseInt(resourceBundle.getString("HOME_POLICY_TYPE"))!=createHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode())
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, "WS_012");
						errors.add(error);
					}
				}
				else
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, "WS_011");
					errors.add(error);
				}
				 For Transaction Class Code 
				if(!Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getClassCode()) && !(createHomeQuoteRequest.getTransactionDetails().getClassCode()==null))
				{
					if(createHomeQuoteRequest.getTransactionDetails().getClassCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					if(Integer.parseInt(resourceBundle.getString("HOME_CLASS_CODE"))!=createHomeQuoteRequest.getTransactionDetails().getClassCode())
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_038");
						errors.add(error);
					}
				}
				else
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_037");
					errors.add(error);
				}
			}*/
			
			
			/* For Building Details*/
			if(createHomeQuoteRequest.getBuildingDetails()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails()))
			{
				
				/* For OwnerShip Status*/
				if(createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==null || Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()))
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, "WS_023");
					errors.add(error5);
				}
				else
				{
					if(createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()!=WsAppConstants.OwnYourHome && createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()!=WsAppConstants.RentYourHome)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, "WS_024");
							errors.add(error5);
						}
					else{
						ownerShipStatus = createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus();
					}
				}
				/* For Emirate */
				/*if(createHomeQuoteRequest.getBuildingDetails().getEmirate()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getEmirate()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getEmirate()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping("Emitrates", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(ValidationUtil.countDigits(createHomeQuoteRequest.getBuildingDetails().getEmirate())>WsAppConstants.maxEmirateLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("Emitrates", "WS_115");
						errors.add(error5);
					}
				}*/
				/* For Area*/
				/*if(createHomeQuoteRequest.getBuildingDetails().getArea()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getArea()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getArea()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping("BuildingArea", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(ValidationUtil.countDigits(createHomeQuoteRequest.getBuildingDetails().getArea())>WsAppConstants.maxAreaLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("BuildingArea", "WS_080");
						errors.add(error5);
					}
				}
				 For Area Others 
				if(createHomeQuoteRequest.getBuildingDetails().getArea()!=null && createHomeQuoteRequest.getBuildingDetails().getArea()==WsAppConstants.AreaOthers)
				{
					if(createHomeQuoteRequest.getBuildingDetails().getAreaOthers()==null || Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getAreaOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_117");
						errors.add(error5);
					}
					else
					{
						if(createHomeQuoteRequest.getBuildingDetails().getAreaOthers().length()>WsAppConstants.maxAreaOthersLength)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_103");
							errors.add(error5);
						}
					}
				}
				else if(createHomeQuoteRequest.getBuildingDetails().getArea()!=null && createHomeQuoteRequest.getBuildingDetails().getArea()!=(WsAppConstants.AreaOthers))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getAreaOthers()!=null || !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getAreaOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_118");
						errors.add(error5);
					}
				}*/
				/* For PropertyType*/
				if(createHomeQuoteRequest.getBuildingDetails().getPropertyType()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getPropertyType()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getPropertyType()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping("PropertyType", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(!(createHomeQuoteRequest.getBuildingDetails().getPropertyType()==WsAppConstants.ApartmentCode || createHomeQuoteRequest.getBuildingDetails().getPropertyType()==WsAppConstants.VillaCode))
					{
						ValidationError error5 = ErrorMapping.errorMapping("PropertyType", "WS_082");
						errors.add(error5);
					}
				}
				/* For Building Name */
				//CTS - TFS #42729 - Building Details Validation - Starts
				if(createHomeQuoteRequest.getBuildingDetails().getBuildingName()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getBuildingName()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getBuildingName().length()>WsAppConstants.maxBuildingNameLength )
					{
						ValidationError error5 = ErrorMapping.errorMapping("BuildingName", "WS_084");
						errors.add(error5);
					}
				}
				//CTS - TFS #42729 - Building Details Validation - Ends
				/* For FlatVillaNo*/
				if(createHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getFlatVillaNo().length() > WsAppConstants.maxFlatVillaNoLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("FlatVilla No", "WS_086");
						errors.add(error5);
					}
				}
				/* For Mortgage Code */
				if(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()))
				{
					if(createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==WsAppConstants.RentYourHome)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, "WS_256");
						errors.add(error5);
					}
					else if(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(ValidationUtil.countDigits(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode())>WsAppConstants.maxMortgageCodeLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, "WS_088");
						errors.add(error5);
					}
				}
				/* For Mortgage Others */
				if(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()==WsAppConstants.MortgageOthers)
				{
					if(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()==null || Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_119");
						errors.add(error5);
					}
					else
					{
						if(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers().length()>WsAppConstants.maxMortgageOthersLength)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_104");
							errors.add(error5);
						}
						
					}
				}
				else if(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=WsAppConstants.MortgageOthers)
				{
					if(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()!=null || !Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_120");
						errors.add(error5);
					}
				}
			}
			else
			{
			ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, "WS_024");
			errors.add(error5);
			}
			
			/* For Mandatory Covers */
			if(createHomeQuoteRequest.getMandatoryCovers()==null || Utils.isEmpty(createHomeQuoteRequest.getMandatoryCovers())){
				ValidationError error5 = ErrorMapping.errorMapping("MandatoryCoverDetails", "WS_036");
				errors.add(error5);
			}
			else
			{
				List<MandatoryCovers> mandatoryCovers = new ArrayList<MandatoryCovers>();
				mandatoryCovers = createHomeQuoteRequest.getMandatoryCovers();
				for(MandatoryCovers c : mandatoryCovers)
				{
					/* For CoverIncluded */
					if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
					{
						ValidationError error5 = ErrorMapping.errorMapping("CoverIncluded", "WS_105");
						errors.add(error5);
					}
					else
					{
						if(c.getCoverIncluded()==true)
						{
							mandatoryCoverPresent=true;
						}
						/* For CoverDesc */
						if((c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc()))&&c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_025");
							errors.add(error5);
						}
						else
						{
							if(c.getCoverDesc().length()>WsAppConstants.maxCoverDescLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_026");
								errors.add(error5);
							}
						}
						
						/* For CoverMappingCode */
						/*if((c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode())) &&c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
							errors.add(error5);
						}
						else
						{
							if(c.getCoverMappingCode().length()>WsAppConstants.maxHomeCoverMappingCodeLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
								errors.add(error5);
							}
						}*/
						/* For CoverageLimit */
						{
							if((c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit())) &&c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping("CoverageLimit", "WS_252");
								errors.add(error5);
							}
						}
						/* For Cover Risk Details */
						if(c.getRiskMappingCode()==null || Utils.isEmpty(c.getRiskMappingCode()))
						{
							ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
							errors.add(error5);
						}
						else
						{/*
							 For Risk Id
							if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
							{
								if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>6)
								{
									ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
									errors.add(error5);
								}
							}
							 For Basic Risk Id
							if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
							{
								if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>6)
								{
									ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
									errors.add(error5);
								}
							}*/
							/* For  Basic Risk Code*/
							String[] riskCodes= c.getRiskMappingCode().split("-");
							/*if(c.getRiskMappingCode()==null || Utils.isEmpty(c.getRiskMappingCode()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
								errors.add(error5);
							}
							else
							{
								if(c.getRiskDetails().getBasicRskCode()<0)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, com.Constant.CONST_WS_254);
									errors.add(error5);
								}
								else if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskCode())>WsAppConstants.maxBasicRiskCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_029");
									errors.add(error5);
								}
							}*/
							/* For Risk Code*/
							if(riskCodes[0]==null || Utils.isEmpty(Integer.parseInt(riskCodes[0])))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
								errors.add(error5);
							}
							else
							{
								if(Integer.parseInt(riskCodes[0])<0)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
									errors.add(error5);
								}
								else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[0]))>WsAppConstants.maxRiskCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
									errors.add(error5);
								}
							}
							/* For Risk Type*/
							if(riskCodes[1]==null || Utils.isEmpty(Integer.parseInt(riskCodes[1])))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
								errors.add(error5);
							}
							else
							{
								if(Integer.parseInt(riskCodes[1])<0)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
									errors.add(error5);
								}
								if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[1]))>WsAppConstants.maxRiskTypeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
									errors.add(error5);
								}
								if(Integer.parseInt(riskCodes[1])==31 && c.getCoverIncluded()==true)
								{
									containsListedItems=true;
									containPersonalPossession=true;
									containContentItems=true;
									contentCoveraggeLimit=c.getCoverageLimit();
								}
								if(Integer.parseInt(riskCodes[1])==32 && containPersonalPossession==false && c.getCoverIncluded()==true)
								{
									ValidationError error5 = ErrorMapping.errorMapping("PersonalPossession", "WS_145");
									errors.add(error5);
								}
								else if(Integer.parseInt(riskCodes[1])==32 && containPersonalPossession==true && c.getCoverIncluded()==true)
								{
									ppCoveraggeLimit=c.getCoverageLimit();
									containPpItems=true;
								}
									
							}
							/* For Risk Category*/
							if(riskCodes[2]==null || Utils.isEmpty(Integer.parseInt(riskCodes[2]))) 
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
								errors.add(error5);
							}
							else
							{
								if(Integer.parseInt(riskCodes[2])<0)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
									errors.add(error5);
								}
								else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[2]))>WsAppConstants.maxRiskCategoryLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
									errors.add(error5);
								}
							}
							if(Integer.parseInt(riskCodes[0])==1 && Integer.parseInt(riskCodes[1])==1 && Integer.parseInt(riskCodes[2])==0)
							{
								if(ownerShipStatus==WsAppConstants.RentYourHome && c.getCoverIncluded()==true )
								{
									ValidationError error5 = ErrorMapping.errorMapping("BuildingCoverIncluded", "WS_255");
									errors.add(error5);
								}
								if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minBuildingCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
								{
									ValidationError error5 = ErrorMapping.errorMapping("BuildingCoverageLimit", "WS_258");
									errors.add(error5);
								}
							}
							if(Integer.parseInt(riskCodes[0])==2 && Integer.parseInt(riskCodes[1])==31 && Integer.parseInt(riskCodes[2])==1)
							{
								if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minContentCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
								{
									ValidationError error5 = ErrorMapping.errorMapping("ContentCoverageLimit", "WS_259");
									errors.add(error5);
								}
							}
							if(Integer.parseInt(riskCodes[0])==2 && Integer.parseInt(riskCodes[1])==32 && Integer.parseInt(riskCodes[2])==1)
							{
								if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minPersonalPossessionCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
								{
									ValidationError error5 = ErrorMapping.errorMapping("PersonalPossessionCoverageLimit", "WS_260");
									errors.add(error5);
								}
							}
						}
					}
					
				}
				if(mandatoryCoverPresent==false)
				{
					ValidationError error5 = ErrorMapping.errorMapping("MandatoryCover", "WS_144");
					errors.add(error5);
				}
			}
			/* For Listed Items*/
			if(containsListedItems==false){
				List<ListedItems> listedItems = new ArrayList<ListedItems>();
				listedItems = createHomeQuoteRequest.getListedItems();
				if(listedItems!=null && !Utils.isEmpty(listedItems) )
				{
					for(ListedItems c : listedItems)
					{
						if(c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping("ListedItemsDetails", "WS_304");
							errors.add(error5);
						}
					}
				}
					
			}
			else
			{
				if((createHomeQuoteRequest.getListedItems()!=null && !Utils.isEmpty(createHomeQuoteRequest.getListedItems()) ))
				{
					List<ListedItems> optionalCovers = new ArrayList<ListedItems>();
					optionalCovers = createHomeQuoteRequest.getListedItems();
					for(ListedItems c : optionalCovers)
					{
						String[] riskCodes= c.getRiskMappingCode().split("-");
						/* For CoverIncluded */
						if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
						{
							ValidationError error5 = ErrorMapping.errorMapping("CoverIncluded", "WS_105");
							errors.add(error5);
						}
						else
						{
							/* For CoverDesc */
							if((c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_025");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverDesc().length()>WsAppConstants.maxCoverDescLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_026");
									errors.add(error5);
								}
							}
							
							/* For CoverMappingCode */
							/*if((c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverMappingCode().length()>WsAppConstants.maxHomeCoverMappingCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
									errors.add(error5);
								}
								if(c.getCoverMappingCode().startsWith("3") && c.getCoverIncluded()==true)
								{
									containsStaffDetails=true;
								}
								if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==true )
								{
									if(ownerShipStatus==WsAppConstants.OwnYourHome)
									{
										ValidationError error5 = ErrorMapping.errorMapping("AdditionalTenantsLiabilityCover", "WS_113");
										errors.add(error5);
									}
								}
							}*/
							/* For Cover Risk Details */
							if(c.getRiskMappingCode()==null || Utils.isEmpty(c.getRiskMappingCode()))
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
								errors.add(error5);
							}
							else
							{/*
								 For Risk Id
								if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
										errors.add(error5);
									}
								}
								 For Basic Risk Id
								if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
										errors.add(error5);
									}
								}*/
								/* For  Basic Risk Code*/
								/*if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
								{
									//ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
									//errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getBasicRskCode()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskCode())>WsAppConstants.maxBasicRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_029");
										errors.add(error5);
									}
								}*/
								/* For Risk Code*/
								if(riskCodes[0]==null || Utils.isEmpty(Integer.parseInt(riskCodes[0])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[0])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[0]))>WsAppConstants.maxRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
										errors.add(error5);
									}
								}
								/* For Risk Type*/
								if(riskCodes[1]==null || Utils.isEmpty(Integer.parseInt(riskCodes[1])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[1])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[1]))>WsAppConstants.maxRiskTypeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
										errors.add(error5);
									}
								}
								/* For Risk Category*/
								if(riskCodes[2]==null || Utils.isEmpty(Integer.parseInt(riskCodes[2])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[2])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[2]))>WsAppConstants.maxRiskCategoryLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
										errors.add(error5);
									}
									if(Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("ContentItems", "WS_146");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minContentItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_147");
												errors.add(error5);
											}
											else{
												sumContentItemsCoveraggeLimit=sumContentItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
											
										}
										
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("PersonalPossessionItems", "WS_251");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minPpItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_148");
												errors.add(error5);
											}
											else{
												sumPpItemsCoveraggeLimit=sumPpItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
										}
										
									}
								}
							}
						}
						
					}
				if(contentCoveraggeLimit.compareTo(sumContentItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_149");
					errors.add(error5);
				}
				if(ppCoveraggeLimit.compareTo(sumPpItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_150");
					errors.add(error5);
				}
				}
			}
			/* For StaffDetails */
			if(containsStaffDetails==true)
			{
				if(createHomeQuoteRequest.getStaffDetails()==null || Utils.isEmpty(createHomeQuoteRequest.getStaffDetails()))
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_022");
					errors.add(error5);
				}
				else
				{
					List<Staff> staff = new ArrayList<Staff>();
					staff = createHomeQuoteRequest.getStaffDetails().getStaff();
					for(Staff s : staff )
					{
						if(Utils.isEmpty(s.getStaffName())|| Utils.isEmpty(s.getStaffDob())|| s.getStaffName()==null || s.getStaffDob() == null)
						{
							ValidationError error4 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_112");
							errors.add(error4);
							break;
						}
					}
				}
			}
			else
			{
				if(createHomeQuoteRequest.getStaffDetails()!=null && !Utils.isEmpty(createHomeQuoteRequest.getStaffDetails()))
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_111");
					errors.add(error5);
				}
			}
			
	}
	else
	{
		ValidationError error5 = ErrorMapping.errorMapping("RequestObject", "WS_140");
		errors.add(error5);
	}
	validationException.setErrors(errors);
	return validationException;
	}
}
