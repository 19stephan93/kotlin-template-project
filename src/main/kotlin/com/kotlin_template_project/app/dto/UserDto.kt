package com.kotlin_template_project.app.dto

import com.kotlin_template_project.app.entity.UserEntity
import java.util.*

class UserDto(
    var id: UUID?,
    var username: String?,
    var firstname: String?,
    var lastname: String?,
    var description: String?,
) {

    companion object {
        fun entitiesToDtos(entities: Iterable<UserEntity>): List<UserDto> {
            return entities.map { UserDto(
                id = it.id,
                username = it.username,
                firstname = it.firstname,
                lastname = it.lastname,
                description = it.description
            ) }
        }

        fun dtosToEntities(dtos: List<UserDto>): Iterable<UserEntity> {
            return dtos.map { UserEntity(
                id = it.id,
                username = it.username,
                firstname = it.firstname,
                lastname = it.lastname,
                description = it.description
            ) }
        }
    }
}