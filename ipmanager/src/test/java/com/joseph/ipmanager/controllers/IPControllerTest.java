package com.joseph.ipmanager.controllers;

import com.joseph.ipmanager.entities.IPPool;
import com.joseph.ipmanager.services.IPAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IPControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private IPAddressService ipAddressService;

    @Test
    public void reserveIPShouldReturnSuccess(){


    }
}
