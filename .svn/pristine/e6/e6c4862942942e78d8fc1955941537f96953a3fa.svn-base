package com.rsaame.pas.b2b.ws.util;

public class WSRequestMapperUtils {

	public static int getCityCodeFromName(String cityName) {

		int cityCode = 1; // Assume by default Dubai

		if (cityName != null && cityName.equalsIgnoreCase("")) {

			if (cityName.equalsIgnoreCase("Dubai")) {

				return 1;
			}
			if (cityName.equalsIgnoreCase("Abu Dhabi") || cityName.equalsIgnoreCase("AbuDhabi")) {

				return 2;
			}
			if (cityName.equalsIgnoreCase("Sharjah")) {

				return 3;
			}
			if (cityName.equalsIgnoreCase("Al Ain") || cityName.equalsIgnoreCase("Alain")) {

				return 4;
			}
			if (cityName.equalsIgnoreCase("RAS AL KHAIMAH") || cityName.equalsIgnoreCase("RASALKHAIMAH")
					|| cityName.equalsIgnoreCase("RASAL KHAIMAH") || cityName.equalsIgnoreCase("RAS ALKHAIMAH")) {

				return 5;
			}
			if (cityName.equalsIgnoreCase("FUJAIRAH")) {

				return 6;
			}
			if (cityName.equalsIgnoreCase("UMM AL QUWAIN") || cityName.equalsIgnoreCase("UMMAL QUWAIN")
					|| cityName.equalsIgnoreCase("UMM ALQUWAIN")) {

				return 7;
			}
			if (cityName.equalsIgnoreCase("AJMAN")) {

				return 8;
			}

		}
		return cityCode;

	}
}
