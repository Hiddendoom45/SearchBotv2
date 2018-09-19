package SearchBot.global.record;

import net.dv8tion.jda.core.entities.Emote;

/**
 * Stores acceptable emotes and their values
 * @author Allen
 *
 */
public enum EmoteValues {
	Poop("EMOTE_POOOP",":poop:",-1,"💩",0),
	trash("EMOTE_TRASH",":put_litter_in_its_place:",-1,"🚮",0),
	abby("EMOTE_ABBYCRI","<:AbbyCri:455777835399774209>",-2,"AbbyCri",455777835399774209L),
	nausea("EMOTE_NAUSEA",":nauseated_face:",-1,"🤢",0),
	down("EMOTE_DOWN",":thumbsdown:",-1,"👎",0),
	up("EMOTE_UP",":thumbsup:",2,"👍",0),
	rofl("EMOTE_ROFL",":rofl:",2,"🤣",0),
	joy("EMOTE_JOY",":joy:",2,"😂",0),
	think("EMOTE_THINK",":thinking:",1,"🤔",0);
	
	public final String textName;
	public final String emoteText;
	public final int value;
	public final String name;
	public final long id;
	EmoteValues(String textName,String emoteText,int value,String name,long id){
		this.textName=textName;
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
