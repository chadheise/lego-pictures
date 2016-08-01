package heise.chad.lego.picture;

public class BrickUnit {
	public static final Double millimeterHeight = 9.6;
	public static final Double millimeterWidth = 8.0;
	private Brick brick = null;
	
	public BrickUnit(Brick brick) {
		this.brick = brick; // The brick that this brick unit is a part of
	}
	
	public Brick getBrick() {
		return brick;
	}
	
	
}
