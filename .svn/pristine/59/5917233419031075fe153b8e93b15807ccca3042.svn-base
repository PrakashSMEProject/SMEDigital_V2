/**
 * 
 */
package com.rsaame.pas.b2c.cmn.base;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.mindtree.ruc.cmn.utils.LocationHandler;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author Sarath Varier
 * @since Phase 3
 */

@Controller
public class BaseController {

	private static LoginLocation location;
	private static LocationHandler locationHandler;

	/**
	 * To support multiple location changes. Initialize user location once
	 */
	public static void setLocation() {

		locationHandler = (LocationHandler) Utils.getBean("locationHandler");
		location = (LoginLocation) Utils.getBean("location");
		if (location != null && Utils.isEmpty(location.getLocation())) {

			location.setLocation(Utils
					.getSingleValueAppConfig("DEPLOYED_LOCATION"));
			locationHandler.setIsApplicationStarted(Boolean.TRUE);
		}
	}

	@InitBinder
	private void dateBinder(WebDataBinder binder) {

		MultipleDateEditor editor = new MultipleDateEditor(
				"dd-MM-yyyy HH:mm:ss", true);
		binder.registerCustomEditor(Date.class, editor);
	}

	/**
	 * @return
	 */
	public LoginLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 */
	public synchronized static void setLocation(LoginLocation location) {
		BaseController.location = location;
	}

}
