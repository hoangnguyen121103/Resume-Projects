import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles logic for level 1
 */
public class Level1 extends Level {

    /**
     * This method is used to spawn notes
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    @Override
    void SpawnNote(int currentFrame) {
        SpawnNoteHelper(currentFrame, NotesLeft);
        SpawnNoteHelper(currentFrame, NotesRight);
        SpawnNoteHelper(currentFrame, NotesUp);
        SpawnNoteHelper(currentFrame, NotesDown);
    }

    /**
     * This method is a helper method for the SpawnNote method above
     * @param currentFrame This refers to the number of frames has passed since the game started
     * @param Input This refers the chosen ArrayList of notes (NotesLeft, NotesRight ,...)
     */
    void SpawnNoteHelper(int currentFrame, ArrayList<Notes> Input) {
        if (!Input.isEmpty()) {
            //Loop through each note to see whether it should be spawned or not
            for (Notes I : Input) {
                if (I.startFrame == currentFrame) {
                    I.NotesStart(currentFrame);
                }
            }
        }
    }

    /**
     * This method is used to update the position of each note as each frame passes
     * @param currentFrame This refers to the number of frames has passed
     */
    @Override
    void UpdateNotePosition(int currentFrame) {
        UpdateNoteHelper(currentFrame, NotesLeft);
        UpdateNoteHelper(currentFrame, NotesRight);
        UpdateNoteHelper(currentFrame, NotesUp);
        UpdateNoteHelper(currentFrame, NotesDown);
    }

    /**
     * This method below used to update player's score for Normal notes
     * @param KeyInput Key pressed by the player
     */
    @Override
    void UpdateNormalNoteScore(String KeyInput) {
        //Check whether the player has pressed the correct key or not
        //And whether the note is within range to be processed or not
        if (KeyInput.equals("Left") && !DistanceMoreThan200(NotesLeft.get(0).yPos)) {
            if (NotesLeft.get(0).NoteType.equals("Normal")) {
                //Update player's score
                playerScore.ScoreUpdate(NotesLeft.get(0).yPos);
                //Remove the note from the arrayList it is in
                NotesRemove("Left");
            }
        }
        if (KeyInput.equals("Right") && !DistanceMoreThan200(NotesRight.get(0).yPos)) {
            if (NotesRight.get(0).NoteType.equals("Normal")) {
                playerScore.ScoreUpdate(NotesRight.get(0).yPos);
                NotesRemove("Right");
            }
        }
        if (KeyInput.equals("Up") && !DistanceMoreThan200(NotesUp.get(0).yPos)) {
            if (NotesUp.get(0).NoteType.equals("Normal")) {
                playerScore.ScoreUpdate(NotesUp.get(0).yPos);
                NotesRemove("Up");
            }
        }
        if (KeyInput.equals("Down") && !DistanceMoreThan200(NotesDown.get(0).yPos)) {
            if (NotesDown.get(0).NoteType.equals("Normal")) {
                playerScore.ScoreUpdate(NotesDown.get(0).yPos);
                NotesRemove("Down");
            }
        }
    }

    /**
     * This method below used to update player's score for the lower end of Hold notes
     * @param KeyInput Key pressed by the player
     */
    @Override
    void UpdateHoldNoteScoreLower(String KeyInput) {
        //Check whether the player has pressed the correct key or not
        //And whether the note is within range to be processed or not
        if (KeyInput.equals("Left") && !DistanceMoreThan200(NotesLeft.get(0).yPos)) {
            if (NotesLeft.get(0).NoteType.equals("Hold")) {
                //Update player's score
                playerScore.ScoreUpdate(NotesLeft.get(0).yPos + 82);
            }
        }
        if (KeyInput.equals("Right") && !DistanceMoreThan200(NotesRight.get(0).yPos)) {
            if (NotesRight.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesRight.get(0).yPos + 82);
            }
        }
        if (KeyInput.equals("Up") && !DistanceMoreThan200(NotesUp.get(0).yPos)) {
            if (NotesUp.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesUp.get(0).yPos + 82);
            }
        }
        if (KeyInput.equals("Down") && !DistanceMoreThan200(NotesDown.get(0).yPos)) {
            if (NotesDown.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesDown.get(0).yPos + 82);
            }
        }
    }

    /**
     * This method below used to update player's score for the higher end of Hold notes
     * @param KeyInput Key pressed by the player
     */
    @Override
    void UpdateHoldNoteScoreHigher(String KeyInput) {
        //Check whether the player has pressed the correct key or not
        //And whether the note is within range to be processed or not
        if (KeyInput.equals("Left") && !DistanceMoreThan200(NotesLeft.get(0).yPos)) {
            if (NotesLeft.get(0).NoteType.equals("Hold")) {
                //Update player's score
                playerScore.ScoreUpdate(NotesLeft.get(0).yPos - 82);
                //Remove the note from the arrayList it is in
                NotesRemove("Left");
            }
        }
        if (KeyInput.equals("Right") && !DistanceMoreThan200(NotesRight.get(0).yPos)) {
            if (NotesRight.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesRight.get(0).yPos - 82);
                NotesRemove("Right");
            }
        }
        if (KeyInput.equals("Up") && !DistanceMoreThan200(NotesUp.get(0).yPos)) {
            if (NotesUp.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesUp.get(0).yPos - 82);
                NotesRemove("Up");
            }
        }
        if (KeyInput.equals("Down") && !DistanceMoreThan200(NotesDown.get(0).yPos)) {
            if (NotesDown.get(0).NoteType.equals("Hold")) {
                playerScore.ScoreUpdate(NotesDown.get(0).yPos - 82);
                NotesRemove("Down");
            }
        }
    }

    /**
     * This method is used to remove the first note in the selected lane
     * @param NoteLane The selected lane
     */
    void NotesRemove(String NoteLane) {
        if (NoteLane.equals("Left")) {
            NotesLeft.remove(0);
            //Update the counter when a note gets removed
            CounterNotesLeft--;
        } else if (NoteLane.equals("Right")) {
            NotesRight.remove(0);
            CounterNotesRight--;
        } else if (NoteLane.equals("Up")) {
            NotesUp.remove(0);
            CounterNotesUp--;
        } else if (NoteLane.equals("Down")) {
            NotesDown.remove(0);
            CounterNotesDown--;
        }
    }

    @Override
    void SpeedChangeEffect() {}

    @Override
    void BombSpecialEffect(String KeyInput, int currentFrame) {}

    @Override
    void DoubleScoreEffect() {}

    @Override
    void DoubleScoreActivation() {}

    @Override
    void SpawnEntities(int currentFrame) {}

    @Override
    void ProjectilesMovement(int currentFrame) {}

    @Override
    void SpawnProjectile() {

    }
}
