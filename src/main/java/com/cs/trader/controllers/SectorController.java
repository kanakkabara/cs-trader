package com.cs.trader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cs.trader.domain.Sector;
import com.cs.trader.services.SectorService;

@RestController
public class SectorController {
	@Autowired
	SectorService sectorService;
	
	@GetMapping("/sectors")
	public List<Sector> findAllSectors(){
		return sectorService.findAllSectors();
	}
	
	@GetMapping("/sectors/{id}")
	public Sector findSectorByID(@PathVariable int id){
		return sectorService.findSectorByID(id);
	}
	
	@PostMapping("/sectors")
	public int addNewSector(@RequestBody Sector sector){
		return sectorService.addNewSector(sector);
	}
	
	@DeleteMapping("/sectors/{id}")
	public void deleteSector(@PathVariable int id){
		sectorService.deleteSector(id);
	}
	
	@PutMapping("/sectors/{id}")
	public Sector updateSector(@PathVariable int id, @RequestBody Sector sector){
		return sectorService.updateSector(id, sector);
	}
}
