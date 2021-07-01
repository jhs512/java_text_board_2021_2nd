package com.sbs.exam.app.service;

import java.util.Map;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.repository.LikeRepository;

public class LikeService {

	private LikeRepository likeRepository;
	private ArticleService articleService;

	public void init() {
		likeRepository = Container.getLikeRepository();
		articleService = Container.getArticleService();
	}

	// 좋아요
	public Map<String, Object> goodlike(String relTypeCode, int relId, int memberId) {
		Article article = null;

		if (relTypeCode.equals("article")) {
			article = articleService.getArticleById(relId);

			if (article == null) {
				return Map.of("resultCode", "F-3", "msg", "존재하지 않는 게시물 입니다.");
			}
		}

		int likePoint = likeRepository.getLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (likePoint > 0) {
			return Map.of("resultCode", "F-1", "msg", "이미 좋아요 처리가 되어있습니다.");
		} else if (likePoint < 0) {
			return Map.of("resultCode", "F-2", "msg", "이미 싫어요 처리가 되어있습니다.");
		}

		likeRepository.goodlike(relTypeCode, relId, memberId);

		if (relTypeCode.equals("article")) {
			articleService.increaseGoodlikePoint(relId);
		}

		return Map.of("resultCode", "S-1", "msg", "좋아요 처리가 되어있습니다.");
	}

	// 좋아요 취소
	public Map<String, Object> cancelGoodlike(String relTypeCode, int relId, int memberId) {
		Article article = null;

		if (relTypeCode.equals("article")) {
			article = articleService.getArticleById(relId);

			if (article == null) {
				return Map.of("resultCode", "F-3", "msg", "존재하지 않는 게시물 입니다.");
			}
		}

		int likePoint = likeRepository.getLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (likePoint == 0) {
			return Map.of("resultCode", "F-1", "msg", "기존에 좋아요를 하지 않았습니다.");
		}

		likeRepository.deleteLike(relTypeCode, relId, memberId);

		if (relTypeCode.equals("article")) {
			articleService.decreaseGoodlikePoint(relId);
		}

		return Map.of("resultCode", "S-1", "msg", "좋아요 취소처리가 되어있습니다.");
	}

	// 싫어요
	public Map<String, Object> dislike(String relTypeCode, int relId, int memberId) {
		Article article = null;

		if (relTypeCode.equals("article")) {
			article = articleService.getArticleById(relId);

			if (article == null) {
				return Map.of("resultCode", "F-3", "msg", "존재하지 않는 게시물 입니다.");
			}
		}

		int likePoint = likeRepository.getLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (likePoint > 0) {
			return Map.of("resultCode", "F-1", "msg", "이미 좋아요 처리가 되어있습니다.");
		} else if (likePoint < 0) {
			return Map.of("resultCode", "F-2", "msg", "이미 싫어요 처리가 되어있습니다.");
		}

		likeRepository.dislike(relTypeCode, relId, memberId);

		if (relTypeCode.equals("article")) {
			articleService.increaseDislikePoint(relId);
		}

		return Map.of("resultCode", "S-1", "msg", "싫어요 처리가 되어있습니다.");
	}

	// 싫어요 취소
	public Map<String, Object> cancelDislike(String relTypeCode, int relId, int memberId) {
		Article article = null;

		if (relTypeCode.equals("article")) {
			article = articleService.getArticleById(relId);

			if (article == null) {
				return Map.of("resultCode", "F-3", "msg", "존재하지 않는 게시물 입니다.");
			}
		}

		int likePoint = likeRepository.getLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

		if (likePoint == 0) {
			return Map.of("resultCode", "F-1", "msg", "기존에 싫어요를 하지 않았습니다.");
		}

		likeRepository.deleteLike(relTypeCode, relId, memberId);

		if (relTypeCode.equals("article")) {
			articleService.decreaseDislikePoint(relId);
		}

		return Map.of("resultCode", "S-1", "msg", "싫어요가 취소처리가 되어있습니다.");
	}
}
