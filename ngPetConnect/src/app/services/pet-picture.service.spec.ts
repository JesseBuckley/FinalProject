import { TestBed } from '@angular/core/testing';

import { PetPictureService } from './pet-picture.service';

describe('PetPictureService', () => {
  let service: PetPictureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PetPictureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
