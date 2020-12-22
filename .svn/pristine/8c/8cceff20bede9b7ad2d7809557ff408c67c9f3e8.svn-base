/**
 * 
 */
package com.rsaame.pas.com.helper;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author Sarath
 * @since WC Monoline
 * Class to set derive details for t_trn_wctpl_unnamed_person
 *
 */
public class DeriveWctplUnnamedPersonDetails extends BaseDervieDetails {

	@Override
	protected void preprocessRecord(String tableInExecution,
			POJOWrapper mappedPojo, POJOWrapper existingRecord,
			TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase) {

		TTrnWctplUnnamedPersonQuo ttrnUnnamed = (TTrnWctplUnnamedPersonQuo) mappedPojo;
		if( Utils.isEmpty(ttrnUnnamed.getWupBasicRiskId())){
			ttrnUnnamed.setWupBasicRiskId(ttrnUnnamed.getId().getWupId());
		}
	}

	@Override
	protected void updateValues(String tableInExecution,
			POJOWrapper mappedPojo, TableData<BaseVO> tableData,
			HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 

	}
}
