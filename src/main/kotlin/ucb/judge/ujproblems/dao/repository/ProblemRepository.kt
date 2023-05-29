package ucb.judge.ujproblems.dao.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ucb.judge.ujproblems.dao.Problem

@Repository
interface ProblemRepository : JpaRepository<Problem, Long> {
    fun existsByProblemIdAndStatusIsTrue(problemId: Long): Boolean
}