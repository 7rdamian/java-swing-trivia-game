package ui.panels;

import model.Player;
import service.Leaderboard;
import ui.frames.HomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

// Child of MenuPanel, displays the leaderboard

public class LeaderboardPanel extends JPanel {
    private static final Color MAIN_BACKGROUND_COLOR = new Color(44, 44, 44);
    private static final Color TEXT_COLOR = new Color(228, 228, 228);
    private static final Color TABLE_HEADER_COLOR = new Color(168, 218, 220);
    private static final Color BACK_BUTTON_COLOR = new Color(91, 131, 131);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);

    private HomePage homePage;

    public LeaderboardPanel(HomePage homePage) {
        this.homePage = homePage;

        Leaderboard leaderboard = new Leaderboard();

        setLayout(new GridBagLayout());
        setBackground(MAIN_BACKGROUND_COLOR);

        JPanel generalContainer = new JPanel();
        generalContainer.setBackground(MAIN_BACKGROUND_COLOR);
        generalContainer.setLayout(new BoxLayout(generalContainer, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("LEADERBOARD");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleWrapper.setBackground(MAIN_BACKGROUND_COLOR);
        titleWrapper.add(titleLabel);

        generalContainer.add(Box.createVerticalStrut(20));
        generalContainer.add(titleWrapper);
        generalContainer.add(Box.createVerticalStrut(50));

        JTable leaderboardTable = createLeaderboardTable(leaderboard);
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.getViewport().setBackground(MAIN_BACKGROUND_COLOR.brighter());
        generalContainer.add(scrollPane);

        JButton backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(BACK_BUTTON_COLOR);
        backButton.setForeground(TEXT_COLOR);
        backButton.setOpaque(true);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(e -> {
            MenuPanel menuPanel = new MenuPanel(homePage);
            homePage.setContentPane(menuPanel);
            homePage.revalidate();
            homePage.repaint();
        });

        generalContainer.add(Box.createVerticalStrut(20));
        generalContainer.add(backButton);
        generalContainer.add(Box.createVerticalStrut(20));

        add(generalContainer);
    }

    public JTable createLeaderboardTable(Leaderboard leaderboard) {
        List<Player> players = leaderboard.getPlayers();

        String[] columnNames = {"Rank", "Name", "Score"};
        Object[][] data = new Object[players.size()][3];

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            data[i][0] = i + 1;
            data[i][1] = p.getName();
            data[i][2] = p.getScore();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        table.setBackground(MAIN_BACKGROUND_COLOR.brighter());
        table.setForeground(TEXT_COLOR);
        table.setGridColor(MAIN_BACKGROUND_COLOR.darker());
        table.setRowHeight(25);

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(MAIN_BACKGROUND_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 16));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        return table;
    }
}
