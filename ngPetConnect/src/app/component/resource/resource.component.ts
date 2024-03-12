import { Component, OnInit } from '@angular/core';
import { ResourceService } from '../../services/resource.service';
import { AuthService } from '../../services/auth.service';
import { Resource } from '../../models/resource';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css'],
})
export class ResourceComponent implements OnInit {
  resources: Resource[] = [];
  errorMessage: string | undefined;
  currentUser: any;
  categories: Category[] = [];


  constructor(
    private resourceService: ResourceService,
    private authService: AuthService, private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.loadAllResources();
    this.getLoggedInUser();
  }

  getLoggedInUser() {
    this.authService.getLoggedInUser().subscribe({
      next: (currentUser) => {
        this.currentUser = currentUser;
      },
      error: (err) => {
        console.error('Error loading the current user', err);
        this.errorMessage = `Error loading the current user: ${err}`;
      },
    });
  }

  loadAllResources(): void {
    this.resourceService.getAllResources().subscribe({
      next: (resources) => {
        this.resources = resources;
      },
      error: (err) => {
        console.error('Error loading resources', err);
        this.errorMessage = `Error loading resources: ${err}`;
      },
    });
  }
  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => (this.categories = categories),
      error: (err) => (this.errorMessage = `Error loading categories: ${err}`),
    });
  }
  createResource(newResource: Resource): void {
    this.resourceService.createResource(newResource).subscribe({
      next: (resource) => {
        this.resources.push(resource);
      },
      error: (err) => {
        console.error('Error creating resource', err);
        this.errorMessage = `Error creating resource: ${err}`;
      },
    });
  }

  updateResource(resourceId: number, updatedResource: Resource): void {
    this.resourceService.updateResource(resourceId, updatedResource).subscribe({
      next: (resource) => {
        const index = this.resources.findIndex(r => r.id === resourceId);
        if (index !== -1) {
          this.resources[index] = resource;
        }
      },
      error: (err) => {
        console.error('Error updating resource', err);
        this.errorMessage = `Error updating resource: ${err}`;
      },
    });
  }

  deleteResource(resourceId: number): void {
    this.resourceService.deleteResource(resourceId).subscribe({
      next: () => {
        this.resources = this.resources.filter(resource => resource.id !== resourceId);
      },
      error: (err) => {
        console.error('Error deleting resource', err);
        this.errorMessage = `Error deleting resource: ${err}`;
      },
    });
  }


  getResourcesByCategory(categoryId: number): void {
    this.resourceService.getResourcesByCategory(categoryId).subscribe({
      next: (resources) => console.log(resources),
      error: (err) => this.handleError(err)
    });
  }

  searchResourcesByKeyword(keyword: string): void {
    this.resourceService.searchResourcesByKeyword(keyword).subscribe({
      next: (resources) => console.log(resources),
      error: (err) => this.handleError(err)
    });
  }

  findResourcesByCity(city: string): void {
    this.resourceService.findResourcesByCity(city).subscribe({
      next: (resources) => console.log(resources),
      error: (err) => this.handleError(err)
    });
  }

  findResourcesByState(state: string): void {
    this.resourceService.findResourcesByState(state).subscribe({
      next: (resources) => console.log(resources),
      error: (err) => this.handleError(err)
    });
  }

  findResourcesByZip(zip: string): void {
    this.resourceService.findResourcesByZip(zip).subscribe({
      next: (resources) => console.log(resources),
      error: (err) => this.handleError(err)
    });
  }

  private handleError(error: any): void {
    console.error('Service Error:', error);
    this.errorMessage = error;
  }
}

