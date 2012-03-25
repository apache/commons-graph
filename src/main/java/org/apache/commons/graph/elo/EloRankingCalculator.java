package org.apache.commons.graph.elo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static java.lang.Math.pow;

import org.apache.commons.graph.DirectedGraph;

final class EloRankingCalculator<P>
{

    private static final double DEFAULT_POW_BASE = 10;

    private static final double DEFAULT_DIVISOR = 400;

    private static final int DEFAULT_K_FACTOR = 32;

    private final DirectedGraph<P, GameResult> tournamentGraph;

    private final PlayersRank<P> playerRanking;

    private final int kFactor;

    public EloRankingCalculator( DirectedGraph<P, GameResult> tournamentGraph,
                                 PlayersRank<P> playerRanking, int kFactor )
    {
        this.tournamentGraph = tournamentGraph;
        this.playerRanking = playerRanking;
        this.kFactor = kFactor;
    }

    // TODO find a way to improve performances, this impl is just a spike
    public void calculateRate()
    {
        for ( P player : tournamentGraph.getVertices() )
        {
            for ( P opponent : tournamentGraph.getOutbound( player ) )
            {
                GameResult gameResult = tournamentGraph.getEdge( player, opponent );
                evaluateMatch( player, gameResult, opponent );
            }
        }
    }

    private boolean evaluateMatch( P playerA, GameResult gameResult, P playerB )
    {
        double qA = calculateQFactor( playerA );
        double qB = calculateQFactor( playerB );

        double eA = calculateEFactor( qA, qB );
        double eB = calculateEFactor( qB, qA );

        double sA;
        double sB;
        switch ( gameResult )
        {
            case WIN:
                sA = 1;
                sB = 0;
                break;

            case DRAW:
                sA = 0.5;
                sB = 0.5;
                break;

            default: // should not happen
                throw new IllegalArgumentException( "Input GameResult not accepted" );
        }

        updateRanking( playerA, kFactor, sA, eA );
        updateRanking( playerB, kFactor, sB, eB );
        return true;
    }

    private double calculateQFactor( P player )
    {
        double ranking = playerRanking.map( player );
        return pow( DEFAULT_POW_BASE, ranking / DEFAULT_DIVISOR);
    }

    private static double calculateEFactor( double qA, double qB )
    {
        return qA / (qA + qB);
    }

    private void updateRanking( P player, double kFactor, double sFactor, double eFactor )
    {
        double newRanking = playerRanking.map( player ) + ( kFactor * ( sFactor - eFactor ) );
        playerRanking.update( player, newRanking );
    }

}
