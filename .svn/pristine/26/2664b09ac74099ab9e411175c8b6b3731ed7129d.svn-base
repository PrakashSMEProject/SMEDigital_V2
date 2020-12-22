package com.rsaame.pas.svc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mindtree.ruc.cmn.base.BaseVO;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.CommonOpDAO;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuo;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuo;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnGaccBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashDetailsQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnMarineDetailQuo;
import com.rsaame.pas.dao.model.TTrnMarineHeaderQuo;
import com.rsaame.pas.dao.model.TTrnMarineTransitQuo;
import com.rsaame.pas.dao.model.TTrnNonStdTextQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPersonQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.lookup.dao.ILookUpDAO;
import com.rsaame.pas.pl.dao.IPublicLiabilityDAO;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.cmn.PASServiceTask;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.AppTypePrem;
import com.rsaame.pas.vo.app.ContentOpType;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.PremiumSummarySectionVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.IPolQuoType;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.ReferralMailTriggerFlow;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;
import com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder;
import com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder;

/*
 * Utility class which can be used within services
 * 
 */
public class SvcUtils {

	private final static Logger logger = Logger.getLogger(SvcUtils.class);
	public static String vatStartDate;
	public static String covStartDate;
	public static String polPreparedDt;
	public static String sysDt;
	/**
	 * @param uwQuestionsVO
	 * @return Underwriter response sequence in the format
	 *         Id1~response1|Id2~response2|..
	 */
	public static final String[] COMBINED_TARIFF_CODE = Utils.getMultiValueAppConfig("COMBINED_TARIFF_CODE");

	public static String getUWResponseSequence(UWQuestionsVO uwQuestionsVO) {
		// Used Stringbuffer to avoid "+" to fix sonar violation on 18-9-2017
		StringBuffer uwResponseSequenceBuffer = new StringBuffer();
		String uwResponseSequence = "";
		List<UWQuestionVO> uwQuestionVOs = uwQuestionsVO.getQuestions();
		for (UWQuestionVO uwQuestionVO : uwQuestionVOs) {
			uwResponseSequenceBuffer = uwResponseSequenceBuffer.append(uwQuestionVO.getQId()).append("~")
					.append(uwQuestionVO.getResponse()).append("|");
			// uwResponseSequence += uwQuestionVO.getQId() + "~"
			// + uwQuestionVO.getResponse() + "|";

		}
		uwResponseSequence = uwResponseSequence.concat(uwResponseSequenceBuffer.toString());
		return uwResponseSequence.substring(0, uwResponseSequence.length() - 1);
	}

	/*
	 * This method copies content of source referralVO to destination referralVO
	 * Method is called to put a record to policyVO referralMap with updated
	 * referralVOKey
	 */

	public static void copyReferralVO(ReferralVO srcReferralVO, ReferralVO destReferralVO) {

		destReferralVO.setSectionId(srcReferralVO.getSectionId());
		destReferralVO.setActionIdentifier(srcReferralVO.getActionIdentifier());
		destReferralVO.setConsolidated(srcReferralVO.isConsolidated());
		destReferralVO.setReferralText(srcReferralVO.getReferralText());
		destReferralVO.setPolLinkingId(srcReferralVO.getPolLinkingId());
		destReferralVO.setCreatedBy(srcReferralVO.getCreatedBy());
		destReferralVO.setConsolidated(srcReferralVO.isConsolidated());
		destReferralVO.setSectionName(srcReferralVO.getSectionName());
		destReferralVO.setStatusActive(srcReferralVO.getStatusActive());
	}

	public static Integer getUserId(BaseVO baseVO) {
		Integer userId = null;
		UserProfile profile = null;
		if (!Utils.isEmpty(baseVO.getLoggedInUser())) {
			profile = (UserProfile) baseVO.getLoggedInUser();
		}
		if (!Utils.isEmpty(profile) && !Utils.isEmpty(profile.getRsaUser())) {
			if (!Utils.isEmpty(profile.getRsaUser().getUserId()) && profile.getUserId() == null) {
				userId = profile.getRsaUser().getUserId();
			} else {
				userId = Integer.parseInt(profile.getUserId()); // Fix given for
																// Wunderman
																// WebService
																// for user 993
			}
		}
		return userId;
	}

	/**
	 * Finds and returns a specific look-up code's description.
	 * 
	 * @param category
	 * @param level1
	 * @param level2
	 * @param code
	 * @return
	 */
	public static String getLookUpDescription(String category, String level1, String level2, Integer code) {

		if (Utils.isEmpty(category) || Utils.isEmpty(code))
			return null;

		BigDecimal bCode = BigDecimal.valueOf(code);
		String desc = null;
		if (!Utils.isEmpty(bCode)) {
			ILookUpDAO luDAO = (ILookUpDAO) Utils.getBean(com.Constant.CONST_LOOKUPDAO);
			LookUpVO lu = (LookUpVO) luDAO.getDescription(getLookUpVO(category, level1, level2, bCode, null));

			desc = Utils.isEmpty(lu) ? null : lu.getDescription();
		}

		return desc;
	}

	/**
	 * Finds and returns a look-up code for specific look-up code's description.
	 * 
	 * @param category
	 * @param level1
	 * @param level2
	 * @param code
	 * @return
	 */

	public static Integer getLookUpCode(String category, String level1, String level2, String desc) {
		if (Utils.isEmpty(category) || Utils.isEmpty(desc))
			return 0;

		BigDecimal bcode = null;
		if (!Utils.isEmpty(desc)) {
			ILookUpDAO luDAO = (ILookUpDAO) Utils.getBean(com.Constant.CONST_LOOKUPDAO);
			LookUpVO lu = (LookUpVO) luDAO.getCode(getLookUpVO(category, level1, level2, null, desc));

			bcode = Utils.isEmpty(lu) ? null : lu.getCode();
		}

		Integer code = null;
		if (!Utils.isEmpty(bcode)) {
			code = bcode.intValue();
		}
		return code;
	}

	/**
	 * Fetches and returns all the codes configured for this combination of
	 * category, level1 and level2.
	 * 
	 * @param category
	 * @param level1
	 * @param level2
	 * @return
	 */
	public static LookUpListVO getLookUpCodesList(String category, String level1, String level2) {
		if (Utils.isEmpty(category))
			return null;

		ILookUpDAO luDAO = (ILookUpDAO) Utils.getBean(com.Constant.CONST_LOOKUPDAO);
		return (LookUpListVO) luDAO.getListOfDescription(getLookUpVO(category, level1, level2, null, null));
	}

	private static LookUpVO getLookUpVO(String category, String level1, String level2, BigDecimal code, String desc) {
		LookUpVO luVO = new LookUpVO();
		luVO.setCategory(category);
		luVO.setLevel1(level1);
		luVO.setLevel2(level2);
		luVO.setDescription(desc);
		luVO.setCode(code);
		return luVO;
	}

	/**
	 * Returns a suffix for use along with a Spring bean name.
	 * 
	 * @see PASServiceTask
	 * @param vo
	 * @return A suffix that can be appended to a bean name to get the instance
	 *         from Spring
	 */
	public static String getBeanNameSuffix(BaseVO vo) {
		String suffix = "";
		if (vo instanceof IPolQuoType) {
			if (!((IPolQuoType) vo).isQuote()) {
				suffix = "_POL";
			}
		} else if (vo instanceof PolicyVO) {
			if (!((PolicyVO) vo).getIsQuote()) {
				suffix = "_POL";
			}
		} else if (vo instanceof PolicyCommentsHolder) {
			if (!Utils.isEmpty(((PolicyCommentsHolder) vo).getPolicyDetails())) {
				if (!(((PolicyCommentsHolder) vo).getPolicyDetails()).getIsQuote()) {
					suffix = "_POL";
				}
			} else if (!Utils.isEmpty(((PolicyCommentsHolder) vo).getCommonDetails())) {
				if (!(((PolicyCommentsHolder) vo).getCommonDetails()).getIsQuote()) {
					suffix = "_POL";
				}
			}
		} else if (vo instanceof CommonVO) {
			CommonVO commonVO = (CommonVO) vo;
			if (!(commonVO.getIsQuote())) {
				suffix = "_POL";
			}
		} else if ((vo instanceof PolicyDataVO)) {
			suffix = getSuffixForPolicyDataVO(vo, suffix);
		} else if (vo instanceof DataHolderVO<?>) {
			if (((DataHolderVO) vo).getData() instanceof Object[]) {
				if (((DataHolderVO<Object[]>) vo).getData() != null
						&& ((DataHolderVO<Object[]>) vo).getData().length == 2) {
					if ((((DataHolderVO<Object[]>) vo).getData())[0] instanceof PolicyDataVO) {
						suffix = getSuffixForPolicyDataVO((PolicyDataVO) (((DataHolderVO<Object[]>) vo).getData())[0],
								suffix);
					}
				}
			}
		}

		return suffix;
	}

	private static String getSuffixForPolicyDataVO(BaseVO vo, String suffix) {
		PolicyDataVO policyDataVO = (PolicyDataVO) vo;
		if (!(policyDataVO.getCommonVO().getIsQuote())) {
			suffix = "_POL";
		}
		return suffix;
	}

	/**
	 * Premium table audit details populate is common across all risks hence
	 * moving to a util method
	 */

	public static void setAuditDetailsforPrm(TTrnPremiumQuo premiumQuo, PolicyVO policyVO, Date date) {
		Integer userId = SvcUtils.getUserId(policyVO);
		// TODO: Need to find a way to conditionally update the prepared by
		// columns. If its an update there is no need to update this column
		premiumQuo.setPrmPreparedBy(userId);
		premiumQuo.setPrmPreparedDt(date);

		premiumQuo.setPrmModifiedBy(userId);
		premiumQuo.setPrmModifiedDt(date);
	}

