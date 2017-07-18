package cn.wizzer.modules.models.customer;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

import cn.wizzer.common.base.Model;
import cn.wizzer.modules.models.service.Service_main;
/**
* @author memory
* @time   2017-04-19 22:23:25
*/
@Table("customer_state_config")
public class CustomerStateConfig extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Name
	@Prev(els = {@EL("uuid()")})
	private String id;
	@Column
	private Integer serviceId;
	@One(field="serviceId")
	private Service_main service_main;
	@Column
	private String customerStateCode;
	@Column
	private String serviceStateCode;
	@Column
	private String serviceStateName;
	public Service_main getService_main() {
		return service_main;
	}
	public void setService_main(Service_main service_main) {
		this.service_main = service_main;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public Integer getServiceId()
	{
		return serviceId;
	}
	public void setServiceId(Integer serviceId)
	{
		this.serviceId=serviceId;
	}
	public String getCustomerStateCode()
	{
		return customerStateCode;
	}
	public void setCustomerStateCode(String customerStateCode)
	{
		this.customerStateCode=customerStateCode;
	}
	public String getServiceStateCode()
	{
		return serviceStateCode;
	}
	public void setServiceStateCode(String serviceStateCode)
	{
		this.serviceStateCode=serviceStateCode;
	}
	public String getServiceStateName()
	{
		return serviceStateName;
	}
	public void setServiceStateName(String serviceStateName)
	{
		this.serviceStateName=serviceStateName;
	}

}