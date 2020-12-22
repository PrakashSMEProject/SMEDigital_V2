package com.rsaame.pas.gpa.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuoId;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
/**
 * 
 * @author m1019834
 *
 */
public class GroupPersonalAccidentSaveDAO extends BaseSectionSaveDAO implements IGroupPersonalAccidentSectionDAO {
/**
 * This class file is used for GPA specific operations like Save, Update and Delete.
*/
private static final Logger LOGGER = Logger.getLogger(GroupPersonalAccidentSaveDAO.class);
/**
 * General global variable declaration arena to GroupPersonalAccidentSaveDAO,
 * Values are fetched from appconfig.properties file.
 */
private static final Integer GROUP_PERSONAL_ACCIDENT_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_SECTION"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_CLASS_CODE = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_CLASS"));
/**(non-Javadoc).*/
private static final Integer PAR_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("PAR_SECTION"));
/**(non-Javadoc).*/
private static final Integer PL_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("PL_SECTION"));
/**(non-Javadoc).*/
private static final Integer GROUP_PERSONAL_ACCIDENT_RISK_CODE = Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RISK_CODE"));
/**(non-Javadoc).*/
private static final Integer GROUP_PERSONAL_ACCIDENT_BASIC_RISK_CODE = Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_BASIC_RISK_CODE"));
/**(non-Javadoc).*/
private static final Integer GROUP_PERSONAL_ACCIDENT_RI_RISK_CODE = Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RI_RISK_CODE"));
/**(non-Javadoc).*/
private static final Integer GROUP_PERSONAL_ACCIDENT_ENDT_ID = Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_ENDT_ID"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_COVER_CODE"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_COVER_TYPE = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_COVER_TYPE"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_RISK_CATEGORY = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RISK_CATEGORY"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_RISK_SUB_CATEGORY = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RISK_SUB_CATEGORY"));
/**(non-Javadoc).*/
private static final Short GROUP_PERSONAL_ACCIDENT_RISK_TYPE_CODE = Short.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RISK_TYPE_CODE"));
/**(non-Javadoc).*/
private static final String GACC_UNNAMED_PERSON_SEQ = "SEQ_GACC_UNNAMED_PERSON_ID";
/**(non-Javadoc).*/
private static final String GACC_PERSON_SEQ = "SEQ_GACC_PERSON_ID";
/**(non-Javadoc).*/	
private static final Long ZERO_CONSTANT = 0L;
/**
 * 	Empty Constructor.
 */	
public GroupPersonalAccidentSaveDAO() {
	
	//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}

/** (non-Javadoc).
 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getSectionId()
 * @return the int
*/
@Override
protected int getSectionId() {
	return GROUP_PERSONAL_ACCIDENT_SECTION_ID;
}

/** (non-Javadoc).
* @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getClassCode()
* @return the int
*/
@Override
protected int getClassCode() {
	return GROUP_PERSONAL_ACCIDENT_CLASS_CODE;
}

/** (non-Javadoc).
 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#saveSection(com.mindtree.ruc.cmn.base.BaseVO)
 * @param input to accept value of type BaseVO
 * @return the BaseVO
*/
@Override
protected BaseVO saveSection(BaseVO input) {
LOGGER.info("GroupPersonalAccident : Inside Save Section");
/**
 * Saving the GroupPersonalAccident data.
*/
		
		PolicyVO policyVO = (PolicyVO) input;
		/** Tables involved 
		 * 1.T_TRN_GACC_PERSON/QUO
		 * 2.T_TRN_GACC_UNNAMED_PERSON/QUO
		 * 3.T_TRN_PREMIUM/QUO.
		 */
		
		/* Let us get the system date right now and use from here on for this transaction. */
		ThreadLevelContext.set(SvcConstants.TLC_KEY_SYSDATE, new Timestamp(System.currentTimeMillis()));
		
		SectionVO sectionVO = PolicyUtils.getSectionVO(policyVO, SvcConstants.SECTION_ID_GROUP_PERSONAL_ACCIDENT);
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing(sectionVO);
		GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) PolicyUtils.getRiskGroupDetails(locationDetails, sectionVO);
		
		/**
		 *  Fetch building details of basic risk section.
		 */
		POJO[] buildingPojo = handleBuilding(policyVO, locationDetails);
		
		if(!Utils.isEmpty(groupPersonalAccidentVO)){
			// Handle the Gacc_Unnnammed Person data if insuring for named employees

			if(!Utils.isEmpty(groupPersonalAccidentVO.getGpaUnnammedEmpVO()) || !Utils.isEmpty(groupPersonalAccidentVO.getIsToBeDeleted())) {
				
				handleUnnammedPerson(policyVO, sectionVO, locationDetails, groupPersonalAccidentVO, buildingPojo);
			 
			}
			//	Handle gacc_Nammed person .
			if(!Utils.isEmpty(groupPersonalAccidentVO.getGpaNammedEmpVO()) || !Utils.isEmpty(groupPersonalAccidentVO.getIsToBeDeleted())){
				
				handleNammedPerson(policyVO, sectionVO, locationDetails, groupPersonalAccidentVO, buildingPojo);
			}
		}
		
		return policyVO;
	}
/**
 * 
 * @param policyVO to accept values like PolicyVO
 * @param locationVO to accept values like PolicyVO
 * @return the POJO
 */

