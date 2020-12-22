package com.rsaame.pas.gen.domain;

// Generated Feb 8, 2012 9:37:43 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

/**
 * TMasCashCustomerQuo generated by hbm2java
 */
public class TMasCashCustomerQuo extends POJOWrapper implements java.io.Serializable {

	private TMasCashCustomerQuoId id;
	private Long cshCustomerId;
	private Byte cshNameTitle;
	private String cshASurnameTribe;
	private String cshESurnameTribe;
	private String cshAName1;
	private String cshEName1;
	private String cshAName2;
	private String cshEName2;
	private String cshAName3;
	private String cshEName3;
	private String cshAName4;
	private String cshEName4;
	private String cshAName5;
	private String cshEName5;
	private String cshAAddress1;
	private String cshEAddress1;
	private String cshAAddress2;
	private String cshEAddress2;
	private String cshAAddress3;
	private String cshEAddress3;
	private String cshAAddress4;
	private String cshEAddress4;
	private String cshAAddress5;
	private String cshEAddress5;
	private String cshAZipCode;
	private String cshEZipCode;
	private String cshAEmailId;
	private String cshEEmailId;
	private Long cshFaxNo;
	private String cshATelexNo;
	private String cshETelexNo;
	private String cshAPhoneNo;
	private String cshEPhoneNo;
	private String cshAGsmNo;
	private String cshEGsmNo;
	private Short cshNationality;
	private Short cshOcCode;
	private Short cshCcgCode;
	private String cshACoRegnNo;
	private String cshECoRegnNo;
	private String cshAPassportNo;
	private String cshEPassportNo;
	private String cshAIdCardNo;
	private String cshEIdCardNo;
	private String cshAVisaNo;
	private String cshEVisaNo;
	private Integer cshUserId;
	private Date cshValidityExpiryDate;
	private Integer cshCtyCode;
	private Integer cshRegCode;
	private Integer cshLocCode;
	private BigDecimal cshTotAccCode;
	private Long cshInsuredId;
	private Long cshEndtId;
	private Byte cshCutCode;
	private Integer cshPreparedBy;
	private Date cshPreparedDt;
	private Integer cshModifiedBy;
	private Date cshModifiedDt;
	private String cshBusiness;
	private String cshCustType;
	private String cshPoboxNo;
	private Integer cshTerritory;
	private Integer cshLaws;
	private String cshCompanyName;
	private String cshCompanyAddr1;
	private Integer cshIntAccExecCode;
	private Integer cshExtAccExecCode;
	private String cshLegalForm;
	private Date cshDtEstablishment;
	private String cshPlaceEstablishment;
	private Integer cshRegulatoryBody;
	private Integer cshExternalAuditor;
	private Short cshSourceOfCust;
	private Long cshMarketingFee;
	private Date cshDob;
	private String cshPob;
	private String cshAffinityRefNo;
	private Short cshEcmCode;
	private Short cshIdTypCode;
	private String cshIdNo;
	private Integer cshCity;
	private Integer cshCountry;
	private Character cshSex;
	private Short cshBusType;
	private String cshDueDilligence;
	private BigDecimal cshLossRatio;
	private BigDecimal cshLossAmt;
	private Long cshTurnover;
	private Integer cshNoOfEmp;
	// Home And Travel New Field Start - Removed since the column is moved to TTrnPolicyQuo
	//private String cshPromoCode;
	// Home And Travel New Field End
	private Integer cshRoyaltyTypCode;
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
	private String cshNationalId;
	private Date cshEmiratesIdExpiryDate;
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends
	
	
	public TMasCashCustomerQuo() {
	}

