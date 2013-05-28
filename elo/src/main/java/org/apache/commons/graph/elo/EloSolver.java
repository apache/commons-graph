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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.DirectedGraph;

public final class EloSolver
{

    /**
     * Ranks the players (vertices) that took part in a tournament (graph) depending on the game results (edges),
     * applying the <a href="http://en.wikipedia.org/wiki/Elo_rating_system.">Elo Rating System</a>.
     *
     * @param <P> the players involved in the tournament
     * @param <TG> the Tournament Graph type
     * @param tournamentGraph the graph representing the tournament
     * @return the builder for the functor which returns/update the players ranking
     */
    public static <P, TG extends DirectedGraph<P, GameResult>> RankingSelector<P> eloRate( TG tournamentGraph )
    {
        tournamentGraph = checkNotNull( tournamentGraph, "ELO ranking can not be applied on null graph!" );
        return new DefaultRankingSelector<P>( tournamentGraph );
    }

    private EloSolver()
    {
        // do nothing
    }

}
