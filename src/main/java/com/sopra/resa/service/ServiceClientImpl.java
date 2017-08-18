package com.sopra.resa.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sopra.resa.dao.DaoClient;
import com.sopra.resa.dao.DaoLogin;
import com.sopra.resa.model.Client;
import com.sopra.resa.model.Login;

@Service
@Transactional
public class ServiceClientImpl implements ServiceClient {

	private Logger logger = LoggerFactory.getLogger(ServiceClientImpl.class);

	@Autowired
	private DaoClient daoClient;

	@Autowired
	private DaoLogin daoLogin = null;

	public DaoLogin getDaoLogin() {
		return daoLogin;
	}

	public void setDaoLogin(DaoLogin daoLogin) {
		this.daoLogin = daoLogin;
	}

	public DaoClient getDaoClient() {
		return daoClient;
	}

	// @Autowired
	public void setDaoClient(DaoClient daoClient) {
		this.daoClient = daoClient;
	}

	@Override
	public Client findClient(Long idClient) {
		return daoClient.findByKey(idClient);
	}

	@Override
	public Client updateClient(Client c) {
		return daoClient.update(c);
	}

	@Override
	public List<Client> findClientByName(String nom) {
		return daoClient.findClientByName(nom);
	}

	@Override
	public Client insertClientWithLogin(Client cli, Login login) {
		Client savedClient = null;
		try {
			savedClient = daoClient.insert(cli);
			login.setIdClient(savedClient.getIdClient());
			Login savedLogin = daoLogin.insert(login);
			savedClient.setLogin(savedLogin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("échec insertClientWithLogin", e);
			throw new RuntimeException("échec insertClientWithLogin", e);
		}
		return savedClient;
	}

	@Override
	public void supprimerClientWithLogin(Long idClient) {
		Client client = daoClient.findByKey(idClient);
		Login login = daoLogin.findByKey(idClient);
		if (login != null)
			daoLogin.delete(login);// ordre selon contrainte du schema
		if (client != null)
			daoClient.delete(client);

	}

	@SuppressWarnings("rawtypes")
	public static void loadLazyCollection(Collection col) {
		col.size();
	}

	@Override
	public Client rechercherClientAvecResa(Long id) {
		Client client = daoClient.findByKey(id);
		loadLazyCollection(client.getListeResa());
		// autre solution : namedquery avec un fetch (voir jpa/findBySalle)
		return client;
	}

}
