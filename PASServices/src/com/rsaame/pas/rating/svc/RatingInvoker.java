package com.rsaame.pas.rating.svc;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.cts.writeRate.Policy;
import com.cts.writeRate.RaterService_ServiceLocator;
import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.kaizen.framework.model.Message;

public class RatingInvoker implements IRatingInvoker {
	private static RaterService_ServiceLocator raterServiceLocator = new RaterService_ServiceLocator();
	private final static Logger logger = Logger.getLogger(RatingInvoker.class);

	/* (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.IRatingInvoker#getDetailsForPolicy(com.cts.writeRate.Policy[])
	 */
	@Override
	public Policy[] getDetailsForPolicy(Policy[] policy)  throws ErateException{
		Policy[] policyResponseList = (Policy[])null;

		try {
			long startTime = System.currentTimeMillis();

			policyResponseList = raterServiceLocator.getRaterServiceSOAP().ratePolicyList(policy);

			long endTime = System.currentTimeMillis();
			logger.info( " Rating Engine integration Timer: Time taken to execute  getDetailsForPolicy write rate (In MilliSec)::" + 
					(endTime - startTime));

			if (policyResponseList == null) {
				Message message = new Message();
				message.setDescription(com.Constant.CONST_SCHEMA_IS_NOT_CONFIGURED);
				ErateException erateException = new ErateException(com.Constant.CONST_SCHEMA_IS_NOT_CONFIGURED);
				erateException.addMessage(message);

			}
		}
		catch (RemoteException e) {
			Message message = new Message();
			message.setDescription(e.getMessage());
			ErateException erateException = new ErateException("RemoteException occcured with Erater");
			erateException.addMessage(message);
			throw erateException;
		} catch (ServiceException e) {
			Message message = new Message();
			message.setDescription(e.getMessage());
			ErateException erateException = new ErateException("ServiceException occcured in Erater");
			erateException.addMessage(message);
			throw erateException;
		}
		return policyResponseList;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.rating.svc.IRatingInvoker#getPremiumForPolicy(com.cts.writeRate.Policy[])
	 */
	@Override
	public Policy[] getPremiumForPolicy(Policy[] policy)throws ErateException {
		 Policy[] policyResponseList = (Policy[])null;
		     try {
		       long startTime = System.currentTimeMillis();
		      policyResponseList = raterServiceLocator.getRaterServiceSOAP()
		       .ratePolicyList(policy);
		
		      long endTime = System.currentTimeMillis();
		
		     logger.info(" Rating Engine integration Timer: Time taken to execute  getPremiumForPolicy write rate (In MilliSec):: " + 
		          (endTime - startTime));
		
		       if (policyResponseList == null) {
		         Message message = new Message();
		         message.setDescription("No premiums found with given schema");
		        ErateException erateException = new ErateException(
		          com.Constant.CONST_SCHEMA_IS_NOT_CONFIGURED);
		     erateException.addMessage(message);
		      throw erateException;
		 }
		    }
		  catch (RemoteException e) {
		 Message message = new Message();
		   message.setDescription(e.getMessage());
		 ErateException erateException = new ErateException(
		  "RemoteException occcured with Erater");
		    erateException.addMessage(message);
		     throw erateException;
		     } catch (ServiceException e) {
		     Message message = new Message();
		 message.setDescription(e.getMessage());
		 ErateException erateException = new ErateException(
		 "ServiceException occcured in Erater");
		erateException.addMessage(message);
		     throw erateException;
		 }
		    return policyResponseList;
	}



}
