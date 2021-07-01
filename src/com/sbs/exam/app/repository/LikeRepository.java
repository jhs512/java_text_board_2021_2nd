package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Like;
import com.sbs.exam.util.Util;

public class LikeRepository {
	private List<Like> likes;
	private int lastId;

	public LikeRepository() {
		likes = new ArrayList<>();
		lastId = 0;
	}

	public Like getLikeByRelTypeCodeAndRelIdAndMemberId(String relTypeCode, int relId, int memberId) {
		for (Like like : likes) {
			if (!like.getRelTypeCode().equals(relTypeCode)) {
				continue;
			}

			if (like.getRelId() != relId) {
				continue;
			}

			if (like.getMemberId() != memberId) {
				continue;
			}

			return like;
		}

		return null;
	}

	public int getLikePointByRelTypeCodeAndRelIdAndMemberId(String relTypeCode, int relId, int memberId) {
		Like like = getLikeByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (like != null) {
			return like.getPoint();
		}

		return 0;
	}

	public int goodlike(String relTypeCode, int relId, int memberId) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Like like = new Like(id, regDate, updateDate, relTypeCode, relId, memberId, 1);
		likes.add(like);

		lastId = id;

		return id;
	}

	public int dislike(String relTypeCode, int relId, int memberId) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Like like = new Like(id, regDate, updateDate, relTypeCode, relId, memberId, -1);
		likes.add(like);

		lastId = id;

		return id;
	}

	public void deleteLike(String relTypeCode, int relId, int memberId) {
		Like like = getLikeByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (like != null) {
			likes.remove(like);
		}
	}
}
