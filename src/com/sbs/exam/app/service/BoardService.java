package com.sbs.exam.app.service;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Board;
import com.sbs.exam.app.repository.BoardRepository;

public class BoardService {

	private BoardRepository boardRepository;
	
	public BoardService() {
		boardRepository = Container.getBoardRepository();
	}

	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}

}
