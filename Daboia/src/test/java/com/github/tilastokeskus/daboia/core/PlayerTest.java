package com.github.tilastokeskus.daboia.core;

import com.github.tilastokeskus.daboia.core.game.ai.DaboiaLogic;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tilastokeskus
 */
public class PlayerTest {
    
    private Player player;
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        player = new Player(1, 1, 1, "player");
        player.setLogicHandler(new MockLogic());
        player.setSnake(new Snake(0, 0));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void method_copy_shouldReturnCopy() {        
        Player p = player.copy();
        assertTrue(player.equals(p));
    }

    @Test
    public void method_copy_shouldReturnCopyFast() {
        for (int i = 0; i < 1600; i++) {
            player.getSnake().grow();
            player.getSnake().move(Direction.RIGHT);
        }
        
        for (int i = 0; i < 10000; i++)
            player.copy();
    }

    @Test
    public void method_setLogicHandler_shouldSetLogicHandlerToPlayerAndPlayerToLogicHandler() {
        MockLogic logic = new MockLogic();
        player.setLogicHandler(logic);
        assertTrue(logic == player.getLogicHandler());
        assertFalse(player == logic.getPlayer());
        assertEquals(player, logic.getPlayer());
    }
    
    private boolean onlyEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2) && o1 != o2;
    }
    
    private class MockLogic extends DaboiaLogic {
        int id = (int) (Math.random() * Integer.MAX_VALUE);
        
        @Override
        public void onLaunch() {}

        @Override
        public String getMove() {
            return "UP";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final MockLogic other = (MockLogic) obj;
            return this.id == other.id;
        }
    }
}
