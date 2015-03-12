package com.github.tilastokeskus.daboia.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    
    private Board board;
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new Board(20, 20);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructor_shouldInitAppleToMiddle() {
        assertTrue(board.getApple().x == 10 && board.getApple().y == 10);
    }
    
    @Test
    public void constructor_boardCore_shouldInitAppleToMiddle() {
        assertTrue(board.getCore()[10][10] == BoardConstant.APPLE);
    }
    
    @Test
    public void method_set_shouldSetDataToCore() {
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++) {
                assertFalse(board.get(i, j) == BoardConstant.SNAKE_HEAD);
                board.set(i, j, BoardConstant.SNAKE_HEAD);
                assertTrue(board.get(i, j) == BoardConstant.SNAKE_HEAD);
            }
    }
    
    @Test (timeout = 1000)
    public void method_set_shouldSetDataToCoreFast() {
        for (int k = 0; k < 100000; k++) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    board.set(i, j, BoardConstant.SNAKE_HEAD);
            
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    board.set(i, j, BoardConstant.FLOOR);
        }
    }
    
    @Test
    public void method_set_shouldUpdateUnoccupiedCount() {
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++) {
                board.set(i, j, BoardConstant.SNAKE_BODY);
                assertTrue(board.numUnoccupied() == 400 - (j + i*20 + 1));
            }
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void method_set_shouldThrowExceptionOnInvalidBounds() {
        board.set(21, 21, BoardConstant.SNAKE_BODY);
    }
    
    @Test
    public void method_setApple_shouldSetApple() {
        Piece a = new Piece(4, 2);
        board.setApple(a);
        assertTrue(board.getApple() == a);
    }
    
    @Test
    public void method_randomlyPlaceApple_shouldRandomlyPlaceApple() {
        Piece a = new Piece(4, 2);
        board.setApple(a);
        board.randomlyPlaceApple();
        assertTrue(board.getApple() != a);
    }
}
