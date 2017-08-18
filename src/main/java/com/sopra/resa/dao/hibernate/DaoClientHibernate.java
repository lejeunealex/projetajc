package com.sopra.resa.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sopra.resa.dao.DaoClient;
import com.sopra.resa.model.Client;

@Repository
@Transactional
public class DaoClientHibernate extends DaoGenericImpl<Client, Long> implements DaoClient {

	@Override
	public List<Client> findClientByName(String nom) {
		return em.createNamedQuery("Client.findByName", Client.class).setParameter("nom", nom).getResultList();

		// return em.createQuery("SELECT c FROM Client c WHERE c.nom = :nom", Client.class).setParameter("nom", nom)
		// .getResultList();
	}

}
