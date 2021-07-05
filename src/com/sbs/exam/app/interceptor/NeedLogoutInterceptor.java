package com.sbs.exam.app.interceptor;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.container.ContainerComponent;

public class NeedLogoutInterceptor implements Interceptor, ContainerComponent {
	public void init() {
		
	}

	@Override
	public boolean run(Rq rq) {
		if (rq.isLogined() == false) {
			return true;
		}

		switch (rq.getActionPath()) {
		case "/usr/member/login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			System.out.printf("이미 로그인 되었습니다.\n");
			return false;
		}

		return true;
	}

}
