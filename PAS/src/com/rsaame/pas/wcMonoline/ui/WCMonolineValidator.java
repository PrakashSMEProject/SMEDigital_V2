package com.rsaame.pas.wcMonoline.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;

/**
 * This class is used to check the incoming workman compensation object.
 * @author M1024781
 *
 */
public class WCMonolineValidator implements IBeanValidator{

	/**
	 * This is the overridden method which is getting called when this class is called in the RH.
	 * This method all the mandatory and valid data check for the incoming workman compensation object.
	 */
	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		boolean success = true;
		Integer noOfEmpFromWC = 0;
		if (!Utils.isEmpty(bean)) 
		{
			WorkmenCompVO workmenCompVO = (WorkmenCompVO) bean;
			List<EmpTypeDetailsVO> empTypeDetailsList = workmenCompVO.getEmpTypeDetails();
			
			if(Utils.isEmpty(workmenCompVO.getLocationVO().getAddress().getLocOverrideJur()))
			{
				errorKeys.add("pl.jurisdiction.required");				
			}
			/*if(Utils.isEmpty(workmenCompVO.getLocationVO().getOccTradeGroup()))
			{
				errorKeys.add("pl.occupancy.tradegroup.required");				
			}*/
			if(Utils.isEmpty(workmenCompVO.getLocationVO().getFreeZone()))
			{
				errorKeys.add("pl.freezone.required");				
			}
			
			if(Utils.isEmpty(empTypeDetailsList))
			{
				errorKeys.add("wc.wcDetails.required");
			}
			
			for(EmpTypeDetailsVO empTypeDetailsVO:empTypeDetailsList)
			{
				if( Utils.isEmpty( empTypeDetailsVO.getEmpType()) && (Utils.isEmpty(empTypeDetailsVO.getNoOfEmp()) || empTypeDetailsVO.getNoOfEmp()==0) 
						&& Utils.isEmpty(empTypeDetailsVO.getWageroll()) && Utils.isEmpty(empTypeDetailsVO.getDeductibles()) ){
					continue;
				}
				if(Utils.isEmpty(empTypeDetailsVO.getEmpType()))
				{
					errorKeys.add("wc.empType.required");					
				}
				if(Utils.isEmpty(empTypeDetailsVO.getNoOfEmp()) || empTypeDetailsVO.getNoOfEmp()==0)
				{
					errorKeys.add("wc.noOfEmp.required");					
				}
				else if(!isNumeric(Integer.valueOf( empTypeDetailsVO.getNoOfEmp() ).toString()))
				{
					errorKeys.add("wc.noOfEmp.valid.number");
				}
				else
				{
					noOfEmpFromWC += empTypeDetailsVO.getNoOfEmp();
				}
				if(Utils.isEmpty(empTypeDetailsVO.getWageroll()))
				{
					errorKeys.add("wc.wageroll.required");				
				}
				else if(!isNumeric(empTypeDetailsVO.getWageroll().toString()))
				{
					errorKeys.add("wc.wageroll.valid.number");	
				}					
				if(Utils.isEmpty(empTypeDetailsVO.getDeductibles()))
				{
					errorKeys.add("wc.deductibles.required");					
				}				
				if(Utils.isEmpty(empTypeDetailsVO.getLimit()))
				{
					errorKeys.add("wc.limit.required");					
				}
			}
			for(int i=0;i<empTypeDetailsList.size();i++){
				if( Utils.isEmpty( empTypeDetailsList.get(i).getEmpType()) && (Utils.isEmpty(empTypeDetailsList.get(i).getNoOfEmp()) || empTypeDetailsList.get(i).getNoOfEmp()==0) 
						&& Utils.isEmpty(empTypeDetailsList.get(i).getWageroll()) && Utils.isEmpty(empTypeDetailsList.get(i).getDeductibles()) ){
					continue;
				}
				for(int j=i+1;j<empTypeDetailsList.size();j++){
					if( Utils.isEmpty( empTypeDetailsList.get(j).getEmpType()) && (Utils.isEmpty(empTypeDetailsList.get(j).getNoOfEmp()) || empTypeDetailsList.get(j).getNoOfEmp()==0) 
							&& Utils.isEmpty(empTypeDetailsList.get(j).getWageroll()) && Utils.isEmpty(empTypeDetailsList.get(j).getDeductibles()) ){
						continue;
					}
					if(empTypeDetailsList.get(i).getEmpType().equals(empTypeDetailsList.get(j).getEmpType() ) 
							&& empTypeDetailsList.get(i).getNoOfEmp() == empTypeDetailsList.get(j).getNoOfEmp()
							&& empTypeDetailsList.get(i).getLimit().compareTo(empTypeDetailsList.get(j).getLimit() ) == SvcConstants.zeroVal 
							&& BigDecimal.valueOf( empTypeDetailsList.get(i).getWageroll() ).compareTo(BigDecimal.valueOf( empTypeDetailsList.get(j).getWageroll() ) ) == SvcConstants.zeroVal){
						errorKeys.add("wc.wcDetails.duplicate");
						break;
					}
				}
			}
			if(!Utils.isEmpty(workmenCompVO.getGeneralInfo().getInsured().getNoOfEmployees()))
			{
				if(noOfEmpFromWC>workmenCompVO.getGeneralInfo().getInsured().getNoOfEmployees())
				{
					errorKeys.add("pas.wc.maxNumberOfPersons");
				}
			}
			
			/*List<WCNammedEmployeeVO> wcNammedEmployeeVOList = workmenCompVO.getWcEmployeeDetails();
			for(WCNammedEmployeeVO wcNammedEmployeeVO:wcNammedEmployeeVOList)
			{
				if(!Utils.isEmpty(wcNammedEmployeeVO.getEmpName()))
				{
					if(!isAlphabets(wcNammedEmployeeVO.getEmpName()))
					{
						errorKeys.add("wc.empName.valid");
					}
				}
			}*/		
			
		}
		if(errorKeys.size()>0)
		{
			success = false;
		}
		return success;
	}
	
	/**
	 * This method will check if the provided string is of numeric type
	 * 
	 * @param input
	 * @return
	 */
	private boolean isNumeric(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		input = Currency.getUnformttedScaledCurrency(new Double(input));
		if (input.matches(AppConstants.NUMERIC_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}
	
	/**
	 * This method will check if the provided string contains only alphabets
	 * 
	 * @param input
	 * @return
	 */
	private boolean isAlphabets(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.ALPHABETS_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}

}
