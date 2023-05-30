package logic;

import java.util.Random;

public class Target {
    public volatile int xCoordinate = 150;
    public volatile int yCoordinate = 100;
    private static final Random random = new Random();

    public Target() {
    }

    public void updateTargetPosition(int width, int height) {
        int ZERO_VALUE = 0;
        if (width != ZERO_VALUE || height != ZERO_VALUE) {
            xCoordinate = random.nextInt(width);
            yCoordinate = random.nextInt(height);
        }
    }
}
