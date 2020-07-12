package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceSDJpaTest {
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    OwnerServiceSDJpa service;

    final String LAST_NAME = "Smith";

    Owner returnOwner;


    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner smith = service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());

        verify(ownerRepository).findByLastName(any());

    }

    @Test
    void findAll() {
        Set<Owner> owners = Set.of(Owner.builder().id(1L).lastName("Pitt").build(),Owner.builder().id(2L).lastName("Cage").build());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> allOwners = service.findAll();
        assertNotNull(allOwners);
        assertEquals(owners, allOwners);
        assertEquals(2,allOwners.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        //since it returns Optional<T> we need to make our mock object to do it as well
        when(ownerRepository.findById(any())).thenReturn(Optional.of(returnOwner));
        Owner owner = service.findById(1L);
        assertNotNull(owner);
    }
    @Test
    void findByIdNotFound() {
        //since it returns Optional<T> we need to make our mock object to do it as well
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);
        assertNull(owner);
    }
    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(ownerToSave);
        Owner savedOwner = service.save(ownerToSave);
        assertNotNull(savedOwner);
        assertEquals(ownerToSave, savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        //since they return void we can just verify that owner repo method was called once
        service.delete(returnOwner);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        //since they return void we can just verify that owner repo method was called once
        service.deleteById(1L);
        verify(ownerRepository).deleteById(any());
    }
}