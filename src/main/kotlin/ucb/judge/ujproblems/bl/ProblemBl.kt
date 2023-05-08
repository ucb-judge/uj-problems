package ucb.judge.ujproblems.bl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ucb.judge.ujproblems.dao.Problem
import ucb.judge.ujproblems.dao.Testcase
import ucb.judge.ujproblems.dao.repository.ProblemRepository
import ucb.judge.ujproblems.dao.repository.TestcaseRepository
import ucb.judge.ujproblems.dto.NewProblemDto
import ucb.judge.ujproblems.service.UjFileUploaderService
import ucb.judge.ujproblems.utils.FileUtils
import ucb.judge.ujproblems.utils.LatexSanitizer

@Service
class ProblemBl constructor(
    private val ujFileUploaderService: UjFileUploaderService,
    private val problemRepository: ProblemRepository,
    private val testcaseRepository: TestcaseRepository
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProblemBl::class.java)
    }

    fun createProblem(newProblem: NewProblemDto): Problem {
        logger.info("Starting business logic for problem creation");
        logger.info("Creating problem description file")
        // sanitize description
        val sanitizedDescription = LatexSanitizer.sanitizeLatex(newProblem.description);
        // sanitize input
        val sanitizedInput = LatexSanitizer.sanitizeLatex(newProblem.inputDescription);
        // sanitize output
        val sanitizedOutput = LatexSanitizer.sanitizeLatex(newProblem.outputDescription);
        val descriptionFile = FileUtils.createProblemFile(sanitizedDescription, sanitizedInput, sanitizedOutput);
        // upload problem description to minio
        val fileUploaderResponse = ujFileUploaderService.uploadFile(descriptionFile, "problems");
        val s3DescriptionId = fileUploaderResponse.data;
        logger.info("Problem description file created and uploaded to minio")
        // store problem in database
        // FIXME: use professorId from token using keycloak
        logger.info("Storing problem in database")
        val problem = Problem(1, newProblem.title, newProblem.isPublic, s3DescriptionId!!, newProblem.maxTime, newProblem.maxMemory, true);
        val savedProblem = problemRepository.save(problem);
        val problemId = savedProblem.problemId;

        logger.info("Storing problem testcases in database")
        // store problem testcases in minio
        for ((index, testcase) in newProblem.testcases.withIndex()) {
            // upload input
            val inputFile = FileUtils.createTextFile(testcase.input, "${problemId}-input-${index}");
            val inputUploadResponse = ujFileUploaderService.uploadFile(inputFile, "inputs", true);
            val inputId = inputUploadResponse.data;
            // upload output
            val outputFile = FileUtils.createTextFile(testcase.output, "${problemId}-output-${index}");
            val outputUploadResponse = ujFileUploaderService.uploadFile(outputFile, "outputs", true);
            val outputId = outputUploadResponse.data;
            // store testcase in database
            val newTestcase = Testcase(problemId, index + 1, inputId!!, outputId!!, testcase.isSample, true);
            testcaseRepository.save(newTestcase)
        }
        logger.info("Storing admitted languages and tags in database")
        // store admitted languages in database
        for(language in newProblem.languages) {
            // store language in database
//            problemRepository.saveLanguage(problemId, language);
        }
        // store problem tags in database
        for(tag in newProblem.tags) {
            // store tag in database
//            problemRepository.saveTag(problemId, tag);

        }
        return savedProblem;
    }
}