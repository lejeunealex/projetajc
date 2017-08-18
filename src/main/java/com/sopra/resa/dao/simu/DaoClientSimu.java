package com.sopra.resa.dao.simu;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sopra.resa.dao.DaoClient;
import com.sopra.resa.model.Client;

//@Component
@Repository
public class DaoClientSimu implements DaoClient {

	@Override
	public Client findByKey(Long idClient) {
		// TODO Auto-generated method stub
		return new Client(idClient, "nomxx", "prenomyy");
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client insert(Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client update(Client c) {
		return c;
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Client> findClientByName(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

}
