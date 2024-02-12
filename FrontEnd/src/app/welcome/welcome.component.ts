import { HttpClient, HttpEventType,HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent {
  constructor(private httpClient: HttpClient) {}

  retrievedData: any;
  selectedFiles: (File | null)[] = [null, null, null];
  imgURLs: (string | null)[] = [null, null, null];
  message: string = "";
  fileName: string = "";

  public onFileChanged(event: any, index: number) {
    // Select File
    this.selectedFiles[index] = event.target.files[0];

    // Display image preview
    if (this.selectedFiles[index]) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imgURLs[index] = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFiles[index]!);
    } else {
      console.error('Selected file is null.');
      // Handle the null case if needed
    }
  }

  onUpload(index: number) {
    const uploadData = new FormData();
    if (this.selectedFiles[index]) {
      uploadData.append('files', this.selectedFiles[index]!, this.selectedFiles[index]!.name);
      
      const id = 1; // Replace this with the actual ID
      
      this.httpClient
        .post(`http://localhost:8080/upload/${id}`, uploadData, {
          observe: 'events',
          reportProgress: true,
          headers: {
            Authorization: `Basic ${btoa(`${'Dheeraj'}:${123456}`)}`,
          },
        })
        .subscribe(
          (event) => {
            if (event.type === HttpEventType.UploadProgress) {
              if (event.total) {
                const percentDone = Math.round((100 * event.loaded) / event.total);
                console.log(`File ${index + 1} is ${percentDone}% uploaded.`);
              } else {
                console.error('Total size of the file is unknown.');
              }
            } else if (event.type === HttpEventType.Response) {
              if (event.status === 200) {
                this.message = `File ${index + 1} uploaded successfully`;
              } else {
                this.message = `File ${index + 1} not uploaded successfully`;
              }
            }
          },
          (error) => {
            console.error(`Error uploading file ${index + 1}:`, error);
            this.message = `File ${index + 1} upload failed.`;
          }
        );
    } else {
      console.error('Selected file is null.');
      // Handle the null case if needed
    }
  }
  onSubmit() {
    alert('You have successfully uploaded all the data.');
  }

  getFile() {
    this.httpClient
      .get(`http://localhost:8080/get/${this.fileName}`, {
        responseType: 'arraybuffer',
      })
      .subscribe(
        (data) => {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            this.retrievedData = e.target.result;
          };
          reader.readAsDataURL(new Blob([data]));
        },
        (error) => {
          console.error('Error retrieving file:', error);
          // Handle error accordingly (e.g., display an error message)
        }
      );
  }
}
