package ucb.judge.ujproblems.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    fun createProblem(@RequestBody newProblemDto: NewProblemDto): ResponseEntity<ResponseDto<Long>> {
        logger.info("POST: creating problem");
        val newProblem: Problem = problemBl.createProblem(newProblemDto);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = newProblem.problemId,
                message = "Success",
                successful = true
            )
        );
    }
}