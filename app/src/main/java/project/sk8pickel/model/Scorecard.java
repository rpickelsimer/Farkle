package project.sk8pickel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Ryan on 6/1/2016.
 */
public class Scorecard implements Parcelable {

    private ArrayList<String> names;
    private ArrayList<Integer> scores;
    private int numPlayers;
    private int turnCount;
    private boolean gameOver;

    public Scorecard() {
        names = new ArrayList<>();
        scores = new ArrayList<>();
        numPlayers = 0;
        gameOver = false;
    }
    public void addName(String name){
        names.add(name);
        scores.add(0);
        turnCount = ++numPlayers;
    }

    public String getName(int i) {
        return names.get(i);
    }

    public int getScore(int i) {
        return scores.get(i);
    }

    /**
     *
     * @param i
     * @param score
     * @return true if game is over and false if not
     */
    public boolean incrementScore(int i, int score) {
        int newScore = scores.get(i) + score;
        scores.set(i, newScore);
        if (newScore >= 10000) gameOver = true;
        if (gameOver) turnCount--;
        if (turnCount == 0) return true;
        return false;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /*public boolean hasWinner() {
        Object obj = Collections.max(scores);

        return false;
    }*/

    /**
     * Override method for parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Scorecard> CREATOR = new Creator<Scorecard>() {
        public Scorecard createFromParcel(Parcel source) {
            Scorecard mScorecard = new Scorecard();

            /**mScorecard.ScorecardName = source.readString();

            mScorecard.author = source.readString();
            33
            mScorecard.publishTime = source.readInt();
            34**/
            return mScorecard;
        }

        public Scorecard[] newArray(int size) {
            return new Scorecard[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
