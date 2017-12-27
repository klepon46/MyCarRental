package com.fatin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static boolean isAllGranted(String auth){

		if(StringUtils.isEmpty(auth.trim())){
			return false;
		}

		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
		boolean isAllGranted = granted.containsAll(parseAuthorities(auth));

		return isAllGranted;

	}

	@SuppressWarnings("rawtypes")
	public static boolean isAnyGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
		final Set grantedCopy = retainAll(granted, parseAuthorities(authorities));
		return !grantedCopy.isEmpty();
	}


	private static Collection<? extends GrantedAuthority> getPrincipalAuthorities(){

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		if(currentUser == null){
			return Collections.emptyList();
		}

		if(currentUser.getAuthorities() == null || currentUser.getAuthorities().isEmpty()){
			return Collections.emptyList();
		}

		Collection<? extends GrantedAuthority> granted = currentUser.getAuthorities();
		return granted;
	}

	private static Collection<GrantedAuthority> parseAuthorities(String authorizationsString) {
		final ArrayList<GrantedAuthority> required = new ArrayList<GrantedAuthority>();
		final String[] roles = authorizationsString.split(",");

		for (int i = 0; i < roles.length; i++) {
			String role = roles[i].trim();
			required.add(new SimpleGrantedAuthority(role));
		}
		return required;
	}

	@SuppressWarnings("rawtypes")
	private static Set retainAll(final Collection<? extends GrantedAuthority> granted, final Collection<? extends GrantedAuthority> required) {
		Set<String> grantedRoles = toRoles(granted);
		Set<String> requiredRoles = toRoles(required);
		grantedRoles.retainAll(requiredRoles);

		return toAuthorities(grantedRoles, granted);
	}

	private static Set<String> toRoles(Collection<? extends GrantedAuthority> authorities) {
		final Set<String> target = new HashSet<String>();
		for (GrantedAuthority au : authorities) {

			if (null == au.getAuthority()) {
				throw new IllegalArgumentException(
						"Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process "
								+ au.toString());
			}

			target.add(au.getAuthority());
		}

		return target;
	}

	private static Set<GrantedAuthority> toAuthorities(Set<String> grantedRoles, Collection<? extends GrantedAuthority> granted) {
		Set<GrantedAuthority> target = new HashSet<GrantedAuthority>();

		for (String role : grantedRoles) {
			for ( GrantedAuthority authority: granted) {

				if (authority.getAuthority().equals(role)) {
					target.add(authority);
					break;
				}
			}
		}
		return target;
	}

}
