package shima.backbase.kalah.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "T_BOARD")
public class Board extends Basic {
    private List<Pit> pits;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }
}
