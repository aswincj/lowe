import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class HttpService {
  constructor(private http: HttpClient) {}
  API_URL: string = "http://localhost:8080/api/";
  public getAllTodos() {
    return this.http.get(this.API_URL + "alldetails");
  }

  public create(url) {
    var formData: any = new FormData();
    formData.append("url", url);
    return this.http.post(this.API_URL + "create", formData);
  }
}
