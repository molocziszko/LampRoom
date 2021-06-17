package com.molocziszko.lamproom.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {
    String getClientIp(HttpServletRequest request);

    String checkClientLocation(String ip);

    boolean isLocalhost(String ip);
}
