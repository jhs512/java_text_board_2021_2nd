package com.sbs.exam.app.controller;

import java.util.Map;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.service.LikeService;

public class UsrLikeController extends Controller {

	private LikeService likeService;

	public UsrLikeController() {
		likeService = Container.getLikeService();
	}

	@Override
	public void performAction(Rq rq) {
		if (rq.getActionPath().equals("/usr/like/goodlike")) {
			actionGoodlike(rq);
		} else if (rq.getActionPath().equals("/usr/like/cancelGoodlike")) {
			actionCancelGoodlike(rq);
		} else if (rq.getActionPath().equals("/usr/like/dislike")) {
			actionDislike(rq);
		} else if (rq.getActionPath().equals("/usr/like/cancelDislike")) {
			actionCancelDislike(rq);
		}
	}

	private void actionGoodlike(Rq rq) {
		String relTypeCode = rq.getParam("relTypeCode", "");
		int relId = rq.getIntParam("relId", 0);

		if (!relTypeCode.equals("article")) {
			System.out.println("relTypeCode 가 올바르지 않습니다.");
			return;
		}

		if (relId == 0) {
			System.out.println("relId를 입력해주세요.");
			return;
		}

		Map<String, Object> rs = likeService.goodlike(relTypeCode, relId, rq.getLoginedMemberId());

		String rsMsg = (String) rs.get("msg");

		System.out.println(rsMsg);
	}

	private void actionCancelGoodlike(Rq rq) {
		String relTypeCode = rq.getParam("relTypeCode", "");
		int relId = rq.getIntParam("relId", 0);

		if (!relTypeCode.equals("article")) {
			System.out.println("relTypeCode 가 올바르지 않습니다.");
			return;
		}

		if (relId == 0) {
			System.out.println("relId를 입력해주세요.");
			return;
		}

		Map<String, Object> rs = likeService.cancelGoodlike(relTypeCode, relId, rq.getLoginedMemberId());

		String rsMsg = (String) rs.get("msg");

		System.out.println(rsMsg);
	}

	private void actionDislike(Rq rq) {
		String relTypeCode = rq.getParam("relTypeCode", "");
		int relId = rq.getIntParam("relId", 0);

		if (!relTypeCode.equals("article")) {
			System.out.println("relTypeCode 가 올바르지 않습니다.");
			return;
		}

		if (relId == 0) {
			System.out.println("relId를 입력해주세요.");
			return;
		}

		Map<String, Object> rs = likeService.dislike(relTypeCode, relId, rq.getLoginedMemberId());

		String rsMsg = (String) rs.get("msg");

		System.out.println(rsMsg);
	}

	private void actionCancelDislike(Rq rq) {
		String relTypeCode = rq.getParam("relTypeCode", "");
		int relId = rq.getIntParam("relId", 0);

		if (!relTypeCode.equals("article")) {
			System.out.println("relTypeCode 가 올바르지 않습니다.");
			return;
		}

		if (relId == 0) {
			System.out.println("relId를 입력해주세요.");
			return;
		}

		Map<String, Object> rs = likeService.cancelDislike(relTypeCode, relId, rq.getLoginedMemberId());

		String rsMsg = (String) rs.get("msg");

		System.out.println(rsMsg);
	}

}
