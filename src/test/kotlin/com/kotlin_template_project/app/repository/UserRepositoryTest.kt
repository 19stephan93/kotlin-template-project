package com.kotlin_template_project.app.repository

import com.kotlin_template_project.app.entity.UserEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest  @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository
) {
    @Test
    fun `When findByLogin then return User`() {
        val juergen = UserEntity("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        entityManager.flush()
        val user = userRepository.findById(juergen.id!!)
        Assertions.assertThat(user.get()).isEqualTo(juergen)
    }
}