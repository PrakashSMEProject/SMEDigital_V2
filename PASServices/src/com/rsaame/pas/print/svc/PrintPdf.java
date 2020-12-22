package com.rsaame.pas.print.svc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

import com.mindtree.ruc.cmn.utils.Utils;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

/**
 * Converts the PDF content into printable format
 */
public class PrintPdf {

	private PrinterJob pjob = null;
	PrintService ps = null;

	/**
	 * Constructs the print job based on the input stream
	 * 
	 * @param inputStream
	 * @param jobName
	 * @throws IOException
	 * @throws PrinterException
	 */
	public PrintPdf(InputStream inputStream, String jobName, String location) throws IOException, PrinterException {
		byte[] pdfContent = new byte[inputStream.available()];
		inputStream.read(pdfContent, 0, inputStream.available());
		initialize(pdfContent, jobName,location);
	}

	/**
	 * Constructs the print job based on the byte array content
	 * 
	 * @param content
	 * @param jobName
	 * @throws IOException
	 * @throws PrinterException
	 */
	public PrintPdf(byte[] content, String jobName,String location) throws IOException, PrinterException {
		initialize(content, jobName,location);
	}

	/**
	 * Initializes the job
	 * 
	 * @param pdfContent
	 * @param jobName
	 * @throws IOException
	 * @throws PrinterException
	 */
	private void initialize(byte[] pdfContent, String jobName,String location) throws IOException, PrinterException {
		ByteBuffer bb = ByteBuffer.wrap(pdfContent);
		// Create PDF Print Page
		PDFFile pdfFile = new PDFFile(bb);
		PDFPrintPage pages = new PDFPrintPage(pdfFile);
		PrintService[] services = PrinterJob.lookupPrintServices();
		
		if( services != null ){
			for( int i = 0; i < services.length; i++ ){
					if(services[ i ].getName().equalsIgnoreCase( Utils.getSingleValueAppConfig( "PRINTER_LOCATION_"+location ))){
						ps = services[ i ];
						System.out.println(services[ i ].toString());
						break;
					}
			}
		}

	 PrintRequestAttributeSet aset =  new HashPrintRequestAttributeSet();

        aset.add(new Copies(1));
        aset.add(MediaSizeName.ISO_A4);
        aset.add(Sides.ONE_SIDED);
        pjob = PrinterJob.getPrinterJob();
        if(!Utils.isEmpty( ps )){
			//PrintService printService= ps;; 
			pjob.setPrintService(ps); 
        }
		
		PageFormat pf = pjob.getPageFormat(aset);
		pjob.setJobName(jobName);
		Book book = new Book();
		book.append(pages, pf, pdfFile.getNumPages());
		pjob.setPageable(book);
		
		// to remove margins
		/*Paper paper = new Paper();
		paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
		pf.setPaper(paper);*/
	}

	public void print() throws PrinterException {
		//System.out.println(pjob.getPrintService());
		pjob.print() ;
	}
	
}

/**
 * Class that actually converts the PDF file into Printable format
 */
class PDFPrintPage implements Printable {

	private PDFFile file;

	PDFPrintPage(PDFFile file) {
		this.file = file;
	}

	public int print(Graphics g, PageFormat format, int index) throws PrinterException {
		int pagenum = index + 1;
		if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
			Graphics2D g2 = (Graphics2D) g;
			PDFPage page = file.getPage(pagenum);

			// fit the PDFPage into the printing area
			Rectangle imageArea = new Rectangle((int) format.getImageableX(), (int) format.getImageableY(),
					(int) format.getImageableWidth(), (int) format.getImageableHeight());
			g2.translate(0, 0);
			PDFRenderer pgs = new PDFRenderer(page, g2, imageArea, null, null);
			try {
				page.waitForFinish();
				pgs.run();
			} catch (InterruptedException ie) {
				// nothing to do
			}
			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	}
}