private POJO[] handleBuilding(PolicyVO policyVO, LocationVO locationVO){
/** Check if par or pl is present, basicSectionID will contain the section id of either par or pl */
Integer basicSectionID = null;
		if(isSectionPresent(PAR_SECTION_ID, policyVO)){
			basicSectionID = PAR_SECTION_ID;
		}
		else if(isSectionPresent(PL_SECTION_ID, policyVO)){
			basicSectionID = PL_SECTION_ID;
		}
		else{
			throw new BusinessException("pas.basicSection.mandatory", null, "Either Par or Pl has to be selected to proceed further");
		}

		SectionVO basicSection = PolicyUtils.getSectionVO(policyVO, basicSectionID);

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;

		/** If PAR is the basic section, then we have to get the PAR building record and use it for the 
		 * construction of the T_TRN_GACC_BUILDING record POJO. If PL is the basic section, then we 
		 * have to use the WCTPL Premise record for this. */
		if(!Utils.isEmpty(basicSection) && (basicSectionID.equals(PAR_SECTION_ID))){
			if(!Utils.isEmpty(locationVO.getRiskGroupId())){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				  buildingQuo = (TTrnBuildingQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_BUILDING ,policyVO.getAppFlow(), getHibernateTemplate(), false, ((Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID)), basicSection.getPolicyId(), Long.valueOf(locationVO.getRiskGroupId()));
				  ThreadLevelContext.set(SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId());
			}
			if(Utils.isEmpty(buildingQuo)){
				throw new BusinessException("PAR_SECTION_ID : pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory");
			}
		}
		else if(!Utils.isEmpty(basicSection) && basicSectionID.equals(PL_SECTION_ID)){
			PublicLiabilityVO plDetails = (PublicLiabilityVO) basicSection.getRiskGroupDetails().get(locationVO);
			if(!Utils.isEmpty(plDetails)){
				// this pojo may not be required, since the id required in case of par is not selected will be available in publicLiabilityVO
				//Renewal Multiple Id's handling changes, added policy in the query parameter
					premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE ,policyVO.getAppFlow(), getHibernateTemplate(), false, ((Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID)), basicSection.getPolicyId(), Long.valueOf(locationVO.getRiskGroupId()));
					ThreadLevelContext.set(SvcConstants.TLC_KEY_BASIC_RISK_ID, premiseQuo.getId().getWbdId());
			}
			if(Utils.isEmpty(premiseQuo)){
				throw new BusinessException(" PL_SECTION_ID : pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory");
			}
		}
		else{
			throw new BusinessException("pas.basicSection.detailsMandatory", null, "Details of the basic section is mandatory");
		}
		
		return new POJO[]{ buildingQuo, premiseQuo };

}

/**
 * 
 * @param policyVO to set policyVO
 * @param sectionVO to set sectionVO
 * @param locationDetails to set locationDetails
 * @param groupPersonalAccidentVO to set groupPersonalAccidentVO
 * @param buildingPojo to set buildingPojo
 * This method is used to update the table T_TRN_GACC_UNNAMED_PERSON and T_TRN_PREMIUM if 
 * unnamed employees are being insured.
 */

private void handleUnnammedPerson(PolicyVO policyVO, SectionVO sectionVO, LocationVO locationDetails, GroupPersonalAccidentVO groupPersonalAccidentVO, POJO[] buildingPojo) {
		
		java.util.List<GPAUnnammedEmpVO> unnammedEmployeeVO = groupPersonalAccidentVO.getGpaUnnammedEmpVO();	
		GPANammedEmpVO nammedEmployeeVO = null;
		
		for (GPAUnnammedEmpVO unnammedEmployeeVOrec: unnammedEmployeeVO){
		
			TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = null;
					
			if(!Utils.isEmpty(unnammedEmployeeVOrec.getGupId() )){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccUnnamedPersonQuo = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON, policyVO, buildingPojo , new BaseVO[]{ locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVOrec, nammedEmployeeVO }, false 
						,sectionVO.getPolicyId(),Long.valueOf(locationDetails.getRiskGroupId()), Long.valueOf(unnammedEmployeeVOrec.getGupId()));
			}else{
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccUnnamedPersonQuo = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON, policyVO, buildingPojo , new BaseVO[]{ locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVOrec, nammedEmployeeVO }, false 
						,sectionVO.getPolicyId(),ZERO_CONSTANT, ZERO_CONSTANT);
			}
			
			TTrnPremiumQuo premium = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, buildingPojo,
					new BaseVO[]{locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVOrec, nammedEmployeeVO }, false, sectionVO.getPolicyId(),
					new BigDecimal(tTrnGaccUnnamedPersonQuo.getId().getGupId()),
					new BigDecimal(tTrnGaccUnnamedPersonQuo.getGupBldId()),
					Integer.valueOf(SvcConstants.APP_BASE_COVER_CODE).shortValue(), GROUP_PERSONAL_ACCIDENT_COVER_TYPE, GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE);
		
		/**  Next, process the premium record for the GACC_PERSON record. */
	
			if(!Utils.isEmpty(premium.getPrmPremium())){
				PremiumHelper.logPremiumInfo("Premium for UnNamed GPA :" +premium.getPrmPremium().doubleValue());
				if(Utils.isEmpty(unnammedEmployeeVOrec.getPremium())){
					unnammedEmployeeVOrec.setPremium(new PremiumVO());
				}	
				unnammedEmployeeVOrec.getPremium().setPremiumAmt(premium.getPrmPremium().doubleValue());
			}else{
				PremiumHelper.logPremiumInfo("Premium for UnNamed GPA default: 0.0");
				if(Utils.isEmpty(unnammedEmployeeVOrec.getPremium())){
					unnammedEmployeeVOrec.setPremium(new PremiumVO());
				}	
				unnammedEmployeeVOrec.getPremium().setPremiumAmt(0.0);
			}
		}
		
	}
/**
 * 
 * @param sectionId to set int
 * @param policyDetails to set PolicyVO
 * @return the boolean
 */
private boolean isSectionPresent(int sectionId, PolicyVO policyDetails){
		SectionVO section = new SectionVO(RiskGroupingLevel.LOCATION);
		section.setSectionId(sectionId);
		return policyDetails.getRiskDetails().contains(section);
	}
/**
 * 
 * @param policyVO to set policyVO
 * @param sectionVO to set sectionVO
 * @param locationDetails to set locationDetails
 * @param groupPersonalAccidentVO to set groupPersonalAccidentVO
 * @param buildingPojo to set buildingPojo
 */

