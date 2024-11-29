package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "instructions")

public class Instruction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idInstruction;
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "super_admin_id")
    private AppUser superAdmin;

    public AppUser getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(AppUser superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdInstruction() {
        return idInstruction;
    }

    public void setIdInstruction(Long idInstruction) {
        this.idInstruction = idInstruction;
    }
}
