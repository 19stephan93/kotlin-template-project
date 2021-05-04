package com.kotlin_template_project.app.repository

interface IUserRepository<T> {
    fun findAll(): Iterable<T>

    fun findByUsername(username: String): T

    fun findTestUsers(): Iterable<T>
}