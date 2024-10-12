import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

interface IMenuItem {
  type: 'link' | 'dropDown' | 'icon' | 'separator' | 'extLink';
  name?: string; // Used as display text for item and title for separator type
  state?: string; // Router state
  icon?: string; // Material icon name
  svgIcon?: string; // UI Lib icon name
  tooltip?: string; // Tooltip text
  disabled?: boolean; // If true, item will not be appeared in sidenav.
  sub?: IChildItem[]; // Dropdown items
  badges?: IBadge[];
}
interface IChildItem {
  type?: string;
  name: string; // Display text
  state?: string; // Router state
  icon?: string;  // Material icon name
  svgIcon?: string; // UI Lib icon name
  sub?: IChildItem[];
}

interface IBadge {
  color: string; // primary/accent/warn/hex color codes(#fff000)
  value: string; // Display text
}

@Injectable()
export class NavigationService {
  iconMenu: IMenuItem[] = [
    {
      name: 'HOME',
      type: 'icon',
      tooltip: 'Home',
      icon: 'home',
      state: 'home'
    },
    {
      name: 'PROFILE',
      type: 'icon',
      tooltip: 'Profile',
      icon: 'person',
      state: 'profile/overview'
    },


    {
      type: 'separator',
      name: 'Main Items'
    },
    {
      name: 'DASHBOARD',
      type: 'dropDown',
      tooltip: 'Dashboard',
      icon: 'dashboard',
      sub: [
        { name: 'Analytics', state: 'dashboard/analytics' },
        { name: 'Learning Management', state: 'dashboard/learning-management' },
        { name: 'Analytics Alt', state: 'dashboard/analytics-alt' },
        { name: 'Cryptocurrency', state: 'dashboard/crypto' },
        { name: 'Dark Cards', state: 'dashboard/dark' },
      ]
    },
    {
      name: 'Investements',
      type: 'dropDown',
      icon: 'list',
      sub: [
        {name: 'Investment', state: 'tables/mat-table'},
        {name: 'Portfolios', state: 'cruds/ngx-table'},
        
      ]
    },

    {
      name: 'Bank Accounts',
      type: 'dropDown',
      icon: 'receipt',
      sub: [
        { name: 'List', state: 'invoice/list' },
        { name: 'View / Edit / Print', state: 'invoice/5a9ae2106518248b68251fd1' },
      ]
    },

    {
      name: 'INBOX',
      type: 'link',
      icon: 'inbox',
      state: 'inbox',
      badges: [{ color: 'primary', value: '4' }]
    },
    {
      name: 'CALENDAR',
      type: 'link',
      tooltip: 'Calendar',
      icon: 'date_range',
      state: 'calendar'
    },

    {
      name: 'DIALOGS',
      type: 'dropDown',
      tooltip: 'Dialogs',
      icon: 'filter_none',
      sub: [
        { name: 'CONFIRM', state: 'dialogs/confirm' },
        { name: 'LOADER', state: 'dialogs/loader' }
      ]
    },
    {
      name: 'Material Kits',
      type: 'dropDown',
      tooltip: 'Material',
      icon: 'favorite',
      badges: [{ color: 'primary', value: '60+' }],
      sub: [
        {
          name: 'Form controls',
          type: 'dropDown',
          sub: [
            { name: 'Autocomplete', state: 'material/autocomplete' },
            { name: 'Checkbox', state: 'material/checkbox' },
            { name: 'Datepicker', state: 'material/datepicker' },
            { name: 'Form Field', state: 'material/form-field' },
            { name: 'Input Field', state: 'material/input-field' },
            { name: 'Radio Button', state: 'material/radio-button' },
            { name: 'Select', state: 'material/select' },
            { name: 'Slider', state: 'material/slider' },
            { name: 'Slider Toggle', state: 'material/slider-toggle' }
          ]
        },
        {
          name: 'Navigation',
          type: 'dropDown',
          sub: [
            { name: 'Menu', state: 'material/menu' },
            { name: 'Sidenav', state: 'material/sidenav' },
            { name: 'Toolbar', state: 'material/toolbar' }
          ]
        },

        {
          name: 'Buttons & Indicators',
          type: 'dropDown',
          sub: [
            { name: 'BUTTONS', state: 'material/buttons' },
            { name: 'Button Toggle', state: 'material/button-toggle' },
            { name: 'Badge', state: 'material/badge' },
            { name: 'Chips', state: 'material/chips' },
            { name: 'Icons', state: 'material/icons' },
            { name: 'Progress Spinner', state: 'material/progress-spinner' },
            { name: 'Progress Bar', state: 'material/progress-bar' },
            { name: 'Ripples', state: 'material/ripples' }
          ]
        },
        {
          name: 'Popups & Modals',
          type: 'dropDown',
          sub: [
            { name: 'Tooltip', state: 'material/tooltip' },
            { name: 'Bottom Sheet', state: 'material/bottom-sheet' },
            { name: 'Dialog', state: 'material/dialog' },
            { name: 'Snackbar', state: 'material/snackbar' }
          ]
        },
        {
          name: 'Data Table',
          type: 'dropDown',
          sub: [
            { name: 'paginator', state: 'material/paginator' },
            { name: 'Sort Header', state: 'material/sort-header' },
            { name: 'Table', state: 'material/table' }
          ]
        }        
      ]
    },
    {
      name: 'Micro Loan',
      type: 'dropDown',
      tooltip: 'Forms',
      icon: 'description',
      sub: [
        { name: 'BASIC', state: 'forms/basic' },

      ]
    },
    {
      name: 'PROFILE',
      type: 'dropDown',
      tooltip: 'Profile',
      icon: 'person',
      badges: [{ color: 'primary', value: '2' }],
      sub: [
        { name: 'OVERVIEW', state: 'profile/overview' },
        { name: 'SETTINGS', state: 'profile/settings' },
        { name: 'BLANK', state: 'profile/blank' }
      ]
    },



    {
      name: 'CHARTS',
      type: 'dropDown',
      tooltip: 'Charts',
      icon: 'show_chart',
      sub: [
        { name: 'Chart js', state: 'charts' },
        {
          name: 'eChart',
          type: 'dropDown',
          state: 'chart',
          sub: [
            { name: 'Pie', state: 'chart/pie' },
            { name: 'Bar', state: 'chart/bar' },
            { name: 'Radar', state: 'chart/radar' },
            { name: "Heatmap", state: "chart/heatmap" },
          ]
        }
      ]
    },
    {
      name: 'SESSIONS',
      type: 'dropDown',
      tooltip: 'Pages',
      icon: 'view_carousel',
      sub: [
        { name: 'SIGNUP', state: 'sessions/signup' },
        { name: 'Signup 2', state: 'sessions/signup2' },
        { name: 'Signup 3', state: 'sessions/signup3' },
        { name: 'Signup 4', state: 'sessions/signup4' },
        { name: 'SIGNIN', state: 'sessions/signin' },
        { name: 'Signin 2', state: 'sessions/signin2' },
        { name: 'Signin 3', state: 'sessions/signin3' },
        { name: 'Signin 4', state: 'sessions/signin4' },
        { name: 'FORGOT', state: 'sessions/forgot-password' },
        { name: 'LOCKSCREEN', state: 'sessions/lockscreen' },
        { name: 'NOTFOUND', state: 'sessions/404' },
        { name: 'ERROR', state: 'sessions/error' }
      ]
    },

    {
      name: 'OTHERS',
      type: 'dropDown',
      tooltip: 'Others',
      icon: 'blur_on',
      sub: [
        { name: 'GALLERY', state: 'others/gallery' },
        { name: 'PRICINGS', state: 'others/pricing' },
        { name: 'USERS', state: 'others/users' },
        { name: 'BLANK', state: 'others/blank' }
      ]
    },
    {
      name: 'Insurance',
      type: 'link',
      tooltip: 'Material Icons',
      icon: 'store',
      state: 'icons'
    },
    {
      name: 'DOC',
      type: 'extLink',
      tooltip: 'Documentation',
      icon: 'library_books',
      state: 'http://demos.ui-lib.com/egret-doc/'
    }
  ];

