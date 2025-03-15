package com.yareach.balancegame.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    DUPLICATE_USER_ID(HttpStatus.CONFLICT, "User id is already exists"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User does not exists"),
}