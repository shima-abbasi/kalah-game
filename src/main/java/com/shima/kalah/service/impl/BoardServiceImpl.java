package com.shima.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shima.kalah.model.Board;
import com.shima.kalah.repository.BoardRepo;
import com.shima.kalah.service.BoardService;


@Service
@Transactional
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
