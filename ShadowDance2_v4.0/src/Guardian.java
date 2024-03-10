import bagel.Image;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * Handles Guardian entity logic
 */
public class Guardian extends Entity{
    //Guardian image
    private final Image GUARDIAN = new Image("res/guardian.png");

    //Class constructor
    Guardian() {
        Name = "Guardian";
        EntityPosition = new Vector2(800,600);
    }

    @Override
    void SpawnEntity() {
        GUARDIAN.draw(EntityPosition.x,EntityPosition.y);
    }

    @Override
    void EntityMovement() {}

    @Override
    double RandomDouble(double start, double end) {
        return 0;
    }

    @Override
    double RandomDirection() {
        return 0;
    }


    @Override
    boolean isCollide(ArrayList<Notes> Input) {
        return false;
    }
}
