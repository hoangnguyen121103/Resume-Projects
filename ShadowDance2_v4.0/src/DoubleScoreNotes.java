import bagel.Image;
/**
 * Handle Double - score notes logic
 */
public class DoubleScoreNotes extends Notes{
    //notes image
    private final Image DoubleScore = new Image("res/note2x.png");

    //Class constructor
    DoubleScoreNotes(String NoteLane, double xPos, String NoteType, int StartFrames) {
        super(NoteLane, xPos, NoteType, StartFrames);
    }

    @Override
    void DrawNotes(double yCoord) {
        yPos = yCoord;
        if (NoteType.equals("DoubleScore")){
            DoubleScore.draw(xPos,yPos);
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
    boolean DoubleScoreActivation() {
        if (NoteType.equals("DoubleScore")) {
            //true if the distance between the note and STATIONARY_Y is less than 50
            if (Math.abs(yPos - STATIONARY_Y) <= 50) {
                return true;
            }
        }
        return false;
    }

    @Override
    boolean BombActivation() {
        return false;
    }

    @Override
    boolean SpeedChangeActivation() {
        return false;
    }
}
