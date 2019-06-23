package shima.backbase.kalah.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Basic implements Serializable {

    private Long id;

    public Basic() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myseq")
    @SequenceGenerator(name = "myseq", sequenceName = "MY_SEQ")
    @Column(name = "C_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

