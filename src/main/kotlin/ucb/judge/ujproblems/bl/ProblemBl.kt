package ucb.judge.ujproblems.bl

import org.checkerframework.checker.nullness.Opt.orElseThrow
import org.keycloak.KeycloakSecurityContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ucb.judge.ujproblems.dao.*
import ucb.judge.ujproblems.dao.repository.*
import ucb.judge.ujproblems.dto.NewProblemDto
import ucb.judge.ujproblems.dto.ProblemDto
import ucb.judge.ujproblems.exception.UjNotFoundException
import ucb.judge.ujproblems.mappers.ProblemMapper
import ucb.judge.ujproblems.mappers.S3ObjectMapper
import ucb.judge.ujproblems.service.MinioService
import ucb.judge.ujproblems.service.UjFileUploaderService
import ucb.judge.ujproblems.utils.FileUtils
import ucb.judge.ujproblems.utils.KeycloakSecurityContextHolder
import ucb.judge.ujproblems.utils.LatexSanitizer

@Service
class ProblemBl constructor(
    private val ujFileUploaderService: UjFileUploaderService,
    private val problemRepository: ProblemRepository,
    private val testcaseRepository: TestcaseRepository,
    private val languageRepository: LanguageRepository,
    private val tagRepository: TagRepository,
    private val professorRepository: ProfessorRepository,
    private val minioService: MinioService
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProblemBl::class.java)
    }

    /** Business logic to get a problem by its id. This method will return the problem with the given id.
     * @param problemId: Id of the problem.
     * @return ProblemDto: Problem with the given id.
     */
    fun getProblemById(problemId: Long): ProblemDto {
        val problem: Problem = problemRepository.findById(problemId)
            .orElseThrow { UjNotFoundException("Problem not found") };
        if(!problem.isPublic && !problem.professor!!.kcUuid.equals(KeycloakSecurityContextHolder.getSubject())) {
            throw UjNotFoundException("Problem not found");
        }
        return ProblemMapper.entityToDto(problem, minioService);
    }

    /**
     * Business logic to create a new problem. This method will create a new problem in the database,
     * and will upload the files to MinIO.
     * @param newProblem: DTO with the information of the new problem.
     * @param token: JWT token of the user.
     * @return Long: Id of the new problem.
     */
    fun createProblem(newProblem: NewProblemDto): Long {
        logger.info("Starting business logic for problem creation");
        // get kc uuid
        val kcUuid = KeycloakSecurityContextHolder.getSubject() ?: throw UjNotFoundException("User not found");
        val professor = professorRepository.findByKcUuid(kcUuid)
            ?: throw UjNotFoundException("Professor not found");

        logger.info("Creating problem for professor ${professor.professorId}");
        val newProblemEntity = Problem();

        newProblemEntity.title = newProblem.title;
        newProblemEntity.isPublic = newProblem.isPublic;
        newProblemEntity.maxTime = newProblem.maxTime;
        newProblemEntity.maxMemory = newProblem.maxMemory;
        newProblemEntity.professor = professor;
        newProblemEntity.status = true;

        // get admitted languages
        val admittedLanguages = ArrayList<AdmittedLanguage>();
        for(language in newProblem.languages) {
            // store language in database
            val languageEntity = languageRepository.findById(language)
                .orElseThrow { UjNotFoundException("Language not found") };
            admittedLanguages.add(AdmittedLanguage(languageEntity, newProblemEntity))
        }
        newProblemEntity.admittedLanguages = admittedLanguages;

        // get problem tags
        val problemTags = ArrayList<ProblemTag>();
        for(tag in newProblem.tags) {
            // store tag in database
            val tagEntity = tagRepository.findById(tag)
                .orElseThrow() { UjNotFoundException("Tag not found") };
            problemTags.add(ProblemTag(tagEntity, newProblemEntity));
        }
        newProblemEntity.problemTags = problemTags;

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
        val fileDto = fileUploaderResponse.data;
        logger.info("Problem description file created and uploaded to minio")

        val s3Object = S3ObjectMapper.fromFileDto(fileDto!!);
        newProblemEntity.s3Description = s3Object;

        // store problem in database
        logger.info("Storing problem in database")
        val savedEntity = problemRepository.save(newProblemEntity);
        val problemId = savedEntity.problemId;

        for ((index, testcase) in newProblem.testcases.withIndex()) {
            // upload input
            val inputFile = FileUtils.createTextFile(testcase.input, "${problemId}-input-${index}");
            val inputUploadResponse = ujFileUploaderService.uploadFile(inputFile, "inputs", true);
            val inputFileDto = inputUploadResponse.data;
            // upload output
            val outputFile = FileUtils.createTextFile(testcase.output, "${problemId}-output-${index}");
            val outputUploadResponse = ujFileUploaderService.uploadFile(outputFile, "outputs", true);
            val outputFileDto = outputUploadResponse.data;
            // store testcase in database
            val inputEntity = S3ObjectMapper.fromFileDto(inputFileDto!!);
            val outputEntity = S3ObjectMapper.fromFileDto(outputFileDto!!);
            val newTestcase = Testcase(savedEntity, index + 1, inputEntity, outputEntity, testcase.isSample);
            testcaseRepository.save(newTestcase)
        }
        return savedEntity.problemId;
    }
}