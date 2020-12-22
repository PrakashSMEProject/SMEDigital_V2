package com.rsaame.pas.home.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

public class HomeContentSaveSVC extends BaseService{
	
	private BaseSaveSvc baseSvc;
	private HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc;
	private final static Logger LOGGER = Logger.getLogger( HomeContentSaveSVC.class );
	private BaseLoadSvc baseLoadSvc;

	/**
	 * @return the baseSvc
	 */
	public BaseSaveSvc getBaseSvc(){
		return baseSvc;
	}

	/**
	 * @param baseSvc the baseSvc to set
	 */
	public void setBaseSvc( BaseSaveSvc baseSvc ){
		this.baseSvc = baseSvc;
	}

	public HomeCoverDetailsLoadSvc getBaseCoverDetailsLoadSvc(){
		return baseCoverDetailsLoadSvc;
	}

	public void setBaseCoverDetailsLoadSvc( HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc ){
		this.baseCoverDetailsLoadSvc = baseCoverDetailsLoadSvc;
	}

	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
	/**
	 * DataHolderVO to be input to the baseSaveDao, contains a linkedHashmap with key table name  and value List of tabledata 
	 */
	@SuppressWarnings( "rawtypes" )
	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();

	/**
	 * @param homeInsuranceVO
	 * @param policyDataVo
	 * @return
	 * Creating list of contents to be saved and calling base save operation. 
	 */
	public BaseVO saveContents( BaseVO homeInsuranceVO, BaseVO policyDataVo ){
		LOGGER.info( "Entering Home Content Save SVC" );
		//Get the list of cover details VO in homeInsuranceVO
		HomeInsuranceVO homeVo = (HomeInsuranceVO) homeInsuranceVO;
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		//Get policyDataVO
		List<TableData> polList = new ArrayList<TableData>();
		PolicyDataVO policyDataVO = (PolicyDataVO) policyDataVo;
		TableData<PolicyDataVO> polTableData = new TableData<PolicyDataVO>();
		polTableData.setTableData( policyDataVO );
		polList.add( polTableData );

		//Radar fix
		BuildingDetailsVO buildingDetailsVO = null;//new BuildingDetailsVO();
		List<TableData> coverListToBeSaved = new ArrayList<TableData>();
		TableData<CoverDetailsVO> tData;
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		if( !Utils.isEmpty( homeVo ) && !Utils.isEmpty( homeVo.getBuildingDetails() ) ){
			buildingDetailsVO = homeVo.getBuildingDetails();
		}
		else{
			throw new BusinessException( "cmn.contentsNotFound", null, "Unexpected exception occurred. Please contact Administrator." );
		}

		double totalSI = SvcUtils.getTotalSIForHome( homeVo );
		/* coverDetailsVO list contains default covers and additional covers, but only default covers have to be saved in T_TRN_CONTENT table
		 * Iterate through the list and add only the covers whose cover code is 1, into the ttrnContentList
		 * call the base Save Dao.
		 */
		if( !Utils.isEmpty( homeVo.getCovers() ) ) {
			for (CoverDetailsVO cover : homeVo.getCovers()) {
				cover.getRiskCodes().setBasicRskId(
						buildingDetailsVO.getRiskCodes().getBasicRskId());
				
				if( Double.valueOf( totalSI ).longValue() <= Long.valueOf( Utils.getSingleValueAppConfig( "HOME_SI_LIMIT" ) ) ){
					cover.setRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_LESSER" ) ) );
				}
				else{
					cover.setRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_GREATER" ) ) );
				}
				
				if (!Utils.isEmpty(cover.getCoverCodes())
						&& !Utils.isEmpty(cover.getCoverCodes().getCovCode())
						&& cover.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {
					tData = new TableData<CoverDetailsVO>();
					tData.setTableData(cover);
					coverListToBeSaved.add(tData);
				}
			}
		}
		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( homeVo.getCommonVO().getIsQuote() );
		inputVO.setLocCode( homeVo.getCommonVO().getLocCode() );
		inputVO.setEndtId( homeVo.getCommonVO().getEndtId() );
		inputVO.setQuoteNo( homeVo.getCommonVO().getQuoteNo() );
		inputVO.setPolicyNo( homeVo.getCommonVO().getPolicyNo() );
		
		dataToLoad.put( SvcConstants.T_TRN_CONTENT, CoverDetailsVO.class );
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad",
				inputVO, dataToLoad );
		List<TableData> coverDetailsVOs = dataHolderVO.getData().get( SvcConstants.T_TRN_CONTENT );
		if( !Utils.isEmpty( coverDetailsVOs ) ){
			coverListToBeSaved = SvcUtils.updateToBeDeletedCovers( coverDetailsVOs, coverListToBeSaved );
		}
		if (!Utils.isEmpty(coverListToBeSaved)) {
			dataMap.put(SvcConstants.T_TRN_POLICY, polList);
			dataMap.put(SvcConstants.T_TRN_CONTENT, coverListToBeSaved);
			toBeSaved.setData(dataMap);
			toBeSaved = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseSvc
					.invokeMethod("baseSave", toBeSaved, homeVo.getCommonVO());

			// Map id values saved in the table to homeInsuranceVO
			mapKeyValuesToVO(toBeSaved, homeVo);
		}
		LOGGER.info( "Exiting Home Content Save SVC" );
		return homeVo;

	}

