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

    /**
     * Method to create a new problem. For this, the user should have the role of professor.
     * @param newProblemDto: DTO with the information of the new problem.
     * @param token: JWT token of the user.
     * @return ResponseEntity<ResponseDto<Long>>: Response with the id of the new problem.
     */
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