import bagel.*;

/**
 * SHADOWDANCE GAME
 * Author: Eric Nguyen
 */
public class ShadowDance extends AbstractGame  {

    /**************** Attributes ****************/
    //Window size, game title, game initial messages
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final Font FONT_TITLE = new Font("res/FSO8BITR.TTF", 64);
    private final Font FONT_MESSAGE = new Font("res/FSO8BITR.TTF", 24);

    //Flags to indicate whether the game has reached its end or whether it has just begun
    private boolean startFlag = true;
    private boolean endFlag = false;

    //Flags to indicate whether the player is supposed to hold or not/
    boolean HoldLeft = false;
    boolean HoldRight = false;
    boolean HoldUp = false;
    boolean HoldDown = false;

    //Variables used to count Frames
    private int currentFrame = 0;
    private int CountFrame = 0;

    //Player selected level
    private Level playerLevel;
    private int LevelSelected;
    /**************** --------- ****************/

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    private void LevelSelect(Input input){
        if (input.wasPressed(Keys.NUM_1)){
            LevelSelected = 1;
            playerLevel = new Level1();
            playerLevel.ReadNotes(1);
            startFlag = false;
        }
        else if(input.wasPressed(Keys.NUM_2)){
            LevelSelected = 2;
            playerLevel = new Level2();
            playerLevel.ReadNotes(2);
            startFlag = false;
        }
        else if(input.wasPressed(Keys.NUM_3)){
            LevelSelected = 3;
            playerLevel = new Level3();
            playerLevel.ReadNotes(3);
            startFlag = false;
        }
    }

    /**
     * Construct background for the game
     */
    private void drawBackground (){
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        playerLevel.DrawLanesAndScore(LevelSelected);
        playerLevel.SpawnNote(currentFrame);
        playerLevel.UpdateNotePosition(currentFrame);
    }

    /**
     * Print message when player scores a note
     */
    private void printMessage (){
        if(CountFrame <= 30){
            if(playerLevel.getPlayerScoreMessage() != null){
                playerLevel.printMessage();
                CountFrame ++;
            }
        }
        if(CountFrame > 30){
            playerLevel.setPlayerScoreMessageNull();
            CountFrame = 0;
        }
    }

    /**
     * Reset parameters when the game reaches its end state
     * @param input Key pressed by player
     */
    private void GameReset (Input input){
        playerLevel.EndGame(LevelSelected);
        if (input.wasPressed(Keys.SPACE)){
            startFlag = true;
            endFlag = false;
            HoldLeft = false;
            HoldRight = false;
            HoldUp = false;
            HoldDown = false;
            currentFrame = 0;
            CountFrame = 0;
            LevelSelected = 0;
            playerLevel = null;
        }
    }

    /**
     * Handles entities that appear in level 3 design
     */
    private void Level3Entities (){
        /*
        If LevelSelected == 3, projectile position is continuously updated as each frame passes
        Guardian is also spawned.
        */
        playerLevel.ProjectilesMovement(currentFrame);
        playerLevel.SpawnEntities(currentFrame);

        //Double Score effect activated if DoubleScore is successfully processed.
        playerLevel.DoubleScoreEffect();
    }
    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        /*
        Used to prevent edge case where if a hold note appear just a few frames after a normal note
        When player presses for normal notes, if the button is not released quickly enough,
        the hold note may get processed as well.
        */
        boolean NotPressed = true;

        //Game start
        if (startFlag) {
            //Print game titles and initial messages
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            FONT_TITLE.drawString(GAME_TITLE, 220, 250);
            FONT_MESSAGE.drawString("SELECT LEVEL WITH", 320, 440);
            FONT_MESSAGE.drawString("NUMBER KEYS", 320, 470);
            FONT_MESSAGE.drawString("1       2       3",365,520 );

            //Select level
            LevelSelect(input);
        }

