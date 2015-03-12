package com.github.tilastokeskus.daboia.core;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SnakeTest {
    
    private Snake snake;
    
    public SnakeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.snake = new Snake(5, 5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructor_shouldSetInitialPiece() {
        List<Piece> pieces = snake.getPieces();
        assertTrue(pieces.size() == 1);
        Piece p = pieces.get(0);
        assertTrue(p.x == 5 && p.y == 5);
    }

    @Test
    public void constructor_shouldSetInitialLengths() {
        assertTrue(snake.getLength() == 3);
        assertTrue(snake.getTrueLength() == 1);
    }
}
