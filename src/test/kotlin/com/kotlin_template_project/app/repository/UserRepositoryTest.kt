package com.kotlin_template_project.app.repository

import com.kotlin_template_project.app.entity.UserEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.2")

        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
            registry.add("spring.liquibase.url", postgresContainer::getJdbcUrl)
            registry.add("spring.liquibase.user", postgresContainer::getUsername)
            registry.add("spring.liquibase.password", postgresContainer::getPassword)
        }
    }

//    @Autowired
//    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository


    @Test
    fun `Find all test users`() {
        val testUser1 = UserEntity("testUser1", "john", "grey")
        val realUser = UserEntity("richard", "richard", "mcallen")
        val testUser2 = UserEntity("testUser2", "alex", "petersburg")

        userRepository.save(testUser1)
        userRepository.save(realUser)
        userRepository.save(testUser2)

        val testUsers: List<UserEntity> = userRepository.findTestUsers().toList()

        Assertions.assertThat(testUsers.size).isEqualTo(2)
        Assertions.assertThat(testUsers[0]).isEqualTo(testUser1)
        Assertions.assertThat(testUsers[1]).isEqualTo(testUser2)
    }
}