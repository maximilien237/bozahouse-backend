package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.dtos.TestimonyDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.model.entities.Testimony;
import net.bozahouse.backend.repositories.TestimonyRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TestimonyServiceImpl implements TestimonyService{

    private TestimonyRepo testimonyRepo;

    @Override
    public Testimony getTestimony(Long id){
        log.info("getting Testimony by id :: " +id + "...");
        Optional<Testimony> optionalTestimony = testimonyRepo.findById(id);

        if (optionalTestimony.isPresent()){
            return optionalTestimony.get();
        }
            throw new EntityNotFoundException("Testimony not found for id ::" +id);

    }

    @Override
    public TestimonyDTO getTestimonyDTO(Long id)  {
        log.info("getting Testimony view by id :: " + id + "...");
        return TestimonyDTO.mapToDTO(getTestimony(id));
    }

    @Override
    public TestimonyDTO createTestimonyDTO(TestimonyDTO testimonyDTO, Long userId) {
        log.info("creating Testimony...");
        Testimony testimony = TestimonyDTO.mapToEntity(testimonyDTO);
        testimonyRepo.save(testimony);

        return TestimonyDTO.mapToDTO(testimony);
    }


    @Override
    public TestimonyDTO updateTestimonyDTO(TestimonyDTO testimonyDTO) {
        log.info("updating Testimony...");

        Testimony testimony = TestimonyDTO.mapToEntity(testimonyDTO);
        Testimony updatedTestimony = getTestimony(testimonyDTO.getId());
        testimony.setCreatedAt(updatedTestimony.getCreatedAt());
        testimony.setAuthor(updatedTestimony.getAuthor());
         testimonyRepo.save(testimony);
        return TestimonyDTO.mapToDTO(testimony);
    }


    @Override
    public void deleteTestimony(Long id) {
        log.info("deleting Testimony by id :: " + id + "...");
        getTestimony(id);
        testimonyRepo.deleteById(id);
    }


    @Override
    public PageDTO<TestimonyDTO> listTestimonyDTO(int page, int size) {
        log.info("list Testimony DTO...");
        Page<Testimony> testimonyPage = testimonyRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return PageDTO.mapToTestimonyPageDTO(testimonyPage);
    }



}
