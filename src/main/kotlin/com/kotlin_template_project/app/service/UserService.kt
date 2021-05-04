package com.kotlin_template_project.app.service

import com.kotlin_template_project.app.dto.UserDto
import com.kotlin_template_project.app.entity.UserEntity
import com.kotlin_template_project.app.repository.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService : IUserService<UserDto> {
    @Autowired
    lateinit var userRepository: IUserRepository<UserEntity>

    override fun findAll(): Iterable<UserDto> = UserDto.entitiesToDtos(userRepository.findAll())

    override fun findByUsername(username: String): Optional<UserDto> {
        return try {
            val entity: UserEntity = userRepository.findByUsername(username)
            Optional.of(UserDto.entityToDto(entity))
        } catch (e: EmptyResultDataAccessException) {
            Optional.empty()
        }
    }
}