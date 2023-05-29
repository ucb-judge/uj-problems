package ucb.judge.ujproblems.mappers

import ucb.judge.ujproblems.dao.Tag
import ucb.judge.ujproblems.dto.TagDto

class TagMapper {
    companion object {
        fun entityToDto(tag: Tag): TagDto {
            return TagDto(
                tag.tagId,
                tag.name
            )
        }
    }
}