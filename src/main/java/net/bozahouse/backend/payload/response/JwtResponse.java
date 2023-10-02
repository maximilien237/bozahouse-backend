package net.bozahouse.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse {
  private String token;
  private String email;



}
