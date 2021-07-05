package com.sbs.exam.app.service;

import java.util.List;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.container.ContainerComponent;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.repository.ArticleRepository;
import com.sbs.exam.util.Util;

public class ArticleService implements ContainerComponent {
	private ArticleRepository articleRepository;
	private MemberService memberService;
	private LikeService likeService;

	public void init() {
		articleRepository = Container.getArticleRepository();
		memberService = Container.getMemberService();
		likeService = Container.getLikeService();
	}

	private int writeForTestData(int boardId, int memberId, String title, String body, int hitCount) {
		return articleRepository.write(boardId, memberId, title, body, hitCount);
	}

	public int write(int boardId, int memberId, String title, String body) {
		return articleRepository.write(boardId, memberId, title, body, 0);
	}

	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void deleteArticleById(int id) {
		articleRepository.deleteArticleById(id);
	}

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			String orderByColumn, String orderAscTypeCode, int page, int pageItemsCount) {
		int limitStart = (page - 1) * pageItemsCount;
		int limitCount = pageItemsCount;
		return articleRepository.getArticles(boardId, searchKeywordTypeCode, searchKeyword, orderByColumn,
				orderAscTypeCode, limitStart, limitCount);
	}

	public void makeTestData() {
		for (int i = 0; i < 100; i++) {
			String title = "제목 " + (i + 1);
			String body = "내용 " + (i + 1);
			int id = writeForTestData(i % 2 + 1, i % 2 + 1, title, body, Util.getRandomInt(1, 100));
			Article article = getArticleById(id);

			makeArticleEtcTestData(article);
		}
	}

	private void makeArticleEtcTestData(Article article) {
		List<Member> members = memberService.getMembers();

		for (Member member : members) {
			int no = Util.getRandomInt(1, 3);

			if (no == 1) {
				likeService.goodlike("article", article.getId(), member.getId());
			} else if (no == 2) {
				likeService.dislike("article", article.getId(), member.getId());
			}
		}
	}

	public int getTotalItemsCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getTotalItemsCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public void increaseHitCount(int id) {
		articleRepository.increaseHitCount(id);
	}

	public void increaseGoodlikePoint(int id) {
		articleRepository.increaseGoodlikePoint(id);
	}

	public void decreaseGoodlikePoint(int id) {
		articleRepository.decreaseGoodlikePoint(id);
	}

	public void increaseDislikePoint(int id) {
		articleRepository.increaseDislikePoint(id);
	}

	public void decreaseDislikePoint(int id) {
		articleRepository.decreaseDislikePoint(id);
	}
}
