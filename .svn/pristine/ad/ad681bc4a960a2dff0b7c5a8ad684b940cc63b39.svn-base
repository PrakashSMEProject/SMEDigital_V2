/**
 * 
 */
package com.rsaame.pas.b2b.ws.vo.request;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author M1037404
 *
 */
public class DocumentContent {
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("content")
	private byte[] content;
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("tradeLicenseNo")
	private String tradeLicenseNo;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTradeLicenseNo() {
		return tradeLicenseNo;
	}
	public void setTradeLicenseNo(String tradeLicenseNo) {
		this.tradeLicenseNo = tradeLicenseNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tradeLicenseNo == null) ? 0 : tradeLicenseNo.hashCode());
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
		DocumentContent other = (DocumentContent) obj;
		if (!Arrays.equals(content, other.content))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tradeLicenseNo == null) {
			if (other.tradeLicenseNo != null)
				return false;
		} else if (!tradeLicenseNo.equals(other.tradeLicenseNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DocumentContent [name=" + name + ", content=" + Arrays.toString(content) + ", fileName=" + fileName
				+ ", tradeLicenseNo=" + tradeLicenseNo + "]";
	}
	
	
	
	
}
