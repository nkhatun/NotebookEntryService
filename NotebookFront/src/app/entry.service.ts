import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse ,HttpHeaders} from '@angular/common/http';
import ApiEndpoints from 'src/app/api-end-point';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, tap, finalize, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EntryService {

  constructor(private http: HttpClient) { }

   public getData(url:string): Observable<any> {
        let headers = new HttpHeaders({'Authorization': this.createBasicAuthentication()});
               return this.http.get(url,{headers}
        )
      .pipe(
        tap(),
        catchError(this.errorHandler)
      );
  }

    public postData( data: any,url:string): Observable<any>  {
              let headers = new HttpHeaders({'Authorization': this.createBasicAuthentication()});
    return this.http.post(url, data,{headers})
        .pipe(
          catchError(this.errorHandler),
        );
  }
  createBasicAuthentication(){
    return 'Basic ' + window.btoa("demo"+":"+"demo")
  }

   errorHandler(error: HttpErrorResponse) {
       return throwError(error);
  }
}
