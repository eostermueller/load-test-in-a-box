export class Workload {

    useCases: any[] = new Array();  
    encryptedKey : string;

    /**
     * This class can be used for either an encrypted key or clear text key unmarshalled into the useCases object,
     * but not both.
     */
    public validate() : boolean {
      var rc: boolean = false;
      var ynClearTextKeyPresent : boolean = false;
      var ynEncryptedKeyPresent : boolean = false;

      if (this.encryptedKey != null && this.encryptedKey.length > 0 && this.isBase64Ish(this.encryptedKey)) {
        ynEncryptedKeyPresent = true;
      }

      if (this.useCases != null && this.useCases.length > 0) {
        ynClearTextKeyPresent = true;
      }

      return (ynClearTextKeyPresent && ynEncryptedKeyPresent);
    }
 /**
   * 0 = end user selected workload using check boxes and radio buttons
   * 1 = end user typed or pasted clear text json workload key
   * 2 = end user typed or pasted base64 encrypted version of above key.
   */
  origin: number = 0;
  /**
   * The only way to really know whether a given string is base64 is to decode it, and we'll let the server-side do that.
   * This method just assesses whether the right characters are used for base64.
   * All Jasypt encryption text is encoded as base64 ("All the String results (of encryption) are encoded in BASE64") , 
   * as detailed here:
   * http://www.jasypt.org/encrypting-texts.html
   * 
   * @stolenFrom: https://stackoverflow.com/a/8571649/2377579
   */
  public isBase64Ish(candidateKey : string) : boolean {
    let regexp = new RegExp( '^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$' );
    return regexp.test(candidateKey);
  }

}