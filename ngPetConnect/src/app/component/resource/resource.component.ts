import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ResourceService } from '../../services/resource.service';
import { CategoryService } from '../../services/category.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Resource } from '../../models/resource';
import { Category } from '../../models/category';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { GoogleMapsModule } from '@angular/google-maps';
import { Address } from '../../models/address';

@Component({
  selector: 'app-resource',
  standalone: true,
  imports: [FormsModule, CommonModule, NgbAccordionModule, GoogleMapsModule],
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css'],
})
export class ResourceComponent implements OnInit {
  allResources: Resource[] = [];
  resources: Resource[] = [];
  resource: Resource | any;
  categories: Category[] = [];
  selectedResource: Resource = new Resource();
  showForm: boolean = false;
  keyword: string = '';
  location: string = '';
  errorMessage: string | undefined;
  currentUser: any;
  currentKeyword: string = '';
  currentLocation: string = '';
  currentCategoryId: string | null = 'all';

  constructor(
    private resourceService: ResourceService,
    private authService: AuthService,
    private catService: CategoryService,
    private sanitizer: DomSanitizer
  ) {}
  // ****************************** Loading... ******************************
  ngOnInit(): void {
    this.loadAllResources();
    this.loadCategories();
    this.getLoggedInUser();
  }
  getGoogleMapsSrc(address: Address): SafeResourceUrl {
    const addressString =
      `${address.street}, ${address.city}, ${address.zip}`.replace(/\s/g, '+');
    const mapsUrl = `https://www.google.com/maps/embed/v1/search?q=${addressString}&key=AIzaSyAkNp0JjCHBEY4IBL4-GizEZeeX_XTtvwo`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(mapsUrl);
  }
  getLoggedInUser(): void {
    this.authService.getLoggedInUser().subscribe({
      next: (user) => (this.currentUser = user),
      error: (err) =>
        this.handleError(`Error loading the current user: ${err}`),
    });
  }

  loadAllResources(): void {
    this.resourceService.getAllResources().subscribe({
      next: (resources) => {
        this.allResources = resources;
        this.applyAllFilters(); // Apply filters immediately after fetching
      },
      error: (err) => {
        console.error('Error loading resources', err);
        // Handle error
      },
    });
  }

  loadCategories(): void {
    this.catService.getAllCategories().subscribe({
      next: (categories) => (this.categories = categories),
      error: (err) => this.handleError(`Error loading categories: ${err}`),
    });
  }

  // ****************************** Resource CRUD ******************************
  createOrUpdateResource(): void {
    if (this.selectedResource?.id) {
      this.resourceService
        .updateResource(this.selectedResource.id, this.selectedResource)
        .subscribe({
          next: () => this.onSaveSuccess(),
          error: (err) => this.handleError(`Error updating resource: ${err}`),
        });
    } else {
      this.resourceService.createResource(this.selectedResource!).subscribe({
        next: () => this.onSaveSuccess(),
        error: (err) => this.handleError(`Error creating resource: ${err}`),
      });
    }
  }

  deleteResource(resourceId: number): void {
    this.resourceService.deleteResource(resourceId).subscribe({
      next: () => {
        this.resources = this.resources.filter(
          (resource) => resource.id !== resourceId
        );
      },
      error: (err) => this.handleError(`Error deleting resource: ${err}`),
    });
  }
  // ****************************** Filters ******************************

  filterByLocation(location: string): void {
    this.currentLocation = location;
    this.applyAllFilters();
  }

  findResourcesByCategory(categoryId: string): void {
    this.currentCategoryId = categoryId;
    this.applyAllFilters();
  }

  searchResourcesByKeyword(keyword: string): void {
    this.currentKeyword = keyword;
    this.applyAllFilters();
  }

  applyAllFilters(): void {
    let filteredResources = [...this.allResources];
    if (this.currentKeyword) {
      filteredResources = filteredResources.filter(
        (resource) =>
          resource.name
            .toLowerCase()
            .includes(this.currentKeyword.toLowerCase()) ||
          resource.description
            .toLowerCase()
            .includes(this.currentKeyword.toLowerCase())
      );
    }

    if (this.currentLocation) {
      filteredResources = filteredResources.filter(
        (resource) =>
          resource.address.city
            .toLowerCase()
            .includes(this.currentLocation.toLowerCase()) ||
          resource.address.state
            .toLowerCase()
            .includes(this.currentLocation.toLowerCase()) ||
          resource.address.zip.includes(this.currentLocation)
      );
    }

    if (this.currentCategoryId && this.currentCategoryId !== 'all') {
      const categoryIdNumber = Number(this.currentCategoryId);
      filteredResources = filteredResources.filter(
        (resource) => resource.category.id === categoryIdNumber
      );
    }

    this.resources = filteredResources;
  }
  // ****************************** Helpers ******************************
  editResource(resource: Resource): void {
    this.selectResource(resource);
  }
  handleError(errorMessage: string): void {
    console.error(errorMessage);
    this.errorMessage = errorMessage;
  }

  onResourceUpdated(): void {
    this.loadAllResources();
    this.resetForm();
  }

  onResourceCreated(): void {
    this.loadAllResources();
    this.resetForm();
  }

  resetForm(): void {
    this.selectedResource = new Resource();
    this.showForm = false;
  }
  onSaveSuccess(): void {
    this.loadAllResources();
    this.resetFormAndHide();
  }

  resetFormAndHide(): void {
    this.selectedResource = this.getNewResource();
    this.showForm = false;
  }

  toggleFormVisibility(): void {
    if (this.showForm) {
      this.resetFormAndHide();
    } else {
      this.showForm = true;
      this.selectedResource = this.getNewResource();
    }
  }
  getNewResource(): Resource {
    return new Resource();
  }

  selectResource(resource: Resource): void {
    this.selectedResource = { ...resource };
    this.showForm = true;
  }
  resetFilters(): void {
    this.currentKeyword = '';
    this.currentLocation = '';
    this.currentCategoryId = 'all';

    this.keyword = '';
    this.location = '';

    this.applyAllFilters();
  }

  viewResourceDetails(resource: Resource) {
    this.selectedResource = resource;
  }
}
