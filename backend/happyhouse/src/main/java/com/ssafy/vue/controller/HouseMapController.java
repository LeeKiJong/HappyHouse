package com.ssafy.vue.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.MapInfoDto;
import com.ssafy.vue.dto.MemberDto;
import com.ssafy.vue.dto.SidoGugunCodeDto;
import com.ssafy.vue.service.HouseMapService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
//@CrossOrigin("*")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		//logger.debug("sido : {}", haHouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		//logger.debug("gugun : {}", haHouseMapService.getGugunInSido(sido));
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		//logger.debug("dong : {}", haHouseMapService.getDongInGugun(gugun));
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/apt/gugun")
	public ResponseEntity<List<HouseInfoDto>> apt_gugun(@RequestParam("gugun") String gugun) throws Exception {
		//logger.debug("apt : {}", haHouseMapService.getAptInGugun(gugun));

		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/apt/dong")
	public ResponseEntity<List<HouseInfoDto>> apt_dong(@RequestParam("dong") String dong) throws Exception {
		//logger.debug("apt : {}", haHouseMapService.getAptInDong(dong));

		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	@GetMapping("/apt/aptName")
	public ResponseEntity<List<HouseInfoDto>> apt_aptName(@RequestParam("aptName") String aptName, @RequestParam("dong") String dong) throws Exception {
		//logger.debug(aptName);
		//logger.debug(gugun);
		//logger.debug(dong);
		//logger.debug("name : {}", haHouseMapService.getAptInName(aptName, gugun, dong));
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInName(aptName, dong), HttpStatus.OK);

	}
	@PostMapping("/apt/map")
	public ResponseEntity<List<HouseInfoDto>> apt_mapInfo(@RequestBody MapInfoDto mapInfoDto) throws Exception {
		logger.debug(mapInfoDto.toString());
		//logger.debug("apt : {}", haHouseMapService.getAptInMap(mapInfoDto));
		
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInMap(mapInfoDto), HttpStatus.OK);
	}
	@GetMapping("/apt/deal")
	public ResponseEntity<List<HouseDealDto>> apt_detail(@RequestParam("aptCode") String aptCode) throws Exception {
		//logger.debug("deal : {}", haHouseMapService.getAptDetail(aptCode));

		return new ResponseEntity<List<HouseDealDto>>(haHouseMapService.getAptDetail(aptCode), HttpStatus.OK);
	}
	@GetMapping("/apt/dealInDate")
	public ResponseEntity<List<HouseDealDto>> apt_detailInDate(@RequestParam("aptCode") String aptCode, @RequestParam("from") String from,
	@RequestParam("to") String to) throws Exception {
		//logger.debug("deal : {}", haHouseMapService.getAptDetail(aptCode));
		System.out.println(aptCode + " " + from +  " " + to);
		return new ResponseEntity<List<HouseDealDto>>(haHouseMapService.getAptDetailInDate(aptCode, from, to),
				HttpStatus.OK);
	}
}
