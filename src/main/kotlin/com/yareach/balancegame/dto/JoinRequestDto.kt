package com.yareach.balancegame.dto

import jakarta.validation.constraints.NotBlank

data class JoinRequestDto (
    @field:NotBlank(message = "id는 필수 입력 값입니다.")
    val id: String?,

    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    val password: String?
)