package com.myhope.service.base.impl;

import java.util.HashMap;
import java.util.Map;

import com.myhope.dao.base.BaseDaoI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhope.model.base.TConfig;
import com.myhope.service.base.ConfigServiceI;
import com.myhope.service.impl.BaseServiceImpl;

/**
 * 
 * @author YangMing
 * 
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<TConfig> implements ConfigServiceI {

	@Autowired
	private BaseDaoI<TConfig> configDao;

	@Override
	public String getConfigVal(String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", key);
		TConfig config = getByHql("FROM TConfig t WHERE key=:key", params);

		if (config != null && StringUtils.isNotBlank(config.getVal())) {
			return config.getVal();
		} else {
			return "";
		}

	}

	@Override
	public long updateALock() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", "goods_num");
		TConfig config = getByHql("from TConfig c where c.key = :key",params);
		if(config != null) {
			try {
				TConfig c_lock = configDao.getById(TConfig.class, config.getId(), true);
				c_lock.setVal(String.valueOf(Long.parseLong(config.getVal())+1));
				update(c_lock);
				return Long.parseLong(c_lock.getVal());
			} catch (Exception e) {
				return System.currentTimeMillis();
			}
		}
		return System.currentTimeMillis();
	}

}
