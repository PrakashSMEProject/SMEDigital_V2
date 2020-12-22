package com.rsaame.pas.b2b.ws.batch.retriever;

import java.util.Map;

import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.dao.model.EplatformWsStaging;

public interface InputPolicyVoHelper {
	EplatformWsStaging retrievePolicyVO(BatchInput input);
	void savePolicyVO(EplatformWsStaging eplatformWsStaging);
	Map<Long,EplatformWsStaging> retrieveCurrentAndPrevious(BatchInput input);
}
