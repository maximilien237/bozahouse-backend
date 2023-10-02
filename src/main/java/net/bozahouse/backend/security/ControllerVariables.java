package net.bozahouse.backend.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ControllerVariables {

    private String admin = "ADMIN";
    private String user = "USER";
    private String editor = "EDITOR";
    private static final String PATH_AUTH =   "/api/auth/v1/**";
    private static final String PATH_USER = "/user/v1/**";
    private static final String PATH_EDITOR = "/editor/v1/**";
    private static final String PATH_ADMIN = "/admin/v1/**";

    private String[] devAntPatterns = new String[]{
            "/v2/api-docs"
            ,"/v3/api-docs"
            ,"/v3/api-docs/**"
            ,"/swagger-resources"
            ,"/swagger-resources/**"
            ,"/configuration/ui"
            ,"/configuration/security"
            ,"/swagger-ui/**"
            ,"/webjars/**"
            ,"/swagger-ui.html",
            "/h2-console/**"
    };

    private String[] publicAntPatterns = new String[]{
            PATH_AUTH

    };

    private String[] userAntPatterns = new String[]{
            PATH_USER
    };


    private String[] editorAntPatterns = new String[]{
            PATH_USER,
            PATH_EDITOR
    };


    private String[] adminAntPatterns = new String[]{
            PATH_USER,
            PATH_EDITOR,
            PATH_ADMIN
    };






}
