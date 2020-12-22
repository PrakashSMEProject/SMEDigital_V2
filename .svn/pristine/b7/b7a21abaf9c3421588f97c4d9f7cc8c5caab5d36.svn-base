package com.rsaame.pas.util;

import java.util.Comparator;
import com.rsaame.pas.vo.bus.LocationVO;

/**
 * 
 * @author Ram
 *
 */
public class LocationVOComparator implements Comparator<LocationVO> {
	
	@Override
	public int compare(LocationVO o1, LocationVO o2) {
		if(null == o1 && null == o2)
			return 0;
		else if (null == o1)
			return -1;
		else if(null == o2)
			return 1;
		
		/* Request ID : 166761
		 * The below replaceAll function used to avoid NumberFormatExceptions thrown when 
		 * new location is added after 10th location as part of JAVA8 HashMap changes
		*/
		if(Long.valueOf(o1.getRiskGroupId().replaceAll("L", "")).longValue() < Long.valueOf(o2.getRiskGroupId().replaceAll("L", "")).longValue())
			return -1;
		if (Long.valueOf(o1.getRiskGroupId().replaceAll("L", "")).longValue() == Long.valueOf(o2.getRiskGroupId().replaceAll("L", "")).longValue())
			return 0;
		else
			return 1;
	}
}
