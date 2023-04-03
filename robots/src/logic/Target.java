package logic;

public class Target {
    public volatile int positionX;
    public volatile int positionY;

    public Target(int targetPositionX, int targetPositionY) {
        this.positionX = targetPositionX;
        this.positionY = targetPositionY;
    }

    public void setTargetPosition(int x, int y) {
        positionX = x;
        positionY = y;
    }
}
