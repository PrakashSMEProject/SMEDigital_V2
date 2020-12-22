package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeOptionalCoversResponse;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.TLLimit;
import com.rsaame.pas.dao.model.TMasPolicyRating;

public class RetrieveHomeOptionalCoversResponseMapper implements BaseResponseVOMapper {

	private final static Logger LOGGER = Logger.getLogger(RetrieveHomeOptionalCoversResponseMapper.class);

	@Override
	public void mapVOToResponse(Object valueObj, Object requestObj) throws Exception {
		LOGGER.debug("entered inside mapVOToResponse() : ");
		if (valueObj instanceof List<?>) {
			for (Object object : (List<?>) valueObj) {
				if (object instanceof TMasPolicyRating && requestObj instanceof RetrieveHomeOptionalCoversResponse) {
					RetrieveHomeOptionalCoversResponse retrieveHomeOptionalCoversResponse = (RetrieveHomeOptionalCoversResponse) requestObj;
					List<TMasPolicyRating> homeOptionalCoverList = (List<TMasPolicyRating>) valueObj;
					List<OptionalCovers> optionalCoversList = new ArrayList<OptionalCovers>();
					List<TLLimit> tllLimitList = new ArrayList<TLLimit>();
					TLLimit tenants = null;
					OptionalCovers optionalCovers = null;
					int count = 1;
					for (TMasPolicyRating homeCover : homeOptionalCoverList) {
						if (homeCover.getId().getPrCtCode() == 0) {
							optionalCovers = new OptionalCovers();
						}

						if (homeCover.getId().getPrCovCode() == 2 || homeCover.getId().getPrCovCode() == 3) {
							if (homeCover.getId().getPrCovCode() == 2) {
								optionalCovers.setCoverDesc(AppConstants.LOD);
							} else if (homeCover.getId().getPrCovCode() == 3) {
								optionalCovers.setCoverDesc(AppConstants.DSC);
							}
							optionalCovers.setPremium(homeCover.getPrPremRate());
							optionalCovers.setCoverIncluded(homeCover.getPrMandatoryInd());
							optionalCovers.setCoverageLimit(null); // homeCover.getPrLimit()
							optionalCovers.setCoverMappingCode(homeCover.getId().getPrCovCode() + "-"
									+ homeCover.getId().getPrCtCode() + "-" + homeCover.getId().getPrCstCode());
							if (Utils.isEmpty(optionalCovers.getRiskDetails())) {
								optionalCovers.setRiskDetails(new RiskDetails());
							}
							optionalCovers.getRiskDetails().setRskId(0);
							optionalCovers.getRiskDetails().setBasicRskId(0);
						}
						if (homeCover.getId().getPrCovCode() == 4) {
							if (homeCover.getId().getPrCtCode() == 0) {
								optionalCovers.setCoverDesc(AppConstants.ATLC);
								//optionalCovers.setPremium(homeCover.getPrPremRate());
								optionalCovers.setCoverIncluded(homeCover.getPrMandatoryInd());
								optionalCovers.setCoverageLimit(null); // homeCover.getPrLimit()
								optionalCovers.setCoverMappingCode(homeCover.getId().getPrCovCode() + "-"
										+ homeCover.getId().getPrCtCode() + "-" + homeCover.getId().getPrCstCode());
								if (Utils.isEmpty(optionalCovers.getRiskDetails())) {
									optionalCovers.setRiskDetails(new RiskDetails());
								}
								optionalCovers.getRiskDetails().setRskId(0);
								optionalCovers.getRiskDetails().setBasicRskId(0);
							} else if (homeCover.getId().getPrCtCode() == 9) {
								tenants = new TLLimit();
								String limit = "Limit";
								tenants.setCoverDesc(limit + count);
								tenants.setSelected(homeCover.getPrMandatoryInd());
								if(homeCover.getPrMandatoryInd()) { optionalCovers.setCoverIncluded(true); }
								tenants.setCoverageLimit(homeCover.getPrLimit());
								tenants.setPremium(homeCover.getPrPremRate());
								tllLimitList.add(tenants);
								optionalCovers.setTllLimit(tllLimitList);
								count++;
							}
						}
						if (homeCover.getId().getPrCtCode() == 0) {
							optionalCoversList.add(optionalCovers);
						}
					}
					retrieveHomeOptionalCoversResponse.setOptionalCovers(optionalCoversList);
					if(!Utils.isEmpty(retrieveHomeOptionalCoversResponse)) {
						retrieveHomeOptionalCoversResponse.setStatus(Boolean.TRUE);
						if(retrieveHomeOptionalCoversResponse.getStatus()) {
							retrieveHomeOptionalCoversResponse.setMessage("Success");
						}
					}
				}
			}
		}
		LOGGER.debug("exiting form mapVOToResponse() : RetrieveHomeOptionalCoversResponseMapper");
	}

}
