package com.kotlin_template_project.app.repository

import com.kotlin_template_project.app.entity.UserEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Find all test users`() {
        val testUser1 = UserEntity("testUser1", "john", "grey")
        val realUser = UserEntity("richard", "richard", "mcallen")
        val testUser2 = UserEntity("testUser2", "alex", "petersburg")

        entityManager.persist(testUser1)
        entityManager.persist(realUser)
        entityManager.persist(testUser2)
        entityManager.flush()

        val testUsers: List<UserEntity> = userRepository.findTestUsers().toList()

        Assertions.assertThat(testUsers.size).isEqualTo(2)
        Assertions.assertThat(testUsers[0]).isEqualTo(testUser1)
        Assertions.assertThat(testUsers[1]).isEqualTo(testUser2)
    }
}