package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRangList extends JPanel {

    private JLabel lblTitle;
    private JComboBox<String> comboBoxDifficulty;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnBack;

    PanelRangList() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 734, 561);

        lblTitle = new JLabel("Rangliste", SwingConstants.CENTER);
        comboBoxDifficulty = new JComboBox<>(new String[] { "4 x 5", "6 x 6", "8 x 8" });
        btnBack = new JButton("Zurück");
        tableModel = new DefaultTableModel(new Object[] { "Platz", "Name", "Score" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(28);
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(120);
        JScrollPane scrollPane = new JScrollPane(table);

        lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
        comboBoxDifficulty.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setFont(new Font("Arial", Font.PLAIN, 17));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));

        lblTitle.setBounds(0, 30, 734, 60);
        comboBoxDifficulty.setBounds(292, 105, 150, 30);
        scrollPane.setBounds(117, 150, 500, 330);
        btnBack.setBounds(125, 500, 120, 40);

        this.add(lblTitle);
        this.add(comboBoxDifficulty);
        this.add(scrollPane);
        this.add(btnBack);
    }

    public String getSelectedDifficulty() {return (String) comboBoxDifficulty.getSelectedItem();}
    public void addDifficultyListener(ActionListener listener) {comboBoxDifficulty.addActionListener(listener);}
    public void addBackListener(ActionListener listener) {btnBack.addActionListener(listener);}

    public void setRows(Object[][] rows) {
        tableModel.setRowCount(0);
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }
}
