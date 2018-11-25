package module;

import bundle.HttpClientBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import org.apache.http.client.HttpClient;
import org.hibernate.SessionFactory;
import rest.LunchOnConfiguration;
import rest.dao.*;
import rest.resources.manager.*;
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
        bind(IChatMessageDAO.class).to(ChatMessageDAO.class);
        bind(ICuisineDAO.class).to(CuisineDAO.class);
        bind(IEventDAO.class).to(EventDAO.class);
        bind(IEventStatusDAO.class).to(EventStatusDAO.class);
        bind(ILocationDAO.class).to(LocationDAO.class);
        bind(IRestaurantDAO.class).to(RestaurantDAO.class);
        bind(ITimeDAO.class).to(TimeDAO.class);
        bind(IUserDAO.class).to(UserDAO.class);
        bind(IChatManager.class).to(ChatManager.class);
        bind(IEventManager.class).to(EventManager.class);
        bind(ILoginManager.class).to(LoginManager.class);
        bind(IUserManager.class).to(UserManager.class);
        bind(IEventManager.class).to(EventManager.class);
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
