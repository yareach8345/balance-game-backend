package com.yareach.balancegame.service

import com.yareach.balancegame.dto.toUserDto
import com.yareach.balancegame.entity.User
import com.yareach.balancegame.exception.AppException
import com.yareach.balancegame.exception.ErrorCode
import com.yareach.balancegame.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
final class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
): UserService {
    override fun join(userId: String, password: String): User {
        val isAlreadyExist = userRepository.existsById(userId)
        if(isAlreadyExist) {
            throw AppException(ErrorCode.DUPLICATE_USER_ID, "user id '$userId' is already used")
        }
        val newUser = User(userId, bCryptPasswordEncoder.encode(password))
        return userRepository.save(newUser)
    }
}