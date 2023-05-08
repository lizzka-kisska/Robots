package logic;

public class Robot {
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;
    public final double duration = 10;
    public volatile double xCoordinate;
    public volatile double yCoordinate;
    public volatile double direction;

    public double angle = 0;

    public double velocity = maxVelocity;
    public double angularVelocity = 0;
    public double angleToTarget = 0;

    public Robot(double robotXCoordinate, double robotYCoordinate, double robotDirection) {
        this.xCoordinate = robotXCoordinate;
        this.yCoordinate = robotYCoordinate;
        this.direction = robotDirection;
    }

    public double distanceToTarget(double targetXCoordinate, double targetYCoordinate) {
        double diffX = targetXCoordinate - xCoordinate;
        double diffY = targetYCoordinate - yCoordinate;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double getAngleToTarget(double targetXCoordinate, double targetYCoordinate) {
        double diffX = targetXCoordinate - xCoordinate;
        double diffY = targetYCoordinate - yCoordinate;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public double asNormalizedRadians(double angle) {
        return (angle % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
    }

    public void updateAngularVelocity(double angleToTarget) {
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


    }

    private static double applyLimits(double value, double max) {
        return Math.min(Math.max(value, 0), max);
    }


    public void moveRobot(int width, int height, double targetXCoordinate, double targetYCoordinate) {
        if (reachedTarget(targetXCoordinate, targetYCoordinate)) {
            return;
        }
        updateAngle(targetXCoordinate, targetYCoordinate);
        updateAngularVelocity(angleToTarget);
        velocity = applyLimits(velocity, maxVelocity);
        updateDirection();
        updateCoordinates(width, height);
    }

    private void updateCoordinates(int width, int height) {
        double newXCoordinate = xCoordinate + velocity * duration * Math.cos(direction);
        double newYCoordinate = yCoordinate + velocity * duration * Math.sin(direction);
        if (width != 0) {
            newXCoordinate = applyLimits(newXCoordinate, width);
        }
        if (height != 0) {
            newYCoordinate = applyLimits(newYCoordinate, height);
        }
        xCoordinate = newXCoordinate;
        yCoordinate = newYCoordinate;
    }

    private void updateDirection() {
        double newDirection = asNormalizedRadians(direction + Math.min(angle, angularVelocity) * duration);
        direction = newDirection;
    }

    private void updateAngle(double targetXCoordinate, double targetYCoordinate) {
        angleToTarget = getAngleToTarget(targetXCoordinate, targetYCoordinate);
        angle = asNormalizedRadians(angleToTarget - direction);
    }

    private boolean reachedTarget(double targetXCoordinate, double targetYCoordinate) {
        return distanceToTarget(targetXCoordinate, targetYCoordinate) < 0.5;
    }

}
