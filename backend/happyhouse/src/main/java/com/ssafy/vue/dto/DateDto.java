package com.ssafy.vue.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class DateDto {
    private String aptCode;
    private String fromY;
    private String fromM;
    private String toY;
    private String toM;

    public String getAptCode() {
		return aptCode;
	}

    public void setAptCode(String aptCode) {
        this.aptCode = aptCode;
    }
    public String getFromY() {
		return fromY;
	}

    public void setFromY(String fromY) {
        this.fromY = fromY;
    }
    public String getFromX() {
		return fromM;
	}

    public void setFromX(String fromM) {
        this.fromM = fromM;
    }
    public String getToY() {
		return toY;
	}

    public void setToY(String toY) {
        this.toY = toY;
    }
    public String getToM() {
		return toM;
	}

    public void setToM(String toM) {
        this.toM = toM;
    }

    public DateDto(String aptCode, String fromY, String fromM, String toY, String toM) {
        this.aptCode = aptCode;
        this.fromY = fromY;
        this.fromM = fromM;
        this.toY = toY;
        this.toM = toM;
    }
    
}