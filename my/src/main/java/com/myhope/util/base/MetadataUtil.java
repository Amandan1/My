package com.myhope.util.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myhope.model.base.TMetadata;
import com.myhope.model.base.TMetadataDetail;
import com.myhope.service.base.MetadataServiceI;

/**
 * Metadata工具类
 * 
 * @author YangMing
 * 
 */
@Component
public class MetadataUtil {

	private static MetadataUtil metadataUtil;

	@Autowired
	private MetadataServiceI metadataService;

	public void setMetadataService(MetadataServiceI metadataService) {
		this.metadataService = metadataService;
	}

	@PostConstruct
	public void init() {
		metadataUtil = this;
		metadataUtil.metadataService = this.metadataService;
	}

	/**
	 * 根据name查找Metadata
	 * 
	 * @param name
	 *            Metadata名
	 * @return
	 */
	public static TMetadata findByName(String name) {
		return metadataUtil.metadataService.findMetadataByName(name);
	}

	/**
	 * 翻译Metadata
	 * 
	 * @param name
	 *            Metadata名
	 * @param key
	 *            键值
	 * @return
	 */
	public static TMetadataDetail findDetailByKey(String name, String key) {
		return metadataUtil.metadataService.findDetailByKey(name, key);
	}

}
