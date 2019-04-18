package com.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 is cross
    // 1 is circle
    // 2 is empty

    int activePlayer = 0;

    int numberOfPlays = 0;

    boolean gameActive = true;

    int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    /**
     * This method makes a move based on the players choice of placement
     * Checks if someone has won or if it's a tie
     * @param view
     */
    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        int tag = Integer.parseInt(counter.getTag().toString());

        if (gameState[tag] == 2 && gameActive) {

            gameState[tag] = activePlayer;

            counter.setTranslationY(-1000);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.cross);
                activePlayer = 1;
            }
            else {
                counter.setImageResource(R.drawable.circle);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000).setDuration(300);

            numberOfPlays++;

            // Checks if somebody won
            for (int [] winningPosition: winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    // Someone has won
                    gameActive = false;

                    String winner;

                    if (activePlayer == 0) winner = "Circle";

                    else winner = "Cross";

                    Button playAgain = findViewById(R.id.playAgainButton);
                    TextView winMessage = findViewById(R.id.winnerTextView);

                    playAgain.setVisibility(View.VISIBLE);
                    winMessage.setText(winner + " has won!");
                    winMessage.setVisibility(View.VISIBLE);
                }
            }

            // Checks if there are no moves left after nobody won
            if (numberOfPlays == 9) {
                Button playAgain = findViewById(R.id.playAgainButton);
                TextView winMessage = findViewById(R.id.winnerTextView);

                playAgain.setVisibility(View.VISIBLE);
                winMessage.setText("It's a tie!");
                winMessage.setVisibility(View.VISIBLE);

                gameActive = false;
            }
        }
    }

    /**
     * Resets the game status so it's possible to play again
     * @param view
     */
    public void toPlayAgain(View view) {

        Button playAgain = (Button) findViewById(R.id.playAgainButton);
        TextView winMessage = (TextView) findViewById(R.id.winnerTextView);

        playAgain.setVisibility(View.INVISIBLE);
        winMessage.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView child = (ImageView) gridLayout.getChildAt(i);
            child.setImageDrawable(null);
        }

        for (int j = 0; j < gameState.length; j++) {
            gameState[j] = 2;
        }

        activePlayer = 0;
        gameActive = true;
        numberOfPlays = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
