package com.kotlin_template_project.app.entity

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "articles")
@Table(name = "articles")
class ArticleEntity(
    var title: String?,
    var headline: String?,
    var content: String?,

    @ManyToOne
    var author: UserEntity,

    var addedAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null
)