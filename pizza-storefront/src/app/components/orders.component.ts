import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from '../models';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email!: string
  orderSummaries!: OrderSummary[]

  constructor(private activatedRoute: ActivatedRoute, private orderSvc : OrderService) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email']
    this.orderSvc.getOrdersByEmail(this.email)
      .then(result => {
        this.orderSummaries = result
      })
      .catch(error => {
        console.error('>>>>>>>:',error)
      });
  }

}
