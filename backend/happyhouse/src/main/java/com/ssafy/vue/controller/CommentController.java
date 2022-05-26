package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("CommentController V1")
@RestController
@RequestMapping("/comment")
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

	@ApiOperation(value = "댓글 목록", notes = "모든 댓글의 정보를 반환한다.", response = List.class)
	@GetMapping("{articleno}")
    public ResponseEntity<List<CommentDto>> retrieveComment(@PathVariable int articleno) throws Exception {
        logger.debug("retrieveComment - 호출");
        return new ResponseEntity<List<CommentDto>>(boardService.retrieveComment(articleno), HttpStatus.OK);
    }
    @GetMapping("/write")
    public ResponseEntity<String> writeComment(@RequestParam("articleno") int articleno, @RequestParam("userid") String userid, @RequestParam("content") String content) throws Exception {
		logger.debug("searchBoard - 호출");
		CommentDto temp = new CommentDto(articleno, userid, content);
		if (boardService.writeComment(temp)) {
         	return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
         }
         return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("{commentno}")
    public ResponseEntity<String> updateComment(@RequestBody CommentDto commentDto) {
        logger.debug("updateComment - 호출");
        if (boardService.updateComment(commentDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{commentno}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentno) {
		logger.debug("deleteComment - 호출");
		if (boardService.deleteComment(commentno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
}