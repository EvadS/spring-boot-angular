import { Component, OnInit } from '@angular/core';
import {FileUploadService} from "../services/file-upload.service";
import {Image} from "../models/Image";
import {ImageDataService} from "../services/image-data.service";


@Component({
  selector: 'app-file-upload',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnInit {

  shortLink: string = "";
  loading: boolean = false;
  file: File = null;

  data = '';
  images?: Image[];
  currentImage: Image = {};
  currentIndex = -1;

  constructor(private fileUploadService: FileUploadService, private imageDataService: ImageDataService) { }

  ngOnInit(): void {
    console.log("retrieveImages")
    this.retrieveImages();
  }

  // On file Select
  onChange(event) {
    this.file = event.target.files[0];
  }

  // OnClick of button Upload
  onUpload() {
    this.loading = !this.loading;
    console.log(this.file);
    this.fileUploadService.upload(this.file).subscribe(
      (event: any) => {
        if (typeof (event) === 'object') {

          // Short link via api response
          this.shortLink = event.link;
          this.loading = false; // Flag variable
        }
      }
    );
    this.retrieveImages();
  }


  refreshList(): void {
    this.retrieveImages();
    this.currentImage = {};
    this.currentIndex = -1;
  }

  searchByTitle() {
    this.currentImage = {};
    this.currentIndex = -1;

    this.imageDataService.findByTitle(this.data)
      .subscribe(
        data => {
          this.images = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  private retrieveImages() {
    this.imageDataService.findAll()
      .subscribe(
        data => {
          this.images = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }
}
