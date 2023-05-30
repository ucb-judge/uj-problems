package ucb.judge.ujproblems.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import ucb.judge.ujproblems.dto.FileDto
import ucb.judge.ujproblems.dto.ResponseDto

/**
 * Interface to communicate with the uj-file-uploader service.
 */
@FeignClient(name = "uj-file-uploader")
interface UjFileUploaderService {

    @PostMapping("/api/v1/files", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(
        @RequestPart(value = "file") file: MultipartFile,
        @RequestPart(value = "bucket") bucket: String,
        @RequestHeader(value = "Authorization") authorization: String,
        @RequestPart(value = "customFilename") customFilename: Boolean = false
    ): ResponseDto<FileDto>
}