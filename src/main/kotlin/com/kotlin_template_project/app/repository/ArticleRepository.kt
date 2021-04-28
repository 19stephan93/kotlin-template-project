package com.kotlin_template_project.app.repository

import com.kotlin_template_project.app.entity.ArticleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleRepository : IArticleRepository<ArticleEntity>, CrudRepository<ArticleEntity, UUID> {
    override fun findAllByOrderByAddedAtDesc(): Iterable<ArticleEntity>

    override fun findById(id: UUID): Optional<ArticleEntity>
}