package ospedale.controller;


import ospedale.clinica.Paziente;
import ospedale.clinica.Prestazione;
import ospedale.clinica.Ricovero;
import ospedale.personale.Assenza;
import ospedale.personale.Turno;
import ospedale.struttura.Letto;
import ospedale.struttura.Reparto;
import ospedale.struttura.Stanza;
import ospedale.utenti.Amministratore;
import ospedale.utenti.Medico;
import ospedale.utenti.Utente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

	//Controller che fa da intermediario tra la GUI e il Model

	public class Controller {

		private List<Utente>   utenti   = new ArrayList<>();
		private List<Reparto>  reparti  = new ArrayList<>();
		private List<Paziente> pazienti = new ArrayList<>();
		private List<Ricovero> ricoveri = new ArrayList<>();
		private Utente utenteCorrente;

		public Controller() {
			popolaDatiEsempio();
		}

		private void popolaDatiEsempio() {
			Amministratore admin = new Amministratore();
			admin.setLogin("admin");
			admin.setPassword("admin");
			utenti.add(admin);

			Medico m1 = new Medico("mario.rossi", "pass1");
			Medico m2 = new Medico("lucia.bianchi", "pass2");
			Medico m3 = new Medico("carlo.verdi", "pass3");
			utenti.add(m1);
			utenti.add(m2);
			utenti.add(m3);

			Reparto cardiologia = new Reparto("Cardiologia");
			Stanza s1 = new Stanza(101);
			Letto l1 = new Letto("L-101A");
			Letto l2 = new Letto("L-101B");
			s1.addLetto(l1);
			s1.addLetto(l2);
			Stanza s2 = new Stanza(102);
			Letto l3 = new Letto("L-102A");
			s2.addLetto(l3);
			cardiologia.addStanza(s1);
			cardiologia.addStanza(s2);
			cardiologia.addMedico(m1);
			cardiologia.addMedico(m2);
			cardiologia.addMedico(m3);
			reparti.add(cardiologia);

			m1.addTurno(new Turno(
					LocalDateTime.now().withHour(8).withMinute(0).withSecond(0),
					LocalDateTime.now().withHour(14).withMinute(0).withSecond(0)));

			Paziente p1 = new Paziente("Marco", "Neri", "NRIMRC80A01F839X");
			pazienti.add(p1);
			Ricovero r1 = new Ricovero(LocalDateTime.now().minusDays(2), p1, l1);
			r1.setDimissionePrevista(LocalDateTime.now().plusDays(1));
			ricoveri.add(r1);

			Prestazione prest = new Prestazione(
					LocalDateTime.now().withHour(9).withMinute(0).withSecond(0), r1);
			prest.setFine(LocalDateTime.now().withHour(10).withMinute(0).withSecond(0));
			prest.setEsito("Parametri nella norma.");
			m1.addPrestazioni(prest);
		}

		// Login
		public Utente login(String login, String password) {
			for (Utente u : utenti) {
				if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
					utenteCorrente = u;
					return u;
				}
			}
			return null;
		}

		public Utente getUtenteCorrente() { return utenteCorrente; }
		public void logout() { utenteCorrente = null; }

		//Pazienti
		public List<Paziente> getPazienti() { return new ArrayList<>(pazienti); }

		public void aggiungiPaziente(String nome, String cognome, String cf) {
			pazienti.add(new Paziente(nome, cognome, cf));
		}

		// Reparti / Letti
		public List<Reparto> getReparti() { return new ArrayList<>(reparti); }

		public List<Letto> getLettiReparto(Reparto reparto) {
			List<Letto> tutti = new ArrayList<>();
			for (Stanza s : reparto.getStanze()) tutti.addAll(s.getLetti());
			return tutti;
		}

		public boolean isLettoOccupato(Letto letto) {
			LocalDateTime ora = LocalDateTime.now();
			for (Ricovero r : ricoveri) {
				if (r.getLetto().equals(letto)) {
					LocalDateTime fine = r.getDimissioneEffettiva() != null
							? r.getDimissioneEffettiva() : r.getDimissionePrevista();
					if (fine == null || (!ora.isBefore(r.getInizio()) && !ora.isAfter(fine)))
						return true;
				}
			}
			return false;
		}

		public List<Letto> getLettiDisponibili(Reparto reparto) {
			List<Letto> disp = new ArrayList<>();
			for (Letto l : getLettiReparto(reparto))
				if (!isLettoOccupato(l)) disp.add(l);
			return disp;
		}

		// Ricoveri
		public List<Ricovero> getRicoveri() { return new ArrayList<>(ricoveri); }

		//Ritorna messaggio di errore, oppure null se l'inserimento va a buon fine
		public String registraRicovero(Paziente paziente, Letto letto,
									   LocalDateTime inizio, LocalDateTime finePrevista) {
			for (Ricovero r : ricoveri) {
				if (r.getLetto().equals(letto)) {
					LocalDateTime fineEsist;
					if (r.getDimissioneEffettiva() != null){
						fineEsist = r.getDimissioneEffettiva();
					}else{
						fineEsist = r.getDimissionePrevista();
					}
					if (fineEsist == null)
						return "Letto " + letto.getCodiceIdentificativo() + " già occupato.";
					if (inizio.isBefore(fineEsist) && finePrevista.isAfter(r.getInizio()))
						return "Letto " + letto.getCodiceIdentificativo()
								+ " già occupato in quell'intervallo.";
				}
			}
			Ricovero nuovo = new Ricovero(inizio, paziente, letto);
			nuovo.setDimissionePrevista(finePrevista);
			ricoveri.add(nuovo);
			return null;
		}

		public List<Paziente> getPazientiInScadenza(LocalDate data) {
			List<Paziente> lista = new ArrayList<>();
			for (Ricovero r : ricoveri) {
				if (r.getDimissionePrevista() != null
						&& r.getDimissionePrevista().toLocalDate().equals(data)
						&& r.getDimissioneEffettiva() == null)
					lista.add(r.getPaziente());
			}
			return lista;
		}
		// Ritorna messaggio di errore, oppure null se è ok
		public String registraPrestazione(Medico medico, Ricovero ricovero,
										  LocalDateTime inizio, LocalDateTime fine) {
			boolean inTurno = false;
			for (Turno t : medico.getTurni()) {
				if (!inizio.isBefore(t.getInizio()) && !fine.isAfter(t.getFine())) {
					inTurno = true; break;
				}
			}
			if (!inTurno) return "La prestazione non rientra in nessun turno del medico.";

			for (Prestazione p : medico.getPrestazioni()) {
				if (p.getFine() != null && inizio.isBefore(p.getFine()) && fine.isAfter(p.getInizio()))
					return "Il medico ha già una prestazione in quell'orario.";
			}
			Prestazione nuova = new Prestazione(inizio, ricovero);
			nuova.setFine(fine);
			medico.addPrestazioni(nuova);
			return null;
		}

		public void aggiornaEsito(Prestazione prestazione, String nuovoEsito) {
			prestazione.setEsito(nuovoEsito);
		}

		public List<Prestazione> getPrestazioniGiorno(Medico medico, LocalDate data) {
			List<Prestazione> lista = new ArrayList<>();
			for (Prestazione p : medico.getPrestazioni())
				if (p.getInizio().toLocalDate().equals(data)) lista.add(p);
			return lista;
		}

		public List<Turno> getTurniSettimana(Medico medico, LocalDate lunedi) {
			LocalDate domenica = lunedi.plusDays(6);
			List<Turno> lista = new ArrayList<>();
			for (Turno t : medico.getTurni()) {
				LocalDate d = t.getInizio().toLocalDate();
				if (!d.isBefore(lunedi) && !d.isAfter(domenica)) lista.add(t);
			}
			return lista;
		}

		// Assenze & Sostituti
		public List<Medico> registraAssenzaESuggerisciSostituti(
				Medico medico, LocalDateTime inizio, LocalDateTime fine) {
			medico.addAssenza(new Assenza(inizio, fine));
			Reparto repartoMedico = null;
			for (Reparto r : reparti)
				if (r.getMedici().contains(medico)) { repartoMedico = r; break; }
			if (repartoMedico == null) return new ArrayList<>();

			List<Medico> sostituti = new ArrayList<>();
			for (Medico c : repartoMedico.getMedici()) {
				if (!c.equals(medico) && !haConflitti(c, inizio, fine)) sostituti.add(c);
			}
			return sostituti;
		}

		private boolean haConflitti(Medico medico, LocalDateTime inizio, LocalDateTime fine) {
			for (Turno t : medico.getTurni())
				if (inizio.isBefore(t.getFine()) && fine.isAfter(t.getInizio())) return true;
			for (Prestazione p : medico.getPrestazioni())
				if (p.getFine() != null && inizio.isBefore(p.getFine()) && fine.isAfter(p.getInizio()))
					return true;
			return false;
		}

		public List<Medico> getMedici() {
			List<Medico> lista = new ArrayList<>();
			for (Utente u : utenti)
				if (u instanceof Medico) lista.add((Medico) u);
			return lista;
		}

		public List<Turno> getTurniScoperti(Medico medico, LocalDateTime inizioAss, LocalDateTime fineAss) {
			List<Turno> scoperti = new ArrayList<>();
			for (Turno t : medico.getTurni())
				if (inizioAss.isBefore(t.getFine()) && fineAss.isAfter(t.getInizio())) scoperti.add(t);
			return scoperti;
		}

		public List<Prestazione> getPrestazioniScoperte(Medico medico, LocalDateTime inizioAss, LocalDateTime fineAss) {
			List<Prestazione> scoperte = new ArrayList<>();
			for (Prestazione p : medico.getPrestazioni())
				if (p.getFine() != null && inizioAss.isBefore(p.getFine()) && fineAss.isAfter(p.getInizio()))
					scoperte.add(p);
			return scoperte;
		}
	}