	public TMasCashCustomerQuo(TMasCashCustomerQuoId id) {
		this.id = id;
	}
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
	public TMasCashCustomerQuo(TMasCashCustomerQuoId id, Long cshCustomerId,
			Byte cshNameTitle, String cshASurnameTribe,
			String cshESurnameTribe, String cshAName1, String cshEName1,
			String cshAName2, String cshEName2, String cshAName3,
			String cshEName3, String cshAName4, String cshEName4,
			String cshAName5, String cshEName5, String cshAAddress1,
			String cshEAddress1, String cshAAddress2, String cshEAddress2,
			String cshAAddress3, String cshEAddress3, String cshAAddress4,
			String cshEAddress4, String cshAAddress5, String cshEAddress5,
			String cshAZipCode, String cshEZipCode, String cshAEmailId,
			String cshEEmailId, Long cshFaxNo, String cshATelexNo,
			String cshETelexNo, String cshAPhoneNo, String cshEPhoneNo,
			String cshAGsmNo, String cshEGsmNo, Short cshNationality,
			Short cshOcCode, Short cshCcgCode, String cshACoRegnNo,
			String cshECoRegnNo, String cshAPassportNo, String cshEPassportNo,
			String cshAIdCardNo, String cshEIdCardNo, String cshAVisaNo,
			String cshEVisaNo, Integer cshUserId, Date cshValidityExpiryDate,
			Integer cshCtyCode, Integer cshRegCode, Integer cshLocCode,
			BigDecimal cshTotAccCode, Long cshInsuredId, Long cshEndtId,
			Byte cshCutCode, Integer cshPreparedBy, Date cshPreparedDt,
			Integer cshModifiedBy, Date cshModifiedDt, String cshBusiness,
			String cshCustType, String cshPoboxNo, Integer cshTerritory,
			Integer cshLaws, String cshCompanyName, String cshCompanyAddr1,
			Integer cshIntAccExecCode, Integer cshExtAccExecCode,
			String cshLegalForm, Date cshDtEstablishment,
			String cshPlaceEstablishment, Integer cshRegulatoryBody,
			Integer cshExternalAuditor, Short cshSourceOfCust,
			Long cshMarketingFee, Date cshDob, String cshPob,
			String cshAffinityRefNo, Short cshEcmCode, Short cshIdTypCode,
			String cshIdNo, Integer cshCity, Integer cshCountry,
			Character cshSex, Short cshBusType, String cshDueDilligence,
			BigDecimal cshLossRatio, BigDecimal cshLossAmt, Long cshTurnover, Integer cshNoOfEmp, Integer cshRoyaltyTypCode,
			String cshNationalId, Date cshEmiratesIdExpiryDate) {
		this.id = id;
		this.cshCustomerId = cshCustomerId;
		this.cshNameTitle = cshNameTitle;
		this.cshASurnameTribe = cshASurnameTribe;
		this.cshESurnameTribe = cshESurnameTribe;
		this.cshAName1 = cshAName1;
		this.cshEName1 = cshEName1;
		this.cshAName2 = cshAName2;
		this.cshEName2 = cshEName2;
		this.cshAName3 = cshAName3;
		this.cshEName3 = cshEName3;
		this.cshAName4 = cshAName4;
		this.cshEName4 = cshEName4;
		this.cshAName5 = cshAName5;
		this.cshEName5 = cshEName5;
		this.cshAAddress1 = cshAAddress1;
		this.cshEAddress1 = cshEAddress1;
		this.cshAAddress2 = cshAAddress2;
		this.cshEAddress2 = cshEAddress2;
		this.cshAAddress3 = cshAAddress3;
		this.cshEAddress3 = cshEAddress3;
		this.cshAAddress4 = cshAAddress4;
		this.cshEAddress4 = cshEAddress4;
		this.cshAAddress5 = cshAAddress5;
		this.cshEAddress5 = cshEAddress5;
		this.cshAZipCode = cshAZipCode;
		this.cshEZipCode = cshEZipCode;
		this.cshAEmailId = cshAEmailId;
		this.cshEEmailId = cshEEmailId;
		this.cshFaxNo = cshFaxNo;
		this.cshATelexNo = cshATelexNo;
		this.cshETelexNo = cshETelexNo;
		this.cshAPhoneNo = cshAPhoneNo;
		this.cshEPhoneNo = cshEPhoneNo;
		this.cshAGsmNo = cshAGsmNo;
		this.cshEGsmNo = cshEGsmNo;
		this.cshNationality = cshNationality;
		this.cshOcCode = cshOcCode;
		this.cshCcgCode = cshCcgCode;
		this.cshACoRegnNo = cshACoRegnNo;
		this.cshECoRegnNo = cshECoRegnNo;
		this.cshAPassportNo = cshAPassportNo;
		this.cshEPassportNo = cshEPassportNo;
		this.cshAIdCardNo = cshAIdCardNo;
		this.cshEIdCardNo = cshEIdCardNo;
		this.cshAVisaNo = cshAVisaNo;
		this.cshEVisaNo = cshEVisaNo;
		this.cshUserId = cshUserId;
		this.cshValidityExpiryDate = cshValidityExpiryDate;
		this.cshCtyCode = cshCtyCode;
		this.cshRegCode = cshRegCode;
		this.cshLocCode = cshLocCode;
		this.cshTotAccCode = cshTotAccCode;
		this.cshInsuredId = cshInsuredId;
		this.cshEndtId = cshEndtId;
		this.cshCutCode = cshCutCode;
		this.cshPreparedBy = cshPreparedBy;
		this.cshPreparedDt = cshPreparedDt;
		this.cshModifiedBy = cshModifiedBy;
		this.cshModifiedDt = cshModifiedDt;
		this.cshBusiness = cshBusiness;
		this.cshCustType = cshCustType;
		this.cshPoboxNo = cshPoboxNo;
		this.cshTerritory = cshTerritory;
		this.cshLaws = cshLaws;
		this.cshCompanyName = cshCompanyName;
		this.cshCompanyAddr1 = cshCompanyAddr1;
		this.cshIntAccExecCode = cshIntAccExecCode;
		this.cshExtAccExecCode = cshExtAccExecCode;
		this.cshLegalForm = cshLegalForm;
		this.cshDtEstablishment = cshDtEstablishment;
		this.cshPlaceEstablishment = cshPlaceEstablishment;
		this.cshRegulatoryBody = cshRegulatoryBody;
		this.cshExternalAuditor = cshExternalAuditor;
		this.cshSourceOfCust = cshSourceOfCust;
		this.cshMarketingFee = cshMarketingFee;
		this.cshDob = cshDob;
		this.cshPob = cshPob;
		this.cshAffinityRefNo = cshAffinityRefNo;
		this.cshEcmCode = cshEcmCode;
		this.cshIdTypCode = cshIdTypCode;
		this.cshIdNo = cshIdNo;
		this.cshCity = cshCity;
		this.cshCountry = cshCountry;
		this.cshSex = cshSex;
		this.cshBusType = cshBusType;
		this.cshDueDilligence = cshDueDilligence;
		this.cshLossRatio = cshLossRatio;
		this.cshLossAmt = cshLossAmt;
		this.cshTurnover = cshTurnover;
		this.cshNoOfEmp = cshNoOfEmp;	
		this.cshRoyaltyTypCode = cshRoyaltyTypCode;
		this.cshNationalId = cshNationalId;
		this.cshEmiratesIdExpiryDate = cshEmiratesIdExpiryDate;
	
		//this.cshPromoCode = cshPromoCode; - Removed since the column is moved to TTrnPolicyQuo table
	}
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends
	public TMasCashCustomerQuoId getId() {
		return this.id;
	}

