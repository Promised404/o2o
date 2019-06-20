package com.it888.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it888.o2o.dao.AreaDao;
import com.it888.o2o.entity.Area;
import com.it888.o2o.service.AreaService;

/**
 * 区域Service
 * @author 邓鹏涛
 *
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	
	@Override
	public List<Area> getAreaList() {
		return areaDao.queryArea();
	}

}