private void handleNammedPerson(PolicyVO policyVO, SectionVO sectionVO,
			LocationVO locationDetails, GroupPersonalAccidentVO groupPersonalAccidentVO, POJO[] buildingPojo) {
	
		GPAUnnammedEmpVO unnammedEmployeeVO = null;
		java.util.List<GPANammedEmpVO> nammedEmployeeVO = groupPersonalAccidentVO.getGpaNammedEmpVO();	
		
		Collections.sort(nammedEmployeeVO);
		for (GPANammedEmpVO nammedEmployeeVOrec : nammedEmployeeVO){
	
		// to Check and decide is it an first time same or second time
		
		TTrnGaccPersonQuo tTrnGaccPersonQuo = null;
		
		if(!Utils.isEmpty(nammedEmployeeVOrec.getGprId())){
			
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			tTrnGaccPersonQuo = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, buildingPojo , new BaseVO[]{ locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVO, nammedEmployeeVOrec }, false ,sectionVO.getPolicyId(),
						Long.valueOf(locationDetails.getRiskGroupId()), Long.valueOf(nammedEmployeeVOrec.getGprId()));
			
			}else{
			
			tTrnGaccPersonQuo = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, buildingPojo , new BaseVO[]{ locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVO, nammedEmployeeVOrec }, false ,sectionVO.getPolicyId()
						,ZERO_CONSTANT, ZERO_CONSTANT);
			}
		
		
		TTrnPremiumQuo premium = handleTableRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, buildingPojo,
				new BaseVO[]{locationDetails, groupPersonalAccidentVO, sectionVO, unnammedEmployeeVO, nammedEmployeeVOrec }, false, sectionVO.getPolicyId(),
				new BigDecimal(tTrnGaccPersonQuo.getId().getGprId()),
				new BigDecimal(tTrnGaccPersonQuo.getGprBldId()),
				Integer.valueOf(SvcConstants.APP_BASE_COVER_CODE).shortValue(), GROUP_PERSONAL_ACCIDENT_COVER_TYPE, GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE);
	
		
		/*  Next, process the premium record for the GACC_PERSON record. */
				
	    if(!Utils.isEmpty(premium.getPrmPremium())){
			PremiumHelper.logPremiumInfo("Premium for Named GPA :" +premium.getPrmPremium().doubleValue());
			if(Utils.isEmpty(nammedEmployeeVOrec.getPremium())){
				nammedEmployeeVOrec.setPremium(new PremiumVO());
			}	
			nammedEmployeeVOrec.getPremium().setPremiumAmt(premium.getPrmPremium().doubleValue());
		}else{
			PremiumHelper.logPremiumInfo("Premium for Named GPA Default: 0.0");
			if(Utils.isEmpty(nammedEmployeeVOrec.getPremium())){
				nammedEmployeeVOrec.setPremium(new PremiumVO());
			}	
			nammedEmployeeVOrec.getPremium().setPremiumAmt(0.0);
		}
	    
	    
	}		
}
/**(non-Javadoc).
 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#mapVOToPOJO(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param deps to set POJO[]
 * @param depsVO to set BaseVO[]
 * @return the POJO
 */
@Override
protected POJO mapVOToPOJO(String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO) {
POJO mappedPOJO = null;
		/**
		 * map to the values if saving the values to GACC_PERSON table.
		 */
		if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){

			LocationVO locationDetails = (LocationVO)depsVO[0];
			GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO)depsVO[1];
			SectionVO groupPersonalAccidentSection = (SectionVO)depsVO[2];
			GPANammedEmpVO nammedEmployeeDetails = (GPANammedEmpVO)depsVO[4];
		
			TMasOccupancy occupancy = getOccDetails(Short.valueOf(locationDetails.getOccTradeGroup().toString()));
			
			TTrnGaccPersonQuo tTrnGaccPersonQuo = getGaccPersonPOJO(policyVO,locationDetails,groupPersonalAccidentDetails,groupPersonalAccidentSection,deps,occupancy,nammedEmployeeDetails);
			mappedPOJO = tTrnGaccPersonQuo;
			
			
		} 
/**
 * map to the values if saving the values to GACC_UNNAMED_PERSON table.
*/
		else if(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId)){

			LocationVO locationDetails = (LocationVO)depsVO[0];
			GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO)depsVO[1];
			SectionVO groupPersonalAccidentSection = (SectionVO)depsVO[2];
			GPAUnnammedEmpVO unnamedEmployeeDetails = (GPAUnnammedEmpVO)depsVO[3];

			TMasOccupancy occupancy = getOccDetails(Short.valueOf(locationDetails.getOccTradeGroup().toString()));
			
			TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = getGaccUnnammedPersonPOJO(policyVO,locationDetails,groupPersonalAccidentDetails,groupPersonalAccidentSection,deps,occupancy,unnamedEmployeeDetails);
			mappedPOJO = tTrnGaccUnnamedPersonQuo;
			
			
		}
		/**
		 * map to the values if saving the values to PREMIUM table.
		 */
		else if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)){
			LocationVO locationDetails = (LocationVO)depsVO[0];
			TMasOccupancy occupancy = getOccDetails(Short.valueOf(locationDetails.getOccTradeGroup().toString()));
			TTrnPremiumQuo premiumQuo = getPremiumPojo(deps, depsVO, policyVO, occupancy);
			
			mappedPOJO = premiumQuo;
		
			
		}
		return mappedPOJO;
	}
/**
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param depsPOJO to set POJO[]
 * @param depsVO to set BaseVO[]
 * @return the boolean
 */
@Override
protected boolean isToBeCreated(String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO) {
		
		GPAUnnammedEmpVO unnamedempployeeDetails = (GPAUnnammedEmpVO)depsVO[3];
		GPANammedEmpVO namedempployeeDetails = (GPANammedEmpVO)depsVO[4];
		
		if (SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId)) {
			
			
			if(Utils.isEmpty(unnamedempployeeDetails.getGupId())){
				ThreadLevelContext.set(SvcConstants.PRM_TO_BE_CREATED, true);
				return true;
			}
			
		} else if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){
			
			if(Utils.isEmpty(namedempployeeDetails.getGprId()))
			{
				ThreadLevelContext.set(SvcConstants.PRM_TO_BE_CREATED, true);
				return true;
			}
			
		} else if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)) {
			
			Boolean isCreated = (Boolean) ThreadLevelContext.get(SvcConstants.PRM_TO_BE_CREATED);
			ThreadLevelContext.clear(SvcConstants.PRM_TO_BE_CREATED);
			return (!Utils.isEmpty(isCreated) && isCreated) ? true : false;
		} 
		ThreadLevelContext.set(SvcConstants.PRM_TO_BE_CREATED, false);
		return false;
	}
/**
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param depsPOJO to set POJO[]
 * @param depsVO to set BaseVO[]
 * @return the boolean
 */
