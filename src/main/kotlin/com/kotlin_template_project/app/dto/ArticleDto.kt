package com.kotlin_template_project.app.dto

import com.kotlin_template_project.app.entity.ArticleEntity
import com.kotlin_template_project.app.entity.UserEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

data class ArticleDto(
    var id: UUID?,
    var title: String?,
    var headline: String?,
    var content: String?,

    var authorId: UUID?,

    var addedAt: LocalDateTime?
) {

    companion object {
        fun entityToDto(entity: ArticleEntity): ArticleDto {
            return ArticleDto(
                id = entity.id,
                title = entity.title,
                headline = entity.headline,
                content = entity.content,
                authorId = entity.author.id,
                addedAt = entity.addedAt
            )
        }

        fun entitiesToDtos(entities: Iterable<ArticleEntity>): List<ArticleDto> {
            return entities.map { entityToDto(it) }
        }

        fun dtoToEntity(dto: ArticleDto): ArticleEntity {
            return ArticleEntity(
                id = dto.id,
                title = dto.title,
                headline = dto.headline,
                content = dto.content,
                author = UserEntity(id = dto.authorId),
                addedAt = dto.addedAt
            )
        }

        fun dtosToEntities(dtos: List<ArticleDto>): Iterable<ArticleEntity> {
            return dtos.map { dtoToEntity(it) }
        }
    }
}