  separatorMenu: IMenuItem[] = [
    {
      type: 'separator',
      name: 'CUSTOM COMPONENTS'
    },
    {
      name: 'DASHBOARD',
      type: 'dropDown',
      tooltip: 'Dashboard',
      icon: 'dashboard',
      sub: [
        { name: 'Analytics', state: 'dashboard/analytics' },
        { name: 'Learning Management', state: 'dashboard/learning-management' },
        { name: 'Analytics Alt', state: 'dashboard/analytics-alt' },
        { name: 'Cryptocurrency', state: 'dashboard/crypto' },
        { name: 'Dark Cards', state: 'dashboard/dark' },
      ]
    },
    {
      name: 'CRUD Table',
      type: 'link',
      tooltip: 'CRUD Table',
      icon: 'list',
      state: 'cruds/ngx-table'
    },
    {
      name: 'ECOMMERCE',
      type: 'dropDown',
      tooltip: 'Shop',
      icon: 'shopping_cart',
      sub: [
        { name: 'PRODUCTS', state: 'shop' },
        { name: 'PRODUCT DETAILS', state: 'shop/products/5a9ae2106f155194e5c95d67' },
        { name: 'CART', state: 'shop/cart' },
        { name: 'CHECKOUT', state: 'shop/checkout' }
      ]
    },
    {
      name: 'INBOX',
      type: 'link',
      icon: 'inbox',
      state: 'inbox',
      badges: [{ color: 'primary', value: '4' }]
    },
    {
      name: 'Bank Accounts',
      type: 'dropDown',
      icon: 'receipt',
      sub: [
        { name: 'List', state: 'invoice/list' },
        { name: 'View / Edit', state: 'invoice/5a9ae2106518248b68251fd1' },
      ]
    },
    {
      name: 'PROFILE',
      type: 'dropDown',
      tooltip: 'Profile',
      icon: 'person',
      badges: [{ color: 'primary', value: '2' }],
      sub: [
        { name: 'OVERVIEW', state: 'profile/overview' },
        { name: 'SETTINGS', state: 'profile/settings' },
        { name: 'BLANK', state: 'profile/blank' }
      ]
    },


    {
      name: 'DIALOGS',
      type: 'dropDown',
      tooltip: 'Dialogs',
      icon: 'filter_none',
      sub: [
        { name: 'CONFIRM', state: 'dialogs/confirm' },
        { name: 'LOADER', state: 'dialogs/loader' }
      ]
    },
    {
      type: 'separator',
      name: 'INTEGRATED COMPONENTS'
    },
    {
      name: 'Material Kits',
      type: 'dropDown',
      tooltip: 'Material',
      icon: 'favorite',
      badges: [{ color: 'primary', value: '60+' }],
      sub: [
        {
          name: 'Form controls',
          type: 'dropDown',
          sub: [
            { name: 'Autocomplete', state: 'material/autocomplete' },
            { name: 'Checkbox', state: 'material/checkbox' },
            { name: 'Datepicker', state: 'material/datepicker' },
            { name: 'Form Field', state: 'material/form-field' },
            { name: 'Input Field', state: 'material/input-field' },
            { name: 'Radio Button', state: 'material/radio-button' },
            { name: 'Select', state: 'material/select' },
            { name: 'Slider', state: 'material/slider' },
            { name: 'Slider Toggle', state: 'material/slider-toggle' }
          ]
        },
        {
          name: 'Navigation',
          type: 'dropDown',
          sub: [
            { name: 'Menu', state: 'material/menu' },
            { name: 'Sidenav', state: 'material/sidenav' },
            { name: 'Toolbar', state: 'material/toolbar' }
          ]
        },
        {
          name: 'Layout',
          type: 'dropDown',
          sub: [
            { name: 'Card', state: 'material/card' },
            { name: 'Divider', state: 'material/divider' },
            { name: 'Expansion Panel', state: 'material/expansion-panel' },
            { name: 'Grid', state: 'material/grid' },
            { name: 'List', state: 'material/list' },
            { name: 'Stepper', state: 'material/stepper' },
            { name: 'Tab', state: 'material/tab-group' },
            { name: 'Tree', state: 'material/tree' }
          ]
        },
        {
          name: 'Buttons & Indicators',
          type: 'dropDown',
          sub: [
            { name: 'BUTTONS', state: 'material/buttons' },
            { name: 'Button Toggle', state: 'material/button-toggle' },
            { name: 'Badge', state: 'material/badge' },
            { name: 'Chips', state: 'material/chips' },
            { name: 'Icons', state: 'material/icons' },
            { name: 'Progress Spinner', state: 'material/progress-spinner' },
            { name: 'Progress Bar', state: 'material/progress-bar' },
            { name: 'Ripples', state: 'material/ripples' }
          ]
        },
        {
          name: 'Popups & Modals',
          type: 'dropDown',
          sub: [
            { name: 'Tooltip', state: 'material/tooltip' },
            { name: 'Bottom Sheet', state: 'material/bottom-sheet' },
            { name: 'Dialog', state: 'material/dialog' },
            { name: 'Snackbar', state: 'material/snackbar' }
          ]
        },
        {
          name: 'Data Table',
          type: 'dropDown',
          sub: [
            { name: 'paginator', state: 'material/paginator' },
            { name: 'Sort Header', state: 'material/sort-header' },
            { name: 'Table', state: 'material/table' }
          ]
        },
      ]
    },
    {
      name: 'CALENDAR',
      type: 'link',
      icon: 'date_range',
      state: 'calendar'
    },
    {
      name: 'Micro loan',
      type: 'dropDown',
      tooltip: 'Forms',
      icon: 'description',
      sub: [
        { name: 'BASIC', state: 'forms/basic' },

      ]
    },
    {
      name: 'Table',
      type: 'link',
      tooltip: 'Table',
      icon: 'format_line_spacing',
      state: 'tables/mat-table',
    },


    {
      name: 'CHARTS',
      type: 'dropDown',
      tooltip: 'Charts',
      icon: 'show_chart',
      sub: [
        { name: 'Chart js', state: 'charts' },
        {
          name: 'eChart',
          type: 'dropDown',
          state: 'chart',
          sub: [
            { name: 'Pie', state: 'chart/pie' },
            { name: 'Bar', state: 'chart/bar' },
            { name: 'Radar', state: 'chart/radar' },
            { name: "Heatmap", state: "chart/heatmap" },
          ]
        }
      ]
    },
    {
      name: 'OTHER COMPONENTS',
      type: 'separator',
    },
    {
      name: 'SESSIONS',
      type: 'dropDown',
      tooltip: 'Pages',
      icon: 'view_carousel',
      sub: [
        { name: 'SIGNUP', state: 'sessions/signup' },
        { name: 'Signup 2', state: 'sessions/signup2' },
        { name: 'Signup 3', state: 'sessions/signup3' },
        { name: 'Signup 4', state: 'sessions/signup4' },
        { name: 'SIGNIN', state: 'sessions/signin' },
        { name: 'Signin 2', state: 'sessions/signin2' },
        { name: 'Signin 3', state: 'sessions/signin3' },
        { name: 'Signin 4', state: 'sessions/signin4' },
        { name: 'FORGOT', state: 'sessions/forgot-password' },
        { name: 'LOCKSCREEN', state: 'sessions/lockscreen' },
        { name: 'NOTFOUND', state: 'sessions/404' },
        { name: 'ERROR', state: 'sessions/error' }
      ]
    },

    {
      name: 'OTHERS',
      type: 'dropDown',
      tooltip: 'Others',
      icon: 'blur_on',
      sub: [
        { name: 'GALLERY', state: 'others/gallery' },
        { name: 'PRICINGS', state: 'others/pricing' },
        { name: 'USERS', state: 'others/users' },
        { name: 'BLANK', state: 'others/blank' }
      ]
    },
    {
      name: 'Insurance',
      type: 'link',
      tooltip: 'Material Icons',
      icon: 'store',
      state: 'icons'
    },
    {
      name: 'Multi Level',
      type: 'dropDown',
      tooltip: 'Multi Level',
      icon: 'format_align_center',
      sub: [
        { name: 'Level Two', state: 'fake-4' },
        {
          name: 'Level Two',
          type: 'dropDown',
          state: 'fake-1',
          sub: [
            { name: 'Level Three', state: 'fake-2' },
            { 
              name: 'Level Three', 
              type: 'dropDown',
              state: 'fake-3',
              sub: [
                { name: 'Level Four', state: 'fake-3' },
                { 
                  name: 'Level Four', 
                  type: 'dropDown',
                  state: 'fake-4',
                  sub: [
                    { name: 'Level Five', state: 'fake-3' },
                    { name: 'Level Five', type: 'link' }
                  ]
                }
              ]
            }
          ]
        },
        { name: 'Level Two', state: 'fake-5' }
      ]
    },
    {
      name: 'DOC',
      type: 'extLink',
      tooltip: 'Documentation',
      icon: 'library_books',
      state: 'http://demos.ui-lib.com/egret-doc/'
    }
  ];

