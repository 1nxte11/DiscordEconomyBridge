package me.pliexe.discordeconomybridge

import me.pliexe.discordeconomybridge.discord.DiscordMember
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

class ModeratorManager(private val main: DiscordEconomyBridge) {
    private val roles = mutableListOf<String>()

    fun getRoles (): MutableList<String> {
        return roles
    }

    fun LoadFromConfig() {
        if(main.config.isList("discordModerators")) {
            main.config.getStringList("discordModerators").forEach {
                if(it !is String) return
                if(it.length == 18) {
                    roles.add(it)
                }
            }
        } else if(main.config.isString("discordModerators")) {
            val role = main.config.getString("discordModerators")
            if(role.length == 18)
                roles.add(role)
        }


    }

    fun isModerator(member: DiscordMember): Boolean
    {
        if(member.isOwner) return true

        if(main.config.isBoolean("ignorePermissionsForAdministrators"))
            if(main.config.getBoolean("ignorePermissionsForAdministrators"))
                if(member.isAdministrator()) return true

        for(role in roles) {
            if(member.rolesContain(role)) return true
        }

        return false
    }
}