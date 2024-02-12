import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string ='';
  apiUrl: string = 'http://localhost:8080/api/login'; // URL of your Spring Boot backend

  constructor(private http: HttpClient, private router: Router) { } // Inject Router in the constructor

  onSubmit() {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)
    });

    this.http.get(this.apiUrl, { headers, responseType: 'text', withCredentials: true }).subscribe(
      response => {
        console.log('Success:', response);
        // Handle successful authentication here
        localStorage.setItem('username', this.username);
        this.router.navigate(['/welcome']); // Navigate to file upload page
      },
      error => {
        console.error('Error:', error);
      
        this.errorMessage = 'Username or password is incorrect';
      }
    );
  }
  }