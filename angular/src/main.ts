/**
 * The main App starting point.
 */

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {enableProdMode} from '@angular/core';
import { environment } from './environments/environment';
import {AppModule} from './app';
import 'zone.js';


if (environment.production) {
enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .then(success => console.log(`Bootstrap success`))
  .catch(err => console.error(err));
