package views;

import controlador.ClientController;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase se encarga de gestionar lo que tiene que ver con la views de autenticación
 * Created by Marc on 13/3/18.
 */
public class AuthenticationView extends JFrame{

    //Paneles
    private JPanel jpEnter;
    private JPanel jpRegister;
    private JPanel jpUsername;
    private JPanel jpCorreo;
    private JPanel jpPassword;
    private JPanel jpConfirmacio;

    //Etiquetas
    private JLabel jlName;
    private JLabel jlUsername;
    private JLabel jlCorreo;
    private JLabel jlPassword;
    private JLabel jlContrasenya;
    private JLabel jlConfirmacio;

    //Campos de texto o contraseña
    private JTextField jtfName;
    private JTextField jtfUsername;
    private JTextField jtfCorreo;
    private JPasswordField jpfPassword;
    private JPasswordField jpfContrasenya;
    private JPasswordField jpfConfirmacio;

    //Botones
    private JButton jbSignIn;
    private JButton jbSignUp;
    private JButton jbLogOut;

    /**
     * Constructor que crea toda la estructura de la parte visual de la autenticación del programa cliente
     */
    public AuthenticationView(){

        jpEnter = new JPanel(new FlowLayout());
        jpEnter.setBorder(BorderFactory.createTitledBorder("SIGN IN"));

        jlName = new JLabel("Username/Email:");
        jpEnter.add(jlName);

        jtfName = new JTextField();
        jtfName.setColumns(10);     //Longitud del campo de texto
        jpEnter.add(jtfName);

        jlPassword = new JLabel(" Password:");
        jpEnter.add(jlPassword);

        jpfPassword = new JPasswordField();
        jpfPassword.setColumns(10);
        jpEnter.add(jpfPassword);

        jbSignIn = new JButton("Sign In");
        jpEnter.add(jbSignIn);

        add(jpEnter, BorderLayout.NORTH);

        jpRegister = new JPanel(new GridLayout(5,1));
        jpRegister.setBorder(BorderFactory.createTitledBorder("SIGN UP"));

        jpUsername = new JPanel(new FlowLayout());
        jlUsername = new JLabel("Username:             ");
        jpUsername.add(jlUsername);
        jtfUsername = new JTextField();
        jtfUsername.setColumns(25);
        jpUsername.add(jtfUsername);
        jpRegister.add(jpUsername);

        jpCorreo = new JPanel(new FlowLayout());
        jlCorreo = new JLabel("Email:                    ");
        jpCorreo.add(jlCorreo);
        jtfCorreo = new JTextField();
        jtfCorreo.setColumns(25);
        jpCorreo.add(jtfCorreo);
        jpRegister.add(jpCorreo);

        jpPassword = new JPanel(new FlowLayout());
        jlContrasenya = new JLabel("Password:              ");
        jpPassword.add(jlContrasenya);
        jpfContrasenya = new JPasswordField();
        jpfContrasenya.setColumns(25);
        jpPassword.add(jpfContrasenya);
        jpRegister.add(jpPassword);

        jpConfirmacio = new JPanel(new FlowLayout());
        jlConfirmacio = new JLabel("Confirm Password:");
        jpConfirmacio.add(jlConfirmacio);
        jpfConfirmacio = new JPasswordField();
        jpfConfirmacio.setColumns(25);
        jpConfirmacio.add(jpfConfirmacio);
        jpRegister.add(jpConfirmacio);


        jbSignUp = new JButton("Sign Up");
        jpRegister.add(jbSignUp);

        add(jpRegister, BorderLayout.CENTER);

        jbLogOut = new JButton("Exit");

        add(jbLogOut, BorderLayout.SOUTH);

        setSize(600,350);                           //Tamaño de la ventana
        setResizable(false);                                      //Tamaño fijo
        setTitle("Organizer Authentication");                     //Título de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //Cerrar cuando se aprieta la cruz roja
        setLocationRelativeTo(null);                              //Poner la ventana en el centro de la pantalla
    }

    /**
     * Procedimiento que conecta el controlador con la views
     * @param controller controlador
     */
    public void registerController(ClientController controller){

        jbSignIn.setActionCommand("SIGNIN");
        jbSignIn.addActionListener(controller);

        jbSignUp.setActionCommand("SIGNUP");
        jbSignUp.addActionListener(controller);

        jbLogOut.setActionCommand("LOGOUT");
        jbLogOut.addActionListener(controller);

        //addWindowListener(windowListener);
    }

    public String getUsername(){
        return jtfUsername.getText();
    }

    public void setContrasenya(String s) {
        this.jpfContrasenya.setText(s);
    }

    public void setConfirmacio(String s) {
        this.jpfConfirmacio.setText(s);
    }

    public void setCorreo(String s) {
        this.jtfCorreo.setText(s);
    }

    public String getPassword(){
        return new String(jpfPassword.getPassword());
    }

    public String getNom(){
        return jtfName.getText();
    }

    public String getCorreu(){
        return jtfCorreo.getText();
    }

    public String getContrasenya() {
        return new String(jpfContrasenya.getPassword());
    }

    public String getConfirmacio() {
        return new String(jpfConfirmacio.getPassword());
    }

    /**
     * Pop up de error (contraseña menor de 8 carácteres)
     */
    public void shortPasswordError(){
        JOptionPane.showMessageDialog(null, "La contraseña tiene que ser como mínimo de 8 carácteres!",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Pop up de error (contraseña no contiene al menos una mayúscula, una minúscula y un número)
     */
    public void numberUpperLowerCasePassword(){
        JOptionPane.showMessageDialog(null, "La contraseña tiene que tener como mínimo un número, una letra minúscula y una mayúscula!",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Pop up de error (la contraseña y la confirmación no coinciden)
     */
    public void passwordsDontMatch(){
        JOptionPane.showMessageDialog(null, "La contraseña y la confirmación no coinciden!",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Pop de error (el formato del email no es correcto)
     */
    public void badEmailFormat(){
        JOptionPane.showMessageDialog(null, "El formato del email no es correcto!",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Comprueba que le formato del email es correcto
     * @param emailStr email
     * @return booleano que indica si el formato del email es correcto o no
     */
    public boolean validate(String emailStr) {

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /**
     * Comprueba que la contraseña tiene al menos una mayúscula, una minúscula y un número
     * @param str contraseña
     * @return booleano que indica que la contraseña contiene al menos una mayúscula, una minúscula y un número
     */
    public boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag) {
                return true;
            }
        }
        return false;
    }
}
