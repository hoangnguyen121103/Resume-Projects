import java.util.Iterator;

/**
 * Handles level3 logic
 */
public class Level3 extends Level2{

    /**
     * This method is used to spawn entities (Enemies, Guardians, Arrow)
     * @param currentFrame  This refers to the number of frames has passed since the game started
     */
    @Override
    void SpawnEntities(int currentFrame) {
        //Spawn Guardian
        guardian.SpawnEntity();
        //Spawn Enemy for every 600 frame
        if (((currentFrame % 600) == 0)){
            Enemies.add(new Enemy());
            CountEnemies ++;
        }
        if (CountEnemies > 0){
            //Loop through an arrayList of enemies and update its position as each frame passes
            Iterator<Enemy> iterator = Enemies.iterator();
            while(iterator.hasNext()) {
                Enemy I = iterator.next();
                I.EntityMovement();
                //If an enemy collide with a note, remove that note
                if (I.isCollide(NotesLeft)) {
                    CounterNotesLeft--;
                }
                if (I.isCollide(NotesRight)) {
                    CounterNotesRight--;
                }
                if (I.isCollide(NotesSpecial)) {
                    CounterSpecial--;
                }
            }
        }
    }

    /**
     * This method used to spawn Arrow shot from the Guardian
     */
    @Override
    void SpawnProjectile(){
        if (CountEnemies > 0) {
            if (Arrow == null) {
                Arrow = new Projectile(Enemies);
            }
        }
    }

    /**
     * This method is used to update the Arrow position as each frame passes
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    @Override
    void ProjectilesMovement(int currentFrame){
        if (Arrow != null) {
            Arrow.ArrowMovement();
            //If the Arrow hit an enemy, remove both Arrow and the enemy, then update the counter
            if (Arrow.isHit(Enemies)) {
                CountEnemies--;
                Arrow = null;
            }
            if (Arrow != null && (Arrow.EntityPosition.x >= 1023 || Arrow.EntityPosition.y >= 767)) {
                Arrow = null;
            }
            //If the Arrow leaves the game window, remove the Arrow object and be ready to create a new Arrow object
            if (Arrow != null && (Arrow.EntityPosition.x <= 0 || Arrow.EntityPosition.y <= 0)) {
                Arrow = null;
            }
        }
    }
}
