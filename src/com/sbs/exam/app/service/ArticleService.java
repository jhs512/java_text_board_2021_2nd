package com.sbs.exam.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private int write(int boardId, int memberId, String title, String body, int hitCount) {
		int id = articleRepository.write(boardId, memberId, title, body, "", hitCount);

		updateKeywordsStrAsync(id);

		return id;
	}

	private void updateKeywordsStrAsync(int id) {
		new Thread(() -> {
			Article article = getArticleById(id);
			String keywordsStr = Util.getKeywordsStrFromStr(article.getBody());
			articleRepository.updateKeywordsStr(id, keywordsStr);
		}).start();
	}

	public int write(int boardId, int memberId, String title, String body) {
		return write(boardId, memberId, title, body, 0);
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
		List<Map<String, Object>> testData = new ArrayList<>();

		testData.add(Util.mapOf("title", "안녕하세요. 저는 홍길동 입니다.", "body", "저는 애플제품을 좋아합니다. 오랜 아이폰 유저인 저는 ..."));
		testData.add(Util.mapOf("title", "안녕하세요. 저는 홍길순 입니다.", "body", "저는 웹 개발자 10년차 프리랜서 입니다. 저는 C++을 참 좋아합니다."));

		int i = 0;
		for (Map<String, Object> testDataRow : testData) {
			String title = (String) testDataRow.get("title");
			String body = (String) testDataRow.get("body");
			int id = write(i % 2 + 1, i % 2 + 1, title, body, Util.getRandomInt(1, 100));
			Article article = getArticleById(id);

			makeArticleEtcTestData(article);
			i++;
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

	public void modify(int id, String title, String body) {
		articleRepository.modify(id, title, body, "");

		updateKeywordsStrAsync(id);
	}
}
