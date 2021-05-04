package com.kotlin_template_project.app.dto

import com.kotlin_template_project.app.entity.UserEntity
import java.util.*

data class UserDto(
    var id: UUID?,
    var username: String?,
    var firstname: String?,
    var lastname: String?,
    var description: String?,
) {

    companion object {
        fun entityToDto(entity: UserEntity): UserDto {
            return UserDto(
                id = entity.id,
                username = entity.username,
                firstname = entity.firstname,
                lastname = entity.lastname,
                description = entity.description
            )
        }

        fun entitiesToDtos(entities: Iterable<UserEntity>): Iterable<UserDto> {
            return entities.map { entityToDto(it) }
        }

        fun dtoToEntity(dto: UserDto): UserEntity {
            return UserEntity(
                id = dto.id,
                username = dto.username,
                firstname = dto.firstname,
                lastname = dto.lastname,
                description = dto.description
            )
        }

        fun dtosToEntities(dtos: Iterable<UserDto>): Iterable<UserEntity> {
            return dtos.map { dtoToEntity(it) }
        }
    }
}