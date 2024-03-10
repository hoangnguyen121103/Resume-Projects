import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles projectile logic shot from the guardian
 */
public class Projectile{
    //Arrow image
    private final Image ARROW = new Image("res/arrow.png");

    //Arrow speed and position
    private final int SPEED = 6;
    public Vector2 EntityPosition;

    //Rotation of Arrow image
    private DrawOptions EntityRotation = new DrawOptions();
    private double RotationAngle;

    //90 and 180 degree to radians
    private final double degree90 = 1.5708;
    private final double degree180 = 3.1415;

    //Class constructor
    Projectile(ArrayList<Enemy> Input){
        EntityPosition = new Vector2(800,600);
        RotationAngle = Rotation(Input);
        EntityRotation.setRotation(RotationAngle);
    }

    /**
     * The method is used draw Arrow
     */
    void SpawnArrow(){
        ARROW.draw(EntityPosition.x,EntityPosition.y,EntityRotation);
    }

    /**
     * The method is used to update each Arrow position as each frame passes
     */
    void ArrowMovement(){
        //Change in xPos and yPos
        double deltaX = Math.cos(RotationAngle)*SPEED;
        double deltaY = Math.sin(RotationAngle)*SPEED;

        Vector2 delta = new Vector2(deltaX,deltaY);
        //Update new position according to the change in xPos and yPos
        EntityPosition = EntityPosition.add(delta);
        SpawnArrow();
    }

    /**
     * This method is used to determine whether the Arrow has hit an enemy
     * @param Input arrayList of Enemies spawned
     * @return boolean
     */
    boolean isHit(ArrayList<Enemy> Input){
        Iterator <Enemy> iterator = Input.iterator();
        while(iterator.hasNext()){
            Enemy I = iterator.next();
            Point IPoint = I.EntityPosition.asPoint();
            Point ArrowPos = EntityPosition.asPoint();
            //If distance between Arrow and enemy is less than 62, the Arrow has hit
            if (IPoint.distanceTo(ArrowPos) <= 62){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to determine how the Arrow image should be rotated
     * @param Input arrayList of enemies
     * @return double the angle of rotation
     */
    double Rotation(ArrayList<Enemy> Input){
        Enemy NearestEnemy = NearestEntity(Input);
        Point NearestEnemyPoint = NearestEnemy.EntityPosition.asPoint();
        Point ArrowPoint = EntityPosition.asPoint();
        //Rotation angle
        double Angle = 0;
        //Enemy is directly above the Guardian
        if (NearestEnemyPoint.y == 800){
            Angle = degree90;
        }
        //Enemy is in the second quadrant
        if (NearestEnemyPoint.y > 800){
            Angle = degree180 - CalculateAngle(NearestEnemyPoint,ArrowPoint);
        }
        //Enemy is in the first quadrant
        if(NearestEnemyPoint.y < 800){
            Angle = CalculateAngle(NearestEnemyPoint,ArrowPoint);
        }
        return Angle;
    }

    /**
     * this is a helper method to the method above
     * @param a coordinates of Enemy
     * @param b coordinates of Guardian
     * @return double
     */
    double CalculateAngle(Point a, Point b){
        double deltaX = a.x - b.x;
        double deltaY = a.y - b.y;
        return Math.atan2(deltaY,deltaX);
    }

    /**
     * This method is used to identify the nearest enemy to the Guardian
     * @param Input arrayList of enemies
     * @return Enemy
     */
    Enemy NearestEntity(ArrayList<Enemy> Input){
        Iterator<Enemy> iterator = Input.iterator();
        int NearestEntity = 0;
        int counterPos = 0;
        Point NearestInputPos = Input.get(0).EntityPosition.asPoint();
        //Loop through the arrayList, find which enemy has the nearest distance to the Guardian
        while(iterator.hasNext()) {
            Enemy I = iterator.next();
            Point IPos = new Point(I.EntityPosition.x, I.EntityPosition.y);
            Point ArrowPos = EntityPosition.asPoint();
            if (IPos.distanceTo(ArrowPos) < NearestInputPos.distanceTo(ArrowPos)) {
                NearestInputPos = IPos;
                NearestEntity = counterPos;
            }
            counterPos++;
        }
        return Input.get(NearestEntity);
    }
}
