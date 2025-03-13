package com.yareach.balancegame.service

import com.yareach.balancegame.entity.UserEntity

interface UserService {
    fun join(userId: String, password: String): UserEntity
}