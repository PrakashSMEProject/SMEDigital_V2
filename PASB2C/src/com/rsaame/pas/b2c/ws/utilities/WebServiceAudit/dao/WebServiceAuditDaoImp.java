package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.dao;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.dao.utils.NextSequenceValue;

public class WebServiceAuditDaoImp implements WebServiceAuditDao {
	
	private final static String GEN_SEQC_TWA_ID = "SEQ_AUDIT_ID";
	private final static Logger LOGGER = Logger.getLogger(WebServiceAuditDaoImp.class);
	@Override
	public WebServiceAudit addToWebServiceAudit(WebServiceAudit webServiceAudit) {
		if(webServiceAudit!=null)
		{
			LOGGER.info("Saving in Audting Table");
			WebServiceAudit webServiceAudit1 = new WebServiceAudit();
			webServiceAudit1=webServiceAudit;
			System.out.println(webServiceAudit1.toString());
			try{
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
				Session session = ht.getSessionFactory().openSession();
				Transaction tx1 = session.beginTransaction();
				Long aid = NextSequenceValue.getNextSequence(GEN_SEQC_TWA_ID, null, null, null, null,ht);
				webServiceAudit.setTwa_id(aid.intValue());
				LOGGER.info("Saving in Audting Table" + webServiceAudit.toString());
				session.save(webServiceAudit);
				tx1.commit();
				session.close();
				return webServiceAudit;
			}
			catch(Exception e)
			{
				LOGGER.info("Exception while saving in Auditing Table : "+e);
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public Boolean updateWebServiceAudit(WebServiceAudit webServiceAudit) {
		try{
			LOGGER.info("Updating in Audting Tabl_1");
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			Session session = ht.getSessionFactory().openSession();
			Transaction tx1 = session.beginTransaction();
			LOGGER.info("Updating in Audting Tabl_2" + webServiceAudit.toString());
			session.update(webServiceAudit);
			tx1.commit();
			session.close();
			return true;
		}
		catch(Exception e)
		{
			LOGGER.info("Exception while updating in Auditing Table : "+e);
			return false;
		}
	}
	
	@Override
	public Boolean updateWebServiceAuditForUploadDoc(Long twa_id , UploadDocumentResponse uploadDocumentResponse) {
		Session session=null;
		String status;
		try{
			LOGGER.info("Updating in Audting Tabl_3");
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			session = ht.getSessionFactory().openSession();
			Transaction tx1 = session.beginTransaction();
			//String updateQuery = "Update T_TRN_WEBSERVICE_AUDIT set TWA_RESPONSE_XML = "+ "'"+WSAppUtils.getJsonStringFromObjectPrettyPrint(uploadDocumentResponse)+"'"+" where TWA_ID = "+twa_id;
			if(uploadDocumentResponse!= null) {
				status="Success";
				}
				else{
					status="Error";
				}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			//String updateQuery = "Update T_TRN_WEBSERVICE_AUDIT set TWA_RESPONSE_XML = "+ "'"+WSAppUtils.getJsonStringFromObjectPrettyPrint(uploadDocumentResponse)+"'"+" where TWA_ID = "+twa_id;
			
			//Query q = session.createSQLQuery(updateQuery);
			//session.update(updateQuery);
			//q.executeUpdate();
			Connection con = session.connection();
			String updateTableSQL = "UPDATE T_TRN_WEBSERVICE_AUDIT SET TWA_TRANSACTION_RES_TYPE = ? , TWA_RES_END_TIME= ? , TWA_RESPONSE_XML= ? "
	                  + " WHERE TWA_ID = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(updateTableSQL)){
				preparedStatement.setString(1, status);
				preparedStatement.setTimestamp(2, timestamp);
				preparedStatement.setString(3, WSAppUtils.getJsonStringFromObjectPrettyPrint(uploadDocumentResponse));
				preparedStatement.setLong(4, twa_id);

				// execute update SQL stetement
				preparedStatement.executeUpdate();

			}catch (Exception e) {
				e.printStackTrace();
			}
			tx1.commit();
			session.close();
			return true;
		}
		catch(Exception e)
		{
			LOGGER.info("Exception while updating in Auditing Table : "+e);
			return false;
		}
		finally
		{
			session.close();	
		
		}
	}
}
