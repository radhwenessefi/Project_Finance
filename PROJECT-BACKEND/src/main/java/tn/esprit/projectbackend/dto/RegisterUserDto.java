package tn.esprit.projectbackend.dto;

public class RegisterUserDto {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String address;

    public RegisterUserDto() {
        // Constructeur par défaut
    }

    public RegisterUserDto(String nom, String prenom, String email, String password, String address, String langue) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public String getNom() {
        return nom;
    }

    public RegisterUserDto setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getPrenom() {
        return prenom;
    }

    public RegisterUserDto setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RegisterUserDto setAddress(String address) {
        this.address = address;
        return this;
    }


    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
