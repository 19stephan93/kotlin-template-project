package com.kotlin_template_project.app.service

import java.util.*

interface IUserService<T> {
    fun findAll(): Iterable<T>

    fun findByUsername(username: String): Optional<T>
}