package name.mutant.dough.web;

import name.mutant.dough.data.Acct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by lynchnf on 7/14/17.
 */
public class AcctListForm implements Page<Acct> {
    private String whereNameContains;
    private Page<Acct> innerPage;

    public AcctListForm(String whereNameContains, Page<Acct> innerPage) {
        this.whereNameContains = whereNameContains;
        this.innerPage = innerPage;
    }

    public String getWhereNameContains() {
        return whereNameContains;
    }

    @Override
    public int getTotalPages() {
        return innerPage.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return innerPage.getTotalElements();
    }

    @Override
    public <S> Page<S> map(Converter<? super Acct, ? extends S> converter) {
        return innerPage.map(converter);
    }

    @Override
    public int getNumber() {
        return innerPage.getNumber();
    }

    @Override
    public int getSize() {
        return innerPage.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return innerPage.getNumberOfElements();
    }

    @Override
    public List<Acct> getContent() {
        return innerPage.getContent();
    }

    @Override
    public boolean hasContent() {
        return innerPage.hasContent();
    }

    @Override
    public Sort getSort() {
        return innerPage.getSort();
    }

    @Override
    public boolean isFirst() {
        return innerPage.isFirst();
    }

    @Override
    public boolean isLast() {
        return innerPage.isLast();
    }

    @Override
    public boolean hasNext() {
        return innerPage.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return innerPage.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return innerPage.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return innerPage.previousPageable();
    }

    @Override
    public Iterator<Acct> iterator() {
        return innerPage.iterator();
    }

    @Override
    public void forEach(Consumer<? super Acct> consumer) {
        innerPage.forEach(consumer);
    }

    @Override
    public Spliterator<Acct> spliterator() {
        return innerPage.spliterator();
    }
}