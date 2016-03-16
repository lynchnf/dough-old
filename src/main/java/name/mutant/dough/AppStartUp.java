package name.mutant.dough;

import name.mutant.dough.domain.Payee;
import name.mutant.dough.service.PayableService;
import name.mutant.dough.service.PayeeService;
import name.mutant.dough.service.filter.request.PayeeFilterRequest;
import name.mutant.dough.service.filter.response.PayeeFilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppStartUp implements ServletContextListener {
    private static final Logger LOG = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        PayeeFilterRequest payeeFilterRequest = new PayeeFilterRequest();
        payeeFilterRequest.setMax(-1);
        try {
            PayeeFilterResponse payeeFilterResponse = PayeeService.filterPayees(payeeFilterRequest);
            Date today = DATE_FORMAT.parse(DATE_FORMAT.format(new Date()));
            for (Payee payee : payeeFilterResponse.getResultList()) {
                PayableService.createEstimatedPayables(payee.getId(), today);
            }
        } catch (DoughException e) {
            LOG.error("Unexpected error on startup:", e);
        } catch (ParseException e) {
            LOG.error("Unexpected error on startup:", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
