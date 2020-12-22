package com.rsaame.pas.rating.svc;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;

import com.cts.writeRate.Coverage;
import com.cts.writeRate.Factor;
import com.cts.writeRate.Item;
import com.cts.writeRate.Policy;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.quote.model.FactorBO;
import com.rsaame.pas.svc.constants.SvcConstants;

public class SectionFactorsHelper{
	private final static Logger logger = Logger.getLogger(SectionFactorsHelper.class);
	public Policy setPolicyForPremium( Map<String, ArrayList<ArrayList<FactorBO>>> allFactors, Policy eRatorPolicyForPremium, boolean isPrepacked ){
		
		
		

		logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
				"Setting Policy Level Run time factor");

		 ArrayList<ArrayList<FactorBO>> PARList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "PAR" );
		 ArrayList<ArrayList<FactorBO>> PLList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "PL" );
		 ArrayList<ArrayList<FactorBO>> WCList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "WC" );
		 ArrayList<ArrayList<FactorBO>> MoneyList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "MONEY" );
		 ArrayList<ArrayList<FactorBO>> PARConetntList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "PARCONTENT" );
		 ArrayList<ArrayList<FactorBO>> BIList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "BI" );
		 ArrayList<ArrayList<FactorBO>> EEQList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "EEQ" );
		 ArrayList<ArrayList<FactorBO>> TravelList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "TB" );
		 ArrayList<ArrayList<FactorBO>> MBList = (ArrayList<ArrayList<FactorBO>>) allFactors.get( "MB" );
		 ArrayList <ArrayList<FactorBO>> GITList=(ArrayList<ArrayList<FactorBO>>) allFactors.get( "GIT" );
		 ArrayList <ArrayList<FactorBO>> GPAList=(ArrayList<ArrayList<FactorBO>>) allFactors.get( "GPA" );
		 ArrayList <ArrayList<FactorBO>> DOSList=(ArrayList<ArrayList<FactorBO>>) allFactors.get( "DOS" );
		 ArrayList <ArrayList<FactorBO>> FIDList=(ArrayList<ArrayList<FactorBO>>) allFactors.get( "FID" );

		 
		// Start: Set Policy level Factors

			 if(!Utils.isEmpty(PARList)){
				 eRatorPolicyForPremium=setPolicyFactors(PARList,eRatorPolicyForPremium);	 
			 }
			 if(!Utils.isEmpty(PLList)){
				 eRatorPolicyForPremium=setPolicyFactors(PLList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(WCList)){
				 eRatorPolicyForPremium=setPolicyFactors(WCList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(MoneyList)){
				 eRatorPolicyForPremium=setPolicyFactors(MoneyList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(PARConetntList)){
				 eRatorPolicyForPremium=setPolicyFactors(PARConetntList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(BIList)){
				 eRatorPolicyForPremium=setPolicyFactors(BIList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(MBList)){
				 eRatorPolicyForPremium=setPolicyFactors(MBList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(EEQList)){
				 eRatorPolicyForPremium=setPolicyFactors(EEQList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(TravelList)){
				 eRatorPolicyForPremium=setPolicyFactors(TravelList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(GITList)){
				 eRatorPolicyForPremium=setPolicyFactors(GITList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(GPAList)){
				 eRatorPolicyForPremium=setPolicyFactors(GPAList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(DOSList)){
				 eRatorPolicyForPremium=setPolicyFactors(DOSList,eRatorPolicyForPremium);
			 }
			 if(!Utils.isEmpty(FIDList)){
				 eRatorPolicyForPremium=setPolicyFactors(FIDList,eRatorPolicyForPremium);
			 }
		// End: Set item level Factors		 
		 
		//NOTE: Logic to set policy level coverage factor values is not yet incorporated since SBS doesn't have any
			 //policy coverage level factors 
			 
			 
		// Start: Set item level Factors
			 // Create newItemsList, fill values and replace existing item list.   
			 ArrayList <Item> newItemsList=new ArrayList<Item>();
			 int ratingItemNumber;
			 for(Item item:eRatorPolicyForPremium.getItems()){
				 
				 //Start: Check for PAR Building item present in policy 
				 if(SvcConstants.RATING_BUILD_ITEM_CODE.equals( item.getItemCode())){

					 if(!Utils.isEmpty(PARList)){
						 
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:PARList ){
							    // Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							    Item items = CopyUtils.copySerializableObject(item); //To avoid the reference problem when more 
							    items=setItemFactors(eachFactorList,items);
								newItemsList.add( items );
								}
						//End: Create Multiple items as number of items present in PAR building category  	 
					 }
					 /*else{
						 //Fill default values for PAR
						 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
						 //item=sectionFactorsDefaulter.setDefaultPARItemFactors(item);
						 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPARFactorList();
						 item=setItemFactors(defaultList,item);
						 newItemsList.add( item );
					 }*/
				 }
				 //End: Check for PAR Building item present in policy
				 
				 
				 else if(SvcConstants.RATING_BUILD_CONTENT_ITEM_CODE.equals( item.getItemCode())){
					 //Contents will be present only if PAR is present
					 if(!Utils.isEmpty(PARList)){
						 
						 if(!Utils.isEmpty(PARConetntList)){
							 
							 //Start: Create Multiple items as number of items present in PAR building category   
							 for(ArrayList<FactorBO> eachFactorList:PARConetntList ){
								
									 //if prepack create only one item skip all contents apart from the two
									 // if flexi create contents list and skip the additional covers
									 Item filledItem=new Item();
									 filledItem=null;
									 filledItem=setItemFactors(eachFactorList,item) ;
									 //setItemFactors(eachFactorList,filledItem);
									 newItemsList.add(filledItem );
								

							 }
							//End: Create Multiple items as number of items present in PAR building category  	 
						 }/*else{
							 //Fill default values 
							 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
							 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPARContentFactorList();
							 item=setItemFactors(defaultList,item);
							 newItemsList.add( item );
						 }*/
						 
						 
						 
					 }
				 }
				 else if(SvcConstants.RATING_PL_ITEM_CODE.equals( item.getItemCode())){
					 
					 if(!Utils.isEmpty(PLList)){
							 
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:PLList ){
							// Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }/*else{
						 //Fill default values 
						 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
						 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultPLFactorList();
						 item=setItemFactors(defaultList,item);
						 newItemsList.add( item );
					 }*/
					 
				 }
				 else if(SvcConstants.RATING_WC_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(WCList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:WCList ){
							 Item itemWC=new Item();
							 itemWC=setItemFactors(eachFactorList,item);
							 newItemsList.add( itemWC );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }/*else{
						 //Fill default values 
						 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
						 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultWCFactorList();
						 item=setItemFactors(defaultList,item);
						 newItemsList.add( item );
					 }*/
				 }
				 else if(SvcConstants.RATING_MONEY_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(MoneyList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:MoneyList ){
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
					 /*else{
						 //Fill default values 
						 SectionFactorsDefaulter sectionFactorsDefaulter= new SectionFactorsDefaulter();
						 ArrayList<FactorBO> defaultList=sectionFactorsDefaulter.createDefaultMoneyFactorList();
						 item=setItemFactors(defaultList,item);
						 newItemsList.add( item );
					 }*/
				 }
				 
				 //Added for Phase 2a
				 else if(SvcConstants.RATING_BI_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(BIList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:BIList ){
						    // Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_MB_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(MBList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:MBList ){
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_EEQ_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(EEQList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:EEQList ){
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_TB_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(TravelList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:TravelList ){
							// Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_GIT_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(GITList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:GITList ){
							// Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_GPA_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(GPAList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:GPAList ){
							// Avoid the reference problem when more than one section/location data is sent to rating engine at a time
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_DOS_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(DOSList)){
						 //Start: Create Multiple items as number of items present in PAR building category
						
						 for(ArrayList<FactorBO> eachFactorList:DOSList ){
							 
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
							 
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }
				 else if(SvcConstants.RATING_FID_ITEM_CODE.equals( item.getItemCode())){
					 if(!Utils.isEmpty(FIDList)){
						 //Start: Create Multiple items as number of items present in PAR building category   
						 for(ArrayList<FactorBO> eachFactorList:FIDList ){
							 Item items = CopyUtils.copySerializableObject(item);
							 items=setItemFactors(eachFactorList,items);
							 newItemsList.add( items );
						 }
						 //End: Create Multiple items as number of items present in PAR building category  	 
					 }
				 }

			 }
		
			 // Set ItemsList as  newItemsList created.
			 eRatorPolicyForPremium.setItems((Item[])newItemsList.toArray(new Item[newItemsList.size()]));
			 
		// End: Set item level Factors
		
		
		return eRatorPolicyForPremium;
	}
	
	
	private Item setItemFactors( ArrayList<FactorBO> eachRisFactorList, Item item ){
		
		
		
		

		Item tempItem=null;
		tempItem=new Item();
		//Item item = CopyUtils.copySerializableObject(itemOrg);
		//Coverage[] coverageArray=new Coverage[item.getCoverages().length];
		Coverage[] coverageArray;
		coverageArray=item.getCoverages();
		tempItem.setCoverages(coverageArray);
		tempItem.setItemCode(item.getItemCode());
		tempItem.setItemFactors(item.getItemFactors());
	
		boolean itemNumberSet=false;
		if (tempItem.getItemFactors() != null ) {
			// boolean to avoid Setting tempItem number nultiple times . 
			
				Factor[] itemFactor = item.getItemFactors();
				Factor[] tempItemFactor=new Factor[itemFactor.length];
				
				for (int countItemFactor = 0; countItemFactor < itemFactor.length; countItemFactor++) {
					ListIterator listItr = eachRisFactorList.listIterator();
					while (listItr.hasNext()) {
						FactorBO keyValue = (FactorBO) listItr.next();
						//START: Set tempItem number
						if ((!itemNumberSet) && (SvcConstants.RATING_ITEM_SEQ_NO_FACTOR
								.equalsIgnoreCase(keyValue.getFactorName()))){
							tempItem.setItemNumber(Integer.parseInt( keyValue.getFactorValue()));
							itemNumberSet=true;
							
						} 
						//END: Set tempItem number	
						
						else if (itemFactor[countItemFactor].getFactorName().equalsIgnoreCase(keyValue.getFactorName())) {
							itemFactor[countItemFactor].setFactorValue(keyValue.getFactorValue());
							Factor tempFact= new Factor();
							tempFact.setFactorCode(itemFactor[countItemFactor].getFactorCode());
							tempFact.setFactorName(itemFactor[countItemFactor].getFactorName());
							tempFact.setFactorValue(keyValue.getFactorValue());
							tempItemFactor[countItemFactor]=tempFact;
							
							
							
						}
					}
					tempItem.setItemFactors(countItemFactor,tempItemFactor[countItemFactor]);
				}

				logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
						"After Setting Item Level Run time factor");
				//eRatorPolicyForPremium.setItems(item);
			}
			
			Coverage[] coverage;
			Coverage[] tempCoverage;
			Factor[] factors;
			Factor[] tempFactors;

			if (tempItem.getCoverages() != null) {
				logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
						"Setting Item Level Coverage Run time factor");
				coverage = item.getCoverages();
				tempCoverage=new Coverage[ item.getCoverages().length];
				for (int covergeCount = 0; covergeCount < coverage.length; covergeCount++) {
					if (coverage[covergeCount] != null) {
						factors = coverage[covergeCount]
								.getCoverageFactors();
						tempFactors=new Factor[factors.length];
						for (int countCoverageFactor = 0; countCoverageFactor < factors.length; countCoverageFactor++) {
							ListIterator listItr = eachRisFactorList
									.listIterator();
							while (listItr.hasNext()) {
								FactorBO keyValue = (FactorBO) listItr
										.next();
								
								//START: Set tempItem number
								if ((!itemNumberSet) && (SvcConstants.RATING_ITEM_SEQ_NO_FACTOR
										.equalsIgnoreCase(keyValue.getFactorName()))){
									tempItem.setItemNumber(Integer.parseInt( keyValue.getFactorValue()));
									itemNumberSet=true;
									
								} 
								//END: Set tempItem number
								if (factors[countCoverageFactor]
										.getFactorName().equalsIgnoreCase(
												keyValue.getFactorName())) {
									
								
									
									
									factors[countCoverageFactor].setFactorValue(keyValue.getFactorValue());
									Factor tempFactor=new Factor();
									tempFactor.setFactorCode(factors[countCoverageFactor].getFactorCode());
									tempFactor.setFactorName(factors[countCoverageFactor].getFactorName());
									tempFactor.setFactorValue(keyValue.getFactorValue());
									tempFactors[countCoverageFactor]=tempFactor;
									logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,"Setting Item Level Coverage Run time factor Name:Value:"
															+ keyValue.getFactorName()
															+ ":"
															+ keyValue.getFactorValue());
									break;
								}
							}
							coverage[covergeCount].setCoverageFactors(countCoverageFactor,factors[countCoverageFactor]);
							Coverage tempCov=new Coverage();
							tempCov.setCoverageFactors(tempFactors);
							tempCov.setCoverageCode(coverage[covergeCount].getCoverageCode());
							tempCoverage[covergeCount]=tempCov;
						}
					}
				}

				tempItem.setCoverages(tempCoverage);

				logger
						.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
								"After Setting Item Level Coverage Run time factor");
			}
		
	
		//dtempItem=item;
		return tempItem;
	
	}




	/**
	 * This method sets all item level factors 
	 * @param sectionList
	 * @param eRatorPolicyForPremium
	 * @return
	 *//*
	private Policy setItemFactors( ArrayList<ArrayList<FactorBO>> sectionList, Policy eRatorPolicyForPremium ){
		
		
		
		
		
		
		
		
		
		
		
		return eRatorPolicyForPremium;
	}*/


	/**
	 * This method fills the policy level factors for policy to be rated
	 * @param pARList
	 * @param eRatorPolicyForPremium
	 * @return eRatorPolicyForPremium
	 */
	private Policy setPolicyFactors( ArrayList<ArrayList<FactorBO>> sectionList, Policy eRatorPolicyForPremium ){

		Factor[] policyFactors;
		policyFactors = eRatorPolicyForPremium.getPolicyFactors();

		//Start: Check if policy factors present or not
		if(!Utils.isEmpty(policyFactors)){


			//Start: Run for each risk in section  List 
			for(ArrayList<FactorBO> eachRiskFactorList:sectionList){

				logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
					"Setting Policy Level Run time factor");


					//Start: For each policy factor
					for (Factor policyFactor:policyFactors) {
						//Start: Run for each risk in section  List
						for(FactorBO keyValue:eachRiskFactorList){
							if (policyFactor.getFactorName().equalsIgnoreCase(
									keyValue.getFactorName())) {
								policyFactor.setFactorValue(keyValue
										.getFactorValue());
								logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
										"Policy Level Run time factor:"
										+ keyValue.getFactorValue());
							}

						}
						//End: Run for each risk in section  List

					}
					//End: For each policy factor
				

			}
			//Start: Run for each risk in section  List 



			logger.debug(AMEConstants.SET_POLICY_FOR_PREMIUM,
			"After Setting Policy Level Run time factor");
			eRatorPolicyForPremium.setPolicyFactors(policyFactors);

		}
		//End: Check if policy factors present or not



		return eRatorPolicyForPremium;
	}

}
