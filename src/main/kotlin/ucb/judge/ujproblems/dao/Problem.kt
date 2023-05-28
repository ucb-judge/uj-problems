package ucb.judge.ujproblems.dao

import javax.persistence.*

@Entity
@Table(name = "problem")
class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    var problemId: Long = 0;

    @Column(name = "title")
    var title: String = "";

    @OneToOne
    @JoinColumn(name = "s3_description_id")
    var s3Description: S3Object? = null;

    @Column(name = "max_time")
    var maxTime: Double = 0.0;

    @Column(name = "max_memory")
    var maxMemory: Int = 0;

    @Column(name = "is_public")
    var isPublic: Boolean = true;

    @Column(name = "status")
    var status: Boolean = true;

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY, mappedBy = "problem")
    var problemTags: List<ProblemTag>? = null;

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY, mappedBy = "problem")
    var admittedLanguages: List<AdmittedLanguage>? = null;

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY, mappedBy = "problem")
    var testcases: List<Testcase>? = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    var professor: Professor? = null;

    constructor()
}