@Override
protected boolean isToBeDeleted(String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO) {
			
GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO)depsVO[1];
GPAUnnammedEmpVO unnamedEmployeeDetails = (GPAUnnammedEmpVO)depsVO[3];
GPANammedEmpVO namedEmployeeDetails = (GPANammedEmpVO)depsVO[4];
		
		if(Utils.isEmpty(groupPersonalAccidentDetails.getIsToBeDeleted())) return false;
		 //deletion logic if any will come here 
			
		if(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId)){
			
			if(!Utils.isEmpty(unnamedEmployeeDetails.getIsToBeDeleted())){
				if(unnamedEmployeeDetails.getIsToBeDeleted()){
					ThreadLevelContext.set(SvcConstants.PRM_TO_BE_DELETED, true);
					return true;
				}
			}
		
		} else if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){
			
			if(!Utils.isEmpty(namedEmployeeDetails.getIsToBeDeleted())){
				if(namedEmployeeDetails.getIsToBeDeleted()){
					ThreadLevelContext.set(SvcConstants.PRM_TO_BE_DELETED, true);
					return true;
				}
			}
		
		}else if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)){

			Boolean isDeleted = (Boolean) ThreadLevelContext.get(SvcConstants.PRM_TO_BE_DELETED);
			ThreadLevelContext.clear(SvcConstants.PRM_TO_BE_DELETED);
			return (!Utils.isEmpty(isDeleted) && isDeleted) ? true : false;
			
		}
		ThreadLevelContext.set(SvcConstants.PRM_TO_BE_DELETED, false);
		return false;
		//return false;
	}
/**
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param mappedRecord to set POJO
 * @param depsVO to set BaseVO[]
 * @param saveCase to set SaveCase
 *
 */
@Override
protected void updateKeyValuesToVOs(String tableId, POJO mappedRecord,
			PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase) {
		
		GPAUnnammedEmpVO unnammedEmployeeVO = (GPAUnnammedEmpVO)depsVO[3];
		GPANammedEmpVO nammedEmployeeVO = (GPANammedEmpVO)depsVO[4];
		
		if(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId))
		{
		
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccUnnamedPersonQuo ){
					unnammedEmployeeVO.setBasicRiskId( ( (TTrnGaccUnnamedPersonQuo) mappedRecord ).getId().getGupId() );
					Long gpridTemp = ( (TTrnGaccUnnamedPersonQuo) mappedRecord ).getId().getGupId();
					unnammedEmployeeVO.setGupId( gpridTemp.toString() );
				}
			}
		
		}else if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){

			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccPersonQuo ){
					nammedEmployeeVO.setBasicRiskId( ( (TTrnGaccPersonQuo) mappedRecord ).getId().getGprId() );
					Long gpridTemp = ( (TTrnGaccPersonQuo) mappedRecord ).getId().getGprId();
					nammedEmployeeVO.setGprId( gpridTemp.toString() );
				}
			}
		}
	}
/**(non-Javadoc).
 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructCreateRecordId(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO)
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param mappedRecord to set POJO
 * @return the POJOId
 */
@Override
protected POJOId constructCreateRecordId(String tableId, PolicyVO policyVO,
			POJO mappedRecord) {
		POJOId id = null;
		if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){
			TTrnGaccPersonQuoId personQuoId = new TTrnGaccPersonQuoId();
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccPersonQuo ){

					personQuoId.setGprId( ( (TTrnGaccPersonQuo) mappedRecord ).getGprBasicRiskId() );
					personQuoId.setGprValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = personQuoId;
				}
			}
		}
		else if(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId)){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccUnnamedPersonQuo ){
					TTrnGaccUnnamedPersonQuoId unnamedPersonQuoId = new TTrnGaccUnnamedPersonQuoId();
					unnamedPersonQuoId.setGupId( ( (TTrnGaccUnnamedPersonQuo) mappedRecord ).getGupBasicRiskId() );
					unnamedPersonQuoId.setGupValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = unnamedPersonQuoId;
				}
			}
		}
		else if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnPremiumQuo ){
					TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
					TTrnPremiumQuoId pId = premiumQuo.getId();
					pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = mappedRecord.getPOJOId();
				}
			}
		} 
				
		return id;
	}
/**(non-Javadoc).
* @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructChangeRecordId(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJOId)
 * @param tableId to set String table name
 * @param policyVO to set PolicyVO
 * @param existingId to set T
 * @return the POJOId
*/
@Override
protected <T extends POJOId> POJOId constructChangeRecordId(String tableId,
			PolicyVO policyVO, T existingId) {
			POJOId id = null;
		
		if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)){
			
			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject(existingId);
			pId.setPrmValidityStartDate((Date) ThreadLevelContext.get(SvcConstants.TLC_KEY_VSD));
			id = pId;
		
		}else if(SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals(tableId)){
		
			TTrnGaccUnnamedPersonQuoId existingTId = (TTrnGaccUnnamedPersonQuoId)existingId;
			TTrnGaccUnnamedPersonQuoId unnamedPersonQuoId = new TTrnGaccUnnamedPersonQuoId();
			unnamedPersonQuoId.setGupId(existingTId.getGupId());
			unnamedPersonQuoId.setGupValidityStartDate(existingTId.getGupValidityStartDate());
			id = unnamedPersonQuoId;
		
		}else if(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals(tableId)){
		
			TTrnGaccPersonQuoId existingTId = (TTrnGaccPersonQuoId) existingId;
			TTrnGaccPersonQuoId tid = new TTrnGaccPersonQuoId();
			tid.setGprId(existingTId.getGprId());
			tid.setGprValidityStartDate(existingTId.getGprValidityStartDate());
			id = tid;
		}
		return id;
	}
/**
 * @param policyVO to set PolicyVO
 */
@Override
protected void sectionPostProcessing(PolicyVO policyVO){
		SectionVO groupPersonalAccidentSection = PolicyUtils.getSectionVO(policyVO, SvcConstants.SECTION_ID_GPA);
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing(groupPersonalAccidentSection);
		GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO) PolicyUtils.getRiskGroupDetails(locationDetails, groupPersonalAccidentSection);
		removeDeletedRowsFromContext(groupPersonalAccidentDetails);
		updateSectionLevelSIAndPremium(groupPersonalAccidentDetails);
		updateEndtText(policyVO);

		super.sectionPostProcessing(policyVO);
	}
