package ucb.judge.ujproblems.dao

import javax.persistence.*

@Entity
@Table(name = "problem")
class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    var problemId: Long = 0;
    @Column(name = "professor_id")
    var professorId: Long = 0;
    var title: String = "";
    @Column(name = "is_public")
    var isPublic: Boolean = false;
    @Column(name = "s3_description")
    var s3Description: Long = 0;
    @Column(name = "max_time")
    var maxTime: Double = 0.0;
    @Column(name = "max_memory")
    var maxMemory: Int = 0;
    @Column(name = "status")
    var status: Boolean = true;

    constructor(professorId: Long, title: String, isPublic: Boolean, s3Description: Long, maxTime: Double, maxMemory: Int, status: Boolean) {
        this.professorId = professorId
        this.title = title
        this.isPublic = isPublic
        this.s3Description = s3Description
        this.maxTime = maxTime
        this.maxMemory = maxMemory
        this.status = status
    }
}