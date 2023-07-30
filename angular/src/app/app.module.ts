import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, Injector, LOCALE_ID, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {ApiService} from './core/api.service';
import {AsyncPipe, registerLocaleData} from '@angular/common';
import {AppRoutingModule} from './app-routing.module';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {ConfigService} from './config-provider';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import localeRu from '@angular/common/locales/ru';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {MainComponent} from "./main/main.component";

export function configFactory(cfg: ConfigService): Function {
  return () => cfg.load();
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

registerLocaleData(localeRu, 'ru');

/**
 * Main Module
 */
@NgModule({
  declarations: [AppComponent, MainComponent],
  entryComponents: [],
  imports: [BsDropdownModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      }
    )
  ],
  providers: [ConfigService, {
    provide: APP_INITIALIZER,
    useFactory: configFactory,
    deps: [ConfigService, HttpClientModule],
    multi: true
  }, {provide: LOCALE_ID, useValue: 'en'}, ApiService, AsyncPipe],
  bootstrap: [AppComponent]
})
export class AppModule {
  /**
   * Allows to inject services without a constructor.
   */
  static injector: Injector;

  constructor(injector: Injector) {
    AppModule.injector = injector;
  }
}
