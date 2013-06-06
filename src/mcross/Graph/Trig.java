package mcross.Graph;

public class Trig {
	public static float degreeToRadian(float value) {
		return (value * (float)(Math.PI/180.0d));
	}
	
	public static float radianToDegree(float value) {
		return (value * (float)(180.0d/Math.PI));
	}
}
