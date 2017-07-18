package cn.wizzer.modules.models.customer;

import cn.wizzer.common.base.Model;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
/**
* @author memory
* @time   2017-04-11 22:32:49
*/
@Table("customer_white_list")
public class CustomerWhiteList extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Name
	@Prev(els = {@EL("uuid()")})
	private String id;
	@Column
	private String userNo;
	@Column
	private String src;
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
	public String getUserNo()
	{
		return userNo;
	}
	public void setUserNo(String userNo)
	{
		this.userNo=userNo;
	}
	public String getSrc()
	{
		return src;
	}
	public void setSrc(String src)
	{
		this.src=src;
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