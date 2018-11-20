import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It implements 
 * the interface ActionListener to be called back when the player makes a move. It computes
 * the next step of the game, and then updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    /**
     * Reference to the view of the game
     */
    private GameView gameView;

    /**
     * Reference to the model of the game
     */
    public GameModel gameModel;
    
    public LinkedStack<GameModel> undo= new LinkedStack<GameModel>();
    
    public LinkedStack<GameModel> redo= new LinkedStack<GameModel>();
    
    
    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) {
        gameModel = new GameModel(size);
        gameView = new GameView(gameModel, this);
        gameView.update();
    }


 
    /**
     * resets the game
     */
    public void reset(){
        gameModel.reset();
        gameView.update();
        undo= new LinkedStack<GameModel>();
        redo=new LinkedStack<GameModel>();
    }
    public void undo(){
      if( !undo.isEmpty()){
              GameModel temp= undo.pop();
              redo.push(gameModel.clone());
              gameModel= temp;
              gameView.board.gameModel=temp;
              gameView.gameModel=temp;
              gameView.update();
              }
    }
    public void redo(){
      if (!redo.isEmpty()){
                GameModel temp2= redo.pop();
                undo.push(gameModel.clone());
                gameModel=temp2;
                gameView.gameModel=temp2;
                gameView.board.gameModel=temp2;
                gameView.update();
              }
    }
    

    /**
     * Callback used when the user clicks a button or one of the dots. 
     * Implements the logic of the game
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof DotButton) {
          
          
            DotButton clicked = (DotButton)(e.getSource());

         if (gameModel.getCurrentStatus(clicked.getColumn(),clicked.getRow()) ==
                    GameModel.AVAILABLE){
           undo.push(gameModel.clone());
                gameModel.select(clicked.getColumn(),clicked.getRow());
                gameView.update();
                //oneStep();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton clicked = (JButton)(e.getSource());

            if (clicked.getText().equals("Quit")) {
                 System.exit(0);
            } else if (clicked.getText().equals("Reset")){
                reset();
            } else if (clicked.getText().equals("Undo")){
              undo();
            } else if (clicked.getText().equals("Redo")){
              redo();
            }
    }

    }


}
