package logic;

public enum UserRobotDirection {
    UP(4.65),
    DOWN(1.5),
    LEFT(3.1),
    RIGHT(0);

    private final double angle;

    UserRobotDirection(double angle) {
        this.angle = angle;
    }

    public double getDirectionAngle(){
        return angle;
    }
}
