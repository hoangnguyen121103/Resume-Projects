import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles logic for level2
 */
public class Level2 extends Level1 {

    /**
     * This method is used to spawn notes
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    @Override
    void SpawnNote(int currentFrame) {
        SpawnNoteHelper(currentFrame, NotesLeft);
        SpawnNoteHelper(currentFrame, NotesRight);
        SpawnNoteHelper(currentFrame, NotesDown);
        SpawnNoteHelper(currentFrame, NotesSpecial);
    }

    /**
     * This method is used to update the position of each note as each frame passes
     * @param currentFrame This refers to the number of frames has passed
     */
    @Override
    void UpdateNotePosition(int currentFrame) {
        UpdateNoteHelper(currentFrame, NotesLeft);
        UpdateNoteHelper(currentFrame, NotesRight);
        UpdateNoteHelper(currentFrame, NotesDown);
        UpdateNoteHelper(currentFrame, NotesSpecial);
    }

    /**
     * This method is used to execute "SpeedDown" and "SpeedUp" notes effects
     */
    @Override
    void SpeedChangeEffect() {
        //Check whether a speed change effect should be activated
        boolean SpeedChangeFlag = NotesSpecial.get(0).SpeedChangeActivation();
        //IF true, then update the game speed accordingly
        if (SpeedChangeFlag) {
            if (getNoteType("Special").equals("SpeedUp")) {
                Speed = Speed + 1;
                playerScore.setMessage("Speed Up");
                NotesRemove("Special");
            } else if (getNoteType("Special").equals("SlowDown")) {
                Speed = Speed - 1;
                playerScore.setMessage("Slow Down");
                NotesRemove("Special");
            }
        }
    }

    /**
     * This method is used to remove the first note in the selected lane
     * @param NoteLane The selected lane
     */
    @Override
    void NotesRemove(String NoteLane) {
        if (NoteLane.equals("Left")) {
            NotesLeft.remove(0);
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
        } else if (NoteLane.equals("Special")) {
            NotesSpecial.remove(0);
            CounterSpecial--;
        }
    }

    /**
     * This method is used to execute Bomb notes special effects
     * @param KeyInput Which lane is the bomb note in
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    @Override
    void BombSpecialEffect(String KeyInput, int currentFrame) {
        //Check whether it is a Bomb note or not and which lane it is in
        if (KeyInput.equals("Left") && getNoteType("Left").equals("Bomb")) {
            //Check whether the effect should be activated or not
            //Remove the Bomb note if found and execute its special effect
            if (NotesLeft.get(0).BombActivation()) {
                NotesLeft.remove(0);
                CounterNotesLeft --;
                BombSpecialEffectHelper(currentFrame,NotesLeft);
                //Print the message on screen
                playerScore.setMessage("Lane Clear");
            }
        }
        if (KeyInput.equals("Right") && getNoteType("Right").equals("Bomb")) {
            if (NotesRight.get(0).BombActivation()) {
                NotesRight.remove(0);
                CounterNotesRight --;
                BombSpecialEffectHelper(currentFrame,NotesRight);
                playerScore.setMessage("Lane Clear");
            }
        }
        if (KeyInput.equals("Down") && getNoteType("Down").equals("Bomb")) {
            if (NotesDown.get(0).BombActivation()) {
                NotesDown.remove(0);
                CounterNotesDown --;
                BombSpecialEffectHelper(currentFrame,NotesDown);
                playerScore.setMessage("Lane Clear");
            }
        }
        if (KeyInput.equals("Space") && getNoteType("Special").equals("Bomb")) {
            if (NotesSpecial.get(0).BombActivation()) {
                NotesSpecial.remove(0);
                CounterSpecial --;
                BombSpecialEffectHelper(currentFrame,NotesSpecial);
                playerScore.setMessage("Lane Clear");
            }
        }
    }

    /**
     * This is a helper method for the method mentioned above
     * @param currentFrame This refers to the number of frames has passed since the game started
     * @param Input arrayList containing all notes from a selected lane
     */
    void BombSpecialEffectHelper(int currentFrame, ArrayList<Notes> Input){
        //Loop through each note in the arrayList
        Iterator<Notes> iterator = Input.iterator();
        while (iterator.hasNext()) {
            Notes I = iterator.next();
            //If a note is spawned on screen, then remove it and update the counter
            if (currentFrame >= I.startFrame){
                iterator.remove();
                if (I.NoteLane.equals("Left")){
                    CounterNotesLeft --;
                }
                if (I.NoteLane.equals("Right")){
                    CounterNotesRight --;
                }
                if (I.NoteLane.equals("Down")){
                    CounterNotesDown --;
                }
                if (I.NoteLane.equals("Special")){
                    CounterSpecial --;
                }
            }
            else {
                break;
            }
        }
    }

    /**
     * The method is used to execute "DoubleScore" notes special effects
     */
    @Override
    void DoubleScoreEffect(){
        //Loop through an arrayList of all "DoubleScore" that are still in effect
        if (CounterDoubleScore > 0){
            Iterator<DoubleScoreNotes> iterator = DoubleScoreInEffect.iterator();
            while(iterator.hasNext()){
                Notes I = iterator.next();
                //After 480 frames, the effect should be expired
                if (I.DoubleScoreDuration > 480){
                    //Update the playerScore.ScoreModifier to its previous state and remove its effect
                    int mod = playerScore.getScoreModifier() / 2;
                    playerScore.setScoreModifier(mod);
                    iterator.remove();
                    CounterDoubleScore --;
                }
                else {
                    //The number of frames has passed since the note is activated
                    I.DoubleScoreDuration = I.DoubleScoreDuration + 1;
                }
            }
        }
    }

    /**
     * This method is a helper method for the method mentioned above, which check whether a "DoubleScore" note should
     * be activated or not. It also allows for overlapping of "DoubleScore" effects
     */
    @Override
    void DoubleScoreActivation(){
        //Check whether the note should be activated or not
        boolean DoubleScoreFlag = NotesSpecial.get(0).DoubleScoreActivation();
        if (DoubleScoreFlag) {
            if (getNoteType("Special").equals("DoubleScore")) {
                //Update the modifier if it is activated
                int mod = playerScore.getScoreModifier() * 2;
                playerScore.setScoreModifier(mod);
                playerScore.setMessage("DoubleScore");
                //If true, then add the note to an arrayList of "DoubleScore" notes that are still in effect
                DoubleScoreInEffect.add(new DoubleScoreNotes(NotesSpecial.get(0).NoteLane,NotesSpecial.get(0).xPos,NotesSpecial.get(0).NoteType,
                                                                NotesSpecial.get(0).startFrame));
                CounterDoubleScore ++;
                //Remove the note if it is activated
                NotesRemove("Special");

            }
        }
    }
}

