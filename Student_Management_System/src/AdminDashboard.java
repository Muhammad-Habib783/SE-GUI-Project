import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AdminDashboard extends JFrame 
{

    // ===== Load + Resize Icons =====
    private final ImageIcon icStudentManage = loadScaledIcon("Pic/Mange student.png", 90, 90); // Manage Students
    private final ImageIcon icStudentShow = loadScaledIcon("Pic/Studnet.png",90, 90); // Show Students
    private final ImageIcon icTeacher = loadScaledIcon("Pic/Manage Teacher.png", 90, 90);
    
    private final ImageIcon icSubject = loadScaledIcon("Pic/mange subject.png", 90, 90);
    private final ImageIcon icAssign = loadScaledIcon("Pic/Assign suject.png", 90, 90);
    private final ImageIcon icFee = loadScaledIcon("Pic/set fee.png", 90, 90);
    private final ImageIcon icReport = loadScaledIcon("Pic/R.png", 90, 90);
    private final ImageIcon icAnalysis = loadScaledIcon("Pic/Analyse.png", 90, 90);
    private final ImageIcon icPassword = loadScaledIcon("Pic/pass.png", 90, 90);
    private final ImageIcon icLogout = loadScaledIcon("Pic/logout.jpg", 90, 90);
    private final ImageIcon icShowTeacher = loadScaledIcon("Pic/Teacher.png", 90, 90); // new icon for showing teachers

    private final ImageIcon icLogo = loadScaledIcon("Pic/admin.png", 170, 115); // Updated admin logo

    public AdminDashboard() 
    {
        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel (Title + Logo)
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titleLbl = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        top.add(titleLbl, BorderLayout.NORTH);

        if (icLogo != null) {
            JLabel logo = new JLabel(icLogo, SwingConstants.CENTER);
            logo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            top.add(logo, BorderLayout.SOUTH);
        }

        add(top, BorderLayout.NORTH);

        // Tiles Grid
        JPanel tiles = new JPanel(new GridLayout(0, 4, 18, 18));
        tiles.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        tiles.add(createTile("Manage Students", icStudentManage, e -> manageStudents()));
        tiles.add(createTile("Manage Teachers", icTeacher, e -> manageTeachers()));
        tiles.add(createTile("Manage Subjects", icSubject, e -> manageSubjects()));
        tiles.add(createTile("Assign Subjects", icAssign, e -> assignSubjects()));

        tiles.add(createTile("Set Class Fee", icFee, e -> setClassFee()));
        tiles.add(createTile("Pay Fee", icFee, e -> payFee()));
        tiles.add(createTile("View Reports", icReport, e -> viewReports()));
        tiles.add(createTile("Analysis", icAnalysis, e -> showAnalysis()));

        tiles.add(createTile("Show Students", icStudentShow, e -> showAllStudents()));
        tiles.add(createTile("Show Teachers", icShowTeacher, e -> showAllTeachers()));


        tiles.add(createTile("Change Password", icPassword, e -> changePassword()));
        tiles.add(createTile("Logout", icLogout, e -> 
        {
            new LoginScreen();
            dispose();
        }));

        add(tiles, BorderLayout.CENTER);
        setVisible(true);
    }

    // ========= Icon Loader + Auto Size ==========
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

    // ========= Tile (button) Creator with Hover Effect ==========
    private JButton createTile(String text, ImageIcon icon, java.awt.event.ActionListener al) {
    JButton b = new JButton("<html><center><b>" + text + "</b></center></html>");
    b.setFont(new Font("Segoe UI", Font.BOLD, 16));

    if (icon != null) {
        b.setIcon(icon);
        b.setHorizontalTextPosition(SwingConstants.RIGHT); // icon on left
        b.setVerticalTextPosition(SwingConstants.CENTER);   // center vertically
    }

    b.setPreferredSize(new Dimension(240, 140));
    b.setFocusPainted(false);
    b.setBackground(UIManager.getColor("Button.background"));

    // ---- Hover Effect ----
    Color normalBG = b.getBackground();
    Color hoverBG = new Color(230, 230, 230);

    b.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            b.setBackground(hoverBG);
            b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            b.setBackground(normalBG);
            b.setBorder(UIManager.getBorder("Button.border"));
        }
    });

    b.addActionListener(al);
    return b;
}


    // ========= Header Panel for Dialogs ==========
    private JPanel dialogHeader(ImageIcon icon, String title) 
    {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);

        if (icon != null) left.add(new JLabel(icon));
        left.add(lbl);

        p.add(left, BorderLayout.NORTH);
        return p;
    }

    // ----- Add your existing manageStudents, manageTeachers, etc. methods here -----

   


    // ---------------- Manage Students ----------------
    private void manageStudents(){
        String[] options = {"Add Student","Update Student","Delete Student","Back"};
        while(true){
            JPanel header = dialogHeader(icStudentManage, "Manage Students");
            int sel = JOptionPane.showOptionDialog(this, header, "Manage Students", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if(sel==0) addStudentDialog();
            else if(sel==1) updateStudentDialog();
            else if(sel==2) deleteStudentDialog();
            else break;
        }
    }
    // ---------------- Manage Students ----------------
   
    private void addStudentDialog(){
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        String[] classNames = DataStore.classes.stream().map(c->c.name).toArray(String[]::new);
        JComboBox<String> clsBox = new JComboBox<>(classNames);
        JPasswordField passFld = new JPasswordField("password");

        Object[] fields = {"ID (integer):", idField, "Name:", nameField, "Contact:", contactField, "Class:", clsBox, "Password:", passFld};
        int res = JOptionPane.showConfirmDialog(this, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(res != JOptionPane.OK_OPTION) return;
        String id = idField.getText().trim();
        String nm = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String cls = (String)clsBox.getSelectedItem();
        String pass = new String(passFld.getPassword());

        if(id.isEmpty()||nm.isEmpty()||contact.isEmpty()||pass.isEmpty()){ JOptionPane.showMessageDialog(this,"All fields are required"); return; }
        try{ Integer.parseInt(id); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"ID must be an integer"); return; }
        if(DataStore.studentIdExists(id)){ JOptionPane.showMessageDialog(this,"Student ID already exists"); return; }

        DataStore.Student s = new DataStore.Student(id,nm,contact,cls,pass);
        DataStore.addStudent(s);
        JOptionPane.showMessageDialog(this,"Student added successfully");
    }

    private void updateStudentDialog(){
        String id = JOptionPane.showInputDialog(this,"Enter Student ID to update:");
        if(id==null || id.trim().isEmpty()) return;
        DataStore.Student s = DataStore.getStudentById(id.trim());
        if(s==null){ JOptionPane.showMessageDialog(this,"Student not found"); return; }

        JTextField idField = new JTextField(s.id);
        JTextField nameField = new JTextField(s.name);
        JTextField contactField = new JTextField(s.contact);
        String[] classNames = DataStore.classes.stream().map(c->c.name).toArray(String[]::new);
        JComboBox<String> clsBox = new JComboBox<>(classNames);
        clsBox.setSelectedItem(s.klass);
        JPasswordField passFld = new JPasswordField(s.password);

        Object[] fields = {"ID (integer):", idField, "Name:", nameField, "Contact:", contactField, "Class:", clsBox, "Password:", passFld};
        int res = JOptionPane.showConfirmDialog(this, fields, "Update Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(res!=JOptionPane.OK_OPTION) return;
        String newId = idField.getText().trim();
        String nm = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String cls = (String)clsBox.getSelectedItem();
        String pass = new String(passFld.getPassword());

        if(newId.isEmpty()||nm.isEmpty()||contact.isEmpty()||pass.isEmpty()){ JOptionPane.showMessageDialog(this,"All fields required"); return; }
        try{ Integer.parseInt(newId); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"ID must be integer"); return; }
        // if changing to an ID that exists (and not same student) -> invalid
        if(!newId.equals(s.id) && DataStore.studentIdExists(newId)){ JOptionPane.showMessageDialog(this,"ID already exists"); return; }

        DataStore.Student ns = new DataStore.Student(newId,nm,contact,cls,pass);
        ns.feePaid = s.feePaid;
        ns.attendance = new LinkedHashMap<>(s.attendance);
        ns.marks = new LinkedHashMap<>(s.marks);
        boolean ok = DataStore.updateStudent(s.id, ns);
        if(ok) JOptionPane.showMessageDialog(this,"Student updated");
        else JOptionPane.showMessageDialog(this,"Failed to update student");
    }


    private void deleteStudentDialog(){
        String id = JOptionPane.showInputDialog(this,"Enter Student ID to delete:");
        if(id==null||id.trim().isEmpty()) return;
        DataStore.Student s = DataStore.getStudentById(id.trim());
        if(s==null){ JOptionPane.showMessageDialog(this,"Student not found"); return; }
        int conf = JOptionPane.showConfirmDialog(this,"Delete student "+s.name+" (ID: "+s.id+")?","Confirm",JOptionPane.YES_NO_OPTION);
        if(conf!=JOptionPane.YES_OPTION) return;
        boolean ok = DataStore.deleteStudent(id.trim());
        if(ok) JOptionPane.showMessageDialog(this,"Student deleted");
        else JOptionPane.showMessageDialog(this,"Failed to delete student");
    }

    // ---------------- Manage Teachers ----------------
    private void manageTeachers(){
        String[] options = {"Add Teacher","Update Teacher","Delete Teacher","Back"};
        while(true){
            JPanel header = dialogHeader(icTeacher, "Manage Teachers");
            int sel = JOptionPane.showOptionDialog(this, header, "Manage Teachers", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if(sel==0) addTeacherDialog();
            else if(sel==1) updateTeacherDialog();
            else if(sel==2) deleteTeacherDialog();
            else break;
        }
    }
    private void addTeacherDialog(){
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JPasswordField passFld = new JPasswordField("password");

        Object[] fields = {"ID (integer):", idField, "Name:", nameField, "Contact:", contactField, "Password:", passFld};
        int res = JOptionPane.showConfirmDialog(this, fields, "Add Teacher", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(res!=JOptionPane.OK_OPTION) return;
        String id = idField.getText().trim(); String nm=nameField.getText().trim();
        String contact = contactField.getText().trim(); String pass = new String(passFld.getPassword());
        if(id.isEmpty()||nm.isEmpty()||contact.isEmpty()||pass.isEmpty()){ JOptionPane.showMessageDialog(this,"All fields required"); return; }
        try{ Integer.parseInt(id);}catch(Exception ex){ JOptionPane.showMessageDialog(this,"ID must be integer"); return; }
        if(DataStore.teacherIdExists(id)){ JOptionPane.showMessageDialog(this,"Teacher ID exists"); return; }
        DataStore.Teacher t = new DataStore.Teacher(id,nm,contact,pass);
        DataStore.addTeacher(t);
        JOptionPane.showMessageDialog(this,"Teacher added successfully");
    }
    private void updateTeacherDialog(){
        String id = JOptionPane.showInputDialog(this,"Enter Teacher ID to update:");
        if(id==null||id.trim().isEmpty()) return;
        DataStore.Teacher t = DataStore.getTeacherById(id.trim());
        if(t==null){ JOptionPane.showMessageDialog(this,"Teacher not found"); return; }
        JTextField idFld = new JTextField(t.id); JTextField nameFld = new JTextField(t.name); JTextField contactFld = new JTextField(t.contact); JPasswordField passFld = new JPasswordField(t.password);
        Object[] fields = {"ID (integer):", idFld, "Name:", nameFld, "Contact:", contactFld, "Password:", passFld};
        int res = JOptionPane.showConfirmDialog(this, fields, "Update Teacher", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(res!=JOptionPane.OK_OPTION) return;
        String nid = idFld.getText().trim(); String nm=nameFld.getText().trim(); String contact=contactFld.getText().trim(); String pass = new String(passFld.getPassword());
        if(nid.isEmpty()||nm.isEmpty()||contact.isEmpty()||pass.isEmpty()){ JOptionPane.showMessageDialog(this,"All fields required"); return; }
        try{ Integer.parseInt(nid);}catch(Exception ex){ JOptionPane.showMessageDialog(this,"ID must be integer"); return; }
        if(!nid.equals(t.id) && DataStore.teacherIdExists(nid)){ JOptionPane.showMessageDialog(this,"ID exists"); return; }
        DataStore.Teacher nt = new DataStore.Teacher(nid,nm,contact,pass); nt.assignedClassSubjects = new LinkedHashMap<>(t.assignedClassSubjects);
        boolean ok = DataStore.updateTeacher(t.id, nt);
        if(ok) JOptionPane.showMessageDialog(this,"Teacher updated");
        else JOptionPane.showMessageDialog(this,"Failed to update teacher");
    }
    private void deleteTeacherDialog(){
        String id = JOptionPane.showInputDialog(this,"Enter Teacher ID to delete:");
        if(id==null||id.trim().isEmpty()) return;
        DataStore.Teacher t = DataStore.getTeacherById(id.trim());
        if(t==null){ JOptionPane.showMessageDialog(this,"Teacher not found"); return; }
        int conf = JOptionPane.showConfirmDialog(this,"Delete teacher "+t.name+" (ID: "+t.id+")? This removes assignments.","Confirm",JOptionPane.YES_NO_OPTION);
        if(conf!=JOptionPane.YES_OPTION) return;
        boolean ok = DataStore.deleteTeacher(id.trim());
        if(ok) JOptionPane.showMessageDialog(this,"Teacher deleted");
        else JOptionPane.showMessageDialog(this,"Failed to delete teacher");
    }

    // ---------------- Manage Subjects ----------------
    private void manageSubjects(){
        String[] options = {"Add New Subject","Update Subject","Delete Subject","Back"};
        while(true){
            JPanel header = dialogHeader(icSubject, "Manage Subjects");
            int sel = JOptionPane.showOptionDialog(this, header, "Subjects", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if(sel==0) addSubjectDialog();
            else if(sel==1) updateSubjectDialog();
            else if(sel==2) deleteSubjectDialog();
            else break;
        }
    }
    private void addSubjectDialog(){
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Add Subject", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        String subj = JOptionPane.showInputDialog(this,"Enter subject name to add to class " + cls + ":");
        if(subj==null || subj.trim().isEmpty()) return;
        boolean ok = DataStore.addSubjectToClass(cls, subj.trim());
        if(ok) JOptionPane.showMessageDialog(this,"Subject added to class");
        else JOptionPane.showMessageDialog(this,"Failed to add subject (duplicate?)");
    }
    private void updateSubjectDialog(){
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Update Subject", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        DataStore.ClassRoom c = DataStore.getClassByName(cls);
        if(c==null || c.subjects.isEmpty()){ JOptionPane.showMessageDialog(this,"No subjects in this class"); return; }
        String old = (String) JOptionPane.showInputDialog(this,"Select subject to update:", "Update", JOptionPane.QUESTION_MESSAGE, null, c.subjects.toArray(), null);
        if(old==null) return;
        String neu = JOptionPane.showInputDialog(this,"Enter new name for subject '" + old + "':");
        if(neu==null || neu.trim().isEmpty()) return;
        boolean ok = DataStore.updateSubjectInClass(cls, old, neu.trim());
        if(ok) JOptionPane.showMessageDialog(this,"Subject updated");
        else JOptionPane.showMessageDialog(this,"Failed to update subject (duplicate or conflict)");
    }
    private void deleteSubjectDialog(){
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Delete Subject", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        DataStore.ClassRoom c = DataStore.getClassByName(cls);
        if(c==null || c.subjects.isEmpty()){ JOptionPane.showMessageDialog(this,"No subjects in this class"); return; }
        String subj = (String) JOptionPane.showInputDialog(this,"Select subject to delete:", "Delete", JOptionPane.QUESTION_MESSAGE, null, c.subjects.toArray(), null);
        if(subj==null) return;
        int conf = JOptionPane.showConfirmDialog(this,"Delete subject '" + subj + "' from class " + cls + "? This will remove marks and assignments.", "Confirm", JOptionPane.YES_NO_OPTION);
        if(conf!=JOptionPane.YES_OPTION) return;
        boolean ok = DataStore.deleteSubjectFromClass(cls, subj);
        if(ok) JOptionPane.showMessageDialog(this,"Subject deleted");
        else JOptionPane.showMessageDialog(this,"Failed to delete subject");
    }

    // ---------------- Assign Subjects ----------------
    private void assignSubjects(){
        String[] options = {"Assign Subject to Teacher","Update Assigned Subjects","Back"};
        while(true){
            JPanel header = dialogHeader(icAssign, "Assign Subjects");
            int sel = JOptionPane.showOptionDialog(this, header, "Assign Subjects", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if(sel==0) assignSubjectDialog();
            else if(sel==1) updateAssignedDialog();
            else break;
        }
    }
    private void assignSubjectDialog(){
        String tid = JOptionPane.showInputDialog(this,"Enter Teacher ID to assign:");
        if(tid==null||tid.trim().isEmpty()) return;
        DataStore.Teacher t = DataStore.getTeacherById(tid.trim());
        if(t==null){ JOptionPane.showMessageDialog(this,"Teacher not found"); return; }
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Assign", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr==null || cr.subjects.isEmpty()){ JOptionPane.showMessageDialog(this,"No subjects in class"); return; }
        // show check list
        JList<String> list = new JList<>(cr.subjects.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane sp = new JScrollPane(list);
        int res = JOptionPane.showConfirmDialog(this, sp, "Select subjects to assign (note: each class-subject can only have one teacher)", JOptionPane.OK_CANCEL_OPTION);
        if(res!=JOptionPane.OK_OPTION) return;
        List<String> chosen = list.getSelectedValuesList();
        if(chosen.isEmpty()){ JOptionPane.showMessageDialog(this,"No subjects selected"); return; }
        // check teacher limit (max 3 total subjects)
        int already = t.totalAssignedSubjects();
        int willAdd=0;
        for(String s: chosen){
            List<String> cur = t.assignedClassSubjects.getOrDefault(cls,new ArrayList<>());
            if(!cur.contains(s)) willAdd++;
        }
        if(already + willAdd > 3){ JOptionPane.showMessageDialog(this,"Teacher cannot have more than 3 subjects in total. Already: "+already); return; }
        // ensure chosen not assigned elsewhere
        List<String> toAssign = new ArrayList<>();
        for(String s: chosen){
            if(!DataStore.isSubjectAssignedToAnyTeacher(cls,s) || (t.assignedClassSubjects.getOrDefault(cls,new ArrayList<>()).contains(s))){
                toAssign.add(s);
            }
        }
        if(toAssign.isEmpty()){ JOptionPane.showMessageDialog(this,"Selected subjects are already assigned to other teachers"); return; }
        boolean ok = DataStore.assignSubjectsToTeacher(tid.trim(), cls, toAssign);
        if(ok) JOptionPane.showMessageDialog(this,"Assigned: " + String.join(", ", toAssign));
        else JOptionPane.showMessageDialog(this,"Failed to assign (conflict)");
    }
    private void updateAssignedDialog(){
        String tid = JOptionPane.showInputDialog(this,"Enter Teacher ID to update assignments:");
        if(tid==null||tid.trim().isEmpty()) return;
        DataStore.Teacher t = DataStore.getTeacherById(tid.trim());
        if(t==null){ JOptionPane.showMessageDialog(this,"Teacher not found"); return; }
        String[] classNames = t.assignedClassSubjects.keySet().toArray(new String[0]);
        if(classNames.length==0){ JOptionPane.showMessageDialog(this,"This teacher has no assigned classes"); return; }
        String cls = (String) JOptionPane.showInputDialog(this,"Select class to update for this teacher:", "Update Assigned", JOptionPane.QUESTION_MESSAGE, null, classNames, null);
        if(cls==null) return;
        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr==null){ JOptionPane.showMessageDialog(this,"Class not found"); return; }
        // build list of available subjects (class subjects). Preselect current ones.
        JList<String> list = new JList<>(cr.subjects.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        List<String> curList = t.assignedClassSubjects.getOrDefault(cls, new ArrayList<>());
        int[] selIdx = curList.stream().mapToInt(s->{
            for(int i=0;i<cr.subjects.size();i++) if(cr.subjects.get(i).equals(s)) return i;
            return -1;
        }).filter(i->i>=0).toArray();
        list.setSelectedIndices(selIdx);
        int res = JOptionPane.showConfirmDialog(this, new JScrollPane(list), "Select new assigned subjects (max 3 per teacher total)", JOptionPane.OK_CANCEL_OPTION);
        if(res!=JOptionPane.OK_OPTION) return;
        List<String> chosen = list.getSelectedValuesList();
        // check teacher total limit
        int otherCount = t.totalAssignedSubjects() - t.assignedClassSubjects.getOrDefault(cls,new ArrayList<>()).size();
        if(otherCount + chosen.size() > 3){ JOptionPane.showMessageDialog(this,"Total assigned subjects cannot exceed 3. Current other count: "+otherCount); return; }
        // validate exclusivity vs other teachers
        boolean ok = DataStore.updateTeacherAssignedSubjects(tid.trim(), cls, chosen);
        if(ok) JOptionPane.showMessageDialog(this,"Assigned subjects updated");
        else JOptionPane.showMessageDialog(this,"Failed to update assignments (conflict with other teacher)");
    }
     // ===================== Change Password =====================
    private void changePassword() {
        JPasswordField p1 = new JPasswordField();
        JPasswordField p2 = new JPasswordField();
        Object[] f = {"New Password:", p1, "Confirm New Password:", p2};
        int res = JOptionPane.showConfirmDialog(this, f, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        String a = new String(p1.getPassword());
        String b = new String(p2.getPassword());
        if (a.isEmpty() || b.isEmpty() || !a.equals(b)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match or empty");
            return;
        }

        DataStore.adminPassword = a;  // Save new admin password in DataStore
        DataStore.saveData();         // persist changes
        JOptionPane.showMessageDialog(this, "Password changed successfully");
    }

    // ---------------- Fee ----------------
    private void setClassFee(){
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Set Class Fee", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        String feeStr = JOptionPane.showInputDialog(this,"Enter monthly fee for class " + cls + " (current: "+cr.fee+"):");
        if(feeStr==null) return;
        try{
            double f = Double.parseDouble(feeStr);
            if(f<0){ JOptionPane.showMessageDialog(this,"Fee cannot be negative"); return; }
            DataStore.setClassFee(cls, f);
            JOptionPane.showMessageDialog(this,"Fee set");
        }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Invalid fee"); }
    }
    private void payFee(){
        String cls = (String) JOptionPane.showInputDialog(this,"Select Class:", "Pay Fee", JOptionPane.QUESTION_MESSAGE, null, DataStore.classes.stream().map(c->c.name).toArray(String[]::new), null);
        if(cls==null) return;
        DataStore.ClassRoom cr = DataStore.getClassByName(cls);
        if(cr==null || cr.studentIds.isEmpty()){ JOptionPane.showMessageDialog(this,"No students in this class"); return; }
        String sid = JOptionPane.showInputDialog(this,"Enter Student ID to pay fee (or press Cancel to select from list):");
        DataStore.Student s = null;
        if(sid==null || sid.trim().isEmpty()){
            sid = (String) JOptionPane.showInputDialog(this,"Select student:", "Pay Fee", JOptionPane.QUESTION_MESSAGE, null, cr.studentIds.toArray(), null);
            if(sid==null) return;
            s = DataStore.getStudentById(sid);
        } else {
            s = DataStore.getStudentById(sid.trim());
            if(s==null){ JOptionPane.showMessageDialog(this,"Student not found"); return; }
        }
        String amt = JOptionPane.showInputDialog(this,"Enter amount to add (class fee: "+cr.fee+"), current paid: "+s.feePaid);
        if(amt==null) return;
        try{
            double a = Double.parseDouble(amt);
            if(a<0){ JOptionPane.showMessageDialog(this,"Invalid amount"); return; }
            DataStore.payFeeForStudent(s.id, a);
            JOptionPane.showMessageDialog(this,"Fee updated. Total paid: "+DataStore.getStudentById(s.id).feePaid);
        }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Invalid amount"); }
    }

    // ---------------- Reports ----------------
    private void viewReports(){
        String[] opts = {"Student Marks (search by ID)","Attendance History (search by ID)","Back"};
        while(true){
            JPanel header = dialogHeader(icReport, "Reports");
            int sel = JOptionPane.showOptionDialog(this, header, "Reports", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
            if(sel==0) reportStudentMarks();
            else if(sel==1) reportAttendanceHistory();
            else break;
        }
    }
    private void reportStudentMarks(){
        String id = JOptionPane.showInputDialog(this,"Enter Student ID:");
        if(id==null||id.trim().isEmpty()) return;
        DataStore.Student s = DataStore.getStudentById(id.trim());
        if(s==null){ JOptionPane.showMessageDialog(this,"Student not found"); return; }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(s.id).append("\nName: ").append(s.name).append("\nClass: ").append(s.klass).append("\nContact: ").append(s.contact).append("\nFee paid: ").append(s.feePaid).append("\n\nMarks:\n");
        if(s.marks.isEmpty()) sb.append("No marks recorded");
        else for(Map.Entry<String,Integer> e: s.marks.entrySet()) sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        JOptionPane.showMessageDialog(this,new JScrollPane(new JTextArea(sb.toString())), "Student Marks", JOptionPane.INFORMATION_MESSAGE);
    }
    private void reportAttendanceHistory(){
        String id = JOptionPane.showInputDialog(this,"Enter Student ID:");
        if(id==null||id.trim().isEmpty()) return;
        DataStore.Student s = DataStore.getStudentById(id.trim());
        if(s==null){ JOptionPane.showMessageDialog(this,"Student not found"); return; }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(s.id).append("\nName: ").append(s.name).append("\nClass: ").append(s.klass).append("\nAttendance %: ").append(String.format("%.2f", s.getAttendancePercentage())).append("%\n\nHistory (date : P/A):\n");
        if(s.attendance.isEmpty()) sb.append("No attendance history");
        else for(Map.Entry<String,String> e: s.attendance.entrySet()) sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
        JOptionPane.showMessageDialog(this,new JScrollPane(new JTextArea(sb.toString())), "Attendance History", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- Analysis ----------------
    private void showAnalysis(){
        StringBuilder sb = new StringBuilder();
        sb.append("Total teachers: ").append(DataStore.teachers.size()).append("\n");
        sb.append("Total students: ").append(DataStore.students.size()).append("\n");
        sb.append("\nStudents per class:\n");
        for(DataStore.ClassRoom c: DataStore.classes) sb.append("Class ").append(c.name).append(": ").append(c.studentIds.size()).append("\n");
        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())), "Analysis", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- New: show all students ----------------
    private void showAllStudents() {
        StringBuilder sb = new StringBuilder();
        for (DataStore.Student s : DataStore.students) {
            sb.append("ID: ").append(s.id).append("  |  Name: ").append(s.name).append("  |  Class: ").append(s.klass).append("\n");
        }
        if (sb.length() == 0) sb.append("No students available");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "All Students", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- New: show all teachers ----------------
    private void showAllTeachers() {
        StringBuilder sb = new StringBuilder();
        for (DataStore.Teacher t : DataStore.teachers) {
            sb.append("ID: ").append(t.id).append("  |  Name: ").append(t.name).append("  |  Assigned: ");
            if (t.assignedClassSubjects == null || t.assignedClassSubjects.isEmpty()) sb.append("None");
            else {
                List<String> pairs = new ArrayList<>();
                for (Map.Entry<String, List<String>> e : t.assignedClassSubjects.entrySet()) {
                    pairs.add(e.getKey() + ":" + String.join(",", e.getValue()));
                }
                sb.append(String.join(" ; ", pairs));
            }
            sb.append("\n");
        }
        if (sb.length() == 0) sb.append("No teachers available");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "All Teachers", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        
        // Launch admin dashboard
        SwingUtilities.invokeLater(AdminDashboard::new);
    }
}
