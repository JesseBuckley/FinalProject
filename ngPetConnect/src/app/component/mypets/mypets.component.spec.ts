import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MypetsComponent } from './mypets.component';

describe('MypetsComponent', () => {
  let component: MypetsComponent;
  let fixture: ComponentFixture<MypetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MypetsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MypetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
