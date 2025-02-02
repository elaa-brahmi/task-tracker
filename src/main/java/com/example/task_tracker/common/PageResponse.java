package com.example.task_tracker.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private long totalPages;
    private boolean first;
    private boolean last;// to know it the page is the first one or the last one

}
