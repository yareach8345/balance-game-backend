package com.yareach.balancegame.repository

import com.yareach.balancegame.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, String> {}