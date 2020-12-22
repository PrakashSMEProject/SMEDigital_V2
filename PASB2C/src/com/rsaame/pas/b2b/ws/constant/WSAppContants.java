package com.rsaame.pas.b2b.ws.constant;

import com.mindtree.ruc.cmn.utils.Utils;

public class WSAppContants {

	public static final int SECTION_ID_PREMIUM = Integer.valueOf(Utils.getSingleValueAppConfig("PREMIUM_PAGE", "999"));
	public static final Integer BUS_TYPE_NEW_FOR_EXISTING = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_NEW_FOR_EXISTING" ) );
	public static final String DEPLOYED_LOCATION = "DEPLOYED_LOCATION";
	public static final Integer BUS_TYPE_RENEWAL = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_RENEWAL" ) );
	public static final Integer BUS_TYPE_NEW = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_NEW" ) );

	
	
}
