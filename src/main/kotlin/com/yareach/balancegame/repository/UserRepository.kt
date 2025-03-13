package com.yareach.balancegame.repository

import com.yareach.balancegame.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, String> {}