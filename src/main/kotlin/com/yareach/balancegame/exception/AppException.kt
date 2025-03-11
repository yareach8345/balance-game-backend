package com.yareach.balancegame.exception

data class AppException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException() {
    override fun toString(): String = "$errorCode: $message"
}