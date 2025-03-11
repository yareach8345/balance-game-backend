package com.yareach.balancegame.service

import com.yareach.balancegame.entity.User

interface UserService {
    fun join(userId: String, password: String): User
}