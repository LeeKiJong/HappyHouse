package com.ssafy.vue.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class CommentDto {
    private int commentno;
    private int articleno;
    private String userid;
    private String content;
    private String regtime;

    public int getCommentno() {
		return commentno;
	}

    public void setCommentno(int commentno) {
        this.commentno = commentno;
    }
    
    public int getArticleno() {
		return articleno;
	}

    public void setArticleno(int articleno) {
        this.articleno = articleno;
    }
    public String getUserid() {
		return userid;
	}

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getContent() {
		return content;
	}

    public void setContent(String content) {
        this.content = content;
    }
    public String getRegtime() {
		return regtime;
	}

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public CommentDto(int commentno, int articleno, String userid, String content, String regtime) {
        super();
        this.commentno = commentno;
		this.articleno = articleno;
		this.userid = userid;
		this.content = content;
		this.regtime = regtime;
	}
}