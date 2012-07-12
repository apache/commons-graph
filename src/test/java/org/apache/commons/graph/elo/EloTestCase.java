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

import static org.apache.commons.graph.elo.GameResult.WIN;

import static org.apache.commons.graph.CommonsGraph.eloRate;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.junit.Test;

/**
 * Sample taken from <a href="http://www.teamliquid.net/forum/viewmessage.php?topic_id=253017">teamliquid</a>
 */
public final class EloTestCase
{

    @Test
    public void performElo()
    {
        DirectedGraph<String, GameResult> tournament =
        newDirectedMutableGraph( new AbstractGraphConnection<String, GameResult>()
        {

            @Override
            public void connect()
            {
                String zenio = addVertex( "Zenio" );
                String marineking = addVertex( "Marineking" );
                String hongun = addVertex( "Hongun" );
                String nestea = addVertex( "Nestea" );
                String tester = addVertex( "Tester" );
                String nada = addVertex( "Nada" );
                String rainbow = addVertex( "Rainbow" );
                String thewind = addVertex( "Thewind" );
                String inka = addVertex( "Inka" );
                String maka = addVertex( "Maka" );
                String ensnare = addVertex( "Ensnare" );
                String kyrix = addVertex( "Kyrix" );
                String killer = addVertex( "Killer" );
                String slayersboxer = addVertex( "Slayersboxer" );
                String fruitdealer = addVertex( "Fruitdealer" );
                String genius = addVertex( "Genius" );

                // no draws
                addEdge( WIN ).from( zenio ).to( marineking );
                addEdge( WIN ).from( fruitdealer ).to( hongun );
                addEdge( WIN ).from( genius ).to( nestea );
                addEdge( WIN ).from( tester ).to( nada );
                addEdge( WIN ).from( thewind ).to( rainbow );
                addEdge( WIN ).from( maka ).to( inka );
                addEdge( WIN ).from( kyrix ).to( ensnare );
                addEdge( WIN ).from( slayersboxer ).to( killer );
                addEdge( WIN ).from( marineking ).to( fruitdealer );
                addEdge( WIN ).from( tester ).to( genius );
                addEdge( WIN ).from( thewind ).to( maka );
                addEdge( WIN ).from( kyrix ).to( slayersboxer );
                addEdge( WIN ).from( marineking ).to( tester );
                addEdge( WIN ).from( kyrix ).to( thewind );
                addEdge( WIN ).from( kyrix ).to( marineking );
            }

        } );

        PlayersRank<String> playersRank = new SimplePlayersRank();

        eloRate( tournament ).werePlayersAreRankedIn( playersRank ).withKFactor( 80 );

        System.out.println( playersRank );
    }

}
