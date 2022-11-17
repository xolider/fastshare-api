package dev.vicart.fastshareapi.repositories

import dev.vicart.fastshareapi.entities.CodeAddrEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface CodeAddrRepo : CrudRepository<CodeAddrEntity, Long> {

    fun findByCode(code: Int) : Optional<CodeAddrEntity>
}