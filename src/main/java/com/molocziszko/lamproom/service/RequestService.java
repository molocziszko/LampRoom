package com.molocziszko.lamproom.service;

import com.molocziszko.lamproom.model.Room;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {
    String getClientIp(HttpServletRequest request);

    String checkClientLocation(Room room, String ip);

    boolean isLocalhost(String ip);
}
