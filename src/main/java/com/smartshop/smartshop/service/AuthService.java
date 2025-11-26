package com.smartshop.smartshop.service;

import com.smartshop.smartshop.dto.auth.RequestLogin;
import com.smartshop.smartshop.dto.auth.ResponseLogin;

public interface AuthService {
    ResponseLogin login(RequestLogin requestLogin);
}
