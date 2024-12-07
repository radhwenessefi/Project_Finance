import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './shared/components/layouts/admin-layout/admin-layout.component';
import { AuthLayoutComponent } from './shared/components/layouts/auth-layout/auth-layout.component';
import { AuthGuard } from './shared/guards/auth.guard';

export const rootRouterConfig: Routes = [
  {
    path: '', // Redirection par défaut
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: '', // Layout pour les pages publiques (authentification, etc.)
    component: AuthLayoutComponent,
    children: [],
  },
  {
    path: '', // Layout pour les pages protégées
    component: AdminLayoutComponent,
    children: [
      // Dashboard
      {
        path: 'dashboard',
        loadChildren: () =>
          import('./views/dashboard/dashboard.module').then(
            (m) => m.DashboardModule
          ),
        data: { title: 'Dashboard', breadcrumb: 'DASHBOARD' },
      },

      // Matériaux
      {
        path: 'material',
        loadChildren: () =>
          import('./views/material-example-view/material-example-view.module').then(
            (m) => m.MaterialExampleViewModule
          ),
        data: { title: 'Material', breadcrumb: 'MATERIAL' },
      },

      // Dialogues
      {
        path: 'dialogs',
        loadChildren: () =>
          import('./views/app-dialogs/app-dialogs.module').then(
            (m) => m.AppDialogsModule
          ),
        data: { title: 'Dialogs', breadcrumb: 'DIALOGS' },
      },

      // Profil utilisateur
      {
        path: 'profile',
        loadChildren: () =>
          import('./views/profile/profile.module').then(
            (m) => m.ProfileModule
          ),
        data: { title: 'Profile', breadcrumb: 'PROFILE' },
      },

      // Autres pages
      {
        path: 'others',
        loadChildren: () =>
          import('./views/others/others.module').then(
            (m) => m.OthersModule
          ),
        data: { title: 'Others', breadcrumb: 'OTHERS' },
      },

      // Tables
      {
        path: 'tables',
        loadChildren: () =>
          import('./views/tables/tables.module').then(
            (m) => m.TablesModule
          ),
        data: { title: 'Tables', breadcrumb: 'TABLES' },
      },

      // Formulaires
      {
        path: 'forms',
        loadChildren: () =>
          import('./views/forms/forms.module').then(
            (m) => m.AppFormsModule
          ),
        data: { title: 'Forms', breadcrumb: 'FORMS' },
      },

      // Graphiques
      {
        path: 'charts',
        loadChildren: () =>
          import('./views/charts/charts.module').then(
            (m) => m.AppChartsModule
          ),
        data: { title: 'Charts', breadcrumb: 'CHARTS' },
      },

      // Inbox
      {
        path: 'inbox',
        loadChildren: () =>
          import('./views/app-inbox/app-inbox.module').then(
            (m) => m.AppInboxModule
          ),
        data: { title: 'Inbox', breadcrumb: 'INBOX' },
      },

      // Calendrier
      {
        path: 'calendar',
        loadChildren: () =>
          import('./views/app-calendar/app-calendar.module').then(
            (m) => m.AppCalendarModule
          ),
        data: { title: 'Calendar', breadcrumb: 'CALENDAR' },
      },

      // CRUD
      {
        path: 'cruds',
        loadChildren: () =>
          import('./views/cruds/cruds.module').then(
            (m) => m.CrudsModule
          ),
        data: { title: 'CRUDs', breadcrumb: 'CRUDs' },
      },

      // Recherche
      {
        path: 'search',
        loadChildren: () =>
          import('./views/search-view/search-view.module').then(
            (m) => m.SearchViewModule
          ),
        data: { title: 'Search', breadcrumb: 'SEARCH' },
      },

      // Factures
      {
        path: 'invoice',
        loadChildren: () =>
          import('./views/invoice/invoice.module').then(
            (m) => m.InvoiceModule
          ),
        data: { title: 'Invoice', breadcrumb: 'INVOICE' },
      },

      // Événements
      {
        path: 'events', // Route pour les événements
        loadChildren: () =>
          import('./views/event-list/event-list.module').then(
            (m) => m.EventListModule
          ),
        data: { title: 'Events', breadcrumb: 'EVENTS' },
      },
    ],
  },
  {
    path: '**', // Page 404
    redirectTo: 'sessions/404',
  },
];
