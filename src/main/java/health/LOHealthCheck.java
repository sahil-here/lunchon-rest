package health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import rotation.RotationHelper;

public class LOHealthCheck extends HealthCheck{
    @Inject
    RotationHelper rotationHelper;


    @Override
    protected HealthCheck.Result check() throws Exception {

        if (!rotationHelper.getRotationStatus()) {
            return HealthCheck.Result.unhealthy("Vendi is down");
        }
        return HealthCheck.Result.healthy("Vendi is up");
    }
}
