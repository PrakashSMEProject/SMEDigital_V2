package com.rsaame.pas.git.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.git.dao.GoodsInTransitSaveDAO;
import com.rsaame.pas.git.dao.IGoodsInTransitSaveDAO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class GoodsInTransitSectionSvc extends BaseService {

    IGoodsInTransitSaveDAO goodsInTransitDAO;

    @Override
    public Object invokeMethod(String methodName, Object... args) {
	BaseVO returnValue = null;

	if ("saveGoodsInTransitSectionSvc".equals(methodName)) {

	    returnValue = saveGoodsInTransitSectionSvc((BaseVO) args[0]);
	}

	return returnValue;
    }

    private BaseVO saveGoodsInTransitSectionSvc(BaseVO baseVO) {

	return ( (GoodsInTransitSaveDAO) goodsInTransitDAO ).save((PolicyVO)baseVO);
    }

    public IGoodsInTransitSaveDAO getGoodsInTransitDAO() {
        return goodsInTransitDAO;
    }

    public void setGoodsInTransitDAO(IGoodsInTransitSaveDAO goodsInTransitDAO) {
        this.goodsInTransitDAO = goodsInTransitDAO;
    }

}
