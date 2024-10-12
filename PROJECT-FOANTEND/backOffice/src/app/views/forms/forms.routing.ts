import { Routes } from '@angular/router';

import { BasicFormComponent } from './basic-form/basic-form.component';

export const FormsRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'basic',
        component: BasicFormComponent,
        data: { title: 'Basic', breadcrumb: 'BASIC' }
      }]
  }
];