package org.batch.chunk.ItemReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemReader implements ItemReader<Integer> {
    private final List<Integer> list = List.of(1, 2, 3, 4, 5);
    int i = 0;

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Inside FirstItemReader: ");
        Integer item;
        if (i< list.size()) {
            item = list.get(i);
            i++;
            return item;
        }
        i = 0;
        return null;
    }
}
