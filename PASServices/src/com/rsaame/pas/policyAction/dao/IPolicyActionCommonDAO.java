package com.rsaame.pas.policyAction.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IPolicyActionCommonDAO {

		BaseVO declineQuote( BaseVO baseVO );

		BaseVO rejectQuote( BaseVO baseVO );

		BaseVO approveQuote( BaseVO baseVO );

	}
