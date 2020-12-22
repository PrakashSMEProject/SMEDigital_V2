package com.rsaame.pas.dao.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.clauses.dao.ViewClausesDAO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.EplatformWsStaging;
import com.rsaame.pas.dao.model.EplatformWsStagingId;
import com.rsaame.pas.dao.model.SsVMasLookup;
import com.rsaame.pas.dao.model.SsVMasLookupId;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TMasPolicyCondition;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.dao.model.TTrnEndorsementDetail;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPolicyCondition;
import com.rsaame.pas.dao.model.TTrnPolicyConditionQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuoId;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuo;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuoId;
import com.rsaame.pas.dao.model.TTrnTempPasReferral;
import com.rsaame.pas.dao.model.TTrnTempPasReferralId;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtlsId;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuoId;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.gen.mapper.PolicyVOToCashCustomerPOJO;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.pojo.mapper.EndorsementVOToTTrnEndorsementDetails;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.uwq.svc.UWQService;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.PPPCriteriaVO;
import com.rsaame.pas.vo.app.UWQInputsVO;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WCVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author M1014644
 * 
 *         Contains common methode used in multiple daos
 */
	public class DAOUtils{
		private static final Logger LOGGER = Logger.getLogger( DAOUtils.class );
		//private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
		private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
		private final static Integer WC_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) );
		private final static Integer BI_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_SECTION" ) );
		private final static Integer EE_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) );
		private final static Integer MB_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_SECTION" ) );
		private final static Integer GIT_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION" ) );
		//private final static Integer GROUP_PERSONAL_ACCIDENT_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION" ) );
		private final static Integer GEN_SECTION_ID = 0;
		private final static Integer LOC_ID = 0;
		private final static Long LOC_ID_LONG = 0l;
		private final static Integer MONEY_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) );
		private final static Integer SI_PRM_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "SI_PRM__SECTION_ID" ) );
		private final static String TABLE_QUERY_RATE_TYPE = "from TMasPolicyRating tMasPolRating where tMasPolRating.id.prTariff = ? and tMasPolRating.id.prPtCode = ? ";
		private final static String TABLE_QUERY_FETCH_VSD = "select Pkg_pas_utils.get_revised_pol_issuedate(:sysdt) as vsdTimeStamp from dual";
		private final static String QUERY_TMAS_CONTROL = "Select * from t_mas_control";
		private final static String QUERY_CUST_ID = "select sch_default_billing_account from t_mas_scheme where sch_code = :schCode";
		private final static String QUERY_TOT_ACC_CODE = "select cu_tot_acc_code from t_mas_customer where cu_customer_id = :custId";
		private final static Short REN_DOC_CODE = 6;
		private static final String CASH_CUSTOMER_QUO = "t_mas_cash_customer_quo";
		private static final String CASH_CUSTOMER = "t_mas_cash_customer";
		private static final String POLICY_QUO = com.Constant.CONST_T_TRN_POLICY_QUO;
		private static final String POLICY = com.Constant.CONST_T_TRN_POLICY;
		private static final Long GEN_SEC_LOC_ID = 0L;
		private static final String QUERY_FETCH_SCHEME_CODE = "SELECT SCH_CODE FROM T_MAS_SCHEME WHERE SCH_BROKER_CODE = :brokerCode";
		private static final String FETCH_REC_FOR_QUOTE = " from TTrnPolicyQuo polQuo where polQuo.polQuotationNo = ? and polQuo.polLocationCode = ? and  polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
		private static final String FETCH_REC_FOR_POLICY = " from TTrnPolicyQuo polQuo where polQuo.polPolicyNo = ? and polQuo.polLocationCode = ? and polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
		private static final String FETCH_REC_DATA_EXTENDED_DOC_CODE = " and polQuo.polDocumentCode=?";
		private static final String FETCH_REC_DATA_EXTENDED_EFF_DATE = " and pol_effective_date = ?";
		
		// Changing query param as per Srini's comments to consider creation date for promo code validation.
		private static final String QUERY_FETCH_PROMOTIONAL_CODE_COVER = "SELECT PDC.PDC_COV_CODE,PDC.PDC_SCHEME_CODE, PC.PRO_E_DESC FROM T_MAS_PROMO_DISC_COVER PDC,T_MAS_PROMOTIONAL_CODE PC "
				+ "WHERE PDC.PDC_PROMO_CODE = PC.PRO_CODE AND PDC.PDC_PROMO_CODE = :promotionalCode AND PDC.PDC_SCHEME_CODE = :schemeCode AND PC.PRO_TYPE = 'Free cover' "
				+ "AND PC.PRO_CLASS_CODE = :classCode AND sysdate between PC.PRO_START_DATE AND PC.PRO_END_DATE";
		// Changing query param as per Srini's comments to consider creation date for promo code validation Ticket ID: 119593 , Nil endorsement .
			private static final String QUERY_FETCH_PROMOTIONAL_CODE_COVER_FOR_ENDT = "SELECT PDC.PDC_COV_CODE,PDC.PDC_SCHEME_CODE, PC.PRO_E_DESC FROM T_MAS_PROMO_DISC_COVER PDC,T_MAS_PROMOTIONAL_CODE PC "
					+ "WHERE PDC.PDC_PROMO_CODE = PC.PRO_CODE AND PDC.PDC_PROMO_CODE = :promotionalCode AND PDC.PDC_SCHEME_CODE = :schemeCode AND PC.PRO_TYPE = 'Free cover' "
					+ "AND PC.PRO_CLASS_CODE = :classCode";
		
		// Changed query param as per Srini's comments to consider creation date for promo code validation.
		private static final String QUERY_FETCH_PROMOTIONAL_CODE_DISCOUNT = "SELECT PC.PRO_DISC_PERC, PC.PRO_E_DESC FROM T_MAS_PROMO_DISC_COVER PDC,T_MAS_PROMOTIONAL_CODE PC "
				+ "WHERE PDC.PDC_PROMO_CODE = PC.PRO_CODE AND PDC.PDC_PROMO_CODE = :promotionalCode AND PDC.PDC_SCHEME_CODE = :schemeCode AND PC.PRO_TYPE = 'Discount' "
				+ "AND PC.PRO_CLASS_CODE = :classCode AND sysdate between PC.PRO_START_DATE AND PC.PRO_END_DATE";
	
		// Changed query param as per Srini's comments to consider creation date for promo code validation. Ticket ID: 119593 , Nil endorsement 
		private static final String QUERY_FETCH_PROMOTIONAL_CODE_DISCOUNT_FOR_ENDT = "SELECT PC.PRO_DISC_PERC, PC.PRO_E_DESC FROM T_MAS_PROMO_DISC_COVER PDC,T_MAS_PROMOTIONAL_CODE PC "
				+ "WHERE PDC.PDC_PROMO_CODE = PC.PRO_CODE AND PDC.PDC_PROMO_CODE = :promotionalCode AND PDC.PDC_SCHEME_CODE = :schemeCode AND PC.PRO_TYPE = 'Discount' "
				+ "AND PC.PRO_CLASS_CODE = :classCode ";
		
		private static final String FETCH_SCHEME_POL_TYPE_QUO = "SELECT POL_COVER_NOTE_HOUR,POL_POLICY_TYPE FROM T_TRN_POLICY_QUO WHERE POL_ISSUE_HOUR = 3 AND POL_QUOTATION_NO = :POL_QUO_NO and POL_ENDT_ID =:pol_end_id";
		private static final String FETCH_SCHEME_POL_TYPE_POL = "SELECT  POL_COVER_NOTE_HOUR,POL_POLICY_TYPE FROM T_TRN_POLICY WHERE POL_ISSUE_HOUR = 3 AND POL_POLICY_NO = :POL_POLICY_NO and POL_ENDT_ID =:pol_end_id";
	
		private static final String QUERY_FETCH_REFERRAL_MESSAGE = "SELECT REF_FIELD ,REF_TEXT " + "FROM T_TRN_PAS_REFERRAL_DETAILS "
				+ "WHERE REF_STATUS = 20 AND REF_POLICY_ID = :policyId AND REF_ENDT_ID = :endtId AND REF_CREATED_BY = :userId";
	
		private static final String QUERY_FETCH_PREVIOUS_PREMIUM = " SELECT PRM_PREMIUM, PRM_EFFECTIVE_DATE , ( NVL(PRM_PREMIUM_ACTUAL,0) +  ( NVL(PRM_PREMIUM_ACTUAL,0) * NVL(PRM_LOAD_DISC,0) /100 ) ),PRM_STATUS,PRM_EXPIRY_DATE FROM T_TRN_PREMIUM " +
				"WHERE PRM_POLICY_ID = :policyId AND PRM_ENDT_ID < :endtId AND PRM_COV_CODE = :coverCode AND PRM_RT_CODE = :rtCode AND PRM_RC_CODE = :rskCat ORDER BY PRM_ENDT_ID desc";
		
		private static final String QUERY_FETCH_PREVIOUS_PREMIUM_TRAVEL = "SELECT PRM_PREMIUM,  PRM_EFFECTIVE_DATE , ( NVL(PRM_PREMIUM_ACTUAL,0) +  ( NVL(PRM_PREMIUM_ACTUAL,0) * NVL(PRM_LOAD_DISC,0) /100 ) ),PRM_STATUS,PRM_EXPIRY_DATE FROM T_TRN_PREMIUM " +
				"WHERE PRM_POLICY_ID = :policyId AND PRM_ENDT_ID < :endtId AND PRM_COV_CODE = :coverCode AND  PRM_RSK_ID = :rskId ORDER BY PRM_ENDT_ID desc";
		
		private static final String FETCH_TARIFF_QUO = "SELECT POL_TAR_CODE FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO = :POL_QUO_NO and POL_ENDT_ID =:pol_end_id";
		private static final String FETCH_TARIFF_POL = "SELECT POL_TAR_CODE FROM T_TRN_POLICY WHERE POL_POLICY_NO = :POL_POLICY_NO and POL_ENDT_ID =:pol_end_id and POL_ISSUE_HOUR = 3";
		private static final String FETCH_CONTENT_SI_QUO = "SELECT CNT_SUM_INSURED FROM T_TRN_CONTENT_QUO WHERE CNT_POLICY_ID = :pol_id and CNT_ENDT_ID <= :pol_end_id and CNT_CATEGORY=31 and cnt_validity_expiry_date = :cnt_val_exp_date and cnt_status <> 4";
		private static final String FETCH_CONTENT_SI_POL = "SELECT CNT_SUM_INSURED FROM T_TRN_CONTENT WHERE CNT_POLICY_ID = :pol_id and CNT_ENDT_ID <= :pol_end_id and CNT_CATEGORY=31 and cnt_validity_expiry_date = :cnt_val_exp_date and cnt_status <> 4";
		
		private static final String FETCH_CITY_DESCRIPTION="SELECT CIT_DESC FROM T_MAS_CITY WHERE CIT_CODE=:cit_code";
		private static final String FETCH_MAX_SI_REFERRALS = " from TTrnTempPasReferral pasRef where pasRef.id.tprPolLinkingId = ? and pasRef.id.tprLocationId = ? and ( ( pasRef.tprRefText like ? and pasRef.tprRefText like ? ) or ( pasRef.tprRefText like ? and pasRef.tprRefText like ? ) )";
		private static final String QUERY_FETCH_PREVIOUS_PREMIUM_RISK_ID = "SELECT PRM_PREMIUM, PRM_EFFECTIVE_DATE , ( NVL(PRM_PREMIUM_ACTUAL,0) +  ( NVL(PRM_PREMIUM_ACTUAL,0) * NVL(PRM_LOAD_DISC,0) /100 ) ),PRM_STATUS,PRM_EXPIRY_DATE FROM T_TRN_PREMIUM  " +
				"WHERE PRM_POLICY_ID = :policyId AND PRM_ENDT_ID < :endtId AND PRM_RSK_ID = :rskId ORDER BY PRM_ENDT_ID desc";
		private static final String FETCH_PARTNER_DETAILS = "select pmm.PMM_ID, pmm.PMM_PARTNER_CODE, pmm.PMM_PARTNER_TYPE, pmm.PMM_CALLCENTRE_NO, "
				+ "pmm.PMM_REPLYTO_EMAIL, pmm.PMM_CC_EMAIL, pmm.PMM_PLCY_TERMS_URL, dft.DFT_USER, dft.DFT_SOC_CUSTOMER, dft.DFT_COVER_NOTE_HOUR, " 
				+ "dft.DFT_TAR_CODE,dft.DFT_ASSIGN_TO,dft.DFT_DISCOUNT_PERC,pmm.PMM_FROM_EMAIL ,dc.DCH_SOB,pmm.PMM_FAQ_URL , pmm.PMM_PRODUCTS_PLCY_TERMS_URL " 
				+ "from T_MAS_PARTNER_MGMT pmm, T_MAS_DEFAULT_VALUES dft , T_MAS_DISTRIBUTION_CHANNEL dc "
				+ "where pmm.PMM_ID = :partnerName and pmm.PMM_STATUS = :status and pmm.PMM_PT_CODE = :ptCode and pmm.PMM_CLASS_CODE = :classCode "
				+ "and pmm.PMM_ID = dft.DFT_PMM_ID and pmm.PMM_PT_CODE = dft.DFT_POLICY_TYPE "
				+ "and pmm.PMM_CLASS_CODE = dft.DFT_CLASS and pmm.PMM_PARTNER_TYPE =dc.DCH_CODE";
		//142244  VAT  implementation
		private static final String FETCH_VAT_RATE_AND_CODE="select pr_prem_rate,pr_vat_code from t_mas_policy_rating where pr_cl_code=:classCode and pr_pt_code=:ptCode and pr_cov_code=:coverCode ";
		    //+" and trunc(pr_rate_effective_date)<=:preparedDate  and trunc(pr_rate_expiry_date)>=:preparedDate";	
			
		//VAT new screen implementation starts													
		private static final String FETCH_INSURED_CODE_POLICY_PL = "select POL_INSURED_CODE from T_TRN_POLICY where POL_POLICY_ID=:policyId and POL_ENDT_ID=:polEndtId " +
																" and POL_POLICY_TYPE in (:lobs) and POL_ISSUE_HOUR =:polIssueHour";
	
		private static final String FETCH_INSURED_CODE_POLICY_SBS = "select POL_INSURED_CODE from T_TRN_POLICY where POL_LINKING_ID=:linkingId and POL_ENDT_ID=:polEndtId " +
																	" and POL_POLICY_TYPE in (:lobs) and POL_ISSUE_HOUR =:polIssueHour";
																
		private static final String UPDATE_VAT_REGNO_POLICY_SBS = "update T_TRN_POLICY set POL_VAT_REGN_NO =:polVatRegNo where POL_LINKING_ID =:linkingId "+															
																  " and POL_POLICY_TYPE in (:lobs) and pol_issue_hour =:polIssueHour";
	
		private static final String UPDATE_VAT_REGNO_POLICY_PL = "update T_TRN_POLICY set POL_VAT_REGN_NO =:polVatRegNo where POL_POLICY_ID =:policyId "+															
																" and POL_POLICY_TYPE in (:lobs) and pol_issue_hour =:polIssueHour";
	
		private static final String UPDATE_VAT_REGNO_INSURED = "update T_MAS_INSURED set INS_VAT_REGN_NO =:insVatRegNo where INS_INSURED_CODE =:insInsuredCode";	
		
		//VAT new screen implementation ends
		
		private static final String FETCH_ENABLED_DISABLED_SECTIONS="SELECT CDM_CODE1,CDM_CODE2 FROM T_MAS_CODE_MASTER where CDM_CODE=:schCode and CDM_ENTITY_TYPE = 'SEC_UNHIDE'";
		
		private static Map<Integer, Integer> SectionToRiskCodeMap = new HashMap<Integer, Integer>();
		
		static{
			initMapSectionToRiskCode();
		}
		
	
		/* TODO Why couldn't we have used handleTableRecord() here? */
		public static TTrnSectionLocationQuo setSecLocDetails( SectionVO section, PolicyVO policyVO ){
			RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
	
			TTrnSectionLocationQuoId tTrnSectionLocationQuoId = new TTrnSectionLocationQuoId();
	
			tTrnSectionLocationQuoId.setTslSecId( section.getSectionId().shortValue() );
			tTrnSectionLocationQuoId.setTslLocId( Long.valueOf( rg.getRiskGroupId() ) );
			tTrnSectionLocationQuoId.setTslPolEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			tTrnSectionLocationQuoId.setTslPolLinkingId( policyVO.getPolLinkingId() );
	
			TTrnSectionLocationQuo tTrnSectionLocationQuo = new TTrnSectionLocationQuo();
			tTrnSectionLocationQuo.setId( tTrnSectionLocationQuoId );
			tTrnSectionLocationQuo.setTslActiveFlag( "Y" );
			tTrnSectionLocationQuo.setTslValidityExpiryDate( SvcConstants.EXP_DATE );
			tTrnSectionLocationQuo.setTslValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			tTrnSectionLocationQuo.setTslBasicRiskId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) );
	
			return tTrnSectionLocationQuo;
		}
	
		public static Map endFlowGeneralInfo( Long policyLinkingId, Boolean isQuote ){
			Map results = null;
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) ( isQuote ? Utils.getBean( "endIDgenerationQuo" ) : Utils.getBean( "endIDgeneration" ) );
			results = sp.call( policyLinkingId );
			return results;
		}
	
		public static Map getPervEndoresemetnId( Long policyLinkingId, Long policyId, Long newEndtId ){
			Map results = null;
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endIDgeneration" );
			results = sp.call( policyLinkingId );
			return results;
		}
	
		public static TTrnPremiumQuo getPrevCoverLevelPrm( long policyId, long riskId, Long endtId, HibernateTemplate hibernateTemplate ){
	
			/*
			 * select * from T_TRN_PREMIUM_QUO where prm_policy_id = '506304' and
			 * prm_rsk_id = '400205' and prm_status <> 4 and
			 * prm_validity_expiry_date = '31-DEC-49' and prm_endt_id = '0';
			 */
			Date expDate = SvcConstants.EXP_DATE;
	
			List<TTrnPremiumQuo> premiumDetail = hibernateTemplate.find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId = ? and "
					+ "prm.id.prmRskId = ? and  prm.prmValidityExpiryDate = ? ", policyId, BigDecimal.valueOf( riskId ), expDate );
	
			if( !Utils.isEmpty( premiumDetail ) && premiumDetail.size() > 1 )
				throw new BusinessException( "", null, "In DAOUtils.getPrevCoverLevelPrm multiple rows where returned for a single cover query:"
						+ "select * from T_TRN_PREMIUM_QUO where prm_policy_id = " + policyId + " and prm_rsk_id = " + riskId
						+ " and prm_status <> 4 and prm_validity_expiry_date = '31-DEC-49'" );
	
			if( Utils.isEmpty( premiumDetail ) )
				return null;
			else
				return premiumDetail.get( 0 );
	
		}
		
		public static TTrnPremiumQuo getPrevCoverLevelPrm( long policyId ,long riskId, long basicRskId, short covcode, HibernateTemplate hibernateTemplate ){
	
			/*
			 * select * from T_TRN_PREMIUM_QUO where prm_policy_id = '506304' and
			 * prm_rsk_id = '400205' and prm_status <> 4 and
			 * prm_validity_expiry_date = '31-DEC-49' and prm_endt_id = '0';
			 */
			Date expDate = SvcConstants.EXP_DATE;
	
			List<TTrnPremiumQuo> premiumDetail = hibernateTemplate.find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId = ? and "
					+ "prm.id.prmRskId = ? and prm.id.prmBasicRskId = ? and prm.id.prmCovCode = ?  and  prm.prmValidityExpiryDate = ? ", policyId, BigDecimal.valueOf( riskId ), BigDecimal.valueOf( basicRskId ), covcode, expDate );
	
			if( !Utils.isEmpty( premiumDetail ) && premiumDetail.size() > 1 )
				throw new BusinessException( "", null, "In DAOUtils.getPrevCoverLevelPrm multiple rows where returned for a single cover query:"
						+ "select * from T_TRN_PREMIUM_QUO where prm_policy_id = " + policyId + " and prm_rsk_id = " + riskId
						+ " and prm_status <> 4 and prm_validity_expiry_date = '31-DEC-49'" );
	
			if( Utils.isEmpty( premiumDetail ) )
				return null;
			else
				return premiumDetail.get( 0 );
	
		}
	
		public static void deletePremium( TTrnPremiumQuo premium, HibernateTemplate hibernateTemplate ){
			hibernateTemplate.delete( premium );
		}
	
		public static void prmEndtTextGen( long prmPolicyId, long prmEndtId, BigDecimal prmBasicRskId, Integer userId ){
			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "Prm_text_gen_POL" );
			try{
				Map results = sp.call( prmPolicyId, prmEndtId, prmBasicRskId, userId );
				if( Utils.isEmpty( results ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_1" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc fro premium end text generation" );
			}
	
		}
	
		public static void updateVED( PolicyVO policyVO, int classCode, int sectionId ){
			PASStoredProcedure sp = null;
			if( !Utils.isEmpty( policyVO.getNewEndtId() ) && policyVO.getNewEndtId() != 0L ){
	
				if( policyVO.getIsQuote() ){
					sp = (PASStoredProcedure) Utils.getBean( "updateValExpDateSecQuo" );
				}
				else{
					sp = (PASStoredProcedure) Utils.getBean( "updateValExpDateSection" );
				}
	
				try{
					Map resultsVED = sp.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(), policyVO.getNewValidityStartDate(), classCode, sectionId );
					if( Utils.isEmpty( resultsVED ) ){
						LOGGER.debug( "The result returned by the stored procedure is empt_2" );
					}
				}
				catch( DataAccessException e ){
					throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_1" );
				}
			}
	
		}
		
		public static void updateHomeTravelVED( CommonVO commonVO){
			PASStoredProcedure sp = null;
			if( !Utils.isEmpty( commonVO.getEndtId() ) && commonVO.getEndtId() != 0L ){
	
				if( commonVO.getIsQuote() ){
					sp = (PASStoredProcedure) Utils.getBean( "updateValExpDateSecQuoHomeTravel" );
				}
				else{
					sp = (PASStoredProcedure) Utils.getBean( "updateValExpDateHomeTravelPOL" );
				}
	
				try{
					Map resultsVED = sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), commonVO.getVsd(), Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_CLASS_CODE" ) ), Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) ) );
					if( Utils.isEmpty( resultsVED ) ){
						LOGGER.debug( "The result returned by the stored procedure is empt_3" );
					}
				}
				catch( DataAccessException e ){
					throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_2" );
				}
			}
	
		}
	
		public static void updateEBCforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId, Integer cntCategory ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "extraBldCnt" );
	
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, riskGroupId, cntCategory );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_4" );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_3" );
			}
	
		}
	
		public static void updateSIforendorsementFlow( long policyLinkingId, long endtId, Integer userId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "updateSI" );
			try{
				Map resultsVED = sp.call( policyLinkingId, endtId, userId, SI_PRM_SECTION_ID, LOC_ID );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_5" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_4" );
			}
	
		}
	
		public static void updatePRMforendorsementFlow( long policyLinkingId, long endtId, Integer userId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "updatePRM" );
			try{
				Map resultsVED = sp.call( policyLinkingId, endtId, userId, SI_PRM_SECTION_ID, LOC_ID );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_6" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_5" );
			}
			
		}
			
		public static void updateFooterforendorsementFlow( long policyLinkingId, long endtId, Integer userId ){
			PASStoredProcedure sp2 = null;
			sp2 = (PASStoredProcedure) Utils.getBean( "addEndtTextHeaderFooter" );
			try{
				Map resultsVED = sp2.call( policyLinkingId, endtId, SI_PRM_SECTION_ID);
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_7" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_6" );
			}
	
		}
	
		public static void updateEndTextForGenInfo( Long policyId, Long cshInsuredId, PolicyVO policyVO ){
			
			//addEndtTextHeaderFooter(policyVO); 		
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "modifyGenInfo" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), cshInsuredId, SvcUtils.getUserId( policyVO ), GEN_SECTION_ID, LOC_ID );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure modifyGenInfo is empty" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_7" );
			}
			/*
			 * Endt Text for Policy Table during Policy Extension
			 */
			System.out.println( "call pro_endt_text_policy" );
			PASStoredProcedure sp1 = null;
			sp1 = (PASStoredProcedure) Utils.getBean( "procEndtTextPol" );
			try{
				Map resultsVED1 = sp1.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GEN_SECTION_ID, LOC_ID );
				if( Utils.isEmpty( resultsVED1 ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_8" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_8" );
			}
	
		}
	
		public static void deletePrevEndtText( Long policyId, Long newEndtId, int sectionId, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "deletePrevEndorsement" );
			try{
				Map resultsVED = sp.call( policyId, newEndtId, sectionId, riskGroupId, null );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_9" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_9" );
			}
	
		}
	
		/**
		 * Call's stored procedure to update endorsement text for
		 * T_TRN_WCTPL_UNNAMED_PERSON table column modifications
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskGroupId
		 * @param basicRiskId
		 */
		public static void updateWCforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long basicRiskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcModi" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), basicRiskId, SvcUtils.getUserId( policyVO ), WC_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_10" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_10" );
			}
	
		}
	
		/**
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param valueOf
		 * @param basicRiskId
		 */
		public static void addWCforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_11" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_11" );
			}
	
		}
	
		public static void updateMBCforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId, Integer cntCategory ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "modifyBldCnt" );
	
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), riskGroupId, SvcUtils.getUserId( policyVO ), sectionId, riskGroupId, cntCategory );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_12" );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_12" );
			}
	
		}
	
		public static void deleteWCforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_13" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_13" );
			}
	
		}
	
		public static void deleteCntforEndorsementFlow( Long policyId, PolicyVO policyVO, Integer SectionId, Long riskGroupId, Integer cntCategory ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "delCnt" );
	
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), SectionId, riskGroupId, cntCategory );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_14" );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_14" );
			}
	
		}
	
		public static void updatePRMADDforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "premAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), PL_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_15" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_15" );
			}
	
		}
	
		/**
		 * Call's stored procedure to update endorsement text for
		 * T_TRN_WCTPL_PREMISE table column modifications
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskGroupId
		 * @param basicRiskId
		 */
		public static void updatePRMMODorendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long basicRiskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "premModi" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), basicRiskId, SvcUtils.getUserId( policyVO ), PL_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_16" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_16" );
			}
	
		}
	
		/**
		 * Fetches and returns the endorsement Id for the policy previous to the one
		 * passed.
		 * 
		 * @param ht
		 * @param policyId
		 * @param newEndtId
		 * @return
		 */
		public static long getPrevEndtId( HibernateTemplate ht, boolean isQuote, long policyId, long newEndtId ){
			String sqlquery = null;
			if( isQuote )
				sqlquery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_PREV_ENDT_ID_QUO_END + policyId + "," + newEndtId + com.Constant.CONST_FROM_DUAL2;
			else
				sqlquery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_PREV_ENDT_ID_END + policyId + "," + newEndtId + com.Constant.CONST_FROM_DUAL2;
	
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlquery );
			List<Object> result = query.list();
			if( Utils.isEmpty( result ) ){
				LOGGER.debug( "Prev Endorsement id is nul_1" );
				return 0L;
			}
	
			return ( (BigDecimal) result.get( 0 ) ).longValue();
		}
	
		/**
		 * Constructs an HBM or SQL query for the table's snapshot view for a
		 * policy.
		 * 
		 * @param tableId
		 * @param flow
		 * @param sql
		 * @return
		 */
		public static Object getTableSnapshotQuery( String tableId, Flow appFlow, TableSnapshotLevel viewType, HibernateTemplate ht, boolean sql, /*
																																					 * Object
																																					 * polOrLinkId
																																					 * ,
																																					 */
				Long endId, Object... params ){
			return TableSnapshotQueryHelper.createTableSnapshotQuery( tableId, viewType, appFlow, ht, sql, /* polOrLinkId, */endId, params );
		}
	
		/**
		 * Constructs an HBM or SQL query for the table's snapshot view for a
		 * policy. This method derives the TableSnapshotLevel based on the value of
		 * appFlow.
		 * 
		 * @param tableId
		 * @param flow
		 * @param sql
		 * @return
		 */
		public static Object getTableSnapshotQuery( String tableId, Flow appFlow, HibernateTemplate ht, boolean sql, /* Object polOrLinkId, */
				Long endId, Object... params ){
			TableSnapshotLevel viewType = TableSnapshotQueryHelper.getViewTypeForFlow( appFlow );
	
			return TableSnapshotQueryHelper.createTableSnapshotQuery( tableId, viewType, appFlow, ht, sql, /* polOrLinkId, */endId, params );
		}
	
		/**
		 * This method will be basically called to get Max validity start date of
		 * the record from the basic risk table for each of the section i.e. for ex
		 * - T_TRN_BUILDING/QUO is basic risk table in case of PAR, similarly
		 * T_TRN_GACC_BUILDING/QUO is basic risk table for Money..
		 * 
		 * @param hibernateTemplate
		 * @param criteria
		 * @param additionalCriteria
		 * @param sql
		 * @param isQuote
		 * @param
		 * @return
		 */
	
		public static Object getMaxVSDFromBasicRiskTable( HibernateTemplate hibernateTemplate, String tableId, boolean sql, boolean isQuote, Object... params ){
	
			Object vsd = null;
			StringBuilder query = new StringBuilder();
	
			String[] criteriaForVSD = TableSnapshotQueryHelper.getConfigKeyForVSD( tableId, sql );
			String[] additionalCriteriaForVSD = TableSnapshotQueryHelper.getAdditionalConfigForVSD( tableId, sql );
			if( Utils.isEmpty( criteriaForVSD ) ){
				throw new BusinessException( "pas.config.tableConfig", null, "There is no Configuration available for table id [" + tableId + "]" );
			}
	
			/*
			 * Throw business exception in case configuration parameter size is <= 2
			 * in case of SQL and in <= 3 in case of HBM
			 */
			if( sql && criteriaForVSD.length <= 2 || !sql && criteriaForVSD.length <= 3 ){
				throw new BusinessException( "pas.config.tableConfig", null, "Criteria configured for [" + tableId + "] table id and [ " + sql + " ] is lesser than size 2 or 3 " );
			}
	
			if( Utils.isEmpty( additionalCriteriaForVSD ) ){
				throw new BusinessException( "pas.config.tableConfig", null, "There is no additionalCriteria configured to fetch max(VSD) for table id [" + tableId + "]" );
			}
	
			if( sql ){
				TableSnapshotQueryHelper.createSQLQueryForVSD( query, criteriaForVSD, additionalCriteriaForVSD, isQuote );
			}
			else{
				TableSnapshotQueryHelper.createHBMQueryForVSD( query, criteriaForVSD, additionalCriteriaForVSD, isQuote );
			}
	
			List<Object> result = hibernateTemplate.find( query.toString(), TableSnapshotQueryHelper.getBindingValuesAsObjectArray( params ) );
			if( !Utils.isEmpty( result ) ){
				vsd = result.get( 0 );
			}
	
			return vsd;
		}
	
		/**
		 * 
		 * @param hibernateTemplate
		 * @param tableId
		 * @param appFlow
		 * @param sql
		 * @return
		 */
		public static Object getExistingRecordFromTheTable( HibernateTemplate hibernateTemplate, String tableId, Flow appFlow, boolean sql, Object... params ){
			StringBuilder query = new StringBuilder();
			/*TableSnapshotLevel viewType = TableSnapshotQueryHelper.getViewTypeForFlow( appFlow );*/
			boolean isQuote = isQuoteFlow( appFlow );
			Object vsd = null;
			Object existingTableRow = null;
	
			// else{
			/*
			 * Fetch the max(validity_start_date) for the configured criteria and
			 * additional criteria .
			 */
			vsd = getMaxVSDFromBasicRiskTable( hibernateTemplate, tableId, sql, isQuote, params );
	
			if( !Utils.isEmpty( vsd ) ){
				/*
				 * Form the criteria for fetching the record basis the validity
				 * start date fetched from the table
				 */
				String[] configValues = TableSnapshotQueryHelper.getConfigForExistingRecordFetch( tableId, sql );
	
				if( Utils.isEmpty( configValues ) ){
					throw new BusinessException( "pas.config.tableConfig", null, "There is no Configuration available for table id [" + tableId + "]" );
				}
	
				TableSnapshotQueryHelper.constructHBMQueryForExistingRecordFetch( query, configValues, isQuote );
	
				List<Object> paramList ;
				paramList = CopyUtils.asList( params );
				paramList.add( vsd );
	
				List<Object> result = hibernateTemplate.find( query.toString(), TableSnapshotQueryHelper.getBindingValuesAsObjectArray( paramList.toArray() ) );
				if( !Utils.isEmpty( result ) ){
					existingTableRow = result.get( 0 );
				}
	
			}
	
			return existingTableRow;
		}
	
		/**
		 * Compares the passed Pojo instance with the current record in the
		 * corresponding table.
		 * 
		 * @param ht
		 * @param tableId
		 * @param newValObj
		 * @param policy
		 * @param params
		 * @return
		 */
		public CurrRecComparisonResult compareWithCurrRec( HibernateTemplate ht, String tableId, Object newValObj, PolicyVO policy, Object... params ){
			/*
			 * (a) Get the key values from PolicyVO. (b) Invoke
			 * DAOUtils.getTableSnapshotQuery() to get the current record for the
			 * key. (c) Compare the passed Pojo with the Pojo instance from (b)
			 * using SvcUtils methods. (d) Construct CurrRecComparisonResult
			 * instance and return.
			 */
			return null;
		}
	
		static boolean isQuoteFlow( Flow appFlow ){
			/*
			 * TODO - temporarily assigned RESOLVE_REFERAL flow as quote flow to be
			 * removed for endorsements release
			 */
			return ( appFlow.equals( Flow.VIEW_QUO ) || appFlow.equals( Flow.EDIT_QUO ) || appFlow.equals( Flow.CREATE_QUO ) || appFlow.equals( Flow.RESOLVE_REFERAL ) || appFlow
					.equals( Flow.RENEWAL ) ) ? true : false;
		}
	
		/**
		 * This class represents the result of comparison of two Pojo instances. It
		 * contains the result of the comparison and the Id of the existing record.
		 * 
		 * @author m1014644
		 * 
		 */
		public static class CurrRecComparisonResult{
			private Action action;
			private Object pojoId;
	
			public Action getAction(){
				return action;
			}
	
			public void setAction( Action action ){
				this.action = action;
			}
	
			public Object getPojoId(){
				return pojoId;
			}
	
			public void setPojoId( Object pojoId ){
				this.pojoId = pojoId;
			}
		}
	
		/**
		 * Fetches and returns the table's current valid state record (ie, the
		 * record with Validity Expiry Date as 31-DEC-2049). <br/>
		 * <br/>
		 * 
		 * If no record is found, this method throws a BusinessException with error
		 * key ""
		 * 
		 * @param <T>
		 * @param tableId
		 * @param flow
		 * @param ht
		 * @param sql
		 * @param polOrLinkingId
		 * @param endtId
		 * @param params
		 * @return
		 */
		public static <T> POJO getExistingValidStateRecord( String tableId, Flow flow, HibernateTemplate ht, boolean sql,
		/* Object polOrLinkingId, */Long endtId, Object... params ){
			@SuppressWarnings( "unchecked" )
			List<T> list = (List<T>) DAOUtils.getTableSnapshotQuery( tableId, flow, TableSnapshotLevel.CURRENT_VALID_STATE, ht, sql, /*
																																		* polOrLinkingId,
																																		*/endtId, params );
	
			if( list.size() > 1 ){
				throw new BusinessException( "pas.cmn.tooManyRecordsFound", null, "Expected one record for table, but got [" + list.size(), "." );
			}
	
			if( list.size() == 0 ){
				throw new BusinessException( "pas.cmn.noRecordFound", null, "Existing record for table not found." );
			}
	
			return (POJO) list.get( 0 );
		}
	
		/**
		 * Returns the record that corresponds to this table and Endt_Id.
		 * 
		 * @param <T>
		 * @param tableId
		 * @param flow
		 * @param ht
		 * @param sql
		 * @param polOrLinkingId
		 * @param endtId
		 * @param params
		 * @return
		 */
		public static <T> T getExistingEndtIdStateRecord( String tableId, Flow flow, HibernateTemplate ht, boolean sql, /* Object polOrLinkingId, */
				Long endtId, Object... params ){
			@SuppressWarnings( "unchecked" )
			List<T> list = (List<T>) DAOUtils.getTableSnapshotQuery( tableId, flow, TableSnapshotLevel.ENDT_ID, ht, sql, /* polOrLinkingId, */
					endtId, params );
	
			if( !Utils.isEmpty( list ) ) return list.get( 0 );
			return null;
		}
	
		/**
		 * Fetches the current or new Endt_Id and Endt_No for the passed policy.
		 * This method even sets a corresponding Validity Start Date to
		 * ThreadLevelContext and the passed PolicyVO instance.
		 * 
		 * @param policyVO
		 */
		public static void fetchEndtId( PolicyVO policyVO, HibernateTemplate ht ){
	
			Map results = null;
			if( Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ) ){
				results = DAOUtils.endFlowGeneralInfo( policyVO.getPolLinkingId(), policyVO.getIsQuote() );
				policyVO.setNewEndtId( Long.valueOf( results.get( "p_endt_id" ).toString() ) );
				policyVO.setNewEndtNo( Long.valueOf( results.get( "p_endt_no" ).toString() ) );
	
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, policyVO.getNewEndtId() );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, policyVO.getNewEndtNo() );
	
				/* We need to set the validity start date for the endorsement as the current date-time, if the Endt_Id is new. If the Endt_Id is
				 * a continuing one, which happens if there is a pending endorsement for this policy, then we need to use the already existing
				 * validity start date in the policy. This would have been mapped to the PolicyVO when it was loaded. */
				int isNew = ( (Number) results.get( "p_is_new" ) ).intValue();
	
				/*
				 * In case the policy is being endorsed on the month open
				 * a, Check if there is a pending endorsement.
				 * b, Check if the revisedVSD less than the VSD for the previous endt
				 * c,If the above condition fails, add 10 seconds to the previous endt.
				 * All the above check is to be don only if the policy is active
				 */
	
				//a,
				if( isNew == 1 ){
	
					//b,
					if( validateVSD( policyVO, ht ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
						Date vsd = (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE );
						//c,
						policyVO.setNewValidityStartDate( SvcUtils.incrementVSD( vsd ) );
					}
					else{
						policyVO.setNewValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
					}
	
				}
	
				ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, policyVO.getNewValidityStartDate() );
			}
	
		}
	
		public static void fetchEndtId( CommonVO commnVo, HibernateTemplate ht ){
	
			Map results = null;
			if( Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ) ){
				results = DAOUtils.endFlowGeneralInfoHomeTravel(commnVo.getPolicyId(),commnVo.getIsQuote() );
				
				commnVo.setEndtId( Long.valueOf( results.get( "p_endt_id" ).toString() ) );
				commnVo.setEndtNo( Long.valueOf( results.get( "p_endt_no" ).toString() ) );
	
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, commnVo.getEndtId() );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, commnVo.getEndtNo() );
	
				int isNew = ( (Number) results.get( "p_is_new" ) ).intValue();
				if (isNew == 1) {
	
					if (validateVSD(commnVo, ht) && commnVo.getAppFlow().equals(Flow.AMEND_POL)) {
					
						Date vsd = (Date) ThreadLevelContext.get(SvcConstants.TLC_KEY_SYSDATE);
						commnVo.setVsd(SvcUtils.incrementVSD(vsd));
				
					} else {
						commnVo.setVsd((Date) ThreadLevelContext.get(SvcConstants.TLC_KEY_SYSDATE));
					}
	
				}
	
				ThreadLevelContext.set(SvcConstants.TLC_KEY_VSD, commnVo.getVsd());
			}
	
		}
		
		public static enum Action{
			INSERT, UPDATE;
		}
	
		/**
		 * 
		 * @param polLinkingId
		 * @param locId
		 * @param secId
		 */
		public static void deleteReferralRecordsForKey( Long polLinkingId, Long locId, Integer secId, HibernateTemplate hibernateTemplate ){
	
			if( Utils.isEmpty( polLinkingId ) || Utils.isEmpty( locId ) || Utils.isEmpty( secId ) )
				throw new BusinessException( "", null, "One of the parameters out of PolicyLinkingId:_1" + polLinkingId + " , LocationId: " + locId + " and SectionId: " + secId
						+ " required to delete the recored from tempPasReferral table is null or empty." );
	
			TTrnTempPasReferral tempPasReferral = new TTrnTempPasReferral();
	
			TTrnTempPasReferralId id = new TTrnTempPasReferralId();
			id.setTprPolLinkingId( polLinkingId );
			id.setTprLocationId( locId );
			id.setTprSecId( secId.shortValue() );
	
			tempPasReferral.setId( id );
	
			try{
				hibernateTemplate.delete( tempPasReferral );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "", null, " Error occured while deleting referral from temp table. " );
			}
	
		}
	
		public static void handleCommonReferrals( Long polLinkingId, Integer secId, Long locId, HibernateTemplate hibernateTemplate ){
			
			/* while saving a section, if there is no referral for maxSIPerLoc or MaxSIPerPolicy in that location, delete the referrals saved for other sections because
			 * if the referral is not triggered while saving the current location of this section, it means SI is not exceeded.*/
			
			boolean referralsExist = false;
			List<TTrnTempPasReferral> pasReferrals = hibernateTemplate.find( FETCH_MAX_SI_REFERRALS, polLinkingId, locId, "%" + Utils.getSingleValueAppConfig( "maxSIPerLoc_Text1" ) + "%",
					"%" + Utils.getSingleValueAppConfig( "maxSIPerLoc_Text2" ) + "%", "%" + Utils.getSingleValueAppConfig( "maxSIPerPolicy_Text1" ) + "%",
					"%" + Utils.getSingleValueAppConfig( "maxSIPerPolicy_Text2" ) + "%" );
			if( !Utils.isEmpty( pasReferrals ) && pasReferrals.size() > 0 ){
				for( TTrnTempPasReferral pasReferral : pasReferrals ){
					if( pasReferral.getId().getTprSecId() == secId.shortValue() ){
						referralsExist = true;
						break;
					}
				}
	
				if( !referralsExist ){
					hibernateTemplate.deleteAll( pasReferrals );
				}
			}
	
		}
	
		/* TODO Why couldn't we have use handleTableRecord() here? */
		public static TTrnSectionDetailsQuo getSection( SectionVO section, PolicyVO policyVO ){
	
			TTrnSectionDetailsQuo tTrnSectionDetailsQuo = new TTrnSectionDetailsQuo();
	
			TTrnSectionDetailsQuoId detailsQuoId = new TTrnSectionDetailsQuoId();
			detailsQuoId.setSecPolicyId( section.getPolicyId() );
			detailsQuoId.setSecSecId( section.getSectionId().shortValue() );
	
			/*
			 * ValidityStartDate to be cascaded from PolicyVO.validityStartDate
			 * which is updated by General Info Save Operation. Hence the same
			 * validityStartDate to be carried across all DB entries for the Quote
			 */
	
			// TODO: The if else condition is to be removed once the dao's move to
			// the new structure.
	
			if( Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) ) ){
				detailsQuoId.setSecValidityStartDate( new Date() );
			}
			else{
				detailsQuoId.setSecValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			}
	
			tTrnSectionDetailsQuo.setId( detailsQuoId );
	
			tTrnSectionDetailsQuo.setSecStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
			// tTrnSectionDetailsQuo.setSecCommVal( Utils.isEmpty(
			// riskDetails.getCommission() )?BigDecimal.valueOf( 0
			// ):BigDecimal.valueOf(riskDetails.getCommission()));
			tTrnSectionDetailsQuo.setSecCommVal( Utils.isEmpty( section.getCommission() ) ? BigDecimal.valueOf( 0 ) : BigDecimal.valueOf( section.getCommission() ) );
			tTrnSectionDetailsQuo.setSecClCode( Short.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", section.getSectionId().toString() ) ) ) );
	
			tTrnSectionDetailsQuo.setSecEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
	
			tTrnSectionDetailsQuo.setSecPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
			tTrnSectionDetailsQuo.setSecValidityExpiryDate( SvcConstants.EXP_DATE );
			return tTrnSectionDetailsQuo;
	
		}
	
		public static Long createPolicyRecord( PolicyVO policyVO, int classCode, int secId ){
			/*
			 * Get SectionVO for the section in order to get commission for the
			 * section to enable insertion of record to t_trn_section_details(_quo)
			 * table through below procedure call itself
			 */
			SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, secId );
			Map results = null;
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "InsertCshPolProc_QUO" ) : Utils.getBean( "InsertCshPolProc_POL" ) );
	
			results = sp
					.call( classCode, policyVO.getPolLinkingId(), 0.0, ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), SvcUtils.getUserId( policyVO ),
							ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ), secId,
							!Utils.isEmpty( sectionVO )/* Will be null for General Info */? sectionVO.getCommission() : null );
	
			/*
			 * Set Status of the Quote/Policy as Pending whenever 'p_is_new'
			 * returned in the 'results' is 1 ( true ).
			 */
			if( !Utils.isEmpty( results ) && !Utils.isEmpty( results.get( PASStoredProcedure.P_IS_NEW ) )
					&& ( (BigDecimal) results.get( PASStoredProcedure.P_IS_NEW ) ).equals( SvcConstants.P_IS_NEW_TRUE )
					&& ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED ) ) ) ) 
					&& ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_SOFT_STOP ) ) ) ) ){
				policyVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			}
	
			return ( (BigDecimal) results.get( "p_pol_id" ) ).longValue();
		}
	
		public static Long createPolicyRecord( CommonVO commonVO, int classCode, int secId ){
			/*
			 * Get SectionVO for the section in order to get commission for the
			 * section to enable insertion of record to t_trn_section_details(_quo)
			 * table through below procedure call itself
			 */
			SectionVO sectionVO = null;
			Map results = null;
			PASStoredProcedure sp = null;
			
			if(commonVO.getLob().equals(LOB.HOME) || commonVO.getLob().equals(LOB.TRAVEL)){
				
				sp = (PASStoredProcedure) ( commonVO.getIsQuote() ? Utils.getBean( commonVO.getLob().toString()+"InsertCshPolProc_QUO" ) : Utils.getBean( commonVO.getLob().toString()+"InsertCshPolProc_POL" ) );
			
			}else {
				
				sp = (PASStoredProcedure) ( commonVO.getIsQuote() ? Utils.getBean( "InsertCshPolProcQuo_monoline" ) : Utils.getBean( "InsertCshPolProcQuo_monoline" ) );
	
			}
	
			results = sp
					.call( classCode,commonVO.getPolicyId(), 0.0, ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), SvcUtils.getUserId( commonVO ),
							ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ), secId, null );
	
			/*
			 * Set Status of the Quote/Policy as Pending whenever 'p_is_new'
			 * returned in the 'results' is 1 ( true ).
			 */
			if( !Utils.isEmpty( results ) && !Utils.isEmpty( "po_is_new")
					&& ( (BigDecimal) results.get( "po_is_new") ).equals( SvcConstants.P_IS_NEW_TRUE )
					&& ( !commonVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED ) ) ) ) ){
				commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			}
	
			return ( (BigDecimal) results.get( "po_pol_id" ) ).longValue();
		}
		
		/**
		 * 
		 * @param hibernateTemplate
		 * @param baseVO
		 * @param vMasBasicAndAddtlDetsList
		 * @param pPPCriteriaVO
		 * @param fetchBasicDets
		 * @param rskCode
		 * @return
		 */
		public static List<VMasPasFetchBasicDtls> getDataFromVMasPasFetchBasicDtls( HibernateTemplate hibernateTemplate, BaseVO baseVO,
				List<VMasPasFetchBasicDtls> vMasBasicAndAddtlDetsList, PPPCriteriaVO pPPCriteriaVO, boolean fetchBasicDets, Integer rskCode ){
	
			List<Object> bindingParamList = new com.mindtree.ruc.cmn.utils.List<Object>( Object.class );
			bindingParamList.add( pPPCriteriaVO.getSectionId() );
			bindingParamList.add( pPPCriteriaVO.getClassCode() );
			bindingParamList.add( pPPCriteriaVO.getTariffCode() );
	
			StringBuilder query = new StringBuilder();
	
			/*
			 * SQLQuery approach is used here as there is no explicit key defined
			 * for this view and data fetch without explicit key using HQL is
			 * returning null values
			 */
			Session session = null;
			SQLQuery sqlQuery = null;
	
			/*
			 * a. Dynamically view name is passed to the query as both the view have
			 * same structure. b. Pojo name under VMasPasFetchAddlDtls.hbm.xml has
			 * also been changed to VMasPasFetchBasicDtls and
			 * VMasPasFetchBasicDtlsId
			 */
			String viewName = fetchBasicDets ? "v_mas_pas_fetch_basic_dtls" : "v_mas_pas_fetch_addl_dtls";
			session = hibernateTemplate.getSessionFactory().getCurrentSession();
	
			query.append( "select * from " + viewName + " vfbd where vfbd.sec_id = :secId and vfbd.PR_CL_CODE =:clCode and vfbd.PR_TARIFF = :tar " );
			if( !Utils.isEmpty( rskCode ) ) query.append( " and  vfbd.PR_RSK_CODE = :rskCode " );
	
			sqlQuery = session.createSQLQuery( query.toString() );
			sqlQuery.setParameter( "secId", Short.valueOf( pPPCriteriaVO.getSectionId().shortValue() ) );
			sqlQuery.setParameter( "clCode", Short.valueOf( pPPCriteriaVO.getClassCode().shortValue() ) );
			sqlQuery.setParameter( "tar", pPPCriteriaVO.getTariffCode() );
	
			if( !Utils.isEmpty( rskCode ) ) sqlQuery.setParameter( "rskCode", rskCode );
	
			List<Object[]> result = sqlQuery.list();
	
			/*
			 * Throw BusinessException only in case of contents for Base Cover Code
			 * is not found as additional covers are optional for a tariff code
			 */
			if( fetchBasicDets && Utils.isEmpty( result ) ){
				throw new BusinessException( "cmn.contentsNotFound", null, "Contents fetch from V_MAS_PAS_FETCH_BASIC_DTLS for [" + pPPCriteriaVO.toString()
						+ "]failed check DB configurations for the same" );
			}
			/*
			 * Iterate the list of objects array obtained to set it to
			 * VMasPasFetchBasicDtls POJO to pass the same to getRiskDetails method
			 */
	
			vMasBasicAndAddtlDetsList = !Utils.isEmpty( vMasBasicAndAddtlDetsList ) ? vMasBasicAndAddtlDetsList : new ArrayList<VMasPasFetchBasicDtls>();
			Iterator<Object[]> it = result.iterator();
	
			while( it.hasNext() ){
				Object[] object = it.next();
				VMasPasFetchBasicDtls vMasPasFetchBasicDtls = new VMasPasFetchBasicDtls();
				vMasPasFetchBasicDtls.setId( new VMasPasFetchBasicDtlsId() );
	
				if( !Utils.isEmpty( object[ 0 ] ) ){
					vMasPasFetchBasicDtls.getId().setSecId( ( (BigDecimal) ( object[ 0 ] ) ).shortValue() ); // SEC_ID
				}
				if( !Utils.isEmpty( object[ 1 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrClCode( ( (BigDecimal) object[ 1 ] ).shortValue() ); // PR_CL_CODE
				}
				if( !Utils.isEmpty( object[ 2 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrCovCode( ( (BigDecimal) object[ 2 ] ).shortValue() ); // PR_cov_code
				}
				if( !Utils.isEmpty( object[ 3 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrCtCode( ( (BigDecimal) object[ 3 ] ).shortValue() ); // PR_ct_code
				}
				if( !Utils.isEmpty( object[ 4 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrCstCode( ( (BigDecimal) object[ 4 ] ).shortValue() ); // PR_cst_code
				}
				if( !Utils.isEmpty( object[ 5 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrRskCode( ( (BigDecimal) object[ 5 ] ).intValue() );// PR_rsk_code
				}
				if( !Utils.isEmpty( object[ 6 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrRtCode( ( (BigDecimal) object[ 6 ] ).intValue() );// Pr_RT_code
				}
				if( !Utils.isEmpty( object[ 7 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrRcCode( ( (BigDecimal) object[ 7 ] ).intValue() ); // Pr_rc_code
				}
				if( !Utils.isEmpty( object[ 8 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrRscCode( ( (BigDecimal) object[ 8 ] ).intValue() ); // Pr_rsc_code
				}
				if( !Utils.isEmpty( object[ 9 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrCompulsoryExcess( (BigDecimal) object[ 9 ] );// PR_Compulsory_excess
				}
				if( !Utils.isEmpty( object[ 10 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrTariff( ( (BigDecimal) object[ 10 ] ).intValue() ); // Pr_tariff
				}
				if( !Utils.isEmpty( object[ 11 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrSumInsured( (BigDecimal) object[ 11 ] ); // PR_sum_insured
				}
				if( !Utils.isEmpty( object[ 12 ] ) ){
					vMasPasFetchBasicDtls.getId().setPrLimit( (BigDecimal) object[ 12 ] ); // Pr_Limit
				}
				if( !Utils.isEmpty( object[ 13 ] ) ){
	
					vMasPasFetchBasicDtls.getId().setPrAggLimit( ( (BigDecimal) object[ 13 ] ) ); // PR_Agg_limit
				}
				if( !Utils.isEmpty( object[ 14 ] ) ){
					vMasPasFetchBasicDtls.getId().setPcrEDesc( ( object[ 14 ] ).toString() ); // PR_e_desc
				}
				if( !Utils.isEmpty( object[ 15 ] ) ){
					vMasPasFetchBasicDtls.getId().setSecNumPersons( ( (BigDecimal) object[ 15 ] ).shortValue() ); // SEc_num_persons
				}
	
				if( !Utils.isEmpty( object[ 16 ] ) ){
					vMasPasFetchBasicDtls.getId().setCovDesc( ( object[ 16 ] ).toString() ); // cov_desc
				}
	
				vMasBasicAndAddtlDetsList.add( vMasPasFetchBasicDtls );
			}
	
			return vMasBasicAndAddtlDetsList;
		}
	
		/*
		 * Constructing List<Contents> i.e. risk types which is configured for base
		 * cover
		 */
		public static List<Contents> getContentsListForSection( BaseVO input, List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
				List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
	
			List<Contents> contentsList = new com.mindtree.ruc.cmn.utils.List<Contents>( Contents.class );
	
			/*
			 * Use BeanMapper to form different Contents which is finally set to
			 * List of contents
			 */
			for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){
	
				Contents content;
				content = BeanMapper.map( vMasPasFetchBasicDtls, Contents.class );
				contentsList.add( content );
			}
	
			return contentsList;
		}
	
		public static PPPCriteriaVO constructPPPCriteriaVOForPPPDataFetch( Integer classCode, Integer SectionId, Integer polTariffCode ){
			PPPCriteriaVO pPPPCriteriaVO = new PPPCriteriaVO();
			pPPPCriteriaVO.setClassCode( classCode );
			pPPPCriteriaVO.setSectionId( SectionId );
			pPPPCriteriaVO.setTariffCode( polTariffCode );
			return pPPPCriteriaVO;
		}
	
		/*
		 * This method will return the general info related referralVO to save into
		 * TempReferral table. Commonly used in General info save for Endorsement
		 * and Quote flows
		 */
		public static ReferralVO getReferralVOForSave( PolicyVO policyVO ){
	
			ReferralVO locreferralVO = null;
			if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
				for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
	
					if( mapRefEntry.getKey().getSectionId() == 0 || mapRefEntry.getKey().getSectionId() == 992){
						locreferralVO = mapRefEntry.getValue();
						if( !Utils.isEmpty( locreferralVO ) ){
							locreferralVO.setPolLinkingId( policyVO.getPolLinkingId() );
						}
					}
	
				}
			}
			return locreferralVO;
		}
	
		/*
		 * This method will return the Renewal related referralVO 
		 */
		public static ReferralVO getRenewalReferralVO( PolicyVO policyVO ){
	
			ReferralVO locreferralVO = null;
			if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
				for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
	
					if( mapRefEntry.getKey().getSectionId() == 104 ){
						locreferralVO = mapRefEntry.getValue();
						if( !Utils.isEmpty( locreferralVO ) ){
							locreferralVO.setPolLinkingId( policyVO.getPolLinkingId() );
							//Added to set policyTypeCode to referralVO - For issue 77934
							locreferralVO.setPolicyTypeCode(policyVO.getPolicyTypeCode());
						}
					}
	
				}
			}
			return locreferralVO;
		}
	
		public static void addEndtTextCshAndCshDetails( Long policyId, PolicyVO policyVO, Long riskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "addCshCshDetails" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), MONEY_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_17" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_17" );
			}
	
		}
	
		public static void updateEndtTextCshAndCshDetails( Long policyId, PolicyVO policyVO, Long riskId, Long basicRiskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "updateCshCshDetails" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), riskId, SvcUtils.getUserId( policyVO ), MONEY_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_18" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_18" );
			}
	
		}
	
		public static void updateDeductibleforendorsementFlow( Long policyId, PolicyVO policyVO, int sectionId, Long riskGroupId, Long  baseRiskGroupId){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endoTextPrmProc" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, riskGroupId, baseRiskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_19" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.endoTextPrmProc.exception", e, "An exception occured while executing stored proc_19" );
			}
	
		}
	
	
		public static void deleteEndtTextCshAndCshDetails( Long policyId, PolicyVO policyVO, Long riskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "deleteCshCshDetails" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), MONEY_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_20" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.deleteCshCshDetails.exception", e, "An exception occured while executing stored proc_20" );
			}
	
		}
	
		/**
		 * Invoke genEndtTextAddMoneyLocation procedure to generate endorsement text
		 * for including new building in money section
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskId
		 */
		public static void genEndtTextAddMoneyLocation( Long policyId, PolicyVO policyVO, Long riskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "genEntTextAddGaccBld" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), MONEY_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_21" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.genEndtTextAddMoneyLocation.exception", e, "An exception occured while executing stored proc_21" );
			}
	
		}
	
		/*
		 * This method takes the EndorsementVO passed and saves the endorsement text
		 * T_TRN_ENDORSEMENT_DETAILS table
		 */
		public static void saveOrUpdateEndorsementText( EndorsmentVO endorsmentVO, HibernateTemplate hbt ){
			if( LOGGER.isInfo() ) LOGGER.info( "Enterning DAOUtils.saveOrUpdateEndorsementText(EndorsmentVO,HibernateTemplate)" );
			List<TTrnEndorsementDetail> endorsementDetailsList = null;
			
			if(!Utils.isEmpty( endorsmentVO.getEndSecId() )){
				endorsementDetailsList = hbt.find( "from TTrnEndorsementDetail where id.edlPolicyId=? and id.edlEndorsementId=? and id.edlEndNo=? and id.edlSerialNo=? and id.edlSecId=?",
						endorsmentVO.getPolicyId(), endorsmentVO.getEndtId(), endorsmentVO.getEndNo() ,endorsmentVO.getSlNo(), endorsmentVO.getEndSecId() );
			}
			else {
				endorsementDetailsList = hbt.find( "from TTrnEndorsementDetail where id.edlPolicyId=? and id.edlEndorsementId=? and id.edlEndNo=? and id.edlSerialNo=?",
						endorsmentVO.getPolicyId(), endorsmentVO.getEndtId(), endorsmentVO.getEndNo() ,endorsmentVO.getSlNo());
			}
			TTrnEndorsementDetail endorsementDetail = null;
			if(!Utils.isEmpty( endorsementDetailsList ))
				endorsementDetail = endorsementDetailsList.get( 0 );
			BaseBeanToBeanMapper<EndorsmentVO, TTrnEndorsementDetail> requestBeanMapper = BeanMapperFactory.getMapperInstance( EndorsementVOToTTrnEndorsementDetails.class );
			endorsementDetail = requestBeanMapper.mapBean( endorsmentVO, endorsementDetail);
			hbt.saveOrUpdate( endorsementDetail );
			hbt.flush();
			hbt.evict( endorsementDetail );
			if( LOGGER.isInfo() ) LOGGER.info( "Endorsement text saved - exiting DAOUtils.saveOrUpdateEndorsementText(EndorsmentVO,HibernateTemplate)" );
		}
		
		
		/* Commented by Vishwa as this method was not used as per SONAR report
		 * 
		 * public static void getEndorsementText( EndorsmentVO endorsmentVO, HibernateTemplate hbt ){
			List<TTrnEndorsementDetail> endorsementDetailsList = null;
			
			String endtSql = " select * from t_trn_endorsement_detail where edl_policy_id = "+endorsmentVO.getPolicyId() +" and edl_endorsement_id = "+endorsmentVO.getEndtId();
	        Session session = hbt.getSessionFactory().getCurrentSession();
	        Query query = session.createSQLQuery(endtSql);
	        List<Object>result = query.list();
				endorsementDetailsList = hbt.find( "from TTrnEndorsementDetail where id.edlPolicyId=? and id.edlEndorsementId=? and id.edlEndNo=?",
						endorsmentVO.getPolicyId(), endorsmentVO.getEndtId(), endorsmentVO.getEndNo()  );
			TTrnEndorsementDetail endorsementDetail = null;
			if(!Utils.isEmpty( endorsementDetailsList ))
				endorsementDetail = endorsementDetailsList.get( 0 );
		
		}
		*/
		
		/*
		 * This method takes the EndorsementVO passed and saves the endorsement text
		 * T_TRN_ENDORSEMENT_DETAILS table
		 */
		public static void saveOrUpdateBulkEndorsementText( com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsmentVOs, HibernateTemplate hbt ){
	
			List<TTrnEndorsementDetail> endorsementDetailsList = null;
			BaseBeanToBeanMapper<EndorsmentVO, TTrnEndorsementDetail> requestBeanMapper = BeanMapperFactory.getMapperInstance( EndorsementVOToTTrnEndorsementDetails.class );
			if( !Utils.isEmpty( endorsmentVOs ) ){
				for( EndorsmentVO endorsmentVO : endorsmentVOs ){
	
					if( isValid( endorsmentVO ) ){
						endorsementDetailsList = hbt.find( "from TTrnEndorsementDetail where id.edlPolicyId=? and id.edlEndorsementId=? and id.edlEndNo=? ",
								endorsmentVO.getPolicyId(), endorsmentVO.getEndtId(), endorsmentVO.getEndNo() );
						break;
					}
				}
			}
	
			if(!Utils.isEmpty( endorsementDetailsList ))
			for( Iterator<TTrnEndorsementDetail> it = endorsementDetailsList.iterator(); it.hasNext(); ){
				TTrnEndorsementDetail endorsementDetail = it.next();
				for( EndorsmentVO endorsmentVO : endorsmentVOs ){
					if( isValid( endorsmentVO ) ){
						if( endorsmentVO.getSlNo().equals( endorsementDetail.getId().getEdlSerialNo() ) && !endorsementDetail.getEdlText().equalsIgnoreCase( endorsmentVO.getEndText() ) ){
							requestBeanMapper.mapBean( endorsmentVO, endorsementDetail );
						}
						/*
						 * else cannot be used since we are looping through endorsmentVOs on TTrnEndorsementDetail List
						 */
						if( endorsmentVO.getSlNo().equals( endorsementDetail.getId().getEdlSerialNo() ) && endorsementDetail.getEdlText().equalsIgnoreCase( endorsmentVO.getEndText() ) ){
							it.remove();
						}
					}
				}
			}
	
			if( !Utils.isEmpty( endorsementDetailsList ) ){
				hbt.saveOrUpdateAll( endorsementDetailsList );
			}
	
		}
	
		private static boolean isValid( EndorsmentVO endorsmentVO ){
			boolean isValid = false;
			if( !Utils.isEmpty( endorsmentVO ) ){
	
				if( !( Utils.isEmpty( endorsmentVO.getSlNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndtId() ) )
						&& !( Utils.isEmpty( endorsmentVO.getPolicyId() ) ) ){
					if( endorsmentVO.getSlNo() != -9999 && endorsmentVO.getEndNo() != -9999 && endorsmentVO.getEndtId() != -9999 && endorsmentVO.getPolicyId() != -9999 ){
						isValid = true;
					}
				}
			}
			return isValid;
		}
	
		/**
		 * Sets Rate Type value to PolicyVO.SchemeVO.tariffRateType from
		 * T_MAS_POLICY_RATING table (this will be basis tariff code and policy type
		 * code - 50)
		 * 
		 * @param baseVO
		 * @return
		 */
		public static void setRateTypeFromRatingTable( HibernateTemplate hibernateTemplate, BaseVO baseVO ){
	
			PolicyVO policyVO = (PolicyVO) baseVO;
	
			List<TMasPolicyRating> resultList = hibernateTemplate.find( TABLE_QUERY_RATE_TYPE, policyVO.getScheme().getTariffCode(), policyVO
					.getPolicyTypeCode().shortValue() );
	
			if( !Utils.isEmpty( resultList ) ){
				policyVO.getScheme().setTariffRateType( resultList.get( SvcConstants.zeroVal ).getPrRateType() );
			}
		}
	
		/**
		 * Returns refundPremiumAmt which is calculated basis below formula (
		 * (currentPremiumAmt * polExpiryDays) - currentPremiumAmt) / totalDays )
		 * where 1. polExpiryDays will be difference in Pol_Expiry_Date and
		 * Pol_Cancellation_Effective_Date(in no of days) 2. totalDays will be 366
		 * in case of LEAP_YEAR else it will be 365
		 * 
		 * @param policyVO
		 * @param currentPremiumAmt
		 * @return
		 */
		/*	public static Double getRefundAmountForCancelPolicy(PolicyVO policyVO,
					Double currentPremiumAmt) {
				long polExpiryDays = PremiumHelper.getDifference(
						policyVO.getPolExpiryDate(), policyVO.getEndEffectiveDate());
				int totalDays = PremiumHelper.isLeapYear(policyVO.getStartDate()) ? SvcConstants.NO_OF_DAYS_LEAP_YEAR
						: SvcConstants.NO_OF_DAYS_YEAR;
	
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Double refundAmount = 0.00;
				if (!sdf.format(policyVO.getStartDate()).equalsIgnoreCase(
						sdf.format(policyVO.getEndEffectiveDate()))) {
					refundAmount = currentPremiumAmt
							- (currentPremiumAmt * polExpiryDays / totalDays);
				}
				return SvcUtils.premiumRoundOff(refundAmount);
			} */
	
		public static void deleteEndtTextPublicLiablity( Long policyId, PolicyVO policyVO, long riskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "premDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), PL_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_22" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_22" );
			}
	
		}
	
		/**
		 * Procedure call to generate the endorsement text for deletion of locations
		 * 
		 * @param cntCategory
		 */
		public static void deleteLocationEndtText( Long policyId, PolicyVO policyVO, Integer SectionId, Long riskGroupId, Integer cntCategory ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endTextLocDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), SectionId, riskGroupId, cntCategory );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_23" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_23" );
			}
	
		}
	
		/*
		 * Calls Procedure to generate endt Text when UW Questions changes made.
		 */
		public static void updateUWQuestions( Long policyId, PolicyVO policyVO, Integer sectionId, Long riskGroupId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endTextUWQuestionsCol" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_24" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.UWEndtTextError.exception", e, "An exception occured while executing stored proc_24" );
			}
	
		}
	
		/*
		 * Calls Procedure to generate endt Text when UW Questions changes made.
		 */
		public static void updateProcLoc( Long policyId, PolicyVO policyVO, Integer sectionId, Long riskGroupId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "procLocModify" );
			try{
				Map results = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( results ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_25" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.ProcLocTextError.exception", e, "An exception occured while executing stored proc_25" );
			}
	
		}
	
		/**
		 * This method is used to get the question list for a specific section.
		 * 
		 * 1. Get the question codes for the section from master table 2. For the
		 * fetched question codes , filter the questions for the section from
		 * questionsQuo list
		 * 
		 * @param questionsQuo
		 * @param sectionIdMb
		 * @param tariffCode
		 * @return List<TTrnUwQuestionsQuo>
		 */
		public static List<TTrnUwQuestionsQuo> getQuestionListForCurrentSection( List<TTrnUwQuestionsQuo> questionsQuo, int sectionIdMb, Integer tariffCode ){
	
			UWQService uwqService = new UWQService();
			UWQInputsVO uwqVO = new UWQInputsVO();
			uwqVO.setSectionId( sectionIdMb );
			uwqVO.setTarCode( tariffCode );
	
			/* 1 */
			UWQuestionsVO questionList = (UWQuestionsVO) uwqService.invokeMethod( "getListOfDescription", uwqVO );
			List<TTrnUwQuestionsQuo> tTrnUwQuestionsQuos = new ArrayList<TTrnUwQuestionsQuo>();
			for( TTrnUwQuestionsQuo question : questionsQuo ){
				if( !Utils.isEmpty( question ) && !Utils.isEmpty( questionList.getQuestions() ) ){
					/* 2 */
					for( UWQuestionVO quesVO : questionList.getQuestions() ){
						if( question.getId().getUqtUwqCode() == quesVO.getQId() ){
							tTrnUwQuestionsQuos.add( question );
							break;
						}
					}
				}
			}
			return tTrnUwQuestionsQuos;
		}
	
		/**
		 * Procedure call to generate endt text during deletion of Risk.
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param sectionId
		 * @param riskGroupId
		 */
		public static void updateEndTextForRiskDel( Long policyId, PolicyVO policyVO, Integer sectionId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endTextRiskDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, null );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_26" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.RiskDelEndtTextError.exception", e, "An exception occured while executing stored proc_26" );
			}
	
		}
	
		public static void updateEndTextForRiskAdd( Long policyId, PolicyVO policyVO, Integer sectionId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endTextRiskAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, null );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_27" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.RiskAddEndtTextError.exception", e, "An exception occured while executing stored proc_27" );
			}
	
		}
	
		/**
		 * Returns java.sql.Timestamp i.e VSD to be used by executing oracle
		 * function which actually checks for T_TRN_CLOSING table records. In case
		 * sysDate parameter passed is on or before clo_closure_date, then SYSDATE
		 * is returned from the function else clo_open_date will be returned which
		 * will be next month's first date
		 * 
		 * @param ht
		 * @param sysDate
		 * @return
		 */
		public static Timestamp getValidityStartDate( HibernateTemplate ht, Timestamp sysDate, Flow flow ){
			if( flow == Flow.AMEND_POL ){
				Session session = ht.getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery( TABLE_QUERY_FETCH_VSD );
				query.setParameter( "sysdt", sysDate );
				query.addScalar( com.Constant.CONST_VSDTIMESTAMP, Hibernate.TIMESTAMP ); /* In order to retrieve SYSDATE as java.sql.Timestamp */
				List<Object> result = query.list();
	
				Timestamp revisedVSD = (Timestamp) result.get( SvcConstants.zeroVal );
				return revisedVSD;
	
			}
			else{
				return sysDate;
			}
		}
		
		/**
		 * Returns java.sql.Timestamp i.e VSD to be used by executing oracle
		 * function which actually checks for T_TRN_CLOSING table records. In case
		 * sysDate parameter passed is on or before clo_closure_date, then SYSDATE
		 * is returned from the function else clo_open_date will be returned which
		 * will be next month's first date
		 * 
		 * @param ht
		 * @param sysDate
		 * @return
		 */
		public static Timestamp getValidityStartDate( HibernateTemplate ht, Timestamp sysDate ){
			//if( flow == Flow.AMEND_POL ){ Commented for Home and Travel so that month end closing date will effect in quote as well.
				Session session = ht.getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery( TABLE_QUERY_FETCH_VSD );
				query.setParameter( "sysdt", sysDate );
				query.addScalar( com.Constant.CONST_VSDTIMESTAMP, Hibernate.TIMESTAMP ); /* In order to retrieve SYSDATE as java.sql.Timestamp */
				List<Object> result = query.list();
	
				Timestamp revisedVSD = (Timestamp) result.get( SvcConstants.zeroVal );
				return revisedVSD;
	
			/*}
			else{
				return sysDate;
			}*/
		}
	
		/**
		 * Updates endt effective date within T_TRN_POLICY table during delete
		 * location/section
		 * 
		 * @param section
		 * @param policyVO
		 * @param ht
		 */
		public static void updatePolEndtDate( SectionVO section, PolicyVO policyVO, HibernateTemplate ht ){
			/*
			 * Get the current Endt_Id-state record, if key is available. That is,
			 * the record with the Endt_Id got from the database function.
			 */
			String stringClass = null;
			stringClass = new String( section.getClassCode().toString() );
			TTrnPolicyQuo existingRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), ht, false,
					(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), policyVO.getPolLinkingId(), Short.parseShort( stringClass ) );
			/*
			 * There is no record for the current (new) Endt_Id. This means that we
			 * will have to create a new record from the previous Endt_Id's record.
			 */
	
			Long polId = getSecPolicyForUpdEndtEffDate( ht, existingRecord, policyVO, section );
	
			if( Utils.isEmpty( existingRecord ) && Utils.isEmpty( polId ) ) return;
			//if( Utils.isEmpty( existingRecord ) && !Utils.isEmpty( polId ) && (policyVO.getAppFlow().equals( Flow.EDIT_QUO)||policyVO.getAppFlow().equals( Flow.CREATE_QUO))) return;
	
			Long prevEndtId = DAOUtils.getPrevEndtId( ht, policyVO.getIsQuote(), polId, (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
	
			TTrnPolicyQuo oldRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), ht, false, prevEndtId,
					policyVO.getPolLinkingId(), Short.parseShort( stringClass ) );
	
			if( !Utils.isEmpty( oldRecord ) ){
	
				if( Utils.isEmpty( existingRecord ) ){
					existingRecord = oldRecord;
				}
	
				/*
				 * In case of endorsement, update the previous record endorsement
				 * expiry date to endorsement effective date
				 */
				if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
					oldRecord.setPolEndtExpiryDate( policyVO.getEndEffectiveDate() );
					try{
						ht.merge( oldRecord );
					}
					catch( Exception e ){
						LOGGER.error( e, "Error when trying to update (merge) entity of type [", oldRecord.getClass().getName(), "]" );
						throw new SystemException( CommonErrorKeys.COULD_NOT_UPDATE_RECORD, e, "Error when trying to update (merge) entity of type [", oldRecord.getClass().getName(),
								"]" );
					}
	
				}
	
			}
	
			TTrnPolicyQuo newRecord = CopyUtils.copySerializableObject( existingRecord );
			newRecord.getId().setPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ); // Jaya
			/*
			 * In case of endorsement, the endt expire date needs to be populated.
			 * First check if the new record is not for end no 0 i,e endID is not 0
			 * then update the endorsement expiry date to policy exp date
			 */
			if( !String.valueOf( newRecord.getId().getPolEndtId() ).equalsIgnoreCase( SvcConstants.FIRST_ENDT ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
				newRecord.setPolEndtExpiryDate( policyVO.getPolExpiryDate() );
			}
	
			/*
			 * update the endt effective date, as the proc update cashpolicy will
			 * just copy the previous record and the PolEndtEffectiveDate is not
			 * set.
			 */
			if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
				newRecord.setPolEndtEffectiveDate( policyVO.getEndEffectiveDate() );
			}
			else{
				newRecord.setPolEndtEffectiveDate( null );
				/*
				 * Set the start date and end date of the quotation.Start date
				 * should be the date the user selects. Hence update the copied
				 * policy record
				 */
				SvcUtils.setStartEndDate( existingRecord, policyVO.getScheme().getEffDate() );
				ht.saveOrUpdate( existingRecord );
			}
			try{
				ht.evict( existingRecord ); /*
											* Evict existingRecord object which is the
											* same object as the one being used to be
											* saved i.e. newRecord object
											*/
				ht.saveOrUpdate( newRecord );
				newRecord.getClass();
			}
			catch( Exception e ){
				LOGGER.error( e, "Error when trying to save/update entity of type [", newRecord.getClass().getName(), "]" );
				throw new SystemException( CommonErrorKeys.COULD_NOT_SAVE_RECORD, e, "Error when trying to save/update entity of type [", newRecord.getClass().getName(), "]" );
			}
			updatePremiumSpecialCover( policyVO, section, newRecord, ht );
		}
	
		private static void updatePremiumSpecialCover( PolicyVO policyVO, SectionVO section, TTrnPolicyQuo newRecord, HibernateTemplate ht ){
	
			if( !Utils.isEmpty( newRecord ) ){
	
				String[] coverCodes = Utils.getMultiValueAppConfig( "SPECIAL_COVER_CODES", "," );
	
				/*
				 * Premium record has to be added to Premium table for each of the
				 * special cover codes.
				 */
				for( String coverCode : coverCodes ){
	
					/*
					 * For each special cover up date the id's of the basic cover
					 */
					TTrnPremiumQuo premiumQuo = SvcUtils.getPremiumQuoSpecialCovers( coverCode, newRecord );
	
					premiumQuo.getId().setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					premiumQuo.setPrmEndtId( newRecord.getId().getPolEndtId() );
					premiumQuo.getId().setPrmPolicyId( newRecord.getId().getPolPolicyId() );
					premiumQuo.setPrmStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
	
					List<TTrnPolicyQuo> existingPolRecs = ht.find( "from TTrnPolicyQuo pol where pol.polLinkingId = ?", newRecord.getPolLinkingId() );
	
					if( !Utils.isEmpty( existingPolRecs ) && existingPolRecs.size() > 0 && !Utils.isEmpty( existingPolRecs.get( 0 ) ) ){
						for( TTrnPolicyQuo existingPolRec : existingPolRecs ){
							/*
							 * if there is an existing record for an endt then no need to insert new record
							 * 
							 * The special covers 101, 100 and 51 should be updated/ created at class code level
							 */
							List<TTrnPremiumQuo> existingRec = ht.find( "from TTrnPremiumQuo prm where prm.prmValidityExpiryDate= ? and prm.id.prmPolicyId = ? "
									+ "and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ? and prm.id.prmCovCode = ? "
									+ "and prm.id.prmCtCode = ? and prm.id.prmCstCode = ? and prm.prmEndtId = ? and prm.prmClCode = ? ", SvcConstants.EXP_DATE, existingPolRec.getId()
									.getPolPolicyId(), premiumQuo.getId().getPrmBasicRskId(), premiumQuo.getId().getPrmRskCode(), premiumQuo.getId().getPrmCovCode(), premiumQuo
									.getId().getPrmCtCode(), premiumQuo.getId().getPrmCstCode(), premiumQuo.getPrmEndtId(), newRecord.getPolClassCode() );
	
							if( !Utils.isEmpty( existingRec ) && existingRec.size() > 0 && !Utils.isEmpty( existingRec.get( SvcConstants.zeroVal ) ) ){
								return;
							}
						}
					}
	
					/*
					 * if cover code is 100 then Fn code should be stored as 2 else
					 * it has to set as 0.
					 */
					/*if( covCode == SvcConstants.SC_PRM_COVER_GOVT_TAX ){
						premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE_FLAT_AMT.byteValue() );
					}
					else{
						premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE.byteValue() );
					}*/
					// FIX as part of back end verification
					premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE_FLAT_AMT.byteValue() );
	
					/*
					 * in case of endorsement only cover code 100 and 101 needs to be inserted, cover code 51 will not change in any no of endorsement  
					 */
					if( policyVO.getAppFlow().equals( Flow.AMEND_POL ) && premiumQuo.getId().getPrmCovCode() != SvcConstants.SC_PRM_COVER_DISCOUNT ){
						ht.saveOrUpdate( premiumQuo );
					}
					else{
						ht.saveOrUpdate( premiumQuo );
					}
	
					//	getHibernateTemplate().flush();
					//	getHibernateTemplate().evict( premiumQuo );
	
				}
			}
	
		}
	
		/**
		 * Returns policyId for a section , this method is used during endorsement
		 * effective date update for delete location.
		 * 
		 * @param ht
		 * @param existingRecord
		 * @param policyVO
		 * @param section
		 * @return
		 */
		private static Long getSecPolicyForUpdEndtEffDate( HibernateTemplate ht, TTrnPolicyQuo existingRecord, PolicyVO policyVO, SectionVO section ){
			Long secPolicyId = null;
	
			if( Utils.isEmpty( existingRecord ) && Utils.isEmpty( section.getPolicyId() ) ){
				try{
					List<TTrnSectionDetailsQuo> secDetailsList = ht
							.find( "from  TTrnSectionDetailsQuo secDt where secDt.id.secSecId = ? and secDt.id.secPolicyId in (select pol.id.polPolicyId from TTrnPolicyQuo pol where pol.polLinkingId = ? ) )",
									section.getSectionId().shortValue(), policyVO.getPolLinkingId() );
	
					if( !Utils.isEmpty( secDetailsList ) ){
						secPolicyId = secDetailsList.get( SvcConstants.zeroVal ).getId().getSecPolicyId();
					}
				}
				catch( Exception ex ){
					LOGGER.error( ex, "Error when trying to select record from TTrnSectionDetails/Quo table" );
					throw new SystemException( CommonErrorKeys.UNKNOWN_ERROR, ex, "Error when trying to select record from TTrnSectionDetails/Quo table" );
				}
	
			}
			else if( !Utils.isEmpty( existingRecord ) ){
				secPolicyId = existingRecord.getId().getPolPolicyId();
			}
			else if( !section.getPolicyId().equals( Long.valueOf( SvcConstants.zeroVal ) ) ){
				secPolicyId = section.getPolicyId();
			}
			return secPolicyId;
		}
	
		/**
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskId
		 */
		public static void genEndtTextWCContentAdd( Long policyId, PolicyVO policyVO, Long riskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcCntAddEndtTxtProc" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_28" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.wcCntAddEndtTxtProc.exception", e, "An exception occured while executing stored proc_28" );
			}
	
		}
	
		/**
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskId
		 */
		public static void genEndtTextWCContentDel( Long policyId, PolicyVO policyVO, Long riskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcCntDelEndtTxtProc" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, riskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_29" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.wcCntDelEndtTxtProc.exception", e, "An exception occured while executing stored proc_29" );
			}
	
		}
	
		/**
		 * 
		  * @param policyVO
		 * @param hibernateTemplate 
		 * @return
		 */
		public static Long getBaseSectionPolicyId( PolicyVO policyVO, HibernateTemplate hibernateTemplate ){
	
			Long endtId = SvcUtils.getLatestEndtId( policyVO );
	
			String sqlQuery = "";
	
			if( policyVO.getIsQuote() ){
				sqlQuery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_BASE_SEC_POL_ID_QUO_END + policyVO.getPolLinkingId() + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			}
			else{
				sqlQuery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_BASE_SEC_POL_ID_END + policyVO.getPolLinkingId() + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			}
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			Long policyId = null;
			Query query = session.createSQLQuery( sqlQuery );
			session.flush();
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				policyId = Long.valueOf( resultsFunc.get( 0 ).toString() );
				if( Utils.isEmpty( policyId ) ){
					throw new BusinessException( "cmn.unknownError", null, "The policy no is 0 or null for inserting into endorsment details table_1" );
				}
			}
			return policyId;
		}
	
		/**
		 * 
		 * @param policyVO
		 * @param hibernateTemplate
		 * @return
		 */
		public static Long getSectionPolicyId( PolicyVO policyVO, int sectionId, HibernateTemplate hibernateTemplate ){
			String sqlQuery = "";
			if( policyVO.getIsQuote() ){
				sqlQuery = "SELECT PKG_PAS_UTILS.get_pol_id_quo(" + policyVO.getPolLinkingId() + "," + sectionId + com.Constant.CONST_FROM_DUAL2;
			}
			else{
				sqlQuery = "SELECT PKG_ENDT_GEN.get_pol_id(" + policyVO.getPolLinkingId() + "," + sectionId + com.Constant.CONST_FROM_DUAL2;
			}
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			Long policyId = null;
			Query query = session.createSQLQuery( sqlQuery );
	
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				policyId = Long.valueOf( resultsFunc.get( 0 ).toString() );
				if( Utils.isEmpty( policyId ) ){
					throw new BusinessException( "cmn.unknownError", null, "The basicRskCode null in getSectionPolicyId " );
				}
			}
			return policyId;
		}
	
		public static Long getSectionPolicyId( Long linkingID, int sectionId, boolean isQuote, HibernateTemplate hibernateTemplate ){
	
			PolicyVO policyVO = new PolicyVO();
			policyVO.setPolLinkingId( linkingID );
			policyVO.setIsQuote( isQuote );
			return getSectionPolicyId( policyVO, sectionId, hibernateTemplate );
		}
	
		/*
		 * Update cash customer table
		 */
		public static void updateCustomerDetails( PolicyVO policyVO, SectionVO section, HibernateTemplate hibernateTemplate ){
	
			Long prevEndtId = DAOUtils.getPrevEndtId( hibernateTemplate, policyVO.getIsQuote(), section.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
	
			/*
			 * fetch the previous record and update the data from policyVO
			 */
			List<TMasCashCustomerQuo> existingRecList = hibernateTemplate.find( "from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? "
					+ "and cashCustomerQuo.id.cshValidityStartDate = ( select pol.polValidityStartDate from TTrnPolicyQuo pol where pol.id.polPolicyId = ? and pol.id.polEndtId = ? )",
					section.getPolicyId(), section.getPolicyId(), prevEndtId );
	
			if( Utils.isEmpty( existingRecList ) || ( !Utils.isEmpty( existingRecList ) && existingRecList.size() == 0 ) ) return;
	
			TMasCashCustomerQuo existingRec = existingRecList.get( 0 );
	
			BaseBeanToBeanMapper<PolicyVO, TMasCashCustomerQuo> policyVoCashCustomerMapper = BeanMapperFactory
					.getMapperInstance( PolicyVOToCashCustomerPOJO.class );
			TMasCashCustomerQuo mappedRec = policyVoCashCustomerMapper.mapBean( policyVO, CopyUtils.copySerializableObject( existingRec ) );
	
			// AdverNet:: Issue: 109230 ,CR: 124044 for customer id update
			if( !Utils.isEmpty( existingRec ) ){			
				/*In case of endorsement, update the previous record endorsement expiry date to endorsement effective 
				 date */
				if( policyVO.getAppFlow().equals( Flow.EDIT_QUO) || policyVO.getAppFlow().equals( Flow.AMEND_POL)){
					existingRec.setCshValidityExpiryDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					hibernateTemplate.saveOrUpdate( existingRec );
				}		
			}
		
			hibernateTemplate.flush();
			hibernateTemplate.evict( existingRec );
			/*
			 * The previous records ID is update to the current ID so that the ney created record will have the latest data entered by user on the screen
			 */
			mappedRec.setId( new TMasCashCustomerQuoId() );
			mappedRec.getId().setCshPolicyId( section.getPolicyId() );
			mappedRec.getId().setCshValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			mappedRec.setCshEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			mappedRec.setCshModifiedBy( SvcUtils.getUserId( policyVO ) );
			mappedRec.setCshModifiedDt( new Date() );
			mappedRec.setValidityExpiryDate( SvcConstants.EXP_DATE );
			/* 
			 * AdverNet:: Issue: 109230 ,CR: 124044
			 * Change of Broker on Record ,customer id  should be updated on table t_trn_policy_quo, t_mas_cash_customer_quo
			 */
			Long customerId = DAOUtils.getCustoemrId(hibernateTemplate, policyVO.getScheme().getSchemeCode());		
			mappedRec.setCshCustomerId(Utils.isEmpty( customerId ) ? null : customerId);
			
			hibernateTemplate.saveOrUpdate( mappedRec );
			hibernateTemplate.flush();
			hibernateTemplate.evict( mappedRec );
	
		}
	
		/*
		 * Update modified date and modified by in cash customer table
		 */
		public static void updateCustomerAuthDetails( PolicyVO policyVO, SectionVO section, HibernateTemplate hibernateTemplate ){
	
			Long endtId = DAOUtils.getEndtToProcess( hibernateTemplate, policyVO );
			/*
			 * fetch the current record and update the data from policyVO
			 */
			List<TMasCashCustomerQuo> existingRecList = hibernateTemplate.find( "from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? "
					+ "and cashCustomerQuo.id.cshValidityStartDate = ( select pol.polValidityStartDate from TTrnPolicyQuo pol where pol.id.polPolicyId = ? and pol.id.polEndtId = ? )",
					section.getPolicyId(), section.getPolicyId(), endtId );
	
			if( Utils.isEmpty( existingRecList ) || ( !Utils.isEmpty( existingRecList ) && existingRecList.size() == 0 ) ) return;
	
			if( existingRecList.size() > 1 ){
				throw new BusinessException( "cmn.unknownError", null, "Cash customer details table have more than one record for PolicyId and endt Id combination." );
			}
	
			TMasCashCustomerQuo existingRec = existingRecList.get( 0 );
	
			/*
			 * The previous records ID is update to the current ID so that the ney created record will have the latest data entered by user on the screen
			 */
			existingRec.setCshModifiedBy( SvcUtils.getUserId( policyVO ) );
			existingRec.setCshModifiedDt( new Date() );
			hibernateTemplate.merge( existingRec );
			hibernateTemplate.flush();
			hibernateTemplate.evict( existingRec );
		}
	
		/**
		 * 
		 * @param hibernateTemplate
		 * @param policyId
		 * @param endId
		 * @param lei
		 * @return
		 * Method to fetch validity start date for the passed endt Id and linking id
		 */
		public static Date getValidityStartDate( HibernateTemplate hibernateTemplate, Long endId, Long linkingID, boolean isQuote ){
	
			String sqlQuery = "select max(pol_validity_start_date) as vsdTimeStamp from " + ( isQuote ? com.Constant.CONST_T_TRN_POLICY_QUO : com.Constant.CONST_T_TRN_POLICY )
					+ " where pol_linking_id = :pol_linking_id and pol_endt_id <= :pol_endt_id ";
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			query.setParameter( "pol_linking_id", linkingID );
			query.setParameter( "pol_endt_id", endId );
			query.addScalar( com.Constant.CONST_VSDTIMESTAMP, Hibernate.TIMESTAMP );
	
			Date valStartDate = null;
			List<Object> resultsFunc = query.list();
	
			if( !Utils.isEmpty( resultsFunc ) ){
	
				valStartDate = (Date) ( resultsFunc.get( 0 ) );
				if( Utils.isEmpty( valStartDate ) ){
					throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_1" );
				}
			}
	
			return valStartDate;
		}
	
		public static void deleteOnlyRiskEntry( Long polLinkingId, SectionVO sectionVO, HibernateTemplate ht ){
			
			String sqlQuery = "DELETE FROM T_TRN_POLICY_SECTIONS_QUO WHERE TPS_LINKING_ID = " + polLinkingId + "  AND TPS_SEC_ID =" + sectionVO.getSectionId();
			Session session = ht.getSessionFactory().getCurrentSession();
			Long policyId = null;
			Query query = session.createSQLQuery( sqlQuery );
			query.executeUpdate();
			
		}
		/*
		 *  Added for Ticket Id :83464 Policy Number blank issue
		 *  if a section is selected and deselected in the same SESSION, only entry in t_trn_policy_sections_quo is deleted and return
		 */
		public static void deletePolicyRiskEntry( Long polLinkingId, SectionVO sectionVO, HibernateTemplate ht ){
			String sqlQuery = "DELETE FROM T_TRN_POLICY_QUO WHERE POL_LINKING_ID IN (SELECT TPS_LINKING_ID from T_TRN_POLICY_SECTIONS_QUO where TPS_LINKING_ID= " + polLinkingId + "  AND TPS_SEC_ID =" + sectionVO.getSectionId()+") AND POL_CLASS_CODE= "+sectionVO.getClassCode();
			Session session = ht.getSessionFactory().getCurrentSession();
			Long policyId = null;
			Query query = session.createSQLQuery( sqlQuery );
			query.executeUpdate();
			deleteOnlyRiskEntry(polLinkingId, sectionVO, ht);
		}
		/**
		 * Returns the saved commission against the policy for the section.
		 * @param policyId
		 * @param endId 
		 * @return
		 */
		public static Double getCommission( Long policyId, Long endId, Integer sectionId, Flow appFlow, HibernateTemplate ht ){
			Double commission = null;
			if( !Utils.isEmpty( policyId ) && !Utils.isEmpty( endId ) && !Utils.isEmpty( sectionId ) ){
				List<TTrnSectionDetailsQuo> commDetails = ht.find(
						"from TTrnSectionDetailsQuo section where  section.id.secPolicyId = ? and section.id.secSecId = ? and section.secValidityExpiryDate = ? ", policyId,
						sectionId.shortValue(), SvcConstants.EXP_DATE );
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
				if( !Utils.isEmpty( commDetails ) && commDetails.size() > 0 && !Utils.isEmpty( commDetails.get( 0 ) ) ){
					commission = converter.getTypeOfB().cast( converter.getBFromA( commDetails.get( 0 ).getSecCommVal() ) );
				}
				if( appFlow == Flow.AMEND_POL ){
					if( Utils.isEmpty( commission ) ){
						commDetails = ht.find(
								"from TTrnSectionDetailsQuo section where  section.id.secPolicyId = ? and section.id.secSecId = ? and section.secValidityExpiryDate = ? ", policyId,
								sectionId.shortValue(), SvcConstants.EXP_DATE );
						converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
						if( !Utils.isEmpty( commDetails ) && commDetails.size() > 0 && !Utils.isEmpty( commDetails.get( 0 ) ) ){
							commission = converter.getTypeOfB().cast( converter.getBFromA( commDetails.get( 0 ).getSecCommVal() ) );
						}
					}
				}
			}
	
			return commission;
		}
	
		public static String getBuildingName( Long linkingId, Long endtId, Long locId, boolean isQuote, HibernateTemplate ht ){
	
			String sqlQuery = "SELECT PKG_PAS_UTILS.GET_BULD_NAME(" + linkingId + "," + endtId + "," + locId + "," + "'" + SvcUtils.getQuoteOrPloicyFlag( isQuote ) + "'"
					+ com.Constant.CONST_FROM_DUAL2;
			String bldName = "";
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
	
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				bldName = resultsFunc.get( 0 ).toString();
	
			}
			return bldName;
		}
	
	//	Method commented. Should not be used as it was returning correct value for Travel baggage. 
	//	public static Integer getBasicRskCode( Long policyId, Long endtIdToProcess, BigDecimal basicRskId, boolean isQuote, HibernateTemplate ht ){
	//
	//		String sqlQuery = "select distinct (prm_basic_rsk_code) from " + ( isQuote ? "t_trn_premium_quo" : "t_trn_premium" ) + " where prm_policy_id = " + policyId
	//				+ " and prm_basic_rsk_id = " + basicRskId;
	//		Session session = ht.getSessionFactory().getCurrentSession();
	//		Query query = session.createSQLQuery( sqlQuery );
	//		Integer basicRskCode = null;
	//		List<Object> resultsFunc = query.list();
	//		if( !Utils.isEmpty( resultsFunc ) ){
	//			basicRskCode = Integer.valueOf( resultsFunc.get( 0 ).toString() );
	//			if( Utils.isEmpty( policyId ) ){
	//				throw new BusinessException( com.Constant.CONST_CMN_UNKNOWNERROR, null, "The basicRskCode null in getBasicRskCode " );
	//			}
	//		}
	//		return basicRskCode;
	//		
	//	}
	
		/**
		 * 
		 * @param ht
		 * @param endtId
		 * @param linkingId
		 * @return
		 */
		public static List<TTrnPolicyQuo> getPolicyRecord( HibernateTemplate ht, Long endtId, Long linkingId ){
	
			List<TTrnPolicyQuo> policy = null;
			/*
			 * fetch all valid policy records for nil endt.
			 */
			policy = ht.find( "from TTrnPolicyQuo where polLinkingId = ? and id.polEndtId<=? and polValidityExpiryDate = ? order by polClassCode", linkingId, endtId, SvcConstants.EXP_DATE );
			return policy;
	
		}
	
		/**
		 * 
		 * @param validityStartDate
		 * @param policyId
		 * @param endId
		 * @param riskGroup
		 * @param lei
		 * @return
		 */
		public static List<TTrnUwQuestionsQuo> getEndtStateUWQ( HibernateTemplate ht, Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei ){
			List<TTrnUwQuestionsQuo> questionsQuo = null;
			List<TTrnUwQuestionsQuo> questionsQuoForCurrSection = new ArrayList<TTrnUwQuestionsQuo>();
			if( !Utils.isEmpty( validityStartDate ) ){
	
				if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
					questionsQuo = ht.find( "from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? "
							+ "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityStartDate <= ? and tUwqs.uqtValidityExpiryDate > ? and " + " tUwqs.id.uqtPolEndtId = ?"+" order by tUwqs.id.uqtUwqCode",
							Long.valueOf( policyId ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), validityStartDate, validityStartDate, endId );
				}
				else{
	
					questionsQuo = ht.find( "from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? "
							+ "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityStartDate <= ? and tUwqs.uqtValidityExpiryDate > ? and "
							+ "tUwqs.id.uqtPolEndtId <= ? and  tUwqs.uqtStatus <> 4"+" order by tUwqs.id.uqtUwqCode",Long.valueOf( policyId ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),
							validityStartDate, validityStartDate, endId );
				}
	
				List<Short> uwqsForCurrSection = ht.find( "select tMas.uwqCode from TMasUwQuestions tMas where tMas.uwqSecId = ? ", lei.getSectionId().shortValue() );
				for( TTrnUwQuestionsQuo tTrnUwqQuo : questionsQuo ){
					if( uwqsForCurrSection.contains( tTrnUwqQuo.getId().getUqtUwqCode() ) ){
						questionsQuoForCurrSection.add( tTrnUwqQuo );
					}
				}
			}
			return questionsQuoForCurrSection;
		}
	
		public static List<TTrnPolicyQuo> getPolRecForQuo( HibernateTemplate ht, Long endtId, Long quotationNo ){
	
			List<TTrnPolicyQuo> policy = null;
			policy = ht.find( "from TTrnPolicyQuo pol where  pol.id.polEndtId=? and pol.polQuotationNo = ?", endtId, quotationNo );
			return policy;
	
		}
	
		public static Long getEndtToProcess( HibernateTemplate ht, PolicyVO policyVO ){
	
			Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
	
			if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
	
				String sqlQuery = "select max (pol_endt_id) from " + ( policyVO.getIsQuote() ? com.Constant.CONST_T_TRN_POLICY_QUO : com.Constant.CONST_T_TRN_POLICY ) + " where pol_linking_id = "
						+ policyVO.getPolLinkingId() + " and pol_endt_id in (" + policyVO.getEndtId() + "," + policyVO.getNewEndtId() + ")";
				Session session = ht.getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery( sqlQuery );
				List<Object> result = query.list();
				if( Utils.isEmpty( result ) ){
	
					throw new BusinessException( "cmn.unknownError", null, "For linking id_1" + policyVO.getPolLinkingId() + " Endorsment id " + policyVO.getNewEndtId() + " or "
							+ policyVO.getEndtId() + "is unavilable in policy table table" );
				}
				endtIdToProcess = ( (BigDecimal) result.get( 0 ) ).longValue();
			}
	
			return endtIdToProcess;
		}
	
	public static Long getMaxPolicyEndtIdFromLinkingId( HibernateTemplate ht, Long linkingId ){
	
			Long endtIdToProcess = 0L;
	
			if( !Utils.isEmpty(linkingId ) ){
	
				String sqlQuery = "select max (pol_endt_id) from t_trn_policy_quo where pol_linking_id = "
						+ linkingId;
				Session session = ht.getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery( sqlQuery );
				List<Object> result = query.list();
				if( Utils.isEmpty( result ) ){
					throw new BusinessException( "cmn.unknownError", null, "For linking id_2" + linkingId + " records are unavilable in policy table" );
				}
				endtIdToProcess = ( (BigDecimal) result.get( 0 ) ).longValue();
			}
	
			return endtIdToProcess;
		}
		/**
		 * This method is used to get OccupancyTradeCode
		 * @param hibernateTemplate
		 * @param policyVO
		 * @param sectionId
		 * @param minValue
		 * @param criteriaCode
		 * @return BigDecimal
		 */
		public static BigDecimal getOccupancyTradeCode( HibernateTemplate hibernateTemplate, PolicyVO policyVO, int sectionId, BigDecimal minValue, Integer criteriaCode ){
	
			String sqlQuery = "select pcr_max_val from T_MAS_PRODUCT_CRITERIA WHERE pcr_cri_code=" + criteriaCode + " and pcr_min_val=" + minValue + " " + "and PCR_SCH_CODE="
					+ policyVO.getScheme().getSchemeCode() + " and PCR_TAR_CODE=" + policyVO.getScheme().getTariffCode();
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			BigDecimal occupancyTradeCode = null;
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				occupancyTradeCode = (BigDecimal) ( resultsFunc.get( 0 ) );
			}
			return occupancyTradeCode;
		}
		
		public static BigDecimal getOccupancyTradeCode( SchemeVO schemeVO, BigDecimal minValue, Integer criteriaCode ){
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			String sqlQuery = "select pcr_max_val from T_MAS_PRODUCT_CRITERIA WHERE pcr_cri_code=" + criteriaCode + " and pcr_min_val=" + minValue + " " + "and PCR_SCH_CODE="
					+ schemeVO.getSchemeCode() + " and PCR_TAR_CODE=" + schemeVO.getTariffCode();
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			BigDecimal occupancyTradeCode = null;
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				occupancyTradeCode = (BigDecimal) ( resultsFunc.get( 0 ) );
			}
			return occupancyTradeCode;
		}
	
		public static Short getSectionIdForPolicyId( HibernateTemplate hibernateTemplate, short classCode, Long policyId, Boolean isQuote ){
			Short secId = null;
	
			if( !Utils.isEmpty( policyId ) && !Utils.isEmpty( classCode ) ){
				String sqlQuery = "select distinct(sec_sec_id) from " + ( isQuote ? "t_trn_section_details_quo" : "t_trn_section_details" ) + " where sec_policy_id = " + policyId
						+ " and sec_cl_code = " + classCode;
				Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery( sqlQuery );
	
				List<Object> resultsFunc = query.list();
				if( !Utils.isEmpty( resultsFunc ) ){
					secId = Short.valueOf( resultsFunc.get( 0 ).toString() );
					if( Utils.isEmpty( secId ) ){
						throw new BusinessException( "cmn.unknownError", null, "The basicRskCode null in getBasicRskCode " );
					}
				}
			}
			else{
				throw new BusinessException( "cmn.unknownError", null, "The policy Id null in getSectionIdForPolicyId " );
			}
			return secId;
	
		}
	
		public static Short getClassCodeForPolicyId( HibernateTemplate ht, PolicyVO policyVO, SectionVO sectionVO ){
	
			short classCode = 2;
	
			//if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
	
			/* To obtain a class code for a policy id we need not put endt id criteria */
			/*
			String sqlQuery = "select max (pol_class_code) from " + ( policyVO.getIsQuote() ? com.Constant.CONST_T_TRN_POLICY_QUO : com.Constant.CONST_T_TRN_POLICY ) + " where pol_linking_id = "
						+ policyVO.getPolLinkingId() + " and pol_endt_id in (" + policyVO.getEndtId() + "," + policyVO.getNewEndtId() + ") and pol_policy_id = " + sectionVO.getPolicyId();
			*/
			String sqlQuery = "select pol_class_code from " + ( policyVO.getIsQuote() ? com.Constant.CONST_T_TRN_POLICY_QUO : com.Constant.CONST_T_TRN_POLICY ) + " where pol_linking_id = " + policyVO.getPolLinkingId()
					+ " and pol_policy_id = " + sectionVO.getPolicyId();
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			List<Object> result = query.list();
			if( Utils.isEmpty( result ) ){
	
				throw new BusinessException( "cmn.unknownError", null, "For linking id_3" + policyVO.getPolLinkingId() + " Endorsment id " + policyVO.getNewEndtId() + " or "
						+ policyVO.getEndtId() + "is unavilable in policy table table" );
			}
			classCode = ( (BigDecimal) result.get( 0 ) ).shortValue();
			//}
	
			return classCode;
		}
	
		public static Long getBaseSectionPolicyId( Long polLinkingId, Long latestEndtId, Boolean isQuote, HibernateTemplate ht ){
			PolicyVO policyVO = new PolicyVO();
			policyVO.setPolLinkingId( polLinkingId );
			policyVO.setEndtId( latestEndtId );
			policyVO.setNewEndtId( latestEndtId );
			policyVO.setIsQuote( isQuote );
			return getBaseSectionPolicyId( policyVO, ht );
		}
	
		/**
		 * 
		 * @param policyVO
		 * @param classCode
		 * @param ht
		 */
		public static Boolean validateVSD( PolicyVO policyVO, HibernateTemplate ht ){
			List<TTrnPolicyQuo> policyQuoList = ht.find(
					"from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId = ? and policyQuo.polValidityStartDate > ? and policyQuo.polPolicyType = ? order by polEndtNo desc",
					policyVO.getPolLinkingId(), ( ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) ), Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) );
	
			if( !Utils.isEmpty( policyQuoList ) && policyQuoList.size() > SvcConstants.zeroVal ){
				return true;
			}
			return false;
		}
		
		public static Boolean validateVSD( CommonVO commonVO, HibernateTemplate ht ){
			List<TTrnPolicyQuo> policyQuoList = ht.find(
					"from TTrnPolicyQuo policyQuo where policyQuo.id.polPolicyId = ? and policyQuo.polValidityStartDate > ? order by polEndtNo desc",
					commonVO.getPolicyId(), ( ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) ) );
	
			if( !Utils.isEmpty( policyQuoList ) && policyQuoList.size() > SvcConstants.zeroVal ){
				 ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, policyQuoList.get( 0 ).getPolValidityStartDate() ) ;
				 return true;
			}
			return false;
		}
	
		public static void addBICLSforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCLSAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), BI_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_30" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_30" );
			}
	
		}
	
		public static void addBICWSforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCWSAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), BI_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_31" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_31" );
			}
	
		}
	
		public static void deleteBICLSforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCLSDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), BI_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_32" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_32" );
			}
	
		}
	
		public static void deleteBICWSforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCWSDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), BI_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_33" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_33" );
			}
	
		}
	
		public static void updateBICLSforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long basicRiskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCLSModi" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), basicRiskId, SvcUtils.getUserId( policyVO ), BI_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_34" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_34" );
			}
	
		}
	
		public static void updateBICWSforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long basicRiskId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "biCWSModi" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), basicRiskId, SvcUtils.getUserId( policyVO ), BI_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_35" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_35" );
			}
	
		}
	
		public static Integer getContentCategory( Integer sectionId ){
	
			if( sectionId.equals( EE_SECTION_ID ) ){
				return Integer.valueOf( Utils.getSingleValueAppConfig( "EE_RISK_TYPE_CODE" ) );
			}
	
			else if( sectionId.equals( MB_SECTION_ID ) ){
				return Integer.valueOf( Utils.getSingleValueAppConfig( "MB_RISK_TYPE" ) );
			}
	
			return null;
	
		}
	
		public static void addGITMDforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMDAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_36" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.Pro_Endt_Text_Md_Add.exception", e, "An exception occured while executing stored proc_36" );
			}
	
		}
	
		public static void addGITMHforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMHAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_37" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_mh_add.exception", e, "An exception occured while executing stored proc_37" );
			}
	
		}
	
		public static void addGITMTforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMTAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_38" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_mt_add.exception", e, "An exception occured while executing stored proc_38" );
			}
	
		}
	
		public static void deleteGITMDforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMDDelete" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_39" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_md_del.exception", e, "An exception occured while executing stored proc_39" );
			}
	
		}
	
		public static void deleteGITMHforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMHDelete" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_40" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_mh_del.exception", e, "An exception occured while executing stored proc_40" );
			}
	
		}
	
		public static void deleteGITMTforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMTDelete" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_41" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_mt_del.exception", e, "An exception occured while executing stored proc_41" );
			}
		}
	
		public static void updateGITMDforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long declarationId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMDModify" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), declarationId, SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_42" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.Pro_Endt_Text_md_Col.exception", e, "An exception occured while executing stored proc_42" );
			}
	
		}
	
		public static void updateGITMHforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long declarationId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMHModify" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), declarationId, SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_43" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.Pro_Endt_Text_mh_Col.exception", e, "An exception occured while executing stored proc_43" );
			}
	
		}
	
		public static void updateGITMTforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long declarationId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gitMTModify" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), declarationId, SvcUtils.getUserId( policyVO ), GIT_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_44" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.Pro_Endt_Text_mt_Col .exception", e, "An exception occured while executing stored proc_44" );
			}
	
		}
	
		// GPA Related Endorsement changes
	
		// GPA Related Endorsement changes
	
		public static void addGPAUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Integer section_Id, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaUnnamedAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), section_Id, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_45" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_45" );
			}
	
		}
	
		public static void addGPANamedforendorsementFlow( Long policyId, PolicyVO policyVO, Integer section_Id, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaNamedAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), section_Id, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_46" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_46" );
			}
	
		}
	
		public static void deleteGPAUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Integer section_Id, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaUnnamedDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), section_Id, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_47" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_47" );
			}
	
		}
	
		public static void deleteGPANamedforendorsementFlow( Long policyId, PolicyVO policyVO, Integer section_Id, Long riskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaNamedDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), section_Id, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_48" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_48" );
			}
	
		}
	
		public static void updateGPAUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long locationId, Long basicRiskId, Integer section_Id, Long GupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaUnnamedMod" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), GupId, SvcUtils.getUserId( policyVO ), section_Id, locationId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_49" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_49" );
			}
	
		}
	
		public static void updateGPANamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long locationId, Long basicRiskId, Integer section_Id, Long GprId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "gpaNamedMod" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), GprId, SvcUtils.getUserId( policyVO ), section_Id, locationId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_50" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_50" );
			}
	
		}
	
		// Fidelity endorsement text coding starts here , these methods are made as
		// generic , can be used for applicable sections
		public static void addUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "unnamedAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(),
	
				SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_51" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_51" );
			}
	
		}
	
		public static void addNamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "namedAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(),
	
				SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_52" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_52" );
			}
	
		}
	
		public static void deleteUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "unnamedDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(),
	
				SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_53" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_53" );
			}
	
		}
	
		public static void deleteNamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "namedDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(),
	
				SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_54" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_54" );
			}
	
		}
	
		public static void updateUnnamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long GupId, int sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "unnamedMod" );
			try{
	
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), GupId, SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_55" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_55" );
			}
	
		}
	
		public static void updateNamedforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long GprId, Integer sectionId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "namedMod" );
			try{
	
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), GprId, SvcUtils.getUserId( policyVO ), sectionId, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_56" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_56" );
			}
	
		}
	
		public static void updateContforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Integer sectionId, Integer cntCategory ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "modifyCnt" );
	
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), riskGroupId, SvcUtils.getUserId( policyVO ), sectionId, riskGroupId, cntCategory );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_57" );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_57" );
			}
		}
	
		/*
		 * To check whether it is a renewal quote.
		 */
		public static boolean isRenewalQuote( Long polLinkingId, HibernateTemplate hibernateTemplate ){
			TTrnPolicyQuo policy = null;
			boolean renQuote = false;
			policy = (TTrnPolicyQuo) hibernateTemplate.find( "from TTrnPolicyQuo where polLinkingId = ?", polLinkingId ).get( 0 );
			if( policy.getPolDocumentCode() == REN_DOC_CODE ){
				renQuote = true;
			}
			return renQuote;
		}
	
		/**
		 * Sync up endtId within PolicyVO i.e. updates endtId passed to PolicyVO.endtId and PolicyVO.newEndtId fields
		 * @param policyVO
		 * @param endtId
		 */
		public static void syncUpEndtIdPolicyVO( PolicyVO policyVO, Long endtId ){
			policyVO.setEndtId( endtId );
			policyVO.setNewEndtId( endtId );
		}
	
		public static TMasInsured fetchTmasInsured( Long InsuredCode, HibernateTemplate ht ){
	
			TMasInsured tMasInsured = null;
			try{
				tMasInsured = (TMasInsured) ht.find( "from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ", InsuredCode ).get( 0 );
			}
			catch( HibernateException hibernateException ){
				throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while fetching customer details from T_MAS_INSURED" );
			}
			return tMasInsured;
		}
	
		public static void saveTradeLicNo( PolicyVO policyVO, HibernateTemplate ht ){
	
			/* GeneralInfoSaveDao could have been used to do this but this method is used only because on click of convert to policy also the Trade license number
			 *  has to be saved in DB. If we use GeneralInfoSaveDao then it will version the record and we will have pending records in TTrnPolicyQuo table while
			 *  converting to policy. */
			//Boolean hasTradeLicNoChanged = Boolean.FALSE;
			String tradeLicNo = policyVO.getGeneralInfo().getInsured().getTradeLicenseNo();
			Long InsuredId = policyVO.getGeneralInfo().getInsured().getInsuredId();
			TMasInsured tMasInsured = fetchTmasInsured( InsuredId, ht );
			Long endtId = getEndtToProcess( ht, policyVO );
	
			if( !Utils.isEmpty( tMasInsured ) ){
				if( ( !Utils.isEmpty( tradeLicNo ) && !tradeLicNo.equals( tMasInsured.getInsECoRegnNo() ) )
						|| ( Utils.isEmpty( tradeLicNo ) && !Utils.isEmpty( tMasInsured.getInsECoRegnNo() ) ) ){
					//hasTradeLicNoChanged = Boolean.TRUE;
					tMasInsured.setInsECoRegnNo( tradeLicNo );
					ht.saveOrUpdate( tMasInsured );
				}
			}
	
			if( !Utils.isEmpty( tradeLicNo ) ){
				updateCashCustomer( ht, endtId, policyVO );
			}
	
			if( policyVO.getAppFlow() == Flow.AMEND_POL ){
	
				List<TTrnPolicyQuo> policyRecs = getPolicyRecord( ht, endtId, policyVO.getPolLinkingId() );
				for( TTrnPolicyQuo policyQuo : policyRecs ){
					if( policyQuo.getPolClassCode() == 2 || policyQuo.getPolClassCode() == 7 ){
	
						deletePrevEndtText( policyQuo.getId().getPolPolicyId(), endtId, GEN_SECTION_ID, GEN_SEC_LOC_ID );
						System.out.println( "call pro_endt_text_cash_cust_col" );
						updateEndTextForGenInfo( policyQuo.getId().getPolPolicyId(), policyQuo.getPolInsuredId(), policyVO );
						/**
						 * Oman multi branching: For Oman there are  no trade license no.  present.
						 */
						System.out.println( "call UW changes change endo SP" );
						updateUWQuestions( policyQuo.getId().getPolPolicyId(), policyVO, GEN_SECTION_ID, GEN_SEC_LOC_ID );
						updateProcLoc( policyQuo.getId().getPolPolicyId(), policyVO, GEN_SECTION_ID, GEN_SEC_LOC_ID );
						break; //So that endorsement text should be updated only once.
					}
				}
				ht.evict( policyRecs );
			}
	
			ht.flush();
			ht.evict( tMasInsured );
		}
	
		private static void updateCashCustomer( HibernateTemplate ht, Long endtId, PolicyVO policyVO ){
	
			String cashCustomerTable = policyVO.getIsQuote() ? CASH_CUSTOMER_QUO : CASH_CUSTOMER;
			String policyQuoTable = policyVO.getIsQuote() ? POLICY_QUO : POLICY;
	
			String sqlQuery = "update " + cashCustomerTable + " set csh_e_co_regn_no = '" + policyVO.getGeneralInfo().getInsured().getTradeLicenseNo()
					+ "' where csh_policy_id in ( select pol_policy_id from " + policyQuoTable + " polQuo where polQuo.pol_linking_id = " + policyVO.getPolLinkingId()
					+ " and polQuo.pol_validity_expiry_date = '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' and polquo.pol_policy_type="
					+ Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) + " and polQuo.pol_issue_hour = " + Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_ISSUE_HOUR )
					+ " ) and csh_validity_expiry_date= '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' ";
	
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			query.executeUpdate();
	
		}
	
		/**
		 * @param questionsVO
		 * @param policyId
		 * @param endtId
		 * @param validityStartDate
		 * @param hibernateTemplate
		 */
		public static void saveOrUpdateUWQS( UWQuestionsVO questionsVO, Long policyId, Long endtId, Date validityStartDate, HibernateTemplate hibernateTemplate ){
	
			List<UWQuestionVO> questionList = questionsVO.getQuestions();
	
			for( UWQuestionVO questionVO : questionList ){
	
				TTrnUwQuestionsQuo uwQuestionsQuo = SvcUtils.getUWAPojo( questionVO, policyId, endtId, validityStartDate );
				if( !Utils.isEmpty( hibernateTemplate.get( TTrnUwQuestionsQuo.class, uwQuestionsQuo.getId() ) ) ){
					hibernateTemplate.merge( uwQuestionsQuo );
				}
				else{
					hibernateTemplate.saveOrUpdate( uwQuestionsQuo );
				}
	
			}
	
		}
	
		public static BigDecimal getNonTaxPrm( long polLinkingId, Long endtId, Boolean isQuote ){
			BigDecimal prm = null;
			DataHolderVO<HashMap<String, Double>> premiumHolder ;
	
			if( isQuote ){
				premiumHolder = PremiumHelper.getOldNewPremiumNoTaxForQuote( polLinkingId, endtId );
			}
			else{
				premiumHolder = PremiumHelper.getOldNewPremiumNoTax( polLinkingId, endtId );
			}
	
			premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );
			premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );
			prm = BigDecimal.valueOf( Math
					.abs( premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM ) - premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM ) ) );
			return prm;
	
		}
	
		/**
		 * @param ht
		 * @return BaseVO
		 * This method queries t_mas_control table and holds the result(region, city etc) in the DataHolderVO
		 */
		public static BaseVO getControlDetails( HibernateTemplate ht ){
			Object ControlDetails[] = new Object[ 10 ];
			DataHolderVO<Object[]> controlData = new DataHolderVO<Object[]>();
			Session session = ht.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_TMAS_CONTROL );
			Object[] result = (Object[]) ( query.list() ).get( 0 );
			ControlDetails[ 0 ] = ( (BigDecimal) result[ 0 ] ).intValue();
			ControlDetails[ 1 ] = ( (BigDecimal) result[ 1 ] ).intValue();
			//.. Any other data from the t_mas_control needed can be add here
			controlData.setData( ControlDetails );
			return controlData;
		}
	
		/**
		 * @param ht
		 * @param schCode
		 * This method returns the default billing account of the customer
		 */
		public static Long getCustoemrId( HibernateTemplate ht, Integer schCode ){
			Session session = ht.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_CUST_ID );
			query.setParameter( "schCode", schCode );
			Object result = ( query.list() ).get( 0 );
			return ( (BigDecimal) result ).longValue();
		}
	
		/**
		 * @param ht
		 * @param schCode
		 * This method returns the tot_acc_code based on customer_id
		 */
		public static BigDecimal getTotAccCode( HibernateTemplate ht, Long custId ){
			Session session = ht.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_TOT_ACC_CODE );
			query.setParameter( "custId", custId );
			Object result = ( query.list() ).get( 0 );
			return (BigDecimal) result ;
		}
		/**
		 * This method populates the commonVO used in services for Phase 3.
		 * @param commonVO
		 * @param baseVo
		 * To populate the CommonVO to be used in BaseSaveOperation.
		 */
		public static CommonVO populateCommonData( CommonVO commonVO, BaseVO baseVo ){
			PolicyDataVO polDataVo = (PolicyDataVO) baseVo;
	
			if( Utils.isEmpty( polDataVo.getIsQuote() ) ){
				commonVO.setIsQuote( Boolean.TRUE );
			}
			else{
				commonVO.setIsQuote( polDataVo.getIsQuote() );
			}
			if( Utils.isEmpty( polDataVo.getStatus() ) ){
				commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			}
			else{
				commonVO.setStatus( polDataVo.getStatus() );
			}
			if( Utils.isEmpty( polDataVo.getEndtId() ) ){
				commonVO.setEndtId( Long.valueOf( SvcConstants.zeroVal ) );
			}
			else{
				commonVO.setEndtId( polDataVo.getEndtId() );
			}
			if( Utils.isEmpty( polDataVo.getEndtNo() ) ){
				commonVO.setEndtNo( Long.valueOf( SvcConstants.zeroVal ) );
			}
			else{
				commonVO.setEndtNo( polDataVo.getEndtNo() );
			}
	
			return commonVO;
		}
	
		/**
		 *  Returning Scheme code for the given broker code for travel section
		 */
		public static Integer getSchemeCodeForBrokerName( Integer brokerName ){
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_FETCH_SCHEME_CODE );
			if( Utils.isEmpty( brokerName ) ){
				query.setParameter( "brokerCode", SvcConstants.DIRECT_BROKER_CODE );
			}
			else{
				query.setParameter( "brokerCode", brokerName );
			}
			Object result = ( query.list() ).get( 0 );
			return ( (BigDecimal) result ).intValue();
		}
	
		/**
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<Object[]> getSqlResult( String sqlQuery, Object... params ){
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );
			List<Object[]> result = ( query.list() );
			return result;
		}
	
		/**
		 * Returns the query results by executes the passed sql query by setting the passed parameters
		 * @param sqlquery
		 * @param hibernateTemplate
		 * @return 
		 * @return
		 */
		public static <T> List<T> getSqlResult( String sqlquery, HibernateTemplate hibernateTemplate, Object ...params ){
			Session session = null;
			session = hibernateTemplate.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlquery );
			
			if( !Utils.isEmpty( params ) && params.length > 0){
				for( int paramCnt = 0 ; paramCnt < params.length ; paramCnt ++){
					query.setParameter( paramCnt, params[ paramCnt ] );
				}
			}
					
			List<T> result = query.list();
			
			if( !Utils.isEmpty( result ) && ( result.size() > 0 ) ){
				if(!Utils.isEmpty( result.get( 0 ) ) ){
					return result;
				}
			}
			return null;
		}
		
		/**
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<Object> getSqlResultSingleColumn( String sqlQuery, HibernateTemplate ht, Object... params ){
			Session session = ht.getSessionFactory().getCurrentSession();
			ht.flush();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			if( !Utils.isEmpty( params ) && ( !Utils.isEmpty( params ) && params.length > 0 && !Utils.isEmpty( params[ 0 ] ) ) ){
				for( Object obj : params ){
					query.setParameter( i, obj );
					i++;
				}
			}
			
			query.setCacheable( false );
			List<Object> result = ( query.list() );
			return result;
		}
		
		public static int updateSql( String sqlQuery, HibernateTemplate ht, Object... params ){
			Session session = ht.getSessionFactory().getCurrentSession();
			ht.flush();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			if( !Utils.isEmpty( params ) && ( !Utils.isEmpty( params ) && params.length > 0 && !Utils.isEmpty( params[ 0 ] ) ) ){
				for( Object obj : params ){
					query.setParameter( i, obj );
					i++;
				}
			}
			return query.executeUpdate();
		}
		
		/**
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<Object> getSqlResultSingleColumnPASFor( String sqlQuery, Object... params ){ //SONARFIX -- changed name from getSqlResultSingleColumnPAS TO getSqlResultSingleColumnPASFor
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = ht.getSessionFactory().getCurrentSession();
			ht.flush();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			if( !Utils.isEmpty( params ) && ( !Utils.isEmpty( params ) && params.length > 0 && !Utils.isEmpty( params[ 0 ] ) ) ){
				for( Object obj : params ){
					query.setParameter( i, obj );
					i++;
				}
			}
			
			query.setCacheable( false );
			List<Object> result = ( query.list() );
			return result;
		}
		
		/**
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<Object> getSqlResultSingleColumnPas( String sqlQuery, Object... params ){
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = ht.getSessionFactory().openSession();
			ht.flush();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			if( !Utils.isEmpty( params ) && ( !Utils.isEmpty( params ) && params.length > 0 && !Utils.isEmpty( params[ 0 ] ) ) ){
				for( Object obj : params ){
					query.setParameter( i, obj );
					i++;
				}
			}
			
			query.setCacheable( false );
			List<Object> result = ( query.list() );
			session.close();
			return result;
		}
		
		public static List<Object> getSqlResultSingleColumnPASNewSession( String sqlQuery, Object... params ){
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = ht.getSessionFactory().openSession();
			ht.flush();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			if( !Utils.isEmpty( params ) && ( !Utils.isEmpty( params ) && params.length > 0 && !Utils.isEmpty( params[ 0 ] ) ) ){
				for( Object obj : params ){
					query.setParameter( i, obj );
					i++;
				}
			}
			
			query.setCacheable( false );
			List<Object> result = ( query.list() );
			session.close();
			return result;
		}
		
	
		/**
		 * Since PHASE 3.
		 * @param endtId
		 * @param policyId
		 * @return TTrnPolicyQuo
		 * This method returns only one latest (valid) record from TTrnPolicy/Quo table.
		 * Added as part of Phase 3.
		 */
		public static TTrnPolicyQuo getLatestPolicyRecord( Long endtId, Long policyId, HibernateTemplate ht ){
	
			List<TTrnPolicyQuo> polRecs = ht.find( "from TTrnPolicyQuo polQuo where polQuo.id.polPolicyId = ? "
					+ "and polQuo.id.polEndtId = ?", policyId, endtId );
	
			/* For Home/Travel, as we should have only one valid record in TTrnPolicy/Quo at any point of time, 
			 * throw exception if more/less records are found.
			 * */
			if( Utils.isEmpty( polRecs ) ){
				throw new BusinessException( "pas.policy.noRecordsFound", null, "No data found in Policy table." );
	
			}
			else if( ( !Utils.isEmpty( polRecs ) && polRecs.size() > 1 ) ){
				throw new BusinessException( "pas.policy.tooManyRecords", null, "Expected one record. Got " + polRecs.size() + " records." );
			}
	
			return polRecs.get( 0 );
		}
	
		/**
		 * @param policyDataVO
		 * This method is used to call issue quote procedure for home
		 */
		public static void callUpdateStatusProcedureForHomeTravel( PolicyDataVO policyDataVO ){
			PASStoredProcedure sp = null;
			if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && !Utils.isEmpty( policyDataVO.getCommonVO().getLob() ) && policyDataVO.getCommonVO().getLob().equals( LOB.HOME ) ){
				if( policyDataVO.getCommonVO().getIsQuote() ){
					sp = (PASStoredProcedure) Utils.getBean( "procIssueQuoteHome" );
				}else{
					sp = (PASStoredProcedure) Utils.getBean( "procConfirmEndtHome" );
				}
				
			}
			else{
				if( policyDataVO.getCommonVO().getIsQuote() ){
					sp = (PASStoredProcedure) Utils.getBean( "procIssueQuoteTravel" );
				}else{
					sp = (PASStoredProcedure) Utils.getBean( "procConfirmEndtTravel" );
				}
			
			}
			try{
				
				Map resultsVED = null;
				if( policyDataVO.getCommonVO().getIsQuote() ){
					resultsVED = sp.call( policyDataVO.getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
				}else{
					resultsVED = sp.call( policyDataVO.getPolicyId(), policyDataVO.getCommonVO().getEndtId(),null );
				}
				
				if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_VSD_DATE ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) ) ){
					
					if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty(  policyDataVO.getCommonVO() ) 
							&& (policyDataVO.getCommonVO().getStatus() != SvcConstants.POL_STATUS_REFERRED)){
						policyDataVO.setValidityStartDate( (Date)resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) );
						policyDataVO.getCommonVO().setVsd( policyDataVO.getValidityStartDate() );
					}
				}
				if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) ) ){
					LOGGER.debug( "Error occured while executing stored procedur_1" );
					throw new BusinessException( "cmn.storedproc.error", null, resultsVED.get( "PO_ERR_TEXT" ).toString() );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc_58" );
			}
		}
	
		/**
		 * @param policyId
		 * @param isQuote
		 * @return
		 */
		public static Map endFlowGeneralInfoHomeTravel( Long policyId, Boolean isQuote ){
			Map results = null;
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) (isQuote ? Utils.getBean( "endIDgenerationHomeTravelQuo" ): Utils.getBean( "endIDgenerationHomeTravelPol" ));
			results = sp.call( policyId );
			return results;
		}
		
		public static Map endFlowGeneralInfoMonoline( Long policyId ){
			Map results = null;
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endIDgenerationMonoline" );
			results = sp.call( policyId );
			return results;
		}
	
		/* 
		 * This method populates the commonVO after transaction search.
		 * Added as part of Phase 3.
		 */
	
		public static BaseVO populateCommonDetails( BaseVO baseVO, HibernateTemplate ht ){
			LOGGER.info("Entered DAOUtils.populateCommonDetails method.");
			CommonVO commonVO = (CommonVO) baseVO;
			
			List<TTrnPolicyQuo> polRecs;
	
			List<Object> queryData = new ArrayList<Object>();
			String query = commonVO.getIsQuote() ? FETCH_REC_FOR_QUOTE : FETCH_REC_FOR_POLICY;
			if( commonVO.getIsQuote() ){
				queryData.add( commonVO.getQuoteNo() );
			}
			else{
				queryData.add( commonVO.getPolicyNo() );
			}
			queryData.add( commonVO.getLocCode().shortValue() );
			queryData.add( commonVO.getEndtId() );
			queryData.add( SvcConstants.POL_ISSUE_HOUR );
			// Check if document code available(Transaction search/Recent activity flow)
			if( !Utils.isEmpty( commonVO.getDocCode() ) ){
				query = query + FETCH_REC_DATA_EXTENDED_DOC_CODE;
				queryData.add( commonVO.getDocCode() );
			}
			// Check if policy effective date available(Transaction search/Recent activity flow)
			if( !Utils.isEmpty( commonVO.getPolEffectiveDate() ) ){
				query = query + FETCH_REC_DATA_EXTENDED_EFF_DATE;
				queryData.add( commonVO.getPolEffectiveDate() );
			}
	
			polRecs = ht.find( query, queryData.toArray() );
	
			if( !Utils.isEmpty( polRecs ) && !Utils.isEmpty( polRecs.get( SvcConstants.zeroVal ) ) && !Utils.isEmpty( polRecs.get( SvcConstants.zeroVal ).getId() ) ){
				commonVO.setPolicyId( polRecs.get( SvcConstants.zeroVal ).getId().getPolicyId() );
				commonVO.setVsd( polRecs.get( SvcConstants.zeroVal ).getPolValidityStartDate() );
				commonVO.setStatus( polRecs.get( SvcConstants.zeroVal ).getStatus() );
				commonVO.setEndtNo( polRecs.get( SvcConstants.zeroVal ).getPolEndtNo() );
				if( !Utils.isEmpty( polRecs.get( SvcConstants.zeroVal ).getPolPreparedBy() ) ){
					commonVO.setCreatedBy( polRecs.get( SvcConstants.zeroVal ).getPolPreparedBy().toString() );
				}
				commonVO.setQuoteNo(polRecs.get(SvcConstants.zeroVal).getPolQuotationNo());
				commonVO.setDocCode( polRecs.get( 0 ).getPolDocumentCode() );
				if(!Utils.isEmpty(commonVO.getEndtId()) && commonVO.getEndtId()>0 ){
					commonVO.setEndtEffectiveDate((polRecs.get(SvcConstants.zeroVal).getPolEndtEffectiveDate()));
				}
				commonVO.setConcatPolicyNo(polRecs.get(SvcConstants.zeroVal).getPolConcPolicyNo());
				commonVO.setPolicyNo( polRecs.get(SvcConstants.zeroVal).getPolPolicyNo());
				commonVO.setPolEffectiveDate( polRecs.get(SvcConstants.zeroVal).getPolEffectiveDate());
				commonVO.setCreatedOn( (Timestamp) polRecs.get(SvcConstants.zeroVal).getPolPreparedDt());
			}
			else{
				throw new BusinessException( null, "No Policy records exists!" );
			}
			LOGGER.info("Exiting DAOUtils.populateCommonDetails method.");
	
			return commonVO;
	
		}
	
		/**
		 * Method to fetch the free cover applied for promotional code  
		 * 
		 * @param schemeCode
		 * @param promoCode
		 * @param classCode
		 * @return
		 */
		public static List<Object> getPromotionalCodeCover( Integer schemeCode, String promoCode, Integer classCode, Date effectiveDate,Boolean isQuote ){
	
			List<Object> coverListWithDesc = new ArrayList<Object>();
			List<Short> promotionalCodeCover = null;
	
			if( !Utils.isEmpty( schemeCode ) && !Utils.isEmpty( promoCode ) && !Utils.isEmpty( classCode ) ){
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
				/* Ticket ID: 119593 Nil endorsement going with wrong premium calculation - Due to Promo discount */
				String fecthQuery = isQuote?QUERY_FETCH_PROMOTIONAL_CODE_COVER : QUERY_FETCH_PROMOTIONAL_CODE_COVER_FOR_ENDT; 
				SQLQuery query = session.createSQLQuery( fecthQuery );
				query.setParameter( "promotionalCode", promoCode );
				query.setParameter( "schemeCode", schemeCode );
				query.setParameter( com.Constant.CONST_CLASSCODE, classCode );
				//query.setParameter( "effectiveDate", effectiveDate );
				List<Object[]> result = query.list();
	
				BigDecimal resultValue = null;
				promotionalCodeCover = new ArrayList<Short>( 0 );
	
				for( Object[] resultSet : result ){
	
					resultValue = (BigDecimal) resultSet[ 0 ];
					promotionalCodeCover.add( resultValue.shortValue() );
	
				}
				
				if( !Utils.isEmpty( result ) && result.size() > 0 ){
					coverListWithDesc.add( promotionalCodeCover );
					if( !Utils.isEmpty( result.get( 0 ) ) &&  !Utils.isEmpty( result.get( 0 )[2] ) ){
						coverListWithDesc.add( result.get( 0 )[2] );	
					}else{
						coverListWithDesc.add( "" );	
					}
				}
				
			}
			
			return coverListWithDesc;
	
		}
	
		/**
		 * Method to fetch the discount given for promotional code  
		 * 
		 * @param schemeCode
		 * @param promoCode
		 * @param classCode
		 * @return
		 */
		public static List<Object> getPromotionalCodeDiscount( Integer schemeCode, String promoCode, Integer classCode, Date effectiveDate,Boolean isQuote ){
	
			List<Object> promotionalCodeDets = null;
	
			if( !Utils.isEmpty( schemeCode ) && !Utils.isEmpty( promoCode ) && !Utils.isEmpty( classCode ) ){
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
				/* Ticket ID: 119593 Nil endorsement going with wrong premium calculation - Due to Promo discount */
				String fecthQuery = isQuote?QUERY_FETCH_PROMOTIONAL_CODE_DISCOUNT : QUERY_FETCH_PROMOTIONAL_CODE_DISCOUNT_FOR_ENDT;
				SQLQuery query = session.createSQLQuery( fecthQuery );
				query.setParameter( "promotionalCode", promoCode );
				query.setParameter( "schemeCode", schemeCode );
				query.setParameter( com.Constant.CONST_CLASSCODE, classCode );
				//query.setParameter( "effectiveDate", effectiveDate );
				List<Object[]> result = query.list();
	
				if( result != null && result.size() > 0 ){
					promotionalCodeDets = new ArrayList<Object>();
					if( !Utils.isEmpty( result.get( 0 )[0] ) ){
						promotionalCodeDets.add( ( (BigDecimal) result.get( 0 )[0] ).doubleValue() );	
					}else{
						promotionalCodeDets.add( Double.valueOf( 0 ) );
					}
					if( !Utils.isEmpty( result.get( 0 )[1] )){
						promotionalCodeDets.add( ( result.get( 0 )[1] ).toString() );
					}else{
						promotionalCodeDets.add( "" );
					}
					
				}
			}
			return promotionalCodeDets;
	
		}
		
		public static void callPolicyExtensionProcedure( PolicyDataVO polData ){
			LOGGER.debug("in callPolicyExtensionProcedur_1");
			PASStoredProcedure sp = null;
			Integer userId = SvcUtils.getUserId(polData.getCommonVO());
			
			if(!Utils.isEmpty( polData.getCommonVO() ) && !Utils.isEmpty( polData.getCommonVO().getLob() ) &&  polData.getCommonVO().getLob() .equals( LOB.HOME ))
			{
				sp = (PASStoredProcedure) Utils.getBean( "procPolExtnHome" );
			}else if (!Utils.isEmpty( polData.getCommonVO() ) && !Utils.isEmpty( polData.getCommonVO().getLob() ) &&  polData.getCommonVO().getLob() .equals( LOB.TRAVEL )){
				sp = (PASStoredProcedure) Utils.getBean( "procPolExtnTravel" );
			}else{
				sp = (PASStoredProcedure) Utils.getBean( "procPolExtnMonoline" );
			}
			
			try{
				Map resultsVED = null;
				if(!Utils.isEmpty( polData.getCommonVO() ) && !Utils.isEmpty( polData.getCommonVO().getLob() ) &&  polData.getCommonVO().getLob() .equals( LOB.TRAVEL ))
				{
					resultsVED = sp.call( polData.getCommonVO().getPolicyId(),userId,polData.getCommonVO().getEndtId(), polData.getScheme().getExpiryDate(),polData.getScheme().getEffDate() );
				}
				/*
				 * Added for #131212
				 */
				else if(!Utils.isEmpty( polData.getCommonVO() ) && !Utils.isEmpty( polData.getCommonVO().getLob() ) &&  polData.getCommonVO().getLob() .equals( LOB.HOME )){
					resultsVED = sp.call( polData.getCommonVO().getPolicyId(),userId,polData.getCommonVO().getEndtId(), polData.getScheme().getExpiryDate() );
				}
				else{
					resultsVED = sp.call( polData.getCommonVO().getPolicyId(),userId,polData.getCommonVO().getEndtId(), polData.getScheme().getExpiryDate(),polData.getEndEffectiveDate() );
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				try {
					LOGGER.debug("Validity Expiry Date returned by PRO_POLICY_EXTENSION_" +   resultsVED.get("PO_VSD_DATE").toString());
					polData.setValidityStartDate(simpleDateFormat.parse(  resultsVED.get(com.Constant.CONST_PO_VSD_DATE).toString()));
					polData.getCommonVO().setVsd(polData.getValidityStartDate());
				} catch (ParseException e) {
					
					e.printStackTrace();
					throw new BusinessException( "cmn.storedproc.error", null , resultsVED.get( "PO_ERR_TEXT" ).toString() );
				}
				if( !Utils.isEmpty( resultsVED ) &&  resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT )  && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) )  ){
					LOGGER.debug( "Error occured while executing stored procedur_2" );
					throw new BusinessException( "cmn.storedproc.error", null , resultsVED.get( "PO_ERR_TEXT" ).toString() );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc_59" );
			}
		}
	
		public static List<Integer> getSchemeAndPolicyType( CommonVO commonVO, HibernateTemplate hibernateTemplate ){
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = null;
			if( commonVO.getIsQuote() ){
				query = session.createSQLQuery( FETCH_SCHEME_POL_TYPE_QUO );
				query.setParameter( "POL_QUO_NO", commonVO.getQuoteNo() );
			}
			else{
				query = session.createSQLQuery( FETCH_SCHEME_POL_TYPE_POL );
				query.setParameter( com.Constant.CONST_POL_POLICY_NO, commonVO.getPolicyNo() );
			}
	
			query.setParameter( com.Constant.CONST_POL_END_ID, commonVO.getEndtId() );
			List<Object> result = query.list();
	
			Object[] object = (Object[]) result.get( SvcConstants.zeroVal );
	
			List<Integer> schemePolTypes = new ArrayList<Integer>();
			schemePolTypes.add( ( (BigDecimal) object[ 0 ] ).intValue() );
			schemePolTypes.add( ( (BigDecimal) object[ 1 ] ).intValue() );
	
			return schemePolTypes;
	
		}
		
		/**
		 * This method will fetch all the referral message(s) saved against the policy id 
		 * from TTrnPasReferralDetails
		 * 
		 * @param policyId
		 * @param endtId 
		 * @return
		 */
		public static List<TTrnPasReferralDetails> getReferralDetails(Long policyId, Long endtId) {
			List<TTrnPasReferralDetails> pasReferralsList = new ArrayList<TTrnPasReferralDetails>();
			if (!Utils.isEmpty(policyId)) {
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
				Query query = session.createQuery("from TTrnPasReferralDetails tprd where tprd.id.polLinkingId = :policyId and tprd.id.refEndId = :endtId and tprd.refStatus = :status");
				query.setParameter(com.Constant.CONST_POLICYID, policyId);
				query.setParameter(com.Constant.CONST_ENDTID, endtId);
				query.setParameter(com.Constant.CONST_STATUS, SvcConstants.POL_STATUS_REFERRED.toString());
				query.setCacheable( false );
				pasReferralsList = query.list();
			}
			return pasReferralsList;
		}
		
		/**
		 * This method generates the referral description based on the entries in T_TRN_PAS_REFERRAL_DETAILS table for given policyId/endtId
		 * @param policyId
		 * @param endtId
		 * @return
		 */
		public static String getTaskDescription(Long policyId, Long endtId){
			List<TTrnPasReferralDetails> referralsList = DAOUtils.getReferralDetails(policyId,endtId);
			StringBuilder messageBuilder = new StringBuilder();
			if (!Utils.isEmpty(referralsList)) {
				/* Populating the message from T_TRN_PAS_REFERRAL_DETAILS */
				int counter = 0;
				List<String> uniqueMessage = new ArrayList<String>();
				for (TTrnPasReferralDetails pasReferralDetails : referralsList) {
					if (counter == 0) {
						if(uniqueMessage.contains( pasReferralDetails.getRefText() )){
							continue;
						}
						messageBuilder.append(pasReferralDetails.getRefText());
						uniqueMessage.add( pasReferralDetails.getRefText() );
						
					} else {
						messageBuilder.append("/n").append(pasReferralDetails.getRefText());
					}
				}
			}
			return messageBuilder.toString();
		}
		
		/**
		 * Method to fetch the stored referral messages
		 * 
		 * @param policyId
		 * @param endtId
		 * @return
		 */
		public static Map<String,String> getReferralMessages(Long policyId,Long endtId,Integer userId){
			Map<String,String> referralMessages = new HashMap<String,String>();
			
			if(!Utils.isEmpty( policyId ) && !Utils.isEmpty( endtId )){
	
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( QUERY_FETCH_REFERRAL_MESSAGE );
				query.setParameter( com.Constant.CONST_POLICYID, policyId );
				query.setParameter( com.Constant.CONST_ENDTID, endtId );
				query.setParameter( "userId", userId );
				
				List<Object[]> resultList = query.list();
	
				for(Object[] result:resultList){
					referralMessages.put( String.valueOf( result[0] ), String.valueOf( result[1] ) );
				}
				session.close();
			}
			
			return referralMessages;
		}
		
		/**
		 * This method is to be used from web app where being in the same session is not required.
		 * This method is not to be used from service layer as new session is opened and the data set in the 
		 * service session may not be available here 
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<Object[]> getSqlResultForPas( String sqlQuery, Object... params ){
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );
			List<Object[]> result = ( query.list() );
			session.close();
			return result;
		}
		
		public static List<Object> getSqlResultForPas( String sqlQuery){
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			/*int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );*/
			List<Object> result = ( query.list() );
			session.close();
			return result;
		}
		
		/*
		 * Added by Vishwa to FGB MS for Advenet ID 123969
		 */
		public static List<String> getSqlResultForPasString( String sqlQuery, Object... params ){
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );
			List<String> result =  query.list() ;
			session.close();
			return result;
		}
		
		public static List<Object[]> getSqlResultForPasObject( String sqlQuery, Object... params ){
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery( sqlQuery );
			int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );
			List<Object[]> result =  query.list() ;
			session.close();
			return result;
		}
		
	
		/**
		 * Method to get the previous data at cover level
		 * @param homeInsuranceVO
		 * @return
		 */
		public static List<Object[]> getPreviousData( Long policyId, Long endtId, Short coverCode, Integer rtCode, Integer rskCat ){
	
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_FETCH_PREVIOUS_PREMIUM );
			query.setParameter( com.Constant.CONST_POLICYID, policyId );
			query.setParameter( com.Constant.CONST_ENDTID, endtId );
			query.setParameter(com.Constant.CONST_COVERCODE,coverCode);
			query.setParameter( "rtCode", rtCode );
			query.setParameter( "rskCat", rskCat );
			
			List<Object[]> resultList = query.list();
			return resultList;
			
		}
		
		/**
		 * Method to get the previous data at cover level
		 * @param homeInsuranceVO
		 * @return
		 */
		public static List<Object[]> getPreviousDataTravel( Long policyId, Long endtId, Short coverCode, BigDecimal rskId ){
	
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_FETCH_PREVIOUS_PREMIUM_TRAVEL );
			query.setParameter( com.Constant.CONST_POLICYID, policyId );
			query.setParameter( com.Constant.CONST_ENDTID, endtId );
			query.setParameter(com.Constant.CONST_COVERCODE,coverCode);
			query.setParameter( "rskId", rskId );
			List<Object[]> resultList = query.list();
			return resultList;
			
		}
		
		public static boolean isRenewalQuoteForHomeAndTravel( Long policyId, HibernateTemplate hibernateTemplate ){
			TTrnPolicyQuo policy = null;
			boolean renQuote = false;
			policy = (TTrnPolicyQuo) hibernateTemplate.find
					( "from TTrnPolicyQuo where id.polPolicyId = ? and polIssueHour =?", policyId, SvcConstants.POL_ISSUE_HOUR).get( 0 );
			if( policy.getPolDocumentCode() == REN_DOC_CODE ){
				renQuote = true;
			}
			return renQuote;
		}
		
		/**
		 * Method to set the Status Code in the T_TRN_SMS
		 * @param STATUS_DESCRIPTION
		 * @return STATUS_CODE
		 */
		public static Byte setSmsStatusCode( String status ){
			String sqlQuery = "select cdm_code from t_mas_code_master where cdm_entity_type='CC_SMSSTTS' and cdm_code_desc='" + status + "'";
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			Byte stausCode = null;
			Query query = session.createSQLQuery( sqlQuery );
			List<Object> results = query.list();
			if( !Utils.isEmpty( results ) ){
				stausCode = Byte.valueOf( results.get( 0 ).toString() );
			}
			session.close();
			return stausCode;
		}
		
		/**
		 * Method to get the Status Description for Status Code
		 * @param STATUS_CODE
		 * @return STATUS_DESCRIPTION
		 */
		public static String getSmsStatusDesc( BigDecimal code ){
			String stausCode = null;
			if( !Utils.isEmpty( code ) ){
				String sqlQuery = "select cdm_code_desc from t_mas_code_master where cdm_entity_type='CC_SMSSTTS' and cdm_code='" + code + "'";
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().openSession();
				stausCode = null;
				Query query = session.createSQLQuery( sqlQuery );
				List<Object> results = query.list();
				if( !Utils.isEmpty( results ) ){
					stausCode = results.get( 0 ).toString();
				}
				session.close();
			}
			
			return stausCode;
		}
		
	
		/**
		 * Method to get the action of Broker for Credit Limit rules
		 * @param BROKER_CODE
		 * @return STATUS_DESCRIPTION
		 */
		public static String getBrActionCL( Integer code ){
			String statusCode = null;
			if( !Utils.isEmpty( code ) ){
				String sqlQuery = "select cdm_code_desc from t_mas_code_master where cdm_entity_type='PAS_CLIMIT' and cdm_code='" + code + "'";
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().openSession();
				statusCode = null;
				Query query = session.createSQLQuery( sqlQuery );
				List<Object> results = query.list();
				if( !Utils.isEmpty( results ) ){
					statusCode = results.get( 0 ).toString();
				}
				session.close();
			}
			
			return statusCode;
		}
		/**
		 * Load the pending state quotes for last days date - to be invoked by B2C ConvertToPolicyReminderScheduler.
		 * Exception handling is done explicitly for this method since it has not been called by using TaskExecutor
		 * 
		 * @return
		 */
		public static List<PolicyDataVO> getLastDaysPendingQuotes(boolean isTwentyFourHrsScheduler) {
			
			List<TTrnPolicyQuo> ttrnPolicyQuo;
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Calendar lastDayDate = Calendar.getInstance();
			Calendar currentDate = Calendar.getInstance();
			List<PolicyDataVO> policyDataVOList = new ArrayList<PolicyDataVO>();
			
			if (isTwentyFourHrsScheduler) {
				lastDayDate.add( Calendar.DATE, -1 );
			} else {
				//Added quote email configurable for Oman 
				LoginLocation location = (LoginLocation) Utils.getBean("location");
				String deployedLocation = location.getLocation();
				if(null != deployedLocation && deployedLocation.equals(QueryConstants.LOCATION_CODE)) {
					lastDayDate.add( Calendar.DATE, Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_REMIND_EMAIL"))-1); //-15
					currentDate.add( Calendar.DATE, Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_REMIND_EMAIL"))); //-14
				}else {
					lastDayDate.add( Calendar.DATE, -15 );
					currentDate.add( Calendar.DATE, -14);
				}
			}
			
			lastDayDate.set( Calendar.HOUR_OF_DAY, 0 );
			lastDayDate.set( Calendar.MINUTE, 0 );
			lastDayDate.set( Calendar.SECOND, 0 );
			lastDayDate.set( Calendar.MILLISECOND, 0 );
			
			currentDate.set( Calendar.HOUR_OF_DAY, 0 );
			currentDate.set( Calendar.MINUTE, 0 );
			currentDate.set( Calendar.SECOND, 0 );
			currentDate.set( Calendar.MILLISECOND, 0 );
			
			Timestamp lastDayTimeStamp = new Timestamp(lastDayDate.getTime().getTime());
			Timestamp currentDayTimeStamp = new Timestamp(currentDate.getTime().getTime());
			
			try {
			
				ttrnPolicyQuo = hibernateTemplate.find("from TTrnPolicyQuo where polPreparedDt between ? and ? and polDistributionChnl in (?, ?, ?) and polStatus = ?",
								lastDayTimeStamp, currentDayTimeStamp,SvcConstants.B2C_DEFAULT_DIST_CHANNEL,SvcConstants.DIST_CHANNEL_BROKER,SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT, Byte.valueOf(String.valueOf(SvcConstants.POL_STATUS_PENDING)));
				
				if (!Utils.isEmpty(ttrnPolicyQuo)) {
					for (TTrnPolicyQuo ttrnPolQuoObj:ttrnPolicyQuo) {
						PolicyDataVO policyDataVO = BeanMapper.map(ttrnPolQuoObj,PolicyDataVO.class);
						policyDataVOList.add(policyDataVO);
					}
					policyDataVOList.removeAll(Collections.singleton(null));
				} 
				
			} catch (BusinessException businessException) {
				LOGGER.debug("Exception occured while fetching data for B2C convert to policy reminder - "+businessException.getMessage());
				throw new SystemException(businessException.getCause(), businessException.getMessage());
			} catch (Exception exception) {
				LOGGER.debug("Exception occured while fetching data for B2C convert to policy reminder - "+exception.getMessage());
				throw new SystemException(exception.getCause(), exception.getMessage());
			} 
			
			if (Utils.isEmpty(ttrnPolicyQuo) || (ttrnPolicyQuo.size()==0)) {
				throw new SystemException(new Throwable(), "No records found in TTrnPolicyQuo for reminder");
			}
			
			return policyDataVOList;
		}
	
		/**
		 * Method to update the effective date 
		 * 
		 * @param polData
		 */
		public static void callPolicyEffectiveDateUpdateProc( PolicyDataVO polData ){
			LOGGER.debug("in callPolicyExtensionProcedur_2");
			PASStoredProcedure sp = null;
			Integer userId = SvcUtils.getUserId(polData.getCommonVO());
			
			
			sp = (PASStoredProcedure) Utils.getBean( "procPolEffChange" );
	
			try{
				Map resultsVED = sp.call( polData.getCommonVO().getPolicyId(), polData.getCommonVO().getEndtId(), polData.getScheme().getExpiryDate(),
						polData.getScheme().getEffDate(), polData.getCommonVO().getStatus().equals( Integer.valueOf( SvcConstants.QUOTE_ACTIVE_STATUS  ) )?null:polData.getCommonVO().getVsd(), userId );
	
				//SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
	
			
				LOGGER.debug( "Validity Expiry Date returned by PRO_POLICY_EXTENSION_" + resultsVED.get( "PO_VSD_DATE" ).toString() );
				polData.setValidityStartDate( (Date)resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) );
				polData.getCommonVO().setVsd( polData.getValidityStartDate() );
			
				/*if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) ) ){
					LOGGER.debug( "Error occured while executing stored procedur_3" );
					throw new BusinessException( "cmn.storedproc.error", null, resultsVED.get( "PO_ERR_TEX_4" ).toString() );
				}*/
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc_60" );
			}
		}
	
		
		/**
		 * Method to update the effective date 
		 * 
		 * @param polData
		 */
		public static void callPolicyEffectiveDateUpdateProcMonoline( PolicyDataVO polData ){
			LOGGER.debug("in callPolicyExtensionProcedur_3");
			PASStoredProcedure sp = null;
			Integer userId = SvcUtils.getUserId(polData.getCommonVO());
			
			
			sp = (PASStoredProcedure) Utils.getBean( "procPolEffChangeMonline" );
	
			try{
				Map resultsVED = sp.call( polData.getCommonVO().getPolicyId(), polData.getCommonVO().getEndtId(), polData.getScheme().getExpiryDate(),
						polData.getScheme().getEffDate(), polData.getCommonVO().getStatus().equals( Integer.valueOf( SvcConstants.QUOTE_ACTIVE_STATUS  ) )?null:polData.getCommonVO().getVsd(), userId );
	
				//SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
	
			
				LOGGER.debug( "Validity Expiry Date returned by PKG_PAS_QUO_POL_EXTEN_MONOLINE.PRO_ENDT_QUO_EXTENSION" + resultsVED.get( "PO_VSD_DATE" ).toString() );
				polData.setValidityStartDate( (Date)resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) );
				polData.getCommonVO().setVsd( polData.getValidityStartDate() );
			
				/*if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) ) ){
					LOGGER.debug( "Error occured while executing stored procedur_4" );
					throw new BusinessException( "cmn.storedproc.error", null, resultsVED.get( "PO_ERR_TEXT" ).toString() );
				}*/
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc_61" );
			}
		}
		
		public static void flushTransaction(){
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			ht.getSessionFactory().getCurrentSession().flush();
	
		}
	
		public static Long getContentSI( CommonVO commonVO, HibernateTemplate hibernateTemplate ){
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = null;
			
			Long cntSI = null;
			if( commonVO.getIsQuote() ){
				query = session.createSQLQuery( FETCH_CONTENT_SI_QUO );
			}
			else{
				query = session.createSQLQuery( FETCH_CONTENT_SI_POL );
			}
	
			query.setParameter( "pol_id", commonVO.getPolicyId() );
			query.setParameter( com.Constant.CONST_POL_END_ID, commonVO.getEndtId() );
			query.setParameter( "cnt_val_exp_date", SvcConstants.EXP_DATE );
			
			List<Object> result = query.list();
	
			if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( SvcConstants.zeroVal ) ) ){
				cntSI = ( (BigDecimal) result.get( SvcConstants.zeroVal ) ).longValue();
			}
	
			return cntSI;
		}
		
		
		public static Integer getTariffCode( CommonVO commonVO, HibernateTemplate hibernateTemplate ){
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = null;
			if( commonVO.getIsQuote() ){
				query = session.createSQLQuery( FETCH_TARIFF_QUO );
				query.setParameter( "POL_QUO_NO", commonVO.getQuoteNo() );
			}
			else{
				query = session.createSQLQuery( FETCH_TARIFF_POL );
				query.setParameter( com.Constant.CONST_POL_POLICY_NO, commonVO.getPolicyNo() );
			}
	
			query.setParameter( com.Constant.CONST_POL_END_ID, commonVO.getEndtId() );
			List<Object> result = query.list();
	
			return ( (BigDecimal) result.get( SvcConstants.zeroVal ) ).intValue();
	
		}
		
		public static BigDecimal getGovtTax(PolicyDataVO policyDataVO ){
	
			StringBuilder queryString = new StringBuilder();
			
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			if (!Utils.isEmpty(policyDataVO.getIsQuote()) && !policyDataVO.getIsQuote()) {
	
				queryString
						.append(com.Constant.CONST_SELECT_DISTINCT_PR_PRM_RATE_FROM_T_TRN_PREMIUM_PR_T_TRN_POLICY_POL_WHERE_PR_PRM_POLICY_ID_POL_POL_POLICY_ID_AND_POL_POL_ISSUE_HOUR_END)
						.append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
						.append(com.Constant.CONST_AND_PR_PRM_COV_CODE_END)
						.append(SvcConstants.SC_PRM_COVER_GOVT_TAX)
						.append(com.Constant.CONST_PRM_ENDT_ID_EQUALS)
						.append(SvcConstants.zeroVal)
						.append(com.Constant.CONST_AND_POL_POL_POLICY_ID_END)
						.append(policyDataVO.getPolicyId())
						.append(com.Constant.CONST_AND_PR_PRM_CL_CODE_END)
						.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE));
	
			}else{
				queryString.append( "select distinct code from ss_v_mas_lookup lkup where lkup.category = 'GOVTTAX' and lkup.level_1 = '")
				.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE)).append( "'" );
			}
			
			Query query = session.createSQLQuery( queryString.toString() );
			List<Object> result = query.list();
			BigDecimal govtTax = new BigDecimal( 0 );
			if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
				govtTax = (BigDecimal) result.get( 0 );
			}
			return govtTax;
		}
	
		/**
		 * 
		 * @param sqlQuery
		 * @param params
		 * @return
		 */
		public static List<TTrnPolicyQuo> getResultForPas( String sqlQuery, /*Boolean isQuote,*/ Object...params ){
			//HibernateTemplate hibernateTemplate = isQuote? (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ):(HibernateTemplate) Utils.getBean( "hibernateTemplate_POL" );
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( "hibernateTemplate_POL" );
			Session session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery( sqlQuery );
			
			int i = 0;
			for( Object obj : params ){
				query.setParameter( i, obj );
				i++;
			}
			query.setCacheable( false );
			List<TTrnPolicyQuo> result = ( query.list() );
			session.close();
			
			return result;
			
		}
		
		public static Long getWcWUPBasicRskId( PolicyVO policyVO, HibernateTemplate ht ){
	
			
			String sqlQuery = "";
			
			SectionVO wcSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_WC );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( wcSection );
			WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( locationDetails, wcSection );
			
			if(policyVO.getIsQuote()){
				sqlQuery = "select max(wup_id) from t_trn_wctpl_unnamed_person_quo where wup_policy_id = "+wcDetails.getPolicyId()+" and wup_endt_id <= "+policyVO.getEndtId()
				+" and  wup_validity_expiry_date ='31-DEC-2049'  " ;
			} else {
				sqlQuery = "select max(wup_id) from t_trn_wctpl_unnamed_person where wup_policy_id = "+wcDetails.getPolicyId()+" and wup_endt_id <= "+policyVO.getEndtId()
				+ " and wup_validity_expiry_date = '31-DEC-2049' " ;
			}
			
			
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			Long wupId = null;
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				if(!Utils.isEmpty( resultsFunc.get( 0 ))){
					wupId = Long.valueOf( resultsFunc.get( 0 ).toString() );
				}
				
				if( Utils.isEmpty( wupId  ) ){
					wupId = (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ); 
					
					//throw new BusinessException( com.Constant.CONST_CMN_UNKNOWNERROR, null, "The basicRskCode null in getBasicRskCode " );
				}
			}
			return wupId;
		}
		
		/**
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param valueOf
		 * @param basicRiskId
		 */
		public static void addWCPersonforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcPersonAdd" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_58" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_62" );
			}
	
		}
		
		/**
		 * Call's stored procedure to update endorsement text for
		 * T_TRN_WCTPL_UNNAMED_PERSON table column modifications
		 * 
		 * @param policyId
		 * @param policyVO
		 * @param riskGroupId
		 * @param basicRiskId
		 */
		public static void updateWCPersonforendorsementFlow( Long policyId, PolicyVO policyVO, Long riskGroupId, Long wprId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcPersonModi" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), wprId, SvcUtils.getUserId( policyVO ), WC_SECTION_ID, riskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_59" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_63" );
			}
	
		}
		
		public static void deleteWCPersonforendorsementFlow( Long policyId, PolicyVO policyVO, Long basicRiskId ){
	
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "wcPersonDel" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), WC_SECTION_ID, basicRiskId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_60" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.PKG_ENDT_GEN.pro_endt_text_wc_un_per_add.exception", e, "An exception occured while executing stored proc_64" );
			}
	
		}
			
		/**
		 * Call issue quote procedure for mono-line.
		 * @param policyDataVO
		 */
		public static void callUpdateStatusProcedureForIssueQuote( PolicyDataVO policyDataVO ){
			PASStoredProcedure sp = null;
					
			try{
				
				if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && !Utils.isEmpty(policyDataVO.getCommonVO().getPolicyId()))
				{
					if( policyDataVO.getCommonVO().getIsQuote() ){
						sp = (PASStoredProcedure) Utils.getBean( "procIssueQuoteMonoline" );
					}
					else
					{
						sp = (PASStoredProcedure) Utils.getBean( "procConfirmEndtMonoline" );
					}
					
				}			
				Map resultsVED = null;
				if(!Utils.isEmpty( sp ))
				{
					if( policyDataVO.getCommonVO().getIsQuote() ){
						resultsVED = sp.call( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
					}
					else{
						//Passing dummy value of Validity Expire date as '31-DEC-49' .
						resultsVED = sp.call( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId(), "2049-12-31 00:00:00.000000000" );
					}
				}
				if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_VSD_DATE ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) ) ){
					
					if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty(  policyDataVO.getCommonVO() ) 
							&& (policyDataVO.getCommonVO().getStatus() != SvcConstants.POL_STATUS_REFERRED)){
						policyDataVO.setValidityStartDate( (Date)resultsVED.get( com.Constant.CONST_PO_VSD_DATE ) );
						policyDataVO.getCommonVO().setVsd( policyDataVO.getValidityStartDate() );
					}
				}
				if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) ) ){
					LOGGER.debug( "Error occured while executing stored procedur_5" );
					throw new BusinessException( "cmn.storedproc.error", null, resultsVED.get( "PO_ERR_TEXT" ).toString() );
				}
			}
			catch( DataAccessException e ){
	
				throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc_65" );
			}
		}
		
		/*
		 * added for fixing emirates dropdown issue
		 */
		public static String getBldEAddress3Desc(String cityCode,HibernateTemplate ht){
			String cityDecsription=null;
			if(cityCode!=null){
				Session session = ht.getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery( FETCH_CITY_DESCRIPTION );
				 query.setParameter( "cit_code", cityCode );
					Object result = query.uniqueResult();
					
					if(!Utils.isEmpty( result )) cityDecsription = result.toString();
					
			}
			
			return cityDecsription;
		}
	
	public static Long getLinkingIdOfQuo( PolicyVO policyVO, HibernateTemplate ht, Boolean isQuote ){
	
			
			String sqlQuery = "";
			
			if(isQuote){
				sqlQuery = "select max(pol_linking_id) from t_trn_policy_quo where pol_quotation_no = "+policyVO.getQuoteNo()
				+ " and pol_validity_expiry_date = '31-DEC-2049' " ;
			
			} else {
				sqlQuery = "select max(pol_linking_id) from t_trn_policy where pol_policy_no = "+policyVO.getPolicyNo()
				+ " and pol_validity_expiry_date = '31-DEC-2049' " ;
			}
			
			
					
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			Long linkingId = null;
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				if(!Utils.isEmpty( resultsFunc.get( 0 ))){
					linkingId = Long.valueOf( resultsFunc.get( 0 ).toString() );
				}
				
			}
			return linkingId;
		}
		
		public static List<Long> getEndtIdOfQuoTL( PolicyVO policyVO, HibernateTemplate ht,Boolean isQuote  ){
	
			
			String sqlQuery = "";
			if(isQuote){
				sqlQuery = "select distinct pol_endt_id from t_trn_policy_quo where pol_quotation_no = "+policyVO.getQuoteNo();
			}else {
				sqlQuery = "select distinct pol_endt_id from t_trn_policy where pol_policy_no = "+policyVO.getPolicyNo();
			}
					
			Session session = ht.getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			Long linkingId = null;
			List<Long> resultsFunc = query.list();
			
		    Collections.sort(resultsFunc);
		    Collections.reverse(resultsFunc);
		    
			return resultsFunc;
		}
		
		public static String checkIfConditionsChanged(
				List<StandardClause> stdClausesList, PolicyVO policyVO,
				SectionVO sectionVO) {
			String conditionChanged = "N";
			List<StandardClause> existingClauseList = new com.mindtree.ruc.cmn.utils.List<StandardClause>(
					StandardClause.class);
			getExistingConditionsClauses(existingClauseList, policyVO,
					sectionVO.getSectionId(), policyVO.getScheme().getSchemeCode(),
					policyVO.getPolicyTypeCode());
			List<String> conds = Arrays.asList(Utils
					.getMultiValueAppConfig("NOREF_CONDITIONS"));
			for (StandardClause sc : stdClausesList) {
				String temp = (sc.getClauseCode().toString())
						+ "-"
						+ Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION);
				if (conds.contains(temp)) {
					continue;
				}
				for (StandardClause esc : existingClauseList) {
					if (sc.getClauseCode().equals(esc.getClauseCode())) {
						if (!(sc.isSelected() == esc.isSelected())
								|| (!Utils.isEmpty(sc.getAmount())
										&& !Utils.isEmpty(esc.getAmount()) && !sc
										.getAmount().equalsIgnoreCase(
												esc.getAmount()))) {
							conditionChanged = "Y";
							break;
						} else {
							break;
						}
					}
				}
			}
			return conditionChanged;
		}
		
		private static void getExistingConditionsClauses( List<StandardClause> existingClauseList, BaseVO baseVO, Integer sectionId, Integer scheme, Integer policyType){
	
			List<TTrnPolicyConditionQuo> ttrnConditionsQou = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyConditionQuo>( TTrnPolicyConditionQuo.class );
			List<TTrnPolicyCondition> ttrnConditions = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyCondition>( TTrnPolicyCondition.class );
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = ht.getSessionFactory().openSession();
			
			
			PolicyVO policyVO = null;
			boolean isQuote = false;
			Long endtId = null, policyId = null;
	
			if( baseVO instanceof PolicyVO ){
				//Phase 1 & 2 Specific block
				policyVO = (PolicyVO) baseVO;
				isQuote = policyVO.getIsQuote();
				policyId = policyVO.getPolLinkingId();//DAOUtils.getSectionPolicyId( policyVO, sectionId, ht );
				//endtId = policyVO.getPrevEndtId();
				//Added as part of fix for tick/untick clauses rules
				endtId =  getPreviousEndtId(ht,policyVO);
			}		
			else{
				throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO only" );
			}
	
			Integer hdrCode = null;
			hdrCode = getHdrCodeForSection( sectionId, policyType,ht );
			
			
			List<TMasPolicyCondition> masConditions = null;
	
				masConditions = (List<TMasPolicyCondition>) ht.find(
						"from TMasPolicyCondition masCon where masCon.id.pcSchCode=? and masCon.id.pcClCode = ? and masCon.pcPhrCode in (?,1)", scheme,
						Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
				if( !Utils.isEmpty( policyId ) ){
					if( isQuote ){
						
						
						Query query = session.createQuery("from com.rsaame.pas.dao.model.TTrnPolicyConditionQuo quoCon where quoCon.id.tpcPolicyId="+policyId+" and (quoCon.id.tpcEndtId = "+endtId+" or "
										+ "(quoCon.id.tpcEndtId < "+endtId+" and trunc(quoCon.tpcValidityEndDate) = '31-DEC-2049' )) and quoCon.id.tpcCode in "
										+ "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = "+scheme+""
										+ " and mas.id.pcClCode = "+Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ))+" and mas.pcPhrCode = "+hdrCode+")");
							ttrnConditionsQou = (List<TTrnPolicyConditionQuo>) query.list();				
						
						}
					else{
						ttrnConditions = (List<TTrnPolicyCondition>) ht.find(
								"from com.rsaame.pas.dao.model.TTrnPolicyCondition polCon where polCon.id.tpcPolicyId=? and (polCon.id.tpcEndtId = ? or "
										+ "(polCon.id.tpcEndtId < ? and trunc(polCon.tpcValidityEndDate) = ?)) and polCon.id.tpcCode in "
										+ "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = ?" + " and mas.id.pcClCode = ? and mas.pcPhrCode in( ?,1))", policyId,
								endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
					}
				}		
	
			for( TMasPolicyCondition condition : masConditions ){
				boolean selected = false;
				boolean amountSet = false;
				StandardClause standardClause = new StandardClause();
				if( isQuote ){
					if( Utils.isEmpty( ttrnConditionsQou ) ){
						if( condition.getPcDefaultInd() == 2 ||  condition.getPcDefaultInd() == 1 ){
							selected = true;
						}
					}
	
					for( TTrnPolicyConditionQuo con : ttrnConditionsQou ){
						if( !Utils.isEmpty( con ) ){
							if( condition.getId().getPcCode() == con.getId().getTpcCode() ){
								if( !Utils.isEmpty( con.getTpcAmount() ) && con.getTpcStatus().intValue() != 4 ){
									standardClause.setAmount( con.getTpcAmount() );
									amountSet = true;
								}
								if( con.getTpcStatus().intValue() != 4 ){
									selected = true;
									break;
								}
							}
						}
	
					}
				}
				else{
					if( Utils.isEmpty( ttrnConditions ) ){
						if( condition.getPcDefaultInd() == 2 ){
							selected = true;
						}
					}
					for( TTrnPolicyCondition con : ttrnConditions ){
						if( condition.getId().getPcCode() == con.getId().getTpcCode() ){
							if( !Utils.isEmpty( con.getTpcAmount() ) && con.getTpcStatus().intValue() != 4 ){
								standardClause.setAmount( con.getTpcAmount() );
								amountSet = true;
							}
							if( con.getTpcStatus().intValue() != 4 ){
								selected = true;
								break;
							}
						}
					}
	
				}
	
				standardClause.setClauseType( "C" );
	
				if( Utils.isEmpty( standardClause.getAmount() ) && !Utils.isEmpty( condition.getPcAmount() ) ){
					/*
					 * set amount field to default value from master table if clause is deselected else
					 * set amount field to whatever is fetched from db.
					 */
					/*if( !selected ){
						standardClause.setAmount(  condition.getPcAmount() );
					}*/
					if( !amountSet ){
						standardClause.setAmount( condition.getPcAmount() );
					}
				}
	
				if( !Utils.isEmpty( condition.getId().getPcCode() ) ){
					standardClause.setClauseCode( Long.valueOf( String.valueOf( condition.getId().getPcCode() ) ) );
				}
	
				if( !Utils.isEmpty( condition.getPcEText() ) ){
					standardClause.setDescription( condition.getPcEText() );
				}
	
				standardClause.setIsDefault( condition.getPcDefaultInd() );
				standardClause.setSelected( selected );
				standardClause.setSectionID( sectionId.longValue() );
	
				existingClauseList.add( standardClause );
			}
	
		}
		
		public static Long getPreviousEndtId(HibernateTemplate ht,
				PolicyVO policyVO) {
			
			String sqlquery = null;
			Long policyId = getBaseSectionPolicyIdForClause(policyVO,ht);
			
			Long endtId = SvcUtils.getLatestEndtId( policyVO );
			if( policyVO.getIsQuote() )
				sqlquery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_PREV_ENDT_ID_QUO_END + policyId + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			else
				sqlquery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_PREV_ENDT_ID_END + policyId + "," + endtId+ com.Constant.CONST_FROM_DUAL2;
	
			Session session = ht.getSessionFactory().openSession();
			Query query = session.createSQLQuery( sqlquery );
			List<Object> result = query.list();
			if( Utils.isEmpty( result ) ){
				LOGGER.debug( "Prev Endorsement id is nul_2" );
				return 0L;
			}
	
			return ( (BigDecimal) result.get( 0 ) ).longValue();
			
		}
		
		
		/**
		 * 
		  * @param policyVO
		 * @param hibernateTemplate 
		 * @return
		 */
		public static Long getBaseSectionPolicyIdForClause( PolicyVO policyVO, HibernateTemplate hibernateTemplate ){
	
			Long endtId = SvcUtils.getLatestEndtId( policyVO );
	
			String sqlQuery = "";
	
			if( policyVO.getIsQuote() ){
				sqlQuery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_BASE_SEC_POL_ID_QUO_END + policyVO.getPolLinkingId() + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			}
			else{
				sqlQuery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_BASE_SEC_POL_ID_END + policyVO.getPolLinkingId() + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			}
	
			Session session = hibernateTemplate.getSessionFactory().openSession();
			Long policyId = null;
			Query query = session.createSQLQuery( sqlQuery );
	
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				policyId = Long.valueOf( resultsFunc.get( 0 ).toString() );
				if( Utils.isEmpty( policyId ) ){
					throw new BusinessException( "cmn.unknownError", null, "The policy no is 0 or null for inserting into endorsment details table_2" );
				}
			}
			return policyId;
		}
	
		private static Integer getHdrCodeForSection( Integer sectionId, Integer policyType,HibernateTemplate ht )
		{
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			String GET_SECTION_HDR = "select sec_pc_hdr from t_mas_pkg_pol_section where sec_pt_code=" + policyType + " and sec_id=" + sectionId;
			Session session = ht.getSessionFactory().openSession();
			Query query = session.createSQLQuery( GET_SECTION_HDR );
			List<Object> result = query.list();
			return ( converter.getBFromA( result.get( 0 ) ) );
		}
		
			
		public static TMasOccupancy getOccDetails( Short ocpCode ){
			
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			return (TMasOccupancy) hibernateTemplate.find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
		}
		
		/**
		 * Added as part of Phase 4: WC Monoline implementation, to save the default clauses.
		 * @param baseVO
		 */
		public static void saveUpdateDefaultClauses(BaseVO baseVO) {
	
			CommonVO commonVO = (CommonVO)baseVO;
			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("insUpdateClausesMonoline");
			
			try{
				Map results = sp.call(commonVO.getPolicyId(), commonVO.getEndtId() );
				
				if (Utils.isEmpty(results)) {
					LOGGER.debug("The result of the stored procedure is null");
				}
				
			} catch (DataAccessException e){
				throw new BusinessException("pas.insClauses.exception",e, "An exception occured while executing insUpdateClausesMonoline stored proc.");
			}
		}
		/**
		 * Method to get the previous data using the risk id. 
		 * Implemented as a part of WC Monoline.
		 * @param homeInsuranceVO
		 * @return
		 */
		public static List<Object[]> getPreviousDataRiskId( Long policyId, Long endtId, Long riskId ){
	
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( QUERY_FETCH_PREVIOUS_PREMIUM_RISK_ID );
			query.setParameter( com.Constant.CONST_POLICYID, policyId );
			query.setParameter( com.Constant.CONST_ENDTID, endtId );
			query.setParameter("rskId",riskId);
			
			List<Object[]> resultList = query.list();
			return resultList;
			
		}
		/**
		 * Method to get the previous tariff using policyNo and endtId
		 * @param policyNo
		 * @param endtId
		 * @return
		 */
		public static Integer getPreviousTariff( Long policyNo, Long endtId ){
	
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( FETCH_TARIFF_POL );
			query.setParameter( com.Constant.CONST_POL_POLICY_NO, policyNo );
			query.setParameter( com.Constant.CONST_POL_END_ID, endtId );
			
			List<Object> result = query.list();
			return ( (BigDecimal) result.get( SvcConstants.zeroVal ) ).intValue();
			
		}
		/*added to fix Endorsement Text CR */
		public static void addEndtTextHeaderFooter(PolicyVO policyVO){
			PASStoredProcedure sp2 = null;
			sp2 = (PASStoredProcedure) Utils.getBean( "addEndtTextHeaderFooter" );
			try{
				/*Change to fix confirm amend functionality for approved policy */
				Map resultsVED = sp2.call( policyVO.getPolLinkingId(), !Utils.isEmpty(policyVO.getNewEndtId())?policyVO.getNewEndtId():policyVO.getEndtId(), GEN_SECTION_ID);
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_61" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_66" );
			}
		}
		
		
		public static void addDeleteEndtHeaderText(PolicyVO policyVO, Boolean flag,HibernateTemplate hibernateTemplate){
			Long EndtID = null;
			Boolean isExist = Boolean.FALSE;
			
			if (!policyVO.isPolicyExtended()){
				if(Utils.isEmpty( policyVO.getNewEndtId() ))
				{
					EndtID = policyVO.getEndtId();
				}
				else
				{
					EndtID = policyVO.getNewEndtId();
				}
				List<Integer> policyIds = hibernateTemplate.find( "select id.polPolicyId from TTrnPolicyQuo where polLinkingId = ? and id.polEndtId = ?",policyVO.getPolLinkingId(),EndtID );
				String[] paramNames ={"ids",com.Constant.CONST_ENDTID};
				Object[] paramValues ={policyIds,EndtID};
				List<TTrnEndorsementDetail> endtTextList = hibernateTemplate.findByNamedParam( "from TTrnEndorsementDetail where id.edlPolicyId in (:ids) and id.edlEndorsementId = :endtId ",paramNames,paramValues);
						
				if(endtTextList.size() > SvcConstants.ZERO){
					for(TTrnEndorsementDetail text : endtTextList){				  
						if(text.getId().getEdlSerialNo() == SvcConstants.ZERO.intValue()
								&& text.getId().getEdlSecId() == SvcConstants.ZERO.intValue()
								){
							isExist = Boolean.TRUE;
							break;
						}
					}
					if(!isExist){
						addEndtTextHeaderFooter(policyVO);
					}
				}
			}
		}
		
		public static void updateTotalSITextforendorsementFlow( Long policyId, PolicyVO policyVO, int sectionId, Long riskGroupId, Long baseRiskGroupId ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "endoTextSIProc" );
			try{
				Map resultsVED = sp.call( policyId, policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionId, riskGroupId, baseRiskGroupId );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_62" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.endoTextPrmProc.exception", e, "An exception occured while executing stored proc_67" );
			}
	
		}
		
		/**
		 * Method to invalidate the old content and PP if it is selected.
		 * @param policyId
		 * @param endtId
		 * @param isQuote
		 * @return
		 */
		public static void checkOldContentPPCode( Long policyId, Long endtId, boolean isQuote ){
	
			boolean oldContentPPCode = false;
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			String qryString=null;
			String updQryString=null;
			long tpcCode = 0;
			if(isQuote)
			{
				qryString = "SELECT tpc_code from T_TRN_POLICY_CONDITION_QUO where TPC_POLICY_ID=:policy_id and TPC_ENDT_ID=:endt_id and TPC_STATUS <> 4 and TPC_CODE IN (6,7)";
			}
			SQLQuery query = session.createSQLQuery( qryString );
			query.setParameter( "policy_id", policyId );
			query.setParameter( "endt_id", endtId );
			
			List<Object> result = query.list();
			if(result.size() > SvcConstants.ZERO)
			{
				int i=0;
				for(Object obj : result)
				{	
					tpcCode = Long.valueOf( result.get( i ).toString() );			
					if(tpcCode==6)			
					{
						updQryString = "Update T_TRN_POLICY_CONDITION_QUO set tpc_code =21 where TPC_POLICY_ID=:policy_id and TPC_ENDT_ID=:endt_id and TPC_CODE =:tpc_code";
					}
					else
					{
						updQryString = "Update T_TRN_POLICY_CONDITION_QUO set tpc_code =22 where TPC_POLICY_ID=:policy_id and TPC_ENDT_ID=:endt_id and TPC_CODE =:tpc_code";
					}			
					SQLQuery query1 = session.createSQLQuery( updQryString );
					query1.setParameter( "policy_id", policyId );
					query1.setParameter( "endt_id", endtId );
					query1.setParameter( "tpc_code", tpcCode );
					query1.executeUpdate();
					i++;
				}
			}
		}
		
		/**
		 * Method to check if the policy is created before the new content and PP condition was implemented.
		 * @param policyNo
		 * @return
		 */
		public static boolean checkOldContentPPPED( Long policyNo){
	
			boolean oldContentPPCode = false;
			Date newcontentDate = null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
			try {
				newcontentDate = simpleDateFormat.parse(Utils.getSingleValueAppConfig("NEW_CONTENT_PP_DATE"));
			}
			catch(ParseException e)
			{
				e.printStackTrace();
				throw new BusinessException( "cmn.storedproc.error", null , "Error while executing the query." );
			}
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			String qryString=null;
			qryString = "SELECT pol_effective_date from T_TRN_POLICY where POL_POLICY_NO=" + policyNo.longValue() + " and POL_ENDT_ID=0 order by pol_policy_year";
			
			SQLQuery query = session.createSQLQuery( qryString );
			List<Object> result = query.list();
			if( !Utils.isEmpty( result ) ){
				if(!Utils.isEmpty( result.get( 0 ))){
					if(((Date)result.get( 0 )).compareTo(newcontentDate) < 0)
					{
						oldContentPPCode= true;
					}
				}
			}
			return oldContentPPCode;
		}
		
		/**
		 * 
		 * @param pmmName
		 * @param commonVO
		 * @param generalInfo
		 * @param schemeVO
		 */
		public static void getPartnerMgmtDetail( CommonVO commonVO, GeneralInfoVO generalInfo, SchemeVO schemeVO ){
			LOGGER.info("Entered DAOUtils.getPartnerMgmtDetail method.");
			
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Integer classCode = Integer.valueOf(Utils.getSingleValueAppConfig(commonVO.getLob().name() + "_CLASS_CODE"));
			Integer policyType = Integer.valueOf(Utils.getSingleValueAppConfig(commonVO.getLob().name() + "_POLICY_TYPE"));
	
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery( FETCH_PARTNER_DETAILS );
			query.setParameter( "partnerName", generalInfo.getSourceOfBus().getPartnerName() );
			query.setParameter( com.Constant.CONST_STATUS, 1 );
			query.setParameter( com.Constant.CONST_PTCODE, policyType );
			query.setParameter( com.Constant.CONST_CLASSCODE, classCode );
			
			LOGGER.info("DAOUtils.getPartnerMgmtDetail method, before fetching partner details.");
			List<Object[]> result = query.list();
			
			
			if( !Utils.isEmpty( result ) &&  !Utils.isEmpty( result.get(0) ) ){
				Object [] data = result.get(0);
				generalInfo.getSourceOfBus().setPartnerName(data[0].toString());
				if(data[2].toString().equals( SvcConstants.DIST_CHANNEL_BROKER.toString()))
				{
					generalInfo.getSourceOfBus().setBrokerName(Integer.valueOf(data[1].toString()));
				}
				else if(data[2].toString().equals( SvcConstants.DIST_CHANNEL_AGENT.toString()) ||data[2].toString().equals( SvcConstants.DIST_CHANNEL_AFFINITY_AGENT.toString()))
				{
					generalInfo.getSourceOfBus().setDirectSubAgent(Long.valueOf(data[1].toString()));
				}
				generalInfo.getSourceOfBus().setPartnerId(data[2].toString()+ classCode.toString() + policyType.toString() + data[1].toString());
				generalInfo.getSourceOfBus().setDistributionChannel(Integer.valueOf(data[2].toString()));
				generalInfo.getSourceOfBus().setCallCentreNo(data[3].toString());
				generalInfo.getSourceOfBus().setReplyToEmailId(data[4].toString());
				generalInfo.getSourceOfBus().setCcEmailId(Utils.isEmpty(data[5])? null: data[5].toString());
				generalInfo.getSourceOfBus().setPolicyTerms(Utils.isEmpty(data[6])? null: data[6].toString());
				generalInfo.getSourceOfBus().setCustomerSource(data[8].toString());
				generalInfo.setExtAccExecCode(Integer.valueOf(data[7].toString()));
				schemeVO.setSchemeCode(Integer.valueOf(data[9].toString()));
				schemeVO.setTariffCode(Integer.valueOf(data[10].toString()));
				if( !Utils.isEmpty(data[11]))
				{
					generalInfo.getSourceOfBus().setDefaultAssignToUser((Long.valueOf( data[11].toString())));
				}
				if( !Utils.isEmpty(data[12]))
				{
					generalInfo.getSourceOfBus().setDefaultOnlineDiscount((Double.valueOf( data[12].toString())));
				}
				generalInfo.getSourceOfBus().setFromEmailID(Utils.isEmpty(data[13])? null: data[13].toString());
				generalInfo.getSourceOfBus().setSourceOfBusiness(Integer.valueOf(data[14].toString()));
				generalInfo.getSourceOfBus().setFaqUrl(Utils.isEmpty(data[15])? null: data[15].toString());
				generalInfo.getSourceOfBus().setPolicyTermUrl(Utils.isEmpty(data[16])? null: data[16].toString());
			}
			LOGGER.info("Exiting DAOUtils.getPartnerMgmtDetail method.");
		}
		
		
		/**
		 * 
		  * @param policyVO
		 * @param hibernateTemplate 
		 * @return
		 */
		public static boolean isStudentLiabilitySelected( Long endtId, Long policyId, PolicyVO policyVO, HibernateTemplate hibernateTemplate ){
	
	
			String sqlQuery = null;
	
			if( policyVO.getIsQuote() ){
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("SELECT uqt_uwq_answer");
				stringBuilder.append(" FROM t_trn_uw_questions_quo uw1");
				stringBuilder.append(" WHERE uw1.uqt_pol_policy_id  = ");
				stringBuilder.append(policyId);
				stringBuilder.append(" AND uqt_validity_expiry_date = '31-DEC-49'");
				stringBuilder.append(" AND uw1.UQT_POL_ENDT_ID      <= ");
				stringBuilder.append(endtId);
				stringBuilder.append(" AND uw1.uqt_status <> 4 ");
				stringBuilder.append(" AND uw1.uqt_uwq_code = 57 ");
				stringBuilder.append(" AND uw1.uqt_uwq_answer = 'yes'");
				sqlQuery= stringBuilder.toString();
			}
			else{
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("SELECT uqt_uwq_answer");
				stringBuilder.append(" FROM t_trn_uw_questions uw1");
				stringBuilder.append(" WHERE uw1.uqt_pol_policy_id  = ");
				stringBuilder.append(policyId);
				stringBuilder.append(" AND uqt_validity_expiry_date = '31-DEC-49'");
				stringBuilder.append(" AND uw1.UQT_POL_ENDT_ID      <= ");
				stringBuilder.append(endtId);
				stringBuilder.append(" AND uw1.uqt_status <> 4 ");
				stringBuilder.append(" AND uw1.uqt_uwq_code = 57 ");
				stringBuilder.append(" AND uw1.uqt_uwq_answer = 'yes'");
				sqlQuery= stringBuilder.toString();
			}
	
			Session session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createSQLQuery( sqlQuery );
	
			List<Object> resultsFunc = query.list();
			if( !Utils.isEmpty( resultsFunc ) ){
				return true;
			}
			return false;
		}
		
		// Added by Anveshan for Student Liability
		public static void updateStudentLiabilityExclusion( SectionVO sectionVO, Long policyId, PolicyVO policyVO ){
	
			PASStoredProcedure sp = null;
	
			DAOUtils.deletePrevEndtText( policyId, policyVO.getNewEndtId(), sectionVO.getSectionId(), LOC_ID_LONG );
	
			sp = (PASStoredProcedure) Utils.getBean( "updateSLCondsAndExclusions" );
			try{
				Map resultsVED = sp.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ) );
				if( Utils.isEmpty( resultsVED ) ){
					LOGGER.debug( "The result returned by the stored procedure is empt_63" );
				}
	
				ViewClausesDAO.updateEndtText( policyVO, sectionVO, "clauseTextGenProc" );
				ViewClausesDAO.updateEndtText( policyVO, sectionVO, "clauseDelTextGenProc" );
				
				ViewClausesDAO.updateEndtTextForLimitChange( policyVO, sectionVO, policyVO.getStandardClause() );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "PKG_ENDT_GEN.update_ins_pl_uwq_pol", e, "An exception occured while executing stored proc_68" );
			}
	
		}
		
		private static void initMapSectionToRiskCode(){
			//String SECTION_RISK_MAP =  Utils.getSingleValueAppConfig("SECTION_RISKCODE_MAP") ;
			String SECTION_RISK_MAP = "1:1,2:1,3:1,4:1,5:1,6:31,7:38,8:42,9:24,10:27,11:45,12:28";
			
			for (String sectionAndRiskCode : SECTION_RISK_MAP.split(",")) {
				String []splitArr = sectionAndRiskCode.split(":");
				SectionToRiskCodeMap.put(Integer.valueOf(splitArr[0]), Integer.valueOf(splitArr[1]));
			}
			
		} 
		
		public static Integer getRisckCodeFromSectionId(Integer sectionId){
			return SectionToRiskCodeMap.get(sectionId);
		}
	
		public static Date getPreparedDate(HibernateTemplate ht,
				Long quoteNo) {
			Date polPreparedDate = null;
			
			List<TTrnPolicyQuo> policy = null;
			policy = ht.find( "from TTrnPolicyQuo pol where  pol.id.polEndtId=? and pol.polQuotationNo = ? and pol.polIssueHour = 3", 0L, quoteNo );
			if( Utils.isEmpty( policy ) ){
	
				throw new BusinessException( "cmn.unknownError", null, "Pol_Prepared_Dt not found for quote " + quoteNo);
			}
			
			for (TTrnPolicyQuo tTrnPolicyQuo : policy) {
				if(polPreparedDate == null ) {
					polPreparedDate = tTrnPolicyQuo.getPolPreparedDt();
				}
				
				if(polPreparedDate.after( tTrnPolicyQuo.getPolPreparedDt()) ) {
					polPreparedDate = tTrnPolicyQuo.getPolPreparedDt();
				}
			}
			
			return polPreparedDate;
		}
		
		// Added by Srinivas for Additional cover select updating exlusions & Warranties
		 // Adding the below method as part of CR#116356 by M1026668 in 3.8 
			public static void updateAdditionalCoversExclusionWarranty( SectionVO sectionVO, Long policyId, PolicyVO policyVO ){
	
				try{
	
					DAOUtils.deletePrevEndtText( policyId, policyVO.getNewEndtId(), Integer.valueOf(Utils.getSingleValueAppConfig("CTP")) , LOC_ID_LONG );
					ViewClausesDAO.updateEndtText( policyVO, sectionVO, "clauseTextGenProc" );
					ViewClausesDAO.updateEndtText( policyVO, sectionVO, "clauseDelTextGenProc" );
					
					//ViewClausesDAO.updateEndtTextForLimitChange( policyVO, sectionVO, policyVO.getStandardClause() );
				}
				catch( DataAccessException e ){
					throw new BusinessException( "clauseTextGenProc/clauseDelTextGenProc", e, "An exception occured while executing stored proc_69" );
				}
	
			}
			
			/*
			 * Added by Vishwa to NEXUS PROMO  for Advenet ID 128926 
			 */
			public static String fetchPartnerInfo(String entityType ){
				String promoFlag=null;
				try{
					 List resultSet = DAOUtils.getSqlResultForPas(QueryConstants.PARTNER_PROMO_CODE,entityType);
	                 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
	                       LOGGER.debug("DAOUTILS fetchPartnerInfo() size:_1"+resultSet.get(0));
	                        promoFlag = (String) resultSet.get(0).toString();
	                        
	                 } 
					}
				catch( DataAccessException e ){
					throw new BusinessException( "fetchPartnerInfo", e, "An exception occured while fetchPartnerInfo" );
				}
				return promoFlag;
				
			}
		
			//Travel scope 131378 
			
			public static Date getPreparedDateForCovers(HibernateTemplate ht,
					Long quoteNo) {
	
				String sqlQuery = " select  pol_prepared_dt from t_trn_policy_quo"
						+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_quotation_no=:quoteNo ";
	
				Session session = ht.getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery( sqlQuery );
				query.setParameter( com.Constant.CONST_QUOTENO, quoteNo );
	
				Date quoPreparedDate = null;
				List<Object> resultsFunc = query.list();
	
				if( !Utils.isEmpty( resultsFunc ) ){
	
					quoPreparedDate = (Date) ( resultsFunc.get( 0 ) );
					if( Utils.isEmpty( quoPreparedDate ) ){
						throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_2" );
					}
				}
	
				return quoPreparedDate;
			}
			
			public static Date getPreparedDateForCovers(Long quoteNo ){
				Date pol_prepared_dt=null;
				String sqlQuery = " select  pol_prepared_dt from t_trn_policy_quo "
						+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_quotation_no=? ";
	
				try{
					 List resultSet = DAOUtils.getSqlResultForPas(sqlQuery,quoteNo);
	                 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
	                       LOGGER.debug("DAOUTILS fetchPartnerInfo() size:_2"+resultSet.get(0));
	                       pol_prepared_dt = (Date) resultSet.get(0);
	                       LOGGER.debug("pol_prepared_dt "+pol_prepared_dt);
	                 } 
					}
				catch( DataAccessException e ){
					throw new BusinessException( "fetchPartnerInfo", e, "An exception occured while fetchPartnerInfo" );
				}
				return pol_prepared_dt;
				
			}
			/*Added to fetch the prepare Date to pass to Rating service - Ticket Id: 140443 */
			
			public static Date getPreparedDateForRating(Long quoteNo, Integer policyType ){
				Date pol_prepared_dt=null;
				if(Utils.isEmpty(policyType)){
					policyType = Integer.parseInt(SvcConstants.SBS_POL_TYPE);
				}
				String sqlQuery = "select  pol_prepared_dt from t_trn_policy_quo "
						+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_quotation_no=? and pol_policy_type = ?";
	
				try{
					 List resultSet = DAOUtils.getSqlResultForPas(sqlQuery,quoteNo,policyType);
	                 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
	                       LOGGER.debug("DAOUTILS fetchPartnerInfo() size:_3"+resultSet.get(0));
	                       pol_prepared_dt = (Date) resultSet.get(0);
	                        
	                 } 
				}
				catch( DataAccessException e ){
					throw new BusinessException( "cmn.unknownError", null, "Pol_Prepared_Dt not found for quote " + quoteNo);
				}
				/*if(Utils.isEmpty(pol_prepared_dt)){
					pol_prepared_dt = new Date();
				}*/
				return pol_prepared_dt;
				
			}
		
			
			/*
			 * Added by 140968
			 */
			public static double fetchPolPrmForQuote(Long quoteNo, Long endtID){
				String sqlQuery = "select  pol_premium from t_trn_policy_quo"
						+ " where pol_issue_hour=3 and  pol_quotation_no=? and  pol_endt_id=?";
				double prmAmt=0.0;
	
					List result = DAOUtils.getSqlResultForPas(sqlQuery,quoteNo,endtID);
	                 if( !Utils.isEmpty( result ) && result.size() > 0 ){
	                       LOGGER.debug("DAOUTILS fetchPolPrmForQuote() prm amt value: "+result.get(0));
	                       
	                       prmAmt = ( (BigDecimal) result.get( 0 ) ).doubleValue();  
	            
					}
	             	return prmAmt;
			}
			
			/**
			 * Common method to get VAT/GOVT tax
			 * @param policyDataVO
			 * @param coverCode
			 * @return
			 */
			public static BigDecimal getTax(PolicyDataVO policyDataVO, Short coverCode ){
	
				StringBuilder queryString = new StringBuilder();
					
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
				if (!Utils.isEmpty(policyDataVO.getIsQuote()) && !policyDataVO.getIsQuote()) {
						queryString
							.append(com.Constant.CONST_SELECT_DISTINCT_PR_PRM_RATE_FROM_T_TRN_PREMIUM_PR_T_TRN_POLICY_POL_WHERE_PR_PRM_POLICY_ID_POL_POL_POLICY_ID_AND_POL_POL_ISSUE_HOUR_END)
							.append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
							.append(com.Constant.CONST_AND_PR_PRM_COV_CODE_END)
							.append(coverCode)
							.append(com.Constant.CONST_PRM_ENDT_ID_EQUALS)
							.append(SvcConstants.zeroVal)
							.append(com.Constant.CONST_AND_POL_POL_POLICY_ID_END)
							.append(policyDataVO.getPolicyId())
							.append(com.Constant.CONST_AND_PR_PRM_CL_CODE_END)
							.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE));
				} else {
					if(coverCode.equals(SvcConstants.SC_PRM_COVER_GOVT_TAX)) {
						queryString.append( "select distinct code from ss_v_mas_lookup lkup where lkup.category = 'GOVTTAX' and lkup.level_1 = '")
							.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE)).append( "'" );
					} else if(coverCode.equals(SvcConstants.SC_PRM_COVER_VAT_TAX)) {
						queryString.append( "select distinct code from ss_v_mas_lookup lkup where lkup.category = 'VATTAX' and lkup.level_1 = '")
							.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE)).append( "'" );
					}
				}
					
				Query query = session.createSQLQuery( queryString.toString() );
				List<Object> result = query.list();
				BigDecimal tax = new BigDecimal( 0 );
				if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
					tax = (BigDecimal) result.get( 0 );
				}
				return tax;
			}
			
			public static void sendMailForBatchSubmit(String policyType){
				
				String emailContent = SvcConstants.BATCH_RENEWAL_EMAIL_CONTENT;
				
				if (!Utils.isEmpty(policyType) && policyType == SvcConstants.SBS_POL_TYPE){
					emailContent = emailContent.replace("%LOB%",SvcConstants.SBS_PRODUCT);
				}
				else{
					emailContent = emailContent.replace("%LOB%","");
				}
				//String toAddress = "RSAePlatform.Support@mindtree.com";
				
				PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
				
				String ccAddress[]=SvcConstants.BATCH_RENEWAL_EMAIL_CC.split(",");
				String fromAddress =  Utils.getSingleValueAppConfig( "EMAIL_FROM_RENL_NOTICE" );
				String toAddress = SvcConstants.BATCH_RENEWAL_EMAIL_TO;
				
				MailVO mailVO=new MailVO();
				mailVO.setToAddress(toAddress);
				mailVO.setFromAddress(fromAddress);
				mailVO.setCcAddress(ccAddress);
				mailVO.setMailContent(new StringBuilder(emailContent));
				mailVO.setSubjectText(SvcConstants.BATCH_RENEWAL_EMAIL_SUBJECT);
				mailVO.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
				
				try {	
					LOGGER.debug("calling sendMail");
					mailVO = (MailVO) mailer.invokeMethod("sendMail", mailVO);					
					LOGGER.debug("sendMail called");
				} catch (Exception e)
				{
					e.printStackTrace();
					mailVO.setMailStatus("fail");
					LOGGER.error("Exception Occured sending the mail:"+ e.getMessage());				
				}			
			}
	
	
			/*VAT*/
			public static BigDecimal getVatTax(PolicyDataVO policyDataVO) {
	
	            StringBuilder queryString = new StringBuilder();
	
	            HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils
	                        .getBean(com.Constant.CONST_HIBERNATETEMPLATE);
	            Session session = hibernateTemplate.getSessionFactory()
	                        .getCurrentSession();
	            if (!Utils.isEmpty(policyDataVO.getIsQuote())
	                        && !policyDataVO.getIsQuote()) {
	
	                  queryString
	                              .append(com.Constant.CONST_SELECT_DISTINCT_PR_PRM_RATE_FROM_T_TRN_PREMIUM_PR_T_TRN_POLICY_POL_WHERE_PR_PRM_POLICY_ID_POL_POL_POLICY_ID_AND_POL_POL_ISSUE_HOUR_END)
	                              .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
	                              .append(com.Constant.CONST_AND_PR_PRM_COV_CODE_END)
	                              .append(SvcConstants.SC_PRM_COVER_VAT_TAX)
	                              .append(com.Constant.CONST_PRM_ENDT_ID_EQUALS)
	                              .append(SvcConstants.zeroVal)
	                              .append(com.Constant.CONST_AND_POL_POL_POLICY_ID_END)
	                              .append(policyDataVO.getPolicyId())
	                              .append(com.Constant.CONST_AND_PR_PRM_CL_CODE_END)
	                              .append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE));
	
	            } else {
	                  queryString
	                              .append("select distinct code from ss_v_mas_lookup lkup where lkup.category = 'VATTAX' and lkup.level_1 = '")
	                              .append(Utils.getSingleValueAppConfig("POLICY_TYPE_TRAVEL"))
	                              .append("'");
	            }
	
	            Query query = session.createSQLQuery(queryString.toString());
	            List<Object> result = query.list();
	            BigDecimal vatTax = new BigDecimal(0);
	            if (!Utils.isEmpty(result) && !Utils.isEmpty(result.get(0))) {
	                  vatTax = (BigDecimal) result.get(0);
	            }
	            return vatTax;
	      }
			
			//142244  Vat Code Implementation 
			
			/**142244
			 * Method  Home
			 * @param 
			 * @return  
			 */
					public static Date getPreparedDateofQuo(Long number,Boolean isQuote) {
						 String sqlQuery = null;
							HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
						if(isQuote){
	
						 sqlQuery = " select  pol_issue_date from t_trn_policy_quo"
									+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_quotation_no=:number ";
						
							
						}
						
						else{
							 sqlQuery = " select  pol_issue_date from t_trn_policy"
									+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_policy_no=:number ";
							
						}
							Session session = ht.getSessionFactory().getCurrentSession();
							SQLQuery query = session.createSQLQuery( sqlQuery );
							query.setParameter( com.Constant.CONST_NUMBER, number );
	
							Date quoissueDate = null;
							List<Object> resultsFunc = query.list();
	
							if( !Utils.isEmpty( resultsFunc ) ){
	
								quoissueDate = (Date) ( resultsFunc.get( 0 ) );
								if( Utils.isEmpty( quoissueDate ) ){
									throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_3" );
								}
								
							}
							return quoissueDate;
					}
							
					
					//142244  Vat Code Implementation 
					
					/**142244
					 * Method  Home
					 * @param 
					 * @return  
					 */
							@Transactional
							public static Date getPolIssueDate(Long quoteNo,Boolean isQuote,Long endtId) {
								 String sqlQuery = null;
									HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
								if(isQuote){
	
								 sqlQuery = "select  pol_issue_date from t_trn_policy_quo "
											+ " where pol_endt_id=:endtId and pol_issue_hour=3 and  pol_quotation_no=:quoteNo ";
								
									
								}
								
								else{
									 sqlQuery = "select  pol_issue_date from t_trn_policy "
											+ " where  pol_quotation_no=:quoteNo and pol_endt_id=:endtId and pol_issue_hour=3 ";
									
								}
									Session session = ht.getSessionFactory().openSession();
									SQLQuery query = session.createSQLQuery( sqlQuery );
									query.setParameter( com.Constant.CONST_QUOTENO, quoteNo );
									query.setParameter( com.Constant.CONST_ENDTID, endtId );
	
									Date quoissueDate = null;
									List<Object> resultsFunc = query.list();
	
									if( !Utils.isEmpty( resultsFunc ) ){
	
										quoissueDate = (Date) ( resultsFunc.get( 0 ) );
										if( Utils.isEmpty( quoissueDate ) ){
											throw new BusinessException( "cmn.unknownError", null, "Unable to fetch pol issue date from policy table." );
										}
										
									}
									return quoissueDate;
							}
							
							@Transactional
							public static Date getPolIssueDateForSBSQuo(Long quoteNo,Boolean isQuote,Long polLinkingID,Long policyNo ) {
								 String sqlQuery = null;
									HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
								if(isQuote){
	
								 sqlQuery = " select  pol_issue_date from t_trn_policy_quo "
											+ " where pol_quotation_no=:quoteNo and pol_linking_id=:polLinkingID and pol_issue_hour=3 and pol_validity_expiry_date = '31-DEC-49' and pol_policy_no = :policyNo";
								
									
								}
								
								else{
									 sqlQuery = "select  pol_issue_date from t_trn_policy_quo"
												+ " where pol_quotation_no=:quoteNo and pol_linking_id=:polLinkingID and pol_issue_hour=3 and pol_validity_expiry_date = '31-DEC-49' and pol_policy_no = :policyNo";
									
								}
									Session session = ht.getSessionFactory().openSession();
									SQLQuery query = session.createSQLQuery( sqlQuery );
									query.setParameter( com.Constant.CONST_QUOTENO, quoteNo );
									query.setParameter( "polLinkingID", polLinkingID );
									query.setParameter( "policyNo", policyNo );
	
									Date quoissueDate = null;
									List<Object> resultsFunc = query.list();
	
									if( !Utils.isEmpty( resultsFunc ) ){
	
										quoissueDate = (Date) ( resultsFunc.get( 0 ) );
										if( Utils.isEmpty( quoissueDate ) ){
											throw new BusinessException( "cmn.unknownError", null, "Unable to fetch pol issue date from policy table." );
										}
										
									}
									return quoissueDate;
							}
	
					
							
							
					/**142244
					 * Method to get the Vat Code and Vat Rate Home
					 * @param 
					 * @return  pr_prem_rate,pr_vat_code
					 */
					public static List<Object> VatCodeAndVatRate( Integer classCode, Integer ptCode, Short coverCode){
						List<Object> vatList = new ArrayList<Object>();
						
						HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
						Session hibSession = hibernateTemplate.getSessionFactory().openSession();
						
						SQLQuery query = hibSession.createSQLQuery( FETCH_VAT_RATE_AND_CODE );
						query.setParameter( com.Constant.CONST_CLASSCODE, classCode );
						query.setParameter( com.Constant.CONST_PTCODE, ptCode );
						query.setParameter(com.Constant.CONST_COVERCODE,coverCode);
						//query.setParameter("vatCode",vatCode);
						//query.setParameter( "preparedDate", preparedDate );
						
						LOGGER.debug("classCode------->"+classCode);
						LOGGER.debug("ptCode------->"+ptCode);
						LOGGER.debug("coverCode------->"+ptCode);
					//	LOGGER.debug("vatCode------->"+vatCode);
						List<Object[]> resultList = query.list();
						
						if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
						Object[] object = (Object[]) resultList.get( SvcConstants.zeroVal );
	
					
						vatList.add( ( (BigDecimal) object[ 0 ] ).doubleValue() ); //Vat Percent 
						vatList.add( ( (BigDecimal) object[ 1 ] ).intValue() ); // Vat code
						}
	
					
						return vatList;
						
					}
					
			// **********************For Travel************************************//
			//142244  Vat Code Implementation 
			public static Date getPreparedDateofQuoForTravel(Long quoteNo, org.hibernate.Session hibSession) {
				String sqlQuery = "select  pol_prepared_dt from t_trn_policy_quo"
										+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_quotation_no=:quoteNo ";
						
				SQLQuery query = hibSession.createSQLQuery( sqlQuery );
				query.setParameter( com.Constant.CONST_QUOTENO, quoteNo );
				Date quoPreparedDate = null;
				List<Object> resultsFunc = query.list();
	
				if( !Utils.isEmpty( resultsFunc ) ){
					quoPreparedDate = (Date) ( resultsFunc.get( 0 ) );
					if( Utils.isEmpty( quoPreparedDate ) ){
						throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_4" );
					}							
				}
				return quoPreparedDate;
			}
			
			/**
			 * Retrieving VatCode and VatRate for Travel from T_MAS_POLICY_RATING table -  142244
			 * @param baseVO
			 * @return
			 */
			@Transactional
			public static List<Object> VatCodeAndVatRateForTravel( Integer policyClassCode, Integer travelShortPolicyTypeCode, 
																		Integer travelLongPolicyTypeCode, Short covCode ) {
				
				List<Object> vatList = new ArrayList<Object>();
						
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session hibSession = hibernateTemplate.getSessionFactory().openSession();
						
				Date preparedDate=null;
				//preparedDate = DAOUtils.getPreparedDateofQuoForTravel(travelInsuranceVO.getCommonVO().getQuoteNo(), hibSession);
						
				SQLQuery query = hibSession.createSQLQuery( FETCH_VAT_RATE_AND_CODE );
				query.setParameter( com.Constant.CONST_CLASSCODE, policyClassCode );
				query.setParameter( com.Constant.CONST_PTCODE, travelLongPolicyTypeCode );
				query.setParameter(com.Constant.CONST_COVERCODE, covCode);
				//query.setParameter( "preparedDate", preparedDate );
								
				List<Object[]> resultList = query.list();					
				if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
					Object[] object = (Object[]) resultList.get( SvcConstants.zeroVal );
							
					vatList.add( ( (BigDecimal) object[ 0 ] ).doubleValue() );
					vatList.add( ( (BigDecimal) object[ 1 ] ).intValue() );
				}				
				return vatList;							
			}
			
			
			/**142244
			 * Method to get the Vat Code and Vat Rate
			 * @param 
			 * @return  pr_prem_rate,pr_vat_code
			 */
			
			public static Integer getVatCodeSBS( Integer ptCode, Short coverCode,Integer classCode){
				List<Object> vatList = new ArrayList<Object>();
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( FETCH_VAT_CODE_SBS_POLICY_RATING );
				query.setParameter( com.Constant.CONST_PTCODE, ptCode );
				query.setParameter(com.Constant.CONST_COVERCODE,coverCode);
				query.setParameter(com.Constant.CONST_CLASSCODE,classCode);
				Integer vatCode = 0;
				List<Object> resultList = query.list();
				
				if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
				 vatCode = Integer.parseInt(resultList.get(0).toString());
				}
	
		
				return vatCode;
				
			}
			
			/**142244
			 * Method to get the Vat Rate Home from T_MAS_CODE_MASTER
			 * @param 
			 * @return  
			 */
			public static double VatCodeAndVatRateFromCodeMaster( Integer vatCode,Date polIssueDate ){
				
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session hibSession = hibernateTemplate.getSessionFactory().openSession();
				
				SQLQuery query = hibSession.createSQLQuery( FETCH_VAT_RATE_HOME );
				query.setParameter( "vatCode", vatCode );
				//query.setParameter( "polIssueDate", polIssueDate );
				double vatRate = 0.0;
				
				LOGGER.debug("vatCode------->"+vatCode);
				//LOGGER.debug("polIssueDate------->"+polIssueDate);
				List<Object> resultList = query.list();
				
				if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
					vatRate = Double.parseDouble((resultList.get(0).toString())); //Vat Percent
				}
	
				LOGGER.debug("vatRate------->"+vatRate);
				return vatRate;
				
				
			}
			
			
			public static Integer checkVATDefaultCode(){
				return getVatCodeSBS(Integer.parseInt(Utils.getSingleValueAppConfig("SBS_Policy_Type")),SvcConstants.SC_PRM_COVER_VAT_TAX,Integer.parseInt(Utils.getSingleValueAppConfig("SBS_CLASS_CODE")));
				
			}
			public static BigDecimal getVATRateSBS( Integer vatCode,Date polIssueDate){
				List<Object> vatList = new ArrayList<Object>();
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( FETCH_VAT_RATE_TRANS );
				query.setParameter( "vatCode", vatCode );
				//query.setParameter("polIssueDate",polIssueDate);
				BigDecimal vatRate = new BigDecimal("0.0");
				List<Object> resultList = query.list();
				
				if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
				
					vatRate =  new BigDecimal(resultList.get(0).toString());
				}
				
				return vatRate;
			}
	
			
			
			/*VAT 142244 - Dileep*/
			public static void updateVATPremium( Double prmPremium, Long policyId, Long endtId){
				
				LOGGER.debug("prPremium =_1"+prmPremium+" policy Id =_1"+policyId+" Endt Id = "+endtId);
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( UPDATE_VAT_PRM_PREMIUM );	
				query.setParameter( "prmPremium", prmPremium );
				query.setParameter( com.Constant.CONST_POLICYID,policyId);
				query.setParameter(com.Constant.CONST_VALSTARTDATE,Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
				query.setParameter( com.Constant.CONST_ENDTID,endtId);
				query.setParameter( "prmCovCode",SvcConstants.SC_PRM_COVER_VAT_TAX);			
				query.executeUpdate();
			}
			
			/* VAT 142244 For updating vat amount in premium table */
			/*public static void updateVATPremiumHome( Double prmPremium, Long policyId, Long endtId){
				
				LOGGER.debug("prPremium =_2"+prmPremium+" policy Id =_2"+policyId+" Endt Id = "+endtId);
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( UPDATE_HOME_VAT_PRM_PREMIUM );	
				query.setParameter( "prmPremium", prmPremium );
				query.setParameter( com.Constant.CONST_POLICYID,policyId);
				query.setParameter(com.Constant.CONST_VALSTARTDATE,Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
	//			query.setParameter( com.Constant.CONST_ENDTID,endtId);
				query.setParameter( "prmCovCode",SvcConstants.SC_PRM_COVER_VAT_TAX);			
				query.executeUpdate();	
				
			}*/
			
			public static void updateVATPremiumHome( Double prmPremium, CommonVO commonVO){
				
				if(!Utils.isEmpty(commonVO) && !Utils.isEmpty(commonVO.getPremiumVO())){
				LOGGER.debug("prPremium =_3"+prmPremium+" policy Id =_3"+commonVO.getPolicyId()+" "+commonVO.getEndtId());
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( UPDATE_HOME_VAT_PRM_PREMIUM );	
				query.setParameter( "prmPremium", prmPremium );
				query.setParameter( com.Constant.CONST_POLICYID,commonVO.getPolicyId());
				query.setParameter(com.Constant.CONST_VALSTARTDATE,Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
	//			query.setParameter( com.Constant.CONST_ENDTID,endtId);
				query.setParameter( "prmCovCode",SvcConstants.SC_PRM_COVER_VAT_TAX);			
				query.executeUpdate();	
				
	//			updateVATABLEPolicyHome(commonVO.getPremiumVO().getVatablePrm(),commonVO.getPolicyId(),commonVO.getEndtId());
				
				
				}
				
				
			}
			
			/* VAT 142244 For updating vatable amount in policy table */
			/*public static void updateVATABLEPolicyHome( Double polVatablePrmeium, Long policyId, Long endtId){
				
				LOGGER.debug("polVatablePrmeium = "+polVatablePrmeium+" policy Id =_4"+policyId+" Endt Id = "+endtId);
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( UPDATE_VATABLE_POL_PREMIUM );	
				query.setParameter( "polVatAmount", polVatablePrmeium );
				query.setParameter( com.Constant.CONST_POLICYID,policyId);
				query.setParameter(com.Constant.CONST_VALSTARTDATE,Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
				query.setParameter( com.Constant.CONST_ENDTID,endtId);		
				query.executeUpdate();
				
				
			}*/
			
			//For calculating vat amount for quote versioning in renewal quote
			public static void updatePolVATPremium( Double prmPremium, Long policyId, Long endtId){
				
				LOGGER.debug("prPremium =_4"+prmPremium+" policy Id =_5"+policyId+" Endt Id = "+endtId);
				
				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
				Session session =  hibernateTemplate.getSessionFactory().openSession();
				SQLQuery query = session.createSQLQuery( UPDATE_VAT_POL_PREMIUM );	
				query.setParameter( "polVatAmount", prmPremium );
				query.setParameter( com.Constant.CONST_POLICYID,policyId);
				query.setParameter(com.Constant.CONST_VALSTARTDATE,Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
				query.setParameter( com.Constant.CONST_ENDTID,endtId);
	//			query.setParameter( "prmCovCode",SvcConstants.SC_PRM_COVER_VAT_TAX);			
				query.executeUpdate();
			}
			
					
	//142244  VAT
	private static final String FETCH_VAT_CODE_SBS_POLICY_RATING="select pr_vat_code from t_mas_policy_rating where  pr_pt_code=:ptCode and pr_cov_code=:coverCode and pr_cl_code=:classCode";	
	private static final String FETCH_VAT_RATE_TRANS="SELECT CDM_CODE3 FROM T_MAS_CODE_MASTER where CDM_CODE=:vatCode and CDM_ENTITY_TYPE = 'VAT_CONFIG'   ";
		//	" and trunc(CDM_CREATED_DATE)<=:polIssueDate" ;	
	private static final String FETCH_VAT_RATE_HOME="SELECT CDM_CODE3 FROM T_MAS_CODE_MASTER where CDM_CODE=:vatCode and CDM_ENTITY_TYPE = 'VAT_CONFIG'   ";
			//" and trunc(CDM_CREATED_DATE)<=:polIssueDate";	
	
	private static final String UPDATE_VAT_PRM_PREMIUM="update T_TRN_PREMIUM_QUO set PRM_PREMIUM =:prmPremium where prm_policy_id =:policyId "
			+" and prm_validity_expiry_date =:valStartDate and prm_endt_id =:endtId and "
			+" prm_cov_code =:prmCovCode and prm_rsk_id = 99999";
	
	private static final String UPDATE_HOME_VAT_PRM_PREMIUM="update T_TRN_PREMIUM_QUO set PRM_PREMIUM =:prmPremium where prm_policy_id =:policyId "
			+" and prm_validity_expiry_date =:valStartDate and "
			+" prm_cov_code =:prmCovCode and prm_rsk_id = 99999";
	
	private static final String UPDATE_VAT_POL_PREMIUM="update T_TRN_POLICY_QUO set POL_VAT_AMOUNT =:polVatAmount where pol_policy_id =:policyId "
														+" and pol_validity_expiry_date =:valStartDate and pol_endt_id =:endtId ";
	//													+" prm_cov_code =:prmCovCode and prm_rsk_id = 99999";
	
	/*private static final String UPDATE_VATABLE_POL_PREMIUM="update T_TRN_POLICY_QUO set pol_vatable_prm =:polVatAmount where pol_policy_id =:policyId "
			+" and pol_validity_expiry_date =:valStartDate and pol_endt_id =:endtId ";
	*/
	
	/*WC  VAT*/
	public static Map<Integer,Double> getVatAmountWC(Long  policyId ,Long endtID) {
	
	    StringBuilder queryString = new StringBuilder();
	    
	    
	    Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		vatResults.put(0, 0.0);
		vatResults.put(1, 0.0); 
	    BigDecimal vatTax = new BigDecimal(0.0);
	    BigDecimal vatablePrm = new BigDecimal(0.0);
	    HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session vatsession =  hibernateTemplate.getSessionFactory().openSession();
		Long prevEndtId = DAOUtils.getPrevEndtIdWC( hibernateTemplate, false,policyId, endtID );
	  
	   		
	  
	    if (!Utils.isEmpty(policyId)) {
	
	          queryString
	                      .append(" select pol_vat_amount, pol_vatable_prm from  t_trn_policy  where pol_policy_id = ")
	                      .append( policyId)
	                      .append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
	                      .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
	                      .append(com.Constant.CONST_AND_POL_CLASS_CODE_END)
	                      .append(Utils.getSingleValueAppConfig("WC_CLASS_CODE"))
	                      .append(com.Constant.CONST_AND_POL_ENDT_ID_END)
	                      .append( prevEndtId);
	        
	
	    
	    }
	
	    Query query = vatsession.createSQLQuery(queryString.toString());
	    List<Object> result = query.list();
	    
	    if (!Utils.isEmpty(result)) {   
	
	    		Object[] object = (Object[])result.get( SvcConstants.zeroVal );		
	    		if(!Utils.isEmpty(object[0])){
	    			vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
	    			}
	    			if(!Utils.isEmpty(object[1])){
	    			vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
	    			}
	    		} 
	   
	    return vatResults;
	}
	
		/* Travel VAT Fields */
		public static Map<Integer,Double> getVatAmountTravel(Long policyId, Long endtNo) {
	
			StringBuilder queryString = new StringBuilder();
			Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
			BigDecimal vatTax = new BigDecimal(0.0);
			BigDecimal vatablePrm = new BigDecimal(0.0);
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			Session vatsession = hibernateTemplate.getSessionFactory().openSession();
	
			if (!Utils.isEmpty(policyId)) {
				queryString
					.append(" select pol_vat_amount, pol_vatable_prm from  t_trn_policy  where pol_policy_id =  ")
					.append(policyId)
					.append(" and POL_ENDT_NO = ")
					.append(endtNo)
					//.append(" and POL_ENDT_ID = ")
					//.append(endtId)					
					.append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
					.append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
					.append(com.Constant.CONST_AND_POL_CLASS_CODE_END)
					.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE));
					//.append(" and pol_validity_expiry_date ='31-DEC-49' ");
			}
	
			Query query = vatsession.createSQLQuery(queryString.toString());
			List<Object> result = query.list();
	    
			if (!Utils.isEmpty(result)) {
				Object[] object = (Object[])result.get( SvcConstants.zeroVal );            
				vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
				vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
			}
			return vatResults;		
		}
		
		/**
		 * Calculate Vat Amount, Vatable Prm for Travel using PolId, EndId
		 * @param policyId
		 * @param endtId
		 * @return
		 */
		@Transactional
	public static Map<Integer,Double> getVatAmountTravelByPolIDAndPolEndId(Long policyId, Long endtID,boolean isCancel) {
			
			StringBuilder queryString = new StringBuilder();
			
			Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
			vatResults.put(0, 0.0);
			vatResults.put(1, 0.0); 
			
			BigDecimal vatTax = new BigDecimal(0.0);
			BigDecimal vatablePrm = new BigDecimal(0.0);
	
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			Session vatsession = hibernateTemplate.getSessionFactory().openSession();
			//Long prevEndtId = DAOUtils.getPrevEndtId( hibernateTemplate, false,policyId, endtID );
		Long prevEndtId=null;
		if(isCancel){
		    prevEndtId=endtID;
		}else{
		    prevEndtId = DAOUtils.getPrevEndtIdWC( hibernateTemplate, false,policyId, endtID );
		}
			//Session vatsession = hibernateTemplate.getSessionFactory().openSession();
			
			if (!Utils.isEmpty(policyId)) {
				queryString
					.append("select nvl(pol_vat_amount,0), nvl(pol_vatable_prm,0) from  t_trn_policy  where pol_policy_id = ")
					.append(policyId)				
					.append(" and POL_ENDT_ID = ")
					.append(prevEndtId)					
					.append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
					.append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
					.append(com.Constant.CONST_AND_POL_CLASS_CODE_END)
					.append(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE));
			}
	
			Query query = vatsession.createSQLQuery(queryString.toString());
			List<Object> result = query.list();
	    
			if (!Utils.isEmpty(result)) {			
				Object[] object = (Object[])result.get( SvcConstants.zeroVal );		
				if(!Utils.isEmpty(object[0])) {
					vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
				}
				if(!Utils.isEmpty(object[1])) {
					vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
				}
			} 
			return vatResults;
		}
		
		/*Home  VAT*/
		public static Map<Integer,Double> getVatAmountHome(Long  policyId, Long  endtID) {
		    HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
	
		    StringBuilder queryString = new StringBuilder();
		    Long prevEndtId = DAOUtils.getPrevEndtId( hibernateTemplate, false,policyId, endtID );
		    Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		    BigDecimal vatTax = new BigDecimal(0.0);
		    BigDecimal vatablePrm = new BigDecimal(0.0);
		    
			Session vatsession =  hibernateTemplate.getSessionFactory().openSession();
		  
		    if (!Utils.isEmpty(policyId)) {
	
		          queryString
		                      .append("select nvl(pol_vat_amount,0), nvl(pol_vatable_prm,0) from  t_trn_policy  where pol_policy_id = ")
		                      .append( policyId)
		                      .append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
		                      .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
		                      .append(com.Constant.CONST_AND_POL_ENDT_ID_END)
		                      .append( prevEndtId);
		        
	
		    
		    }
	
		    Query query = vatsession.createSQLQuery(queryString.toString());
		    List<Object> result = query.list();
		    
		    if (!Utils.isEmpty(result)) {
		    	
		    	
		    	Object[] object = (Object[])result.get( SvcConstants.zeroVal );		
		    	vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
		        vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
		    }
		    
		    return vatResults;
		}
	
		
		
		/*SBS  VAT*/
		public static Map<Integer,Double> getVatAmountSBSSum(PolicyVO policyVO,Long prevEndtId,Boolean isJSP) {
	
			StringBuilder queryString = new StringBuilder();
			
			String tableToQuery = "T_TRN_POLICY";
	
			Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
			vatResults.put(0, 0.0);
			vatResults.put(1, 0.0);
				BigDecimal vatTax = new BigDecimal(0.0);
				BigDecimal vatablePrm = new BigDecimal(0.0);
	
					HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
					Session vatsession =  hibernateTemplate.getSessionFactory().openSession();
	
					if(policyVO.getIsQuote()){
						tableToQuery = "T_TRN_POLICY_QUO";
					}
					
					
					if (isJSP) {
	
						queryString
		              .append("Select sum(nvl(P.pol_vat_amount,0)),sum(nvl(P.pol_vatable_prm,0))  From ")
		              .append(tableToQuery)
		              .append(" P Where P.POL_LINKING_ID =  ")
		              .append( policyVO.getPolLinkingId())
		              .append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
		              .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
		              .append(com.Constant.CONST_AND_POL_ENDT_ID_END)
		              .append( prevEndtId);
						}
				
						
						Query query = vatsession.createSQLQuery(queryString.toString());
						List<Object> result = query.list();
						
						if (!Utils.isEmpty(result)) {
						
						
						Object[] object = (Object[])result.get( SvcConstants.zeroVal );	
						if(!Utils.isEmpty(object[0])){
						vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
						}
						if(!Utils.isEmpty(object[1])){
						vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
						}
						}
						
						return vatResults;
		}
	
	
	
	
		/*WC  VAT*/
		public static Map<Integer,Double> getVatAmountSBS(Long  policyId ,Long prevEndtId,PolicyVO policyVO) {
	
		StringBuilder queryString = new StringBuilder();
	
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		BigDecimal vatTax = new BigDecimal(0.0);
		BigDecimal vatablePrm = new BigDecimal(0.0);
		vatResults.put(0, 0.0);
		vatResults.put(1, 0.0);
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session vatsession =  hibernateTemplate.getSessionFactory().openSession();
	
		if (!Utils.isEmpty(policyId)) {
	
		  queryString
		              .append("select pol_vat_amount, pol_vatable_prm from  t_trn_policy  where pol_policy_id = ")
		              .append( policyId)
		              .append(com.Constant.CONST_AND_POL_ISSUE_HOUR_END)
		              .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
		              .append(" and POL_Linking_id = ")
		              .append( policyVO.getPolLinkingId())
		              .append(" and POL_ENDT_ID = ")
		              .append( prevEndtId);
	
	
	
		}
	
		Query query = vatsession.createSQLQuery(queryString.toString());
		List<Object> result = query.list();
	
		if (!Utils.isEmpty(result)) {
	
	
		Object[] object = (Object[])result.get( SvcConstants.zeroVal );		
		if(!Utils.isEmpty(object[0])){
			vatResults.put(0, ((BigDecimal)object[0]).doubleValue());
			}
			if(!Utils.isEmpty(object[1])){
			vatResults.put(1, ((BigDecimal)object[1]).doubleValue());
			}
		}
	
		return vatResults;
		}
		@Transactional
		public static long getPrevEndtIdWC( HibernateTemplate ht, boolean isQuote, long policyId, long newEndtId ){
			String sqlquery = null;
			if( isQuote )
				sqlquery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_PREV_ENDT_ID_QUO_END + policyId + "," + newEndtId + com.Constant.CONST_FROM_DUAL2;
			else
				sqlquery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_PREV_ENDT_ID_END + policyId + "," + newEndtId + com.Constant.CONST_FROM_DUAL2;
	
			Session session = ht.getSessionFactory().openSession();
			Query query = session.createSQLQuery( sqlquery );
			List<Object> result = query.list();
			if( Utils.isEmpty( result ) ){
				LOGGER.debug( "Prev Endorsement id is nul_3" );
				return 0L;
			}
	
			return ( (BigDecimal) result.get( 0 ) ).longValue();
		}
		
		/*142244 - Added as part of new VAT screen to update VAT Reg No in Policy and Insured table*/	
		
		public static List<Integer> updateVATRegnoForPolicy( String vatRegNo, Long polId,Long polEndtId, List<Integer> polTypeLIst ){
			
			LOGGER.debug("polVatRegNo = "+vatRegNo+" policyId/PolLinkingId = "+polId);	
			
			int count = 0;			
			List<Integer> resultcount = new ArrayList<Integer>();
			String objProcessed= null;
			
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session =  hibernateTemplate.getSessionFactory().openSession();	
			Query updatePolVATRegQuery = null;
			//Long insuredCode = null;
			
			try{				
				objProcessed = "fetching insured code from T_Mas_Insured Table";
				BigDecimal insuredCode = getInsuredCodetoUpdate(polId,polEndtId,polTypeLIst,hibernateTemplate);				
				objProcessed = "Updating VAT Reg no in T_TRN_POLICY table";				
				if(polTypeLIst.contains(Integer.parseInt( SvcConstants.SBS_POL_TYPE ))){					
					updatePolVATRegQuery = session.createSQLQuery( UPDATE_VAT_REGNO_POLICY_SBS );
					updatePolVATRegQuery.setParameter("polVatRegNo", vatRegNo);
					updatePolVATRegQuery.setParameter(com.Constant.CONST_LINKINGID, polId);
				}else{
					updatePolVATRegQuery = session.createSQLQuery( UPDATE_VAT_REGNO_POLICY_PL );
					updatePolVATRegQuery.setParameter("polVatRegNo", vatRegNo);
					updatePolVATRegQuery.setParameter(com.Constant.CONST_POLICYID, polId);
				}
				//updatePolVATRegQuery.setParameter("polEndtId", polEndtId);
				//updatePolVATRegQuery.setParameter("polValExpDate", Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
				updatePolVATRegQuery.setParameterList( "lobs", polTypeLIst );
				updatePolVATRegQuery.setParameter("polIssueHour",Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_ISSUE_HOUR ));
				count = updatePolVATRegQuery.executeUpdate();
				resultcount.add(count);
				
				//Updating T_MAS_INSURED table
				objProcessed = "Updating VAT Reg no in T_MAS_INSURED table";	
				Query updateVATRegInsuredQuery = session.createSQLQuery( UPDATE_VAT_REGNO_INSURED );
				updateVATRegInsuredQuery.setParameter("insVatRegNo", vatRegNo);
				updateVATRegInsuredQuery.setParameter("insInsuredCode", insuredCode);
				count = updateVATRegInsuredQuery.executeUpdate();
				resultcount.add(count);
				return resultcount;
			}catch( HibernateException e ){
	
				throw new BusinessException( "pas.vat.regNo.error", e, "An exception occured while "+objProcessed );
			}
		}			
		
		
		/*142244 - Added as part of new VAT screen to fetch the insured code to update the VAT Reg No*/		
		private static BigDecimal getInsuredCodetoUpdate(Long polId, Long polEndtId,
				List<Integer> polTypeLIst,HibernateTemplate template) {
			
			SQLQuery fetchPolInsuredQuery = null;
			Session session =  template.getSessionFactory().openSession();
			BigDecimal insuredCode = null;
			
			if(polTypeLIst.contains(Integer.parseInt( SvcConstants.SBS_POL_TYPE ))){
				
				fetchPolInsuredQuery = session.createSQLQuery( FETCH_INSURED_CODE_POLICY_SBS );			
				fetchPolInsuredQuery.setParameter( com.Constant.CONST_LINKINGID, polId );				
			}
			else{
		
				fetchPolInsuredQuery = session.createSQLQuery( FETCH_INSURED_CODE_POLICY_PL );			
				fetchPolInsuredQuery.setParameter( com.Constant.CONST_POLICYID, polId );				
			}
			
			fetchPolInsuredQuery.setParameter("polEndtId",polEndtId);
			//fetchPolInsuredQuery.setParameter("polValExpDate",Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ));
			fetchPolInsuredQuery.setParameterList( "lobs", polTypeLIst );
			fetchPolInsuredQuery.setParameter("polIssueHour",Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_ISSUE_HOUR ));
			
			List<Object> resultList = fetchPolInsuredQuery.list();
			
			if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){				
				insuredCode = new BigDecimal(resultList.get(0).toString());
			}
			
			return insuredCode;
		}
		
		/*To determine whther the pol is legacy issued in 2017*/
		public static Date getPreparedDateofQuoHome(Long number, Long polPolicyID) {
			 String sqlQuery = null;
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			
			
			
				 sqlQuery = " select  pol_issue_date from t_trn_policy "
						+ " where pol_endt_id=0 and pol_issue_hour=3 and  pol_policy_no=:number and POL_POLICY_ID=:polPolicyID";
				
		
				Session session = ht.getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery( sqlQuery );
				query.setParameter( com.Constant.CONST_NUMBER, number );
				
				query.setParameter( "polPolicyID", polPolicyID );
	
				Date quoissueDate = null;
				List<Object> resultsFunc = query.list();
	
				if( !Utils.isEmpty( resultsFunc ) ){
	
					quoissueDate = (Date) ( resultsFunc.get( 0 ) );
					if( Utils.isEmpty( quoissueDate ) ){
						throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_5" );
					}
					
				}
				return quoissueDate;
		}
		public static Long getPreviousEndtIdSBS(HibernateTemplate ht,
				PolicyVO policyVO) {
			
			String sqlquery = null;
			Long policyId = getBaseSectionPolicyIdForClause(policyVO,ht);
			
			Long endtId = SvcUtils.getLatestEndtId( policyVO );
			
			if( policyVO.getIsQuote() )
				sqlquery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_PREV_ENDT_ID_QUO_END + policyId + "," + endtId + com.Constant.CONST_FROM_DUAL2;
			else
				sqlquery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_PREV_ENDT_ID_END + policyId + "," + endtId+ com.Constant.CONST_FROM_DUAL2;
	
			Session session = ht.getSessionFactory().openSession();
			Query query = session.createSQLQuery( sqlquery );
			List<Object> result = query.list();
			if( Utils.isEmpty( result ) ){
				LOGGER.debug( "Prev Endorsement id is nul_4" );
				return 0L;
			}
	
			return ( (BigDecimal) result.get( 0 ) ).longValue();
			
		}
		
		/*Dileep - enable GIT section at scheme level */
		public static Map<Integer,String> getSectionVisibility( Integer schCode){
			
			Map<Integer,String> sectionMap = new HashMap<Integer, String>();
			
			HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			Session session =  hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery( FETCH_ENABLED_DISABLED_SECTIONS );
			query.setParameter( "schCode", schCode );
			BigDecimal vatRate = new BigDecimal("0.0");
			List<Object[]> resultList = query.list();
			
			//if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){	
			for(Object[] rec : resultList){
				Integer secId =((BigDecimal) rec[0]).intValue();
				sectionMap.put(secId, rec[1].toString());
			}
			
			return sectionMap;
		}
