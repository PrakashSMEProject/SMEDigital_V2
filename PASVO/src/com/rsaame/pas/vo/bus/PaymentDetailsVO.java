/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * VO to hold the payment details ( created for the online payments )
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class PaymentDetailsVO extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 3641453865059531762L;
	
	private double authorizedPremiumAmt;
	private String authizationCode;
	private Date authirizationTime;
	private String transactionId;
	private String decision;
	private Integer responseCode;
	private String message;
	private double requestedPremiumAmt;
	private String eMailId;
	private String firstName;
	private String surname;
	private String cardExipiryDate;
	private String cardNumber;
	private String paymentMode;
	private String transactionRefNo;
	private String transactionUuid;
	private String cardIssuer;
	private String cardType;
	private Long quoteNo;
	private Long policyId;
	private String currency;
	private String billingAddress;
	private String custName;
	private LOB lob;
	private String partnerName;
	private String partnerId;
	private String partnerCallCenterNo;
	
	//131378
	private Long endtID;
	private Integer tariffCode;
	private Short documentCode;
	private Date  transDate;
	private String requestdeatils;
	private Integer brokerCode;
	private String mobileNo;
	

	/**
	 * @return the authorizedPremiumAmt
	 */
	public double getAuthorizedPremiumAmt(){
		return authorizedPremiumAmt;
	}

	/**
	 * @param authorizedPremiumAmt the authorizedPremiumAmt to set
	 */
	public void setAuthorizedPremiumAmt( double authorizedPremiumAmt ){
		this.authorizedPremiumAmt = authorizedPremiumAmt;
	}

	/**
	 * @return the authizationCode
	 */
	public String getAuthizationCode(){
		return authizationCode;
	}

	/**
	 * @param authizationCode the authizationCode to set
	 */
	public void setAuthizationCode( String authizationCode ){
		this.authizationCode = authizationCode;
	}

	/**
	 * @return the authirizationTime
	 */
	public Date getAuthirizationTime(){
		return authirizationTime;
	}

	/**
	 * @param authirizationTime the authirizationTime to set
	 */
	public void setAuthirizationTime( Date authirizationTime ){
		this.authirizationTime = authirizationTime;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId(){
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId( String transactionId ){
		this.transactionId = transactionId;
	}

	/**
	 * @return the decision
	 */
	public String getDecision(){
		return decision;
	}

	/**
	 * @param decision the decision to set
	 */
	public void setDecision( String decision ){
		this.decision = decision;
	}

	/**
	 * @return the responseCode
	 */
	public Integer getResponseCode(){
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode( Integer responseCode ){
		this.responseCode = responseCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage( String message ){
		this.message = message;
	}

	/**
	 * @return the requestedPremiumAmt
	 */
	public double getRequestedPremiumAmt(){
		return requestedPremiumAmt;
	}

	/**
	 * @param requestedPremiumAmt the requestedPremiumAmt to set
	 */
	public void setRequestedPremiumAmt( double requestedPremiumAmt ){
		this.requestedPremiumAmt = requestedPremiumAmt;
	}

	/**
	 * @return the eMailId
	 */
	public String geteMailId(){
		return eMailId;
	}

	/**
	 * @param eMailId the eMailId to set
	 */
	public void seteMailId( String eMailId ){
		this.eMailId = eMailId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName(){
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName( String firstName ){
		this.firstName = firstName;
		this.custName = firstName + " " + this.surname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname(){
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname( String surname ){
		this.surname = surname;
		this.custName = this.firstName + " " + surname;
	}

	/**
	 * @return the cardExipiryDate
	 */
	public String getCardExipiryDate(){
		return cardExipiryDate;
	}

	/**
	 * @param cardExipiryDate the cardExipiryDate to set
	 */
	public void setCardExipiryDate( String cardExipiryDate ){
		this.cardExipiryDate = cardExipiryDate;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber(){
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber( String cardNumber ){
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode(){
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode( String paymentMode ){
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the transactionRefNo
	 */
	public String getTransactionRefNo(){
		return transactionRefNo;
	}

	/**
	 * @param transactionRefNo the transactionRefNo to set
	 */
	public void setTransactionRefNo( String transactionRefNo ){
		this.transactionRefNo = transactionRefNo;
	}

	/**
	 * @return the transactionUuid
	 */
	public String getTransactionUuid(){
		return transactionUuid;
	}

	/**
	 * @param transactionUuid the transactionUuid to set
	 */
	public void setTransactionUuid( String transactionUuid ){
		this.transactionUuid = transactionUuid;
	}

	/**
	 * @return the cardIssuer
	 */
	public String getCardIssuer(){
		return cardIssuer;
	}

	/**
	 * @param cardIssuer the cardIssuer to set
	 */
	public void setCardIssuer( String cardIssuer ){
		this.cardIssuer = cardIssuer;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType(){
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType( String cardType ){
		this.cardType = cardType;
	}

	public Long getQuoteNo(){
		return quoteNo;
	}

	public void setQuoteNo( Long quoteNo ){
		this.quoteNo = quoteNo;
	}

	public Long getPolicyId(){
		return policyId;
	}

	public void setPolicyId( Long policyId ){
		this.policyId = policyId;
	}

	public String getCurrency(){
		return currency;
	}

	public void setCurrency( String currency ){
		this.currency = currency;
	}

	public String getBillingAddress(){
		return billingAddress;
	}

	public void setBillingAddress( String billingAddress ){
		this.billingAddress = billingAddress;
	}

	public String getCustName(){
		return custName;
	}

	public void setCustName( String custName ){
		this.custName = custName;
		this.firstName = custName.split( " " )[0];
		if( custName.split( " " ).length > 1 ){
			this.surname = custName.split( " " )[1];
		}
	}
	
	@Override
	public Object getFieldValue( String fieldName ){

		return null;
	}

	public LOB getLob(){
		return lob;
	}

	public void setLob( LOB lob ){
		this.lob = lob;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerCallCenterNo() {
		return partnerCallCenterNo;
	}

	public void setPartnerCallCenterNo(String partnerCallCenterNo) {
		this.partnerCallCenterNo = partnerCallCenterNo;
	}

	public Long getEndtID() {
		return endtID;
	}

	public void setEndtID(Long endtID) {
		this.endtID = endtID;
	}

	public Integer getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(Integer tariffCode) {
		this.tariffCode = tariffCode;
	}

	public Short getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(Short documentCode) {
		this.documentCode = documentCode;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getRequestdeatils() {
		return requestdeatils;
	}

	public void setRequestdeatils(String requestdeatils) {
		this.requestdeatils = requestdeatils;
	}

	public Integer getBrokerCode() {
		return brokerCode;
	}

	public void setBrokerCode(Integer brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	
}
