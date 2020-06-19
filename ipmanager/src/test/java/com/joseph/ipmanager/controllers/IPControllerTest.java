package com.joseph.ipmanager.controllers;

import com.google.gson.Gson;
import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.pojos.IPRequest;
import com.joseph.ipmanager.services.IPAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IPControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPAddressService ipAddressService;

    @Test
    public void reserveIPShouldReturnSuccess() throws Exception {
        IPRequest mockRequestIp = new IPRequest();
        mockRequestIp.setPoolId(1);
        mockRequestIp.setIpAddress("127.0.0.1");

        IPAddress mockResponseIP = new IPAddress();
        mockResponseIP.setId(1);

        Gson gson = new Gson();

        when(ipAddressService.registerIp(any())).thenReturn(mockResponseIP);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/ip/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(mockRequestIp)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void blacklistIPShouldReturnSuccess() throws Exception {
        IPRequest mockRequestIp = new IPRequest();
        mockRequestIp.setPoolId(1);
        mockRequestIp.setIpAddress("127.0.0.1");

        IPAddress mockResponseIP = new IPAddress();
        mockResponseIP.setId(1);

        Gson gson = new Gson();

        when(ipAddressService.registerIp(any())).thenReturn(mockResponseIP);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/ip/blacklist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(mockRequestIp)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void invalidIpAddressShouldReturnError() throws Exception {
        IPRequest mockRequestIp = new IPRequest();
        mockRequestIp.setPoolId(1);
        mockRequestIp.setIpAddress("127.0.0.1.1");

        IPAddress mockResponseIP = new IPAddress();
        mockResponseIP.setId(1);

        Gson gson = new Gson();

        when(ipAddressService.registerIp(any())).thenReturn(mockResponseIP);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/ip/blacklist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(mockRequestIp)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
