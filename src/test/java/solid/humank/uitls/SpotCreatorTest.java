package solid.humank.uitls;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpotCreatorTest {

    @Test
    public void createSpotTest(){
        SpotCreator creator = new SpotCreator();
        creator.create();
    }


    @Test
    public void createsimple(){

        SpotCreator creator = new SpotCreator();
        creator.simple();
    }

    @Test
    public void showsgTest(){
        SpotCreator creator = new SpotCreator();
        creator.showsgs();
    }

    @Test
    public void aaa(){

    }
}