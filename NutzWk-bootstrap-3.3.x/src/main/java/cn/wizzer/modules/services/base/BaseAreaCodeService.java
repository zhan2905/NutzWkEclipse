package cn.wizzer.modules.services.base;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.base.Base_area_code;

/**
 * @author memory
 * @time 2017-03-29 16:50:00
 * 
 */
@IocBean(args = {"refer:dao"})
public class BaseAreaCodeService extends Service<Base_area_code> {
		public BaseAreaCodeService(Dao dao) {
				super(dao);
		}
}