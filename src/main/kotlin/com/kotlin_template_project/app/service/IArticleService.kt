package com.kotlin_template_project.app.service

import java.util.*

interface IArticleService<T> {
    fun findAll(): Iterable<T>

    fun findById(id: UUID): Optional<T>
}