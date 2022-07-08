import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ConfigService {

  private _cfg: any;

  constructor(private http: HttpClient) {
  }

  // This is the method you want to call at bootstrap

  get data(): any {
    return this._cfg;
  }


  // Important: It should return a Promise
  public load() {

    this.http.get('/config').subscribe(
      data => {
        this._cfg = data['props'];
      },
      err => console.log(`Unable to load a config: ${JSON.stringify(err)}`)
    );
  }
}
