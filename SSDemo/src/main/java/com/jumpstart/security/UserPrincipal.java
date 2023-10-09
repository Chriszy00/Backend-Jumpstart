package com.jumpstart.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jumpstart.entity.Store;
import com.jumpstart.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal  implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7945505693895755863L;

	private Long id;
	
	@JsonIgnore
	private String userName;
	@JsonIgnore
	private String password;
	private long store_id;
    private Store store;
	private Collection<? extends GrantedAuthority> authorities;

	
	

	public UserPrincipal(Long id, String userName, String password, long store_id, Store store,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.store_id = store_id;
		this.store = store;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
	    List<GrantedAuthority> authorities = user.getRoles().stream()
	            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
	            .collect(Collectors.toList());

	    return new UserPrincipal(user.getId(), 
	            user.getUserName(), user.getPassword(), user.getStore_id(), user.getStore(), authorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}


	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	@Override
	public String getUsername() {
		return userName;
	}
	
	
	
	
	
}
