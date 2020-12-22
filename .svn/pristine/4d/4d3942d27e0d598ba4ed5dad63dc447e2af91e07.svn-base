package com.rsaame.pas.ui.cmn;

import java.util.Map;

/**
 * This class is a wrapper over SectionVO to handle the UI needs for quote generation and policy
 * screens.
 */
public class UISection{
	private boolean sectionSelected;
	private String jspPage;
	//the key is the risk group ID and the value is all the status for the risk per location
	private Map<Integer , UIRiskGroupStatus>  statusValues;
	private Integer sectionId;
	private int nextRiskGroupSequence; //Maintains the next in-memory sequence to be used for an unsaved risk group
	
	public boolean isSectionSelected() {
		return sectionSelected;
	}
	public void setSectionSelected(boolean sectionSelected) {
		this.sectionSelected = sectionSelected;
	}
	public Map<Integer, UIRiskGroupStatus> getStatusValues() {
		return statusValues;
	}
	public void setStatusValues(
			Map<Integer, UIRiskGroupStatus> statusValues) {
		this.statusValues = statusValues;
	}
	public String getJspPage() {
		return jspPage;
	}
	public void setJspPage(String jspPage) {
		this.jspPage = jspPage;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	
	/**
	 * Returns the next risk group sequence for the section and increments the internal counter.
	 * @return
	 */
	public int getNextRiskGroupSequence(){
		return ++nextRiskGroupSequence;
	}
	
}
