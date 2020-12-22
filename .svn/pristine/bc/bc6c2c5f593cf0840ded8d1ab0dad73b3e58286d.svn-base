package com.rsaame.pas.b2b.ws.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Clob;
import java.util.Date;
import java.util.Enumeration;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant;
import com.rsaame.pas.b2b.ws.mapper.SBSCreateQuoteRequestMapper;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.DefaultSchedulerUser;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

public class WSAppUtils {

	private final static Logger LOGGER = Logger.getLogger(WSAppUtils.class);

	public static UserProfile getWSUserProfileVo(String loginId) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");

		List<TMasUser> userRecs = new ArrayList<TMasUser>();

		List<Object> queryData = new ArrayList<Object>();
		queryData.add(Integer.parseInt(loginId));

		String query = "from TMasUser where userId = ?";

		userRecs = ht.find(query, queryData.toArray());

		TMasUser tMasUser = userRecs.get(0);

		UserProfile userProfile = new UserProfile();
		// not used
		GrantedAuthority[] grantedAuth = new GrantedAuthorityImpl[1];
		grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig("BROKER_USER"));

		int brokerId = 0;
		if (tMasUser.getBrokerId() != null) {
			brokerId = tMasUser.getBrokerId().intValue();
		}

        DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( tMasUser.getUserEName(), tMasUser.getPassword(), true, grantedAuth , 
        		Integer.valueOf(tMasUser.getCountry()), tMasUser.getBranch(), tMasUser.getEmployeeId(),brokerId,
        		0, 0, 0, 0, Integer.valueOf(tMasUser.getStatusId()), tMasUser.getProfile(),
        		tMasUser.getUserId(), tMasUser.getUserAName(), tMasUser.getUserEName(), tMasUser.getUserEmailId(), tMasUser.getUserMobNo() );
        
        System.out.println("\n Default user obj is: " + defaultUser.toString());
        
        userProfile.setRsaUser( defaultUser );
        userProfile.setPassword(tMasUser.getPassword());

		userProfile.setUserId(String.valueOf(tMasUser.getUserId()));

		return userProfile;

	}

	public static String getJsonStringFromObjectPrettyPrint(Object obj) {

		String jsonInStringPrettyPrint = "";
		ObjectMapper objMapper = new ObjectMapper();
		try {
			jsonInStringPrettyPrint = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			// LOGGER.info("jsonInStringPrettyPrint is: ");
			// LOGGER.info(jsonInStringPrettyPrint);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return jsonInStringPrettyPrint;
	}

	public static String getStringFromClob(Clob clb) throws IOException, SQLException {

		{
			if (clb == null)
				return "";

			StringBuffer str = new StringBuffer();
			String strng;

			BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

			while ((strng = bufferRead.readLine()) != null)
				str.append(strng);

			return str.toString();
		}
	}

	public static Object getObjectFromJsonStrin(String input, Class classname) {

		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try {
			
			obj = mapper.readValue(input,classname);
			// CreateSBSQuoteResponse createSBSQuoteResponse = mapper.readValue(input,CreateSBSQuoteResponse.class);
			//System.out.println(createSBSQuoteResponse);
	}
		
		catch(Exception e) {
			e.printStackTrace();
		}

		return obj;

	}

	public static byte[] getByteArrayFromObject(Object source, Class className) {

		// PolicyVO policyVO = (PolicyVO) source;
		byte[] output = "".getBytes();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//Sonar Fix to use Try with Resources 
		//ObjectOutput out = null;
		try(ObjectOutput out = new ObjectOutputStream(bos))		
		{
		  //out = new ObjectOutputStream(bos);   
		  out.writeObject(source);
		  out.flush();
		 output = bos.toByteArray();
		} 
		
		catch(IOException e) {
			
			e.printStackTrace();
		}
		finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}

		return output;
	}

	/**
	 * Method to check if Free zone has to be displayed or not
	 * @param policyNo
	 * @param endtId
	 * @param date
	 * @return
	 */
	public static boolean isFreeZoneToBeShow( Long policyNo, Long polQuotationNo, Long endtId, Date date, Boolean isQuote ){
		boolean isFreeZoneToBeShown = false;
		Long parPolicyId = null;
		Long plPolicyId = null;
		Date parVsd = null;
		Date plVsd = null;
		Long endtIdToProcess = null;

		/* When isQuote is true, the endtId that is passed is of quote whereas we are querying on policy tables for pasResultSet and plResultSet as seen below.
		 * Hence whenever isQuote is true, pass the endtId as zero ( as it will be the initial endtId of the policy ).
		 * */
		if( isQuote ){
			endtIdToProcess = ServiceConstant.INTIAL_POL_ENDT;
		} else {
			endtIdToProcess = endtId;
		}
		
		List<TTrnPolicyQuo> parPolicyIdList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(QueryConstants.FETCH_PAR_POLICY_ID,policyNo,polQuotationNo,endtIdToProcess);
		List<TTrnPolicyQuo> plPolicyIdList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(QueryConstants.FETCH_PL_POLICY_ID,policyNo,polQuotationNo,endtIdToProcess);
		
		
		if( !Utils.isEmpty( parPolicyIdList ) && parPolicyIdList.size() > 0 ){
			parPolicyId = ( (TTrnPolicyQuo) parPolicyIdList.get( 0 ) ).getId().getPolicyId();
			parVsd = ( (TTrnPolicyQuo) parPolicyIdList.get( 0 ) ).getPolValidityStartDate();
		}

		if (!Utils.isEmpty(plPolicyIdList) && plPolicyIdList.size() > 0) {
			plPolicyId = ((TTrnPolicyQuo) plPolicyIdList.get(0)).getId().getPolicyId();
			plVsd = ((TTrnPolicyQuo) plPolicyIdList.get(0)).getPolValidityStartDate();
		}

		if (!Utils.isEmpty(endtIdToProcess)) {
			List<Object> pasResultSet = null;
			List<Object> plResultSet = null;

			if( !Utils.isEmpty( parPolicyId ) && !Utils.isEmpty( parVsd ) ){
				pasResultSet = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.PAR_FREE_ZONE_CERTIFICATE, parPolicyId, parVsd, parVsd );
			}

			if( !Utils.isEmpty( plPolicyId ) && !Utils.isEmpty( plVsd ) ){
				plResultSet = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.PL_FREE_ZONE_CERTIFICATE, plPolicyId, plVsd, plVsd );
			}

			if( ( !Utils.isEmpty( pasResultSet ) && pasResultSet.size() > 0 ) || ( !Utils.isEmpty( plResultSet ) && plResultSet.size() > 0 ) ){
				isFreeZoneToBeShown = true;
			}
		}
		return isFreeZoneToBeShown;
	}
	public static PolicyVO getPolicyDetailsFromPolicyNo(PolicyVO policyVO){
		//CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT quote converted to policy in B2B - Starts
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policyList =null;
		boolean isJLTQuote = false;
		isJLTQuote = WSDAOUtils.checkForJLTQuote(policyVO.getPolicyNo(),policyVO.getPolicyYear().toString());
		String sql = "SELECT * FROM T_TRN_POLICY WHERE POL_POLICY_NO = :policyNo AND POL_POLICY_YEAR = :policyYear AND POL_ISSUE_HOUR = 3 AND POL_STATUS = 1 AND POL_ENDT_ID = 0";
		try{
		if(isJLTQuote){
		SQLQuery query = session.createSQLQuery(sql);	

		query.addEntity(TTrnPolicyQuo.class);
		query.setParameter("policyNo", policyVO.getPolicyNo());
		query.setParameter("policyYear", policyVO.getPolicyYear());
		
		policyList = query.list();
		}
		}
		finally{
			session.close();
		}
		//CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT quote converted to policy in B2B - Starts
		//from TTrnPolicyQuo where polPolicyNo = ? and polPolicyYear = ? and id.polEndtId = 0 and polIssueHour = 3 and polStatus=1 and polPreparedBy = ?
		//policyList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(query,policyVO.getPolicyNo(),policyVO.getPolicyYear(),Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		if(!Utils.isEmpty(policyList) && policyList.size() >0) {
			policyVO.setValidityStartDate(policyList.get(0).getPolValidityStartDate());

			policyVO.setConcatPolicyNo(policyList.get(0).getPolConcPolicyNo());

			policyVO.setPolEffectiveDate(policyList.get(0).getPolEffectiveDate());

			policyVO.setPolExpiryDate(policyList.get(0).getPolExpiryDate());

			policyVO.setPolLinkingId(policyList.get(0).getPolLinkingId());

			policyVO.setBasePolicyId(policyList.get(0).getId().getPolicyId());

			policyVO.setQuoteNo(policyList.get(0).getPolQuotationNo());

			policyVO.setCreated(policyList.get(0).getPolIssueDate());

		}

		else {

			LOGGER.error("Policy No not exists::::" + policyVO.getPolicyNo());

			throw new BusinessException(null, "Policy No not exists");

		}

		return policyVO;

	}

	public static Date getEffectiveDate(Date effDate) {

		Date sysDate = getSystemDateWithoutTime();

		Calendar renQuoteValidDate = Calendar.getInstance();
		renQuoteValidDate.setTime(effDate);
		renQuoteValidDate.add(Calendar.DATE, AppConstants.REN_QUOTE_VALID_DAYS);

		if (sysDate.after(effDate) && sysDate.before(renQuoteValidDate.getTime())) {
			return sysDate;
		}
		return effDate;

	}

	private static Date getSystemDateWithoutTime() {
		Calendar sysDate = Calendar.getInstance();
		sysDate.set(Calendar.HOUR_OF_DAY, 0);
		sysDate.set(Calendar.MINUTE, 0);
		sysDate.set(Calendar.SECOND, 0);
		sysDate.set(Calendar.MILLISECOND, 0);
		return sysDate.getTime();
	}

	public static boolean isLeapYear(Date policyYear) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(policyYear);
		GregorianCalendar greCal = new GregorianCalendar();
		return greCal.isLeapYear(cal.get(cal.YEAR)); // use calender.get()
														// function to get year
														// in YYYY format.
	}

	public static String dateFormatter(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if( !Utils.isEmpty( date ) ){
			return dateFormat.format( date );
		}
		else{
			return "";
		}

	}

	// For Auditing Purpose
	public static Map<String, String> setHeaderInfo(HttpServletRequest request) {
		Map<String, String> headerInfoMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headers = request.getHeaders(headerName);
			String headerValue = "";
			while (headers.hasMoreElements()) {
				headerValue = headers.nextElement();
			}
			headerInfoMap.put(headerName, headerValue);
		}
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		headerInfoMap.put("ipAddress", ipAddress);
		return headerInfoMap;
	}

	public static String decodeToFile(String fileName, byte[] content) throws IOException {
		// SONAR issue fix
		String encodedPDFString = "Failed to upload File";
		try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
			byte[] buf = content;
			fileOutputStream.write(buf);
			LOGGER.debug("File Written Successfully...");
			encodedPDFString = "File Uploaded Successfully";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedPDFString;
	}

	public static String getQuoteExpiryAddedDate(String originalDate) {

		String[] arrOfStr = originalDate.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2]) + 30);

		String newDate = "";
		try {
			newDate = sdf.format(sdf.parse(cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newDate;
	}
}
