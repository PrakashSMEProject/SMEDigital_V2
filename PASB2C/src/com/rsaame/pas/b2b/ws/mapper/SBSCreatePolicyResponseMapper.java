package com.rsaame.pas.b2b.ws.mapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.handler.DocumentHandler;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.Document;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.PolicyVO;

/* This mapper is to map the SBS CreatePolicy Request to existing object of PolicyVo.
 * All fields in PolicyVO, PaymentVO and CommonVO needs to be identified and mapped here.
 * Any default value setting should be mapped here.
 * Any value from DB needs to be queried here. 
*/

public class SBSCreatePolicyResponseMapper {

	private final static Logger LOGGER = Logger.getLogger(SBSCreatePolicyResponseMapper.class);

	public void mapDataToResponse(Object source1, Object target, HttpServletRequest request) {

		LOGGER.info("Entered mapRequestToVO() method");

		if (source1 != null) {

			LOGGER.info("requestObj is NOT null ");
		}
		if (target != null) {

			LOGGER.info("valueObj is NOT null ");
		}

		CreateSBSPolicyResponse createSBSPolicyResponse = (CreateSBSPolicyResponse) source1;
		DataHolderVO<List<BaseVO>> dataHolderVO = (DataHolderVO<List<BaseVO>>) target;

		List inputData = dataHolderVO.getData();
		PolicyVO policyVO = (PolicyVO) inputData.get( 0 );
		

		//CTS 29.07.2020 JLT Fix - Display policy no instead of concatinated policy no  - Start
		if(!Utils.isEmpty(policyVO.getPolicyNo())){
			createSBSPolicyResponse.setPolicyId(policyVO.getPolicyNo().toString());
		}
		//CTS 29.07.2020 JLT Fix - Display policy no instead of concatinated policy no - Ends
		
		if(!Utils.isEmpty(policyVO.getPolCustomerId())){
			createSBSPolicyResponse.setCustomerId(policyVO.getPolCustomerId().toString());
		}
		/*
		 * Added for JLT Renewal Scope #11424
		 */
		if(!Utils.isEmpty(policyVO.getPolEffectiveDate())) {
			createSBSPolicyResponse.setPolicyYear(SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate()));
			policyVO.setPolicyYear(new Integer(SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate())).shortValue());
		}
		
		//Get Document List
		DocumentHandler documentHandler = new DocumentHandler();
		List<Document> documentList= documentHandler.getDocumentList(policyVO);
		createSBSPolicyResponse.setDocumentId(documentList);
		
	}

}
