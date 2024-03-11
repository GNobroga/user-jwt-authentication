package com.apiauth.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public final class Pagination {

    private static final int MAX_SIZE = 50;
    private static final int MIN_SIZE = 10;
    
    private int size;

    private int page;

	public int getSize() {
        return size <= 0 ? MIN_SIZE : size > MAX_SIZE ? MAX_SIZE : size;
	}

	public void setSize(int size) {
		this.size = size <= 0 ? MIN_SIZE : size > MAX_SIZE ? MAX_SIZE : size; 
	}

	public int getPage() {
		return Math.max(page, 0);
	}

	public void setPage(int page) {
		this.page = Math.max(page, 0);
	}

}
