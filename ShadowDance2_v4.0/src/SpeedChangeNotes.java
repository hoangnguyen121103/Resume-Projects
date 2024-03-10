import bagel.Image;

/**
 * Handles speed change for the game when player hits a speed change note
 */
public class SpeedChangeNotes extends Notes{
    //notes images
    private final Image SpeedUp = new Image ("res/noteSpeedUp.png");
    private final Image SlowDown = new Image ("res/noteSlowDown.png");

    //Class constructor
    SpeedChangeNotes(String NoteLane, double xPos, String NoteType, int StartFrames) {
        super(NoteLane, xPos, NoteType, StartFrames);
    }

    @Override
    protected void DrawNotes(double yCoord) {
        yPos = yCoord;
        if (NoteType.equals("SpeedUp")){
            SpeedUp.draw(xPos,yPos);
        }
        else if (NoteType.equals("SlowDown")){
            SlowDown.draw(xPos,yPos);
        }
    }

    @Override
    public void NotesMovement(int Speed) {
        double yPosUpdated = yPos + Speed;
        DrawNotes(yPosUpdated);
    }

    @Override
    public void NotesStart(int currentFrame) {
        //initial yPos of each note
        double yCoord = 100;
        if (startFrame == currentFrame) {
            DrawNotes(yCoord);
        }
    }

    @Override
    public boolean SpeedChangeActivation() {
        if (NoteType.equals("SpeedUp") || NoteType.equals("SlowDown")) {
            //true if the distance between the note and STATIONARY_Y is less than 50
            if (Math.abs(yPos - STATIONARY_Y) <= 50) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean DoubleScoreActivation() {
        return false;
    }

    @Override
    public boolean BombActivation() {
        return false;
    }
}
