// Add your models here if you have any
export interface Order {
    name: string
    email: string
    size: number
    base: boolean
    sauce: string
    toppings: string
    comments: string
  }

  export interface OrderSummary {
    orderId: number
    amount: number
    email: string
    name: string
  }
