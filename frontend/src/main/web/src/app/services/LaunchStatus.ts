export enum LaunchStatus {
    Uninitialized,
    Stopped,
    Starting,
    Started,
    Stopping,
  }
  
  /**
   * @stolenFrom:  https://stackoverflow.com/a/28151986
   * 
   */
export namespace LaunchStatus {
  export function theStatusBefore(myStatus:LaunchStatus):LaunchStatus {
    var rc:LaunchStatus = null;

    if (myStatus==LaunchStatus.Stopped)
      rc = LaunchStatus.Stopping;
    else if (myStatus==LaunchStatus.Starting)
     rc = LaunchStatus.Stopped;
    else if (myStatus == LaunchStatus.Started)
     rc = LaunchStatus.Starting;
     else if (myStatus == LaunchStatus.Stopping)
      rc = LaunchStatus.Started;

    return rc;
  }

}