/**
 * This method is used to remove the deleted rows fron the context 
 * after actual deletion from the data base.
 * @param groupPersonalAccidentDetails to set groupPersonalAccidentDetails
*/
private void removeDeletedRowsFromContext(GroupPersonalAccidentVO groupPersonalAccidentDetails){
		
		if(Utils.isEmpty(groupPersonalAccidentDetails.getIsToBeDeleted())) {return;}
		
		boolean deletionflagNamed = false;
		boolean deletionflagUnnamed = false;
		ArrayList<GPANammedEmpVO> toBeDeletedVOs = new ArrayList<GPANammedEmpVO>();
		ArrayList<GPAUnnammedEmpVO> toBeDeletedVOs1 = new ArrayList<GPAUnnammedEmpVO>();
		for(GPANammedEmpVO gpaNammedEmpVO : groupPersonalAccidentDetails.getGpaNammedEmpVO()){
			if(!Utils.isEmpty(gpaNammedEmpVO.getIsToBeDeleted()) && gpaNammedEmpVO.getIsToBeDeleted()){
				toBeDeletedVOs.add(gpaNammedEmpVO);
				deletionflagNamed = true;
			}
		}
		for(GPAUnnammedEmpVO gpaUnnammedEmpVO : groupPersonalAccidentDetails.getGpaUnnammedEmpVO()){
			if(!Utils.isEmpty(gpaUnnammedEmpVO.getIsToBeDeleted()) && gpaUnnammedEmpVO.getIsToBeDeleted()){
				toBeDeletedVOs1.add(gpaUnnammedEmpVO);
				deletionflagUnnamed = true;
			}
		}
		if(deletionflagNamed){
			for(GPANammedEmpVO toBeDeletedVO : toBeDeletedVOs){

				((GroupPersonalAccidentVO) groupPersonalAccidentDetails).getGpaNammedEmpVO().remove(toBeDeletedVO);
			}
		}
		if(deletionflagUnnamed){
			for(GPAUnnammedEmpVO toBeDeletedVO1 : toBeDeletedVOs1){

				((GroupPersonalAccidentVO) groupPersonalAccidentDetails).getGpaUnnammedEmpVO().remove(toBeDeletedVO1);
			}
		}
		
	}

/**
 * 	
 * @param groupPersonalAccidentDetails to set GroupPersonalAccidentVO
 */
private void updateSectionLevelSIAndPremium(GroupPersonalAccidentVO groupPersonalAccidentDetails){
		groupPersonalAccidentDetails.setSumInsured(getSectionLevelSumInsured(groupPersonalAccidentDetails));
		getSectionLevelPremium(groupPersonalAccidentDetails);

}

/**
 * This method is used to update the GPA level SumInsured and Premium Amount.
 * @param groupPersonalAccidentDetails to set groupPersonalAccidentDetails
 * 
 */
private void getSectionLevelPremium(GroupPersonalAccidentVO groupPersonalAccidentDetails){
		if(Utils.isEmpty(groupPersonalAccidentDetails.getPremium())){
			groupPersonalAccidentDetails.setPremium(new PremiumVO());
		}
		PremiumHelper.getSectionLevelPremium(groupPersonalAccidentDetails);
	}

/**
 * This method is used to update the GPA level SumInsured and Premium Amount.
 * @param groupPersonalAccidentDetails to set groupPersonalAccidentDetails
 * @return the double
*/
private double getSectionLevelSumInsured(GroupPersonalAccidentVO groupPersonalAccidentDetails){
		return PremiumHelper.getSectionLevelSumInsured(groupPersonalAccidentDetails);

	}
/**
* 
* @param policyVO to set policyVO
* @param groupPersonalAccidentDetails to set groupPersonalAccidentDetails
* @param groupPersonalAccidentSection to set groupPersonalAccidentSection
* @param deps to set deps
* @param occupancy to set occupancy
* @param namedEmployeesDetails to set namedEmployeesDetails
* @return the tTrnGaccPersonQuo
* This method is used to map the values to TTrnGaccPersonQuo pojo so that values can be saved in corresponding table.
 * 
*/
	
private TTrnGaccPersonQuo getGaccPersonPOJO(PolicyVO policyVO, LocationVO locationdetails, GroupPersonalAccidentVO groupPersonalAccidentDetails, SectionVO groupPersonalAccidentSection,POJO[] deps,TMasOccupancy occupancy,GPANammedEmpVO namedEmployeesDetails){
		
		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo)deps[0];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo)deps[1];
		Integer userId = SvcUtils.getUserId(policyVO);
		TTrnGaccPersonQuo tTrnGaccPersonQuo = new TTrnGaccPersonQuo(); 
		
		tTrnGaccPersonQuo.setGprAName(namedEmployeesDetails.getNammedEmpDesignation());
		if(Utils.isEmpty(namedEmployeesDetails.getGprId())){
		Long cntSequence = NextSequenceValue.getNextSequence(GACC_PERSON_SEQ,null,null, getHibernateTemplate());
		tTrnGaccPersonQuo.setGprBasicRiskId(cntSequence );
		}else{
			tTrnGaccPersonQuo.setGprBasicRiskId(Long.valueOf(namedEmployeesDetails.getGprId()));
		}
		tTrnGaccPersonQuo.setGprBasicRskCode(GROUP_PERSONAL_ACCIDENT_RISK_CODE);
		
		if(!Utils.isEmpty(tTrnBuildingQuo)){
			tTrnGaccPersonQuo.setGprBldId(tTrnBuildingQuo.getId().getBldId());
		}
		else if(!Utils.isEmpty(trnWctplPremiseQuo)){
			tTrnGaccPersonQuo.setGprBldId(trnWctplPremiseQuo.getId().getWbdId());
		}
		
		try{
			Date dateOfBirth = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dateOfBirth =  sdf.parse(namedEmployeesDetails.getNammedEmpDob());
			
			tTrnGaccPersonQuo.setGprDateOfBirth(dateOfBirth);
		}catch(Exception e){
			e.printStackTrace();
		}
		tTrnGaccPersonQuo.setGprGender(namedEmployeesDetails.getNammedEmpGender());
		tTrnGaccPersonQuo.setGprEName(namedEmployeesDetails.getNameOfEmployee());
		tTrnGaccPersonQuo.setGprEndDate(policyVO.getEndDate());
		tTrnGaccPersonQuo.setGprEndtId(GROUP_PERSONAL_ACCIDENT_ENDT_ID.longValue());
		tTrnGaccPersonQuo.setGprModifiedBy(userId);
		tTrnGaccPersonQuo.setGprModifiedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
		tTrnGaccPersonQuo.setGprOcCode(occupancy.getOcpCode());
		tTrnGaccPersonQuo.setGprPolicyId(groupPersonalAccidentSection.getPolicyId());
		tTrnGaccPersonQuo.setGprPreparedBy(userId );
		tTrnGaccPersonQuo.setGprPreparedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
		tTrnGaccPersonQuo.setGprRcCode((long)GROUP_PERSONAL_ACCIDENT_RISK_CATEGORY);
		tTrnGaccPersonQuo.setGprRiRskCode(GROUP_PERSONAL_ACCIDENT_RI_RISK_CODE);
		tTrnGaccPersonQuo.setGprRskCode(GROUP_PERSONAL_ACCIDENT_RISK_CODE.longValue());
		tTrnGaccPersonQuo.setGprRtCode((long)namedEmployeesDetails.getEmployeeType());
		
		tTrnGaccPersonQuo.setGprSalary(new BigDecimal(namedEmployeesDetails.getNammedEmpAnnualSalary())); 
		tTrnGaccPersonQuo.setGprStartDate(policyVO.getScheme().getEffDate());
		tTrnGaccPersonQuo.setGprStatus(SvcConstants.POL_STATUS_PENDING.byteValue());
		tTrnGaccPersonQuo.setGprSumInsured(new BigDecimal(namedEmployeesDetails.getSumInsuredDetails().getSumInsured()));
		tTrnGaccPersonQuo.setGprTradeGroup(occupancy.getOcpTradeCode().longValue());
		tTrnGaccPersonQuo.setGprAgrLmt(new BigDecimal(groupPersonalAccidentDetails.getAggregateLimit()));
		tTrnGaccPersonQuo.setGprValidityExpiryDate(SvcConstants.EXP_DATE);
		
		
		return tTrnGaccPersonQuo;
	}

