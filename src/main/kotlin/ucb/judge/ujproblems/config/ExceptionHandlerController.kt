package ucb.judge.ujproblems.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ucb.judge.ujproblems.dto.ResponseDto
import ucb.judge.ujproblems.exception.UjNotFoundException

@ControllerAdvice
class ExceptionHandlerController {
    companion object {
        private val logger = LoggerFactory.getLogger(ExceptionHandlerController::class.java)
    }

    @ExceptionHandler(UjNotFoundException::class)
    fun handleUjNotFoundException(e: UjNotFoundException): ResponseEntity<ResponseDto<Nothing>> {
        val message = e.message ?: "Not found"
        logger.error("UjNotFoundException: $message")
        return ResponseEntity(ResponseDto(
            data = null,
            message = message,
            successful = false
        ), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(ex: FeignException): ResponseEntity<ResponseDto<Nothing>> {
        val http = mapOf(
            400 to HttpStatus.BAD_REQUEST,
            401 to HttpStatus.UNAUTHORIZED,
            403 to HttpStatus.FORBIDDEN,
            404 to HttpStatus.NOT_FOUND,
            500 to HttpStatus.INTERNAL_SERVER_ERROR,
        )
        val objectMapper = jacksonObjectMapper()
        val errorMessage = ex.contentUTF8()
        val responseDto = objectMapper.readValue(errorMessage, ResponseDto::class.java)
        logger.error("Error message: $errorMessage")
        return ResponseEntity(ResponseDto(null, responseDto.message, false),http[ex.status()]!!)
    }
}