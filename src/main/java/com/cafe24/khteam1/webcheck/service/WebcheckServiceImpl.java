package com.cafe24.khteam1.webcheck.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.cafe24.khteam1.common.common.CommandMap;
import com.cafe24.khteam1.webcheck.dao.WebcheckDAO;

@Service("webcheckService")
public class WebcheckServiceImpl implements WebcheckService{
	Logger log = Logger.getLogger(this.getClass());

	
	@Resource(name= "webcheckDAO")
	private WebcheckDAO webcheckDAO;


	@Override
	public List<Map<String, Object>> checkinList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return webcheckDAO.checkinList(map);
	}

	
	/*BookServiceImpl
	public Map<String, Object> viewBook(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = bookDAO.viewBook(map);
		return resultMap;
	}*/
	
}
