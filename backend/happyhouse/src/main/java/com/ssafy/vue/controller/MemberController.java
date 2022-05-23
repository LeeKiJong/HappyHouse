package com.ssafy.vue.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ssafy.vue.dto.MemberDto;
import com.ssafy.vue.service.JwtServiceImpl;
import com.ssafy.vue.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("MemberController V1")
@RestController
@RequestMapping("/user")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	
	@ApiOperation(value = "회원가입", notes = "회원가입 결과 메세지를 반환한다.")
	@PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody @ApiParam(value = "회원가입 시 필요한 회원정보(memberDto).", required = true) MemberDto memberDto) throws JsonMappingException, JsonProcessingException {
		logger.debug("회원가입 정보 : {}", memberDto.toString());

        try {
        	if (memberService.join(memberDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		logger.debug(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원정보 수정", notes = "회원정보 수정 결과 메세지를 반환한다.")
	@PostMapping("/update/{userid}")
	public ResponseEntity<String> update(
			@PathVariable("userid") @ApiParam(value = "수정할 회원의 아이디.", required = true) String userid,
			@ApiParam(value = "수정 시 필요한 회원정보(memberDto).", required = true) @RequestPart(value = "user") MemberDto memberDto,
		@RequestPart(value = "img", required = false) MultipartFile file) {
		logger.debug("회원정보 수정 : {}", memberDto.toString());
		logger.debug("토큰 id : {}", userid);
		logger.debug("token : {} ", userid);

		if (jwtService.isUsable(userid)) {
			logger.info("사용 가능한 토큰!!!");
			try {
				if (memberService.update(memberDto)) {
					if (!new File("./images/" + memberDto.getFilename()).exists()) {
						FileOutputStream writer = new FileOutputStream("./images/" + memberDto.getFilename());
						writer.write(file.getBytes());
						writer.close();
					} 
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "회원 삭제", notes = "회원정보 삭제 결과 메세지를 반환한다.")
	@GetMapping("/info/delete/{userid}")
	public ResponseEntity<String> delete(
			@PathVariable("userid") @ApiParam(value = "삭제할 회원의 아이디.", required = true) String userid, HttpServletRequest request) {
		logger.debug("회원정보 삭제 : {}", userid);
		logger.debug("token : {} ", request.getHeader("access-token"));
		
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				if (memberService.delete(userid)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			} catch (Exception e) {
				logger.error("정보 삭제 실패 : {}", e);
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
	}
	
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
		logger.debug("회원조회");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				logger.debug("회원조회 : {}", memberDto);
				
				//File file = new File("./images/" + memberDto.getFilename());
				
				String path = "";
				if (!new File("./images/" + memberDto.getFilename()).exists()) {
					path = "./images/default.png";
					memberDto.setFilename("default.png");
				} 
				else {
					path = "./images/" + memberDto.getFilename();	
				}
				FileInputStream reader = new FileInputStream(path);
				byte[] bytes = new byte[reader.available()];
				reader.read(bytes, 0, reader.available());
				reader.close();
				//resultMap.put("img", FileUtils.readFileToByteArray(file));
				resultMap.put("img", bytes);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
