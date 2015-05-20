package fog.ethereal.util;

public class Mode {
	public static final Mode PLAY = new Mode("play");
	public static final Mode MOVE = new Mode("move");
	public static final Mode ADD = new Mode("add");
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
