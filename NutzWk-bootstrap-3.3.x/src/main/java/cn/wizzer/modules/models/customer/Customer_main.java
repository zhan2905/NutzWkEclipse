package cn.wizzer.modules.models.customer;

import cn.wizzer.common.base.Model;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import org.nutz.dao.entity.annotation.Name;
/**
* @author memory
* @time   2017-03-23 22:13:38
*/
@Table("customer_main")
public class Customer_main extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Name
	@Prev(els = {@EL("uuid()")})
	private String id;
	@Column("service_id")
	private Integer serviceId;
	@Column("customer_no")
	private String customerNo;
	@Column("broadband_account")
	private String broadbandAccount;
	@Column("customer_name")
	private String customerName;
	@Column("no_type")
	private String noType;
	@Column("city_code")
	private String cityCode;
	@Column("customer_state")
	private String customerState;
	@Column("service_state")
	private String serviceState;
	@Column("free_days")
	private String freeDays;
	@Column("call_time")
	private String callTime;
	@Column("function_start_date")
	private String functionStartDate;
	@Column("function_end_date")
	private String functionEndDate;
	@Column("function_user")
	private String functionUser;
	@Column("charge_start_date")
	private String chargeStartDate;
	@Column("charge_end_date")
	private String chargeEndDate;
	@Column("charge_user")
	private String chargeUser;
	@Column("import_time")
	private String importTime;
	@Column("sale_way")
	private String saleWay;
	@Column
	private String remark;
	@Column("tracking_number")
	private String trackingNumber;
	@Column
	private String mark;
	@Column("sale_number")
	private String saleNumber;
	@Column("expand_1")
	private String expand1;
	@Column("expand_2")
	private String expand2;
	@Column("expand_3")
	private String expand3;
	@Column("expand_4")
	private String expand4;
	
	private String serviceName;
	//是否黑名单
	@Column
	@Default("0")
	private String blackList;
	//是否白名单
	@Column
	@Default("0")
	private String whiteList;
	//是否屏蔽
	@Column
	@Default("0")
	private String shielding;
	//是否地推
	private String marketingAssistant;
	//更新开始时间
	private String updateStartDate;
	//更新结束时间
	private String updateEndDate;
	public String getUpdateStartDate() {
		return updateStartDate;
	}
	public void setUpdateStartDate(String updateStartDate) {
		this.updateStartDate = updateStartDate;
	}
	public String getUpdateEndDate() {
		return updateEndDate;
	}
	public void setUpdateEndDate(String updateEndDate) {
		this.updateEndDate = updateEndDate;
	}
	public String getMarketingAssistant() {
		return marketingAssistant;
	}
	public void setMarketingAssistant(String marketingAssistant) {
		this.marketingAssistant = marketingAssistant;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getBroadbandAccount() {
		return broadbandAccount;
	}
	public void setBroadbandAccount(String broadbandAccount) {
		this.broadbandAccount = broadbandAccount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getNoType() {
		return noType;
	}
	public void setNoType(String noType) {
		this.noType = noType;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCustomerState() {
		return customerState;
	}
	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}
	public String getServiceState() {
		return serviceState;
	}
	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}
	public String getFreeDays() {
		return freeDays;
	}
	public void setFreeDays(String freeDays) {
		this.freeDays = freeDays;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getFunctionStartDate() {
		return functionStartDate;
	}
	public void setFunctionStartDate(String functionStartDate) {
		this.functionStartDate = functionStartDate;
	}
	public String getFunctionEndDate() {
		return functionEndDate;
	}
	public void setFunctionEndDate(String functionEndDate) {
		this.functionEndDate = functionEndDate;
	}
	public String getFunctionUser() {
		return functionUser;
	}
	public void setFunctionUser(String functionUser) {
		this.functionUser = functionUser;
	}
	public String getChargeStartDate() {
		return chargeStartDate;
	}
	public void setChargeStartDate(String chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}
	public String getChargeUser() {
		return chargeUser;
	}
	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	public String getSaleWay() {
		return saleWay;
	}
	public void setSaleWay(String saleWay) {
		this.saleWay = saleWay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getExpand3() {
		return expand3;
	}
	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
	public String getExpand4() {
		return expand4;
	}
	public void setExpand4(String expand4) {
		this.expand4 = expand4;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getBlackList() {
		return blackList;
	}
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	public String getWhiteList() {
		return whiteList;
	}
	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}
	public String getShielding() {
		return shielding;
	}
	public void setShielding(String shielding) {
		this.shielding = shielding;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}