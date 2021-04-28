package com.kotlin_template_project.app.controller

import com.kotlin_template_project.app.dto.ArticleDto
import com.kotlin_template_project.app.service.IArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/article")
class ArticleController @Autowired constructor(
    val articleService: IArticleService<ArticleDto>
) {

    @GetMapping("/")
    fun findAll() = articleService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ArticleDto {
        val resultOpt: Optional<ArticleDto> = articleService.findById(id)
        if (resultOpt.isPresent) {
            return resultOpt.get()
        }
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
    }
}