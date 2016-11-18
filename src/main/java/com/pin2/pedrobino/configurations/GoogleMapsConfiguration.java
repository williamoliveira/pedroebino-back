package com.pin2.pedrobino.configurations;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfiguration {

    @Bean
    public GeoApiContext getGeoApiContext() {
        return new GeoApiContext().setApiKey("AIzaSyAdy24rqWFRtchtsjD2xUVV11Txt75wL-g");
    }
}