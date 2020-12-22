package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo;

import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

public class WebServiceAudit {
	private Integer twa_id;
	private String twa_transaction_ref_no;
	private String twa_transaction_serreq_no;
	private String twa_transaction_req_type;
	private String twa_transaction_res_type;
	private String twa_partner;
	private String twa_user_name;
	private Integer twa_quote_no;
	private Long twa_policy_no;
	private Long twa_policy_id;
	private Long twa_endt_id;
	private Clob twa_request_xml;
	private Clob twa_response_xml;
	private Date twa_created_date;
	private Integer twa_policy_type;
	private Integer twa_class_code;
	private Clob twa_soap_request_xml;
	private Timestamp twa_req_start_time;
	private Timestamp twa_res_end_time;
	private Integer twa_time_diff_in_ms;
	private Clob twa_header_info;
	private String twa_client_ip;

	public Clob getTwa_header_info() {
		return twa_header_info;
	}

	public void setTwa_header_info(Clob twa_header_info) {
		this.twa_header_info = twa_header_info;
	}

	public Integer getTwa_id() {
		return twa_id;
	}

	public void setTwa_id(Integer twa_id) {
		this.twa_id = twa_id;
	}

	public String getTwa_transaction_ref_no() {
		return twa_transaction_ref_no;
	}

	public void setTwa_transaction_ref_no(String twa_transaction_ref_no) {
		this.twa_transaction_ref_no = twa_transaction_ref_no;
	}

	public String getTwa_transaction_serreq_no() {
		return twa_transaction_serreq_no;
	}

	public void setTwa_transaction_serreq_no(String twa_transaction_serreq_no) {
		this.twa_transaction_serreq_no = twa_transaction_serreq_no;
	}

	public String getTwa_transaction_req_type() {
		return twa_transaction_req_type;
	}

	public void setTwa_transaction_req_type(String twa_transaction_req_type) {
		this.twa_transaction_req_type = twa_transaction_req_type;
	}

	public String getTwa_transaction_res_type() {
		return twa_transaction_res_type;
	}

	public void setTwa_transaction_res_type(String twa_transaction_res_type) {
		this.twa_transaction_res_type = twa_transaction_res_type;
	}

	public String getTwa_partner() {
		return twa_partner;
	}

	public void setTwa_partner(String twa_partner) {
		this.twa_partner = twa_partner;
	}

	public String getTwa_user_name() {
		return twa_user_name;
	}

	public void setTwa_user_name(String twa_user_name) {
		this.twa_user_name = twa_user_name;
	}

	public Integer getTwa_quote_no() {
		return twa_quote_no;
	}

	public void setTwa_quote_no(Integer twa_quote_no) {
		this.twa_quote_no = twa_quote_no;
	}

	public Long getTwa_policy_no() {
		return twa_policy_no;
	}

	public void setTwa_policy_no(Long twa_policy_no) {
		this.twa_policy_no = twa_policy_no;
	}

	public Long getTwa_policy_id() {
		return twa_policy_id;
	}

	public void setTwa_policy_id(Long twa_policy_id) {
		this.twa_policy_id = twa_policy_id;
	}

	public Long getTwa_endt_id() {
		return twa_endt_id;
	}

	public void setTwa_endt_id(Long twa_endt_id) {
		this.twa_endt_id = twa_endt_id;
	}


	public Date getTwa_created_date() {
		return twa_created_date;
	}

	public void setTwa_created_date(Date twa_created_date) {
		this.twa_created_date = twa_created_date;
	}

	public Integer getTwa_policy_type() {
		return twa_policy_type;
	}

	public void setTwa_policy_type(Integer twa_policy_type) {
		this.twa_policy_type = twa_policy_type;
	}

	public Integer getTwa_class_code() {
		return twa_class_code;
	}

	public void setTwa_class_code(Integer twa_class_code) {
		this.twa_class_code = twa_class_code;
	}


	public Clob getTwa_request_xml() {
		return twa_request_xml;
	}

	public void setTwa_request_xml(Clob twa_request_xml) {
		this.twa_request_xml = twa_request_xml;
	}

	public Clob getTwa_response_xml() {
		return twa_response_xml;
	}

	public void setTwa_response_xml(Clob twa_response_xml) {
		this.twa_response_xml = twa_response_xml;
	}

	public Clob getTwa_soap_request_xml() {
		return twa_soap_request_xml;
	}

