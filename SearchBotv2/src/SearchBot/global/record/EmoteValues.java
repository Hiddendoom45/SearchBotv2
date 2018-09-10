package SearchBot.global.record;

/**
 * Stores acceptable emotes and their values
 * @author Allen
 *
 */
public enum EmoteValues {
	Poop(":poop:",-1),
	trash(":put_litter_in_its_place:",-1),
	abby("<:AbbyCri:455777835399774209>",-2),
	nausea(":nauseated_face:",-1),
	down(":nauseated_face:",-1),
	up(":thumbsup:",2),
	rofl(":rofl:",2),
	joy(":joy:",2),
	think(":thinking:",1);
	
	public final String emoteText;
	public final int value;
	EmoteValues(String emoteText,int value){
		this.emoteText=emoteText;
		this.value=value;
	}
	
}
