package com.sbs.exam.app.interceptor;

import com.sbs.exam.app.Rq;

public class NeedLoginInterceptor implements Interceptor {
	public void init() {
		
	}
	
	@Override
	public boolean run(Rq rq) {
		if (rq.isLogined()) {
			return true;
		}

		switch (rq.getActionPath()) {
		case "/usr/like/like":
		case "/usr/like/cancelLike":
		case "/usr/like/dislike":
		case "/usr/like/cancelDislike":
		case "/usr/article/write":
		case "/usr/article/modify":
		case "/usr/article/delete":
			System.out.printf("로그인 후 이용해주세요.\n");
			return false;
		}

		return true;
	}
}
