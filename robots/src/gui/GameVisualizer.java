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
    GameLogic gameLogic = new GameLogic();
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
                    if (zoomLevel - 0.1 >= 1){
                        zoomLevel -= 0.1;
                    }
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
            gameLogic.checkPositionRelativeToBush(userRobot, bush);
            if (userRobot.reachedTarget(target.xCoordinate, target.yCoordinate)) {
                target.updateTargetPosition(getWidth(), getHeight());
                repaint();
            }
            gameLogic.checkDistance(robot, userRobot);
        }
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        setZoomLevel(g2d, userRobot.xCoordinate, userRobot.yCoordinate);
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

    private void setZoomLevel(Graphics2D g, double tx, double ty){
        AffineTransform t = new AffineTransform();
        t.translate(tx,ty);
        t.scale(zoomLevel, zoomLevel);
        t.translate(-tx,-ty);
        g.setTransform(t);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction, Color bodyColor) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.rotate(direction, robotCenterX, robotCenterY);
        graphics2D.setColor(bodyColor);
        fillOval(graphics2D, robotCenterX, robotCenterY, 30, 10);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, robotCenterX, robotCenterY, 30, 10);
        graphics2D.setColor(Color.WHITE);
        fillOval(graphics2D, robotCenterX + 10, robotCenterY, 5, 5);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, robotCenterX + 10, robotCenterY, 5, 5);
        graphics2D.dispose();
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void drawBush(Graphics2D g, int x, int y) {
        Rectangle square = new Rectangle(x, y, 20, 20);
        g.setColor(Color.GREEN);
        g.fill(square);
    }
}
