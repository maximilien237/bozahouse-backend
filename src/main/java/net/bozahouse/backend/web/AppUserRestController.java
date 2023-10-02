package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.*;
import net.bozahouse.backend.services.*;
import net.bozahouse.backend.services.email.EmailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/user/v1/")
@Tag(name = "AppUserRestController", description = "rest api for user")
public class AppUserRestController {

    private TestimonyService testimonyService;
    private OfferService offerService;
    private TalentService talentService;
    private AppUserService appUserService;
    private EmailSender sender;



    @GetMapping("users/account")
    @Secured({"USER"})
    public AppUserDTO profile(Authentication authentication)  {

        return appUserService.getAppUserByEmailDTO(authentication.getName());
    }


    //
    @GetMapping("offers/filter")
    public PageDTO<OfferDTO> filterOffer(@RequestParam(name = "title", defaultValue = "") String title,
                                         @RequestParam(name = "contract", defaultValue = "") String contract,
                                         @RequestParam(name = "workMode", defaultValue = "") String workMode,
                                         @RequestParam(name = "address", defaultValue = "") String address,
                                         @RequestParam(name = "experience", defaultValue = "") String experience,
                                         @RequestParam(name = "type", defaultValue = "") String type,
                                         @RequestParam(name = "domain", defaultValue = "") String domain,
                                         @RequestParam(name = "startDate", defaultValue = "") Date startDate,
                                         @RequestParam(name = "endDate", defaultValue = "") Date endDate,
                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                         @RequestParam(name = "size",defaultValue = "5") int size)  {

        log.info(" beforeController " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);


        return offerService.listOffer(title, contract, workMode, address,  experience, type,domain,startDate, endDate, page, size);

    }


    @PostMapping("offers/{userId}")
    public OfferDTO saveOffer(@RequestBody OfferDTO offerDTO, @PathVariable(name = "userId") Long userId) {

        OfferDTO offerDTO1 = offerService.createOffer(offerDTO, userId);
        //send email
        sender.jobNotification(offerDTO1);
        return offerDTO1;
    }


    @PutMapping("offers/{id}")
    public OfferDTO updateOffer(@RequestBody OfferDTO offerDTO, @PathVariable(name = "id") Long offerId) {
        offerDTO.setId(offerId);
        return offerService.updateOffer(offerDTO);
    }

    @DeleteMapping("offers/{id}")
    public void deleteOffer(@PathVariable(name = "id") Long offerId) {
        offerService.deleteOffer(offerId);

    }

    @GetMapping("offers/{offerId}")
    public OfferDTO getOffer(@PathVariable(name = "offerId") Long offerId){
        return offerService.getOfferDTO(offerId);

    }


    @GetMapping("offers/three")
    public List<OfferDTO> lastThreeOffer()  {
        return offerService.lastThreeOffer();

    }



    @GetMapping("talents/filter")
    public PageDTO<TalentDTO> filterTalent(@RequestParam(name = "title", defaultValue = "") String title,
                                           @RequestParam(name = "contract", defaultValue = "") String contract,
                                           @RequestParam(name = "workMode", defaultValue = "") String workMode,
                                           @RequestParam(name = "address", defaultValue = "") String address,
                                           @RequestParam(name = "experience", defaultValue = "") String experience,
                                           @RequestParam(name = "type", defaultValue = "") String type,
                                           @RequestParam(name = "domain", defaultValue = "") String domain,
                                           @RequestParam(name = "startDate", defaultValue = "") Date startDate,
                                           @RequestParam(name = "endDate", defaultValue = "") Date endDate,
                                           @RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size) {

        log.info(" beforeController " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        return talentService.filterTalent(title, contract, workMode, address,  experience, type,domain,startDate,endDate, page, size);



    }

    //about talent
    @PostMapping("talents/{userId}")
    public TalentDTO saveTalent(@RequestBody TalentDTO talentDTO, @PathVariable(name = "userId") Long userId){

        TalentDTO talentDTO1 = talentService.createTalent(talentDTO, userId);
        // Send email to employer and firm
        sender.talentNotification(talentDTO1);
        return talentDTO1;
    }

    @GetMapping("talents/{talentId}")
    public TalentDTO getTalent(@PathVariable(name = "talentId") Long talentId) {
        return talentService.getTalentDTO(talentId);


    }


    @GetMapping("talents/three")
    public List<TalentDTO> lastThreeTalent()  {
        return talentService.lastThreeTalent();

    }




    @PutMapping("talents/{id}")
    public TalentDTO updateTalent(@RequestBody TalentDTO talentDTO, @PathVariable(name = "id") Long talentId)  {
        talentDTO.setId(talentId);
        return  talentService.updateTalent(talentDTO);
    }


    @DeleteMapping("talents/disable/{id}")
    public void disableTalent(@PathVariable(name = "id") Long talentId){

        talentService.enableOrDisableTalent(talentId);

    }

    @DeleteMapping("offers/disable/{id}")
    public void disableOffer(@PathVariable(name = "id") Long offerId)  {
        offerService.activateOrDisableOffer(offerId);


    }


    @GetMapping("offers/user/{id}")
    public PageDTO<OfferDTO> listOfferByAppUser(@PathVariable(name = "id")Long appUserId,
                                                @RequestParam(name = "page",defaultValue = "0") int page,
                                                @RequestParam(name = "size",defaultValue = "5") int size) {

        return offerService.listOfferByAppUser(appUserId,page,size);
    }


    @GetMapping("talents/user/{id}")
    public PageDTO<TalentDTO> listTalentByAppUser(@PathVariable(name = "id")Long appUserId,
                                                  @RequestParam(name = "page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5") int size)  {

        return talentService.listTalentByAppUser(appUserId,page,size);


    }

    //about Testimony
    @PostMapping("testimonies/{userId}")
    public TestimonyDTO saveTestimony(@RequestBody TestimonyDTO testimonyDTO, @PathVariable(name = "userId") Long userId) {


        TestimonyDTO testimonyDTO1 = testimonyService.createTestimonyDTO(testimonyDTO, userId);
        //send email to user
        sender.testimonies(testimonyDTO1);

        return testimonyDTO1;

    }

    //testOK
    @PutMapping("testimonies/{id}")
    public TestimonyDTO updateTestimony(@RequestBody TestimonyDTO testimonyDTO, @PathVariable(name = "id") Long id) {
        testimonyDTO.setId(id);
        return testimonyService.updateTestimonyDTO(testimonyDTO);

    }


    //testOK
    @GetMapping("testimonies/{id}")
    public TestimonyDTO getTestimony(@PathVariable(name = "id") Long id)  {
        return testimonyService.getTestimonyDTO(id);

    }

    //testOK
    @GetMapping("testimonies")
    public PageDTO<TestimonyDTO> listTestimony(@RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) {
        return testimonyService.listTestimonyDTO(page, size);
    }



}
