package ucb.judge.ujproblems.dao

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Entity
@Table(name = "problem_tag")
class ProblemTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_tag_id")
    var problemTagId: Long = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    var problem: Problem? = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    var tag: Tag? = null;

    @Column(name = "status")
    var status: Boolean = true;

    constructor(tag: Tag, problem: Problem) {
        this.problem = problem
        this.tag = tag
        this.status = true
    }
}