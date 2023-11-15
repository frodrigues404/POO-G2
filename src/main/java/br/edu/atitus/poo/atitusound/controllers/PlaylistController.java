package br.edu.atitus.poo.atitusound.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.poo.atitusound.dtos.PlaylistDTO;
import br.edu.atitus.poo.atitusound.entities.PlaylistEntity;
import br.edu.atitus.poo.atitusound.services.GenericService;
import br.edu.atitus.poo.atitusound.services.MusicService;
import br.edu.atitus.poo.atitusound.services.PlaylistService;

@RestController
@RequestMapping("playlists")
public class PlaylistController extends GenericController<PlaylistEntity, PlaylistDTO>{
	private final PlaylistService service;
	
	public PlaylistController(PlaylistService service) {
		super();
		this.service = service;
	}
	
	@Override
	protected PlaylistEntity convertDTO2Entity(PlaylistDTO dto) {
		PlaylistEntity playlist = new PlaylistEntity();
		playlist.setName(dto.getName());
		playlist.setPublic_share(dto.getPublic_share());
		return playlist;
	}

	@Override
	protected GenericService<PlaylistEntity> getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
}