	/**
	 * This Method is used to get the total premium at section level.
	 * 
	 * @param section
	 * @return
	 */
	public static Double getSectionLevelPremium(SectionVO section) {
		double totalPremiumforSection = 0.0;
		String PAR_SECTION = "PAR_SECTION";
		if (!Utils.isEmpty(section)) {
			Map<? extends RiskGroup, ? extends RiskGroupDetails> riskDetails = section.getRiskGroupDetails();
			if (!Utils.isEmpty(riskDetails)) {
				for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> risk : riskDetails.entrySet()) {
					if (!Utils.isEmpty(risk)) {
						RiskGroupDetails groupDetails = risk.getValue();
						if (!Utils.isEmpty(groupDetails) && !Utils.isEmpty(groupDetails.getPremium())) {
							PremiumVO premiumVO = groupDetails.getPremium();
							totalPremiumforSection += premiumVO.getPremiumAmt();
						}
					}
				}

			}
		}
		return Double.valueOf(totalPremiumforSection);
	}

	/**
	 * This Method is used to get the total premium at class level.
	 * 
	 * @param policyVO
	 * @param classCodes
	 * @return
	 */
	public static Double getClassLevelPremium(PolicyVO policyVO, Short classCode) {
		List<SectionVO> sectionVOs = policyVO.getRiskDetails();

		double totalPremiumforClass = 0;
		for (SectionVO section : sectionVOs) {
			String code = Utils.getSingleValueAppConfig("SEC_" + section.getSectionId());
			Short sectionClassCode = new Short(code);
			if (!Utils.isEmpty(section)) {
				if ((sectionClassCode.shortValue() == classCode)) {
					totalPremiumforClass += getSectionLevelPremium(section);
				}
			}
		}

		return totalPremiumforClass;

	}

	/**
	 * Checks if this section is allowed to add risk groups (locations). If no,
	 * then throws BusinessException.
	 * 
	 * @param sectionId
	 *            SectionId for the section being checked
	 * @param locationVO
	 */
	public static void checkIfSectionCanAddRiskGroups(int sectionId, LocationVO locationVO) {
		if (Utils.isEmpty(locationVO) || Utils.isEmpty(locationVO.getRiskGroupId())
				|| locationVO.getRiskGroupId().startsWith("L")) {
			List<String> allowedSections = CopyUtils
					.asList(Utils.getMultiValueAppConfig("ADD_LOCATION_ALLOWED_SECTIONS"));

			if (Utils.isEmpty(allowedSections) || !allowedSections.contains(String.valueOf(sectionId))) {
				throw new BusinessException("pas.locComp.addRiskGroupsNotAllowed", null,
						"Risk Group Id has not been generated. This section is not allowed to add risk groups");
			}
		}
	}

	public static ContentOpType isContentChanged(PropertyRiskDetails propertyRiskDetails,
			List<TTrnContentQuo> oldListTrnContentQuo) {
		DecimalFormat newFormat = new DecimalFormat("#.##");

		if (Utils.isEmpty(oldListTrnContentQuo) && (propertyRiskDetails.getCover() > 0))
			return ContentOpType.ADD;
		if ((propertyRiskDetails.getCoverId() == -9999) && (propertyRiskDetails.getCover() == 0.0))
			return ContentOpType.NOCHANGE;
		for (TTrnContentQuo tTrnContentQuo : oldListTrnContentQuo) {
			if (propertyRiskDetails.getCoverId() == tTrnContentQuo.getId().getCntContentId()) {
				if (propertyRiskDetails.getCover() > 0) {
					// if
					// ((BigDecimal.valueOf(propertyRiskDetails.getCover())==tTrnContentQuo.getCntSumInsured())){
					// if
					// (Double.valueOf(newFormat.format(propertyRiskDetails.getCover())).compareTo(Double.valueOf(newFormat.format(tTrnContentQuo.getCntSumInsured().doubleValue()))){
					if (Double.valueOf(newFormat.format(propertyRiskDetails.getCover())).compareTo(
							Double.valueOf(newFormat.format(tTrnContentQuo.getCntSumInsured().doubleValue()))) == 0) {
						return ContentOpType.NOCHANGE;
					} else {
						return ContentOpType.MODI;
					}

				} else {
					return ContentOpType.DELE;
				}

			}

		}
		return ContentOpType.ADD;
	}

	public static ContentOpType isUwQChanged(TTrnUwQuestionsQuo quo, List<TTrnUwQuestionsQuo> oldListTrnUwqQuo) {

		for (TTrnUwQuestionsQuo tTrnUWqQuo : oldListTrnUwqQuo) {
			if (quo.getId().getUqtPolPolicyId() == tTrnUWqQuo.getId().getUqtPolPolicyId()) {
				if (!Utils.isEmpty(quo.getId().getUqtPolPolicyId())
						|| !Utils.isEmpty(tTrnUWqQuo.getId().getUqtPolPolicyId())) {
					if (Utils.isEmpty(quo.getUqtUwqAnswer()))
						return ContentOpType.NOCHANGE;
					if (quo.getUqtUwqAnswer().equalsIgnoreCase(tTrnUWqQuo.getUqtUwqAnswer())) {

						return ContentOpType.NOCHANGE;
					} else {
						return ContentOpType.MODI;
					}
				}
			}
		}
		return ContentOpType.ADD;
	}

	public static String populateTotFieldDetails(BaseVO input, AppTypePrem type, int sectionId) {

		StringBuffer str = new StringBuffer();
		switch (type) {
		case BUILDING:
			if (!Utils.isEmpty(input)) {
				if (input instanceof LocationVO) {

					LocationVO locationVO = (LocationVO) input;
					populateLocationFields(str, locationVO);
				}
			}
			break;

		case COVER:
			if (!Utils.isEmpty(input)) {
				if (input instanceof PropertyRiskDetails) {
					PropertyRiskDetails riskDetails = (PropertyRiskDetails) input;
					populateCoverFields(str, riskDetails);
				}
			}
			break;
		case UWQUESTIONS:
			if (!Utils.isEmpty(input)) {
				if (input instanceof UWQuestionVO) {
					UWQuestionVO uWQuestionVO = (UWQuestionVO) input;
					populateUWQuestionsFields(str, uWQuestionVO);
				}
			}
			break;
		case PREMIUM:
			switch (sectionId) {
			case 1:
				if (!Utils.isEmpty(input)) {
					if (input instanceof ParVO) {
						ParVO parVo = (ParVO) input;
						populatePremFieldValuesforPAR(str, parVo);
					}
				}
				break;
			}

			break;

		// sonar fix
		default:
			break;
		}
		return str.toString();
	}

	private static void populateUWQuestionsFields(StringBuffer str, UWQuestionVO uWQuestionVO) {
		str.append(uWQuestionVO.getQId());
		str.append(uWQuestionVO.getResponseType());

	}

	private static void populatePremFieldValuesforPAR(StringBuffer str, ParVO parVo) {

		str.append(parVo.getBldDeductibles());
		str.append(parVo.getBldDeductibles());

	}

	private static void populateCoverFields(StringBuffer str, PropertyRiskDetails riskDetails) {
		str.append(riskDetails.getBuildingId());
		str.append(riskDetails.getCoverId());
		str.append(riskDetails.getSetValidityStartDate());
		str.append(riskDetails.getCover());
		str.append(riskDetails.getDesc());
		str.append(riskDetails.getDeductibles());

		str.append(riskDetails.getCoverCode());
		str.append(riskDetails.getCoverType());
		str.append(riskDetails.getCoverSubType());
		str.append(riskDetails.getBasicRiskCode());
		str.append(riskDetails.getRiskCode());

		str.append(riskDetails.getRiskType());
		str.append(riskDetails.getRiskCat());
		str.append(riskDetails.getRiskSubCat());

	}

	private static void populateLocationFields(StringBuffer str, LocationVO locationVO) {
		str.append(locationVO.getAddress().getOfficeShopNo());
		str.append(locationVO.getAddress().getFloor());
		str.append(locationVO.getAddress().getBuildingName());
		str.append(locationVO.getAddress().getStreetName());
		str.append(locationVO.getAddress().getLocOverrideTer());
		str.append(locationVO.getAddress().getLocOverrideJur());

		str.append(locationVO.getOccTradeGroup());
		str.append(locationVO.getDirectorate());
		str.append(locationVO.getFreeZone());
		str.append(locationVO.getFreeZoneOthers());
		str.append(locationVO.getEmirates());
		str.append(locationVO.getValiditySrtDt());

	}

	public static Integer getClassCodeforSection(Integer sectionId) {

		switch (sectionId) {
		case 1:
			System.out.print("Case 1 for Section Id " + sectionId);
			return Integer.valueOf(Utils.getSingleValueAppConfig("PAR_CLASS"));

		case 6:
			System.out.print("Case 2 for Section Id " + sectionId);
			return Integer.valueOf(Utils.getSingleValueAppConfig("PAR_CLASS"));

		case 7:
			return Integer.valueOf(Utils.getSingleValueAppConfig("WC_CLASS"));

		case 8:
			return Integer.valueOf(Utils.getSingleValueAppConfig("MONEY_CLASS"));
		// sonar fix
		default:
			break;

		}
		return 0;
	}

	public static void main(String[] args) {
		/*
		 * TTrnPremiumQuo endorsedPremium = new TTrnPremiumQuo(); TTrnPremiumQuo
		 * prevPrmDetails = new TTrnPremiumQuo(); PolicyVO policyVO = new
		 * PolicyVO(); String str_date = "9-May-2013"; SimpleDateFormat sdf =
		 * new SimpleDateFormat( com.Constant.CONST_DD_MMM_YYYY ); try{ Date
		 * endDate = sdf.parse( str_date ); policyVO.setEndDate( endDate );
		 * str_date = "10-May-2012"; Date startDate = sdf.parse( str_date );
		 * policyVO.setStartDate( startDate ); } catch( ParseException e ){ //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * policyVO.setEndEffectiveDate( new Date() );
		 * prevPrmDetails.setPrmPremium( BigDecimal.valueOf( 1600.604 ) );
		 * endorsedPremium.setPrmPremiumActual( BigDecimal.valueOf( 1600.604 )
		 * );
		 */
		// calculateProratedPremium(endorsedPremium,policyVO,prevPrmDetails, 1);
		String dob = "01-AUG-1937";
		String effDate = "05-JUL-2013";

		DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);

		try {
			Date dateOfBirth = dateFormat.parse(dob);
			Date effectiveDate = dateFormat.parse(effDate);

			getAge(dateOfBirth, effectiveDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	public static void writeObjToFile(BaseVO baseVO) {

		try (OutputStream file = new FileOutputStream("D:/BaseVO");
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);) {
			output.writeObject(baseVO);
		} catch (IOException ex) {
			System.out.println("Some error is writing ");
			ex.printStackTrace();
		}
	}

	public static BaseVO readObjToFile() {
		BaseVO baseVO = null;

		try (InputStream file = new FileInputStream("D:/BaseVO");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);) {
			// deserialize the List
			baseVO = (BaseVO) input.readObject();

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return baseVO;
	}

	public static <T> boolean isRecordModified(T previousRecord, T PresentRecord)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		return isRecordModifiedCommon(previousRecord, PresentRecord, null);

	}

	/*
	 * isRecordModifiedCommon is added for Phase3-Home and Travel
	 * compareFieldName is null for SBS
	 */

	public static <T> boolean isRecordModifiedCommon(T previousRecord, T PresentRecord, String compareFieldName)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		boolean flag = false;
		if (compareFieldName == null)
			return compareBeans(previousRecord, PresentRecord, Utils.getMultiValueAppConfig(
					Utils.concat("FIELDS_TO_COMPARE_", previousRecord.getClass().getSimpleName())));

		else
			return compareBeans(previousRecord, PresentRecord,
					Utils.getMultiValueAppConfig(Utils.concat("FIELDS_TO_COMPARE_", compareFieldName)));
	}

	public static boolean compareBeans(Object newBean, Object oldBean, String[] fieldsToCompare)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (newBean.getClass() != oldBean.getClass()) {
			throw new IllegalArgumentException("The beans must be from the same Class!");
		}

		boolean changed = false;

		for (String fieldName : fieldsToCompare) {
			Object oldValue = PropertyUtils.getProperty(oldBean, fieldName);
			Object newValue = PropertyUtils.getProperty(newBean, fieldName);

			/*
			 * If both values are null or empty, it will be considered as equal.
			 * Continue with the next field.
			 */
			if (SvcUtils.isEmpty(oldValue) && SvcUtils.isEmpty(newValue)) {
				continue;
			}

			/*
			 * If one value is empty and the other is not, then the value has
			 * changed.
			 */
			if (!SvcUtils.isEmpty(oldValue) && SvcUtils.isEmpty(newValue))
				changed = true;
			else if (SvcUtils.isEmpty(oldValue) && !SvcUtils.isEmpty(newValue))
				changed = true;

			/* Both values are non-null. */
			else {
				/*
				 * In this case, handle BigDecimal specifically and rest with
				 * .equals().
				 * Handle insEmiratesIdExpiryDate and cshEmiratesIdExpiryDate for CTS - 21.10.2020 - CR#16903 IA Emirates CR W/O affecting existing functionality
				 */
				if (oldValue instanceof BigDecimal) {
					if (((BigDecimal) oldValue).setScale(5, BigDecimal.ROUND_CEILING)
							.compareTo(((BigDecimal) newValue).setScale(5, BigDecimal.ROUND_CEILING)) != 0) {
						changed = true;
					}
				} else if(fieldName.equalsIgnoreCase("insEmiratesIdExpiryDate") || fieldName.equalsIgnoreCase("cshEmiratesIdExpiryDate")) {
					if(((Date) oldValue).compareTo((Date) newValue) != 0) {
						changed = true;
					}
				} else
					changed = !oldValue.equals(newValue);
			}

			/*
			 * If the value for even one field has changed, we don't need to
			 * continue.
			 */
			if (changed)
				break;
		}

		return changed;
	}

	/**
	 * The purpose of this method is to wrap calls to Utils.isEmpty() overloaded
	 * methods when the reference to the object we have is of type
	 * java.lang.Object. Wrapping the calls helps invoke the specific isEmpty()
	 * method in Utils and get all the checks done for that particular type.
	 * 
	 * @param o
	 * @return
	 */
	private static boolean isEmpty(Object o) {
		boolean isEmpty = false;
		checkBlock: {
			if (o == null) {
				isEmpty = true;
				break checkBlock;
			}
			if (o instanceof String) {
				isEmpty = Utils.isEmpty((String) o);
				break checkBlock;
			}
			if (o instanceof Map) {
				isEmpty = Utils.isEmpty((Map) o);
				break checkBlock;
			}
			if (o instanceof java.util.Collection) {
				isEmpty = Utils.isEmpty((java.util.Collection) o);
				break checkBlock;
			}
			if (o instanceof Object[]) {
				isEmpty = Utils.isEmpty((Object[]) o);
				break checkBlock;
			}
			isEmpty = Utils.isEmpty(o);
		}

		return isEmpty;
	}

	/*
	 * hasDataChangedCommon is added for Phase3-Home and Travel This method is
	 * used to check the newdata with existingData.
	 */
	public static <T> boolean hasDataChangedCommon(T newData, T existingData, String tMasInsuredCommon) {
		System.out.println("NewData_1" + newData);
		System.out.println("NewData_2" + existingData);
		try {

			if (newData instanceof TMasInsured) {
				return SvcUtils.isRecordModifiedCommon(newData, existingData, tMasInsuredCommon);
			}

		} catch (IllegalAccessException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, tried to access private attribut_1");
		} catch (InvocationTargetException e) {
			throw new BusinessException("cmn.compareError", null, "Error in compar_1");
		} catch (NoSuchMethodException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, one of the field is not present in poj_1");
		}

		return false;
	}

	/**
	 * Compares two records and returns true if they are different based on the
	 * comparison rules. The comparison rules are different for different
	 * classes. Hence, this method internally calls other methods that handle
	 * specific cases.
	 * 
	 * @param newData
	 * @param existingData
	 * @return
	 */

	public static <T> boolean hasDataChanged(T newData, T existingData) {
		System.out.println("NewData_3" + newData);
		System.out.println("NewData_4" + existingData);
		try {
			if (newData instanceof TTrnBuildingQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnContentQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnUwQuestionsQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnPremiumQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TMasCashCustomerQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnGaccCashQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnGaccCashDetailsQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnGaccBuildingQuo) {
				/*
				 * TTrnGaccBuildingQuo data is derived from ttrnbuilding and
				 * this record is not deleted unless there is a location delete
				 * which is taken care by a stored proc
				 */
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnWctplUnnamedPersonQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnWctplPremiseQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TMasInsured) {
				return SvcUtils.isRecordModified(newData, existingData);
			}

			if (newData instanceof TTrnPolicyQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}

			if (newData instanceof TTrnSectionDetailsQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}

			if (newData instanceof TTrnNonStdTextQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}

			if (newData instanceof TTrnGaccPersonQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnConsequentialLossQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnColWorkSheetQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnMarineTransitQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnMarineDetailQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnMarineHeaderQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnGaccUnnamedPersonQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}
			if (newData instanceof TTrnWctplPersonQuo) {
				return SvcUtils.isRecordModified(newData, existingData);
			}

		} catch (IllegalAccessException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, tried to access private attribut_2");
		} catch (InvocationTargetException e) {
			throw new BusinessException("cmn.compareError", null, "Error in compar_2");
		} catch (NoSuchMethodException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, one of the field is not present in poj_2");
		}

		return false;
	}

	/*
	 * This method will return flag whether the policy is cancelled based on the
	 * POLICY_STATUS_CODE value from ThreadLevelContext
	 */
	public static Boolean isPolicyCancelled() {
		if (!Utils.isEmpty(ThreadLevelContext.get(SvcConstants.POLICY_STATUS_CODE))
				&& ThreadLevelContext.get(SvcConstants.POLICY_STATUS_CODE)
						.equals(Integer.valueOf(Utils.getSingleValueAppConfig("POLICY_CANCELLED")))) {
			return true;
		}
		return false;
	}

	/*
	 * This method is used to check whether for a given sectionId , section is
	 * present in policyDetails or not
	 * 
	 * @param sectionId
	 * 
	 * @param policyDetails
	 * 
	 * @return boolean
	 */
	public static boolean isSectionPresent(int sectionId, PolicyVO policyDetails) {
		SectionVO section = new SectionVO(RiskGroupingLevel.LOCATION);
		section.setSectionId(sectionId);
		return policyDetails.getRiskDetails().contains(section);
	}

	public static BigDecimal getNonNullPrmSI(BigDecimal prmSi) {
		return Utils.isEmpty(prmSi) ? BigDecimal.valueOf(SvcConstants.zeroVal) : prmSi;
	}

	public static String getCancelledPendingPrm(Object[] queryString) {

		return "update t_trn_premium set prm_status = 6 , prm_premium = " + queryString[0] + " , prm_rsk_id = "
				+ queryString[1] + " where trunc(prm_validity_expiry_date) = '31-DEC-2049' and " + "prm_policy_id = "
				+ queryString[2] + " and prm_basic_rsk_id = " + queryString[3] + " and prm_rsk_code = " + queryString[4]
				+ " and prm_cov_code = " + queryString[5] + " " + "and prm_ct_code = " + queryString[6]
				+ " and prm_cst_code = " + queryString[7] + " and prm_endt_id = " + queryString[8]
				+ " and prm_status = 4";
	}

	public static TTrnPremiumQuo getPremiumQuoSpecialCovers(String coverCode, TTrnPolicyQuo policyQuo) {

		TTrnPremiumQuo premiumQuo = BeanMapper.map(policyQuo, TTrnPremiumQuo.class);

		if (!Utils.isEmpty(premiumQuo.getId())) {
			TTrnPremiumQuoId id = premiumQuo.getId();

			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory
					.getInstance(com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "");

			id.setPrmBasicRskCode(Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			id.setPrmBasicRskId(converter.getAFromB(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			id.setPrmCovCode(Short.parseShort(coverCode));
			id.setPrmCtCode(SvcConstants.SC_PRM_CT_CODE);
			id.setPrmCstCode(SvcConstants.SC_PRM_CT_CODE);
			id.setPrmRskCode(Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			id.setPrmRskId(converter.getAFromB(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));

			premiumQuo.setId(id);
			premiumQuo.setPrmPtCode(Short.parseShort(Utils.getSingleValueAppConfig("POLICY_TYPES")));
			premiumQuo.setPrmRtCode(Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			premiumQuo.setPrmRcCode(Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			premiumQuo.setPrmRscCode(Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_SPECIAL_CODE)));
			premiumQuo.setPrmSumInsured(BigDecimal.valueOf(SvcConstants.SC_PRM_SUM_INSURED));
			premiumQuo.setPrmPremium(BigDecimal.valueOf(SvcConstants.SC_PRM_SUM_INSURED));
			premiumQuo.setPrmRate(BigDecimal.valueOf(SvcConstants.SC_PRM_SUM_INSURED));
			premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(SvcConstants.SC_PRM_SUM_INSURED));
			premiumQuo.setPrmExcessRate(BigDecimal.valueOf(SvcConstants.SC_PRM_SUM_INSURED));
			premiumQuo.setPrmPremiumCurr(
					Byte.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_CURRENCY)));

			premiumQuo.setPrmRiRskCode(SvcConstants.SC_PRM_RI_RSK_CODE); //
			premiumQuo.setPrmSitypeCode(SvcConstants.SC_PRM_SI_TYPE);
			premiumQuo.setPrmSumInsuredCurr(SvcConstants.SC_PRM_SI_CURR);
			premiumQuo.setPrmRateType(SvcConstants.SC_PRM_RATE_TYPE);
			premiumQuo.setPrmOldPremium(BigDecimal.valueOf(SvcConstants.SC_PRM_OLD_PRM));

			premiumQuo.setPrmModifiedBy(policyQuo.getPolPreparedBy());
			premiumQuo.setPrmModifiedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
			premiumQuo.setPrmPreparedBy(policyQuo.getPolModifiedBy());
			if (!Utils.isEmpty(ThreadLevelContext.get("PRM_PREPARED_DATE"))) {
				premiumQuo.setPrmPreparedDt((Date) ThreadLevelContext.get("PRM_PREPARED_DATE"));
			} else {
				premiumQuo.setPrmPreparedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));
			}
			premiumQuo.setPrmRiLocCode(SvcConstants.APP_PRM_RI_LOC_CODE);
		}
		return premiumQuo;
	}

	public static void updateCommonPrmFields(PolicyVO policyVO, TTrnPremiumQuo mappedRec) {

		mappedRec.setPrmValidityExpiryDate(SvcConstants.EXP_DATE);
		mappedRec.setPrmEndtId((Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID));
		mappedRec
				.setPrmSumInsuredCurr(Byte.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_CURRENCY)));
		mappedRec.setPrmPremiumCurr(Byte.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_CURRENCY)));
		mappedRec.setPrmPtCode(Short.valueOf(Utils.getSingleValueAppConfig("SBS_Policy_Type")));
		// premiumQuo.setPrmEffectiveDate( policyVO.getStartDate() );
		mappedRec.setPrmEffectiveDate(policyVO.getScheme().getEffDate());
		mappedRec.setPrmExpiryDate(policyVO.getEndDate());
		mappedRec.setPrmFnCode(SvcConstants.PRM_FN_CODE);
		mappedRec.setPrmPreparedDt((Date) ThreadLevelContext.get(com.Constant.CONST_SYSDATE));

		mappedRec.setPrmRiLocCode(SvcConstants.APP_PRM_RI_LOC_CODE);

	}

	public static Date getVED() {
		SimpleDateFormat generalDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		Date vedDate = null;
		try {
			vedDate = generalDateFormat.parse("12-31-2049 12:00:00");
		} catch (ParseException e) {
			throw new SystemException("", null, "Error in parsing VED: Critical error");
		}
		return vedDate;
	}

	/**
	 * Returns always latest endt Id.
	 * 
	 * @param policyVO
	 * @return
	 */
	public static Long getLatestEndtId(PolicyVO policyVO) {
		Long endtId = 0L;

		if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getEndtId() < policyVO.getNewEndtId()) {
			endtId = policyVO.getNewEndtId();
		} else {
			if (!Utils.isEmpty(policyVO.getEndtId())) {
				endtId = policyVO.getEndtId();
			}
		}

		return endtId;
	}

	public static <T> boolean hasLocDataChanged(T newData, T existingData)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		if (newData instanceof TTrnBuildingQuo) {
			return SvcUtils.isLocRecordModified(newData, existingData);
		}
		if (newData instanceof TTrnWctplPremiseQuo) {
			return SvcUtils.isLocRecordModified(newData, existingData);
		}

		return false;
	}

	public static <T> boolean isLocRecordModified(T previousRecord, T PresentRecord)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return compareBeans(previousRecord, PresentRecord, Utils.getMultiValueAppConfig(
				Utils.concat("LOC_FIELDS_TO_COMPARE_", previousRecord.getClass().getSimpleName())));
	}

	public static boolean hasLocDataChanged() {

		boolean hasDataChnaged = false;
		if (!Utils.isEmpty(ThreadLevelContext.get(SvcConstants.OLD_REC))
				&& !Utils.isEmpty(ThreadLevelContext.get(SvcConstants.NEW_REC))) {
			try {
				hasDataChnaged = isLocRecordModified(ThreadLevelContext.get(SvcConstants.OLD_REC),
						ThreadLevelContext.get(SvcConstants.NEW_REC));
			} catch (IllegalAccessException e) {
				throw new BusinessException("cmn.compareError", null,
						"Error in compare, tried to access private attribut_3");
			} catch (InvocationTargetException e) {
				throw new BusinessException("cmn.compareError", null, "Error in compar_3");
			} catch (NoSuchMethodException e) {
				throw new BusinessException("cmn.compareError", null,
						"Error in compare, one of the field is not present in poj_3");
			}
		}
		return hasDataChnaged;
	}

	public static double premiumRoundOff(double calculatedPremium) {

		NumberFormat formatter = new DecimalFormat(SvcConstants.DEFAULT_CURRENCY_FORMAT);
		return Double.valueOf(formatter.format(calculatedPremium));

	}

	public static Date getExpDateFromEffDate(Date effDate) {
		Calendar polEffDate = Calendar.getInstance();
		polEffDate.setTime(effDate);
		polEffDate.add(Calendar.YEAR, SvcConstants.POLICY_TERM);
		polEffDate.add(Calendar.DATE, SvcConstants.EXCLUDE_CURRENT_DAY);
		return polEffDate.getTime();
	}

	public static void setStartEndDate(TTrnPolicyQuo policyRec, Date polEffDate) {
		policyRec.setPolEffectiveDate(polEffDate);
		// Commented as part of policy extension changes
		// policyRec.setPolExpiryDate( SvcUtils.getExpDateFromEffDate(
		// polEffDate ) );
	}

	public static char getQuoteOrPloicyFlag(boolean isQuote) {
		return isQuote ? 'Q' : 'P';
	}

	/**
	 * Fetches and returns the code configured for this combination of category,
	 * level1 and level2.
	 * 
	 * @param category
	 * @param level1
	 * @param level2
	 * @return
	 */
	public static Short getLookUpCodeForLOneLTwo(String category, String level1, String level2) {
		if (Utils.isEmpty(category))
			return null;

		ILookUpDAO luDAO = (ILookUpDAO) Utils.getBean(com.Constant.CONST_LOOKUPDAO);
		LookUpListVO lookUpListVO = (LookUpListVO) luDAO
				.getListOfDescription(getLookUpVO(category, level1, level2, null, null));
		if (!Utils.isEmpty(lookUpListVO) && !Utils.isEmpty(lookUpListVO.getLookUpList())
				&& !Utils.isEmpty(lookUpListVO.getLookUpList().get(0).code)) {
			return Short.valueOf(lookUpListVO.getLookUpList().get(0).code.toString());
		} else {
			return null;
		}
	}

	/*
	 * Added the below method to address 120476 by Vishwa and now adding the
	 * changes to all the location
	 */
	public static Double getLookUpCodeForLOneLTwo(String category, String level1, String level2, String identifier) {
		if (Utils.isEmpty(category))
			return null;

		ILookUpDAO luDAO = (ILookUpDAO) Utils.getBean(com.Constant.CONST_LOOKUPDAO);
		LookUpListVO lookUpListVO = (LookUpListVO) luDAO
				.getListOfDescription(getLookUpVO(category, level1, level2, null, null));
		if (!Utils.isEmpty(lookUpListVO) && !Utils.isEmpty(lookUpListVO.getLookUpList())
				&& !Utils.isEmpty(lookUpListVO.getLookUpList().get(0).code)) {
			return Double.valueOf(lookUpListVO.getLookUpList().get(0).code.toString());
		} else {
			return null;
		}
	}

	/*
	 * gets the discount or loading for cover code 51
	 */
	public static BigDecimal setDiscLoadPrm(PolicyVO policyVO, BigDecimal clPrm) {

		BigDecimal discOrLoadAmt = new BigDecimal(SvcConstants.zeroVal);

		if (policyVO.getPremiumVO().getDiscOrLoadPerc() < SvcConstants.MIN_PERCENTAGE) {
			throw new BusinessException("cmn.unknownError", null, "The percentage value cannot be less than -100");
		}
		if (!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getPremiumVO())
				&& !Utils.isEmpty(policyVO.getPremiumVO().getDiscOrLoadPerc())
				&& !policyVO.getPremiumVO().getDiscOrLoadPerc().equals(Double.valueOf(SvcConstants.zeroVal))) {
			// discOrLoadAmt = BigDecimal.valueOf( clPrm.doubleValue() /
			// policyVO.getPremiumVO().getDiscOrLoadPerc() ) ; //clPrm.divide(
			// BigDecimal.valueOf( policyVO.getPremiumVO().getDiscOrLoadPerc() )
			// , RoundingMode.HALF_DOWN );
			discOrLoadAmt = getNonNull(BigDecimal
					.valueOf((getNonNull(clPrm).doubleValue() * policyVO.getPremiumVO().getDiscOrLoadPerc()) / 100));
		}

		return discOrLoadAmt;// clPrm/policyVO.getPremiumVO().getDiscOrLoadPerc();
	}

	public static Byte setDiscLoadRateType(PolicyVO policyVO) {

		/*
		 * Byte rateType = SvcConstants.SC_PRM_RATE_TYPE; if( !Utils.isEmpty(
		 * policyVO ) && !Utils.isEmpty( policyVO.getPremiumVO() ) ){
		 * if(!policyVO.getPremiumVO().isLoading()){ rateType = Byte.valueOf(
		 * SvcConstants.SC_PRM_RATE_TYPE_DISC); } }
		 */
		return SvcConstants.SC_PRM_RATE_TYPE;
	}

	public static boolean setLoadingFlag(Byte prmRateType) {
		return Integer.valueOf(prmRateType.toString()) < 0 ? false : true;
	}

	public static BigDecimal getNonNull(BigDecimal val) {
		if (Utils.isEmpty(val)) {
			return BigDecimal.valueOf(0);
		}
		return val;
	}

	public static Date incrementVSD(Date vsd) {
		Calendar revisedVDS = Calendar.getInstance();
		revisedVDS.setTime(vsd);
		revisedVDS.add(Calendar.SECOND, SvcConstants.VSD_INCREMENT_SEC);
		return revisedVDS.getTime();
	}

	public static int getYearFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static String getMonthFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer monthInt = cal.get(Calendar.MONTH) + 1;
		String monthStr = monthInt.toString();
		if (monthInt <= 9) {
			monthStr = "0" + monthInt.toString();
		}
		return monthStr;
	}

	public static String getDayFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// return cal.get(Calendar.DATE);
		Integer dateInt = cal.get(Calendar.DATE);
		String dateStr = dateInt.toString();
		if (dateInt <= 9) {
			dateStr = "0" + dateInt.toString();
		}

		return dateStr;

	}

	/**
	 * @param policyVO
	 * @return
	 */
	public static double getTotalPremium(PolicyVO policyVO) {

		Double totalPrm = 0.0;

		if (!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getPremiumVO())
				&& !Utils.isEmpty(policyVO.getPremiumVO().getPremiumAmt())
				&& !Utils.isEmpty(policyVO.getPremiumVO().getDiscOrLoadPerc())
				&& !policyVO.getPremiumVO().getDiscOrLoadPerc().equals(Double.valueOf(SvcConstants.zeroVal))) {
			totalPrm = policyVO.getPremiumVO().getPremiumAmt()
					+ policyVO.getPremiumVO().getPremiumAmt() * policyVO.getPremiumVO().getDiscOrLoadPerc() / 100;
		} else if (!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getPremiumVO())
				&& !Utils.isEmpty(policyVO.getPremiumVO().getPremiumAmt())) {
			totalPrm = policyVO.getPremiumVO().getPremiumAmt();
		}

		return totalPrm;
	}

	public static BigDecimal getRateAmt(BigDecimal govtTaxRate, BigDecimal clPrm) {
		return !Utils.isEmpty(govtTaxRate) ? ((clPrm.multiply(govtTaxRate)).divide(BigDecimal.valueOf(100.00)))
				: BigDecimal.ZERO;
	}

	/**
	 * @param questionVO
	 * @param policyId
	 * @param endtId
	 * @param validityStartDate
	 * @return
	 */
	public static TTrnUwQuestionsQuo getUWAPojo(UWQuestionVO question, Long policyId, Long endtId,
			Date validityStartDate) {
		TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
		TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
		id.setUqtPolPolicyId(policyId);
		id.setUqtPolEndtId(endtId);
		id.setUqtUwqCode(question.getQId());
		id.setUqtLocId(SvcConstants.zeroVal);
		uwQuestionsQuo.setId(id);
		uwQuestionsQuo.setStatus(SvcConstants.POL_STATUS_PENDING);
		uwQuestionsQuo.setUqtUwqAnswer(question.getResponse());
		uwQuestionsQuo.setUqtValidityStartDate(validityStartDate);
		uwQuestionsQuo.setUqtValidityExpiryDate(SvcConstants.EXP_DATE);
		return uwQuestionsQuo;
	}

	/**
	 * This method calls the required VO to Pojo mappers dynamically.
	 * 
	 * @param tableInExecution
	 * @param obj
	 * @param tableData
	 * @param commonVO
	 * @return
	 */
	public static POJOWrapper mapBean(String tableInExecution, TableData<BaseVO> tableData, CommonVO commonVO) {

		POJOWrapper pojo = (POJOWrapper) Utils.getBean(tableInExecution);
		// POJOWrapperId id = (POJOWrapperId)Utils.getBean(
		// tableInExecution+"_ID" );
		BaseBeanToBeanMapper mapperClass = (BaseBeanToBeanMapper) Utils.getBean(tableInExecution + "Mapper");
		pojo = (POJOWrapper) mapperClass.mapBean(tableData.getTableData(), pojo);

		return pojo;
	}

	/**
	 * This methods calculates the age of the passenger from dob to pol
	 * effective date
	 * 
	 * @param dateOfBirth
	 * @param effectiveDate
	 * @return
	 */
	public static Short getAge(Date dateOfBirth, Date effectiveDate) {
		Short age = null;
		if (dateOfBirth != null && effectiveDate != null) {
			/*
			 * Calendar dob = Calendar.getInstance(); dob.setTime( dateOfBirth
			 * );
			 * 
			 * int diff = 0; int ageValue = 0; Calendar effDate =
			 * Calendar.getInstance(); effDate.setTime( effectiveDate );
			 * 
			 * DateFormat dateFormat = new
			 * SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
			 * 
			 * 
			 * String sql = "SELECT MONTHS_IN_BETWEEN('"+dateFormat.format(
			 * effectiveDate )+","+dateFormat.format( dateOfBirth
			 * )+")/12 from dual";
			 * 
			 * DAOUtils.getSqlResultForPas( sql);
			 * 
			 * if( ( effDate.get( Calendar.MONTH ) < dob.get( Calendar.MONTH ) )
			 * || ( ( effDate.get( Calendar.MONTH ) == dob.get( Calendar.MONTH )
			 * ) && ( effDate.get( Calendar.DAY_OF_MONTH ) < dob.get(
			 * Calendar.DAY_OF_MONTH ) ) ) ){ diff = -1; } else if( (
			 * effDate.get( Calendar.MONTH ) > dob.get( Calendar.MONTH ) ) || (
			 * ( effDate.get( Calendar.MONTH ) == dob.get( Calendar.MONTH ) ) &&
			 * ( effDate.get( Calendar.DAY_OF_MONTH ) > dob.get(
			 * Calendar.DAY_OF_MONTH ) ) ) ){ diff = 1; }
			 * 
			 * ageValue = effDate.get( Calendar.YEAR ) - dob.get( Calendar.YEAR
			 * ) + diff;
			 * 
			 * age = Short.valueOf( String.valueOf( ageValue ) );
			 */

			DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);

			String sql = "SELECT MONTHS_BETWEEN('" + dateFormat.format(effectiveDate) + "','"
					+ dateFormat.format(dateOfBirth) + "')/12 from dual";

			List<Object> result = DAOUtils.getSqlResultForPas(sql);

			BigDecimal bigDecimalAge = (BigDecimal) result.get(0);

			int scaleValue = bigDecimalAge.scale();

			Short ageValue = bigDecimalAge.shortValue();

			if (scaleValue > 0 && ageValue >= 65) {
				ageValue++;
			}

			age = ageValue;
		}

		return age;
	}

	/**
	 * @param lob
	 * @param req
	 *            This method is used to map the request data to corresponding
	 *            VO based on LOB
	 * @param commonVO
	 */
	public static PolicyDataVO mapGeneralInfoVO(String lob, HttpServletRequest req, CommonVO commonVO) {
		PolicyDataVO polData = (PolicyDataVO) Utils.getBean(SvcConstants.VO + lob);
		BaseBeanToBeanMapper mapperClass = (BaseBeanToBeanMapper) Utils.getBean(SvcConstants.Mapper + lob);
		polData = (PolicyDataVO) mapperClass.mapBean(req, polData);
		if (!Utils.isEmpty(polData.getEndEffectiveDate())) {
			commonVO.setEndtEffectiveDate(polData.getEndEffectiveDate());
		}
		return polData;
	}

	/**
	 * Method to set the default values for int, short, long and double before
	 * the bean utils object is created
	 * 
	 * @return
	 */
	public static BeanUtilsBean getBeanUtilBean() {
		Converter wrapperInteger = new IntegerConverter(null);
		Converter nativeInteger = new IntegerConverter(0);

		Converter wrapperLong = new LongConverter(null);
		Converter nativeLong = new LongConverter(0);

		Converter wrapperDouble = new DoubleConverter(null);
		Converter nativeDouble = new DoubleConverter(0);

		Converter wrapperShort = new ShortConverter(null);
		Converter nativeShort = new ShortConverter(0);

		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
		convertUtilsBean.register(wrapperInteger, Integer.class);
		convertUtilsBean.register(nativeInteger, Integer.TYPE);

		convertUtilsBean.register(wrapperLong, Long.class);
		convertUtilsBean.register(nativeLong, Long.TYPE);

		convertUtilsBean.register(wrapperDouble, Double.class);
		convertUtilsBean.register(nativeDouble, Double.TYPE);

		convertUtilsBean.register(wrapperShort, Short.class);
		convertUtilsBean.register(nativeShort, Short.TYPE);

		return new BeanUtilsBean(convertUtilsBean);
	}

	/**
	 * Method to compare the saved cover list with to be saved cover list and
	 * update the to be deleted flag.
	 * 
	 * @param savedCovers
	 *            of type List<TableData>
	 * @param tobeSavedCovers
	 *            of type List<TableData>
	 * @return tobeSavedCovers
	 * @author Sarath
	 * @since Phase 3 - Home & Travel Insurance
	 */
	public static List<TableData> updateToBeDeletedCovers(List<TableData> savedCovers,
			@SuppressWarnings("rawtypes") List<TableData> tobeSavedCovers) {

		TableData<CoverDetailsVO> tDataNew;
		List<TableData> contentTableDataList = new ArrayList<TableData>(0);
		Map<String, Object> toBeSaved = getToBeSavedCovers(tobeSavedCovers);
		for (TableData<?> tData : savedCovers) {
			CoverDetailsVO cover = (CoverDetailsVO) tData.getTableData();
			tDataNew = new TableData<CoverDetailsVO>();
			CoverDetailsVO coverToSave = (CoverDetailsVO) checkContains(toBeSaved, cover);
			if (Utils.isEmpty(coverToSave)) {
				tData.setToBeDeleted(true);
				contentTableDataList.add(tData);
			} else {
				coverToSave.setVsd(cover.getVsd());
				tDataNew.setTableData(coverToSave);
				tDataNew.setToBeDeleted(false);
				contentTableDataList.add(tDataNew);
			}
		}
		/* Add new covers into the list of covers to be saved. */
		// TODO : Reduce iterations in second for loop for optimisation.
		Map<String, Object> alreadySaved = getToBeSavedCovers(savedCovers);
		for (TableData<?> tableData : tobeSavedCovers) {
			// tDataNew = new TableData<CoverDetailsVO>();
			CoverDetailsVO coverToSave = (CoverDetailsVO) checkContains(alreadySaved, tableData.getTableData());
			if (Utils.isEmpty(coverToSave)) {
				contentTableDataList.add(tableData);
			}
		}
		return contentTableDataList;
	}

	public static List<TableData> updateToBeDeletedPremiums(List<TableData> savedCovers,
			@SuppressWarnings("rawtypes") List<TableData> tobeSavedCovers) {
		Map<String, Object> toBeSaved = getToBeSavedCovers(tobeSavedCovers);
		List<TableData> premiumTableDataList = new ArrayList<TableData>(0);
		TableData<TTrnPremiumVOHolder> premiumTableData = null;
		for (TableData<?> tData : savedCovers) {
			premiumTableData = new TableData<TTrnPremiumVOHolder>();
			TTrnPremiumVOHolder premiumVOHolder;
			premiumVOHolder = (TTrnPremiumVOHolder) checkContains(toBeSaved, tData.getTableData());
			if (Utils.isEmpty(premiumVOHolder)) {
				tData.setToBeDeleted(true);
				((TTrnPremiumVOHolder) tData.getTableData()).setPrmPremium(new BigDecimal(0));
				premiumTableDataList.add(tData);
			} else {
				premiumVOHolder.setPrmValidityStartDate(
						((TTrnPremiumVOHolder) tData.getTableData()).getPrmValidityStartDate());
				premiumTableData.setTableData(premiumVOHolder);
				premiumTableDataList.add(premiumTableData);
			}
		}
		/* Add new covers into the list of covers to be saved. */
		// TODO : Reduce iterations in second for loop for optimisation.
		Map<String, Object> alreadySaved = getToBeSavedCovers(savedCovers);
		for (TableData<?> tableData : tobeSavedCovers) {
			// premiumTableData = new TableData<TTrnPremiumVOHolder>();
			TTrnPremiumVOHolder premiumVOHolder;
			premiumVOHolder = (TTrnPremiumVOHolder) checkContains(alreadySaved, tableData.getTableData());
			if (Utils.isEmpty(premiumVOHolder)) {
				premiumTableDataList.add(tableData);
			}
		}
		return premiumTableDataList;
	}

	/**
	 * Check whether the to be saved cover details contain saved covers
	 * 
	 * @param coverMap
	 * @param coverVO
	 * @return boolean
	 */
	private static Object checkContains(Map<String, Object> coverMap, Object cover) {

		String key = null;
		if (cover instanceof CoverDetailsVO) {
			CoverDetailsVO coverDetailsVO = (CoverDetailsVO) cover;
			String riskCat = coverDetailsVO.getRiskCodes().getRiskCat() != null
					? coverDetailsVO.getRiskCodes().getRiskCat().toString()
					: "0";
			String riskId = coverDetailsVO.getRiskCodes().getRskId() != null
					? coverDetailsVO.getRiskCodes().getRskId().toString()
					: "0";
			key = riskId + coverDetailsVO.getRiskCodes().getRiskCode().toString()
					+ coverDetailsVO.getRiskCodes().getRiskType().toString() + riskCat;
		} else if (cover instanceof TTrnPremiumVOHolder) {
			TTrnPremiumVOHolder premiumData = (TTrnPremiumVOHolder) cover;
			String riskId = premiumData.getPrmRskId() != null ? premiumData.getPrmRskId().toString() : "0";
			key = riskId + String.valueOf(premiumData.getPrmRskCode()) + String.valueOf(premiumData.getPrmRtCode())
					+ String.valueOf(premiumData.getPrmRcCode()) + String.valueOf(premiumData.getPrmCovCode());
		}
		return coverMap.get(key);
	}

	/**
	 * Create map of to be saved cover details
	 * 
	 * @param coverDetails
	 *            of type List<TableData>
	 * @return cover details map
	 */
	private static Map<String, Object> getToBeSavedCovers(@SuppressWarnings("rawtypes") List<TableData> coverDetails) {
		Map<String, Object> coverMap = new HashMap<String, Object>();
		for (TableData<?> tdata : coverDetails) {
			if ((tdata.getTableData()) instanceof CoverDetailsVO) {
				CoverDetailsVO coverDetailsVO = (CoverDetailsVO) tdata.getTableData();
				String riskCat = coverDetailsVO.getRiskCodes().getRiskCat() != null
						? coverDetailsVO.getRiskCodes().getRiskCat().toString()
						: "0";
				String riskId = coverDetailsVO.getRiskCodes().getRskId() != null
						? coverDetailsVO.getRiskCodes().getRskId().toString()
						: "0";
				String key = riskId + coverDetailsVO.getRiskCodes().getRiskCode().toString()
						+ coverDetailsVO.getRiskCodes().getRiskType().toString() + riskCat;
				coverMap.put(key, coverDetailsVO);
			} else if ((tdata.getTableData()) instanceof TTrnPremiumVOHolder) {
				TTrnPremiumVOHolder premiumData = (TTrnPremiumVOHolder) tdata.getTableData();
				String riskId = premiumData.getPrmRskId() != null ? premiumData.getPrmRskId().toString() : "0";
				String key = riskId + String.valueOf(premiumData.getPrmRskCode())
						+ String.valueOf(premiumData.getPrmRtCode()) + String.valueOf(premiumData.getPrmRcCode())
						+ String.valueOf(premiumData.getPrmCovCode());
				coverMap.put(key, premiumData);
			}
		}
		return coverMap;
	}

	/**
	 * 
	 * @param policyVO
	 * @return
	 */
	public static String getKeyForCommisionCacheObj(PolicyDataVO policyDataVO) {

		String brokerId = null;
		String agentId = null;

		/*
		 * Added new criteria while fetching the commission value. This criteria
		 * is required as different broker/Agent can have different commission
		 * configured for the same scheme and policy type combination
		 */
		if (!Utils.isEmpty(policyDataVO.getGeneralInfo())
				&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())
				&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName())) {
			brokerId = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString();
		}

		if (!Utils.isEmpty(policyDataVO.getGeneralInfo())
				&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())
				&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent())) {
			agentId = policyDataVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().toString();
		}

		String channelNameKeyVal = !Utils.isEmpty(brokerId) ? brokerId : agentId;

		return channelNameKeyVal;
	}

	/**
	 * @param policyDataVO
	 * @param schemeCode
	 * @param travelInsuranceVO
	 * @return
	 */
	// Modified by Vishwa for the 120476 to Oman and now adding the changes to
	// all the location
	public static Double getCommission(PolicyDataVO policyDataVO, String schemeCode) {
		if (!Utils.isEmpty(policyDataVO.getCommission())) {
			return policyDataVO.getCommission();
		} else {
			Double comm = null;
			Double commission = SvcUtils.getLookUpCodeForLOneLTwo("PAS_COMMISSION", schemeCode,
					SvcUtils.getKeyForCommisionCacheObj(policyDataVO), " ");
			if (!Utils.isEmpty(commission)) {
				comm = commission.doubleValue();
			}
			return comm;
		}
	}

	/**
	 * Method to calculate the discount premium for promotional code
	 * 
	 * @param premiumAmt
	 * @param discount
	 * @return
	 */
	public static double getPromoDiscount(double premiumAmt, Double discount) {
		if (!Utils.isEmpty(discount)) {
			return premiumAmt - (premiumAmt * discount / 100);
		}

		return premiumAmt;

	}

	/*
	 * This method will return the base appFlow for RESOLVE_REFERAL flow in case
	 * of Home/Travel 1. Used in endorsement RESOLVE_REFERAL flow 2. Based on
	 * the flow returned here, the endorsement text and endorsement related
	 * buttons will be displayed on premium page 3. This is also used in
	 * PremiumPageRH for getting EndorsementSummary
	 */
	public static Flow getBasicFlowCommonResolveReferral(BaseVO baseVO) {

		Flow appFlow = null;
		if (baseVO instanceof CommonVO) {
			CommonVO p = (CommonVO) baseVO;
			appFlow = p.getAppFlow();
			TaskVO taskDetails = p.getTaskDetails();

			/*
			 * In the case of referrals, we need to send one of EDIT_QUO,
			 * VIEW_QUO, AMEND_POL or VIEW_POL to the service layer based on
			 * whether the logged in user is the initiator of the
			 */
			if (!Utils.isEmpty(p.getAppFlow()) && p.getAppFlow().equals(Flow.RESOLVE_REFERAL)) {
				UserProfile user = (UserProfile) p.getLoggedInUser();

				if (p.getIsQuote()) {
					if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getAssignedTo()))) {
						appFlow = Flow.EDIT_QUO;
					} else if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getCreatedBy()))
							&& p.getStatus().equals(
									Integer.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_POLICY_ACCEPT)))) {
						appFlow = Flow.EDIT_QUO;
					} else {
						appFlow = Flow.VIEW_QUO;
					}
				} else {
					appFlow = Flow.VIEW_POL;
					if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getAssignedTo()))
							&& p.getStatus()
									.equals(Integer.valueOf(Utils.getSingleValueAppConfig("POLICY_REFERRED")))) {
						appFlow = Flow.AMEND_POL;
					} else if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getAssignedTo()))
							&& p.getStatus().equals(
									Integer.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_POLICY_ACCEPT)))) {
						appFlow = Flow.VIEW_POL;
					} else if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getCreatedBy()))
							&& p.getStatus().equals(
									Integer.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_POLICY_ACCEPT)))) {
						appFlow = Flow.AMEND_POL;
					} else if (!Utils.isEmpty(taskDetails)
							&& user.getRsaUser().getUserId().equals(Integer.valueOf(taskDetails.getCreatedBy()))
							&& p.getStatus()
									.equals(Integer.valueOf(Utils.getSingleValueAppConfig("POLICY_REFERRED")))) {
						appFlow = Flow.VIEW_POL;
					} else {
						appFlow = Flow.VIEW_POL;
					}
				}
			}
		}

		return appFlow;
	}

	/**
	 * Method to update the task vo
	 * 
	 * @param homeInsuranceVO
	 */
	public static void updateTaskVO(HomeInsuranceVO homeInsuranceVO) {
		CommonVO commonVO = homeInsuranceVO.getCommonVO();
		TaskVO taskVO = null;

		if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {
			taskVO = homeInsuranceVO.getReferralVOList().getTaskVO();
		}

		if (!Utils.isEmpty(commonVO) && !Utils.isEmpty(taskVO)) {

			if (Utils.isEmpty(taskVO.getPolLinkingId()) && !Utils.isEmpty(commonVO.getPolicyId())) {
				taskVO.setPolLinkingId(commonVO.getPolicyId());
			}

			if ((Utils.isEmpty(taskVO.getPolEndId())
					|| (!Utils.isEmpty(taskVO.getPolEndId()) && taskVO.getPolEndId() == 0))
					&& !Utils.isEmpty(commonVO.getEndtId())) {
				taskVO.setPolEndId(commonVO.getEndtId());
			}

			if (commonVO.getIsQuote()) {
				if ((Utils.isEmpty(taskVO.getQuoteNo())
						|| (!Utils.isEmpty(taskVO.getQuoteNo()) && taskVO.getQuoteNo() == 0))
						&& !Utils.isEmpty(commonVO.getQuoteNo())) {
					taskVO.setQuoteNo(commonVO.getQuoteNo());
				}
			} else {
				if ((Utils.isEmpty(taskVO.getPolicyNo())
						|| (!Utils.isEmpty(taskVO.getPolicyNo()) && taskVO.getPolicyNo() == 0))
						&& !Utils.isEmpty(commonVO.getPolicyNo())) {
					taskVO.setPolicyNo(commonVO.getPolicyNo());
				}
			}

			if (Utils.isEmpty(taskVO.getTaskName())) {
				taskVO.setTaskName(new StringBuilder().append("Transaction ")
						.append(commonVO.getIsQuote() ? commonVO.getQuoteNo() : commonVO.getPolicyNo())
						.append(" is referred").toString());
			}
		}

	}

	/**
	 * This method will generate the endorsement text for policy
	 * 
	 * @param policyDataVo
	 */
	public static void generateEndtText(PolicyDataVO policyDataVo) {
		CommonVO commonVO = policyDataVo.getCommonVO();
		if (!commonVO.getIsQuote() && ((commonVO.getAppFlow().equals(Flow.AMEND_POL)
				&& !commonVO.getStatus().equals(SvcConstants.POL_STATUS_REFERRED))
				|| (commonVO.getAppFlow().equals(Flow.RESOLVE_REFERAL)
						&& commonVO.getStatus().equals(SvcConstants.POL_STATUS_ACCEPT)))) {

			// Only in case of policy and amend flow call the procedure
			Integer userId = ((UserProfile) (commonVO).getLoggedInUser()).getRsaUser().getUserId();
			// Added equals() instead of == to avoid sonar violation on
			// 25-9-2017
			// if (policyDataVo.getPolicyType()==(
			// Integer.valueOf(SvcConstants.HOME_POL_TYPE))) {
			if (policyDataVo.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE))) {
				policyDataVo.setSectionId((short) 14);
			} else if ((policyDataVo.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)))
					|| (policyDataVo.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))) {
				policyDataVo.setSectionId((short) 15);
			}
			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("commonEndtTxtGen");
			sp.call(commonVO.getPolicyId(), commonVO.getEndtId(), "T_TRN_PREMIUM", userId,
					Integer.valueOf(policyDataVo.getSectionId().toString()), commonVO.getLocCode());
		}
	}

	/**
	 * Method to determine if it is a quote or policy
	 * 
	 * @param commonVO
	 */
	public static boolean isQuote(CommonVO commonVO) {
		boolean isQuote = false;

		if (!Utils.isEmpty(commonVO)) {
			if (commonVO.getIsQuote()) {
				isQuote = true;
			}

			List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.GET_QUOTE_STATUS,
					commonVO.getEndtId(), commonVO.getPolicyId());

			if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
				int documentCode = Integer.valueOf(String.valueOf(resultSet.get(0)[0]));
				int quoteStatus = Integer.valueOf(String.valueOf(resultSet.get(0)[1]));

				if (documentCode == 6 && quoteStatus == SvcConstants.QUOTE_ACTIVE_STATUS) {
					isQuote = true;
				}
			}

		}

		return isQuote;
	}

	/*
	 * hasDataChangedCommon is added for Phase3-Home and Travel This method is
	 * used to check the newdata with existingData.
	 */
	public static <T> boolean hasDataChangedCheck(T newData, T existingData, String tMasInsuredCommonCheck) {
		System.out.println("NewData_5" + newData);
		System.out.println("ExistingData:" + existingData);
		try {

			if (newData instanceof TMasInsured) {
				return SvcUtils.isRecordModifiedCommon(newData, existingData, tMasInsuredCommonCheck);
			}

		} catch (IllegalAccessException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, tried to access private attribut_4");
		} catch (InvocationTargetException e) {
			throw new BusinessException("cmn.compareError", null, "Error in compar_4");
		} catch (NoSuchMethodException e) {
			throw new BusinessException("cmn.compareError", null,
					"Error in compare, one of the field is not present in poj_4");
		}

		return false;
	}

	public static String getHighestRole(String lob, String[] roles) {
		String higestRole = null;
		Integer prevRank = 999;
		Integer actualrank = null;
		Map<Integer, String> rankRoleMap = new HashMap<Integer, String>();

		roles = getLobRoles(lob, roles);

		for (String role : roles) {
			String rank = Utils.getSingleValueAppConfig(role);
			if (!Utils.isEmpty(rank)) {
				Integer currentRank = Integer.valueOf(rank);
				rankRoleMap.put(currentRank, role);
				if (currentRank < prevRank) {
					prevRank = currentRank;
					actualrank = currentRank;
				}

			}
		}
		higestRole = rankRoleMap.get(actualrank);
		return higestRole;
	}

	public static String[] getLobRoles(String lob, String[] roles) {
		List<String> lobRoles = new ArrayList<String>();
		String[] lobConfigRoles = Utils.getMultiValueAppConfig("LOB_" + lob);
		if (Utils.isEmpty(lobConfigRoles))
			return roles;

		for (String role : roles) {
			for (String configRole : lobConfigRoles) {
				if (configRole.equalsIgnoreCase(role)) {
					lobRoles.add(role);
				}
			}

		}

		return lobRoles.toArray(new String[lobRoles.size()]);
	}

	/**
	 * Method to set the mailVo
	 * 
	 * @param policyDataVO
	 * @param flow
	 * @return
	 */
	public static MailVO populateMailVO(PolicyDataVO policyDataVO, String flow) {
		CommonVO commonVO = policyDataVO.getCommonVO();
		MailVO mailVO = new MailVO();

		// Changes-CR#-157083 -From Email ID-Referral Email in WC
		UserProfile userProfile = (UserProfile) commonVO.getLoggedInUser();
		String fromAddress = userProfile.getRsaUser().getEmail().toString();
		switch (ReferralMailTriggerFlow.valueOf(flow)) {
		case REFERRAL_MAIL_TRIGGER:
			setToAddressReferralMail(policyDataVO, mailVO);
			mailVO.setFromAddress(fromAddress);
			mailVO.setReplyToEmailID(fromAddress);
			setMailContentReferralMail(policyDataVO, mailVO);
			break;
		// Changes-CR#-157083 -From Email ID-Referral Email in WC

		default:
			break;
		}

		mailVO.setSubjectText(Utils.getSingleValueAppConfig(flow + "_" + commonVO.getLob().toString() + "_SUBJECT"));

		mailVO.setMailType(Utils.getSingleValueAppConfig(flow + "_" + commonVO.getLob().toString() + "_MAIL_TYPE"));

		return mailVO;
	}

	/**
	 * Method to set the mail content.
	 * 
	 * @param policyDataVO
	 * @param mailVO
	 */
	private static void setMailContentReferralMail(PolicyDataVO policyDataVO, MailVO mailVO) {
		CommonVO commonVO = policyDataVO.getCommonVO();

		if (!Utils.isEmpty(commonVO)) {
			String mailContent = null;

			if (commonVO.getIsQuote()) {
				mailContent = Utils.getSingleValueAppConfig(
						"REFERRAL_MAIL_TRIGGER" + "_" + commonVO.getLob().toString() + "_QUOTE_CONTENT");
				mailContent = mailContent.replace("%QUOTE_NO%", String.valueOf(commonVO.getQuoteNo()));
			} else {
				mailContent = Utils.getSingleValueAppConfig(
						"REFERRAL_MAIL_TRIGGER" + "_" + commonVO.getLob().toString() + "_POLICY_CONTENT");
				mailContent = mailContent.replace("%QUOTE_NO%", String.valueOf(commonVO.getPolicyNo()));
			}

			mailContent = mailContent.replace("%REFERRAL_DATE%", String.valueOf(dateFormatter(new Date())));

			List<Object> schemeList = DAOUtils.getSqlResultSingleColumnPASFor(QueryConstants.FETCH_SCHEME_NAME,
					policyDataVO.getScheme().getSchemeCode());

			if (!Utils.isEmpty(schemeList) && schemeList.size() > 0) {
				mailContent = mailContent.replace("%SCHEME_NAME%", schemeList.get(0).toString());
			}

			ReferralListVO referralListVO = policyDataVO.getReferralVOList();

			if (!Utils.isEmpty(referralListVO)) {
				TaskVO taskVO = referralListVO.getTaskVO();

				if (!Utils.isEmpty(taskVO)) {

					mailContent = mailContent.replace("%REFERRAL_REASON%", taskVO.getDesc());
				}
			}

			mailVO.setMailContent(new StringBuilder(mailContent));
		}
	}

	/**
	 * Method to set the to address of email.
	 * 
	 * @param policyDataVO
	 * @param mailVO
	 */
	private static void setToAddressReferralMail(PolicyDataVO policyDataVO, MailVO mailVO) {
		ReferralListVO referralListVO = policyDataVO.getReferralVOList();

		if (!Utils.isEmpty(referralListVO)) {
			TaskVO taskVO = referralListVO.getTaskVO();

			List<Object> emailAddressList = DAOUtils
					.getSqlResultSingleColumnPASFor(QueryConstants.FETCH_UNDERWRITER_EMAIL, taskVO.getAssignedTo());

			if (!Utils.isEmpty(emailAddressList) && emailAddressList.size() > 0) {
				mailVO.setToAddress(emailAddressList.get(0).toString());
			}
		}

	}

	/**
	 * Method to format the date
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatter(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if (!Utils.isEmpty(date)) {
			return dateFormat.format(date);
		} else {
			return "";
		}

	}

	/**
	 * @param homeInsuranceVO
	 * @return
	 */
	public static double getTotalSIForHome(HomeInsuranceVO homeInsuranceVO) {
		return ((!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
				&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured())
				&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured()))
						? homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured()
						: 0.0)
				+ getTotalCoverSI(homeInsuranceVO);
	}

	public static double getTotalCoverSI(HomeInsuranceVO homeInsuranceVO) {
		Double totalCoverSI = 0.0;
		for (CoverDetailsVO detailsVO : homeInsuranceVO.getCovers()) {
			if (detailsVO.getCoverCodes().getCovCode() == SvcConstants.CONTENT_MAIN_RISK_CATEGORY) {
				totalCoverSI += (!Utils.isEmpty(detailsVO.getSumInsured())
						&& !Utils.isEmpty(detailsVO.getSumInsured().getSumInsured()))
								? detailsVO.getSumInsured().getSumInsured()
								: 0.0;
			}
		}
		return totalCoverSI;
	}

	public static <T> List<T> copyAsMutableList(final T[] v) {
		List<T> list = new ArrayList<T>(Arrays.asList(v));
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public static boolean isCombinedTariff(String tariff) {
		boolean isCombinedTariff = false;

		if (!Utils.isEmpty(tariff)) {
			for (int i = 0; i < COMBINED_TARIFF_CODE.length; i++) {
				if (tariff.equals(COMBINED_TARIFF_CODE[i])) {
					isCombinedTariff = true;
					break;
				}
			}
		}

		return isCombinedTariff;
	}

	/**
	 * This methods calculates the age of the passenger from dob to pol
	 * effective date
	 * 
	 * @param dateOfBirth
	 * @param effectiveDate
	 * @return
	 */

	public static BigDecimal getAgeForHome(Date dateOfBirth, Date effectiveDate) {
		BigDecimal bigDecimalAge = null;
		if (dateOfBirth != null && effectiveDate != null) {

			DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
			String sql = "SELECT MONTHS_BETWEEN('" + dateFormat.format(effectiveDate) + "','"
					+ dateFormat.format(dateOfBirth) + "')/12 from dual";
			List<Object> result = DAOUtils.getSqlResultForPas(sql);
			bigDecimalAge = (BigDecimal) result.get(0);

		}

		return bigDecimalAge;
	}

	/**
	 * @param policyDataVO
	 * @return TableData TTrnPremiumVOHolder
	 */
	public static List<TableData> getPremiumSplCovers(PolicyDataVO policyDataVo) {

		List<TableData> premSplCoverRecs = new ArrayList<TableData>();
		TableData<TTrnPremiumVOHolder> premSplCovTableData = null;

		CommonOpDAO commonOpDAO;

		if (!Utils.isEmpty(policyDataVo.getCommonVO()) && policyDataVo.getCommonVO().getIsQuote()) {
			commonOpDAO = (CommonOpDAO) Utils.getBean("commonOpDAO");
		} else {
			commonOpDAO = (CommonOpDAO) Utils.getBean("commonOpDAO_POL");
		}

		TTrnPremiumVOHolder basePrmHolder = commonOpDAO.getPremiumSpecialCoverRecs(policyDataVo.getCommonVO());

		validateUserAuthForDiscLoad(policyDataVo);

		for (String coverCode : getSpecialCovers()) {

			TTrnPremiumVOHolder coverToSave = CopyUtils.copySerializableObject(basePrmHolder);

			// If coverCode - 51 ==> discount, if coverCode - 20 ==> load, if
			// coverCode - 100 ==> policyFees, if coverCode - 101 ==> govtTax
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_DISCOUNT)
					&& (policyDataVo.getPremiumVO().getDiscOrLoadPerc() < 0)) {
				coverToSave.setPrmLoadDisc(new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc().toString()));
			}
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_LOADING)
					&& (policyDataVo.getPremiumVO().getDiscOrLoadPerc() > 0)) {
				coverToSave.setPrmLoadDisc(new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc().toString()));
			}
			if (coverCode.equals(SvcConstants.SC_PRM_COVER_PROMO_DISC)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getPromoDiscPerc())) {
				coverToSave.setPrmLoadDisc(new BigDecimal(policyDataVo.getPremiumVO().getPromoDiscPerc()));
			}
			// Set the Policy Fees Premium
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_POLICY_FEE)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getPolicyFees())) {
				coverToSave.setPrmPremium(new BigDecimal(policyDataVo.getPremiumVO().getPolicyFees()));
			}
			// Set the Govt Tax Premium
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_GOVT_TAX)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getGovtTax())) {
				coverToSave.setPrmPremium(new BigDecimal(policyDataVo.getPremiumVO().getGovtTax()));
			}

			// Set the vat Tax Premium 142244
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_VAT_TAX)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getVatTax())) {
				coverToSave.setPrmPremium(new BigDecimal(policyDataVo.getPremiumVO().getVatTax()));
			}
			// Set the vat Tax Percentage 142244
			if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_VAT_TAX)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getVatTaxPerc())) {
				coverToSave.setPrmRate(new BigDecimal(policyDataVo.getPremiumVO().getVatTaxPerc()));
			}
			// Set the Minimum Premium
			if (coverCode.equals(SvcConstants.SPECIAL_COVER_MIN_PRM)
					&& !Utils.isEmpty(policyDataVo.getPremiumVO().getMinPremiumApplied())) {
				coverToSave.setPrmPremium(policyDataVo.getPremiumVO().getMinPremiumApplied());
			}
			coverToSave.setPrmCovCode(Short.valueOf(coverCode));
			premSplCovTableData = new TableData<TTrnPremiumVOHolder>();
			premSplCovTableData.setTableData(coverToSave);
			premSplCoverRecs.add(premSplCovTableData);
		}
		return premSplCoverRecs;
	}

	private static void validateUserAuthForDiscLoad(PolicyDataVO policyDataVo) {
		/*
		 * get the previous record for loading
		 */
		boolean validationPassed = true;
		Long endtId = policyDataVo.getCommonVO().getEndtId();
		/*
		 * if the endt is 0 then the query to fetch the previous erc will not
		 * work. Hence for endt 0 we are setting temp endt id to 1 to get the
		 * curr rec
		 */
		if (endtId.compareTo(0L) == 0) {
			endtId = 1L;
		}
		List<Object[]> resultSet = DAOUtils.getSqlResult(
				policyDataVo.getCommonVO().getIsQuote() ? QueryConstants.GET_COVER_PRM_RATE_QUOTE
						: QueryConstants.GET_COVER_PRM_RATE_POLICY,
				policyDataVo.getCommonVO().getPolicyId(), endtId, SvcConstants.SC_PRM_COVER_LOADING,
				SvcConstants.SC_PRM_COVER_DISCOUNT);

		if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
			BigDecimal discLoadToCompare = null;
			for (Object[] result : resultSet) {
				Integer modifiedUser = !Utils.isEmpty(result[2]) ? ((BigDecimal) result[2]).intValue()
						: ((BigDecimal) result[3]).intValue();
				Integer currentUser = SvcUtils.getUserId(policyDataVo.getCommonVO());

				List<String> modifiedUserRoles = (List<String>) (List<?>) DAOUtils
						.getSqlResultSingleColumnPASFor(QueryConstants.GET_USER_ROLE, modifiedUser);
				List<String> currentUserRoles = (List<String>) (List<?>) DAOUtils
						.getSqlResultSingleColumnPASFor(QueryConstants.GET_USER_ROLE, currentUser);

				Integer modifiedUserRank = Integer.valueOf(Utils.getSingleValueAppConfig(SvcUtils.getHighestRole(
						policyDataVo.getCommonVO().getLob().toString(), CopyUtils.toArray(modifiedUserRoles))));
				Integer currentUserRank = Integer.valueOf(Utils.getSingleValueAppConfig(SvcUtils.getHighestRole(
						policyDataVo.getCommonVO().getLob().toString(), CopyUtils.toArray(currentUserRoles))));
				BigDecimal discLoad = (BigDecimal) result[1];
				if (!Utils.isEmpty(discLoadToCompare) && discLoad.compareTo(discLoadToCompare) == 0) {
					discLoadToCompare = (BigDecimal) result[1];
					BigDecimal currentDiscLoad = new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc());
					if (discLoad.compareTo(currentDiscLoad) > 0 && (modifiedUserRank.compareTo(currentUserRank) < 0)) {
						validationPassed = false;
						break;
					}
				} else if (!Utils.isEmpty(discLoadToCompare) && discLoad.compareTo(discLoadToCompare) != 0) {
					break;
				}

				if (Utils.isEmpty(discLoadToCompare) && (modifiedUserRank.compareTo(currentUserRank) < 0)) {
					discLoadToCompare = (BigDecimal) result[1];
					BigDecimal currentDiscLoad = new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc());
					// Added new local variables with the scaled values as
					// comparison was failing because of mismatch in scale.
					BigDecimal currDiscLoadForComparison = currentDiscLoad.setScale(3, RoundingMode.UP);
					BigDecimal discountLoadForComparison = discLoad.setScale(3, RoundingMode.UP);
					if (discountLoadForComparison.compareTo(currDiscLoadForComparison) > 0) {
						validationPassed = false;
						break;

					}
				}
				discLoadToCompare = (BigDecimal) result[1];
			}
		}

		if (!validationPassed) {
			throw new BusinessException("err.discload.auth", null, "Disc/Loading was reduced by lower auth user");
		}
	}

	private static List<String> getSpecialCovers() {
		List<String> SPECIAL_COVER_CODES = null;
		// SPECIAL_COVER_CODES is used by SBS, hence the configaration not
		// chnaged
		SPECIAL_COVER_CODES = CopyUtils.asList(Utils.getMultiValueAppConfig("SPECIAL_COVER_CODES", ","));
		/*
		 * This configuration is used for ph3 as SBS currrenty does not have
		 * promo discount
		 */
		SPECIAL_COVER_CODES.add(SvcConstants.SC_PRM_COVER_PROMO_DISC);
		SPECIAL_COVER_CODES.add(SvcConstants.SPECIAL_COVER_MIN_PRM);
		return SPECIAL_COVER_CODES;
	}

	/**
	 * Method to compare the saved Unnamed employee list with to be saved
	 * Unnamed employee list and update the to be deleted flag.
	 * 
	 * @param savedUnNamedEmp
	 * @param tobeSavedUnNamedEmp
	 * @return
	 */
	public static List<TableData> updateToBeDeletedUnNamedEmp(List<TableData> savedUnNamedEmp,
			@SuppressWarnings("rawtypes") List<TableData> tobeSavedUnNamedEmp) {

		List<TableData> unNamedEmpTableDataList = new ArrayList<TableData>(0);
		TableData<TTrnWctplUnnamedPersonQuoHolder> unNamedEmpTableData = null;
		int index = 0;
		for (TableData<TTrnWctplUnnamedPersonQuoHolder> tData : savedUnNamedEmp) {
			unNamedEmpTableData = new TableData<TTrnWctplUnnamedPersonQuoHolder>();
			TTrnWctplUnnamedPersonQuoHolder wctplUnnamedPersonQuoHolder;
			boolean unNamedEmpPresent = tobeSavedUnNamedEmp
					.contains((TTrnWctplUnnamedPersonQuoHolder) tData.getTableData());
			if (!unNamedEmpPresent) {
				tData.setToBeDeleted(true);
				unNamedEmpTableDataList.add(tData);
			} else {
				index = tobeSavedUnNamedEmp.indexOf(tData.getTableData());
				wctplUnnamedPersonQuoHolder = (TTrnWctplUnnamedPersonQuoHolder) tobeSavedUnNamedEmp.get(index)
						.getTableData();
				wctplUnnamedPersonQuoHolder.setWupValidityStartDate(
						((TTrnWctplUnnamedPersonQuoHolder) tData.getTableData()).getWupValidityStartDate());
				unNamedEmpTableData.setTableData(wctplUnnamedPersonQuoHolder);
				unNamedEmpTableDataList.add(unNamedEmpTableData);
			}
		}

		for (TableData<?> tableData : tobeSavedUnNamedEmp) {
			// unNamedEmpTableData = new
			// TableData<TTrnWctplUnnamedPersonQuoHolder>();
			boolean unNamedEmpPresent = savedUnNamedEmp.contains(tableData);
			if (!unNamedEmpPresent) {
				unNamedEmpTableDataList.add(tableData);
			}
		}
		return unNamedEmpTableDataList;
	}

	/**
	 * Method to compare the saved Named employee list with to be saved Named
	 * employee list and update the to be deleted flag.
	 * 
	 * @param savedNamedEmp
	 * @param tobeSavedNamedEmp
	 * @return
	 */
	public static List<TableData> updateToBeDeletedNamedEmp(List<TableData> savedNamedEmp,
			@SuppressWarnings("rawtypes") List<TableData> tobeSavedNamedEmp) {

		List<TableData> namedEmpTableDataList = new ArrayList<TableData>(0);
		TableData<TTrnWctplPersonQuoHolder> namedEmpTableData = null;
		int index = 0;
		for (TableData<TTrnWctplPersonQuoHolder> tData : savedNamedEmp) {
			namedEmpTableData = new TableData<TTrnWctplPersonQuoHolder>();
			TTrnWctplPersonQuoHolder wctplPersonQuoHolder;
			boolean namedEmpPresent = tobeSavedNamedEmp.contains((TTrnWctplPersonQuoHolder) tData.getTableData());
			if (!namedEmpPresent) {
				tData.setToBeDeleted(true);
				namedEmpTableDataList.add(tData);
			} else {
				index = tobeSavedNamedEmp.indexOf(tData.getTableData());
				wctplPersonQuoHolder = (TTrnWctplPersonQuoHolder) tobeSavedNamedEmp.get(index).getTableData();
				wctplPersonQuoHolder.setWprValidityStartDate(
						((TTrnWctplPersonQuoHolder) tData.getTableData()).getWprValidityStartDate());
				namedEmpTableData.setTableData(wctplPersonQuoHolder);
				namedEmpTableDataList.add(namedEmpTableData);
			}
		}

		for (TableData<?> tableData : tobeSavedNamedEmp) {
			// namedEmpTableData = new TableData<TTrnWctplPersonQuoHolder>();
			boolean namedEmpPresent = savedNamedEmp.contains(tableData);
			if (!namedEmpPresent) {
				namedEmpTableDataList.add(tableData);
			}
		}
		return namedEmpTableDataList;
	}

	/*
	 * This method copies the trade license during generating of renewal quote
	 */
	public static void copyTradeLicense(File srcDir, File destDir) throws IOException {

		// creates the destination directory if it does not exist
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		// throws exception if the source does not exist
		/*
		 * if (!srcDir.exists()) { throw new
		 * IllegalArgumentException("source Directory does not exist"); }
		 */

		// throws exception if the arguments are not directories
		if (srcDir.exists()) {
			if (srcDir.isFile() || destDir.isFile()) {
				throw new IllegalArgumentException("Either sourceDir or destDir is not a directory");
			}
			copyDirectory(srcDir, destDir);
		}

	}

	public static void copyDirectory(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			logger.debug("Directory copied from " + src + "  to " + dest);
			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyDirectory(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			try {
				byte[] buffer = new byte[1024];
				int length;
				// copy the file content in bytes
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
				logger.debug("File copied from " + src + " to " + dest);
			}

			catch (Exception e) {

				e.getMessage();
			}

			finally { // SONARFIX - 20-apr-2018 -- added finally block

				try {

					if (out != null) {
						out.close();
					}
				} catch (Exception e) {
					e.getMessage();
				}
				try {

					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	/**
	 * Method to rename any file present in the given directory. It checks for
	 * all the files with given name and renames it.
	 * 
	 * @param srcDir
	 * @param existingName
	 * @param newName
	 * @return boolean
	 */
	public static Boolean renameFile(File srcDir, String existingName, String newName) {

		Boolean result = false;
		if (srcDir.isDirectory()) {
			String files[] = srcDir.list();

			for (String file : files) {

				String fileName = file.substring(0, (file.lastIndexOf(".")));
				String fileExtn = file.substring(file.lastIndexOf("."));
				if (existingName.equals(fileName)) {
					File srcFile = new File(srcDir, file);
					File destFile = new File(srcDir, newName + fileExtn);
					result = srcFile.renameTo(destFile);
					if (!result)
						return false;
				}
			}
		}
		return result;
	}

	public static void saveFileToDisk(String destDir, String fileName, MultipartFile file) {

		byte[] bytes;
		File serverFile = new File(destDir + "/" + fileName);
		try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));) {

			bytes = file.getBytes();
			File dir = new File(destDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			stream.write(bytes);
			stream.close();
			logger.debug("File uploaded to " + destDir + "/" + fileName);

		} catch (Exception e) {
			throw new BusinessException("rsadirect.fileupload.failed", null,
					"Failed to upload file - " + destDir + "/" + fileName);
		}

	}

	/*
	 * This Method is used to get the total SI per location across all sections.
	 */
	public static Double getTotalSumInsured(PolicyVO policyVO, Long basicRiskId, boolean isPerLoc) {

		double totalSIPerLoc = 0.0;
		double totalSIPerPolicy = 0.0;
		LocationVO location = null;
		RiskGroupDetails locationDetail = null;

		if (!Utils.isEmpty(policyVO)) {
			List<SectionVO> sectionVOs = policyVO.getRiskDetails();

			for (SectionVO section : sectionVOs) {

				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
				if (!Utils.isEmpty(riskGroupDetails)) {
					for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails
							.entrySet()) {
						if (!Utils.isEmpty(riskGroupDetailsEntry)) {
							location = (LocationVO) riskGroupDetailsEntry.getKey();
							locationDetail = riskGroupDetailsEntry.getValue();
							if (!Utils.isEmpty(location) && !Utils.isEmpty(locationDetail)) {

								if (!Utils.isEmpty(basicRiskId) && basicRiskId.equals(locationDetail.getBasicRiskId())
										&& isPerLoc) {
									totalSIPerLoc += locationDetail.getSumInsured();
								} else if (Utils.isEmpty(basicRiskId) && isPerLoc) {
									totalSIPerLoc += locationDetail.getSumInsured();
								} else if (!isPerLoc) {
									totalSIPerPolicy += locationDetail.getSumInsured();
								}

							}
						}
					}

				}
			}
		}

		if (isPerLoc) {
			return totalSIPerLoc;
		} else {
			return totalSIPerPolicy;
		}

	}

	/**
	 * Method to get the broker credit limit percentage
	 * 
	 * @param premiumAmt
	 * @param brokerName
	 * @return
	 */
	public static double getBrokerCreditLimitPercentage(double premiumAmt, Integer brokerName) {

		double currentOutstanding = 0;

		double returnValue = 0;

		List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_CURRENT_OUTSTANDING_BROKER,
				brokerName);

		if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0 && !Utils.isEmpty(valueHolder.get(0))) {
			currentOutstanding = ((BigDecimal) valueHolder.get(0)).doubleValue();
		}

		double totalOutstanding = currentOutstanding + premiumAmt;

		double brokerCreditLimit = 0;

		List<Object> creditLimit = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_BROKER_CREDIT_LIMIT,
				brokerName);

		if (!Utils.isEmpty(creditLimit) && creditLimit.size() > 0 && !Utils.isEmpty(creditLimit.get(0))) {
			brokerCreditLimit = ((BigDecimal) creditLimit.get(0)).doubleValue();
		}

		Double brokerCredit = totalOutstanding / brokerCreditLimit * 100;

		BigDecimal credit = new BigDecimal(brokerCredit);

		int scale = credit.scale();

		if (scale > 0) {
			returnValue = Math.round(brokerCredit) + 1;
		} else {
			returnValue = Math.round(brokerCredit);
		}

		return returnValue;
	}

	/**
	 * Method to check whether the licensed by is of role CREDIT_MANAGER
	 * 
	 * @param licensedBy
	 * @return
	 */
	public static boolean checkCreditLimitRule(Integer licensedBy) {
		boolean filterCreditLimit = false;

		if (!Utils.isEmpty(licensedBy)) {
			List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_ROLE_LICENSED_BY,
					licensedBy);

			if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0) {
				filterCreditLimit = true;
			}
		}

		return filterCreditLimit;
	}

	/**
	 * 
	 * @param brokerId
	 * @return
	 */
	public static double getBrokerCredit(Integer brokerId) {
		List<Object> creditLimit = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_BROKER_CREDIT_LIMIT,
				brokerId);
		double brokerCreditLimit = 0;
		if (!Utils.isEmpty(creditLimit) && creditLimit.size() > 0 && !Utils.isEmpty(creditLimit.get(0))) {
			brokerCreditLimit = ((BigDecimal) creditLimit.get(0)).doubleValue();
		}

		return brokerCreditLimit;
	}

	/**
	 * 
	 * @param premiumAmt
	 * @param brokerId
	 * @return
	 */
	public static double getCurrentOs(double premiumAmt, Integer brokerId) {

		double currentOutstanding = 0;

		List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_CURRENT_OUTSTANDING_BROKER,
				brokerId);

		if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0 && !Utils.isEmpty(valueHolder.get(0))) {
			currentOutstanding = ((BigDecimal) valueHolder.get(0)).doubleValue();
		}

		double totalOutstanding = currentOutstanding + premiumAmt;
		return totalOutstanding;
	}

	public static String[] getCreditLimitEmail(String mailType) {
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.CREDIT_LIMIT_EMAIL_ID, mailType);

		String[] emailList = null;

		if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
			emailList = new String[resultSet.size()];

			for (int i = 0; i < resultSet.size(); i++) {
				emailList[i] = (String) resultSet.get(i);
			}
		}
		return emailList;
	}

	/**
	 * Service method to get the email id based on quotenumber and date of birth
	 * 
	 * @param quoteNumber
	 * @param dateOfBirth
	 * @return
	 */
	public static String getEmailId(String quoteNumber, Date dateOfBirth) {

		String emailId = "";
		List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_EMAIL_ID, quoteNumber,
				dateOfBirth);

		if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0 && !Utils.isEmpty(valueHolder.get(0))) {
			emailId = String.valueOf(valueHolder.get(0));
		}
		return emailId;
	}

	public static BigDecimal premiumRoundOff(BigDecimal calculatedPremium) {

		NumberFormat formatter = new DecimalFormat(SvcConstants.DEFAULT_CURRENCY_FORMAT);
		return new BigDecimal(formatter.format(calculatedPremium));

	}

	public static boolean isRenewalPolicyAmend(Long policyId, Long policyNo) {
		boolean result = false;

		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_BUSINESS_TYPE, policyId,
				policyNo);

		if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {

			BigDecimal businessType = null;

			businessType = (BigDecimal) resultSet.get(0);

			if (businessType != null) {
				int polBusinessType = businessType.intValue();

				if (polBusinessType == SvcConstants.BUS_TYPE_RENEWAL) {
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * This method will generate the endorsement text for policy
	 * 
	 * @param policyDataVo
	 */
	public static void generateEndtTextMonoline(PolicyDataVO policyDataVo) {

		CommonVO commonVO = policyDataVo.getCommonVO();
		if (!commonVO.getIsQuote() && ((commonVO.getAppFlow().equals(Flow.AMEND_POL)
				&& !commonVO.getStatus().equals(SvcConstants.POL_STATUS_REFERRED))
				|| (commonVO.getAppFlow().equals(Flow.RESOLVE_REFERAL)
						&& commonVO.getStatus().equals(SvcConstants.POL_STATUS_ACCEPT)))) {

			// Only in case of policy and amend flow call the procedure
			Integer userId = ((UserProfile) (commonVO).getLoggedInUser()).getRsaUser().getUserId();

			policyDataVo.setSectionId(
					Short.valueOf(Utils.getSingleValueAppConfig(policyDataVo.getCommonVO().getLob() + "_SECTION_ID")));

			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("commonEndtTxtGenMonoline");
			sp.call(commonVO.getPolicyId(), commonVO.getEndtId(), "T_TRN_PREMIUM", userId,
					Integer.valueOf(policyDataVo.getSectionId().toString()), commonVO.getLocCode());
		}
	}

	public static void populateEndorsmentVO(PolicyDataVO policyDataVO) {

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(
				EndorsmentVO.class);
		/* Update endt no and id before loading premium page */

		EndorsmentVO endorsmentVO = null;
		Long endtId = policyDataVO.getCommonVO().getEndtId();
		Long polId = policyDataVO.getCommonVO().getPolicyId();

		if (!Utils.isEmpty(endtId)) {

			if (Flow.VIEW_POL.equals(policyDataVO.getCommonVO().getAppFlow())
					|| Flow.RESOLVE_REFERAL.equals(policyDataVO.getCommonVO().getAppFlow())) {
				BaseVO baseVO1 = TaskExecutor.executeTasks("CAPTURE_AMEND_POLICY_ENDT_TEXT", policyDataVO);
				policyDataVO = (WorkmenCompVO) baseVO1;
				if (!Utils.isEmpty(policyDataVO.getEndorsmentVO())) {
					endorsmentVO = policyDataVO.getEndorsmentVO().get(0);
				} else {
					endorsmentVO = new EndorsmentVO();
					endorsmentVO.setEndNo(policyDataVO.getCommonVO().getEndtNo());
					endorsmentVO.setEndtId(policyDataVO.getCommonVO().getEndtId());
					endorsmentVO.setPolicyId(policyDataVO.getCommonVO().getPolicyId());
					endorsmentVO.setSlNo(1);
				}
			} else {
				endorsmentVO = new EndorsmentVO();
				endorsmentVO.setEndNo(policyDataVO.getCommonVO().getEndtNo());
				endorsmentVO.setEndtId(policyDataVO.getCommonVO().getEndtId());
				endorsmentVO.setPolicyId(policyDataVO.getCommonVO().getPolicyId());
				endorsmentVO.setSlNo(1);
			}

			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the
			 * need is to display the refund amount before actually cancellation
			 * of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;

			// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob
			// removed, the variable is passed as parameter in the
			// getPremiumForProrate function : Start
			// PremiumHelper.lob = SvcConstants.COMMON_FLOW;
			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getPremiumForProrate(polId, endtId,
					policyDataVO.getCommonVO().getLob());
			// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob
			// removed, the variable is passed as parameter in the
			// getPremiumForProrate function : End

			newPremiumAmt = premiumHolder.getData().get(SvcConstants.NEW_ANNUALIZED_PREMIUM);

			Double currentPremiumAmt = premiumHolder.getData().get(SvcConstants.OLD_ANNUALIZED_PREMIUM);

			newPremiumVO.setPremiumAmt(newPremiumAmt); // New premium amount.
			oldPremiumVO.setPremiumAmt(currentPremiumAmt); // Old premium
															// amount.

			endorsmentVO.setOldPremiumVO(oldPremiumVO);
			endorsmentVO.setPremiumVO(newPremiumVO);
			endorsmentVO.setEndEffDate(policyDataVO.getEndEffectiveDate());
			endorsmentVO.setEndDate(policyDataVO.getScheme().getExpiryDate());
			endorsmentVO.setEndNo(policyDataVO.getCommonVO().getEndtNo());

			endorsements.add(endorsmentVO);

			policyDataVO.setEndorsmentVO(endorsements);
		}
	}

	public static boolean isAlsamScheme(String tariff) {
		String[] alsalamTariffs = Utils.getMultiValueAppConfig("ALSALAM_TECOM_SCHEMES");
		boolean isAlsalam = false;
		if (!Utils.isEmpty(alsalamTariffs)) {
			for (String tar : alsalamTariffs) {
				if (tariff.equals(tar)) {
					isAlsalam = true;
				}
			}
		}
		return isAlsalam;
	}

	public static List<TTrnPolicyQuo> getQUote(Long quoteNo, Long endtId) {
		IPublicLiabilityDAO plDAO = (IPublicLiabilityDAO) Utils.getBean("publicLiabilityDAO");
		List<TTrnPolicyQuo> quoList = plDAO.getQuote(quoteNo, endtId);
		return quoList;
	}

	public static Date getPreparedDate(Long quoteNo) {
		IPublicLiabilityDAO plDAO = (IPublicLiabilityDAO) Utils.getBean("publicLiabilityDAO");
		return plDAO.getPreparedDate(quoteNo);

	}

	// 142244

	public static BigDecimal getVatRateAmt(BigDecimal vatTaxRate, BigDecimal clPrm) {
		return !Utils.isEmpty(vatTaxRate) ? ((clPrm.multiply(vatTaxRate)).divide(BigDecimal.valueOf(100.00)))
				: BigDecimal.ZERO;
	}

	/*
	 * 
	 * 
	 * Populate Vat Inception Date from Database only once
	 */
	public static String populateVatDt() {
		logger.info("Entering populateVatDt() method");
		System.out.println("vatStartDate inside count before : " + vatStartDate);
		vatStartDate = null;
		System.out.println("vatStartDate inside count after : " + vatStartDate);
		// if (Utils.isEmpty(vatStartDate)) {

		List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_VAT_START_DATE);

		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {

			String vatIncDate = null;

			vatIncDate = (String) resultSetVat.get(0);

			if (vatIncDate != null) {
				System.out.println("vatIncDate1 : " + vatIncDate);
				vatIncDate = formatDt(vatIncDate, com.Constant.CONST_DD_MMM_YYYY, "MM/dd/yyyy");
				System.out.println("vatIncDate2 : " + vatIncDate);
				vatStartDate = vatIncDate;
				System.out.println("vatStartDate inside vatStartDate condition: " + vatStartDate);
			}
		}
		// }

		logger.info("Exiting populateVatDt() method");
		return vatStartDate;
	}

	/*
	 * 
	 * 
	 * Populate Cover removal date from Database only once
	 */
	// Home Revamp requirement 1.1
	public static String populateCovRemovalDt() {
		logger.info("Entering populateCovRemovalDt() method");

		covStartDate = null;

		List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_COV_START_DATE);

		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {

			String covIncDate = null;

			covIncDate = (String) resultSetVat.get(0);

			if (covIncDate != null) {

				covIncDate = formatDt(covIncDate, com.Constant.CONST_DD_MMM_YYYY, "MM/dd/yyyy");

				covStartDate = covIncDate;
				System.out.println("covStartDate inside vatStartDate condition: " + covStartDate);
			}
		}

		return covStartDate;
	}

	// Home Revamp requirement 1.1
	public static String populatePolPreparedDt(Long quotationNo) {
		polPreparedDt = null;
		List resultSetVat = DAOUtils.getSqlResultForPas(QueryConstants.FETCH_POL_PREPARED_DATE, quotationNo);
		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {
			Date covIncDate = null;
			covIncDate = (Date) resultSetVat.get(0);
			SimpleDateFormat destDf = new SimpleDateFormat("MM/dd/yyyy");
			// format the date into another format
			polPreparedDt = destDf.format(covIncDate);

		}
		return polPreparedDt;
	}

	// Home Revamp requirement 1.1
	public static String populateSysDt() {
		sysDt = null;
		List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_SYS_DATE);
		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {
			Date covIncDate = null;
			covIncDate = (Date) resultSetVat.get(0);
			SimpleDateFormat destDf = new SimpleDateFormat("MM/dd/yyyy");
			// format the date into another format
			sysDt = destDf.format(covIncDate);
			// format the date into another format

		}
		return sysDt;
	}

	/*
	 * 
	 * Date Format : Converter
	 */
	private static String formatDt(String vatIncDate, String srcFormat, String destFormat) {

		String dateStr = null;

		try {
			dateStr = vatIncDate;

			DateFormat srcDf = new SimpleDateFormat(srcFormat);

			// parse the date string into Date object
			Date date = srcDf.parse(dateStr);

			// 11/01/2017
			DateFormat destDf = new SimpleDateFormat(destFormat);

			// format the date into another format
			dateStr = destDf.format(date);

			System.out.println("Converted date is : " + dateStr);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateStr;
	}

	/**
	 * 142244 - WC Vat Tax Implementation
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, String> calculateSBSVatTaxAmount(PolicyVO policyVO, Double premiumAfterDiscount,
			TTrnPolicyQuo policyQuo, Double olDpremiumforClass) {

		logger.info("HHH - Entering calculateSBSVatTaxAmount() method");

		Map<String, String> vatList = new HashMap<String, String>();

		Double vatTaxAmount = 0.0;
		Double vatTaxPerc = 0.0;
		Double premiumAmt = 0.0;
		Double vatbleAmt = 0.0;
		Double proratevatTaxAmount = 0.0;
		Double proratepremiumAmt = 0.0;
		int diffInDays = 0;
		int policyPeriodDays = 0;
		int endDiffInDays = 0;
		int endPeriodDays = 0;
		Double OldVatTaxAmt = 0.0, OldvatbleAmt = 0.0;
		Double OldPremium = 0.0;

		Date polEffectiveDate = null;
		Date polExpiryDate = null;
		Date vatEffDate = null;
		Date polEndEffectiveDate = null;
		Date polEndExpiryDate = null;

		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();

		Double vatAmt = 0.0;
		Double proVatAmt = 0.0;
		/*
		 * String vatStartDate =
		 * Utils.getSingleValueAppConfig("TRAVEL_VAT_START_DATE"); // 01/01/2018
		 */
		System.out.println("vatStartDate1 :" + SvcUtils.populateVatDt());
		String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
		try {
			// SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
			System.out.println("calculateSBSVatTaxAmount() method : " + vatEffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Date> polDateList = new LinkedList<Date>();
		polDateList.add(vatEffDate); // VAT Effective Date for Policy

		List<Date> endDateList = new LinkedList<Date>();
		endDateList.add(vatEffDate); // VAT Effective Date for endorsement

		Comparator<Date> cmp = new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		};

		if (!Utils.isEmpty(policyVO)) {

			if (!Utils.isEmpty(policyVO.getPremiumVO())) {

				if (policyVO.getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty(policyVO.getEndorsements())) {

					// polEndExpiryDate =
					// workmenCompVO.getEndorsmentVO().get(0).getEndExpiryDate();
					// Getting the previous vatamount
					polEndExpiryDate = policyVO.getScheme().getExpiryDate();
					polEndEffectiveDate = policyVO.getEndEffectiveDate();
					endDateList.add(polEndEffectiveDate);

					if (!Utils.isEmpty(policyVO.getEndorsements())
							&& !Utils.isEmpty(policyVO.getEndorsements().get(0).getOldPremiumVO())
							&& !Utils.isEmpty(policyVO.getEndorsements().get(0).getOldPremiumVO().getPremiumAmt())) {

						HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
						Long previousEndtId = DAOUtils.getPreviousEndtIdSBS(ht, policyVO);
						vatResults = DAOUtils.getVatAmountSBS(policyQuo.getId().getPolicyId(), previousEndtId,
								policyVO);
						OldPremium = olDpremiumforClass;

						OldVatTaxAmt = (Double) vatResults.get(0);
						/*
						 * if (Utils.isEmpty(OldVatTaxAmt)) { polEffectiveDate =
						 * workmenCompVO.getCommonVO().getPolEffectiveDate();
						 * polDateList.add(polEffectiveDate); polExpiryDate =
						 * workmenCompVO.getScheme().getExpiryDate(); diffInDays
						 * = (int) ((polExpiryDate.getTime() -
						 * Collections.max(polDateList, cmp).getTime()) / (1000
						 * * 60 * 60 * 24)) + 1; policyPeriodDays = (int)
						 * ((polExpiryDate.getTime() -
						 * polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24))
						 * + 1; OldVatTaxAmt=premiumAmt *
						 * diffInDays/policyPeriodDays * vatTaxPerc/100; }
						 */
						proratepremiumAmt = premiumAfterDiscount - (OldPremium);
						OldvatbleAmt = (Double) vatResults.get(1);

					}

					endDiffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(endDateList, cmp).getTime())
							/ (1000 * 60 * 60 * 24)) + 1;
					endPeriodDays = (int) ((polEndExpiryDate.getTime() - polEndEffectiveDate.getTime())
							/ (1000 * 60 * 60 * 24)) + 1;

				}
				// for calculation actual Vat amount for storing to DB
				polEffectiveDate = policyVO.getPolEffectiveDate();
				polDateList.add(polEffectiveDate);
				polExpiryDate = policyVO.getScheme().getExpiryDate();

				vatTaxPerc = policyVO.getPolVatRate().doubleValue();
				;

				premiumAmt = premiumAfterDiscount;

				logger.info("PolEffDate:_1" + polEffectiveDate + " PolExpiryDate:_1" + polExpiryDate
						+ " polEndEffectiveDate:_1" + polEndEffectiveDate + "polEndExpiryDat_1" + polEndExpiryDate

						+ " VatTaxPerc:_1" + vatTaxPerc + " PremiumAmt:_1" + premiumAmt);

			}

		}

		logger.info("Max Date out of PolicyEffDate and VatEffDate _1" + Collections.max(endDateList, cmp) + "\n");

		diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime())
				/ (1000 * 60 * 60 * 24)) + 1;
		policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;

		if (policyPeriodDays != 0) {
			vatTaxAmount = premiumAmt * diffInDays / policyPeriodDays * vatTaxPerc / 100;
			vatbleAmt = (Double) (premiumAmt * diffInDays / policyPeriodDays);
			vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));
			if (endPeriodDays != 0) {
				// vatTaxAmount = OldPremium * diffInDays/policyPeriodDays *
				// vatTaxPerc/100;
				/* Ticket 173966 - Vat amount difference - refund scenario */
				if (isSBSBahrainPolicy(policyVO)) {
					OldVatTaxAmt = OldPremium * endDiffInDays / endPeriodDays * vatTaxPerc / 100; // old
																									// vat
																									// amount
					proratevatTaxAmount = proratepremiumAmt * endDiffInDays / endPeriodDays * vatTaxPerc / 100; // new
																												// prorated
																												// vat
																												// amount
					vatTaxAmount = OldVatTaxAmt + proratevatTaxAmount;
				} else {
					proratevatTaxAmount = proratepremiumAmt * endDiffInDays / endPeriodDays * vatTaxPerc / 100;
					vatTaxAmount = OldVatTaxAmt + proratevatTaxAmount;
				}
				logger.info("HHH Refund Endorsement Flow" + "proratevatTaxAmount: " + proratevatTaxAmount);
				logger.info("HHH Refund Endorsement Flow" + "OldVatTaxAmt: " + OldVatTaxAmt);

				if (proratevatTaxAmount < 0 && (OldVatTaxAmt + proratevatTaxAmount) < 0) {

					logger.info("HHH Refund Endorsement Flow -Inside IF block");
					proratevatTaxAmount = OldVatTaxAmt;
					vatTaxAmount = 0.0;
					vatbleAmt = OldvatbleAmt;
				}

				else {
					logger.info("HHH Refund Endorsement Flow -Inside Else block");
					vatbleAmt = OldvatbleAmt + (proratepremiumAmt * endDiffInDays / endPeriodDays);
				}
				vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(endDiffInDays));
				vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(endPeriodDays));
			}
		}

		logger.info("VatTax Amount before formatting:_1" + vatTaxAmount + "Transaction level VatTax Amoun_1"
				+ proratevatTaxAmount);

		if (!Utils.isEmpty(policyVO.getEndorsements())
				&& policyVO.getEndorsements().get(0).isPolicyToBeCancelled() == true) {
			List<Date> canDateList = new LinkedList<Date>();
			canDateList.add(vatEffDate);
			polEndExpiryDate = policyVO.getEndEffectiveDate();
			polEndEffectiveDate = policyVO.getScheme().getEffDate();
			polDateList.add(polEndEffectiveDate);
			premiumAmt = policyVO.getEndorsements().get(0).getPremiumVO().getPremiumAmt();
			diffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(canDateList, cmp).getTime())
					/ (1000 * 60 * 60 * 24)) + 1;
			policyPeriodDays = (int) ((polEndExpiryDate.getTime() - polEndEffectiveDate.getTime())
					/ (1000 * 60 * 60 * 24)) + 1;
			vatTaxAmount = premiumAmt * diffInDays / policyPeriodDays * vatTaxPerc / 100;
			vatbleAmt = (premiumAmt * diffInDays / policyPeriodDays);

			// new changes for cancellation tax refund if UPR is not run
			// vatResults =
			// DAOUtils.getVatAmountSBS(policyQuo.getId().getPolicyId(),policyVO.getEndorsements().get(0).getEndtId(),policyVO);
			// OldVatTaxAmt=(Double)vatResults.get(0);
			// vatTaxAmount = premiumAmt * diffInDays/policyPeriodDays *
			// vatTaxPerc/100;
			// proratevatTaxAmount=vatTaxAmount-OldVatTaxAmt;

			logger.info(
					"HHH - CANCELLATION Refund vat amount is grater than the Vat which are collected Previously - begin logic ");

			if (proratevatTaxAmount > 0) // HHH
			{
				logger.info("HHH - CANCELLATION - Inside IF"); // HHH
				proratevatTaxAmount = OldVatTaxAmt; // HHH
				vatTaxAmount = 0.0; // HHH
				vatbleAmt = OldvatbleAmt; // HHH
			}

			vatList.put("diffInDay_1", String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));

		}
		// Added for Bahrain 3 decimal - Starts
		if (policyVO.getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty(policyVO.getEndorsements())) {
			if (isSBSBahrainPolicy(policyVO)) {
				Double vatAmtFormatted = Double
						.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
				Double proRateVatAmtFormatted = Double
						.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(proratevatTaxAmount), "SBS"));
				vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
				proVatAmt = SvcUtils.getRoundingOffBah(proRateVatAmtFormatted);
				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(proVatAmt), "SBS")));

			} else {
				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX, String.valueOf(
						Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(proratevatTaxAmount), "SBS")));
			}
		} else {
			if (isSBSBahrainPolicy(policyVO)) {
				Double vatAmtFormatted = Double
						.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
				vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));

			} else {

				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
			}
		}
		// Added for Bahrain 3 decimal - Ends
		logger.info("Exiting AppUtils.calculateVatTaxAmoun_1");

		vatList.put(com.Constant.CONST_VATBLEAMT, String.valueOf(vatbleAmt));
		return vatList;
	}

	/**
	 * 142244 - WC Vat Tax Implementation
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, String> calculateSBSVatTaxAmountJSP(PolicyVO policyVO, Double premiumAfterDiscount) {

		logger.info("HHH - Entering calculateSBSVatTaxAmountJSP() method");

		Map<String, String> vatList = new HashMap();

		// Boolean isUPR = false;

		Double vatTaxAmount = 0.0;
		Double vatTaxPerc = 0.0;
		Double premiumAmt = 0.0;
		Double vatbleAmt = 0.0;
		Double proratevatTaxAmount = 0.0;
		Double proratepremiumAmt = 0.0;
		int diffInDays = 0;
		int policyPeriodDays = 0;
		int endDiffInDays = 0;
		int endPeriodDays = 0;
		Double OldVatTaxAmt = 0.0, OldvatbleAmt = 0.0;
		Double OldPremium = 0.0;

		Date polEffectiveDate = null;
		Date polExpiryDate = null;
		Date vatEffDate = null;
		Date polEndEffectiveDate = null;
		Date polEndExpiryDate = null;
		Double vatAmt = 0.0;
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		Map<Integer, Double> vatResultsViewMode = new HashMap<Integer, Double>();

		/*
		 * String vatStartDate =
		 * Utils.getSingleValueAppConfig("TRAVEL_VAT_START_DATE"); // 01/01/2018
		 */
		System.out.println("vatStartDate2 :" + SvcUtils.populateVatDt());
		String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
		try {
			// SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
			System.out.println("calculateSBSVatTaxAmountJSP() method : " + vatEffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Date> polDateList = new LinkedList<Date>();
		polDateList.add(vatEffDate); // VAT Effective Date for Policy

		List<Date> endDateList = new LinkedList<Date>();
		endDateList.add(vatEffDate); // VAT Effective Date for endorsement

		Comparator<Date> cmp = new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		};

		if (!Utils.isEmpty(policyVO)) {

			if (!Utils.isEmpty(policyVO.getPremiumVO())) {

				/* Added by Dileep to fix Vat Amount issue in View mode */
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
				Long previousEndtId = DAOUtils.getPreviousEndtIdSBS(ht, policyVO);
				System.out.println("previousEndtId = " + previousEndtId);
				vatResults = DAOUtils.getVatAmountSBSSum(policyVO, previousEndtId, true);
				Long endtId = getLatestEndtId(policyVO);
				System.out.println("New EndtId: " + endtId);

				vatResultsViewMode = DAOUtils.getVatAmountSBSSum(policyVO, endtId, true);
				;

				if (policyVO.getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty(policyVO.getEndorsements())) {

					// polEndExpiryDate =
					// workmenCompVO.getEndorsmentVO().get(0).getEndExpiryDate();
					// Getting the previous vatamount
					polEndExpiryDate = policyVO.getScheme().getExpiryDate();
					polEndEffectiveDate = policyVO.getEndEffectiveDate();
					endDateList.add(polEndEffectiveDate);

					if (!Utils.isEmpty(policyVO.getEndorsements())
							&& !Utils.isEmpty(policyVO.getEndorsements().get(0).getOldPremiumVO())
							&& !Utils.isEmpty(policyVO.getEndorsements().get(0).getOldPremiumVO().getPremiumAmt())) {

						// Commented by Dileep
						/*
						 * HibernateTemplate ht = (HibernateTemplate)
						 * Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
						 * 
						 * Long previousEndtId =
						 * DAOUtils.getPreviousEndtIdSBS(ht, policyVO);
						 */
						/*
						 * Get total Premium from previous endorsements
						 */
						// Commented by Dileep
						// vatResults =
						// DAOUtils.getVatAmountSBSSum(policyVO,previousEndtId,true);

						OldPremium = policyVO.getEndorsements().get(0).getOldPremiumVO().getPremiumAmt();
						OldVatTaxAmt = (Double) vatResults.get(0);

						/*
						 * if(policyVO.getCreatedBy().equals(SvcConstants.
						 * UPR_PREPARED_BY)){ isUPR = true; }
						 * 
						 * else if( (Utils.isEmpty(OldvatbleAmt) ||
						 * Utils.isEmpty(vatResults.get(1))) || (OldvatbleAmt ==
						 * 0.0 && (Double)vatResults.get(1) == 0.0)){ isUPR =
						 * true; }
						 */
						/*
						 * if (Utils.isEmpty(OldVatTaxAmt)) { polEffectiveDate =
						 * workmenCompVO.getCommonVO().getPolEffectiveDate();
						 * polDateList.add(polEffectiveDate); polExpiryDate =
						 * workmenCompVO.getScheme().getExpiryDate(); diffInDays
						 * = (int) ((polExpiryDate.getTime() -
						 * Collections.max(polDateList, cmp).getTime()) / (1000
						 * * 60 * 60 * 24)) + 1; policyPeriodDays = (int)
						 * ((polExpiryDate.getTime() -
						 * polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24))
						 * + 1; OldVatTaxAmt=premiumAmt *
						 * diffInDays/policyPeriodDays * vatTaxPerc/100; }
						 */
						proratepremiumAmt = premiumAfterDiscount - (OldPremium - OldVatTaxAmt);
						OldvatbleAmt = (Double) vatResults.get(1);

					}

					endDiffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(endDateList, cmp).getTime())
							/ (1000 * 60 * 60 * 24)) + 1;
					endPeriodDays = (int) ((polEndExpiryDate.getTime() - polEndEffectiveDate.getTime())
							/ (1000 * 60 * 60 * 24)) + 1;

				}
				// for calculation actual Vat amount for storing to DB
				polEffectiveDate = policyVO.getPolEffectiveDate();
				polDateList.add(polEffectiveDate);
				polExpiryDate = policyVO.getScheme().getExpiryDate();

				vatTaxPerc = policyVO.getPolVatRate().doubleValue();
				;

				premiumAmt = premiumAfterDiscount;

				logger.info("PolEffDate:_2" + polEffectiveDate + " PolExpiryDate:_2" + polExpiryDate
						+ " polEndEffectiveDate:_2" + polEndEffectiveDate + "polEndExpiryDat_2" + polEndExpiryDate

						+ " VatTaxPerc:_2" + vatTaxPerc + " PremiumAmt:_2" + premiumAmt);

			}

		}

		logger.info("Max Date out of PolicyEffDate and VatEffDate _2" + Collections.max(endDateList, cmp) + "\n");

		diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime())
				/ (1000 * 60 * 60 * 24)) + 1;
		policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
		logger.info("vatResults =_1" + vatResults);
		logger.info("vatResultsViewMode =_1" + vatResultsViewMode);

		/* Added by Dileep */
		if (((policyVO.getStatus().equals(SvcConstants.POL_STATUS_ACTIVE) || policyVO.getStatus()
				.equals(Integer.valueOf(Utils.getSingleValueAppConfig(SvcConstants.CONV_TO_POL))))
				&& (policyVO.getAppFlow().equals(Flow.VIEW_QUO) || policyVO.getAppFlow().equals(Flow.VIEW_POL)))) {

			OldVatTaxAmt = (Double) vatResults.get(0);
			OldvatbleAmt = (Double) vatResults.get(1);
			vatTaxAmount = (Double) vatResultsViewMode.get(0);
			vatbleAmt = (Double) vatResultsViewMode.get(1);

			proratevatTaxAmount = Math.abs(vatTaxAmount - OldVatTaxAmt);

			vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));
			logger.info("Inside View Mode block::  vatResults = " + vatResults + " vatResultsViewMode= "
					+ vatResultsViewMode);

		}

		else if (policyPeriodDays != 0) {
			vatTaxAmount = premiumAmt * diffInDays / policyPeriodDays * vatTaxPerc / 100;
			vatbleAmt = (Double) (premiumAmt * diffInDays / policyPeriodDays);
			vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));
			/*
			 * if(isUPR){ proratevatTaxAmount = vatTaxAmount; }
			 */
			if (endPeriodDays != 0) {
				// vatTaxAmount = OldPremium * diffInDays/policyPeriodDays *
				// vatTaxPerc/100;
				proratevatTaxAmount = proratepremiumAmt * endDiffInDays / endPeriodDays * vatTaxPerc / 100;
				vatTaxAmount = OldVatTaxAmt + proratevatTaxAmount;

				logger.info("HHH JSP Refund Endorsement Flow" + "proratevatTaxAmount: " + proratevatTaxAmount);
				logger.info("HHH JSP Refund Endorsement Flow" + "OldVatTaxAmt: " + OldVatTaxAmt);

				if (proratevatTaxAmount < 0 && (OldVatTaxAmt + proratevatTaxAmount) < 0) {

					logger.info("HHH JSP Refund Endorsement Flow -Inside IF block");
					proratevatTaxAmount = OldVatTaxAmt;
					vatTaxAmount = 0.0;
					vatbleAmt = OldvatbleAmt;
				}

				else {
					logger.info("HHH JSP Refund Endorsement Flow -Inside Else block");
					vatbleAmt = OldvatbleAmt + (proratepremiumAmt * endDiffInDays / endPeriodDays);
				}

				vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(endDiffInDays));
				vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(endPeriodDays));
			}
		}

		logger.info("VatTax Amount before formatting:_2" + vatTaxAmount + "Transaction level VatTax Amoun_2"
				+ proratevatTaxAmount);

		if (!Utils.isEmpty(policyVO.getEndorsements())
				&& policyVO.getEndorsements().get(0).isPolicyToBeCancelled() == true) {
			List<Date> canDateList = new LinkedList<Date>();
			canDateList.add(vatEffDate);
			polEndExpiryDate = policyVO.getEndEffectiveDate();
			polEndEffectiveDate = policyVO.getScheme().getEffDate();
			polDateList.add(polEndEffectiveDate);
			premiumAmt = policyVO.getEndorsements().get(0).getPremiumVO().getPremiumAmt();
			diffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(canDateList, cmp).getTime())
					/ (1000 * 60 * 60 * 24)) + 1;
			policyPeriodDays = (int) ((polEndExpiryDate.getTime() - polEndEffectiveDate.getTime())
					/ (1000 * 60 * 60 * 24)) + 1;
			vatTaxAmount = premiumAmt * diffInDays / policyPeriodDays * vatTaxPerc / 100;
			vatbleAmt = (premiumAmt * diffInDays / policyPeriodDays);

			// new changes for cancellation tax refund if UPR is not run
			// vatResults = DAOUtils.getVatAmountSBSSum(policyVO,
			// policyVO.getEndorsements().get(0).getEndtId(), true);
			/// OldVatTaxAmt=(Double)vatResults.get(0);
			// vatTaxAmount = premiumAmt * diffInDays/policyPeriodDays *
			// vatTaxPerc/100;
			// proratevatTaxAmount=vatTaxAmount-OldVatTaxAmt;

			logger.info(
					"HHH - CANCELLATION Refund vat amount is grater than the Vat which are collected Previously - begin logic ");

			if (proratevatTaxAmount > 0) // HHH
			{
				logger.info("HHH - CANCELLATION - Inside IF"); // HHH
				proratevatTaxAmount = OldVatTaxAmt; // HHH
				vatTaxAmount = 0.0; // HHH
				vatbleAmt = OldvatbleAmt; // HHH
			}

			vatList.put("diffInDay_2", String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));

		}

		// Added for Bahrain 3 decimal - Starts
		if (policyVO.getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty(policyVO.getEndorsements())) {
			logger.info("FFF ENDT flow BAH debug for vatTax & ProvatTax");

			vatList.put(com.Constant.CONST_VATTAX,
					String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
			vatList.put(com.Constant.CONST_PROVATTAX, String
					.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(proratevatTaxAmount), "SBS")));

			// add new logic for BAH custom round off here -- FFF
			if (isSBSBahrainPolicy(policyVO)) {

				logger.info("FFF before executing custom BAH round-off logic");
				logger.info("FFF vatTax" + vatList.get("vatTax"));
				logger.info("FFF ProvatTax" + vatList.get("ProvatTax"));

				Double bahvatTax = new Double(
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
				Double bahProvatTax = new Double(String.valueOf(
						Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(proratevatTaxAmount), "SBS")));

				vatList.put(com.Constant.CONST_VATTAX, String.valueOf(getRoundingOffBah(bahvatTax)));
				vatList.put(com.Constant.CONST_PROVATTAX, String.valueOf(getRoundingOffBah(bahProvatTax)));

				logger.info("FFF after executing custom BAH round-off logic");
				logger.info("FFF vatTax" + vatList.get("vatTax"));
				logger.info("FFF ProvatTax" + vatList.get("ProvatTax"));

			}

		} else {
			if (isSBSBahrainPolicy(policyVO)) {
				Double vatAmtFormatted = Double
						.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
				vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));

			} else {

				vatList.put(com.Constant.CONST_VATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
				vatList.put(com.Constant.CONST_PROVATTAX,
						String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));
			}
		}
		// Added for Bahrain 3 decimal - Ends
		logger.info("Exiting AppUtils.calculateVatTaxAmoun_2");

		// add new logic for BAH custom round off here -- FFF

		vatList.put(com.Constant.CONST_VATBLEAMT, String.valueOf(vatbleAmt));

		logger.info("VATList sent to JSP:_1" + vatList);
		return vatList;
	}

	public static double getVatTaxForTravel(BaseVO baseVO) {

		double discountAmount = 0.0;
		double totalPremium = 0.0;
		double promoDiscountAmount = 0.0;
		double vatTax = 0.0;
		double vatTaxPerc = 0.0;

		if (baseVO instanceof TravelInsuranceVO) {
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) baseVO;

			if (!Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO())
					&& !Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList())) {

				for (TravelerDetailsVO travelerDetailsVO : travelInsuranceVO.getTravelDetailsVO()
						.getTravelerDetailsList()) {
					totalPremium += travelerDetailsVO.getPremiumAmtActual();
				}

				if (!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc())) {
					discountAmount = (travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * totalPremium) / 100;
					// Added for Radar Defect Unique ID: 444536; Reason: To
					// remove "," from discountAmount as it was throwing error
					discountAmount = Double.valueOf(Currency
							.getFormattedCurrency(discountAmount, travelInsuranceVO.getCommonVO().getLob().toString())
							.replaceAll(",", ""));
					discountAmount = Double.valueOf(Currency
							.getFormattedCurrency(discountAmount, travelInsuranceVO.getCommonVO().getLob().toString())
							.replaceAll(",", ""));
				}

				if (!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getPromoDiscPerc())) {
					promoDiscountAmount = (travelInsuranceVO.getPremiumVO().getPromoDiscPerc() * totalPremium) / 100;
					// Added for Radar Defect Unique ID: 444536; Reason: To
					// remove "," from discountAmount as it was throwing error
					promoDiscountAmount = Double.valueOf(Currency.getFormattedCurrency(promoDiscountAmount,
							travelInsuranceVO.getCommonVO().getLob().toString()).replaceAll(",", ""));
				}

				Date effectiveDate = travelInsuranceVO.getScheme().getEffDate();
				Date expiryDate = travelInsuranceVO.getScheme().getExpiryDate();
				long policyPeriod = 0;
				policyPeriod = ((expiryDate.getTime() - effectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;

				Date vatStartDate = null;
				Date covStartDate = null;
				String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
				try {

					// SvcUtils.populateVatDt();
					vatStartDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
				} catch (ParseException e) {

					e.printStackTrace();
				}

				try {

					covStartDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateCovRemovalDt());
				} catch (ParseException e) {

					e.printStackTrace();
				}

				Date maxDate = null;
				long maxDays = 0;
				maxDays = ((effectiveDate.getTime() - vatStartDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
				if (maxDays > 1) {
					maxDate = effectiveDate;
				} else {
					maxDate = vatStartDate;
				}

				/* VAT - Dileep */
				if (!Utils.isEmpty(travelInsuranceVO.getVatTaxPerc())) {
					if (Utils.isEmpty(travelInsuranceVO.getPremiumVO())) {
						travelInsuranceVO.setPremiumVO(new PremiumVO());
						vatTaxPerc = travelInsuranceVO.getVatTaxPerc();
					} else
						vatTaxPerc = travelInsuranceVO.getPremiumVO().getVatTaxPerc();

				}
				vatTax = ((((totalPremium + discountAmount) - promoDiscountAmount)
						* (((expiryDate.getTime() - maxDate.getTime()) / (1000 * 60 * 60 * 24)) + 1) / policyPeriod)
						* vatTaxPerc) / 100;
			}

		}
		return vatTax;
	}

	// For calculating vat amount for quote versioning in renewal quote
	public static double getVatTax(BaseVO baseVO) {

		double vatTax = 0.0;
		double onlineDiscOrLoad = 0.0;
		double promoDisc = 0.0;

		if (baseVO instanceof PolicyDataVO) {
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

			onlineDiscOrLoad = (policyDataVO.getPremiumVO().getPremiumAmt()
					+ policyDataVO.getPremiumVO().getMinPremiumApplied().doubleValue())
					* policyDataVO.getPremiumVO().getDiscOrLoadPerc() / 100;
			if (!Utils.isEmpty(policyDataVO.getPremiumVO().getPromoDiscPerc())) {
				promoDisc = (policyDataVO.getPremiumVO().getPremiumAmt()
						+ policyDataVO.getPremiumVO().getMinPremiumApplied().doubleValue())
						* policyDataVO.getPremiumVO().getPromoDiscPerc() / 100;
			}

			onlineDiscOrLoad = Double.valueOf(Currency.getFormattedCurrency(onlineDiscOrLoad));
			// CTS - 12.08.2020 - SAT FIX - Accept Quote paid amount mismatch
			// between request and response fix -- Starts
			BigDecimal onlineDisc = new BigDecimal(onlineDiscOrLoad).round(new MathContext(2, RoundingMode.UP));
			// CTS - 12.08.2020 - SAT FIX - Accept Quote paid amount mismatch
			// between request and response fix -- Ends
			promoDisc = Double.valueOf(Currency.getFormattedCurrency(promoDisc));

			Date effectiveDate = policyDataVO.getScheme().getEffDate();
			Date expiryDate = policyDataVO.getScheme().getExpiryDate();
			long policyPeriod = 0;
			policyPeriod = ((expiryDate.getTime() - effectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;

			Date vatStartDate = null;
			String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
			try {

				// SvcUtils.populateVatDt();
				vatStartDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
			} catch (ParseException e) {

				e.printStackTrace();
			}

			Date maxDate = null;
			long maxDays = 0;
			maxDays = ((effectiveDate.getTime() - vatStartDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			if (maxDays > 1) {
				maxDate = effectiveDate;
			} else {
				maxDate = vatStartDate;
			}
			// CTS - 12.08.2020 - SAT FIX - Accept Quote paid amount mismatch
			// between request and response fix -- Starts
			vatTax = (((policyDataVO.getPremiumVO().getPremiumAmt()
					+ policyDataVO.getPremiumVO().getMinPremiumApplied().doubleValue() + onlineDisc.doubleValue()
					- promoDisc) * (((expiryDate.getTime() - maxDate.getTime()) / (1000 * 60 * 60 * 24)) + 1)
					/ policyPeriod) * policyDataVO.getCommonVO().getPremiumVO().getVatTaxPerc()) / 100;
			// CTS - 12.08.2020 - SAT FIX - Accept Quote paid amount mismatch
			// between request and response fix -- Ends

			/*
			 * vatTax = ((policyDataVO.getPremiumVO().getPremiumAmt() +
			 * policyDataVO.getPremiumVO().getMinPremiumApplied().doubleValue()
			 * +onlineDiscOrLoad - promoDisc)* policyDataVO
			 * .getCommonVO().getPremiumVO().getVatTaxPerc()) / 100;
			 */
		}
		return vatTax;
	}

	/*
	 * Ticket : 164097 - Bahrain VAT enabled for all LOBs Dileep M S
	 * 
	 */
	public static Object getPremiumAndVatRoundingOffBah(Object objectVo) {
		double premiumAmt, vatAmt;
		if (objectVo instanceof HomeInsuranceVO) {
			HomeInsuranceVO homeVO = (HomeInsuranceVO) objectVo;
			if (!Utils.isEmpty(homeVO.getPremiumVO().getPremiumAmt())) {
				try {
					premiumAmt = homeVO.getPremiumVO().getPremiumAmt();
					premiumAmt = SvcUtils.getRoundingOffBah(premiumAmt);
					homeVO.getPremiumVO().setPremiumAmt(premiumAmt);
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!Utils.isEmpty(homeVO.getPremiumVO().getVatTax())) {
				try {
					vatAmt = homeVO.getPremiumVO().getVatTax();
					vatAmt = SvcUtils.getRoundingOffBah(vatAmt);
					homeVO.getPremiumVO().setVatTax(vatAmt);
					// homeVO.getBuildingDetails().setVatTax(vatAmt);
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (objectVo instanceof TravelInsuranceVO) {
			TravelInsuranceVO travelVO = (TravelInsuranceVO) objectVo;
			premiumAmt = travelVO.getPremiumVO().getPremiumAmt();
			vatAmt = travelVO.getPremiumVO().getVatTax();
			premiumAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
					.getFormattedCurrency(premiumAmt, travelVO.getCommonVO().getLob().toString()).replace(",", "")));
			vatAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
					.getFormattedCurrency(vatAmt, travelVO.getCommonVO().getLob().toString()).replace(",", "")));
			travelVO.getPremiumVO().setPremiumAmt(premiumAmt);
			travelVO.getPremiumVO().setPremiumAmtActual(premiumAmt);
		}
		return objectVo;
	}

	/*
	 * Ticket : 164097 - Bahrain VAT enabled for all LOBs Dileep M S
	 * 
	 */
	public static Double getRoundingOffBah(Double decimalValue) {
		decimalValue = Math.abs(decimalValue);
		String str = Double.toString(decimalValue);
		String result = String.format("%.3f", decimalValue).replaceAll(com.Constant.CONST_D_END, "");
		int indexOfDecimal = str.indexOf(".");
		System.out.println("Integer Part: " + str.substring(0, indexOfDecimal));
		String decimal = String.valueOf(decimalValue).replaceAll(com.Constant.CONST_D_END, "");
		System.out.println("Decimal Part: " + String.valueOf(decimalValue).replaceAll("^\\d*\\_1", ""));
		System.out.println(String.valueOf(decimalValue).replaceAll("^\\d*\\_2", ""));

		int reminder = Integer.parseInt(result) % 10;
		double customRoundedValue = 0;
		DecimalFormat ft = new DecimalFormat("####");
		ft = new DecimalFormat("#.000");
		System.out.println(String.format("%.3f", decimalValue));
		if (reminder == 0 || reminder == 1 || reminder == 2) {
			customRoundedValue = Math.round(decimalValue * 100.0) / 100.0;
			System.out.println("Round to lowest value :" + ft.format(customRoundedValue));
		} else if (reminder == 3 || reminder == 4 || reminder == 5 || reminder == 6 || reminder == 7) {
			String roundOffData = null;
			String s = String.valueOf(reminder);
			String lastDecimal = s.replace(s, "5");
			System.out.println("After Round : " + lastDecimal);
			String decimalPart = (Integer.parseInt(decimal) / 10) + lastDecimal;
			if (decimalPart.length() == 2) {
				String padded = String.format("%03d", Integer.parseInt(decimalPart));
				System.out.println("padded:" + padded);
				roundOffData = str.substring(0, indexOfDecimal) + "." + padded;
			} else {
				roundOffData = str.substring(0, indexOfDecimal) + "." + decimalPart;
			}
			customRoundedValue = Double.parseDouble(roundOffData);
			System.out.println("After Concat : " + Double.parseDouble(roundOffData));
		} else if (reminder == 8 || reminder == 9) {
			customRoundedValue = Math.round(decimalValue * 100.0) / 100.0;
			System.out.println("Round to highest value :" + ft.format(customRoundedValue));
		}

		return customRoundedValue;
	}

	// Added for Bahrain 3 decimal - Starts
	public static boolean isSBSBahrainPolicy(PolicyVO policy) {

		Integer policyType = 0;
		policyType = Integer.valueOf(Utils.getSingleValueAppConfig("SBS_Policy_Type"));
		if (policyType == Integer.valueOf(policy.getScheme().getPolicyType())
				&& Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")) {
			return true;
		}
		return false;
	}
	// Added for Bahrain 3 decimal - Ends

	public static Map<String, String> getVatAmount(PolicyVO policyVO) {

		logger.info("HHH - Entering calculateSBSVatTaxAmountJSP() method");

		Map<String, String> vatList = new HashMap();

		Double vatTaxAmount = 0.0;
		Double vatTaxPerc = 0.0;
		Double premiumAmt = 0.0;
		Double vatbleAmt = 0.0;
		int diffInDays = 0;
		int policyPeriodDays = 0;

		Date polEffectiveDate = null;
		Date polExpiryDate = null;
		Date vatEffDate = null;
		Date polEndEffectiveDate = null;
		Date polEndExpiryDate = null;
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		Map<Integer, Double> vatResultsViewMode = new HashMap<Integer, Double>();

		String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
		try {
			// SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Date> polDateList = new LinkedList<Date>();
		polDateList.add(vatEffDate); // VAT Effective Date for Policy

		List<Date> endDateList = new LinkedList<Date>();
		endDateList.add(vatEffDate); // VAT Effective Date for endorsement

		Comparator<Date> cmp = new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		};

		if (!Utils.isEmpty(policyVO)) {

			if (!Utils.isEmpty(policyVO.getPremiumVO())) {

				HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
				Long previousEndtId = DAOUtils.getPreviousEndtIdSBS(ht, policyVO);
				System.out.println("previousEndtId = " + previousEndtId);
				vatResults = DAOUtils.getVatAmountSBSSum(policyVO, previousEndtId, true);
				Long endtId = getLatestEndtId(policyVO);
				System.out.println("New EndtId: " + endtId);

				vatResultsViewMode = DAOUtils.getVatAmountSBSSum(policyVO, endtId, true);

				// for calculation actual Vat amount for storing to DB
				polEffectiveDate = policyVO.getPolEffectiveDate();
				polDateList.add(polEffectiveDate);
				polExpiryDate = policyVO.getScheme().getExpiryDate();

				vatTaxPerc = policyVO.getPolVatRate().doubleValue();

				logger.info("PolEffDate:_3" + polEffectiveDate + " PolExpiryDate:_3" + polExpiryDate
						+ " polEndEffectiveDate:_3" + polEndEffectiveDate + "polEndExpiryDat_3" + polEndExpiryDate

						+ " VatTaxPerc:_3" + vatTaxPerc + " PremiumAmt:_3" + premiumAmt);

			}

		}

		logger.info("Max Date out of PolicyEffDate and VatEffDate _3" + Collections.max(endDateList, cmp) + "\n");

		diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime())
				/ (1000 * 60 * 60 * 24)) + 1;
		policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
		logger.info("vatResults =_2" + vatResults);
		logger.info("vatResultsViewMode =_2" + vatResultsViewMode);

		vatTaxAmount = (Double) vatResultsViewMode.get(0);
		vatbleAmt = (Double) vatResultsViewMode.get(1);

		vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(diffInDays));
		vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));
		logger.info(
				"Inside View Mode block::  vatResults = " + vatResults + " vatResultsViewMode= " + vatResultsViewMode);

		vatList.put(com.Constant.CONST_PROVATTAX,
				String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatTaxAmount), "SBS")));

		vatList.put(com.Constant.CONST_VATBLEAMT, String.valueOf(vatbleAmt));

		logger.info("VATList sent to JSP:_2" + vatList);
		return vatList;

	}

	@SuppressWarnings("deprecation")
	public static Map<String, String> calculateSBSVatJSP(PolicyVO policyVO, Double premiumAfterDiscount) {

		logger.info("HHH - Entering calculateSBSVatJSP() method");

		Map<String, String> vatList = new HashMap();

		// Boolean isUPR = false;

		Double vatTaxAmount = 0.0;
		Double vatTaxPerc = 0.0;
		Double premiumAmt = 0.0;
		Double vatbleAmt = 0.0;
		Double proratevatTaxAmount = 0.0;
		int diffInDays = 0;
		int policyPeriodDays = 0;

		Date polEffectiveDate = null;
		Date polExpiryDate = null;
		Date vatEffDate = null;
		Date polEndEffectiveDate = null;
		Date polEndExpiryDate = null;
		Double vatAmt = 0.0;
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		Map<Integer, Double> vatResultsViewMode = new HashMap<Integer, Double>();

		/*
		 * String vatStartDate =
		 * Utils.getSingleValueAppConfig("TRAVEL_VAT_START_DATE"); // 01/01/2018
		 */
		System.out.println("vatStartDate3 :" + SvcUtils.populateVatDt());
		String defaultDateFormat = Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_DATE_FORMAT); // MM/dd/yyyy
		try {
			// SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
			System.out.println("calculateSBSVatJSP() method : " + vatEffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Date> polDateList = new LinkedList<Date>();
		polDateList.add(vatEffDate); // VAT Effective Date for Policy

		List<Date> endDateList = new LinkedList<Date>();
		endDateList.add(vatEffDate); // VAT Effective Date for endorsement

		Comparator<Date> cmp = new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		};

		if (!Utils.isEmpty(policyVO)) {

			if (!Utils.isEmpty(policyVO.getPremiumVO())) {

				// for calculation actual Vat amount for storing to DB
				polEffectiveDate = policyVO.getPolEffectiveDate();
				polDateList.add(polEffectiveDate);
				polExpiryDate = policyVO.getScheme().getExpiryDate();

				vatTaxPerc = policyVO.getPolVatRate().doubleValue();
				;

				premiumAmt = premiumAfterDiscount;

				logger.info("PolEffDate:_4" + polEffectiveDate + " PolExpiryDate:_4" + polExpiryDate
						+ " polEndEffectiveDate:_4" + polEndEffectiveDate + "polEndExpiryDat_4" + polEndExpiryDate

						+ " VatTaxPerc:_4" + vatTaxPerc + " PremiumAmt:_4" + premiumAmt);

			}

		}

		logger.info("Max Date out of PolicyEffDate and VatEffDate _4" + Collections.max(endDateList, cmp) + "\n");

		diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime())
				/ (1000 * 60 * 60 * 24)) + 1;
		policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
		logger.info("vatResults =_3" + vatResults);
		logger.info("vatResultsViewMode =_3" + vatResultsViewMode);

		if (policyPeriodDays != 0) {
			vatTaxAmount = premiumAmt * diffInDays / policyPeriodDays * vatTaxPerc / 100;
			vatbleAmt = (Double) (premiumAmt * diffInDays / policyPeriodDays);
			vatList.put(com.Constant.CONST_DIFFINDAYS, String.valueOf(diffInDays));
			vatList.put(com.Constant.CONST_POLICYPERIODDAYS, String.valueOf(policyPeriodDays));

		}

		logger.info("VatTax Amount before formatting:_3" + vatTaxAmount + "Transaction level VatTax Amoun_3"
				+ proratevatTaxAmount);

		if (isSBSBahrainPolicy(policyVO)) {
			Double vatAmtFormatted = Double
					.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
			vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
			vatList.put(com.Constant.CONST_VATTAX,
					String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));
			vatList.put(com.Constant.CONST_PROVATTAX,
					String.valueOf(Currency.getUnformattedScaledCurrency(BigDecimal.valueOf(vatAmt), "SBS")));

		}

		// Added for Bahrain 3 decimal - Ends
		logger.info("Exiting AppUtils.calculateVatTaxAmoun_3");

		// add new logic for BAH custom round off here -- FFF

		vatList.put(com.Constant.CONST_VATBLEAMT, String.valueOf(vatbleAmt));

		logger.info("VATList sent to JSP:_3" + vatList);
		return vatList;
	}

	// Added for Bahrain 3 decimal - Starts
	public static Double getCommPercForEachClassCd(Map<Integer, Double> classCodeComm, int classCode) {
		logger.info("HHH - Entering getCommPercForEachClassCd() method");
		Double commPerc = classCodeComm.get(classCode);
		if (Utils.isEmpty(commPerc))
			return 0.0;

		return commPerc;

	}

	private static Double getClassLevelPrem(List<PremiumSummarySectionVO> prmSummarySecs, int classCode,
			Double discoundloading) {
		logger.info("HHH - Entering getClassLevelPrem() method");
		Double classLevelPrem = 0.0;
		Double classLevelDiscPrem = 0.0;
		for (PremiumSummarySectionVO prmSummarySec : prmSummarySecs) {
			if (prmSummarySec.getClazz() == classCode) {
				classLevelPrem = classLevelPrem + prmSummarySec.getSecPrm().doubleValue();
			}
		}
		classLevelPrem = Double.valueOf(Currency.getFormattedCurrency(classLevelPrem).replaceAll(",", ""));
		classLevelDiscPrem = calcClassLevelPremAftDisc(classLevelPrem, discoundloading);
		System.out.println("Class leve prem with or without discount/loading------------" + classLevelDiscPrem);
		return classLevelDiscPrem;
	}

	public static Double calcClassLevelComm(List<PremiumSummarySectionVO> prmSummarySecs, int classCode,
			Double discoundloading, Double commPerc) {
		logger.info("HHH - Entering calcClassLevelComm() method");
		Double commAmt = 0.0;
		commAmt = (commPerc * getClassLevelPrem(prmSummarySecs, classCode, discoundloading)) / 100;
		String formattedCommission = Currency.getFormattedCurrency(commAmt).replaceAll(",", "");
		Double roundedComm = SvcUtils.getRoundingOffBah(Double.valueOf(formattedCommission));
		return roundedComm;

	}

	private static Double calcClassLevelPremAftDisc(Double premAmt, Double discountLoading) {
		logger.info("HHH - Entering calcClassLevelPrem() method");
		Double premAftDisc = 0.0;
		premAftDisc = ((premAmt * discountLoading) / 100) + premAmt;
		String formattedPrem = Currency.getFormattedCurrency(premAftDisc).replaceAll(",", "");
		Double roundedPrem = SvcUtils.getRoundingOffBah(Double.valueOf(formattedPrem));
		return roundedPrem;

	}

	// Added for Bahrain 3 decimal - Ends
	public static boolean isValidSchemeCode(PolicyVO policyVO) {
		boolean validSchemeCode = false;
		if (!Utils.isEmpty(Utils.getSingleValueAppConfig("SBS_API_SCHEME_CODE"))) {
			String schemeCodes[] = Utils.getSingleValueAppConfig("SBS_API_SCHEME_CODE").split(",");
			String schemeCode = "";
			if (!Utils.isEmpty(policyVO.getScheme())) {
				schemeCode = policyVO.getScheme().getSchemeCode().toString();
			}
			if (schemeCode != null) {
				for (String val : schemeCodes) {
					if (schemeCode.equals(val)) {
						validSchemeCode = true;
						break;
					} else {
						validSchemeCode = false;
					}
				}
			}
		}
		return validSchemeCode;
	}

	public static boolean isValidUser(PolicyVO policyVO) {
		boolean validUser = false;

		if (!Utils.isEmpty(Utils.getSingleValueAppConfig("SBS_API_USER_ID"))) {
			String userIds[] = Utils.getSingleValueAppConfig("SBS_API_USER_ID").split(",");
			int quoteCreatedBy = 0;
			if (!Utils.isEmpty(policyVO.getCreatedBy())) {
				quoteCreatedBy = Integer.parseInt(policyVO.getCreatedBy());
			}

			for (String val : userIds) {
				if (quoteCreatedBy == Integer.parseInt(val)) {
					validUser = true;
					break;
				} else {
					validUser = false;
				}
			}

		}
		return validUser;
	}

	// changes-HomeRevamp#7.1
	// changes-HomeRevamp#4.1

	public static String populateAEDDt() {
		logger.info("Entering populateAEDDt() method");

		String populateAEDDt = null;
		// if (Utils.isEmpty(vatStartDate)) {

		List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_AED_START_DATE);

		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {

			String AEDIncDate = null;
			AEDIncDate = (String) resultSetVat.get(0);

			if (AEDIncDate != null) {
				AEDIncDate = formatDt(AEDIncDate, com.Constant.CONST_DD_MMM_YYYY, "MM/dd/yyyy");
				populateAEDDt = AEDIncDate;
			}
		}
		logger.info("Exiting populateVatDt() method");
		return populateAEDDt;
	}

	// changes-HomeRevamp#4.1
	public static String populatePolEDt(Long quotationNo) {
		logger.info("Entering populatePolEDt() method");

		String populatePolEDt = null;
		List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.HomeRevamp_pol_prepareDt,
				quotationNo);

		if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date polPrepareDate = (Date) resultSetVat.get(0);
			String AEDIncDate = df.format(polPrepareDate);
			populatePolEDt = AEDIncDate;

		}
		logger.info("Exiting populatePolEDt() method");
		return populatePolEDt;
	}

	// CTS - 21.08.2020 - Balaji - Starts
	public static List<String> populatePolEDtPolDocCode(Long quotationNo) {
		logger.info("Entering populatePolEDtPolDocCode() method");

		String populatePolEDt = null, date = null;
		String docCode = null;
		Date polPrepareDate = null;
		List<Object[]> resultSet = DAOUtils.getSqlResultForPasObject(QueryConstants.HOMEREVAMP_POL_PREPAREDDT_DOCCODE,
				quotationNo);

		List<String> result = new ArrayList<String>();
		if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			polPrepareDate = (Date) resultSet.get(0)[0];
			String AEDIncDate = df.format(polPrepareDate);
			populatePolEDt = AEDIncDate;
			docCode =  resultSet.get(0)[1].toString();			
			result.add(populatePolEDt);
			result.add(docCode);
		}
		logger.info("Exiting populatePolEDtPolDocCode() method");
		return result;
	}
	// CTS - 21.08.2020 - Balaji - Ends
	
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
	public static List<String> populateKYCDt() {
		logger.info("Entering populateKYCDt() method");
		
		String cdmCodeIA = null;
		String KYCCutDate = null;
		String populateKYCDat = null;
		List<Object[]> resultSetKyc = DAOUtils.getSqlResultForPasObject(QueryConstants.FETCH_KYC_START_DATE);
		
		List<String> resultIALVDT = new ArrayList<String>();

		if (!Utils.isEmpty(resultSetKyc) && resultSetKyc.size() > 0) {
			KYCCutDate = (String) resultSetKyc.get(0)[0];
			if (KYCCutDate != null) {
				KYCCutDate = formatDt(KYCCutDate, com.Constant.CONST_DD_MMM_YYYY, "MM/dd/yyyy");
				populateKYCDat = KYCCutDate;
			}
			cdmCodeIA = resultSetKyc.get(0)[1].toString();
			resultIALVDT.add(populateKYCDat);
			resultIALVDT.add(cdmCodeIA);
		}
		logger.info("Exiting populateKYCDt() method");
		return resultIALVDT;
	}
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End

}
