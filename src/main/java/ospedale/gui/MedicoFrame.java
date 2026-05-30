package ospedale.gui;

import ospedale.clinica.Prestazione;
import ospedale.clinica.Ricovero;
import ospedale.controller.Controller;
import ospedale.personale.Turno;
import ospedale.utenti.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

    public class MedicoFrame extends JFrame {

        private static final DateTimeFormatter FMT_DT =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        private static final DateTimeFormatter FMT_D =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        private final Controller controller;
        private final Medico medico;

        // Tab giornaliera
        private JSpinner spinnerGiorno;
        private DefaultTableModel modelGiorno;
        private JTable tabellaGiorno;
        private JTextArea areaEsito;

        // Tab settimanale
        private JSpinner spinnerLunedi;
        private DefaultTableModel modelSettimana;

        // Tab nuova prestazione
        private JTextField campoInizioP, campoFineP;
        private JComboBox<RicoveroWrapper> comboRicoveri;
        private JLabel labelEsitoOp;

        public MedicoFrame(Controller controller, Medico medico) {
            this.controller = controller;
            this.medico     = medico;
            costruisciUI();
        }

        private void costruisciUI() {
            setTitle("Dashboard Medico — " + medico.getLogin());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(750, 500));

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Agenda Giornaliera", costruisciPannelloGiorno());
            tabs.addTab("Agenda Settimanale", costruisciPannelloSettimana());
            tabs.addTab("Nuova Prestazione",  costruisciPannelloNuovaPrestazione());

            JButton btnLogout = new JButton("Logout");
            btnLogout.addActionListener(e -> {
                controller.logout();
                new LoginFrame(controller).setVisible(true);
                dispose();
            });

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.add(btnLogout);

            add(tabs, BorderLayout.CENTER);
            add(bottom, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            aggiornaAgendaGiorno();
        }

        // Pannello agenda giornaliera
        private JPanel costruisciPannelloGiorno() {
            JPanel p = new JPanel(new BorderLayout(8, 8));
            p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Selezione data
            JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            spinnerGiorno = new JSpinner(
                    new SpinnerDateModel(java.util.Date.from(
                            LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)),
                            null, null, java.util.Calendar.DAY_OF_MONTH));
            spinnerGiorno.setEditor(new JSpinner.DateEditor(spinnerGiorno, "dd/MM/yyyy"));
            spinnerGiorno.setPreferredSize(new Dimension(120, 28));
            JButton btnCerca = new JButton("Mostra");
            btnCerca.addActionListener(e -> aggiornaAgendaGiorno());
            topBar.add(new JLabel("Data:"));
            topBar.add(spinnerGiorno);
            topBar.add(btnCerca);
            p.add(topBar, BorderLayout.NORTH);

            // Tabella prestazioni
            modelGiorno = new DefaultTableModel(
                    new String[]{"Inizio", "Fine", "Ricovero (paziente)", "Esito"}, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            tabellaGiorno = new JTable(modelGiorno);
            tabellaGiorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tabellaGiorno.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) caricaEsitoSelezionato();
            });
            p.add(new JScrollPane(tabellaGiorno), BorderLayout.CENTER);

            // Pannello modifica esito
            JPanel sud = new JPanel(new BorderLayout(5, 5));
            sud.setBorder(BorderFactory.createTitledBorder("Modifica esito prestazione selezionata"));
            areaEsito = new JTextArea(4, 40);
            areaEsito.setLineWrap(true);
            JButton btnSalvaEsito = new JButton("Salva esito");
            btnSalvaEsito.addActionListener(e -> salvaEsito());
            sud.add(new JScrollPane(areaEsito), BorderLayout.CENTER);
            sud.add(btnSalvaEsito, BorderLayout.EAST);
            p.add(sud, BorderLayout.SOUTH);

            return p;
        }

        private void aggiornaAgendaGiorno() {
            modelGiorno.setRowCount(0);
            java.util.Date d = (java.util.Date) spinnerGiorno.getValue();
            LocalDate data = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            List<Prestazione> lista = controller.getPrestazioniGiorno(medico, data);
            for (Prestazione p : lista) {
                String fine    = p.getFine() != null ? p.getFine().format(FMT_DT) : "—";
                String pazNome = p.getRicovero() != null
                        ? p.getRicovero().getPaziente().getNome() + " "
                        + p.getRicovero().getPaziente().getCognome() : "—";
                modelGiorno.addRow(new Object[]{
                        p.getInizio().format(FMT_DT), fine, pazNome,
                        p.getEsito() != null ? p.getEsito() : ""});
            }
            areaEsito.setText("");
        }

        private void caricaEsitoSelezionato() {
            int riga = tabellaGiorno.getSelectedRow();
            if (riga < 0) return;
            java.util.Date d = (java.util.Date) spinnerGiorno.getValue();
            LocalDate data = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            List<Prestazione> lista = controller.getPrestazioniGiorno(medico, data);
            if (riga < lista.size()) {
                String esito = lista.get(riga).getEsito();
                areaEsito.setText(esito != null ? esito : "");
            }
        }

        private void salvaEsito() {
            int riga = tabellaGiorno.getSelectedRow();
            if (riga < 0) { JOptionPane.showMessageDialog(this, "Seleziona prima una prestazione."); return; }
            java.util.Date d = (java.util.Date) spinnerGiorno.getValue();
            LocalDate data = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            List<Prestazione> lista = controller.getPrestazioniGiorno(medico, data);
            if (riga < lista.size()) {
                controller.aggiornaEsito(lista.get(riga), areaEsito.getText().trim());
                aggiornaAgendaGiorno();
                JOptionPane.showMessageDialog(this, "Esito salvato.");
            }
        }

        // Pannello agenda settimanale
        private JPanel costruisciPannelloSettimana() {
            JPanel p = new JPanel(new BorderLayout(8, 8));
            p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            spinnerLunedi = new JSpinner(
                    new SpinnerDateModel(java.util.Date.from(
                            lunediCorrente().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)),
                            null, null, java.util.Calendar.DAY_OF_MONTH));
            spinnerLunedi.setEditor(new JSpinner.DateEditor(spinnerLunedi, "dd/MM/yyyy"));
            spinnerLunedi.setPreferredSize(new Dimension(120, 28));
            JButton btnMostra = new JButton("Mostra settimana");
            btnMostra.addActionListener(e -> aggiornaSettimana());
            topBar.add(new JLabel("Lunedì:"));
            topBar.add(spinnerLunedi);
            topBar.add(btnMostra);
            p.add(topBar, BorderLayout.NORTH);

            modelSettimana = new DefaultTableModel(
                    new String[]{"Giorno", "Inizio turno", "Fine turno"}, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            p.add(new JScrollPane(new JTable(modelSettimana)), BorderLayout.CENTER);
            aggiornaSettimana();
            return p;
        }

        private LocalDate lunediCorrente() {
            LocalDate oggi = LocalDate.now();
            return oggi.minusDays(oggi.getDayOfWeek().getValue() - 1);
        }

        private void aggiornaSettimana() {
            modelSettimana.setRowCount(0);
            java.util.Date d = (java.util.Date) spinnerLunedi.getValue();
            LocalDate lunedi = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            List<Turno> turni = controller.getTurniSettimana(medico, lunedi);
            for (Turno t : turni) {
                modelSettimana.addRow(new Object[]{
                        t.getInizio().toLocalDate().format(FMT_D),
                        t.getInizio().format(FMT_DT),
                        t.getFine().format(FMT_DT)});
            }
        }

        // Pannello nuova prestazione
        private JPanel costruisciPannelloNuovaPrestazione() {
            JPanel p = new JPanel(new GridBagLayout());
            p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            GridBagConstraints g = new GridBagConstraints();
            g.insets  = new Insets(6, 6, 6, 6);
            g.fill    = GridBagConstraints.HORIZONTAL;
            g.anchor  = GridBagConstraints.WEST;

            comboRicoveri = new JComboBox<>();
            for (Ricovero r : controller.getRicoveri()) comboRicoveri.addItem(new RicoveroWrapper(r));

            campoInizioP = new JTextField("dd/MM/yyyy HH:mm", 16);
            campoFineP   = new JTextField("dd/MM/yyyy HH:mm", 16);

            g.gridy = 0; g.gridx = 0; p.add(new JLabel("Ricovero:"), g);
            g.gridx = 1; p.add(comboRicoveri, g);

            g.gridy = 1; g.gridx = 0; p.add(new JLabel("Inizio (dd/MM/yyyy HH:mm):"), g);
            g.gridx = 1; p.add(campoInizioP, g);

            g.gridy = 2; g.gridx = 0; p.add(new JLabel("Fine (dd/MM/yyyy HH:mm):"), g);
            g.gridx = 1; p.add(campoFineP, g);

            JButton btnRegistra = new JButton("Registra prestazione");
            btnRegistra.addActionListener(e -> registraPrestazione());
            g.gridy = 3; g.gridx = 0; g.gridwidth = 2;
            p.add(btnRegistra, g);

            labelEsitoOp = new JLabel(" ");
            labelEsitoOp.setHorizontalAlignment(SwingConstants.CENTER);
            g.gridy = 4;
            p.add(labelEsitoOp, g);

            return p;
        }

        private void registraPrestazione() {
            try {
                LocalDateTime inizio = LocalDateTime.parse(campoInizioP.getText().trim(), FMT_DT);
                LocalDateTime fine   = LocalDateTime.parse(campoFineP.getText().trim(), FMT_DT);
                RicoveroWrapper rw   = (RicoveroWrapper) comboRicoveri.getSelectedItem();
                if (rw == null) { labelEsitoOp.setText("Seleziona un ricovero."); return; }

                String errore = controller.registraPrestazione(medico, rw.ricovero, inizio, fine);
                if (errore != null) {
                    labelEsitoOp.setForeground(Color.RED);
                    labelEsitoOp.setText(errore);
                } else {
                    labelEsitoOp.setForeground(new Color(0, 128, 0));
                    labelEsitoOp.setText("Prestazione registrata.");
                    aggiornaAgendaGiorno();
                }
            } catch (DateTimeParseException ex) {
                labelEsitoOp.setForeground(Color.RED);
                labelEsitoOp.setText("Formato data non valido (usa dd/MM/yyyy HH:mm).");
            }
        }

        // Wrapper per JComboBox
        private static class RicoveroWrapper {
            final Ricovero ricovero;
            RicoveroWrapper(Ricovero r) { this.ricovero = r; }
            public String toString() {
                return r().getPaziente().getNome() + " " + r().getPaziente().getCognome()
                        + " — " + r().getLetto().getCodiceIdentificativo();
            }
            private Ricovero r() { return ricovero; }
        }
    }

