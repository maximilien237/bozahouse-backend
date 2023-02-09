package net.bozahouse.backend.security;

public interface ControllerVariables {

    String[] devAntPatterns = new String[]{
            "/swagger-ui/**",
            "/api-docs/**",
            "/h2-console/**"
    };

    String[] publicAntPatterns = new String[]{
            "/api/auth/v1/**"
    };

    String[] userAntPatterns = new String[]{
            "/user/v1/**"
    };


    String[] editorAntPatterns = new String[]{
            "/user/v1/**",
            "/editor/v1/**"
    };


    String[] adminAntPatterns = new String[]{
            "/user/v1/**",
            "/editor/v1/**",
            "/admin/v1/**"
    };


    String ADMIN_ROLE_NAME = "ADMIN";
    String USER_ROLE_NAME = "USER";
    String EDITOR_ROLE_NAME = "EDITOR";

    //String ROLE_PREFIX = "ROLE_";
}
