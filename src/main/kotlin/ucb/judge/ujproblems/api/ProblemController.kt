package ucb.judge.ujproblems.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ucb.judge.ujproblems.bl.ProblemBl
import ucb.judge.ujproblems.dto.*

@RestController
@RequestMapping("/api/v1/problems")
class ProblemController constructor(
    private val problemBl: ProblemBl
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProblemController::class.java)
    }

    /**
     * Method to get a problem by its id. For this, the user should have either the role of professor or student.
     * @param problemId: Id of the problem.
     * @return ResponseEntity<ResponseDto<ProblemDto>>: Response with the problem.
     * @throws NotFoundException: If the problem does not exist.
     */
    @GetMapping("/{id}")
    fun getProblemById(
        @PathVariable id: Long
    ): ResponseEntity<ResponseDto<ProblemDto>> {
        logger.info("GET: getting problem by id");
        val problem: ProblemDto = problemBl.getProblemById(id);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = problem,
                message = "Success",
                successful = true
            )
        );
    }

    /**
     * Method to verify if a problem exists. For this, the client should have the role uj-problems-access.
     * @param problemId: Id of the problem.
     * @return ResponseEntity<ResponseDto<Boolean>>: Response with the verification.
     */
    @GetMapping("/{id}/exists")
    fun existsProblemById(
        @PathVariable id: Long
    ): ResponseEntity<ResponseDto<Boolean>> {
        logger.info("GET: verifying if problem exists");
        val exists: Boolean = problemBl.existsProblemById(id);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = exists,
                message = "Success",
                successful = true
            )
        );
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
    ): ResponseEntity<ResponseDto<Long>> {
        logger.info("POST: creating problem");
        val newProblemId: Long = problemBl.createProblem(newProblemDto);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = newProblemId,
                message = "Success",
                successful = true
            )
        );
    }

    /**
     * Method to get a problem's testcases by its id.
     * @param problemId: Id of the problem.
     * @return ResponseEntity<ResponseDto<List<TestcaseDto>>>: Response with the testcases.
     */
    @GetMapping("/{id}/testcases")
    fun getProblemTestcases(@PathVariable id: Long): ResponseEntity<ResponseDto<List<TestcaseDto>>> {
        logger.info("GET: getting problem testcases");
        val testcases: List<TestcaseDto> = problemBl.getProblemTestcases(id);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = testcases,
                message = "Success",
                successful = true
            )
        );
    }

    /**
     * Method to get a problem's limits by its id.
     * @param problemId: Id of the problem.
     * @return ResponseEntity<ResponseDto<ProblemLimitsDto>>: Response with the time and memory limits.
     */
    @GetMapping("/{id}/limits")
    fun getProblemLimits(@PathVariable id: Long): ResponseEntity<ResponseDto<ProblemLimitsDto>> {
        logger.info("GET: getting problem limits");
        val limits: ProblemLimitsDto = problemBl.getProblemLimits(id);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = limits,
                message = "Success",
                successful = true
            )
        );
    }

    /**
     * Method to get a problem's minimal information by its id.
     */
    @GetMapping("/{id}/minimal")
    fun getProblemMinimalInfo(@PathVariable id: Long): ResponseEntity<ResponseDto<ProblemMinimalDto>> {
        logger.info("GET: getting problem minimal info");
        val minimalInfo: ProblemMinimalDto = problemBl.getProblemMinimalInfo(id);
        logger.info("Sending response");
        return ResponseEntity.ok(
            ResponseDto(
                data = minimalInfo,
                message = "Success",
                successful = true
            )
        );
    }
}
