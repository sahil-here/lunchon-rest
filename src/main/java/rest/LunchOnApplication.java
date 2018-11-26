package rest;

import bundle.HttpClientBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.dropwizard.guice.GuiceBundle;
import health.LOHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import module.LunchOnModule;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.entity.*;
import exception.LOExceptionMapper;
import rest.resources.ChatMessagingResource;
import rest.resources.EventResource;
import rest.resources.LoginResource;
import rest.resources.UserResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class LunchOnApplication extends Application<LunchOnConfiguration> {

    protected static final Logger logger = LoggerFactory.getLogger(LunchOnApplication.class);

    private final HibernateBundle<LunchOnConfiguration> hibernateBundle = new HibernateBundle<LunchOnConfiguration>(
            User.class, Event.class, Cuisine.class, CuisinePoll.class, EventStatus.class,Location.class,ChatMessage.class,
            Restaurant.class, RestaurantPoll.class, Time.class, TimePoll.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(LunchOnConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final HttpClientBundle<LunchOnConfiguration> httpClientBundle = new HttpClientBundle<LunchOnConfiguration>() {
        @Override
        public HttpClientConfiguration getHttpConfiguration(LunchOnConfiguration configuration) {
            return configuration.getHttpClientConfiguration();
        }
    };

    private final SwaggerBundle<LunchOnConfiguration> swaggerBundle = new SwaggerBundle<LunchOnConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(LunchOnConfiguration configuration) {
            return configuration.getSwaggerBundleConfiguration();
        }
    };

    private GuiceBundle<LunchOnConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new LunchOnApplication().run(args);
        logger.info("LunchOn Service is up");
    }

    @Override
    public void initialize(Bootstrap<LunchOnConfiguration> bootstrap) {

        guiceBundle = GuiceBundle.<LunchOnConfiguration> newBuilder()
                .addModule(getVendiModule(httpClientBundle, hibernateBundle,bootstrap.getObjectMapper()))
                .setConfigClass(LunchOnConfiguration.class).build();

        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(httpClientBundle);
        bootstrap.addBundle(swaggerBundle);
        bootstrap.addBundle(guiceBundle);
    }

    protected LunchOnModule getVendiModule(HttpClientBundle<LunchOnConfiguration> httpClientBundle,
                                           HibernateBundle<LunchOnConfiguration> hibernateBundle, ObjectMapper objectMapper) {
        return new LunchOnModule(httpClientBundle, hibernateBundle, objectMapper);
    }

    @Override
    public void run(LunchOnConfiguration configuration, Environment environment) throws Exception {
        LOHealthCheck healthCheck = guiceBundle.getInjector().getInstance(LOHealthCheck.class);
        environment.healthChecks().register("rotation-health", healthCheck);
        environment.jersey().register(guiceBundle.getInjector().getInstance(LoginResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(UserResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(EventResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(ChatMessagingResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(LOExceptionMapper.class));

        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
