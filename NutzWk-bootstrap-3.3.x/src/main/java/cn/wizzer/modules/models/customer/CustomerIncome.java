package cn.wizzer.modules.models.customer;

import cn.wizzer.common.base.Model;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import org.nutz.dao.entity.annotation.Name;
/**
* @author memory
* @time   2017-06-12 15:44:51
*/
@Table("customer_income")
public class CustomerIncome extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Name
	@Prev(els = {@EL("uuid()")})
	private String id;
	@Column
	private Integer serviceId;
	@Column
	private String month;
	@Column
	private String income;
	@Column
	private String accIncome;
	@Column
	private String remark;
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
	public String getMonth()
	{
		return month;
	}
	public void setMonth(String month)
	{
		this.month=month;
	}
	public String getIncome()
	{
		return income;
	}
	public void setIncome(String income)
	{
		this.income=income;
	}
	public String getAccIncome()
	{
		return accIncome;
	}
	public void setAccIncome(String accIncome)
	{
		this.accIncome=accIncome;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark=remark;
	}

}