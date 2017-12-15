package com.cafe24.khteam1.book;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cafe24.khteam1.common.common.CommandMap;
import com.cafe24.khteam1.flight.service.FlightService;
import com.cafe24.khteam1.route.service.RouteService;
import com.cafe24.khteam1.book.service.BookService;
/*import com.cafe24.khteam1.book.service.TicketService;
*/ 
@Controller
@SessionAttributes("flightInfo") 
public class BookController { 
    Logger log = Logger.getLogger(this.getClass());
      
    @Resource(name="bookService")
    private BookService bookService;
    
    /*@Resource(name="ticketService")
    private TicketService ticketService;*/
    
    @Resource(name="routeService")
    private RouteService routeService;

    @Resource(name="flightService")
    private FlightService flightService;

    @ModelAttribute("flightInfo")
    private Map<String, Object> map() {
        return new HashMap<String, Object>();
    }

    //항공 조회 정보(노선, 날짜, 인원) 저장후 페이지 이동
    @RequestMapping(value="/book/flightSearch.do",method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView flightSearch(@ModelAttribute("flightInfo") Map<String, Object> map, CommandMap commandMap) {
        map.putAll(commandMap.getMap());
        log.debug(map);
        return new ModelAndView("/book/selectFlight");
    }


    //ajax
    //항공 조회(왕복)
    @ResponseBody
    @RequestMapping(value="/book/flightList.do")
    public Map<String, Object> flightList(@ModelAttribute("flightInfo") Map<String, Object> map, CommandMap commandMap) throws Exception {
        
    	Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = routeService.selectRouteNo(map); //가는편 노선 조회 
        Map<String, Object> map3 = map;
        
        log.debug(map);
        log.debug(commandMap.getMap() + "................////");
        
        int itiNO = Integer.parseInt(map2.get("ITI_NO").toString());
        itiNO++;
        
        map.put("ITI_NO1", map2.get("ITI_NO"));
        map.put("ITI_NO2", itiNO);
        
        map1.put("DEP_DATE", map.get("DEP_DATE1"));
        map1.put("ITI_NO", map2.get("ITI_NO"));
        log.debug(map1);
        List<Map<String, Object>> list1 = flightService.flightList(map1); //가는 항공편 
        
        map1.put("DEP_DATE", map.get("DEP_DATE2"));
        map1.put("ITI_NO", itiNO);
        List<Map<String, Object>> list2 = flightService.flightList(map1); //오는 항공편 
        Map<String, Object> map4 = routeService.selectRouteDetail(map1); //오는편 노선 조회
       
        if(commandMap.getMap().get("DEP_DATE1") != null) {
            map3.put("DEP_DATE1", commandMap.getMap().get("DEP_DATE1"));
        }
        
        if(commandMap.getMap().get("DEP_DATE2") != null) {
            map3.put("DEP_DATE2", commandMap.getMap().get("DEP_DATE2"));
        }
        
        
        //날짜 버튼 설정(출국, 귀국)
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse((String) map3.get("DEP_DATE1"));
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse((String) map3.get("DEP_DATE2"));
        
        List<String> dateButton1 = dateButton(date1);
        List<String> dateButton2 = dateButton(date2);
        
        log.debug(dateButton1);
        
        //항공편 조회 리스트, 날짜 버튼리스트 담아서 보내기
        Map<String, Object> flightList = new HashMap<String, Object>();
        flightList.put("list1", list1);
        flightList.put("list2", list2);
        flightList.put("route", map2);
        flightList.put("route2", map4);
        flightList.put("date1", dateButton1);
        flightList.put("date2", dateButton2);
        return flightList;
    }
    
    
    //승객의 수만큼 탑승자 정보를 입력 받는 메서드.
    @RequestMapping(value="/book/pgInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView psInfo(@ModelAttribute("flightInfo") Map<String, Object> map, CommandMap commandMap) throws Exception {
		ModelAndView mv =new ModelAndView("/book/pgInfo");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map2.put("ITI_NO", map.get("ITI_NO1"));
		map3.put("ITI_NO", map.get("ITI_NO2"));
		
		map2 = routeService.selectRouteDetail(map2);
		map3 = routeService.selectRouteDetail(map3);
		
		int price1 =  Integer.parseInt(map2.get("PRICE").toString());
		int price2 =  Integer.parseInt(map3.get("PRICE").toString());
		int total_price = price1 + price2;
		List<String> list = new ArrayList<String>();
		int adult = Integer.parseInt((String) (map.get("adult_count")));
		int child = Integer.parseInt((String) (map.get("child_count")));
		int price_sum = (int) ((total_price * adult) + (total_price * child * 0.7));
		
		for(int i=0; i < adult+child; i++) {
			if(i==0) list.add("collapseOne");
			if(i==1) list.add("collapseTwo");
			if(i==2) list.add("collapseThree");
			if(i==3) list.add("collapseFour");
			if(i==4) list.add("collapseFive");
			if(i==5) list.add("collapseSix");
			if(i==6) list.add("collapseSeven");
			if(i==7) list.add("collapseEight");
			if(i==8) list.add("collapseNine");
		}
		
		map.put("people", adult+child);
		map.put("PRICE", toPriceFormat(price_sum));
		map.put("TIME1_1", map2.get("TI_DEP"));
        map.put("TIME1_2", map2.get("TI_ARR"));
		map.put("TIME2_1", map3.get("TI_DEP"));
        map.put("TIME2_2", map3.get("TI_ARR"));
		map.put("DEP_CODE", commandMap.getMap().get("DEP_CODE"));
		map.put("ARR_CODE", commandMap.getMap().get("ARR_CODE"));

		mv.addObject("listSize", list.size());
        mv.addObject("map", map);
        mv.addObject("list", list);
        
        log.debug(map);
        return mv;
    }
    
    //승객의 수만큼 탑승자 정보를 입력 받는 메서드.
    @RequestMapping(value="/book/bookSuccess.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView bookSuccess(@ModelAttribute("flightInfo") Map<String, Object> map, CommandMap commandMap, HttpServletRequest request) throws Exception{
    	ModelAndView mv =new ModelAndView("book/pay");

    	log.debug(map);
    	log.debug(commandMap.getMap() + "commandMap ////////////////////////");
    	
    	Map<String, Object> map2Book = new HashMap<String, Object>();	
    	
    	String sysDate = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
    	int random = (int)(Math.random()*10000);
    	String BookNum = "B"+sysDate+random;
    	
    	String price = (String) (map.get("PRICE"));
    	price = price.replaceAll("\\,", "");
    	
    	String name = ((String) commandMap.getMap().get("fmName1"))+((String) commandMap.getMap().get("lastName1"));
    	String email = (String) commandMap.getMap().get("email");
    	String phone = (String) commandMap.getMap().get("phone");
    	
		String mem_id = "id";
				//(String) request.getSession().getAttribute("ID");
		String day = "MON";
		
		
		map2Book.put("BOOK_NO", BookNum);
    	map2Book.put("COUNT", map.get("people"));
    	map2Book.put("DEP_CODE", map.get("DEP_CODE"));
    	map2Book.put("ARR_CODE", map.get("ARR_CODE"));
    	map2Book.put("PAY", map.get("pay"));
    	map2Book.put("PRICE", price);
    	map2Book.put("NAME", name);
    	map2Book.put("EMAIL", email);
    	map2Book.put("PHONE", phone);
    	map2Book.put("DAY", day);
    	map2Book.put("MEM_NO", mem_id);
    	
    	mv.addObject("map", map2Book);
    	
    	log.debug(map2Book);
    	
    	bookService.insertBook(map2Book);
    	
    	log.debug(commandMap);
    	
        return mv;
    }
    
    /*@RequestMapping(value="/book/createBookNo.do", method = RequestMethod.POST)
    public ModelAndView createBookNo(@ModelAttribute("filghtInfo") CommandMap commandMap, HttpServletRequest request) throws Exception {
    	ModelAndView mv = new ModelAndView("book/pay");
    	log.debug(commandMap);
    	bookService.insertBook(commandMap.getMap(), request);
    	log.debug(commandMap);
    	return mv;
    }*/
    


    //dateButton 리스트 메소드
    public List<String> dateButton(Date date) {
        List<String> dList = new ArrayList<String>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
    
        for(int i=-2; i<3; i++) {
            cal.setTime(date);
            cal.add(Calendar.DATE, i);
            String datefo = df.format(cal.getTime());
            dList.add(datefo);
        }
        
        return dList; 
    }
    
    //int를 가격으로 변환 
    public static String toPriceFormat(int num) {
    	  DecimalFormat df = new DecimalFormat("#,###");
    	  return df.format(num);
    	 }
    
    public static String toNumFormat(int num) {
  	  DecimalFormat df = new DecimalFormat("####");
  	  return df.format(num);
  	 }


} 
 