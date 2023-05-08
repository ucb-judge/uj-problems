package ucb.judge.ujproblems.dto

data class ResponseDto<T>(
    val data: T? = null,
    val message: String = "",
    val successful: Boolean = false
);
