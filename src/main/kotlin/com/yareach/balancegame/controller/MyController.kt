package com.yareach.balancegame.controller

import com.yareach.balancegame.dto.UserDto
import com.yareach.balancegame.dto.toUserDto
import com.yareach.balancegame.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 기능 구현 당시 테스트를 위해 만들어짐
 */
@RestController
class MyController(val userRepository: UserRepository) {
    @GetMapping("/my")
    fun getMyInfo(): UserDto? {
        return userRepository.findByIdOrNull(
            SecurityContextHolder.getContext().authentication.name
        )?.toUserDto()
    }
}