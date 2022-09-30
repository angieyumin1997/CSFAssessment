import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router'

import { HttpClientModule } from '@angular/common/http'
import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';
import { OrdersComponent } from './components/orders.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderService } from './services/order.service';

const appPath: Routes = [
  { path: '', component: MainComponent },
  { path: 'orders/:email', component: OrdersComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full'},
]

@NgModule({
  declarations: [
    AppComponent, MainComponent, OrdersComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, ReactiveFormsModule,
    RouterModule.forRoot(appPath),
    HttpClientModule
  ],

  providers: [OrderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
