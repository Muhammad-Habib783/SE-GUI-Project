// TeacherDashboard.java
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TeacherDashboard extends JFrame 
{

    private DataStore.Teacher teacher;

    // ===== ICONS (same style as AdminDashboard & StudentDashboard) =====
    private final ImageIcon icLogo = loadScaledIcon("Pic/Teacher.png", 180, 120);

    private final ImageIcon icAttendance = loadScaledIcon("Pic/markattence.png", 90, 90);
    private final ImageIcon icMarks = loadScaledIcon("Pic/enter marks.png", 90, 90);
    private final ImageIcon icStudents = loadScaledIcon("Pic/Studnet.png", 90, 90);
    private final ImageIcon icClasses = loadScaledIcon("Pic/view class.png", 90, 90);
    private final ImageIcon icPerformance = loadScaledIcon("Pic/Pro.png", 90, 90);
    private final ImageIcon icStudentAttendance = loadScaledIcon("Pic/markattence.png", 90, 90);
    private final ImageIcon icPassword = loadScaledIcon("Pic/Ch.png", 90, 90);
    private final ImageIcon icLogout = loadScaledIcon("Pic/logout.jpg", 90, 90);

    public TeacherDashboard(String teacherId) 
    {

        teacher = DataStore.getTeacherById(teacherId);
        if (teacher == null) 
            {
            JOptionPane.showMessageDialog(null, "Teacher not found");
            return;
        }

        setTitle("Teacher Dashboard - " + teacher.name);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP PANEL (Logo + Title) =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lblTitle = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        top.add(lblTitle, BorderLayout.NORTH);

        if (icLogo != null) 
            {
            JLabel lblLogo = new JLabel(icLogo, SwingConstants.CENTER);
            lblLogo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            top.add(lblLogo, BorderLayout.SOUTH);
        }

        add(top, BorderLayout.NORTH);

        // ===== BUTTON GRID =====
        JPanel panel = new JPanel(new GridLayout(0, 4, 18, 18));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        panel.add(createBtn("Mark Attendance", icAttendance, e -> markAttendance()));
        panel.add(createBtn("Enter Marks", icMarks, e -> enterMarks()));
        panel.add(createBtn("View My Students", icStudents, e -> viewMyStudents()));
        panel.add(createBtn("View Assigned Classes", icClasses, e -> viewAssigned()));
        panel.add(createBtn("Student Performance", icPerformance, e -> viewStudentPerformance()));
        panel.add(createBtn("Student Attendance", icStudentAttendance, e -> viewStudentAttendance()));
        panel.add(createBtn("Change Password", icPassword, e -> changePassword()));

        // ===== LOGOUT BUTTON =====
        JButton logout = createBtn("Logout", icLogout, e -> {
            new LoginScreen();
            dispose();
        });

        add(panel, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ========= Reusable Tile Button ==========
    private JButton createBtn(String text, ImageIcon icon, java.awt.event.ActionListener al) {
        JButton b = new JButton("<html><center><b>" + text + "</b></center></html>");
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));

        if (icon != null) {
            b.setIcon(icon);
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        b.setPreferredSize(new Dimension(240, 140));
        b.addActionListener(al);

        // Hover effect
        Color normal = b.getBackground();
        Color hover = new Color(230, 230, 230);

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(hover);
                b.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(normal);
                b.setBorder(UIManager.getBorder("Button.border"));
            }
        });

        return b;
    }

    // ========= ICON LOADER (same for all dashboards) ==========
    private ImageIcon loadScaledIcon(String path, int w, int h) {
        try {
            java.net.URL u = getClass().getClassLoader().getResource(path);
            if (u != null) {
                ImageIcon original = new ImageIcon(u);
                Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                System.out.println("Missing icon: " + path);
            }
        } catch (Exception ignored) {}
        return null;
    }
    // ===================== Mark Attendance =====================
    private void markAttendance(){
        String[] classNames = teacher.assignedClassSubjects.keySet().toArray(new String[0]);
        if(classNames.length == 0){
            JOptionPane.showMessageDialog(this,"No assigned classes");
            return;
        }

        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Mark Attendance",
                JOptionPane.QUESTION_MESSAGE, null, classNames, null);
        if(cls == null) return;

        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr == null || cr.studentIds.isEmpty()){
            JOptionPane.showMessageDialog(this,"No students in class");
            return;
        }

        String date = DataStore.todayDate();
        for(String sid : cr.studentIds){
            DataStore.Student s = DataStore.getStudentById(sid);
            if(s == null) continue;

            if(s.attendance.containsKey(date)){
                int ch = JOptionPane.showConfirmDialog(this,
                        "Attendance for " + s.name + " already exists for today ("+s.attendance.get(date)+"). Overwrite?",
                        "Already", JOptionPane.YES_NO_OPTION);
                if(ch != JOptionPane.YES_OPTION) continue;
            }

            int res = JOptionPane.showConfirmDialog(this,"Is " + s.name + " present?", "Attendance", JOptionPane.YES_NO_OPTION);
            String val = (res == JOptionPane.YES_OPTION) ? "P" : "A";
            DataStore.markAttendanceForStudent(s.id, date, val);
        }
        JOptionPane.showMessageDialog(this,"Attendance saved for " + date);
    }

    // ===================== Enter Marks =====================
    private void enterMarks(){
        String[] classNames = teacher.assignedClassSubjects.keySet().toArray(new String[0]);
        if(classNames.length == 0){
            JOptionPane.showMessageDialog(this,"No assigned classes");
            return;
        }

        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Enter Marks",
                JOptionPane.QUESTION_MESSAGE, null, classNames, null);
        if(cls == null) return;

        List<String> subs = teacher.assignedClassSubjects.getOrDefault(cls, new ArrayList<>());
        if(subs.isEmpty()){
            JOptionPane.showMessageDialog(this,"No subjects assigned for this class");
            return;
        }

        String sub = (String) JOptionPane.showInputDialog(this,"Select Subject:", "Subject",
                JOptionPane.QUESTION_MESSAGE, null, subs.toArray(), null);
        if(sub == null) return;

        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr == null || cr.studentIds.isEmpty()){
            JOptionPane.showMessageDialog(this,"No students in class");
            return;
        }

        for(String sid: cr.studentIds){
            DataStore.Student s = DataStore.getStudentById(sid);
            if(s == null) continue;

            String input = JOptionPane.showInputDialog(this,"Enter marks (0-100) for " + s.name + " in " + sub + " (Cancel skips):");
            if(input == null) continue;

            try{
                int m = Integer.parseInt(input.trim());
                if(m < 0 || m > 100){
                    JOptionPane.showMessageDialog(this,"Invalid mark (0-100). Skipping.");
                    continue;
                }
                DataStore.setMarkForStudent(s.id, sub, m);
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"Invalid input (must be integer). Skipping.");
            }
        }

        JOptionPane.showMessageDialog(this,"Marks entry complete");
    }

    // ===================== View My Students =====================
    private void viewMyStudents(){
        String[] classNames = teacher.assignedClassSubjects.keySet().toArray(new String[0]);
        if(classNames.length == 0){
            JOptionPane.showMessageDialog(this,"No assigned classes");
            return;
        }

        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "View Students",
                JOptionPane.QUESTION_MESSAGE, null, classNames, null);
        if(cls == null) return;

        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr == null || cr.studentIds.isEmpty()){
            JOptionPane.showMessageDialog(this,"No students");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(String sid: cr.studentIds){
            DataStore.Student s = DataStore.getStudentById(sid);
            if(s == null) continue;
            sb.append("ID: ").append(s.id)
              .append(", Name: ").append(s.name)
              .append(", Attendance%: ").append(String.format("%.2f", s.getAttendancePercentage())).append("%")
              .append(", Marks: ").append(s.marks.toString())
              .append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Students", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== View Assigned Classes =====================
    private void viewAssigned(){
        StringBuilder sb = new StringBuilder();
        if(teacher.assignedClassSubjects.isEmpty()) sb.append("No assigned classes");
        else {
            for(Map.Entry<String,List<String>> en : teacher.assignedClassSubjects.entrySet()){
                sb.append("Class ").append(en.getKey()).append(" : ").append(en.getValue()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    // ===================== View Student Performance =====================
    private void viewStudentPerformance(){
        String[] opts = {"View Marks (by student ID)","View Attendance (by student ID)","Back"};
        while(true){
            int sel = JOptionPane.showOptionDialog(this, "Student Performance", "Performance",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);

            if(sel == 0){
                String id = JOptionPane.showInputDialog(this,"Enter Student ID:");
                if(id == null || id.trim().isEmpty()) continue;

                DataStore.Student s = DataStore.getStudentById(id.trim());
                if(s == null){
                    JOptionPane.showMessageDialog(this,"Student not found");
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Marks:\n");
                if(s.marks.isEmpty()) sb.append("No marks");
                else for(Map.Entry<String,Integer> e : s.marks.entrySet())
                    sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");

                JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Marks", JOptionPane.INFORMATION_MESSAGE);

            } else if(sel == 1){
                String id = JOptionPane.showInputDialog(this,"Enter Student ID:");
                if(id == null || id.trim().isEmpty()) continue;

                DataStore.Student s = DataStore.getStudentById(id.trim());
                if(s == null){
                    JOptionPane.showMessageDialog(this,"Student not found");
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Attendance %: ").append(String.format("%.2f", s.getAttendancePercentage())).append("%\nHistory:\n");
                if(s.attendance.isEmpty()) sb.append("No attendance");
                else for(Map.Entry<String,String> e : s.attendance.entrySet())
                    sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");

                JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Attendance", JOptionPane.INFORMATION_MESSAGE);

            } else break;
        }
    }

    // ===================== New: View Student Attendance =====================
    private void viewStudentAttendance(){
        String id = JOptionPane.showInputDialog(this,"Enter Student ID to view attendance:");
        if(id == null || id.trim().isEmpty()) return;

        DataStore.Student s = DataStore.getStudentById(id.trim());
        if(s == null){
            JOptionPane.showMessageDialog(this,"Student not found");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Attendance %: ").append(String.format("%.2f", s.getAttendancePercentage())).append("%\n");
        sb.append("Attendance History:\n--------------------\n");

        if(s.attendance.isEmpty()) sb.append("No attendance recorded\n");
        else for(Map.Entry<String,String> e : s.attendance.entrySet())
            sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");

        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);

        JOptionPane.showMessageDialog(this, sp, "Student Attendance History", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== Change Password =====================
    private void changePassword(){
        JPasswordField p1 = new JPasswordField();
        JPasswordField p2 = new JPasswordField();
        Object[] f = {"New Password:", p1, "Confirm New Password:", p2};
        int res = JOptionPane.showConfirmDialog(this, f, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if(res != JOptionPane.OK_OPTION) return;

        String a = new String(p1.getPassword());
        String b = new String(p2.getPassword());
        if(a.isEmpty() || b.isEmpty() || !a.equals(b)){
            JOptionPane.showMessageDialog(this,"Passwords do not match or empty");
            return;
        }

        teacher.password = a;
        DataStore.saveData();
        JOptionPane.showMessageDialog(this,"Password changed");
    }
}
