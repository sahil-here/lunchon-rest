package module;

import bundle.HttpClientBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import org.apache.http.client.HttpClient;
import org.hibernate.SessionFactory;
import rest.LunchOnConfiguration;
import rest.dao.IUserDAO;
import rest.dao.UserDAO;
import rest.resources.manager.ILoginManager;
import rest.resources.manager.IUserManager;
import rest.resources.manager.LoginManager;
import rest.resources.manager.UserManager;
import rotation.RotationHelper;

public class LunchOnModule extends AbstractModule {
    private ObjectMapper objectMapper;
    private HttpClientBundle httpClientBundle;
    private HibernateBundle<LunchOnConfiguration> hibernateBundle;

    public LunchOnModule(HttpClientBundle httpClientBundle, HibernateBundle<LunchOnConfiguration> hibernateBundle, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClientBundle = httpClientBundle;
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
        bind(RotationHelper.class).toInstance(new RotationHelper());
        bind(IUserDAO.class).to(UserDAO.class);
        bind(ILoginManager.class).to(LoginManager.class);
        bind(IUserManager.class).to(UserManager.class);
    }

    @Provides
    public HttpClient getHttpClient() {
        return httpClientBundle.getHttpClient();
    }

    @Provides
    public SessionFactory getSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}
