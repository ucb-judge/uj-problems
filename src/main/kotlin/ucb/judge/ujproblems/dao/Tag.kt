package ucb.judge.ujproblems.dao

import javax.persistence.*

@Entity
@Table(name = "tag")
class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    var tagId: Long = 0;

    @Column(name = "name")
    var name: String = "";

    @Column(name = "status")
    var status: Boolean = true;

    @OneToMany(fetch = FetchType.LAZY)
    var problemTags: List<ProblemTag>? = null;
}