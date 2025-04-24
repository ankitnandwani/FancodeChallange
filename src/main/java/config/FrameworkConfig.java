package config;

import org.aeonbits.owner.Config;

@Config.Sources(value="file:${user.dir}/src/test/Resources/com/cucumber/Config.properties")
public interface FrameworkConfig extends Config{
    String URL();
}
