package ucb.judge.ujproblems.mappers

import ucb.judge.ujproblems.dto.FileDto
import ucb.judge.ujproblems.dao.S3Object

class S3ObjectMapper {
    companion object {
        fun fromFileDto(fileDto: FileDto): S3Object {
            return S3Object(
                s3ObjectId = fileDto.s3ObjectId,
                contentType = fileDto.contentType,
                bucket = fileDto.bucket,
                filename = fileDto.filename,
                status = fileDto.status
            )
        }
    }
}