package com.rsaame.pas.b2b.ws.validators;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.vo.bus.PolicyVO;

public class RetrievePolicyByPolicyNoValidator {
	
	public List<SBSWSValidators> validate(String policyNo, Short policyYear, List<SBSWSValidators> sbsWSValidatorsList,PolicyVO policyVO) {

		if (Utils.isEmpty(policyNo)) {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICY_NUMBER, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}else{
			
			if (!ValidationUtil.isNumeric(policyNo)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICY_NUMBER, "SBSWS_ERR_003",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else if ((Integer.parseInt(policyNo))<=0) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICY_NUMBER, "SBSWS_ERR_004",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
		if (Utils.isEmpty(policyYear)) {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYYEAR, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}else{
			
			if (!ValidationUtil.isNumeric(policyYear.toString())) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYYEAR, "SBSWS_ERR_003",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else if (policyYear<=0) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYYEAR, "SBSWS_ERR_004",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
		if(!Utils.isEmpty(policyNo) && !Utils.isEmpty(policyYear)) {
			List<TTrnPolicyQuo> policy = null;
			policyVO.setPolicyNo(Long.parseLong(policyNo));
			policyVO.setPolicyYear(policyYear);
			policy = WSDAOUtils.getPolicyDetailsByPolicyNoAndYear(policyVO);
			if(Utils.isEmpty(policy)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_104",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
		return sbsWSValidatorsList;
	}
}
