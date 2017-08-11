package net.bitnine.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import net.bitnine.domain.Member;
import net.bitnine.domain.MemberRole;

import lombok.Getter;

@Getter
public class SecurityUser extends User {

	private static final String ROLE_PREFIX = "ROLE_";

	private Member member;

	public SecurityUser(Member member) {

		super(member.getMid(), member.getMpw(), makeGrantedAuthority(member.getRole()));

		this.member = member;

	}

	private static List<GrantedAuthority> makeGrantedAuthority(MemberRole role) {

		List<GrantedAuthority> list = new ArrayList<>();

		//roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName()));

		return list;
	}

}
