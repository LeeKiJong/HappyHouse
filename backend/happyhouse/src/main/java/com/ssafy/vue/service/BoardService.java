package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;

public interface BoardService {
	public List<Board> retrieveBoard(String type);
	public Board detailBoard(int articleno);
	public void updateHit(int articleno);
	public boolean writeBoard(Board board);
	public boolean updateBoard(Board board);
	public boolean deleteBoard(int articleno);
	
	public List<CommentDto> retrieveComment(int articleno);

	public boolean writeComment(CommentDto commentDto);

	public boolean updateComment(CommentDto commentDto);
	public boolean deleteComment(int commentno);
}
