package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
    @Autowired
	private BoardMapper boardMapper;

    @Override
	public List<Board> retrieveBoard(String type) {
		return boardMapper.selectBoard(type);
	}
    
  	@Override
	public boolean writeBoard(Board board) {
		return boardMapper.insertBoard(board) == 1;
	}

	@Override
	public Board detailBoard(int articleno) {
		return boardMapper.selectBoardByNo(articleno);
	}
	
	@Override
	public void updateHit(int articleno) {
		boardMapper.updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean updateBoard(Board board) {
		return boardMapper.updateBoard(board) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int articleno) {
		return boardMapper.deleteBoard(articleno) == 1;
	}

	@Override
	public List<CommentDto> retrieveComment(int articleno) {
		return boardMapper.retrieveComment(articleno);
	}

	@Override
	public boolean writeComment(CommentDto commentDto) {
		return boardMapper.writeComment(commentDto) == 1;
	}

	@Override
	public boolean updateComment(CommentDto commentDto) {
		return boardMapper.updateComment(commentDto) == 1;
	}

	@Override
	public boolean deleteComment(int commentno) {
		return boardMapper.deleteComment(commentno) == 1;
	}

	@Override
	public List<Board> searchBoard(String subjectd) {
		return boardMapper.searchBoard("%" + subjectd+"%");
	}
}