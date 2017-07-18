package cn.wizzer.modules.models.service;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import cn.wizzer.common.base.Enum;
import cn.wizzer.common.base.Model;
import cn.wizzer.common.util.StringUtil;
/**
* @author Wizzer.cn
* @time   2017-03-06 23:37:02
*/
@Table("service_main")
public class Service_main extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	@Prev(@SQL("select IFNULL(MAX(id)+1,0) from service_main"))
	private Integer id;
	@Column
	private String service_name;
	@Column
	private String service_type;
	@Column
	private String no_type;
	@Column
	private String charge_type;
	@Column
	private String charge_way;
	@Column
	private Integer charge_value;
	@Column
	private String service_sts;
	@Column
	private String start_date;
	@Column
	private String end_date;
	@Column
	private String company_id;
	@Column
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getService_name()
	{
		return service_name;
	}
	public void setService_name(String service_name)
	{
		this.service_name=service_name;
	}
	public String getService_type()
	{
		return service_type;
	}
	public void setService_type(String service_type)
	{
		this.service_type=service_type;
	}
	public String getNo_type()
	{
		return no_type;
	}
	public void setNo_type(String no_type)
	{
		this.no_type=no_type;
	}
	public String getCharge_type()
	{
		return charge_type;
	}
	public void setCharge_type(String charge_type)
	{
		this.charge_type=charge_type;
	}
	public String getCharge_way()
	{
		return charge_way;
	}
	public void setCharge_way(String charge_way)
	{
		this.charge_way=charge_way;
	}
	public Integer getCharge_value()
	{
		return charge_value;
	}
	public void setCharge_value(Integer charge_value)
	{
		this.charge_value=charge_value;
	}
	public String getService_sts()
	{
		return service_sts;
	}
	public void setService_sts(String service_sts)
	{
		this.service_sts=service_sts;
	}
	public String getStart_date()
	{
		return start_date;
	}
	public void setStart_date(String start_date)
	{
		this.start_date=start_date;
	}
	public String getEnd_date()
	{
		return end_date;
	}
	public void setEnd_date(String end_date)
	{
		if(StringUtil.isEmpty(end_date))
			end_date=null;
		this.end_date=end_date;
	}
	public String getCompany_id()
	{
		return company_id;
	}
	public void setCompany_id(String company_id)
	{
		this.company_id=company_id;
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