	public void setId(TMasCashCustomerQuoId id) {
		this.id = id;
	}

	public Long getCshCustomerId() {
		return this.cshCustomerId;
	}

	public void setCshCustomerId(Long cshCustomerId) {
		this.cshCustomerId = cshCustomerId;
	}

	public Byte getCshNameTitle() {
		return this.cshNameTitle;
	}

	public void setCshNameTitle(Byte cshNameTitle) {
		this.cshNameTitle = cshNameTitle;
	}

	public String getCshASurnameTribe() {
		return this.cshASurnameTribe;
	}

	public void setCshASurnameTribe(String cshASurnameTribe) {
		this.cshASurnameTribe = cshASurnameTribe;
	}

	public String getCshESurnameTribe() {
		return this.cshESurnameTribe;
	}

	public void setCshESurnameTribe(String cshESurnameTribe) {
		this.cshESurnameTribe = cshESurnameTribe;
	}

	public String getCshAName1() {
		return this.cshAName1;
	}

	public void setCshAName1(String cshAName1) {
		this.cshAName1 = cshAName1;
	}

	public String getCshEName1() {
		return this.cshEName1;
	}

	public void setCshEName1(String cshEName1) {
		this.cshEName1 = cshEName1;
	}

	public String getCshAName2() {
		return this.cshAName2;
	}

