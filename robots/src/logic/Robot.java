package logic;

public class Robot {
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;
    public volatile double positionX;
    public volatile double positionY;
    public volatile double direction;

    public Robot(double robotPositionX, double robotPositionY, double robotDirection) {
        this.positionX = robotPositionX;
        this.positionY = robotPositionY;
        this.direction = robotDirection;
    }

    public double distanceToTarget(double targetPositionX, double targetPositionY, double robotPositionX, double robotPositionY) {
        double diffX = targetPositionX - robotPositionX;
        double diffY = targetPositionY - robotPositionY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double angleToTarget(double targetPositionX, double targetPositionY, double robotPositionX, double robotPositionY) {
        double diffX = targetPositionX - robotPositionX;
        double diffY = targetPositionY - robotPositionY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public double angularVelocity(double angleToTarget) {
        double angularVelocity = 0;
        if (angleToTarget > direction) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < direction) {
            angularVelocity = -maxAngularVelocity;
        }
        return angularVelocity;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private double updateXCoordinate(double velocity, double angularVelocity, double duration) {
        double newX = positionX + velocity / angularVelocity *
                (Math.sin(direction + angularVelocity * duration) -
                        Math.sin(direction));
        if (!Double.isFinite(newX)) {
            newX = positionX + velocity * duration * Math.cos(direction);
        }
        return newX;
    }

    private double updateYCoordinate(double velocity, double angularVelocity, double duration) {
        double newY = positionY - velocity / angularVelocity *
                (Math.cos(direction + angularVelocity * duration) -
                        Math.cos(direction));
        if (!Double.isFinite(newY)) {
            newY = positionY + velocity * duration * Math.sin(direction);
        }
        return newY;
    }

    public void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = updateXCoordinate(velocity, angularVelocity, duration);
        double newY = updateYCoordinate(velocity, angularVelocity, duration);
        positionX = newX;
        positionY = newY;
        double newDirection = asNormalizedRadians(direction + angularVelocity * duration);
        direction = newDirection;
    }
}
