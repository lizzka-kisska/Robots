package logic;

public interface MovingRobot {
    default double applyLimits(double value, double max) {
        return Math.min(Math.max(value, 0), max);
    }
}
