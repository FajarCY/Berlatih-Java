package LatihanPemrograman;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class coba extends JFrame {

    private JTextField txtNamaKartu, txtKampus, txtProdi, txtEmail;
    private JTextField txtNamaUmur, txtUmur;
    private JTextField txtAngka1, txtAngka2;
    private JTextArea displayHasil;

    public coba() {
        setTitle("Aplikasi Tugas dengan Penyimpanan Data");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color bgDark = new Color(30, 32, 40);
        Color panelDark = new Color(42, 45, 57);
        Color inputDark = new Color(53, 57, 72);
        Color accentColor = new Color(98, 114, 241);
        Color textColor = new Color(240, 240, 240);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(bgDark);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Navigation Menu (Kiri)
        JPanel navPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        navPanel.setBackground(bgDark);
        navPanel.setPreferredSize(new Dimension(160, 0));

        JButton btnTab1 = createNavButton(" Kartu Nama", accentColor);
        JButton btnTab2 = createNavButton(" Input Umur", accentColor);
        JButton btnTab3 = createNavButton(" Kalkulator", accentColor);

        navPanel.add(btnTab1);
        navPanel.add(btnTab2);
        navPanel.add(btnTab3);

        // Content Stack (Tengah)
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(panelDark);

        // Tab 1: Kartu Nama
        JPanel pnlKartu = createBasePanel(panelDark);
        txtNamaKartu = addInputGroup(pnlKartu, "Nama Lengkap", inputDark, textColor);
        txtKampus = addInputGroup(pnlKartu, "Kampus", inputDark, textColor);
        txtProdi = addInputGroup(pnlKartu, "Prodi", inputDark, textColor);
        txtEmail = addInputGroup(pnlKartu, "Email", inputDark, textColor);

        JButton btnCetakKartu = createActionButton("Tampilkan & Simpan", accentColor);
        btnCetakKartu.addActionListener(e -> {
            String hasil = "--- KARTU NAMA ---\n" +
                           "Nama   : " + txtNamaKartu.getText() + "\n" +
                           "Kampus : " + txtKampus.getText() + "\n" +
                           "Prodi  : " + txtProdi.getText() + "\n" +
                           "Email  : " + txtEmail.getText();
            
            displayHasil.setText(hasil);
            simpanKeFile(hasil);
        });
        pnlKartu.add(btnCetakKartu);

        // Tab 2: Umur
        JPanel pnlUmur = createBasePanel(panelDark);
        txtNamaUmur = addInputGroup(pnlUmur, "Nama Anda", inputDark, textColor);
        txtUmur = addInputGroup(pnlUmur, "Umur Sekarang", inputDark, textColor);

        JButton btnCetakUmur = createActionButton("Hitung & Simpan", accentColor);
        btnCetakUmur.addActionListener(e -> {
            try {
                int umur = Integer.parseInt(txtUmur.getText());
                String hasil = "--- DATA UMUR ---\n" +
                               "Nama       : " + txtNamaUmur.getText() + "\n" +
                               "Umur Lahir : " + umur + " tahun\n" +
                               "Tahun depan umurmu akan menjadi " + (umur + 1) + " tahun.";
                
                displayHasil.setText(hasil);
                simpanKeFile(hasil);
            } catch (NumberFormatException ex) {
                displayHasil.setText("Error: Umur harus berupa angka!");
            }
        });
        pnlUmur.add(btnCetakUmur);

        // Tab 3: Kalkulator
        JPanel pnlKalkulator = createBasePanel(panelDark);
        txtAngka1 = addInputGroup(pnlKalkulator, "Bilangan Pertama", inputDark, textColor);
        txtAngka2 = addInputGroup(pnlKalkulator, "Bilangan Kedua", inputDark, textColor);

        JButton btnHitung = createActionButton("Hitung & Simpan", accentColor);
        btnHitung.addActionListener(e -> {
            try {
                int a = Integer.parseInt(txtAngka1.getText());
                int b = Integer.parseInt(txtAngka2.getText());

                String hasilBagi = (b != 0) ? String.format("%.2f", (double) a / b) : "Tidak terdefinisi";
                String sisaBagi = (b != 0) ? String.valueOf(a % b) : "N/A";

                String hasil = "--- HASIL KALKULATOR ---\n" +
                               "Input       : " + a + " dan " + b + "\n" +
                               "Penjumlahan : " + (a + b) + "\n" +
                               "Pengurangan : " + (a - b) + "\n" +
                               "Perkalian   : " + (a * b) + "\n" +
                               "Pembagian   : " + hasilBagi + "\n" +
                               "Sisa Bagi   : " + sisaBagi;

                displayHasil.setText(hasil);
                simpanKeFile(hasil);
            } catch (NumberFormatException ex) {
                displayHasil.setText("Error: Input harus angka bulat!");
            }
        });
        pnlKalkulator.add(btnHitung);

        contentPanel.add(pnlKartu, "soal1");
        contentPanel.add(pnlUmur, "soal2");
        contentPanel.add(pnlKalkulator, "soal3");

        // Display Area (Kanan)
        displayHasil = new JTextArea();
        displayHasil.setFont(new Font("Consolas", Font.PLAIN, 13));
        displayHasil.setBackground(inputDark);
        displayHasil.setForeground(textColor);
        displayHasil.setEditable(false);
        displayHasil.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollHasil = new JScrollPane(displayHasil);
        scrollHasil.setPreferredSize(new Dimension(240, 0));
        scrollHasil.setBorder(BorderFactory.createLineBorder(bgDark, 2));

        // Event Navigasi
        btnTab1.addActionListener(e -> cardLayout.show(contentPanel, "soal1"));
        btnTab2.addActionListener(e -> cardLayout.show(contentPanel, "soal2"));
        btnTab3.addActionListener(e -> cardLayout.show(contentPanel, "soal3"));

        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(scrollHasil, BorderLayout.EAST);

        add(mainPanel);
    }

    // Fungsi Tambahan untuk Menyimpan Teks ke File
    private void simpanKeFile(String konten) {
        try (FileWriter fw = new FileWriter("data_tersimpan.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            // Menambahkan Waktu Penyimpanan Data
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            pw.println("[" + dtf.format(now) + "]");
            pw.println(konten);
            pw.println("=========================================\n");
            
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke 'data_tersimpan.txt'!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createBasePanel(Color bg) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bg);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    private JTextField addInputGroup(JPanel parent, String labelText, Color bg, Color fg) {
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(fg);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tf.setBackground(bg);
        tf.setForeground(fg);
        tf.setCaretColor(fg);
        tf.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(lbl);
        parent.add(Box.createRigidArea(new Dimension(0, 4)));
        parent.add(tf);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));

        return tf;
    }

    private JButton createNavButton(String text, Color accent) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(accent);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new coba().setVisible(true));
    }
}