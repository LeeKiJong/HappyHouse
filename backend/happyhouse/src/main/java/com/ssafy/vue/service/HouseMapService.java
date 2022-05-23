package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.MapInfoDto;
import com.ssafy.vue.dto.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseInfoDto> getAptInName(String aptName, String gugun, String dong) throws Exception;
	List<HouseInfoDto> getAptInMap(MapInfoDto mapInfoDto) throws Exception;
	List<HouseDealDto> getAptDetail(String aptCode) throws Exception;
	List<HouseDealDto> getAptDetailInDate(String aptCode, String from, String to) throws Exception;
	
}
