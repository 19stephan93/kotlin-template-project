package com.kotlin_template_project.app.configs

import com.kotlin_template_project.app.entities.Article
import com.kotlin_template_project.app.entities.User
import com.kotlin_template_project.app.repositories.ArticleRepository
import com.kotlin_template_project.app.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository
    ) = ApplicationRunner {

        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
        articleRepository.save(
            Article(
            title = "Reactor Bismuth is out",
            headline = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        )
        )
        articleRepository.save(
            Article(
            title = "Reactor Aluminium has landed",
            headline = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        )
        )
    }
}