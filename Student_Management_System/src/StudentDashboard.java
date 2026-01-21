// StudentDashboard.java
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StudentDashboard extends JFrame 
{
    private DataStore.Student student;

    // ===== Icons (Add your images in Pic/ folder) =====
    private final ImageIcon icProfile = loadScaledIcon("Pic/P.jpg", 70, 70);
    private final ImageIcon icAttendance = loadScaledIcon("Pic/markattence.png", 70, 70);
    private final ImageIcon icMarks = loadScaledIcon("Pic/enter marks.png", 70, 70);
    private final ImageIcon icFee = loadScaledIcon("Pic/Set fee.png", 70, 70);
    private final ImageIcon icPassword = loadScaledIcon("Pic/Ch.png", 70, 70);
    private final ImageIcon icLogout = loadScaledIcon("Pic/logout.jpg", 70, 70);
    private final ImageIcon icLogo = loadScaledIcon("Pic/studnet.png", 150, 90);  // Student dashboard logo

    public StudentDashboard(String studentId) 
    {

        student = DataStore.getStudentById(studentId);
        if (student == null) 
            {
            JOptionPane.showMessageDialog(null, "Student not found");
            return;
        }

        setTitle("Student Dashboard - " + student.name);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP TITLE + LOGO =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLbl = new JLabel("Student Dashboard", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        top.add(titleLbl, BorderLayout.NORTH);

        if (icLogo != null) {
            JLabel logo = new JLabel(icLogo, SwingConstants.CENTER);
            top.add(logo, BorderLayout.SOUTH);
        }

        add(top, BorderLayout.NORTH);

        // ===== MAIN BUTTON GRID =====
        JPanel panel = new JPanel(new GridLayout(2, 3, 18, 18));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        panel.add(createBtn("View Profile", icProfile, e -> showProfile()));
        panel.add(createBtn("View Attendance", icAttendance, e -> showAttendance()));
        panel.add(createBtn("View Marks", icMarks, e -> showMarks()));
        panel.add(createBtn("View Fee Status", icFee, e -> showFeeStatus()));
        panel.add(createBtn("Change Password", icPassword, e -> changePassword()));

        // ===== LOGOUT BUTTON WITH ICON =====
        JButton logout = createBtn("Logout", icLogout, e -> { new LoginScreen(); dispose(); });

        add(panel, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ========= BUTTON CREATOR WITH ICON + HOVER EFFECT ==========
    private JButton createBtn(String text, ImageIcon icon, java.awt.event.ActionListener al) {
        JButton b = new JButton("<html><center>" + text + "</center></html>", icon);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setFocusPainted(false);

        Color normal = UIManager.getColor("Button.background");
        Color hover = new Color(230, 230, 230);

        // Hover Effect
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(hover);
                b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(normal);
                b.setBorder(UIManager.getBorder("Button.border"));
            }
        });

        b.addActionListener(al);
        return b;
    }

    // ========= ICON LOADER (Same as AdminDashboard) ==========
    private ImageIcon loadScaledIcon(String path, int w, int h) 
    {
        try {
            java.net.URL u = getClass().getResource(path);
            if (u != null) 
                {
                ImageIcon original = new ImageIcon(u);
                Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception ignored) {}
        return null;
    }

    private void showProfile() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(student.id).append("\n");
        sb.append("Name: ").append(student.name).append("\n");
        sb.append("Class: ").append(student.klass).append("\n");
        sb.append("Contact: ").append(student.contact).append("\n");
        sb.append("Password: ").append(student.password).append("\n");
        sb.append("Fee Paid: ").append(student.feePaid).append("\n");

        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAttendance() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Attendance %: ").append(String.format("%.2f", student.getAttendancePercentage())).append("%\nHistory:\n");

        if (student.attendance.isEmpty()) sb.append("No attendance recorded\n");
        else for (Map.Entry<String, String> e : student.attendance.entrySet())
            sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");

        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Attendance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMarks() {
        StringBuilder sb = new StringBuilder();
        if (student.marks.isEmpty()) sb.append("No marks recorded\n");
        else {
            sb.append("Subject       Marks\n----------------\n");
            for (Map.Entry<String, Integer> e : student.marks.entrySet())
                sb.append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
        }

        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Marks", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFeeStatus() {
        DataStore.ClassRoom cr = DataStore.getClassByName(student.klass);
        double totalFee = cr != null ? cr.fee : 0;
        double pending = totalFee - student.feePaid;
        if (pending < 0) pending = 0;

        String msg = "Total Fee: " + totalFee +
                     "\nPaid: " + student.feePaid +
                     "\nPending: " + pending;

        JOptionPane.showMessageDialog(this, msg, "Fee Status", JOptionPane.INFORMATION_MESSAGE);
    }

    private void changePassword() {
        JPasswordField p1 = new JPasswordField();
        JPasswordField p2 = new JPasswordField();
        Object[] f = {"New Password:", p1, "Confirm New Password:", p2};

        int res = JOptionPane.showConfirmDialog(this, f, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        String a = new String(p1.getPassword());
        String b = new String(p2.getPassword());

        if (a.isEmpty() || b.isEmpty() || !a.equals(b)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match or empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        student.password = a;
        DataStore.saveData();
        JOptionPane.showMessageDialog(this, "Password changed successfully!");
    }

}

  
  