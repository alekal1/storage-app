package ee.alekal.storage.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static ee.alekal.storage.utils.AppConstants.DEFAULT_ALLOCATION_SIZE;
import static ee.alekal.storage.utils.AppConstants.PERSON_SEQUENCE;

@Data
@Entity
@Table(name = "person")
@NoArgsConstructor
public class Person {

    @Id
    @SequenceGenerator(
            name = PERSON_SEQUENCE,
            sequenceName = PERSON_SEQUENCE,
            allocationSize = DEFAULT_ALLOCATION_SIZE
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = PERSON_SEQUENCE
    )
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_type")
    private String profileType;

}
