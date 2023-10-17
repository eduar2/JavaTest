package com.pichincha.prueba.controller;

import com.pichincha.prueba.model.dto.ClientDTO;
import com.pichincha.prueba.response.GenericResponse;
import com.pichincha.prueba.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService service;
    @Autowired
    private JacksonTester<ClientDTO> jsonRequestClient;

    @Autowired
    private JacksonTester<GenericResponse> jsonResponseClient;

    @Test
    void getClients() throws Exception {
        ClientDTO client = new ClientDTO();
        client.setPassword("123");
        client.setStatus(true);
        client.setName("Tester");
        client.setGender("Male");
        client.setAddress("Cuenca");
        client.setAge(30);
        client.setIdentification("17...");
        client.setPhone("911");

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus(HttpStatus.OK.value());
        expectedResponse.setMessage("Cliente creado correctamente");

        String json = jsonRequestClient.write(client).getJson();
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse();

        BDDMockito.then(response.getStatus()).equals(HttpStatus.OK.value());

        BDDMockito.then(response.getContentAsString())
                .equals(jsonResponseClient.write(expectedResponse).getJson());
    }

    @Test
    void postClient() {
    }

    @Test
    void putClient() {
    }

    @Test
    void deleteClient() {
    }
}