package com.yareach.balancegame.dto

import com.yareach.balancegame.entity.User
import java.time.LocalDateTime

data class UserDto(
    val id: String,
    val role: String,
    val isBanned: Boolean,
    val isDeleted: Boolean,
    val warnCnt: Byte,
    val joinAt: LocalDateTime?
)

fun User.toUserDto(): UserDto = UserDto( id, role, isBanned, isDeleted, warnCnt, joinAt)