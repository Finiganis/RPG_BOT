package mainBot.utils;

import mainBot.exception.RoleException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Usefull {
	public static String rolltoeffect(int roll){
		String[] tab = new String[6];
		tab[0] = "+5% réussite critique";
		tab[1] = "+20% réussite";
		tab[2] = "neutre";
		tab[3] = "conséquence";
		tab[4] = "-20% réussite";
		tab[5] = "+5% échec critique";
		if(roll < 1 || roll > 6) return "";
		return tab[roll-1];
	}

	public static Role getRole(MessageReceivedEvent event, String roleSearched) throws RoleException {
		List<Role> roles = event.getGuild().getRoles();
		for(Role role : roles) {
			if (role.getName().equals(roleSearched)) return role;
		}

		throw new RoleException("Role not found : " + roleSearched);
	}

	public static boolean isMJ(MessageReceivedEvent event) throws RoleException {
		String userId = event.getAuthor().getId();
		List<Member> members = event.getGuild().getMembersWithRoles(getRole(event, "MJ"));
		for (Member member : members) {
			if (userId.equals(member.getId())) {
				return true;
			}
		}
		return false;
	}
}
