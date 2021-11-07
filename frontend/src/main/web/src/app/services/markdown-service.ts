import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../model/api.response';
import { ApiResponseInterface } from '../model/api.response.interface';
import { ParentMarkdownFile, ParentMarkdownFileJsonUtil } from '../model/parent.markdown.file';
@Injectable({
  providedIn: 'root'
})
export class MarkdownService {
  constructor(
    private http: HttpClient,
    ) {
  }
  apiResponse : ApiResponse = null;
    getBaseUrl(host:string, port:number) :string{
      var baseUrl:string = 'http://' + host + ':' + port;
      console.log('Base url for markdownService [' + baseUrl + ']');
      return baseUrl;
    }

    public getMarkdown(host:string, port:number) : Observable<ApiResponseInterface> {
  var result : Observable<ApiResponseInterface>;
  console.log('b4 try block to call http startLg');
      try {
        console.log("before http call to startLg");
        result = this.http.get<ApiResponseInterface>(
          this.getBaseUrl(host,port) + '/traffic/markdown');
        console.log("after http call to startLg");
        console.log('after startLg');

      } catch(e) {
        if(e instanceof Error) {
            // IDE type hinting now available
            // properly handle Error e
            console.log('##@ error on /snail4j/startLg ' + e.name);
            console.log('##@ error on /snail4j/startLg ' + e.message);
            console.log('##@ error on /snail4j/startLg ' + e.stack);
        } else {
            // probably cannot recover...therefore, rethrow
            console.log('##@ unable to recover from error on /snail4j/startLg')
            throw e;
        }
  
      }
      return result;
    }
  
}
