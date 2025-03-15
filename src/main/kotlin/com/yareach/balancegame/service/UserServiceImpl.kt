package com.yareach.balancegame.service

import com.yareach.balancegame.dto.UserDto
import com.yareach.balancegame.dto.toUserDto
import com.yareach.balancegame.entity.UserEntity
import com.yareach.balancegame.exception.AppException
import com.yareach.balancegame.exception.ErrorCode
import com.yareach.balancegame.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
final class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
): UserService {
    override fun join(userId: String, password: String): UserDto {
        val isAlreadyExist = userRepository.existsById(userId)
        if(isAlreadyExist) {
            throw AppException(ErrorCode.DUPLICATE_USER_ID, "user id '$userId' is already used")
        }
        val newUser = UserEntity(userId, bCryptPasswordEncoder.encode(password))
        return userRepository.save(newUser).toUserDto()
    }

    override fun getUser(userId: String): UserDto {
        println("????????????????/")
        println(userId)
        println(userRepository.findByIdOrNull(userId))
        val user = userRepository.findByIdOrNull(userId)
            ?: throw AppException(ErrorCode.USER_NOT_FOUND, "user id '$userId' does not exist")
        return user.toUserDto()
    }
}