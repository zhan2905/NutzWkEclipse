package cn.wizzer.modules.models.base;

import cn.wizzer.common.base.Model;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
/**
* @author memory
* @time   2017-03-29 16:50:00
*/
@Table("base_area_code")
public class Base_area_code extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	private String provinceCode;
	@Column
	private String provinceName;
	@Column
	private String cityCode;
	@Column
	private String cityName;
		public String getProvinceCode()
	{
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode)
	{
		this.provinceCode=provinceCode;
	}
	public String getProvinceName()
	{
		return provinceName;
	}
	public void setProvinceName(String provinceName)
	{
		this.provinceName=provinceName;
	}
	public String getCityCode()
	{
		return cityCode;
	}
	public void setCityCode(String cityCode)
	{
		this.cityCode=cityCode;
	}
	public String getCityName()
	{
		return cityName;
	}
	public void setCityName(String cityName)
	{
		this.cityName=cityName;
	}

}