	public void setCshAName2(String cshAName2) {
		this.cshAName2 = cshAName2;
	}

	public String getCshEName2() {
		return this.cshEName2;
	}

	public void setCshEName2(String cshEName2) {
		this.cshEName2 = cshEName2;
	}

	public String getCshAName3() {
		return this.cshAName3;
	}

	public void setCshAName3(String cshAName3) {
		this.cshAName3 = cshAName3;
	}

	public String getCshEName3() {
		return this.cshEName3;
	}

	public void setCshEName3(String cshEName3) {
		this.cshEName3 = cshEName3;
	}

	public String getCshAName4() {
		return this.cshAName4;
	}

	public void setCshAName4(String cshAName4) {
		this.cshAName4 = cshAName4;
	}

	public String getCshEName4() {
		return this.cshEName4;
	}

	public void setCshEName4(String cshEName4) {
		this.cshEName4 = cshEName4;
	}

	public String getCshAName5() {
		return this.cshAName5;
	}

	public void setCshAName5(String cshAName5) {
		this.cshAName5 = cshAName5;
	}

	public String getCshEName5() {
		return this.cshEName5;
	}

	public void setCshEName5(String cshEName5) {
		this.cshEName5 = cshEName5;
	}

	public String getCshAAddress1() {
		return this.cshAAddress1;
	}

	public void setCshAAddress1(String cshAAddress1) {
		this.cshAAddress1 = cshAAddress1;
	}

	public String getCshEAddress1() {
		return this.cshEAddress1;
	}

	public void setCshEAddress1(String cshEAddress1) {
		this.cshEAddress1 = cshEAddress1;
	}

	public String getCshAAddress2() {
		return this.cshAAddress2;
	}

	public void setCshAAddress2(String cshAAddress2) {
		this.cshAAddress2 = cshAAddress2;
	}

	public String getCshEAddress2() {
		return this.cshEAddress2;
	}

	public void setCshEAddress2(String cshEAddress2) {
		this.cshEAddress2 = cshEAddress2;
	}

	public String getCshAAddress3() {
		return this.cshAAddress3;
	}

	public void setCshAAddress3(String cshAAddress3) {
		this.cshAAddress3 = cshAAddress3;
	}

	public String getCshEAddress3() {
		return this.cshEAddress3;
	}

	public void setCshEAddress3(String cshEAddress3) {
		this.cshEAddress3 = cshEAddress3;
	}

	public String getCshAAddress4() {
		return this.cshAAddress4;
	}

	public void setCshAAddress4(String cshAAddress4) {
		this.cshAAddress4 = cshAAddress4;
	}

	public String getCshEAddress4() {
		return this.cshEAddress4;
	}

	public void setCshEAddress4(String cshEAddress4) {
		this.cshEAddress4 = cshEAddress4;
	}

	public String getCshAAddress5() {
		return this.cshAAddress5;
	}

	public void setCshAAddress5(String cshAAddress5) {
		this.cshAAddress5 = cshAAddress5;
	}

	public String getCshEAddress5() {
		return this.cshEAddress5;
	}

	public void setCshEAddress5(String cshEAddress5) {
		this.cshEAddress5 = cshEAddress5;
	}

	public String getCshAZipCode() {
		return this.cshAZipCode;
	}

	public void setCshAZipCode(String cshAZipCode) {
		this.cshAZipCode = cshAZipCode;
	}

	public String getCshEZipCode() {
		return this.cshEZipCode;
	}

	public void setCshEZipCode(String cshEZipCode) {
		this.cshEZipCode = cshEZipCode;
	}

	public String getCshAEmailId() {
		return this.cshAEmailId;
	}

