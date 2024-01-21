package org.batch.chunk.itemWriter;

import org.batch.model.Student;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<Student> {
    @Override
    public void write(Chunk<? extends Student> chunk) throws Exception {
        System.out.println("Inside FirstItemWriter: ");
        chunk.getItems().forEach(System.out::println);
    }
}
