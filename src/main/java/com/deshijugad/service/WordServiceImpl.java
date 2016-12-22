package com.deshijugad.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deshijugad.model.Category;
import com.deshijugad.model.Word;
import com.deshijugad.utils.Log;

@Service("WordService")
public class WordServiceImpl implements WordService {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	public List<Word> getWords(int userId, int categoryId, int offset, int limit) {		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Word> words = new ArrayList<Word>();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_words (:in_userId, :in_categoryId, :in_offset, :in_limit)")
					.setParameter("in_userId", userId)
					.setParameter("in_categoryId", categoryId)
					.setParameter("in_offset", offset)
					.setParameter("in_limit", limit);				
			procedure.setResultTransformer(Transformers.aliasToBean(Word.class));		
			words = procedure.list();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}	
		return words;
	}

	@Override
	public int insertWord(Word word) {
		int result = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_insert_word (:in_title, :in_description, :in_translation, "
					+ ":in_pronunciation, :in_key, :in_method, :in_synonyms, :in_antonyms, :in_image, :in_status, :in_category)")
					.setParameter("in_title", word.getTitle())
					.setParameter("in_description", word.getDescription())
					.setParameter("in_translation", word.getTranslation())
					.setParameter("in_pronunciation", word.getPronunciation())
					.setParameter("in_key", word.getKey())
					.setParameter("in_method", word.getMethod())
					.setParameter("in_synonyms", word.getSynonyms())
					.setParameter("in_antonyms", word.getAntonyms())
					.setParameter("in_image", word.getImage())
					.setParameter("in_status", word.getStatus())
					.setParameter("in_category", word.getCategory());
					
			result = procedure.executeUpdate();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}
		return result;
	}

	@Override
	public List<Category> getCategories() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Category> categories = new ArrayList<Category>();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_categories ()");				
			procedure.setResultTransformer(Transformers.aliasToBean(Category.class));		
			categories = procedure.list();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}
		return categories;
	}
}