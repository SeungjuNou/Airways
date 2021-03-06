package com.cafe24.khteam1.miles.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cafe24.khteam1.miles.dao.MilesDAO;

@Service("milesService")
public class MilesServiceImpl implements MilesService {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "milesDAO")
	private MilesDAO milesDAO;

	@Override
	public List<Map<String, Object>> milesList(Map<String, Object> map) throws Exception {
		return milesDAO.milesList(map);
	}

	@Override
	public void insertMiles(Map<String, Object> map, HttpServletRequest request) throws Exception {
		milesDAO.insertMiles(map);
		
	}

	@Override
	public void updateMiles(Map<String, Object> map, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> milesManage(Map<String, Object> map) throws Exception {
		return milesDAO.milesManage(map);
	}

	@Override
	public Map<String, Object> nowMile(Map<String, Object> map) throws Exception {
		return milesDAO.nowMile(map);
	}

	@Override
	public void useMile(Map<String, Object> map) throws Exception {
		milesDAO.useMile(map);
	}
	
	@Override
	public void saveMile(Map<String, Object> map) throws Exception {
		milesDAO.saveMile(map);
	}
	
	

}