	public void setTwa_soap_request_xml(Clob twa_soap_request_xml) {
		this.twa_soap_request_xml = twa_soap_request_xml;
	}

	public Timestamp getTwa_req_start_time() {
		return twa_req_start_time;
	}

	public void setTwa_req_start_time(Timestamp twa_req_start_time) {
		this.twa_req_start_time = twa_req_start_time;
	}

	public Timestamp getTwa_res_end_time() {
		return twa_res_end_time;
	}

	public void setTwa_res_end_time(Timestamp twa_res_end_time) {
		this.twa_res_end_time = twa_res_end_time;
	}

	public Integer getTwa_time_diff_in_ms() {
		return twa_time_diff_in_ms;
	}

	public void setTwa_time_diff_in_ms(Integer twa_time_diff_in_ms) {
		this.twa_time_diff_in_ms = twa_time_diff_in_ms;
	}

	public String getTwa_client_ip() {
		return twa_client_ip;
	}

	public void setTwa_client_ip(String twa_client_ip) {
		this.twa_client_ip = twa_client_ip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((twa_class_code == null) ? 0 : twa_class_code.hashCode());
		result = prime * result + ((twa_client_ip == null) ? 0 : twa_client_ip.hashCode());
		result = prime * result + ((twa_created_date == null) ? 0 : twa_created_date.hashCode());
		result = prime * result + ((twa_endt_id == null) ? 0 : twa_endt_id.hashCode());
		result = prime * result + ((twa_header_info == null) ? 0 : twa_header_info.hashCode());
		result = prime * result + ((twa_id == null) ? 0 : twa_id.hashCode());
		result = prime * result + ((twa_partner == null) ? 0 : twa_partner.hashCode());
		result = prime * result + ((twa_policy_id == null) ? 0 : twa_policy_id.hashCode());
		result = prime * result + ((twa_policy_no == null) ? 0 : twa_policy_no.hashCode());
		result = prime * result + ((twa_policy_type == null) ? 0 : twa_policy_type.hashCode());
		result = prime * result + ((twa_quote_no == null) ? 0 : twa_quote_no.hashCode());
		result = prime * result + ((twa_req_start_time == null) ? 0 : twa_req_start_time.hashCode());
		result = prime * result + ((twa_request_xml == null) ? 0 : twa_request_xml.hashCode());
		result = prime * result + ((twa_res_end_time == null) ? 0 : twa_res_end_time.hashCode());
		result = prime * result + ((twa_response_xml == null) ? 0 : twa_response_xml.hashCode());
		result = prime * result + ((twa_soap_request_xml == null) ? 0 : twa_soap_request_xml.hashCode());
		result = prime * result + ((twa_time_diff_in_ms == null) ? 0 : twa_time_diff_in_ms.hashCode());
		result = prime * result + ((twa_transaction_ref_no == null) ? 0 : twa_transaction_ref_no.hashCode());
		result = prime * result + ((twa_transaction_req_type == null) ? 0 : twa_transaction_req_type.hashCode());
		result = prime * result + ((twa_transaction_res_type == null) ? 0 : twa_transaction_res_type.hashCode());
		result = prime * result + ((twa_transaction_serreq_no == null) ? 0 : twa_transaction_serreq_no.hashCode());
		result = prime * result + ((twa_user_name == null) ? 0 : twa_user_name.hashCode());
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
		WebServiceAudit other = (WebServiceAudit) obj;
		if (twa_class_code == null) {
			if (other.twa_class_code != null)
				return false;
		} else if (!twa_class_code.equals(other.twa_class_code))
			return false;
		if (twa_client_ip == null) {
			if (other.twa_client_ip != null)
				return false;
		} else if (!twa_client_ip.equals(other.twa_client_ip))
			return false;
		if (twa_created_date == null) {
			if (other.twa_created_date != null)
				return false;
		} else if (!twa_created_date.equals(other.twa_created_date))
			return false;
		if (twa_endt_id == null) {
			if (other.twa_endt_id != null)
				return false;
		} else if (!twa_endt_id.equals(other.twa_endt_id))
			return false;
		if (twa_header_info == null) {
			if (other.twa_header_info != null)
				return false;
		} else if (!twa_header_info.equals(other.twa_header_info))
			return false;
		if (twa_id == null) {
			if (other.twa_id != null)
				return false;
		} else if (!twa_id.equals(other.twa_id))
			return false;
		if (twa_partner == null) {
			if (other.twa_partner != null)
				return false;
		} else if (!twa_partner.equals(other.twa_partner))
			return false;
		if (twa_policy_id == null) {
			if (other.twa_policy_id != null)
				return false;
		} else if (!twa_policy_id.equals(other.twa_policy_id))
			return false;
		if (twa_policy_no == null) {
			if (other.twa_policy_no != null)
				return false;
		} else if (!twa_policy_no.equals(other.twa_policy_no))
			return false;
		if (twa_policy_type == null) {
			if (other.twa_policy_type != null)
				return false;
		} else if (!twa_policy_type.equals(other.twa_policy_type))
			return false;
		if (twa_quote_no == null) {
			if (other.twa_quote_no != null)
				return false;
		} else if (!twa_quote_no.equals(other.twa_quote_no))
			return false;
		if (twa_req_start_time == null) {
			if (other.twa_req_start_time != null)
				return false;
		} else if (!twa_req_start_time.equals(other.twa_req_start_time))
			return false;
		if (twa_request_xml == null) {
			if (other.twa_request_xml != null)
				return false;
		} else if (!twa_request_xml.equals(other.twa_request_xml))
			return false;
		if (twa_res_end_time == null) {
			if (other.twa_res_end_time != null)
				return false;
		} else if (!twa_res_end_time.equals(other.twa_res_end_time))
			return false;
		if (twa_response_xml == null) {
			if (other.twa_response_xml != null)
				return false;
		} else if (!twa_response_xml.equals(other.twa_response_xml))
			return false;
		if (twa_soap_request_xml == null) {
			if (other.twa_soap_request_xml != null)
				return false;
		} else if (!twa_soap_request_xml.equals(other.twa_soap_request_xml))
			return false;
		if (twa_time_diff_in_ms == null) {
			if (other.twa_time_diff_in_ms != null)
				return false;
		} else if (!twa_time_diff_in_ms.equals(other.twa_time_diff_in_ms))
			return false;
		if (twa_transaction_ref_no == null) {
			if (other.twa_transaction_ref_no != null)
				return false;
		} else if (!twa_transaction_ref_no.equals(other.twa_transaction_ref_no))
			return false;
		if (twa_transaction_req_type == null) {
			if (other.twa_transaction_req_type != null)
				return false;
		} else if (!twa_transaction_req_type.equals(other.twa_transaction_req_type))
			return false;
		if (twa_transaction_res_type == null) {
			if (other.twa_transaction_res_type != null)
				return false;
		} else if (!twa_transaction_res_type.equals(other.twa_transaction_res_type))
			return false;
		if (twa_transaction_serreq_no == null) {
			if (other.twa_transaction_serreq_no != null)
				return false;
		} else if (!twa_transaction_serreq_no.equals(other.twa_transaction_serreq_no))
			return false;
		if (twa_user_name == null) {
			if (other.twa_user_name != null)
				return false;
		} else if (!twa_user_name.equals(other.twa_user_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebServiceAudit [twa_id=" + twa_id + ", twa_transaction_ref_no=" + twa_transaction_ref_no
				+ ", twa_transaction_serreq_no=" + twa_transaction_serreq_no + ", twa_transaction_req_type="
				+ twa_transaction_req_type + ", twa_transaction_res_type=" + twa_transaction_res_type + ", twa_partner="
				+ twa_partner + ", twa_user_name=" + twa_user_name + ", twa_quote_no=" + twa_quote_no
				+ ", twa_policy_no=" + twa_policy_no + ", twa_policy_id=" + twa_policy_id + ", twa_endt_id="
				+ twa_endt_id + ", twa_request_xml=" + twa_request_xml + ", twa_response_xml=" + twa_response_xml
				+ ", twa_created_date=" + twa_created_date + ", twa_policy_type=" + twa_policy_type
				+ ", twa_class_code=" + twa_class_code + ", twa_soap_request_xml=" + twa_soap_request_xml
				+ ", twa_req_start_time=" + twa_req_start_time + ", twa_res_end_time=" + twa_res_end_time
				+ ", twa_time_diff_in_ms=" + twa_time_diff_in_ms + ", twa_header_info=" + twa_header_info
				+ ", twa_client_ip=" + twa_client_ip + "]";
	}
	
	



}
