export class Workload {

    useCases: any[] = new Array();  
    encryptedKey : string;
    alias : string;

    public getAbbreviatedEncryptedKey():string {
      var rc:string = '<Unabled to created abbreviation for encrypted workload key>';
      if (this.encryptedKey!=null) {
        rc = this.encryptedKey.substr(0,3) + '...' + this.encryptedKey.substr( this.encryptedKey.length-3,3)
      }
      return rc;
    }

    /**
     * This class can be used for either an encrypted key or clear text key unmarshalled into the useCases object,
     * but not both.
     */
    public validate() : boolean {
      var ynClearTextKeyPresent : boolean = false;
        var rc: boolean = false;


      if (this.isClearText() && this.isEncrypted() ) //design says one or the other but not both.
        rc = false;
      else if ( !this.isClearText() && !this.isEncrypted() ) // if neither are set, there is no workload key present.
        rc = false; // then there is a problem
      else  
        rc = true;
        
      return rc;
    }
    public isClearText() : boolean {
      var rc : boolean = false;
      if (this.useCases != null && this.useCases.length > 0 )
        rc = true;

      return rc;
    }

    public static halfAssedDeserialize(workloadObj : any) : Workload {
      var workloadTyped : Workload = new Workload();
      workloadTyped.origin = workloadObj.origin;
      workloadTyped.alias = workloadObj.alias;
      workloadTyped.encryptedKey = workloadObj.encryptedKey;
      return workloadTyped;
    }
    public isEncrypted() : boolean {
      var rc : boolean = false;
      if (this.encryptedKey != null 
          && this.encryptedKey.length > 0 
          && this.origin==1 //clipboard pasted or handkeyed workload text.
          && this.isBase64Ish() )
        rc = true;

      return rc;
    }
    /**
     * https://medium.com/@aems/one-mans-struggle-with-typescript-class-serialization-478d4bbb5826
     * @param personAsObject 
    public static deserialize(workloadAsObject: any): Workload {
      const workload = new Workload();
      for (const propertyName of Object.keys(workloadAsObject)) {
        if (Workload.propertyDeserializers.has(propertyName)) {
          const deserializer =
            Workload.propertyDeserializers.get(propertyName);
          
          workload[propertyName] = deserializer === undefined ?
            workloadAsObject[propertyName] :
            deserializer(workloadAsObject[propertyName]);
        } else {
          throw Error(`No deserializer for ${propertyName}!`);
        }
      }
      
      return person;
    }    
     */
 /**
   * 0 = end user selected workload using check boxes and radio buttons
   * 1 = end user typed or pasted workload key, could be either encrypted or clear text.
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
  public isBase64Ish_(candidateKey : string) : boolean {
    let regexp = new RegExp( '^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$' );
    return regexp.test(candidateKey);
  }
  public isBase64Ish() : boolean {
    return this.isBase64Ish_(this.encryptedKey);
  }

  public static createDefaultWorkload() : Workload {

    var typedWorkload:Workload = new Workload();
    typedWorkload.origin = 0;
    typedWorkload.useCases.push(
      {
        "processingUnits": [
          {
            "description": {
              "en_US": "http-response_delay_25ms"
            },
            "useCaseName": "02_AlienSystems",
            "selected": false,
            "methodWrapper": {
              "parameters": [],
              "declaringClassName": "com.github.eostermueller.tjp2.alien.BackendHttpRequest",
              "methodName": "getBackendData_delay_25ms"
            }
          },
          {
            "description": {
              "en_US": "http-response_delay_10s"
            },
            "useCaseName": "02_AlienSystems",
            "selected": true,
            "methodWrapper": {
              "parameters": [],
              "declaringClassName": "com.github.eostermueller.tjp2.alien.BackendHttpRequest",
              "methodName": "getBackendData_delay_10s"
            }
          },
          {
            "description": {
              "en_US": "http-response_delay_1s"
            },
            "useCaseName": "02_AlienSystems",
            "selected": false,
            "methodWrapper": {
              "parameters": [],
              "declaringClassName": "com.github.eostermueller.tjp2.alien.BackendHttpRequest",
              "methodName": "getBackendData_delay_1s"
            }
          },
          {
            "description": {
              "en_US": "http-response_noWait"
            },
            "useCaseName": "02_AlienSystems",
            "selected": false,
            "methodWrapper": {
              "parameters": [],
              "declaringClassName": "com.github.eostermueller.tjp2.alien.BackendHttpRequest",
              "methodName": "getBackendData_noWait"
            }
          }
        ],
        "name": "02_AlienSystems"
      }
  
    );
   return typedWorkload;

  }

}