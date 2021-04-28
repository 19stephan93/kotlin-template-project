package com.kotlin_template_project.app.repository

interface IUserRepository<T> {
    fun findAll(): Iterable<T>

    fun findByUsername(login: String): T
}