  plainMenu: IMenuItem[] = [
    {
      name: 'DASHBOARD',
      type: 'dropDown',
      tooltip: 'Dashboard',
      icon: 'dashboard',
      sub: [
        { name: 'Analytics', state: 'dashboard/analytics' },
        { name: 'Learning Management', state: 'dashboard/learning-management' },
        { name: 'Analytics Alt', state: 'dashboard/analytics-alt' },
        { name: 'Cryptocurrency', state: 'dashboard/crypto' },
        { name: 'Dark Cards', state: 'dashboard/dark' },
      ]
    },
    {
      name: 'CRUD Table',
      type: 'link',
      tooltip: 'CRUD Table',
      icon: 'list',
      state: 'cruds/ngx-table'
    },
    {
      name: 'INBOX',
      type: 'link',
      icon: 'inbox',
      state: 'inbox',
      badges: [{ color: 'primary', value: '4' }]
    },
    {
      name: 'Bank Accounts',
      type: 'dropDown',
      icon: 'receipt',
      sub: [
        { name: 'List', state: 'invoice/list' },
        { name: 'View / Edit', state: 'invoice/5a9ae2106518248b68251fd1' },
      ]
    },

    {
      name: 'CALENDAR',
      type: 'link',
      tooltip: 'Calendar',
      icon: 'date_range',
      state: 'calendar'
    },

    {
      name: 'DIALOGS',
      type: 'dropDown',
      tooltip: 'Dialogs',
      icon: 'filter_none',
      sub: [
        { name: 'CONFIRM', state: 'dialogs/confirm' },
        { name: 'LOADER', state: 'dialogs/loader' }
      ]
    },
    {
      name: 'Material Kits',
      type: 'dropDown',
      tooltip: 'Material',
      icon: 'favorite',
      badges: [{ color: 'primary', value: '60+' }],
      sub: [
        {
          name: 'Form controls',
          type: 'dropDown',
          sub: [
            { name: 'Autocomplete', state: 'material/autocomplete' },
            { name: 'Checkbox', state: 'material/checkbox' },
            { name: 'Datepicker', state: 'material/datepicker' },
            { name: 'Form Field', state: 'material/form-field' },
            { name: 'Input Field', state: 'material/input-field' },
            { name: 'Radio Button', state: 'material/radio-button' },
            { name: 'Select', state: 'material/select' },
            { name: 'Slider', state: 'material/slider' },
            { name: 'Slider Toggle', state: 'material/slider-toggle' }
          ]
        },
        {
          name: 'Navigation',
          type: 'dropDown',
          sub: [
            { name: 'Menu', state: 'material/menu' },
            { name: 'Sidenav', state: 'material/sidenav' },
            { name: 'Toolbar', state: 'material/toolbar' }
          ]
        },
        {
          name: 'Layout',
          type: 'dropDown',
          sub: [
            { name: 'Card', state: 'material/card' },
            { name: 'Divider', state: 'material/divider' },
            { name: 'Expansion Panel', state: 'material/expansion-panel' },
            { name: 'Grid', state: 'material/grid' },
            { name: 'List', state: 'material/list' },
            { name: 'Stepper', state: 'material/stepper' },
            { name: 'Tab', state: 'material/tab-group' },
            { name: 'Tree', state: 'material/tree' }
          ]
        },
        {
          name: 'Buttons & Indicators',
          type: 'dropDown',
          sub: [
            { name: 'BUTTONS', state: 'material/buttons' },
            { name: 'Button Toggle', state: 'material/button-toggle' },
            { name: 'Badge', state: 'material/badge' },
            { name: 'Chips', state: 'material/chips' },
            { name: 'Icons', state: 'material/icons' },
            { name: 'Progress Spinner', state: 'material/progress-spinner' },
            { name: 'Progress Bar', state: 'material/progress-bar' },
            { name: 'Ripples', state: 'material/ripples' }
          ]
        },
        {
          name: 'Popups & Modals',
          type: 'dropDown',
          sub: [
            { name: 'Tooltip', state: 'material/tooltip' },
            { name: 'Bottom Sheet', state: 'material/bottom-sheet' },
            { name: 'Dialog', state: 'material/dialog' },
            { name: 'Snackbar', state: 'material/snackbar' }
          ]
        },
        {
          name: 'Data Table',
          type: 'dropDown',
          sub: [
            { name: 'paginator', state: 'material/paginator' },
            { name: 'Sort Header', state: 'material/sort-header' },
            { name: 'Table', state: 'material/table' }
          ]
        }
      ]
    },
    {
      name: 'Micro Loan',
      type: 'dropDown',
      tooltip: 'Forms',
      icon: 'description',
      sub: [
        { name: 'BASIC', state: 'forms/basic' }
  
      ]
    },
    {
      name: 'Table',
      type: 'link',
      tooltip: 'Table',
      icon: 'format_line_spacing',
      state: 'tables/mat-table',
    },
    {
      name: 'PROFILE',
      type: 'dropDown',
      tooltip: 'Profile',
      icon: 'person',
      badges: [{ color: 'primary', value: '2' }],
      sub: [
        { name: 'OVERVIEW', state: 'profile/overview' },
        { name: 'SETTINGS', state: 'profile/settings' },
        { name: 'BLANK', state: 'profile/blank' }
      ]
    },


    {
      name: 'CHARTS',
      type: 'dropDown',
      tooltip: 'Charts',
      icon: 'show_chart',
      sub: [
        { name: 'Chart js', state: 'charts' },
        {
          name: 'eChart',
          type: 'dropDown',
          state: 'chart',
          sub: [
            { name: 'Pie', state: 'chart/pie' },
            { name: 'Bar', state: 'chart/bar' },
            { name: 'Radar', state: 'chart/radar' },
            { name: "Heatmap", state: "chart/heatmap" },
          ]
        }
      ]
    },
    {
      name: 'SESSIONS',
      type: 'dropDown',
      tooltip: 'Pages',
      icon: 'view_carousel',
      sub: [
        { name: 'SIGNUP', state: 'sessions/signup' },
        { name: 'Signup 2', state: 'sessions/signup2' },
        { name: 'Signup 3', state: 'sessions/signup3' },
        { name: 'Signup 4', state: 'sessions/signup4' },
        { name: 'SIGNIN', state: 'sessions/signin' },
        { name: 'Signin 2', state: 'sessions/signin2' },
        { name: 'Signin 3', state: 'sessions/signin3' },
        { name: 'Signin 4', state: 'sessions/signin4' },
        { name: 'FORGOT', state: 'sessions/forgot-password' },
        { name: 'LOCKSCREEN', state: 'sessions/lockscreen' },
        { name: 'NOTFOUND', state: 'sessions/404' },
        { name: 'ERROR', state: 'sessions/error' }
      ]
    },

    {
      name: 'OTHERS',
      type: 'dropDown',
      tooltip: 'Others',
      icon: 'blur_on',
      sub: [
        { name: 'GALLERY', state: 'others/gallery' },
        { name: 'PRICINGS', state: 'others/pricing' },
        { name: 'USERS', state: 'others/users' },
        { name: 'BLANK', state: 'others/blank' }
      ]
    },
    {
      name: 'Insurance',
      type: 'link',
      tooltip: 'Material Icons',
      icon: 'store',
      state: 'icons'
    },
    {
      name: 'Multi Level',
      type: 'dropDown',
      tooltip: 'Multi Level',
      icon: 'format_align_center',
      sub: [
        { name: 'Level Two', state: 'fake-4' },
        {
          name: 'Level Two',
          type: 'dropDown',
          state: 'fake-1',
          sub: [
            { name: 'Level Three', state: 'fake-2' },
            { 
              name: 'Level Three', 
              type: 'dropDown',
              state: 'fake-3',
              sub: [
                { name: 'Level Four', state: 'fake-3' },
                { 
                  name: 'Level Four', 
                  type: 'dropDown',
                  state: 'fake-4',
                  sub: [
                    { name: 'Level Five', state: 'fake-3' },
                    { name: 'Level Five', type: 'link' }
                  ]
                }
              ]
            }
          ]
        },
        { name: 'Level Two', state: 'fake-5' }
      ]
    },
    {
      name: 'DOC',
      type: 'extLink',
      tooltip: 'Documentation',
      icon: 'library_books',
      state: 'http://demos.ui-lib.com/egret-doc/'
    }
  ];

  // Icon menu TITLE at the very top of navigation.
  // This title will appear if any icon type item is present in menu.
  iconTypeMenuTitle = 'Frequently Accessed';
  // sets iconMenu as default;
  menuItems = new BehaviorSubject<IMenuItem[]>(this.iconMenu);
  // navigation component has subscribed to this Observable
  menuItems$ = this.menuItems.asObservable();
  constructor() { }

  // Customizer component uses this method to change menu.
  // You can remove this method and customizer component.
  // Or you can customize this method to supply different menu for
  // different user type.
  publishNavigationChange(menuType: string) {
    switch (menuType) {
      case 'separator-menu':
        this.menuItems.next(this.separatorMenu);
        break;
      case 'icon-menu':
        this.menuItems.next(this.iconMenu);
        break;
      default:
        this.menuItems.next(this.plainMenu);
    }
  }
}
