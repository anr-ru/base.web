import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import {TranslateService} from "@ngx-translate/core";

/**
 *  Main Dashboard.
 *
 *  This component contains the full logic of the authorization procedure,
 *  global event handlers including http errors.
 */
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html'
})
export class MainComponent {

  constructor(private translate: TranslateService) {

  }

  getId(): Observable<string> {
    return this.translate.get('prop');
  }

  clicked() {

  }
}
