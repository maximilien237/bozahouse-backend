package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.dtos.*;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.services.*;
import net.bozahouse.backend.services.email.EmailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/admin/v1/")
@Tag(name = "AdminRestController", description = "rest api for admin")
public class AdminRestController {


    private AppUserService userService;
    private AppRoleService roleService;
    private OfferService offerService;
    private TalentService talentService;
    private AppUserService appUserService;
    private EmailSender sender;
    private NewsletterService newsletterService;
    private TestimonyService testimonyService;
    private AuthService authService;


    @DeleteMapping("talents/enable/{id}")
    public void enableTalent(@PathVariable(name = "id") Long talentId) {
        talentService.enableOrDisableTalent(talentId);

    }

    @DeleteMapping("talents/{id}")
    public void deleteTalent(@PathVariable(name = "id") Long talentId)  {
        talentService.deleteTalent(talentId);

    }

    @DeleteMapping("offers/enable/{id}")
    public void enableOffer(@PathVariable(name = "id") Long offerId){
        offerService.activateOrDisableOffer(offerId);

    }

    @DeleteMapping("offers/{id}")
    public void deleteOffer(@PathVariable(name = "id") Long offerId) {
        offerService.deleteOffer(offerId);
    }

    //about News
    //testOK
    @PostMapping("news/{userId}")
    public NewsletterDTO saveNews(@RequestBody NewsletterDTO newsletterDTO, @PathVariable(name = "userId") Long userId) {


        NewsletterDTO newsletterDTO1 = newsletterService.createNews(newsletterDTO, userId);
        //send email to user
        sender.newsletters(newsletterDTO1);

        return newsletterDTO1;
    }

    //testOK
    @PutMapping("news/{id}")
    public NewsletterDTO updateNews(@RequestBody NewsletterDTO newsletterDTO,
                                    @PathVariable(name = "id") Long id)  {

        newsletterDTO.setId(id);
        return newsletterService.updateNews(newsletterDTO);
    }


    //testOK
    @GetMapping("news/{id}")
    public NewsletterDTO getNews(@PathVariable(name = "id") Long id)  {
        return newsletterService.getNewsDTO(id);
    }

    //testOK
    @GetMapping("news")
    public PageDTO<NewsletterDTO> listNews(@RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size) {

        return newsletterService.listNews(page, size);

    }


    //testOK
    @DeleteMapping("news/{id}")
    public void deleteNews(@PathVariable(name = "id") Long id) {
        newsletterService.deleteNews(id);
    }





    //testOK
    @PutMapping("users/add")
    public AppUserDTO addRoleToUser(@RequestParam(name = "username") String username,
                                    @RequestParam(name = "roleName") String roleName)  {
        return  appUserService.addRoleToUser(username, roleName);

    }

    //testOK
    @PutMapping("users/remove")
    public AppUserDTO removeRoleToUser(@RequestParam(name = "username") String username,
                                       @RequestParam(name = "roleName") String roleName){
        return appUserService.removeRoleToUser(username, roleName);

    }

/*
    //testOK
    @GetMapping("users/role")
    public PageDTO<AppUserDTO> listAppUserWithRole(@RequestParam(name = "roleName",defaultValue = "") String roleName,
                                                   @RequestParam(name = "page",defaultValue = "0") int page,
                                                   @RequestParam(name = "size",defaultValue = "5") int size)  {

        return appUserService.listAppUserWithRole(roleName, page, size);
    }

*/

    //testOK
    @DeleteMapping("users/{id}")
    public void deleteAppUser(@PathVariable(name = "id") Long userId) {

        appUserService.deleteAppUser(userId);
    }


    //testOK
    @GetMapping("users/{id}")
    public AppUserDTO getAppUser(@PathVariable(name = "id") Long userId)  {

        return userService.getAppUserDTO(userId);
    }

    //testOK
    @PostMapping("users")
    public AppUserDTO saveAppUser(@RequestBody AppUserDTO appUserDTO) {

        return authService.register(appUserDTO);

    }

    //testOK
    @PutMapping("users/{id}")
    public AppUserDTO updateAppUser(@PathVariable(name = "id") Long userId, @RequestBody AppUserDTO appUserDTO)  {

        appUserDTO.setId(userId);
        return userService.updateAppUser(appUserDTO);

    }


    @DeleteMapping("users/enabled/{id}")
    public void enabledAppUser(@PathVariable(name = "id") Long appUserId)  {
        userService.enabledOrDisableAppUser(appUserId);
    }

    @DeleteMapping("users/disabled/{id}")
    public void disabledAppUser(@PathVariable(name = "id") Long appUserId) {
        userService.enabledOrDisableAppUser(appUserId);
    }

    //testOK
    @GetMapping("users/disabled/username")
    public PageDTO<AppUserDTO> searchAppUserDisabledByUsername(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                                               @RequestParam(name = "size",defaultValue = "5") int size)  {

        return userService.listAppUserDisabledByUsername("%" + keyword + "%",page, size);

    }

    //about role
    //testOK
    @GetMapping("roles")
    public PageDTO<AppRoleDTO> listRole(@RequestParam(name = "page",defaultValue = "0") int page,
                                        @RequestParam(name = "size",defaultValue = "5") int size) {

        return roleService.listRole(page, size);

    }


    //testOK
    @GetMapping("roles/{id}")
    public AppRoleDTO getRole(@PathVariable(name = "id") Long roleId)  {

        return roleService.getRoleDTO(roleId);

    }

    //testOK
    @PostMapping("roles")
    public void saveRole(@RequestBody AppRole role)  {
        roleService.createOrUpdateAppRole(role);

    }

    //testOk
    @PutMapping("roles/{id}")
    public AppRoleDTO updateRole(@PathVariable(name = "id") Long roleId, @RequestBody AppRole role)  {

        role.setId(roleId);
        return roleService.createOrUpdateAppRole(role);

    }

    //testOK
    @DeleteMapping("roles/{id}")
    public void deleteRole(@PathVariable(name = "id") Long roleId)  {

        roleService.deleteRole(roleId);
    }



    //testOK
    @DeleteMapping("testimonies/{id}")
    public void deleteTestimonyView(@PathVariable(name = "id") Long id) {

        testimonyService.deleteTestimony(id);


    }


    //
    @GetMapping("offers/disabled/filter")
    public PageDTO<OfferDTO> filterOfferDisabledView(@RequestParam(name = "title", defaultValue = "") String title,
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

        return offerService.listOfferNotValid(title, contract, workMode, address,  experience, type,domain,startDate,endDate, page, size);

    }



    @GetMapping("talents/disabled/filter")
    public PageDTO<TalentDTO> filterTalentDisabledView(@RequestParam(name = "title", defaultValue = "") String title,
                                                       @RequestParam(name = "contract", defaultValue = "") String contract,
                                                       @RequestParam(name = "workMode", defaultValue = "") String workMode,
                                                       @RequestParam(name = "address", defaultValue = "") String address,
                                                       @RequestParam(name = "experience", defaultValue = "") String experience,
                                                       @RequestParam(name = "type", defaultValue = "") String type,
                                                       @RequestParam(name = "domain", defaultValue = "") String domain,
                                                       @RequestParam(name = "startDate", defaultValue = "") Date startDate,
                                                       @RequestParam(name = "endDate", defaultValue = "") Date endDate,
                                                       @RequestParam(name = "page",defaultValue = "0") int page,
                                                       @RequestParam(name = "size",defaultValue = "5") int size){

        log.info(" beforeController " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        return talentService.filterTalentNotValid(title, contract, workMode, address,  experience, type,domain,startDate,endDate, page, size);

    }

}
