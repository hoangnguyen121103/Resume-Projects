import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Handles Enemy logic
 */
public class Enemy extends Entity{
    //Enemy image
    private final Image ENEMY = new Image("res/enemy.png");

    //Class constructor
    Enemy() {
        Name = "Enemy";
        EntityPosition = new Vector2(RandomDouble(100,900),RandomDouble(100,500));
        MoveDirection = RandomDirection();
    }


    @Override
    void SpawnEntity() {
        ENEMY.draw(EntityPosition.x,EntityPosition.y);
    }

    @Override
    void EntityMovement() {
        //If an enemy has reached xPos of 900 or 100, change direction
        if (EntityPosition.x >= 900){
            MoveDirection = -1;
        }
        else if (EntityPosition.x <= 100){
            MoveDirection = 1;
        }
        Vector2 MovingVector = new Vector2(MoveDirection,0);
        EntityPosition = EntityPosition.add(MovingVector);
        SpawnEntity();
    }

    @Override
    double RandomDouble(double start, double end) {
        Random rand = new Random();
        double randomDouble = rand.nextDouble() * end;
        randomDouble += start;
        return randomDouble;
    }

    @Override
    double RandomDirection() {
        Random rand = new Random();
        boolean RandomBool = rand.nextBoolean();
        //RandomBool is true, return 1 which means move to the right
        if (RandomBool){
            return 1;
        }
        //or else, return -1 meaning move to the left
        return -1;
    }


    @Override
    boolean isCollide(ArrayList<Notes> Input) {
        Iterator<Notes> iterator = Input.iterator();
        //Loop through each note in the selected lane
        while(iterator.hasNext()){
            Notes I = iterator.next();
            Point IPoint = new Point(I.xPos,I.yPos);
            Point EntityPos = EntityPosition.asPoint();
            //If the distance between a note and an enemy is less 104, collision occurs
            if (IPoint.distanceTo(EntityPos) <= 104){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
