package ru.praktikum_services.qa_scooter.config;

import java.util.Arrays;
import java.util.List;

public class Config {
    private static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/api/v1/";
    public static String getBaseUri(){
        return BASE_URI;
    }
    
}
