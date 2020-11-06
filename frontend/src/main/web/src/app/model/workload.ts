export class Workload {

    useCases: any[] = new Array();  

 /**
   * 0 = end user selected workload using check boxes and radio buttons
   * 1 = clear text json workload key
   * 2 = encrypted version of above key.
   */
  origin: number = 0;
}