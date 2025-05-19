package com.vedruna.TFG_Workly.security.auth.dto;

import java.util.List;

public record LoginResponse(String username, List<String> authorities, String token) {
}
