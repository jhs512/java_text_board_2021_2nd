package com.sbs.exam.app.service;

import java.util.List;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.repository.MemberRepository;

public class MemberService {

	private MemberRepository memberRepository;

	public void init() {
		memberRepository = Container.getMemberRepository();
	}

	public void makeTestData() {
		for (int i = 0; i < 100; i++) {
			String loginId = "user" + String.format("%03d", i + 1);
			String loginPw = loginId;
			String name = "홍길동" + String.format("%03d", i + 1);
			String nickname = "강바람" + String.format("%03d", i + 1);

			join(loginId, loginPw, name, nickname);
		}
	}

	private int join(String loginId, String loginPw, String name, String nickname) {
		return memberRepository.join(loginId, loginPw, name, nickname);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public List<Member> getMembers() {
		return memberRepository.getMembers();
	}

}
