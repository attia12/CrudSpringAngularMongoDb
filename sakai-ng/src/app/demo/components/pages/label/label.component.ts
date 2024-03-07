import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ToastModule} from "primeng/toast";
import {TableModule} from "primeng/table";
import {LabelService} from "../../../service/label.service";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {ToggleButtonModule} from "primeng/togglebutton";
import {ToolbarModule} from "primeng/toolbar";
import {FileUploadModule} from "primeng/fileupload";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MessageService} from "primeng/api";

import {DocumentationService} from "../../../service/documentation.service";
import {InputTextareaModule} from "primeng/inputtextarea";
import {NotificationServiceService} from "../../../service/notification-service.service";
import { Router, RouterOutlet} from "@angular/router";


interface expandedRows {
    [key: string]: boolean;
}

@Component({
  selector: 'app-label',
  standalone: true,
    imports: [
        ToastModule,
        TableModule,
        ButtonModule,
        RippleModule,
        ToggleButtonModule,
        ToolbarModule,
        FileUploadModule,
        DialogModule,
        InputTextModule,
        NgIf,
        FormsModule,
        InputTextareaModule,
        NgForOf,
        RouterOutlet,
    ],
  templateUrl: './label.component.html',
  styleUrl: './label.component.scss'
})
export class LabelComponent implements OnInit{
    expandedRows: expandedRows = {};
    isExpanded: boolean = false;
    labels: any;
    selectedLabels: any | boolean;
    labelDialog: boolean = false;
    label: any;
    submitted: boolean= false;
    deleteLabelDialog: boolean;
    documentDialog: boolean = false;
    document: any = {};
    receivedMessages: string[] = [];

     isEditDocumentMode: boolean=false;
    input: any;


    constructor(private lableService:LabelService,private messageService: MessageService,private docService:DocumentationService,

                public notificationService:NotificationServiceService,private router: Router) {


    }

    expandAll() {
        if (!this.isExpanded) {
            this.labels.forEach(label => label && label._id ? this.expandedRows[label._id] = true : '');

        } else {
            this.expandedRows = {};
        }
        this.isExpanded = !this.isExpanded;

    }

    ngOnInit(): void {

        this.loadData();










    }

    private loadData() {
        this.lableService.getAll().subscribe((res)=>{
            console.log("data",res)
            this.labels=res;

        },error => {

            console.log(error);
        })

    }

    toggleExpansion(_id: any) {
        this.isExpanded=!this.isExpanded
    }

    deleteSelectedLabels() {
        this.deleteLabelDialog = true;

    }

    openNew() {
        this.label = {};
        this.submitted = false;
        this.labelDialog = true;

    }

    hideDialog() {
        this.submitted = false;
        this.labelDialog = false;

    }

    saveLabel() {

        this.submitted=true;
        if (!this.label.name || !this.label.name.match(/^[a-zA-Z\s]*$/)) {
            // Show error message for invalid name
            this.messageService.add({
                severity: 'error',
                summary: 'Validation Error',
                detail: 'Please enter a valid name containing only alphabetic characters.',
                life: 3000
            });
            return;
        }

        this.lableService.createLabel(this.label).subscribe((res)=>{
            this.labelDialog = false;
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Label added', life: 3000 });
            this.notificationService.sendMessage('New label added: ' + this.label.name);
            this.loadData();
        },error => {
           console.log(error);

        })

    }


    confirmDelete() {
        this.deleteLabelDialog=false;
        this.lableService.deleteLabel(this.label._id).subscribe(()=>{
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'label Deleted', life: 3000 });
          this.loadData();

        },error => {
            console.log(error)
        })


    }

    deleteLabel(label: any) {
        this.deleteLabelDialog = true;
        this.label = { ...label };
    }

    editLabel(label:any) {
        this.label = { ...label };
        this.labelDialog = true;

    }

    affecterNewDocument() {
        console.log("hello")
    }

    deleteDocumentation(doc: any,label:any) {

        this.docService.deleteDocumentation(label._id,doc._id).subscribe(()=>{
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Documentation Deleted', life: 3000 });
            this.loadData();

        },error => {
            console.log(error);
        })
    }

    addDocument(label: any) {
        console.log(label)
        this.documentDialog=true;
        this.label={...label}
        this.isEditDocumentMode = false;

    }

    hideDialogDoc() {
        this.submitted = false;
        this.documentDialog = false;

    }

    saveDoc() {
        this.submitted = true;

        // Validate country input against the Rest Countries API
        this.lableService.fetchCountryNames().subscribe(
            (countries: any[]) => {
                console.log(countries)
                const countryNames = countries;

                if (countryNames.includes(this.document.country)) {
                    // Country is valid, proceed with saving the documentation
                    if (this.isEditDocumentMode) {
                        console.log("this update data request")
                        console.log(this.document);
                        this.docService.updateDocumentation(this.document, this.document._id).subscribe(
                            () => {
                                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Documentation updated', life: 3000 });
                                this.loadData();
                            },
                            error => {
                                console.error('Error updating documentation:', error);
                            }
                        );
                    } else {
                        this.docService.affecterdocumentation(this.document, this.label._id).subscribe(() => {
                            this.messageService.add({
                                severity: 'success',
                                summary: 'Successful',
                                detail: 'Documentation Added',
                                life: 3000
                            });
                            this.loadData();
                        }, error => {
                            console.log(error);
                        });
                        this.hideDialogDoc();
                        this.isEditDocumentMode = !this.isEditDocumentMode;
                    }
                } else {
                    // Country is not valid, display an error message
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid country name', life: 3000 });
                }
            },
            error => {
                console.error('Error fetching country data:', error);
            }
        );

    }

    editDocumentation(doc: any) {
        this.isEditDocumentMode = true;
        this.document = { ...doc };

        this.documentDialog = true;



    }

    sendMessage() {
        if (this.input.trim() !== '') {
            this.notificationService.sendMessage(this.input.trim());
            this.input = ''; // Clear the input field after sending the message
        }

    }


    goToStat() {

        this.router.navigate(['/pages/label/statistique'])

    }


    viewDetails(id:string) {
        this.router.navigate(['/pages/label/detail', id]);

    }

    askChatGPT() {
        this.lableService.askChatGpt(this.document.country).subscribe((res:any)=>{
            console.log(res);
            this.document.description = res;

        },error => {
            console.log(error);
        })

    }
}
