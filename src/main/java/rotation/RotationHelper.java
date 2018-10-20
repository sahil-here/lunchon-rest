package rotation;


public class RotationHelper {

	private static volatile boolean rotationStatus = true;

	public boolean getRotationStatus() {
		return rotationStatus;
	}

	public static void setRotationStatus(boolean rotationStatus) {
		RotationHelper.rotationStatus = rotationStatus;
	}
	
}
