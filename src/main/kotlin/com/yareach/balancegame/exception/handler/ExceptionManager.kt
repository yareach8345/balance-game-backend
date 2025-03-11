package com.yareach.balancegame.exception.handler

import com.yareach.balancegame.dto.Response
import com.yareach.balancegame.dto.errorResponse
import com.yareach.balancegame.exception.AppException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionManager {
    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(e: AppException): ResponseEntity<Response<Nothing>> {
        return ResponseEntity.status(e.errorCode.status).body(errorResponse(e.errorCode.name))
    }
}