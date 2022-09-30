import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from '../models';
import { OrderService } from '../services/order.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

  pizzaSize = SIZES[0]

  form!: FormGroup
  email!: string 
  todoArrayCtrl!: FormArray

  constructor(private fb: FormBuilder, private orderSvc : OrderService, private router : Router) {}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  private createForm(): FormGroup {
    this.todoArrayCtrl = this.fb.array([this.fb.group({
      chicken: this.fb.control<Boolean>(false),
      seafood: this.fb.control<Boolean>(false),
      beef: this.fb.control<Boolean>(false),
      vegetables: this.fb.control<Boolean>(false),
      cheese: this.fb.control<Boolean>(false),
      arugula: this.fb.control<Boolean>(false),
      pineapple: this.fb.control<Boolean>(false),
    })])

    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required]),
      email: this.fb.control<string>('', [ Validators.required, Validators.email]),
      size: this.fb.control<string>('', [ Validators.required]),
      base: this.fb.control<string>('', [ Validators.required]),
      sauce: this.fb.control<string>('', [ Validators.required]),
      comments: this.fb.control<string>(''),
      toppings: this.todoArrayCtrl
    })
  }

  processForm() {
    const order: Order = this.form.value as Order
    this.email = order.email
    console.info('>>> order: ', order)
    this.orderSvc.insert(order)
    this.router.navigate(['/orders',this.email])
  }

}
