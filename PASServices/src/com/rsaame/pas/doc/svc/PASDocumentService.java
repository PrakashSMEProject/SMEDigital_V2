package com.rsaame.pas.doc.svc;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.reportgenerator.client.ReportGeneratorLocator;
import com.rsaame.pas.reportgenerator.client.ReportParams;
import com.rsaame.pas.reportgenerator.client.ReportRequest;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.PrintDocVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;

public class PASDocumentService{
	private final static Logger logger = Logger.getLogger( PASDocumentService.class );

	public MailVO createDocument( MailVO mailVO ){
		int filePosition = 0;
		String[] fileNames = mailVO.getFileNames();
		ReportRequest[] reportReqArray = new ReportRequest[ fileNames.length ];
		for( String fileName : fileNames ){

			logger.debug( "PASDocumetServiceClient:fileNam_1" + fileName );
			ReportRequest reportReq = new ReportRequest();
			reportReq.setUsername( "test" );
			reportReq.setPassword( "test" );
			reportReq.setReportFormat( "pdf" );

			HashMap<String, String> docParms = mailVO.getDocParameter();
			ReportParams[] reportParamsArray = new ReportParams[ docParms.size() ];
			logger.debug( "PASDocumetServiceClient:Total#ofreportParams_1" + docParms.size() );

			ReportTemplateSet templateSet = ReportTemplateSet._SBS;

			/*FGB changes to pick the reports templates files */
			if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._FGB_SBS.toString() ) ){

				templateSet = ReportTemplateSet._FGB_SBS;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );

			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._HOME.toString() ) ){
				templateSet = ReportTemplateSet._HOME;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._TRAVEL.toString() ) ){
				templateSet = ReportTemplateSet._TRAVEL;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._SBS.toString() ) ){
				templateSet = ReportTemplateSet._SBS;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else if(!Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( com.Constant.CONST_RENEWALSTATUSREPORTEMAIL )){
				
				reportReq.setReportType(com.Constant.CONST_RENEWALSTATUSREPORTEMAIL );		
			}
			else{
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, ReportTemplateSet.valueOf(docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) ) );
			}

			reportReq.setReportName( fileName );

			int i = 0;
			for( Map.Entry<String, String> entry : docParms.entrySet() ){

				logger.debug( "PASDocumetServiceClient:reportParam No_1" + i );

				ReportParams reportParams = new ReportParams();
				reportParams.setKey( entry.getKey() );
				String value = entry.getValue();
				logger.debug( "PASDocumetServiceClient:reportParams: (Key, Value) -_1" + entry.getKey() + "," + value );

				if( Utils.isEmpty( value ) ){
					mailVO.setDocCreationStatus( com.Constant.CONST_FAILED );
					BusinessException businessExcp = new BusinessException( "mail.error", null, com.Constant.CONST_REPORT_CREATION_INPUT_ERROR );
					throw businessExcp;
				}

				if( reportReq.getReportType().equals( templateSet.getPolicyScheduleTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) 
								|| entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getDebitNoteTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getGrossDebitNoteTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getCreditNotesTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getGrossCreditNoteTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getPolicyReceiptTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getEndorsementScheduleTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_RECEIPT )
								|| entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_SHOWBANKLETTER ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else if( reportReq.getReportType().equals( templateSet.getBankLetterTemplate() )
						&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_RECEIPT )
								|| entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_GROSSCREDITNOTEREPORT )
								|| entry.getKey().equals( com.Constant.CONST_GROSSDEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT ) ) ){
					reportParams.setValue( com.Constant.CONST_FALSE );
				}
				else{
					reportParams.setValue( value );
				}

				reportParamsArray[ i ] = reportParams;
				i++;
			}
			reportReq.setParams( reportParamsArray );

			reportReqArray[ filePosition ] = reportReq;
			filePosition++;

		}

		logger.debug( "PASDocumetServiceClient:Request to Document Servic_1" );

		ReportGeneratorLocator repGenLoc = new ReportGeneratorLocator();
		try{
			String status = repGenLoc.getReportGeneratorHttpSoap11Endpoint().saveReport( reportReqArray );
			logger.debug( "Response from Document Service_1" + status );
			mailVO.setDocCreationStatus( status );
		}
		catch( RemoteException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}
		catch( ServiceException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}

		return mailVO;

	}

	/**
	 * 
	 * @param fileName
	 * @param templateSetType
	 * @return
	 */
	private String getReportTypeFromTemplateSet( String fileName, ReportTemplateSet templateTypeSet ){

		if( fileName.contains( "ProposalForms" ) ){
			return templateTypeSet.getProposalFormTemplate();
		}
		else if( fileName.contains( "PolicySchedules" ) ){
			return templateTypeSet.getPolicyScheduleTemplate();
		}
		else if( fileName.contains( "GrossDebitNotes" ) ){
			return templateTypeSet.getGrossDebitNoteTemplate();
		}
		else if( fileName.contains( "GrossCreditNotes" ) ){
			return templateTypeSet.getGrossCreditNoteTemplate();
		}
		else if( fileName.contains( "DebitNotes" ) ){
			return templateTypeSet.getDebitNoteTemplate();
		}
		else if( fileName.contains( "CreditNotes" ) ){
			return templateTypeSet.getCreditNotesTemplate();
		}
		else if( fileName.contains( "FreeZoneCertificates" ) ){
			return templateTypeSet.getFreeZoneCertificateTemplate();
		}
		else if( fileName.contains( "PolicyReceipts" ) ){
			return templateTypeSet.getPolicyReceiptTemplate();
		}
		else if( fileName.contains( "EndorsementSchedules" ) ){
			return templateTypeSet.getEndorsementScheduleTemplate();
		}
		else if( fileName.contains( "RPTRenewalNoticeForms" ) || fileName.contains( "RPTRenewalNoticePrintForms" ) ){
			return templateTypeSet.getRenewalNoticeTemplate();
		}
		else if( fileName.contains( "BankLetter" ) ){
			return templateTypeSet.getBankLetterTemplate();
		}
		else if( fileName.contains( "RenewalNoticeStatus" ) ){
			return com.Constant.CONST_RENEWALSTATUSREPORTEMAIL;
		}
		else{
			return templateTypeSet.getDefaultTemplate();
		}

	}

	public PrintDocVO createPrintDocument( PrintDocVO printDocVO ){
		//int filePosition = 0;
		String fileName = printDocVO.getFileNames();
		ReportRequest[] reportReqArray = new ReportRequest[ 1 ];

		logger.debug( "PASDocumetServiceClient:fileNam_2" + fileName );
		ReportRequest reportReq = new ReportRequest();
		reportReq.setUsername( "test" );
		reportReq.setPassword( "test" );
		reportReq.setReportFormat( "pdf" );

		/*if( fileName.contains( "ProposalForms" ) ){
			reportReq.setReportType( "ProposalForm" );
		}
		else if( fileName.contains( "PolicySchedules" ) ){
			reportReq.setReportType( com.Constant.CONST_POLICYSCHEDULE );

		}
		else if( fileName.contains( "DebitNotes" ) ){
			reportReq.setReportType( "DebitNote" );
		}
		else if( fileName.contains( "CreditNotes" ) ){
			reportReq.setReportType( "CreditNote" );
		}
		else if( fileName.contains( "FreeZoneCertificates" ) ){
			reportReq.setReportType( "FreeZoneCertificate" );
		}
		else if( fileName.contains( "PolicyReceipts" ) ){
			reportReq.setReportType( "printReceipt" );
		}
		else if( fileName.contains( "EndorsementSchedules" ) ){
			reportReq.setReportType( "EndorsementSchedule" );
		}
		else if( fileName.contains( "RPTRenewalNoticePrintForms" ) ){
			reportReq.setReportType( "RenewalNotice" );
		}

		reportReq.setReportName( fileName );*/

		HashMap<String, String> docParms = printDocVO.getDocParameter();
		ReportParams[] reportParamsArray = new ReportParams[ docParms.size() ];
		logger.debug( "PASDocumetServiceClient:Total#ofreportParams_2" + docParms.size() );

		ReportTemplateSet templateSet = ReportTemplateSet._SBS;

		/*FGB changes to pick the reports templates files */
		if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._FGB_SBS.toString() ) ){

			templateSet = ReportTemplateSet._FGB_SBS;
			reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );

		}
		else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._HOME.toString() ) ){
			templateSet = ReportTemplateSet._HOME;
			reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
		}
		else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._TRAVEL.toString() ) ){
			templateSet = ReportTemplateSet._TRAVEL;
			reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
		}
		else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._SBS.toString() ) ){
			templateSet = ReportTemplateSet._SBS;
			reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
		}
		else{
			reportReq.setReportType( getReportTypeFromTemplateSet( fileName, ReportTemplateSet.valueOf(docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) ) );
		}

		reportReq.setReportName( fileName );

		int i = 0;
		for( Map.Entry<String, String> entry : docParms.entrySet() ){

			logger.debug( "PASDocumetServiceClient:reportParam No_2" + i );

			ReportParams reportParams = new ReportParams();
			reportParams.setKey( entry.getKey() );
			logger.debug( "PASDocumetServiceClient:reportParams:Key" + entry.getKey() );
			String value = entry.getValue();
			logger.debug( "PASDocumetServiceClient:reportParams:Value" + value );
			if( Utils.isEmpty( value ) ){
				printDocVO.setDocCreationStatus( com.Constant.CONST_FAILED );
				BusinessException businessExcp = new BusinessException( "PrintDocument.error", null, com.Constant.CONST_REPORT_CREATION_INPUT_ERROR );
				throw businessExcp;
			}
			if( Utils.isEmpty( reportReq.getReportType() ) ){
				printDocVO.setDocCreationStatus( com.Constant.CONST_FAILED );
				BusinessException businessExcp = new BusinessException( "PrintDocument.error ", null, "Report creation Input Error due to  reportReq.getReportType() is null" );
				throw businessExcp;
			}
			if( reportReq.getReportType().equals( com.Constant.CONST_POLICYSCHEDULE )
					&& ( entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) ) ){
				reportParams.setValue( com.Constant.CONST_FALSE );
			}
			else if( reportReq.getReportType().equals( "DebitNote" )
					&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
							|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) ) ){
				reportParams.setValue( com.Constant.CONST_FALSE );
			}
			else if( reportReq.getReportType().equals( "CreditNote" )
					&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
							|| entry.getKey().equals( com.Constant.CONST_RECEIPT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) ) ){
				reportParams.setValue( com.Constant.CONST_FALSE );
			}
			else if( reportReq.getReportType().equals( "printReceipt" )
					&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_ENDSCHEDULEREPORT )
							|| entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) ) ){
				reportParams.setValue( com.Constant.CONST_FALSE );
			}
			else if( reportReq.getReportType().equals( "EndorsementSchedule" )
					&& ( entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULE ) || entry.getKey().equals( com.Constant.CONST_POLICYSCHEDULECLAUSESUAE ) || entry.getKey().equals( com.Constant.CONST_RECEIPT )
							|| entry.getKey().equals( com.Constant.CONST_CREDITNOTEREPORT ) || entry.getKey().equals( com.Constant.CONST_DEBITNOTEREPORT ) ) ){
				reportParams.setValue( com.Constant.CONST_FALSE );
			}
			else{
				reportParams.setValue( value );
			}

			reportParamsArray[ i ] = reportParams;
			i++;
		}
		reportReq.setParams( reportParamsArray );

		reportReqArray[ 0 ] = reportReq;
		//filePosition++;

		ReportGeneratorLocator repGenLoc = new ReportGeneratorLocator();
		try{
			String status = repGenLoc.getReportGeneratorHttpSoap11Endpoint().saveReport( reportReqArray );
			logger.debug( "Response from Document Service_2" + status );
			printDocVO.setDocCreationStatus( status );
		}
		catch( RemoteException e ){
			printDocVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}
		catch( ServiceException e ){
			printDocVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}

		return printDocVO;

	}

	public Object invokeMethod( String methodName, Object... args ){
		Object returnValue = null;
		if( methodName.equals( "createDocument" ) ){
			returnValue = createDocument( (MailVO) args[ 0 ] );
		}

		if( methodName.equals( "createDocumentForDownload" ) ){
			returnValue = createDocumentForDownload( (MailVO) args[ 0 ] );
		}
		
		if( methodName.equals( "createPrintDocument" ) ){
			returnValue = createPrintDocument( (PrintDocVO) args[ 0 ] );
		}
		else if( methodName.equals( "createPrintDocument" ) ){
			returnValue = createPrintDocument( (PrintDocVO) args[ 0 ] );
		}
		else if (methodName.equals("createRenewalDocument")){
			returnValue = createRenewalDocument(  (MailVO) args[ 0 ]);
		}
		return returnValue;
	}

	private Object createDocumentForDownload( MailVO mailVO ){

		int filePosition = 0;
		String[] fileNames = mailVO.getFileNames();
		ReportRequest[] reportReqArray = new ReportRequest[ fileNames.length ];

		for( String fileName : fileNames ){

			logger.debug( "PASDocumetServiceClient:fileNam_3" + fileName );
			ReportRequest reportReq = new ReportRequest();
			reportReq.setUsername( "test" );
			reportReq.setPassword( "test" );
			reportReq.setReportFormat( "pdf" );

			HashMap<String, String> docParms = mailVO.getDocParameter();
			ReportParams[] reportParamsArray = new ReportParams[ docParms.size() ];
			logger.debug( "PASDocumetServiceClient:Total#ofreportParams_3" + docParms.size() );

			ReportTemplateSet templateSet = ReportTemplateSet._SBS;

			/*FGB changes to pick the reports templates files */
			if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._FGB_SBS.toString() ) ){

				templateSet = ReportTemplateSet._FGB_SBS;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );

			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._HOME.toString() ) ){
				templateSet = ReportTemplateSet._HOME;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._TRAVEL.toString() ) ){
				templateSet = ReportTemplateSet._TRAVEL;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else if( !Utils.isEmpty( docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) && docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ).equals( ReportTemplateSet._SBS.toString() ) ){
				templateSet = ReportTemplateSet._SBS;
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, templateSet ) );
			}
			else{
				reportReq.setReportType( getReportTypeFromTemplateSet( fileName, ReportTemplateSet.valueOf(docParms.get( com.Constant.CONST_REPORTTEMPLATESTYPE ) ) ) );
			}

			//reportReq.setReportName( fileName );

			int i = 0;
			for( Map.Entry<String, String> entry : docParms.entrySet() ){

				logger.debug( "PASDocumetServiceClient:reportParam No_3" + i );

				ReportParams reportParams = new ReportParams();
				reportParams.setKey( entry.getKey() );
				String value = entry.getValue();
				logger.debug( "PASDocumetServiceClient:reportParams: (Key, Value) -_2" + entry.getKey() + "," + value );

				if( Utils.isEmpty( value ) ){
					mailVO.setDocCreationStatus( com.Constant.CONST_FAILED );
					BusinessException businessExcp = new BusinessException( "mail.error", null, com.Constant.CONST_REPORT_CREATION_INPUT_ERROR );
					throw businessExcp;
				}
				reportParams.setValue( value );

				reportParamsArray[ i ] = reportParams;
				i++;
				
			}
			reportReq.setParams( reportParamsArray );

			reportReqArray[ filePosition ] = reportReq;
			filePosition++;

			reportReq.setReportName( fileName.split( "-" )[0] + "-"+ Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_FILE_NAME" ) );
		}

		logger.debug( "PASDocumetServiceClient:Request to Document Servic_2" );

		ReportGeneratorLocator repGenLoc = new ReportGeneratorLocator();
		try{
			String status = repGenLoc.getReportGeneratorHttpSoap11Endpoint().saveReport( reportReqArray );
			logger.debug( "Response from Document Service_3" + status );
			mailVO.setDocCreationStatus( status );
		}
		catch( RemoteException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}
		catch( ServiceException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}

		return mailVO;
	}
	
	public MailVO createRenewalDocument( MailVO mailVO ){
		int filePosition = 0;
		String[] fileNames = mailVO.getFileNames();
		ReportRequest[] reportReqArray = new ReportRequest[ fileNames.length ];
		for( String fileName : fileNames ){

			logger.debug( "PASDocumetServiceClient:fileNam_4" + fileName );
			ReportRequest reportReq = new ReportRequest();
			reportReq.setUsername( "test" );
			reportReq.setPassword( "test" );
			reportReq.setReportFormat( "xls" );

			HashMap<String, String> docParms = mailVO.getDocParameter();
			ReportParams[] reportParamsArray = new ReportParams[ docParms.size() ];
			logger.debug( "PASDocumetServiceClient:Total#ofreportParams_4" + docParms.size() );
				reportReq.setReportType("RenewalStatusReportEmail.rptdesign" );		
			
			reportReq.setReportName( fileName );

			int i = 0;
			for( Map.Entry<String, String> entry : docParms.entrySet() ){

				logger.debug( "PASDocumetServiceClient:reportParam No_4" + i );

				ReportParams reportParams = new ReportParams();
				reportParams.setKey( entry.getKey() );
				String value = entry.getValue();
				logger.debug( "PASDocumetServiceClient:reportParams: (Key, Value) -_3" + entry.getKey() + "," + value );

				
					reportParams.setValue( entry.getValue() );

				reportParamsArray[ i ] = reportParams;
				i++;
			}
			reportReq.setParams( reportParamsArray );

			reportReqArray[ filePosition ] = reportReq;
			filePosition++;

		}

		logger.debug( "PASDocumetServiceClient:Request to Document Servic_3" );

		ReportGeneratorLocator repGenLoc = new ReportGeneratorLocator();
		try{
			String status = repGenLoc.getReportGeneratorHttpSoap11Endpoint().saveReport( reportReqArray );
			logger.debug( "Response from Document Service_4" + status );
			mailVO.setDocCreationStatus( status );
		}
		catch( RemoteException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}
		catch( ServiceException e ){
			mailVO.setDocCreationStatus( com.Constant.CONST_FAILURE );
			e.printStackTrace();
		}

		return mailVO;
	}
}
