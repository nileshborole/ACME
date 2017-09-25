package com.turvo.acme;

import com.google.maps.GeoApiContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public GeoApiContext geoApiContext(){
        GeoApiContext context = new GeoApiContext();
        context.setApiKey("AIzaSyDdGRaAHa85uoffAOgd7D7ePDW6nxSNmKo");
        return context;
    }

    public static void main(String[] args) throws Exception{
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }

}
