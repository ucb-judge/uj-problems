package ucb.judge.ujproblems.config

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
        logger.error("UjNotFoundException: ${message}")
        return ResponseEntity(ResponseDto(
            data = null,
            message = message,
            successful = false
        ), HttpStatus.NOT_FOUND)
    }
}