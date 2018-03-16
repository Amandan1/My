package com.myhope.service.base.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhope.dao.base.BaseDaoI;
import com.myhope.model.base.TMetadata;
import com.myhope.model.base.TMetadataDetail;
import com.myhope.service.base.MetadataServiceI;
import com.myhope.service.impl.BaseServiceImpl;

/**
 * 
 * @author YangMing
 * 
 */
@Service
public class MetadataServiceImpl extends BaseServiceImpl<TMetadata> implements MetadataServiceI {

	@Autowired
	private BaseDaoI<TMetadataDetail> metadataDetailDao;

	@Override
	public TMetadata findMetadataByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);

		return getByHql("FROM TMetadata metadata WHERE name = :name", params);
	}

	@Override
	public TMetadataDetail findDetailByKey(String name, String key) {
		TMetadata metadata = findMetadataByName(name);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", key);
		params.put("metadata", metadata);

		return metadataDetailDao.getByHql("FROM TMetadataDetail md WHERE key = :key AND metadata = :metadata", params);
	}

	@Override
	public void update(TMetadata o) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metadata", o);
		executeHql("DELETE FROM TMetadataDetail md WHERE metadata = :metadata", params);
		Set<TMetadataDetail> metadataDetails = o.getMetadataDetails();
		for (TMetadataDetail m : metadataDetails) {
			m.setMetadata(o);
		}
		
		super.saveOrUpdate(o);
	}

 
	

}
