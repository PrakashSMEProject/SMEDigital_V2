/**
 * 
 */
package com.rsaame.pas.vo.app;

public enum ReportTemplateSet{
	
	 _SBS("ProposalForm","PolicySchedule","GrossDebitNote","GrossCreditNote","DebitNote","FreeZoneCertificate","PrintReceipt","EndorsementSchedule","CreditNote","RenewalNotice","ShowBankLetter"),
	 
	 _FGB_SBS("ProposalForm_FGB","PolicySchedule_FGB","GrossDebitNote_FGB","GrossCreditNote_FGB","DebitNote_FGB","FreeZoneCertificate_FGB","PrintReceipt_FGB","EndorsementSchedule_FGB","CreditNote_FGB","RenewalNotice_FGB","ShowBankLetter_FGB"),
	
	 _HOME("ProposalForm_HOME.rptdesign","PrintPolicyReports_HOME_TRAVEL.rptdesign","GrossDebitNote_HOME_TRAVEL","GrossCreditNote_HOME_TRAVEL","DebitNote_HOME_TRAVEL","FreeZoneCertificate_HOME_TRAVEL","PrintReceipt_HOME_TRAVEL","EndorsementSchedule_HOME_TRAVEL","CreditNote_HOME_TRAVEL","RenewalNotice_HOME","ShowBankLetter_HOME_TRAVEL"),
	 
	 _TRAVEL("ProposalForm_TRAVEL.rptdesign","PrintPolicyReports_HOME_TRAVEL.rptdesign","GrossDebitNote_HOME_TRAVEL","GrossCreditNote_HOME_TRAVEL","DebitNote_HOME_TRAVEL","FreeZoneCertificates_HOME_TRAVEL","PrintReceipt_HOME_TRAVEL","EndorsementSchedule_HOME_TRAVEL","CreditNote_HOME_TRAVEL","RenewalNotice_TRAVEL","ShowBankLetter_HOME_TRAVEL"),
	
	 _WC("ProposalForm_WC.rptdesign","PrintPolicyReports_WC.rptdesign","GrossDebitNote_WC","GrossCreditNote_WC","DebitNote_WC","FreeZoneCertificates_WC.rptdesign","PrintReceipt_WC","EndorsementSchedule_WC","CreditNote_WC","RenewalNotice_WC","ShowBankLetter_WC");
	 
	 private String proposalFormTemplate;
	 private String policyScheduleTemplate;
	 private String grossDebitNoteTemplate;
	 private String grossCreditNoteTemplate;
	 private String debitNoteTemplate;
	 private String freeZoneCertificateTemplate;
	 private String policyReceiptTemplate;
	 private String endorsementScheduleTemplate;
	 private String creditNotesTemplate;
	 private String renewalNoticeTemplate;
	 private String defaultTemplate="DEFAULT";
	 private String bankLetterTemplate;
	
