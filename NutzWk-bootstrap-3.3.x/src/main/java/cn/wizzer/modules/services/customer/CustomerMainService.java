package cn.wizzer.modules.services.customer;

import java.sql.Connection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.nutz.dao.Cnd;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;

import com.mysql.jdbc.CallableStatement;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.Customer_main;
import cn.wizzer.modules.models.sys.Sys_dict;
import cn.wizzer.modules.models.sys.Sys_user;
import cn.wizzer.modules.services.base.BaseAreaCodeService;
import cn.wizzer.modules.services.service.ServiceMainService;
import cn.wizzer.modules.services.sys.SysUserService;

/**
 * @author memory
 * @time 2017-03-23 22:13:38
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerMainService extends Service<Customer_main> {
	
	@Inject
	BaseAreaCodeService baseAreaCodeService;
	@Inject
	ServiceMainService serviceMainService;
	@Inject
	SysUserService userService;
	
		public CustomerMainService(Dao dao) {
				super(dao);
		}
		public void exportDo(String serviceId, String count, String cityCode) {
			Subject subject = SecurityUtils.getSubject();
            Sys_user user = (Sys_user) subject.getPrincipal();
			this.dao().run(new ConnCallback() {
				@Override
				public void invoke(Connection conn) throws Exception {
					CallableStatement callStmt = null;
					try {
						callStmt = (CallableStatement) conn.prepareCall("{call P_CREATE_TASK_EXPORT(?,?,?,?)}");
						callStmt.setInt(1,Integer.parseInt(count));
						callStmt.setString(2, cityCode);
						callStmt.setString(3, serviceId);
						callStmt.setLong(4, Long.parseLong(user == null ? "":user.getId()));
						callStmt.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						if (null != callStmt) {
							callStmt.close();
						}
					}
				}
			});
		}
		public Customer_main getDetail(String id) {
			NutMap paramsMaps = new NutMap();
			Customer_main customerMain = this.dao().fetch(Customer_main.class,id);
			customerMain.setNoType(getDictName("2", customerMain.getNoType()).getName());
			paramsMaps.put("provinceCode", "1");
			customerMain.setCityCode(baseAreaCodeService.data(paramsMaps,
					Cnd.where("cityCode","=",customerMain.getCityCode())).get(0).getCityName());
			paramsMaps.clear();
			customerMain.setServiceName(serviceMainService.fetch(customerMain.getServiceId()).getService_name());
			Sys_dict dict = getDictName("8", customerMain.getCustomerState());
			customerMain.setCustomerState(null == dict ?"":dict.getName());
			Sys_user user = userService.fetch(customerMain.getFunctionUser());
			customerMain.setFunctionUser(null == user ?"":user.getNickname());
			user = userService.fetch(customerMain.getChargeUser());
			customerMain.setChargeUser(null == user ?"":user.getNickname());
			dict = getDictName("9", customerMain.getSaleWay());
			customerMain.setSaleWay(null == dict ?"":dict.getName());
			return customerMain;
		}
}