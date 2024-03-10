import bagel.DrawOptions;
import bagel.util.Vector2;

import java.util.ArrayList;
/**
 * An abstract class that handles common characteristics of all entities appear in level 3
 */
public abstract class Entity {
    //Class main attributes
    String Name;
    Vector2 EntityPosition;
    double MoveDirection = 0;


    /**
     * The method is used to spawn entities
     */
    abstract void SpawnEntity();

    /**
     * This method is used to update each entity position as each frame passes
     */
    abstract void EntityMovement();

    /**
     * This method is used to randomise the position that each enemy spawns
     * @param start Start range of xPos or yPos
     * @param end end range of xPos or yPos
     * @return randomised xPos or yPos within the input range
     */
    abstract double RandomDouble(double start, double end);

    /**
     * This method is used to randomise the direction at which the enemy initially moves
     * @return 1 move the right, -1 move to the left
     */
    abstract double RandomDirection();

    /**
     * The method is used to determine if an enemy has collided with a note
     * @param Input arrayList containing all notes in the selected lane
     * @return boolean true if it has, false if it has not
     */
    abstract boolean isCollide(ArrayList<Notes> Input);
}