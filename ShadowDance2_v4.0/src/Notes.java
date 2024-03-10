/**
 * An abstract class that handles common characteristics of all notes in the game
 */
abstract class Notes {
    final double STATIONARY_Y = 657;

    //Main attributes of each note
    String NoteType;
    String NoteLane;
    int startFrame;
    double xPos;
    double yPos;

    //If a note is a "DoubleScore" note, then this attribute refers to the duration that this note has been in effect
    int DoubleScoreDuration = 0;

    //Class constructor
    Notes(String NoteLane, double xPos, String NoteType, int StartFrames){
        this.NoteLane = NoteLane;
        this.NoteType = NoteType;
        this.startFrame = StartFrames;
        this.xPos = xPos;
    }

    /**
     * This method is used to draw each note
     * @param yCoord The yPos that each note should be spawned
     */
    abstract void DrawNotes(double yCoord);

    /**
     * This method is used to update the position of each note
     * @param Speed The speed that each note is supposed to travel
     */
    abstract void NotesMovement(int Speed);

    /**
     * This method determines when each note should be spawned
     * @param currentFrame This refers to the number of frames has passed
     */
    abstract void NotesStart(int currentFrame);

    /**
     * This method is used to determine whether a speed change effect should be activated
     * @return boolean true if it should, false if it shouldn't
     */
    abstract boolean SpeedChangeActivation();

    /**
     * This method is used to determine whether a "DoubleScore" note should be activated or not
     * @return boolean true if it should, false if it shouldn't
     */
    abstract boolean DoubleScoreActivation();

    /**
     * This method is used to determine whether a Bomb note should be activated or not
     * @return boolean true if it should, false if it shouldn't
     */
    abstract boolean BombActivation();
}
