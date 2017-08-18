package com.sopra.resa.dao;

import java.util.List;

import com.sopra.resa.model.Client;

public interface DaoClient extends DaoGeneric<Client, Long> {
	
	public List<Client> findClientByName(String nom);
}