	public void setCshAEmailId(String cshAEmailId) {
		this.cshAEmailId = cshAEmailId;
	}

	public String getCshEEmailId() {
		return this.cshEEmailId;
	}

	public void setCshEEmailId(String cshEEmailId) {
		this.cshEEmailId = cshEEmailId;
	}

	public Long getCshFaxNo() {
		return this.cshFaxNo;
	}

	public void setCshFaxNo(Long cshFaxNo) {
		this.cshFaxNo = cshFaxNo;
	}

	public String getCshATelexNo() {
		return this.cshATelexNo;
	}

	public void setCshATelexNo(String cshATelexNo) {
		this.cshATelexNo = cshATelexNo;
	}

	public String getCshETelexNo() {
		return this.cshETelexNo;
	}

	public void setCshETelexNo(String cshETelexNo) {
		this.cshETelexNo = cshETelexNo;
	}

	public String getCshAPhoneNo() {
		return this.cshAPhoneNo;
	}

	public void setCshAPhoneNo(String cshAPhoneNo) {
		this.cshAPhoneNo = cshAPhoneNo;
	}

	public String getCshEPhoneNo() {
		return this.cshEPhoneNo;
	}

	public void setCshEPhoneNo(String cshEPhoneNo) {
		this.cshEPhoneNo = cshEPhoneNo;
	}

	public String getCshAGsmNo() {
		return this.cshAGsmNo;
	}

	public void setCshAGsmNo(String cshAGsmNo) {
		this.cshAGsmNo = cshAGsmNo;
	}

	public String getCshEGsmNo() {
		return this.cshEGsmNo;
	}

	public void setCshEGsmNo(String cshEGsmNo) {
		this.cshEGsmNo = cshEGsmNo;
	}

	public Short getCshNationality() {
		return this.cshNationality;
	}

	public void setCshNationality(Short cshNationality) {
		this.cshNationality = cshNationality;
	}

	public Short getCshOcCode() {
		return this.cshOcCode;
	}

	public void setCshOcCode(Short cshOcCode) {
		this.cshOcCode = cshOcCode;
	}

	public Short getCshCcgCode() {
		return this.cshCcgCode;
	}

	public void setCshCcgCode(Short cshCcgCode) {
		this.cshCcgCode = cshCcgCode;
	}

	public String getCshACoRegnNo() {
		return this.cshACoRegnNo;
	}

	public void setCshACoRegnNo(String cshACoRegnNo) {
		this.cshACoRegnNo = cshACoRegnNo;
	}

	public String getCshECoRegnNo() {
		return this.cshECoRegnNo;
	}

	public void setCshECoRegnNo(String cshECoRegnNo) {
		this.cshECoRegnNo = cshECoRegnNo;
	}

	public String getCshAPassportNo() {
		return this.cshAPassportNo;
	}

	public void setCshAPassportNo(String cshAPassportNo) {
		this.cshAPassportNo = cshAPassportNo;
	}

	public String getCshEPassportNo() {
		return this.cshEPassportNo;
	}

	public void setCshEPassportNo(String cshEPassportNo) {
		this.cshEPassportNo = cshEPassportNo;
	}

	public String getCshAIdCardNo() {
		return this.cshAIdCardNo;
	}

	public void setCshAIdCardNo(String cshAIdCardNo) {
		this.cshAIdCardNo = cshAIdCardNo;
	}

	public String getCshEIdCardNo() {
		return this.cshEIdCardNo;
	}

	public void setCshEIdCardNo(String cshEIdCardNo) {
		this.cshEIdCardNo = cshEIdCardNo;
	}

	public String getCshAVisaNo() {
		return this.cshAVisaNo;
	}

	public void setCshAVisaNo(String cshAVisaNo) {
		this.cshAVisaNo = cshAVisaNo;
	}

	public String getCshEVisaNo() {
		return this.cshEVisaNo;
	}

	public void setCshEVisaNo(String cshEVisaNo) {
		this.cshEVisaNo = cshEVisaNo;
	}

	public Integer getCshUserId() {
		return this.cshUserId;
	}

