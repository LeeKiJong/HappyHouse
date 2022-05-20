package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.SidoGugunCodeDto;
import com.ssafy.vue.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInGugun(String gugun) throws Exception {
		return houseMapMapper.getAptInGugun(gugun);
	}
	
	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}
	@Override
	public List<HouseInfoDto> getAptInName(String aptName, String gugun, String dong) throws Exception {
		if(dong.equals("")) {
			return houseMapMapper.getAptInName_gugun("%"+aptName+"%", gugun);
		}else {
			return houseMapMapper.getAptInName_dong("%"+aptName+"%", dong);
		}
	}
	
	@Override
	public List<HouseDealDto> getAptDetail(String aptCode) throws Exception {
		return houseMapMapper.getAptDetail(aptCode);
	}

}
