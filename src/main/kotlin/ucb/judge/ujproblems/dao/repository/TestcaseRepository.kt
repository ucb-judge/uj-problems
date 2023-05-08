package ucb.judge.ujproblems.dao.repository

import org.springframework.data.jpa.repository.JpaRepository
import ucb.judge.ujproblems.dao.Testcase

interface TestcaseRepository : JpaRepository<Testcase, Long> {
}