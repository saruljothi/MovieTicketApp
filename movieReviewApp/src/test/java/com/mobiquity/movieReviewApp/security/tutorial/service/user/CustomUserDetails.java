//package com.mobiquity.movieReviewApp.security.tutorial.service.user;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final String name;
//    private final String email;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserDetails(String name, String email) {
//        this.name = name;
//        this.email = email;
//        this.authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomUserDetails{" +
//                "username='" + email + '\'' +
//                '}';
//    }
//}
