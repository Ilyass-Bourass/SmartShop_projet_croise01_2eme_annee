package com.smartshop.smartshop.service;

import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.dto.client.ResponseClient;

public interface ClientService {
    ResponseClient createClient(RequestClient requestClient);
}
