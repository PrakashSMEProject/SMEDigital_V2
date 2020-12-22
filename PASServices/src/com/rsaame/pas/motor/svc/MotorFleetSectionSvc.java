package com.rsaame.pas.motor.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.rsaame.pas.motor.dao.IMotorFleetSectionDAO;

/**
 * 
 * @author m1014438
 * 
 */

public class MotorFleetSectionSvc extends BaseService {

	IMotorFleetSectionDAO motorFleetSaveDAO, motorFleetLoadDAO;

	public IMotorFleetSectionDAO getMotorFleetSaveDAO() {
		return motorFleetSaveDAO;
	}

	public void setMotorFleetSaveDAO(IMotorFleetSectionDAO motorFleetSaveDAO) {
		this.motorFleetSaveDAO = motorFleetSaveDAO;
	}

	public IMotorFleetSectionDAO getMotorFleetLoadDAO() {
		return motorFleetLoadDAO;
	}

	public void setMotorFleetLoadDAO(IMotorFleetSectionDAO motorFleetLoadDAO) {
		this.motorFleetLoadDAO = motorFleetLoadDAO;
	}

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

}
