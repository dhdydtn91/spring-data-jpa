package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

@SpringBootTest
class itemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    
    @Test
    public void save() throws Exception{
        //given
        Item item = new Item("A");
        itemRepository.save(item);
        //when
        
        //then
    }
}