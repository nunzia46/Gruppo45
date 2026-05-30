package ospedale.gui;

import ospedale.clinica.Paziente;
import ospedale.clinica.Prestazione;
import ospedale.controller.Controller;
import ospedale.personale.Turno;
import ospedale.struttura.Letto;
import ospedale.struttura.Reparto;
import ospedale.utenti.Medico;

import javax.swing.*;
        import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
        import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdminFrame extends JFrame {

    private static final DateTimeFormatter FMT_DT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FMT_D =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Controller controller;

    // Tab pazienti
    private DefaultTableModel modelPazienti;

    // Tab ricoveri
    private JComboBox<PazienteWrapper> comboPazienti;
    private JComboBox<LettoWrapper>    comboLetti;
    private JComboBox<RepartoWrapper>  comboRepartiRicovero;
    private JTextField campoInizioR, campoFineR;
    private JLabel labelEsitoR;

    // Tab letti
    private JComboBox<RepartoWrapper> comboRepartiLetti;
    private DefaultTableModel modelLetti;

    // Tab dimissioni
    private JSpinner spinnerDimissioni;
    private DefaultTableModel modelDimissioni;

    // Tab assenze
    private JComboBox<MedicoWrapper> comboMediciAssenza;
    private JTextField campoInizioA, campoFineA;
    private JTextArea areaRisultatiAssenza;

    public AdminFrame(Controller controller) {
        this.controller = controller;
        costruisciUI();
    }

    private void costruisciUI() {
        setTitle("Dashboard Amministratore");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(820, 560));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pazienti",   costruisciPannelloPazienti());
        tabs.addTab("Ricoveri",   costruisciPannelloRicoveri());
        tabs.addTab("Letti",      costruisciPannelloLetti());
        tabs.addTab("Dimissioni", costruisciPannelloDimissioni());
        tabs.addTab("Assenze",    costruisciPannelloAssenze());

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
    }

    // Tab pazienti
    private JPanel costruisciPannelloPazienti() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelPazienti = new DefaultTableModel(
                new String[]{"Nome", "Cognome", "Codice Fiscale"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        aggiornaTabellaP();
        p.add(new JScrollPane(new JTable(modelPazienti)), BorderLayout.CENTER);

        // Aggiunta
        JPanel form = new JPanel(new GridLayout(1, 7, 5, 0));
        form.setBorder(BorderFactory.createTitledBorder("Aggiungi paziente"));
        JTextField fNome = new JTextField(); JTextField fCognome = new JTextField();
        JTextField fCF   = new JTextField();
        form.add(new JLabel("Nome:")); form.add(fNome);
        form.add(new JLabel("Cognome:")); form.add(fCognome);
        form.add(new JLabel("CF:")); form.add(fCF);
        JButton btnAgg = new JButton("Aggiungi");
        btnAgg.addActionListener(e -> {
            if (fNome.getText().trim().isEmpty() || fCognome.getText().trim().isEmpty()) return;
            controller.aggiungiPaziente(fNome.getText().trim(),
                    fCognome.getText().trim(), fCF.getText().trim());
            aggiornaTabellaP();
            aggiornaComboP();
            fNome.setText(""); fCognome.setText(""); fCF.setText("");
        });
        form.add(btnAgg);
        p.add(form, BorderLayout.SOUTH);
        return p;
    }

    private void aggiornaTabellaP() {
        if (modelPazienti == null) return;
        modelPazienti.setRowCount(0);
        for (Paziente paz : controller.getPazienti())
            modelPazienti.addRow(new Object[]{paz.getNome(), paz.getCognome(), paz.getCodiceFiscale()});
    }

    private void aggiornaComboP() {
        if (comboPazienti == null) return;
        comboPazienti.removeAllItems();
        for (Paziente paz : controller.getPazienti())
            comboPazienti.addItem(new PazienteWrapper(paz));
    }

    // Tab ricoveri
    private JPanel costruisciPannelloRicoveri() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 7, 7, 7);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboPazienti       = new JComboBox<>();
        comboRepartiRicovero = new JComboBox<>();
        comboLetti          = new JComboBox<>();
        campoInizioR        = new JTextField("dd/MM/yyyy HH:mm", 16);
        campoFineR          = new JTextField("dd/MM/yyyy HH:mm", 16);
        labelEsitoR         = new JLabel(" ");

        for (Paziente paz : controller.getPazienti())
            comboPazienti.addItem(new PazienteWrapper(paz));
        for (Reparto r : controller.getReparti())
            comboRepartiRicovero.addItem(new RepartoWrapper(r));

        // Aggiorna letti al cambio reparto
        comboRepartiRicovero.addActionListener(e -> aggiornaComboLetti());
        aggiornaComboLetti();

        int riga = 0;
        addCoppia(p, g, riga++, "Paziente:", comboPazienti);
        addCoppia(p, g, riga++, "Reparto:",  comboRepartiRicovero);
        addCoppia(p, g, riga++, "Letto:",    comboLetti);
        addCoppia(p, g, riga++, "Inizio (dd/MM/yyyy HH:mm):", campoInizioR);
        addCoppia(p, g, riga++, "Dimissione prevista:",        campoFineR);

        JButton btnReg = new JButton("Registra ricovero");
        btnReg.addActionListener(e -> eseguiRegistraRicovero());
        g.gridy = riga++; g.gridx = 0; g.gridwidth = 2; p.add(btnReg, g);

        labelEsitoR.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = riga; p.add(labelEsitoR, g);
        return p;
    }

    private void aggiornaComboLetti() {
        if (comboLetti == null || comboRepartiRicovero == null) return;
        comboLetti.removeAllItems();
        RepartoWrapper rw = (RepartoWrapper) comboRepartiRicovero.getSelectedItem();
        if (rw == null) return;
        for (Letto l : controller.getLettiReparto(rw.reparto))
            comboLetti.addItem(new LettoWrapper(l, controller.isLettoOccupato(l)));
    }

    private void eseguiRegistraRicovero() {
        try {
            LocalDateTime inizio = LocalDateTime.parse(campoInizioR.getText().trim(), FMT_DT);
            LocalDateTime fine   = LocalDateTime.parse(campoFineR.getText().trim(),   FMT_DT);
            PazienteWrapper pw = (PazienteWrapper) comboPazienti.getSelectedItem();
            LettoWrapper    lw = (LettoWrapper)    comboLetti.getSelectedItem();
            if (pw == null || lw == null) return;

            String err = controller.registraRicovero(pw.paziente, lw.letto, inizio, fine);
            if (err != null) {
                labelEsitoR.setForeground(Color.RED); labelEsitoR.setText(err);
            } else {
                labelEsitoR.setForeground(new Color(0, 128, 0));
                labelEsitoR.setText("Ricovero registrato.");
                aggiornaComboLetti();
            }
        } catch (DateTimeParseException ex) {
            labelEsitoR.setForeground(Color.RED);
            labelEsitoR.setText("Formato data non valido (dd/MM/yyyy HH:mm).");
        }
    }

    // Tab letti
    private JPanel costruisciPannelloLetti() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboRepartiLetti = new JComboBox<>();
        for (Reparto r : controller.getReparti())
            comboRepartiLetti.addItem(new RepartoWrapper(r));

        JButton btnMostra = new JButton("Mostra letti");
        btnMostra.addActionListener(e -> aggiornaTabLetti());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Reparto:"));
        top.add(comboRepartiLetti);
        top.add(btnMostra);
        p.add(top, BorderLayout.NORTH);

        modelLetti = new DefaultTableModel(
                new String[]{"Codice letto", "Stato"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabella = new JTable(modelLetti);

        // Celle occupate in rosso
        tabella.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String stato = (String) modelLetti.getValueAt(row, 1);
                if ("Occupato".equals(stato)) c.setForeground(Color.RED);
                else c.setForeground(new Color(0, 128, 0));
                return c;
            }
        });

        p.add(new JScrollPane(tabella), BorderLayout.CENTER);

        JLabel legenda = new JLabel("  ■ Rosso = occupato   ■ Verde = disponibile");
        legenda.setFont(legenda.getFont().deriveFont(Font.ITALIC, 11f));
        p.add(legenda, BorderLayout.SOUTH);

        aggiornaTabLetti();
        return p;
    }

    private void aggiornaTabLetti() {
        if (modelLetti == null) return;
        modelLetti.setRowCount(0);
        RepartoWrapper rw = (RepartoWrapper) comboRepartiLetti.getSelectedItem();
        if (rw == null) return;
        for (Letto l : controller.getLettiReparto(rw.reparto)) {
            boolean occ = controller.isLettoOccupato(l);
            modelLetti.addRow(new Object[]{
                    l.getCodiceIdentificativo(), occ ? "Occupato" : "Disponibile"});
        }
    }

    // Tab dimissioni
    private JPanel costruisciPannelloDimissioni() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        spinnerDimissioni = new JSpinner(
                new SpinnerDateModel(java.util.Date.from(
                        LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)),
                        null, null, java.util.Calendar.DAY_OF_MONTH));
        spinnerDimissioni.setEditor(new JSpinner.DateEditor(spinnerDimissioni, "dd/MM/yyyy"));
        spinnerDimissioni.setPreferredSize(new Dimension(120, 28));

        JButton btnOggi  = new JButton("Oggi");
        JButton btnCerca = new JButton("Cerca");
        btnOggi.addActionListener(e -> {
            spinnerDimissioni.setValue(java.util.Date.from(
                    LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
            aggiornaDimissioni();
        });
        btnCerca.addActionListener(e -> aggiornaDimissioni());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Data:"));
        top.add(spinnerDimissioni);
        top.add(btnOggi);
        top.add(btnCerca);
        p.add(top, BorderLayout.NORTH);

        modelDimissioni = new DefaultTableModel(
                new String[]{"Nome", "Cognome", "Codice Fiscale"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(new JScrollPane(new JTable(modelDimissioni)), BorderLayout.CENTER);
        aggiornaDimissioni();
        return p;
    }

    private void aggiornaDimissioni() {
        if (modelDimissioni == null) return;
        modelDimissioni.setRowCount(0);
        java.util.Date d = (java.util.Date) spinnerDimissioni.getValue();
        LocalDate data = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        for (Paziente paz : controller.getPazientiInScadenza(data))
            modelDimissioni.addRow(new Object[]{paz.getNome(), paz.getCognome(), paz.getCodiceFiscale()});
    }

    // Tab assenze
    private JPanel costruisciPannelloAssenze() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Registra assenza medico"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboMediciAssenza = new JComboBox<>();
        for (Medico m : controller.getMedici())
            comboMediciAssenza.addItem(new MedicoWrapper(m));

        campoInizioA = new JTextField("dd/MM/yyyy HH:mm", 16);
        campoFineA   = new JTextField("dd/MM/yyyy HH:mm", 16);

        addCoppia(form, g, 0, "Medico:",  comboMediciAssenza);
        addCoppia(form, g, 1, "Inizio (dd/MM/yyyy HH:mm):", campoInizioA);
        addCoppia(form, g, 2, "Fine (dd/MM/yyyy HH:mm):",   campoFineA);

        JButton btnRegistra = new JButton("Registra assenza");
        btnRegistra.addActionListener(e -> eseguiRegistraAssenza());
        g.gridy = 3; g.gridx = 0; g.gridwidth = 2; form.add(btnRegistra, g);
        p.add(form, BorderLayout.NORTH);

        areaRisultatiAssenza = new JTextArea(10, 50);
        areaRisultatiAssenza.setEditable(false);
        areaRisultatiAssenza.setFont(new Font("Monospaced", Font.PLAIN, 12));
        p.add(new JScrollPane(areaRisultatiAssenza), BorderLayout.CENTER);
        return p;
    }

    private void eseguiRegistraAssenza() {
        try {
            LocalDateTime inizio = LocalDateTime.parse(campoInizioA.getText().trim(), FMT_DT);
            LocalDateTime fine   = LocalDateTime.parse(campoFineA.getText().trim(),   FMT_DT);
            MedicoWrapper mw = (MedicoWrapper) comboMediciAssenza.getSelectedItem();
            if (mw == null) return;

            List<Turno>      turniScoperti = controller.getTurniScoperti(mw.medico, inizio, fine);
            List<Prestazione> prestScop   = controller.getPrestazioniScoperte(mw.medico, inizio, fine);
            List<Medico>      sostituti   = controller.registraAssenzaESuggerisciSostituti(
                    mw.medico, inizio, fine);

            StringBuilder sb = new StringBuilder();
            sb.append("=== Assenza registrata per ").append(mw.medico.getLogin())
                    .append(" dal ").append(inizio.format(FMT_DT))
                    .append(" al ").append(fine.format(FMT_DT)).append(" ===\n\n");

            sb.append("Turni scoperti (").append(turniScoperti.size()).append("):\n");
            for (Turno t : turniScoperti)
                sb.append("  • ").append(t.getInizio().format(FMT_DT))
                        .append(" → ").append(t.getFine().format(FMT_DT)).append("\n");

            sb.append("\nPrestazioni scoperte (").append(prestScop.size()).append("):\n");
            for (Prestazione pr : prestScop)
                sb.append("  • ").append(pr.getInizio().format(FMT_DT))
                        .append(" → ").append(pr.getFine() != null ? pr.getFine().format(FMT_DT) : "?")
                        .append("\n");

            sb.append("\nMedici sostitutivi suggeriti (").append(sostituti.size()).append("):\n");
            if (sostituti.isEmpty()) sb.append("  Nessun sostituto disponibile.\n");
            else for (Medico s : sostituti) sb.append("  ✓ ").append(s.getLogin()).append("\n");

            areaRisultatiAssenza.setText(sb.toString());
        } catch (DateTimeParseException ex) {
            areaRisultatiAssenza.setText("Formato data non valido (usa dd/MM/yyyy HH:mm).");
        }
    }

    // utility
    private void addCoppia(JPanel p, GridBagConstraints g, int riga,
                           String etichetta, JComponent campo) {
        g.gridy = riga; g.gridx = 0; g.gridwidth = 1; p.add(new JLabel(etichetta), g);
        g.gridx = 1; p.add(campo, g);
    }

    // Wrapper
    private static class PazienteWrapper {
        final Paziente paziente;
        PazienteWrapper(Paziente p) { this.paziente = p; }
        public String toString() { return paziente.getNome() + " " + paziente.getCognome(); }
    }
    private static class RepartoWrapper {
        final Reparto reparto;
        RepartoWrapper(Reparto r) { this.reparto = r; }
        public String toString() { return reparto.getNome(); }
    }
    private static class LettoWrapper {
        final Letto letto; final boolean occupato;
        LettoWrapper(Letto l, boolean occ) { this.letto = l; this.occupato = occ; }
        public String toString() {
            return letto.getCodiceIdentificativo() + (occupato ? " [OCCUPATO]" : " [libero]");
        }
    }
    private static class MedicoWrapper {
        final Medico medico;
        MedicoWrapper(Medico m) { this.medico = m; }
        public String toString() { return medico.getLogin(); }
    }
}