/**
     * 
     * @param polLinkingId
     * @param secId
     */
    public static void deleteReferralRecords(Long polLinkingId, Integer secId,
            HibernateTemplate ht) {
       
        if (Utils.isEmpty(polLinkingId) || Utils.isEmpty(secId)) throw new BusinessException(
                    "", null, com.Constant.CONST_ONE_OF_THE_PARAMETERS_OUT_OF_POLICYLINKINGID_END + polLinkingId
                            + " and SectionId: " + secId + " required to delete the recored from tempPasReferral table is null or empty.");
        Session session = null;
        String sqlQuery = "DELETE FROM T_TRN_TEMP_PAS_REFERRAL WHERE TPR_POL_LINKING_ID= "
                + polLinkingId + "  AND TPR_SEC_ID =" + secId;
        session = ht.getSessionFactory().openSession();
        Query query = session.createSQLQuery(sqlQuery);
        query.executeUpdate();
        session.close();

    }
	
		/*
		 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
		 * Method used to check if vat is enabled from ss_v_mas_lookup -> CTL_VAT_ENABLED 
		 */
		public static boolean isVatEnabled(){
			LOGGER.debug("inside isVatEnabled() : DAOUtil");
			SsVMasLookupId id;
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			List<SsVMasLookup> vatEnabledFlag = (List<SsVMasLookup>) ht.find("from SsVMasLookup where id.category = 'VAT_FLAG' and id.description='CTL_VAT_ENABLED' ");
			if (vatEnabledFlag.size() > 0 && (!Utils.isEmpty( vatEnabledFlag.get(0) ))) {
				for (SsVMasLookup ssVMasLookup : vatEnabledFlag) {
					BigDecimal value = ssVMasLookup.getId().getCode();
					if(value.equals(BigDecimal.ZERO)){
						System.out.println("Value Zero Dont insert: " + value);
						LOGGER.debug( "Vat is not Enabled " );
						return true;
					}
				}
			}
			return false;
		}
		
		/*
		 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
		 * Method used to check if vat is applicable from ss_v_mas_lookup ->VAT_APPLICABLE_FLAG  
		 */
		/*public static boolean isVatApplicable(){
			LOGGER.debug("inside isVatApplicable() : DAOUtil");
			SsVMasLookupId id;
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
			List<SsVMasLookup> vatEnabledFlag = (List<SsVMasLookup>) ht.find("from SsVMasLookup where id.category = 'VAT_FLAG' and id.description='VAT_APPLICABLE_FLAG' ");
			if (!Utils.isEmpty( vatEnabledFlag.get(0))) {
				for (SsVMasLookup ssVMasLookup : vatEnabledFlag) {
					BigDecimal value = ssVMasLookup.getId().getCode();
					if(value.equals(BigDecimal.ZERO)){
						System.out.println("Value Zero Dont insert: " + value);
						LOGGER.debug( "Vat is not Applicable " );
						return true;
					}
				}
			}
			return false;
		}*/
		
		/*
		 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
		 * Method used to check vat enabled/applicable and set the flag to attributes.
		 */
		public static void  checkVatEnabled(HttpServletRequest request ){
			/* Added for Bahrain check for 151 and hide vat.reg.no and vat code based on flags*/
			Boolean vatEnabledFlag = DAOUtils.isVatEnabled();
			request.setAttribute("vatEnabledFlag",vatEnabledFlag);
			//Boolean vatApplicableFlag = DAOUtils.isVatApplicable();
			//request.setAttribute("vatApplicableFlag",vatApplicableFlag);
		}
		
