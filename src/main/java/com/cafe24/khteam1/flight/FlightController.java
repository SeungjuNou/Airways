package com.cafe24.khteam1.flight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cafe24.khteam1.common.common.CommandMap;
import com.cafe24.khteam1.common.util.DateTrans;
import com.cafe24.khteam1.flight.service.FlightService;
import com.cafe24.khteam1.route.service.RouteService;

@Controller
@SessionAttributes("info")
public class FlightController {
Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="flightService")
	private FlightService flightService;
	
	@Resource(name="routeService")
    private RouteService routeService;
	
	@Resource(name="dateTrans")
	private DateTrans dateTrans;
	
	@ModelAttribute("info")
	private Map<String, Object> map() {
		return new HashMap<String, Object>();
	}

	
	//flight 등록 페이지 이동
	@RequestMapping(value="/admin/flightRegFrom.do")
	public ModelAndView flightRegFrom() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> rourte = routeService.selectRouteList();
		
		mv.addObject("rourte", rourte);
		mv.setViewName("/admin/adminFlightReg");
		return mv;
	}
	
	//flight 입력
	@RequestMapping(value="/admin/flightReg.do",  method=RequestMethod.POST)
	public ModelAndView flightReg(CommandMap commandMap) throws Exception {
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse((String) commandMap.get("DEP_DATE1"));
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse((String) commandMap.get("DEP_DATE2"));
		
		//날짜 차이 구하기
		int count = dateTrans.DateCount(date1, date2);
		
		//
		for(int i=0; i<=count; i++) {
			commandMap.put("DEP_DATE1", dateTrans.DateAdd(date1, i));//날짜 변경(더하기)
			flightService.insertFlight(commandMap.getMap());
			System.out.println(commandMap.getMap());
		}
		
		return new ModelAndView("redirect:/admin/flightRegFrom.do");
	}
	
	//관리자 FlightList
	@RequestMapping(value="/admin/flightAdminList.do")
	public ModelAndView flightAdminList() throws Exception {
		ModelAndView mv = new ModelAndView();
		
		List<Map<String, Object>> flightList = flightService.flightAllList();
		mv.addObject("flightList", flightList);
		mv.setViewName("/admin/adminFlightList");
		return mv;
	}
}
