package dev.vicart.fastshareapi.controllers

import dev.vicart.fastshareapi.entities.CodeAddrEntity
import dev.vicart.fastshareapi.repositories.CodeAddrRepo
import dev.vicart.fastshareapi.utils.CodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.Date
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private lateinit var repo: CodeAddrRepo

    @GetMapping("/code")
    fun requestCode(request: HttpServletRequest) : Int {
        val remoteAddr = request.remoteAddr

        val newCode = CodeUtil.generateCode()

        val codeAddr = CodeAddrEntity()
        codeAddr.remoteAddr = if(remoteAddr == "0:0:0:0:0:0:0:1") "127.0.0.1" else remoteAddr
        codeAddr.code = newCode
        codeAddr.createdAt = Date.from(Instant.now())

        repo.save(codeAddr)

        return newCode
    }

    @GetMapping("/address")
    fun getAddress(@RequestParam code: Int) : ResponseEntity<String> {
        val entity = repo.findByCode(code)

        if(entity.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(entity.get().remoteAddr)
    }
}