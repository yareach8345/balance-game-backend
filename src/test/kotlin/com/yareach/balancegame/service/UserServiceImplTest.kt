package com.yareach.balancegame.service

import com.yareach.balancegame.dto.toUserDto
import com.yareach.balancegame.entity.User
import com.yareach.balancegame.exception.AppException
import com.yareach.balancegame.exception.ErrorCode
import com.yareach.balancegame.repository.UserRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.ArgumentMatchers.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {
    lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @BeforeEach
    fun setUp() {
        userService = UserServiceImpl(userRepository, bCryptPasswordEncoder)
    }

    @Test
    @DisplayName("새로운 유저 가입 테스트")
    fun joinSuccess() {
        val newUserId = "newUser"
        val newUserPassword = "newPassword"
        val encodedPassword = bCryptPasswordEncoder.encode(newUserPassword)
        val time = LocalDateTime.now()

        Mockito.`when`(userRepository.existsById(newUserId))
            .thenReturn(false)
        Mockito.`when`(userRepository.save(notNull()))
            .thenReturn(User(newUserId, encodedPassword))

        val user = userService.join(newUserId, newUserPassword)

        assertEquals(user.id, newUserId)
        assert(bCryptPasswordEncoder.matches(newUserPassword, user.password))
        assertEquals(user.role, "USER")
        assertEquals(user.banned, false)
        assertEquals(user.quit, false)
        assertEquals(user.warnCnt, 0)
        assert(user.joinAt?.isAfter(time) ?: false)
    }

    @Test
    @DisplayName("입력으로 들어온 ID를 가진 유저가 이미 존재 할 경우 실패함")
    fun joinFailureDuplication() {
        val newUserId = "testUser"
        val newUserPassword = "testPassword"

        Mockito.`when`(userRepository.existsById(newUserId))
            .thenReturn(true)

        val exception = assertThrows(AppException::class.java) {
            userService.join(newUserId, newUserPassword)
        }

        assertEquals(exception.message, "user id '$newUserId' is already used")
        assertEquals(exception.errorCode, ErrorCode.DUPLICATE_USER_ID)
    }
}