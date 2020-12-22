package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo;

import java.util.HashMap;
import java.util.Map;

public class HeaderInfo {
private Map<String , String> headerInfo = new HashMap<>();

public Map<String, String> getHeaderInfo() {
	return headerInfo;
}

public void setHeaderInfo(Map<String, String> headerInfo) {
	this.headerInfo = headerInfo;
}

@Override
public String toString() {
	return "HeaderInfo [headerInfo=" + headerInfo + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((headerInfo == null) ? 0 : headerInfo.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	HeaderInfo other = (HeaderInfo) obj;
	if (headerInfo == null) {
		if (other.headerInfo != null)
			return false;
	} else if (!headerInfo.equals(other.headerInfo))
		return false;
	return true;
}
}
