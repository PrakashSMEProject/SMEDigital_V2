package com.rsaame.pas.amend.svc;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.List;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;

/**
 * @author m1020278
 *
 */
public interface IPolicyCancellationDao {

	public BaseVO getCancelPolRefundPremium(BaseVO baseVO);

	public BaseVO processCancelPolicy(BaseVO baseVO);
	
	public List<EndorsmentVO> getEndorsementsForCancelPolicy( PolicyDataVO policyDataVO );
}
