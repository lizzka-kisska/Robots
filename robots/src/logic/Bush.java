package logic;

import java.util.Random;

public class Bush {
    public volatile int xCoordinate;
    public volatile int yCoordinate;
    private static final Random random = new Random();

    public Bush() {
        int BOUND = 100;
        xCoordinate = random.nextInt(BOUND);
        yCoordinate = random.nextInt(BOUND);
    }
}
