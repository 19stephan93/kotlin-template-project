package com.kotlin_template_project.app.service

import com.kotlin_template_project.app.dto.ArticleDto
import com.kotlin_template_project.app.entity.ArticleEntity
import com.kotlin_template_project.app.repository.IArticleRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleService(
    @Qualifier(value = "articleRepository")
    var articleRepository: IArticleRepository<ArticleEntity>
) : IArticleService<ArticleDto> {

    override fun findAll(): List<ArticleDto> = ArticleDto.entitiesToDtos(articleRepository.findAllByOrderByAddedAtDesc())

    override fun findById(id: UUID): Optional<ArticleDto> {
        val optEntity: Optional<ArticleEntity> = articleRepository.findById(id)
        if (optEntity.isEmpty) {
            return Optional.empty()
        }
        return Optional.of(ArticleDto.entitiesToDtos(listOf(optEntity.get()))[0])
    }
}