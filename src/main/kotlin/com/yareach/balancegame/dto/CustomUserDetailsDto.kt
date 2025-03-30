package com.yareach.balancegame.dto

import com.yareach.balancegame.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetailsDto(
    val userEntity: UserEntity
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(userEntity.role))
    }

    override fun getPassword(): String {
        return userEntity.password
    }

    override fun getUsername(): String {
        return userEntity.id
    }

    override fun isAccountNonExpired(): Boolean {
        return !userEntity.isDeleted
    }

    override fun isAccountNonLocked(): Boolean {
        return !userEntity.isBanned
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return (!userEntity.isDeleted) && (!userEntity.isBanned)
    }
}