/*Start of Ticket:161309, This method is used to check whether referral messages are present in the t_trn_temp_pas_referral table */ 
    public static boolean checkReferralMessages(PolicyVO policyVO) {
        String sqlQuery = null;
        boolean referralMsg = false;
        
            HibernateTemplate ht = (HibernateTemplate) Utils
                    .getBean(com.Constant.CONST_HIBERNATETEMPLATE);

            sqlQuery = "select  * from t_trn_temp_pas_referral "
                    + " where TPR_POL_LINKING_ID=:polLinkingId ";

            Session session = ht.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery(sqlQuery);
            query.setParameter("polLinkingId", policyVO.getPolLinkingId());
            List<Object> referralTempMsg = query.list();

            if (!Utils.isEmpty(referralTempMsg)) {
                referralMsg = true;
            }
      
        return referralMsg;
    }
    /*End of Ticket:161309, This method is used to check whether referral messages are present in the t_trn_temp_pas_referral table */ 

/*To determine whther the pol is legacy issued in 2017*/
    public static Date getPreparedDateofQuoTravel(Long number, Long polPolicyID) {
         String sqlQuery = null;
            HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
                    
             sqlQuery = "select  pol_issue_date from t_trn_policy"
                    + " where pol_endt_id=0 and pol_issue_hour=3 and  pol_policy_no=:number and POL_POLICY_ID=:polPolicyID";
                
             Session session = ht.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery( sqlQuery );
            query.setParameter( com.Constant.CONST_NUMBER, number );
            
            query.setParameter( "polPolicyID", polPolicyID );

            Date quoissueDate = null;
            List<Object> resultsFunc = query.list();

            if( !Utils.isEmpty( resultsFunc ) ){

                quoissueDate = (Date) ( resultsFunc.get( 0 ) );
                if( Utils.isEmpty( quoissueDate ) ){
                    throw new BusinessException( "cmn.unknownError", null, "Unable to fetch validity start date from policy table_6" );
                }
                
            }
            return quoissueDate;
    }


    /*End of Ticket:161309, This method is used to check whether referral messages are present in the t_trn_temp_pas_referral table */ 

	public static List<BigDecimal> getQuoteFromPolicy(BaseVO baseVO) {
		CommonVO commonVO = null;
		String query = "";
		String errorMsg = null;
		if (baseVO instanceof CommonVO) {
			commonVO = (CommonVO) baseVO;

			if (!Utils.isEmpty(commonVO.getPolicyNo()) && commonVO.getLob().equals(LOB.HOME)) {
				query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE (POL_POLICY_NO = " + commonVO.getPolicyNo()
						+ " OR POL_REF_POLICY_NO =" + commonVO.getPolicyNo()
						+ " ) AND POL_ISSUE_HOUR = 3 AND POL_CLASS_CODE = 2 AND POL_POLICY_TYPE = 7 AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND POL_STATUS in (1,6,7,20,22,23) "
						+ " AND POL_DISTRIBUTION_CHNL in (4,8,9,10)  ";
				/*
				 * +
				 * "AND pol_policy_id in (select csh_policy_id from t_mas_cash_customer_quo where CSH_E_EMAIL_ID = '"
				 */
				/* + commonVO.getConcatPolicyNo() */
				if (!Utils.isEmpty(commonVO.getCreatedBy())) {
					query = query
							+ " AND pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"
							+ commonVO.getCreatedBy()+"')";
				}

			} else if (!Utils.isEmpty(commonVO.getPolicyNo()) && commonVO.getLob().equals(LOB.TRAVEL)) {
				query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE (POL_POLICY_NO = " + commonVO.getPolicyNo()
						+ " OR POL_REF_POLICY_NO =" + commonVO.getPolicyNo()
						+ " ) AND pol_issue_hour = 3 AND POL_CLASS_CODE = 5 AND POL_POLICY_TYPE in (6,31) AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND POL_STATUS in (1,6,7,20,22,23) "
						+ "AND POL_DISTRIBUTION_CHNL in (4,8,9,10) ";

				if (!Utils.isEmpty(commonVO.getCreatedBy())) {
					query = query
							+ " AND pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"
							+ commonVO.getCreatedBy()+"')";
				}
			}
		}
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query sqlQuery = session.createSQLQuery(query);

		List<BigDecimal> quoteNumbers = sqlQuery.list();
		if (Utils.isEmpty(quoteNumbers)) {
			throw new BusinessException("pas.cmn.noRecordFound", null, "There is no Quote for this Policy");
		}

		else if (!Utils.isEmpty(quoteNumbers)) {
			return quoteNumbers;
		}
		session.close();
		return null;
	}
	
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start	
	public static List<BigDecimal> getQuoteFromPol(BaseVO baseVO) {
		CommonVO commonVO = null;
		String query = "";
		String errorMsg = null;
		if (baseVO instanceof CommonVO) {
			commonVO = (CommonVO) baseVO;

			if (!Utils.isEmpty(commonVO.getPolicyNo()) && commonVO.getLob().equals(LOB.HOME)) {
				query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE (POL_POLICY_NO = " + commonVO.getPolicyNo()
						+ " OR POL_REF_POLICY_NO =" + commonVO.getPolicyNo()
						+ " ) AND POL_ISSUE_HOUR = 3 AND POL_CLASS_CODE = 2 AND POL_POLICY_TYPE = 7 AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND POL_STATUS in (1,6,7,20,22,23) "
						+ " AND POL_DISTRIBUTION_CHNL in (4,8,9,10) AND pol_document_code = 6  and rownum = 1" + 
						"           order by POL_QUOTATION_NO desc ";
				

			} 
		}
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query sqlQuery = session.createSQLQuery(query);

		List<BigDecimal> quoteNumbers = sqlQuery.list();
		if (Utils.isEmpty(quoteNumbers)) {
			throw new BusinessException("pas.cmn.noRecordFound", null, "There is no Quote for this Policy");
		}

		else if (!Utils.isEmpty(quoteNumbers)) {
			return quoteNumbers;
		}
		session.close();
		return null;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	/*
	 * Method used to retrieve optional covers from T_MAS_POLICY_RATING table for Home lob
	 */
	public static List<TMasPolicyRating> getHomeOptionalCovers(BaseVO baseVO, Integer classCode, Integer policyType, Integer schemeCode, Integer tariffCode){
		LOGGER.info("Enters getHomeOptionalCovers");
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		List<TMasPolicyRating> homeOptionalCovers = new com.mindtree.ruc.cmn.utils.List<TMasPolicyRating>(TMasPolicyRating.class);
		try {
			String dateInString = "31-DEC-49";
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			Date prcEndDate = formatter.parse(dateInString);
			Integer disaplayValue = SvcConstants.zeroVal;
			
			Query query = session.createQuery("from TMasPolicyRating view WHERE view.id.prClCode = "+classCode.shortValue()+" AND view.id.prPtCode = "+policyType.shortValue()+" "
					+ "AND view.id.prTariff = "+tariffCode.intValue()+" AND view.id.prCovCode NOT IN (1,60) and view.id.prCtCode IN(0,9)");
			LOGGER.info("getHomeOptionalCovers"+query);
			homeOptionalCovers = (List<TMasPolicyRating>)query.list();
			session.close();
			return homeOptionalCovers;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exiting getHomeOptionalCovers");
		return null;
	}
	public static void deleteSections(PolicyVO policyVO) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery( "Delete from t_trn_policy_sections_quo where Tps_linking_id=:linkingId  ");
		query.setParameter(com.Constant.CONST_LINKINGID, policyVO.getPolLinkingId());
		query.executeUpdate();
		session.close();
	}
public static void setPremiumVED(PolicyVO policyVO) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery( "update T_TRN_PREMIUM_QUO set PRM_VALIDITY_EXPIRY_DATE =:prmValidityStartDate where   PRM_ENDT_ID<:endtId"
				+ " AND PRM_POLICY_ID IN (SELECT POl_POLICY_ID FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO=:quotationNo and POL_ISSUE_HOUR = 3) and PRM_VALIDITY_EXPIRY_DATE = '31-DEC-2049'");
		query.setParameter("prmValidityStartDate", policyVO.getValidityStartDate()); 
		query.setParameter(com.Constant.CONST_ENDTID, policyVO.getEndtId());
		query.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
		query.executeUpdate();
		session.close();
	}
	public static void invalidateRecord(PolicyVO policyVO) {
		PASStoredProcedure sp = null;
		if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getNewEndtId() != 0L) {

			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoOnDemandCurrEndt");
			} 

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId());
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empt_64");
				}
			} catch (DataAccessException e) {
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc_70");
			}
		}

	}
	public static void updateVEDWeb(PolicyVO policyVO, int classCode, int sectionId) {
		PASStoredProcedure sp = null;
		if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getNewEndtId() != 0L) {

			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoOnDemand");
			} 

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId(), policyVO.getNewEndtId(),
						policyVO.getNewValidityStartDate(), classCode, sectionId);
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empt_65");
				}
			} catch (DataAccessException e) {
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc_71");
			}
		}

	}
	public static void updateReferralStatus(PolicyVO policyVO) {

		if (!policyVO.getTaskDetails().getPolicyType().equals("31")) {
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			/*
			 * Below query should fetch records basis linking id and VED in
			 * order to update status of current version which is in pending
			 * status
			 */
			/*
			 * List<TTrnPolicyQuo> trnPolicyQuos = getHibernateTemplate().find(
			 * "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=?",
			 * taskDetails.getPolLinkingId() );
			 */
			/*
			 * Fetch only the latest endorsementId record to update the status.
			 */
			List<TTrnPolicyQuo> polRecList = ht.find(
					"from TTrnPolicyQuo polTable where polTable.polLinkingId=? and polTable.polValidityExpiryDate=? and polTable.id.polEndtId=?",
					policyVO.getTaskDetails().getPolLinkingId(), SvcConstants.EXP_DATE,
					policyVO.getTaskDetails().getPolEndId());

			for (TTrnPolicyQuo tTrnPolicyQuo : polRecList) {
				tTrnPolicyQuo.setPolStatus(Byte.valueOf(Utils.getSingleValueAppConfig(SvcConstants.QUOTE_REFERRED)));
				tTrnPolicyQuo.setPolApprovedBy(null);
				tTrnPolicyQuo.setPolApprovalDate(null);
				ht.merge(tTrnPolicyQuo);
			}

		}

	}
	public static PolicyVO getPolicyVOFromStaging(Long polQuotationNo) {

		List staging = null;
		PolicyVO  policyVO = new PolicyVO();
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		
		/*staging = ht.find(
				"from EplatformWsStaging stagingTable where stagingTable.polQuotationNo =? and stagingTable.polStatus  =?",
				polQuotationNo,6);*/
		
		Query query = session.createSQLQuery("select QUO_INTR_RESPONSE_ADD from T_TRN_EPLATFORM_STAGING_QUO where POL_QUOTATION_NO=:quotationNo and BATCH_STATUS=0 Order by POL_ENDT_ID");
		query.setParameter(com.Constant.CONST_QUOTATIONNO, polQuotationNo);
		
		if(!Utils.isEmpty(query.list())) {
			staging = query.list();
		}
		else {
			throw new BusinessException("pas.cmn.noRecordFound", null, "No Record Found ");
		}
		
		Blob blob = (Blob) staging.get(0);
		policyVO = getPolicyVoFromBlob(blob);
		
		session.close();
		return policyVO;
	}
	public static void batchResponse(BaseVO baseVO) {
		PolicyVO policyVO = (PolicyVO) baseVO;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		EplatformWsStaging eplatformWsStaging = new EplatformWsStaging();
		List<EplatformWsStaging> staging = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(policyVO);
			Blob blob = Hibernate.createBlob(bos.toByteArray());
			
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			Session session = ht.getSessionFactory().openSession();
			/*staging = ht.find(
					"from EplatformWsStaging stagingTable where stagingTable.polQuotationNo=? and stagingTable.polStatus =6 Order by stagingTable.polEndtNo desc ",
					policyVO.getQuoteNo());
				eplatformWsStaging = staging.get(0);
			eplatformWsStaging.setQuoIntrBatchResponse(blob);
			eplatformWsStaging.setPolStatus(policyVO.getStatus().byteValue());	
			ht.saveOrUpdate(staging);
					*
					*/
			Query query = session.createSQLQuery("update T_TRN_EPLATFORM_STAGING_QUO set POL_STATUS=:status, INTR_BATCH_VO=:intr_batch_vo,BATCH_STATUS=:batch_status where POL_QUOTATION_NO=:quotationNo and POL_ENDT_ID=:pol_endtId");
			LOGGER.info("Updating T_TRN_EPLATFORM_STAGING_QUO With Values:: Status:"+policyVO.getStatus()+" quotationNo: "+policyVO.getQuoteNo()+" pol_endtId :"+policyVO.getEndtId()+" batch_status:1");
			query.setParameter(com.Constant.CONST_STATUS, policyVO.getStatus().byteValue());
			query.setParameter("intr_batch_vo", blob);
			query.setParameter(com.Constant.CONST_QUOTATIONNO,policyVO.getQuoteNo());
			query.setParameter("pol_endtId", policyVO.getEndtId());
			query.setParameter("batch_status", 1);
			query.executeUpdate();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List<PolicyVO> getPolicyVOSFromStaging() {

		List<Blob> staging = null;
		List<PolicyVO> policyVOs = new ArrayList<PolicyVO>();
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		
		
		Query query = session.createSQLQuery("select QUO_INTR_RESPONSE_ADD from T_TRN_EPLATFORM_STAGING_QUO where BATCH_STATUS=0 "); // 0 means batch fails
		if(!Utils.isEmpty(query.list().size()>0)) {  
			staging = query.list();
		}
		else {
			throw new BusinessException("pas.cmn.noRecordFound", null, "No Record Found ");
		}
		if (staging.size() > 0) {
			for (Blob blob : staging) {
				PolicyVO policyVO = new PolicyVO();
				policyVO = getPolicyVoFromBlob(blob);
				policyVOs.add(policyVO);
			}
		}
		session.close();
		return policyVOs;
	}

	public static PolicyVO getPolicyVoFromBlob(Blob blob) {
		PolicyVO policyVO = new PolicyVO();
		//Sonar Fix to use Try with Resources 
		//ObjectInputStream ois = null;
		try(ObjectInputStream ois= new ObjectInputStream(blob.getBinaryStream())) {
			//ois = new ObjectInputStream(blob.getBinaryStream());
			policyVO = (PolicyVO) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} //Sonar Fix to use Try with Resources 
		/*finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return policyVO;
	}
	
	// Added for JLT

	public static PolicyVO storeComments(PolicyVO policyVO, String comment) {

		Boolean exists = false;
		List<TTrnPolicyQuo> policies = null;
		CommentsVO commentsVO = new CommentsVO();
		SQLQuery sqlQuery = null;
		Long endtId=0L;
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();

		Long basePolId = DAOUtils.baseSectionPolicyId(policyVO);
		if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
			endtId=policyVO.getNewEndtId();
		}else {
			endtId=policyVO.getEndtId();
		}
		if (basePolId != null) {
			
			policies = hibernateTemplate.find("from TTrnPolicyQuo where id.polPolicyId=? and id.polEndtId=?", basePolId,
					policyVO.getEndtId());
		}

		if (!Utils.isEmpty(policyVO) &&  basePolId!=null) {
			String selectQuery = "SELECT * FROM T_TRN_POLICY_COMMENTS WHERE POC_POLICY_ID=" + basePolId
					+ " AND POC_ENDT_ID=" + endtId +" AND POC_REASON_CODE=72";
			Query sqlQuery1 = session.createSQLQuery(selectQuery);
			List<Object> result = sqlQuery1.list();
			if (result.size() > 0) {
				exists = true;
			}
		}

		if (exists) {
			sqlQuery = session.createSQLQuery("update  t_trn_policy_comments set "
					+ "  POC_REASON_CODE=:reasonCode,POC_COMMENTS=:comment,POC_POLICY_STATUS=:status,"
					+ "POC_DATE=:pocDate,POC_PREPARED_BY=:preparedBy,POC_PREPARED_DT=:preparedDt,"
					+ "POC_MODIFIED_BY=:modifiedBy,POC_MODIFIED_DT=:modifiedDt,POC_DOCUMENT_CODE=:docCode"
					+ " WHERE POC_POLICY_ID=:polId AND POC_ENDT_ID=:endId and POC_REASON_CODE=72  ");
		} else {
			sqlQuery = session.createSQLQuery(
					"insert into t_trn_policy_comments(POC_POLICY_ID,POC_ENDT_ID,POC_REASON_CODE,POC_COMMENTS,POC_POLICY_STATUS,POC_DATE,POC_PREPARED_BY,POC_PREPARED_DT,POC_MODIFIED_BY,POC_MODIFIED_DT,POC_DOCUMENT_CODE)"
							+ " values(:polId,:endId,:reasonCode,:comment,:status,:pocDate,:preparedBy,:preparedDt,:modifiedBy,:modifiedDt,:docCode)");
		}
		
		
		if (exists) {

			if (!Utils.isEmpty(policies)) { 
				for (com.rsaame.pas.dao.model.TTrnPolicyQuo policy : policies) {

					commentsVO.setReasonCode((short) 72);
					if (!Utils.isEmpty(commentsVO.getReasonCode())) {
						sqlQuery.setShort(com.Constant.CONST_REASONCODE, commentsVO.getReasonCode());
					} else if (!Utils.isEmpty(policyVO.getStatus()) && policyVO.getStatus()
							.equals(Byte.valueOf(Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP")))) {
						/*
						 * Renewals Feedback : will check if status is
						 * 99(softstop) then will set null as reason code.
						 */
						sqlQuery.setString(com.Constant.CONST_REASONCODE, null);
					} else {
						sqlQuery.setShort(com.Constant.CONST_REASONCODE,
								Short.parseShort(Utils.getSingleValueAppConfig("QUOTE_REASON_CODE")));
					}

					if (!Utils.isEmpty(comment)) {
						sqlQuery.setString(com.Constant.CONST_COMMENT, comment);
					} else {
						sqlQuery.setString(com.Constant.CONST_COMMENT, null);
					}

					if (!Utils.isEmpty(policyVO.getStatus())) {
						sqlQuery.setByte(com.Constant.CONST_STATUS, policyVO.getStatus().byteValue());
					} else {
						sqlQuery.setByte(com.Constant.CONST_STATUS, policy.getPolStatus());
					}

					sqlQuery.setTimestamp("pocDate", (Date) (ThreadLevelContext.get(SvcConstants.TLC_KEY_SYSDATE)));

					if (!Utils.isEmpty(policy.getPolPreparedBy())) {
						sqlQuery.setInteger(com.Constant.CONST_PREPAREDBY, policy.getPolPreparedBy());
					} else {
						sqlQuery.setInteger(com.Constant.CONST_PREPAREDBY, Integer.valueOf(policyVO.getCreatedBy()));
					}

					if (!Utils.isEmpty(policy.getPolPreparedDt())) {
						sqlQuery.setDate(com.Constant.CONST_PREPAREDDT, new java.sql.Date(policy.getPolPreparedDt().getTime()));
					} else {
						sqlQuery.setDate(com.Constant.CONST_PREPAREDDT, null);
					}

					if (!Utils.isEmpty(policy.getPolModifiedBy())) {
						sqlQuery.setInteger(com.Constant.CONST_MODIFIEDBY, SvcUtils.getUserId(policyVO));
					} else {
						sqlQuery.setInteger(com.Constant.CONST_MODIFIEDBY, SvcUtils.getUserId(policyVO));
					}

					if (!Utils.isEmpty(policy.getPolModifiedDt())) {
						sqlQuery.setDate(com.Constant.CONST_MODIFIEDDT, new java.sql.Date(policy.getPolModifiedDt().getTime()));
					} else {
						sqlQuery.setDate(com.Constant.CONST_MODIFIEDDT, null);
					}

					if (!Utils.isEmpty(policy.getPolDocumentCode())) {
						sqlQuery.setByte(com.Constant.CONST_DOCCODE, Byte.parseByte(Short.toString(policy.getPolDocumentCode())));
					} else {
						sqlQuery.setByte(com.Constant.CONST_DOCCODE, (Byte) null);
					}
					sqlQuery.setLong("polId", basePolId);
					if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
						sqlQuery.setLong(com.Constant.CONST_ENDID, policyVO.getNewEndtId());
					}else {
						sqlQuery.setLong(com.Constant.CONST_ENDID, policyVO.getEndtId());

					}
					sqlQuery.executeUpdate();
				}
			}
		}else {
			
			if (!Utils.isEmpty(policies)) { 
				for (com.rsaame.pas.dao.model.TTrnPolicyQuo policy : policies) {
					sqlQuery.setLong("polId", basePolId);
					if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
						sqlQuery.setLong(com.Constant.CONST_ENDID, policyVO.getNewEndtId());
					}else {
						sqlQuery.setLong(com.Constant.CONST_ENDID, policyVO.getEndtId());

					}
					commentsVO.setReasonCode((short) 72);
					if (!Utils.isEmpty(commentsVO.getReasonCode())) {
						sqlQuery.setShort(com.Constant.CONST_REASONCODE, commentsVO.getReasonCode());
					} else if (!Utils.isEmpty(policyVO.getStatus()) && policyVO.getStatus()
							.equals(Byte.valueOf(Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP")))) {
						/*
						 * Renewals Feedback : will check if status is
						 * 99(softstop) then will set null as reason code.
						 */
						sqlQuery.setString(com.Constant.CONST_REASONCODE, null);
					} else {
						sqlQuery.setShort(com.Constant.CONST_REASONCODE,
								Short.parseShort(Utils.getSingleValueAppConfig("QUOTE_REASON_CODE")));
					}

					if (!Utils.isEmpty(comment)) {
						sqlQuery.setString(com.Constant.CONST_COMMENT, comment);
					} else {
						sqlQuery.setString(com.Constant.CONST_COMMENT, null);
					}

					if (!Utils.isEmpty(policyVO.getStatus())) {
						sqlQuery.setByte(com.Constant.CONST_STATUS, policyVO.getStatus().byteValue());
					} else {
						sqlQuery.setByte(com.Constant.CONST_STATUS, policy.getPolStatus());
					}

					sqlQuery.setTimestamp("pocDate", (Date) (ThreadLevelContext.get(SvcConstants.TLC_KEY_SYSDATE)));

					if (!Utils.isEmpty(policy.getPolPreparedBy())) {
						sqlQuery.setInteger(com.Constant.CONST_PREPAREDBY, policy.getPolPreparedBy());
					} else {
						sqlQuery.setInteger(com.Constant.CONST_PREPAREDBY, Integer.valueOf(policyVO.getCreatedBy()));
					}

					if (!Utils.isEmpty(policy.getPolPreparedDt())) {
						sqlQuery.setDate(com.Constant.CONST_PREPAREDDT, new java.sql.Date(policy.getPolPreparedDt().getTime()));
					} else {
						sqlQuery.setDate(com.Constant.CONST_PREPAREDDT, null);
					}

					if (!Utils.isEmpty(policy.getPolModifiedBy())) {
						sqlQuery.setInteger(com.Constant.CONST_MODIFIEDBY, SvcUtils.getUserId(policyVO));
					} else {
						sqlQuery.setInteger(com.Constant.CONST_MODIFIEDBY, SvcUtils.getUserId(policyVO));
					}

					if (!Utils.isEmpty(policy.getPolModifiedDt())) {
						sqlQuery.setDate(com.Constant.CONST_MODIFIEDDT, new java.sql.Date(policy.getPolModifiedDt().getTime()));
					} else {
						sqlQuery.setDate(com.Constant.CONST_MODIFIEDDT, null);
					}

					if (!Utils.isEmpty(policy.getPolDocumentCode())) {
						sqlQuery.setByte(com.Constant.CONST_DOCCODE, Byte.parseByte(Short.toString(policy.getPolDocumentCode())));
					} else {
						sqlQuery.setByte(com.Constant.CONST_DOCCODE, (Byte) null);
					}
					sqlQuery.executeUpdate();
				}
			}
			
			
			
		}
		

		return policyVO;
	}

	public static Long baseSectionPolicyId(PolicyVO policyVO) {

		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Long policyId = null;
		Long endtId = SvcUtils.getLatestEndtId(policyVO);

		String sqlQuery = "";

		if (policyVO.getIsQuote()) {
			sqlQuery = com.Constant.CONST_SELECT_PKG_PAS_UTILS_GET_BASE_SEC_POL_ID_QUO_END + policyVO.getPolLinkingId() + "," + endtId
					+ com.Constant.CONST_FROM_DUAL2;
		} else {
			sqlQuery = com.Constant.CONST_SELECT_PKG_ENDT_GEN_GET_BASE_SEC_POL_ID_END + policyVO.getPolLinkingId() + "," + endtId
					+ com.Constant.CONST_FROM_DUAL2;
		}

		Query query = session.createSQLQuery(sqlQuery);
		session.flush();
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			policyId = Long.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(policyId)) {
				throw new BusinessException("cmn.unknownError", null,
						"The policy no is 0 or null for inserting into endorsment details table_3");
			}
		}
		session.close();
		return policyId;
	}

	public static String getRemarks(PolicyVO policyVO) {

		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		String comment = null;

		Long basePolId = DAOUtils.baseSectionPolicyId(policyVO);
		if (basePolId != null) {

			String sqlQuery = "";

			if (policyVO.getIsQuote()) {
				sqlQuery = "SELECT POC_COMMENTS FROM T_TRN_POLICY_COMMENTS WHERE POC_POLICY_ID =" + basePolId
						+ " AND POC_ENDT_ID =" + policyVO.getEndtId()
						+ " AND POC_POLICY_ID IN (SELECT POL_POLICY_ID FROM T_TRN_POLICY_QUO WHERE POL_ISSUE_HOUR=3"
						+ " AND POL_POLICY_ID =" + basePolId + " AND POL_ENDT_ID=" + policyVO.getEndtId() + ")"
						+ " AND POC_REASON_CODE=72 " ;
			} else {
				sqlQuery = "SELECT POC_COMMENTS FROM T_TRN_POLICY_COMMENTS WHERE POC_POLICY_ID =" + basePolId
						+ " AND POC_ENDT_ID =" + policyVO.getEndtId()
						+ " AND POC_POLICY_ID IN (SELECT POL_POLICY_ID FROM  T_TRN_POLICY WHERE POL_ISSUE_HOUR=3"
						+ " AND POL_POLICY_ID =" + basePolId + " AND POL_ENDT_ID=" + policyVO.getEndtId() + ")"
						+ " AND POC_REASON_CODE=72 " ;
			}

			Query query = session.createSQLQuery(sqlQuery);
			session.flush();
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc) && resultsFunc.get(0).toString() != null) {
				comment = resultsFunc.get(0).toString();
			}else {
				comment=null;
			}
			session.close();
		}
		return comment;
	}
	//Added for Bahrain 3 decimal - Starts	
	public static Map<Integer, Double> getCommPercForEachClassCd(PolicyVO policyVO) {
		StringBuilder queryString = new StringBuilder();
		
		String tableToQuery = "T_TRN_POLICY";

		  Map<Integer,Double> classCodeComm=new HashMap<Integer,Double>();

				HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
				Session session =  hibernateTemplate.getSessionFactory().openSession();

				if(policyVO.getIsQuote()){
					tableToQuery = "T_TRN_POLICY_QUO";
				}
				
					queryString
	              .append("Select P.POL_CLASS_CODE,P.POL_COMMISION_ID From ")
	              .append(tableToQuery)
	              .append(" P Where P.POL_LINKING_ID =  ")
	              .append( policyVO.getPolLinkingId())
	              .append(" and P.pol_issue_hour = ")
	              .append(Utils.getSingleValueAppConfig(com.Constant.CONST_SBS_POLICY_ISSUE_HOUR))
	              .append(" and P.pol_endt_id = ")
	              .append( policyVO.getEndtId());
				
					Query query = session.createSQLQuery(queryString.toString());
					List<Object> objects = query.list();
					Iterator <Object>itr =null;
					itr = objects.iterator();
					Object[] row =null;
					while(itr.hasNext()){
						row = (Object[])itr.next();
						if(!Utils.isEmpty(row[1])){
						classCodeComm.put(((BigDecimal)row[0]).intValue(),((BigDecimal)row[1]).doubleValue());
						}
					}	
					
					return classCodeComm;
	}
	//Added for Bahrain 3 decimal - Ends



	public static boolean deleteReferral(Long polLinkingId, HibernateTemplate ht) {

		if (Utils.isEmpty(polLinkingId))
			throw new BusinessException("", null, "One of the parameters out of PolicyLinkingId: " + polLinkingId);
		Session session = null;
		try {
			String sqlQuery = "DELETE FROM T_TRN_TEMP_PAS_REFERRAL WHERE TPR_POL_LINKING_ID= " + polLinkingId;
			session = ht.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createSQLQuery(sqlQuery);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}



	public static String getUserRole( PolicyVO policyVO ){
		String highestRole = null;
		UserProfile profile = null;
		if( !Utils.isEmpty( policyVO.getLoggedInUser() ) ){
			profile = (UserProfile) policyVO.getLoggedInUser();
		}
		if( !Utils.isEmpty( profile ) && !Utils.isEmpty( profile.getRsaUser() ) ){
			if( !Utils.isEmpty( profile.getRsaUser().getHighestRole() ) ){
				highestRole = profile.getRsaUser().getHighestRole("SBS");
			}
		}
		return highestRole;
	}
	
	//Added for Informap Changes
	public static boolean isInformapAvailable(){
		LOGGER.debug("inside isInformapAvailable() : DAOUtil");
		SsVMasLookupId id;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
		List<SsVMasLookup> informapEnabledFlag = (List<SsVMasLookup>) ht.find("from SsVMasLookup where id.category = 'PAS_INFOMAP_FLAG' ");
		if (informapEnabledFlag.size() > 0 && (!Utils.isEmpty( informapEnabledFlag.get(0) ))) {
			for (SsVMasLookup ssVMasLookup : informapEnabledFlag) {
				BigDecimal value = ssVMasLookup.getId().getCode();
				if(value.equals(BigDecimal.ONE)){
					
					LOGGER.debug( "Informap is Available " );
					return true;
				}
			}
		}
		return false;
	}
	
	// changes-HomeRevamp#7.1
				public static String fetchOwnershipInfo(String policyNo ){
					String ownerShipStatus=null;
					try{
						 List resultSet = DAOUtils.getSqlResultForPas(QueryConstants.Fetch_Ownership_status,policyNo);
		                 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
		                       LOGGER.debug("DAOUTILS fetchOwnershipInfo() size:_1"+resultSet.get(0));
		                       ownerShipStatus = (String) resultSet.get(0).toString();
		                        
		                 } 
						}
					catch( DataAccessException e ){
						throw new BusinessException( "fetchOwnershipInfo", e, "An exception occured while fetchOwnershipInfo" );
					}
					return ownerShipStatus;
					
				}
				// changes-HomeRevamp#7.1
				
				@Transactional
				public static Date getPolModOrPrepdateDate(Long quoteNo,Boolean isQuote,Long endtId) {
					 String sqlQuery = null;
						HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
					if(isQuote){
						if(endtId>0){

					 sqlQuery = "select  pol_modified_dt from t_trn_policy_quo "
								+ " where pol_endt_id=:endtId and pol_issue_hour=3 and  pol_quotation_no=:quoteNo ";
						}else{
							
							 sqlQuery = "select  pol_prepared_dt from t_trn_policy_quo "
										+ " where pol_endt_id=:endtId and pol_issue_hour=3 and  pol_quotation_no=:quoteNo ";
						}
						
					}
					
					else{
						 sqlQuery = "select  pol_prepared_dt from t_trn_policy "
								+ " where  pol_quotation_no=:quoteNo and pol_endt_id=:endtId and pol_issue_hour=3 ";
						
					}
						Session session = ht.getSessionFactory().openSession();
						SQLQuery query = session.createSQLQuery( sqlQuery );
						query.setParameter( com.Constant.CONST_QUOTENO, quoteNo );
						query.setParameter( com.Constant.CONST_ENDTID, endtId );

						Date quomodDate = null;
						List<Object> resultsFunc = query.list();

						if( !Utils.isEmpty( resultsFunc ) ){

							quomodDate = (Date) ( resultsFunc.get( 0 ) );
							if( Utils.isEmpty( quomodDate ) ){
								throw new BusinessException( "cmn.unknownError", null, "Unable to fetch pol modified date from policy table." );
							}
							
						}
						return quomodDate;
				}
				// changes-HomeRevamp#7.1	
