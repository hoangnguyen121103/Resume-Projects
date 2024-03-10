import bagel.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Handles Hold notes logic
 */
public class HoldNotes extends Notes{
    //Hold notes images
    private final Image HOLD_DOWN = new Image("res/holdNoteDown.png");
    private final Image HOLD_UP = new Image("res/holdNoteUp.png");
    private final Image HOLD_RIGHT = new Image("res/holdNoteRight.png");
    private final Image HOLD_LEFT = new Image("res/holdNoteLeft.png");


    //Class constructor
    HoldNotes(String NoteLane, double xPos, String NoteType, int StartFrame) {
        super(NoteLane, xPos, NoteType, StartFrame);
    }

    @Override
    void DrawNotes(double yCoord) {
        yPos = yCoord;
        if (NoteLane.equals("Left")){
            HOLD_LEFT.draw(xPos,yPos);
        }
        else if(NoteLane.equals("Right")){
            HOLD_RIGHT.draw(xPos,yPos);
        }
        else if(NoteLane.equals("Up")){
            HOLD_UP.draw(xPos,yPos);
        }
        else if(NoteLane.equals("Down")){
            HOLD_DOWN.draw(xPos,yPos);
        }
    }

    @Override
    void NotesMovement(int Speed) {
        double yPosUpdated = yPos + Speed;
        DrawNotes(yPosUpdated);
    }

    @Override
    void NotesStart(int currentFrame) {
        //Initial yPos of each note
        double yCoord = 24;
        if (startFrame == currentFrame ){
            DrawNotes(yCoord);
        }
    }

    @Override
    boolean SpeedChangeActivation() {
        return false;
    }

    @Override
    boolean DoubleScoreActivation() {
        return false;
    }

    @Override
    boolean BombActivation() {
        return false;
    }
}
