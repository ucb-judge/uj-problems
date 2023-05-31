package ucb.judge.ujproblems.dao.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ucb.judge.ujproblems.dao.Problem

@Repository
interface ProblemRepository : JpaRepository<Problem, Long> {
    fun existsByProblemIdAndStatusIsTrue(problemId: Long): Boolean

    @Query("""
        SELECT p FROM Problem p
        WHERE p.status = true
        AND p.isPublic = true
        ORDER BY p.problemId ASC
    """)
    fun findAll(pageRequest: PageRequest): Page<Problem>
}