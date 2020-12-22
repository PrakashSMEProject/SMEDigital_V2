package com.rsaame.pas.b2b.ws.batch.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.EplatformWsStaging;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.util.WSDAOUtils;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.WCVO;




public abstract class SBSJob implements Job {
	private static final String DELETE_QUOTE = "DELETE_QUOTE";
	private static final String UPDATE_QUOTE = "UPDATE_QUOTE";
	private static final String CREATE_QUOTE = "CREATE_QUOTE";

	protected PolicyVO formInputPolicyVO(EplatformWsStaging eplatformWsStaging) {
		Blob blob = eplatformWsStaging.getQuoIntrResponseAdd();
		PolicyVO policyVO = null;
		//Sonar Fix to use Try with Resources 
		//ObjectInputStream ois = null;
		try(ObjectInputStream ois=new ObjectInputStream(blob.getBinaryStream())) {
			//ois = new ObjectInputStream(blob.getBinaryStream());
			policyVO = (PolicyVO) ois.readObject();
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		//Sonar Fix to use Try with Resources 
		/*finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return policyVO;
	}

	private PolicyVO setRiskGroupIdAndName(PolicyVO policyVO) {
		List<SectionVO> sectionVOList = null;
		SectionVO sectionVO = null;
		sectionVOList = policyVO.getRiskDetails();
		Iterator<SectionVO> sectionListItr = null;
		List<Object[]> riskDetails = null;
		List<Object[]> coverDetails = null;
		List<String> gprDetails = null;
		List<String> gaccDetails = null;
		List<String> wcRiskDetails = null;
		Long policyId = null;
		Long riskId = null;
		List<String> contentId = null;
		riskDetails = WSDAOUtils.getBuildingId(policyVO);
		String buildingId = null;
		String grpId = null;
		String buildingName = null;
		int riskCode = 0;
		for (Object[] riskDetail : riskDetails) {
			buildingId = ((BigDecimal) riskDetail[0]).toString();
			buildingName = (riskDetail[1]).toString();
		}

		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;

		if (!Utils.isEmpty(sectionVOList)) {
			sectionListItr = sectionVOList.iterator();
			while (sectionListItr.hasNext()) {
				sectionVO = (SectionVO) sectionListItr.next();
				int sectionId = sectionVO.getSectionId().intValue();
				riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
				if (!Utils.isEmpty(riskGroupDetails)) {
					LocationVO locationDetails = null;
					for (Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet()) {
						locationDetails = (LocationVO) riskGroupDetailsEntry.getKey();
						if (!Utils.isEmpty(locationDetails)
								&& !Utils.isEmpty(PolicyUtils.getRiskGroupDetails(locationDetails, sectionVO))) {
							locationDetails.setRiskGroupId(buildingId);
							locationDetails.setRiskGroupName(buildingName);
						}

						policyId = WSDAOUtils.getPolicyIds(policyVO, sectionVO.getClassCode());
						if (policyId != null) {
							sectionVO.setPolicyId((long) policyId);
						}

						if (sectionId == 1) {
							// PAR
							ParVO parVO = null;

							parVO = (ParVO) riskGroupDetailsEntry.getValue();
							if (!Utils.isEmpty(parVO)
									&& !Utils.isEmpty(PolicyUtils.getRiskGroupDetails(locationDetails, sectionVO))) {
								parVO.setBasicRiskId(Long.valueOf(buildingId));

								if (parVO.getCovers().getPropertyCoversDetails().size() > 0) {
									for (int i = 0; i < parVO.getCovers().getPropertyCoversDetails().size(); i++) {
										coverDetails = WSDAOUtils.getCoverDetails(policyVO, sectionVO.getPolicyId(),
												parVO.getCovers().getPropertyCoversDetails().get(i).getRiskType());
										for (Object[] coverDetail : coverDetails) {
											String coverId = coverDetail[2].toString();
											parVO.getCovers().getPropertyCoversDetails().get(i)
													.setCoverId(Long.valueOf(coverId));
											parVO.getCovers().getPropertyCoversDetails().get(i)
													.setBuildingId(Long.valueOf(buildingId));
										}
									}
								}

								if (parVO.getCovers().getPropertyCoversDetails().size() == 0) {
									PropertyRisks propertyRisks = new PropertyRisks();
									java.util.List<PropertyRiskDetails> propertyCoversDetailsList = new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(
											PropertyRiskDetails.class);
									PropertyRiskDetails propertyRiskDetailsContent = new PropertyRiskDetails();
									propertyRiskDetailsContent.setRiskType(999); // as
																					// per
																					// debug
																					// log
																					// in
																					// PAS

									coverDetails = WSDAOUtils.getCoverDetails(policyVO, sectionVO.getPolicyId(),
											propertyRiskDetailsContent.getRiskType());
									if (coverDetails.size() > 0) {
										for (Object[] coverDetail : coverDetails) {
											String coverId = coverDetail[2].toString();
											propertyRiskDetailsContent.setCoverId(Long.valueOf(coverId));
											propertyRiskDetailsContent.setBuildingId(Long.valueOf(buildingId));
											propertyRiskDetailsContent.setDeductibles(0.0); // Only
																													// for
																													// JLT
											propertyRiskDetailsContent.setCover(0.0);
											propertyRiskDetailsContent.setCoverOpted(1);
											propertyCoversDetailsList.add(propertyRiskDetailsContent);
										}
									}

									PropertyRiskDetails propertyRiskDetailsRent = new PropertyRiskDetails();
									propertyRiskDetailsRent.setRiskType(13); // as
																				// per
																				// debug
																				// log
																				// in
																				// PAS
									coverDetails = WSDAOUtils.getCoverDetails(policyVO, sectionVO.getPolicyId(),
											propertyRiskDetailsRent.getRiskType());
									if (coverDetails.size() > 0) {
										for (Object[] coverDetail : coverDetails) {
											String coverId = coverDetail[2].toString();
											propertyRiskDetailsRent.setCoverId(Long.valueOf(coverId));
											propertyRiskDetailsRent.setDeductibles(0.0); // Only
																												// for
																												// JLT
											propertyRiskDetailsRent.setCover(0.0);
											propertyRiskDetailsRent.setCoverOpted(1);

											propertyCoversDetailsList.add(propertyRiskDetailsRent);
										}
									}

									propertyRisks.setPropertyCoversDetails(propertyCoversDetailsList);
									parVO.setCovers(propertyRisks);
									riskGroupDetails.put(locationDetails, parVO);
								}
							}

						}
						if (sectionId == 2) {

							// BI

							BIVO bivo = null;
							bivo = (BIVO) riskGroupDetailsEntry.getValue();
							bivo.setBasicRiskId(Long.valueOf(buildingId));
							bivo.setBiCwsAcwlId(sectionVO.getPolicyId());
							bivo.setBiCwsEGIncomeId(sectionVO.getPolicyId());
							bivo.setBiCwsRentId(sectionVO.getPolicyId());

						}
						if (sectionId == 3) {
							// MB
							contentId = WSDAOUtils.contentId(policyVO, sectionId);

							MBVO mbvo = (MBVO) riskGroupDetailsEntry.getValue();

							if (mbvo.getMachineryDetails().size() > 0) {
								for (int i = 0; i < contentId.size(); i++) {
									mbvo.getMachineryDetails().get(i).getContents()
											.setCoverId(Long.valueOf(String.valueOf(contentId.get(i))));
								}
							}

						}
						if (sectionId == 4) {
							// DOS
							DeteriorationOfStockVO deteriorationOfStockVO = null;
							deteriorationOfStockVO = (DeteriorationOfStockVO) riskGroupDetailsEntry.getValue();
							contentId = WSDAOUtils.contentId(policyVO, sectionId);
							if (deteriorationOfStockVO.getDeteriorationOfStockDetails().size() > 0) {
								for (int i = 0; i < contentId.size(); i++) {
									deteriorationOfStockVO.getDeteriorationOfStockDetails().get(i)
											.setContentId(Long.valueOf(String.valueOf(contentId.get(i))));
								}
							}

						}

						if (sectionId == 5) {
							// EE
							EEVO eeVO = null;
							contentId = WSDAOUtils.contentId(policyVO, sectionId);
							eeVO = (EEVO) riskGroupDetailsEntry.getValue();
							if (eeVO.getEquipmentDtls().size() > 0) {
								for (int i = 0; i < contentId.size(); i++) {
									eeVO.getEquipmentDtls().get(i)
											.setContentId(Long.valueOf(String.valueOf(contentId.get(i))));
								}
							}

						}
						if (sectionId == 6) {// PL
							PublicLiabilityVO publicLiabilityVO = null;
							publicLiabilityVO = (PublicLiabilityVO) riskGroupDetailsEntry.getValue();
							if (!Utils.isEmpty(publicLiabilityVO)
									&& !Utils.isEmpty(PolicyUtils.getRiskGroupDetails(locationDetails, sectionVO))) {
								publicLiabilityVO.setWbdId(Long.valueOf(buildingId));
							}
						}
						if (sectionId == 7) {
							// WC
							WCVO wcVO = (WCVO) riskGroupDetailsEntry.getValue();
							if (wcVO.getEmpTypeDetails().size() > 0) {
								wcRiskDetails = WSDAOUtils.getunNameEMRiskId(policyVO, sectionVO.getPolicyId());
								for (int i = 0; i < wcRiskDetails.size(); i++) {
									wcVO.getEmpTypeDetails().get(i)
											.setRiskId(Long.valueOf(String.valueOf(wcRiskDetails.get(i))));

								}
							}
						}
						if (sectionId == 8) {
							// Money
							MoneyVO moneyVO = (MoneyVO) riskGroupDetailsEntry.getValue();
							riskId = WSDAOUtils.getGacchID(policyVO, sectionVO.getPolicyId());
							moneyVO.setBasicRiskId(riskId);
							gaccDetails = WSDAOUtils.getGacchCahID(policyVO, policyId);
							if (moneyVO.getContentsList().size() > 0) {
								for (int i = 0; i < gaccDetails.size(); i++) {
									moneyVO.getContentsList().get(i)
											.setRiskId(Long.valueOf(String.valueOf(gaccDetails.get(i))));
								}
							}

						}
						if (sectionId == 9) {
							// Fidelity
							gprDetails = null;
							riskCode = 24;
							FidelityVO fidelityVO = (FidelityVO) riskGroupDetailsEntry.getValue();
							if (fidelityVO.getFidelityEmployeeDetails().size() > 0) {
								gprDetails = WSDAOUtils.getGprId(policyVO, sectionVO.getPolicyId(), riskCode);
								for (int i = 0; i < gprDetails.size(); i++) {
									grpId = String.valueOf(gprDetails.get(i));

									String gprId = String.valueOf(gprDetails.get(i));
									fidelityVO.getFidelityEmployeeDetails().get(i)
											.setGprFidelityId(Long.valueOf(gprId));

								}
							}

							gprDetails = null;
							if (fidelityVO.getUnnammedEmployeeDetails().size() > 0) {
								gprDetails = WSDAOUtils.getGupFidelityId(policyVO, sectionVO.getPolicyId(), riskCode);
								for (int i = 0; i < gprDetails.size(); i++) {
									String gprId = String.valueOf(gprDetails.get(i));
									fidelityVO.getUnnammedEmployeeDetails().get(i)
											.setGupFidelityId(Long.valueOf(gprId));
								}
							}

						}
						if (sectionId == 10) {
							// TB
							gprDetails = null;
							riskCode = 27;
							TravelBaggageVO travelBaggageVO = (TravelBaggageVO) riskGroupDetailsEntry.getValue();
							if (travelBaggageVO.getTravellingEmpDets().size() > 0) {
								gprDetails = WSDAOUtils.getGprId(policyVO, sectionVO.getPolicyId(), riskCode);
								for (int i = 0; i < gprDetails.size(); i++) {
									grpId = String.valueOf(gprDetails.get(i));
									travelBaggageVO.getTravellingEmpDets().get(i).setGprId(grpId);
								}

							}

						}

						if (sectionId == 12) {
							// GPAVO
							gprDetails = null;
							riskCode = 28;
							GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) riskGroupDetailsEntry
									.getValue();
							if (groupPersonalAccidentVO.getGpaNammedEmpVO().size() > 0) {
								gprDetails = WSDAOUtils.getGprId(policyVO, sectionVO.getPolicyId(), riskCode);
								for (int i = 0; i < gprDetails.size(); i++) {
									groupPersonalAccidentVO.getGpaNammedEmpVO().get(i)
											.setGprId(String.valueOf(gprDetails.get(i)));
								}
							}
						}
					}
				}
			}

		}

		sectionVO.setRiskGroupDetails(riskGroupDetails);
		policyVO.getRiskDetails().add(sectionVO);
		return policyVO;

	}

	protected EplatformWsStaging batchResponse(EplatformWsStaging eplatformWsStaging, BaseVO baseVO) {
		PolicyVO policyVO = (PolicyVO) baseVO;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(policyVO);
			Blob blob = Hibernate.createBlob(bos.toByteArray());
			eplatformWsStaging.setQuoIntrBatchResponse(blob);
			eplatformWsStaging.setPolStatus(policyVO.getStatus().byteValue());
			if(!(policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_PENDING")))){
			eplatformWsStaging.setBatchStatus(new Integer(1).byteValue());  // setting 1 means batch ran successfully
			}
			// to re-set the policy status in the response json of create quote
			// with the current quotation status -- begin


			         
			
			// to re-set the policy status in the response json of create quote
			// with the current quotation status -- end

		} catch (IOException e) {
			e.printStackTrace();
		}
		return eplatformWsStaging;
	}

	protected JobExecutor createExecutor(String eventName) {
		if (CREATE_QUOTE.equalsIgnoreCase(eventName) || UPDATE_QUOTE.equalsIgnoreCase(eventName)) {
			return new JobExecutor.CreateExecutor();
		}
		if (DELETE_QUOTE.equalsIgnoreCase(eventName)) {
			return new JobExecutor.DeleteExecutor();
		}
		return null;
	}

	protected PolicyVO sectionsToBeDeleted(Map<Long, EplatformWsStaging> stgBucket) {
		return determine(stgBucket, DELETE_QUOTE);
	}

	protected PolicyVO sectionsToBeUpdated(Map<Long, EplatformWsStaging> stgBucket) {
		return determine(stgBucket, UPDATE_QUOTE);
	}

	private PolicyVO determine(Map<Long, EplatformWsStaging> stgBucket, String flow) {
		List<Input> input = new ArrayList<>();
		stgBucket.entrySet().stream().forEach(entry -> {
			long endorsementId = entry.getKey();
			EplatformWsStaging value = entry.getValue();
			PolicyVO policyVO = formInputPolicyVO(value);
			input.add(new Input(endorsementId, policyVO));
		});
		return determineSections(input, flow);
	}

	private PolicyVO determineSections(List<Input> input, String flow) {
		Input previous = null;
		Input current = null;
		/*
		 * Checking the size for pending quote, if pending then 1 record if not then it will come 2 records 
		 */
		if(input.size()>1) {
			previous = input.get(0);
			current = input.get(1);
		}
		else {
			previous = input.get(0);
			current = previous;
		}
		

		List<SectionVO> currentSections = current.getPolicyVO().getRiskDetails().stream().collect(Collectors.toList());
		if (DELETE_QUOTE.equalsIgnoreCase(flow)) {
			previous.getPolicyVO().getRiskDetails().removeAll(currentSections);
		} else {
			//commented for Approarch 1
		//	setRiskGroupIdAndName(current.getPolicyVO());
			
			return current.getPolicyVO();
		}
		return previous.getPolicyVO();
	}

	private static class Input implements Comparable<Input> {
		private long id;
		private PolicyVO policyVO;

		private Input(long id, PolicyVO policyVO) {
			this.id = id;
			this.policyVO = policyVO;
		}

		private PolicyVO getPolicyVO() {
			return policyVO;
		}

		@Override
		public int compareTo(Input o) {
			if (this.id < o.id) {
				return -1;
			}
			if (this.id > o.id) {
				return 1;
			}
			return 0;
		}
	}

	
	protected EplatformWsStaging maxEndorsementRecord(Map<Long, EplatformWsStaging> stgBucket){
			Long maxkey=stgBucket.keySet().stream().max(Long::compareTo).orElse(new Long(0));
			return stgBucket.get(maxkey);		
	}
	
	

	
}
