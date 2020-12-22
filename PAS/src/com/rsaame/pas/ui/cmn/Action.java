package com.rsaame.pas.ui.cmn;

/**
 * Defines actions that are used and handled within SectionRH.
 */
public enum Action {

	SAVE, /* For saving details of a risk group in a section */
	NEXT, /* For moving from one section to the next or to Premium Summary Page */
	PREVIOUS, /* For moving from one section to the previous */
	LOAD_SCREEN, /* For loading the screen for a section - empty or with data */
	LOAD_DATA, /* For loading the data for a section */
	CALCULATE_PREMIUM, /* For calculating premium on a section's risk group details */
	DELETE_LOCATION, /* For deleting a location from a section */
	ADD_LOCATION, /* For adding a location to a section */
	FETCH_PP_DATA,  /* For loading pre-populated data for the section. Mostly used in pre-packaged policies. */
	ADD_SECTION, /* For adding a new section through left navigation */
	DELETE_SECTION, /* Fpr deleting a section through left navigation */
	ADD_DAIRY_ITEM,
	REJECT_QUOTE,
	WORDINGS,
	CONVERT_TO_POLICY,
	CLOSE,
	LIST_ITEM_SCREEN,
	LIST_ITEM_SAVE,
	POPULATE_PREMIUM,
	CAPTURE_STAFF_DETAILS,  /* For displaying the staff details popup */
	SAVE_STAFF_DETAILS; /* For storing the staff details from the popup */
}
