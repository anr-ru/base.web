import {AfterViewInit, ChangeDetectionStrategy, Component} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

/**
 *  Main Dashboard.
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements AfterViewInit {

  constructor(private translate: TranslateService) {

    this.translate.addLangs(['ru', 'en']);
    this.translate.setDefaultLang('en');
    this.translate.use('en');
  }

  ngAfterViewInit(): void {
    const userLang = navigator.language;
    console.log(userLang);
  }

  public changeLang(lang: string) {
    this.translate.use(lang);
  }
}
