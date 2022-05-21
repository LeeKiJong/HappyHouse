package com.ssafy.vue.dto;

public class MapInfoDto {
	private String level;
	private String swLat;
	private String swLng;
	private String neLat;
	private String neLng;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getSwLat() {
		return swLat;
	}
	public void setSwLat(String swLat) {
		this.swLat = swLat;
	}
	public String getSwLng() {
		return swLng;
	}
	public void setSwLng(String swLng) {
		this.swLng = swLng;
	}
	public String getNeLat() {
		return neLat;
	}
	public void setNeLat(String neLat) {
		this.neLat = neLat;
	}
	public String getNeLng() {
		return neLng;
	}
	public void setNeLng(String neLng) {
		this.neLng = neLng;
	}
	@Override
	public String toString() {
		return "MapInfoDto [level=" + level + ", swLat=" + swLat + ", swLng=" + swLng + ", neLat=" + neLat + ", neLng="
				+ neLng + "]";
	}

	
}
