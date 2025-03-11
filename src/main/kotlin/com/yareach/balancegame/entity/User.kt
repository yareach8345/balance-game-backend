package com.yareach.balancegame.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import java.time.LocalDateTime

@Entity
@DynamicInsert
@Table(name = "users")
class User (
    @Id
    @Column(name = "user_id")
    var id: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    @ColumnDefault(value = "'USER'")
    var role: String = "USER",

    @Column(name="is_banned", nullable = false)
    @ColumnDefault(value = "false")
    var isBanned: Boolean = false,

    @Column(name="is_deleted", nullable = false)
    @ColumnDefault("false")
    var isDeleted: Boolean = false,

    @Column(name = "warn_cnt", nullable = false)
    @ColumnDefault(0.toString())
    var warnCnt: Byte = 0,

    @Column(name = "join_at", nullable = true)
    var joinAt: LocalDateTime? = null

) {
    @PrePersist
    fun prePersist() {
        if(joinAt == null)
            joinAt = LocalDateTime.now()
    }
}