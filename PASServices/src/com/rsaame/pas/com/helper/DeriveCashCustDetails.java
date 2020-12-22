package com.rsaame.pas.com.helper;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1006438
 * This class is used to derive the data needed to insert to the t_trn_cash_customer table
 */

public class DeriveCashCustDetails extends BaseDervieDetails{

	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){
		TMasCashCustomerQuo cshQuo = (TMasCashCustomerQuo) mappedPojo;
		cshQuo.setCshInsuredId( polData.getGeneralInfo().getInsured().getInsuredId() );
		// Get the city/region details from t_mas_control
		DataHolderVO<Object[]> controlData = (DataHolderVO<Object[]>) DAOUtils.getControlDetails( ht );
		Object[] contrlolResult = controlData.getData();
		cshQuo.setCshCtyCode( (Integer) contrlolResult[ 0 ] );
		cshQuo.setCshRegCode( (Integer) contrlolResult[ 1 ] );
		cshQuo.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );

		// Set customer Id
		if( !Utils.isEmpty( polData.getScheme().getSchemeCode() ) ){
			cshQuo.setCshCustomerId( DAOUtils.getCustoemrId( ht, polData.getScheme().getSchemeCode() ) );
		}
		// set tot_acc_code baed on customer id
		if(!Utils.isEmpty(cshQuo.getCshCustomerId())) {
			cshQuo.setCshTotAccCode( DAOUtils.getTotAccCode(ht, cshQuo.getCshCustomerId()) );
		}
		if( !Utils.isEmpty( commonVO.getPolicyId() ) ){
			POJOWrapperId tableID = (POJOWrapperId) Utils.getBean( tableInExecution + "_ID" );
			tableID.setPolicyId( commonVO.getPolicyId() );
			tableID.setVSD( commonVO.getVsd() );
			cshQuo.setPOJOWrapperId( tableID );
		}
		cshQuo.setCshCcgCode( SvcConstants.CCG_CODE );
		cshQuo.setCshOcCode( SvcConstants.CASH_OC_CODE );
		cshQuo.setCshCutCode( Byte.valueOf( Utils.getSingleValueAppConfig( "POL_CUT_CODE" ) )  );
		
		//Change due to Renewal Batch Processing - If Scheduler is running, getRsaUser() returns the  null value
		if(Utils.isEmpty(commonVO.getLoggedInUser().getUserId())){
			cshQuo.setCshUserId( ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId() );
		}
		else{
			cshQuo.setCshUserId(Integer.parseInt(commonVO.getLoggedInUser().getUserId()));		
		}
		if(Utils.isEmpty( cshQuo.getCshAIdCardNo() ))
		cshQuo.setCshAIdCardNo(SvcConstants.AS_AGREED_PAYMENT_FREQUENCY);		
	}
	
	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO , SaveCase saveCase){
		// TODO Auto-generated method stub
		
	}

}