/**
* @param policyVO to set policyVO
* @param groupPersonalAccidentDetails to set groupPersonalAccidentDetails
* @param groupPersonalAccidentSection to set groupPersonalAccidentSection
* @param deps to set deps
* @param occupancy to set occupancy
* @param unnamedEmployeeDetails to set unnamedEmployeeDetails
* @return the tTrnGaccUnnamedPersonQuo
* 
* This method is used to map the values to TTrnGaccUnnamesPersonQuo pojo so that values can be saved in corresponding table.
*/
private TTrnGaccUnnamedPersonQuo getGaccUnnammedPersonPOJO(PolicyVO policyVO, LocationVO locationDetails,
			GroupPersonalAccidentVO groupPersonalAccidentDetails, SectionVO GroupPersonalAccidentSection, POJO[] deps,
			TMasOccupancy occupancy,
			GPAUnnammedEmpVO unnammedEmployeeDetails) {
		

		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo)deps[0];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo)deps[1];
		Integer userId = SvcUtils.getUserId(policyVO);
		TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = new TTrnGaccUnnamedPersonQuo(); 
		
		if(Utils.isEmpty(unnammedEmployeeDetails.getGupId())){
			Long cntSequence = NextSequenceValue.getNextSequence(GACC_UNNAMED_PERSON_SEQ,null,null, getHibernateTemplate());
			tTrnGaccUnnamedPersonQuo.setGupBasicRiskId(cntSequence );
			}else{
			tTrnGaccUnnamedPersonQuo.setGupBasicRiskId(Long.valueOf(unnammedEmployeeDetails.getGupId()));
		}
		
		tTrnGaccUnnamedPersonQuo.setGupBasicRskCode(GROUP_PERSONAL_ACCIDENT_BASIC_RISK_CODE);
		tTrnGaccUnnamedPersonQuo.setGupStatus(SvcConstants.POL_STATUS_PENDING.byteValue());
		tTrnGaccUnnamedPersonQuo.setGupEndDate(policyVO.getEndDate());
		tTrnGaccUnnamedPersonQuo.setGupEndtId(GROUP_PERSONAL_ACCIDENT_ENDT_ID.longValue());
		tTrnGaccUnnamedPersonQuo.setGupModifiedBy(userId);
		tTrnGaccUnnamedPersonQuo.setGupModifiedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
		tTrnGaccUnnamedPersonQuo.setGupPolicyId(GroupPersonalAccidentSection.getPolicyId());
		tTrnGaccUnnamedPersonQuo.setGupPreparedBy(userId );
		tTrnGaccUnnamedPersonQuo.setGupPreparedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
		tTrnGaccUnnamedPersonQuo.setGupRcCode((int)GROUP_PERSONAL_ACCIDENT_RISK_CATEGORY);
		tTrnGaccUnnamedPersonQuo.setGupRiRskCode(GROUP_PERSONAL_ACCIDENT_RI_RISK_CODE);
		tTrnGaccUnnamedPersonQuo.setGupRskCode(GROUP_PERSONAL_ACCIDENT_RISK_CODE);
		tTrnGaccUnnamedPersonQuo.setGupValidityExpiryDate(SvcConstants.EXP_DATE);
		
		tTrnGaccUnnamedPersonQuo.setGupAgrLmt(new BigDecimal(groupPersonalAccidentDetails.getAggregateLimit()));
		if(!Utils.isEmpty(tTrnBuildingQuo)){
			tTrnGaccUnnamedPersonQuo.setGupBldId(tTrnBuildingQuo.getId().getBldId());
		}
		else if(!Utils.isEmpty(trnWctplPremiseQuo)){
			tTrnGaccUnnamedPersonQuo.setGupBldId(trnWctplPremiseQuo.getId().getWbdId());
		}
		
		tTrnGaccUnnamedPersonQuo.setGupOcCode(unnammedEmployeeDetails.getUnnammedEmployeeType());
		tTrnGaccUnnamedPersonQuo.setGupSalary(new BigDecimal(unnammedEmployeeDetails.getUnnammedAnnualSalary()));
		tTrnGaccUnnamedPersonQuo.setGupNoOfPerson(unnammedEmployeeDetails.getUnnammedNumberOfEmloyee());
		if(!Utils.isEmpty(unnammedEmployeeDetails.getSumInsuredDetails().getSumInsured())){
			tTrnGaccUnnamedPersonQuo.setGupSumInsured(new BigDecimal(unnammedEmployeeDetails.getSumInsuredDetails().getSumInsured()));
		}else{
			tTrnGaccUnnamedPersonQuo.setGupSumInsured(new BigDecimal(0));
		}
		
		tTrnGaccUnnamedPersonQuo.setGupStartDate(policyVO.getScheme().getEffDate());
		
		return tTrnGaccUnnamedPersonQuo;
	
	}