	/**
	 * @param proposalFormTemplate
	 * @param policyScheduleTemplate
	 * @param grossDebitNoteTemplate
	 * @param grossCreditNoteTemplate
	 * @param debitNoteTemplate
	 * @param freeZoneCertificateTemplate
	 * @param policyReceiptTemplate
	 * @param endorsementScheduleTemplate
	 * @param creditNotes
	 */
	private ReportTemplateSet( String proposalFormTemplate, String policyScheduleTemplate, String grossDebitNoteTemplate, String grossCreditNoteTemplate, String debitNoteTemplate,
			String freeZoneCertificateTemplate, String policyReceiptTemplate, String endorsementScheduleTemplate, String creditNotesTemplate,String renewalNoticeTemplate,String bankLetterTemplate ){
		this.proposalFormTemplate = proposalFormTemplate;
		this.policyScheduleTemplate = policyScheduleTemplate;
		this.grossDebitNoteTemplate = grossDebitNoteTemplate;
		this.grossCreditNoteTemplate = grossCreditNoteTemplate;
		this.debitNoteTemplate = debitNoteTemplate;
		this.freeZoneCertificateTemplate = freeZoneCertificateTemplate;
		this.policyReceiptTemplate = policyReceiptTemplate;
		this.endorsementScheduleTemplate = endorsementScheduleTemplate;
		this.creditNotesTemplate = creditNotesTemplate;
		this.renewalNoticeTemplate = renewalNoticeTemplate;
		this.bankLetterTemplate = bankLetterTemplate;
	}
	/**
	 * @return the proposalFormTemplate
	 */
	public String getProposalFormTemplate(){
		return proposalFormTemplate;
	}
	/**
	 * @param proposalFormTemplate the proposalFormTemplate to set
	 */
	public void setProposalFormTemplate( String proposalFormTemplate ){
		this.proposalFormTemplate = proposalFormTemplate;
	}
	/**
	 * @return the policyScheduleTemplate
	 */
	public String getPolicyScheduleTemplate(){
		return policyScheduleTemplate;
	}
	/**
	 * @param policyScheduleTemplate the policyScheduleTemplate to set
	 */
	public void setPolicyScheduleTemplate( String policyScheduleTemplate ){
		this.policyScheduleTemplate = policyScheduleTemplate;
	}
	/**
	 * @return the grossDebitNoteTemplate
	 */
	public String getGrossDebitNoteTemplate(){
		return grossDebitNoteTemplate;
	}
	/**
	 * @param grossDebitNoteTemplate the grossDebitNoteTemplate to set
	 */
	public void setGrossDebitNoteTemplate( String grossDebitNoteTemplate ){
		this.grossDebitNoteTemplate = grossDebitNoteTemplate;
	}
	/**
	 * @return the grossCreditNoteTemplate
	 */
	public String getGrossCreditNoteTemplate(){
		return grossCreditNoteTemplate;
	}
	/**
	 * @param grossCreditNoteTemplate the grossCreditNoteTemplate to set
	 */
	public void setGrossCreditNoteTemplate( String grossCreditNoteTemplate ){
		this.grossCreditNoteTemplate = grossCreditNoteTemplate;
	}
	/**
	 * @return the debitNoteTemplate
	 */
	public String getDebitNoteTemplate(){
		return debitNoteTemplate;
	}
	/**
	 * @param debitNoteTemplate the debitNoteTemplate to set
	 */
	public void setDebitNoteTemplate( String debitNoteTemplate ){
		this.debitNoteTemplate = debitNoteTemplate;
	}
	/**
	 * @return the freeZoneCertificateTemplate
	 */
	public String getFreeZoneCertificateTemplate(){
		return freeZoneCertificateTemplate;
	}
	/**
	 * @param freeZoneCertificateTemplate the freeZoneCertificateTemplate to set
	 */
	public void setFreeZoneCertificateTemplate( String freeZoneCertificateTemplate ){
		this.freeZoneCertificateTemplate = freeZoneCertificateTemplate;
	}
	/**
	 * @return the policyReceiptTemplate
	 */
	public String getPolicyReceiptTemplate(){
		return policyReceiptTemplate;
	}
	/**
	 * @param policyReceiptTemplate the policyReceiptTemplate to set
	 */
	public void setPolicyReceiptTemplate( String policyReceiptTemplate ){
		this.policyReceiptTemplate = policyReceiptTemplate;
	}
	/**
	 * @return the endorsementScheduleTemplate
	 */
	public String getEndorsementScheduleTemplate(){
		return endorsementScheduleTemplate;
	}
	/**
	 * @param endorsementScheduleTemplate the endorsementScheduleTemplate to set
	 */
	public void setEndorsementScheduleTemplate( String endorsementScheduleTemplate ){
		this.endorsementScheduleTemplate = endorsementScheduleTemplate;
	}
	 
	 
	/**
	 * @return the creditNotes
	 */
	public String getCreditNotesTemplate(){
		return creditNotesTemplate;
	}

	/**
	 * @param creditNotes the creditNotes to set
	 */
	public void setCreditNotesTemplate( String creditNotesTemplate ){
		this.creditNotesTemplate = creditNotesTemplate;
	}
	
	/**
	 * @return the defaultTemplate
	 */
	public String getDefaultTemplate(){
		return defaultTemplate;
	}
	/**
	 * @param defaultTemplate the defaultTemplate to set
	 */
	public void setDefaultTemplate( String defaultTemplate ){
		this.defaultTemplate = defaultTemplate;
	}
	
	/**
	 * @return the renewalNoticeTemplate
	 */
	public String getRenewalNoticeTemplate() {
		return renewalNoticeTemplate;
	}
	/**
	 * @param renewalNoticeTemplate the renewalNoticeTemplate to set
	 */
	public void setRenewalNoticeTemplate(String renewalNoticeTemplate) {
		this.renewalNoticeTemplate = renewalNoticeTemplate;
	}
	/**
	 * @return the bankLetterTemplate
	 */
	public String getBankLetterTemplate(){
		return bankLetterTemplate;
	}
	/**
	 * @param bankLetterTemplate the bankLetterTemplate to set
	 */
	public void setBankLetterTemplate( String bankLetterTemplate ){
		this.bankLetterTemplate = bankLetterTemplate;
	}
	  
	
}
