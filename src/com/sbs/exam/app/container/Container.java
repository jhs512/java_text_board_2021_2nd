package com.sbs.exam.app.container;

import java.util.ArrayList;
import java.util.List;
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
	private static List<ContainerComponent> containerComponents;

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
		containerComponents = new ArrayList<>();

		memberRepository = addContainerComponent(new MemberRepository());
		boardRepository = addContainerComponent(new BoardRepository());
		likeRepository = addContainerComponent(new LikeRepository());
		articleRepository = addContainerComponent(new ArticleRepository());

		memberService = addContainerComponent(new MemberService());
		boardService = addContainerComponent(new BoardService());
		likeService = addContainerComponent(new LikeService());
		articleService = addContainerComponent(new ArticleService());

		needLoginInterceptor = addContainerComponent(new NeedLoginInterceptor());
		needLogoutInterceptor = addContainerComponent(new NeedLogoutInterceptor());

		usrSystemController = addContainerComponent(new UsrSystemController());
		usrMemberController = addContainerComponent(new UsrMemberController());
		usrLikeController = addContainerComponent(new UsrLikeController());
		usrArticleController = addContainerComponent(new UsrArticleController());

		// 초기화
		for (ContainerComponent containerComponent : containerComponents) {
			containerComponent.init();
		}
	}

	private static <T> T addContainerComponent(ContainerComponent containerComponent) {
		containerComponents.add(containerComponent);

		return (T) containerComponent;
	}
}
