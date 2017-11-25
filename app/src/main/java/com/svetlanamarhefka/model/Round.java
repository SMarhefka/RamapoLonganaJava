package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;

import java.io.Serializable;
import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/12/2017                                          *
 ****************************************************************/

public class Round implements Serializable {

    private int m_TourScore;
    private int m_RoundNumber;
    private int m_EngineValue;
    private Boneyard m_Boneyard;
    private Board m_Board;
    private Human m_Human;
    private Computer m_Computer;


    public Round()
    {
        this.m_TourScore = 150;
        this.m_RoundNumber = 1;
        this.m_EngineValue = getEngine();
        this.m_Boneyard = new Boneyard();
    }

    public Round(int a_InTourScore, int a_InRoundNum, Computer a_InComputer, Human a_InHuman)
    {
        m_TourScore = a_InTourScore;
        m_RoundNumber = a_InRoundNum;
        m_EngineValue = getEngine();
        m_Boneyard = new Boneyard();
        m_Board = new Board();
        m_Computer = a_InComputer;
        m_Human = a_InHuman;
    }

    private int getEngine()
    {
        int a_count = 1;
        int a_engineCount = 6;
        while (a_count != m_RoundNumber)
        {
            if (a_engineCount == 0)
            {
                // reset the engine count
                a_engineCount = 6;
            }
            else
            {
                // reduce engine count by 1
                a_engineCount--;
            }
            // increment the count by 1
            a_count++;
        }
        return a_engineCount;
    }

    private void distributeTiles()
    {
        // distribute 8 tiles to each player
        for (int count = 0; count <= 7; count++)
        {
            // Give a tile to the player
            m_Human.getHand().addTileToHand(m_Boneyard.dealTile());
            // Give a tile to the computer
            m_Computer.getHand().addTileToHand(m_Boneyard.dealTile());
        }
    }

    public int getM_TourScore()
    {
        return m_TourScore;
    }

    public int getM_RoundNumber()
    {
        return m_RoundNumber;
    }

    public Vector<Domino> getBoneYard()
    {
        return m_Boneyard.getM_UnusedTiles();
    }

    public String playerName()
    {
        return m_Human.getPlayerName();
    }



    /**
     * To find the round winner and update the scores
     * @return String including the round winner and respective player scores
     */
    public String getRoundWinnerAndScore() {
        StringBuilder scores = new StringBuilder();
        //holds sum of all tiles for human
        int l_HumanTotal = m_Human.getHand().getHandTotal();
        //holds sum of all tiles for computer
        int l_CompTotal = m_Computer.getHand().getHandTotal();
        scores.append("Computer Score: ").append(l_HumanTotal)
                .append("\nHuman Score: ").append(l_CompTotal).append("\n");

        int score = 0;
        //if human emptied the hand , he wins
        if (m_Human.getHand().isEmpty()) {
            scores.append("Human");
            m_Human.addToScore(l_CompTotal);
            score = l_CompTotal;
        }
        //if computer emptied the hand, computer wins
        else if (m_Computer.getHand().isEmpty()) {
            scores.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            score = l_HumanTotal;
        }
        //if human has less sum than computer, human wins
        else if (l_HumanTotal < l_CompTotal) {
            scores.append("Human");
            m_Human.addToScore(l_CompTotal);
            score = l_CompTotal;
        }
        //if computer has less sum, computer wins
        else if (l_HumanTotal > l_CompTotal) {
            scores.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            score = l_HumanTotal;
        }
        //if equal, its a draw
        else {
            scores.append("The round ended with a draw!");
            return scores.toString();
        }
        return scores.append(" won this round with a score of ").append(score).toString();
    }
}