export class ApiResponse {
    nanoStart: number;
    nanoStop: number;
    status: number;
    message: number;
    result: any;
    public static halfAssedDeserialize(apiResponseUntypedObj : any) : ApiResponse {
      console.log("Entering ApiResponse.halfAssedDeserialize ^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
      var apiResponseTyped : ApiResponse = new ApiResponse();

      apiResponseTyped.nanoStart = apiResponseUntypedObj.nanoStart;
      apiResponseTyped.nanoStop = apiResponseUntypedObj.nanoStop;
      apiResponseTyped.status = apiResponseUntypedObj.status;
      apiResponseTyped.message = apiResponseUntypedObj.message;


      apiResponseTyped.result = apiResponseUntypedObj.result;
      // console.log("apiResponseUntypedObj.result [" + apiResponseUntypedObj.result.constructor.name + "]!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      // console.log("apiResponseTyped.result [" + apiResponseTyped.result.constructor.name + "]");

      console.log("Exiting ApiResponse.halfAssedDeserialize %%%%%%%%%%%%%%%%%%%%%%%%%");
      return apiResponseTyped;
    }
  }