	public void setCshUserId(Integer cshUserId) {
		this.cshUserId = cshUserId;
	}

	public Date getCshValidityExpiryDate() {
		return this.cshValidityExpiryDate;
	}

	public void setCshValidityExpiryDate(Date cshValidityExpiryDate) {
		this.cshValidityExpiryDate = cshValidityExpiryDate;
	}

	public Integer getCshCtyCode() {
		return this.cshCtyCode;
	}

	public void setCshCtyCode(Integer cshCtyCode) {
		this.cshCtyCode = cshCtyCode;
	}

	public Integer getCshRegCode() {
		return this.cshRegCode;
	}

	public void setCshRegCode(Integer cshRegCode) {
		this.cshRegCode = cshRegCode;
	}

	public Integer getCshLocCode() {
		return this.cshLocCode;
	}

	public void setCshLocCode(Integer cshLocCode) {
		this.cshLocCode = cshLocCode;
	}

	public BigDecimal getCshTotAccCode() {
		return this.cshTotAccCode;
	}

	public void setCshTotAccCode(BigDecimal cshTotAccCode) {
		this.cshTotAccCode = cshTotAccCode;
	}

	public Long getCshInsuredId() {
		return this.cshInsuredId;
	}

	public void setCshInsuredId(Long cshInsuredId) {
		this.cshInsuredId = cshInsuredId;
	}

	public Long getCshEndtId() {
		return this.cshEndtId;
	}

	public void setCshEndtId(Long cshEndtId) {
		this.cshEndtId = cshEndtId;
	}

	public Byte getCshCutCode() {
		return this.cshCutCode;
	}

	public void setCshCutCode(Byte cshCutCode) {
		this.cshCutCode = cshCutCode;
	}

	public Integer getCshPreparedBy() {
		return this.cshPreparedBy;
	}

	public void setCshPreparedBy(Integer cshPreparedBy) {
		this.cshPreparedBy = cshPreparedBy;
	}

	public Date getCshPreparedDt() {
		return this.cshPreparedDt;
	}

	public void setCshPreparedDt(Date cshPreparedDt) {
		this.cshPreparedDt = cshPreparedDt;
	}

	public Integer getCshModifiedBy() {
		return this.cshModifiedBy;
	}

	public void setCshModifiedBy(Integer cshModifiedBy) {
		this.cshModifiedBy = cshModifiedBy;
	}

	public Date getCshModifiedDt() {
		return this.cshModifiedDt;
	}

	public void setCshModifiedDt(Date cshModifiedDt) {
		this.cshModifiedDt = cshModifiedDt;
	}

	public String getCshBusiness() {
		return this.cshBusiness;
	}

	public void setCshBusiness(String cshBusiness) {
		this.cshBusiness = cshBusiness;
	}

	public String getCshCustType() {
		return this.cshCustType;
	}

	public void setCshCustType(String cshCustType) {
		this.cshCustType = cshCustType;
	}

	public String getCshPoboxNo() {
		return this.cshPoboxNo;
	}

	public void setCshPoboxNo(String cshPoboxNo) {
		this.cshPoboxNo = cshPoboxNo;
	}

	public Integer getCshTerritory() {
		return this.cshTerritory;
	}

	public void setCshTerritory(Integer cshTerritory) {
		this.cshTerritory = cshTerritory;
	}

	public Integer getCshLaws() {
		return this.cshLaws;
	}

	public void setCshLaws(Integer cshLaws) {
		this.cshLaws = cshLaws;
	}

	public String getCshCompanyName() {
		return this.cshCompanyName;
	}

	public void setCshCompanyName(String cshCompanyName) {
		this.cshCompanyName = cshCompanyName;
	}

	public String getCshCompanyAddr1() {
		return this.cshCompanyAddr1;
	}

	public void setCshCompanyAddr1(String cshCompanyAddr1) {
		this.cshCompanyAddr1 = cshCompanyAddr1;
	}

	public Integer getCshIntAccExecCode() {
		return this.cshIntAccExecCode;
	}

