package com.yareach.balancegame.dto

data class Response<T>(
    val resultCode: String,
    val result: T?
)

fun <T> successResponse(result: T) = Response("success", result)

fun errorResponse(errorCode: String) = Response(errorCode, null)