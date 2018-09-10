package SearchBot.global.record;

/**
 * Standard Enum to store module names
 * @author Allen
 *
 */
public enum Modules {
	Core("Core");
	private final String name;
	Modules(String name){
		this.name=name;
	}
	@Override
	public String toString(){
		return name;
	}
}
