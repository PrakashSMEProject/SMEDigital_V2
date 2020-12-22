/**
 * 
 */
package com.rsaame.pas.com.helper;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class DeriveUWQuestions extends BaseDervieDetails{

	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase ){
		TTrnUwQuestionsQuo uwQuestionsQuo = (TTrnUwQuestionsQuo) existingRecord;
		if(!Utils.isEmpty( existingRecord )){
		/*Hacking Endt id for quote versioning. Done in Phase 3*/
		uwQuestionsQuo.getId().setEndtId( getLatestEndId(uwQuestionsQuo, ht,commonVO)  );
		}
	}

	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){
		TTrnUwQuestionsQuo uwQuestionsQuo = (TTrnUwQuestionsQuo) mappedPojo;
		uwQuestionsQuo.getId().setEndtId( getLatestEndId(uwQuestionsQuo, ht,commonVO)  );
	}
	
	private Long getLatestEndId(TTrnUwQuestionsQuo uwQuestionsQuo, HibernateTemplate ht,CommonVO commonVO){
		Long oldEndtId = Long.valueOf(0);
		String fetchQuery = null;
		if(commonVO.getIsQuote()){
			fetchQuery = QueryConstants.FETCH_LATEST_ENDT_ID_QUO;
		} else {
			fetchQuery = QueryConstants.FETCH_LATEST_ENDT_ID_POL;
		}
		Query query = ht.getSessionFactory().getCurrentSession().createSQLQuery( fetchQuery);
        query.setParameter( 0, uwQuestionsQuo.getId().getUqtPolPolicyId());
        query.setParameter( 1, uwQuestionsQuo.getId().getUqtUwqCode() );
        query.setParameter( 2, uwQuestionsQuo.getId().getUqtLocId());        
        List<Object[]> result = (List<Object[]>) query.list();
        if(!Utils.isEmpty( result) ){
        	Object [] latestRecord = result.get( 0 );
        	if(!Utils.isEmpty( latestRecord )){
        		oldEndtId = Long.valueOf( latestRecord[0].toString() );		
        	}
        }
        /*
		 * adding this  endt id as part of fix given
		 * in 3.2 release for underwriting questions. 
		 */
        else {
        	oldEndtId=commonVO.getEndtId();
        }
        return oldEndtId;
	}
}
