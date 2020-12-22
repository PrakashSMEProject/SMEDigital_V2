package com.rsaame.pas.b2b.ws.batch.tasks;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.cmn.PASServiceTask;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;

public class ManualJobHandler {

	public BaseVO triggerJobManually(PolicyVO policyVo) {
		
		List<Integer> sectionIds =collectSectionIds(policyVo);
		
		try {
			//Added for Approarch 1
			if(policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
				DAOUtils.deleteSections(policyVo);
				DAOUtils.setPremiumVED(policyVo);
			}
			saveSections(sectionIds,policyVo);	

			
			saveSectionDetails(sectionIds,policyVo);
			
			if(policyVo.getStatus()==6) {
				policyVo= (PolicyVO) issueQuote(sectionIds,policyVo);
			}

			handleReferral(policyVo);
			
			//Added for Approarch 1 : procedure to invalidate all previous endt records
			if(policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
				policyVo=invalidatePreviousRecords(policyVo);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DAOUtils.invalidateRecord(policyVo);
		}
		return policyVo;
	}
	private static List<Integer> collectSectionIds(PolicyVO policyVO) {
		return sortSections(
				policyVO.getRiskDetails().stream().map(s -> s.getSectionId()).collect(Collectors.toList()));
	}

	private static List<String> formIdentifier(List<Integer> sectionIds) {				
				return (sectionIds.stream()
				.map(section -> Utils.getSingleValueAppConfig(Utils.concat("SVC_IDENTIFIER_", section.toString())))
				.collect(Collectors.toList()));
	}
	private static void saveSectionDetails(List<Integer> sectionIds, PolicyVO policyVo) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String[] arr=new String[sectionIds.size()];
		List<String> identifiers=formIdentifier(sectionIds);
    	perfromTask(identifiers.toArray(arr),policyVo);		
	}

	private static void saveSections(List<Integer> sectionIds,PolicyVO policyVO) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final String[] sectionIdidentifier= {"SAVE_SELECTED_SECTIONS"};
		SectionList sectionList=new SectionList();
		sectionList.setSelectedSec(sectionIds);
		sectionList.setPolicyLinkingId(policyVO.getPolLinkingId() );
		sectionList.setEndorsementNo(Long.valueOf(0));  
    	perfromTask(sectionIdidentifier,sectionList);
	}
	private static BaseVO perfromTask(String[] identifier, BaseVO baseVO)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String[] taskInputConfig = null;
		for (String string : identifier) {
			String[] taskNames = Utils.getMultiValueAppConfig(Utils.concat(string, "_TASKLIST"));
			Utils.trimAllEntries(taskNames);

			for (String task : taskNames) {
				String taskName = getTaskChain(task);
				if (!"".equalsIgnoreCase(task)) {
					taskInputConfig = Utils.getMultiValueAppConfig(Utils.concat(string, "_", taskName, "_TASK_INPUT"));
					Utils.trimAllEntries(taskInputConfig);
					PASServiceTask pASServiceTask = (PASServiceTask) Class
							.forName("com.rsaame.pas.svc.cmn.PASServiceTask").newInstance();
					baseVO=pASServiceTask.invokeMethodFromBatch(taskInputConfig[0],taskInputConfig[1],baseVO);
				}
			}
		}
		return baseVO;
	}
	private static BaseVO issueQuote(List<Integer> sectionIds, PolicyVO policyVo) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final String[] issueQuoteidentifier= {"ISSUE_QUOTE"};
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		polComHolder.setComments(null);
		polComHolder.setPolicyDetails(policyVo);
		
		
		BaseVO baseVO= perfromTask(issueQuoteidentifier,polComHolder);
		BaseVO policyVO=null;
		if (baseVO instanceof PolicyCommentsHolder) {
			policyVO = ((PolicyCommentsHolder) baseVO).getPolicyDetails();			
		}
		return policyVO;
		
	}
	private static PolicyVO invalidatePreviousRecords(PolicyVO policyVO) {
		List<SectionVO> sectionVOList = null;
		sectionVOList = policyVO.getRiskDetails();
		policyVO.setNewEndtId(policyVO.getEndtId());
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session = ht.getSessionFactory().openSession();
		if (!Utils.isEmpty(sectionVOList)) {
			Integer[] sectionList = {1,2,3,4,5,6,7,8,9,10,12};
			for (Integer sectionId : sectionList) {
				Integer classCode =  Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", sectionId.toString()) ) );
				DAOUtils.updateVED(policyVO, classCode, sectionId);
				session.flush();
			}
		}
		return policyVO;
	}
	private static void handleReferral(PolicyVO policyVO) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(!policyVO.getMapReferralVO().isEmpty()) {
			/*SaveQuoteHandler handler=new SaveQuoteHandler();
			if(!policyVO.getTaskDetails().getPolicyType().equals("31"))
			{
				handler.updateRefStatusInTransQuo(policyVO);
			}*/
		
		}
	}
	private static String getTaskChain(String taskName) {
		String[] tasks = taskName.split(">");
		for (String string : tasks) {
			if (string.equalsIgnoreCase("SERVICE")) {
				return string;
			}
		}
		return "";
	}
	public static List<Integer> sortSections( List<Integer> parameterNames ){
		/*The below code is used to sort the sections is such an order so that PAR or PL 
		(basic sections always appears before other section on UI)*/
		int indxPAR = parameterNames.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PAR ) );
		int indxPL = parameterNames.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PL ) );
		if( indxPL != -1 ){
			parameterNames.remove( indxPL );
			if( indxPAR != -1 )
				parameterNames.add( 1, Integer.valueOf( AppConstants.SECTION_ID_PL ) );
			else
				parameterNames.add( 0, Integer.valueOf( AppConstants.SECTION_ID_PL ) );

		}

		return parameterNames;
	}
}
