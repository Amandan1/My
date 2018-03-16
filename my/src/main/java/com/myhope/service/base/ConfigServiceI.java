package com.myhope.service.base;

import com.myhope.model.base.TConfig;
import com.myhope.service.BaseServiceI;

/**
 * 
 * @author YangMing
 * 
 */
public interface ConfigServiceI extends BaseServiceI<TConfig> {
	/**
	 * 获得系统参数值
	 * 
	 * @param key
	 *            系统参数编码
	 * @return
	 */
	public String getConfigVal(String key);

	public long updateALock();
}
