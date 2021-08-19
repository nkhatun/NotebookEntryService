import { Component, OnInit } from '@angular/core';
import { EntryService } from 'src/app/entry.service';
import ApiEndpoints from 'src/app/api-end-point';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private entryService: EntryService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  panelVisible: boolean = false;
  entries: any = [];
  createEntry() {
    this.panelVisible = true;
    var obj = { "noteType": "TEXT", "noteContent": "" };
    this.entries.push(obj);

  }
  saveEntry() {
    if(this.entries.length != 0 && this.validNoteContent()){
 var requestObject = { "projectName": "Assignment Task", "noteEntryDtoList": this.entries };
    this.entryService.postData(requestObject, `${ApiEndpoints.createEntryEndUrl}`).subscribe((data) => {
      this.openSnackBar(data.message, "OK");
      this.entries = [];this.panelVisible = false;
    });
    }
    else{
       this.openSnackBar("Please enter note content before saving", "OK");
    }
   
  }
  validNoteContent():boolean{
    var emptyContent = true;
    this.entries.forEach( (element:any) => {
      if(!element.noteContent){emptyContent = false;return;}
     });
    return emptyContent;
  }
  onTextChange(value: string, index: number) {
    this.entries[index].noteContent = value;
  }
  deleteEntry(index:number){
      this.entries.splice(index, 1);  
      if(this.entries.length == 0){this.panelVisible = false;}  
  }

  freqResponse: any = [];
  searchFreqOccur(searchString: string) {
    if(searchString && searchString.trim().length != 0){
    let requestUrl = `${ApiEndpoints.fetchFreqEndUrl}` + "searchword=" + searchString;
    this.entryService.getData(requestUrl).subscribe((data) => {
      this.freqResponse = [];
      this.similarWordResponse = [];
      var resultData = data.data;
      if (data.status == 'success') {
        if (resultData != null && resultData.dataItems != null && resultData.dataItems.length != 0) {
          this.freqResponse = data.data.dataItems[0];
        }
        else {
          this.freqResponse = [];
          this.openSnackBar(data.message, "OK");
        }
      }
      else {
        this.openSnackBar(data.message, "OK");
      }
    });}
    else{
          this.similarWordResponse =[];
          this.freqResponse = [];
          this.openSnackBar("Please enter valid word to search", "OK");
    }
  }

  similarWordResponse: any = [];
  searchSimilarWords(searchString: string) {
    if(searchString && searchString.trim().length != 0){
    let requestUrl = `${ApiEndpoints.fetchSimilarEndUrl}` + "searchword=" + searchString;
    this.entryService.getData(requestUrl).subscribe((data) => {
      this.similarWordResponse = [];
      this.freqResponse = [];
      var resultData = data.data;
      if (data.status == 'success') {
        if (resultData != null && resultData.dataItems != null && resultData.dataItems.length != 0) {
          this.similarWordResponse = data.data.dataItems[0];
        }
        else {
          this.similarWordResponse = [];
          this.openSnackBar(data.message, "OK");
        }
      }
      else {
        this.openSnackBar(data.message, "OK");
      }
    });}
     else{
          this.similarWordResponse =[];
          this.freqResponse = [];
          this.openSnackBar("Please enter valid word to search", "OK");
    }

  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000
    });
  }
}
