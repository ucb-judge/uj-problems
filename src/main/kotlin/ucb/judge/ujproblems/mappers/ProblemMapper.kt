package ucb.judge.ujproblems.mappers

import ucb.judge.ujproblems.dao.Problem
import ucb.judge.ujproblems.dto.ProblemDto
import ucb.judge.ujproblems.service.MinioService

class ProblemMapper constructor() {
    companion object {
        fun entityToDto(problem: Problem, minioService: MinioService): ProblemDto {
            val sampleInputs: List<String> = problem.testcases!!.filter { it.isSample }.map {
                String(minioService.getFile(it.s3Input!!.bucket, it.s3Input!!.filename))
            }
            val sampleOutputs: List<String> = problem.testcases!!.filter { it.isSample }.map {
                String(minioService.getFile(it.s3Output!!.bucket, it.s3Output!!.filename))
            }
            return ProblemDto(
                problemId = problem.problemId,
                title = problem.title,
                description = String(minioService.getFile(problem.s3Description!!.bucket, problem.s3Description!!.filename)),
                sampleInputs = sampleInputs,
                sampleOutputs = sampleOutputs,
                timeLimit = problem.maxTime,
                memoryLimit = problem.maxMemory,
                tags = problem.problemTags!!.filter{ it.status }.map { TagMapper.entityToDto(it.tag!!) },
                admittedLanguages = problem.admittedLanguages!!.filter{ it.status }.map { LanguageMapper.entityToDto(it.language!!) }
            )
        }
    }
}