package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shima.backbase.kalah.model.Board;
import shima.backbase.kalah.repository.BoardRepo;
import shima.backbase.kalah.service.BoardService;


@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepo boardRepo;

    @Override
    public Board initiateBoard() {
        Board board = new Board();
        boardRepo.save(board);
        return board;
    }
}
