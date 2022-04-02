package ch.unisg.edpo.eau.onboardingGateway.common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ConfigProperties {
    @Autowired
    private Environment environment;

    public String getExampleVariable() {
        return Objects.requireNonNull(environment.getProperty("example"));
    }

}