/**
* 
* @param depsVO to set depsVO
* @param policyDetails to set policyDetails
* @param occupancy to set policyDetails
* @return the premiumVO 
* 
* This method is to map the values to TTrnPremiumQuo pojo so that values can be saved in corresponding table.
*/
	
private TTrnPremiumQuo getPremiumPojo(POJO[] deps, BaseVO[] depsVO, PolicyVO policyDetails, TMasOccupancy occupancy){
		
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		
		GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO)depsVO[1];
		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo)deps[0];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo)deps[1];
		SectionVO GroupPersonalAccidentSection = (SectionVO)depsVO[2];
		GPAUnnammedEmpVO unnamedEmployeeVO = (GPAUnnammedEmpVO)depsVO[3];
		GPANammedEmpVO namedEmployeeVO = (GPANammedEmpVO)depsVO[4];
		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		
		Integer riRiskCode = null;
		Long bldId = null;
		if(!Utils.isEmpty(deps[ 0 ])){

			
			if(deps[ 0 ] instanceof TTrnBuildingQuo){
				bldId = tTrnBuildingQuo.getId().getBldId();
				riRiskCode = tTrnBuildingQuo.getBldRiRskCode();
			}
			else if(deps[ 0 ] instanceof TTrnWctplPremiseQuo){
				bldId = trnWctplPremiseQuo.getId().getWbdId();
				riRiskCode = trnWctplPremiseQuo.getWbdRiRskCode();
			}
		}
		else if(!Utils.isEmpty(deps[ 1 ])){

			if(deps[ 1 ] instanceof TTrnWctplPremiseQuo){
				bldId = trnWctplPremiseQuo.getId().getWbdId();
				riRiskCode = trnWctplPremiseQuo.getWbdRiRskCode();
			}
		}
		premiumQuoId.setPrmBasicRskCode(GROUP_PERSONAL_ACCIDENT_BASIC_RISK_CODE);
				
		if(!Utils.isEmpty(namedEmployeeVO)){
			premiumQuoId.setPrmBasicRskId(new BigDecimal(namedEmployeeVO.getGprId()));	
			premiumQuoId.setPrmRskId(new BigDecimal(namedEmployeeVO.getGprId()));
		}else if(!Utils.isEmpty(unnamedEmployeeVO)){
			premiumQuoId.setPrmRskId(new BigDecimal(unnamedEmployeeVO.getGupId()));	
		}
		if(!Utils.isEmpty( bldId )){
			premiumQuoId.setPrmBasicRskId(BigDecimal.valueOf(bldId));
		}
		premiumQuoId.setPrmPolicyId(GroupPersonalAccidentSection.getPolicyId());
		premiumQuoId.setPrmValidityStartDate((Date)ThreadLevelContext.get("VSD"));
		premiumQuoId.setPrmRskCode(GROUP_PERSONAL_ACCIDENT_RISK_CODE);                                                  
		premiumQuoId.setPrmCovCode(GROUP_PERSONAL_ACCIDENT_COVER_CODE);                                             
		premiumQuoId.setPrmCtCode(GROUP_PERSONAL_ACCIDENT_COVER_TYPE);                                            
		premiumQuoId.setPrmCstCode(GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE);                                           
		premiumQuo.setId(premiumQuoId);
		premiumQuo.setPrmEndtId(GROUP_PERSONAL_ACCIDENT_ENDT_ID.longValue());
		premiumQuo.setPrmClCode(GROUP_PERSONAL_ACCIDENT_CLASS_CODE);
		premiumQuo.setPrmPtCode((short) 50);
		premiumQuo.setPrmRcCode(GROUP_PERSONAL_ACCIDENT_RISK_CATEGORY);
		premiumQuo.setPrmRscCode(GROUP_PERSONAL_ACCIDENT_RISK_SUB_CATEGORY);
		premiumQuo.setPrmStatus(SvcConstants.POL_STATUS_PENDING.byteValue());
		premiumQuo.setPrmSitypeCode(SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
		premiumQuo.setPrmFnCode(SvcConstants.PRM_FN_CODE);
		premiumQuo.setPrmValidityExpiryDate(SvcConstants.EXP_DATE);
		premiumQuo.setPrmRiRskCode(GROUP_PERSONAL_ACCIDENT_RI_RISK_CODE);
		premiumQuo.setPrmRtCode(GROUP_PERSONAL_ACCIDENT_RISK_TYPE_CODE);
		
		if(!Utils.isEmpty(namedEmployeeVO)){
			premiumQuo.setPrmSumInsured(new BigDecimal(namedEmployeeVO.getSumInsuredDetails().getSumInsured()));
			premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(groupPersonalAccidentDetails.getGpaDeductible()));
			
		}
		else if(!Utils.isEmpty(unnamedEmployeeVO)){
			premiumQuo.setPrmSumInsured(new BigDecimal(unnamedEmployeeVO.getSumInsuredDetails().getSumInsured()));
			premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(groupPersonalAccidentDetails.getGpaDeductible()));
			
		}
				
		premiumQuo.setPrmEffectiveDate(policyDetails.getScheme().getEffDate());
		premiumQuo.setPrmExpiryDate(policyDetails.getEndDate());
		
		
		
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyDetails, (Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
		
		if(!Utils.isEmpty(namedEmployeeVO)){
			if( !Utils.isEmpty( namedEmployeeVO.getPremium() ) ){
				if( !Utils.isEmpty( namedEmployeeVO.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium(new BigDecimal(String.valueOf(namedEmployeeVO.getPremium().getPremiumAmt())));
				premiumQuo.setPrmPremiumActual(new BigDecimal(String.valueOf(namedEmployeeVO.getPremium().getPremiumAmt())));
			}else{
				setZeroPrmValue(premiumQuo);
				}
			}else{
				setZeroPrmValue(premiumQuo);
			}
		}else if(!Utils.isEmpty(unnamedEmployeeVO)){
				if( !Utils.isEmpty( unnamedEmployeeVO.getPremium() ) ){
					if( !Utils.isEmpty( unnamedEmployeeVO.getPremium().getPremiumAmt() ) ){
						premiumQuo.setPrmPremium(new BigDecimal(String.valueOf(unnamedEmployeeVO.getPremium().getPremiumAmt())));
						premiumQuo.setPrmPremiumActual(new BigDecimal(String.valueOf(unnamedEmployeeVO.getPremium().getPremiumAmt())));
					}else{
						setZeroPrmValue(premiumQuo);
					}
				}else{
					setZeroPrmValue(premiumQuo);
				}
		
		}
		else{
				setZeroPrmValue(premiumQuo);
		}
				
		//groupPersonalAccidentDetails.setSumInsured(sumInsured);
		setRateTypeToPremiumPOJO(policyDetails, premiumQuo);
		
		return premiumQuo;
	}
/**
 * 	
 * @param policyVO to set PolicyVO
 */
private void updateEndtText(PolicyVO policyVO){
		if((policyVO.getAppFlow() == Flow.AMEND_POL))
		{
			SectionVO groupPersonalAccidentSection = PolicyUtils.getSectionVO(policyVO, getSectionId());
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing(groupPersonalAccidentSection);
			GroupPersonalAccidentVO groupPersonalAccidentDetails = (GroupPersonalAccidentVO) PolicyUtils.getRiskGroupDetails(locationDetails, groupPersonalAccidentSection);
			
			DAOUtils.deletePrevEndtText(groupPersonalAccidentSection.getPolicyId(), (Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID),GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(locationDetails.getRiskGroupId()));			
				
				LOGGER.debug("call pro_endt_text_gacc_per_add");
				DAOUtils.addGPANamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(locationDetails.getRiskGroupId()));
			
				LOGGER.debug("call pro_endt_text_gacc_unper_add");
				DAOUtils.addGPAUnnamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(locationDetails.getRiskGroupId()));			
				
				LOGGER.debug("call pro_endt_text_gacc_unper_del");
				DAOUtils.deleteGPAUnnamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(locationDetails.getRiskGroupId()));
			
				LOGGER.debug("call pro_endt_text_gacc_per_del");
				DAOUtils.deleteGPANamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(locationDetails.getRiskGroupId()));
			
				LOGGER.debug("call pro_endt_text_gacc_unper_mod");
				for(GPAUnnammedEmpVO gpaUnnammedEmpVO : groupPersonalAccidentDetails.getGpaUnnammedEmpVO()){
					if(!Utils.isEmpty(gpaUnnammedEmpVO.getGupId())){
						DAOUtils.updateGPAUnnamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()), groupPersonalAccidentDetails.getBasicRiskId(), GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(gpaUnnammedEmpVO.getGupId()));
					}
				}
				
				LOGGER.debug("call pro_endt_text_gacc_per_mod ");
				for(GPANammedEmpVO gpaNammedEmpVO : groupPersonalAccidentDetails.getGpaNammedEmpVO()){
					if(!Utils.isEmpty(gpaNammedEmpVO.getGprId())){
				DAOUtils.updateGPANamedforendorsementFlow(groupPersonalAccidentSection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()), groupPersonalAccidentDetails.getBasicRiskId(), GROUP_PERSONAL_ACCIDENT_SECTION_ID, Long.valueOf(gpaNammedEmpVO.getGprId()));
					}
				}
			
				//LOGGER.debug("call Risk Add changes change endo SP");
				//DAOUtils.updateEndTextForRiskAdd(groupPersonalAccidentSection.getPolicyId(), policyVO, groupPersonalAccidentSection.getSectionId());
				
				DAOUtils.updateDeductibleforendorsementFlow( groupPersonalAccidentSection.getPolicyId(), policyVO,groupPersonalAccidentSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
								
				DAOUtils.updateTotalSITextforendorsementFlow( groupPersonalAccidentSection.getPolicyId(), policyVO,groupPersonalAccidentSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
			}
			
		}
	