        else if (!endFlag) {
            ++currentFrame;
            drawBackground();

            //When LEFT key is pressed, process normal and bomb notes in Left lane as well as Bomb special effect
            if (input.wasPressed(Keys.LEFT)) {
                if (playerLevel.getCounterNotesLeft() > 0) {
                    if (playerLevel.getNoteType("Left").equals("Normal")) {
                        playerLevel.UpdateNormalNoteScore("Left");
                        NotPressed = false;
                    }
                    else if (playerLevel.getNoteType("Left").equals("Bomb")){
                        playerLevel.BombSpecialEffect("Left",currentFrame);
                        NotPressed = false;
                    }
                }
            }

            //When RIGHT key is pressed, process normal and bomb notes in Right lane as well as Bomb special effect
            if (input.wasPressed(Keys.RIGHT)){
                if (playerLevel.getCounterNotesRight() > 0) {
                    if (playerLevel.getNoteType("Right").equals("Normal")) {
                        playerLevel.UpdateNormalNoteScore("Right");
                        NotPressed = false;
                    }
                    else if (playerLevel.getNoteType("Right").equals("Bomb")){
                        playerLevel.BombSpecialEffect("Right",currentFrame);
                        NotPressed = false;
                    }
                }
            }

            //When UP key is pressed, process normal and bomb notes in Up lane as well as Bomb special effect
            if (input.wasPressed(Keys.UP)){
                if (playerLevel.getCounterNotesUp() > 0) {
                    if (playerLevel.getNoteType("Up").equals("Normal")) {
                        playerLevel.UpdateNormalNoteScore("Up");
                        NotPressed = false;
                    }
                    else if (playerLevel.getNoteType("Up").equals("Bomb")){
                        playerLevel.BombSpecialEffect("Up",currentFrame);
                        NotPressed = false;
                    }
                }
            }

            //When DOWN key is pressed, process normal and bomb notes in Down lane as well as Bomb special effect
            if (input.wasPressed(Keys.DOWN)){
                if (playerLevel.getCounterNotesDown() > 0) {
                    if (playerLevel.getNoteType("Down").equals("Normal")) {
                        playerLevel.UpdateNormalNoteScore("Down");
                        NotPressed = false;
                    }
                    else if (playerLevel.getNoteType("Down").equals("Bomb")){
                        playerLevel.BombSpecialEffect("Down",currentFrame);
                        NotPressed = false;
                    }

                }
            }

            //When SPACE key is pressed, process special notes and implement their special effects
            if (input.wasPressed(Keys.SPACE)){
                if (playerLevel.getCounterSpecial() > 0){
                    playerLevel.SpeedChangeEffect();
                    playerLevel.BombSpecialEffect("Space",currentFrame);
                    playerLevel.DoubleScoreActivation();
                }
            }

            //When LEFT_SHIFT key is pressed, Guardian shoots out an Arrow
            if (input.wasPressed(Keys.LEFT_SHIFT)){
                if (playerLevel.getCountEnemies() > 0){
                    playerLevel.SpawnProjectile();
                }
            }

            /*
            For each hold note, when a key pressed matches the Lane it is in, score will be updated for the lower end of the note
            If NotPressed is true, that means there is no normal notes just a few frames before the hold note
             */
            if (input.wasPressed(Keys.LEFT) && NotPressed){
                if (playerLevel.getCounterNotesLeft() > 0) {
                    if (playerLevel.getNoteType("Left").equals("Hold")) {
                        playerLevel.UpdateHoldNoteScoreLower("Left");
                        HoldLeft = true;
                    }
                }
            }
            //score will be also updated for the higher end of the note when the key is released
            if (input.wasReleased(Keys.LEFT) && HoldLeft){
                if (playerLevel.getCounterNotesLeft() > 0) {
                    if (playerLevel.getNoteType("Left").equals("Hold")) {
                        playerLevel.UpdateHoldNoteScoreHigher("Left");
                        HoldLeft = false;
                    }
                }
            }

            if (input.wasPressed(Keys.RIGHT) && NotPressed){
                if (playerLevel.getCounterNotesRight() > 0){
                    if (playerLevel.getNoteType("Right").equals("Hold")){
                        playerLevel.UpdateHoldNoteScoreLower("Right");
                        HoldRight = true;
                    }
                }
            }
            if (input.wasReleased(Keys.RIGHT) && HoldRight){
                if (playerLevel.getCounterNotesRight() > 0) {
                    if (playerLevel.getNoteType("Right").equals("Hold")) {
                        playerLevel.UpdateHoldNoteScoreHigher("Right");
                        HoldRight = false;
                    }
                }
            }

            if (input.wasPressed(Keys.UP) && NotPressed){
                if (playerLevel.getCounterNotesUp() > 0){
                    if (playerLevel.getNoteType("Up").equals("Hold")){
                        playerLevel.UpdateHoldNoteScoreLower("Up");
                        HoldUp = true;
                    }
                }
            }
            if (input.wasReleased(Keys.UP) && HoldUp){
                if (playerLevel.getCounterNotesUp() > 0) {
                    if (playerLevel.getNoteType("Up").equals("Hold")) {
                        playerLevel.UpdateHoldNoteScoreHigher("Up");
                        HoldUp = false;
                    }
                }
            }


            if (input.wasPressed(Keys.DOWN) && NotPressed){
                if (playerLevel.getCounterNotesDown() > 0){
                    if (playerLevel.getNoteType("Down").equals("Hold")){
                        playerLevel.UpdateHoldNoteScoreLower("Down");
                        HoldDown = true;
                    }
                }
            }
            if (input.wasReleased(Keys.DOWN) && HoldDown){
                if (playerLevel.getCounterNotesDown() > 0) {
                    if (playerLevel.getNoteType("Down").equals("Hold")) {
                        playerLevel.UpdateHoldNoteScoreHigher("Down");
                        HoldDown = false;
                    }
                }
            }

            //Print the message player receive for 30 frames
            printMessage ();

            //Logic for level 3 entities
            Level3Entities();

            //Check whether the game has reached the end or not.
            endFlag = playerLevel.EndState();


        }

        //If the game has reached the end, print end game messages and reset all attributes.
        else if (endFlag) {
            GameReset(input);
        }
    }
}