/*
	 * Method used to fetch the scheme,tariff,policy type.. from T_MAS_DEFAULT_VALUES, T_MAS_PARTNER_MGMT table
	 */
	public static Object fetchDefaultValues(String partnerName) {
		LOGGER.debug("enters inside fetchDefaultValues()");
		Object[] object = null;
		List<String> resultList = new ArrayList<>();
		resultList = DAOUtils.getSqlResultForPasString(QueryConstants.FETCH_TRANSACTION_DETAILS, partnerName);
		try {
			if (!Utils.isEmpty(resultList) && resultList.size() > 0) {
				Iterator itr = resultList.listIterator(0);
				while (itr.hasNext()) {
					object = (Object[]) itr.next();
				}
			}

			if (Utils.isEmpty(resultList)) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			LOGGER.error(e.getStackTrace().toString());
		}
		LOGGER.debug("enters inside fetchDefaultValues()");
		return object;
	}
	public static String fetchComments(PolicyVO policyVO) {
		String comments = null;
		List<String> resultList = new ArrayList<>();
		resultList = DAOUtils.getSqlResultForPasString(QueryConstants.FETCH_COMMENT_DETAILS,
				policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		try {
			if (!Utils.isEmpty(resultList) && resultList.size() > 0) {
				comments = resultList.get(0).toString();
			} else {
				comments = null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getStackTrace().toString());
		}
		return comments;
	}
	
	/**
	 * 
	 * @param homeInsuranceVO
	 * @return
	 * @throws ParseException
	 */
	public static List<TTrnGaccPersonQuo> getGaccPersonDetails(HomeInsuranceVO homeInsuranceVO) throws ParseException{
		LOGGER.info("Enters getGaccPersonDetails");
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		//CTS - 31.08.2020 - TFS#42424 - Staff Details not populating in Retrieve Quote Response - Starts
		String policyId = null;
		if(Utils.isEmpty(homeInsuranceVO.getPolicyId())){
			policyId = homeInsuranceVO.getCommonVO().getPolicyId().toString();
		}else{
			policyId = homeInsuranceVO.getPolicyId().toString();
		}
		List<TTrnGaccPersonQuo> gaccPerson = new com.mindtree.ruc.cmn.utils.List<TTrnGaccPersonQuo>(TTrnGaccPersonQuo.class);
		Query query = session.createQuery("from TTrnGaccPersonQuo gacc WHERE gacc.gprPolicyId = "+policyId+" AND gacc.gprValidityExpiryDate = '31-DEC-49' and gacc.gprStatus <> 4");
		//CTS - 31.08.2020 - TFS#42424 - Staff Details not populating in Retrieve Quote Response - Ends
		LOGGER.info("getGaccPersonDetails"+query);
		gaccPerson = (List<TTrnGaccPersonQuo>)query.list();
		session.close();
		return gaccPerson;
	}		
	//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule
	public static Double getOldVATTravelPremium(Long policyId){
		List<Object> vatDetailsList = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_OLD_PREMIUM_FOR_VAT,policyId,policyId);
		return Double.valueOf(vatDetailsList.get(0).toString());
	}
	//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule
	
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
	public static String getPolicyCommentFromReasonCode(short reasonCode){
		String reasonCodeDiscription = null;
		String sqlQuery = "Select rsc_e_desc from t_mas_reason_codes where rsc_code = " +reasonCode;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
		Session session = ht.getSessionFactory().openSession();
		ht.flush();
		SQLQuery query = session.createSQLQuery( sqlQuery );	
		List<Object> tempList = query.list();
		if(!Utils.isEmpty(tempList))
		reasonCodeDiscription = tempList.get(0).toString().trim();
		return reasonCodeDiscription;
	}
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
				public static String FetchInsuredCode(String QuoteNo ){
					String InsuredCode=null;
					try{
						 List resultSet = DAOUtils.getSqlResultForPas(QueryConstants.FETCH_INSURED_CODE,QuoteNo);
		                 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
		                       LOGGER.debug("DAOUTILS FetchInsuredCode() size:_1"+resultSet.get(0));
		                       InsuredCode = (String) resultSet.get(0).toString();
		                        
		                 } 
						}
					catch( DataAccessException e ){
						throw new BusinessException( "FetchInsuredCode", e, "An exception occured while FetchInsuredCode" );
					}
					return InsuredCode;
					
				}
				//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
}
