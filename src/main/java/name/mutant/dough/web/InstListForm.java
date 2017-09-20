package name.mutant.dough.web;

import name.mutant.dough.data.Inst;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by lynchnf on 9/15/17.
 */
public class InstListForm implements Page<Inst> {
    private Page<Inst> innerPage;
    private Sort.Order firstSortOrder;

    public InstListForm(Page<Inst> innerPage) {
        this.innerPage = innerPage;
        firstSortOrder = innerPage.getSort().iterator().next();
    }

    public String getSortColumn() {
        return firstSortOrder.getProperty();
    }

    public Sort.Direction getSortDirection() {
        return firstSortOrder.getDirection();
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
    public <S> Page<S> map(Converter<? super Inst, ? extends S> converter) {
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
    public List<Inst> getContent() {
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
    public Iterator<Inst> iterator() {
        return innerPage.iterator();
    }

    @Override
    public void forEach(Consumer<? super Inst> consumer) {
        innerPage.forEach(consumer);
    }

    @Override
    public Spliterator<Inst> spliterator() {
        return innerPage.spliterator();
    }
}