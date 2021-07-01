package com.sbs.exam.app.container;

import java.util.Scanner;

import com.sbs.exam.app.Session;
import com.sbs.exam.app.controller.UsrArticleController;
import com.sbs.exam.app.controller.UsrLikeController;
import com.sbs.exam.app.controller.UsrMemberController;
import com.sbs.exam.app.controller.UsrSystemController;
import com.sbs.exam.app.interceptor.NeedLoginInterceptor;
import com.sbs.exam.app.interceptor.NeedLogoutInterceptor;
import com.sbs.exam.app.repository.ArticleRepository;
import com.sbs.exam.app.repository.BoardRepository;
import com.sbs.exam.app.repository.LikeRepository;
import com.sbs.exam.app.repository.MemberRepository;
import com.sbs.exam.app.service.ArticleService;
import com.sbs.exam.app.service.BoardService;
import com.sbs.exam.app.service.LikeService;
import com.sbs.exam.app.service.MemberService;

import lombok.Getter;

public class Container {
	@Getter
	private static Scanner sc;
	@Getter
	private static Session session;

	@Getter
	private static MemberRepository memberRepository;
	@Getter
	private static BoardRepository boardRepository;
	@Getter
	private static ArticleRepository articleRepository;
	@Getter
	private static LikeRepository likeRepository;

	@Getter
	private static MemberService memberService;
	@Getter
	private static BoardService boardService;
	@Getter
	private static ArticleService articleService;
	@Getter
	private static LikeService likeService;

	@Getter
	private static NeedLoginInterceptor needLoginInterceptor;
	@Getter
	private static NeedLogoutInterceptor needLogoutInterceptor;

	@Getter
	private static UsrSystemController usrSystemController;
	@Getter
	private static UsrMemberController usrMemberController;
	@Getter
	private static UsrArticleController usrArticleController;
	@Getter
	private static UsrLikeController usrLikeController;

	static {
		sc = new Scanner(System.in);
		session = new Session();

		memberRepository = new MemberRepository();
		boardRepository = new BoardRepository();
		likeRepository = new LikeRepository();
		articleRepository = new ArticleRepository();

		memberService = new MemberService();
		boardService = new BoardService();
		likeService = new LikeService();
		articleService = new ArticleService();

		needLoginInterceptor = new NeedLoginInterceptor();
		needLogoutInterceptor = new NeedLogoutInterceptor();

		usrSystemController = new UsrSystemController();
		usrMemberController = new UsrMemberController();
		usrLikeController = new UsrLikeController();
		usrArticleController = new UsrArticleController();

		// 초기화
		memberRepository.init();
		boardRepository.init();
		likeRepository.init();
		articleRepository.init();

		memberService.init();
		boardService.init();
		likeService.init();
		articleService.init();

		needLoginInterceptor.init();
		needLogoutInterceptor.init();

		usrSystemController.init();
		usrMemberController.init();
		usrLikeController.init();
		usrArticleController.init();
	}
}
