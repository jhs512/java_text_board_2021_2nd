package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sbs.exam.app.container.ContainerComponent;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.util.Util;

public class ArticleRepository implements ContainerComponent {
	private List<Article> articles;
	private int lastId;

	public void init() {
		articles = new ArrayList<>();
		lastId = 0;
	}

	public int write(int boardId, int memberId, String title, String body, String keywordsStr, int hitCount) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;
		Article article = new Article(id, regDate, updateDate, boardId, memberId, title, body, keywordsStr, hitCount, 0,
				0);
		articles.add(article);

		lastId = id;

		return id;
	}

	public void deleteArticleById(int id) {
		Article article = getArticleById(id);

		if (article != null) {
			articles.remove(article);
		}
	}

	public Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	// 검색조건에 게시물이 부합하는지 알려준다.
	private boolean searchOptionsMatched(Article article, int boardId, String searchKeywordTypeCode,
			String searchKeyword) {

		if (boardId != 0) {
			if (article.getBoardId() != boardId) {
				return false;
			}
		}

		if (searchKeyword.length() > 0) {
			switch (searchKeywordTypeCode) {
			case "body":
				if (!article.getBody().contains(searchKeyword)) {
					return false;
				}
				break;
			case "title,body":
				if (!article.getTitle().contains(searchKeyword) && !article.getBody().contains(searchKeyword)) {
					return false;
				}
				break;
			case "keyword":
				if (!article.getKeywordsStr().contains(searchKeyword) && !article.getBody().contains(searchKeyword)) {
					return false;
				}
				break;
			case "title":
			default:
				if (!article.getTitle().contains(searchKeyword)) {
					return false;
				}
				break;
			}
		}

		return true;
	}

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			String orderByColumn, String orderAscTypeCode, int limitStart, int limitCount) {
		List<Article> sortedArticles = getSortedArticles(orderByColumn, orderAscTypeCode);

		List<Article> filteredArticles = new ArrayList<>();

		int dataIndex = 0;

		for (Article article : sortedArticles) {

			if (searchOptionsMatched(article, boardId, searchKeywordTypeCode, searchKeyword) == false) {
				continue;
			}

			if (dataIndex >= limitStart) {
				filteredArticles.add(article);
			}

			dataIndex++;

			if (filteredArticles.size() == limitCount) {
				break;
			}
		}

		return filteredArticles;
	}

	private List<Article> getSortedArticles(String orderByColumn, String orderAscTypeCode) {
		if (orderByColumn.equals("id") && orderAscTypeCode.equals("asc")) {
			return articles;
		}

		List<Article> sortedArticles = articles;

		if (orderByColumn.equals("id") && orderAscTypeCode.equals("desc")) {
			sortedArticles = new ArrayList<Article>(articles);
			Collections.reverse(sortedArticles);
		} else if (orderByColumn.equals("hitCount") && orderAscTypeCode.equals("asc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getHitCount))
					.collect(Collectors.toList());
		} else if (orderByColumn.equals("hitCount") && orderAscTypeCode.equals("desc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getHitCount).reversed())
					.collect(Collectors.toList());
		} else if (orderByColumn.equals("goodlikePoint") && orderAscTypeCode.equals("asc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getGoodlikePoint))
					.collect(Collectors.toList());
		} else if (orderByColumn.equals("goodlikePoint") && orderAscTypeCode.equals("desc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getGoodlikePoint).reversed())
					.collect(Collectors.toList());
		} else if (orderByColumn.equals("dislikePoint") && orderAscTypeCode.equals("asc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getDislikePoint))
					.collect(Collectors.toList());
		} else if (orderByColumn.equals("dislikePoint") && orderAscTypeCode.equals("desc")) {
			return sortedArticles.stream().sorted(Comparator.comparing(Article::getDislikePoint).reversed())
					.collect(Collectors.toList());
		}

		return sortedArticles;
	}

	public int getTotalItemsCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		int totalItemsCount = 0;

		for (Article article : articles) {
			if (searchOptionsMatched(article, boardId, searchKeywordTypeCode, searchKeyword) == false) {
				continue;
			}

			totalItemsCount++;
		}

		return totalItemsCount;
	}

	public void increaseHitCount(int id) {
		Article article = getArticleById(id);
		article.setHitCount(article.getHitCount() + 1);
	}

	public void increaseGoodlikePoint(int id) {
		Article article = getArticleById(id);
		article.setGoodlikePoint(article.getGoodlikePoint() + 1);
	}

	public void decreaseGoodlikePoint(int id) {
		Article article = getArticleById(id);
		article.setGoodlikePoint(article.getGoodlikePoint() - 1);
	}

	public void increaseDislikePoint(int id) {
		Article article = getArticleById(id);
		article.setDislikePoint(article.getDislikePoint() + 1);
	}

	public void decreaseDislikePoint(int id) {
		Article article = getArticleById(id);
		article.setDislikePoint(article.getDislikePoint() - 1);
	}

	public void modify(int id, String title, String body, String keywordsStr) {
		Article article = getArticleById(id);

		article.setTitle(title);
		article.setBody(body);
		article.setKeywordsStr(keywordsStr);
		article.setUpdateDate(Util.getNowDateStr());
	}

	public void updateKeywordsStr(int id, String keywordsStr) {
		Article article = getArticleById(id);
		
		article.setKeywordsStr(keywordsStr);
	}
}
