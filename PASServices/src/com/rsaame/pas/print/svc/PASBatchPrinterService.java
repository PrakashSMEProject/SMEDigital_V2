package com.rsaame.pas.print.svc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;
import com.rsaame.pas.dao.model.TTrnPrintBatchPasId;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.renewals.dao.IRenewalCommonDAO;
import com.rsaame.pas.renewals.dao.IRenewalsDAO;
import com.rsaame.pas.vo.app.PrintDocVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
/**
 * 
 * @author m1019834
 *
 */

public class PASBatchPrinterService {
	
	private final static Logger logger = Logger.getLogger(PASBatchPrinterService.class);

	IRenewalsDAO renewalsDAO;
	
	IRenewalCommonDAO renewalCommonDAO;
	
	private HibernateTemplate hibernateTemplate;
	
	private final static short ZEROVAL = 0;
	
	public void setRenewalsDAO( IRenewalsDAO renewalsDAO ){ 
		this.renewalsDAO = renewalsDAO;
	}	
	
	/**
	 * @param renewalCommonDAO the renewalCommonDAO to set
	 */
	public void setRenewalCommonDAO(IRenewalCommonDAO renewalCommonDAO) {
		this.renewalCommonDAO = renewalCommonDAO;
	}


	public void setHibernateTemplate( HibernateTemplate hibernateTemplate ){
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public HibernateTemplate getHibernateTemplate(){
		return hibernateTemplate;
	}

	public void run() {
	/*boolean executeBatch = false;
	Calendar startTime;
	Calendar endTime ;
	Calendar currTime = Calendar.getInstance(); */  
	logger.debug( ":::::::: Printer Batch execution: Start:::::::::::");
	List<TTrnPrintBatchPas> printBatchPas = null;
	
	/*String[] scheduledTime =  Utils.getMultiValueAppConfig( "PRINT_JOB_SCHEDULE", ";" );
	for( String timeFactor : scheduledTime ){
		startTime = Calendar.getInstance(); 
		String[] timeComp = timeFactor.split( ":" );
		startTime.set(Calendar.HOUR, Integer.parseInt(timeComp[0]));
		startTime.set(Calendar.MINUTE,Integer.parseInt(timeComp[1]));
		startTime.set(Calendar.AM_PM,timeComp[2].equalsIgnoreCase("AM")?0:1);
		endTime = (Calendar) startTime.clone();
		// job can execute any time in the given 15 mins range
		endTime.set(Calendar.MINUTE,startTime.get(Calendar.MINUTE)+15);
		System.out.println(startTime.get(Calendar.HOUR) + ":"+ startTime.get(Calendar.MINUTE)+":"+startTime.get(Calendar.AM_PM));
		System.out.println(endTime.get(Calendar.HOUR) + ":"+ endTime.get(Calendar.MINUTE)+":"+endTime.get(Calendar.AM_PM));
		System.out.println(currTime.get(Calendar.HOUR) + ":"+ currTime.get(Calendar.MINUTE)+":"+currTime.get(Calendar.AM_PM));
		if(currTime.getTime().after(startTime.getTime()) && currTime.getTime().before( endTime.getTime() )){
			System.out.println("execute");
			executeBatch = true;
			break;
		}
		
	} */
	//if(executeBatch){
		try{
			printBatchPas = renewalsDAO.getRenewalBatchPrint();
			
			logger.debug( ":::::::: Query for execution: printBatchPas.size():::::::::::" + printBatchPas.size());
			for(int i=0; i< printBatchPas.size(); ++i){
				logger.debug(":: Calling sharingListForPrint() ::" + printBatchPas.get(i));
					sharingListForPrint(printBatchPas.get(i), i);
				}
			
		}catch(Exception  e) {
	        e.printStackTrace();
		}
	//}
	logger.debug(":: Printer Batch for execution: END ::");
}

private void sharingListForPrint(TTrnPrintBatchPas printBatchPas, int indx ) {
		
	logger.debug(":: Entering sharingListForPrint() ::");
		try{
			
			PrintDocVO printDocVo = new PrintDocVO();
			//Code added for print option Start 
			
			String fileNames=new String();
			printDocVo = new PrintDocVO();
			
			
						
			HashMap <String, String> docParam = new HashMap <String, String>();
			if(printBatchPas.getId().getPolLinkingId() == ZEROVAL){
				if(!Utils.isEmpty(printBatchPas.getRenQuoNo())){
					fileNames = Utils.getSingleValueAppConfig("QUOTE_PRINT_RPTDESIGN_RENEWAL_LOC") + printBatchPas.getRenQuoNo()+com.Constant.CONST_QUOTE_PDF;
				}else{
					
					logger.debug(":: Not able to create Filename ::" + printBatchPas.getRenQuoNo()+"-Quote.pdf");
				}
				
				docParam.put("quoteNo", String.valueOf(printBatchPas.getRenQuoNo()));
				docParam.put("endtId", String.valueOf(printBatchPas.getPolEndtId()));
			}
			else{
				if(!Utils.isEmpty(printBatchPas.getRenQuoNo())){
					fileNames = Utils.getSingleValueAppConfig( "QUOTE_PRINT_DOC_PROPOSAL_LOC")+printBatchPas.getRenQuoNo()+com.Constant.CONST_QUOTE_PDF;
				}else{
					
					logger.debug(":: Not able to create Filename ::" + printBatchPas.getRenQuoNo()+"-Quote.pd_2");
				}
				docParam.put("polLinkingId", String.valueOf(printBatchPas.getId().getPolLinkingId()));
				try{
					if(!Utils.isEmpty(printBatchPas.getPolValidityStartDate())){
						Date validityStartDate = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						validityStartDate =  sdf.parse(printBatchPas.getPolValidityStartDate().toString());
						SimpleDateFormat sdf1 = new SimpleDateFormat( "dd-MMM-yyyy" );
						docParam.put("polValStartDate", sdf1.format(validityStartDate).toString());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				docParam.put("endoresementId", String.valueOf(printBatchPas.getPolEndtId()));
				docParam.put("language", "E");
			}			
			
			
			
			printDocVo.setFileNames(fileNames);
			printDocVo.setDocParameter(docParam);
				
				PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
				PASPrinterService printerDoc = (PASPrinterService) Utils.getBean("printerService");
				
				boolean docSuccess = true;
				try{
					printDocVo=(PrintDocVO)docCreator.invokeMethod("createPrintDocument", printDocVo);
					printDocVo.setLocation( printBatchPas.getPrnLocation() );
					
				} catch(Exception e){
					e.printStackTrace();
					docSuccess = false;
					BusinessException businessExcp=new BusinessException(": Document creation error", null, "User does not have Document");
					throw businessExcp;
				}
				if(docSuccess){
					try{	
						printDocVo=(PrintDocVO)printerDoc.invokeMethod("sendingPrintDoc", printDocVo);
						if(printDocVo.getDocCreationStatus().equals("success")){
							logger.info(":: Updating status after execution of pdf print ::::::" + printDocVo.getDocCreationStatus());
							renewalsDAO.updateBatchPrintStatus(printDocVo);
						}else{
							logger.info(":: Updating status after execution of pdf print ::::::" + printDocVo.getDocCreationStatus());
						}
						
					} catch(Exception e)
					{
						e.printStackTrace();
						docSuccess = false;
						BusinessException businessExcp=new BusinessException(": Document creation error", null, "User does not have Document");
						throw businessExcp;
					}
			}	
						
			//Code added for print option End 
			
		} catch(Exception e){
			logger.error("Error in creating Document :"+e.getMessage());
		}
		
	}
}
