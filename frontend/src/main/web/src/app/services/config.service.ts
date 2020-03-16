import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConfigModel } from './config.model';

/**
 * @stolenFrom: https://stackblitz.com/edit/angular-app-initializer-load-config?embed=1&file=app/app.module.ts&view=editor
 */
@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private _config: ConfigModel;
  get config() { 
    console.log("@#% request for ConfigService data item");
    return this._config; }

  constructor(private http: HttpClient) { }

  load(): Promise<any> {
    console.log("@#% ConfigService.load()");
    return this.http.get<ConfigModel>('snail4j/config')
      .toPromise()
      .then(data => this._config = data);
  }

  loader = () => {
    console.log("@#% ConfigService.loader()");
    return this.http.get<ConfigModel>('snail4j/config')
      .toPromise()
      .then(data => this._config = data);
  }
}
