package org.apache.commons.graph.utils;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Simple utility class. 
 * It is used for executing multi-thread test case.
 */
public class MultiThreadedTestRunner
{
    final private List<Thread> th;
    final long maxWait = 60L * 60L * 1000;
    final private List<Throwable> exeptions;
    
    
    public MultiThreadedTestRunner(final TestRunner[] runnables )
    {
        th = new ArrayList<Thread>();
        exeptions = new ArrayList<Throwable>();
        for (final TestRunner runnable : runnables) {
            runnable.setTestRunner(this);
            th.add(new Thread(runnable));
        }
    }

    public void runRunnables() throws Throwable
    {
        for ( final Thread t : th )
        {
            t.start();
        }
        
        for ( final Thread t : th )
        {
            t.join( maxWait );
        }
        
        if ( this.exeptions.size() > 0 )
        {
            throw this.exeptions.get( 0 );
        }
    }

    /**
     * @param e
     */
    public void addException(final Throwable e )
    {
        exeptions.add( e );
    }
}
