import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {TranslateService} from '@ngx-translate/core';
import {ApiService} from '../core/api.service';
import {map} from 'rxjs/operators';

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
export class MainComponent implements OnInit {

  value: Observable<string>;

  constructor(private translate: TranslateService, private api: ApiService, private changes: ChangeDetectorRef) {

  }

  getId(): Observable<string> {
    return this.translate.get('prop');
  }

  clickedEn() {
    this.translate.use('en').subscribe(() => this.ngOnInit());
  }

  clickedRu() {
    this.translate.use('ru').subscribe(() => this.ngOnInit());
  }

  ngOnInit() {
    this.api.get<{ message: string }>('/api/v1/value').pipe(map(v => v.message)).subscribe(m => {
      this.value = of(m);
      this.changes.detectChanges();
    });
  }
}
