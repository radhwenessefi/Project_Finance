import { CommonModule } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { UserService } from 'app/User/user.service';

@Component({
  selector: 'app-users',
  standalone: true, 
  imports: [CommonModule,MatIconModule,MatCardModule],
  templateUrl: './app-users.component.html',
  styleUrls: ['./app-users.component.css']
})
export class AppUsersComponent implements OnInit {
  users: any[] = []; // Dynamic user data from API
  loading: boolean = true; // Loading indicator
  error: string | null = null; // Error message, if any
  @Output() userSelected = new EventEmitter<any>(); 
  selectedUser: any = null;
  constructor(private userService: UserService) {}

  ngOnInit() {
    this.fetchUsers();
  }

  fetchUsers() {
    this.userService.getAllEvents().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to fetch users. Please try again later.';
        this.loading = false;
        console.error(err);
      }
    });
  }
  selectUser(user: any): void {
    console.log(user)
    this.selectedUser = user; 
    this.userSelected.emit(user); 
  }
}
