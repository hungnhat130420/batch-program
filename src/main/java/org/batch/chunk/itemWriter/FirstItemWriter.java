package org.batch.chunk.itemWriter;

import org.batch.model.StudentCSV;
import org.batch.model.StudentJDBC;
import org.batch.model.StudentJson;
import org.batch.model.StudentXML;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<StudentJDBC> {
    @Override
    public void write(Chunk<? extends StudentJDBC> chunk) throws Exception {
        System.out.println("Inside FirstItemWriter: ");
        chunk.getItems().forEach(System.out::println);
    }
}
