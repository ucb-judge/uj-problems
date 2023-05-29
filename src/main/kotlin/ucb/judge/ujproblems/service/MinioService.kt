package ucb.judge.ujproblems.service

import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MinioService constructor(
    private val minioClient: MinioClient
) {
    @Value("\${minio.url")
    private lateinit var minioUrl: String

    fun getSharedUrl(bucket: String, filename: String): String {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .`object`(filename)
                .expiry(60 * 60)
                .build()
        )
    }
}