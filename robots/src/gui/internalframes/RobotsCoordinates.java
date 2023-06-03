package gui.internalframes;

import logic.Robot;
import logic.UserRobot;

import java.util.Observable;
import java.util.Observer;

public class RobotsCoordinates extends RobotsMetaInformation implements Observer {
    public RobotsCoordinates(UserRobot userRobot, Robot robot) {
        super("ROBOTS_COORDINATES");
        userRobot.addObserver(this);
        robot.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UserRobot) {
            Double X = ((UserRobot) o).xCoordinate;
            Double Y = ((UserRobot) o).yCoordinate;
            usersRobotTextField.setText(String.format("X: %.1f, Y: %.1f", X, Y));
        }
        if (o instanceof Robot) {
            Double X = ((Robot) o).xCoordinate;
            Double Y = ((Robot) o).yCoordinate;
            robotTextField.setText(String.format("X: %.1f, Y: %.1f", X, Y));
        }
    }
}