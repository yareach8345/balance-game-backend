package com.yareach.balancegame.controller

import com.yareach.balancegame.dto.*
import com.yareach.balancegame.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {
    @PostMapping("/join")
    fun join(@RequestBody @Valid joinDto: JoinDto): ResponseEntity<Response<UserDto>> {
        val userId = joinDto.id!!
        val password = joinDto.password!!
        val user = userService.join(userId, password)
        return ResponseEntity
            .created(URI.create("/user/${userId}"))
            .body(successResponse(user.toUserDto()))
    }
}