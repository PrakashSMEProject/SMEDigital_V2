/**
 * 
 */
package com.rsaame.pas.com.helper;

import java.math.BigDecimal;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1017029
 *
 */
public class DeriveTtrnContentDetails extends BaseDervieDetails{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#updateValues(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 */
	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){
		TTrnContentQuo content = (TTrnContentQuo) mappedPojo;
		// Values from ttrnPolicy,required in ttrnContent are set here 
		content.setCntPolicyId( polData.getPolicyId() );
		content.setCntEndtId( polData.getEndtId() );
		//content.setValidityStartDate( polData.getValidityStartDate() );
		content.setCntStartDate( polData.getScheme().getEffDate());
		content.setCntEndDate( polData.getScheme().getExpiryDate() );
		content.setCntMplFirePerc( new BigDecimal( Utils.getSingleValueAppConfig( "BLD_MPL_FIRE_PERC" ) ));
	}
	
	
	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO , SaveCase saveCase){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
		
	}

}
