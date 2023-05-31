package ucb.judge.ujproblems.dto

data class ProblemTableDataDto(
    var problemId: Long = 0,
    var title: String = "",
    var timeLimit: Double = 0.0,
    var memoryLimit: Int = 0,
)
