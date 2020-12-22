package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.dao;

import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;

public interface WebServiceAuditDao {

	public WebServiceAudit addToWebServiceAudit(WebServiceAudit webServiceAudit);
	public Boolean updateWebServiceAudit(WebServiceAudit webServiceAudit);
	public Boolean updateWebServiceAuditForUploadDoc(Long twa_id , UploadDocumentResponse uploadDocumentResponse) ;
}
