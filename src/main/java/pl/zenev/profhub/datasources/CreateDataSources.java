package pl.zenev.profhub.datasources;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.zenev.profhub.controllers.ProfessorsController;
import pl.zenev.profhub.services.ProfessorService;

@WebListener
public class CreateDataSources implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStorage dataStorage = new DataStorage();
        event.getServletContext().setAttribute("dataStorage", dataStorage);
    }
}
