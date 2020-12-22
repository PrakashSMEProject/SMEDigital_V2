package com.rsaame.pas.endorse.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Query;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.pas.dao.model.TMasPromoDiscCover;
import com.rsaame.pas.dao.model.TMasPromoDiscCoverId;
import com.rsaame.pas.dao.model.TMasPromotionalCode;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.svc.TMasPromoDiscCoverVO;
import com.rsaame.pas.vo.svc.TMasPromotionalCodeVO;

public class PromotionalCdConfigDAO extends BaseDBDAO implements
		IPromoCodeConfigDAO {

	/** Logger instance for server logging */
	private final static Logger logger = Logger
			.getLogger(PromotionalCdConfigDAO.class);

	@Override
	public void savePromotionalCd(BaseVO baseVO) {
		TMasPromotionalCodeVO codeVOHolder = (TMasPromotionalCodeVO) baseVO;

		TMasPromotionalCode tmasPromotionalCode = BeanMapper.map(codeVOHolder,
				com.rsaame.pas.dao.model.TMasPromotionalCode.class);
		tmasPromotionalCode.setProid(codeVOHolder.getProCode());
		savePromotionalCode(tmasPromotionalCode);

	}

	private void savePromotionalCode(TMasPromotionalCode tmasPromoCd) {
			getHibernateTemplate().save(tmasPromoCd);
	}

	@Override
	public void savePromotionalDiscCover(BaseVO baseVO) {
		TMasPromoDiscCoverVO tMasPromoDiscCoverVO = (TMasPromoDiscCoverVO) baseVO;
		int PERCENTAGE = 100; //default val
		BigDecimal ctCode = getCtCode(tMasPromoDiscCoverVO.getPdcSchemeCode(),tMasPromoDiscCoverVO.getPdcCovCode());
		TMasPromoDiscCoverId id = new TMasPromoDiscCoverId();
		TMasPromoDiscCover masPromoDiscCover = new TMasPromoDiscCover();
		id.setPdcProCode(tMasPromoDiscCoverVO.getPdcProCode());
		id.setPdcSchemeCode(tMasPromoDiscCoverVO.getPdcSchemeCode());
		id.setPdcCovCode(tMasPromoDiscCoverVO.getPdcCovCode());
		id.setPdcCtCode(ctCode);
		id.setPdcPerc(BigDecimal.valueOf(PERCENTAGE));
		id.setPdcPreparedDate(new Date());
		masPromoDiscCover.setId(id);
		savePromotionalSchemeCovers(masPromoDiscCover);

	}
	
	/**
	 * @param pdcSchemeCode
	 * @param pdcCovCode
	 * @return
	 */
	private BigDecimal getCtCode(BigDecimal pdcSchemeCode, BigDecimal pdcCovCode) {
		String codeDesc = SvcUtils.getLookUpDescription("PAS_COVERS", "ALL", pdcSchemeCode.toString(), pdcCovCode.intValue());
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_COV_TYPE_CODE );
        query.setParameter( 0, pdcSchemeCode );
        query.setParameter( 1, codeDesc );
		BigDecimal ctCode = new BigDecimal(query.uniqueResult().toString());
		return ctCode;
	}

	@Override
	public void savePromoDiscount(BaseVO baseVO) {
		TMasPromoDiscCoverVO tMasPromoDiscCoverVO = (TMasPromoDiscCoverVO) baseVO;
		
		TMasPromoDiscCoverId id = new TMasPromoDiscCoverId();
		TMasPromoDiscCover masPromoDiscCover = new TMasPromoDiscCover();
		id.setPdcProCode(tMasPromoDiscCoverVO.getPdcProCode());
		id.setPdcSchemeCode(tMasPromoDiscCoverVO.getPdcSchemeCode());
	
		id.setPdcPreparedDate(new Date());
		masPromoDiscCover.setId(id);
		savePromotionalSchemeCovers(masPromoDiscCover);

	}

	private void savePromotionalSchemeCovers(
			TMasPromoDiscCover masPromoDiscCover) {
		logger.debug("PromotionalCdConfigDAO ------> Going to invoke hibernate template to persist the data");
		getHibernateTemplate().save(masPromoDiscCover);

	}

}
