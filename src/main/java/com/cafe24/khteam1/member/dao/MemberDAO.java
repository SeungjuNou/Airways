package com.cafe24.khteam1.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cafe24.khteam1.common.dao.AbstractDAO;

@Repository
public class MemberDAO extends AbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> memberList(Map<String, Object> map) {
		return (List<Map<String, Object>>) selectList("member.memberList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> memberGradeList(Map<String, Object> map) {
		return (List<Map<String, Object>>) selectList("member.memberGradeList", map);
	}
	
	public void insertMember(Map<String, Object> map) throws Exception {
		insert("member.insertMember", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> loginCheck(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("member.loginCheck", map);
	}

	public String checkId(Map<String, Object> map) throws Exception {
		return (String) selectOne("member.checkId", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> viewMember(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("member.viewMember", map);
	}
	
	public void updateMember(Map<String, Object> map) throws Exception {
		update("member.updateMember", map);
	}
	
	public void deleteMember(Map<String, Object> map) throws Exception {
		delete("member.deleteMember", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMemberList(Map<String, Object> map) {
		return (List<Map<String, Object>>) selectList("member.findMemberList", map);
		}

	public void updateGrade(Map<String, Object> map) throws Exception {
		update("member.updateGrade", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> findId(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("member.findId", map);
	}
	}
