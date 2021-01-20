import java.util.*;

public class Battleship {
    
    private int mLength;
    private int mOrientation;
    private ArrayList<String> mBattleshipPos = new ArrayList<String>();

    public Battleship(int length) {
        float tempOrientation = (float) Math.random() * 2;
        mOrientation = (int) Math.floor(tempOrientation);
        mLength = length;
    }

    public int getLength() {
        return mLength;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public ArrayList<String> getStartingPos() {
        return mBattleshipPos;
    }

    //if orientation == 0 (Hoz) / orientation == 1 (Vert)
    public void placeBattleship() {
        float randX = (float) Math.random() * 9;
        float randY = (float) Math.random() * 9;
        int x = (int) Math.floor(randX);
        int y = (int) Math.floor(randY);

        mBattleshipPos.add(Screen.grid[x][y]);
        if(mLength == 2) {
            try {
                if(mOrientation == 0) {
                    mBattleshipPos.add(Screen.grid[x+1][y]);
                } else {
                    mBattleshipPos.add(Screen.grid[x][y+1]);
                }
            } catch(Exception ex) {
                mBattleshipPos.clear();
                placeBattleship();
            }
        } else if(mLength == 3) {
            try {
                if(mOrientation == 0) {
                    mBattleshipPos.add(Screen.grid[x+1][y]);
                    mBattleshipPos.add(Screen.grid[x+2][y]);
                } else {
                    mBattleshipPos.add(Screen.grid[x][y+1]);
                    mBattleshipPos.add(Screen.grid[x][y+2]);
                }
            } catch(Exception ex) {
                mBattleshipPos.clear();
                placeBattleship();
            }
        } else if(mLength == 5) {
            try {
                if(mOrientation == 0) {
                    mBattleshipPos.add(Screen.grid[x+1][y]);
                    mBattleshipPos.add(Screen.grid[x+2][y]);
                    mBattleshipPos.add(Screen.grid[x+3][y]);
                    mBattleshipPos.add(Screen.grid[x+4][y]);
                } else {
                    mBattleshipPos.add(Screen.grid[x][y+1]);
                    mBattleshipPos.add(Screen.grid[x][y+2]);
                    mBattleshipPos.add(Screen.grid[x][y+3]);
                    mBattleshipPos.add(Screen.grid[x][y+4]);
                }
            } catch(Exception ex) {
                mBattleshipPos.clear();
                placeBattleship();
            }
        }
    }
}