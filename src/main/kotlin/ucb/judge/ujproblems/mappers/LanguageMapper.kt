package ucb.judge.ujproblems.mappers

import ucb.judge.ujproblems.dao.Language
import ucb.judge.ujproblems.dto.LanguageDto

class LanguageMapper {
    companion object {
        fun entityToDto(language: Language): LanguageDto {
            return LanguageDto(
                language.languageId,
                language.name
            )
        }
    }
}