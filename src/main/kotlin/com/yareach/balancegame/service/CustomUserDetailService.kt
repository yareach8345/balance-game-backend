package com.yareach.balancegame.service

import com.yareach.balancegame.dto.CustomUserDetails
import com.yareach.balancegame.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? = username?.let {
        userRepository.findByIdOrNull(it)
    }?.let {
        CustomUserDetails(it)
    }
}