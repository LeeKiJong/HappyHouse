package com.ssafy.vue.service;

import com.ssafy.vue.dto.MemberDto;

public interface MemberService {

	public boolean join(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws Exception;
	public boolean update(MemberDto memberDto) throws Exception;
	public boolean delete(String userid) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	
}
