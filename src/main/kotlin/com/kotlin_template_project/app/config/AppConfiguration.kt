package com.kotlin_template_project.app.config

import com.kotlin_template_project.app.entity.ArticleEntity
import com.kotlin_template_project.app.entity.UserEntity
import com.kotlin_template_project.app.repository.ArticleRepository
import com.kotlin_template_project.app.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository
    ) = ApplicationRunner {

        val smaldini = userRepository.save(UserEntity("smaldini", "Stéphane", "Maldini"))
        articleRepository.save(
            ArticleEntity(
            title = "Reactor Bismuth is out",
            headline = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        )
        )
        articleRepository.save(
            ArticleEntity(
            title = "Reactor Aluminium has landed",
            headline = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        )
        )
    }
}