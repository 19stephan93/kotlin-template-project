package com.kotlin_template_project.app.repository

import java.util.*

interface IArticleRepository<T> {
    fun findAllByOrderByAddedAtDesc(): Iterable<T>

    fun findById(id: UUID): Optional<T>
}