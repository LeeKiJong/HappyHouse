package com.ssafy.vue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.MemberDto;
import com.ssafy.vue.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean join(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null || memberDto.getEmail() ==null || memberDto.getUsername() == null)
			return false;
		return memberMapper.join(memberDto)==1;
	}
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		System.out.println("qwe");
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
			System.out.println("asd");
		return memberMapper.login(memberDto);
	}

	@Override
	public boolean update(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getEmail() ==null || memberDto.getUsername() == null)
			return false;
		return memberMapper.update(memberDto)==1;
	}
	
	@Override
	public boolean delete(String userid) throws Exception {
		if(userid == null )
			return false;
		return memberMapper.delete(userid)==1;
	}
	
	
	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return memberMapper.userInfo(userid);
	}

}
