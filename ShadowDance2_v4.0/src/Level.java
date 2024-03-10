import bagel.Font;
import bagel.Image;
import bagel.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An abstract class that handles all common characteristics of all 3 levels in the game
 */
abstract class Level {

                                    /******** ATTRIBUTES **********/
    //End game messages font
    final Font END_MESSAGE = new Font("res/FSO8BITR.TTF",64);
    final Font PLAY_AGAIN = new Font("res/FSO8BITR.TTF",24);
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    //Window size
    final double STATIONARY_Y = 657;
    private final static double WINDOW_WIDTH = 1024;
    private final static double WINDOW_HEIGHT = 768;

    //ArrayLists, each contains all notes that are supposed to be spawned in the corresponding lanes
    ArrayList <Notes> NotesLeft = new ArrayList<Notes>();
    ArrayList <Notes> NotesRight= new ArrayList<Notes>();
    ArrayList <Notes> NotesUp = new ArrayList<Notes>();
    ArrayList <Notes> NotesDown = new ArrayList<Notes>();
    ArrayList <Notes> NotesSpecial = new ArrayList<Notes>();

    //ArrayList that contains all lanes that are available for the level selected
    ArrayList <Lanes> LanesList;

    //ArrayList that contains all Enemies spawned
    ArrayList <Enemy> Enemies = new ArrayList<Enemy>();

    //ArrayList that contains all Double Score notes that are in effect
    ArrayList <DoubleScoreNotes> DoubleScoreInEffect = new ArrayList<DoubleScoreNotes>();

    //Guardian and Projectile object
    Guardian guardian = new Guardian();
    Projectile Arrow = null;

    //Counter for arrayLists mentioned above
    int CounterNotesLeft = 0;
    int CounterNotesRight = 0;
    int CounterNotesUp = 0;
    int CounterNotesDown = 0;
    int CounterSpecial = 0;
    int CounterDoubleScore = 0;
    int CountEnemies = 0;

    //Player's Score
    Score playerScore = new Score();

    //Game speed
    int Speed = 2;

