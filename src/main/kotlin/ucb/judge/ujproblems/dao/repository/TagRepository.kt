package ucb.judge.ujproblems.dao.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ucb.judge.ujproblems.dao.Tag

@Repository
interface TagRepository: JpaRepository<Tag, Long> {
}