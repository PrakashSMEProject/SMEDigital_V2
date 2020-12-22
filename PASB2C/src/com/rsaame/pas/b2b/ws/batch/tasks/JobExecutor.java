
package com.rsaame.pas.b2b.ws.batch.tasks;


import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.handler.SaveQuoteHandler;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.svc.cmn.PASServiceTask;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public abstract class JobExecutor {
	/*
	 * The class handles the invocation of different services to save in the transaction tables.
	 * The section id is the unique identifier to fetch the related the services from the appconfig.
	 * The sections are saved first.
	 * Then the section details, then the issue quote is performed and finally the referral tasks are performed.
	 * The rules and rating can also be invoked from this executor provided the proper identifiers are passed.
	 */	
	
	public abstract BaseVO process(PolicyVO policyVo) throws Exception;
	
	
	public static class DeleteExecutor extends JobExecutor
	{	
		@Override
		public BaseVO process(PolicyVO policyVo) throws InstantiationException, IllegalAccessException, ClassNotFoundException
		{
		DataHolderVO<Object[]> holder=new DataHolderVO<>();	
		final String[] sectionIdidentifier= {"DELETE_SECTION"};
		BaseVO baseVO=null;
		List<SectionVO> sections= policyVo.getRiskDetails();
		for (SectionVO sectionVO : sections) {
			Object[] vos=new Object[2];
			vos[0]=policyVo;
			vos[1]=sectionVO.getSectionId();
			holder.setData(vos);
			baseVO=perfromTask(sectionIdidentifier,holder);			
		}
		//We are returning the last baseVO due to the design constraint of the staging table.
		return baseVO;
		}		
	}
	public static class CreateExecutor extends JobExecutor
	{
		@Override
		public BaseVO process(PolicyVO policyVo)
			throws Exception  {		
		List<Integer> sectionIds =collectSectionIds(policyVo);
		
		//Added for Approarch 1
		try {
			EplatformWsStaging staging = WSDAOUtils.getPolicyRecordFromStaging(policyVo.getQuoteNo(),policyVo.getEndtId());
			
			if(staging.getBatchStatus()==0) {
				WSDAOUtils.invalidateRecord(policyVo);
			}
			if(policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
				WSDAOUtils.deleteSections(policyVo);
				WSDAOUtils.setPremiumVED(policyVo);
				//policyVo=invalidatePreviousRecords(policyVo);
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
			throw e;
			
		}
		return policyVo;
	}
		
	}
	
	private static void handleReferral(PolicyVO policyVO) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(!policyVO.getMapReferralVO().isEmpty()) {
			SaveQuoteHandler handler=new SaveQuoteHandler();
			if(!policyVO.getTaskDetails().getPolicyType().equals("31"))
			{
				handler.updateRefStatusInTransQuo(policyVO);
			}
		
		}
	}
	
	
	private static PolicyVO invalidatePreviousRecords(PolicyVO policyVO) {
		List<SectionVO> sectionVOList = null;
		SectionVO sectionVO = null;
		sectionVOList = policyVO.getRiskDetails();
		Iterator<SectionVO> sectionListItr = null;
		
		policyVO.setNewEndtId(policyVO.getEndtId());
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session = ht.getSessionFactory().openSession();
		if (!Utils.isEmpty(sectionVOList)) {
//			sectionListItr = sectionVOList.iterator();
			Integer[] sectionList = {1,2,3,4,5,6,7,8,9,10,12};
			for (Integer sectionId : sectionList) {
				Integer classCode =  Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", sectionId.toString()) ) );
				WSDAOUtils.updateVED(policyVO, classCode, sectionId);
				session.flush();
			}
				/*sectionVO = (SectionVO) sectionListItr.next();
				int sectionId = sectionVO.getSectionId().intValue();
				WSDAOUtils.updateVED(policyVO, sectionVO.getClassCode(), sectionId);
				session.flush();*/
				
		}
		
		return policyVO;
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

	private static List<Integer> collectSectionIds(PolicyVO policyVO) {
		return AppUtils.sortSections(
				policyVO.getRiskDetails().stream().map(s -> s.getSectionId()).collect(Collectors.toList()));
	}

	private static List<String> formIdentifier(List<Integer> sectionIds) {				
				return (sectionIds.stream()
				.map(section -> Utils.getSingleValueAppConfig(Utils.concat("SVC_IDENTIFIER_", section.toString())))
				.collect(Collectors.toList()));
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

	private static String getTaskChain(String taskName) {
		String[] tasks = taskName.split(">");
		for (String string : tasks) {
			if (string.equalsIgnoreCase("SERVICE")) {
				return string;
			}
		}
		return "";
	}

}