                                    /********* METHODS ***********/
    /**
     * This method is used to read CSV file and add data read in appropriate arrayLists
     * @param LevelSelected This is player's selected level
     */
    void ReadNotes(int LevelSelected){
        //Find all available lanes for the level selected
        LanesList = Lanes.LaneReadCSV(LevelSelected);

        //Choose the correct CSV file to read according to the level selected
        String FilePath = "res/level1.csv";
        if (LevelSelected == 2){
            FilePath = "res/level2.csv";
        }
        else if(LevelSelected == 3){
            FilePath = "res/level3.csv";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            String text = null;
            while ((text = br.readLine()) != null) {
                String words[] = text.split(",");

                /*
                Find all normal notes, classify and add them to the corresponding arrayLists
                according to the lane they are supposed to be spawned in
                */
                if (words[1].equals("Normal")) {
                    if (words[0].equals("Left")) {
                        CounterNotesLeft++;
                        if (LevelSelected == 1) {
                            NotesLeft.add(new NormalNotes("Left", LanesList.get(0).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                        else if (LevelSelected == 2 || LevelSelected == 3){
                            NotesLeft.add(new NormalNotes("Left", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                    } else if (words[0].equals("Right")) {
                        CounterNotesRight++;
                        NotesRight.add(new NormalNotes("Right", LanesList.get(1).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equals("Up")) {
                        CounterNotesUp ++;
                        NotesUp.add(new NormalNotes("Up", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equals("Down")) {
                        CounterNotesDown ++;
                        NotesDown.add(new NormalNotes("Down", LanesList.get(3).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    }
                }

                /*
                Find all Hold notes, classify and add them to the corresponding arrayLists
                according to the lane they are supposed to be spawned in
                */
                if (words[1].equals("Hold")) {
                    if (words[0].equals("Left")) {
                        CounterNotesLeft ++;
                        if (LevelSelected == 1) {
                            NotesLeft.add(new HoldNotes("Left", LanesList.get(0).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                        else if (LevelSelected == 2 || LevelSelected == 3){
                            NotesLeft.add(new HoldNotes("Left", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                    } else if (words[0].equals("Right")) {
                        CounterNotesRight ++;
                        NotesRight.add(new HoldNotes("Right", LanesList.get(1).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equals("Up")) {
                        CounterNotesUp ++;
                        NotesUp.add(new HoldNotes("Up", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equals("Down")) {
                        CounterNotesDown ++;
                        NotesDown.add(new HoldNotes("Down", LanesList.get(3).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    }
                }

                /*
                Find all special notes, classify and add them to the corresponding arrayLists
                according to the lane that they are supposed to be spawned in
                */
                if (words[1].equals("SpeedUp") || words[1].equals("SlowDown")){
                    CounterSpecial ++;
                    NotesSpecial.add(new SpeedChangeNotes("Special",LanesList.get(0).Coordinates_x,words[1],Integer.parseInt(words[2])));
                }
                if (words[1].equals("DoubleScore")){
                    CounterSpecial ++;
                    NotesSpecial.add(new DoubleScoreNotes("Special",LanesList.get(0).Coordinates_x,words[1],Integer.parseInt(words[2])));
                }

                /*
                Find all Bomb notes, classify and add them to the corresponding arrayLists
                according to the lane they are supposed to be spawned in
                */
                if (words[1].equals("Bomb")){
                    if (words[0].equals("Left")){
                        CounterNotesLeft ++;
                        if (LevelSelected == 1) {
                            NotesLeft.add(new BombNotes("Left", LanesList.get(0).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                        else if (LevelSelected == 2 || LevelSelected == 3){
                            NotesLeft.add(new BombNotes("Left", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                        }
                    }
                    else if(words[0].equals("Right")){
                        CounterNotesRight ++;
                        NotesRight.add(new BombNotes("Right", LanesList.get(1).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    }
                    else if(words[0].equals("Up")){
                        CounterNotesUp ++;
                        NotesUp.add(new BombNotes("Up", LanesList.get(2).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    }
                    else if(words[0].equals("Down")){
                        CounterNotesDown ++;
                        NotesDown.add(new BombNotes("Down", LanesList.get(3).Coordinates_x, words[1], Integer.parseInt(words[2])));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to read CSV file and find available lanes
     * @param LevelSelected This is player's selected level
     */
    void DrawLanesAndScore(int LevelSelected){
        Lanes.DrawLanes(LevelSelected);
        playerScore.DrawScore();
    }

    /**
     * This method is used to return the NoteType of the first note in the selected lane
     * @param NoteLane This is the lane selected
     * @return String This is type of the first note in the corresponding lane (Normal, Hold, SpeedChange, Bomb)
     */
    String getNoteType(String NoteLane){
        if (NoteLane.equals("Left")) {
            if (CounterNotesLeft > 0) {
                return NotesLeft.get(0).NoteType;
            }
        } else if (NoteLane.equals("Right")) {
            if (CounterNotesRight > 0) {
                return NotesRight.get(0).NoteType;
            }
        } else if (NoteLane.equals("Up")) {
            if (CounterNotesUp > 0) {
                return NotesUp.get(0).NoteType;
            }
        } else if (NoteLane.equals("Down")) {
            if (CounterNotesDown > 0) {
                return NotesDown.get(0).NoteType;
            }
        } else if (NoteLane.equals("Special")) {
            if (CounterSpecial > 0) {
                return NotesSpecial.get(0).NoteType;
            }
        }
        return null;
    }

    /**
     * This method is used to update the position of each note as each frame passes
     * @param currentFrame This refers to the number of frames has passed
     */
    abstract void UpdateNotePosition(int currentFrame);


    /**
     * This is a helper method of UpdateNotePosition function above
     * @param currentFrame This refers to the number of frames has passed since the game started
     * @param Input This refers the chosen ArrayList of notes (NotesLeft, NotesRight ,...)
     */
    void UpdateNoteHelper(int currentFrame, ArrayList<Notes> Input) {
        if (!Input.isEmpty()) {
            Iterator<Notes> iterator = Input.iterator();

            //Loop through each note in the input ArrayList
            while (iterator.hasNext()) {
                Notes I = iterator.next();
                if (currentFrame > I.startFrame) {
                    //If a note leaves the window
                    if (I.yPos >= 768) {
                        //Update counter as well as player's score
                        if (I.NoteLane.equals("Left")){
                            CounterNotesLeft --;
                            if (!I.NoteType.equals("Bomb")){
                                playerScore.ScoreUpdate(I.yPos);
                            }
                        }
                        if (I.NoteLane.equals("Right")){
                            CounterNotesRight --;
                            if (!I.NoteType.equals("Bomb")){
                                playerScore.ScoreUpdate(I.yPos);
                            }
                        }
                        if (I.NoteLane.equals("Up")){
                            CounterNotesUp --;
                            if (!I.NoteType.equals("Bomb")){
                                playerScore.ScoreUpdate(I.yPos);
                            }
                        }
                        if (I.NoteLane.equals("Down")){
                            CounterNotesDown --;
                            if (!I.NoteType.equals("Bomb")){
                                playerScore.ScoreUpdate(I.yPos);
                            }
                        }
                        if (I.NoteLane.equals("Special")){
                            CounterSpecial --;
                        }
                        //Remove that note
                        iterator.remove();
                    }
                    //Or else update its position
                    else {
                        I.NotesMovement(Speed);
                    }
                }
            }
        }
    }

    /**
     * This method is used to determine if a note is 200 pixels or more from STATIONARY_Y
     * @param y_coordinate This refers to the yPos of a note
     * @return boolean This determines whether it is true that a note is 200 pixels or more away from STATIONARY_Y
     */
    boolean DistanceMoreThan200(double y_coordinate){
        if(Math.abs(y_coordinate - STATIONARY_Y) > 200){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method is used to determines whether the game has reached its end
     * @return boolean Is it true that the game has reached its end
     */
    boolean EndState(){
        if ((CounterNotesLeft == 0)&&(CounterNotesRight == 0)&&(CounterNotesUp == 0)&&(CounterNotesDown == 0)&&(CounterSpecial == 0)){
            return true;
        }
        return false;
    }

    /**
     * This method is used to print End game messages
     * @param LevelSelected The player's selected level
     */
    void EndGame(int LevelSelected){
        //Check whether the game has reached its end
        if (EndState()) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            //Check whether the player has satisfied the condition to be considered as a winner of the selected level
            if (LevelSelected == 1) {
                if (playerScore.getScore() >= 150) {
                    END_MESSAGE.drawString("CLEAR!", (WINDOW_WIDTH - END_MESSAGE.getWidth("CLEAR!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
                else {
                    END_MESSAGE.drawString("TRY AGAIN!", (WINDOW_WIDTH - END_MESSAGE.getWidth("TRY AGAIN!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
            } else if (LevelSelected == 2) {
                if (playerScore.getScore() >= 400) {
                    END_MESSAGE.drawString("CLEAR!", (WINDOW_WIDTH - END_MESSAGE.getWidth("CLEAR!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
                else {
                    END_MESSAGE.drawString("TRY AGAIN!", (WINDOW_WIDTH - END_MESSAGE.getWidth("TRY AGAIN!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
            } else if (LevelSelected == 3) {
                if (playerScore.getScore() >= 350) {
                    END_MESSAGE.drawString("CLEAR!", (WINDOW_WIDTH - END_MESSAGE.getWidth("CLEAR!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
                else {
                    END_MESSAGE.drawString("TRY AGAIN!", (WINDOW_WIDTH - END_MESSAGE.getWidth("TRY AGAIN!")) / 2, (WINDOW_HEIGHT - 64) / 2);
                }
            }
            PLAY_AGAIN.drawString("PRESS SPACE TO RETURN TO LEVEL SELECTION!", (WINDOW_WIDTH - PLAY_AGAIN.getWidth("PRESS SPACE TO RETURN TO LEVEL SELECTION!")) / 2, 500);
        }
    }


    /**
     * This method is used to print message as each note is successfully processed
     */
    void printMessage(){
        playerScore.PrintMessage();
    }

    String getPlayerScoreMessage (){return playerScore.getMessage();}
    int getCounterNotesLeft(){return CounterNotesLeft;}
    int getCounterNotesRight(){return CounterNotesRight;}
    int getCounterNotesUp(){return CounterNotesUp;}
    int getCounterNotesDown(){return CounterNotesDown;}
    int getCounterSpecial(){return CounterSpecial;}
    int getCounterDoubleScore(){return CounterDoubleScore;}
    int getCountEnemies (){return CountEnemies;}
    void setPlayerScoreMessageNull(){playerScore.setMessageNull();}



    /**
     * These 3 methods below used to update player's score for Normal notes and Hold notes
     * @param KeyInput Key pressed by the player
     */
    abstract void UpdateNormalNoteScore(String KeyInput);
    abstract void UpdateHoldNoteScoreLower(String KeyInput);
    abstract void UpdateHoldNoteScoreHigher(String KeyInput);

    /**
     * This method is used to spawn notes
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    abstract void SpawnNote(int currentFrame);

    /**
     * This method is used to execute "SpeedDown" and "SpeedUp" notes effects
     */
    abstract void SpeedChangeEffect();

    /**
     * This method is used to execute Bomb notes special effects
     * @param KeyInput Which lane is the bomb note in
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    abstract void BombSpecialEffect(String KeyInput, int currentFrame);

    /**
     * This method is used to execute the DoubleScore notes effects
     */
    abstract void DoubleScoreEffect();

    /**
     *  This method is used to check whether the DoubleScore notes effects should be activated or not
     */
    abstract void DoubleScoreActivation();

    /**
     * This method is used to spawn entities, including Enemies, Guardian for level 3
     * @param currentFrame  This refers to the number of frames has passed since the game started
     */
    abstract void SpawnEntities(int currentFrame);

    /**
     * This method is used to update the position of each Arrow shot by the Guardian
     * @param currentFrame This refers to the number of frames has passed since the game started
     */
    abstract void ProjectilesMovement(int currentFrame);

    /**
     * This method is used to spawned Arrow
     */
    abstract void SpawnProjectile();

}
