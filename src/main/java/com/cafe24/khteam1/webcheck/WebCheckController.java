package com.cafe24.khteam1.webcheck;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cafe24.khteam1.book.service.BookService;
import com.cafe24.khteam1.common.common.CommandMap;
import com.cafe24.khteam1.flight.service.FlightService;
import com.cafe24.khteam1.ticket.service.TicketService;
import com.cafe24.khteam1.webcheck.service.WebcheckService;

@Controller
@SessionAttributes("webcheckInfo")
public class WebCheckController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "webcheckService")
	private WebcheckService webcheckService;

	@Resource(name = "bookService")
	private BookService bookService;
	
	@Resource(name = "flightService")
	private FlightService flightService;

	@Resource(name = "ticketService")
	private TicketService ticketService;

	@ModelAttribute("webcheckInfo")
	private Map<String, Object> map() {
		return new HashMap<String, Object>();
	}

	@RequestMapping(value = "/webcheck/checkinList.do") // 체크인 현황(리스트)를 불러오는
	public ModelAndView checkinList(Map<String, Object> commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/webCheck/checkinList");
		List<Map<String, Object>> list = webcheckService.checkinList(commandMap);
		mv.addObject("list", list);
		return mv;
	}

	// 체크인 창(체크인 main form) 띄우는 메소드
	@RequestMapping(value = "/webcheck/openCheckin.do")
	public ModelAndView openCheckin(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/webCheck/openWebCheck");
		return mv;
	}

	@RequestMapping(value = "/webcheck/webCheckStep1.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView webCheckStep1(@ModelAttribute("webcheckInfo") Map<String, Object> map, CommandMap commandMap)
			throws Exception {
		ModelAndView mv = new ModelAndView("/webCheck/webCheckStep1");
		log.debug(commandMap.getMap() + "////////////////step1 commandMap");
		Map<String, Object> book = bookService.bookDetail(commandMap.getMap());
		List<Map<String, Object>> ticket = ticketService.TKlistByBKno(commandMap.getMap());
		map.putAll(bookService.bookDetail(commandMap.getMap()));

		mv.addObject("book", book);
		mv.addObject("ticket", ticket);
		mv.addObject("map", map);
		log.debug(map + "////////////////step1 map");
		return mv;
	}

	@RequestMapping(value = "/webcheck/webCheckStep2.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView webCheckStep2(@ModelAttribute("webcheckInfo") Map<String, Object> map, CommandMap commandMap)
			throws Exception {
		ModelAndView mv = new ModelAndView("/webCheck/webCheckStep2");
		log.debug(commandMap.getMap() + "////////////////step2 commandMap");
		map.putAll(commandMap.getMap());
		
		
		log.debug(map + "////////////////step2 map");
		
		mv.addObject("map", map);
		return mv;
	}
	
	
	@RequestMapping(value = "/webcheck/webCheckStep3.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView webCheckStep3(@ModelAttribute("webcheckInfo") Map<String, Object> map, CommandMap commandMap)
			throws Exception { 
		ModelAndView mv = new ModelAndView("/webCheck/webCheckStep3");
		log.debug(commandMap.getMap() + "////////////////step2 commandMap");
		map.putAll(commandMap.getMap());
		
		Map<String, Object> flight = flightService.flightDetail(map);
		String seat = (String) flight.get("BOOK_SET");
		String seat2 = (String) commandMap.getMap().get("seat");
		String strResult = seat + seat2;
		map.put("result", strResult);
		flightService.seatUpdate(map, null);
		
		
		
		
		log.debug(map + "////////////////step2 map");
		
		mv.addObject("map", map);
		return mv;
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/webcheck/seatCheck.do", method = { RequestMethod.GET, RequestMethod.POST })
	public List<String> seatCheck(@ModelAttribute("webcheckInfo") Map<String, Object> map, CommandMap commandMap)
			throws Exception {
		
		Map<String, Object> flight = flightService.flightDetail(map);
		
		String str = (String) flight.get("BOOK_SET");
		
		log.debug(flight.get("BOOK_SET") + "     sdaffasdfasfasdfdas");
		String arr[] = str.split(",");
		List<String> list = new ArrayList<String>();
		
		log.debug(arr); 
		
		for(String str2 : arr) {
			list.add(str2);
		}
		
		log.debug(map + "////////////////step2 map");
		return list;
	}
	
	
	
	

}
