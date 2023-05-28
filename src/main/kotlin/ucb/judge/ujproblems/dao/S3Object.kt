package ucb.judge.ujproblems.dao

import javax.persistence.*

@Entity
@Table(name = "s3_object")
class S3Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s3_object_id")
    var s3ObjectId: Long = 0;

    @Column(name = "content_type")
    var contentType: String = "";

    @Column(name = "bucket")
    var bucket: String = "";

    @Column(name = "filename")
    var filename: String = "";

    @Column(name = "status")
    var status: Boolean = true;

    constructor(s3ObjectId: Long, contentType: String, bucket: String, filename: String, status: Boolean) {
        this.s3ObjectId = s3ObjectId
        this.contentType = contentType
        this.bucket = bucket
        this.filename = filename
        this.status = status
    }
}