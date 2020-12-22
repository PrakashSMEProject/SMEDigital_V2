package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.handler;

import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.service.WebServiceAuditService;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.service.WebServiceAuditServiceImp;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;

public class WebServiceAuditHandler {

	public WebServiceAudit saveToWebServiceAudit(WebServiceAudit webServiceAudit)
	{
		WebServiceAuditService webServiceAuditService = new WebServiceAuditServiceImp();
		try {
			return webServiceAuditService.AddToWebServiceAudit(webServiceAudit);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public Boolean updateWebServiceAudit(WebServiceAudit webServiceAudit)
	{
		WebServiceAuditService webServiceAuditService = new WebServiceAuditServiceImp();
		try {
			return webServiceAuditService.updateWebServiceAudit(webServiceAudit);
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public Boolean updateWebServiceAuditForUploadDoc(Long twa_id, UploadDocumentResponse uploadDocumentResponse) {
		WebServiceAuditService webServiceAuditService = new WebServiceAuditServiceImp();
		try {
			return webServiceAuditService.updateWebServiceAuditForUploadDoc(twa_id, uploadDocumentResponse);
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
