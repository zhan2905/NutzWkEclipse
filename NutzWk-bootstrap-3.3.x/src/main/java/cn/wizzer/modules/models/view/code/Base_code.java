package cn.wizzer.modules.models.view.code;

import org.nutz.dao.entity.annotation.View;

@View("v_base_code")
public class Base_code {

	private String parentCode;
	
	private String parentName;
	
	private String childCode;
	
	private String childName;

	public String getParentCode() {
		return parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public String getChildCode() {
		return childCode;
	}

	public String getChildName() {
		return childName;
	}
}
