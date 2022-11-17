package dev.vicart.fastshareapi.entities

import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "code_addr")
class CodeAddrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var code: Int = -1

    var remoteAddr: String? = null

    var createdAt: Date? = null
}