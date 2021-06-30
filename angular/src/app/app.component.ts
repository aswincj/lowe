import { HttpService } from "./http.service";
import { Component } from "@angular/core";
import { MatTableModule } from "@angular/material/table";
import { MatInputModule } from "@angular/material/input";
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
  FormGroup,
  FormBuilder,
} from "@angular/forms";
import { ErrorStateMatcher } from "@angular/material/core";

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}
@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  form: FormGroup = new FormGroup({});
  matcher = new MyErrorStateMatcher();
  shorturl: any;
  constructor(private api: HttpService, private fb: FormBuilder) {
    const reg = "(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?";
    this.form = fb.group({
      url: ["", [Validators.required, Validators.pattern(reg)]],
    });
  }
  get f() {
    return this.form.controls;
  }
  model: any = {};
  submit() {
    var string = this.form.value.url;
    if (!string.includes("http")) {
      string = "https://" + string;
    }

    this.api.create(string).subscribe(
      (res) => {
        console.log(res);
        this.model = res;
        this.shorturl = "http://localhost:8080/api/" + this.model.code;
        this.getdetails();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  title = "lowe";
  dataSource: any;

  displayedColumns: string[] = ["BaseUrl", "ShortUrl", "Hits"];
  ngOnInit(): void {
    this.getdetails();
  }

  getdetails() {
    this.api.getAllTodos().subscribe(
      (res) => {
        this.dataSource = res;
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
