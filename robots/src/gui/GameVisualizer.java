package gui;

import logic.*;
import logic.Robot;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameVisualizer extends JPanel {
    Robot robot = new Robot(100, 100, 0);
    Target target = new Target();
    UserRobot userRobot = new UserRobot(150, 150, 0);
    Bush bush = new Bush();
    TimerToEndGame timerToEndGame = new TimerToEndGame();
    private Timer m_timer = initTimer();
    public static boolean runGame = true;
    private double zoomLevel = 1;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer() {
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == '='){
                    zoomLevel += 0.1;
                } else if (e.getKeyChar() == '-') {
                    zoomLevel -= 0.1;
                } else userRobot.changeDirection(e);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {
        if (runGame) {
            robot.moveRobot(getWidth(), getHeight());
            userRobot.moveUserRobot(getWidth(), getHeight());
            if (userRobot.isInsideBush(bush.xCoordinate, bush.yCoordinate)) {
                userRobot.isVisible = false;
                userRobot.xOffset = UserRobotOffset.HALT.getXOffset();
                userRobot.yOffset = UserRobotOffset.HALT.getYOffset();
            } else {
                userRobot.isVisible = true;
            }
            if (userRobot.reachedTarget(target.xCoordinate, target.yCoordinate)) {
                target.updateTargetPosition(getWidth(), getHeight());
                repaint();
            }
            if (userRobot.isVisible) {
                checkDistance();
            }
        }
    }

    protected void checkDistance() {
        int RADIUS = 100;
        if (distance(robot.xCoordinate, userRobot.xCoordinate, robot.yCoordinate, userRobot.yCoordinate) <= RADIUS) {
            timerToEndGame.startAndRunTimer();
        } else {
            timerToEndGame.stopTimer();
        }
    }

    private double distance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2));
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robot.xCoordinate), round(robot.yCoordinate), robot.direction, Color.RED);
        drawRobot(g2d, round(userRobot.xCoordinate), round(userRobot.yCoordinate), userRobot.direction, Color.CYAN);
        drawTarget(g2d, target.xCoordinate, target.yCoordinate);
        drawBush(g2d, bush.xCoordinate, bush.yCoordinate);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private AffineTransform getAffineTransform(double tx, double ty, double angle){
        AffineTransform t = new AffineTransform();
        t.translate(tx,ty);
        t.rotate(angle);
        t.scale(zoomLevel, zoomLevel);
        t.translate(-tx,-ty);
        return t;
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction, Color bodyColor) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        g.setTransform(getAffineTransform(robotCenterX, robotCenterY, direction));
        g.setColor(bodyColor);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setTransform(getAffineTransform(0, 0, 0));
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void drawBush(Graphics2D g, int x, int y) {
        g.setTransform(getAffineTransform(0, 0, 0));
        Rectangle square = new Rectangle(x, y, 20, 20);
        g.setColor(Color.GREEN);
        g.fill(square);
    }
}
