package com.deshijugad.service;

import java.util.List;

import com.deshijugad.model.Category;
import com.deshijugad.model.Word;

public interface WordService {
	List<Category> getCategories();
	int insertWord(Word word);
	List<Word> getWords(int userId, int categoryId, int offset, int limit);
}
