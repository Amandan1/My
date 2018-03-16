package com.myhope.service.base;

import com.myhope.model.base.TMetadata;
import com.myhope.model.base.TMetadataDetail;
import com.myhope.service.BaseServiceI;

/**
 * 
 * @author YangMing
 * 
 */
public interface MetadataServiceI extends BaseServiceI<TMetadata> {
	/**
	 * 根据name查找Metadata
	 */
	public TMetadata findMetadataByName(String name);
	
	public TMetadataDetail findDetailByKey(String name, String key);
	
}
