/**
 * 
 */
package com.rsaame.pas.ui.cmn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.CommissionVO;
import com.rsaame.pas.vo.app.CommissionVOList;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.PPPCriteriaVO;
import com.rsaame.pas.vo.app.SectionList;

/**
 * @author m1014644
 *
 */
public class GetCommissionRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		Map<Integer, Double> classComMap = new HashMap<Integer, Double>();
		/*
		 * Getting commission info for the risk section
		 * commission is base on class and scheme
		 */
		CommissionVOList commissionVOList = (CommissionVOList) TaskExecutor.executeTasks( AppConstants.GET_COMMISSION, polContext.getPolicyDetails() );
		java.util.List<CommissionVO> commisionList = commissionVOList.getCommision();
		for( CommissionVO commission : commisionList ){
			classComMap.put( commission.getClassCode(), Double.valueOf( commission.getComPrec() ) );
		}
		// put commision map <class,commission> into session
		request.getSession( false ).setAttribute( AppConstants.GET_COMMISSION, classComMap );
		//PolicyContext policyContext=PolicyContextUtil.getPolicyContext( request );
		
		/*Start for making a service call for section list for pre packaged policy and populating the same to
		 *selected risk sections  */
		if(!Utils.isEmpty( polContext )){
			if(!Utils.isEmpty( polContext.getPolicyDetails())&& !Utils.isEmpty( polContext.getPolicyDetails().getIsPrepackaged() )){
				if(polContext.getPolicyDetails().getIsPrepackaged() )
				{
				boolean flag=true;
				PPPCriteriaVO criteriaVO=(PPPCriteriaVO)TaskExecutor.executeTasks( AppConstants.GET_PPP_SECTIONLIST,constructPPPInput(polContext) );
				if(!Utils.isEmpty( criteriaVO )){
			
					List<Short> secList=criteriaVO.getSectionList();
					saveSelectedSect(secList,polContext);
					/*secList = CopyUtils.asList(CopyUtils.asSet(secList));
					
					Collections.sort(secList);
					Integer[] sectionList = new Integer[secList.size()];
					int index=0;
					for(Short secId : secList ){
						sectionList[index++]=secId.intValue();
					}
					policyContext.populateSelectedSec(sectionList);
					Added to save selected risk sections list in to DB so that the selected risk information is not lost in case
					 *  of user is not saving all the selected sections 
					SectionList sectionsVO=new SectionList();
					sectionsVO.setSelectedSec( Arrays.asList(sectionList) );
					if(!Utils.isEmpty( polContext.getPolicyDetails() )){
						sectionsVO.setPolicyLinkingId( polContext.getPolicyDetails().getPolLinkingId() );
					//TODO: Confirm where to get Endorsement number from
						sectionsVO.setEndorsementNo( polContext.getPolicyDetails().getEndtId() );
					}
					TaskExecutor.executeTasks( AppConstants.SAVE_SELECTED_SECTIONS,sectionsVO);*/
				}
				}
				/*
				 * For Oman risk selection popup is not required.
				 */
				else if( !AppUtils.isRiskSelectionEnabled() ){
					List<Short> secList= new ArrayList<Short>();
					
					LoadExistingInputVO input = new LoadExistingInputVO();
					input.setPolLinkingId(polContext.getPolicyDetails().getPolLinkingId());
					input.setEndtId(polContext.getPolicyDetails().getEndtId());
					input.setQuote( polContext.getPolicyDetails().getIsQuote() );
					input.setAppFlow( polContext.getPolicyDetails().getAppFlow() );
					input.setPolicyStatus(  polContext.getPolicyDetails().getStatus() );

					SectionList output = (SectionList) TaskExecutor.executeTasks( AppConstants.FETCH_SELECTED_SECTIONS, input );
					
					if(!Utils.isEmpty(output) && (Utils.isEmpty(output.getSelectedSec()) || output.getSelectedSec().size()==0)){
						LookUpListVO lookUpListVo = SvcUtils.getLookUpCodesList("DEFAULT_SECTIONS", "ALL", "ALL");
						
						for(LookUpVO lookUp: lookUpListVo.getLookUpList()){
							secList.add(lookUp.getCode().shortValue());
						}
						
					}else{
						
						for(Integer secId : output.getSelectedSec()){
							secList.add(secId.shortValue());
						}
					}
					
					
					/*secList = CopyUtils.asList(CopyUtils.asSet(secList));
					
					Collections.sort(secList);
					Integer[] sectionList = new Integer[secList.size()];
					int index=0;
					for(Short secId : secList ){
						sectionList[index++]=secId.intValue();
					}
					polContext.populateSelectedSec(sectionList);*/
					saveSelectedSect(secList,polContext);
					
				}
			} 
			
			
			
		}
		/*End for making a service call for section list for pre packaged policy and populating the same to
		 *selected risk sections  */
		Redirection redirection = new Redirection("SECTION&action=LOAD_DATA",Redirection.Type.TO_NEW_OPERATION);
		Response responseObj = new Response();
		responseObj.setRedirection(redirection);
		return responseObj;
	} 
	
	/*
	 *  Construct PPPCriteriaVO to query VMasPasFetchBasicDtls view to 
	 *  get the number of contents and description to display the same
	 *  on screen
	 */
	private PPPCriteriaVO  constructPPPInput(PolicyContext policyContext){
		PPPCriteriaVO pppCriteriaVO = new PPPCriteriaVO();
		
		if(!Utils.isEmpty(policyContext)){
					
			if(!Utils.isEmpty( policyContext.getPolicyDetails()))
			{
				if(!Utils.isEmpty( policyContext.getPolicyDetails().getScheme() )){
					pppCriteriaVO.setTariffCode(policyContext.getPolicyDetails().getScheme().getTariffCode());	
					pppCriteriaVO.setPolicyType( policyContext.getPolicyDetails().getScheme().getPolicyType() );
				}
			}
		}
		return pppCriteriaVO;
	}
	/*
	 * Method will save selected section from risk section into db. For Oman and prepack risk selection 
	 */
	private void saveSelectedSect(List<Short> secList,PolicyContext polContext){
		
		secList = CopyUtils.asList(CopyUtils.asSet(secList));
		
		Collections.sort(secList);
		Integer[] sectionList = new Integer[secList.size()];
		int index=0;
		for(Short secId : secList ){
			sectionList[index++]=secId.intValue();
		}
		polContext.populateSelectedSec(sectionList);
		/*Added to save selected risk sections list in to DB so that the selected risk information is not lost in case
		 *  of user is not saving all the selected sections */
		SectionList sectionsVO=new SectionList();
		sectionsVO.setSelectedSec( Arrays.asList(sectionList) );
		if(!Utils.isEmpty( polContext.getPolicyDetails() )){
			sectionsVO.setPolicyLinkingId( polContext.getPolicyDetails().getPolLinkingId() );
		//TODO: Confirm where to get Endorsement number from
			sectionsVO.setEndorsementNo( polContext.getPolicyDetails().getEndtId() );
		}
		TaskExecutor.executeTasks( AppConstants.SAVE_SELECTED_SECTIONS,sectionsVO);
	}
	

}
