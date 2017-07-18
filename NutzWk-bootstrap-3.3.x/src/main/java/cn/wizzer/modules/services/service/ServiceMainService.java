package cn.wizzer.modules.services.service;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.service.Service_main;

/**
 * @author Wizzer.cn
 * @time 2017-03-06 23:37:02
 * 
 */
@IocBean(args = {"refer:dao"})
public class ServiceMainService extends Service<Service_main> {
		public ServiceMainService(Dao dao) {
				super(dao);
		}
}