/**
 * @param tableId to set TableId
 * @param mappedRecord to set POJO
 * @param policyVO to set PolicyVO
 */
@Override
protected void tableRecPostSaveProcessing(String tableId, POJO mappedRecord, PolicyVO policyVO) {
			
			//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
		}
		
/**(non-Javadoc).
* @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#tableRecPostProcessing(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO)
*  * @param tableId to set TableId
 * @param mappedRecord to set POJO
 * @param policyVO to set PolicyVO
*/
@Override
protected void tableRecPostProcessing(String tableId, POJO mappedRecord, PolicyVO policyVO) {
	
			//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

		}

/**(non-Javadoc).
* @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructAddtlCoverCntListForCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
* @param currRgd to RiskGroupDetails
* @return the list
*/
@Override
public List<Contents> constructAddtlCoverCntListForCurrRGD(RiskGroupDetails currRgd) {
			
			return null;
		}

/**(non-Javadoc).
 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getBasicRiskIdFromCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
 * @param rgd to set RiskGroupDetails
 * @return the Long
*/
@Override
public Long getBasicRiskIdFromCurrRGD(RiskGroupDetails rgd) {
			
			return null;
		}

/**(non-Javadoc).
* @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructPOJOAForPrmTableMapping(com.rsaame.pas.vo.bus.PolicyVO, java.lang.Long)
* @param policyVO to set PolicyVO
* @param basicRiskIdOfCurrRGD to set Long
* @return the POJO
*/
@Override
public POJO[] constructPOJOAForPrmTableMapping(PolicyVO policyVO, Long basicRiskIdOfCurrRGD) {
			
			return null;
		}
/**
 * @param baseVO to set BaseVO
 * @return the BaseVO
 */
public BaseVO loadGroupPersonalAccidentSection(BaseVO baseVO) {
			
			return null;
		}
/**
 * @param baseVO to set BaseVO
 * @return the BaseVO
 */
public BaseVO saveGroupPersonalAccidentSection(BaseVO baseVO) {
		
			return saveSection(baseVO);
		}
/**
 * @param ocpCode to set Short
 * @return the TMasOccupancy
 */		
private TMasOccupancy getOccDetails(Short ocpCode){
			return (TMasOccupancy) getHibernateTemplate().find("from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode).get(0);
		}
}
