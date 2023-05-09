import { Component,OnInit } from '@angular/core';
import { Ville } from 'src/app/models/ville';
import { VilleService } from 'src/app/services/ville.service';

@Component({
  selector: 'app-visualiser',
  templateUrl: './visualiser.component.html',
  styleUrls: ['./visualiser.component.css']
})
export class VisualiserComponent implements OnInit {
  villes!: Ville[];

  constructor(private villeService: VilleService) { }

  ngOnInit() {
    this.villeService.getAllVilles().subscribe(
      data => this.villes = data,
      error => console.log(error)
    );
  }


}
