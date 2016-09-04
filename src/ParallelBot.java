package ccc;
import robocode.*;

/**
 * ParallelBot- a robot by (your name here)
 */
public class ParallelBot extends AdvancedRobot {

    boolean isScanning = true;
    double direction = 1;
    /**
     * run: ParallelBot's default behavior
     */
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            if (isScanning) {
                scan();
                setTurnRadarRight(22);
                execute();
             } else {
                setTurnRadarRight(getGunHeading() - getRadarHeading() - 30);
                scan();
                execute();
                isScanning = true;
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        isScanning = false;
        double enemyDirection = e.getBearing() / e.getBearing();

        double relativeX = Math.cos(enemyDirection) * e.getDistance();
        double relativeY = Math.sin(enemyDirection) * e.getDistance();

        double radarTurn = enemyDirection * (getHeading() + e.getBearing() - getRadarHeading());
        double gunTurn = enemyDirection * (getHeading() + e.getBearing() - getGunHeading());

        setTurnRadarRight(normalize(radarTurn));  
        setTurnGunRight(normalize(gunTurn % 360));
        setTurnRight(e.getBearing() - 90);
        setAhead(15 * direction);

        while (getGunTurnRemaining() > 0 ) { execute(); }
        setFire(2);
    }

    public double normalize(double angle) {
        double newAngle = angle % 360;
        if (newAngle > 180) {
            return  newAngle - 360;
        } else if (newAngle < -180) {
            return newAngle + 360;
        } else {
            return newAngle;
        }
    }
    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        direction *= -1;
    }
}
