package fog.ethereal.util;

public class Mode {
	public static final Mode PLAY = new Mode("play");
	public static final Mode EDIT = new Mode("edit");
	public static final Mode MENU = new Mode("menu");
	private String mode;
	public Mode(String mode) {
		this.mode = mode;
	}
	
	public boolean equals(Mode m) {
		return m.toString().equals(mode);
	}
	
	public String toString() {
		return mode;
	}
}
