/**
 * 
 */
package com.rsaame.pas.clauses.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.IBaseSaveOperation;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.cmn.TempPasReferralDAO;
import com.rsaame.pas.dao.model.TMasPolicyCondition;
import com.rsaame.pas.dao.model.TMasPolicyExclusion;
import com.rsaame.pas.dao.model.TMasPolicyWarranty;
import com.rsaame.pas.dao.model.TTrnNonStdTextQuo;
import com.rsaame.pas.dao.model.TTrnNonStdTextQuoId;
import com.rsaame.pas.dao.model.TTrnPolicyCondition;
import com.rsaame.pas.dao.model.TTrnPolicyConditionQuo;
import com.rsaame.pas.dao.model.TTrnPolicyExclusion;
import com.rsaame.pas.dao.model.TTrnPolicyExclusionQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPolicyWarranty;
import com.rsaame.pas.dao.model.TTrnPolicyWarrantyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.NonStandardClause;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1016303
 * 
 */
public class ViewClausesDAO extends BaseSectionSaveDAO implements IViewClausesDAO{

	private static final Logger LOGGER = Logger.getLogger( ViewClausesDAO.class );
	private Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );

	//private Integer CLASS_SECTION_ID1=988;
	private Integer CONDTIONS_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "CONDTIONS_SECTION_ID" ) );
	private final static Long LOC_ID = Long.valueOf( "0" );
	private final static String GET_COVERAGE_AMT = "select cdm_entity_desc from t_mas_code_master where cdm_entity_type = 'PAS_ANACON' AND cdm_code1=:contentSI AND cdm_code_desc=:clauseCode";
	private final static String FETCH_CONTENT_PREV_QUO = "select cnt_status from t_trn_content_quo where cnt_policy_id = :pol_id AND cnt_endt_id < :endt_id order by cnt_endt_id desc";
	private final static String FETCH_CONTENT_PREV_POL = "select cnt_status from t_trn_content where cnt_policy_id = :pol_id AND cnt_endt_id < :endt_id order by cnt_endt_id desc";

	private final String[] FLAGS = { "C", "E", "W" };
	com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );

	@Override
	public BaseVO getClauses( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof DataHolderVO ){
				@SuppressWarnings( "unchecked" )
				DataHolderVO<Object[]> inputs = (DataHolderVO<Object[]>) baseVO;
				Object[] data = inputs.getData();

				PolicyVO policyVO = null;
				CommonVO commonVO = null;
				Integer sectionId = null;

				Integer scheme = null;
				java.util.List<SectionVO> riskDetails = null;
				DataHolderVO<java.util.List<StandardClause>> clausesVO = null;
				Integer policyType = null;

				if( data[ 0 ] instanceof PolicyVO ){
					//Phase 1 & 2 Specific block
					policyVO = (PolicyVO) data[ 0 ];
					riskDetails = policyVO.getRiskDetails();
					sectionId = (Integer) data[ 1 ];
					SectionVO section = getSection( sectionId, riskDetails );
					scheme = policyVO.getScheme().getSchemeCode();
					policyType = Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_TYPE ) );
					
					if( Utils.isEmpty( section ) ){
						LOGGER.error( "Section obtained from getSection method for sectionId [" + sectionId + "] is null" );
						throw new BusinessException( "cmn.unknownError", null, "section obj is null for sectionId [" + sectionId + "] from getSection method" );
					}

					clausesVO = getstdClauses( policyVO, sectionId, scheme, policyType,false,false );
				}
				else{
					//Phase 3 Specific block
					commonVO = (CommonVO) data[ 0 ];
					sectionId = (Integer) data[ 1 ];
					Boolean isExcess = (Boolean) data[ 2 ];
					Boolean isBoth = (Boolean) data[ 3 ];
					List<Integer> schemePolType = DAOUtils.getSchemeAndPolicyType( commonVO, getHibernateTemplate() );
					scheme = schemePolType.get( 0 );
					policyType = schemePolType.get( 1 );

					if( isExcess ){
						List<StandardClause> clauseList = new com.mindtree.ruc.cmn.utils.List<StandardClause>( StandardClause.class );
						getConditionsClauses( clauseList, commonVO, sectionId, scheme, policyType, isExcess,isBoth );

						clausesVO = new DataHolderVO<List<StandardClause>>();
						clausesVO.setData( clauseList );
					}
					else{
						clausesVO = getstdClauses( commonVO, sectionId, scheme, policyType, isExcess,isBoth );
					}
				}

				return clausesVO;
			}
		}
		return baseVO;
	}

	private DataHolderVO<List<StandardClause>> getstdClauses( BaseVO baseVO, Integer sectionId, Integer scheme, Integer policyType, Boolean isExcess, Boolean isBoth ){
		DataHolderVO<List<StandardClause>> clauses = new DataHolderVO<List<StandardClause>>();
		List<StandardClause> clauseList = new com.mindtree.ruc.cmn.utils.List<StandardClause>( StandardClause.class );

		getConditionsClauses( clauseList, baseVO, sectionId, scheme, policyType, isExcess,isBoth );
		if( baseVO instanceof PolicyVO || (baseVO instanceof CommonVO && !(((CommonVO) baseVO).getLob().equals( LOB.HOME )||((CommonVO) baseVO).getLob().equals( LOB.TRAVEL )))){
			getExclusionClauses( clauseList, baseVO, sectionId, scheme, policyType );
			getWarrantyClauses( clauseList, baseVO, sectionId, scheme, policyType );
		}

		clauses.setData( clauseList );

		return clauses;
	}

	private void getWarrantyClauses( List<StandardClause> clauseList, BaseVO baseVO, Integer sectionId, Integer scheme, Integer policyType ){

		List<TTrnPolicyWarrantyQuo> ttrnWarrantyQou = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyWarrantyQuo>( TTrnPolicyWarrantyQuo.class );
		List<TTrnPolicyWarranty> ttrnWarranties = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyWarranty>( TTrnPolicyWarranty.class );

		PolicyVO policyVO = null;
		CommonVO commonVO = null;
		boolean isQuote = false;
		Long endtId = null, policyId = null;
		boolean isDeletedInCurrentEndt = false;
		boolean existingSelection,selected = false;
		Date effectiveDatePol = null;

		if( baseVO instanceof PolicyVO ){
			policyVO = (PolicyVO) baseVO;
			isQuote = policyVO.getIsQuote();
			policyId = DAOUtils.getSectionPolicyId( policyVO, sectionId, getHibernateTemplate() );
			endtId = getLatestEndtId( policyVO );
			
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), policyVO.getQuoteNo());
			
		}
		else if( baseVO instanceof CommonVO ){
			commonVO = (CommonVO) baseVO;
			isQuote = commonVO.getIsQuote();
			policyId = commonVO.getPolicyId();
			endtId = commonVO.getEndtId();
			
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), commonVO.getQuoteNo());
		}
		else{
			throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO onl_1" );
		}

		Integer hdrCode = getHdrCodeForSection( sectionId, policyType );
		List<TMasPolicyWarranty> masWarranties = null;

		masWarranties = (List<TMasPolicyWarranty>) getHibernateTemplate().find(		
				"from TMasPolicyWarranty masCon where masCon.id.pwSchCode=? and masCon.id.pwClCode = ? and masCon.pwPhrCode = ? and trunc(masCon.pwEffectiveDate) <= ? and trunc(masCon.pwExpiryDate) >= ?", scheme,
				Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode ,effectiveDatePol,effectiveDatePol);
		
		/*if(baseVO instanceof PolicyVO )
			{
				masWarranties = (List<TMasPolicyWarranty>) getHibernateTemplate().find(		
				"from TMasPolicyWarranty masCon where masCon.id.pwSchCode=? and masCon.id.pwClCode = ? and masCon.pwPhrCode = ? and trunc(masCon.pwEffectiveDate) <= ? and trunc(masCon.pwExpiryDate) >= ?", scheme,
				Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode ,effectiveDatePol,effectiveDatePol);
			}
		else if( baseVO instanceof CommonVO )
		{
			masWarranties = (List<TMasPolicyWarranty>) getHibernateTemplate().find(		
					"from TMasPolicyWarranty masCon where masCon.id.pwSchCode=? and masCon.id.pwClCode = ? and masCon.pwPhrCode = ?", scheme,
					Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode);
		}*/
		if( !Utils.isEmpty( policyId ) ){
			if( isQuote ){
				ttrnWarrantyQou = (List<TTrnPolicyWarrantyQuo>) getHibernateTemplate().find(
						"from TTrnPolicyWarrantyQuo quoCon where quoCon.id.tpwPolicyId=? and (quoCon.id.tpwEndtId = ? or "
								+ "(quoCon.id.tpwEndtId < ? and trunc(quoCon.tpwValidityEndDate) = ?)) and quoCon.id.tpwCode in "
								+ "(select masCon.id.pwCode from TMasPolicyWarranty masCon where masCon.id.pwSchCode=? " + "and masCon.id.pwClCode = ? and masCon.pwPhrCode = ?)",
						policyId, endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );

			}
			else{
				ttrnWarranties = (List<TTrnPolicyWarranty>) getHibernateTemplate().find(
						"from TTrnPolicyWarranty polCon where polCon.id.tpwPolicyId=? and (polCon.id.tpwEndtId = ? or "
								+ "(polCon.id.tpwEndtId < ? and trunc(polCon.tpwValidityEndDate) = ?)) and polCon.id.tpwCode in "
								+ "(select masCon.id.pwCode from TMasPolicyWarranty masCon where masCon.id.pwSchCode=? " + "and masCon.id.pwClCode = ? and masCon.pwPhrCode = ?)",
						policyId, endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
			}
		}

		for( TMasPolicyWarranty warranty : masWarranties ){
			 selected = false;existingSelection = false;
			StandardClause standardClause = new StandardClause();
			if( isQuote ){

				if( Utils.isEmpty( ttrnWarrantyQou ) ){
					if( warranty.getPwDefaultInd() == 2 ){
						selected = true;
					}
				}

				for( TTrnPolicyWarrantyQuo quo : ttrnWarrantyQou ){
					if( !Utils.isEmpty( quo ) ){
						if( warranty.getId().getPwCode() == quo.getId().getTpwCode() ){
							//	if(!Utils.isEmpty( quo.getTpwAmount() ) || quo.getTpwStatus().intValue() != 4 ){
							if( !Utils.isEmpty( quo.getTpwAmount() ) && quo.getTpwStatus().intValue() != 4 ){
								standardClause.setAmount( quo.getTpwAmount() );
							}
							if( quo.getTpwStatus().intValue() != 4 ){
								selected = true;
								break;
							}
							//Added by Anveshan
							if(endtId.equals( quo.getId().getTpwEndtId()) &&  quo.getTpwStatus().intValue() == 4 ){
								isDeletedInCurrentEndt = true;
							}
						}
					}
				}

			}
			else{
				if( Utils.isEmpty( ttrnWarranties ) ){
					if( warranty.getPwDefaultInd() == 2 ){
						selected = true;
					}
				}

				for( TTrnPolicyWarranty policyWarranty : ttrnWarranties ){
					if( !Utils.isEmpty( policyWarranty ) ){
						if( warranty.getId().getPwCode() == policyWarranty.getId().getTpwCode() ){
							//if(!Utils.isEmpty( policyWarranty.getTpwAmount() ) || policyWarranty.getTpwStatus().intValue() != 4 ){
							if( !Utils.isEmpty( policyWarranty.getTpwAmount() ) && policyWarranty.getTpwStatus().intValue() != 4 ){
								standardClause.setAmount( policyWarranty.getTpwAmount() );
							}
							if( policyWarranty.getTpwStatus().intValue() != 4 ){
								selected = true;
								break;
							}
							//}
						}
					}
				}

			}
			existingSelection = selected;
			standardClause.setClauseType( "W" );

			if( Utils.isEmpty( standardClause.getAmount() ) && !Utils.isEmpty( warranty.getPwAmount() ) ){
				standardClause.setAmount( warranty.getPwAmount().toString() );
			}

			if( !Utils.isEmpty( warranty.getId().getPwCode() ) ){
				standardClause.setClauseCode( Long.valueOf( String.valueOf( warranty.getId().getPwCode() ) ) );
			}

			if( !Utils.isEmpty( warranty.getPwEText() ) ){
				standardClause.setDescription( warranty.getPwEText() );
			}
			standardClause.setExistingSelection( existingSelection );
			standardClause.setSelected( selected );
			standardClause.setIsDefault( warranty.getPwDefaultInd() );
			standardClause.setSectionID( sectionId.longValue() );
			standardClause.setDeletedInCurrentEndt( isDeletedInCurrentEndt );
			isDeletedInCurrentEndt = false;

			clauseList.add( standardClause );
		}

	}

	private void getExclusionClauses( List<StandardClause> clauseList, BaseVO baseVO, Integer sectionId, Integer scheme, Integer policyType ){
		List<TTrnPolicyExclusionQuo> ttrnExclusionQuos = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyExclusionQuo>( TTrnPolicyExclusionQuo.class );
		List<TTrnPolicyExclusion> ttrnExclusions = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyExclusion>( TTrnPolicyExclusion.class );

		PolicyVO policyVO = null;
		CommonVO commonVO = null;
		boolean isQuote = false;
		Long endtId = null, policyId = null;
		boolean isDeletedInCurrentEndt = false;
		List<UWQuestionVO> uwQuestionVOList=null;
		boolean isStudentLiabilityflagEnabled = false;
		boolean selected,existingSelection = false;
		Date effectiveDatePol=null;

		if( baseVO instanceof PolicyVO ){
			policyVO = (PolicyVO) baseVO;
			isQuote = policyVO.getIsQuote();
			policyId = DAOUtils.getSectionPolicyId( policyVO, sectionId, getHibernateTemplate() );
			endtId = getLatestEndtId( policyVO );
			
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			if( 6  == sectionId ){
				isStudentLiabilityflagEnabled = DAOUtils.isStudentLiabilitySelected(endtId,policyId, policyVO, getHibernateTemplate());
			}
			
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), policyVO.getQuoteNo());
			
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
		}
		else if( baseVO instanceof CommonVO ){
			commonVO = (CommonVO) baseVO;
			isQuote = commonVO.getIsQuote();
			policyId = commonVO.getPolicyId();
			endtId = commonVO.getEndtId();
			
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), commonVO.getQuoteNo());
		}
		else{
			throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO onl_2" );
		}

		Integer hdrCode = getHdrCodeForSection( sectionId, policyType );
		List<TMasPolicyExclusion> masExclusions = null;

		masExclusions = (List<TMasPolicyExclusion>) getHibernateTemplate().find(			
				"from TMasPolicyExclusion masCon where masCon.id.pexSchCode=? and masCon.id.pexClCode = ? and masCon.pexPhrCode = ? and trunc(masCon.pexEffectiveDate) <= ? and trunc(masCon.pexExpiryDate) >= ?", scheme,
				Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode ,effectiveDatePol,effectiveDatePol);
		
		/*if( baseVO instanceof PolicyVO )
			{
			masExclusions = (List<TMasPolicyExclusion>) getHibernateTemplate().find(			
				"from TMasPolicyExclusion masCon where masCon.id.pexSchCode=? and masCon.id.pexClCode = ? and masCon.pexPhrCode = ? and trunc(masCon.pexEffectiveDate) <= ? and trunc(masCon.pexExpiryDate) >= ?", scheme,
				Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode ,effectiveDatePol,effectiveDatePol);
			}
		else if( baseVO instanceof CommonVO)
		{
			masExclusions = (List<TMasPolicyExclusion>) getHibernateTemplate().find(					
					"from TMasPolicyExclusion masCon where masCon.id.pexSchCode=? and masCon.id.pexClCode = ? and masCon.pexPhrCode = ?", scheme,
					Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
		}*/
		if( !Utils.isEmpty( policyId ) ){
			if( isQuote ){
				ttrnExclusionQuos = (List<TTrnPolicyExclusionQuo>) getHibernateTemplate().find(
						"from TTrnPolicyExclusionQuo quoCon where quoCon.id.tpePolicyId=? and (quoCon.id.tpeEndtId = ? or "
								+ "(quoCon.id.tpeEndtId < ? and trunc(quoCon.tpeValidityEndDate) = ?)) and quoCon.id.tpeCode in "
								+ "(select mas.id.pexCode from TMasPolicyExclusion mas where mas.id.pexSchCode=? " + "and mas.id.pexClCode = ? and mas.pexPhrCode = ?)", policyId,
						endtId, endtId, SvcConstants.EXP_DATE, scheme, Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
			}
			else{
				ttrnExclusions = (List<TTrnPolicyExclusion>) getHibernateTemplate().find(
						"from TTrnPolicyExclusion polCon where polCon.id.tpePolicyId=? and (polCon.id.tpeEndtId = ? or "
								+ "(polCon.id.tpeEndtId < ? and trunc(polCon.tpeValidityEndDate) = ?)) and polCon.id.tpeCode in "
								+ "(select mas.id.pexCode from TMasPolicyExclusion mas where mas.id.pexSchCode=? " + "and mas.id.pexClCode = ? and mas.pexPhrCode = ?)", policyId,
						endtId, endtId, SvcConstants.EXP_DATE, scheme, Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
			}
		}
		for( TMasPolicyExclusion exclusion : masExclusions ){
			selected = false;existingSelection = false;
			StandardClause standardClause = new StandardClause();
			if( isQuote ){
				if( Utils.isEmpty( ttrnExclusionQuos ) ){
					if( exclusion.getPexDefaultInd() == 2 ){
						selected = true;
					}
				}
				for( TTrnPolicyExclusionQuo exclusionQuo : ttrnExclusionQuos ){

					if( !Utils.isEmpty( exclusionQuo ) ){
						if( exclusion.getId().getPexCode() == exclusionQuo.getId().getTpeCode() ){

							//if(!Utils.isEmpty( exclusionQuo.getTpeAmount() ) || exclusionQuo.getTpeStatus().intValue() != 4 ){
							if( !Utils.isEmpty( exclusionQuo.getTpeAmount() ) && exclusionQuo.getTpeStatus().intValue() != 4 ){
								standardClause.setAmount( exclusionQuo.getTpeAmount() );
							}

							if( exclusionQuo.getTpeStatus().intValue() != 4 ){
								selected = true;
								break;
							}
							//}	
						}
					}

				}
			}
			else{

				if( Utils.isEmpty( ttrnExclusions ) ){
					if( exclusion.getPexDefaultInd() == 2 ){
						selected = true;
					}
				}
				for( TTrnPolicyExclusion policyExclusion : ttrnExclusions ){

					if( !Utils.isEmpty( policyExclusion ) ){
						if( exclusion.getId().getPexCode() == policyExclusion.getId().getTpeCode() ){
							//if(!Utils.isEmpty( policyExclusion.getTpeAmount() ) || policyExclusion.getTpeStatus().intValue() != 4 ){
							if( !Utils.isEmpty( policyExclusion.getTpeAmount() ) && policyExclusion.getTpeStatus().intValue() != 4 ){
								standardClause.setAmount( policyExclusion.getTpeAmount() );
							}
							if( policyExclusion.getTpeStatus().intValue() != 4 ){
								selected = true;
								break;
							}
							//Added by Anveshan
							if(endtId.equals( policyExclusion.getId().getTpeEndtId()) &&  policyExclusion.getTpeStatus().intValue() == 4 ){
								isDeletedInCurrentEndt = true;
							}
						}
					}

				}

			}
			existingSelection = selected;
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			if(exclusion.getId().getPexCode()== SvcConstants.STUDENT_LIABILITY_PEX_CODE_SHORT_2  
					&& isStudentLiabilityflagEnabled)
			{
				selected = false;
			}
			if( exclusion.getId().getPexCode()== SvcConstants.STUDENT_LIABILITY_PEX_CODE_SHORT_2 
					&& !isStudentLiabilityflagEnabled)
			{
				selected = true;
			}
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			
			standardClause.setClauseType( "E" );

			if( Utils.isEmpty( standardClause.getAmount() ) && !Utils.isEmpty( exclusion.getPexAmount() ) ){
				standardClause.setAmount( exclusion.getPexAmount().toString() );
			}

			if( !Utils.isEmpty( exclusion.getId().getPexCode() ) ){
				standardClause.setClauseCode( Long.valueOf( String.valueOf( exclusion.getId().getPexCode() ) ) );
			}

			if( !Utils.isEmpty( exclusion.getPexEText() ) ){
				standardClause.setDescription( exclusion.getPexEText() );
			}

			standardClause.setSelected( selected );
			standardClause.setIsDefault( exclusion.getPexDefaultInd() );
			standardClause.setSectionID( sectionId.longValue() );
			standardClause.setDeletedInCurrentEndt( isDeletedInCurrentEndt );
			isDeletedInCurrentEndt = false;
			standardClause.setExistingSelection( existingSelection );

			clauseList.add( standardClause );
		}

	}

	private Long getLatestEndtId( PolicyVO policyVO ){
		Long endtId = 0L;

		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && policyVO.getEndtId() < policyVO.getNewEndtId() ){
			endtId = policyVO.getNewEndtId();
		}
		else{
			if( !Utils.isEmpty( policyVO.getEndtId() ) ){
				endtId = policyVO.getEndtId();
			}
		}

		return endtId;
	}

	private void getConditionsClauses( List<StandardClause> clauseList, BaseVO baseVO, Integer sectionId, Integer scheme, Integer policyType, Boolean isExcess, Boolean isBoth ){

		List<TTrnPolicyConditionQuo> ttrnConditionsQou = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyConditionQuo>( TTrnPolicyConditionQuo.class );
		List<TTrnPolicyCondition> ttrnConditions = new com.mindtree.ruc.cmn.utils.List<TTrnPolicyCondition>( TTrnPolicyCondition.class );

		PolicyVO policyVO = null;
		CommonVO commonVO = null;
		List<UWQuestionVO> uwQuestionVOList=null;
		boolean isQuote = false;
		boolean isStudentLiabilityflagEnabled = false;
		Long endtId = null, policyId = null;
		boolean isDeletedInCurrentEndt = false;
		boolean selected,existingSelection = false;
		Date effectiveDatePol=null;
				
		if( baseVO instanceof PolicyVO ){
			//Phase 1 & 2 Specific block
			policyVO = (PolicyVO) baseVO;
			isQuote = policyVO.getIsQuote();
			policyId = DAOUtils.getSectionPolicyId( policyVO, sectionId, getHibernateTemplate() );
			endtId = getLatestEndtId( policyVO );
			
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			if( 6  == sectionId ){
				isStudentLiabilityflagEnabled = DAOUtils.isStudentLiabilitySelected(endtId,policyId, policyVO, getHibernateTemplate());
			}
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), policyVO.getQuoteNo());
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
		}
		else if( baseVO instanceof CommonVO ){
			//Phase 3 Specific block
			commonVO = (CommonVO) baseVO;
			isQuote = commonVO.getIsQuote();
			policyId = commonVO.getPolicyId();
			endtId = commonVO.getEndtId();
			
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			effectiveDatePol = DAOUtils.getPreparedDate(getHibernateTemplate(), commonVO.getQuoteNo());
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
		}
		else{
			throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO onl_3" );
		}

		Integer hdrCode = null;
		hdrCode = getHdrCodeForSection( sectionId, policyType );
		
		if( isExcess ){
			hdrCode = 1;
		}
		
		List<TMasPolicyCondition> masConditions = null;

		if(!isBoth){
		 masConditions = (List<TMasPolicyCondition>) getHibernateTemplate().find(
					"from TMasPolicyCondition masCon where masCon.id.pcSchCode=? and masCon.id.pcClCode = ? and masCon.pcPhrCode = ? and trunc(masCon.pcEffectiveDate) <= ? and trunc(masCon.pcExpiryDate) >= ?", scheme,
					Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode,effectiveDatePol,effectiveDatePol );
		 /*if(baseVO instanceof PolicyVO)
			{
				masConditions = (List<TMasPolicyCondition>) getHibernateTemplate().find(
					"from TMasPolicyCondition masCon where masCon.id.pcSchCode=? and masCon.id.pcClCode = ? and masCon.pcPhrCode = ? and trunc(masCon.pcEffectiveDate) <= ? and trunc(masCon.pcExpiryDate) >= ?", scheme,
					Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode,effectiveDatePol,effectiveDatePol );
			}
			else if( baseVO instanceof CommonVO )
			{
				masConditions = (List<TMasPolicyCondition>) getHibernateTemplate().find(
						"from TMasPolicyCondition masCon where masCon.id.pcSchCode=? and masCon.id.pcClCode = ? and masCon.pcPhrCode = ? ", scheme,
						Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode,effectiveDatePol,effectiveDatePol);
			}*/
			if( !Utils.isEmpty( policyId ) ){
				if( isQuote ){
					ttrnConditionsQou = (List<TTrnPolicyConditionQuo>) getHibernateTemplate().find(
							"from TTrnPolicyConditionQuo quoCon where quoCon.id.tpcPolicyId=? and (quoCon.id.tpcEndtId = ? or "
									+ "(quoCon.id.tpcEndtId < ? and trunc(quoCon.tpcValidityEndDate) = ?)) and quoCon.id.tpcCode in "
									+ com.Constant.CONST_SELECT_MAS_ID_PCCODE_FROM_TMASPOLICYCONDITION_MAS_WHERE_MAS_ID_PCSCHCODE_END + " and mas.id.pcClCode = ? and mas.pcPhrCode = ?)", policyId,
							endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
				}
				else{
					ttrnConditions = (List<TTrnPolicyCondition>) getHibernateTemplate().find(
							"from TTrnPolicyCondition polCon where polCon.id.tpcPolicyId=? and (polCon.id.tpcEndtId = ? or "
									+ "(polCon.id.tpcEndtId < ? and trunc(polCon.tpcValidityEndDate) = ?)) and polCon.id.tpcCode in "
									+ com.Constant.CONST_SELECT_MAS_ID_PCCODE_FROM_TMASPOLICYCONDITION_MAS_WHERE_MAS_ID_PCSCHCODE_END + " and mas.id.pcClCode = ? and mas.pcPhrCode = ?)", policyId,
							endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
				}
			}
		}else{
			masConditions = (List<TMasPolicyCondition>) getHibernateTemplate().find(
					"from TMasPolicyCondition masCon where masCon.id.pcSchCode=? and masCon.id.pcClCode = ? and masCon.pcPhrCode in (?,1) and trunc(masCon.pcEffectiveDate) <= ? and trunc(masCon.pcExpiryDate) >= ?", scheme,
					Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode,effectiveDatePol,effectiveDatePol );
			if( !Utils.isEmpty( policyId ) ){
				if( isQuote ){
                    Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
                    Query query = session.createQuery("from com.rsaame.pas.dao.model.TTrnPolicyConditionQuo quoCon where quoCon.id.tpcPolicyId="+policyId+" and (quoCon.id.tpcEndtId = "+endtId+" or "
                                            + "(quoCon.id.tpcEndtId < "+endtId+" and trunc(quoCon.tpcValidityEndDate) = '31-DEC-2049' )) and quoCon.id.tpcCode in "
                                            + "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = "+scheme+""
                                            + " and mas.id.pcClCode = "+Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ))+" and mas.pcPhrCode in ( "+hdrCode+",1))");
                          ttrnConditionsQou = (List<TTrnPolicyConditionQuo>) query.list();                       
                    
                    
				
					
					}
				else{
					ttrnConditions = (List<TTrnPolicyCondition>) getHibernateTemplate().find(
							"from com.rsaame.pas.dao.model.TTrnPolicyCondition polCon where polCon.id.tpcPolicyId=? and (polCon.id.tpcEndtId = ? or "
									+ "(polCon.id.tpcEndtId < ? and trunc(polCon.tpcValidityEndDate) = ?)) and polCon.id.tpcCode in "
									+ com.Constant.CONST_SELECT_MAS_ID_PCCODE_FROM_TMASPOLICYCONDITION_MAS_WHERE_MAS_ID_PCSCHCODE_END + " and mas.id.pcClCode = ? and mas.pcPhrCode in( ?,1))", policyId,
							endtId, endtId, SvcConstants.EXP_DATE, scheme, Short.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionId ) ), hdrCode );
				}
			}
			
			
		}
		
		

		for( TMasPolicyCondition condition : masConditions ){
			selected = false ;existingSelection = false;
			boolean amountSet = false;
			StandardClause standardClause = new StandardClause();
			if( isQuote ){
				if( Utils.isEmpty( ttrnConditionsQou ) ){
					if( condition.getPcDefaultInd() == 2  || condition.getPcDefaultInd() == 1 ){
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
						//Added by Anveshan
						if(endtId.equals( con.getId().getTpcEndtId()) &&  con.getTpcStatus().intValue() == 4 ){
							isDeletedInCurrentEndt = true;
					}
				}
			
			}
			}
			existingSelection = selected;
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			if(condition.getId().getPcCode()== Short.parseShort(SvcConstants.STUDENT_LIABILITY_PC_CODE) && isStudentLiabilityflagEnabled)
			{
				selected = true;
			} else if(condition.getId().getPcCode() == Short.parseShort(SvcConstants.STUDENT_LIABILITY_PC_CODE) && !isStudentLiabilityflagEnabled)
			{
				selected = false;	
			}
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR

			standardClause.setClauseType( "C" );

			if( Utils.isEmpty( standardClause.getAmount() ) && !Utils.isEmpty( condition.getPcAmount() ) ){
				/*
				 * set amount field to default value from master table if clause is deselected else
				 * set amount field to whatever is fetched from db.
				 */
				/*if( !selected ){
					standardClause.setAmount(  condition.getPcAmount() );
				}*/
				// Changed from AND to OR
				if( !amountSet || !selected){
					standardClause.setAmount( condition.getPcAmount() );
				}
			}

			if( !Utils.isEmpty( condition.getId().getPcCode() ) ){
				standardClause.setClauseCode( Long.valueOf( String.valueOf( condition.getId().getPcCode() ) ) );
			}

			if( !Utils.isEmpty( condition.getPcEText() ) ){
				standardClause.setDescription( condition.getPcEText() );
			}
			standardClause.setExistingSelection( existingSelection );
			standardClause.setIsDefault( condition.getPcDefaultInd() );
			standardClause.setSelected( selected );
			standardClause.setSectionID( sectionId.longValue() );
			standardClause.setDeletedInCurrentEndt( isDeletedInCurrentEndt );
			isDeletedInCurrentEndt = false;

			clauseList.add( standardClause );
		}

	}

	@Override
	public BaseVO getNonStdClauses( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;

				Integer basicSectionID = null;
				if( isSectionPresent( PAR_SECTION_ID, policyVO ) ){
					basicSectionID = PAR_SECTION_ID;
				}
				else if( isSectionPresent( PL_SECTION_ID, policyVO ) ){
					basicSectionID = PL_SECTION_ID;
				}

				java.util.List<SectionVO> riskDetails = policyVO.getRiskDetails();
				SectionVO section = getSection( basicSectionID, riskDetails );
				Long policyId = section.getPolicyId();
				if( Utils.isEmpty( policyId ) ){
					policyId = DAOUtils.getSectionPolicyId( policyVO, basicSectionID, getHibernateTemplate() );
				}
				List<TTrnNonStdTextQuo> tTrnNonStdTextQuoList = null;
				Long latestEndtId = SvcUtils.getLatestEndtId( policyVO );
				tTrnNonStdTextQuoList = (List<TTrnNonStdTextQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_NON_STD_TEXT_POL", policyVO.getAppFlow(), getHibernateTemplate(), false,
						latestEndtId, policyId );

				DataHolderVO<java.util.List<NonStandardClause>> holderVO = getNonStdClauseForPolicy( tTrnNonStdTextQuoList );

				return holderVO;
			}
			else if( baseVO instanceof CommonVO ){

				CommonVO commonVO = (CommonVO) baseVO;
				List<TTrnNonStdTextQuo> tTrnNonStdTextQuoList = null;

				tTrnNonStdTextQuoList = (List<TTrnNonStdTextQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_NON_STD_TEXT_POL", commonVO.getAppFlow(), getHibernateTemplate(), false,
						commonVO.getEndtId(), commonVO.getPolicyId() );

				DataHolderVO<java.util.List<NonStandardClause>> holderVO = getNonStdClauseForPolicy( tTrnNonStdTextQuoList );

				return holderVO;

			}
			else{

				throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO onl_4" );
			}
		}

		return baseVO;
	}

	private DataHolderVO<List<NonStandardClause>> getNonStdClauseForPolicy( List<TTrnNonStdTextQuo> tTrnNonStdTextQuoList ){

		java.util.List<NonStandardClause> nonStandardClause = new com.mindtree.ruc.cmn.utils.List<NonStandardClause>( NonStandardClause.class );
		for( TTrnNonStdTextQuo nonStandardClauseTmp : tTrnNonStdTextQuoList ){
			NonStandardClause clauseVO = new NonStandardClause();
			if( !Utils.isEmpty( nonStandardClauseTmp ) ){
				if( !Utils.isEmpty( nonStandardClauseTmp.getId().getNstTypeCode() ) ){

					if( nonStandardClauseTmp.getId().getNstTypeCode() == 1 ){
						clauseVO.setClauseType( "C" );
					}
					else if( nonStandardClauseTmp.getId().getNstTypeCode() == 2 ){
						clauseVO.setClauseType( "W" );
					}
					else if( nonStandardClauseTmp.getId().getNstTypeCode() == 3 ){
						clauseVO.setClauseType( "E" );
					}

				}
				if( !Utils.isEmpty( nonStandardClauseTmp.getNstEText() ) ){
					clauseVO.setDescription( nonStandardClauseTmp.getNstEText() );
				}
				if( !Utils.isEmpty( nonStandardClauseTmp.getNstValidityStartDate() ) ){
					clauseVO.setVsd( nonStandardClauseTmp.getNstValidityStartDate() );
				}
				nonStandardClause.add( clauseVO );

			}
		}

		DataHolderVO<java.util.List<NonStandardClause>> holderVO = new DataHolderVO<List<NonStandardClause>>();
		holderVO.setData( nonStandardClause );
		return holderVO;
	}

	private Integer getHdrCodeForSection( Integer sectionId, Integer policyType ){

		String GET_SECTION_HDR = "select sec_pc_hdr from t_mas_pkg_pol_section where sec_pt_code=" + policyType + " and sec_id=" + sectionId;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( GET_SECTION_HDR );
		List<Object> result = query.list();
		return ( converter.getBFromA( result.get( 0 ) ) );
	}

	private Integer getDefaultValue( Object[] object, Integer hdrCode ){

		String GET_DEFAULT_VALUES = "";
		if( object[ 13 ].toString().equalsIgnoreCase( FLAGS[ 0 ] ) ){

			GET_DEFAULT_VALUES = "SELECT pc_default_ind from t_mas_policy_condition where pc_cl_code=" + ( (BigDecimal) object[ 3 ] ).shortValue() + " and pc_phr_code=" + hdrCode
					+ " and pc_code=" + object[ 8 ];

		}
		else if( object[ 13 ].toString().equalsIgnoreCase( FLAGS[ 1 ] ) ){

			GET_DEFAULT_VALUES = "SELECT pex_default_ind from t_mas_policy_exclusion  where pex_cl_code=" + ( (BigDecimal) object[ 3 ] ).shortValue() + " and pex_phr_code="
					+ hdrCode + " and pex_code=" + object[ 8 ];

		}
		else if( object[ 13 ].toString().equalsIgnoreCase( FLAGS[ 2 ] ) ){

			GET_DEFAULT_VALUES = "SELECT pw_default_ind from t_mas_policy_warranty where pw_cl_code=" + ( (BigDecimal) object[ 3 ] ).shortValue() + " and pw_phr_code=" + hdrCode
					+ " and pw_code=" + object[ 8 ];

		}

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( GET_DEFAULT_VALUES );
		List<Object> result = query.list();

		return ( converter.getBFromA( result.get( 0 ) ) );
	}

	/**
	 * 
	 * Returns the SectionVO for the passed section ID, The reference is
	 * returned.
	 */
	private SectionVO getSection( Integer sectionId, List<SectionVO> riskDetails ){
		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );
		int indexOfSection = riskDetails.indexOf( finderSection );
		return riskDetails.get( indexOfSection );
	}

	@Override
	public BaseVO saveClauses( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof DataHolderVO ){
				DataHolderVO<Object[]> inputs = (DataHolderVO<Object[]>) baseVO;
				Object[] data = inputs.getData();

				SectionVO sectionVO = (SectionVO) data[ 0 ];
				List<StandardClause> stdClausesList = (List<StandardClause>) data[ 2 ];

				PolicyVO policyVO = null;
				CommonVO commonVO = null;

				if( data[ 1 ] instanceof PolicyVO ){
					//Phase 1 & 2 Specific block
					policyVO = (PolicyVO) data[ 1 ];
					getEndtIdAndCreatePolicyRec( policyVO, sectionVO );

					if( !Utils.isEmpty( sectionVO.getStandardClauses() ) ){
						saveStandardClause( sectionVO, policyVO, stdClausesList, false );
					}

					DAOUtils.updateVED( policyVO, sectionVO.getClassCode(), sectionVO.getSectionId() );
			if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
						DAOUtils.deletePrevEndtText( sectionVO.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), sectionVO.getSectionId(), LOC_ID );
						updateEndtText( policyVO, sectionVO, "clauseTextGenProc" );
						updateEndtText( policyVO, sectionVO, "clauseDelTextGenProc" );
						updateEndtTextForLimitChange(policyVO, sectionVO, stdClausesList);
						//updateEndtText( policyVO, sectionVO, "nonStdTextGenProc" );
						//updateEndtText( policyVO, sectionVO, "nonStdTextDelGenProc" );
						updateEndtNonStdClauseChange( policyVO, sectionVO, "nonStdTextColGenProc" );
						
					}
					
				}
				else if( data[ 1 ] instanceof CommonVO ){
					//Phase 3 Specific block
					commonVO = (CommonVO) data[ 1 ];
					boolean isExcess = (Boolean) data[ 3 ];
					getEndtIdAndCreatePolicyRec( commonVO );

					if( !Utils.isEmpty( sectionVO.getStandardClauses() ) ){
						saveStandardClause( sectionVO, commonVO, stdClausesList, isExcess );
					}
					DAOUtils.updateHomeTravelVED( commonVO );
					if( ( commonVO.getAppFlow() == Flow.AMEND_POL ) ){
						DAOUtils.deletePrevEndtText( commonVO.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), CONDTIONS_SECTION_ID, LOC_ID );
						updateEndtText(commonVO,"clauseTextHTProc" );
						updateEndtText( commonVO,"clauseDelTextHTProc" );

					}
				}
				else{

					throw new BusinessException( "err.invalid.classType", null, "Invalid Class Type : Expecting instance of PolicyVO/CommonVO onl_5" );
				}
				ThreadLevelContext.clearAll();
				return policyVO;
			}
		}
		return baseVO;
	}

	public static void updateEndtTextForLimitChange( PolicyVO policyVO, SectionVO sectionVO, List<StandardClause> stdClausesList ){
		PASStoredProcedure sp = null;
		

		/*sp = (PASStoredProcedure) Utils.getBean( "addEndtTextHeaderFooter" );
		try{
			Map results = sp.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(),sectionVO.getSectionId());
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is nul_1" );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_1" );
			}*/
		
				Integer flag = 0;
		for(StandardClause stdClause : stdClausesList){
		
			sp = (PASStoredProcedure) Utils.getBean( "clauseColTextGenProc" );
			try{			
				//Map results = sp.call( sectionVO.getPolicyId(), policyVO.getNewEndtId(),stdClause.getClauseCode(), sectionVO.getClassCode(),SvcUtils.getUserId( policyVO ), CONDTIONS_SECTION_ID, LOC_ID );
				Map<String, Object> results = sp.call(sectionVO.getPolicyId(), policyVO.getNewEndtId(),stdClause.getClauseCode(), sectionVO.getClassCode(),SvcUtils.getUserId( policyVO ), sectionVO.getSectionId(), LOC_ID,flag,stdClause.getClauseType());
				if( Utils.isEmpty( results ) ){
					LOGGER.info( "The result of the stored procedure is null updateEndtTextForLimitChange()" );
				}
				else{
					Integer resFlag = Integer.valueOf(results.get("p_flag_output").toString());
					flag = resFlag;
				}				
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_2" );
			}
		}
		
	}

	public static void updateEndtText( PolicyVO policyVO, SectionVO sectionVO, String beanName ){
		PASStoredProcedure sp = null;
		sp = (PASStoredProcedure) Utils.getBean( beanName );
		try{
			Map<String, Object> results = sp.call( sectionVO.getPolicyId(), policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), sectionVO.getSectionId(), LOC_ID );
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is null for updateEndtText(policyVO, sectionVO, beanName)" );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_3" );
		}

	}
	
	public static void updateEndtTextNonStandard( PolicyVO policyVO, SectionVO sectionVO, String beanName ){
		PASStoredProcedure sp = null;
		Integer nonStdSectionID = Integer.valueOf( Utils.getSingleValueAppConfig( "CONDTIONS_SECTION_ID" ) );
		sp = (PASStoredProcedure) Utils.getBean( beanName );
		try{
			Map<String, Object> results = sp.call( sectionVO.getPolicyId(), policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ), nonStdSectionID, LOC_ID );
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is null for updateEndtTextNonStandard()" );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_4" );
		}

	}
	
	private void updateEndtText( CommonVO commonVO, String beanName ){
		PASStoredProcedure sp = null;
		sp = (PASStoredProcedure) Utils.getBean( beanName );
		try{
			Map<String, Object> results = sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), SvcUtils.getUserId( commonVO ), Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ), null );
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is null for updateEndtText(commonVO, beanName )" );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_5" );
		}

	}

	/**
	 * Creates policy record for the policy id and endt id combination if it already doesn't exists and updates sectionVO
	 * with the policy id for the section under consideration
	 * @param policyVO
	 * @param sectionVO
	 */
	private void getEndtIdAndCreatePolicyRec( PolicyVO policyVO, SectionVO sectionVO ){

		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		ThreadLevelContext.clearAll();

		/* Let us get the system date right now and use from here on for this transaction. */
		//Closing date : SvcConstants.TLC_KEY_SYSDATE is set to derived VSD
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow() ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.FALSE );

		/* Let us first generate the Endt_Id. This will initiate values that will be required for the Pojo Id because VSD forms
		 * part of the key in most tables. */
		DAOUtils.fetchEndtId( policyVO, getHibernateTemplate() );

		/* If the policy Id for this section's class code has not been created, we have to do it now. Both T_TRN_POLICY(_QUO) ,
		 * T_MAS_CASH_CUSTOMER(_QUO) and T_TRN_SECTION_DETAILS(_QUO) records have to be created. 
		 * 
		 * TODO : But premium 0.0 has to be changed to actual premium if directly navigated from left panel without changing
		 * anything in PAR or any other risk sections.
		 * */

		Long policyId = DAOUtils.createPolicyRecord( policyVO, Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionVO.getSectionId() ) ), sectionVO.getSectionId() );
		sectionVO.setPolicyId( policyId );

	}

	private void getEndtIdAndCreatePolicyRec( CommonVO commonVO ){

		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		ThreadLevelContext.clearAll();

		/* Let us get the system date right now and use from here on for this transaction. */
		//Closing date : SvcConstants.TLC_KEY_SYSDATE is set to derived VSD
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), commonVO.getAppFlow() ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.FALSE );

		/* Let us first generate the Endt_Id. This will initiate values that will be required for the Pojo Id because VSD forms
		 * part of the key in most tables. */
		DAOUtils.fetchEndtId( commonVO, getHibernateTemplate() );

		/* If the policy Id for this section's class code has not been created, we have to do it now. Both T_TRN_POLICY(_QUO) ,
		 * T_MAS_CASH_CUSTOMER(_QUO) and T_TRN_SECTION_DETAILS(_QUO) records have to be created. 
		 * 
		 * TODO : But premium 0.0 has to be changed to actual premium if directly navigated from left panel without changing
		 * anything in PAR or any other risk sections.
		 * */

		int secId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) );

		DAOUtils.createPolicyRecord( commonVO, Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + secId ) ), secId );

	}

	/**
	 * Inserts records to temporary table T_TRN_TEMP_CON_EXC_WAR and then invokes DEL_INS_CON_EXC_WAR_POL/_ENDT procedure to 
	 * insert records for standard conditions, standard exclusions and standard warranties
	 * @param sectionVO
	 * @param policyVO
	 * @param stdClausesList
	 * @param isExcess 
	 */
	private void saveStandardClause( SectionVO sectionVO, BaseVO baseVO, List<StandardClause> stdClausesList, boolean isExcess ){

		Session session = null;
		SQLQuery sqlQuery = null;

		String INSERT_TEMP_VALUES = "";
		PolicyVO policyVO = null;
		CommonVO commonVO = null;
		Flow flow = null;
		Long endtId = null, policyId = null;
		Short sectionId = null;
		boolean isSBS = false;
		Integer hdrCode = null, classCode = null, policyType = null;
		List<Integer> schemePolType = null;
		Integer tariffCode = null;
		String userProfile = null;

		if( baseVO instanceof PolicyVO ){

			policyVO = (PolicyVO) baseVO;
			flow = policyVO.getAppFlow();
			endtId = policyVO.getNewEndtId();
			sectionId = sectionVO.getSectionId().shortValue();
			policyId = sectionVO.getPolicyId();
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( "SEC_" + sectionVO.getSectionId() ) ) ){
				classCode = Integer.valueOf( Utils.getSingleValueAppConfig( "SEC_" + sectionVO.getSectionId() ) );
			}
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_TYPE ) ) ){
				policyType = Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_TYPE ) );
			}
			isSBS = true;
		}
		else if( baseVO instanceof CommonVO ){
			commonVO = (CommonVO) baseVO;
			schemePolType = DAOUtils.getSchemeAndPolicyType( commonVO, getHibernateTemplate() );
			tariffCode = DAOUtils.getTariffCode( commonVO, getHibernateTemplate() );
			updateStandardClauses( sectionVO.getStandardClauses(), tariffCode, commonVO, getHibernateTemplate() );
			flow = commonVO.getAppFlow();
			endtId = commonVO.getEndtId();
			sectionId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ).shortValue();
			policyId = commonVO.getPolicyId();
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( commonVO.getLob() + "_CLASS_CODE" ) ) ){
				classCode = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_CLASS_CODE" ) );
			}
			if( !Utils.isEmpty( schemePolType.get( SvcConstants.oneVal ) ) ){
				policyType = schemePolType.get( SvcConstants.oneVal );
			}
			if( !isExcess ){
				hdrCode = getHdrCodeForSection( Integer.valueOf( sectionId ), policyType );
			}
			else{
				hdrCode = 1;
			}
			userProfile  = ( (UserProfile) ( commonVO ).getLoggedInUser()).getRsaUser().getProfile();
		}
		
		session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		if( flow == Flow.AMEND_POL ){
			INSERT_TEMP_VALUES = "insert into T_TRN_TEMP_CON_EXC_WAR(TEMP_POLICY_ID,TEMP_ENDT_ID,TEMP_CL_CODE,TEMP_PT_CODE,TEMP_SEC_ID,TEMP_CODE, temp_status,TEMP_FLAG,TEMP_AMOUNT)"
					+ " values(:polId,:endId,:clCode,:ptCode,:secId,:code,:status,:flag,:amount)";
		}
		else{
			INSERT_TEMP_VALUES = "insert into T_TRN_TEMP_CON_EXC_WAR(TEMP_POLICY_ID,TEMP_ENDT_ID,TEMP_SEC_ID,TEMP_CODE,temp_status,TEMP_FLAG,TEMP_AMOUNT)"
					+ " values(:polId,:endId,:secId,:code,:status,:flag,:amount)";
		}
		sqlQuery = session.createSQLQuery( INSERT_TEMP_VALUES );
		boolean toInsert = false;
		int status = SvcConstants.POL_STATUS_PENDING;
		int count = 0;

		for( String flag : FLAGS ){
			count = 0;
			for( StandardClause standardClause : sectionVO.getStandardClauses() ){
				//if(isExcess || !isValid(standardClause)) continue;
				StandardClause oldClause = stdClausesList.get( count++ );
				if (oldClause.isSelected() != standardClause.isSelected() || oldClause.isExistingSelection() != standardClause.isSelected()){
					toInsert = true;
					if( standardClause.isSelected() ){
						status = SvcConstants.POL_STATUS_PENDING;
					}
					else{
						status = SvcConstants.POL_STATUS_DELETED;
					}
				}
				/*Added the below check for Excess to be made edited for RSA user - for building/content/personal possesions
				* Ticket ID : 80398
				*/
				else if (((flow == Flow.EDIT_QUO)||(flow == Flow.AMEND_POL)) && (oldClause.isSelected() == standardClause.isSelected()) 
						&& !Utils.isEmpty(oldClause.getAmount()) && !Utils.isEmpty(standardClause.getAmount()) 
						&& (!(oldClause.getAmount()).equalsIgnoreCase(standardClause.getAmount()))&& !Utils.isEmpty(userProfile) 
						&& userProfile.equalsIgnoreCase("employee") ){
					toInsert = true;
					
				}
				// Added by Anveshan..
				else if (oldClause.isDeletedInCurrentEndt()) {
					status = SvcConstants.POL_STATUS_DELETED;
					toInsert = true;
				}
				else{
					toInsert = false;
				}
				
				/*Added the below check for Excess to be made edited for RSA user - for building/content/personal possesions
				* ( flow == Flow.EDIT_QUO && toInsert )
				* Ticket ID : 80398
				*/

				if( ( standardClause.isSelected() ) || ( flow == Flow.AMEND_POL && toInsert ) || ( flow == Flow.EDIT_QUO && toInsert )|| ( !standardClause.isSelected() && toInsert
				/*&& ( !Utils.isEmpty( policyVO.getNewEndtId() ) && policyVO.getNewEndtId() != Long
				.valueOf( SvcConstants.FIRST_ENDT ) )*/) ){

					if( flag.equalsIgnoreCase( standardClause.getClauseType() ) ){
						if( !Utils.isEmpty( policyId ) ){
							sqlQuery.setLong( "polId", policyId );
						}
						if( ( flow == Flow.AMEND_POL ) ){
							if( !Utils.isEmpty(classCode) ){
								sqlQuery.setLong( "clCode", classCode );
							}
							if( !Utils.isEmpty(policyType) ){
								sqlQuery.setLong( "ptCode", policyType );
							}
						}
						if( !Utils.isEmpty( status ) ){
							sqlQuery.setLong( "status", status );
						}
						if( !Utils.isEmpty( endtId ) ){
							sqlQuery.setLong( "endId", endtId );
						}
						if( !Utils.isEmpty( sectionId ) ){
							sqlQuery.setShort( "secId", sectionId );
						}
						if( !Utils.isEmpty( standardClause.getClauseCode() ) ){
							sqlQuery.setShort( "code", standardClause.getClauseCode().shortValue() );
						}
						if( !Utils.isEmpty( standardClause.isSelected() ) ){
							sqlQuery.setString( "flag", standardClause.getClauseType() );
						}
						if( !Utils.isEmpty( standardClause.getAmount() ) ){
							sqlQuery.setString( "amount", standardClause.getAmount() );
						}
						else{
							sqlQuery.setString( "amount", null );
						}

						sqlQuery.executeUpdate();
						if( isSBS && ( flow == Flow.AMEND_POL ) ){
							callStoredProcEndt( sectionVO, policyVO, flag, status );
							
						}
						/*Added the below check for Excess to be made edited for RSA user - for building/content/personal possesions
						* Ticket ID : 80398
						*/
						if( !isSBS && (( (SvcUtils.getBasicFlowCommonResolveReferral( commonVO ) == Flow.AMEND_POL )))){
							callStoredProcHomeTravelEndt( commonVO, flag, hdrCode,status);
						}
						
						if(!isSBS && (Flow.EDIT_QUO.equals( SvcUtils.getBasicFlowCommonResolveReferral( commonVO )))){
							callStoredProcHomeTravel( commonVO, flag, hdrCode );
						}
						
					}
				}
				status = SvcConstants.POL_STATUS_PENDING;
				
			}

			if( ( flow != Flow.AMEND_POL )){

				if( isSBS ){
					callStoredProc( sectionVO, policyVO, flag );
				}
				else{
					/*Added the below check for Excess to be made edited for RSA user only - for building/content/personal possesions
					* !Utils.isEmpty(userProfile) && !userProfile.equalsIgnoreCase("employee")
					* Ticket ID : 80398
					*/
					if(Flow.CREATE_QUO.equals( SvcUtils.getBasicFlowCommonResolveReferral( commonVO ))
							||(Flow.EDIT_QUO.equals( SvcUtils.getBasicFlowCommonResolveReferral( commonVO ))
									&& !Utils.isEmpty(userProfile) && !userProfile.equalsIgnoreCase("employee"))){
						callStoredProcHomeTravel( commonVO, flag, hdrCode );
					}
				}

			}
		}
	}
	

	private List<StandardClause> updateStandardClauses( List<StandardClause> list, Integer tariffCode, CommonVO commonVO, HibernateTemplate ht ){
		
		String[] splTariffs = Utils.getMultiValueAppConfig( "SPL_TAR_CODES" );
		String[] anaClauseCodes = Utils.getMultiValueAppConfig( "ANA_CLAUSE_CODES" );
		List<StandardClause> clausesToRemove = new ArrayList<StandardClause>();
		
		if( !Utils.isEmpty( splTariffs ) && splTariffs.length > 0 && CopyUtils.asList( splTariffs ).contains( tariffCode.toString() ) ){
			
			Long contentSI = DAOUtils.getContentSI( commonVO , getHibernateTemplate() );
			String amount = null;
			boolean isCntAdded = false;
			
			if( commonVO.getEndtId() > 0 && !Utils.isEmpty( contentSI ) && !checkCntForPrevVersion( commonVO , getHibernateTemplate() )){
				isCntAdded = true;
			}
						
			for( StandardClause stdClause : list ){
				
				if( !Utils.isEmpty( anaClauseCodes ) && splTariffs.length > 0 && !Utils.isEmpty( contentSI ) && 
						CopyUtils.asList( anaClauseCodes ).contains( stdClause.getClauseCode().toString() ) ){
					
					Session session = ht.getSessionFactory().getCurrentSession();
					SQLQuery query = null;
					query = session.createSQLQuery( GET_COVERAGE_AMT );

					query.setParameter( "contentSI", contentSI );
					query.setParameter( "clauseCode", stdClause.getClauseCode() );
					
					List<Object> result = query.list();
					
					if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( SvcConstants.zeroVal ) ) ){
						amount = (String)result.get( SvcConstants.zeroVal );
					}
					stdClause.setAmount( amount );
					
					if( isCntAdded ){
						stdClause.setSelected( true );
					}
					
				}else if( !Utils.isEmpty( anaClauseCodes ) && splTariffs.length > 0 && Utils.isEmpty( contentSI ) && 
						CopyUtils.asList( anaClauseCodes ).contains( stdClause.getClauseCode().toString() ) ){
					if( commonVO.getEndtId() == SvcConstants.zeroVal ){
						clausesToRemove.add( stdClause );
					}else{
						stdClause.setSelected( false );
					}
					
				}
					
			}
			
			for( StandardClause clause : clausesToRemove ){
				list.remove( clause );
			}
			
		}

		return list;
		
	}

	private boolean checkCntForPrevVersion( CommonVO commonVO, HibernateTemplate hibernateTemplate ){
		
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery query = null;
		
		if( commonVO.getIsQuote() ){
			query = session.createSQLQuery( FETCH_CONTENT_PREV_QUO );
		}
		else{
			query = session.createSQLQuery( FETCH_CONTENT_PREV_POL );
		}

		query.setParameter( "pol_id", commonVO.getPolicyId() );
		query.setParameter( "endt_id", commonVO.getEndtId() );
		List<Object> result = query.list();

		if( result.size() > 0 && !Utils.isEmpty( result.get( SvcConstants.zeroVal ) ) && ( (BigDecimal)result.get( SvcConstants.zeroVal ) ).intValue() != SvcConstants.POL_STATUS_DELETED ){
			return true;
		}
		return false;
		
	}

	private boolean isValid(StandardClause standardClause) {
		Long clauseCode = standardClause.getClauseCode();
		if(clauseCode==1L ||clauseCode==2L ||clauseCode==3L ||clauseCode==4L ||clauseCode==5L){
			return false;
		}
		return true;
	}

	private void callStoredProc( SectionVO sectionVO, PolicyVO policyVO, String flag ){

		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "clausesStProc" );
		try{
			Map results = sp.call( sectionVO.getPolicyId(), policyVO.getNewEndtId(), sectionVO.getSectionId(), flag, SvcUtils.getUserId( policyVO ) );
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is nul_2" );
			}
			SQLQuery sqlQuery2 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( com.Constant.CONST_SELECT_FROM_T_TRN_TEMP_CON_EXC_WAR );
			sqlQuery2.list();

		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc_6" );
		}

	}

	private void callStoredProcHomeTravel( CommonVO commonVO, String flag, Integer hdrCode ){

		PASStoredProcedure sp = null;
		
		if(commonVO.getLob().equals( LOB.HOME ) || commonVO.getLob().equals( LOB.TRAVEL )){
			sp = (PASStoredProcedure) Utils.getBean( "clausesStProcHomeTravel" );
		}else{
			sp = (PASStoredProcedure) Utils.getBean( "clausesStProc" );
		}
		
		
		List<Integer> schemePolType = DAOUtils.getSchemeAndPolicyType( commonVO, getHibernateTemplate() );
		try{
			Map results = null;
			
			if(commonVO.getLob().equals( LOB.HOME ) || commonVO.getLob().equals( LOB.TRAVEL )){
				results = sp.call( commonVO.getPolicyId(), Long.valueOf( schemePolType.get( 1 ) ), commonVO.getEndtId(),
					Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ), flag, SvcUtils.getUserId( commonVO ), hdrCode );
			}else{
				results = sp.call( commonVO.getPolicyId(), commonVO.getEndtId(),
						Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ), flag, SvcUtils.getUserId( commonVO ));
			}
			
			if( Utils.isEmpty( results ) ){
				LOGGER.info( "The result of the stored procedure is nul_3" );
			}
			SQLQuery sqlQuery2 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( com.Constant.CONST_SELECT_FROM_T_TRN_TEMP_CON_EXC_WAR );
			sqlQuery2.list();

		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc_7" );
		}

	}

	private void callStoredProcEndt( SectionVO sectionVO, PolicyVO policyVO, String flag, int status ){

		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "clausesStEndtProc" );
		try{
			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
			sp.call( sectionVO.getPolicyId(), policyVO.getNewEndtId(), sectionVO.getSectionId(), flag, SvcUtils.getUserId( policyVO ), policyVO.getNewValidityStartDate(), status );
			SQLQuery sqlQuery2 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( com.Constant.CONST_SELECT_FROM_T_TRN_TEMP_CON_EXC_WAR );
			sqlQuery2.list();

		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc_8" );
		}

	}

	private void callStoredProcHomeTravelEndt( CommonVO commonVO, String flag, Integer hdrCode, int status ){

		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "clausesStEndtProcHomeTravel" );
		
		
		if(commonVO.getLob().equals( LOB.HOME ) || commonVO.getLob().equals( LOB.TRAVEL )){
			sp = (PASStoredProcedure) Utils.getBean( "clausesStEndtProcHomeTravel" );
		}else{
			sp = (PASStoredProcedure) Utils.getBean( "clausesStEndtProc" );
		}
		
		try{
			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
			
			if(commonVO.getLob().equals( LOB.HOME ) || commonVO.getLob().equals( LOB.TRAVEL )){
				sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ), flag,
						SvcUtils.getUserId( commonVO ), commonVO.getVsd(), status,hdrCode);
			}else{
				sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + com.Constant.CONST_SEC_ID ) ), flag,
						SvcUtils.getUserId( commonVO ), commonVO.getVsd(), status);
			}
			
			SQLQuery sqlQuery2 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( com.Constant.CONST_SELECT_FROM_T_TRN_TEMP_CON_EXC_WAR );
			sqlQuery2.list();

		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc_9" );
		}

	}

	@Override
	public BaseVO saveNonStandardClauses( BaseVO input ){

		BaseVO output = null;

		if( input instanceof PolicyVO ){

			output = handleNonStandardClausesSBS( input );
		}
		else{
			saveHomeTravelNonStandardClauses( input );
		}

		return output;
	}

	private BaseVO handleNonStandardClausesHomeTravel( BaseVO input ){
		HomeInsuranceVO homeInsuranceVO = null;
		TravelInsuranceVO travelInsuranceVO = null;

		if( input instanceof HomeInsuranceVO ){
			homeInsuranceVO = (HomeInsuranceVO) input;
			homeInsuranceVO.getCommonVO();
		}
		else if( input instanceof TravelInsuranceVO ){
			travelInsuranceVO = (TravelInsuranceVO) input;
			travelInsuranceVO.getCommonVO();
		}

		saveHomeTravelNonStandardClauses( input );

		return null;
	}

	private void saveHomeTravelNonStandardClauses( BaseVO input ){
		CommonVO commonVO = null;
		
		IBaseSaveOperation baseOperationDao;
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		List<NonStandardClause> nonStandardClauses = new ArrayList<NonStandardClause>();
		PolicyDataVO policyDataVO = (PolicyDataVO)input;
		commonVO = policyDataVO.getCommonVO();
		nonStandardClauses = policyDataVO.getNonStandardClauses();
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		if( commonVO.getIsQuote() ){
			loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		}
		else{
			loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		}

		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		loadDataInputVO.setDocCode( commonVO.getDocCode() );
		dataMap.put( SvcConstants.SAVE_CASH_CUST_DATA, GeneralInfoVO.class );

		BaseLoadSvc baseLoadSvc;
		if( commonVO.getIsQuote() ){
			baseLoadSvc = (BaseLoadSvc) Utils.getBean( "baseLoadSvc" );
		}
		else{
			baseLoadSvc = (BaseLoadSvc) Utils.getBean( "baseLoadSvc_POL" );
		}

		@SuppressWarnings( "unchecked" )
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		List<TableData<BaseVO>> genTableData = dataHolder.getData().get( SvcConstants.SAVE_CASH_CUST_DATA );

		PolicyDataVO polData = null;

		GeneralInfoVO generalInfoVO = null;
		/* Get the general info vo obtained from the T_MAS_CASH_CUSTOMER Table */
		if( !Utils.isEmpty( genTableData.get( 0 ) ) ){
			generalInfoVO = (GeneralInfoVO) genTableData.get( 0 ).getTableData();
		}

		if( !Utils.isEmpty( polTableData.get( 0 ) ) ){
			polData = (PolicyDataVO) polTableData.get( 0 ).getTableData();
			polData.setCommonVO( commonVO );
		}

		LinkedHashMap<String, List<TableData>> dataMapForSave = new LinkedHashMap<String, List<TableData>>();
		DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		//PolicyDataVO polDataVo = (PolicyDataVO) baseVo;

		//polDataVo.setCommonVO( commonVO );
		// For Policy data save
		TableData<PolicyDataVO> polTableDataForSave = new TableData<PolicyDataVO>();
		polTableDataForSave.setTableData( polData );
		List<TableData> polList = new ArrayList<TableData>();
		polList.add( polTableDataForSave );

		// For Cash customer save
		TableData<GeneralInfoVO> genInfoData = new TableData<GeneralInfoVO>();
		genInfoData.setTableData( generalInfoVO );

		List<TableData> genInfo = new ArrayList<TableData>();
		genInfo.add( genInfoData );

		List<TableData> nonStdTxtDataList = new ArrayList<TableData>();

		for( NonStandardClause nonClause : nonStandardClauses ){
			TableData<NonStandardClause> nonStdTxtData = new TableData<NonStandardClause>();
			nonClause.setPolicyId( commonVO.getPolicyId() );
			nonStdTxtData.setTableData( nonClause );
			nonStdTxtDataList.add( nonStdTxtData );
		}

		dataMapForSave.put( SvcConstants.T_TRN_POLICY, polList );
		dataMapForSave.put( SvcConstants.SAVE_CASH_CUST_DATA, genInfo );
		dataMapForSave.put( SvcConstants.T_TRN_NON_STD_TXT, nonStdTxtDataList );

		toBeSaved.setData( dataMapForSave );

		if( commonVO.getIsQuote() ){
			baseOperationDao = (IBaseSaveOperation) Utils.getBean( "commonGenDao" );
		}
		else{
			baseOperationDao = (IBaseSaveOperation) Utils.getBean( "commonGenDao_POL" );
		}

		baseOperationDao.executeSave( toBeSaved, commonVO );

	}

	private BaseVO handleNonStandardClausesSBS( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;

		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		clearThreadLevelContext();

		/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
		 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow() ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.FALSE );

		/*SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId(policyVO) );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
		RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );*/

		SectionVO section = getSectionVO( policyVO );

		/* Let us first generate the Endt_Id. This will initiate values that will be required for the Pojo Id because VSD forms
		 * part of the key in most tables. */
		DAOUtils.fetchEndtId( policyVO, getHibernateTemplate() );

		/* If the policy Id for this section's class code has not been created, we have to do it now. Both T_TRN_POLICY(_QUO) and
		 * T_MAS_CASH_CUSTOMER(_QUO) records have to be created. */

		createPolicyRecord( policyVO, section );

		/* Invoke the section-specific save activities. */
		BaseVO output = saveSection( policyVO );

		updateOrRollBackPolicyRec( policyVO );

		if( policyVO.getAppFlow() == Flow.AMEND_POL ){

			/* Clear all the endorsement texts inserted in current endorsement for given policy id and section id, for doing fresh insert with latest data */
			DAOUtils.deletePrevEndtText( section.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), CONDTIONS_SECTION_ID, LOC_ID );
			//DAOUtils.deletePrevEndtText( section.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), PAR_SECTION_ID, LOC_ID );
			
			updateEndtTextNonStandard( policyVO, section, "nonStdTextGenProc" );
			updateEndtTextNonStandard( policyVO, section, "nonStdTextDelGenProc" );
			updateEndtNonStdClauseChange( policyVO, section, "nonStdTextColGenProc" );

			/* DAOUtils.deletePrevEndtText deletes all the endorsement texts of of base section, 
			 * To regenerate deleted clauses text invoking below procedures */
			//updateEndtText( policyVO, section, "clauseTextGenProc" );
			//updateEndtText( policyVO, section, "clauseDelTextGenProc" );

		}

		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		clearThreadLevelContext();
		return output;
	}

	private void updateEndtNonStdClauseChange( PolicyVO policyVO, SectionVO section, String beanName ){
		List<NonStandardClause> nonStandardClauses = policyVO.getNonStandardClause();

		for( NonStandardClause clause : nonStandardClauses ){
			if( Utils.isEmpty( clause.getClauseType() ) && ( Utils.isEmpty( clause.getDescription() ) || clause.getDescription().trim().equalsIgnoreCase( "" ) ) ){
				continue;
			}

			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( beanName );
			try{
				Map results = sp.call( section.getPolicyId(), policyVO.getNewEndtId(), getClauseTypeCode( clause.getClauseType() ), SvcUtils.getUserId( policyVO ),
						CONDTIONS_SECTION_ID, LOC_ID );
				if( Utils.isEmpty( results ) ){
					LOGGER.info( "The result of the stored procedure is nul_4" );
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.updateEndtNonStdClauseChange.exception", e, "An exception occured while executing stored proc pro_endt_text_nonstd_col." );
			}
		}
	}

	private SectionVO getbasicSection( Integer basicSectionID, PolicyVO policyDetails ){
		List<SectionVO> sectionVOs = policyDetails.getRiskDetails();
		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( basicSectionID );
		int indexOfSection = sectionVOs.indexOf( finderSection );
		return indexOfSection >= 0 ? sectionVOs.get( sectionVOs.indexOf( finderSection ) ) : null;
	}

	private int getSectionId( PolicyVO policyVO ){
		if( isSectionPresent( SvcConstants.SECTION_ID_PAR, policyVO ) )
			return SvcConstants.SECTION_ID_PAR;
		else
			return SvcConstants.SECTION_ID_PL;
	}

	private int getClassCode( PolicyVO policyVO ){
		if( isSectionPresent( SvcConstants.SECTION_ID_PAR, policyVO ) )
			return SvcConstants.CLASS_ID_PAR;
		else
			return SvcConstants.CLASS_ID_PL;
	}

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/* Clears off contextMap values for the current thread */
	private void clearThreadLevelContext(){
		ThreadLevelContext.clearAll();
	}

	@Override
	protected int getSectionId(){

		return SvcConstants.SECTION_ID_PAR;
	}

	@Override
	protected int getClassCode(){
		return SvcConstants.CLASS_ID_PAR;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;

		//SectionVO basicSection = PolicyUtils.getSectionVO( policyVO, getSectionId(policyVO) );
		SectionVO basicSection = getSectionVO( policyVO );

		/*This method handles non standard clauses */
		handleNonStandardClauses( policyVO, basicSection );

		return null;
	}

	/**
	 * 
	 * @param policyVO
	 * @return
	 */
	private SectionVO getSectionVO( PolicyVO policyVO ){

		SectionVO basicSection = new SectionVO( RiskGroupingLevel.LOCATION );
		basicSection.setPolicyId( DAOUtils.getBaseSectionPolicyId( policyVO, getHibernateTemplate() ) );

		short classCode = DAOUtils.getClassCodeForPolicyId( getHibernateTemplate(), policyVO, basicSection );
		short sectionId = DAOUtils.getSectionIdForPolicyId( getHibernateTemplate(), classCode, basicSection.getPolicyId(), policyVO.getIsQuote() );

		basicSection.setSectionId( Integer.valueOf( sectionId ) );
		return basicSection;
	}

	private void handleNonStandardClauses( PolicyVO policyVO, SectionVO basicSection ){
		List<NonStandardClause> nonStandardClauses = policyVO.getNonStandardClause();

		for( NonStandardClause clause : nonStandardClauses ){
			if( Utils.isEmpty( clause.getClauseType() ) && ( Utils.isEmpty( clause.getDescription() ) || clause.getDescription().trim().equalsIgnoreCase( "" ) ) ){
				continue;
			}
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_NON_STD_TXT, policyVO, null, new BaseVO[]{ basicSection, policyVO, clause }, false, basicSection.getPolicyId(),
					getClauseTypeCode( clause.getClauseType() ) );
		}

	}

	/**
	 * This method maps NonStandardClauseVO to TTrnNonStandardText POJO
	 */
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;
		SectionVO basicSection = (SectionVO) depsVO[ 0 ];
		NonStandardClause clause = (NonStandardClause) depsVO[ 2 ];
		TTrnNonStdTextQuo tTrnNonStdText = getPojo( basicSection, policyVO, clause );
		mappedPOJO = tTrnNonStdText;

		return mappedPOJO;
	}

	/**
	 * This method prepares a POJO record
	 * @param basicSection
	 * @param policyVO
	 * @param clause
	 * @return
	 */
	private TTrnNonStdTextQuo getPojo( SectionVO basicSection, PolicyVO policyVO, NonStandardClause clause ){
		final String ONE_SPACE_STRING = " ";
		TTrnNonStdTextQuo tTrnNonStdText = new TTrnNonStdTextQuo();
		TTrnNonStdTextQuoId id = new TTrnNonStdTextQuoId();
		id.setNstPolicyId( basicSection.getPolicyId() );

		id.setNstEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		if( !Utils.isEmpty( clause.getClauseType() ) ){
			if( clause.getClauseType().equalsIgnoreCase( "C" ) ){
				id.setNstTypeCode( 1 );
			}
			else if( clause.getClauseType().equalsIgnoreCase( "W" ) ){
				id.setNstTypeCode( 2 );
			}
			else if( clause.getClauseType().equalsIgnoreCase( "E" ) ){
				id.setNstTypeCode( 3 );
			}

		}
		tTrnNonStdText.setId( id );
		if( Utils.isEmpty( clause.getDescription() ) ){
			clause.setDescription( ONE_SPACE_STRING );
		}
		tTrnNonStdText.setNstEText( clause.getDescription() );
		tTrnNonStdText.setNstPhrCode( Byte.valueOf( getHdrCodeForSection( getSectionId( policyVO ), Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SBS_POLICY_TYPE ) ) )
				.toString() ) );
		tTrnNonStdText.setNstStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		tTrnNonStdText.setNstValidityExpiryDate( SvcConstants.EXP_DATE );
		tTrnNonStdText.setNstValidityStartDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

		setAuditDetailsforcontent( tTrnNonStdText, policyVO );
		return tTrnNonStdText;
	}

	/**
	 * This method checks whether new record is to be created for POJO
	 * @param tableId
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return boolean
	 */
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		NonStandardClause nonStandardClause = (NonStandardClause) depsVO[ 2 ];
		java.util.List<NonStandardClause> nonStandardClausesSaved ;
		boolean flag = false;
		if( !Utils.isEmpty( nonStandardClause.getDescription() ) ){
			DataHolderVO<java.util.List<NonStandardClause>> holderVO = (DataHolderVO<List<NonStandardClause>>) getNonStdClauses( policyVO );
			if( !Utils.isEmpty( holderVO ) ){
				nonStandardClausesSaved = holderVO.getData();
				if( !Utils.isEmpty( nonStandardClausesSaved ) ){
					for( NonStandardClause clause : nonStandardClausesSaved ){
						if( nonStandardClause.getClauseType().equalsIgnoreCase( clause.getClauseType() ) ){
							flag = true;
							break;
						}
					}
				}
			}

		}

		return !flag;

	}

	/**
	 * This method checks whether  record is to be deleted for POJO
	 * @param tableId
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return boolean
	 */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		NonStandardClause nonStandardClause = (NonStandardClause) depsVO[ 2 ];
		java.util.List<NonStandardClause> nonStandardClausesSaved ;
		boolean flag = true;
		if( Utils.isEmpty( nonStandardClause.getDescription() ) ){
			DataHolderVO<java.util.List<NonStandardClause>> holderVO = (DataHolderVO<List<NonStandardClause>>) getNonStdClauses( policyVO );
			if( !Utils.isEmpty( holderVO ) ){
				nonStandardClausesSaved = holderVO.getData();
				if( !Utils.isEmpty( nonStandardClausesSaved ) ){
					for( NonStandardClause clause : nonStandardClausesSaved ){
						if( nonStandardClause.getClauseType().equalsIgnoreCase( clause.getClauseType() ) ){
							flag = false;
							break;
						}
					}
				}
			}

		}

		return !flag;

	}

	/**
	 * This method updates key values 
	 * @param tableId
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return boolean
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){

		if( !Utils.isEmpty( mappedRecord ) ){
			if( mappedRecord instanceof TTrnNonStdTextQuo ){

				TTrnNonStdTextQuo nonStdText = (TTrnNonStdTextQuo) mappedRecord;
				NonStandardClause nonStandardClause = (NonStandardClause) depsVO[ 2 ];

				if( !Utils.isEmpty( nonStdText.getId().getNstTypeCode() ) ){
					if( nonStdText.getId().getNstTypeCode() == 1 ){
						nonStandardClause.setClauseType( "C" );
					}
					else if( nonStdText.getId().getNstTypeCode() == 2 ){
						nonStandardClause.setClauseType( "W" );
					}
					else if( nonStdText.getId().getNstTypeCode() == 3 ){
						nonStandardClause.setClauseType( "E" );
					}

				}

				if( saveCase == SaveCase.DELETE_PENDING_REC || saveCase == SaveCase.DELETE  ){

					String CLAUSE_DELETED = null;
					nonStandardClause.setClauseType( CLAUSE_DELETED );
				}
			}
		}
	}

	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

	}

	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		TTrnNonStdTextQuo nonStdText = new TTrnNonStdTextQuo();
		TTrnNonStdTextQuoId pId = nonStdText.getId();
		id = pId;
		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){/*
																													TTrnNonStdText nonStdText = (TTrnNonStdText) existingId;
																													TTrnNonStdTextId pId = new TTrnNonStdTextId();
																													pId.setNstTypeCode(nonStdText)
																													id = cId;
																													*/
		return null;
	}

	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates the T_TRN_POLICY(_QUO) and T_MAS_CASH_CUSTOMER(_QUO) records for this section.
	 * 
	 * @param policyVO
	 * @param section The section for which the T_TRN_POLICY(_QUO) and T_MAS_CASH_CUSTOMER(_QUO) records have to be created
	 */
	private void createPolicyRecord( PolicyVO policyVO, SectionVO section ){
		Long policyId = DAOUtils.createPolicyRecord( policyVO, getClassCode( policyVO ), section.getSectionId() );
		section.setPolicyId( policyId );
	}

	private void updateOrRollBackPolicyRec( PolicyVO policyVO ){

		//SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId(policyVO) );
		SectionVO section = getSectionVO( policyVO );

		if( (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_DATA_HAS_CHANGED ) ){
			if( ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED ) ) ) )
					&& ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_SOFT_STOP ) ) ) )
					&& ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_EXPIRED ) ) ) )
					&& ( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.CONV_TO_POL ) ) ) ) ){
				/* If the data has changed in any sections, set the status to Pending in the cases other than Referred, Expired and Converted to Policy. */
				policyVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			}
			updatePolicy( policyVO, section );
			getHibernateTemplate().flush();
			/* Update the previous Endt_Id records' Validity_Expiry_Date to current date. */
			updateValidityExpiryDates( policyVO, section.getSectionId() );
			getHibernateTemplate().flush();

			// Call to the stored procedure to update the non base section building table if base section building table changed  
			//LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
			// Only if any location has got changes 
			/*if(!Utils.isEmpty( locationDetails )){
				
				 * Checks if the location data has changed only then call the proc to update the location information to 
				 * other depended tables 
				 
				if(SvcUtils.hasLocDataChanged()){
					PASStoredProcedure sp =null;		
					if(policyVO.getAppFlow()==Flow.AMEND_POL){
						sp = (PASStoredProcedure) Utils.getBean("cascadeBaseLocChangesPol");
					} else {
						sp = (PASStoredProcedure) Utils.getBean("cascadeBaseLocChangesQuo");
					}			
					try
					{
						Map results = sp.call( policyVO.getPolLinkingId(), getSectionId(), locationDetails.getRiskGroupId(), (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ),
								policyVO.getPremiumVO().getPremiumAmt(), policyVO.getAuthInfoVO().getCreatedBy(), section.getCommission() );
					} catch (DataAccessException e){
						throw new BusinessException( "pas.dao.cmn.unknownError", e, "An exception occured while executing stored proc_10" );
					}
				}
			}*/
		}
		else{
			/*
			 * If no data is changed, then roll back the record inserted into t_trn_policy(quo).
			 * This is required because we are inserting data into t_trn_policy at the beginning of the transaction to avoid foreign key
			 * Violation with content tables 
			 */
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}

	private void updatePolicy( PolicyVO policyVO, SectionVO section ){
		/*
		 * If the endtId or EndNo is null that means there is no change in the any of the building or 
		 * content table, here there is no need to update the ttrn policy table. The reason for this assumption is that a new 
		 * Endt_Id is "attempted" to be generated only if we figure out that there is a change to a risk detail.
		 */
		if( Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ) || Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) ) ){
			return;
		}

		/* Get the current Endt_Id-state record, if key is available. That is, the record with the Endt_Id got from the database function. */
		TTrnPolicyQuo existingRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), false,
				(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), policyVO.getPolLinkingId(), (short) getClassCode( policyVO ) );

		/* There is no record for the current (new) Endt_Id. This means that we will have to create a new record from the previous
		 * Endt_Id's record. */
		Long prevEndtId = DAOUtils.getPrevEndtId( getHibernateTemplate(), policyVO.getIsQuote(), section.getPolicyId(),
				(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );

		TTrnPolicyQuo oldRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), false, prevEndtId,
				policyVO.getPolLinkingId(), (short) getClassCode( policyVO ) );

		if( !Utils.isEmpty( oldRecord ) ){

			if( Utils.isEmpty( existingRecord ) ){
				existingRecord = oldRecord;
			}

			/*In case of endorsement, update the previous record endorsement expiry date to endorsement effective 
			 date */
			if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
				oldRecord.setPolEndtExpiryDate( policyVO.getEndEffectiveDate() );
				/* processing branch of old policy record should not be updated to new processing location.
				 * if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo()) 
						&& !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc())){
					oldRecord.setPolProcLocCode( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
				}*/

				update( oldRecord );
			}

		}

		TTrnPolicyQuo newRecord = CopyUtils.copySerializableObject( existingRecord );

		newRecord.getId().setPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		newRecord.setPolEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) );
		newRecord.setPolValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		newRecord.setPolValidityExpiryDate( SvcConstants.EXP_DATE );
		newRecord.setPolPreparedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		/* Setting modified date so that the same can be used for transaction date & time during transaction search. */
		newRecord.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );

		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc() ) ){
			newRecord.setPolProcLocCode( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc() );
		}

		/*
		 * In case of endorsement, the endt expire date needs to be populated.
		 * First check if the new record is not for end no 0 i,e endID is not 0
		 * then update the endorsement expiry date to policy exp date
		 */
		if( !String.valueOf( newRecord.getId().getPolEndtId() ).equalsIgnoreCase( SvcConstants.FIRST_ENDT ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
			newRecord.setPolEndtExpiryDate( policyVO.getPolExpiryDate() );
		}

		/*
		 * update the endt effective date, as the proc update cashpolicy will just copy the previous record and 
		 * the PolEndtEffectiveDate is not set.
		 */
		if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
			newRecord.setPolEndtEffectiveDate( policyVO.getEndEffectiveDate() );
		}
		else{
			newRecord.setPolEndtEffectiveDate( null );
		}

		/*
		 * Below line to set policy status as pending is commented, because it is resetting the status of the policy to pending, irrespective of the current status.
		 * Below line purpose to set the policy status to pending while creating the policy is achieved in procedure used to create policy.
		 */
		//	newRecord.setPolStatus( SvcConstants.POL_STATUS_PENDING.byteValue() ); /* TODO Need to set this to 4 in case there is no section for this policyId. */
		if( !Utils.isEmpty( section.getCommission() ) ){
			newRecord.setPolCommisionId( BigDecimal.valueOf( section.getCommission() ) );
		}
		getHibernateTemplate().evict( existingRecord );
		saveOrUpdate( newRecord );

		/*
		 * The method updates the special covers in t_trn_premium for the basic section
		 */
		//	updatePremiumSpecialCover( policyVO,  section , newRecord);
	}

	private void updateValidityExpiryDates( PolicyVO policyVO, int sectionId ){
		/* Call the Stored Procedure that updates the previous Endt_Id records' Validity_Expiry_Date to current date. */
		/*if( policyVO.getAppFlow() == Flow.AMEND_POL ){*/
		DAOUtils.updateVED( policyVO, getClassCode( policyVO ), sectionId );
		/*}*/
	}

	private void setAuditDetailsforcontent( TTrnNonStdTextQuo tTrnNonStdText, PolicyVO policyVO ){
		Integer userId = SvcUtils.getUserId( policyVO );
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		tTrnNonStdText.setNstPreparedBy( userId );
		tTrnNonStdText.setNstPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

		tTrnNonStdText.setNstModifiedBy( userId );
		tTrnNonStdText.setNstModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

	}

	private int getClauseTypeCode( String clauseType ){

		int typeCode = 0;
		if( !Utils.isEmpty( clauseType ) ){
			if( clauseType.equalsIgnoreCase( "C" ) ){
				typeCode = 1;
			}
			else if( clauseType.equalsIgnoreCase( "W" ) ){
				typeCode = 2;
			}
			else if( clauseType.equalsIgnoreCase( "E" ) ){
				typeCode = 3;
			}

		}
		return typeCode;
	}
	
	public BaseVO insertReferralForConditions( BaseVO baseVO ){

		PolicyVO policyVO = (PolicyVO) baseVO;
		/*Start of ticket 137704 */
		int userId=0;
		String role=null;
		if(baseVO instanceof PolicyVO) {
			PolicyVO policyVO1 = (PolicyVO) baseVO;
			UserProfile userProfileVO = (UserProfile) policyVO1.getLoggedInUser();
			if (!Utils.isEmpty(userProfileVO)) {
			userId= userProfileVO.getRsaUser().getUserId();       
			role=DAOUtils.getUserRole(policyVO1);
			}
		}
		/*End of ticket 137704 */
		
		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf(  Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_GENERAL )  ) , 
		Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GENERAL ) ) , getHibernateTemplate()  );
		TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
		ReferralVO referralVO = DAOUtils.getReferralVOForSave( policyVO );	
		
		/*Start of ticket 137704 */
		if (!Utils.isEmpty(referralVO)) {
			referralVO.setTprUserId(userId);
			referralVO.setTprUserRole(role);
		}
		/*End of ticket 137704 */
		
		insertTempPasReferalDao.insertReferal( referralVO );
		return policyVO;
	}

}
