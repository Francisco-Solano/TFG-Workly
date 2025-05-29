package com.vedruna.TFG_Workly.security.auth.dto;

import java.util.List;

public record LoginResponse(String email, List<String> authorities, String token, Integer id) {
}