	/**
	 * 
	 * @param toBeSaved2
	 * @param homeInsuranceVO
	 * Generated id's are set to the vo
	 */
	private void mapKeyValuesToVO( DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved2, HomeInsuranceVO homeVO ){

		LinkedHashMap<String, List<TableData>> dataMap = toBeSaved2.getData();
		List<TableData> contentTableData = dataMap.get( SvcConstants.T_TRN_CONTENT );
		List<CoverDetailsVO> covers = homeVO.getCovers();
		Long contentCoverRskId = null;
		// Additional cover risk id to be set to main contents risk id
		for( TableData<CoverDetailsVO> contentData : contentTableData ){
			if( contentData.getTableData().getRiskCodes().getRiskCat() == SvcConstants.DEFAULT_COVER_RSK_CAT
					&& contentData.getTableData().getRiskCodes().getRiskType() == SvcConstants.CONTENT_RSK_TYPE_CODE 
					&& !Utils.isEmpty( contentData.getTableData().getCoverCodes() )
					&& contentData.getTableData().getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE ) {
				contentCoverRskId = contentData.getContentID();
			}

		}
		if( !Utils.isEmpty( covers ) &&  !Utils.isEmpty( contentCoverRskId )  ){
			//Setting the sequence generated content id to risk id field in coverdetails vo
			for( CoverDetailsVO cover : covers ){
				//For additional covers, cover code will be other than 1.
				if( cover.getCoverCodes().getCovCode() != SvcConstants.DEFAULT_COVER_CODE ){
					//Setting contentId of a default cover to RskId of additional cover
					cover.getRiskCodes().setRskId( new BigDecimal( contentCoverRskId ) );
				}
				//For default covers, cover code will always be 1
				else{
					for( TableData<CoverDetailsVO> contentData : contentTableData ){
						if( !Utils.isEmpty( contentData.getTableData().getCoverCodes() )
								&& cover.getCoverCodes().getCovCode() == contentData.getTableData().getCoverCodes().getCovCode()
								&& cover.getRiskCodes().getRiskCat() == contentData.getTableData().getRiskCodes().getRiskCat()
								&& cover.getRiskCodes().getRiskType() == contentData.getTableData().getRiskCodes().getRiskType()
								&& cover.getCoverName().equals(contentData.getTableData().getCoverName())){
							cover.getRiskCodes().setRskId( new BigDecimal( contentData.getContentID() ) );
							break;
						}
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		if( "saveContents".equals( methodName ) ){
			saveContents( (BaseVO) args[ 0 ], (BaseVO) args[ 1 ] );
		}
		return args[ 0 ];
	}
}
