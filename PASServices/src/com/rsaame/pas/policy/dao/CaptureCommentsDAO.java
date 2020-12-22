package com.rsaame.pas.policy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
/**
 *Since Phase1 
 *DAO class used for storing the comments captured.
 *
 */
public class CaptureCommentsDAO extends BaseDBDAO implements ICaptureComments{
	
	
	
	public  BaseVO storeComments(BaseVO baseVO){
		

		CommentsVO commentsVO = (CommentsVO)baseVO;
		List<TTrnPolicyQuo> policies= null;
		

		if( !Utils.isEmpty( commentsVO.getLob() ) && !( commentsVO.getLob().equals( LOB.SBS ))){

			policies= getHibernateTemplate().find("from TTrnPolicyQuo where id.polPolicyId=? and id.polEndtId=?",commentsVO.getPocPolicyId(),commentsVO.getPocEndtId());

		}
		else{
			Long basePolId = DAOUtils.getBaseSectionPolicyId(commentsVO.getPocPolicyId() ,commentsVO.getPocEndtId(),commentsVO.getIsQuote(),getHibernateTemplate());
			if(basePolId!=null){
			policies= getHibernateTemplate().find("from TTrnPolicyQuo where id.polPolicyId=? and id.polEndtId=?",basePolId,commentsVO.getPocEndtId());
			}
	
		}

		Session session = null;
		SQLQuery sqlQuery = null;
		
		session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		sqlQuery = session.createSQLQuery("insert into t_trn_policy_comments(POC_POLICY_ID,POC_ENDT_ID,POC_REASON_CODE,POC_COMMENTS,POC_POLICY_STATUS,POC_DATE,POC_PREPARED_BY,POC_PREPARED_DT,POC_MODIFIED_BY,POC_MODIFIED_DT,POC_DOCUMENT_CODE)" +
				" values(:polId,:endId,:reasonCode,:comment,:status,:pocDate,:preparedBy,:preparedDt,:modifiedBy,:modifiedDt,:docCode)");
		
		
		if(!Utils.isEmpty( policies )){			/* Added if condition for policies null check - sonar violation fix */
		for(com.rsaame.pas.dao.model.TTrnPolicyQuo policy:policies){
			
			sqlQuery.setLong( "polId", policy.getId().getPolPolicyId() );
			sqlQuery.setLong( "endId", policy.getId().getPolEndtId() );
			if(!Utils.isEmpty( commentsVO.getReasonCode() )){
				sqlQuery.setShort( com.Constant.CONST_REASONCODE, commentsVO.getReasonCode() );
			} else if (!Utils.isEmpty( commentsVO.getPolicyStatus() ) && commentsVO.getPolicyStatus().equals(Byte.valueOf(Utils.getSingleValueAppConfig( "QUOTE_SOFT_STOP" )))){
				/*
				 * Renewals Feedback : will check if status is 99(softstop) then will set null as
				 * reason code.
				 */
				sqlQuery.setString(com.Constant.CONST_REASONCODE, null);
			}else {
				sqlQuery.setShort( com.Constant.CONST_REASONCODE, Short.parseShort(Utils.getSingleValueAppConfig( "QUOTE_REASON_CODE" )));
			}
			
			if(!Utils.isEmpty(commentsVO.getComment() )){
				sqlQuery.setString( "comment",commentsVO.getComment() );		
			} else{
				sqlQuery.setString( "comment",null );
			}
			
			if(!Utils.isEmpty( commentsVO.getPolicyStatus() )){
				sqlQuery.setByte( "status",commentsVO.getPolicyStatus() );
			} else {
				sqlQuery.setByte( "status",policy.getPolStatus());
			}
			
			sqlQuery.setTimestamp( "pocDate", new java.sql.Timestamp(getSysDate().getTime()) );
			
			if(!Utils.isEmpty( policy.getPolPreparedBy() )){
				sqlQuery.setInteger( "preparedBy", policy.getPolPreparedBy() );
			} else {
				sqlQuery.setInteger( "preparedBy", SvcUtils.getUserId(commentsVO));
			}
			
			if(!Utils.isEmpty( policy.getPolPreparedDt() )){
				sqlQuery.setDate( "preparedDt", new java.sql.Date(policy.getPolPreparedDt().getTime()) );
			} else {
				sqlQuery.setDate( "preparedDt", null );
			} 
			
			if(!Utils.isEmpty(  policy.getPolModifiedBy() )){
				sqlQuery.setInteger( "modifiedBy", policy.getPolModifiedBy());
			} else {
				sqlQuery.setInteger( "modifiedBy", SvcUtils.getUserId(commentsVO));
			} 
			
			if(!Utils.isEmpty(  policy.getPolModifiedDt() )){
				sqlQuery.setDate( "modifiedDt", new java.sql.Date(policy.getPolModifiedDt().getTime()) );
			} else {
				sqlQuery.setDate( "modifiedDt", null );
			} 
			
			if(!Utils.isEmpty( policy.getPolDocumentCode() )){
				sqlQuery.setByte( "docCode", Byte.parseByte(Short.toString(policy.getPolDocumentCode())));
			} else {
			
				sqlQuery.setByte( "docCode",(Byte) null);
				
			} 
			
			sqlQuery.executeUpdate();
			
		}
		}
		return commentsVO;
		
	}
	
	/*private TTrnPolicyComments mapPolicyComments(TTrnPolicy policy) {
		
		TTrnPolicyComments policyComments = new TTrnPolicyComments();
		Short docId = policy.getPolDocumentCode();
		policyComments.setPocDocumentCode(docId.byteValue());
		policyComments.setPocModifiedBy(policy.getPolModifiedBy());
		policyComments.setPocModifiedDt(policy.getPolModifiedDt());
		policyComments.setPocPolicyStatus(policy.getPolStatus());
		policyComments.setPocPreparedBy(policy.getPolPreparedBy());
		policyComments.setPocPreparedDt(policy.getPolPreparedDt());
		policyComments.setPocReasonCode(policy.getPolReasonCode());
		policyComments.setPocPolicyId( policy.getId().getPolPolicyId() );
		policyComments.setPocEndtId( policy.getId().getPolEndtId());
		
		return policyComments;
	}*/


}
