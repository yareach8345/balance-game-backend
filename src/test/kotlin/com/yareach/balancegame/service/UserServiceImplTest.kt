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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class UserServiceImplTest{

    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Spy
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    private final val testId = "test user id"
    private final val testPassword = "test password"

    @BeforeEach
    fun setUp() {
        Mockito.reset(userRepository)
        userService = UserServiceImpl(userRepository, bCryptPasswordEncoder)
    }

    @Test
    @DisplayName("새로운 유저가 추가 됨")
    fun addUser() {
        val testStartTime = LocalDateTime.now()
        val argumentCaptor = ArgumentCaptor.forClass(UserEntity::class.java)
        val testUserEntity = UserEntity(testId, testPassword)

        whenever(userRepository.existsById(any())).thenReturn(false)
        whenever(userRepository.save(argumentCaptor.capture())).thenReturn(testUserEntity.apply { prePersist() })

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

    @Test
    @DisplayName("이미 존재하는 유저를 성공적으로 불러옴")
    fun getUserTest() {
        val captor = ArgumentCaptor.forClass(String::class.java)
        val oldUser = UserEntity(testId, testPassword, joinAt = LocalDateTime.of(2025, 3, 14, 11, 0,0, 24))

        whenever(userRepository.findById(captor.capture())).thenReturn(Optional.of(oldUser))

        val userDto = userService.getUser(testId)

        assertEquals(testId, captor.value)
        assertEquals(testId, userDto.id)
    }

    @Test
    @DisplayName("없는 유저의 ID로 조회할 시 조회에 실패함")
    fun userWhoHaveTheseIdIsNotExists() {
        whenever(userRepository.findById(any())).thenReturn(Optional.empty())

        val exception = assertThrows<AppException>{
            userService.getUser(testId)
        }

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.errorCode)
        assertEquals("user id '${testId}' does not exist", exception.message)
    }
}