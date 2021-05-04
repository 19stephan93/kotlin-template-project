package com.kotlin_template_project.app.service

import com.kotlin_template_project.app.dto.UserDto
import com.kotlin_template_project.app.entity.UserEntity
import com.kotlin_template_project.app.repository.IUserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.dao.EmptyResultDataAccessException

class UserServiceTest {
    @MockK
    lateinit var userRepository: IUserRepository<UserEntity>

    @InjectMockKs
    var userService: IUserService<UserDto> = UserService()

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testFindAll() {
        val first = UserEntity(username = "a", firstname = "b", lastname = "c")
        val second = UserEntity(username = "b", firstname = "c", lastname = "d")
        val third = UserEntity(username = "c", firstname = "d", lastname = "e")

        val allUsers: List<UserEntity> = listOf(first, second, third)
        every { userRepository.findAll() } returns allUsers.asIterable()

        val userDtos: List<UserDto> = userService.findAll().toList()

        Assertions.assertThat(userDtos.size).isEqualTo(3)
        Assertions.assertThat(userDtos[0]).isEqualTo(UserDto.entityToDto(first))
        Assertions.assertThat(userDtos[1]).isEqualTo(UserDto.entityToDto(second))
        Assertions.assertThat(userDtos[2]).isEqualTo(UserDto.entityToDto(third))
    }

    @Test
    fun testByUsername() {
        val user = UserEntity(username = "a", firstname = "b", lastname = "c")
        every { userRepository.findByUsername("a") } returns user

        every { userRepository.findByUsername("noUser") } throws EmptyResultDataAccessException(1)

        val userDtoOpt = userService.findByUsername("a")
        val noUserDtoOpt = userService.findByUsername("noUser")
        Assertions.assertThat(userDtoOpt.isPresent).isEqualTo(true)
        Assertions.assertThat(noUserDtoOpt.isPresent).isEqualTo(false)

        Assertions.assertThat(userDtoOpt.get()).isEqualTo(UserDto.entityToDto(user))
    }
}