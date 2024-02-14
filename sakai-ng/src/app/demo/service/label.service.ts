import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LabelService {
    private baseUrl="http://localhost:8080/";


  constructor(private http :HttpClient) { }
    getAll()
    {
        return this.http.get(`${this.baseUrl}api/label`)
    }
    createLabel(label:any)
    {
        return this.http.post(`${this.baseUrl}api/label`,label);
    }

     deleteLabel(id:any)
     {
        return this.http.delete(`${this.baseUrl}api/label/${id}`,{responseType:'text'})
     }

}
