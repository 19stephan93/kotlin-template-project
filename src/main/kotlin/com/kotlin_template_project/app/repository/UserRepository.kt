package com.kotlin_template_project.app.repository

import com.kotlin_template_project.app.entity.UserEntity
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.Throws

@Repository
interface UserRepository : IUserRepository<UserEntity>, CrudRepository<UserEntity, UUID> {
    override fun findAll(): Iterable<UserEntity>

    @Throws(EmptyResultDataAccessException::class)
    override fun findByUsername(login: String): UserEntity
}