import bagel.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Handles Normal notes logic
 */
public class NormalNotes extends Notes {
    //notes images
    private final Image DOWN = new Image("res/noteDown.png");
    private final Image UP = new Image("res/noteUp.png");
    private final Image LEFT = new Image("res/noteLeft.png");
    private final Image RIGHT = new Image("res/noteRight.png");


    //Class constructor
    NormalNotes(String NoteLane, double xPos, String NoteType, int StartFrame) {
        super(NoteLane, xPos, NoteType, StartFrame);
    }

    /**
     * This method is used to draw each note
     * @param yCoord The yPos that each note should be spawned
     */
    @Override
    void DrawNotes(double yCoord) {
        yPos = yCoord;
        //Which lane it should be drawn
        if (NoteLane.equals("Left")) {
            LEFT.draw(xPos, yPos);
        } else if (NoteLane.equals("Right")) {
            RIGHT.draw(xPos, yPos);
        } else if (NoteLane.equals("Up")) {
            UP.draw(xPos, yPos);
        } else if (NoteLane.equals("Down")) {
            DOWN.draw(xPos, yPos);
        }
    }

    /**
     * This method is used to update the position of each note
     * @param Speed The speed that each note is supposed to travel
     */
    @Override
    void NotesMovement(int Speed) {
        double yPosUpdated = yPos + Speed;
        DrawNotes(yPosUpdated);
    }

    /**
     * This method determines when each note should be spawned
     * @param currentFrame This refers to the number of frames has passed
     */
    @Override
    void NotesStart(int currentFrame) {
        //Initial yPos of each note
        double yCoord = 100;
        if (startFrame == currentFrame) {
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

