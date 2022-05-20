package com.ssafy.vue.mapper;

import java.sql.SQLException;


import com.ssafy.vue.dto.MemberDto;


public interface MemberMapper{

	public int join(MemberDto memberDto) throws SQLException;
	public MemberDto login(MemberDto memberDto) throws SQLException;
	public int update(MemberDto memberDto) throws SQLException;
	public int delete(String userid) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	
}