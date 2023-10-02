package net.bozahouse.backend.model.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "newsletters")
public class Newsletter extends AbstractEntity {

    private String subject;
    @Column(name = "frenchContent", columnDefinition = "LONGTEXT")
    private String frenchContent;
    @Column(name = "englishContent", columnDefinition = "LONGTEXT")
    private String englishContent;
    private Date sendingDate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;
}
