import {Injectable} from '@angular/core';
import {catchError, finalize} from 'rxjs/operators';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';


/**
 * A class to represent an API error object including JSON body and the status of responses.
 */
export class ApiError {
  constructor(public status: number,
              public statusText?: string,
              public body?: any) {
  }

  toString() {
    const err = JSON.stringify(this.body) || '';
    return 'Error: (' + this.status + '): ' + err;
  }
}


@Injectable()
export class ApiService {

  static rqOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  http: HttpClient;

  constructor(_http: HttpClient) {
    this.http = _http;
  }

  /**
   * HTTP GET method
   *
   * @param url The URL of the query
   */
  public get<T>(url: string): Observable<T> {
    return this.http.get<T>(url, ApiService.rqOptions).pipe(
      catchError(err => this.handleError(err)),
      finalize(() => {
      })
    );
  }


  // Try to wrap the error
  private handleError(error: HttpErrorResponse | any) {

    let errObject: ApiError;

    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
      errObject = new ApiError(-1, error.error.message ? error.error.message : error.toString());
    } else {
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
      errObject = new ApiError(error.status, error.statusText, error.error);
    }
    return throwError(errObject);
  }

}
