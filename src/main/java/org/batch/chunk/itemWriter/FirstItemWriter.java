package org.batch.chunk.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<Long> {
    @Override
    public void write(Chunk<? extends Long> chunk) throws Exception {
        System.out.println("Inside FirstItemWriter: ");
        chunk.getItems().forEach(System.out::println);
    }
    private void testClass() {

    }
}
