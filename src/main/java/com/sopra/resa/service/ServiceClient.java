package com.sopra.resa.service;

import java.util.List;

import com.sopra.resa.model.Client;
import com.sopra.resa.model.Login;

public interface ServiceClient {
	public Client findClient(Long idClient);
	public List<Client> findClientByName(String nom);
	public Client updateClient(Client c);
	public Client insertClientWithLogin(Client c, Login l);
	public void supprimerClientWithLogin(Long idClient);
	public Client rechercherClientAvecResa(Long id);
}
