package cn.wizzer.modules.controllers.platform.customer;

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import cn.wizzer.modules.models.customer.CustomerIncome;
import cn.wizzer.modules.services.customer.CustomerIncomeService;

/**
 * @author memory
 * @time 2017-06-12 15:44:51
 * 
 */
@IocBean
@At("/platform/customer/customerincome")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerIncomeController {
private static final Log log = Logs.get();
	@Inject
	CustomerIncomeService customerIncomeService;

	@At("")
	@Ok("beetl:/platform/customer/income/index.html")
	@RequiresAuthentication
	public void index() {
		
	}
	
	@At
	@Ok("beetl:/platform/customer/income/add.html")
	@RequiresAuthentication
	public void add() {
	
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.customer.add")
	@SLog(tag = "Add", msg = "Add:customerIncome")
	public Object addDo(@Param("..") CustomerIncome customerIncome, HttpServletRequest req) {
		try {
			customerIncomeService.insert(customerIncome);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/income/edit.html")
	@RequiresAuthentication
	public Object edit(String id) {
		return customerIncomeService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.customer.edit")
	@SLog(tag = "Edit", msg = "Edit:customerIncome")
	public Object editDo(@Param("..") CustomerIncome customerIncome, HttpServletRequest req) {
		try {
			customerIncomeService.updateIgnoreNull(customerIncome);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("platform.customer.delete")
	@SLog(tag = "Delete", msg = "Delete:customerIncome")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerIncomeService.delete(ids);
			}else{
				customerIncomeService.delete(id);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At
	@Ok("json:full")
	@RequiresAuthentication
	public Object data(@Param("length") int length, @Param("start") int start, @Param("draw") int draw, @Param("::order") List<DataTableOrder> order, @Param("::columns") List<DataTableColumn> columns) {
		Cnd cnd = Cnd.NEW();
		return customerIncomeService.data(length, start, draw, order, columns, cnd, null);
	}

}