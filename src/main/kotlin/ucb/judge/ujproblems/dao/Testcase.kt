package ucb.judge.ujproblems.dao

import javax.persistence.*

@Entity
@Table(name = "testcase")
class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testcase_id")
    var testcaseId: Long = 0;

    @Column(name = "problem_id")
    var problemId: Long = 0;

    @Column(name = "testcase_number")
    var testcaseNumber: Int = 0;

    @Column(name = "s3_input")
    var s3Input: Long = 0;

    @Column(name = "s3_output")
    var s3Output: Long = 0;

    @Column(name = "is_sample")
    var isSample: Boolean = false;

    @Column
    var status: Boolean = true;

    constructor(problemId: Long, testcaseNumber: Int, s3Input: Long, s3Output: Long, isSample: Boolean, status: Boolean) {
        this.problemId = problemId
        this.testcaseNumber = testcaseNumber
        this.s3Input = s3Input
        this.s3Output = s3Output
        this.isSample = isSample
        this.status = status
    }
}