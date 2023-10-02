package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.dtos.TestimonyDTO;
import net.bozahouse.backend.model.entities.Testimony;

public interface TestimonyService {

    Testimony getTestimony(Long id);

    TestimonyDTO getTestimonyDTO(Long id);

    TestimonyDTO createTestimonyDTO(TestimonyDTO testimonyDTO, Long userId);

    TestimonyDTO updateTestimonyDTO(TestimonyDTO testimonyDTO);

    void deleteTestimony(Long id);

    PageDTO<TestimonyDTO> listTestimonyDTO(int page, int size);
}