	public void setCshIntAccExecCode(Integer cshIntAccExecCode) {
		this.cshIntAccExecCode = cshIntAccExecCode;
	}

	public Integer getCshExtAccExecCode() {
		return this.cshExtAccExecCode;
	}

	public void setCshExtAccExecCode(Integer cshExtAccExecCode) {
		this.cshExtAccExecCode = cshExtAccExecCode;
	}

	public String getCshLegalForm() {
		return this.cshLegalForm;
	}

	public void setCshLegalForm(String cshLegalForm) {
		this.cshLegalForm = cshLegalForm;
	}

	public Date getCshDtEstablishment() {
		return this.cshDtEstablishment;
	}

	public void setCshDtEstablishment(Date cshDtEstablishment) {
		this.cshDtEstablishment = cshDtEstablishment;
	}

	public String getCshPlaceEstablishment() {
		return this.cshPlaceEstablishment;
	}

	public void setCshPlaceEstablishment(String cshPlaceEstablishment) {
		this.cshPlaceEstablishment = cshPlaceEstablishment;
	}

	public Integer getCshRegulatoryBody() {
		return this.cshRegulatoryBody;
	}

	public void setCshRegulatoryBody(Integer cshRegulatoryBody) {
		this.cshRegulatoryBody = cshRegulatoryBody;
	}

	public Integer getCshExternalAuditor() {
		return this.cshExternalAuditor;
	}

	public void setCshExternalAuditor(Integer cshExternalAuditor) {
		this.cshExternalAuditor = cshExternalAuditor;
	}

	public Short getCshSourceOfCust() {
		return this.cshSourceOfCust;
	}

	public void setCshSourceOfCust(Short cshSourceOfCust) {
		this.cshSourceOfCust = cshSourceOfCust;
	}

	public Long getCshMarketingFee() {
		return this.cshMarketingFee;
	}

	public void setCshMarketingFee(Long cshMarketingFee) {
		this.cshMarketingFee = cshMarketingFee;
	}

	public Date getCshDob() {
		return this.cshDob;
	}

	public void setCshDob(Date cshDob) {
		this.cshDob = cshDob;
	}

	public String getCshPob() {
		return this.cshPob;
	}

	public void setCshPob(String cshPob) {
		this.cshPob = cshPob;
	}

	public String getCshAffinityRefNo() {
		return this.cshAffinityRefNo;
	}

	public void setCshAffinityRefNo(String cshAffinityRefNo) {
		this.cshAffinityRefNo = cshAffinityRefNo;
	}

	public Short getCshEcmCode() {
		return this.cshEcmCode;
	}

	public void setCshEcmCode(Short cshEcmCode) {
		this.cshEcmCode = cshEcmCode;
	}

	public Short getCshIdTypCode() {
		return this.cshIdTypCode;
	}

	public void setCshIdTypCode(Short cshIdTypCode) {
		this.cshIdTypCode = cshIdTypCode;
	}

	public String getCshIdNo() {
		return this.cshIdNo;
	}

	public void setCshIdNo(String cshIdNo) {
		this.cshIdNo = cshIdNo;
	}

	public Integer getCshCity() {
		return this.cshCity;
	}

	public void setCshCity(Integer cshCity) {
		this.cshCity = cshCity;
	}

	public Integer getCshCountry() {
		return this.cshCountry;
	}

	public void setCshCountry(Integer cshCountry) {
		this.cshCountry = cshCountry;
	}

	public Character getCshSex() {
		return this.cshSex;
	}

	public void setCshSex(Character cshSex) {
		this.cshSex = cshSex;
	}

	public Short getCshBusType() {
		return this.cshBusType;
	}

	public void setCshBusType(Short cshBusType) {
		this.cshBusType = cshBusType;
	}

	public String getCshDueDilligence() {
		return this.cshDueDilligence;
	}

	public void setCshDueDilligence(String cshDueDilligence) {
		this.cshDueDilligence = cshDueDilligence;
	}

	public BigDecimal getCshLossRatio() {
		return this.cshLossRatio;
	}

	public void setCshLossRatio(BigDecimal cshLossRatio) {
		this.cshLossRatio = cshLossRatio;
	}

