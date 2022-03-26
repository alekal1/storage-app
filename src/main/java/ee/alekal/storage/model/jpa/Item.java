package ee.alekal.storage.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.math.BigInteger;
import java.time.LocalDate;

import static ee.alekal.storage.utils.AppConstants.DEFAULT_ALLOCATION_SIZE;
import static ee.alekal.storage.utils.AppConstants.ITEM_SEQUENCE;

@Data
@Entity
@Table(name = "item")
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(
            name = ITEM_SEQUENCE,
            sequenceName = ITEM_SEQUENCE,
            allocationSize = DEFAULT_ALLOCATION_SIZE
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = ITEM_SEQUENCE
    )
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "picture")
    private String picturePath;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "color")
    private String color;

    @Column(name = "last_accessed_on")
    private LocalDate lastAccessedOn;

    @Column(name = "size")
    private BigInteger size;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "parent_item_id")
    private Item parentItem;
}
