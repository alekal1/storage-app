export class ErrorResponse {
  uuid: string;
  code: string;
  message: string;

  constructor(
    uuid: string,
    code: string,
    message: string
  ) {
    this.uuid = uuid;
    this.code = code;
    this.message = message;
  }
}