	public BigDecimal getCshLossAmt() {
		return this.cshLossAmt;
	}

	public void setCshLossAmt(BigDecimal cshLossAmt) {
		this.cshLossAmt = cshLossAmt;
	}

	@Override
	public void setEndtId( Long endtId ){
		setCshEndtId( endtId );
	}
	
	@Override
	public void setValidityExpiryDate( Date ved ){
		setCshValidityExpiryDate( ved );
	}
	
	@Override
	public void setValidityStartDate( Date vsd ){
		if( !Utils.isEmpty( getId() ) ) getId().setCshValidityStartDate( vsd );
	}
	
	public Long getCshTurnover() {
		return this.cshTurnover;
	}

	public void setCshTurnover(Long cshTurnover) {
		this.cshTurnover = cshTurnover;
	}

	public Integer getCshNoOfEmp() {
		return this.cshNoOfEmp;
	}

	public void setCshNoOfEmp(Integer cshNoOfEmp) {
		this.cshNoOfEmp = cshNoOfEmp;
	}

	@Override
	public POJOId getPOJOId(){
		return this.getId();
	}
	
	@Override
	public void setPOJOId( POJOId id ){
		setId( (TMasCashCustomerQuoId) id );
	}
	// Home And Travel New Field Start - Removed since the column is moved to TTrnPolicyQuo table
	
	/*public String getCshPromoCode() {
		return cshPromoCode;
	}

	public void setCshPromoCode(String cshPromoCode) {
		this.cshPromoCode = cshPromoCode;
	}*/
	
	
	// Home And Travel New Field End
	
	@Override
	public void setStatus(int status) {
		/* Not applicable.*/
	}

	@Override
	public void setEndtNo(Long endtNo) {
		/* Not applicable.*/
	}

	@Override
	public void setPreparedBy(Integer preparedBy) {
		setCshPreparedBy(preparedBy);
	}

	@Override
	public void setPreparedDt(Date preparedDt) {
		setCshPreparedDt(preparedDt);
		
	}

	@Override
	public void setModifiedBy(Integer modifiedBy) {
		setCshModifiedBy(modifiedBy);
	}

	@Override
	public void setModifiedDt(Date modifiedDt) {
		setCshModifiedDt(modifiedDt);
	}
	public POJOWrapperId getPOJOWrapperId(){
		return this.id;
	}

	@Override
	public void setPOJOWrapperId( POJOWrapperId id ){
		setId((TMasCashCustomerQuoId)id);
		
	}

	@Override
	public Integer getPreparedBy(){
		return getCshPreparedBy();
	}

	@Override
	public Date getPreparedDt(){
		return getCshPreparedDt();
	}

	@Override
	public Integer getModifiedBy(){
		return getCshModifiedBy();
	}

	@Override
	public Date getModifiedDt(){
		return getCshModifiedDt();
	}

	/**
	 * @return the cshRoyaltyTypCode
	 */
	public Integer getCshRoyaltyTypCode(){
		return cshRoyaltyTypCode;
	}

	/**
	 * @param cshRoyaltyTypCode the cshRoyaltyTypCode to set
	 */
	public void setCshRoyaltyTypCode( Integer cshRoyaltyTypCode ){
		this.cshRoyaltyTypCode = cshRoyaltyTypCode;
	}
	
	@Override
	public void setPreparedDate( Date preparedDate ){
		setCshPreparedDt( preparedDate );
	}

	@Override
	public Date getPreparedDate(){
		return getCshPreparedDt();
	}

	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
	public String getCshNationalId() {
		return cshNationalId;
	}

	public void setCshNationalId(String cshNationalId) {
		this.cshNationalId = cshNationalId;
	}

	public Date getCshEmiratesIdExpiryDate() {
		return cshEmiratesIdExpiryDate;
	}
	
	public void setCshEmiratesIdExpiryDate(Date cshEmiratesIdExpiryDate) {
		this.cshEmiratesIdExpiryDate = cshEmiratesIdExpiryDate;
	}

	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends
	
}

