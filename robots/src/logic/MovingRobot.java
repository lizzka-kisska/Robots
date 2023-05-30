package logic;

public interface MovingRobot {
    default double applyLimits(double value, double max) {
        double ZERO_VALUE = 0;
        return Math.min(Math.max(value, ZERO_VALUE), max);
    }
}
