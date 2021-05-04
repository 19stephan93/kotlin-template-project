package com.kotlin_template_project.app.entity

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "users")
@Table(name = "users")
class UserEntity(
    var username: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var description: String? = null,

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null
)