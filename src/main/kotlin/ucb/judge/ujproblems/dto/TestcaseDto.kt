package ucb.judge.ujproblems.dto

data class TestcaseDto (
    var testcaseId: Long = 0,
    var testcaseNumber: Int = 0,
    var input: String = "",
    var expectedOutput: String = ""
)