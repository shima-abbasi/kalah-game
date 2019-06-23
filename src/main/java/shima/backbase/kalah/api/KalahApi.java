package shima.backbase.kalah.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import shima.backbase.kalah.dto.KalahApiDTO;
import shima.backbase.kalah.service.KalahService;

@RestController
@PropertySource(value = "classpath:kalah-api.properties")
public class KalahApi {

    @Autowired
    private KalahService kalahService;

    @RequestMapping("${CREATE_GAME}")
    public KalahApiDTO createGame() {
        return new KalahApiDTO(Boolean.FALSE, null, kalahService.createGame());
    }

    @RequestMapping("${MOVE}")
    public KalahApiDTO move(@PathVariable(value = "gameId") int gameId,
                            @PathVariable(value = "pitId") int pitId){
        return new KalahApiDTO(Boolean.FALSE, null, kalahService.move(gameId, pitId));
    }
}
