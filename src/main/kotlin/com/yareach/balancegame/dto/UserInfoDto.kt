package com.yareach.balancegame.dto

import com.yareach.balancegame.entity.UserEntity
import java.time.LocalDateTime

data class UserInfoDto(
    val id: String,
    val role: String,
    val isBanned: Boolean,
    val isDeleted: Boolean,
    val warnCnt: Byte,
    val joinAt: LocalDateTime?
)

fun UserEntity.toUserDto(): UserInfoDto = UserInfoDto( id, role, isBanned, isDeleted, warnCnt, joinAt)