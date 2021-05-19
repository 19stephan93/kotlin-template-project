package com.kotlin_template_project.app.service

import com.kotlin_template_project.app.dto.ArticleDto
import com.kotlin_template_project.app.entity.ArticleEntity
import com.kotlin_template_project.app.entity.UserEntity
import com.kotlin_template_project.app.repository.IArticleRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class ArticleServiceTest {
    @MockK
    lateinit var articleRepository: IArticleRepository<ArticleEntity>

    @InjectMockKs
    var articleService: IArticleService<ArticleDto> = ArticleService()

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testFindAll() {
        val firstUser = UserEntity(username = "a", firstname = "b", lastname = "c")
        val secondUser = UserEntity(username = "b", firstname = "c", lastname = "d")
        val thirdUser = UserEntity(username = "c", firstname = "d", lastname = "e")

        val time: LocalDateTime = LocalDateTime.now()
        val firstArticle = ArticleEntity(title = "a", headline = "b", content = "c", author = firstUser, addedAt = time.plusMinutes(3))
        val secondArticle = ArticleEntity(title = "b", headline = "c", content = "d", author = secondUser, addedAt = time.plusMinutes(1))
        val thirdArticle = ArticleEntity(title = "c", headline = "d", content = "e", author = thirdUser, addedAt = time.plusMinutes(2))

        val allArticles: List<ArticleEntity> = listOf(secondArticle, thirdArticle, firstArticle)
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns allArticles.asIterable()

        val articleDtos: List<ArticleDto> = articleService.findAll().toList()

        Assertions.assertThat(articleDtos.size).isEqualTo(3)
        Assertions.assertThat(articleDtos[0]).isEqualTo(ArticleDto.entityToDto(secondArticle))
        Assertions.assertThat(articleDtos[1]).isEqualTo(ArticleDto.entityToDto(thirdArticle))
        Assertions.assertThat(articleDtos[2]).isEqualTo(ArticleDto.entityToDto(firstArticle))
    }

    @Test
    fun testFindById() {
        val user = UserEntity(username = "a", firstname = "b", lastname = "c")
        val time: LocalDateTime = LocalDateTime.now()
        val article = ArticleEntity(title = "a", headline = "b", content = "c", author = user, addedAt = time)
        every { articleRepository.findById(UUID.fromString("6a1c6540-5b9d-4cb8-9a51-642038c5cecc")) } returns Optional.of(article)

        every { articleRepository.findById(UUID.fromString("44e5a5e7-e625-48ba-b3ec-2a4d3081cd88")) } returns Optional.empty()

        val articleDtoOpt = articleService.findById(UUID.fromString("6a1c6540-5b9d-4cb8-9a51-642038c5cecc"))
        val noArticleDtoOpt = articleService.findById(UUID.fromString("44e5a5e7-e625-48ba-b3ec-2a4d3081cd88"))
        Assertions.assertThat(articleDtoOpt.isPresent).isEqualTo(true)
        Assertions.assertThat(noArticleDtoOpt.isPresent).isEqualTo(true)

        Assertions.assertThat(articleDtoOpt.get()).isEqualTo(ArticleDto.entityToDto(article))
    }
}