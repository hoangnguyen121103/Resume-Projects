import bagel.Image;
/**
 * Handles Bomb notes logic
 */
public class BombNotes extends Notes{
    //notes image
    private final Image NOTE_BOMB = new Image("res/noteBomb.png");

    //Class constructor
    BombNotes(String NoteLane, double xPos, String NoteType, int StartFrames) {
        super(NoteLane, xPos, NoteType, StartFrames);
    }

    @Override
    void DrawNotes(double yCoord) {
        yPos = yCoord;
        if (NoteType.equals("Bomb")){
            NOTE_BOMB.draw(xPos,yPos);
        }
    }

    @Override
    void NotesMovement(int Speed) {
        double yPosUpdated = yPos + Speed;
        DrawNotes(yPosUpdated);
    }

    @Override
    void NotesStart(int currentFrame) {
        //initial yPos of each note
        double yCoord = 100;
        if (startFrame == currentFrame) {
            DrawNotes(yCoord);
        }
    }


    @Override
    boolean BombActivation() {
        if (NoteType.equals("Bomb")) {
            //true if the distance between the note and STATIONARY_Y is less than 50
            if (Math.abs(yPos - STATIONARY_Y) <= 50) {
                return true;
            }
        }
        return false;
    }

    @Override
    boolean SpeedChangeActivation() {
        return false;
    }

    @Override
    boolean DoubleScoreActivation() {
        return false;
    }
}
