package com.rsaame.pas.b2b.ws.batch.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.svc.cmn.PASServiceTask;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.WSDAOUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;

public abstract class JobExecutor {
	/*
	 * The class handles the invocation of different services to save in the
	 * transaction tables. The section id is the unique identifier to fetch the
	 * related the services from the appconfig. The sections are saved first.
	 * Then the section details, then the issue quote is performed and finally
	 * the referral tasks are performed. The rules and rating can also be
	 * invoked from this executor provided the proper identifiers are passed.
	 */

	public abstract BaseVO process(PolicyVO policyVo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException;

	public static class DeleteExecutor extends JobExecutor {
		@Override
		public BaseVO process(PolicyVO policyVo)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			DataHolderVO<Object[]> holder = new DataHolderVO<>();
			final String[] sectionIdidentifier = { "DELETE_SECTION" };
			BaseVO baseVO = null;
			List<SectionVO> sections = policyVo.getRiskDetails();
			for (SectionVO sectionVO : sections) {
				Object[] vos = new Object[2];
				vos[0] = policyVo;
				vos[1] = sectionVO.getSectionId();
				holder.setData(vos);
				baseVO = perfromTask(sectionIdidentifier, holder);
			}
			// We are returning the last baseVO due to the design constraint of
			// the staging table.
			return baseVO;
		}
	}
	/*
	 * public static class CreateExecutor extends JobExecutor {
	 * 
	 * @Override public BaseVO process(PolicyVO policyVo) throws
	 * InstantiationException, IllegalAccessException, ClassNotFoundException {
	 * List<Integer> sectionIds =collectSectionIds(policyVo);
	 * 
	 * //Added for Approarch 1 try {
	 * if(policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
	 * DAOUtils.deleteSections(policyVo); DAOUtils.setPremiumVED(policyVo); //
	 * policyVo=invalidatePreviousRecords(policyVo); }
	 * saveSections(sectionIds,policyVo);
	 * 
	 * 
	 * saveSectionDetails(sectionIds,policyVo);
	 * 
	 * if(policyVo.getStatus()==6) { policyVo= (PolicyVO)
	 * issueQuote(sectionIds,policyVo); }
	 * 
	 * handleReferral(policyVo);
	 * 
	 * //Added for Approarch 1 : procedure to invalidate all previous endt
	 * records if(policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
	 * policyVo=invalidatePreviousRecords(policyVo); } } catch (Exception e) {
	 * // TODO Auto-generated catch block DAOUtils.invalidateRecord(policyVo);
	 * e.printStackTrace(); } return policyVo; }
	 * 
	 * }
	 */

