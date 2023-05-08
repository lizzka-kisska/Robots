package logic;

public class Target {
    public volatile int xCoordinate;
    public volatile int yCoordinate;

    public Target(int targetXCoordinate, int targetYCoordinate) {
        this.xCoordinate = targetXCoordinate;
        this.yCoordinate = targetYCoordinate;
    }

    public void setTargetPosition(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
}
