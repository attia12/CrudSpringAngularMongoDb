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
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MessageService} from "primeng/api";

import {DocumentationService} from "../../../service/documentation.service";
import {InputTextareaModule} from "primeng/inputtextarea";

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

     isEditDocumentMode: boolean=false;
    constructor(private lableService:LabelService,private messageService: MessageService,private docService:DocumentationService) {
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
        this.lableService.createLabel(this.label).subscribe((res)=>{
            this.labelDialog = false;
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Label added', life: 3000 });
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
        this.submitted=true;
        if (this.isEditDocumentMode) {

            console.log("this update data reequest")
            console.log(this.document);
            this.docService.updateDocumentation(this.document,this.document._id).subscribe(
                () => {
                    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Documentation updated', life: 3000 });
                    this.loadData();
                },
                error => {
                    console.error('Error updating documentation:', error);
                }
            );
        } else {


            this.docService.affecterdocumentation(this.document, this.label._id).subscribe((res) => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Successful',
                    detail: 'Documentation Added',
                    life: 3000
                });
                this.loadData();

            }, error => {

                console.log(error);
            })
            this.hideDialogDoc();
        }

    }

    editDocumentation(doc: any) {
        this.isEditDocumentMode = true;
        this.document = { ...doc };

        this.documentDialog = true;



    }
}
