package ucb.judge.ujproblems.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NewTestcaseDto(
    val input: String = "",
    val output: String = "",
    @JsonProperty("isSample")
    val isSample: Boolean = false
)
