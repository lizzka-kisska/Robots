package gui.internalframes;

import logic.Robot;
import logic.UserRobot;

import java.util.Observable;
import java.util.Observer;

public class RobotsDistanceToTarget extends RobotsMetaInformation implements Observer {
    public RobotsDistanceToTarget(UserRobot userRobot, Robot robot) {
        super("DISTANCE_TO_TARGET");
        userRobot.addObserver(this);
        robot.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UserRobot) {
            Double distance = ((UserRobot) o).distanceToTarget;
            usersRobotTextField.setText((String.format("%.1f", distance)));
        }
        if (o instanceof Robot) {
            Double distance = ((Robot) o).distanceToTarget;
            robotTextField.setText(String.format("%.1f", distance));
        }
    }
}