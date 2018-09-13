package SearchBot.global.record;

import net.dv8tion.jda.core.entities.Emote;

/**
 * Stores acceptable emotes and their values
 * @author Allen
 *
 */
public enum EmoteValues {
	Poop(":poop:",-1,"💩",0),
	trash(":put_litter_in_its_place:",-1,"🚮",0),
	abby("<:AbbyCri:455777835399774209>",-2,"AbbyCri",455777835399774209L),
	nausea(":nauseated_face:",-1,"🤢",0),
	down(":thumbsdown:",-1,"👎",0),
	up(":thumbsup:",2,"👍",0),
	rofl(":rofl:",2,"🤣",0),
	joy(":joy:",2,"😂",0),
	think(":thinking:",1,"🤔",0);
	
	public final String emoteText;
	public final int value;
	public final String name;
	public final long id;
	EmoteValues(String emoteText,int value,String name,long id){
		this.emoteText=emoteText;
		this.value=value;
		this.name=name;
		this.id=id;
	}
	public static EmoteValues getValueFor(Emote emote){
		for(EmoteValues e:EmoteValues.values()){
			if(e.name.equals(emote.getName())){
				if(e.id==0){
					return e;
				}
				else if(e.id==emote.getIdLong()){
					return e;
				}
			}
		}
		return null;
	}
	
}
