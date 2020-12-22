package com.rsaame.pas.b2b.ws.batch.retriever;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.dao.model.EplatformWsStaging;

public class DbHelper implements InputPolicyVoHelper {	
	private Session session;
	private static final String TEMPLATE="hibernateTemplate";

	@Override
	public EplatformWsStaging retrievePolicyVO(BatchInput input) {
		session = ((HibernateTemplate)Utils.getBean(TEMPLATE)).getSessionFactory().openSession();
		EplatformWsStaging eplatformWsStaging=(EplatformWsStaging)session.createQuery("from EplatformWsStaging stg" + 
								"   where stg.polQuotationNo =:quotation"
								+ " and stg.id.polEndtId =:endorsementNo"
								+"  and stg.id.polPolicyId =:polPolicyId"
								+"  and stg.polLinkingId =:polLinkingId"
								)
				/*.setLong(com.Constant.CONST_QUOTATION, 2448695)
				.setLong("endorsementNo",input.getEndorsementId())
				.setLong("polPolicyId", 1686412)
				.setLong("polLinkingId", input.getPolLinkingId())*/
				.setLong(com.Constant.CONST_QUOTATION, input.getQuoteNo())
				.setLong("endorsementNo",input.getEndorsementId())
				.setLong("polPolicyId", input.getPolicyId())
				.setLong("polLinkingId", input.getPolLinkingId())
				.uniqueResult();
		return eplatformWsStaging;		
	}

	@Override
	public void savePolicyVO(EplatformWsStaging eplatformWsStaging) {
		session = ((HibernateTemplate)Utils.getBean(TEMPLATE)).getSessionFactory().openSession();
		EplatformWsStaging newObj=new EplatformWsStaging();
		BeanUtils.copyProperties(eplatformWsStaging, newObj);
		((HibernateTemplate)Utils.getBean(TEMPLATE)).saveOrUpdate(newObj);
		this.session.close();
	}
	
	public Map<Long,EplatformWsStaging> retrieveCurrentAndPrevious(BatchInput input){
		Map<Long,EplatformWsStaging> bucket=new HashMap<Long, EplatformWsStaging>();
		session = ((HibernateTemplate)Utils.getBean(TEMPLATE)).getSessionFactory().openSession();
		long maxEndorsementNo=(Long)session.createQuery("select max(a.polEndtNo) as curr from EplatformWsStaging a  where a.polQuotationNo = :quotation")
		.setLong(com.Constant.CONST_QUOTATION, input.getQuoteNo()).uniqueResult();
		
		EplatformWsStaging current=(EplatformWsStaging)session.createQuery("Select stg from EplatformWsStaging stg where stg.polQuotationNo =:quotation and stg.polEndtNo =:maxEndorsementNo")
				.setLong(com.Constant.CONST_QUOTATION, input.getQuoteNo())
				.setLong("maxEndorsementNo", maxEndorsementNo).uniqueResult();
		
		long prevEndorsementNo=maxEndorsementNo-1;
		String status[] = Utils.getMultiValueAppConfig("VERSION_STATUS");
		/*
		 * Added to get the current record if status is pending in staging table, in that case it will have only 1 record.
		 */
		if(current.getPolStatus().equals(status[0]) || current.getPolStatus().equals(status[1])) {
			EplatformWsStaging previous=(EplatformWsStaging)session.createQuery("Select stg from EplatformWsStaging stg where stg.polQuotationNo =:quotation and stg.polEndtNo =:prevEndorsementNo")
					.setLong(com.Constant.CONST_QUOTATION, input.getQuoteNo())
					.setLong("prevEndorsementNo", prevEndorsementNo).uniqueResult();
			bucket.put(current.getPolEndtNo(), current);
			bucket.put(previous.getPolEndtNo(), previous);
			return bucket;	
		}
		
		bucket.put(current.getPolEndtNo(), current);
		return bucket;		
	}

}
