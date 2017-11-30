package com.cafe24.khteam1.book.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TicketService {

	//?��공권리스?��
	List<Map<String, Object>> ticketList(Map<String, Object> map) throws Exception;

	//?��?���? ?��공권 리스?��
	List<Map<String, Object>> memberTicketList(Map<String, Object> map) throws Exception;
	
	//?��공권 ?��?��?�� ?��?�� 
	void insertTicket(Map<String, Object> map, HttpServletRequest request) throws Exception;

	//?��공권 ?��?��보기
	Map<String, Object> viewTicket(Map<String, Object> map) throws Exception;
	
	//?��공권 ?��?�� (?��?��몰라..)
	void updateTicket(Map<String, Object> map, HttpServletRequest request) throws Exception;

	//?��공권 취소 (?��?��몰라..)
	void deleteTicket(Map<String, Object> map) throws Exception;
}
