package ucb.judge.ujproblems.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NewProblemDto(
    val title: String = "",
    @JsonProperty("isPublic")
    val isPublic: Boolean = false,
    val maxTime: Double = 0.0,
    val maxMemory: Int = 0,
    val description: String = "",
    val inputDescription: String = "",
    val outputDescription: String = "",
    val testcases: Array<NewTestcaseDto> = emptyArray(),
    val languages: Array<Long> = emptyArray(),
    val tags: Array<Long> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewProblemDto

        if (title != other.title) return false
        if (isPublic != other.isPublic) return false
        if (maxTime != other.maxTime) return false
        if (maxMemory != other.maxMemory) return false
        if (description != other.description) return false
        if (inputDescription != other.inputDescription) return false
        if (outputDescription != other.outputDescription) return false
        if (!testcases.contentEquals(other.testcases)) return false
        if (!languages.contentEquals(other.languages)) return false
        return tags.contentEquals(other.tags)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + isPublic.hashCode()
        result = 31 * result + maxTime.hashCode()
        result = 31 * result + maxMemory
        result = 31 * result + description.hashCode()
        result = 31 * result + inputDescription.hashCode()
        result = 31 * result + outputDescription.hashCode()
        result = 31 * result + testcases.contentHashCode()
        result = 31 * result + languages.contentHashCode()
        result = 31 * result + tags.contentHashCode()
        return result
    }
}

