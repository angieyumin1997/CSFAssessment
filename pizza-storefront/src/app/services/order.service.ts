import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Order, OrderSummary } from "../models";

@Injectable()
export class OrderService {

    constructor(private http: HttpClient) { }

    insert(order: Order) {
        return firstValueFrom(
            this.http.post('api/order', order)
        )
    }

    getOrdersByEmail(email: string): Promise<OrderSummary[]> {
        return firstValueFrom(
          this.http.get<OrderSummary[]>(`/api/order/${email}/all`)
        )
      }
}