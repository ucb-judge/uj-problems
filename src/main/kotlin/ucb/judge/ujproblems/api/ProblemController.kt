package ucb.judge.ujproblems.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ucb.judge.ujproblems.bl.ProblemBl
import ucb.judge.ujproblems.dao.Problem
import ucb.judge.ujproblems.dto.NewProblemDto
import ucb.judge.ujproblems.dto.ResponseDto

@RestController
@RequestMapping("/api/v1/problems")
class ProblemController constructor(
    private val problemBl: ProblemBl
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProblemController::class.java)
    }

    @PostMapping
    fun createProblem(
        @RequestBody newProblemDto: NewProblemDto,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<ResponseDto<Long>> {
        logger.info("POST: creating problem");
        val newProblemId: Long = problemBl.createProblem(newProblemDto, token);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = newProblemId,
                message = "Success",
                successful = true
            )
        );
    }
}