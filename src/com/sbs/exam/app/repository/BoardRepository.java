package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Board;
import com.sbs.exam.util.Util;

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

	public int make(String code, String name) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Board board = new Board(id, regDate, updateDate, code, name);
		boards.add(board);

		lastId = id;

		return id;
	}

}
