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

/**
 * Main controller used to map pin-to-address and address-to-pin
 */
@RestController
@RequestMapping("/api")
class MainController {

    /**
     * JPA Repository for pin/address mapping
     */
    @Autowired
    private lateinit var repo: CodeAddrRepo

    /**
     * This controller function receives a GET request, generates a PIN code and stores the public IP address of the origin
     * with the PIN code.
     * @param request HTTP request
     * @return the pin code for receiving file
     */
    @GetMapping("/code")
    fun requestCode(request: HttpServletRequest) : Int {
        val remoteAddr = request.remoteAddr

        val newCode = CodeUtil.generateCode()

        val codeAddr = CodeAddrEntity()
        codeAddr.remoteAddr = if(remoteAddr == "0:0:0:0:0:0:0:1") "127.0.0.1" else remoteAddr //For local debug
        codeAddr.code = newCode
        codeAddr.createdAt = Date.from(Instant.now())

        repo.save(codeAddr)

        return newCode
    }

    /**
     * This controller function receives a PIN code to get the mapping from the {@link MainController#repo} and returns the public IP address of the receiver
     * @param code The code we want to receive the IP address from
     * @return The public IP address as a ResponseEntity
     */
    @GetMapping("/address")
    fun getAddress(@RequestParam code: Int) : ResponseEntity<String> {
        val entity = repo.findByCode(code)

        if(entity.isEmpty) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(entity.get().remoteAddr)
    }
}