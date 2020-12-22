package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rsaame.pas.b2c.ws.vo.Customer;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * @author m1020637
 * 
 */
public class TravelCreateQuoteRequestMapper implements BaseRequestVOMapper {

	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj)
			throws Exception {
		// TODO Auto-generated method stub
		if (requestObj instanceof Customer
				&& valueObj instanceof TravelInsuranceVO) {
			Customer customer = (Customer) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			
			//travelInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(customer.getCustomerDetails().getPoBox());
			AddressVO addressVO=new AddressVO();
			addressVO.setPoBox(customer.getCustomerDetails().getPoBox());			
			travelInsuranceVO.getGeneralInfo().getInsured().setAddress(addressVO);
			
			//travelInsuranceVO.getGeneralInfo().getInsured().setMobileNo(customer.getMobile().toString());//remove any of this item, you will get error in bindingResult object.			
			travelInsuranceVO.getGeneralInfo().getInsured().setMobileNo(customer.getCustomerDetails().getMobileNo());
			//travelInsuranceVO.getGeneralInfo().getInsured().setEmailId(customer.geteMail());
			travelInsuranceVO.getGeneralInfo().getInsured().setEmailId(customer.getCustomerDetails().getEmailId());
			//travelInsuranceVO.getScheme().setEffDate(customer.getStartDate());
			travelInsuranceVO.getScheme().setEffDate(customer.getTransactionDetails().getEffectiveDate() );
			//travelInsuranceVO.getScheme().setExpiryDate(customer.getEndDate());		
			//travelInsuranceVO.getScheme().setExpiryDate(customer.getTransactionDetails().getExpiryDate());
			
			//if(customer.getInclUSACa()) {
			if(customer.getUnderWritingQuestions().getInclUsaCa()) {
				travelInsuranceVO.getTravelDetailsVO().setTravelLocation("Worldwide including USA and Canada");
			}else {
				travelInsuranceVO.getTravelDetailsVO().setTravelLocation("Worldwide excluding USA and Canada");
			}
			
			List<TravelerDetailsVO> travelerDetailsVOList=new ArrayList();
			//for(Travelers travelCustomer:customer.getTravelers()){
			for(Travellers travelCustomer:customer.getTravellers() ){
				TravelerDetailsVO traveller = new TravelerDetailsVO();
				traveller.setName(travelCustomer.getTravellerName());
				//if(travelCustomer.relation.equalsIgnoreCase("Self"))
					traveller.setRelation(travelCustomer.getRelation()); //Self
				traveller.setNationality(travelCustomer.getTravellerNationality().shortValue()); //Afghan
				traveller.setDateOfBirth(travelCustomer.getTravellerDOB());
				
				travelerDetailsVOList.add(traveller);
			}	
			travelInsuranceVO.getTravelDetailsVO().setTravelerDetailsList(travelerDetailsVOList); //TravelerDetailsVO
			
			//long difference= customer.getEndDate().getTime() - customer.getStartDate().getTime();
			/*long difference= customer.getTransactionDetails().getExpiryDate().getTime() - customer.getTransactionDetails().getEffectiveDate().getTime();
			long daysBetween = (difference / (1000*60*60*24));
			daysBetween++;
			travelInsuranceVO.setPolicyTerm((int)daysBetween);*/
			
			 	Calendar cal = Calendar.getInstance();
		        cal.setTime(customer.getTransactionDetails().getEffectiveDate());
		        cal.add(Calendar.DAY_OF_YEAR, customer.getTransactionDetails().getPolicyTerm());
		        //Date t=cal.getTime(); 
		        travelInsuranceVO.getScheme().setExpiryDate(cal.getTime());
		        
			travelInsuranceVO.setPolicyTerm(customer.getTransactionDetails().getPolicyTerm());
			//travelInsuranceVO.setPolicyType( (Integer)customer.type ); //this is not required. May be this is Package type.

			//return travelInsuranceVO;
		} else {
			throw new Exception("Unexpected request or value object");
		}

	}

}
