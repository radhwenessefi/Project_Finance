import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef as MatDialogRef, MAT_DIALOG_DATA as MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UntypedFormBuilder, Validators, UntypedFormGroup, ReactiveFormsModule } from '@angular/forms';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { get } from 'http';
import { HttpClient } from '@angular/common/http';
import { AppLoaderService } from 'app/shared/services/app-loader/app-loader.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-update-popup',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './update-popup.component.html',
  styleUrl: './update-popup.component.scss'
})
export class UpdatePopupComponent implements OnInit  {
  public itemForm: UntypedFormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<UpdatePopupComponent>,
    private fb: UntypedFormBuilder,
    private http: HttpClient,
    private loader: AppLoaderService,
    private snack: MatSnackBar,
    @Inject(PortfolioService) private portfolioService: PortfolioService
  ) { }
  ngOnInit() {
    
  }
  submit() {
  
  
    this.portfolioService.updateOrder(this.itemForm.value).subscribe(
      (response: any) => {
        console.log("Response from addInvestment:", response);
       console.log("the error message is: ", response.console.error.text);
          this.loader.close();
          this.snack.open(response.console.error.message, 'OK', { duration: 4000 });
          this.dialogRef.close(true); // Close dialog if successful
       
      },
  
    );
  }
}
