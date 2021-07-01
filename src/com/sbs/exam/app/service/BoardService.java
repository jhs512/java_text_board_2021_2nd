package com.sbs.exam.app.service;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Board;
import com.sbs.exam.app.repository.BoardRepository;

public class BoardService {

	private BoardRepository boardRepository;
	
	public void init() {
		boardRepository = Container.getBoardRepository();
	}

	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}

	public void makeTestData() {
		make("notice", "공지사항");
		make("free", "자유기고");
	}

	private int make(String code, String name) {
		return boardRepository.make(code, name);
	}

}
