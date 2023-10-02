package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.AppRole;
import org.springframework.beans.BeanUtils;

/**
 * @author maximilien kengne kongne
 * email : maximiliendenver@gmail.com
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AppRoleDTO {

    private Long id;
    private String name;

    public static AppRoleDTO mapToDTO(AppRole appRole) {
        if(appRole==null){
            return null;
        }

        AppRoleDTO appRoleDTO = new AppRoleDTO();
        BeanUtils.copyProperties(appRole,appRoleDTO);

        return appRoleDTO;

    }

    public static AppRole mapToEntity(AppRoleDTO appRoleDTO) {
        if(appRoleDTO==null){
            return null;
        }
        AppRole appRole = new AppRole();
        BeanUtils.copyProperties(appRoleDTO,appRole);

        return  appRole;
    }
}
