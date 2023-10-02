package net.bozahouse.backend.exception;

public enum ErrorCodes {

  NOT_FOUND(404),
  BAD_REQUEST(400),
  BAD_CREDENTIALS(401),

  // Liste des exception techniaues
  UPDATE_PHOTO_EXCEPTION(14000),
  UNKNOWN_CONTEXT(14001);

  private final int code;

  ErrorCodes(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
