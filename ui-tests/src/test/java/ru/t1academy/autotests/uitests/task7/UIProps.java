package ru.t1academy.autotests.uitests.task7;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"

})
public interface UIProps extends Config {

    @Key("BASE_URL")
    String baseUrl();
}

