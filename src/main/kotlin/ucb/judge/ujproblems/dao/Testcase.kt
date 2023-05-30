package ucb.judge.ujproblems.dao

import org.hibernate.annotations.Cascade
import javax.persistence.*

@Entity
@Table(name = "testcase")
class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testcase_id")
    var testcaseId: Long = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "problem_id", nullable = false)
    var problem: Problem? = null;

    @Column(name = "testcase_number")
    var testcaseNumber: Int = 0;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "s3_input")
    var s3Input: S3Object? = null;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "s3_output")
    var s3Output: S3Object? = null;

    @Column(name = "is_sample")
    var isSample: Boolean = false;

    @Column(name = "status")
    var status: Boolean = true;

    constructor(problem: Problem, testcaseNumber: Int, s3Input: S3Object, s3Output: S3Object, isSample: Boolean) {
        this.problem = problem
        this.testcaseNumber = testcaseNumber
        this.s3Input = s3Input
        this.s3Output = s3Output
        this.isSample = isSample
        this.status = true
    }
}