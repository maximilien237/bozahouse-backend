package net.bozahouse.backend.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "newsletters")
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    @Column(name = "frenchContent", columnDefinition = "LONGTEXT")
    private String frenchContent;
    @Column(name = "englishContent", columnDefinition = "LONGTEXT")
    private String englishContent;
    private Date sendingDate;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    private AppUser user;
}
