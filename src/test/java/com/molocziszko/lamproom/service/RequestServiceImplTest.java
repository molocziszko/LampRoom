package com.molocziszko.lamproom.service;

import com.molocziszko.lamproom.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceImplTest {
    private static final String AUSTRALIA_IP_ADR = "1.44.255.245";
    private static final String INDIA_IP_ADR = "5.101.108.6";
    public static final String LOCAL_IPV4 = "127.0.0.1";
    public static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";

    @BeforeEach
    void init() {

    }

    @Test
    @DisplayName("Check is IP a localhost or not")
    void isLocalhost() {
        RequestServiceImpl requestService = new RequestServiceImpl();

        boolean isNotLocalhost_1 = requestService.isLocalhost(AUSTRALIA_IP_ADR);
        boolean isNotLocalhost_2 = requestService.isLocalhost(INDIA_IP_ADR);
        boolean isIPV4Localhost = requestService.isLocalhost(LOCAL_IPV4);
        boolean isIPV6Localhost = requestService.isLocalhost(LOCAL_IPV6);

        assertAll("isLocalhost",
                () -> assertFalse(isNotLocalhost_1),
                () -> assertFalse(isNotLocalhost_2),
                () -> assertTrue(isIPV4Localhost),
                () -> assertTrue(isIPV6Localhost)
        );
    }

    @Test
    void checkClientLocation() {
        RequestService service = new RequestServiceImpl();
        String expected = Country.AUSTRALIA.toString().toUpperCase(Locale.ROOT);
        String actualTrue = service.checkClientLocation(AUSTRALIA_IP_ADR).toUpperCase(Locale.ROOT);
        String actualFalse = service.checkClientLocation(INDIA_IP_ADR).toUpperCase(Locale.ROOT);

        assertAll("country",
                () -> assertEquals(expected, actualTrue),
                () -> assertNotEquals(expected, actualFalse)
        );
    }
}