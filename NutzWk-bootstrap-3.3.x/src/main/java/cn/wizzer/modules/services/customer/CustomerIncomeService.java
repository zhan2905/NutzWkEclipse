package cn.wizzer.modules.services.customer;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.CustomerIncome;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author memory
 * @time 2017-06-12 15:44:51
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerIncomeService extends Service<CustomerIncome> {
		public CustomerIncomeService(Dao dao) {
				super(dao);
		}
}