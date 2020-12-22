package com.rsaame.pas.b2b.ws.mapper;

import javax.servlet.http.HttpServletRequest;

public interface BaseRequestToVoMapper {

	public void mapRequestToVO(Object requestObj,Object valueObj,HttpServletRequest request) throws Exception;

}
