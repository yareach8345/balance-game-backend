package com.yareach.balancegame.service

import com.yareach.balancegame.dto.UserDto
import com.yareach.balancegame.entity.UserEntity

interface UserService {
    fun join(userId: String, password: String): UserDto

    fun getUser(userId: String): UserDto
}