/* MachinePlayer.java */

package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 *
 *  The myBoard field stores the internal game board of "this" MachinePlayer. The color field stores the color of "this" MachinePlayer.
 *  The depth field stores the remaining search depths. The DEPTH field stores the search depth limit (a constant after the MachinePlayer 
 *  is created) of "this" MachinePlayer. The BLACK field stores the representation of the black color(0). The WHITE field stores the 
 *  representation of the white color(1). The MAX_VALUE field stores the highest possible score of a move(same with MAX_VALUE in Gameboard).
 *  The MIN_VALUE field stores the lowest possible score of a move(same with MIN_VALUE in Gameboard).
 */
public class MachinePlayer extends Player {

  private Gameboard myBoard;
  private int depth, color;
  private int DEPTH;
  private static final int BLACK = 0;
  private static final int WHITE = 1;
  private static final int MAX_VALUE = 100000;
  private static final int MIN_VALUE = -100000;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this(color, 3);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    myBoard = new Gameboard();
    this.color = color;
    depth = searchDepth;
    DEPTH = searchDepth;
  }

  // Applies Alpha-Beta Search on the current game board for the given color, to the given depth. Returns a Best object which contains 
  // the best move of the given color and the corresponding score of that move.
  private Best alphaBeta(int color, int depth, int alpha, int beta){
    Move[] possibleMoves = myBoard.allPossibleMoves(color);
    
    Best myBest = new Best();
    Best reply; 

    if(myBoard.hasNetwork(this.color)){
      myBest.score = MAX_VALUE;
      return myBest;
    }else if(myBoard.hasNetwork(1 - this.color)){
      myBest.score = MIN_VALUE;
      return myBest;
    }
    if(color == this.color){
      myBest.score = alpha;
    }else{
      myBest.score = beta;
    }
    
    myBest.move = possibleMoves[0];

    if(depth == 1){
      for(Move m : possibleMoves){
        myBoard.setMove(m);
        int score = myBoard.evaluate(this.color);
        myBoard.undo(m);
        if(color == this.color && score > myBest.score){
          myBest.move = m;
          myBest.score = score;
        }else if(color != this.color && score < myBest.score){
          myBest.move = m;
          myBest.score = score;
        }
        
      }
    }else{
      for(Move m : possibleMoves){
        myBoard.setMove(m);
        reply = alphaBeta(1-color, depth-1, alpha, beta);
        myBoard.undo(m);
        if((color == this.color) && (reply.score > myBest.score)){
          myBest.move = m;
          myBest.score = reply.score;
          alpha = reply.score;
        } else if ((this.color != color) && (reply.score < myBest.score)){
          myBest.move = m;
          myBest.score = reply.score;
          beta = reply.score;
        }
        if(alpha >= beta) {return myBest;}
      }
    }
    
    if(color == this.color && myBest.score >= MAX_VALUE){
      myBest.score -= 20 * (DEPTH-depth);
    }else if(color == (1 - this.color) && myBest.score <= MIN_VALUE){
      myBest.score += 20 * (DEPTH-depth);
    }

    return myBest;
  }

  

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    Best myBest = alphaBeta(color, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    myBoard.setMove(myBest.move);
    return myBest.move;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if(myBoard.isValidMove(m, 1 - this.color)){
      myBoard.setMove(m);
      return true;
    }
    return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    if(myBoard.isValidMove(m, this.color)){
      myBoard.setMove(m);
      return true;
    }
    return false;
  }

}
