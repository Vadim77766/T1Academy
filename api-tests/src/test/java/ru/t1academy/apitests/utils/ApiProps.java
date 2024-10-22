package ru.t1academy.apitests.utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"

})
public interface ApiProps extends Config {

    @Key("BASE_URL")
    String baseUrl();
    @Key("ENABLE_LOGS")
    boolean enableLogs();
}
