import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerService} from "../services/customer.service";
import {CustomersComponent} from "../customers/customers.component";
import {Customer} from "../model/customer.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit{
  newCustomerFormGroup!: FormGroup;
  constructor(private fb:FormBuilder,
              private service:CustomerService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.newCustomerFormGroup=this.fb.group({
      name:this.fb.control(null,[Validators.required,Validators.minLength(4)]),
      email:this.fb.control(null,[Validators.email,Validators.required])
    })
  }

  handleSaveCustomer() {
    let customer:Customer=this.newCustomerFormGroup.value;
    this.service.saveCustomer(customer).subscribe({
      next:value => {
        alert("customer has been saved !");
        //this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers");
          },error:err => {
        console.log(err);
      }
    });
  }
}
