package com.deshijugad.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deshijugad.model.User;
import com.deshijugad.utils.Log;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUser(String email, String password) {		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		User user = new User();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_user (:in_email, :in_password)")
					.setParameter("in_email", email).setParameter("in_password", password);
			procedure.setResultTransformer(Transformers.aliasToBean(User.class));		
			user = (User) procedure.uniqueResult();	
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}				
		return user;
	}

	@Override
	public boolean setUser(User user) throws MySQLIntegrityConstraintViolationException {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_insert_user (:firstname, :lastname, :image, :email, :password, :mobile, :pincode)")
					.setParameter("firstname", user.getFirstname())
					.setParameter("lastname", user.getLastname())
					.setParameter("image", user.getImage())
					.setParameter("email", user.getEmail())
					.setParameter("password", user.getPassword())
					.setParameter("mobile", user.getMobile())
					.setParameter("pincode", user.getPincode());
		
			int result = procedure.executeUpdate();
			Log.e("User Registration Result => " + result);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);	
			return false;
		}
	}
}