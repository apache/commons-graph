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

import java.util.Map;
import java.util.TreeMap;

final class SimplePlayersRank
    implements PlayersRank<String>
{

    private final Map<String, Double> ranks = new TreeMap<String, Double>();

    public Double getRanking( String player )
    {
        if ( !ranks.containsKey( player ) )
        {
            return 0D;
        }
        return ranks.get( player );
    }

    public void updateRanking( String player, Double ranking )
    {
        ranks.put( player, ranking );
    }

    @Override
    public String toString()
    {
        return ranks.toString();
    }

}
