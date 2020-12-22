package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * Uploads the MB Section
 * 
 * @author m1014438
 * 
 */
public class MBFileUploadRH extends GenericFileUploadRH {

	private static final int CIR_MACHINE_TYPE_COL = 0;
	private static final int CIR_DESCRIPTION_COL = 1;
	private static final int CIR_YROFMAKE_COL = 2;
	private static final int CIR_NEWREPLACEMENTVAL_COL = 3;
	private static final int CIR_DEDUCTIBLE_COL = 4;
	private static final Map mbCodeDescMap = new Map<String, Integer>(null,
			null);
	private static final String MACHINERY_TYPE_CATEGORY = "MACHINERY_TYPE";
	private static final String MB_SECTION = "MB_SECTION";

	@Override
	protected BaseVO sectionRelatedChanges(HttpServletRequest request,
			Response response, String fileName) {

		/*
		 * 1. Get PolicyVO from PolicyContext 2. Get SectionVO from PolicyVO 3.
		 * Get LocationVO from SectionVO 4. Get MoneyVO for the locationVO
		 * obtained in step 3 5. SetPageData i.e.
		 * AppUtils.setSectionPageDataForJSON 6. It will always be case of
		 * reload hence implement the logic of
		 * SectionRHUtils.getLocationReloadJSP blindly
		 */
		MBVO mbVO = new MBVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext(request)
				.getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO(policyVO,
				AppConstants.SECTION_ID_MB);
		LocationVO locationVO = (LocationVO) PolicyUtils
				.getRiskGroupForProcessing(sectionVO);

		if (!Utils.isEmpty(fileName)) {

			File file = null;
			file = new File(fileName);
			// MSExcelFileHandler readingExcelFiles = new MSExcelFileHandler();

			if (file.exists()) {
				getMBTypeDescription(request, policyVO);
				mbVO = readFileForMachineryDetails(fileName, mbVO);
			}

			AppUtils.setSectionPageDataForJSON(request, sectionVO, locationVO,
					mbVO, policyVO);
			setRedirectionIfAny(response);

		}
		return mbVO;

	}

	/**
	 * Loops through all (from second row)rows and their cells from upload excel
	 * file and populates MBVO
	 * 
	 * @param fileName
	 * @param mbVO
	 * @return
	 */
	private MBVO readFileForMachineryDetails(String fileName, MBVO mbVO) {

		List<Map<Integer, Cell>> listOfRows = getFileData(fileName, null);
		int noOfRows = listOfRows.size();

		/**
		 * Looping starts from the second row because, the first row we defined
		 * the headings for columns
		 * 
		 */
		for (int i = 1; i < noOfRows; i++) {
			Map<Integer, Cell> row = listOfRows.get(i);
			MachineDetailsVO machinedetails = new MachineDetailsVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			machinedetails.setSumInsuredVO(sumInsuredVO);
			Cell myCell = null;

			Double tempVar;
			if (row.containsKey(CIR_MACHINE_TYPE_COL)) {
				myCell = row.get(CIR_MACHINE_TYPE_COL);
				if (myCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					Integer mbTypeCode = null;
					if (mbCodeDescMap.containsKey(myCell.getStringCellValue())) {
						mbTypeCode = (Integer) mbCodeDescMap.get(myCell
								.getStringCellValue());
						machinedetails.setMachineryType(mbTypeCode);
					}
				
				}

			} else {
				machinedetails.setMachineryType(null);
			}
			if (row.containsKey(CIR_DESCRIPTION_COL)) {
				myCell = row.get(CIR_DESCRIPTION_COL);
				if (myCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					machinedetails.setMachineDescription(myCell
							.getStringCellValue());
				}else if(myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
					machinedetails.setMachineDescription(String.valueOf(myCell
							.getNumericCellValue()));
				}

			} else {
				machinedetails.setMachineDescription(null);
			}
			if (row.containsKey(CIR_NEWREPLACEMENTVAL_COL)) {
				myCell = row.get(CIR_NEWREPLACEMENTVAL_COL);
				if (myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					sumInsuredVO.setSumInsured(myCell.getNumericCellValue());
				}

			} else {
				sumInsuredVO.setSumInsured(null);
			}

			if (row.containsKey(CIR_DEDUCTIBLE_COL)) {
				myCell = row.get(CIR_DEDUCTIBLE_COL);
				if (myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					tempVar = myCell.getNumericCellValue();
					sumInsuredVO.setDeductible(myCell.getNumericCellValue());
				}

			} else {
				sumInsuredVO.setDeductible(null);
			}

			if (row.containsKey(CIR_YROFMAKE_COL)) {
				myCell = row.get(CIR_YROFMAKE_COL);
				if (myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				tempVar = myCell.getNumericCellValue();
				machinedetails.setYearOfMake(tempVar.intValue());
				}
			} else {
				machinedetails.setYearOfMake(null);
			}

			mbVO.getMachineryDetails().add(machinedetails);
		}
		return mbVO;

	}

	@Override
	protected void setRedirectionIfAny(Response responseObj) {
		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP(
				Integer.valueOf(Utils.getSingleValueAppConfig(MB_SECTION)),
				true);
		redirection.setUrl(redirectionURL);
		redirection.setType(Type.TO_JSP);
		responseObj.setRedirection(redirection);
	}

	@Override
	protected String getFileName(String fileName, HttpServletRequest request) {
		String riskGroupId = request.getParameter("riskGroupId");

		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		return "CIR_" + riskGroupId + "." + extension;
	}

	/**
	 * Loads the MB look up (code , description)fields and stored it in map
	 * 
	 * @param request
	 * @param policyVO
	 */
	protected void getMBTypeDescription(HttpServletRequest request,
			PolicyVO policyVO) {
		LookUpListVO listVO = SvcUtils.getLookUpCodesList(
				MACHINERY_TYPE_CATEGORY, policyVO.getScheme().getSchemeCode()
						.toString(), policyVO.getScheme().getTariffCode()
						.toString());
		for (LookUpVO luVO : listVO.getLookUpList()) {
			mbCodeDescMap.put(luVO.getDescription(), luVO.getCode().intValue());
		}

	}

}
