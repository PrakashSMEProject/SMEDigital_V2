/**
 /* Since Phase 3.
 * Added for Travel Page. 
 */
package com.rsaame.pas.b2c.productComparison.ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.controllers.TravelController;
import com.rsaame.pas.b2c.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;

/**
 * @author M1016284
 *
 */
public class ProductsRenderer implements IHtmlRenderer {

	private static final Logger logger = Logger
			.getLogger(ProductsRenderer.class);
	private static final String ATTR_TEMPLATE = "%s='%s'";

	private int packageCount210 = 0;
	private int packageCount211 = 0;
	private int packageCount212 = 0;

	private String tariff210 = null;
	private String tariff211 = null;
	private String tariff212 = null;

	@SuppressWarnings("unchecked")
	@Override
	public void buildHTMLContent(HashMap<String, Object> attributeList)
			throws IOException {

		try {
			if (!Utils.isEmpty(attributeList.get(AppConstants.OUT))) {
				/* Preparing HTML content for under writing questions */
				JspWriter out = (JspWriter) attributeList.get("Out");
				StringBuffer responseString = new StringBuffer();
				List<CoverDetailsVO> covers;
				SchemeVO schemeVO = null;
				@SuppressWarnings("unused")
				Integer schemeCode = null;
				Boolean isFooter = false;
				if (!Utils.isEmpty(attributeList.get(AppConstants.INPUTTYPE))) {
					/*
					 * 
					 * Travel Scope Detaching Footer from the Rest : BEGIN :
					 * HIMANISH
					 */
					String inputType = (String) attributeList
							.get(AppConstants.INPUTTYPE);
					if (inputType.equalsIgnoreCase("productsRendererFooter")) {

						isFooter = true;
					}
					/*
					 * 
					 * Travel Scope Detaching Footer from the Rest : END :
					 * HIMANISH
					 */
					LoginLocation location = (LoginLocation) Utils
							.getBean("location");
					String deployedLocation = location.getLocation();
					java.util.List<TravelPackageVO> travelPackageVOs = new List<TravelPackageVO>(
							TravelPackageVO.class);
					TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) attributeList
							.get("TravelInsuranceVO");
					travelPackageVOs = getOrderedPackageList(travelInsuranceVO
							.getTravelPackageList());
					/* Preparing for a database call to get required values */
					if (attributeList.get(AppConstants.INPUTTYPE).toString()
							.equalsIgnoreCase(AppConstants.PRODUCT_RENDERER)) {

						boolean bchangeSelection = false;

						// Removing the schengen on below condition for Oman D2C
						if (null != deployedLocation
								&& deployedLocation
										.equals(AppConstants.LOCATION_CODE)) {
							for (TravelPackageVO travelPackageVO : travelPackageVOs) {
								if (travelPackageVO.getIsSelected()
										&& travelPackageVO
												.getTariffCode()
												.equals(AppConstants.SCHENGEN_TARIFF)) {
									if (!travelInsuranceVO.getTravelDetailsVO()
											.getTravelLocation()
											.equals(AppConstants.TRAVEL_TYPE)) {
										travelPackageVO.setPremiumAmt(0.0);
										travelPackageVO.setIsSelected(false);
										bchangeSelection = true;
									} else if (travelInsuranceVO
											.getPolicyTerm() == AppConstants.TRAVEL_PERIOD) {
										travelPackageVO.setPremiumAmt(0.0);
										travelPackageVO.setIsSelected(false);
										bchangeSelection = true;
									}
								} else if (travelPackageVO.getIsRecommended()
										&& bchangeSelection) {
									travelPackageVO.setIsSelected(true);
									bchangeSelection = false;
								} else if (travelPackageVO.getTariffCode()
										.equals(AppConstants.SCHENGEN_TARIFF)) {
									if (null != travelInsuranceVO
											.getTravelDetailsVO()
											&& !travelInsuranceVO
													.getTravelDetailsVO()
													.getTravelLocation()
													.equals(AppConstants.TRAVEL_TYPE)) {
										travelPackageVO.setPremiumAmt(0.0);
										travelPackageVO.setIsSelected(false);
									} else if (null != travelInsuranceVO
											.getTravelDetailsVO()
											&& travelInsuranceVO
													.getPolicyTerm() == AppConstants.TRAVEL_PERIOD) {
										travelPackageVO.setPremiumAmt(0.0);
										travelPackageVO.setIsSelected(false);
									}
								}
							}
						}

						if (!Utils.isEmpty(travelPackageVOs)) {

							//Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
							//int noOfProducts = travelPackageVOs.size();
							String defaultTariff = null;
							LoadCoverSvc coverSvc = null;
							if (!Utils.isEmpty(travelInsuranceVO)
									&& travelInsuranceVO.getCommonVO()
											.getIsQuote()) {
								coverSvc = (LoadCoverSvc) Utils
										.getBean("loadCoverSvc");
							} else {
								coverSvc = (LoadCoverSvc) Utils
										.getBean("loadCoverSvc_POL");
							}
							//Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
							//schemeCode = travelInsuranceVO.getScheme()
							//		.getSchemeCode();
							schemeVO = travelInsuranceVO.getScheme();
							if (!Utils.isEmpty(travelInsuranceVO.getCommonVO())
									&& !Utils.isEmpty(travelInsuranceVO
											.getCommonVO().getQuoteNo())) { // Travel
																			// Scope
																			// 131378
								schemeVO.setQuoteNo(travelInsuranceVO
										.getCommonVO().getQuoteNo());
							}
							List<CoverDetailsVO> addlCovers = new List<CoverDetailsVO>(
									CoverDetailsVO.class);
						
							covers = (List<CoverDetailsVO>) coverSvc
									.invokeMethod("getTravelCovers", schemeVO);

							responseString.append("<section ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_CLASS, "coverage"));
							responseString.append(">");

							if (null != deployedLocation
									&& deployedLocation
											.equals(AppConstants.LOCATION_CODE)) {
								responseString.append("<ul ");
								responseString.append(String.format(
										ATTR_TEMPLATE, com.Constant.CONST_CLASS,
										"quote oman-quote"));
							}

							responseString.append(com.Constant.CONST_DIV_NONCLOSING);
							responseString
									.append(String.format(ATTR_TEMPLATE,
											com.Constant.CONST_CLASS,
											"quote-table-wrapper travel-step2"));
							responseString.append(">");

							responseString.append(com.Constant.CONST_DIV_NONCLOSING);
							responseString
									.append("data-scrollmagic-pin-spacer=\"\" ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_CLASS, "scrollmagic-pin-spacer"));
							responseString
									.append(String
											.format(ATTR_TEMPLATE,
													"style",
													"top: 0px; left: 0px; bottom: auto; right: 0px; margin: 0px; display: table; position: absolute; box-sizing: content-box; width: 100%; height: 115px;"));
							responseString.append(">");

							responseString.append("<table ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_CLASS, "quote-table quote-table-header"));
							responseString
									.append(String
											.format(ATTR_TEMPLATE,
													"style",
													"position: absolute; margin: auto; top: 0px; left: 0px; bottom: auto; right: auto; box-sizing: border-box;"));
							responseString.append(">");

							responseString.append("<thead>");

							createPackageHeader(responseString,
									travelPackageVOs);

							responseString.append("</thead>");
							responseString.append("</table>");
							responseString.append(com.Constant.CONST_DIV_CLOSING);

							responseString.append("<table ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_CLASS, "quote-table"));
							responseString.append("> ");

							responseString.append(createCoversColumn(covers,
									travelInsuranceVO, addlCovers,
									travelPackageVOs, defaultTariff));

						}
					} else if (isFooter && !Utils.isEmpty(travelPackageVOs)) {

						responseString = createPackageFooter(responseString,
								deployedLocation, travelPackageVOs,travelInsuranceVO);

						responseString.append("</table> ");

						responseString.append(com.Constant.CONST_DIV_CLOSING);

						responseString.append("</ul>");
						responseString.append("</section>");
					}
				}
				out.print(responseString);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private StringBuffer createCoversColumn(List<CoverDetailsVO> covers,
			TravelInsuranceVO travelInsuranceVO,
			List<CoverDetailsVO> addlCovers,
			java.util.List<TravelPackageVO> travelPackageVOs,
			String defaultTariff) {

		StringBuffer responseString = new StringBuffer();

		defaultTariff = createPackageHeader(responseString, travelPackageVOs,
				defaultTariff);

		System.out.println("defaultTariff       --" + defaultTariff);
		displayCoverNames(responseString, covers, travelPackageVOs);

		createSchemeDetails(responseString, travelInsuranceVO.getScheme()
				.getSchemeCode(), defaultTariff);

		return responseString;
	}

	/**
	 * @param responseString
	 * @param heading
	 *            Creates the First row with Package Name.
	 */
	private void createPackageHeader(StringBuffer responseString,
			java.util.List<TravelPackageVO> travelPackageVOs) {
		int headerSize = travelPackageVOs.size();

		responseString.append(com.Constant.CONST_TABLE_ROW_START);
		responseString = populateTopHeader(headerSize, responseString,
				travelPackageVOs);
		responseString.append(com.Constant.CONST_TABLE_ROW_END);

		responseString.append(com.Constant.CONST_TABLE_ROW_START);
		responseString = populateTopSecondHeader(headerSize, responseString,
				travelPackageVOs);
		responseString.append(com.Constant.CONST_TABLE_ROW_END);

	}

	private String createPackageHeader(StringBuffer responseString,
			java.util.List<TravelPackageVO> travelPackageVOs,
			String defaultTariff) {
		String isSelected = "";
		int i = 0;

		responseString.append("<tbody ");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
				"table-group-cell"));
		responseString.append(">");
		responseString.append("<tr>");
		responseString.append("<td> </td>");
		for (TravelPackageVO travelPackageVO : travelPackageVOs) {
			responseString.append("<td> ");
			if (travelPackageVO.getTariffCode().equals(
					AppConstants.SCHENGEN_TARIFF)) {
				packageCount210 = i;
				tariff210 = travelPackageVO.getTariffCode();

				System.out.println("packageCount210 ********* "
						+ packageCount210);
			}

			if (travelPackageVO.getTariffCode().equals(
					AppConstants.HOLIDAYTRAVEL_TARIFF)) {
				packageCount212 = i;
				tariff212 = travelPackageVO.getTariffCode();
				System.out.println("packageCount212 *********"
						+ packageCount212);

			}

			if (travelPackageVO.getTariffCode().equals(
					AppConstants.EXECUTIVETRAVEL_TARIFF)) {
				packageCount211 = i;
				tariff211 = travelPackageVO.getTariffCode();
				System.out.println("packageCount211    *********"
						+ packageCount211);

			}

			if (travelPackageVO.getIsSelected()) {
				isSelected = " selected";
			}

			if (travelPackageVO.getIsRecommended()) {
				if (defaultTariff == null) {
					defaultTariff = travelPackageVO.getTariffCode();

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, "type",
							com.Constant.CONST_HIDDEN));
					responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
							+ String.valueOf(i) + "].isRecommended'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
							true));
					responseString.append(com.Constant.CONST_START_INPUT_END);
				}
			}

			if (travelPackageVO.getIsSelected()) {
				defaultTariff = travelPackageVO.getTariffCode(); // override
																	// default
			}

			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
					+ String.valueOf(i) + "].premiumAmt'");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					travelPackageVO.getPremiumAmt()));
			responseString.append(com.Constant.CONST_START_INPUT_END);

			if (!Utils.isEmpty(travelPackageVO.getTariffCode())) {
				responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
				responseString.append(String.format(ATTR_TEMPLATE, "type",
						com.Constant.CONST_HIDDEN));
				responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
						+ String.valueOf(i) + "].tariffCode'");
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
						travelPackageVO.getTariffCode()));
				responseString.append(com.Constant.CONST_START_INPUT_END);
			}

			if (!Utils.isEmpty(travelPackageVO.getPackageName())) {
				responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
				responseString.append(String.format(ATTR_TEMPLATE, "type",
						com.Constant.CONST_HIDDEN));
				responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
						+ String.valueOf(i) + "].packageName'");
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
						travelPackageVO.getPackageName()));
				responseString.append(com.Constant.CONST_START_INPUT_END);
			}

			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
					+ String.valueOf(i) + "].order'");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					travelPackageVO.getOrder()));
			responseString.append(com.Constant.CONST_START_INPUT_END);

			responseString.append("</td> ");

			i++;
		}
		System.out.println("defaultTariff    *********" + defaultTariff);
		responseString.append("</tr>");
		return defaultTariff;

	}

	private void displayCoverNames(StringBuffer responseString,
			List<CoverDetailsVO> covers,
			java.util.List<TravelPackageVO> travelPackageVOs) {

		CoverDetailsVO coverDetailsVO210 = null;
		CoverDetailsVO coverDetailsVO211 = null;
		CoverDetailsVO coverDetailsVO212 = null;

		Boolean matchExists210 = Boolean.FALSE;
		Boolean matchExists211 = Boolean.FALSE;
		Boolean matchExists212 = Boolean.FALSE;
		int coverCount = 0;
		responseString.append("<tbody ");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
				"table-group-cell"));
		responseString.append(">");
		String isSelected = "";

		/* Render the cover names one by one in a loop. */
		for (CoverDetailsVO cover : covers) {

			List<CoverDetailsVO> covers210 = (List<CoverDetailsVO>) travelPackageVOs
					.get(0).getCovers();
			List<CoverDetailsVO> covers212 = (List<CoverDetailsVO>) travelPackageVOs
					.get(1).getCovers();
			List<CoverDetailsVO> covers211 = (List<CoverDetailsVO>) travelPackageVOs
					.get(2).getCovers();

			for (CoverDetailsVO coverDetails210 : covers210) {
				if (coverDetails210.getCoverCodes().getCovCode() == cover
						.getCoverCodes().getCovCode()) {
					matchExists210 = Boolean.TRUE;
					coverDetailsVO210 = coverDetails210;
					break;

				}
			}

			for (CoverDetailsVO coverDetails212 : covers212) {
				if (coverDetails212.getCoverCodes().getCovCode() == cover
						.getCoverCodes().getCovCode()) {
					matchExists212 = Boolean.TRUE;
					coverDetailsVO212 = coverDetails212;
					break;
				}
			}

			for (CoverDetailsVO coverDetails211 : covers211) {
				if (coverDetails211.getCoverCodes().getCovCode() == cover
						.getCoverCodes().getCovCode()) {
					matchExists211 = Boolean.TRUE;
					coverDetailsVO211 = coverDetails211;
					break;
				}
			}

			if (cover.getPrcBToCDisplayInd() == 1) { // Travel Scope 131378 :
														// Shortened covers : to
														// display 9 covers on
														// UI

				responseString.append("<tr>");
				responseString.append("<td> ");
				responseString.append(com.Constant.CONST_DIV_NONCLOSING);
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
						"content-holder body-content text-left"));
				responseString.append(">");
				responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
				responseString.append(cover.getCoverName());
				responseString.append(com.Constant.CONST_STRONG_CLOSING);
				responseString.append(com.Constant.CONST_DIV_CLOSING);
				responseString.append(com.Constant.CONST_TD_END);

				/*
				 * Schengen Travel
				 */
				if (matchExists210
						&& cover.getMandatoryIndicator()
						&& FieldType.LABEL.equals(coverDetailsVO210
								.getFieldType())) {
					responseString.append("<td ");
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff210 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(0).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(coverDetailsVO210.getSumInsured()
							.geteDesc());
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount210, coverDetailsVO210);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";

				} else {

					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(0).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(com.Constant.CONST_NOT_COVERED);
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				}

				/*
				 * Holiday Travel
				 */
				if (matchExists212
						&& cover.getMandatoryIndicator()
						&& FieldType.LABEL.equals(coverDetailsVO212
								.getFieldType())) {
					responseString.append("<td ");
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					if (travelPackageVOs.get(1).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							"content-holder body-content bordered-cell recommended-content  "
									+ isSelected));
					responseString.append(">");
					responseString.append("<span ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							"amt"));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(coverDetailsVO212.getSumInsured()
							.geteDesc());
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append("</span>");
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount212, coverDetailsVO212);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";

				} else if (matchExists212
						&& cover.getMandatoryIndicator()
						&& FieldType.CHECK_BOX.equals(coverDetailsVO212
								.getFieldType())) {
					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(1).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_RECOMMENDED_CONTENT_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_TS_CHECKBOXWRAP_CLS));
					responseString.append(">");

					// hp
					responseString.append(com.Constant.CONST_DIV_CLASS_CHECKBOX_END);

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_TYPE,
							com.Constant.CONST_CHECKBOX));
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_DATA_INDEX,
							2));
					if (!coverDetailsVO212.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO212
											.getIsCovered())) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
					}
					/*
					 * make the check box checked and disabled if the cover is
					 * selected in promo code
					 */
					if (coverDetailsVO212.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
						responseString.append(String.format(ATTR_TEMPLATE,
								" disabled", true));
					}

					responseString.append(com.Constant.CONST_START_INPUT_END);
					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, "type",
							com.Constant.CONST_HIDDEN));
					responseString.append(com.Constant.CONST_ID_COVERSI_END1 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
							+ String.valueOf(packageCount212) + com.Constant.CONST_COVERS
							+ String.valueOf(coverCount) + com.Constant.CONST_ISCOVERED);
					if (!coverDetailsVO212.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO212
											.getIsCovered())
							|| coverDetailsVO212.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, "on"));
					} else {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, null));
					}
					responseString.append(com.Constant.CONST_START_INPUT_END);

					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount212, coverDetailsVO212);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				} else if (matchExists212 && !cover.getMandatoryIndicator()) { // Additional
																				// Covers
																				// :
																				// winter
																				// ,
																				// golf....
					responseString.append("<td ");
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(1).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_RECOMMENDED_CONTENT_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_TS_CHECKBOXWRAP_CLS));
					responseString.append(">");

					responseString.append(com.Constant.CONST_DIV_CLASS_CHECKBOX_END);

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_TYPE,
							com.Constant.CONST_CHECKBOX));
					responseString.append(String.format(ATTR_TEMPLATE,
							com.Constant.CONST_CLASS, com.Constant.CONST_REQUIRED));
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_DATA_INDEX,
							2));

					/* new changes */
					if (!coverDetailsVO212.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO212
											.getIsCovered())) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
					}

					responseString.append(com.Constant.CONST_START_INPUT_END);
					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, "type",
							com.Constant.CONST_HIDDEN));
					responseString.append(com.Constant.CONST_ID_COVERSI_END1 + tariff212 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
							+ String.valueOf(packageCount212) + com.Constant.CONST_COVERS
							+ String.valueOf(coverCount) + com.Constant.CONST_ISCOVERED);
					if (!coverDetailsVO212.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO212
											.getIsCovered())
							|| coverDetailsVO212.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, "on"));
					} else {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, null));
					}
					responseString.append(com.Constant.CONST_START_INPUT_END);
					// hp
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount212, coverDetailsVO212);
					responseString.append(com.Constant.CONST_TD_END);

					isSelected = "";
				} else {
					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(1).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_RECOMMENDED_CONTENT_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(com.Constant.CONST_NOT_COVERED);
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				}
				/*
				 * Executive Travel
				 */
				if (matchExists211
						&& cover.getMandatoryIndicator()
						&& FieldType.LABEL.equals(coverDetailsVO211
								.getFieldType())) {
					responseString.append("<td ");
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(2).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(coverDetailsVO211.getSumInsured()
							.geteDesc());
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount211, coverDetailsVO211);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";

				} else if (matchExists211
						&& cover.getMandatoryIndicator()
						&& FieldType.CHECK_BOX.equals(coverDetailsVO211
								.getFieldType())) {
					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(2).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_TS_CHECKBOXWRAP_CLS));
					responseString.append(">");

					// hp
					responseString.append(com.Constant.CONST_DIV_CLASS_CHECKBOX_END);

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_TYPE,
							com.Constant.CONST_CHECKBOX));
					responseString.append(String.format(ATTR_TEMPLATE,
							com.Constant.CONST_CLASS, com.Constant.CONST_REQUIRED));
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_DATA_INDEX,
							3));
					/* new changes */
					if (!coverDetailsVO211.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO211
											.getIsCovered())) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
					}
					/*
					 * make the check box checked and disabled if the cover is
					 * selected in promo code
					 */
					if (coverDetailsVO211.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
						responseString.append(String.format(ATTR_TEMPLATE,
								" disabled", true));
					}
					responseString.append(com.Constant.CONST_START_INPUT_END);

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, "type",
							com.Constant.CONST_HIDDEN));
					responseString.append(com.Constant.CONST_ID_COVERSI_END1 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
							+ String.valueOf(packageCount211) + com.Constant.CONST_COVERS
							+ String.valueOf(coverCount) + com.Constant.CONST_ISCOVERED);
					if (!coverDetailsVO211.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO211
											.getIsCovered())
							|| coverDetailsVO211.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, "on"));
					} else {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, null));
					}
					responseString.append(com.Constant.CONST_START_INPUT_END);
					// hp
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					createRequiredHiddenFields(responseString, coverCount,
							packageCount211, coverDetailsVO211);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				} else if (matchExists211 && !cover.getMandatoryIndicator()) { // Additional
																				// Covers
					responseString.append("<td ");
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(2).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_TS_CHECKBOXWRAP_CLS));
					responseString.append(">");

					// hp
					responseString.append(com.Constant.CONST_DIV_CLASS_CHECKBOX_END);

					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_TYPE,
							com.Constant.CONST_CHECKBOX));
					responseString.append(String.format(ATTR_TEMPLATE,
							com.Constant.CONST_CLASS, com.Constant.CONST_REQUIRED));
					responseString.append(com.Constant.CONST_ID_COVERSI_END2 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_DATA_INDEX,
							3));

					/* new changes */
					if (!coverDetailsVO211.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO211
											.getIsCovered())) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED));
					}

					responseString.append(com.Constant.CONST_START_INPUT_END);
					responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, "type",
							com.Constant.CONST_HIDDEN));
					responseString.append(com.Constant.CONST_ID_COVERSI_END1 + tariff211 + "["
							+ String.valueOf(coverCount) + "]'");
					responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
							+ String.valueOf(packageCount211) + com.Constant.CONST_COVERS
							+ String.valueOf(coverCount) + com.Constant.CONST_ISCOVERED);
					if (!coverDetailsVO211.getSumInsured().geteDesc()
							.equals(AppConstants.zeroVal)
							|| AppConstants.STATUS_ON
									.equalsIgnoreCase(coverDetailsVO211
											.getIsCovered())
							|| coverDetailsVO211.getSumInsured().isPromoCover()) {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, "on"));
					} else {
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_VALUE, null));
					}
					responseString.append(com.Constant.CONST_START_INPUT_END);
					responseString.append(com.Constant.CONST_DIV_CLOSING);

					createRequiredHiddenFields(responseString, coverCount,
							packageCount211, coverDetailsVO211);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				} else {
					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CELL_SEPERATOR));
					responseString.append(">");
					if (travelPackageVOs.get(2).getIsSelected()) {
						isSelected = com.Constant.CONST_ACTIVE;
					}
					responseString.append(com.Constant.CONST_DIV_NONCLOSING);
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
							com.Constant.CONST_CONTENT_HOLDER_BODY_CONTENT_BORDERED_CELL_END
									+ isSelected));
					responseString.append(">");
					responseString.append(com.Constant.CONST_STRONG_NONCLOSING);
					responseString.append(com.Constant.CONST_NOT_COVERED);
					responseString.append(com.Constant.CONST_STRONG_CLOSING);
					responseString.append(com.Constant.CONST_DIV_CLOSING);
					responseString.append(com.Constant.CONST_TD_END);
					isSelected = "";
				}
				responseString.append("</tr>");
			}
			if (cover.getPrcBToCDisplayInd() == 0) {
				/*
				 * 
				 * Hidden Covers are being created with div class -
				 * hidden-covers Visible Covers are being created using by <tr>
				 */
				responseString.append(com.Constant.CONST_DIV_NONCLOSING);
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
						"hidden-covers"));
				responseString.append("> ");
				if (matchExists210 && coverDetailsVO210 != null) {
					createRequiredHiddenFields(responseString, coverCount,
							packageCount210, coverDetailsVO210);
				}

				if (matchExists212 && coverDetailsVO212 != null) {
					createRequiredHiddenFields(responseString, coverCount,
							packageCount212, coverDetailsVO212);
				}

				if (matchExists211 && coverDetailsVO211 != null) {
					createRequiredHiddenFields(responseString, coverCount,
							packageCount211, coverDetailsVO211);
				}

				responseString.append("</div> ");

			}

			coverCount++;

			coverDetailsVO210 = null;
			coverDetailsVO211 = null;
			coverDetailsVO212 = null;

			matchExists210 = Boolean.FALSE;
			matchExists211 = Boolean.FALSE;
			matchExists212 = Boolean.FALSE;
		}
		responseString.append("</tbody> ");
		System.out.println("responseString" + responseString);

	}

	private StringBuffer createPackageFooter(StringBuffer responseString,
			String deployedLocation,
			java.util.List<TravelPackageVO> travelPackageVOs, TravelInsuranceVO travelInsuranceVO) throws ParseException {

		responseString.append("  <tfoot>");
		responseString.append(com.Constant.CONST_TABLE_ROW_START);
		responseString.append("  <td>");	
		responseString.append("  </td>");
		
		TravelController tc = new TravelController();
		
		
		if(tc.checkPolPreparedDt(travelInsuranceVO)){
		createPackageFooterViewMore(responseString, deployedLocation,
				travelPackageVOs);
		}
		responseString.append(com.Constant.CONST_TABLE_ROW_END);

		responseString.append("  <tr ");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
				"button-wrapper"));
		responseString.append(" > ");

		createPackageFooterSelect(responseString, deployedLocation,
				travelPackageVOs);

		responseString.append("  </tr> ");
		responseString.append("  </tfoot>");

		return responseString;
	} 

	private StringBuffer createPackageFooterViewMore(
			StringBuffer responseString, String deployedLocation,
			java.util.List<TravelPackageVO> travelPackageVOs) {

		String isSelected = "";
		String isRecommended = "";
		int dataFlag=0;

		for (int index = 0; index <= travelPackageVOs.size() - 1; index++) {
			if (travelPackageVOs.get(index).getIsSelected()) {
				isSelected = com.Constant.CONST_ACTIVE;
				dataFlag=1;
				
			}
			if (travelPackageVOs.get(index).getIsRecommended()) {
				isRecommended = com.Constant.CONST_RECOMMENDED_CONTENT;

			}

			responseString.append("  <td ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					com.Constant.CONST_CELL_SEPERATOR));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_DIV_NONCLOSING);
			responseString
					.append(String
							.format(ATTR_TEMPLATE,
									com.Constant.CONST_CLASS,
									"content-holder body-content bg-grey viewmore-btn bordered-cell bordered-cell-bottom  "
											+ isSelected + " " + isRecommended+" "));
			
			/*
			 * 
			 * Data-Index and Data-Flag for View More
			 * 
			 */
			responseString
			.append(String
					.format(ATTR_TEMPLATE,
							com.Constant.CONST_DATA_INDEX,index+1));
			
			responseString
			.append(String
					.format(ATTR_TEMPLATE,
							"data-flag",dataFlag));
			
	responseString.append("> ");

			responseString.append("<i  ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"icon-status"));
			responseString.append(" ></i>&nbsp;&nbsp;<span>");
			responseString.append(AppConstants.B2C_TRAVEL_SCOPE_MORE_STRING+"</span>");
			responseString.append(com.Constant.CONST_DIV_CLOSING);
			responseString.append(com.Constant.CONST_TD_END);
			isRecommended = "";
			isSelected = "";
			dataFlag=0;
		}
		return responseString;
	}

	private StringBuffer createPackageFooterSelect(StringBuffer responseString,
			String deployedLocation,
			java.util.List<TravelPackageVO> travelPackageVOs) {

		String isSelected = "";
		String isRecommended = "";

		responseString.append("  <td >");
		responseString.append("  </td>  ");

		for (int index = 0; index <= travelPackageVOs.size() - 1; index++) {
			if (travelPackageVOs.get(index).getIsSelected()) {
				isSelected = com.Constant.CONST_ACTIVE;
			}
			if (travelPackageVOs.get(index).getIsRecommended()) {
				isRecommended = com.Constant.CONST_RECOMMENDED_CONTENT;

			}

			responseString.append("  <td ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					com.Constant.CONST_CELL_SEPERATOR));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_DIV_NONCLOSING);
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"control-group "));
			responseString.append("> ");

			responseString.append("  <fieldset  ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"controls "));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"premium-amt"));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					travelPackageVOs.get(index).getPremiumAmt()));
			responseString.append(">");
			responseString.append(com.Constant.CONST_INPUT_CLOSING);

			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"premium-discount"));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					travelPackageVOs.get(index).getPromoDiscPerc()));
			responseString.append(">");
			responseString.append(com.Constant.CONST_INPUT_CLOSING);

			responseString.append("<a ");
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(String.format(ATTR_TEMPLATE, "id", "select_"
					+ travelPackageVOs.get(index).getTariffCode() + " "));
			responseString.append(String.format(ATTR_TEMPLATE, "onclick",
					"populateTariffAndPremium("
							+ travelPackageVOs.get(index).getTariffCode()
							+ ") "));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"btn btn-select btn-green " + isSelected + " "));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_DATA_INDEX,
					index + 1));
			responseString.append(">");
			responseString.append(AppConstants.B2C_TRAVEL_SCOPE_SELECT_STRING);
			responseString.append("</a>");

			responseString.append("  </fieldset>  ");

			responseString.append(com.Constant.CONST_DIV_CLOSING);
			responseString.append("  </td>  ");
			isRecommended = "";
			isSelected = "";
		}
		return responseString;
	}

	/**
	 * Method to order the packages.
	 * 
	 * @param travelPackageList
	 * @return
	 */
	private java.util.List<TravelPackageVO> getOrderedPackageList(
			java.util.List<TravelPackageVO> travelPackageList) {
		int order = 1;
		boolean toBeSorted = false;
		for (TravelPackageVO packageVO : travelPackageList) {
			if (Utils.isEmpty(packageVO.getOrder())) {
				packageVO.setOrder(order);
			} else {
				toBeSorted = true;
			}
			order++;
		}

		if (toBeSorted) {
			Collections.sort(travelPackageList);
		}
		return travelPackageList;
	}

	/**
	 * @param responseString
	 *            Render hidden schemeCode and tariffCode fields.
	 * @param schemeCode
	 * @param tariffCode
	 * @param setHiddenCodes
	 */
	private void createSchemeDetails(StringBuffer responseString,
			Integer schemeCode, String tariffCode) {

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(String.format(ATTR_TEMPLATE, "id", "tariffCode"));
		responseString
				.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE, tariffCode));
		responseString.append(String.format(ATTR_TEMPLATE, "name",
				"scheme.tariffCode"));
		responseString.append(">");
		responseString.append(com.Constant.CONST_INPUT_CLOSING);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				schemeCode.toString()));
		responseString.append(String.format(ATTR_TEMPLATE, "id", "schemeCode"));
		responseString.append(String.format(ATTR_TEMPLATE, "name",
				"scheme.schemeCode"));
		responseString.append(">");
		responseString.append(com.Constant.CONST_INPUT_CLOSING);

	}

	private StringBuffer populateTopSecondHeader(int headerSize,
			StringBuffer responseString,
			java.util.List<TravelPackageVO> travelPackageVOs) {
		String isSelected = "";
		String isRecommended = "";

		responseString.append("  <th>");
		responseString.append(com.Constant.CONST_DIV_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
				"content-holder bg-pink text-left header-strip"));
		responseString.append("> ");
		responseString.append("  <strong>");
		responseString.append(AppConstants.B2C_TRAVEL_SCOPE_COVER_STRING);
		responseString.append("  </strong>");
		responseString.append(com.Constant.CONST_DIV_CLOSING);

		responseString.append("  </th>");
		for (int index = 0; index <= headerSize - 1; index++) {
			if (travelPackageVOs.get(index).getIsSelected()) {
				isSelected = com.Constant.CONST_ACTIVE;
			}
			if (travelPackageVOs.get(index).getIsRecommended()) {
				isRecommended = com.Constant.CONST_RECOMMENDED_CONTENT;

			}

			responseString.append("  <th ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					com.Constant.CONST_CELL_SEPERATOR));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_DIV_NONCLOSING);
			if (isRecommended.equalsIgnoreCase(com.Constant.CONST_RECOMMENDED_CONTENT))
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
						"content-holder bg-pink-gradient header-strip  bordered-cell   "
								+ isSelected + " " + isRecommended));
			else
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
						"content-holder bg-dark header-strip  bordered-cell   "
								+ isSelected + " " + isRecommended));
			responseString.append("> ");

			responseString.append("  <div>  ");
			responseString.append(com.Constant.CONST_SPAN_NONCLOSING);
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"unit font-size-13"));
			responseString.append("> ");
			responseString.append(Currency.getUnitName());
			responseString.append(com.Constant.CONST_SPAN_CLOSING);

			responseString.append(com.Constant.CONST_SPAN_NONCLOSING);
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS, "amt"));
			responseString.append(String.format(ATTR_TEMPLATE, "id", "header_"
					+ travelPackageVOs.get(index).getTariffCode()));

			responseString.append("> ");
			responseString.append(Currency.getFormattedCurrency(
					travelPackageVOs.get(index).getPremiumAmt(),
					LOB.TRAVEL.toString()));
			responseString.append(com.Constant.CONST_SPAN_CLOSING);

			/*
			 * 
			 * Hidden Fields
			 */
			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
					+ String.valueOf(index) + "].premiumAmt'");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					travelPackageVOs.get(index).getPremiumAmt()));
			responseString.append(com.Constant.CONST_START_INPUT_END);

			responseString.append(com.Constant.CONST_DIV_CLOSING);
			responseString.append(com.Constant.CONST_DIV_CLOSING);
			isRecommended = "";
			isSelected = "";

		}

		return responseString;

	}

	@SuppressWarnings("unused")
	private StringBuffer populateTopHeader(int headerSize,
			StringBuffer responseString,
			java.util.List<TravelPackageVO> travelPackageVOs) {
		@SuppressWarnings("unused")
		String isSelected = "";
		String isRecommended = "";
		/*
		 * Empty Box : Top Header
		 */
		responseString.append("  <th> ");
		responseString.append("  </th> ");

		// Is recommended and is selected
		for (int index = 0; index <= headerSize - 1; index++) {
			/*
			 * Check Previously selected one
			 */
			if (travelPackageVOs.get(index).getIsSelected()) {
				isSelected = com.Constant.CONST_ACTIVE;
			}
			if (travelPackageVOs.get(index).getIsRecommended()) {
				isRecommended = com.Constant.CONST_RECOMMENDED_CONTENT;

			}

			responseString.append("  <th ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					com.Constant.CONST_CELL_SEPERATOR));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_DIV_NONCLOSING);
			/*
			 * 
			 * Recommended Portion
			 */
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"content-holder header-content bordered-cell bordered-cell-top "
							+ isSelected + " " + isRecommended));
			responseString.append(">");

			if (isRecommended.equalsIgnoreCase(com.Constant.CONST_RECOMMENDED_CONTENT)) {
				responseString.append(com.Constant.CONST_DIV_NONCLOSING);
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
						"recommended-label bg-pink"));
				responseString.append(">");
				responseString
						.append(AppConstants.B2C_TRAVEL_SCOPE_RECOMMENDED_STRING);
				responseString.append(com.Constant.CONST_DIV_CLOSING);
			}

			responseString.append(" <h2> "
					+ travelPackageVOs.get(index).getPackageName()
							.replace("Travel", ""));
			responseString.append("  <i  ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"icon-tool-tip"));
			responseString.append("> ");

			responseString.append("  <p  ");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"smallipopWhite"));
			responseString.append(String.format(ATTR_TEMPLATE, "title", ""));
			responseString.append("> ");

			responseString.append(com.Constant.CONST_SPAN_NONCLOSING);
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_CLASS,
					"smallipopHint"));

			responseString.append("  ><span>");

			responseString.append(Utils.getSingleValueAppConfig("TOOLTIP_"
										+ travelPackageVOs.get(index)
												.getTariffCode()));

			responseString.append(com.Constant.CONST_SPAN_CLOSING);

			responseString.append("<em>x</em></span> ");

			/*
			 * 
			 * 
			 * Hidden Fields
			 */
			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
					+ String.valueOf(index) + "].description'");
			if (null != travelPackageVOs
					&& travelPackageVOs.equals(AppConstants.LOCATION_CODE)) {
				responseString
						.append(String.format(
								ATTR_TEMPLATE,
								com.Constant.CONST_VALUE,
								Utils.getSingleValueAppConfig("TRAFFIC_"
										+ travelPackageVOs.get(index)
												.getTariffCode())));
			} else {
				responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
						travelPackageVOs.get(index).getDescription()));
			}
			responseString.append(com.Constant.CONST_START_INPUT_END);

			responseString.append("  </p>  ");
			responseString.append("  </i>  ");
			responseString.append("  </h2>  ");

			responseString.append("  <span>  ");
			responseString.append(AppConstants.B2C_TRAVEL_SCOPE_TRAVEL_STRING);
			responseString.append("  </span>  ");

			responseString.append("  </div> ");
			responseString.append("  </th> ");
			isRecommended = "";
			isSelected = "";
		}

		return responseString;
	}

	/**
	 * @param responseString
	 * @param coverCount
	 * @param packageCount
	 * @param coverInPackage
	 */
	private void createRequiredHiddenFields(StringBuffer responseString,
			int coverCount, int packageCount, CoverDetailsVO coverInPackage) {

		/* Start : Hidden fields for Load. */
		/* new changes */
		if (FieldType.LABEL.equals(coverInPackage.getFieldType())
				|| FieldType.RADIO.equals(coverInPackage.getFieldType())) {
			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String
					.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
			responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
					+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
					+ String.valueOf(coverCount) + "].sumInsured.sumInsured'");
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getSumInsured().getSumInsured()));
			responseString.append(com.Constant.CONST_START_INPUT_END);
		}
		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].mandatoryIndicator'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getMandatoryIndicator()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverName'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getCoverName()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].tariffCode'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getTariffCode()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].fieldType'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getFieldType()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].sumInsured.eDesc'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getSumInsured().geteDesc()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].sumInsured.aDesc'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getSumInsured().getaDesc()));
		responseString.append(com.Constant.CONST_START_INPUT_END);

		/*
		 * Oman:D2C Deductible for Policy Report
		 */
		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString
				.append(String.format(
						ATTR_TEMPLATE,
						"name",
						("travelPackageList[" + String.valueOf(packageCount)
								+ com.Constant.CONST_COVERS + String.valueOf(coverCount) + "].sumInsured.deductible")));
		responseString
				.append(String.format(
						ATTR_TEMPLATE,
						"id",
						("travelPackageList[" + String.valueOf(packageCount)
								+ com.Constant.CONST_COVERS + String.valueOf(coverCount) + "].sumInsured.deductible")));
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getSumInsured().getDeductible().toString()));
		responseString.append("/>");

		/* End : Hidden fields for Load. */

		/* Start : Hidden fields for save. */

		if (coverInPackage.getSumInsured().isPromoCover()) {
			responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
			responseString.append(String.format(ATTR_TEMPLATE, " name",
					("promoCover_" + coverInPackage.getTariffCode().toString()
							+ "[" + coverCount + "]")));
			responseString.append(String.format(ATTR_TEMPLATE, " id",
					("promoCover_" + coverInPackage.getTariffCode().toString()
							+ "[" + coverCount + "]")));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_TYPE,
					com.Constant.CONST_HIDDEN));
			responseString.append(String
					.format(ATTR_TEMPLATE, " value", "true"));
			responseString.append("/>");
		}

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covCode'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covCode'");
		if (!Utils.isEmpty(coverInPackage.getCoverCodes())
				&& !Utils.isEmpty(coverInPackage.getCoverCodes().getCovCode())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getCoverCodes().getCovCode()));
		}
		/*
		 * else{ responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,
		 * ZERO ) ); }
		 */
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covSubTypeCode'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covSubTypeCode'");
		if (!Utils.isEmpty(coverInPackage.getCoverCodes())
				&& !Utils.isEmpty(coverInPackage.getCoverCodes()
						.getCovSubTypeCode())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getCoverCodes().getCovSubTypeCode()));
		}
		/*
		 * else{ responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,
		 * ZERO ) ); }
		 */
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covTypeCode'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].coverCodes.covTypeCode'");
		responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
				coverInPackage.getCoverCodes().getCovTypeCode()));
		if (!Utils.isEmpty(coverInPackage.getCoverCodes())
				&& !Utils.isEmpty(coverInPackage.getCoverCodes()
						.getCovTypeCode())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getCoverCodes().getCovTypeCode()));
		}
		/*
		 * else{ responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,
		 * ZERO ) ); }
		 */
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskCode'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskCode'");
		if (!Utils.isEmpty(coverInPackage.getCoverCodes())
				&& !Utils.isEmpty(coverInPackage.getCoverCodes()
						.getCovSubTypeCode())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getRiskCode()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.basicRskCode'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.basicRskCode'");
		if (!Utils.isEmpty(coverInPackage.getRiskCodes())
				&& !Utils.isEmpty(coverInPackage.getRiskCodes()
						.getBasicRskCode())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getBasicRskCode()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskCat'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskCat'");
		if (!Utils.isEmpty(coverInPackage.getRiskCodes())
				&& !Utils.isEmpty(coverInPackage.getRiskCodes().getRiskCat())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getRiskCat()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskType'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.riskType'");
		if (!Utils.isEmpty(coverInPackage.getRiskCodes())
				&& !Utils.isEmpty(coverInPackage.getRiskCodes().getRiskType())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getRiskType()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.basicRskId'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.basicRskId'");
		if (!Utils.isEmpty(coverInPackage.getRiskCodes())
				&& !Utils
						.isEmpty(coverInPackage.getRiskCodes().getBasicRskId())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getBasicRskId()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.rskId'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].riskCodes.rskId'");
		if (!Utils.isEmpty(coverInPackage.getRiskCodes())
				&& !Utils.isEmpty(coverInPackage.getRiskCodes().getRskId())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getRiskCodes().getRskId()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		responseString.append(com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append(String.format(ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN));
		responseString.append(com.Constant.CONST_NAME_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].vsd'");
		responseString.append(com.Constant.CONST_ID_TRAVELPACKAGELIST_END
				+ String.valueOf(packageCount) + com.Constant.CONST_COVERS
				+ String.valueOf(coverCount) + "].vsd'");
		if (!Utils.isEmpty(coverInPackage.getVsd())) {
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_VALUE,
					coverInPackage.getVsd()));
		}
		responseString.append(com.Constant.CONST_START_INPUT_END);

		/* End : Hidden fields for save. */
	}

	@Override
	public void buildEmptyControl(JspWriter out) throws IOException {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

}
