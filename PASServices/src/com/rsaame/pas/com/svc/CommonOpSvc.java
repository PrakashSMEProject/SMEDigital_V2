/**
 * 
 */
package com.rsaame.pas.com.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.cmn.ICommonOpDAO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014644
 *
 */
public class CommonOpSvc extends BaseService {

	private Logger logger = Logger.getLogger( CommonOpSvc.class );
	
	ICommonOpDAO commonOpDAO;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "getCommisionSvc".equals( methodName ) ){
			returnValue = getCommisionSvc( (BaseVO) args[ 0 ] );
		}
		if("setPrepackedFlagSvc".equals(methodName)){
			returnValue = setPrepackedFlagSvc((BaseVO)args[0]);
		}
		if("getPolLinkID".equals(methodName)){
			returnValue = getPolLinkID((BaseVO)args[0]);
		}
		if("generateQuotationNo".equals(methodName)){
			returnValue = generateQuotationNo((BaseVO)args[0]);
		}
		if("generateInsuredId".equals(methodName)){
			returnValue = generateInsuredId((BaseVO)args[0]);
		}
		
		if("getUserDetails".equals(methodName)){
			returnValue = getUserDetails((Integer)args[0]);
		}
		
		if("editQuoteUpdateStatusToPending".equals( methodName )){
			returnValue = editQuoteUpdateStatusToPending((BaseVO) args[0]);
		}
		
		if("handleReferralMessages".equalsIgnoreCase(methodName)){
			returnValue = handleReferralMessages((BaseVO) args[0]);
		}
		if("isEndorsementRecordExist".equalsIgnoreCase(methodName)){
			returnValue = isEndorsementRecordExist((BaseVO) args[0]);
		}
		if("getNoticeBoardItems".equalsIgnoreCase(methodName)){
			returnValue = getNoticeBoardItems((BaseVO) args[0]);
		}
		if("updateTradeLicNo".equalsIgnoreCase(methodName)){
			returnValue = upadteTradeLicNo((BaseVO) args[0]);
		}
		if("populateCommonDetails".equalsIgnoreCase(methodName)){
			returnValue = populateCommonDetails((BaseVO) args[0]);
		}// For Home/Travel to fetch the latest policy record
		if( "fetchPolicyRecord".equalsIgnoreCase( methodName ) ){
			returnValue = fetchPolicyRecord( (BaseVO) args[ 0 ] );
		}
		if( "activateQuote".equalsIgnoreCase( methodName ) ){
			activateQuote( (BaseVO) args[ 0 ] );
		}
		if( "getNextEndorsementId".equalsIgnoreCase( methodName ) ){
			returnValue = getNextEndorsementId( (BaseVO) args[ 0 ] );
		}
		if( "callTariffChangeProcedure".equalsIgnoreCase( methodName ) ){
			callTariffChangeProcedure( (BaseVO) args[ 0 ] );
		}
		if( "checkForMortgageeName".equalsIgnoreCase( methodName ) ){
			returnValue = checkForMortgageeName( (BaseVO) args[ 0 ] );
		}
		if( "getClauseForCurrentEndtId".equalsIgnoreCase( methodName ) ){
			returnValue = getClauseForCurrentEndtId( (BaseVO) args[ 0 ] ,(BaseVO) args[ 1 ]);
		}
		if( "validatePromoCode".equalsIgnoreCase( methodName ) ){
			returnValue = validatePromoCode( (BaseVO) args[ 0 ] );
		}
		if( "getPrevEndtIdForPendingPolicy".equalsIgnoreCase( methodName ) ){
			returnValue = getPrevEndtIdForPendingPolicy( (BaseVO) args[ 0 ]);
		}
		if("getPolicyIdForPolicy".equalsIgnoreCase(methodName)){
			returnValue = getPolicyIdForPolicy((BaseVO) args[0]);
		}
		if("getPolicyTypeCurrencyScaleMap".equalsIgnoreCase(methodName)){
			returnValue = getPolicyTypeCurrencyScaleMap();
		}
		if("sendReferralMail".equalsIgnoreCase( methodName )){
			sendReferralMail((BaseVO) args[0]);
		}
		if("getLegacyPolicies".equalsIgnoreCase( methodName )){
			returnValue = getLegacyPolicies((BaseVO) args[0]);
		}
		if("getRenQuoteForPolicy".equalsIgnoreCase( methodName )){
			returnValue = getRenQuoteForPolicy((BaseVO) args[0]);
		}
        if("forgotPasswordSvc".equalsIgnoreCase( methodName )){
			returnValue = forgotPassword((BaseVO) args[0]);
		}
		if("updatePassword".equalsIgnoreCase( methodName )){
			returnValue = updatePassword((BaseVO) args[0]);
		}
		if("getUserRoles".equals(methodName)){
			returnValue = getUserRoles((BaseVO) args[0]);
		}
		if( "getUpdatedPoBox".equalsIgnoreCase( methodName ) ){
			returnValue = getUpdatedPoBox( (BaseVO) args[ 0 ] );
		}
		
		/*New Screen VAT */
		if( "updateVatRegNo".equalsIgnoreCase( methodName ) ){
			returnValue = updateVatRegNo( (BaseVO) args[ 0 ] );
		}
		
		/*WebServices Wunderman */
		if("getQuoteforPolicy".equalsIgnoreCase(methodName)){
			returnValue = getQuoteForPolicy( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}
	
	private BaseVO updatePassword( BaseVO baseVO ){
		return commonOpDAO.updatePassword( baseVO );
	}
	private BaseVO forgotPassword(BaseVO baseVO) {
		return commonOpDAO.getforgotPassword(baseVO);
	}
	private BaseVO getRenQuoteForPolicy( BaseVO baseVO ){
		return commonOpDAO.getRenQuoteForPolicy(baseVO);
	}
	/*WebServices Wunderman */
	private BaseVO getQuoteForPolicy( BaseVO baseVO ){
		return commonOpDAO.getQuoteForPolicy(baseVO);
	}
	
	
	private BaseVO getLegacyPolicies( BaseVO baseVO ){
		return commonOpDAO.getLegacyPolicies(baseVO);
		
	}

	private BaseVO getPolicyTypeCurrencyScaleMap(){
		return commonOpDAO.getPolicyTypeCurrencyScaleMap();
	}
	
	private BaseVO getPolicyIdForPolicy( BaseVO baseVO ){
		return commonOpDAO.getPolicyIdForPolicy( baseVO );
	}

	private BaseVO validatePromoCode( BaseVO baseVO ){
		return commonOpDAO.validatePromoCode( baseVO );
	}

	private BaseVO getClauseForCurrentEndtId(BaseVO baseVO,BaseVO baseVO2) {
		
		return commonOpDAO.getClauseForCurrentEndtId( baseVO,baseVO2 );
	}
	
	private BaseVO getPrevEndtIdForPendingPolicy(BaseVO baseVO) {
		
		return commonOpDAO.getPrevEndtIdForPendingPolicy( baseVO );
	}

	private BaseVO checkForMortgageeName(BaseVO baseVO) {
		
		return commonOpDAO.isMortgageeExists( baseVO );
	}

	private void callTariffChangeProcedure(BaseVO baseVO) {
		commonOpDAO.callTariffChangeProcedure( baseVO );
	}

	private BaseVO getNextEndorsementId( BaseVO baseVO ){
		return commonOpDAO.getNextEndorsementId( baseVO );
	}
	
	private BaseVO fetchPolicyRecord( BaseVO baseVO ){
		return commonOpDAO.fetchPolicyRecord( baseVO );
	}
	
	private BaseVO populateCommonDetails( BaseVO baseVO ){
		logger.info("Entered CommonOpSvc.populateCommonDetails method.");
		return commonOpDAO.populateCommonDetails( baseVO );
	}

	private BaseVO upadteTradeLicNo(BaseVO baseVO) {
		return commonOpDAO.upadteTradeLicNo(baseVO);
	}

	private BaseVO handleReferralMessages(BaseVO baseVO) {
		return commonOpDAO.handleReferralMessages(baseVO);
	}

	
	private BaseVO getPolLinkID(BaseVO baseVO) {
		return commonOpDAO.getPolLinkID( baseVO ); 
	}

	public BaseVO setPrepackedFlagSvc(BaseVO baseVO) {
		
		return commonOpDAO.setPrepackedFlag(baseVO); 
	}

	private BaseVO getCommisionSvc(BaseVO baseVO) {
		
		return commonOpDAO.getCommision(baseVO);
		
	}

	private BaseVO generateQuotationNo(BaseVO baseVO) {
		return commonOpDAO.generateQuotationNo( baseVO ); 
	}
	
	private BaseVO generateInsuredId(BaseVO baseVO) {
		return commonOpDAO.generateInsuredId( baseVO ); 
	}
	
	private BaseVO getUserDetails(Integer userId ) {
		return commonOpDAO.getUserDetails( userId  ); 
	}
	
	private BaseVO isEndorsementRecordExist(BaseVO baseVO){
		return commonOpDAO.isEndorsementRecordExist(baseVO);
	}
	
	private BaseVO getNoticeBoardItems (BaseVO baseVO){
		return commonOpDAO.getNoticeBoardItems(baseVO);
	}

	public void setCommonOpDAO(ICommonOpDAO commonOpDAO) {
		this.commonOpDAO = commonOpDAO;
	}
	
	public BaseVO editQuoteUpdateStatusToPending(BaseVO baseVO){
		return commonOpDAO.editQuoteUpdateStatusToPending( baseVO );
	}
	public void activateQuote(BaseVO baseVO){
		commonOpDAO.activateQuote( baseVO );
	}
	public BaseVO getUserRoles(BaseVO baseVO){
		return commonOpDAO.getUserRoles( baseVO );
	}
	
	public BaseVO updateVatRegNo(BaseVO baseVO){
		return commonOpDAO.updateVATRegNo( baseVO );	
	}
	
	/**
	 * Method to send the referral mail once the referral is assigned to user
	 * 
	 * @param baseVO
	 */
	private void sendReferralMail( BaseVO baseVO ){
		try{
			PASMailerService mailer = (PASMailerService) Utils.getBean( "emailService" );
			DataHolderVO<Object[]> inputObject = (DataHolderVO<Object[]>)baseVO;
			
			PolicyDataVO policyDataVO = (PolicyDataVO)inputObject.getData()[0];
			String flow = (String)inputObject.getData()[1];
			
			MailVO mailVO = SvcUtils.populateMailVO(policyDataVO,flow);
			mailer.invokeMethod( "sendMail", mailVO );
		}catch(Exception e){
			logger.error( "Error while sending referral mail:"+e );
			logger.debug( "Error while sending referral mail:"+e );
		}
	}

/*	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
		PolicyVO policyVO = new PolicyVO();
		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setSchemeCode(1001);
		policyVO.setScheme(schemeVO);
		GetCommisionSvc commisionSvc = (GetCommisionSvc) applicationContext.getBean("geComSvc");
		
		CommissionVOList commissionVOList = (CommissionVOList) commisionSvc.invokeMethod("getCommisionSvc", policyVO);
		
	}*/
	private BaseVO getUpdatedPoBox(BaseVO baseVO) {
		return commonOpDAO.getUpdatedPoBox( baseVO );
	}
}
