import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ResourceService } from 'app/Resource/resource.service';
@Component({
  selector: 'app-resource-popup',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
  ],  templateUrl: './resource-popup.component.html',
  styleUrl: './resource-popup.component.scss'
})
export class ResourcePopupComponent {
  resourceForm: FormGroup;

  resourceTypes = [
    { value: 'VIDEO', label: 'Video' },
    { value: 'PDF', label: 'PDF' },
    { value: 'ARTICLE', label: 'Article' },
    { value: 'TOOL', label: 'Tool' },
  ];

  constructor(
    public dialogRef: MatDialogRef<ResourcePopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private resourceService: ResourceService
  ) {
    this.resourceForm = this.fb.group({
      resourceTitle: ['', Validators.required],
      resourceType: ['', Validators.required],
      url: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    if (this.resourceForm.valid) {
      const resourceData = this.resourceForm.value;

      this.resourceService.createResource(resourceData).subscribe({
        next: (response) => {
          console.log('Resource created successfully:', response);
          this.dialogRef.close(response); // Close dialog and send created resource
        },
        error: (error) => {
          console.error('Error creating resource:', error);
        },
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close(); // Close the dialog without saving
  }
}