package pl.zenev.profhub.services;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.repositories.ProfessorRepository;
@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //DataStorage dataStorage = (DataStorage) event.getServletContext().getAttribute("dataStorage");
        ProfessorRepository professorRepository = new ProfessorRepository(new DataStorage());

        event.getServletContext().setAttribute(
                "professorService", new ProfessorService(professorRepository)
        );

    }
}
