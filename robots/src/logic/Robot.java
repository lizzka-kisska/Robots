package logic;

public class Robot {
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;
    public final double duration = 10;
    public volatile double positionX;
    public volatile double positionY;
    public volatile double direction;

    public double angle = 0;

    public double velocity = maxVelocity;
    public double angularVelocity = 0;

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

    public double asNormalizedRadians(double angle) {
        return (angle % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
    }

    public double angularVelocity(double angleToTarget) {
        double angularVelocity;
        if (Math.abs(direction - angleToTarget) < 10e-7) {
            angularVelocity = 0;
        } else if (direction >= Math.PI) {
            if (direction - Math.PI < angleToTarget && angleToTarget < direction)
                angularVelocity = -maxAngularVelocity;
            else
                angularVelocity = maxAngularVelocity;
        } else {
            if (direction < angleToTarget && angleToTarget < direction + Math.PI)
                angularVelocity = maxAngularVelocity;
            else
                angularVelocity = -maxAngularVelocity;
        }
        return angularVelocity;


    }

    private static double applyLimits(double value, double max) {
        return Math.min(Math.max(value, 0), max);
    }


    public void moveRobot(int width, int height) {
        velocity = applyLimits(velocity, maxVelocity);
        double newDirection = asNormalizedRadians(direction + Math.min(angle, angularVelocity) * duration);
        direction = newDirection;
        double newX = positionX + velocity * duration * Math.cos(direction);
        double newY = positionY + velocity * duration * Math.sin(direction);
        positionX = newX;
        positionY = newY;
        if (width != 0) {
            newX = applyLimits(positionX, width);
            positionX = newX;
        }
        if (height != 0) {
            newY = applyLimits(positionY, height);
            positionY = newY;
        }
    }
}
