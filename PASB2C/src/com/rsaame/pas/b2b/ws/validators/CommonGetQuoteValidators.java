package com.rsaame.pas.b2b.ws.validators;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

public class CommonGetQuoteValidators {
	public List<SBSWSValidators> validate(PolicyVO policyVO, String quotationNo, List<SBSWSValidators> sbsWSValidatorsList, String policyNo,String expiryPolicyYear) {
		
		if (!policyVO.getIsRenewal()) {

			if (Utils.isEmpty(quotationNo)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
						com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_001", SBSErrorCodes.ERROR.name(),
						com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else {

				if (!ValidationUtil.isNumeric(quotationNo)) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
							com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_003", SBSErrorCodes.ERROR.name(),
							com.Constant.CONST_BUSINESS);
					;
					sbsWSValidatorsList.add(SBSbusinessValidators);
				} else if ((Integer.parseInt(quotationNo)) <= 0) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
							com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_004", SBSErrorCodes.ERROR.name(),
							com.Constant.CONST_BUSINESS);
					;
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}

			/* Start DB fields validation */
			if (!Utils.isEmpty(quotationNo)
					&& (ValidationUtil.isNumeric(quotationNo) && ((Integer.parseInt(quotationNo)) > 0))) {
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
				Session session = ht.getSessionFactory().openSession();
				/*Query endtIDStagquery = session.createQuery(
						"SELECT max(id.polEndtId) FROM EplatformWsStaging e  WHERE e.polQuotationNo=:quotationNo");
				endtIDStagquery.setParameter("quotationNo", Long.parseLong(quotationNo));
				if (null == endtIDStagquery.uniqueResult()) {
					if (isFlag) {
						isFlag = false;
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
								com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_096", SBSErrorCodes.ERROR.name(),
								com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
					}
				}
*/
				policyVO.setQuoteNo(Long.parseLong(quotationNo));
				Query endtIDTransquery = session.createSQLQuery(
						"SELECT pol_endt_id,pol_status FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo and pol_issue_hour=3 and pol_prepared_by=:prepared_by and pol_validity_expiry_date=:expiryDate order by pol_endt_id desc");
				endtIDTransquery.setParameter("quotationNo", policyVO.getQuoteNo());
				endtIDTransquery.setParameter("prepared_by", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
				endtIDTransquery.setParameter("expiryDate", SvcConstants.EXP_DATE);
				List<Object> results = endtIDTransquery.list();
				if (Utils.isEmpty(results)) {
						SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
								com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_096", SBSErrorCodes.ERROR.name(),
								com.Constant.CONST_BUSINESS);
						sbsWSValidatorsList.add(SBSbusinessValidators);
				}else {
					Object[] result =(Object[]) results.get(0);
					policyVO.setEndtId(( (BigDecimal) result[0]).longValue());
					policyVO.setStatus(( (BigDecimal) result[1]).intValue());
				}
			}
		} else {
			if (Utils.isEmpty(policyNo) || Utils.isEmpty(expiryPolicyYear)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
						com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_001", SBSErrorCodes.ERROR.name(),
						com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else if (!ValidationUtil.isNumeric(policyNo) || !ValidationUtil.isNumeric(expiryPolicyYear)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
						com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_003", SBSErrorCodes.ERROR.name(),
						com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else if ((Integer.parseInt(policyNo)) <= 0 || (Integer.parseInt(expiryPolicyYear)) <= 0 || (expiryPolicyYear.length()!=4)) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
						com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_005", SBSErrorCodes.ERROR.name(),
						com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			} else {
				
				policyVO.setPolicyNo(Long.parseLong(policyNo));
				policyVO.setPolicyYear(Short.parseShort(expiryPolicyYear));
				List<TTrnPolicyQuo> policy = null;
				policy = WSDAOUtils.getRenewalQuoteFromPolicyNo(policyVO);
				if(Utils.isEmpty(policy)) {
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
							com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_102", SBSErrorCodes.ERROR.name(),
							com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}else {
					policyVO.setEndtId(policy.get(0).getId().getEndtId());
					policyVO.setStatus(policy.get(0).getStatus());
					policyVO.setQuoteNo(policy.get(0).getPolQuotationNo());
				}
				
			}
		}
		
		/* End DB fields validation*/
		
		return sbsWSValidatorsList;

	}
}
