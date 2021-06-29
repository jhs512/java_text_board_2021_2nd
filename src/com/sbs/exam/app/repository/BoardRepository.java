package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.dto.Board;

public class BoardRepository {
	private List<Board> boards;
	private int lastId;

	public BoardRepository() {
		boards = new ArrayList<>();
		lastId = 0;
	}

	public Board getBoardById(int id) {
		for (Board board : boards) {
			if (board.getId() == id) {
				return board;
			}
		}

		return null;
	}

}
