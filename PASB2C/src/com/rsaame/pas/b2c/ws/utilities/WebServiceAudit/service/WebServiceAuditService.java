package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.service;

import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;

public interface WebServiceAuditService {

	public WebServiceAudit AddToWebServiceAudit(WebServiceAudit webServiceAudit);
	public Boolean updateWebServiceAudit(WebServiceAudit webServiceAudit);
	public Boolean updateWebServiceAuditForUploadDoc(Long twa_id , UploadDocumentResponse uploadDocumentResponse);
}