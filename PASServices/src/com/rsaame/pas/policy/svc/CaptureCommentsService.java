package com.rsaame.pas.policy.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.policy.dao.ICaptureComments;


public class CaptureCommentsService extends BaseService{
	
	ICaptureComments commentsDao;
	
	public Object invokeMethod(String methodName, Object... args) {
		Object returnValue = null;
		if(methodName.equals("storeComments")){
			returnValue = storeComments((BaseVO)args[ 0 ]);
		}
		return returnValue;
	}

	private BaseVO storeComments(BaseVO baseVO) {
		return commentsDao.storeComments(baseVO);
	}

	/**
	 * @param commentsDao the commentsDao to set
	 */
	public void setCommentsDao(ICaptureComments commentsDao) {
		this.commentsDao = commentsDao;
	}

	
	
}
