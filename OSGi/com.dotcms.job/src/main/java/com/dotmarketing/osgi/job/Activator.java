package com.dotmarketing.osgi.job;

import com.dotcms.repackage.org.apache.logging.log4j.LogManager;
import com.dotcms.repackage.org.apache.logging.log4j.core.LoggerContext;
import com.dotmarketing.loggers.Log4jUtil;
import com.dotmarketing.osgi.GenericBundleActivator;
import com.dotmarketing.quartz.CronScheduledTask;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.quartz.CronTrigger;

public class Activator extends GenericBundleActivator {

    public final static String JOB_NAME = "Custom Job";
    public final static String JOB_CLASS = "com.dotmarketing.osgi.job.CustomJob";
    public final static String JOB_GROUP = "User Jobs";

    public final static String CRON_EXPRESSION = "0/10 * * * * ?";//Every 10 seconds

    private LoggerContext pluginLoggerContext;

    @SuppressWarnings ("unchecked")
    public void start ( BundleContext context ) throws Exception {

        //Initializing log4j...
        LoggerContext dotcmsLoggerContext = Log4jUtil.getLoggerContext();
        //Initialing the log4j context of this plugin based on the dotCMS logger context
        pluginLoggerContext = (LoggerContext) LogManager
                .getContext(this.getClass().getClassLoader(),
                        false,
                        dotcmsLoggerContext,
                        dotcmsLoggerContext.getConfigLocation());

        //Initializing services...
        initializeServices ( context );

        // Job params
        Map<String, Object> params = new HashMap<>();
        params.put( "param1", "value1" );
        params.put( "param2", "value2" );

        //Creating our custom Quartz Job
        CronScheduledTask cronScheduledTask =
                new CronScheduledTask( JOB_NAME, JOB_GROUP, JOB_NAME, JOB_CLASS,
                        new Date(), null, CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
                        params, CRON_EXPRESSION );

        //Schedule our custom job
        scheduleQuartzJob( cronScheduledTask );
    }

    public void stop ( BundleContext context ) throws Exception {

        //Unregister all the bundle services
        unregisterServices( context );

        //Shutting down log4j in order to avoid memory leaks
        Log4jUtil.shutdown(pluginLoggerContext);
    }

}