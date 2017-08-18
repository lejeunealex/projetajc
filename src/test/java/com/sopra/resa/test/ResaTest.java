package com.sopra.resa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sopra.resa.model.Client;
import com.sopra.resa.model.Login;
import com.sopra.resa.model.Resa;
import com.sopra.resa.service.ServiceClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configSpring.xml" })
public class ResaTest {

	@Autowired
	private ServiceClient serviceClient;

	@Test
	public void testFindClient() {
		Client c = serviceClient.findClient(1L);
		System.out.println(c.toString());
		assertTrue(c.getIdClient() == 1L);
	}

	@Test
	public void testFindClientByName() {
		Client c = serviceClient.findClient(1L);
		List<Client> liste = serviceClient.findClientByName(c.getNom());
		assertTrue(!liste.isEmpty());
	}

	@Test
	public void testUpdateClient() {
		Client c = serviceClient.findClient(1L);
		String n = c.getPrenom();
		c.setPrenom("Alain");
		Client cc = serviceClient.updateClient(c);
		assertTrue(cc.getPrenom() == "Alain");
		c.setPrenom(n);
		serviceClient.updateClient(c);
	}

	@Test
	public void testValidInsertClientWithLogin() {
		Client nouveauClient = new Client(null, "nomXx", "prenomYy");
		Login nouveauLogin = new Login(null, "usernameXx", "passwordYy");
		Client savedClientWithSavedLogin = serviceClient.insertClientWithLogin(nouveauClient, nouveauLogin);
		assertNotNull(savedClientWithSavedLogin);
		Long nouvelId = savedClientWithSavedLogin.getIdClient();
		Client client = serviceClient.findClient(nouvelId);
		assertEquals(client.getLogin().getUsername(), "usernameXx");
		assertEquals(client.getLogin().getPassword(), "passwordYy");
		// affichage temporaire ou exceptionnel (tp):
		System.out.println("nouveau client: " + client + " avec login: " + client.getLogin());
		// suppression à la fin pour pouvoir relancer le test plusieurs fois:
		serviceClient.supprimerClientWithLogin(nouvelId);
	}

	@Test
	public void testInValidInsertClientWithLogin() {
		Client nouveauClient = new Client(null, "nomXx", "prenomYy");
		Login nouveauLogin = new Login(null, "alex-therieur", "passwordYy"); // invalide car username dejà en base et
																				// devant être unique
		try {
			@SuppressWarnings("unused")
			Client savedClientWithSavedLogin = serviceClient.insertClientWithLogin(nouveauClient, nouveauLogin);
			fail("une exception aurait du remonter");
		} catch (Exception ex) {
			System.err.println("exception normale:" + ex.getMessage());
		}

		Long nouvelId = nouveauClient.getIdClient(); // savedClientWithSavedLogin.getIdClient();
		Client client = serviceClient.findClient(nouvelId);
		if (client != null) {
			// affichage temporaire ou exceptionnel (tp):
			System.out.println("nouveau client: " + client + " avec login: " + client.getLogin());
			// suppression à la fin pour pouvoir relancer le test plusieurs fois:
			serviceClient.supprimerClientWithLogin(nouvelId);
			fail("comportement non transactionnel (action partielle enregistree en base)");
		}
	}

	@Test
	public void testRechercherClientAvecResa() {
		Client c = serviceClient.rechercherClientAvecResa(1L);
		assertTrue(c.getIdClient() == 1L);
		System.out.println(c);
		for (Resa r : c.getListeResa()) {
			System.out.println("/t" + r);
		}
	}

}
