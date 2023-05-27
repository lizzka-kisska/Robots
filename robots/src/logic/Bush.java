package logic;

import java.util.Random;

public class Bush {
    public volatile int xCoordinate;
    public volatile int yCoordinate;
    private static final Random random = new Random();

    public Bush() {
        xCoordinate = random.nextInt(100);
        yCoordinate = random.nextInt(100);
    }
}
