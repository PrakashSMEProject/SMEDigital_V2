package com.rsaame.pas.b2c.lookup.ui;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.lookup.svc.LookUpService;

public abstract class BaseHTMLRenderer implements IHtmlRenderer{
	protected LookUpService getLookUpService(){
		return (LookUpService) Utils.getBean( "lookUpService" );
	}
}
