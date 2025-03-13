package com.yareach.balancegame.service

import com.yareach.balancegame.entity.UserEntity
import com.yareach.balancegame.exception.AppException
import com.yareach.balancegame.exception.ErrorCode
import com.yareach.balancegame.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

@SpringBootTest
class UserServiceImplTest{

    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Spy
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    val testId = "test user id"
    val testPassword = "test password"

    @BeforeEach
    fun setUp() {
        userService = UserServiceImpl(userRepository, bCryptPasswordEncoder)
    }

    @Test
    @DisplayName("새로운 유저가 추가 됨")
    fun addUser() {
        val testStartTime = LocalDateTime.now()
        val argumentCaptor = ArgumentCaptor.forClass(UserEntity::class.java)

        Mockito.`when`(userRepository.existsById(testId))
            .thenReturn(false)

        Mockito.`when`(userRepository.save(argumentCaptor.capture()))
            .thenReturn(UserEntity(testId, testPassword).apply{ prePersist() })

        val res = userService.join(testId, testPassword)
        assertEquals(testId, res.id)
        assertTrue(bCryptPasswordEncoder.matches(testPassword, argumentCaptor.value.password))
        assertEquals("USER", res.role)
        assertEquals(false, res.isBanned)
        assertEquals(false, res.isDeleted)
        assertEquals(0, res.warnCnt)
        assertTrue(testStartTime.isBefore(res.joinAt))
    }

    @Test
    @DisplayName("이미 존재하는 ID일 경우 에러 발생")
    fun duplicationTest() {
        whenever(userRepository.existsById(any<String>())).thenReturn(true)

        val exception = assertThrows<AppException>{
            userService.join(testId, testPassword)
        }

        assertEquals("user id '${testId}' is already used", exception.message)
        assertEquals(ErrorCode.DUPLICATE_USER_ID, exception.errorCode)
    }
}