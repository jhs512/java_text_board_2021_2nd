package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Article;
import com.sbs.exam.util.Util;

public class ArticleRepository {
	private List<Article> articles;
	private int lastId;

	public ArticleRepository() {
		articles = new ArrayList<>();
		lastId = 0;
	}

	public int write(int boardId, int memberId, String title, String body) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Article article = new Article(id, regDate, updateDate, boardId, memberId, title, body);
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

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int limitCount) {
		List<Article> sortedArticles = new ArrayList<>();

		for (int i = articles.size() - 1; i >= 0; i--) {
			sortedArticles.add(articles.get(i));
		}

		List<Article> filteredArticles = new ArrayList<>();

		int dataIndex = 0;

		for (Article article : sortedArticles) {
			if (boardId != 0) {
				if (article.getBoardId() != boardId) {
					continue;
				}
			}

			if (searchKeyword.length() > 0) {
				switch (searchKeywordTypeCode) {
				case "body":
					if (!article.getBody().contains(searchKeyword)) {
						continue;
					}
					break;
				case "title,body":
					if (!article.getTitle().contains(searchKeyword) && !article.getBody().contains(searchKeyword)) {
						continue;
					}
					break;
				case "title":
					if (!article.getTitle().contains(searchKeyword)) {
						continue;
					}
				default:
					break;
				}
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

}
