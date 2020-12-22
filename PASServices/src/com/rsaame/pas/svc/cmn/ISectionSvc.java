package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * Service methods common to all sections
 */
public interface ISectionSvc{
	/**
	 * Deletes a location from a section. The expected input is <code>DelLocationInputVO</code>. The signature
	 * has been kept with BaseVO input and BaseVO return only to be able to comply with the needs of
	 * TaskExecutor.
	 * 
	 * @param input An instance of <code>DelLocationInputVO</code>
	 * @return null
	 */
	public BaseVO deleteLocation( BaseVO input );
	
	/**
	 * Loads the data for the section passed in the input. The input is expected to be of type LoadExistingDataInputVO
	 * only. Throws a ClassCaseException if it isn't.
	 * 
	 * @param input
	 * @return The data for the section in the form of <code>SectionVO</code> populated with all the location data
	 */
	public BaseVO loadSectionData( BaseVO input );
	
	/**
	 * Fetches the list of sections that were saved against the quote or policy as indicated by the passed PolicyVO.
	 * 
	 * @param input An instance of PolicyVO with <code>policyLinkingId</code> and <code>endtId</code> set.
	 * @return
	 */
	public BaseVO getSectionListForPolicy( BaseVO input );
	
	/**
	 * This function is used to fetch policy details from policy table for print policy docs
	 * @param input
	 * @return
	 */
	public BaseVO fetchPolicyInfoFromPolicyNo(BaseVO input);

	/**
	 * This function is used to fetch policy details from policy table for print policy docs
	 * @param input
	 * @return
	 */
	public BaseVO fetchPolicyInfoFromPolicyNoHomeTravel(BaseVO input);
	
	/**
	 * This function is used to fetch quote details from quotation table for proposal form
	 * @param input
	 * @return
	 */
	public BaseVO fetchQuotationInfoFromQuoteNo(BaseVO input);
	/**
	 * This function is used to save selected  risk selections list
	 * @param input
	 * @return
	 */
	
	
	public BaseVO saveSelectedRiskSections( BaseVO baseVO );
	
	/**
	 * This function is used to fetch premium summary details
	 * @param input
	 * @return
	 */
	public BaseVO getPremiumSummaryDetails(BaseVO baseVO);
	
	/**
	 * This function is used to delete selected  risk selections list
	 *  @param input
	 *  @return
	 */
	public BaseVO deleteSelectedRiskSection(BaseVO baseVO);
	
	public BaseVO getSavedSectionListForQuote(BaseVO baseVO);

}
