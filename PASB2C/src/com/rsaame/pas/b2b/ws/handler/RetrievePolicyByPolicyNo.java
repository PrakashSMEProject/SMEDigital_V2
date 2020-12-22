package com.rsaame.pas.b2b.ws.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant;
import com.rsaame.pas.b2b.ws.mapper.SBSRetrievePolicyByPolicyNoMapper;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.RetrievePolicyByPolicyNoResponse;
import com.rsaame.pas.b2b.ws.vo.policy.request.Payment;
import com.rsaame.pas.b2b.ws.vo.policy.request.PaymentAmount;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WCVO;

public class RetrievePolicyByPolicyNo {
	
	private final static Logger LOGGER = Logger.getLogger(RetrievePolicyByPolicyNo.class);
	
	public RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNo(PolicyVO policyVO) {
		
		LOGGER.info("Now fetching policy details from DB");
		SBSRetrievePolicyByPolicyNoMapper sbsRetrievePolicyByPolicyNoMapper =new SBSRetrievePolicyByPolicyNoMapper();
		RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNoResponse = new RetrievePolicyByPolicyNoResponse();
		LocationVO locationVO = new LocationVO();
		
		//Initialize all associated objects before hitting the DB.
		initializePolicyVoObjects (policyVO);
		
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session = hibernateTemplate.getSessionFactory().openSession();
	
		Query query = session.createSQLQuery("SELECT * FROM TABLE (CAST(GET_POLICY_DATE(" + policyVO.getPolicyNo()+","+policyVO.getPolicyYear() + ") AS POL_RECORD_OBJ_ARRAY))");
		List<Object[]> resultList = query.list();
		LOGGER.debug("results : " + resultList);
		if(resultList==null || resultList.size()==0) {
			retrievePolicyByPolicyNoResponse.setPolicyId("No Policy Found with policy_id = "+policyVO.getPolicyNo());
			return retrievePolicyByPolicyNoResponse;
		}
		for(Object[] result:resultList){
			//Insured Details
			if(!Utils.isEmpty(result[85]))
				policyVO.getGeneralInfo().getInsured().setName(String.valueOf( result[85] ));
			
			if((!Utils.isEmpty(result[86]) && ((BigDecimal)result[86]).doubleValue() != 0))
				policyVO.getGeneralInfo().getInsured().setTurnover(((BigDecimal)result[86]).longValue());
			
			if(!Utils.isEmpty(result[87]))
				policyVO.getGeneralInfo().getInsured().setVatRegNo(String.valueOf( result[87] ));
			
			if((!Utils.isEmpty(result[88]) && ((BigDecimal)result[88]).doubleValue() != 0))
				policyVO.getGeneralInfo().getInsured().setNoOfEmployees(((BigDecimal)result[88]).intValue());
			
			if(!Utils.isEmpty(result[89]))
				policyVO.getGeneralInfo().getInsured().setBusDescription(String.valueOf( result[89] )); //need to return name not digit (Harshitha)
			
			if(!Utils.isEmpty(result[90]))
				policyVO.getGeneralInfo().getInsured().getAddress().setAddress(String.valueOf( result[90] ));
			
			if((!Utils.isEmpty(result[92]) && ((BigDecimal)result[92]).doubleValue() != 0))
				policyVO.getGeneralInfo().getInsured().setCity(((BigDecimal)result[92]).intValue());
			
			if((!Utils.isEmpty(result[93]) && ((BigDecimal)result[93]).doubleValue() != 0))
				policyVO.getGeneralInfo().getInsured().getAddress().setCountry(((BigDecimal)result[93]).intValue());
			
			if(!Utils.isEmpty(result[94]))
				policyVO.getGeneralInfo().getInsured().getAddress().setPoBox(String.valueOf( result[94] ));
			
			if(!Utils.isEmpty(result[95]))
				policyVO.getGeneralInfo().getInsured().setMobileNo(String.valueOf( result[95] ));
			
			if(!Utils.isEmpty(result[96]))
				policyVO.getGeneralInfo().getInsured().setPhoneNo(String.valueOf( result[96] ));
			
			if(!Utils.isEmpty(result[97]))
				policyVO.getGeneralInfo().getInsured().setEmailId(String.valueOf( result[97] ));
			
			if((!Utils.isEmpty(result[98]) && ((BigDecimal)result[98]).doubleValue() != 0))
				policyVO.getScheme().setPolicyType(((BigDecimal)result[98]).toString());
			
			if((!Utils.isEmpty(result[159]) && ((BigDecimal)result[159]).doubleValue() != 0))
				policyVO.setStatus(((BigDecimal)result[159]).intValue());
			
			//policySchedule Details
			if((!Utils.isEmpty(result[105]) && ((BigDecimal)result[105]).doubleValue() != 0))
				policyVO.setPolicyTerm(((BigDecimal)result[105]).intValue());
			try{
				Date issueDate = (Date) result[99];
				LOGGER.info("For Policy Processing Date is " + issueDate );
				policyVO.setProcessedDate(issueDate);
				}
			catch (Exception e) {
				LOGGER.info("Exception in parsing policy Processing date");
				e.printStackTrace();
			}
			
			try{
				Date effDate = (Date) result[100];
				LOGGER.info("For schemeVO Policy Effective Date is " + effDate );
				policyVO.getScheme().setEffDate(effDate);
				}
			catch (Exception e) {
				LOGGER.info("Exception in parsing effective date");
				e.printStackTrace();
			}
			
			try{
				Date expDate = (Date) result[101];
				policyVO.getScheme().setExpiryDate(expDate);
				LOGGER.info("For schemeVO Policy Expiry Date is " + expDate );

				}
			catch (Exception e) {
				LOGGER.info("Exception in parsing effective date");
				e.printStackTrace();
			}
			
			//Mapping Total Premium
			if((!Utils.isEmpty(result[158]) && ((BigDecimal)result[158]).doubleValue() != 0))
				policyVO.getPremiumVO().setPremiumAmt(((BigDecimal)result[158]).doubleValue());
			
			//Mapping VAT
			if((!Utils.isEmpty(result[157]) && ((BigDecimal)result[157]).doubleValue() != 0))
				policyVO.setPolVatRate(((BigDecimal)result[157]));
			
			SectionVO sectionVo = new SectionVO(RiskGroupingLevel.LOCATION);
			java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
			
			if(!Utils.isEmpty(result[65]))
				locationVO.setFreeZone(String.valueOf(result[65]));
			else if(!Utils.isEmpty(result[114]))
				locationVO.setFreeZone(String.valueOf(result[114]));
			
			if(!Utils.isEmpty(result[33]))
				locationVO.setDirectorate(Integer.parseInt(result[33].toString()));
			
			else if(!Utils.isEmpty(result[189]))
				locationVO.setDirectorate(Integer.parseInt(result[189].toString()));
			
			if((!Utils.isEmpty(result[72]) && ((BigDecimal)result[72]).doubleValue() != 0) || (!Utils.isEmpty(result[81]) && ((BigDecimal)result[81]).doubleValue() != 0) || (!Utils.isEmpty(result[78]) && ((BigDecimal)result[78]).doubleValue() != 0)|| (!Utils.isEmpty(result[75]) && ((BigDecimal)result[75]).doubleValue() != 0)) { //PAR
				LOGGER.info("Request mapping for PAR section with Premium : " + ((BigDecimal)result[72]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("1"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getPARVO(result));
			}
			if((!Utils.isEmpty(result[116]) && ((BigDecimal)result[116]).doubleValue() != 0)) { //PL
				LOGGER.info("Request mapping for PL section with Premium : " + ((BigDecimal)result[116]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("2"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getPLVO(result,policyVO));
			}
			if((!Utils.isEmpty(result[130]) && ((BigDecimal)result[130]).doubleValue() != 0) || (!Utils.isEmpty((Clob) result[173]))) { //WC
				LOGGER.info("Request mapping for WC section with Premium :");
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("3"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getWCVO(result));
			}
			if((!Utils.isEmpty(result[134]) && ((BigDecimal)result[134]).doubleValue() != 0)) { //BI
				LOGGER.info("Request mapping for BI section with Premium : " + ((BigDecimal)result[134]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("4"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getBIVO(result));
			}
			if((!Utils.isEmpty(result[137]) && ((BigDecimal)result[137]).doubleValue() != 0)) { //MB
				LOGGER.info("Request mapping for MB section with Premium : " + ((BigDecimal)result[137]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("5"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getMBVO(result));
			}
			if((!Utils.isEmpty(result[143]) && ((BigDecimal)result[143]).doubleValue() != 0)) { //EE
				LOGGER.info("Request mapping for EE section with Premium : " + ((BigDecimal)result[143]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("6"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getEEVO(result));
			}
			if((!Utils.isEmpty(result[152]) && ((BigDecimal)result[152]).doubleValue() != 0)) { //GPA
				LOGGER.info("Request mapping for GPA section with Premium : " + ((BigDecimal)result[152]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("7"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getGPAVO(result));
			}
			if((!Utils.isEmpty(result[140]) && ((BigDecimal)result[140]).doubleValue() != 0)) { //DOS
				LOGGER.info("Request mapping for DOS section with Premium : " + ((BigDecimal)result[140]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("8"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getDOSVO(result));
			}
			if((!Utils.isEmpty(result[150]) && ((BigDecimal)result[150]).doubleValue() != 0)) { //Travel Baggage
				LOGGER.info("Request mapping for Travel Baggage section with Premium : " + ((BigDecimal)result[149]).doubleValue());
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("9"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getTBVO(result));
			}
			if((!Utils.isEmpty(result[175])) && (((BigDecimal)result[175]).doubleValue() != 0)) { //FG
				LOGGER.info("Request mapping for FG section");
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("10"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getFidelityVO(result));
			} 
			if((!Utils.isEmpty(result[169])) || (!Utils.isEmpty(result[170])) || (!Utils.isEmpty(result[171])) || (!Utils.isEmpty(result[172]))) {
				LOGGER.info("Request mapping for Money section");
				LocationVO locationVO1 = new LocationVO();
				locationVO1.setRiskGroupId("11"); //analyze and set proper value to this. this changes for each section
				riskGroupDetails.put(locationVO1, getMoneyVO(result));
			}
			
			sectionVo.setRiskGroupDetails(riskGroupDetails);
			policyVO.getRiskDetails().add(sectionVo);
			
			if(!Utils.isEmpty(result[176]) || !Utils.isEmpty(result[177])){
				updatePaymentDetails(result, retrievePolicyByPolicyNoResponse);
			}
		}
		sbsRetrievePolicyByPolicyNoMapper.mapRequestToVO(policyVO, retrievePolicyByPolicyNoResponse, locationVO);
		return retrievePolicyByPolicyNoResponse;
	}
	
	private void updatePaymentDetails(Object[] result, RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNoResponse) {
		
		if(Utils.isEmpty(retrievePolicyByPolicyNoResponse.getPayment())){
			retrievePolicyByPolicyNoResponse.setPayment(new Payment());
		}
		
		if((String.valueOf(result[177])).equalsIgnoreCase("CARD")){
			
			if(Utils.isEmpty(retrievePolicyByPolicyNoResponse.getPayment().getPaymentAmount())){
				retrievePolicyByPolicyNoResponse.getPayment().setPaymentAmount(new PaymentAmount());
			}
			
			if((!Utils.isEmpty(result[176]) && ((BigDecimal)result[176]).doubleValue() != 0)){
				retrievePolicyByPolicyNoResponse.getPayment().getPaymentAmount().setAmount(((BigDecimal)result[176]).doubleValue());
			}
			
			if(!Utils.isEmpty(result[177])){
				retrievePolicyByPolicyNoResponse.getPayment().setPaymentMode(String.valueOf(result[177]));
			}
			
			if(!Utils.isEmpty(result[178])){
				retrievePolicyByPolicyNoResponse.getPayment().setPaymentRefNumber(String.valueOf(result[178]));
			}
			
			if(!Utils.isEmpty(result[179])){
				retrievePolicyByPolicyNoResponse.getPayment().setCardNo(String.valueOf(result[179]));
			}
			
			if(!Utils.isEmpty(result[180])){
				retrievePolicyByPolicyNoResponse.getPayment().setCardType(String.valueOf(result[180]));
			}
			
			if(!Utils.isEmpty(result[181])){
				retrievePolicyByPolicyNoResponse.getPayment().setMerchantRefNo(String.valueOf(result[181]));
			}
			
			if(!Utils.isEmpty(result[182])){
				retrievePolicyByPolicyNoResponse.getPayment().setPaymtTransactionDate(String.valueOf(result[182]));
			}
			
			if(!Utils.isEmpty(result[183])){
				retrievePolicyByPolicyNoResponse.getPayment().setCustomerName(String.valueOf(result[183]));
			}
		}else{
			retrievePolicyByPolicyNoResponse.getPayment().setPaymentMode(String.valueOf(result[177]));
		}
		
				
	}

	private RiskGroupDetails getMoneyVO(Object[] result) {
		MoneyVO moneyVO = new MoneyVO();
		moneyVO.setContentsList(new ArrayList<>());
		List<SumInsuredVO> sumInsuredVOs = new ArrayList<>();

		// Estimated Annual Carryings  Premium
		SumInsuredVO sumInsuredVO1 = new SumInsuredVO();
		if((!Utils.isEmpty(result[163]) && ((BigDecimal)result[163]).doubleValue() != 0)){
			sumInsuredVO1.setSumInsured(((BigDecimal)result[163]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO1);
		}
			
		// Cash In Transit (Single Transit Limit)
		SumInsuredVO sumInsuredVO2 = new SumInsuredVO();
		if((!Utils.isEmpty(result[164]) && ((BigDecimal)result[164]).doubleValue() != 0)){
			sumInsuredVO2.setSumInsured(((BigDecimal)result[164]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO2);
		}
			
		//Cash locked in safe during business hour
		SumInsuredVO sumInsuredVO3 = new SumInsuredVO();
		if((!Utils.isEmpty(result[165]) && ((BigDecimal)result[165]).doubleValue() != 0)){
			sumInsuredVO3.setSumInsured(((BigDecimal)result[165]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO3);
		}
			
		//Cash locked in drawer during business hour
		SumInsuredVO sumInsuredVO4 = new SumInsuredVO();
		if((!Utils.isEmpty(result[166]) && ((BigDecimal)result[166]).doubleValue() != 0)){
			sumInsuredVO4.setSumInsured(((BigDecimal)result[166]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO4);
		}
		
		//Cash locked in drawer during business hour
		SumInsuredVO sumInsuredVO5 = new SumInsuredVO();
		if((!Utils.isEmpty(result[167]) && ((BigDecimal)result[167]).doubleValue() != 0)){
			sumInsuredVO5.setSumInsured(((BigDecimal)result[167]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO5);
		}
		
		//Cash locked in drawer during business hour
		SumInsuredVO sumInsuredVO6 = new SumInsuredVO();
		if((!Utils.isEmpty(result[168]) && ((BigDecimal)result[168]).doubleValue() != 0)){
			sumInsuredVO6.setSumInsured(((BigDecimal)result[168]).doubleValue());
			sumInsuredVOs.add(sumInsuredVO6);
		}
		
		PremiumVO premiumVO = new PremiumVO();
		Double sumPremiumAmt = 0.0;
		String jsonList =null;
		if(!Utils.isEmpty((Clob) result[172])){
			try {
				jsonList = WSAppUtils.getStringFromClob((Clob) result[172]);
				LOGGER.info("CLOB in String Formate :_1"+jsonList);
				String[] splitJsonList = jsonList.split(com.Constant.CONST_OR_OR_END); 
				
				if(!Utils.isEmpty(splitJsonList[3])){
					String resPremiumInd = splitJsonList[3];
					String[] ressplitData = resPremiumInd.split(":|/");
					String resPremium = ressplitData[1];
					sumPremiumAmt += Double.parseDouble(resPremium.trim());
				}
				
				if(!Utils.isEmpty(splitJsonList[9])){
					SumInsuredVO sumInsuredVO7 = new SumInsuredVO();
					String resAmoutInd = splitJsonList[9];
					String[] resSplitAmt = resAmoutInd.split(":|/");
					String totalAnnualWage1 = resSplitAmt[1];
					sumInsuredVO7.setSumInsured(Double.parseDouble(totalAnnualWage1.trim()));
					sumInsuredVOs.add(sumInsuredVO7);
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Estimated Annual Carryings  Premium
		if((!Utils.isEmpty(result[169]) && ((BigDecimal)result[169]).doubleValue() != 0))
			sumPremiumAmt += ((BigDecimal)result[169]).doubleValue();
		//Cash in premises after business hours In locked safe 
		if((!Utils.isEmpty(result[170]) && ((BigDecimal)result[170]).doubleValue() != 0))
			sumPremiumAmt += ((BigDecimal)result[170]).doubleValue();
		//Cash in premises after business hours In locked drawer
		if((!Utils.isEmpty(result[171]) && ((BigDecimal)result[171]).doubleValue() != 0))
			sumPremiumAmt += ((BigDecimal)result[171]).doubleValue();

		premiumVO.setPremiumAmt(sumPremiumAmt);
		moneyVO.setPremium(premiumVO);
		
		// Cash In Transit for Content (Single Transit Limit)
		Contents cashInTransit = new Contents();
		if((!Utils.isEmpty(result[164]) && ((BigDecimal)result[164]).doubleValue() != 0))
			cashInTransit.setCover(((BigDecimal)result[164]));
		//BigDecimal bd =new BigDecimal(666);
		//cashInTransit.setCover(bd);
		cashInTransit.setRiskType(1);
		cashInTransit.setRiskCat(2);
		moneyVO.getContentsList().add(cashInTransit);
		
		moneyVO.setSumInsuredDets(sumInsuredVOs);
		return moneyVO;
	}

	private RiskGroupDetails getFidelityVO(Object[] result) {
		float aggBasePremium =0.0F;
		double namePremium =0.0;
		double unNamePremium =0.0;
		Double aggregateLimit =0.0;
		FidelityVO fidelityVO = new FidelityVO();
		//FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO = new FidelityUnnammedEmployeeVO();
		com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityNammedEmployeeDetailsVO.class);
		com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeeVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityUnnammedEmployeeVO.class);
		//double nameAggLimit =0.0;
		//double unNameAggLimit = 0.0;
		try {
			//fidelity Named
			String fgNameEmpDetails =null;
			String deductible = "0.0";
			if(!Utils.isEmpty((Clob) result[132])){
				fgNameEmpDetails = WSAppUtils.getStringFromClob((Clob) result[132]);
				LOGGER.info("CLOB in String Formate :_2"+fgNameEmpDetails);
				String[] fgNameEmpSplitList = fgNameEmpDetails.split(com.Constant.CONST_OR_OR_END); 
				//m1043116 adding fidelity named person details
				String[] fgNameEmpSplitList1 = fgNameEmpDetails.split(com.Constant.CONST_ITEMNUMBER);
				for(int i=0;i<fgNameEmpSplitList1.length;i++)
				{
				System.out.println(fgNameEmpSplitList1[i]);
				}
				for(int i=0;i<fgNameEmpSplitList1.length-1;i++)
				{
					FidelityNammedEmployeeDetailsVO employeeDetailsVO = new FidelityNammedEmployeeDetailsVO();
					String empName=null;
					String empDesignation=null;
				    Double limitPerPerson=0.0;
					Integer empType=null;
					String[] details = (fgNameEmpSplitList1[i+1]).split(com.Constant.CONST_OR_OR_END);
					for(int j=0;j<4;j++) {
						 String[] details1 = details[j+1].split(":");
						 System.out.println(details1[1].replaceAll("\"", ""));
						 if(j+1==1)
						 {
							 System.out.println(details[1]);
							 String name=(details1[1]).replaceAll(" ", "");
							 System.out.println(name);
							 empName = name.replaceAll("\"", "");
						 }
						 if(j+1==2)
						 {
							 details[1]=(details1[1]).replaceAll(" ", "");
							 String type=StringUtils.strip(details[1], "\"");
							 empType = Integer.parseInt(type);
							
							 
						 }
						 if(j+1==3)
						 {
							 empDesignation = details1[1].replaceAll("\"", "");
						 }
						 if(j+1==4)
						 {
							 limitPerPerson = Double.parseDouble(details1[1].replaceAll("\"", ""));
						 }
					 }
					String[] details_0 = (fgNameEmpSplitList1[i]).split(com.Constant.CONST_OR_OR_END);
					for(int j=0;j<4;j++) {
  						 String[] details1 = details_0[j+1].split(":");
						 System.out.println(details1[0].replaceAll("\"", ""));
						
						 if(j+1==4) {
							 deductible = details1[1];
							 break;
						 }
						 
					}
					
					
					employeeDetailsVO.setEmpName(empName);;
					employeeDetailsVO.setEmpDesignation(empDesignation);;
					employeeDetailsVO.setEmpType(empType);
					employeeDetailsVO.setLimitPerPerson(limitPerPerson);
					fidelityNammedEmployeeDetailsVOs.add(employeeDetailsVO);
					
					
				}
				/*if(!Utils.isEmpty(fgNameEmpSplitList[3])){
					String fgEmpAggLimit = fgNameEmpSplitList[3];
					String[] fgNameEmpAggLimitSplit = fgEmpAggLimit.split(":|/");
					String aggNameLimit = (fgNameEmpAggLimitSplit[1]).replace("\"", "");
					nameAggLimit = Double.parseDouble(aggNameLimit);
				}*/
				
				if(!Utils.isEmpty(fgNameEmpSplitList[5])){
					String fgEmpName = fgNameEmpSplitList[5];
					String[] fgEmpNameSplit = fgEmpName.split(":|/");
					String npremium = fgEmpNameSplit[1];
					namePremium =Double.parseDouble(npremium);
				}
			}
			
			//fidelity unNamed details
			String fgUnNameEmpDetails = null;
			if(!Utils.isEmpty((Clob) result[133])){
				fgUnNameEmpDetails = WSAppUtils.getStringFromClob((Clob) result[133]);
				LOGGER.info("CLOB in String Formate :_3"+fgUnNameEmpDetails);
				String[] fgUnNameEmpSplitList = fgUnNameEmpDetails.split(com.Constant.CONST_OR_OR_END);
				String[] fgUnNameEmpSplitList1 = fgUnNameEmpDetails.split(com.Constant.CONST_ITEMNUMBER);
				for(int i=0;i<fgUnNameEmpSplitList1.length;i++)
				{
				System.out.println(fgUnNameEmpSplitList1[i]);
				}
				for(int i=0;i<fgUnNameEmpSplitList1.length-1;i++)
				{
					FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO = new FidelityUnnammedEmployeeVO();
					Integer totalNumberOfEmployee=0;
					Double limitPerPerson=0.0;
					Integer empType=0;
					String[] details = (fgUnNameEmpSplitList1[i+1]).split(com.Constant.CONST_OR_OR_END);
					for(int j=0;j<3;j++) {
						 String[] details1 = details[j+1].split(":");
						 System.out.println(details1[1].replaceAll("\"", ""));
						 if(j+1==1)
						 {
							
							details[1]=(details1[1]).replaceAll(" ", "");
							 String type=StringUtils.strip(details[1], "\"");
							totalNumberOfEmployee = Integer.parseInt(type);
						 }
						 if(j+1==2)
						 {
							 details[1]=(details1[1]).replaceAll(" ", "");
							 String type=StringUtils.strip(details[1], "\"");
							 empType = Integer.parseInt(type);
						 }
						 if(j+1==3)
						 {
							 
							 details[1]=(details1[1]).replaceAll(" ", "");
							 String type=StringUtils.strip(details[1], "\"");
							 limitPerPerson = Double.parseDouble(type);
							
							 
						 }
						
					 }
					
					String[] details_0 = (fgUnNameEmpSplitList1[0]).split(com.Constant.CONST_OR_OR_END);
					for(int j=0;j<4;j++) {
						 String[] details1 = details_0[j+1].split(":");
						 System.out.println(details1[0].replaceAll("\"", ""));
						 if(j+1==4)
						 {
							 deductible = details1[1];
						 }
					}
					fidelityUnnammedEmployeeVO.setTotalNumberOfEmployee(totalNumberOfEmployee);
					fidelityUnnammedEmployeeVO.setLimitPerPerson(limitPerPerson);
					fidelityUnnammedEmployeeVO.setEmpType(empType);
					fidelityUnnammedEmployeeVOs.add(fidelityUnnammedEmployeeVO);
					//m1043116-adding fidelity named person details
					
				}
				
				
				/*if(!Utils.isEmpty(fgUnNameEmpSplitList[3])){
					String fgUnNameEmpAggLimit = fgUnNameEmpSplitList[3];
					String[] fgUnNameEmpAggLimitSplit = fgUnNameEmpAggLimit.split(":|/");
					String aggUnNameLimit = (fgUnNameEmpAggLimitSplit[1]).replace("\"", "");;
					unNameAggLimit = Double.parseDouble(aggUnNameLimit);
				}*/
				
				if(!Utils.isEmpty(fgUnNameEmpSplitList[5])){
					String fgUnNameEmp = fgUnNameEmpSplitList[5];
					String[] fgEmpUnNameSplit = fgUnNameEmp.split(":|/");
					String unNamepremium = fgEmpUnNameSplit[1];
					unNamePremium =Double.parseDouble(unNamepremium);
				}
				
			}
			
			fidelityVO.setDeductible(Double.parseDouble(deductible));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//FidelityVO fidelityVO = new FidelityVO();
		//com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityNammedEmployeeDetailsVO.class);
		//com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeeVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityUnnammedEmployeeVO.class);
		
		//fidelity Named Employee Details
		FidelityNammedEmployeeDetailsVO employeeDetailsVO = new FidelityNammedEmployeeDetailsVO();
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(namePremium);
		employeeDetailsVO.setPremium(premiumVO);
		fidelityNammedEmployeeDetailsVOs.add(employeeDetailsVO);
		
		//fidelity UnNamed Employee Details
		FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO = new FidelityUnnammedEmployeeVO();
		PremiumVO premiumVO1 = new PremiumVO();
		premiumVO1.setPremiumAmt(unNamePremium);
		fidelityUnnammedEmployeeVO.setPremium(premiumVO1);
		fidelityUnnammedEmployeeVOs.add(fidelityUnnammedEmployeeVO);
		
		fidelityVO.setFidelityEmployeeDetails(fidelityNammedEmployeeDetailsVOs);
		fidelityVO.setUnnammedEmployeeDetails(fidelityUnnammedEmployeeVOs);
		
		//AggregateLimit
		if((!Utils.isEmpty(result[174]) && ((BigDecimal)result[174]).doubleValue() != 0)){
			aggregateLimit = ((BigDecimal)result[174]).doubleValue();
			fidelityVO.setAggregateLimit(aggregateLimit*2);
		}
		
		//AggregateBasePremium
		if((!Utils.isEmpty(result[175]) && ((BigDecimal)result[175]).floatValue() != 0)){
			aggBasePremium = ((BigDecimal)result[175]).floatValue();
			fidelityVO.setFidAggregateBasePremium(aggBasePremium);
		}
		
		return fidelityVO;
	}

	private RiskGroupDetails getTBVO(Object[] result) {
		
		TravelBaggageVO tbVO = new TravelBaggageVO();
		tbVO.setSumInsured(((BigDecimal)result[150]).doubleValue());
		
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[149]).doubleValue());
		tbVO.setPremium(premiumVO);
		String travellerDetails=null;
		try
		{
		if(!Utils.isEmpty((Clob) result[184])){
			travellerDetails = WSAppUtils.getStringFromClob((Clob) result[184]);
			List<TravellingEmployeeVO> travellingEmpDets =  new com.mindtree.ruc.cmn.utils.List<TravellingEmployeeVO>(TravellingEmployeeVO.class);
			 //travellingEmployeeVO.setName(name);
		
			LOGGER.info("CLOB in String Formate :_4"+travellerDetails);
			String[] trDetailsSplitList = travellerDetails.split(com.Constant.CONST_ITEMNUMBER);
			for(int i=0;i<trDetailsSplitList.length;i++)
			{
			System.out.println(trDetailsSplitList[i]);
			}
			
			for(int i=0;i<trDetailsSplitList.length-1;i++)
			{
				 TravellingEmployeeVO travellingEmployeeVO = new TravellingEmployeeVO();
				 SumInsuredVO sumInsuredVO = new SumInsuredVO();
				 String name="";
				 String dob="";
				 Double limit=null;
				 String[] details = (trDetailsSplitList[i+1]).split(com.Constant.CONST_OR_OR_END);
				 for(int j=0;j<3;j++) {
					 String[] details1 = details[j+1].split(":");
					 System.out.println(details1[1].replaceAll("\"", ""));
					 if(j+1==1)
					 {
						String traveller=(details1[1]).replaceAll(" ", "");
						 name = traveller.replaceAll("\"", "");
					 }
					 if(j+1==2)
					 {
						 dob = details1[1].replaceAll("\"", "");
					 }
					 if(j+1==3)
					 {
						 limit = Double.parseDouble(details1[1].replaceAll("\"", ""));
					 }
				 }
				travellingEmployeeVO.setName(name);
				travellingEmployeeVO.setDateOfBirth(dob);
				sumInsuredVO.setSumInsured(limit);
				
				if(!Utils.isEmpty(result[151])) {
					sumInsuredVO.setDeductible(((BigDecimal)result[151]).doubleValue());
				}
				
				travellingEmployeeVO.setSumInsuredDtl(sumInsuredVO);
				travellingEmpDets.add(travellingEmployeeVO);
			}
			tbVO.setTravellingEmpDets(travellingEmpDets);
			System.out.println(travellingEmpDets.toString());
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tbVO;
	}

	private RiskGroupDetails getDOSVO(Object[] result) {
		DeteriorationOfStockVO dosVO = new DeteriorationOfStockVO();
		List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsList = new com.mindtree.ruc.cmn.utils.List<DeteriorationOfStockDetailsVO>(DeteriorationOfStockDetailsVO.class);
		
		DeteriorationOfStockDetailsVO frozenfoodDOSVO = new DeteriorationOfStockDetailsVO();
		SumInsuredVO forzenfoodSumInsured = new SumInsuredVO();
		forzenfoodSumInsured.setSumInsured(((BigDecimal)result[141]).doubleValue());
		
		if(!Utils.isEmpty(result[142])) {
			forzenfoodSumInsured.setDeductible(((BigDecimal)result[142]).doubleValue());
		}
		frozenfoodDOSVO.setSumInsuredDetails(forzenfoodSumInsured);
		
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[140]).doubleValue());
		frozenfoodDOSVO.setPremium(premiumVO);
		
		deteriorationOfStockDetailsList.add(frozenfoodDOSVO);
		dosVO.setDeteriorationOfStockDetails(deteriorationOfStockDetailsList);		
		return dosVO;
	}

	private RiskGroupDetails getGPAVO(Object[] result) {
		GroupPersonalAccidentVO groupPersonalAccidentVO = new GroupPersonalAccidentVO();

		Double aggregateLimit = new Double(0.0);
		double sumInsured =0.0;
		java.util.List<GPANammedEmpVO> gpaNammedEmpVO = new com.mindtree.ruc.cmn.utils.List<GPANammedEmpVO>(GPANammedEmpVO.class);
		GPANammedEmpVO nammedEmpVO = new GPANammedEmpVO();
		SumInsuredVO  sumInsuredDetails = new SumInsuredVO();
		sumInsuredDetails.setSumInsured(((BigDecimal)result[153]).doubleValue());
		sumInsured=((BigDecimal)result[153]).doubleValue();
		nammedEmpVO.setSumInsuredDetails(sumInsuredDetails);		
		gpaNammedEmpVO.add(nammedEmpVO);

		aggregateLimit = sumInsured /2;
		groupPersonalAccidentVO.setGpaNammedEmpVO(gpaNammedEmpVO);
		groupPersonalAccidentVO.setSumInsured(sumInsured);
		groupPersonalAccidentVO.setAggregateLimit(aggregateLimit);
		
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[152]).doubleValue());
		groupPersonalAccidentVO.setPremium(premiumVO);
		
		if(!Utils.isEmpty(result[154])) {
			groupPersonalAccidentVO.setGpaDeductible(((BigDecimal)result[154]).doubleValue());
		}
				String gpaDetails=null;
				try
				{
				if(!Utils.isEmpty((Clob) result[185])){
					gpaDetails = WSAppUtils.getStringFromClob((Clob) result[185]);

					LOGGER.info("CLOB in String Formate :_5"+gpaDetails);
					String[] gpaDetailsSplitList = gpaDetails.split(com.Constant.CONST_ITEMNUMBER);
					for(int i=0;i<gpaDetailsSplitList.length;i++)
					{
					System.out.println(gpaDetailsSplitList[i]);
					}
					
					for(int i=0;i<gpaDetailsSplitList.length-1;i++)
					{
						GPANammedEmpVO nammedEmpVO1 = new GPANammedEmpVO();
						SumInsuredVO sumInsuredVO = new SumInsuredVO();
						String nameOfEmployee=null;
						Integer employeeType=0;
						char nammedEmpGender=' ';
						String nammedEmpDob=null;
						String nammedEmpDesignation=null;
						Double nammedEmpAnnualSalary=0.0;
						Double sumInsured1=null;
						 String[] details = (gpaDetailsSplitList[i+1]).split(com.Constant.CONST_OR_OR_END);
						 for(int j=0;j<8;j++) {
							 String[] details1 = details[j+1].split(":");
							 System.out.println(details1[1].replaceAll("\"", ""));
							 if(j+1==1)
							 {
								String name=(details1[1]).replaceAll(" ", "");
								 nameOfEmployee = name.replaceAll("\"", "");
							 }
							 if(j+1==3)
							 {
								 details[1]=(details1[1]).replaceAll(" ", "");
								 String type=StringUtils.strip(details[1], "\"");
								 employeeType = Integer.parseInt(type);
							 }
							 if(j+1==4)
							 {
								 details[1]=(details1[1]).replaceAll(" ", "");
								 String type=StringUtils.strip(details[1], "\"");
								 Integer gender = Integer.parseInt(type);
								 if(gender==2)
								 {
									 nammedEmpGender='M'; 
								 }
								 else
								 {
									 nammedEmpGender='F'; 
								 }
							 }
							 if(j+1==5)
							 {
								 nammedEmpDob =details1[1].replaceAll("\"", "");
							 }
							 if(j+1==6)
							 {
								 //nammedEmpDesignation= 
							 }
							 if(j+1==7)
							 {
								 nammedEmpAnnualSalary=Double.parseDouble(details1[1].replaceAll("\"", ""));
							 }
							 if(j+1==8)
							 {
								 sumInsured1 =Double.parseDouble(details1[1].replaceAll("\"", ""));
							 }
						 }
						 nammedEmpVO1.setNameOfEmployee(nameOfEmployee);
						 nammedEmpVO1.setEmployeeType(employeeType);
						 nammedEmpVO1.setNamedEmpGender(nammedEmpGender);
						 nammedEmpVO1.setNammedEmpDob(nammedEmpDob);
						 nammedEmpVO1.setNammedEmpAnnualSalary(nammedEmpAnnualSalary);
						 //nammedEmpVO1.set
						 sumInsuredVO.setSumInsured(sumInsured1);
						 nammedEmpVO1.setSumInsuredDetails(sumInsuredVO);
						 gpaNammedEmpVO.add(nammedEmpVO1);
						 groupPersonalAccidentVO.setGpaNammedEmpVO(gpaNammedEmpVO);
						 
					}
					 }
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return groupPersonalAccidentVO;
	}

	private RiskGroupDetails getEEVO(Object[] result) {
		EEVO eeVO = new EEVO();
		java.util.List<EquipmentVO> equipmentDtlsList = new com.mindtree.ruc.cmn.utils.List<EquipmentVO>(EquipmentVO.class);
		//For potable equipment -- begin 
		
		if(!Utils.isEmpty(result[143])){
			EquipmentVO portableEquipVO = new EquipmentVO();
			SumInsuredVO sumInsuredDetailsPortable = new SumInsuredVO();
			sumInsuredDetailsPortable.setSumInsured(((BigDecimal)result[144]).doubleValue());
			portableEquipVO.setEquipmentType("4"); //4 for portable
			
			if(!Utils.isEmpty(result[145])) {
				sumInsuredDetailsPortable.setDeductible(((BigDecimal)result[145]).doubleValue());
			}
			
			portableEquipVO.setSumInsuredDetails(sumInsuredDetailsPortable);
			
			PremiumVO premiumVO = new PremiumVO();
			premiumVO.setPremiumAmt(((BigDecimal)result[143]).doubleValue());
			portableEquipVO.setPremium(premiumVO);
			equipmentDtlsList.add(portableEquipVO);
		}
		
		//For potable equipment -- end
		
		//For Non potable equipment -- begin 
		
		if(!Utils.isEmpty(result[146])){
			EquipmentVO nonportableEquipVO = new EquipmentVO();
			SumInsuredVO sumInsuredDetailsNonPortable = new SumInsuredVO();
			sumInsuredDetailsNonPortable.setSumInsured(((BigDecimal)result[147]).doubleValue());
			nonportableEquipVO.setEquipmentType("6"); //4 for portable
			
			if(!Utils.isEmpty(result[148])) {
				sumInsuredDetailsNonPortable.setDeductible(((BigDecimal)result[148]).doubleValue());
			}
			
			nonportableEquipVO.setSumInsuredDetails(sumInsuredDetailsNonPortable);
			
			PremiumVO premiumVO1 = new PremiumVO();
			premiumVO1.setPremiumAmt(((BigDecimal)result[146]).doubleValue());
			nonportableEquipVO.setPremium(premiumVO1);
			equipmentDtlsList.add(nonportableEquipVO);
		}
		//For Non potable equipment -- end
		
		eeVO.setEquipmentDtls(equipmentDtlsList);
		
		return eeVO;
	}

	private RiskGroupDetails getMBVO(Object[] result) {
		MBVO mbVO = new MBVO();
		List<MachineDetailsVO> machineryDetailsList = new com.mindtree.ruc.cmn.utils.List<MachineDetailsVO>(MachineDetailsVO.class);
		MachineDetailsVO machineryOther = new MachineDetailsVO();
		machineryOther.setMachineryType(3);
		
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[137]).doubleValue());
		machineryOther.setPremium(premiumVO);
		
		SumInsuredVO otherMachinerySumInsuredVO = new SumInsuredVO();
		otherMachinerySumInsuredVO.setSumInsured(((BigDecimal)result[138]).doubleValue());
		if(!Utils.isEmpty(result[139])) {
			otherMachinerySumInsuredVO.setDeductible(((BigDecimal)result[139]).doubleValue());
		}
		
		machineryOther.setSumInsuredVO(otherMachinerySumInsuredVO);
		machineryDetailsList.add(machineryOther);
		mbVO.setMachineryDetails(machineryDetailsList);
		
		return mbVO;
	}

	private RiskGroupDetails getBIVO(Object[] result) {
		BIVO biVO= new BIVO();
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[134]).doubleValue());
		biVO.setPremium(premiumVO);
		
		Double estimatedGrossIncome = new Double(0);
		Double rentRecievable = new Double(0);
		Double rentAndIcowLimit = new Double(0);
		
		if(!Utils.isEmpty(result[136])) {
			biVO.setDeductible(((BigDecimal)result[136]).longValue());
		}
		
		if(result[160]!=null) {
			rentAndIcowLimit = ((BigDecimal)result[160]).doubleValue();
		}
		if(result[161]!=null) {
			rentRecievable = ((BigDecimal)result[161]).doubleValue();
		}
		if(result[162]!=null) {
			estimatedGrossIncome = ((BigDecimal)result[162]).doubleValue();
		}
		
		biVO.setWorkingLimit(rentAndIcowLimit);
		biVO.setEstimatedGrossIncome(estimatedGrossIncome); 
		biVO.setRentRecievable(rentRecievable);
		biVO.setSumInsured(rentAndIcowLimit + estimatedGrossIncome + rentRecievable);
		return biVO;
	}

	private RiskGroupDetails getWCVO(Object[] result) {
		WCVO wcVO = new WCVO();
		com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO> empTypeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(EmpTypeDetailsVO.class);
		
		if(!Utils.isEmpty(result[131])){
			EmpTypeDetailsVO empTypeDetailsVO = new EmpTypeDetailsVO();
			empTypeDetailsVO.setWageroll(((BigDecimal)result[131]).doubleValue());
			empTypeDetailsVOs.add(empTypeDetailsVO);
		}
		
		String WorkmenCompList = null;
		if(!Utils.isEmpty((Clob) result[173])){
			try {
				WorkmenCompList = WSAppUtils.getStringFromClob((Clob) result[173]);
				LOGGER.info("CLOB in String Formate :_6"+WorkmenCompList);
				String[] fgNameEmpSplitList = WorkmenCompList.split(com.Constant.CONST_OR_OR_END);
				String totalAnnualWage1 = null;
				String empLiabilityL1 = null;
				String typeofe1 = null;
				
				if(fgNameEmpSplitList.length > 6){
					if(!Utils.isEmpty(fgNameEmpSplitList[4])){
						String typeofemp1 = fgNameEmpSplitList[4];
						String[] typeofempI1 = typeofemp1.split(":|/");
						typeofe1 = (typeofempI1[1]).replace("\"", "");
					}
					
					if(!Utils.isEmpty(fgNameEmpSplitList[5])){
						String empLiabilityLimit1 = fgNameEmpSplitList[5];
						String[] empLiabilityLimitI1 = empLiabilityLimit1.split(":|/");
						empLiabilityL1 = (empLiabilityLimitI1[1]).replace("\"", "");
					}
					
					if(!Utils.isEmpty(fgNameEmpSplitList[6])){
						String totalAnnualWageroll1 = fgNameEmpSplitList[6];
						String[] totalAnnualWagerol = totalAnnualWageroll1.split(":|/");
						totalAnnualWage1 = totalAnnualWagerol[1];
					}
					System.out.println("1St : "+typeofe1+" "+empLiabilityL1+" "+totalAnnualWage1);
					
					EmpTypeDetailsVO empTypeDetailsVO1 = new EmpTypeDetailsVO();
					if((Integer.parseInt(typeofe1.trim())) == 8){
						empTypeDetailsVO1.setEmpType(ServiceConstant.WC_ADMIN_CODE);
						empTypeDetailsVO1.setLimit(new BigDecimal(empLiabilityL1.trim()));
						empTypeDetailsVO1.setWageroll(Double.parseDouble(totalAnnualWage1.trim()));
					}else if(Integer.parseInt(typeofe1.trim()) == 9){
						empTypeDetailsVO1.setEmpType(ServiceConstant.WC_NON_ADMIN_CODE); 
						empTypeDetailsVO1.setLimit(new BigDecimal(empLiabilityL1.trim()));
						empTypeDetailsVO1.setWageroll(Double.parseDouble(totalAnnualWage1.trim()));
					}
					empTypeDetailsVOs.add(empTypeDetailsVO1);
				}
				
				String typeofe2 = null;
				String empLiabilityL2 = null;
				String totalAnnualWage2 = null;
				
				if(fgNameEmpSplitList.length > 12){
					if(!Utils.isEmpty(fgNameEmpSplitList[10])){
						String typeofemp2 = fgNameEmpSplitList[10];
						String[] typeofempI2 = typeofemp2.split(":|/");
						typeofe2 = (typeofempI2[1]).replace("\"", "");
					}
					
					if(!Utils.isEmpty(fgNameEmpSplitList[11])){
						String empLiabilityLimit2 = fgNameEmpSplitList[11];
						String[] empLiabilityLimitI2 = empLiabilityLimit2.split(":|/");
						empLiabilityL2 = (empLiabilityLimitI2[1]).replace("\"", "");
					}
					
					if(!Utils.isEmpty(fgNameEmpSplitList[12])){
						String totalAnnualWageroll2 = fgNameEmpSplitList[12];
						String[] totalAnnualWagero2 = totalAnnualWageroll2.split(":|/");
						totalAnnualWage2 = totalAnnualWagero2[1];
					}
					System.out.println("2St : "+typeofe2+" "+empLiabilityL2+" "+totalAnnualWage2);
					
					EmpTypeDetailsVO empTypeDetailsVO2 = new EmpTypeDetailsVO();
					if(Integer.parseInt(typeofe2.trim()) == 8){
						empTypeDetailsVO2.setEmpType(ServiceConstant.WC_ADMIN_CODE);
						empTypeDetailsVO2.setLimit(new BigDecimal(empLiabilityL2.trim()));
						empTypeDetailsVO2.setWageroll(Double.parseDouble(totalAnnualWage2.trim()));
					}else if(Integer.parseInt(typeofe2.trim()) == 9){
						empTypeDetailsVO2.setEmpType(ServiceConstant.WC_NON_ADMIN_CODE); 
						empTypeDetailsVO2.setLimit(new BigDecimal(empLiabilityL2.trim()));
						empTypeDetailsVO2.setWageroll(Double.parseDouble(totalAnnualWage2.trim()));
					}
					empTypeDetailsVOs.add(empTypeDetailsVO2);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		wcVO.setEmpTypeDetails((com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>) empTypeDetailsVOs);	
		
		if(!Utils.isEmpty(result[130])){
			PremiumVO premiumVO = new PremiumVO();
			premiumVO.setPremiumAmt(((BigDecimal)result[130]).doubleValue());
			wcVO.setPremium(premiumVO);
		}
		
		return wcVO;
	}

	private RiskGroupDetails getPLVO(Object[] result, PolicyVO policyVO) {
		PublicLiabilityVO publicLiabilityVO= new PublicLiabilityVO();
		
		SumInsuredVO sumInsuredVO = new SumInsuredVO();
		sumInsuredVO.setSumInsured(((BigDecimal)result[117]).doubleValue());
		
		if(!Utils.isEmpty(result[118])) {
			sumInsuredVO.setDeductible(((BigDecimal)result[118]).doubleValue());
		}
		
		publicLiabilityVO.setSumInsuredDets(sumInsuredVO);
		
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[116]).doubleValue());
		publicLiabilityVO.setPremium(premiumVO);
		
		if((!Utils.isEmpty(result[115])) && (((BigDecimal)result[115]).doubleValue() != 0))
			publicLiabilityVO.setIndemnityAmtLimit(((BigDecimal)result[115]).intValue());
		
		// code added for  WorkAwayRiskLimit Cover M1043209
		UWQuestionsVO plUwQuestionsVO = new UWQuestionsVO();
		java.util.List<UWQuestionVO> plQuestions = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);
		if((!Utils.isEmpty(result[119]))) {
			if(Integer.parseInt(result[119].toString())==1) {
				
				UWQuestionVO question5 = new UWQuestionVO();
				question5.setQId((short)5);
				question5.setQDesc("Do you need work away extension?");
				question5.setResponseType(UWQuestionRespType.RADIO);
				question5.setResponse("yes");
				
				plQuestions.add(question5);

				UWQuestionVO question6 = new UWQuestionVO();
				question6.setQId((short)6);
				question6.setQDesc("If yes, please provide details.");
				question6.setResponseType(UWQuestionRespType.TEXT);
				if((!Utils.isEmpty(result[120]))) {
					try {
						if(Integer.parseInt(result[120].toString())>0) {
							question6.setResponse(result[120].toString());
						}
					}
					catch(Exception e) {
						Integer siCode = Integer.parseInt(result[115].toString());
						question6.setResponse(SvcUtils.getLookUpDescription("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL", siCode));
					}
				}
				plQuestions.add(question6);
			}
		}
		if((!Utils.isEmpty(result[122])) && (Integer.parseInt(result[122].toString())>0)) {
			UWQuestionVO question7 = new UWQuestionVO();
			question7.setQId((short)7);
			question7.setQDesc("Student liability extension.");
			question7.setResponseType(UWQuestionRespType.TEXT);
			question7.setResponse(result[122].toString());
			plQuestions.add(question7);
		
		}
		plUwQuestionsVO.setQuestions(plQuestions);
		publicLiabilityVO.setUwQuestions(plUwQuestionsVO);
		
		// code end for  WorkAwayRiskLimit Cover M1043209
		return publicLiabilityVO;
	}

	public void initializePolicyVoObjects(PolicyVO policyVO) {
		if(Utils.isEmpty(policyVO.getGeneralInfo())) {
			policyVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getInsured())) {
			policyVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getAddress())) {
			policyVO.getGeneralInfo().getInsured().setAddress(new AddressVO());;
		}
		
		if(Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo())) {
			policyVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getSourceOfBus())) {
			policyVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory())) {
			policyVO.getGeneralInfo().setClaimsHistory(new ClaimsSummaryVO());
		}
		if(Utils.isEmpty(policyVO.getScheme())) {
			policyVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(policyVO.getPremiumVO())) {
			policyVO.setPremiumVO(new PremiumVO());
		}
		
		if(Utils.isEmpty(policyVO.getAuthInfoVO())) {
			policyVO.setAuthInfoVO(new AuthenticationInfoVO());
		}
		if(Utils.isEmpty(policyVO.getRiskDetails())) {
			policyVO.setRiskDetails(new com.mindtree.ruc.cmn.utils.List<SectionVO>(SectionVO.class));
			SectionVO sectionVo = new SectionVO(RiskGroupingLevel.LOCATION);
			java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
			sectionVo.setRiskGroupDetails(riskGroupDetails);
			policyVO.getRiskDetails().add(sectionVo);
		}
		
		if(Utils.isEmpty(policyVO.getMapReferralVO())) {
			policyVO.setMapReferralVO(new com.mindtree.ruc.cmn.utils.Map<ReferralLocKey,ReferralVO>(ReferralLocKey.class,ReferralVO.class));
		}
	}
	
	public ParVO getPARVO(Object[] result) {
		
		ParVO parVO = new ParVO();
		
		//Map building cover - 1st row on PAR page
		parVO.setBuilCovered(1);
		
		if(!Utils.isEmpty(result[73])){
			parVO.setBldCover(((BigDecimal)result[73]).doubleValue());
		}
				
		if(!Utils.isEmpty(result[72])){
			PremiumVO premiumVO = new PremiumVO();
			premiumVO.setPremiumAmt(((BigDecimal)result[72]).doubleValue());
			parVO.setBldPremium(premiumVO);
		}
		if(!Utils.isEmpty(result[74])){
			parVO.setBldDeductibles(((BigDecimal)result[74]).doubleValue());
		}
		//map PropertyRisks - other 3 rows on PAR page (list of 3 objects) 1 for each cover.
		parVO.setCovers(getPropertyRisks(result));
		
		return parVO;
	}
	
	public PropertyRisks getPropertyRisks(Object[] result) {
		
		PropertyRisks propertyRisks = new PropertyRisks();
		
		java.util.List<PropertyRiskDetails> propertyCoversDetailsList =  new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class);
		
		//PAR Content -- begin
		if(((BigDecimal)result[81]).doubleValue() != 0) {
			
		PropertyRiskDetails propertyRiskDetails = new PropertyRiskDetails();
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt(((BigDecimal)result[81]).doubleValue());
		propertyRiskDetails.setPremium(premiumVO); // Just Instantiation will suffice.
		
		propertyRiskDetails.setCover(((BigDecimal)result[82]).doubleValue()); 
		propertyRiskDetails.setRiskType(999);
		
		if(!Utils.isEmpty(result[80])) {
			propertyRiskDetails.setDeductibles(((BigDecimal)result[80]).doubleValue());
		}
		
		propertyCoversDetailsList.add(propertyRiskDetails);  //add content cover to list

		}
		//PAR Content -- end
		
		//PAR Rent cover -- begin
		if(((BigDecimal)result[78]).doubleValue() != 0) {
			
			PropertyRiskDetails propertyRiskDetails1 = new PropertyRiskDetails();
			PremiumVO premiumVO = new PremiumVO();
			premiumVO.setPremiumAmt(((BigDecimal)result[78]).doubleValue());
			propertyRiskDetails1.setPremium(premiumVO); // Just Instantiation will suffice.
			
			propertyRiskDetails1.setCover(((BigDecimal)result[79]).doubleValue()); 
			propertyRiskDetails1.setRiskType(13);
			
			if(!Utils.isEmpty(result[83])) {
				propertyRiskDetails1.setDeductibles(((BigDecimal)result[83]).doubleValue());
			}

			propertyCoversDetailsList.add(propertyRiskDetails1);  //add content cover to list

			}
		//PAR Rent cover -- end
		
		//PAR Stock cover -- begin
		if(((BigDecimal)result[75]).doubleValue() != 0) {
			
			PropertyRiskDetails propertyRiskDetailsStock = new PropertyRiskDetails();
			PremiumVO premiumVO = new PremiumVO();
			premiumVO.setPremiumAmt(((BigDecimal)result[75]).doubleValue());
			propertyRiskDetailsStock.setPremium(premiumVO); // Just Instantiation will suffice.
			
			propertyRiskDetailsStock.setCover(((BigDecimal)result[76]).doubleValue()); 
			propertyRiskDetailsStock.setRiskType(9);
			
			if(!Utils.isEmpty(result[83])) {
				propertyRiskDetailsStock.setDeductibles(((BigDecimal)result[77]).doubleValue());
			}

			propertyCoversDetailsList.add(propertyRiskDetailsStock);  //add content cover to list

			}
		//PAR Stock cover -- end
		propertyRisks.setPropertyCoversDetails(propertyCoversDetailsList);
		
		return propertyRisks;
	}
}
