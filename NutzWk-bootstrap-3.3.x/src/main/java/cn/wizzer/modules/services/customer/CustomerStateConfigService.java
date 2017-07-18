package cn.wizzer.modules.services.customer;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.CustomerStateConfig;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author memory
 * @time 2017-04-19 22:23:25
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerStateConfigService extends Service<CustomerStateConfig> {
		public CustomerStateConfigService(Dao dao) {
				super(dao);
		}
}