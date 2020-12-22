package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.service;

import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.dao.WebServiceAuditDao;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.dao.WebServiceAuditDaoImp;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;

public class WebServiceAuditServiceImp implements WebServiceAuditService {

	@Override
	public WebServiceAudit AddToWebServiceAudit(WebServiceAudit webServiceAudit) {
		WebServiceAuditDao webServiceAuditDao = new WebServiceAuditDaoImp();
		try {
			return webServiceAuditDao.addToWebServiceAudit(webServiceAudit);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public Boolean updateWebServiceAudit(WebServiceAudit webServiceAudit) {
		WebServiceAuditDao webServiceAuditDao = new WebServiceAuditDaoImp();
		try {
			return webServiceAuditDao.updateWebServiceAudit(webServiceAudit);
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public Boolean updateWebServiceAuditForUploadDoc(Long twa_id, UploadDocumentResponse uploadDocumentResponse) {
		WebServiceAuditDao webServiceAuditDao = new WebServiceAuditDaoImp();
		try {
			return webServiceAuditDao.updateWebServiceAuditForUploadDoc(twa_id, uploadDocumentResponse);
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
