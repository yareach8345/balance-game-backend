package com.yareach.balancegame.controller

import com.yareach.balancegame.dto.*
import com.yareach.balancegame.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {
    @PostMapping("/join")
    fun join(@RequestBody @Valid joinDto: JoinDto): ResponseEntity<UserDto> {
        val userId = joinDto.id!!
        val password = joinDto.password!!
        val user = userService.join(userId, password)
        return ResponseEntity
            .created(URI.create("/user/${userId}"))
            .body(user)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<UserDto> {
        val user = userService.getUser(userId)
        return ResponseEntity.ok(user)
    }
}