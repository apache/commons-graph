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

final class DefaultKFactorBuilder<P>
    implements KFactorBuilder<P>
{

    private static final double DEFAULT_POW_BASE = 10;

    private static final double DEFAULT_DIVISOR = 400;

    private static final int DEFAULT_K_FACTOR = 32;

    private final DirectedGraph<P, GameResult> tournamentGraph;

    private final PlayersRank<P> playerRanking;

    public DefaultKFactorBuilder(final DirectedGraph<P, GameResult> tournamentGraph,
                                 final PlayersRank<P> playerRanking )
    {
        this.tournamentGraph = tournamentGraph;
        this.playerRanking = playerRanking;
    }

    public void withDefaultKFactor()
    {
        withKFactor( DEFAULT_K_FACTOR );
    }

    public void withKFactor(final int kFactor )
    {
        // TODO find a way to improve performances, this impl is just a spike
        for ( final P player : tournamentGraph.getVertices() )
        {
            for ( final P opponent : tournamentGraph.getOutbound( player ) )
            {
                final GameResult gameResult = tournamentGraph.getEdge( player, opponent );
                evaluateMatch( player, gameResult, opponent, kFactor );
            }
        }
    }

    private boolean evaluateMatch(final P playerA, final GameResult gameResult, final P playerB, final int kFactor )
    {
        final double qA = calculateQFactor( playerA );
        final double qB = calculateQFactor( playerB );

        final double eA = calculateEFactor( qA, qB );
        final double eB = calculateEFactor( qB, qA );

        final double sA;
        final double sB;
        switch ( gameResult )
        {
            case WIN:
                sA = 1;
                sB = 0;
                break;

            case DRAW:
                final double number = 0.5;
                sA = number;
                sB = number;
                break;

            default: // should not happen
                throw new IllegalArgumentException( "Input GameResult not accepted" );
        }

        updateRanking( playerA, kFactor, sA, eA );
        updateRanking( playerB, kFactor, sB, eB );
        return true;
    }

    private double calculateQFactor(final P player )
    {
        final double ranking = playerRanking.getRanking( player );
        return pow( DEFAULT_POW_BASE, ranking / DEFAULT_DIVISOR );
    }

    private static double calculateEFactor(final double qA, final double qB )
    {
        return qA / ( qA + qB );
    }

    private void updateRanking(final P player, final double kFactor, final double sFactor, final double eFactor )
    {
        final double newRanking = playerRanking.getRanking( player ) + ( kFactor * ( sFactor - eFactor ) );
        playerRanking.updateRanking( player, newRanking );
    }

}
