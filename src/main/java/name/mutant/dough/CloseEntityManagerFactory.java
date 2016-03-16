package name.mutant.dough;

import name.mutant.dough.service.AcctService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CloseEntityManagerFactory implements ServletContextListener {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        AcctService.getEntityManagerFactory().close();
    }
}