	public static class CreateExecutor extends JobExecutor {
		@Override
		public BaseVO process(PolicyVO policyVo)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			List<Integer> sectionIds = collectSectionIds(policyVo);
			int sectionCount = 0;
			//Sonar fix for Close the statement in a finally clause
			//Statement stmt = null;
			ResultSet result = null;
			//Connection con=null;
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
			Session session = ht.getSessionFactory().openSession();
			// Added for Approarch 1
			//Sonar fix for Close the statement in a finally clause
			try(Connection  con = session.connection();
				  Statement stmt = con.createStatement()) {
				String query = "select batch_status from T_TRN_EPLATFORM_STAGING_QUO where pol_quotation_no = "
						+ policyVo.getQuoteNo() + " and pol_endt_id = " + policyVo.getEndtId();
				//Sonar fix for Close the statement in a finally clause
				//con = session.connection();
				//stmt = con.createStatement();
				result = stmt.executeQuery(query);
				System.out.println(result.toString());
				boolean batchStatus = false;
				// To get system date and time
				Date sysdate = java.util.Calendar.getInstance().getTime();
				policyVo.setNewValidityStartDate(sysdate);
				policyVo.setValidityStartDate(sysdate);
				System.out.println("policyVo.setNewValidityStartDate..........."+policyVo.getNewValidityStartDate());
				System.out.println("policyVo.setValidityStartDate..........."+policyVo.getValidityStartDate());
				if (result != null) {
					while (result.next()) {
						System.out.println(result.getInt(1));
						if (result.getInt(1) == 1) {
							batchStatus = true;
						}
					}
				}
				if (batchStatus == false) {
					sectionCount = WSDAOUtils.getSectionCount(policyVo);
					WSDAOUtils.deleteSections(policyVo);
					WSDAOUtils.invalidateRecord(policyVo);
				}
				if (policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
				//	WSDAOUtils.deleteSections(policyVo);
					WSDAOUtils.setPremiumVED(policyVo);
					// policyVo=invalidatePreviousRecords(policyVo);
				}

				saveSections(sectionIds, policyVo);

				saveSectionDetails(sectionIds, policyVo);
				if (sectionCount == sectionIds.size()) {
					if (policyVo.getStatus() == 6) {
						policyVo = (PolicyVO) issueQuote(sectionIds, policyVo);
					}
				}
				handleReferral(policyVo);

				// Added for Approarch 1 : procedure to invalidate all previous
				// endt records
				if (policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
					policyVo = invalidatePreviousRecords(policyVo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				WSDAOUtils.invalidateRecord(policyVo);
				e.printStackTrace();
			}
			finally {
				try {
					if (session != null){ session.close(); }
					if (result != null){ result.close(); }
					//Sonar fix for Close the statement in a finally clause
					//if (stmt != null){ stmt.close(); }
					//if (con != null){ con.close(); }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return policyVo;
		}

	}

	private static void handleReferral(PolicyVO policyVO)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (!policyVO.getMapReferralVO().isEmpty()) {
			/*
			 * SaveQuoteHandler handler=new SaveQuoteHandler();
			 * if(!policyVO.getTaskDetails().getPolicyType().equals("31")) {
			 * handler.updateRefStatusInTransQuo(policyVO); }
			 */

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
			// sectionListItr = sectionVOList.iterator();
			Integer[] sectionList = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12 };
			for (Integer sectionId : sectionList) {
				Integer classCode = Integer
						.valueOf(Utils.getSingleValueAppConfig(Utils.concat("SEC_", sectionId.toString())));
				WSDAOUtils.updateVED(policyVO, classCode, sectionId);
				session.flush();
			}
			/*
			 * sectionVO = (SectionVO) sectionListItr.next(); int sectionId =
			 * sectionVO.getSectionId().intValue();
			 * WSDAOUtils.updateVED(policyVO, sectionVO.getClassCode(),
			 * sectionId); session.flush();
			 */

		}

		return policyVO;
	}

	private static BaseVO issueQuote(List<Integer> sectionIds, PolicyVO policyVo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final String[] issueQuoteidentifier = { "ISSUE_QUOTE" };
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		polComHolder.setComments(null);
		polComHolder.setPolicyDetails(policyVo);

		BaseVO baseVO = perfromTask(issueQuoteidentifier, polComHolder);
		BaseVO policyVO = null;
		if (baseVO instanceof PolicyCommentsHolder) {
			policyVO = ((PolicyCommentsHolder) baseVO).getPolicyDetails();
		}
		return policyVO;

	}

	private static void saveSectionDetails(List<Integer> sectionIds, PolicyVO policyVo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String[] arr = new String[sectionIds.size()];
		List<String> identifiers = formIdentifier(sectionIds);
		perfromTask(identifiers.toArray(arr), policyVo);
	}

	private static void saveSections(List<Integer> sectionIds, PolicyVO policyVO)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final String[] sectionIdidentifier = { "SAVE_SELECTED_SECTIONS" };
		SectionList sectionList = new SectionList();
		sectionList.setSelectedSec(sectionIds);
		sectionList.setPolicyLinkingId(policyVO.getPolLinkingId());
		sectionList.setEndorsementNo(Long.valueOf(0));
		perfromTask(sectionIdidentifier, sectionList);

	}

	private static List<Integer> collectSectionIds(PolicyVO policyVO) {
		return sortSections(policyVO.getRiskDetails().stream().map(s -> s.getSectionId()).collect(Collectors.toList()));
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
					baseVO = pASServiceTask.invokeMethodFromBatch(taskInputConfig[0], taskInputConfig[1], baseVO);
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

	public static List<Integer> sortSections(List<Integer> parameterNames) {
		/*
		 * The below code is used to sort the sections is such an order so that
		 * PAR or PL (basic sections always appears before other section on UI)
		 */
		int indxPAR = parameterNames.indexOf(Integer.valueOf(AppConstants.SECTION_ID_PAR));
		int indxPL = parameterNames.indexOf(Integer.valueOf(AppConstants.SECTION_ID_PL));
		if (indxPL != -1) {
			parameterNames.remove(indxPL);
			if (indxPAR != -1)
				parameterNames.add(1, Integer.valueOf(AppConstants.SECTION_ID_PL));
			else
				parameterNames.add(0, Integer.valueOf(AppConstants.SECTION_ID_PL));

		}

		return parameterNames;
	}
}
