/**
 * 
 */
package com.rsaame.pas.pa.svc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.com.svc.UWQASaveCommonSvc;
import com.rsaame.pas.dao.cmn.IBaseSaveOperation;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.tasks.svc.TaskSvc;
import com.rsaame.pas.travel.svc.TravelDetailSvc;
import com.rsaame.pas.travel.svc.TravelInsuranceSVC;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PersonDetailsVO;
import com.rsaame.pas.vo.bus.PersonalAccidentPersonVO;
import com.rsaame.pas.vo.bus.PersonalAccidentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author  Raman Shankar
 * @since Phase 4 generalization
 */
public class PersonalDetailsSvc extends BaseService{


	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
	
	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( PersonalDetailsSvc.class );
	
	private BaseSaveSvc baseSaveSvc;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		CommonVO commonVO = null;
		if( args[ 0 ] instanceof PolicyDataVO ){
			//Radar fix
			 ( (PolicyDataVO) args[ 0 ] ).getCommonVO();
		}
		
		
		BaseVO returnValue = null;
		if( SvcConstants.SAVE_PERSONAL_ACCIDENT_INFO.equals( methodName ) ){
			PolicyDataVO polData = (PolicyDataVO) (BaseVO) args[ 0 ];
			
				returnValue = savePersonalAccidentDetails( polData);
			}
		
		//--- code for loadpersonalinfo to be written here
		
		return returnValue;
	}
	

	/**
	 * 
	 * @param baseVo
	 * @return
	 */
	 private BaseVO savePersonalAccidentDetails( BaseVO baseVo ){
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
		PolicyDataVO polDataVo = (PolicyDataVO) baseVo;
		CommonVO commonVO = polDataVo.getCommonVO();
		
		// For Policy data save
		TableData<PolicyDataVO> polTableData = new TableData<PolicyDataVO>();
		polTableData.setTableData( (PolicyDataVO) baseVo );
		
		List<TableData> polList = new ArrayList<TableData>();
		polList.add( polTableData );

		
	/*
			 * Get the required VO from BaseVO
			 */
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
			

			PersonalAccidentVO personalAccidentVO = (PersonalAccidentVO) polDataVo;
			
		
			List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );
			 
			TTrnGaccPersonVOHolderWrapper tTrnGaccPersonVOHolderWrapper=new TTrnGaccPersonVOHolderWrapper();
			
			
			//-- Values of policyDataVO is mapped to TTrnGaccPersonVOHolderWrapper
			tTrnGaccPersonVOHolderWrapper = BeanMapper.map( personalAccidentVO, tTrnGaccPersonVOHolderWrapper );
		
			//--Setting of policyid from commonvo to ttrngaccperson
			
			List<TableData>  tTrnGaccPersonVOHolderList= tTrnGaccPersonVOHolderWrapper.getTTrnGaccPersonVOHolderList();


			for( TableData tTrnGaccPersonVOHolder :tTrnGaccPersonVOHolderList)
			{
				
				TTrnGaccPersonVOHolder gaccTableData=(TTrnGaccPersonVOHolder) tTrnGaccPersonVOHolder.getTableData(); 

				gaccTableData.setGprPolicyId(commonVO.getPolicyId());
				
				
				
			}

			
			
			/*
			 * Prepare the VOHOlder for T_TRN_GACC_PERSON table and set the vo holder to table data
			 */
			if( personalAccidentVO != null ){
					
				TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
				policyTableData.setTableData( polDataVo );

				policyTableDataList.add( policyTableData );

				dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
				dataMap.put( SvcConstants.T_TRN_GACC_PERSON, tTrnGaccPersonVOHolderWrapper.getTTrnGaccPersonVOHolderList() );
				
				dataHolder.setData( dataMap );

				dataHolder=	(DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseSaveSvc.invokeMethod( "baseSave", dataHolder, commonVO );
			}
				
			//-- compare personalaacidentVO and getTTrnGaccPersonVOHolderList in dataholder and set gprID backinto 
			dataMap=dataHolder.getData();
		
			setGprIdToVO(dataMap.get(SvcConstants.T_TRN_GACC_PERSON),polDataVo);
			
			
		return polDataVo;
	}

	 
	 
	/*
	 * @param personalAccidentListVO
	 * @param TTrnGaccPersonVOHolderList
	 * Generated id's are set to the vo
	 * 
	 */
	 private void setGprIdToVO(List<TableData> TTrnGaccPersonVOHolderList,PolicyDataVO polDataVo){

		 PersonalAccidentVO personalAccidentVO = (PersonalAccidentVO) polDataVo;
		 
		 List<PersonalAccidentPersonVO> personalAccidentListVO = personalAccidentVO.getPersonalAccidentPersonVO();

		 if( !Utils.isEmpty( TTrnGaccPersonVOHolderList ) &&  !Utils.isEmpty( personalAccidentListVO ) ){


			 for(TableData tTrnGaccPersonVOHolder :TTrnGaccPersonVOHolderList){
				 
				 if(!Utils.isEmpty( tTrnGaccPersonVOHolder )){


					 TTrnGaccPersonVOHolder gaccTableData=(TTrnGaccPersonVOHolder) tTrnGaccPersonVOHolder.getTableData(); 

					 for(int i = 0; i < personalAccidentListVO.size(); i++){
						 if(gaccTableData.getGprEName().equalsIgnoreCase(personalAccidentListVO.get(i).getPersonDetailsVO().getName())
								 && gaccTableData.getGprPersonId().equalsIgnoreCase(personalAccidentListVO.get(i).getPersonDetailsVO().getPersonID())
								 && gaccTableData.getGprDateOfBirth().equals(personalAccidentListVO.get(i).getPersonDetailsVO().getDateOfBirth())){
							 
							 //--setting the gprId into person details
							 com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
							 personalAccidentListVO.get(i).getPersonDetailsVO().setGprId(converter.getAFromB(tTrnGaccPersonVOHolder.getContentID()));
							 personalAccidentListVO.get(i).getPersonDetailsVO().setValidityStartDate(tTrnGaccPersonVOHolder.getContentVsd());
							 break;
						 }
					 }


				 }
			 }

		 }



	 }
		
	
	
	public BaseSaveSvc getBaseSaveSvc() {
		return baseSaveSvc;
	}


	public void setBaseSaveSvc(BaseSaveSvc baseSaveSvc) {
		this.baseSaveSvc = baseSaveSvc;
	}

	 
	 
}
