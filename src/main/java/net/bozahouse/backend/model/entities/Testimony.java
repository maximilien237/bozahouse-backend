package net.bozahouse.backend.model.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "testimonies")
public class Testimony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date createdAt;
    private Date updatedAt;
    @Column(name = "message",length = 500)
    private String message;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private AppUser author;
}
