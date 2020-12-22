package com.rsaame.pas.promotionalcode.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.svc.TMasPromoDiscCoverVO;
import com.rsaame.pas.vo.svc.TMasPromotionalCodeVO;

/**
 * This request handler will be invoked while saving the data from
 * Promotional Code configuration screen
 * 
 * @author M1020859
 *
 */
public class PromotionalCodeConfigSaveRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( PromotionalCodeConfigSaveRH.class );

	@SuppressWarnings( "unchecked" )
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.debug( "PromoCodeConfigSaveRH ---------> Entering into the Save RH" );
		Response response = new Response();
		String identifier = null;
		identifier = request.getParameter( "opType" );
		//String action = request.getParameter( "action" );
		BaseVO baseVO = null;
		//	String count= request.getParameter( "schemes_cnt" );
		
		HttpSession session = request.getSession(false);
		logger.debug( "PromoCodeConfigSaveRH ---------> Going to call RequestToTMasPromotionalCodeVOHolder" );
		
		//opType on clicking of 'add' button on popUp for 'FREE COVER'
		if( "SAVE_PROMOTIONAL_CODE_SCHEME_COVER".equals( identifier ) ){
			String prCode = request.getParameter( "promoCode" );

		
			Set<TMasPromoDiscCoverVO> vo = null;
			
			vo = (Set<TMasPromoDiscCoverVO>)session.getAttribute( com.Constant.CONST_FREECOVERS );
			
			if(Utils.isEmpty( vo )){
				vo = new HashSet<TMasPromoDiscCoverVO>();
			}
			
			TMasPromoDiscCoverVO tMasPromoDiscCoverVO = null;
			List<String> noOfSchemeItems = HTTPUtils.getMatchingMultiReqParamKeys( request, "promo_dropdwn_scheme" );
			List<String> coverItems = HTTPUtils.getMatchingMultiReqParamKeys( request, "promo_dropdwn_cover" );
			int index = 0;
			for( String i : noOfSchemeItems ){
				tMasPromoDiscCoverVO = new TMasPromoDiscCoverVO();

				com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "",
						"" );
				tMasPromoDiscCoverVO.setPdcProCode(prCode);
				tMasPromoDiscCoverVO.setPdcSchemeCode( converter.getAFromB( request.getParameter(noOfSchemeItems.get( index )) ) );
				tMasPromoDiscCoverVO.setPdcCovCode( converter.getAFromB( request.getParameter( coverItems.get( index ) ) ) );				
				tMasPromoDiscCoverVO.setPdcPreparedDate(new Date());
				vo.add( tMasPromoDiscCoverVO );
				index++;
			}
			
			session.setAttribute( com.Constant.CONST_FREECOVERS, vo );
			
			return null;
		}
		
		
		List<String> noOfItems = null;
		TMasPromotionalCodeVO tMasPromotionalCodeVO = BeanMapper.map( request, com.rsaame.pas.vo.svc.TMasPromotionalCodeVO.class );
		TMasPromoDiscCoverVO coverVO = null;
		
		tMasPromotionalCodeVO.setProPreparedDate( new Date() ); // Setting current date
		
		if(tMasPromotionalCodeVO.getProPtCode().intValue() == AppConstants.HOME_POLICY_TYPE) {
		tMasPromotionalCodeVO.setProClassCode(new BigDecimal(AppConstants.HOME_CLASS_CODE )); 
		}else if (tMasPromotionalCodeVO.getProPtCode().intValue() == AppConstants.TRAVEL_SHORT_TERM_POLICY_TYPE){
			tMasPromotionalCodeVO.setProClassCode( new BigDecimal(AppConstants.TRAVEL_CLASS_CODE ) ); 
		}else if (tMasPromotionalCodeVO.getProPtCode().intValue() == AppConstants.SBS_POLICY_TYPE){
			tMasPromotionalCodeVO.setProClassCode( new BigDecimal(AppConstants.SBS_CLASS_CODE) ); 
		}
		
		if( tMasPromotionalCodeVO.getProType().equalsIgnoreCase( "Discount" ) ){

			noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( request, "schemesList" );
			
			if (!Utils.isEmpty(noOfItems)) {
				
				//saving promotional code to T_MAS_PROMOTIONAL_CODE
				try{
					baseVO = TaskExecutor.executeTasks( identifier, tMasPromotionalCodeVO );
					
				}/*catch(Throwable DataIntegrityViolationException){	*/			/* replaced throwable catch with exception catch - sonar violation fix */
				catch(Exception DataIntegrityViolationException){
					
						throw new BusinessException( "err.promo.duplicate", null, "Error while saving the promotional code" );
				}
				
				for (String i : noOfItems) {
					com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory
							.getInstance(
									com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class,
									"", "");
					coverVO = new TMasPromoDiscCoverVO();
					coverVO.setPdcProCode(tMasPromotionalCodeVO.getProCode());
					coverVO.setPdcSchemeCode(converter.getTypeOfA().cast(
							converter.getAFromB(request.getParameter(i))));
					coverVO.setPdcPreparedDate(new Date());
					baseVO = TaskExecutor.executeTasks(
							"SAVE_PROMOTIONAL_DISCOUNT_PAGE", coverVO);
				}
			}else{
				throw new BusinessException( "err.discount.empty", null, "No Free Cover has been selected" );
			}
			
		}


		
		
		
		//saving promotional code details to T_MAS_PROMO_DISC_COVER
		if(tMasPromotionalCodeVO.getProType().equalsIgnoreCase( "Free Cover" )){
			Set<TMasPromoDiscCoverVO> vo = null;
			
			vo = (Set<TMasPromoDiscCoverVO>) request.getSession( false ).getAttribute( com.Constant.CONST_FREECOVERS );
			
			if(!Utils.isEmpty( vo )){
				
				//saving promotional code to T_MAS_PROMOTIONAL_CODE
				try{
					baseVO = TaskExecutor.executeTasks( identifier, tMasPromotionalCodeVO );
					
				}/*catch(Throwable DataIntegrityViolationException){	*/			/* replaced throwable catch with exception catch - sonar violation fix */
				catch(Exception DataIntegrityViolationException){
					
						throw new BusinessException( "err.promo.duplicate", null, "Error while saving the promotional code" );
				}
				for(TMasPromoDiscCoverVO tMasPromoDiscCoverVO : vo){
					baseVO = TaskExecutor.executeTasks( "SAVE_PROMOTIONAL_FREECOVER_PAGE", tMasPromoDiscCoverVO );
				}
			}else{
				throw new BusinessException( "err.free.cover.empty", null, "No Free Cover has been selected" );
			}
			
			session.removeAttribute( com.Constant.CONST_FREECOVERS );
			
		}
		
		
		if( !Utils.isEmpty( baseVO ) ){
			
			AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
			response.setSuccess( true );
			response.setData( baseVO );
			
		}
		
		
		return response;
	}

}
