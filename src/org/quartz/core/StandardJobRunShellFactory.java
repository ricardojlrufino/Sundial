/* 
 * Copyright 2001-2009 Terracotta, Inc. 
 * Copyright 2011 Xeiam LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.quartz.core;

import org.quartz.ExecuteInJTATransaction;
import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;

/**
 * <p>
 * Responsible for creating the instances of a {@link JobRunShell} to be used within the <class>{@link org.quartz.core.QuartzScheduler} </code> instance. It will create a standard {@link JobRunShell} unless the job class has the
 * {@link ExecuteInJTATransaction} annotation in which case it will create a {@link JTAJobRunShell}.
 * </p>
 * <p>
 * This implementation does not re-use any objects, it simply makes a new JTAJobRunShell each time <code>borrowJobRunShell()</code> is called.
 * </p>
 * 
 * @author James House
 * @author timmolter
 */
public class StandardJobRunShellFactory implements JobRunShellFactory {

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Data members. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private Scheduler scheduler;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructors. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public StandardJobRunShellFactory() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Interface. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p>
     * Initialize the factory, providing a handle to the <code>Scheduler</code> that should be made available within the <code>JobRunShell</code> and the <code>JobExecutionContext</code> s within it, and a handle to the <code>SchedulingContext</code>
     * that the shell will use in its own operations with the <code>JobStore</code>.
     * </p>
     */
    @Override
    public void initialize(Scheduler scheduler) throws SchedulerConfigException {
        this.scheduler = scheduler;
    }

    /**
     * <p>
     * Called by the <class>{@link org.quartz.core.QuartzSchedulerThread} </code> to obtain instances of <code>
     * {@link org.quartz.core.JobRunShell}</code>.
     * </p>
     */
    @Override
    public JobRunShell createJobRunShell(TriggerFiredBundle bundle) throws SchedulerException {
        // boolean needsJTA = ClassUtils.isAnnotationPresent(bundle.getJobDetail().getJobClass(), ExecuteInJTATransaction.class);
        //
        // if(needsJTA)
        // return new JTAJobRunShell(scheduler, bundle);
        // else
        return new JobRunShell(scheduler, bundle);
    }

}