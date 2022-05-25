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

	
	@ApiOperation(value = "ȸ������", notes = "ȸ������ ��� �޼����� ��ȯ�Ѵ�.")
	@PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody @ApiParam(value = "ȸ������ �� �ʿ��� ȸ������(memberDto).", required = true) MemberDto memberDto) throws JsonMappingException, JsonProcessingException {
		logger.debug("ȸ������ ���� : {}", memberDto.toString());

        try {
        	if (memberService.join(memberDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

	@ApiOperation(value = "�α���", notes = "Access-token�� �α��� ��� �޼����� ��ȯ�Ѵ�.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "�α��� �� �ʿ��� ȸ������(���̵�, ��й�ȣ).", required = true) MemberDto memberDto) {
		logger.debug(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("�α��� ��ū���� : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("�α��� ���� : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "ȸ������ ����", notes = "ȸ������ ���� ��� �޼����� ��ȯ�Ѵ�.")
	@PostMapping("/update/{userid}")
	public ResponseEntity<String> update(
			@PathVariable("userid") @ApiParam(value = "������ ȸ���� ���̵�.", required = true) String userid,
			@ApiParam(value = "���� �� �ʿ��� ȸ������(memberDto).", required = true) @RequestPart(value = "user") MemberDto memberDto,
		@RequestPart(value = "img", required = false) MultipartFile file) {
		logger.debug("ȸ������ ���� : {}", memberDto.toString());
		logger.debug("��ū id : {}", userid);
		logger.debug("token : {} ", userid);

		if (jwtService.isUsable(userid)) {
			logger.info("��� ������ ��ū!!!");
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
				logger.error("������ȸ ���� : {}", e);
			}
		} else {
			logger.error("��� �Ұ��� ��ū!!!");
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "ȸ�� ����", notes = "ȸ������ ���� ��� �޼����� ��ȯ�Ѵ�.")
	@GetMapping("/info/delete/{userid}")
	public ResponseEntity<String> delete(
			@PathVariable("userid") @ApiParam(value = "������ ȸ���� ���̵�.", required = true) String userid, HttpServletRequest request) {
		logger.debug("ȸ������ ���� : {}", userid);
		logger.debug("token : {} ", request.getHeader("access-token"));
		
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("��� ������ ��ū!!!");
			try {
				if (memberService.delete(userid)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
			} catch (Exception e) {
				logger.error("���� ���� ���� : {}", e);
			}
		} else {
			logger.error("��� �Ұ��� ��ū!!!");
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
	}
	
	@ApiOperation(value = "ȸ������", notes = "ȸ�� ������ ���� Token�� ��ȯ�Ѵ�.", response = Map.class)
	@GetMapping("/info/idcheck/{userid}")
	public ResponseEntity<String> getInfo(
			@PathVariable("userid") @ApiParam(value = "������ ȸ���� ���̵�.", required = true) String userid)
			throws Exception {

		MemberDto memberDto = memberService.userInfo(userid);
		System.out.println(userid);
		if (memberDto == null) {
			System.out.println("성공");
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} else {
			System.out.println("실패");
			return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/info/pwdcheck")
	public ResponseEntity<Map<String, Object>> getPwd(@RequestBody MemberDto memberDto)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(memberDto.getUserid() + ", " + memberDto.getEmail());
		MemberDto temp = memberService.userInfo(memberDto.getUserid());
		if (memberDto == null) {
			System.out.println("실패");
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} else {
			System.out.println(temp.getUserid() + ", " + temp.getEmail());
			if (temp.getEmail().equals(memberDto.getEmail())) {
				System.out.println("성공");
				resultMap.put("userpwd", temp.getUserpwd());
				resultMap.put("message", SUCCESS);
			} else {
				System.out.println("실패");
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "ȸ������", notes = "ȸ�� ������ ���� Token�� ��ȯ�Ѵ�.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "������ ȸ���� ���̵�.", required = true) String userid,
			HttpServletRequest request) {
		logger.debug("ȸ����ȸ");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("��� ������ ��ū!!!");
			try {
//				�α��� ����� ����.
				MemberDto memberDto = memberService.userInfo(userid);
				logger.debug("ȸ����ȸ : {}", memberDto);
				
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
				logger.error("������ȸ ���� : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("��� �Ұ��� ��ū!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
