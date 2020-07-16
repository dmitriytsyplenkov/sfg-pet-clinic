package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;
    @Mock
    PetService petService;
    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        visitController = new VisitController(visitService, petService);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).visits(new HashSet<>()).build());
        mockMvc.perform(get("/owners/*/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("pets/createOrUpdateVisitForm"));

    }

    @Test
    void processNewVisitForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).visits(new HashSet<>()).build());
        mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("redirect:/owners/1"));
        verify(visitService).save(any());

    }

}