package com.kotlin_template_project.app.dto

import com.kotlin_template_project.app.entity.ArticleEntity
import com.kotlin_template_project.app.entity.UserEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

class ArticleDto(
    var id: UUID?,
    var title: String?,
    var headline: String?,
    var content: String?,

    var authorId: UUID?,

    var addedAt: LocalDateTime?
) {

    companion object {
        fun entitiesToDtos(entities: Iterable<ArticleEntity>): List<ArticleDto> {
            return entities.map { ArticleDto(
                id = it.id,
                title = it.title,
                headline = it.headline,
                content = it.content,
                authorId = it.author.id,
                addedAt = it.addedAt
            ) }
        }

        fun dtosToEntities(dtos: List<ArticleDto>): Iterable<ArticleEntity> {
            return dtos.map { ArticleEntity(
                id = it.id,
                title = it.title,
                headline = it.headline,
                content = it.content,
                author = UserEntity(id = it.authorId),
                addedAt = it.addedAt
            ) }
        }
    }
}