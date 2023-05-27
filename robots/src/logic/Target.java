package logic;

import java.util.Random;

public class Target {
    public volatile int xCoordinate = 150;
    public volatile int yCoordinate = 100;
    private static final Random random = new Random();

    public Target() {
    }

    public void updateTargetPosition(int width, int height) {
        if (width != 0 || height != 0) {
            xCoordinate = random.nextInt(width);
            yCoordinate = random.nextInt(height);
        }
    }
}
