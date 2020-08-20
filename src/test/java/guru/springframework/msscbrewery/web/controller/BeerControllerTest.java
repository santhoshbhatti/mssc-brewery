package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.service.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;
    @BeforeEach
    public void setUp(){
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Irish Coffee")
                .beerStyle("Pale ayl")
                .upc(324324L)
                .build();
    }
    @Test
    void getBeer() throws Exception{
        given(beerService.getBeer(any(UUID.class))).willReturn(validBeer);
        mockMvc.perform(get("/api/v1/beer/"+validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is("Irish Coffee")));

    }

    @Test
    void saveNewBeer() throws Exception{
        BeerDto beerDto = validBeer;
        //beerDto.setId(UUID.randomUUID());
        beerDto.setId(null);
        String json = objectMapper.writeValueAsString(beerDto);

        BeerDto beerDtoSaved=BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        given(beerService.saveNewBeer(any())).willReturn(beerDtoSaved);
        mockMvc.perform(post("/api/v1/beer")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        BeerDto beerDto = validBeer;
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        //when
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(), any());
    }


}