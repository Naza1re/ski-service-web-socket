package com.kotlin.skiservice.list.provider

import com.kotlin.skiservice.dto.list.ListItemResponseValue
import com.kotlin.skiservice.entities.enums.Skill
import com.kotlin.skiservice.list.ListProvider
import org.springframework.stereotype.Service

@Service
class SkillListProvider : ListProvider<Skill> {

    companion object {
        const val SKILL_LIST = "skill"
    }

    override fun match(string: String): Boolean {
        return string == SKILL_LIST
    }

    override fun getList(): List<ListItemResponseValue> {
        return Skill.entries.map { item->
            ListItemResponseValue(item.value, item.name)
        }
    }

}