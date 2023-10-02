package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.services.AppUserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/editor/v1/")
@Tag(name = "EditorRestController", description = "rest api for editor")
public class EditorRestController {

    private AppUserService userService;

    //testOK
    @GetMapping("users/username")
    @Secured({"EDITOR","ADMIN"})
    public PageDTO<AppUserDTO> searchUser(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name = "size",defaultValue = "5") int size) {
            return userService.findAllByUsername("%" + keyword + "%",page, size);

    }

    //testOK
    @GetMapping("users")
    @Secured({"EDITOR","ADMIN"})
    public PageDTO<AppUserDTO> listAppUser(@RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size) {
            return userService.listAppUser(page, size);
    }

}
